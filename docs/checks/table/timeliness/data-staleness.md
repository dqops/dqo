**data staleness** checks

**Description**
Table-level check that calculates the time difference between the last timestamp when any data was loaded into a table and the current time.
 This check could be used only when the data pipeline, a ETL process or a trigger in the data warehouse is filling an extra column with the timestamp when the data loading job was loaded.
 The ingestion column that is used for comparison is defined as the timestamp_columns.ingestion_timestamp_column on the table configuration.
 This check is also known as &quot;Data Staleness&quot;.

___

## **profile data staleness**


**Check description**
Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_data_staleness|profiling| |Timeliness|[data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)|[max_days](../../../../reference/rules/Comparison.md#max-days)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=profile_data_staleness
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=profile_data_staleness
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=profile_data_staleness
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_data_staleness
```

**Check structure (YAML)**

```yaml
  profiling_checks:
    timeliness:
      profile_data_staleness:
        warning:
          max_days: 1.0
        error:
          max_days: 2.0
        fatal:
          max_days: 1.0
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="11-19"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  profiling_checks:
    timeliness:
      profile_data_staleness:
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

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

    === "Sensor template for BigQuery"

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
    === "Rendered SQL for BigQuery"

        ```sql
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
    === "Rendered SQL for Databricks"

        ```sql
        SELECT
            (
                BIGINT(CURRENT_TIMESTAMP())
                -
                BIGINT(MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ))
            ) / 24.0 / 3600.0 AS actual_value,
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
    === "Rendered SQL for MySQL"

        ```sql
        SELECT
            TIMESTAMPDIFF(
                SECOND,
                MAX(CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)),
                CURRENT_TIMESTAMP()
            ) / 24.0 / 3600.0 AS actual_value,
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
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_inserted_at") AS DATE)) AS actual_value,
            time_period,
            time_period_utc
            FROM (
                SELECT
                    original_table.*,
            TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
            CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "PostgreSQL"

    === "Sensor template for PostgreSQL"

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
    === "Rendered SQL for PostgreSQL"

        ```sql
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
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
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                CURRENT_TIMESTAMP
            ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
    === "Rendered SQL for Redshift"

        ```sql
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
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
    === "Rendered SQL for Snowflake"

        ```sql
        SELECT
            TIMESTAMPDIFF(
                MILLISECOND,
                MAX(
                    TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                ),
                CURRENT_TIMESTAMP
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            (
                BIGINT(CURRENT_TIMESTAMP())
                -
                BIGINT(MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ))
            ) / 24.0 / 3600.0 AS actual_value,
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
    === "Rendered SQL for SQL Server"

        ```sql
        SELECT
            DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_inserted_at]),
                    SYSDATETIMEOFFSET()
                ) / 24.0 / 3600.0 AS actual_value,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        ```
??? example "Trino"

    === "Sensor template for Trino"

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
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                CURRENT_TIMESTAMP
            ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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

    ```yaml hl_lines="11-19 36-41"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
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
      profiling_checks:
        timeliness:
          profile_data_staleness:
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
    [data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0 AS actual_value,
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
                    {{- lib.render_where_clause(table_alias_prefix='original_table') }}
                ) analyzed_table
                {{- lib.render_group_by() -}}
                {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_inserted_at") AS DATE)) AS actual_value,
            
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
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "<target_schema>"."<target_table>" original_table
                ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                        SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(
                        TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                DATEDIFF(SECOND,
                        MAX(analyzed_table.[col_inserted_at]),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0 AS actual_value,
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
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                        SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___

## **daily data staleness**


**Check description**
Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_data_staleness|monitoring|daily|Timeliness|[data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)|[max_days](../../../../reference/rules/Comparison.md#max-days)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=daily_data_staleness
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=daily_data_staleness
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=daily_data_staleness
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_data_staleness
```

**Check structure (YAML)**

```yaml
  monitoring_checks:
    daily:
      timeliness:
        daily_data_staleness:
          warning:
            max_days: 1.0
          error:
            max_days: 2.0
          fatal:
            max_days: 1.0
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="11-20"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  monitoring_checks:
    daily:
      timeliness:
        daily_data_staleness:
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

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

    === "Sensor template for BigQuery"

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
    === "Rendered SQL for BigQuery"

        ```sql
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
    === "Rendered SQL for Databricks"

        ```sql
        SELECT
            (
                BIGINT(CURRENT_TIMESTAMP())
                -
                BIGINT(MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ))
            ) / 24.0 / 3600.0 AS actual_value,
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
    === "Rendered SQL for MySQL"

        ```sql
        SELECT
            TIMESTAMPDIFF(
                SECOND,
                MAX(CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)),
                CURRENT_TIMESTAMP()
            ) / 24.0 / 3600.0 AS actual_value,
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
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_inserted_at") AS DATE)) AS actual_value,
            time_period,
            time_period_utc
            FROM (
                SELECT
                    original_table.*,
            TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
            CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "PostgreSQL"

    === "Sensor template for PostgreSQL"

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
    === "Rendered SQL for PostgreSQL"

        ```sql
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
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
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                CURRENT_TIMESTAMP
            ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
    === "Rendered SQL for Redshift"

        ```sql
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
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
    === "Rendered SQL for Snowflake"

        ```sql
        SELECT
            TIMESTAMPDIFF(
                MILLISECOND,
                MAX(
                    TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                ),
                CURRENT_TIMESTAMP
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            (
                BIGINT(CURRENT_TIMESTAMP())
                -
                BIGINT(MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ))
            ) / 24.0 / 3600.0 AS actual_value,
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
    === "Rendered SQL for SQL Server"

        ```sql
        SELECT
            DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_inserted_at]),
                    SYSDATETIMEOFFSET()
                ) / 24.0 / 3600.0 AS actual_value,
            CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        ```
??? example "Trino"

    === "Sensor template for Trino"

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
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                CURRENT_TIMESTAMP
            ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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

    ```yaml hl_lines="11-19 37-42"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
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
      monitoring_checks:
        daily:
          timeliness:
            daily_data_staleness:
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
    [data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0 AS actual_value,
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
                    {{- lib.render_where_clause(table_alias_prefix='original_table') }}
                ) analyzed_table
                {{- lib.render_group_by() -}}
                {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_inserted_at") AS DATE)) AS actual_value,
            
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
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "<target_schema>"."<target_table>" original_table
                ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                        SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(
                        TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                DATEDIFF(SECOND,
                        MAX(analyzed_table.[col_inserted_at]),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0 AS actual_value,
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
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                        SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___

## **monthly data staleness**


**Check description**
Monthly monitoring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_data_staleness|monitoring|monthly|Timeliness|[data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)|[max_days](../../../../reference/rules/Comparison.md#max-days)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=monthly_data_staleness
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=monthly_data_staleness
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=monthly_data_staleness
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_data_staleness
```

**Check structure (YAML)**

```yaml
  monitoring_checks:
    monthly:
      timeliness:
        monthly_data_staleness:
          warning:
            max_days: 1.0
          error:
            max_days: 2.0
          fatal:
            max_days: 1.0
```

**Sample configuration (YAML)**
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="11-20"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  monitoring_checks:
    monthly:
      timeliness:
        monthly_data_staleness:
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

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

    === "Sensor template for BigQuery"

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
    === "Rendered SQL for BigQuery"

        ```sql
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ),
                MILLISECOND
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
    === "Rendered SQL for Databricks"

        ```sql
        SELECT
            (
                BIGINT(CURRENT_TIMESTAMP())
                -
                BIGINT(MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ))
            ) / 24.0 / 3600.0 AS actual_value,
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
    === "Rendered SQL for MySQL"

        ```sql
        SELECT
            TIMESTAMPDIFF(
                SECOND,
                MAX(CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)),
                CURRENT_TIMESTAMP()
            ) / 24.0 / 3600.0 AS actual_value,
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
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_inserted_at") AS DATE)) AS actual_value,
            time_period,
            time_period_utc
            FROM (
                SELECT
                    original_table.*,
            TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
            CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                FROM "<target_schema>"."<target_table>" original_table
            ) analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "PostgreSQL"

    === "Sensor template for PostgreSQL"

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
    === "Rendered SQL for PostgreSQL"

        ```sql
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
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
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                CURRENT_TIMESTAMP
            ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
    === "Rendered SQL for Redshift"

        ```sql
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
            )) / 24.0 / 3600.0 AS actual_value,
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
    === "Rendered SQL for Snowflake"

        ```sql
        SELECT
            TIMESTAMPDIFF(
                MILLISECOND,
                MAX(
                    TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                ),
                CURRENT_TIMESTAMP
            ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            (
                BIGINT(CURRENT_TIMESTAMP())
                -
                BIGINT(MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ))
            ) / 24.0 / 3600.0 AS actual_value,
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
    === "Rendered SQL for SQL Server"

        ```sql
        SELECT
            DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_inserted_at]),
                    SYSDATETIMEOFFSET()
                ) / 24.0 / 3600.0 AS actual_value,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        ```
??? example "Trino"

    === "Sensor template for Trino"

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
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                CURRENT_TIMESTAMP
            ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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

    ```yaml hl_lines="11-19 37-42"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
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
      monitoring_checks:
        monthly:
          timeliness:
            monthly_data_staleness:
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
    [data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)),
                    CURRENT_TIMESTAMP()
                ) / 24.0 / 3600.0 AS actual_value,
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
                    {{- lib.render_where_clause(table_alias_prefix='original_table') }}
                ) analyzed_table
                {{- lib.render_group_by() -}}
                {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_inserted_at") AS DATE)) AS actual_value,
            
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
                TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS time_period,
                CAST(TRUNC(CAST(CURRENT_TIMESTAMP AS DATE), 'MONTH') AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "<target_schema>"."<target_table>" original_table
                ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                        SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
                )) / 24.0 / 3600.0 AS actual_value,
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(
                        TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                    ),
                    CURRENT_TIMESTAMP
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                DATEDIFF(SECOND,
                        MAX(analyzed_table.[col_inserted_at]),
                        SYSDATETIMEOFFSET()
                    ) / 24.0 / 3600.0 AS actual_value,
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
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                        SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___

## **daily partition data staleness**


**Check description**
Daily partitioned check calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_partition_data_staleness|partitioned|daily|Timeliness|[data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)|[max_days](../../../../reference/rules/Comparison.md#max-days)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=daily_partition_data_staleness
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=daily_partition_data_staleness
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=daily_partition_data_staleness
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_data_staleness
```

**Check structure (YAML)**

```yaml
  partitioned_checks:
    daily:
      timeliness:
        daily_partition_data_staleness:
          warning:
            max_days: 1.0
          error:
            max_days: 2.0
          fatal:
            max_days: 1.0
```

**Sample configuration (YAML)**
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
        daily_partition_data_staleness:
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

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

    === "Sensor template for BigQuery"

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
    === "Rendered SQL for BigQuery"

        ```sql
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
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
    === "Rendered SQL for Databricks"

        ```sql
        SELECT
            (
                BIGINT(CURRENT_TIMESTAMP())
                -
                BIGINT(MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ))
            ) / 24.0 / 3600.0 AS actual_value,
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
    === "Rendered SQL for MySQL"

        ```sql
        SELECT
            TIMESTAMPDIFF(
                SECOND,
                MAX(CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)),
                CURRENT_TIMESTAMP()
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
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_inserted_at") AS DATE)) AS actual_value,
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
    === "Rendered SQL for PostgreSQL"

        ```sql
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
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
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                CURRENT_TIMESTAMP
            ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
    === "Rendered SQL for Redshift"

        ```sql
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
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
    === "Rendered SQL for Snowflake"

        ```sql
        SELECT
            TIMESTAMPDIFF(
                MILLISECOND,
                MAX(
                    TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                ),
                CURRENT_TIMESTAMP
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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            (
                BIGINT(CURRENT_TIMESTAMP())
                -
                BIGINT(MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
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
    === "Rendered SQL for SQL Server"

        ```sql
        SELECT
            DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_inserted_at]),
                    SYSDATETIMEOFFSET()
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
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                CURRENT_TIMESTAMP
            ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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

    ```yaml hl_lines="12-20 43-48"
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
            daily_partition_data_staleness:
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
    [data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
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
    ??? example "MySQL"

        === "Sensor template for MySQL"
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)),
                    CURRENT_TIMESTAMP()
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
                    {{- lib.render_where_clause(table_alias_prefix='original_table') }}
                ) analyzed_table
                {{- lib.render_group_by() -}}
                {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_inserted_at") AS DATE)) AS actual_value,
            
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
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
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                        SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(
                        TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                    ),
                    CURRENT_TIMESTAMP
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                DATEDIFF(SECOND,
                        MAX(analyzed_table.[col_inserted_at]),
                        SYSDATETIMEOFFSET()
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
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                        SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___

## **monthly partition data staleness**


**Check description**
Monthly partitioned check calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)

|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_partition_data_staleness|partitioned|monthly|Timeliness|[data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)|[max_days](../../../../reference/rules/Comparison.md#max-days)|

**Activate check (Shell)**
Activate this data quality using the [check activate](../../../../command-line-interface/check.md#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=monthly_partition_data_staleness
```

**Run check (Shell)**
Run this data quality check using the [check run](../../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=monthly_partition_data_staleness
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=monthly_partition_data_staleness
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_partition_data_staleness
```

**Check structure (YAML)**

```yaml
  partitioned_checks:
    monthly:
      timeliness:
        monthly_partition_data_staleness:
          warning:
            max_days: 1.0
          error:
            max_days: 2.0
          fatal:
            max_days: 1.0
```

**Sample configuration (YAML)**
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
        monthly_partition_data_staleness:
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

Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
[data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

    === "Sensor template for BigQuery"

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
    === "Rendered SQL for BigQuery"

        ```sql
        SELECT
            TIMESTAMP_DIFF(
                CURRENT_TIMESTAMP(),
                MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
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
    === "Rendered SQL for Databricks"

        ```sql
        SELECT
            (
                BIGINT(CURRENT_TIMESTAMP())
                -
                BIGINT(MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                ))
            ) / 24.0 / 3600.0 AS actual_value,
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
    === "Rendered SQL for MySQL"

        ```sql
        SELECT
            TIMESTAMPDIFF(
                SECOND,
                MAX(CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)),
                CURRENT_TIMESTAMP()
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
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_inserted_at") AS DATE)) AS actual_value,
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
    === "Rendered SQL for PostgreSQL"

        ```sql
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
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
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                CURRENT_TIMESTAMP
            ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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
    === "Rendered SQL for Redshift"

        ```sql
        SELECT
            EXTRACT(EPOCH FROM (
                CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
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
    === "Rendered SQL for Snowflake"

        ```sql
        SELECT
            TIMESTAMPDIFF(
                MILLISECOND,
                MAX(
                    TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                ),
                CURRENT_TIMESTAMP
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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            (
                BIGINT(CURRENT_TIMESTAMP())
                -
                BIGINT(MAX(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
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
    === "Rendered SQL for SQL Server"

        ```sql
        SELECT
            DATEDIFF(SECOND,
                    MAX(analyzed_table.[col_inserted_at]),
                    SYSDATETIMEOFFSET()
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
                    SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                    SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                ),
                CURRENT_TIMESTAMP
            ) AS DOUBLE) / 24.0 / 3600.0 / 1000.0 AS actual_value,
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

    ```yaml hl_lines="12-20 43-48"
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
            monthly_partition_data_staleness:
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
    [data_staleness](../../../../reference/sensors/table/timeliness-table-sensors.md#data-staleness)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
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
    ??? example "MySQL"

        === "Sensor template for MySQL"
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                TIMESTAMPDIFF(
                    SECOND,
                    MAX(CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)),
                    CURRENT_TIMESTAMP()
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
                    {{- lib.render_where_clause(table_alias_prefix='original_table') }}
                ) analyzed_table
                {{- lib.render_group_by() -}}
                {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                (CAST(CURRENT_TIMESTAMP AS DATE) - CAST(MAX(analyzed_table."col_inserted_at") AS DATE)) AS actual_value,
            
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
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
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                        SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                EXTRACT(EPOCH FROM (
                    CURRENT_TIMESTAMP - MAX((analyzed_table."col_inserted_at")::TIMESTAMP)
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                TIMESTAMPDIFF(
                    MILLISECOND,
                    MAX(
                        TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                    ),
                    CURRENT_TIMESTAMP
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                (
                    BIGINT(CURRENT_TIMESTAMP())
                    -
                    BIGINT(MAX(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                DATEDIFF(SECOND,
                        MAX(analyzed_table.[col_inserted_at]),
                        SYSDATETIMEOFFSET()
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
                        SAFE_CAST({{ lib.render_column(table.timestamp_columns.ingestion_timestamp_column, 'analyzed_table') }} AS TIMESTAMP)
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
                        SAFE_CAST(analyzed_table."col_inserted_at" AS TIMESTAMP)
                    ),
                    CURRENT_TIMESTAMP
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
                FROM ""."<target_schema>"."<target_table>" original_table
            ) analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    






___
