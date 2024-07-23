---
title: table level schema data quality checks
---
# table level schema data quality checks

This is a list of schema table data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## table-level schema checks
Detects schema drifts such as columns added, removed, reordered or the data types of columns have changed.

### [column count](./column-count.md)
A table-level check that retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_count`</span>](./column-count.md#profile-column-count)|Expected column count|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|:material-check-bold:|
|[<span class="no-wrap-code">`daily_column_count`</span>](./column-count.md#daily-column-count)|Expected column count|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_column_count`</span>](./column-count.md#monthly-column-count)|Expected column count|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns). Stores the most recent column count for each month when the data quality check was evaluated.|:material-check-bold:|



### [column count changed](./column-count-changed.md)
A table-level check that detects if the number of columns in the table has changed since the last time the check (checkpoint) was run.
 This check retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to the last known number of columns
 that was captured and is stored in the data quality check results database.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_count_changed`</span>](./column-count-changed.md#profile-column-count-changed)|Detect change of column count|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_column_count_changed`</span>](./column-count-changed.md#daily-column-count-changed)|Detect change of column count|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if the count of columns has changed since the most recent day. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_column_count_changed`</span>](./column-count-changed.md#monthly-column-count-changed)|Detect change of column count|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if the count of columns has changed since the last month. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time. Stores the most recent column count for each month when the data quality check was evaluated.|:material-check-bold:|



### [column list changed](./column-list-changed.md)
A table-level check that detects if the list of columns has changed since the last time the check was run.
 This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will not depend on the order of columns, only on the column names.
 A data quality issue will be detected if new columns were added or columns that existed during the previous test were dropped.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_list_changed`</span>](./column-list-changed.md#profile-column-list-changed)|Detect if columns were added or removed|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.| |
|[<span class="no-wrap-code">`daily_column_list_changed`</span>](./column-list-changed.md#daily-column-list-changed)|Detect if columns were added or removed|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if new columns were added or existing columns were removed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.| |
|[<span class="no-wrap-code">`monthly_column_list_changed`</span>](./column-list-changed.md#monthly-column-list-changed)|Detect if columns were added or removed|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if new columns were added or existing columns were removed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.| |



### [column list or order changed](./column-list-or-order-changed.md)
A table-level check that detects if the list of columns and the order of columns have changed since the last time the check was run.
 This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will depend on the order of columns.
 A data quality issue will be detected if new columns were added, columns that existed during the previous test were dropped or the columns were reordered.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_list_or_order_changed`</span>](./column-list-or-order-changed.md#profile-column-list-or-order-changed)|Detect if column list or order has changed|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.| |
|[<span class="no-wrap-code">`daily_column_list_or_order_changed`</span>](./column-list-or-order-changed.md#daily-column-list-or-order-changed)|Detect if column list or order has changed|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if new columns were added, existing columns were removed or the columns were reordered since the most recent day. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.| |
|[<span class="no-wrap-code">`monthly_column_list_or_order_changed`</span>](./column-list-or-order-changed.md#monthly-column-list-or-order-changed)|Detect if column list or order has changed|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if new columns were added, existing columns were removed or the columns were reordered since the last month. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.| |



### [column types changed](./column-types-changed.md)
A table-level check that detects if the column names or column types have changed since the last time the check was run.
 This check calculates a hash of the column names and all the components of the column&#x27;s data type: the data type name, length, scale, precision and nullability.
 A data quality issue will be detected if the hash of the column data types has changed. This check does not depend on the order of columns, the columns can be reordered as long
 as all columns are still present and the data types match since the last time they were tested.


| Data quality check name | Friendly name | Check type | Description | Standard |
|-------------------------|---------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_column_types_changed`</span>](./column-types-changed.md#profile-column-types-changed)|Detect if column list or data type has changed|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.| |
|[<span class="no-wrap-code">`daily_column_types_changed`</span>](./column-types-changed.md#daily-column-types-changed)|Detect if column list or data type has changed|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if new columns were added, removed or their data types have changed since the most recent day. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.| |
|[<span class="no-wrap-code">`monthly_column_types_changed`</span>](./column-types-changed.md#monthly-column-types-changed)|Detect if column list or data type has changed|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects if new columns were added, removed or their data types have changed since the last month. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.| |







