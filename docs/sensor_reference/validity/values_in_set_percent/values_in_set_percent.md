# Values in set percent

The query for this sensor calculates the percentage of records matching with provided list of values from user.
It performed by using SQL function IN(). This function takes value/values of the same type, then checks the values from table is match to provided values.
If types of given values don't match to the `values_type`, sensor will convert this values to the correct type. 
For e.g. `values_type: STRING` and `values_list: [1,2,3]`, after converting function IN takes ('1', '2', '3'), not (1, 2, 3).

In the case, when `values_type` and type of values in the list is the same, function IN takes same values from list without converting.
The special case is when we declare `DATE` values, because sensor will cast this values to `DATE`, even then type is correct. 
This works that to avoid problem with `DATE` types in databases.

In the case, when `values_list` length is equal to zero (it's mean empty list), function IN takes `NULL` as parameter.

Furthermore, when specifying a different data type than the represented by the column, the sensor tries to cast column
values to a specified type.

Successfully matched records are assigned values of 1, and any other values, 0. Those values are then summed
(so the counting of valid values is effectively performed), divided by the number of records, and multiplicated by a
100.0 so that the result is in percent.

!!! Warning
    Running this check defining the wrong data type for the column might result with an error.
    This is because casting some types to another is impossible, for e.g. `DATE` to `NUMERIC`. Therefore, it is
    recommended to use this query on STRING types, where such errors do not occur.

___
## Jinja Template
=== "BigQuery"
    ```
    --8<-- "home/sensors/column/validity/values_in_set_percent/bigquery.sql.jinja2"
    ```

=== "Snowflake"
    ```
    --8<-- "home/sensors/column/validity/values_in_set_percent/snowflake.sql.jinja2"
    ```