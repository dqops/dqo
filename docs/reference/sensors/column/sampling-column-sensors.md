
## **column samples**
**Full sensor name**
```
column/sampling/column_samples
```
**Description**  
Column level sensor that retrieves a column value samples. Column value sampling is used in profiling and in capturing error samples for failed data quality checks.

**Parameters**  
  
| Field name | Description | Allowed data type | Is it required? | Allowed values |
|------------|-------------|-------------------|-----------------|----------------|
|limit|The limit of results that are returned. The default value is 10 sample values with the highest count (the most popular).|integer| ||




**SQL Template (Jinja2)**  
=== "bigquery"
      
    ```sql+jinja
    {% import '/dialects/bigquery.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            GROUP BY sample_value
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "mysql"
      
    ```sql+jinja
    {% import '/dialects/mysql.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            GROUP BY sample_value
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "postgresql"
      
    ```sql+jinja
    {% import '/dialects/postgresql.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            GROUP BY sample_value
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "redshift"
      
    ```sql+jinja
    {% import '/dialects/redshift.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            GROUP BY sample_value
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "snowflake"
      
    ```sql+jinja
    {% import '/dialects/snowflake.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            GROUP BY sample_value
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
=== "sqlserver"
      
    ```sql+jinja
    {% import '/dialects/sqlserver.sql.jinja2' as lib with context -%}
    WITH column_samples AS (
        SELECT
            unlimited_samples.sample_value AS sample_value,
            unlimited_samples.sample_count AS sample_count,
            ROW_NUMBER() OVER (ORDER BY unlimited_samples.sample_count DESC) AS sample_index
        FROM
        (
            SELECT
                {{ lib.render_target_column('analyzed_table') }} AS sample_value,
                COUNT(*) AS sample_count
            FROM
                {{ lib.render_target_table() }} AS analyzed_table
            GROUP BY {{ lib.render_target_column('analyzed_table') }}
            {{- lib.render_where_clause(table_alias_prefix = 'analyzed_table', indentation = '        ') }}
        ) AS unlimited_samples
    )
    SELECT
       sample_table.sample_value AS actual_value,
       sample_table.sample_count AS sample_count,
       sample_table.sample_index AS sample_index
    FROM column_samples AS sample_table
    WHERE sample_table.sample_index <= {{ parameters.limit }}
    ORDER BY sample_index DESC
    ```
___
