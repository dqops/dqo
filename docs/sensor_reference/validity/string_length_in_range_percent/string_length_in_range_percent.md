The query for this check calculates the percent of string values whose length is within a certain range provided by the user.
It performed by using SQL function LENGTH(), which returns the length of the value passed.
The query then checks if the value is between `min_length` and `max_length`. These values are provided by user.

Furthermore, when user specifying a different data type then `STRING`, sensor will cast the column to `STRING`.
 
Successfully classified records are assigned value of 1, and any other values, 0.
Those values are then summed (so effectively we perform count of valid values), divided by the number of records,
and multiplicated by a 100.0 so that the results is in percent.

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