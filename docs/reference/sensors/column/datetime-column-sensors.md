# Data quality datetime sensors
All [data quality sensors](../../../dqo-concepts/definition-of-data-quality-sensors.md) in the **datetime** category supported by DQOps are listed below. Those sensors are measured on a column level.

---


## date match format percent
Column level sensor that calculates the percentage of values that does fit a given date regex in a column.

**Sensor summary**

The date match format percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | datetime | <span class="no-wrap-code">`column/datetime/date_match_format_percent`</span> | [*sensors/column/datetime*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/datetime/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`date_formats`</span>|Desired date format. Sensor will try to parse the column records and cast the data using this format.|*enum*|:material-check-bold:|*DD/MM/YYYY*<br/>*DD-MM-YYYY*<br/>*DD.MM.YYYY*<br/>*YYYY-MM-DD*<br/>|






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

=== "BigQuery"

    ```sql+jinja
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
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'DD/MM/YYYY'-%}
            "^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4})$"
        {%- elif date_formats == 'DD-MM-YYYY' -%}
            "^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4})$"
        {%- elif date_formats == 'DD.MM.YYYY' -%}
            "^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4})$"
        {%- elif date_formats == 'YYYY-MM-DD' -%}
            "^(\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])$"
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), {{render_date_formats(parameters.date_formats)}}) IS NOT FALSE
                        THEN 1
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
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'DD/MM/YYYY'-%}
            '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/]([0-9]{4})$'
        {%- elif date_formats == 'DD-MM-YYYY' -%}
            '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-]([0-9]{4})$'
        {%- elif date_formats == 'DD.MM.YYYY' -%}
            '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.]([0-9]{4})$'
        {%- elif date_formats == 'YYYY-MM-DD' -%}
            '^([0-9]{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])$'
        {%- endif -%}
    {% endmacro -%}
    
    {% macro render_regex(column, regex_pattern) %}
        {%- if lib.engine_type == 'singlestoredb' %}{{ column }} RLIKE {{ regex_pattern }}
        {%- else -%}REGEXP_LIKE({{ column }}, {{ regex_pattern }})
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(CASE
                  WHEN {{ render_regex(lib.render_target_column('analyzed_table'), render_date_formats(parameters.date_formats) ) }}
                     THEN 1
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
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
      {% macro render_date_formats(date_formats) %}
      {%- if date_formats == 'DD/MM/YYYY'-%}
      '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\d{4})$'
      {%- elif date_formats == 'DD-MM-YYYY' -%}
      '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\d{4})$'
      {%- elif date_formats == 'DD.MM.YYYY' -%}
      '^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\d{4})$'
      {%- elif date_formats == 'YYYY-MM-DD' -%}
      '^([0-9]{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])$'
      {%- endif -%}
      {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(CASE
                  WHEN REGEXP_LIKE({{ lib.render_target_column('analyzed_table') }}, {{render_date_formats(parameters.date_formats)}})
                     THEN 1
                  ELSE 0
                END
            ) / COUNT(*)
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
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "PostgreSQL"

    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'DD/MM/YYYY'-%}
            '^([0][1-9]|[1-2][0-9]|[3][0-1])/(0[1-9]|1[0-2])/([0-9]{4})$'
        {%- elif date_formats == 'DD-MM-YYYY' -%}
            '^([0][1-9]|[1-2][0-9]|[3][0-1])-(0[1-9]|1[0-2])-(\d{4})$'
        {%- elif date_formats == 'DD.MM.YYYY' -%}
            '^([0][1-9]|[1-2][0-9]|[3][0-1]).(0[1-9]|1[0-2]).(\d{4})$'
        {%- elif date_formats == 'YYYY-MM-DD' -%}
            '^(\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$'
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN CAST({{lib.render_target_column('analyzed_table')}} AS VARCHAR) ~ {{render_date_formats(parameters.date_formats)}} IS NOT NULL
                        THEN 1
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
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
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
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), {{render_date_formats(parameters.date_formats)}})
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'DD/MM/YYYY'-%}
            "^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[/](0[1-9]|1[0-2])[/](\\d{4})$"
        {%- elif date_formats == 'DD-MM-YYYY' -%}
            "^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[-](0[1-9]|1[0-2])[-](\\d{4})$"
        {%- elif date_formats == 'DD.MM.YYYY' -%}
            "^(0[1-9]|[1][0-9]|[2][0-9]|3[01])[.](0[1-9]|1[0-2])[.](\\d{4})$"
        {%- elif date_formats == 'YYYY-MM-DD' -%}
            "^(\\d{4})[-](0[1-9]|1[0-2])[-](0[1-9]|[1][0-9]|[2][0-9]|3[01])$"
        {%- endif -%}
    {% endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN REGEXP(CAST({{ lib.render_target_column('analyzed_table') }} AS STRING), {{render_date_formats(parameters.date_formats)}}) IS NOT FALSE
                        THEN 1
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
    
    {% macro render_date_formats(date_formats) %}
        {%- if date_formats == 'DD/MM/YYYY'-%}
            '^(0[1-9]|[1-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/([1-9][0-9]{3})$'
        {%- elif date_formats == 'DD-MM-YYYY' -%}
            '^(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-([1-9][0-9]{3})$'
        {%- elif date_formats == 'DD.MM.YYYY' -%}
            '^(0[1-9]|[1-2][0-9]|3[0-1])\.(0[1-9]|1[0-2])\.([1-9][0-9]{3})$'
        {%- elif date_formats == 'YYYY-MM-DD' -%}
            '^([1-9][0-9]{3})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$'
        {%- endif -%}
    {% endmacro %}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} LIKE {{render_date_formats(parameters.date_formats)}} ESCAPE '~'
                        THEN 1
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
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
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
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN REGEXP_LIKE(CAST({{ lib.render_target_column('analyzed_table') }} AS VARCHAR), {{render_date_formats(parameters.date_formats)}})
                        THEN 1
                    ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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



## date values in future percent
Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.

**Sensor summary**

The date values in future percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | datetime | <span class="no-wrap-code">`column/datetime/date_values_in_future_percent`</span> | [*sensors/column/datetime*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/datetime/) |







**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
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
                    WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
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
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
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
                    WHEN SAFE_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP
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
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                {{ render_value_in_future() }}
            ) AS DOUBLE) / COUNT(*)
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
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
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
                    WHEN CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP()
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
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
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
                    WHEN {{ lib.render_target_column('analyzed_table') }} > CURRENT_DATETIME()
                        THEN 1
                    ELSE 0
                END
        {%- else -%}
                CASE
                    WHEN TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS TIMESTAMP) > CURRENT_TIMESTAMP
                        THEN 1
                    ELSE 0
                END
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT(*) = 0 THEN 100.0
            ELSE CAST(100.0 * SUM(
                {{ render_value_in_future() }}
            ) AS DOUBLE) / COUNT(*)
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



## value in range date percent
Column level sensor that calculates the percent of non-negative values in a column.

**Sensor summary**

The value in range date percent sensor is documented below.

| Target | Category | Full sensor name | Source code on GitHub |
|--------|----------|------------------|-----------------------|
| column | datetime | <span class="no-wrap-code">`column/datetime/value_in_range_date_percent`</span> | [*sensors/column/datetime*](https://github.com/dqops/dqo/tree/develop/home/sensors/column/datetime/) |


**Sensor parameters**

| Field name | Description | Allowed data type | Required | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|<span class="no-wrap-code">`min_value`</span>|Lower bound range variable.|*date*| ||
|<span class="no-wrap-code">`max_value`</span>|Upper bound range variable.|*date*| ||






**Jinja2 SQL templates**

The templates used to generate the SQL query for each data source supported by DQOps is shown below.

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
=== "Databricks"

    ```sql+jinja
    {% import '/dialects/databricks.sql.jinja2' as lib with context -%}
    
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
=== "Oracle"

    ```sql+jinja
    {% import '/dialects/oracle.sql.jinja2' as lib with context -%}
    
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
=== "Presto"

    ```sql+jinja
    {% import '/dialects/presto.sql.jinja2' as lib with context -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        {{ lib.render_target_column('analyzed_table') }}
        {%- elif lib.is_local_time(table.columns[column_name].type_snapshot.column_type) == 'true' or lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ render_date_format_cast() }} >= CAST({{ lib.make_text_constant(parameters.min_value) }} AS TIMESTAMP) AND {{ render_date_format_cast() }} <= CAST({{ lib.make_text_constant(parameters.max_value) }} AS TIMESTAMP) THEN 1
                ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
=== "Spark"

    ```sql+jinja
    {% import '/dialects/spark.sql.jinja2' as lib with context -%}
    
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
=== "Trino"

    ```sql+jinja
    {% import '/dialects/trino.sql.jinja2' as lib with context -%}
    
    {% macro render_date_format_cast() -%}
        {%- if lib.is_local_date(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        {{ lib.render_target_column('analyzed_table') }}
        {%- elif lib.is_local_time(table.columns[column_name].type_snapshot.column_type) == 'true' or lib.is_instant(table.columns[column_name].type_snapshot.column_type) == 'true' -%}
        CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- else -%}
        TRY_CAST({{ lib.render_target_column('analyzed_table') }} AS DATE)
        {%- endif -%}
    {%- endmacro -%}
    
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN NULL
            ELSE CAST(100.0 * SUM(
                CASE
                    WHEN {{ render_date_format_cast() }} >= CAST({{ lib.make_text_constant(parameters.min_value) }} AS TIMESTAMP) AND {{ render_date_format_cast() }} <= CAST({{ lib.make_text_constant(parameters.max_value) }} AS TIMESTAMP) THEN 1
                ELSE 0
                END
            ) AS DOUBLE) / COUNT(*)
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
