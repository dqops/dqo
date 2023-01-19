# column/numeric/values_in_range_numeric_percent
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_numeric_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/numeric/values_in_range_numeric_percent/snowflake.sql.jinja2"
    ```
