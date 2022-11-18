# String length in range percent

The query for this check calculates the percent of string values whose length is within a certain range.
It is performed by using SQL function `LENGTH()`, which returns the length of the value passed.
The query then checks if the value is between `min_length` and `max_length`.

Furthermore, when specifying a different data type then `STRING`, sensor will cast the column to `STRING`.
 
Successfully classified records are assigned values of 1, and any other values, 0.
Those values are then summed (so the counting of valid values is effectively performed), divided by the number of records,
and multiplicated by a 100.0 so that the result is in percent.

___
## Jinja Template
=== "BigQuery"
    ```
    --8<-- "home/sensors/column/validity/string_length_in_range_percent/bigquery.sql.jinja2"
    ```

=== "Snowflake"
    ```
    --8<-- "home/sensors/column/validity/string_length_in_range_percent/snowflake.sql.jinja2"
    ```