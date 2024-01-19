# column level schema data quality checks

This is a list of schema column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **schema**
Detects schema drifts such as a column is missing or the data type has changed.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_column_exists`</span>](./column-exists.md#profile-column-exists)|profiling|Checks the metadata of the monitored table and verifies if the column exists.|**|
|[<span class="no-wrap-code">`daily_column_exists`</span>](./column-exists.md#daily-column-exists)|monitoring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_column_exists`</span>](./column-exists.md#monthly-column-exists)|monitoring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.|**|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_column_type_changed`</span>](./column-type-changed.md#profile-column-type-changed)|profiling|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed.|**|
|[<span class="no-wrap-code">`daily_column_type_changed`</span>](./column-type-changed.md#daily-column-type-changed)|monitoring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last day. Stores the most recent hash for each day when the data quality check was evaluated.|**|
|[<span class="no-wrap-code">`monthly_column_type_changed`</span>](./column-type-changed.md#monthly-column-type-changed)|monitoring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.|**|







