**sum anomaly stationary 30 days** checks  

**Description**  
Column level check that ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.

___

## **daily partition sum anomaly stationary 30 days**  
  
**Check description**  
Verifies that the sum in a column is within a percentile from measurements made during the last 30 days.  
  
|Check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_partition_sum_anomaly_stationary_30_days|partitioned|daily|Consistency|[sum](../../../../reference/sensors/column/numeric-column-sensors/#sum)|[anomaly_stationary_percentile_moving_average_30_days](../../../../reference/rules/Percentile/#anomaly-stationary-percentile-moving-average-30-days)|
  
**Activate check (Shell)**  
Activate this data quality using the [check activate](../../../../command-line-interface/check/#dqo-check-activate) CLI command, providing the connection name, check name, and all other filters.

```
dqo> check activate -c=connection_name -ch=daily_partition_sum_anomaly_stationary_30_days
```

**Run check (Shell)**  
Run this data quality check using the [check run](../../../../command-line-interface/check/#dqo-check-run) CLI command by providing the check name and all other targeting filters.

```
dqo> check run -ch=daily_partition_sum_anomaly_stationary_30_days
```

It is also possible to run this check on a specific connection. In order to do this, add the connection name to the below

```
dqo> check run -c=connection_name -ch=daily_partition_sum_anomaly_stationary_30_days
```

It is additionally feasible to run this check on a specific table. In order to do this, add the table name to the below

```
dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_partition_sum_anomaly_stationary_30_days
```

**Check structure (YAML)**

```yaml
      partitioned_checks:
        daily:
          anomaly:
            daily_partition_sum_anomaly_stationary_30_days:
              warning:
                anomaly_percent: 0.1
              error:
                anomaly_percent: 0.1
              fatal:
                anomaly_percent: 0.1
```

**Sample configuration (YAML)**  
The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.
  

```yaml hl_lines="14-23"
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
  columns:
    target_column:
      partitioned_checks:
        daily:
          anomaly:
            daily_partition_sum_anomaly_stationary_30_days:
              warning:
                anomaly_percent: 0.1
              error:
                anomaly_percent: 0.1
              fatal:
                anomaly_percent: 0.1
      labels:
      - This is the column that is analyzed for data quality issues
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
[sum](../../../../reference/sensors/column/numeric-column-sensors/#sum)
[sensor](../../../dqo-concepts/sensors/sensors.md).

??? example "BigQuery"

    === "Sensor template for BigQuery"

        ```sql+jinja
        {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
        SELECT
            SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            SUM(analyzed_table.`target_column`) AS actual_value,
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
            SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            SUM(analyzed_table.`target_column`) AS actual_value,
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
            SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            SUM(analyzed_table.`target_column`) AS actual_value,
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
            SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
            {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
             {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
         FROM(
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
            SUM(analyzed_table."target_column") AS actual_value,
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
            SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            SUM(analyzed_table."target_column") AS actual_value,
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
            SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            SUM(analyzed_table."target_column") AS actual_value,
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
            SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            SUM(analyzed_table."target_column") AS actual_value,
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
            SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            SUM(analyzed_table."target_column") AS actual_value,
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
            SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            SUM(analyzed_table.`target_column`) AS actual_value,
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
            SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
            SUM(analyzed_table.[target_column]) AS actual_value,
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

    ```yaml hl_lines="12-22 46-51"
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
      columns:
        target_column:
          partitioned_checks:
            daily:
              anomaly:
                daily_partition_sum_anomaly_stationary_30_days:
                  warning:
                    anomaly_percent: 0.1
                  error:
                    anomaly_percent: 0.1
                  fatal:
                    anomaly_percent: 0.1
          labels:
          - This is the column that is analyzed for data quality issues
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
    [sum](../../../../reference/sensors/column/numeric-column-sensors/#sum)
    [sensor](../../../dqo-concepts/sensors/sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
                SUM(analyzed_table.`target_column`) AS actual_value,
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
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
                SUM(analyzed_table.`target_column`) AS actual_value,
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
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
                SUM(analyzed_table.`target_column`) AS actual_value,
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
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
                {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
                 {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
             FROM(
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
                SUM(analyzed_table."target_column") AS actual_value,
            
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
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
                SUM(analyzed_table."target_column") AS actual_value,
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
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
                SUM(analyzed_table."target_column") AS actual_value,
            
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
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
                SUM(analyzed_table."target_column") AS actual_value,
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
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
                SUM(analyzed_table."target_column") AS actual_value,
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
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
                SUM(analyzed_table.`target_column`) AS actual_value,
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
                SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
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
                SUM(analyzed_table.[target_column]) AS actual_value,
                analyzed_table.[country] AS grouping_level_1,
                analyzed_table.[state] AS grouping_level_2,
                CAST(analyzed_table.[date_column] AS date) AS time_period,
                CAST((CAST(analyzed_table.[date_column] AS date)) AS DATETIME) AS time_period_utc
            FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
            GROUP BY analyzed_table.[country], analyzed_table.[state], CAST(analyzed_table.[date_column] AS date), CAST(analyzed_table.[date_column] AS date)
            ORDER BY level_1, level_2CAST(analyzed_table.[date_column] AS date)
            
                
            ```
    






___
