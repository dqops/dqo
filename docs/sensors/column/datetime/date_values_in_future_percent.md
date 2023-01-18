# column/datetime/date_values_in_future_percent
Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/column/datetime/date_values_in_future_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/datetime/date_values_in_future_percent/snowflake.sql.jinja2"
    ```
