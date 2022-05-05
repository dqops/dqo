The query for this check calculates the percent of date values that are within a range provided by the user.
The `value_in_range_date_percent` checks values from column which are within a range of `min_value` and `max_value`.
The user decides whether to include these values to the range, using optional parameters `include_min_value` and `include_max_value`.
Default in check `min_value` and `max_value`  are included in the range. 

Successfully classified records are assigned value of 1, and any other values, 0.
Those values are then summed (so effectively we perform count of valid values), divided by the number of records,
and multiplicated by a 100.0 so that the results is in percent.

___
## Jinja Template
=== "BigQuery"
    ```
    --8<-- "home/sensors/column/validity/value_in_range_date_percent/bigquery.sql.jinja2"
    ```
=== "Snowflake"
    ```
    --8<-- "home/sensors/column/validity/value_in_range_date_percent/snowflake.sql.jinja2"
    ```