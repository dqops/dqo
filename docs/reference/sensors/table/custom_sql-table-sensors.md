---
title: DQOps data quality custom sql sensors
---
# DQOps data quality custom sql sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **custom sql** category supported by DQOps are listed below. Those sensors are measured on a table level.

---


## import custom result
Table level sensor that uses a custom SQL SELECT statement to retrieve a result of running a custom data quality check that was hardcoded
 in the data pipeline, and the result was stored in a separate table. The SQL query that is configured in this external data quality results importer must be
 a complete SELECT statement that queries a dedicated table (created by the data engineers) that stores the results of custom data quality checks.

**Sensor summary**

The import custom result sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | custom_sql | <span class="no-wrap-code">`table/custom_sql/import_custom_result`</span> | [*sensors/table/custom_sql*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/custom_sql/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`sql_query`</span>|A custom SELECT statement that queries a logging table with custom results of data quality checks executed by the data pipeline. The query must return a result column named *severity*. The values of the *severity* column must be: 0 - data quality check passed, 1 - warning issue, 2 - error severity issue, 3 - fatal severity issue. The query can return *actual_value* and *expected_value* results that are imported into DQOps data lake. The query can use a {table_name} placeholder that is replaced with a table name for which the results are imported.|*string*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    {{ parameters.sql_query | replace('{table_name}', target_table.table_name) | replace('{schema_name}', target_table.schema_name) }}
    ```
___



## sql aggregated expression
Table level sensor that executes a given SQL expression on a table.

**Sensor summary**

The sql aggregated expression sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | custom_sql | <span class="no-wrap-code">`table/custom_sql/sql_aggregated_expression`</span> | [*sensors/table/custom_sql*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/custom_sql/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`sql_expression`</span>|SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated for the entire table or within a GROUP BY clause for daily partitions and/or data groups. The expression can use a {table} placeholder that is replaced with a full table name.|*string*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value {{-"," if lib.time_series is not none else ""}}
        {%- if lib.time_series is not none-%}
        time_period,
        time_period_utc
        {% endif %}
    FROM (SELECT
             original_table.*
             {{- lib.render_data_grouping_projections('original_table') }}
             {{- lib.render_time_dimension_projection('original_table') }}
         FROM {{ lib.render_target_table() }} original_table
         {{- lib.render_where_clause(table_alias_prefix='original_table') }}
     ) analyzed_table
     {{- lib.render_group_by() -}}
     {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
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
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
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
___



## sql condition failed count
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.

**Sensor summary**

The sql condition failed count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | custom_sql | <span class="no-wrap-code">`table/custom_sql/sql_condition_failed_count`</span> | [*sensors/table/custom_sql*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/custom_sql/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`sql_condition`</span>|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use a {table} placeholder that is replaced with a full table name.|*string*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                             replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                             replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                             replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                             replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                             replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value {{-"," if lib.time_series is not none else ""}}
        {%- if lib.time_series is not none-%}
        time_period,
        time_period_utc
        {% endif %}
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
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                             replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                             replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
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
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                             replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                             replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                             replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                             replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN NOT ({{ parameters.sql_condition |
                             replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
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
___



## sql condition failed percent
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.

**Sensor summary**

The sql condition failed percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | custom_sql | <span class="no-wrap-code">`table/custom_sql/sql_condition_failed_percent`</span> | [*sensors/table/custom_sql*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/custom_sql/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`sql_condition`</span>|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use a {table} placeholder that is replaced with a full table name.|*string*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                              replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                              replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                              replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                              replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                              replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value {{-"," if lib.time_series is not none else ""}}
        {%- if lib.time_series is not none-%}
        time_period,
        time_period_utc
        {% endif %}
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
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                              replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                              replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) AS DOUBLE) / COUNT(*)
        END AS actual_value
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
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                              replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                              replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                              replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                              replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT_BIG(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT (*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                             CASE
                                 WHEN NOT ({{ parameters.sql_condition |
                                              replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) AS DOUBLE) / COUNT(*)
        END AS actual_value
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
___



## sql condition passed count
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.

**Sensor summary**

The sql condition passed count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | custom_sql | <span class="no-wrap-code">`table/custom_sql/sql_condition_passed_count`</span> | [*sensors/table/custom_sql*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/custom_sql/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`sql_condition`</span>|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use a {table} placeholder that is replaced with a full table name.|*string*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                         replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                         replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                         replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                         replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                         replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value {{-"," if lib.time_series is not none else ""}}
        {%- if lib.time_series is not none-%}
        time_period,
        time_period_utc
        {% endif %}
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
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                         replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                         replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
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
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                         replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                         replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                         replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                         replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN ({{ parameters.sql_condition |
                         replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                     THEN 1
                ELSE 0
            END
        ) AS actual_value
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
___



## sql condition passed percent
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.

**Sensor summary**

The sql condition passed percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | custom_sql | <span class="no-wrap-code">`table/custom_sql/sql_condition_passed_percent`</span> | [*sensors/table/custom_sql*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/custom_sql/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`sql_condition`</span>|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use a {table} placeholder that is replaced with a full table name.|*string*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                          replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                          replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                          replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                          replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                          replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value {{-"," if lib.time_series is not none else ""}}
        {%- if lib.time_series is not none-%}
        time_period,
        time_period_utc
        {% endif %}
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
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                          replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                          replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) AS DOUBLE) / COUNT(*)
        END AS actual_value
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
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                          replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                          replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                          replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                          replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) / COUNT_BIG(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                             CASE
                                 WHEN ({{ parameters.sql_condition |
                                          replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                                      THEN 1
                                 ELSE 0
                             END) AS DOUBLE) / COUNT(*)
        END AS actual_value
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
___




## What's next
- Learn how the [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) are defined in DQOps and what is the definition of all Jinja2 macros used in the templates
- Understand how DQOps [runs data quality checks](../../../dqo-concepts/architecture/data-quality-check-execution-flow.md), rendering templates to SQL queries
