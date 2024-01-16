# table level schema data quality checks

This is a list of schema table data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **schema**
Detects schema drifts such as columns added, removed, reordered or the data types of columns have changed.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_column_count`</span>](./column-count.md#profile-column-count)|profiling|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|*standard*|
|[<span class="no-wrap-code">`daily_column_count`</span>](./column-count.md#daily-column-count)|monitoring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.|*standard*|
|[<span class="no-wrap-code">`monthly_column_count`</span>](./column-count.md#monthly-column-count)|monitoring|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.|*standard*|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_column_count_changed`</span>](./column-count-changed.md#profile-column-count-changed)|profiling|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|*standard*|
|[<span class="no-wrap-code">`daily_column_count_changed`</span>](./column-count-changed.md#daily-column-count-changed)|monitoring|Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.|*standard*|
|[<span class="no-wrap-code">`monthly_column_count_changed`</span>](./column-count-changed.md#monthly-column-count-changed)|monitoring|Detects if the count of columns has changed since the last month. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.|*standard*|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_column_list_changed`</span>](./column-list-changed.md#profile-column-list-changed)|profiling|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|*advanced*|
|[<span class="no-wrap-code">`daily_column_list_changed`</span>](./column-list-changed.md#daily-column-list-changed)|monitoring|Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|*advanced*|
|[<span class="no-wrap-code">`monthly_column_list_changed`</span>](./column-list-changed.md#monthly-column-list-changed)|monitoring|Detects if new columns were added or existing columns were removed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|*advanced*|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_column_list_or_order_changed`</span>](./column-list-or-order-changed.md#profile-column-list-or-order-changed)|profiling|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|*advanced*|
|[<span class="no-wrap-code">`daily_column_list_or_order_changed`</span>](./column-list-or-order-changed.md#daily-column-list-or-order-changed)|monitoring|Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|*advanced*|
|[<span class="no-wrap-code">`monthly_column_list_or_order_changed`</span>](./column-list-or-order-changed.md#monthly-column-list-or-order-changed)|monitoring|Detects if new columns were added, existing columns were removed or the columns were reordered since the last month. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|*advanced*|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_column_types_changed`</span>](./column-types-changed.md#profile-column-types-changed)|profiling|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|*advanced*|
|[<span class="no-wrap-code">`daily_column_types_changed`</span>](./column-types-changed.md#daily-column-types-changed)|monitoring|Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|*advanced*|
|[<span class="no-wrap-code">`monthly_column_types_changed`</span>](./column-types-changed.md#monthly-column-types-changed)|monitoring|Detects if new columns were added, removed or their data types have changed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|*advanced*|







