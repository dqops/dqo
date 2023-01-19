# column/strings/string_invalid_email_count
Column level sensor that calculates the number of rows with an invalid emails value in a column.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_email_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_email_count/snowflake.sql.jinja2"
    ```
