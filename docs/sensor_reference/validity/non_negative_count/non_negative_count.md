#  Non negative count
The query for this check calculates the count of non negative values from column.

Successfully classified records are assigned value of 1, and any other values, 0.
Those values are then summed (so effectively we perform count of valid values).
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