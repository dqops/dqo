# table/timeliness/days_since_most_recent_event
Tabular sensor that runs a query calculating maximum days since the most recent event.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/table/timeliness/days_since_most_recent_event/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/days_since_most_recent_event/snowflake.sql.jinja2"
    ```
