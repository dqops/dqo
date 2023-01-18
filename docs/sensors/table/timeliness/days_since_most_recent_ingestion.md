# table/timeliness/days_since_most_recent_ingestion
Tabular sensor that runs a query calculating the time difference in days between the current date and most recent data loading timestamp (staleness).
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/table/timeliness/days_since_most_recent_ingestion/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/days_since_most_recent_ingestion/snowflake.sql.jinja2"
    ```
