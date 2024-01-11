# Checks/column/blanks

This is a list of blanks column data quality checks supported by DQOps and a brief description of what they do.





## **blanks**


| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_empty_text_found](./empty-text-found.md#profile-empty-text-found)|profiling|Verifies that empty strings in a column does not exceed the maximum accepted count.|standard|
|[daily_empty_text_found](./empty-text-found.md#daily-empty-text-found)|monitoring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_empty_text_found](./empty-text-found.md#monthly-empty-text-found)|monitoring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[daily_partition_empty_text_found](./empty-text-found.md#daily-partition-empty-text-found)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_empty_text_found](./empty-text-found.md#monthly-partition-empty-text-found)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_whitespace_text_found](./whitespace-text-found.md#profile-whitespace-text-found)|profiling|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.|standard|
|[daily_whitespace_text_found](./whitespace-text-found.md#daily-whitespace-text-found)|monitoring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_whitespace_text_found](./whitespace-text-found.md#monthly-whitespace-text-found)|monitoring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[daily_partition_whitespace_text_found](./whitespace-text-found.md#daily-partition-whitespace-text-found)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_whitespace_text_found](./whitespace-text-found.md#monthly-partition-whitespace-text-found)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_null_placeholder_text_found](./null-placeholder-text-found.md#profile-null-placeholder-text-found)|profiling|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.|standard|
|[daily_null_placeholder_text_found](./null-placeholder-text-found.md#daily-null-placeholder-text-found)|monitoring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_null_placeholder_text_found](./null-placeholder-text-found.md#monthly-null-placeholder-text-found)|monitoring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[daily_partition_null_placeholder_text_found](./null-placeholder-text-found.md#daily-partition-null-placeholder-text-found)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|standard|
|[monthly_partition_null_placeholder_text_found](./null-placeholder-text-found.md#monthly-partition-null-placeholder-text-found)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|standard|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_empty_text_percent](./empty-text-percent.md#profile-empty-text-percent)|profiling|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.|advanced|
|[daily_empty_text_percent](./empty-text-percent.md#daily-empty-text-percent)|monitoring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_empty_text_percent](./empty-text-percent.md#monthly-empty-text-percent)|monitoring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[daily_partition_empty_text_percent](./empty-text-percent.md#daily-partition-empty-text-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_empty_text_percent](./empty-text-percent.md#monthly-partition-empty-text-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_whitespace_text_percent](./whitespace-text-percent.md#profile-whitespace-text-percent)|profiling|Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.|advanced|
|[daily_whitespace_text_percent](./whitespace-text-percent.md#daily-whitespace-text-percent)|monitoring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_whitespace_text_percent](./whitespace-text-percent.md#monthly-whitespace-text-percent)|monitoring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[daily_partition_whitespace_text_percent](./whitespace-text-percent.md#daily-partition-whitespace-text-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_whitespace_text_percent](./whitespace-text-percent.md#monthly-partition-whitespace-text-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|



| Check name | Check type | Description | Class |
|------------|------------|-------------|-------|
|[profile_null_placeholder_text_percent](./null-placeholder-text-percent.md#profile-null-placeholder-text-percent)|profiling|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.|advanced|
|[daily_null_placeholder_text_percent](./null-placeholder-text-percent.md#daily-null-placeholder-text-percent)|monitoring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_null_placeholder_text_percent](./null-placeholder-text-percent.md#monthly-null-placeholder-text-percent)|monitoring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[daily_partition_null_placeholder_text_percent](./null-placeholder-text-percent.md#daily-partition-null-placeholder-text-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|advanced|
|[monthly_partition_null_placeholder_text_percent](./null-placeholder-text-percent.md#monthly-partition-null-placeholder-text-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|advanced|







