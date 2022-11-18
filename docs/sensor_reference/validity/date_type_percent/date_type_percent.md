# Date type percent

The query for this sensor calculates the percentage of records that can be interpreted as dates.
It performed by casting values as dates, parsing them with a certain date format and casting as float.


The records that can be casted or parsed successfully are assigned value of 1, and any other values, 0.
Those values are then summed (so the counting of valid values is effectively performed), divided by the number of records,
and multiplicated by a 100.0 so that the results is in percent.

___
## Jinja Template
=== "BigQuery"
    ```
    --8<-- "home/sensors/column/validity/date_type_percent/bigquery.sql.jinja2"
    ```

=== "Snowflake"
    ```
    --8<-- "home/sensors/column/validity/date_type_percent/snowflake.sql.jinja2"
    ```
