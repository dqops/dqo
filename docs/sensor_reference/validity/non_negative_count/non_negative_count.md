#  Non-negative count
The query for this check calculates the count of non-negative values from column.

Successfully classified records are assigned values of 1, and any other values, 0.
Those values are then summed (so the counting of valid values is effectively performed).
___
## Jinja Template
=== "BigQuery"
    ```
    --8<-- "home/sensors/column/validity/non_negative_count/bigquery.sql.jinja2"
    ```

=== "Snowflake"
    ```
    --8<-- "home/sensors/column/validity/non_negative_count/bigquery.sql.jinja2"
    ```