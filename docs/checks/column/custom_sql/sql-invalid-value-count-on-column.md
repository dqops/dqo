---
title: sql invalid value count on column data quality checks
---
# sql invalid value count on column data quality checks

A column-level check that uses a custom SQL query that return invalid values from column.
 This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries).
 Use the {alias} token to reference the tested table, and the {column} to reference the column that is tested.
 For example, when this check is applied on a column, the condition can verify that the column has lower value than 18 using an SQL expression: &#x60;{alias}.{column} &lt; 18&#x60;.


___
The **sql invalid value count on column** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile sql invalid value count on column


**Check description**

Runs a custom query that retrieves invalid values found in a column and returns the number of them, and raises an issue if too many failures were detected. This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries). For example, when this check is applied on a column, the condition can verify that the column has lower value than 18 using an SQL expression: &#x60;{alias}.{column} &lt; 18&#x60;.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_sql_invalid_value_count_on_column`</span>|Custom SELECT SQL that returns invalid values|[custom_sql](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-with-custom-sql.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |[Validity](../../../dqo-concepts/data-quality-dimensions.md#data-validity)|[*sql_invalid_value_count*](../../../reference/sensors/column/custom_sql-column-sensors.md#sql-invalid-value-count)|[*max_count*](../../../reference/rules/Comparison.md#max-count)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile sql invalid value count on column data quality check.

??? example "Managing profile sql invalid value count on column check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_sql_invalid_value_count_on_column --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_sql_invalid_value_count_on_column --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_sql_invalid_value_count_on_column --enable-warning
                            -Wmax_count=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=profile_sql_invalid_value_count_on_column --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_sql_invalid_value_count_on_column --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=profile_sql_invalid_value_count_on_column --enable-error
                            -Emax_count=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_sql_invalid_value_count_on_column* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_sql_invalid_value_count_on_column
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_sql_invalid_value_count_on_column
        ```

        You can also run this check on all tables (and columns)  on which the *profile_sql_invalid_value_count_on_column* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=profile_sql_invalid_value_count_on_column
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-20"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      profiling_checks:
        custom_sql:
          profile_sql_invalid_value_count_on_column:
            parameters:
              sql_query: |-
                SELECT age AS actual_value
                FROM customers
                WHERE age < 18
            warning:
              max_count: 0
            error:
              max_count: 10
            fatal:
              max_count: 100
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [sql_invalid_value_count](../../../reference/sensors/column/custom_sql-column-sensors.md#sql-invalid-value-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for ClickHouse"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for MariaDB"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) analyzed_table
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) analyzed_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"

            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for QuestDB"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Teradata"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 32-37"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
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
          profiling_checks:
            custom_sql:
              profile_sql_invalid_value_count_on_column:
                parameters:
                  sql_query: |-
                    SELECT age AS actual_value
                    FROM customers
                    WHERE age < 18
                warning:
                  max_count: 0
                error:
                  max_count: 10
                fatal:
                  max_count: 100
          labels:
          - This is the column that is analyzed for data quality issues
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [sql_invalid_value_count](../../../reference/sensors/column/custom_sql-column-sensors.md#sql-invalid-value-count)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for ClickHouse"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for HANA"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"
            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for MariaDB"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) analyzed_table
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) analyzed_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for QuestDB"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"
            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Teradata"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    
___


## daily sql invalid value count on column


**Check description**

Runs a custom query that retrieves invalid values found in a column and returns the number of them, and raises an issue if too many failures were detected. This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries). For example, when this check is applied on a column, the condition can verify that the column has lower value than 18 using an SQL expression: &#x60;{alias}.{column} &lt; 18&#x60;.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_sql_invalid_value_count_on_column`</span>|Custom SELECT SQL that returns invalid values|[custom_sql](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-with-custom-sql.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|[Validity](../../../dqo-concepts/data-quality-dimensions.md#data-validity)|[*sql_invalid_value_count*](../../../reference/sensors/column/custom_sql-column-sensors.md#sql-invalid-value-count)|[*max_count*](../../../reference/rules/Comparison.md#max-count)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily sql invalid value count on column data quality check.

??? example "Managing daily sql invalid value count on column check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_sql_invalid_value_count_on_column --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_sql_invalid_value_count_on_column --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_sql_invalid_value_count_on_column --enable-warning
                            -Wmax_count=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=daily_sql_invalid_value_count_on_column --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_sql_invalid_value_count_on_column --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=daily_sql_invalid_value_count_on_column --enable-error
                            -Emax_count=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_sql_invalid_value_count_on_column* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_sql_invalid_value_count_on_column
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_sql_invalid_value_count_on_column
        ```

        You can also run this check on all tables (and columns)  on which the *daily_sql_invalid_value_count_on_column* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=daily_sql_invalid_value_count_on_column
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-21"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        daily:
          custom_sql:
            daily_sql_invalid_value_count_on_column:
              parameters:
                sql_query: |-
                  SELECT age AS actual_value
                  FROM customers
                  WHERE age < 18
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 100
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [sql_invalid_value_count](../../../reference/sensors/column/custom_sql-column-sensors.md#sql-invalid-value-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for ClickHouse"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for MariaDB"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) analyzed_table
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) analyzed_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"

            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for QuestDB"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Teradata"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 33-38"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
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
          monitoring_checks:
            daily:
              custom_sql:
                daily_sql_invalid_value_count_on_column:
                  parameters:
                    sql_query: |-
                      SELECT age AS actual_value
                      FROM customers
                      WHERE age < 18
                  warning:
                    max_count: 0
                  error:
                    max_count: 10
                  fatal:
                    max_count: 100
          labels:
          - This is the column that is analyzed for data quality issues
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [sql_invalid_value_count](../../../reference/sensors/column/custom_sql-column-sensors.md#sql-invalid-value-count)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for ClickHouse"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for HANA"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"
            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for MariaDB"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) analyzed_table
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) analyzed_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for QuestDB"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"
            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Teradata"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    
___


## monthly sql invalid value count on column


**Check description**

Runs a custom query that retrieves invalid values found in a column and returns the number of them, and raises an issue if too many failures were detected. This check is used for setting testing queries or ready queries used by users in their own systems (legacy SQL queries). For example, when this check is applied on a column, the condition can verify that the column has lower value than 18 using an SQL expression: &#x60;{alias}.{column} &lt; 18&#x60;.

|Data quality check name|Friendly name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|-------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_sql_invalid_value_count_on_column`</span>|Custom SELECT SQL that returns invalid values|[custom_sql](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-with-custom-sql.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|monthly|[Validity](../../../dqo-concepts/data-quality-dimensions.md#data-validity)|[*sql_invalid_value_count*](../../../reference/sensors/column/custom_sql-column-sensors.md#sql-invalid-value-count)|[*max_count*](../../../reference/rules/Comparison.md#max-count)|:material-check-bold:|

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly sql invalid value count on column data quality check.

??? example "Managing monthly sql invalid value count on column check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_sql_invalid_value_count_on_column --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_sql_invalid_value_count_on_column --enable-warning
        ```
        
        Additional rule parameters are passed using the *-Wrule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_sql_invalid_value_count_on_column --enable-warning
                            -Wmax_count=value
        ```


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name -col=column_name -ch=monthly_sql_invalid_value_count_on_column --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_sql_invalid_value_count_on_column --enable-error
        ```
        
        Additional rule parameters are passed using the *-Erule_parameter_name=value*.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_* -col=column_name -ch=monthly_sql_invalid_value_count_on_column --enable-error
                            -Emax_count=value
        ```


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_sql_invalid_value_count_on_column* check on all tables and columns on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_sql_invalid_value_count_on_column
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_sql_invalid_value_count_on_column
        ```

        You can also run this check on all tables (and columns)  on which the *monthly_sql_invalid_value_count_on_column* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_* -col=column_name_* -ch=monthly_sql_invalid_value_count_on_column
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="7-21"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  columns:
    target_column:
      monitoring_checks:
        monthly:
          custom_sql:
            monthly_sql_invalid_value_count_on_column:
              parameters:
                sql_query: |-
                  SELECT age AS actual_value
                  FROM customers
                  WHERE age < 18
              warning:
                max_count: 0
              error:
                max_count: 10
              fatal:
                max_count: 100
      labels:
      - This is the column that is analyzed for data quality issues

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [sql_invalid_value_count](../../../reference/sensors/column/custom_sql-column-sensors.md#sql-invalid-value-count)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"

            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for ClickHouse"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"

            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for DB2"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"

            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for DuckDB"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"

            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for HANA"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"

            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for MariaDB"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) analyzed_table
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) analyzed_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"

            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for QuestDB"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"

            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Teradata"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    

Expand the *Configure with data grouping* section to see additional examples for configuring this data quality checks to use data grouping (GROUP BY).

??? info "Configuration with data grouping"

    **Sample configuration with data grouping enabled (YAML)**
    The sample below shows how to configure the data grouping and how it affects the generated SQL query.

    ```yaml hl_lines="5-13 33-38"
    # yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
    apiVersion: dqo/v1
    kind: table
    spec:
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
          monitoring_checks:
            monthly:
              custom_sql:
                monthly_sql_invalid_value_count_on_column:
                  parameters:
                    sql_query: |-
                      SELECT age AS actual_value
                      FROM customers
                      WHERE age < 18
                  warning:
                    max_count: 0
                  error:
                    max_count: 10
                  fatal:
                    max_count: 100
          labels:
          - This is the column that is analyzed for data quality issues
        country:
          labels:
          - column used as the first grouping key
        state:
          labels:
          - column used as the second grouping key
    ```

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [sql_invalid_value_count](../../../reference/sensors/column/custom_sql-column-sensors.md#sql-invalid-value-count)
    [sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"
            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for BigQuery"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "ClickHouse"

        === "Sensor template for ClickHouse"
            ```sql+jinja
            {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for ClickHouse"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"
            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Databricks"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "DB2"

        === "Sensor template for DB2"
            ```sql+jinja
            {% import '/dialects/db2.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for DB2"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "DuckDB"

        === "Sensor template for DuckDB"
            ```sql+jinja
            {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for DuckDB"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "HANA"

        === "Sensor template for HANA"
            ```sql+jinja
            {% import '/dialects/hana.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for HANA"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "MariaDB"

        === "Sensor template for MariaDB"
            ```sql+jinja
            {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for MariaDB"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"
            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for MySQL"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"
            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) analyzed_table
            ```
        === "Rendered SQL for Oracle"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) analyzed_table
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"
            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for PostgreSQL"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Presto"

        === "Sensor template for Presto"
            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Presto"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "QuestDB"

        === "Sensor template for QuestDB"
            ```sql+jinja
            {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for QuestDB"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"
            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Redshift"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"
            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Snowflake"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Spark"

        === "Sensor template for Spark"
            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Spark"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"
            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for SQL Server"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Teradata"

        === "Sensor template for Teradata"
            ```sql+jinja
            {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Teradata"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    ??? example "Trino"

        === "Sensor template for Trino"
            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            SELECT
                COUNT(*) as actual_value
            FROM (
                {{ parameters.sql_query | replace('{column}', lib.render_target_column('analyzed_table')) | replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}
            ) AS analyzed_table
            ```
        === "Rendered SQL for Trino"
            ```sql
            SELECT
                COUNT(*) as actual_value
            FROM (
                SELECT age AS actual_value
            FROM customers
            WHERE age < 18
            ) AS analyzed_table
            ```
    
___



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
