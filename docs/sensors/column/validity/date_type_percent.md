# column/validity/date_type_percent
Column date type percent check that safe casts string type columns as date or float (UNIX time) and calculates the percent of not NULL values.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/column/validity/date_type_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/column/validity/date_type_percent/snowflake.sql.jinja2"
    ```
