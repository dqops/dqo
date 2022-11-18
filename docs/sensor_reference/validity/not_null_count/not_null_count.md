#  Not null count
The query for this check calculates the count of not null values from column.

Successfully classified records are assigned values of 1, and any other values, 0.
Those values are then summed (so the counting of valid values is effectively performed)
___
## Jinja Template
=== "BigQuery"
    ```
    --8<-- "home/sensors/column/validity/not_null_count/bigquery.sql.jinja2"
    ```

=== "Snowflake"
    ```
    --8<-- "home/sensors/column/validity/not_null_count/bigquery.sql.jinja2"
    ```