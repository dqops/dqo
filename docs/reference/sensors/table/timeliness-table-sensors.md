---
title: DQOps data quality timeliness sensors
---
# DQOps data quality timeliness sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **timeliness** category supported by DQOps are listed below. Those sensors are measured on a table level.

---


## data freshness
Table sensor that runs a query calculating maximum days since the most recent event.

**Sensor summary**

The data freshness sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | timeliness | <span class="no-wrap-code">`table/timeliness/data_freshness`</span> | [*sensors/table/timeliness*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/timeliness/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

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
=== "ClickHouse"

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
=== "Databricks"

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
=== "DB2"

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
=== "DuckDB"

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
=== "HANA"

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
=== "MariaDB"

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
=== "MySQL"

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
=== "Oracle"

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
=== "PostgreSQL"

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
=== "Presto"

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
=== "Redshift"

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
=== "Snowflake"

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
=== "Spark"

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
=== "SQL Server"

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
=== "Trino"

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
___



## data ingestion delay
Table sensor that runs a query calculating the time difference in days between the most recent transaction timestamp and the most recent data loading timestamp.

**Sensor summary**

The data ingestion delay sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | timeliness | <span class="no-wrap-code">`table/timeliness/data_ingestion_delay`</span> | [*sensors/table/timeliness*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/timeliness/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

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
=== "ClickHouse"

    ```sql+jinja
    {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        DATE_DIFF(
            'MILLISECOND',
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        DATE_DIFF(
            'DAY'
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        DATE_DIFF(
            'MILLISECOND',
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        DATE_DIFF(
            'MILLISECOND',
            MAX(
                toDateTime64OrNull({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}, 3)
            ),
            MAX(
                toDateTime64OrNull({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}), 3)
            )
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
=== "Databricks"

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
=== "DB2"

    ```sql+jinja
    {% import '/dialects/db2.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
            SECONDS_BETWEEN(
                MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)),
                MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)))
            ) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
            DAYS_BETWEEN(
                MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)),
                MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)))
            )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
            SECONDS_BETWEEN(
                MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE)),
                MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
            ) / 24.0 / 3600.0
        {%- else -%}
            SECONDS_BETWEEN(
                MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)),
                MAX((CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)))
            ) / 24.0 / 3600.0
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
    ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

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
=== "HANA"

    ```sql+jinja
    {% import '/dialects/hana.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_max_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        NANO100_BETWEEN(
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        ) / 24.0 / 3600.0 / 1000.0 / 10000
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        CAST(DAYS_BETWEEN(
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        NANO100_BETWEEN(
            MAX({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        ) / 24.0 / 3600.0 / 1000.0 / 10000
        {%- else -%}
        NANO100_BETWEEN(
            MAX(
                TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
            ),
            MAX(
                TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )
        ) / 24.0 / 3600.0 / 1000.0
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
    ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MariaDB"

    ```sql+jinja
    {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
    
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
=== "MySQL"

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
=== "Oracle"

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
    ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

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
=== "Presto"

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
    ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"

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
=== "Snowflake"

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
=== "Spark"

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
=== "SQL Server"

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
=== "Trino"

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
    ) analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___



## data staleness
Table sensor that runs a query calculating the time difference in days between the current date and most recent data loading timestamp (staleness).

**Sensor summary**

The data staleness sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | timeliness | <span class="no-wrap-code">`table/timeliness/data_staleness`</span> | [*sensors/table/timeliness*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/timeliness/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        DATE_DIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            DAY
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        DATETIME_DIFF(
            CURRENT_DATETIME(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMP_DIFF(
            CURRENT_TIMESTAMP(),
            MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            MILLISECOND
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "ClickHouse"

    ```sql+jinja
    {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        DATE_DIFF(
            'MILLISECOND',
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            toDateTime64(now(), 3)
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        DATE_DIFF(
            'DAY',
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            toDate(now())
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        DATE_DIFF(
            'MILLISECOND',
            toDateTime(now()),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        DATE_DIFF(
            'MILLISECOND',
            MAX(
                toDateTime64OrNull({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}, 3)
            ),
            toDateTime64(now(), 3)
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
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
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        (
            BIGINT(CURRENT_TIMESTAMP())
            -
            BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
        ) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        DATEDIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        (
            BIGINT(CURRENT_DATETIME())
            -
            BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
        ) / 24.0 / 3600.0
        {%- else -%}
        (
            BIGINT(CURRENT_TIMESTAMP())
            -
            BIGINT(MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ))
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DB2"

    ```sql+jinja
    {% import '/dialects/db2.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
            SECONDS_BETWEEN(CURRENT_TIMESTAMP, MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
            DAYS_BETWEEN(CURRENT_DATE, MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
            SECONDS_BETWEEN(CURRENT_TIMESTAMP, MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})) / 24.0 / 3600.0
        {%- else -%}
            SECONDS_BETWEEN(CURRENT_TIMESTAMP, MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
        )) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP WITH TIME ZONE
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))::TIMESTAMP WITH TIME ZONE
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "HANA"

    ```sql+jinja
    {% import '/dialects/hana.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        NANO100_BETWEEN(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_TIMESTAMP
        ) / 24.0 / 3600.0 / 1000.0 / 10000
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        NANO100_BETWEEN(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_DATE
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        NANO100_BETWEEN(
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_TIMESTAMP
        ) / 24.0 / 3600.0 / 1000.0 / 10000
        {%- else -%}
        NANO100_BETWEEN(
            MAX(
                TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            ),
            CURRENT_TIMESTAMP
        ) / 24.0 / 3600.0 / 1000.0 / 10000
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
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
=== "MariaDB"

    ```sql+jinja
    {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        TIMESTAMPDIFF(
            SECOND,
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_TIMESTAMP()
        ) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        DATEDIFF(
            DAY,
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_DATE()
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        TIMESTAMPDIFF(
            SECOND,
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_TIMESTAMP()
        ) / 24.0 / 3600.0
        {%- else -%}
        TIMESTAMPDIFF(
            SECOND,
            MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)),
            CURRENT_TIMESTAMP()
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
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
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        TIMESTAMPDIFF(
            SECOND,
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_TIMESTAMP()
        ) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        DATEDIFF(
            DAY,
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_DATE()
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        TIMESTAMPDIFF(
            SECOND,
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_TIMESTAMP()
        ) / 24.0 / 3600.0
        {%- else -%}
        TIMESTAMPDIFF(
            SECOND,
            MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)),
            CURRENT_TIMESTAMP()
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
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
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
         (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) AS DATE))
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
             (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) AS DATE))
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
         (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) AS DATE))
        {%- else -%}
        (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}) AS DATE))
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
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
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
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
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        CAST(DATE_DIFF(
            'MILLISECOND',
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_TIMESTAMP
        ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        CAST(DATE_DIFF(
            'DAY',
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_DATE
        ) AS DOUBLE)
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        CAST(DATE_DIFF(
            'MILLISECOND',
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_TIMESTAMP
        ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        CAST(DATE_DIFF(
            'MILLISECOND',
            MAX(
                TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            CURRENT_TIMESTAMP
        ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
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
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
            CURRENT_DATE - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
        )) / 24.0 / 3600.0
        {%- else -%}
        EXTRACT(EPOCH FROM (
            CURRENT_TIMESTAMP - MAX(({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP)
        )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
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
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        TIMESTAMPDIFF(
            MILLISECOND,
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_TIMESTAMP
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        DATEDIFF(
            DAY,
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_DATE
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        TIMESTAMPDIFF(
            MILLISECOND,
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_TIMESTAMP
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        TIMESTAMPDIFF(
            MILLISECOND,
            MAX(
                TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            ),
            CURRENT_TIMESTAMP
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
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
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        (
            BIGINT(CURRENT_TIMESTAMP())
            -
            BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
        ) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        DATEDIFF(
            CURRENT_DATE(),
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        (
            BIGINT(CURRENT_DATETIME())
            -
            BIGINT(MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}))
        ) / 24.0 / 3600.0
        {%- else -%}
        (
            BIGINT(CURRENT_TIMESTAMP())
            -
            BIGINT(MAX(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ))
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
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
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
            DATEDIFF(SECOND,
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                SYSDATETIMEOFFSET()
            ) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
            DATEDIFF(DAY,
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                GETDATE()
            )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
            DATEDIFF(SECOND,
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                GETDATE()
            ) / 24.0 / 3600.0
        {%- else -%}
            DATEDIFF(SECOND,
                MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
                SYSDATETIMEOFFSET()
            ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
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
    
    {% macro render_current_ingestion_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        CAST(DATE_DIFF(
            'MILLISECOND',
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_TIMESTAMP
        ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        CAST(DATE_DIFF(
            'DAY',
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_DATE
        ) AS DOUBLE)
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true' -%}
        CAST(DATE_DIFF(
            'MILLISECOND',
            MAX({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}),
            CURRENT_TIMESTAMP
        ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        CAST(DATE_DIFF(
            'MILLISECOND',
            MAX(
                TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            ),
            CURRENT_TIMESTAMP
        ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_current_ingestion_diff() }} AS actual_value
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
___



## partition reload lag
Table sensor that runs a query calculating maximum difference in days between ingestion timestamp and event timestamp rows.

**Sensor summary**

The partition reload lag sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | timeliness | <span class="no-wrap-code">`table/timeliness/partition_reload_lag`</span> | [*sensors/table/timeliness*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/timeliness/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            TIMESTAMP_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DATE_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                DAY
            )
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DATETIME_DIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        MAX(
            TIMESTAMP_DIFF(
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                MILLISECOND
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "ClickHouse"

    ```sql+jinja
    {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DATE_DIFF(
                'MILLISECOND',
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DATE_DIFF(
                'DAY',
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DATE_DIFF(
                'MILLISECOND',
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        MAX(
            DATE_DIFF(
                'MILLISECOND',
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            BIGINT({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            -
            BIGINT({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        ) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DATEDIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            BIGINT({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            -
            BIGINT({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        ) / 24.0 / 3600.0
        {%- else -%}
        MAX(
            BIGINT(SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP))
            -
            BIGINT(SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP))
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DB2"

    ```sql+jinja
    {% import '/dialects/db2.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
            MAX(SECONDS_BETWEEN(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            )) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
            MAX(DAYS_BETWEEN(
                CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE),
                CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)
            ))
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
            MAX(SECONDS_BETWEEN(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            )) / 24.0 / 3600.0
        {%- else -%}
            MAX(SECONDS_BETWEEN(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            )) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        ) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        ) / 24.0 / 3600.0
        {%- else -%}
        MAX(
            EXTRACT(EPOCH FROM (
                ({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP - ({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP
            ))
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "HANA"

    ```sql+jinja
    {% import '/dialects/hana.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            NANO100_BETWEEN(
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0 / 1000.0 / 10000
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DAYS_BETWEEN(
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            NANO100_BETWEEN(
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0 / 1000.0 / 10000
        {%- else -%}
        MAX(
            NANO100_BETWEEN(
                TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )
        ) / 24.0 / 3600.0 / 1000.0 / 10000
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
=== "MariaDB"

    ```sql+jinja
    {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
             TIMESTAMPDIFF(
                SECOND,
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DATEDIFF(
            DAY,
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            TIMESTAMPDIFF(
                SECOND,
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0
        {%- else -%}
        MAX(
             TIMESTAMPDIFF(
                SECOND,
                CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            )
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
             TIMESTAMPDIFF(
                SECOND,
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DATEDIFF(
            DAY,
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            TIMESTAMPDIFF(
                SECOND,
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0
        {%- else -%}
        MAX(
             TIMESTAMPDIFF(
                SECOND,
                CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            )
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
       MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE) - (CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE) - (CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
       MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE) - (CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
        {%- else -%}
        MAX(CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS DATE) - (CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS DATE)))
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        ) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        ) / 24.0 / 3600.0
        {%- else -%}
        MAX(
            EXTRACT(EPOCH FROM (
                ({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP - ({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP
            ))
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        CAST(MAX(
            DATE_DIFF(
                'MILLISECOND',
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        CAST(MAX(
            DATE_DIFF(
                'DAY',
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) AS DOUBLE)
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        CAST(MAX(
            DATE_DIFF(
                'MILLISECOND',
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        CAST(MAX(
            DATE_DIFF(
                'MILLISECOND',
                TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            )
        ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            EXTRACT(EPOCH FROM (
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} - {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            ))
        ) / 24.0 / 3600.0
        {%- else -%}
        MAX(
            EXTRACT(EPOCH FROM (
                ({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})::TIMESTAMP - ({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})::TIMESTAMP
            ))
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            TIMESTAMPDIFF(
                MILLISECOND,
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DATEDIFF(
                DAY,
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            TIMESTAMPDIFF(
                MILLISECOND,
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        MAX(
            TIMESTAMPDIFF(
                MILLISECOND,
                TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}),
                TRY_TO_TIMESTAMP({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            )
        ) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            BIGINT({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            -
            BIGINT({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        ) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DATEDIFF(
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            BIGINT({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }})
            -
            BIGINT({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }})
        ) / 24.0 / 3600.0
        {%- else -%}
        MAX(
            BIGINT(SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP))
            -
            BIGINT(SAFE_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP))
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DATEDIFF(SECOND,
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DATEDIFF(
                DAY,
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        )
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        MAX(
            DATEDIFF(SECOND,
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0
        {%- else -%}
        MAX(
            DATEDIFF(SECOND,
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) / 24.0 / 3600.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
    
    {% macro render_ingestion_event_diff() -%}
        {%- if lib.is_instant(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_instant(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        CAST(MAX(
            DATE_DIFF(
                'MILLISECOND',
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
        {%- elif lib.is_local_date(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        CAST(MAX(
            DATE_DIFF(
                'DAY',
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) AS DOUBLE)
        {%- elif lib.is_local_date_time(table.columns[table.timestamp_columns.ingestion_timestamp_column].type_snapshot.column_type) == 'true'
        and lib.is_local_date_time(table.columns[table.timestamp_columns.event_timestamp_column].type_snapshot.column_type) == 'true' -%}
        CAST(MAX(
            DATE_DIFF(
                'MILLISECOND',
                {{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }},
                {{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }}
            )
        ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
        {%- else -%}
        CAST(MAX(
            DATE_DIFF(
                'MILLISECOND',
                TRY_CAST({{ lib.render_column(table.timestamp_columns.event_timestamp_column, 'analyzed_table') }} AS TIMESTAMP),
                TRY_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
            )
        ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        {{ render_ingestion_event_diff() }} AS actual_value
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
___




## What's next
- Learn how the [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) are defined in DQOps and what is the definition of all Jinja2 macros used in the templates
- Understand how DQOps [runs data quality checks](../../../dqo-concepts/architecture/data-quality-check-execution-flow.md), rendering templates to SQL queries
