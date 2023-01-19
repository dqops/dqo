# column/sql/sql_condition_failed_percent
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that do not meet the condition.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/sql/sql_condition_failed_percent/snowflake.sql.jinja2"
    ```
