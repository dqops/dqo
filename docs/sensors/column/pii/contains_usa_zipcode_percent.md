# column/pii/contains_usa_zipcode_percent
Column level sensor that calculates the percent of values that contain a USA zip code number in a column.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_zipcode_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/pii/contains_usa_zipcode_percent/snowflake.sql.jinja2"
    ```
