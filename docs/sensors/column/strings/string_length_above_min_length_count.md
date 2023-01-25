# column/strings/string_length_above_min_length_count
Column level sensor that calculates the count of values that are not shorter than a given length in a column.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_length_above_min_length_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_length_above_min_length_count/snowflake.sql.jinja2"
    ```
