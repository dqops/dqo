# table availability data quality checks

Table-level check that verifies that a query can be executed on a table and that the server does not return errors, that the table exists, and that the table is accessible (queryable).
 The actual value (the result of the check) is the number of failures. When the table is accessible and a simple query was executed without errors, the result is 0.0.
 The sensor result (the actual value) 1.0 means that there is a failure. A value higher than 1.0 is stored only in the check result table and it is the number of consecutive failures in following days.


___
The **table availability** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile table availability


**Check description**

Verifies availability of a table in a monitored database using a simple query.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|profile_table_availability|profiling| |Availability|[table_availability](../../../reference/sensors/table/availability-table-sensors.md#table-availability)|[max_failures](../../../reference/rules/Comparison.md#max-failures)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the profile table availability data quality check.

??? example "Managing profile table availability check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -ch=profile_table_availability --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=profile_table_availability --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=profile_table_availability --enable-warning
                            -Wmax_failures=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -ch=profile_table_availability --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=profile_table_availability --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=profile_table_availability --enable-error
                            -Emax_failures=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_table_availability* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_table_availability
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_table_availability
        ```

        You can also run this check on all tables  on which the *profile_table_availability* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -ch=profile_table_availability
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-13"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  profiling_checks:
    availability:
      profile_table_availability:
        warning:
          max_failures: 0
        error:
          max_failures: 5
        fatal:
          max_failures: 10
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [table_availability](../../../reference/sensors/table/availability-table-sensors.md#table-availability)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                    FROM `<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
                    FROM `<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
                    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                    FROM `<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT TOP 1
                        *
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                ) AS tab_scan
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT TOP 1
                        *
                    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                    
                ) AS tab_scan
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    
___


## daily table availability


**Check description**

Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each day when the data quality check was evaluated.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|daily_table_availability|monitoring|daily|Availability|[table_availability](../../../reference/sensors/table/availability-table-sensors.md#table-availability)|[max_failures](../../../reference/rules/Comparison.md#max-failures)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the daily table availability data quality check.

??? example "Managing daily table availability check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -ch=daily_table_availability --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=daily_table_availability --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=daily_table_availability --enable-warning
                            -Wmax_failures=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -ch=daily_table_availability --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=daily_table_availability --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=daily_table_availability --enable-error
                            -Emax_failures=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_table_availability* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_table_availability
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_table_availability
        ```

        You can also run this check on all tables  on which the *daily_table_availability* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -ch=daily_table_availability
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-14"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    daily:
      availability:
        daily_table_availability:
          warning:
            max_failures: 0
          error:
            max_failures: 5
          fatal:
            max_failures: 10
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [table_availability](../../../reference/sensors/table/availability-table-sensors.md#table-availability)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                0.0 AS actual_value,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
                    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                0.0 AS actual_value,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
                    FROM `<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-%d 00:00:00'))) AS time_period_utc
                    FROM `<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                0.0 AS actual_value,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                0.0 AS actual_value,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                CAST(LOCALTIMESTAMP AS date) AS time_period,
                CAST((CAST(LOCALTIMESTAMP AS date)) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                0.0 AS actual_value,
                CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
                TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date) AS time_period,
                TO_TIMESTAMP(CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period_utc
                    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                0.0 AS actual_value,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period,
                TIMESTAMP(CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period_utc
                    FROM `<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT TOP 1
                        *
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                ) AS tab_scan
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT TOP 1
                        *
                    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                    
                ) AS tab_scan
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                CAST(CURRENT_TIMESTAMP AS date) AS time_period,
                CAST(CAST(CURRENT_TIMESTAMP AS date) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    
___


## monthly table availability


**Check description**

Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each month when the data quality check was evaluated.

|Data quality check name|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|
|----------|----------|----------|-----------------|-----------------|------------|
|monthly_table_availability|monitoring|monthly|Availability|[table_availability](../../../reference/sensors/table/availability-table-sensors.md#table-availability)|[max_failures](../../../reference/rules/Comparison.md#max-failures)|

**Command-line examples**

Please expand the section below to see the DQOps command-line examples to run or activate the monthly table availability data quality check.

??? example "Managing monthly table availability check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -ch=monthly_table_availability --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=monthly_table_availability --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=monthly_table_availability --enable-warning
                            -Wmax_failures=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -ch=monthly_table_availability --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=monthly_table_availability --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -ch=monthly_table_availability --enable-error
                            -Emax_failures=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_table_availability* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_table_availability
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_table_availability
        ```

        You can also run this check on all tables  on which the *monthly_table_availability* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -ch=monthly_table_availability
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-14"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    monthly:
      availability:
        monthly_table_availability:
          warning:
            max_failures: 0
          error:
            max_failures: 5
          fatal:
            max_failures: 10
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [table_availability](../../../reference/sensors/table/availability-table-sensors.md#table-availability)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                    FROM `<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00') AS time_period,
                FROM_UNIXTIME(UNIX_TIMESTAMP(DATE_FORMAT(LOCALTIMESTAMP, '%Y-%m-01 00:00:00'))) AS time_period_utc
                    FROM `<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date)) AS time_period,
                CAST((DATE_TRUNC('MONTH', CAST(LOCALTIMESTAMP AS date))) AS TIMESTAMP WITH TIME ZONE) AS time_period_utc
                    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date)) AS time_period,
                TO_TIMESTAMP(DATE_TRUNC('MONTH', CAST(TO_TIMESTAMP_NTZ(LOCALTIMESTAMP()) AS date))) AS time_period_utc
                    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                0.0 AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE)) AS time_period,
                TIMESTAMP(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP() AS DATE))) AS time_period_utc
                    FROM `<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT TOP 1
                        *
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                ) AS tab_scan
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT TOP 1
                        *
                    FROM [your_sql_server_database].[<target_schema>].[<target_table>] AS analyzed_table
                    
                ) AS tab_scan
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
                {{- lib.render_time_dimension_projection('tab_scan') }}
            FROM
                (
                    SELECT
                        *
                        {{- lib.render_time_dimension_projection('analyzed_table') }}
                    FROM {{ lib.render_target_table() }} AS analyzed_table
                    {{ lib.render_where_clause() }}
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
            FROM
                (
                    SELECT
                        *,
                DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS time_period,
                CAST(DATE_TRUNC('MONTH', CAST(CURRENT_TIMESTAMP AS date)) AS TIMESTAMP) AS time_period_utc
                    FROM ""."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            GROUP BY time_period
            ORDER BY time_period
            ```
    
___


