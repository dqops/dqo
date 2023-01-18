# table/timeliness/data_ingestion_delay
Tabular sensor that runs a query calculating the time difference in days between the most recent transaction timestamp and the most recent data loading timestamp.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/table/timeliness/data_ingestion_delay/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/data_ingestion_delay/snowflake.sql.jinja2"
    ```
