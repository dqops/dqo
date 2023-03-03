
## **date values in future percent**
**Full sensor name**
```
column/datetime/date_values_in_future_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.




**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | lower == 'timestamp with time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'timestamp without time zone' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
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
    
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | lower == 'date' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMPTZ' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN ({{ lib.render_target_column('analyzed_table') }})::TIMESTAMP > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
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
    {% macro render_value_in_future() -%}
        {%- if table.columns[column_name].type_snapshot.column_type | upper == 'TIMESTAMP' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATE' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif table.columns[column_name].type_snapshot.column_type | upper == 'DATETIME' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                {{ render_value_in_future() }}
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

## **value in range date percent**
**Full sensor name**
```
column/datetime/value_in_range_date_percent
```
**Description**  
Column level sensor that calculates the percent of non-negative values in a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|min_value|Lower bound range variable.|date| ||
|max_value|Upper bound range variable.|date| ||




**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_value) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'timestamp' or lib.target_column_data_type == 'timestamp with time zone' or lib.target_column_data_type == 'varchar'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_value) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_format_cast()%}
        {%- if lib.target_column_data_type == 'date' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'TIMESTAMP' or lib.target_column_data_type == 'TIMESTAMPTZ' or lib.target_column_data_type == 'VARCHAR'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_value) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
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
    
    {% macro render_date_format_cast() -%}
        {%- if lib.target_column_data_type == 'DATE' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.target_column_data_type == 'DATETIME' or lib.target_column_data_type == 'TIMESTAMP'-%}
        TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        100.0 * SUM(
            CASE
                WHEN {{ render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_value) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_value) }} THEN 1
                ELSE 0
            END
        ) / COUNT(*) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___
