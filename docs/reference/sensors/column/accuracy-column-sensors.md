---
title: DQOps data quality accuracy sensors
---
# DQOps data quality accuracy sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **accuracy** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## total average match percent
Column level sensor that calculates the percentage of the difference in average of a column in a table and average of a column of another table.

**Sensor summary**

The total average match percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | accuracy | <span class="no-wrap-code">`column/accuracy/total_average_match_percent`</span> | [*sensors/column/accuracy*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/accuracy/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`referenced_table`</span>|The name of the reference table. DQOps accepts the name in two forms: a fully qualified name including the schema name, for example landing_zone.customer_raw, or only a table name. When only a table name is used, DQOps assumes that the table is in the same schema as the analyzed table, and prefixes the name with the schema and optionally database name.|*string*|:material-check-bold:||
|<span class="no-wrap-code">`referenced_column`</span>|The name of a column in the reference table. DQOps calculates an aggregate value on that column and compares it with the value in the analyzed table.|*string*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ lib.render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
      {{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
      {{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} referenced_table
        ) AS expected_value,
        analyzed_table.actual_value
    FROM (SELECT
            AVG({{ lib.render_target_column('original_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause() -}} ) analyzed_table
    GROUP BY actual_value
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_catalog_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            AVG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        AVG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
___



## total max match percent
Column level sensor that calculates the percentage of the difference in max of a column in a table and max of a column of another table.

**Sensor summary**

The total max match percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | accuracy | <span class="no-wrap-code">`column/accuracy/total_max_match_percent`</span> | [*sensors/column/accuracy*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/accuracy/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`referenced_table`</span>|The name of the reference table. DQOps accepts the name in two forms: a fully qualified name including the schema name, for example landing_zone.customer_raw, or only a table name. When only a table name is used, DQOps assumes that the table is in the same schema as the analyzed table, and prefixes the name with the schema and optionally database name.|*string*|:material-check-bold:||
|<span class="no-wrap-code">`referenced_column`</span>|The name of a column in the reference table. DQOps calculates an aggregate value on that column and compares it with the value in the analyzed table.|*string*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MAX(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MAX(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    SELECT
        (SELECT
            MAX(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ lib.render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MAX(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
      {{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MAX(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} referenced_table
        ) AS expected_value,analyzed_table.actual_value
    FROM (SELECT
            MAX({{ lib.render_target_column('original_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause() -}} ) analyzed_table
    GROUP BY actual_value
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MAX(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MAX(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MAX(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MAX(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MAX(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MAX(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_catalog_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MAX(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MAX({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
___



## total min match percent
Column level sensor that calculates the percentage of the difference in min of a column in a table and min of a column of another table.

**Sensor summary**

The total min match percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | accuracy | <span class="no-wrap-code">`column/accuracy/total_min_match_percent`</span> | [*sensors/column/accuracy*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/accuracy/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`referenced_table`</span>|The name of the reference table. DQOps accepts the name in two forms: a fully qualified name including the schema name, for example landing_zone.customer_raw, or only a table name. When only a table name is used, DQOps assumes that the table is in the same schema as the analyzed table, and prefixes the name with the schema and optionally database name.|*string*|:material-check-bold:||
|<span class="no-wrap-code">`referenced_column`</span>|The name of a column in the reference table. DQOps calculates an aggregate value on that column and compares it with the value in the analyzed table.|*string*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MIN(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MIN(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    SELECT
        (SELECT
            MIN(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ lib.render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MIN(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MIN(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} referenced_table
        ) AS expected_value,
        analyzed_table.actual_value
    FROM (SELECT
            MIN({{ lib.render_target_column('original_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause() -}} ) analyzed_table
    GROUP BY actual_value
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MIN(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MIN(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MIN(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MIN(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MIN(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MIN(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_catalog_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            MIN(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        MIN({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
___



## total not null count match percent
Column level sensor that calculates the percentage of the difference in row count of a column in a table and row count of a column of another table.

**Sensor summary**

The total not null count match percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | accuracy | <span class="no-wrap-code">`column/accuracy/total_not_null_count_match_percent`</span> | [*sensors/column/accuracy*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/accuracy/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`referenced_table`</span>|The name of the reference table. DQOps accepts the name in two forms: a fully qualified name including the schema name, for example landing_zone.customer_raw, or only a table name. When only a table name is used, DQOps assumes that the table is in the same schema as the analyzed table, and prefixes the name with the schema and optionally database name.|*string*|:material-check-bold:||
|<span class="no-wrap-code">`referenced_column`</span>|The name of a column in the reference table. DQOps calculates an aggregate value on that column and compares it with the value in the analyzed table.|*string*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ lib.render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} referenced_table
        ) AS expected_value,
        analyzed_table.actual_value
    FROM (SELECT
            COUNT({{ lib.render_target_column('original_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause() -}} ) analyzed_table
    GROUP BY actual_value
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT_BIG(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT_BIG({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_catalog_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
___



## total sum match percent
Column level sensor that calculates the percentage of the difference in sum of a column in a table and sum of a column of another table.

**Sensor summary**

The total sum match percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | accuracy | <span class="no-wrap-code">`column/accuracy/total_sum_match_percent`</span> | [*sensors/column/accuracy*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/accuracy/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`referenced_table`</span>|The name of the reference table. DQOps accepts the name in two forms: a fully qualified name including the schema name, for example landing_zone.customer_raw, or only a table name. When only a table name is used, DQOps assumes that the table is in the same schema as the analyzed table, and prefixes the name with the schema and optionally database name.|*string*|:material-check-bold:||
|<span class="no-wrap-code">`referenced_column`</span>|The name of a column in the reference table. DQOps calculates an aggregate value on that column and compares it with the value in the analyzed table.|*string*|:material-check-bold:||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    SELECT
        (SELECT
            SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ lib.render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
      {{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    
    SELECT
        (SELECT
            SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} referenced_table
        ) AS expected_value,
        analyzed_table.actual_value
    FROM (SELECT
            SUM({{ lib.render_target_column('original_table')}}) AS actual_value
        FROM {{ lib.render_target_table() }} original_table
        {{- lib.render_where_clause() -}} ) analyzed_table
    GROUP BY actual_value
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Redshift"

    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Snowflake"

    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "SQL Server"

    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_catalog_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            SUM(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        SUM({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
___




## What's next
- Learn how the [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) are defined in DQOps and what is the definition of all Jinja2 macros used in the templates
- Understand how DQOps [runs data quality checks](../../../dqo-concepts/architecture/data-quality-check-execution-flow.md), rendering templates to SQL queries
