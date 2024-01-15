# Data quality availability sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **availability** category supported by DQOps are listed below. Those sensors are measured on a table level.

---


## table availability
Table availability sensor runs a simple table scan query to detect if the table is queryable. This sensor returns 0.0 when no failure was detected or 1.0 when a failure was detected.

**Sensor summary**

The table availability sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | availability | `table/availability/table_availability` | [sensors/table/availability](https://github.com/dqops/dqo/tree/develop/home/sensors/table/availability/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

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
=== "Databricks"

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
=== "MySQL"

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
=== "PostgreSQL"

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
=== "Presto"

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
=== "Redshift"

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
=== "Snowflake"

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
=== "Spark"

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
=== "SQL Server"

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
=== "Trino"

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
___




## What's next
- Learn how the [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) are defined in DQOps and what is the definition of all Jinja2 macros used in the templates
- Understand how DQOps [runs data quality checks](../../../dqo-concepts/architecture/data-quality-check-execution-flow.md), rendering templates to SQL queries
