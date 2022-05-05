# Average delay
The query for this check calculates the average timestamp difference between two columns provided by the user .
It performed by using SQL function:

- AVG().
- ABS().
- TIMESTAMP_DIFF() in bigquery.
- TIMESTAMPDIFF() in snowflake.

In addition, the sensor protects against operations on columns other than timestamp.
For this purpose, it uses column casting to the timestamp type.
___
## Jinja Template
=== "BigQuery"
    ```
    --8<-- "home/sensors/table/timeliness/average_delay/bigquery.sql.jinja2"
    ```

=== "Snowflake"
    ```
    --8<-- "home/sensors/table/timeliness/average_delay/snowflake.sql.jinja2"
    ```