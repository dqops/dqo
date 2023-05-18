
## **sql aggregated expression**
**Full sensor name**
```
column/sql/sql_aggregated_expression
```
**Description**  
Column level sensor that executes a given SQL expression on a column.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_expression|SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or withing a GROUP BY clause for daily partitions and/or data streams. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string| ||




**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "sqlserver"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        ({{ parameters.sql_expression | replace('{column}', lib.render_target_column('analyzed_table')) |
            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }}) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **sql condition failed count**
**Full sensor name**
```
column/sql/sql_condition_failed_count
```
**Description**  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string| ||




**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "sqlserver"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **sql condition failed percent**
**Full sensor name**
```
column/sql/sql_condition_failed_percent
```
**Description**  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string| ||




**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT ({{ lib.render_target_column('analyzed_table')}}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                                replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table')}})
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT ({{ lib.render_target_column('analyzed_table')}}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                                replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table')}})
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT ({{ lib.render_target_column('analyzed_table')}}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                                replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table')}})
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT ({{ lib.render_target_column('analyzed_table')}}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                                replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table')}})
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "sqlserver"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table')}}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                    AND NOT ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                                replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG({{ lib.render_target_column('analyzed_table')}})
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **sql condition passed count**
**Full sensor name**
```
column/sql/sql_condition_passed_count
```
**Description**  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string| ||




**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                        replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                        replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                        replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                        replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "sqlserver"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        SUM(
            CASE
                WHEN {{ lib.render_target_column('analyzed_table')}} IS NOT NULL
                AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                        replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                    THEN 1
                ELSE 0
            END
        ) AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___

## **sql condition passed percent**
**Full sensor name**
```
column/sql/sql_condition_passed_percent
```
**Description**  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string| ||




**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "postgresql"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
=== "sqlserver"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    SELECT
        CASE
            WHEN COUNT_BIG({{ lib.render_target_column('analyzed_table') }}) = 0 THEN 100.0
            ELSE 100.0 * SUM(
                CASE
                    WHEN {{ lib.render_target_column('analyzed_table') }} IS NOT NULL
                    AND ({{ parameters.sql_condition | replace('{column}', lib.render_target_column('analyzed_table')) |
                            replace('{table}', lib.render_target_table()) | replace('{alias}', 'analyzed_table') }})
                        THEN 1
                    ELSE 0
                END
            ) / COUNT_BIG({{ lib.render_target_column('analyzed_table') }})
        END AS actual_value
        {{- lib.render_data_stream_projections('analyzed_table') }}
        {{- lib.render_time_dimension_projection('analyzed_table') }}
    FROM {{ lib.render_target_table() }} AS analyzed_table
    {{- lib.render_where_clause() -}}
    {{- lib.render_group_by() -}}
    {{- lib.render_order_by() -}}
    ```
___
