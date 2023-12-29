
## **foreign key match percent**
**Full sensor name**
```
column/integrity/foreign_key_match_percent
```
**Description**  
Column level sensor that calculates the percentage of values that match values in column of another table.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|foreign_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| ||
|foreign_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| ||




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Oracle"
      
    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}
    ) analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CAST( 100.0 * SUM(
            CASE
                WHEN foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }} IS NULL AND {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    THEN 0
                ELSE 1
            END
        ) / COUNT(*) AS DOUBLE) AS actual_value
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **foreign key not match count**
**Full sensor name**
```
column/integrity/foreign_key_not_match_count
```
**Description**  
Column level sensor that calculates the count of values that does not match values in column of another table.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|foreign_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| ||
|foreign_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| ||




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_project_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Databricks"
      
    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
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
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
        {{- lib.render_where_clause(table_alias_prefix='original_table') }}
    ) analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {%- macro render_target_column(table_alias_prefix = '') -%}
        {{ table_alias_prefix }}.{{ lib.quote_identifier(column_name) }}
    {%- endmacro %}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Presto"
      
    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
            {{- lib.render_where_clause(table_alias_prefix='original_table') }}
        ) analyzed_table
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
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
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Spark"
      
    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "SQL Server"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    {%- macro render_foreign_table(foreign_table) -%}
    {%- if foreign_table.find(".") < 0 -%}
       {{ lib.quote_identifier(lib.macro_database_name) }}.{{ lib.quote_identifier(lib.macro_schema_name) }}.{{- lib.quote_identifier(foreign_table) -}}
    {%- else -%}
       {{ foreign_table }}
    {%- endif -%}
    {%- endmacro -%}
    
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
    LEFT OUTER JOIN {{ render_foreign_table(parameters.foreign_table) }} AS foreign_table
    ON {{ lib.render_target_column('analyzed_table')}} = foreign_table.{{ lib.quote_identifier(parameters.foreign_column) }}
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___
