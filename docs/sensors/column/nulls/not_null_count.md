# column/nulls/not_null_count
Column-level sensor that calculates the number of rows with not null values.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/column/nulls/not_null_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/nulls/not_null_count/snowflake.sql.jinja2"
    ```
