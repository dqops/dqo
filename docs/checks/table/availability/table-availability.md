---
title: table availability data quality checks
---
# table availability data quality checks

A table-level check that ensures a query can be successfully executed on a table without server errors. It also verifies that the table exists and is accessible (queryable).
 The actual value (the result of the check) indicates the number of failures. If the table is accessible and a simple query can be executed without errors, the result will be 0.0.
 A sensor result (the actual value) of 1.0 indicates that there is a failure. Any value greater than 1.0 is stored only in the check result table and represents the number of consecutive failures in the following days.


___
The **table availability** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile table availability


**Check description**

Verifies availability of a table in a monitored database using a simple query.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_table_availability`</span>|Table availability|[availability](../../../categories-of-data-quality-checks/how-to-table-availability-issues-and-downtimes.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |[Availability](../../../dqo-concepts/data-quality-dimensions.md#data-availability)|[*table_availability*](../../../reference/sensors/table/availability-table-sensors.md#table-availability)|[*max_failures*](../../../reference/rules/Comparison.md#max-failures)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile table availability data quality check.

??? example "Managing profile table availability check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_table_availability --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_table_availability --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_table_availability --enable-warning
                            -Wmax_failures=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_table_availability --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_table_availability --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_table_availability --enable-error
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
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_table_availability
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
          max_failures: 1
        fatal:
          max_failures: 5
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM  AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for MariaDB"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                   WHEN COUNT(*) > 0 THEN COUNT(*)
                   ELSE 1.0
                END AS actual_value
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                   WHEN COUNT(*) > 0 THEN COUNT(*)
                   ELSE 1.0
                END AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    
___


## daily table availability


**Check description**

Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each day when the data quality check was evaluated.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_table_availability`</span>|Table availability|[availability](../../../categories-of-data-quality-checks/how-to-table-availability-issues-and-downtimes.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|[Availability](../../../dqo-concepts/data-quality-dimensions.md#data-availability)|[*table_availability*](../../../reference/sensors/table/availability-table-sensors.md#table-availability)|[*max_failures*](../../../reference/rules/Comparison.md#max-failures)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily table availability data quality check.

??? example "Managing daily table availability check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_table_availability --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_table_availability --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_table_availability --enable-warning
                            -Wmax_failures=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_table_availability --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_table_availability --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_table_availability --enable-error
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
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_table_availability
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
            max_failures: 1
          fatal:
            max_failures: 5
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM  AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for MariaDB"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                   WHEN COUNT(*) > 0 THEN COUNT(*)
                   ELSE 1.0
                END AS actual_value
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                   WHEN COUNT(*) > 0 THEN COUNT(*)
                   ELSE 1.0
                END AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    
___


## monthly table availability


**Check description**

Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each month when the data quality check was evaluated.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_table_availability`</span>|Table availability|[availability](../../../categories-of-data-quality-checks/how-to-table-availability-issues-and-downtimes.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|monthly|[Availability](../../../dqo-concepts/data-quality-dimensions.md#data-availability)|[*table_availability*](../../../reference/sensors/table/availability-table-sensors.md#table-availability)|[*max_failures*](../../../reference/rules/Comparison.md#max-failures)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly table availability data quality check.

??? example "Managing monthly table availability check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_table_availability --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_table_availability --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_table_availability --enable-warning
                            -Wmax_failures=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_table_availability --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_table_availability --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_table_availability --enable-error
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
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_table_availability
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
            max_failures: 1
          fatal:
            max_failures: 5
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `your-google-project-id`.`<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM  AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for MariaDB"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                CASE
                   WHEN COUNT(*) > 0 THEN COUNT(*)
                   ELSE 1.0
                END AS actual_value
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                CASE
                   WHEN COUNT(*) > 0 THEN COUNT(*)
                   ELSE 1.0
                END AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_postgresql_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_trino_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_redshift_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_snowflake_database"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                0.0 AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM `<target_schema>`.`<target_table>` AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
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
            {% if lib.time_series is not none -%}
            GROUP BY time_period
            ORDER BY time_period
            {%- endif -%}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                CAST(0.0 AS DOUBLE) AS actual_value
            FROM
                (
                    SELECT
                        *
                    FROM "your_trino_catalog"."<target_schema>"."<target_table>" AS analyzed_table
                    
                    LIMIT 1
                ) AS tab_scan
            ```
    
___



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
