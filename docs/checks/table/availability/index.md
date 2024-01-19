# table level availability data quality checks

This is a list of availability table data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **availability**
Checks whether the table is accessible and available for use.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_table_availability`</span>](./table-availability.md#profile-table-availability)|profiling|Verifies availability of a table in a monitored database using a simple query.|**|
|[<span class="no-wrap-code">`daily_table_availability`</span>](./table-availability.md#daily-table-availability)|monitoring|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_table_availability`</span>](./table-availability.md#monthly-table-availability)|monitoring|Verifies availability of a table in a monitored database using a simple query. Stores the most recent table availability status for each month when the data quality check was evaluated.|**|







