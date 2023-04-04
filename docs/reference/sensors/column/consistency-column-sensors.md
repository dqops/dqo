
## **date match format percent**
**Full sensor name**
```
column/consistency/date_match_format_percent
```
**Description**  
Column level sensor that calculates the percentage of values that does fit a given date regex in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|date_formats|Desired date format. Sensor will try to parse the column records and cast the data using this format.|enum| |DD.MM.YYYY<br/>DD-MM-YYYY<br/>YYYY-MM-DD<br/>DD/MM/YYYY<br/>|




**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'DD/MM/YYYY'-%}
            "^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})$"
        {%- elif date_formats == 'DD-MM-YYYY' -%}
            "^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})$"
        {%- elif date_formats == 'DD.MM.YYYY' -%}
            "^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})$"
        {%- elif date_formats == 'YYYY-MM-DD' -%}
            "^(\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])$"
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_CONTAINS(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), r{{render_date_formats(parameters.date_formats)}}) IS NOT FALSE
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'DD/MM/YYYY'-%}
            '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})$'
        {%- elif date_formats == 'DD-MM-YYYY' -%}
            '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})$'
        {%- elif date_formats == 'DD.MM.YYYY' -%}
            '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})$'
        {%- elif date_formats == 'YYYY-MM-DD' -%}
            '^(\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])$'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN SUBSTRING(CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) FROM {{render_date_formats(parameters.date_formats)}}) IS NOT NULL
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'DD/MM/YYYY'-%}
            '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])$'
        {%- elif date_formats == 'DD-MM-YYYY' -%}
            '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])$'
        {%- elif date_formats == 'DD.MM.YYYY' -%}
            '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])$'
        {%- elif date_formats == 'YYYY-MM-DD' -%}
            '^([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])$'
        {%- endif -%}
    {% endmacro -%}
    
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{lib.render_target_column('analyzed_table')}} ~ {{render_date_formats(parameters.date_formats)}})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'DD/MM/YYYY'-%}
            '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])$'
        {%- elif date_formats == 'DD-MM-YYYY' -%}
            '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])$'
        {%- elif date_formats == 'DD.MM.YYYY' -%}
            '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])$'
        {%- elif date_formats == 'YYYY-MM-DD' -%}
            '^([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9])[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])$'
        {%- endif -%}
    {% endmacro -%}
    
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___
