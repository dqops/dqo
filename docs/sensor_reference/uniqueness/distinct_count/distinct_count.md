# Distinct count
The query for this check counts unique values in a specified column.

Successfully classified records are assigned value of 1, and any other values, 0.
Those values are then summed (so the counting of valid values is effectively performed).
___
## Jinja Template
=== "BigQuery"
    ```
    --8<-- "home/sensors/column/uniqueness/distinct_count/bigquery.sql.jinja2"
    ```

=== "Snowflake"
    ```
    --8<-- "home/sensors/column/uniqueness/distinct_count/snowflake.sql.jinja2"
    ```