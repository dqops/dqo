---
title: DQOps data quality sampling sensors
---
# DQOps data quality sampling sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **sampling** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## column samples
Column level sensor that retrieves a column value samples. Column value sampling is used in profiling and in capturing error samples for failed data quality checks.

**Sensor summary**

The column samples sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | sampling | <span class="no-wrap-code">`column/sampling/column_samples`</span> | [*sensors/column/sampling*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/sampling/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`limit`</span>|The limit of results that are returned. The default value is 10 sample values with the highest count (the most popular).|*integer*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
            GROUP BY sample_value
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
            GROUP BY sample_value
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
            GROUP BY sample_value
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
            GROUP BY sample_value
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
            GROUP BY 1
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_table.sample_index DESC
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
            GROUP BY sample_value
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
            GROUP BY 1
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_table.sample_index DESC
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
            GROUP BY sample_value
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
            GROUP BY sample_value
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
            GROUP BY sample_value
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
            GROUP BY {{ lib.render_target_column('analyzed_table') }}
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
            GROUP BY 1
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_table.sample_index DESC
    ```
___




## What's next
- Learn how the [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) are defined in DQOps and what is the definition of all Jinja2 macros used in the templates
- Understand how DQOps [runs data quality checks](../../../dqo-concepts/architecture/data-quality-check-execution-flow.md), rendering templates to SQL queries
