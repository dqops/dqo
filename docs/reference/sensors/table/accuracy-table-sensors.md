# Data quality accuracy sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **accuracy** category supported by DQOps are listed below. Those sensors are measured on a table level.

---


## total row count match percent
Table level sensor that calculates the percentage of the difference of the total row count of all rows in the tested table and the total row count of the other (reference) table.

**Sensor summary**

The total row count match percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| table | accuracy | <span class="no-wrap-code">`table/accuracy/total_row_count_match_percent`</span> | [*sensors/table/accuracy*](https://github.com/dqops/dqo/tree/develop/home/sensors/table/accuracy/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`referenced_table`</span>|The name of the reference table. DQOps accepts the name in two forms: a fully qualified name including the schema name, for example landing_zone.customer_raw, or only a table name. When only a table name is used, DQOps assumes that the table is in the same schema as the analyzed table, and prefixes the name with the schema and optionally database name.|*string*|:material-check-bold:||






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
            COUNT(*)
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT(*) AS actual_value
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
            COUNT(*)
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT(*) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "DuckDB"

    ```sql+jinja
    {% import '/dialects/duckdb.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(*)
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT(*) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "MySQL"

    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(*)
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT(*) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
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
            COUNT(*)
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT(*) AS actual_value
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
            COUNT(*)
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT(*) AS actual_value
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
            COUNT(*)
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT(*) AS actual_value
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
            COUNT(*)
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT(*) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {%- macro render_referenced_table(referenced_table) -%}
    {%- if referenced_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(referenced_table) -}}
    {%- else -%}
       {{ referenced_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        (SELECT
            COUNT(*)
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT(*) AS actual_value
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
            COUNT_BIG(*)
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT_BIG(*) AS actual_value
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
            COUNT(*)
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT(*) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
___




## What's next
- Learn how the [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) are defined in DQOps and what is the definition of all Jinja2 macros used in the templates
- Understand how DQOps [runs data quality checks](../../../dqo-concepts/architecture/data-quality-check-execution-flow.md), rendering templates to SQL queries
