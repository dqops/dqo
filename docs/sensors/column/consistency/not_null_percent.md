# column/consistency/not_null_percent
Column level sensor that calculates the count of not null values in a column.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/column/consistency/not_null_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/consistency/not_null_percent/snowflake.sql.jinja2"
    ```
