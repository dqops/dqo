
## **total average match percent**
**Full sensor name**
```
column/accuracy/total_average_match_percent
```
**Description**  
Column level sensor that calculates the percentage of the difference in average of a column in a table and average of a column of another table.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| ||
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| ||




**SQL Template (Jinja2)**  
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
___

## **total max match percent**
**Full sensor name**
```
column/accuracy/total_max_match_percent
```
**Description**  
Column level sensor that calculates the percentage of the difference in max of a column in a table and max of a column of another table.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| ||
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| ||




**SQL Template (Jinja2)**  
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
___

## **total min match percent**
**Full sensor name**
```
column/accuracy/total_min_match_percent
```
**Description**  
Column level sensor that calculates the percentage of the difference in min of a column in a table and min of a column of another table.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| ||
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| ||




**SQL Template (Jinja2)**  
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
___

## **total not null count match percent**
**Full sensor name**
```
column/accuracy/total_not_null_count_match_percent
```
**Description**  
Column level sensor that calculates the percentage of the difference in row count of a column in a table and row count of a column of another table.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| ||
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| ||




**SQL Template (Jinja2)**  
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
            COUNT(referenced_table.{{ lib.quote_identifier(parameters.referenced_column) }})
        FROM {{ render_referenced_table(parameters.referenced_table) }} AS referenced_table
        ) AS expected_value,
        COUNT({{ lib.render_target_column('analyzed_table')}}) AS actual_value
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    ```
___

## **total sum match percent**
**Full sensor name**
```
column/accuracy/total_sum_match_percent
```
**Description**  
Column level sensor that calculates the percentage of the difference in sum of a column in a table and sum of a column of another table.

**Parameters**  
  
| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| ||
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| ||




**SQL Template (Jinja2)**  
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
___
