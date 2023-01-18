# table/sql/sql_condition_passed_percent
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.
___
## Jinja Template

=== "bigquery"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_passed_percent/bigquery.sql.jinja2"
    ```

=== "snowflake"
    ```
    --8<-- "home/sensors/table/sql/sql_condition_passed_percent/snowflake.sql.jinja2"
    ```
