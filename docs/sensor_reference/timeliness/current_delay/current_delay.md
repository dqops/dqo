# Current delay
The current_delay sensor calculates the timestamp difference between last record (the newest one appointed by MAX() function) and a CURRENT_TIMESTAMP().

___
## Jinja Template
=== "BigQuery"
    ```
    --8<-- "home/sensors/table/timeliness/current_delay/bigquery.sql.jinja2"
    ```

=== "Snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/current_delay/snowflake.sql.jinja2"
    ```