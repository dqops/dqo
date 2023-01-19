# table/timeliness/partition_reload_lag
Tabular sensor that runs a query calculating maximum difference in days between ingestion timestamp and event timestamp rows.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/table/timeliness/partition_reload_lag/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/partition_reload_lag/snowflake.sql.jinja2"
    ```
