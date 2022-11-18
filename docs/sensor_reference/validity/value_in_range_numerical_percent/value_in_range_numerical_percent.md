# Value in range numerical percent

The query for this check calculates the percent of numerical values that are within a range provided by the user.
The `value_in_range_numerical_percent` checks values from column which are within a range of `min_value` and `max_value`.
Decide whether to include these values to the range, using optional parameters `include_min_value`
and `include_max_value`. Default in check `min_value` and `max_value` are included in the range.

Successfully classified records are assigned values of 1, and any other values, 0.  Those values are then summed
(so the counting of valid values is effectively performed), divided by the number of records, and multiplicated by a
100.0 so that the result is in percent.

___
## Jinja Template
=== "BigQuery"
    ```
    --8<-- "home/sensors/column/validity/value_in_range_numerical_percent/bigquery.sql.jinja2"
    ```
=== "Snowflake"
    ```
    --8<-- "home/sensors/column/validity/value_in_range_numerical_percent/snowflake.sql.jinja2"
    ```