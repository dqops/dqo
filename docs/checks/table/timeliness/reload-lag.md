**reload lag** checks  

**Description**  
Table-level check that calculates maximum difference in days between ingestion timestamp and event timestamp rows.
 This check should be executed only as a partitioned check because this check finds the longest delay between the time that the row was created
 in the data source and the timestamp when the row was loaded into its daily or monthly partition.
 This check will detect that a daily or monthly partition was reloaded, setting also the most recent timestamps in the created_at, loaded_at, inserted_at or other similar columns
 filled by the data pipeline or an ETL process during data loading.

___

## **daily partition reload lag**  
  
**Check description**  
Daily partitioned check calculating the longest time a row waited to be load  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_partition_reload_lag|partitioned|daily|Timeliness|[partition_reload_lag](../../../../reference/sensors/table/timeliness-table-sensors/#partition-reload-lag)|[max_days](../../../../reference/rules/Comparison/#max-days)|
  
**Activate check (Shell)**  
Activate this data quality using the [check activate](../../../../command-line-interface/check/#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=daily_partition_reload_lag
```

**Run check (Shell)**  
Run this data quality check using the [check run](../../../../command-line-interface/check/#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=daily_partition_reload_lag
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=daily_partition_reload_lag
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_reload_lag
```

**Check structure (YAML)**

```yaml
  partitioned_checks:
    daily:
      timeliness:
        daily_partition_reload_lag:
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
        daily_partition_reload_lag:
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
[partition_reload_lag](../../../../reference/sensors/table/timeliness-table-sensors/#partition-reload-lag)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

    === "Sensor template for BigQuery"

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
    === "Rendered SQL for BigQuery"

        ```sql
        SELECT
            MAX(
                TIMESTAMP_DIFF(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP),
                    SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MILLISECOND
                )
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
    === "Rendered SQL for Databricks"

        ```sql
        SELECT
            MAX(
                BIGINT(SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP))
                -
                BIGINT(SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP))
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
    === "Rendered SQL for MySQL"

        ```sql
        SELECT
            MAX(
                 TIMESTAMPDIFF(
                    SECOND,
                    CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                )
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
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            MAX(CAST(analyzed_table."col_inserted_at" AS DATE) - (CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value,
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
    === "Rendered SQL for PostgreSQL"

        ```sql
        SELECT
            MAX(
                EXTRACT(EPOCH FROM (
                    (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
                ))
            ) / 24.0 / 3600.0 AS actual_value,
            CAST(analyzed_table."date_column" AS date) AS time_period,
            CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

    === "Sensor template for Redshift"

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
    === "Rendered SQL for Redshift"

        ```sql
        SELECT
            MAX(
                EXTRACT(EPOCH FROM (
                    (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
                ))
            ) / 24.0 / 3600.0 AS actual_value,
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
    === "Rendered SQL for Snowflake"

        ```sql
        SELECT
            MAX(
                TIMESTAMPDIFF(
                    MILLISECOND,
                    TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp"),
                    TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                )
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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            MAX(
                BIGINT(SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP))
                -
                BIGINT(SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP))
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
    === "Rendered SQL for SQL Server"

        ```sql
        SELECT
            MAX(
                DATEDIFF(SECOND,
                    analyzed_table.[col_event_timestamp],
                    analyzed_table.[col_inserted_at]
                )
            ) / 24.0 / 3600.0 AS actual_value,
            CAST(analyzed_table.[date_column] AS date) AS time_period,
            CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
        ORDER BY CAST(analyzed_table.[date_column] AS date)
        
            
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
            daily_partition_reload_lag:
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
    [partition_reload_lag](../../../../reference/sensors/table/timeliness-table-sensors/#partition-reload-lag)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                MAX(
                    TIMESTAMP_DIFF(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP),
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                        MILLISECOND
                    )
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                MAX(
                    BIGINT(SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP))
                    -
                    BIGINT(SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP))
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                MAX(
                     TIMESTAMPDIFF(
                        SECOND,
                        CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                        CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    )
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
                    {{- lib.render_where_clause(table_alias_prefix='original_table') }}
                ) analyzed_table
                {{- lib.render_group_by() -}}
                {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                MAX(CAST(analyzed_table."col_inserted_at" AS DATE) - (CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value,
            
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                MAX(
                    EXTRACT(EPOCH FROM (
                        (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                CAST(analyzed_table."date_column" AS date) AS time_period,
                CAST((CAST(analyzed_table."date_column" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                MAX(
                    EXTRACT(EPOCH FROM (
                        (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                MAX(
                    TIMESTAMPDIFF(
                        MILLISECOND,
                        TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp"),
                        TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                    )
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                MAX(
                    BIGINT(SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP))
                    -
                    BIGINT(SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP))
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                MAX(
                    DATEDIFF(SECOND,
                        analyzed_table.[col_event_timestamp],
                        analyzed_table.[col_inserted_at]
                    )
                ) / 24.0 / 3600.0 AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                CAST(analyzed_table.[date_column] AS date) AS time_period,
                CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state], CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
            ORDER BY level_1, level_2CAST(analyzed_table.[date_column] AS date)
            
                
            ```
    






___

## **monthly partition reload lag**  
  
**Check description**  
Monthly partitioned check calculating the longest time a row waited to be load  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_partition_reload_lag|partitioned|monthly|Timeliness|[partition_reload_lag](../../../../reference/sensors/table/timeliness-table-sensors/#partition-reload-lag)|[max_days](../../../../reference/rules/Comparison/#max-days)|
  
**Activate check (Shell)**  
Activate this data quality using the [check activate](../../../../command-line-interface/check/#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=monthly_partition_reload_lag
```

**Run check (Shell)**  
Run this data quality check using the [check run](../../../../command-line-interface/check/#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=monthly_partition_reload_lag
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=monthly_partition_reload_lag
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_partition_reload_lag
```

**Check structure (YAML)**

```yaml
  partitioned_checks:
    monthly:
      timeliness:
        monthly_partition_reload_lag:
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
        monthly_partition_reload_lag:
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
[partition_reload_lag](../../../../reference/sensors/table/timeliness-table-sensors/#partition-reload-lag)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

    === "Sensor template for BigQuery"

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
    === "Rendered SQL for BigQuery"

        ```sql
        SELECT
            MAX(
                TIMESTAMP_DIFF(
                    SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP),
                    SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    MILLISECOND
                )
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
    === "Rendered SQL for Databricks"

        ```sql
        SELECT
            MAX(
                BIGINT(SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP))
                -
                BIGINT(SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP))
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
    === "Rendered SQL for MySQL"

        ```sql
        SELECT
            MAX(
                 TIMESTAMPDIFF(
                    SECOND,
                    CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                    CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                )
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
                {{- lib.render_where_clause(table_alias_prefix='original_table') }}
            ) analyzed_table
            {{- lib.render_group_by() -}}
            {{- lib.render_order_by() -}}
        ```
    === "Rendered SQL for Oracle"

        ```sql
        SELECT
            MAX(CAST(analyzed_table."col_inserted_at" AS DATE) - (CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value,
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
    === "Rendered SQL for PostgreSQL"

        ```sql
        SELECT
            MAX(
                EXTRACT(EPOCH FROM (
                    (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
                ))
            ) / 24.0 / 3600.0 AS actual_value,
            DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY time_period, time_period_utc
        ORDER BY time_period, time_period_utc
        ```
??? example "Redshift"

    === "Sensor template for Redshift"

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
    === "Rendered SQL for Redshift"

        ```sql
        SELECT
            MAX(
                EXTRACT(EPOCH FROM (
                    (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
                ))
            ) / 24.0 / 3600.0 AS actual_value,
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
    === "Rendered SQL for Snowflake"

        ```sql
        SELECT
            MAX(
                TIMESTAMPDIFF(
                    MILLISECOND,
                    TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp"),
                    TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                )
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
    === "Rendered SQL for Spark"

        ```sql
        SELECT
            MAX(
                BIGINT(SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP))
                -
                BIGINT(SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP))
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
    === "Rendered SQL for SQL Server"

        ```sql
        SELECT
            MAX(
                DATEDIFF(SECOND,
                    analyzed_table.[col_event_timestamp],
                    analyzed_table.[col_inserted_at]
                )
            ) / 24.0 / 3600.0 AS actual_value,
            DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
        ORDER BY DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
        
            
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
            monthly_partition_reload_lag:
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
    [partition_reload_lag](../../../../reference/sensors/table/timeliness-table-sensors/#partition-reload-lag)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
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
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                MAX(
                    TIMESTAMP_DIFF(
                        SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP),
                        SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                        MILLISECOND
                    )
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
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                MAX(
                    BIGINT(SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP))
                    -
                    BIGINT(SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP))
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
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                MAX(
                     TIMESTAMPDIFF(
                        SECOND,
                        CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP),
                        CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP)
                    )
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
                    {{- lib.render_where_clause(table_alias_prefix='original_table') }}
                ) analyzed_table
                {{- lib.render_group_by() -}}
                {{- lib.render_order_by() -}}
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                MAX(CAST(analyzed_table."col_inserted_at" AS DATE) - (CAST(analyzed_table."col_event_timestamp" AS DATE))) AS actual_value,
            
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
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                MAX(
                    EXTRACT(EPOCH FROM (
                        (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
                analyzed_table."country" AS grouping_level_1,
                analyzed_table."state" AS grouping_level_2,
                DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."date_column" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
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
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                MAX(
                    EXTRACT(EPOCH FROM (
                        (analyzed_table."col_inserted_at")::TIMESTAMP - (analyzed_table."col_event_timestamp")::TIMESTAMP
                    ))
                ) / 24.0 / 3600.0 AS actual_value,
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
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                MAX(
                    TIMESTAMPDIFF(
                        MILLISECOND,
                        TRY_TO_TIMESTAMP(analyzed_table."col_event_timestamp"),
                        TRY_TO_TIMESTAMP(analyzed_table."col_inserted_at")
                    )
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
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                MAX(
                    BIGINT(SAFE_CAST(analyzed_table.`col_inserted_at` AS TIMESTAMP))
                    -
                    BIGINT(SAFE_CAST(analyzed_table.`col_event_timestamp` AS TIMESTAMP))
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
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                MAX(
                    DATEDIFF(SECOND,
                        analyzed_table.[col_event_timestamp],
                        analyzed_table.[col_inserted_at]
                    )
                ) / 24.0 / 3600.0 AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1) AS time_period,
                CAST((DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state], DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, analyzed_table.[date_column]), 0)
            ORDER BY level_1, level_2DATEFROMPARTS(YEAR(CAST(analyzed_table.[date_column] AS date)), MONTH(CAST(analyzed_table.[date_column] AS date)), 1)
            
                
            ```
    






___
