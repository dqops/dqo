# column/uniqueness/duplicate_count
Column level sensor that calculates the number of rows with a null column value.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/uniqueness/duplicate_count/snowflake.sql.jinja2"
    ```
