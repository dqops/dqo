---
title: DQOps data quality integrity sensors, SQL examples
---
# DQOps data quality integrity sensors, SQL examples
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **integrity** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## foreign key match percent
Column level sensor that calculates the percentage of values that match values in a column of another dictionary table.

**Sensor summary**

The foreign key match percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | integrity | <span class="no-wrap-code">`column/integrity/foreign_key_match_percent`</span> | [*sensors/column/integrity*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/integrity/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`foreign_table`</span>|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|*string*|:material-check-bold:||
|<span class="no-wrap-code">`foreign_column`</span>|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|*string*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "ClickHouse"

    ```sql+jinja
    {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DB2"

    ```sql+jinja
    {% import '/dialects/db2.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM (
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
    ) analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "HANA"

    ```sql+jinja
    {% import '/dialects/hana.sql.jinja2' as lib with context -%}
    
    SELECT
        CAST( 100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) AS DOUBLE) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
         {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM (
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
    ) analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MariaDB"

    ```sql+jinja
    {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM (
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
    ) analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    SELECT
        CAST( 100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) AS DOUBLE) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
         {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM (
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
    ) analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "QuestDB"

    ```sql+jinja
    {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT() AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
    ) analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Teradata"

    ```sql+jinja
    {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    SELECT
        CAST( 100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) AS DOUBLE) / COUNT(*) AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
         {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM (
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
    ) analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___



## foreign key not match count
Column level sensor that calculates the count of values that does not match values in a column of another dictionary table.

**Sensor summary**

The foreign key not match count sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | integrity | <span class="no-wrap-code">`column/integrity/foreign_key_not_match_count`</span> | [*sensors/column/integrity*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/integrity/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`foreign_table`</span>|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|*string*|:material-check-bold:||
|<span class="no-wrap-code">`foreign_column`</span>|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|*string*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "ClickHouse"

    ```sql+jinja
    {% import '/dialects/clickhouse.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DB2"

    ```sql+jinja
    {% import '/dialects/db2.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
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
    ) analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "HANA"

    ```sql+jinja
    {% import '/dialects/hana.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
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
    ) analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "MariaDB"

    ```sql+jinja
    {% import '/dialects/mariadb.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
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
    ) analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
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
    ) analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "QuestDB"

    ```sql+jinja
    {% import '/dialects/questdb.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections_reference('analyzed_table') }}
        {{- lib.render_time_dimension_projection_reference('analyzed_table') }}
    FROM(
        SELECT
            original_table.*
            {{- lib.render_data_grouping_projections('original_table') }}
            {{- lib.render_time_dimension_projection('original_table') }}
        FROM {{ lib.render_target_table() }} original_table
    ) analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Teradata"

    ```sql+jinja
    {% import '/dialects/teradata.sql.jinja2' as lib with context -%}
    
    SELECT
        SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
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
    ) analyzed_table
    LEFT OUTER JOIN {{ lib.render_referenced_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___




## What's next
- Learn how the [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) are defined in DQOps and what is the definition of all Jinja2 macros used in the templates
- Understand how DQOps [runs data quality checks](../../../dqo-concepts/architecture/data-quality-check-execution-flow.md), rendering templates to SQL queries
