---
title: data freshness data quality checks
---
# data freshness data quality checks

A table-level check that calculates the time difference between the most recent row in the table and the current time.
 The timestamp column that is used for comparison is defined as the timestamp_columns.event_timestamp_column on the table configuration.
 This check is also known as &quot;Data Freshness&quot;.


___
The **data freshness** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile data freshness


**Check description**

Calculates the number of days since the most recent event timestamp (freshness)

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_data_freshness`</span>|Data freshness (Maximum age of the most recent row)|[timeliness](../../../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |[Timeliness](../../../dqo-concepts/data-quality-dimensions.md#data-timeliness)|[*data_freshness*](../../../reference/sensors/table/timeliness-table-sensors.md#data-freshness)|[*max_days*](../../../reference/rules/Comparison.md#max-days)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile data freshness data quality check.

??? example "Managing profile data freshness check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_data_freshness --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_data_freshness --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_data_freshness --enable-warning
                            -Wmax_days=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_data_freshness --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_data_freshness --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_data_freshness --enable-error
                            -Emax_days=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_data_freshness* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_data_freshness
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_data_freshness
        ```

        You can also run this check on all tables  on which the *profile_data_freshness* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_data_freshness
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
      profile_data_freshness:
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
    [data_freshness](../../../reference/sensors/table/timeliness-table-sensors.md#data-freshness)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    CURRENT_DATETIME(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDate(now())
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDateTime(now())
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        toDateTime64OrNull({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}, 3)
                    ),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        toDateTime64OrNull(analyzed_table."col_event_timestamp", 3)
                    ),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_DATETIME())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DAYS_BETWEEN(CURRENT_DATE - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- else -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) / 24.0 / 3600.0 AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::DATE
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM  AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                NANO100_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DAYS_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                NANO100_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- else -%}
                NANO100_BETWEEN(
                    MAX(
                        TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                NANO100_BETWEEN(
                    MAX(
                        TO_TIMESTAMP(analyzed_table."col_event_timestamp")
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000 AS actual_value
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE()
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`col_event_timestamp`)
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE()
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`col_event_timestamp`)
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- else -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_event_timestamp") AS DATE)) AS actual_value
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    TODAY() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    NOW() - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(
                        TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                    )
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    MAX(
                        TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")
                    )
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_DATETIME())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    BIGINT(CURRENT_TIMESTAMP())
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(DAY,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        GETDATE()
                    )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        GETDATE()
                    ) / 24.0 / 3600.0
                {%- else -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0 AS actual_value
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0 AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
          profile_data_freshness:
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
    [data_freshness](../../../reference/sensors/table/timeliness-table-sensors.md#data-freshness)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    CURRENT_DATETIME(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP(),
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
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDate(now())
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDateTime(now())
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        toDateTime64OrNull({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}, 3)
                    ),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        toDateTime64OrNull(analyzed_table."col_event_timestamp", 3)
                    ),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_DATETIME())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    BIGINT(CURRENT_TIMESTAMP())
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
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DAYS_BETWEEN(CURRENT_DATE - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- else -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) / 24.0 / 3600.0 AS actual_value,
            
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
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::DATE
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                NANO100_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DAYS_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                NANO100_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- else -%}
                NANO100_BETWEEN(
                    MAX(
                        TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                NANO100_BETWEEN(
                    MAX(
                        TO_TIMESTAMP(analyzed_table."col_event_timestamp")
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000 AS actual_value,
            
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE()
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`col_event_timestamp`)
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE()
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`col_event_timestamp`)
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- else -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_event_timestamp") AS DATE)) AS actual_value,
            
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    TODAY() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    NOW() - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
            
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(
                        TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                    )
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    MAX(
                        TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")
                    )
                    CURRENT_TIMESTAMP
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_DATETIME())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    BIGINT(CURRENT_TIMESTAMP())
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(DAY,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        GETDATE()
                    )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        GETDATE()
                    ) / 24.0 / 3600.0
                {%- else -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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


## daily data freshness


**Check description**

Daily calculating the number of days since the most recent event timestamp (freshness)

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_data_freshness`</span>|Data freshness (Maximum age of the most recent row)|[timeliness](../../../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|[Timeliness](../../../dqo-concepts/data-quality-dimensions.md#data-timeliness)|[*data_freshness*](../../../reference/sensors/table/timeliness-table-sensors.md#data-freshness)|[*max_days*](../../../reference/rules/Comparison.md#max-days)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily data freshness data quality check.

??? example "Managing daily data freshness check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_data_freshness --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_data_freshness --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_data_freshness --enable-warning
                            -Wmax_days=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_data_freshness --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_data_freshness --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_data_freshness --enable-error
                            -Emax_days=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_data_freshness* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_data_freshness
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_data_freshness
        ```

        You can also run this check on all tables  on which the *daily_data_freshness* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_data_freshness
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
        daily_data_freshness:
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
    [data_freshness](../../../reference/sensors/table/timeliness-table-sensors.md#data-freshness)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    CURRENT_DATETIME(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDate(now())
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDateTime(now())
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        toDateTime64OrNull({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}, 3)
                    ),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        toDateTime64OrNull(analyzed_table."col_event_timestamp", 3)
                    ),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_DATETIME())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DAYS_BETWEEN(CURRENT_DATE - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- else -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) / 24.0 / 3600.0 AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::DATE
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM  AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                NANO100_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DAYS_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                NANO100_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- else -%}
                NANO100_BETWEEN(
                    MAX(
                        TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                NANO100_BETWEEN(
                    MAX(
                        TO_TIMESTAMP(analyzed_table."col_event_timestamp")
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000 AS actual_value
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE()
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`col_event_timestamp`)
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE()
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`col_event_timestamp`)
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- else -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_event_timestamp") AS DATE)) AS actual_value
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    TODAY() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    NOW() - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(
                        TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                    )
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    MAX(
                        TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")
                    )
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_DATETIME())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    BIGINT(CURRENT_TIMESTAMP())
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(DAY,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        GETDATE()
                    )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        GETDATE()
                    ) / 24.0 / 3600.0
                {%- else -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0 AS actual_value
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0 AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
            daily_data_freshness:
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
    [data_freshness](../../../reference/sensors/table/timeliness-table-sensors.md#data-freshness)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    CURRENT_DATETIME(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP(),
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
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDate(now())
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDateTime(now())
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        toDateTime64OrNull({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}, 3)
                    ),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        toDateTime64OrNull(analyzed_table."col_event_timestamp", 3)
                    ),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_DATETIME())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    BIGINT(CURRENT_TIMESTAMP())
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
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DAYS_BETWEEN(CURRENT_DATE - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- else -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) / 24.0 / 3600.0 AS actual_value,
            
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
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::DATE
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                NANO100_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DAYS_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                NANO100_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- else -%}
                NANO100_BETWEEN(
                    MAX(
                        TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                NANO100_BETWEEN(
                    MAX(
                        TO_TIMESTAMP(analyzed_table."col_event_timestamp")
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000 AS actual_value,
            
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE()
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`col_event_timestamp`)
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE()
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`col_event_timestamp`)
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- else -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_event_timestamp") AS DATE)) AS actual_value,
            
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    TODAY() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    NOW() - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
            
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(
                        TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                    )
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    MAX(
                        TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")
                    )
                    CURRENT_TIMESTAMP
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_DATETIME())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    BIGINT(CURRENT_TIMESTAMP())
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(DAY,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        GETDATE()
                    )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        GETDATE()
                    ) / 24.0 / 3600.0
                {%- else -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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


## monthly data freshness


**Check description**

Monthly monitoring calculating the number of days since the most recent event timestamp (freshness)

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_data_freshness`</span>|Data freshness (Maximum age of the most recent row)|[timeliness](../../../categories-of-data-quality-checks/how-to-detect-timeliness-and-freshness-issues.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|monthly|[Timeliness](../../../dqo-concepts/data-quality-dimensions.md#data-timeliness)|[*data_freshness*](../../../reference/sensors/table/timeliness-table-sensors.md#data-freshness)|[*max_days*](../../../reference/rules/Comparison.md#max-days)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly data freshness data quality check.

??? example "Managing monthly data freshness check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_data_freshness --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_data_freshness --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_data_freshness --enable-warning
                            -Wmax_days=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_data_freshness --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_data_freshness --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_data_freshness --enable-error
                            -Emax_days=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_data_freshness* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_data_freshness
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_data_freshness
        ```

        You can also run this check on all tables  on which the *monthly_data_freshness* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_data_freshness
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
        monthly_data_freshness:
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
    [data_freshness](../../../reference/sensors/table/timeliness-table-sensors.md#data-freshness)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    CURRENT_DATETIME(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDate(now())
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDateTime(now())
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        toDateTime64OrNull({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}, 3)
                    ),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        toDateTime64OrNull(analyzed_table."col_event_timestamp", 3)
                    ),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_DATETIME())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_schema>`.`<target_table>` AS analyzed_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DAYS_BETWEEN(CURRENT_DATE - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- else -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) / 24.0 / 3600.0 AS actual_value
            FROM (
                SELECT
                    original_table.*
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::DATE
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM  AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                NANO100_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DAYS_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                NANO100_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- else -%}
                NANO100_BETWEEN(
                    MAX(
                        TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                NANO100_BETWEEN(
                    MAX(
                        TO_TIMESTAMP(analyzed_table."col_event_timestamp")
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000 AS actual_value
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE()
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`col_event_timestamp`)
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE()
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`col_event_timestamp`)
                ) / 24.0 / 3600.0 AS actual_value
            FROM `<target_table>` AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- else -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_event_timestamp") AS DATE)) AS actual_value
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    TODAY() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    NOW() - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value
            FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(
                        TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                    )
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    MAX(
                        TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")
                    )
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_DATETIME())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    BIGINT(CURRENT_TIMESTAMP())
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(DAY,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        GETDATE()
                    )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        GETDATE()
                    ) / 24.0 / 3600.0
                {%- else -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0 AS actual_value
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0 AS actual_value
            FROM "<target_schema>"."<target_table>" AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
            monthly_data_freshness:
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
    [data_freshness](../../../reference/sensors/table/timeliness-table-sensors.md#data-freshness)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    DAY
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATETIME_DIFF(
                    CURRENT_DATETIME(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP(),
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
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDate(now())
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    toDateTime(now())
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        toDateTime64OrNull({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}, 3)
                    ),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        toDateTime64OrNull(analyzed_table."col_event_timestamp", 3)
                    ),
                    toDateTime64(now(), 3)
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_DATETIME())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    BIGINT(CURRENT_TIMESTAMP())
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
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DAYS_BETWEEN(CURRENT_DATE - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- else -%}
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                SECONDS_BETWEEN(CURRENT_TIMESTAMP, CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) / 24.0 / 3600.0 AS actual_value,
            
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
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::DATE
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                NANO100_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DAYS_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                NANO100_BETWEEN(
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- else -%}
                NANO100_BETWEEN(
                    MAX(
                        TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                NANO100_BETWEEN(
                    MAX(
                        TO_TIMESTAMP(analyzed_table."col_event_timestamp")
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 / 10000 AS actual_value,
            
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE()
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`col_event_timestamp`)
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE()
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    SECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0
                {%- else -%}
                TIMESTAMPDIFF(
                    SECOND,
                    CURRENT_TIMESTAMP(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`col_event_timestamp`)
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- else -%}
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS DATE))
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_event_timestamp") AS DATE)) AS actual_value,
            
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    TODAY() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    NOW() - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                EXTRACT(EPOCH FROM (
                    NOW() - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
            
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )) / 24.0 / 3600.0
                {%- else -%}
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
                )) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_event_timestamp")::TIMESTAMP)
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(DAY,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(
                        TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                    )
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    MAX(
                        TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp")
                    )
                    CURRENT_TIMESTAMP
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE(),
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    BIGINT(CURRENT_DATETIME())
                    -
                    BIGINT(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                    BIGINT(CURRENT_TIMESTAMP())
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(DAY,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        GETDATE()
                    )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        GETDATE()
                    ) / 24.0 / 3600.0
                {%- else -%}
                    DATEDIFF(SECOND,
                        MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                DATEDIFF(
                    CURRENT_DATE,
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
                )
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- else -%}
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}) AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                (
                    EXTRACT(DAY FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 86400
                    + EXTRACT(HOUR FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 3600
                    + EXTRACT(MINUTE FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND)) * 60
                    + EXTRACT(SECOND FROM ((CURRENT_TIMESTAMP - CAST(MAX(analyzed_table."col_event_timestamp") AS TIMESTAMP)) DAY(4) TO SECOND))
                ) / 24.0 / 3600.0 AS actual_value,
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
            
            {% macro render_current_event_diff() -%}
                {%- if lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- elif lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'DAY',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_DATE
                ) AS DOUBLE)
                {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- else -%}
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
                ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
                {%- endif -%}
            {%- endmacro -%}
            
            SELECT
                {{ render_current_event_diff() }} AS actual_value
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
                CAST(DATE_DIFF(
                    'MILLISECOND',
                    MAX(
                        TRY_CAST(analyzed_table."col_event_timestamp" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
