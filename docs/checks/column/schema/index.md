# Checks/column/schema

**This is a list of schema column checks in DQOps and a brief description of what they do.**





## **schema**  
Detects schema drifts such as a column is missing or the data type has changed.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_exists](./column/schema/column-exists/#profile-column-exists)|profiling|Checks the metadata of the monitored table and verifies if the column exists.|
|[daily_column_exists](./column/schema/column-exists/#daily-column-exists)|monitoring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.|
|[monthly_column_exists](./column/schema/column-exists/#monthly-column-exists)|monitoring|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_type_changed](./column/schema/column-type-changed/#profile-column-type-changed)|profiling|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed.|
|[daily_column_type_changed](./column/schema/column-type-changed/#daily-column-type-changed)|monitoring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last day. Stores the most recent hash for each day when the data quality check was evaluated.|
|[monthly_column_type_changed](./column/schema/column-type-changed/#monthly-column-type-changed)|monitoring|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.|





