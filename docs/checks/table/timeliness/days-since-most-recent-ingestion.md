**days since most recent ingestion** checks  

**Description**  
Table level check that calculates the maximum number of days between event timestamp and ingestion timestamp.

___

## **days since most recent ingestion**  
  
**Check description**  
Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|days_since_most_recent_ingestion|profiling| |[days_since_most_recent_ingestion](../../../../reference/sensors/Table/timeliness-table-sensors/#days-since-most-recent-ingestion)|[max_days](../../../../reference/rules/Comparison/#max-days)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=days_since_most_recent_ingestion
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=days_since_most_recent_ingestion
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=days_since_most_recent_ingestion
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=days_since_most_recent_ingestion
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=days_since_most_recent_ingestion
```
**Check structure (Yaml)**
```yaml
  profiling_checks:
    timeliness:
      days_since_most_recent_ingestion:
        warning:
          max_days: 1.0
        error:
          max_days: 2.0
        fatal:
          max_days: 1.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-19"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
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
      days_since_most_recent_ingestion:
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
### **BigQuery**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        CURRENT_TIMESTAMP() AS time_period,
        TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
        TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        LOCALTIMESTAMP AS time_period,
        CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        SYSDATETIMEOFFSET() AS time_period,
        CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 35-40"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      profiling_checks:
        timeliness:
          days_since_most_recent_ingestion:
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
    **BigQuery**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CURRENT_TIMESTAMP() AS time_period,
            TIMESTAMP(CURRENT_TIMESTAMP()) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS time_period,
            TO_TIMESTAMP(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP())) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            LOCALTIMESTAMP AS time_period,
            CAST((LOCALTIMESTAMP) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            SYSDATETIMEOFFSET() AS time_period,
            CAST((SYSDATETIMEOFFSET()) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state]
        ORDER BY level_1, level_2
                , 
            
        
            
        ```
    





___

## **daily days since most recent ingestion**  
  
**Check description**  
Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_days_since_most_recent_ingestion|recurring|daily|[days_since_most_recent_ingestion](../../../../reference/sensors/Table/timeliness-table-sensors/#days-since-most-recent-ingestion)|[max_days](../../../../reference/rules/Comparison/#max-days)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_days_since_most_recent_ingestion
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_days_since_most_recent_ingestion
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_days_since_most_recent_ingestion
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_days_since_most_recent_ingestion
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_days_since_most_recent_ingestion
```
**Check structure (Yaml)**
```yaml
  recurring_checks:
    daily:
      timeliness:
        daily_days_since_most_recent_ingestion:
          warning:
            max_days: 1.0
          error:
            max_days: 2.0
          fatal:
            max_days: 1.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-20"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  recurring_checks:
    daily:
      timeliness:
        daily_days_since_most_recent_ingestion:
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
### **BigQuery**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
### **Snowflake**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
### **PostgreSQL**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
### **Redshift**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
### **SQL Server**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 36-41"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      recurring_checks:
        daily:
          timeliness:
            daily_days_since_most_recent_ingestion:
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
    **BigQuery**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
            TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
            TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(LOCALTIMESTAMP AS date) AS time_period,
            CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            CAST(SYSDATETIMEOFFSET() AS date) AS time_period,
            CAST((CAST(SYSDATETIMEOFFSET() AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state]
        ORDER BY level_1, level_2
                , 
            
        
            
        ```
    





___

## **monthly days since most recent ingestion**  
  
**Check description**  
Monthly recurring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_days_since_most_recent_ingestion|recurring|monthly|[days_since_most_recent_ingestion](../../../../reference/sensors/Table/timeliness-table-sensors/#days-since-most-recent-ingestion)|[max_days](../../../../reference/rules/Comparison/#max-days)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_days_since_most_recent_ingestion
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_days_since_most_recent_ingestion
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_days_since_most_recent_ingestion
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_days_since_most_recent_ingestion
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_days_since_most_recent_ingestion
```
**Check structure (Yaml)**
```yaml
  recurring_checks:
    monthly:
      timeliness:
        monthly_days_since_most_recent_ingestion:
          warning:
            max_days: 1.0
          error:
            max_days: 2.0
          fatal:
            max_days: 1.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-20"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  recurring_checks:
    monthly:
      timeliness:
        monthly_days_since_most_recent_ingestion:
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
### **BigQuery**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
### **Snowflake**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
### **PostgreSQL**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
### **Redshift**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
### **SQL Server**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 36-41"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      recurring_checks:
        monthly:
          timeliness:
            monthly_days_since_most_recent_ingestion:
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
    **BigQuery**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0) AS time_period,
            CAST((DATEADD(month, DATEDIFF(month, 0, SYSDATETIMEOFFSET()), 0)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state]
        ORDER BY level_1, level_2
                , 
            
        
            
        ```
    





___

## **daily partition days since most recent ingestion**  
  
**Check description**  
Daily partitioned check calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|daily_partition_days_since_most_recent_ingestion|partitioned|daily|[days_since_most_recent_ingestion](../../../../reference/sensors/Table/timeliness-table-sensors/#days-since-most-recent-ingestion)|[max_days](../../../../reference/rules/Comparison/#max-days)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=daily_partition_days_since_most_recent_ingestion
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=daily_partition_days_since_most_recent_ingestion
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=daily_partition_days_since_most_recent_ingestion
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=daily_partition_days_since_most_recent_ingestion
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=daily_partition_days_since_most_recent_ingestion
```
**Check structure (Yaml)**
```yaml
  partitioned_checks:
    daily:
      timeliness:
        daily_partition_days_since_most_recent_ingestion:
          warning:
            max_days: 1.0
          error:
            max_days: 2.0
          fatal:
            max_days: 1.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-20"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  partitioned_checks:
    daily:
      timeliness:
        daily_partition_days_since_most_recent_ingestion:
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
### **BigQuery**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        CAST(analyzed_table.`` AS DATE) AS time_period,
        TIMESTAMP(CAST(analyzed_table.`` AS DATE)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        CAST(analyzed_table."" AS date) AS time_period,
        TO_TIMESTAMP(CAST(analyzed_table."" AS date)) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        CAST(analyzed_table."" AS date) AS time_period,
        CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        CAST(analyzed_table."" AS date) AS time_period,
        CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        CAST([] AS date) AS time_period,
        CAST((CAST([] AS date)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY CAST([] AS date), CAST([] AS date)
    ORDER BY CAST([] AS date)
    
        
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 36-41"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      partitioned_checks:
        daily:
          timeliness:
            daily_partition_days_since_most_recent_ingestion:
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
    **BigQuery**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            CAST(analyzed_table.`` AS DATE) AS time_period,
            TIMESTAMP(CAST(analyzed_table.`` AS DATE)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            TO_TIMESTAMP(CAST(analyzed_table."" AS date)) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            CAST(analyzed_table."" AS date) AS time_period,
            CAST((CAST(analyzed_table."" AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            CAST([] AS date) AS time_period,
            CAST((CAST([] AS date)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state], CAST([] AS date), CAST([] AS date)
        ORDER BY level_1, level_2CAST([] AS date)
        
            
        ```
    





___

## **monthly partition days since most recent ingestion**  
  
**Check description**  
Monthly partitioned check calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)  
  
|Check name|Check type|Time scale|Sensor definition|Quality rule|
|----------|----------|----------|-----------|-------------|
|monthly_partition_days_since_most_recent_ingestion|partitioned|monthly|[days_since_most_recent_ingestion](../../../../reference/sensors/Table/timeliness-table-sensors/#days-since-most-recent-ingestion)|[max_days](../../../../reference/rules/Comparison/#max-days)|
  
**Enable check (Shell)**  
To enable this check provide connection name and check name in [check enable command](../../../../command_line_interface/check/#dqo-check-enable)
```
dqo> check enable -c=connection_name -ch=monthly_partition_days_since_most_recent_ingestion
```
**Run check (Shell)**  
To run this check provide check name in [check run command](../../../../command_line_interface/check/#dqo-check-run)
```
dqo> check run -ch=monthly_partition_days_since_most_recent_ingestion
```
It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below
```
dqo> check run -c=connection_name -ch=monthly_partition_days_since_most_recent_ingestion
```
It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below
```
dqo> check run -c=connection_name -t=table_name -ch=monthly_partition_days_since_most_recent_ingestion
```
It is furthermore viable to combine run this check on a specific column. In order to do this, add the column name to the below
```
dqo> check run -c=connection_name -t=table_name -col=column_name -ch=monthly_partition_days_since_most_recent_ingestion
```
**Check structure (Yaml)**
```yaml
  partitioned_checks:
    monthly:
      timeliness:
        monthly_partition_days_since_most_recent_ingestion:
          warning:
            max_days: 1.0
          error:
            max_days: 2.0
          fatal:
            max_days: 1.0
```
**Sample configuration (Yaml)**  
```yaml hl_lines="11-20"
# yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  timestamp_columns:
    event_timestamp_column: col_event_timestamp
    ingestion_timestamp_column: col_inserted_at
  incremental_time_window:
    daily_partitioning_recent_days: 7
    monthly_partitioning_recent_months: 1
  partitioned_checks:
    monthly:
      timeliness:
        monthly_partition_days_since_most_recent_ingestion:
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
### **BigQuery**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH) AS time_period,
        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH)) AS time_period_utc
    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Snowflake**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
        TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS time_period_utc
    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **PostgreSQL**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **Redshift**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
        CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
    GROUP BY time_period, time_period_utc
    ORDER BY time_period, time_period_utc
    ```
### **SQL Server**
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
        {{- lib.render_data_stream_projections('analyzed_table') }}
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
        DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1) AS time_period,
        CAST((DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1)) AS DATETIME) AS time_period_utc
    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
    GROUP BY DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, []), 0)
    ORDER BY DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1)
    
        
    ```
### **Configuration with a data stream segmentation**  
??? info "Click to see more"  
    **Sample configuration (Yaml)**  
    ```yaml hl_lines="11-18 36-41"
    # yaml-language-server: $schema=https://cloud.dqo.ai/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
      timestamp_columns:
        event_timestamp_column: col_event_timestamp
        ingestion_timestamp_column: col_inserted_at
      incremental_time_window:
        daily_partitioning_recent_days: 7
        monthly_partitioning_recent_months: 1
      data_streams:
        default:
          level_1:
            source: column_value
            column: country
          level_2:
            source: column_value
            column: state
      partitioned_checks:
        monthly:
          timeliness:
            monthly_partition_days_since_most_recent_ingestion:
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
    **BigQuery**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table.`country` AS stream_level_1,
            analyzed_table.`state` AS stream_level_2,
            DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH) AS time_period,
            TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`` AS DATE), MONTH)) AS time_period_utc
        FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Snowflake**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
            TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS time_period_utc
        FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **PostgreSQL**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **Redshift**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table."country" AS stream_level_1,
            analyzed_table."state" AS stream_level_2,
            DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date)) AS time_period,
            CAST((DATE_TRUNC('MONTH', CAST(analyzed_table."" AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
        FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
        GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
        ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc
        ```
    **SQL Server**  
      
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
            {{- lib.render_data_stream_projections('analyzed_table') }}
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
            analyzed_table.[country] AS stream_level_1,
            analyzed_table.[state] AS stream_level_2,
            DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1) AS time_period,
            CAST((DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1)) AS DATETIME) AS time_period_utc
        FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
        GROUP BY analyzed_table.[country], analyzed_table.[state], DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1), DATEADD(month, DATEDIFF(month, 0, []), 0)
        ORDER BY level_1, level_2DATEFROMPARTS(YEAR(CAST([] AS date)), MONTH(CAST([] AS date)), 1)
        
            
        ```
    





___
