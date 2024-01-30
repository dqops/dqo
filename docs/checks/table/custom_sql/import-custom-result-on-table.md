# import custom result on table data quality checks

A table-level check that uses a custom SQL SELECT statement to retrieve a result of running a custom data quality check that was hardcoded
 in the data pipeline, and the result was stored in a separate table. The SQL query that is configured in this external data quality results importer must be
 a complete SELECT statement that queries a dedicated table (created by the data engineers) that stores the results of custom data quality checks.
 The SQL query must return a *severity* column with values: 0 - data quality check passed, 1 - warning issue, 2 - error severity issue, 3 - fatal severity issue.


___
The **import custom result on table** data quality check has the following variants for each
[type of data quality](../../../dqo-concepts/definition-of-data-quality-checks/index.md#types-of-checks) checks supported by DQOps.


## profile import custom result on table


**Check description**

Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`profile_import_custom_result_on_table`</span>|[custom_sql](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-with-custom-sql.md)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)| |Validity|[*import_custom_result*](../../../reference/sensors/table/custom_sql-table-sensors.md#import-custom-result)|[*import_severity*](../../../reference/rules/Comparison.md#import-severity)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the profile import custom result on table data quality check.

??? example "Managing profile import custom result on table check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_import_custom_result_on_table --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_import_custom_result_on_table --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=profile_import_custom_result_on_table --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_import_custom_result_on_table --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *profile_import_custom_result_on_table* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=profile_import_custom_result_on_table
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=profile_import_custom_result_on_table
        ```

        You can also run this check on all tables  on which the *profile_import_custom_result_on_table* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=profile_import_custom_result_on_table
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-18"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  profiling_checks:
    custom_sql:
      profile_import_custom_result_on_table:
        parameters:
          sql_query: |-
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '{schema_name}' AND logs.analyzed_table_name = '{table_name}'
        warning: {}
        error: {}
        fatal: {}
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [import_custom_result](../../../reference/sensors/table/custom_sql-table-sensors.md#import-custom-result)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    
___


## daily import custom result on table


**Check description**

Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`daily_import_custom_result_on_table`</span>|[custom_sql](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-with-custom-sql.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|daily|Validity|[*import_custom_result*](../../../reference/sensors/table/custom_sql-table-sensors.md#import-custom-result)|[*import_severity*](../../../reference/rules/Comparison.md#import-severity)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the daily import custom result on table data quality check.

??? example "Managing daily import custom result on table check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_import_custom_result_on_table --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_import_custom_result_on_table --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=daily_import_custom_result_on_table --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_import_custom_result_on_table --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *daily_import_custom_result_on_table* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=daily_import_custom_result_on_table
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=daily_import_custom_result_on_table
        ```

        You can also run this check on all tables  on which the *daily_import_custom_result_on_table* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=daily_import_custom_result_on_table
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-19"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    daily:
      custom_sql:
        daily_import_custom_result_on_table:
          parameters:
            sql_query: |-
              SELECT
                logs.my_actual_value as actual_value,
                logs.my_expected_value as expected_value,
                logs.error_severity as severity
              FROM custom_data_quality_results as logs
              WHERE logs.analyzed_schema_name = '{schema_name}' AND logs.analyzed_table_name = '{table_name}'
          warning: {}
          error: {}
          fatal: {}
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [import_custom_result](../../../reference/sensors/table/custom_sql-table-sensors.md#import-custom-result)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    
___


## monthly import custom result on table


**Check description**

Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.

|Data quality check name|Category|Check type|Time scale|Quality dimension|Sensor definition|Quality rule|Standard|
|-----------------------|--------|----------|----------|-----------------|-----------------|------------|--------|
|<span class="no-wrap-code">`monthly_import_custom_result_on_table`</span>|[custom_sql](../../../categories-of-data-quality-checks/how-to-detect-data-quality-issues-with-custom-sql.md)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|monthly|Validity|[*import_custom_result*](../../../reference/sensors/table/custom_sql-table-sensors.md#import-custom-result)|[*import_severity*](../../../reference/rules/Comparison.md#import-severity)| |

**Command-line examples**

Please expand the section below to see the [DQOps command-line](../../../dqo-concepts/command-line-interface.md) examples to run or activate the monthly import custom result on table data quality check.

??? example "Managing monthly import custom result on table check from DQOps shell"

    === "Activate the check with a warning rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the warning rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_import_custom_result_on_table --enable-warning
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_import_custom_result_on_table --enable-warning
        ```
        


    === "Activate the check with an error rule"

        Activate this data quality using the [check activate](../../../command-line-interface/check.md#dqo-check-activate) CLI command,
        providing the connection name, table name, check name, and all other filters. Activates the error rule with the default parameters.

        ```
        dqo> check activate -c=connection_name -t=schema_name.table_name  -ch=monthly_import_custom_result_on_table --enable-error
        ```

        You can also use patterns to activate the check on all matching tables and columns.

        ```
        dqo> check activate -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_import_custom_result_on_table --enable-error
        ```
        


    === "Run all configured checks"

        Run this data quality check using the [check run](../../../command-line-interface/check.md#dqo-check-run) CLI command by providing the check name and all other targeting filters.
        The following example shows how to run the *monthly_import_custom_result_on_table* check on all tables on a single data source.

        ```
        dqo> check run -c=data_source_name -ch=monthly_import_custom_result_on_table
        ```

        It is also possible to run this check on a specific connection and table. In order to do this, use the connection name and the full table name parameters.

        ```
        dqo> check run -c=connection_name -t=schema_name.table_name -ch=monthly_import_custom_result_on_table
        ```

        You can also run this check on all tables  on which the *monthly_import_custom_result_on_table* check is enabled
        using patterns to find tables.

        ```
        dqo> check run -c=connection_name -t=schema_prefix*.fact_*  -ch=monthly_import_custom_result_on_table
        ```


**YAML configuration**

The sample *schema_name.table_name.dqotable.yaml* file with the check configured is shown below.


```yaml hl_lines="5-19"
# yaml-language-server: $schema=https://cloud.dqops.com/dqo-yaml-schema/TableYaml-schema.json
apiVersion: dqo/v1
kind: table
spec:
  monitoring_checks:
    monthly:
      custom_sql:
        monthly_import_custom_result_on_table:
          parameters:
            sql_query: |-
              SELECT
                logs.my_actual_value as actual_value,
                logs.my_expected_value as expected_value,
                logs.error_severity as severity
              FROM custom_data_quality_results as logs
              WHERE logs.analyzed_schema_name = '{schema_name}' AND logs.analyzed_table_name = '{table_name}'
          warning: {}
          error: {}
          fatal: {}
  columns: {}

```

??? info "Samples of generated SQL queries for each data source type"

    Please expand the database engine name section to see the SQL query rendered by a Jinja2 template for the
    [import_custom_result](../../../reference/sensors/table/custom_sql-table-sensors.md#import-custom-result)
    [data quality sensor](../../../dqo-concepts/definition-of-data-quality-sensors.md).

    ??? example "BigQuery"

        === "Sensor template for BigQuery"

            ```sql+jinja
            {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for BigQuery"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Databricks"

        === "Sensor template for Databricks"

            ```sql+jinja
            {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Databricks"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "MySQL"

        === "Sensor template for MySQL"

            ```sql+jinja
            {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for MySQL"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Oracle"

        === "Sensor template for Oracle"

            ```sql+jinja
            {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Oracle"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "PostgreSQL"

        === "Sensor template for PostgreSQL"

            ```sql+jinja
            {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for PostgreSQL"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Presto"

        === "Sensor template for Presto"

            ```sql+jinja
            {% import '/dialects/presto.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Presto"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Redshift"

        === "Sensor template for Redshift"

            ```sql+jinja
            {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Redshift"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Snowflake"

        === "Sensor template for Snowflake"

            ```sql+jinja
            {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Snowflake"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Spark"

        === "Sensor template for Spark"

            ```sql+jinja
            {% import '/dialects/spark.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Spark"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "SQL Server"

        === "Sensor template for SQL Server"

            ```sql+jinja
            {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for SQL Server"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    ??? example "Trino"

        === "Sensor template for Trino"

            ```sql+jinja
            {% import '/dialects/trino.sql.jinja2' as lib with context -%}
            {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
            ```
        === "Rendered SQL for Trino"

            ```sql
            SELECT
              logs.my_actual_value as actual_value,
              logs.my_expected_value as expected_value,
              logs.error_severity as severity
            FROM custom_data_quality_results as logs
            WHERE logs.analyzed_schema_name = '<target_schema>' AND logs.analyzed_table_name = '<target_table>'
            ```
    
___



## What's next
- Learn how to [configure data quality checks](../../../dqo-concepts/configuring-data-quality-checks-and-rules.md) in DQOps
- Look at the examples of [running data quality checks](../../../dqo-concepts/running-data-quality-checks.md), targeting tables and columns
