# column/strings/string_invalid_ip4_address_count
Column level sensor that calculates the number of rows with an invalid IP4 address value in a column.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip4_address_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_invalid_ip4_address_count/snowflake.sql.jinja2"
    ```
