# column/strings/string_not_match_date_regex_count
Column level sensor that calculates the number of values that does not fit to a date regex in a column.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_date_regex_count/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/strings/string_not_match_date_regex_count/snowflake.sql.jinja2"
    ```
