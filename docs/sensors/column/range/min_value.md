# column/range/min_value
Column level sensor that finds the minimum value. It works on any data type that supports the MIN functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/column/range/min_value/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/range/min_value/snowflake.sql.jinja2"
    ```
