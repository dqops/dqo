# Checks/table/schema

**This is a list of schema table data quality checks supported by DQOps and a brief description of what they do.**





## **schema**  
Detects schema drifts such as columns added, removed, reordered or the data types of columns have changed.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_count](column-count/#profile-column-count)|profiling|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|
|[daily_column_count](column-count/#daily-column-count)|monitoring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.|
|[monthly_column_count](column-count/#monthly-column-count)|monitoring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_count_changed](column-count-changed/#profile-column-count-changed)|profiling|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|
|[daily_column_count_changed](column-count-changed/#daily-column-count-changed)|monitoring|Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.|
|[monthly_column_count_changed](column-count-changed/#monthly-column-count-changed)|monitoring|Detects if the count of columns has changed since the last month. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_list_changed](column-list-changed/#profile-column-list-changed)|profiling|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|
|[daily_column_list_changed](column-list-changed/#daily-column-list-changed)|monitoring|Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|
|[monthly_column_list_changed](column-list-changed/#monthly-column-list-changed)|monitoring|Detects if new columns were added or existing columns were removed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_list_or_order_changed](column-list-or-order-changed/#profile-column-list-or-order-changed)|profiling|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|
|[daily_column_list_or_order_changed](column-list-or-order-changed/#daily-column-list-or-order-changed)|monitoring|Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|
|[monthly_column_list_or_order_changed](column-list-or-order-changed/#monthly-column-list-or-order-changed)|monitoring|Detects if new columns were added, existing columns were removed or the columns were reordered since the last month. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_column_types_changed](column-types-changed/#profile-column-types-changed)|profiling|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|
|[daily_column_types_changed](column-types-changed/#daily-column-types-changed)|monitoring|Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|
|[monthly_column_types_changed](column-types-changed/#monthly-column-types-changed)|monitoring|Detects if new columns were added, removed or their data types have changed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|





