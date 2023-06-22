
## **date values in future percent**
**Full sensor name**
```
column/datetime/date_values_in_future_percent
```
**Description**  
Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.




**SQL Template (Jinja2)**  
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_value_in_future() -%}
        {%- if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
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
    
    {% macro render_value_in_future() -%}
        {%- if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP()
                        THEN 1
                    ELSE 0
                END
        {%- elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
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
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_value_in_future() -%}
        {%- if lib.is_instant(table.columns[column_name].type_snapshot.column_typ) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
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
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_value_in_future() -%}
        {%- if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
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
    {% macro render_value_in_future() -%}
        {%- if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATE
                        THEN 1
                    ELSE 0
                END
        {%- elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN TRY_TO_TIMESTAMP({{ lib.render_target_column('analyzed_table') }}) > CURRENT_TIMESTAMP
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
    
    {% macro render_value_in_future() -%}
        {%- if lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > SYSDATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- elif lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > GETDATE()
                        THEN 1
                    ELSE 0
                END
        {%- elif lib.is_local_date_time(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} > GETDATE()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DATETIME) > SYSDATETIME()
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
        {{- lib.render_data_grouping_projections('analyzed_table') }}
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
=== "BigQuery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        {{ lib.render_target_column('analyzed_table') }}
        {%- elif lib.is_local_time(table.columns[column_name].type_snapshot.column_type) == 'true' or lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_value) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_value) }} THEN 1
                ELSE 0
                END
            ) / COUNT(*)
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
    
    {% macro render_date_format_cast() -%}
        {%- if lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        {{ lib.render_target_column('analyzed_table') }}
        {%- elif lib.is_local_time(table.columns[column_name].type_snapshot.column_type) == 'true' or lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_value) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_value) }} THEN 1
                ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_date_format_cast()%}
        {%- if lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.is_local_time(table.columns[column_name].type_snapshot.column_type) == 'true' or lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true'-%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_value) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_value) }} THEN 1
                ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "Redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    
    {% macro render_date_format_cast()%}
        {%- if lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.is_local_time(table.columns[column_name].type_snapshot.column_type) == 'true' or lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_value) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_value) }} THEN 1
                ELSE 0
                END
            ) / COUNT(*)
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
    
    {% macro render_date_format_cast() -%}
        {%- if lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        {{ render_target_column('analyzed_table') }}
        {%- elif lib.is_local_time(table.columns[column_name].type_snapshot.column_type) == 'true' or lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_value) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_value) }} THEN 1
                ELSE 0
                END
            ) / COUNT(*)
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
    
    {% macro render_date_format_cast() -%}
        {%- if lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        {{ lib.render_target_column('analyzed_table') }}
        {%- elif lib.is_local_time(table.columns[column_name].type_snapshot.column_type) == 'true' or lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    {% macro render_ordering_column_names() %}
        {%- if lib.time_series is not none and lib.time_series.mode != 'current_time' -%}
            ORDER BY {{ lib.render_time_dimension_expression(lib.table_alias_prefix) }}
        {%- elif (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) %}
            {{ ', ' }}
        {% endif %}
        {%- if (lib.data_groupings is not none and (lib.data_groupings | length()) > 0) -%}
            {%- for attribute in lib.data_groupings -%}
                {%- if not loop.first -%}
                    {{ ', ' }}
                {%- endif -%}
                    {{ attribute }}
            {%- endfor -%}
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ render_date_format_cast() }} >= {{ lib.make_text_constant(parameters.min_value) }} AND {{ render_date_format_cast() }} <= {{ lib.make_text_constant(parameters.max_value) }} THEN 1
                ELSE 0
                END
            ) / COUNT(*)
        END AS actual_value
        {{- lib.render_data_grouping_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- render_ordering_column_names() -}}
    ```
___
