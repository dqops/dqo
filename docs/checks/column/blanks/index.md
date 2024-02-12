# column level blanks data quality checks

This is a list of blanks column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level blanks checks
Detects text columns that contain blank values, or values that are used as placeholders for missing values: &#x27;n/a&#x27;, &#x27;None&#x27;, etc.

### [empty text found](./empty-text-found.md)
A column-level check that ensures that there are no more than a maximum number of empty texts in a monitored column.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_empty_text_found`</span>](./empty-text-found.md#profile-empty-text-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that empty strings in a column does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_empty_text_found`</span>](./empty-text-found.md#daily-empty-text-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_empty_text_found`</span>](./empty-text-found.md#monthly-empty-text-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_empty_text_found`</span>](./empty-text-found.md#daily-partition-empty-text-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_empty_text_found`</span>](./empty-text-found.md#monthly-partition-empty-text-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [whitespace text found](./whitespace-text-found.md)
A column-level check that ensures that there are no more than a maximum number of whitespace texts in a monitored column.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_whitespace_text_found`</span>](./whitespace-text-found.md#profile-whitespace-text-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_whitespace_text_found`</span>](./whitespace-text-found.md#daily-whitespace-text-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_whitespace_text_found`</span>](./whitespace-text-found.md#monthly-whitespace-text-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_whitespace_text_found`</span>](./whitespace-text-found.md#daily-partition-whitespace-text-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_whitespace_text_found`</span>](./whitespace-text-found.md#monthly-partition-whitespace-text-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [null placeholder text found](./null-placeholder-text-found.md)
A column-level check that ensures that there are no more than a maximum number of rows with a null placeholder text in a monitored column.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_null_placeholder_text_found`</span>](./null-placeholder-text-found.md#profile-null-placeholder-text-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_null_placeholder_text_found`</span>](./null-placeholder-text-found.md#daily-null-placeholder-text-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_null_placeholder_text_found`</span>](./null-placeholder-text-found.md#monthly-null-placeholder-text-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_null_placeholder_text_found`</span>](./null-placeholder-text-found.md#daily-partition-null-placeholder-text-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_null_placeholder_text_found`</span>](./null-placeholder-text-found.md#monthly-partition-null-placeholder-text-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [empty text percent](./empty-text-percent.md)
A column-level check that ensures that there are no more than a maximum percent of empty texts in a monitored column.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_empty_text_percent`</span>](./empty-text-percent.md#profile-empty-text-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.| |
|[<span class="no-wrap-code">`daily_empty_text_percent`</span>](./empty-text-percent.md#daily-empty-text-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_empty_text_percent`</span>](./empty-text-percent.md#monthly-empty-text-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_empty_text_percent`</span>](./empty-text-percent.md#daily-partition-empty-text-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_empty_text_percent`</span>](./empty-text-percent.md#monthly-partition-empty-text-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.| |



### [whitespace text percent](./whitespace-text-percent.md)
A column-level check that ensures that there are no more than a maximum percent of whitespace texts in a monitored column.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_whitespace_text_percent`</span>](./whitespace-text-percent.md#profile-whitespace-text-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.| |
|[<span class="no-wrap-code">`daily_whitespace_text_percent`</span>](./whitespace-text-percent.md#daily-whitespace-text-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_whitespace_text_percent`</span>](./whitespace-text-percent.md#monthly-whitespace-text-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_whitespace_text_percent`</span>](./whitespace-text-percent.md#daily-partition-whitespace-text-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_whitespace_text_percent`</span>](./whitespace-text-percent.md#monthly-partition-whitespace-text-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each monthly partition.| |



### [null placeholder text percent](./null-placeholder-text-percent.md)
A column-level check that ensures that there are no more than a maximum percent of rows with a null placeholder text in a monitored column.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_null_placeholder_text_percent`</span>](./null-placeholder-text-percent.md#profile-null-placeholder-text-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.| |
|[<span class="no-wrap-code">`daily_null_placeholder_text_percent`</span>](./null-placeholder-text-percent.md#daily-null-placeholder-text-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_null_placeholder_text_percent`</span>](./null-placeholder-text-percent.md#monthly-null-placeholder-text-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_null_placeholder_text_percent`</span>](./null-placeholder-text-percent.md#daily-partition-null-placeholder-text-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_null_placeholder_text_percent`</span>](./null-placeholder-text-percent.md#monthly-partition-null-placeholder-text-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.| |



### [text surrounded by whitespace](./text-surrounded-by-whitespace.md)
A column-level check that ensures that there are no more than a maximum number of text values that are surrounded by whitespace in a monitored column.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_surrounded_by_whitespace`</span>](./text-surrounded-by-whitespace.md#profile-text-surrounded-by-whitespace)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table| |
|[<span class="no-wrap-code">`daily_text_surrounded_by_whitespace`</span>](./text-surrounded-by-whitespace.md#daily-text-surrounded-by-whitespace)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_surrounded_by_whitespace`</span>](./text-surrounded-by-whitespace.md#monthly-text-surrounded-by-whitespace)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_surrounded_by_whitespace`</span>](./text-surrounded-by-whitespace.md#daily-partition-text-surrounded-by-whitespace)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_surrounded_by_whitespace`</span>](./text-surrounded-by-whitespace.md#monthly-partition-text-surrounded-by-whitespace)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text surrounded by whitespace percent](./text-surrounded-by-whitespace-percent.md)
A column-level check that ensures that there are no more than a maximum percentage of text values that are surrounded by whitespace in a monitored column.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_surrounded_by_whitespace_percent`</span>](./text-surrounded-by-whitespace-percent.md#profile-text-surrounded-by-whitespace-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage| |
|[<span class="no-wrap-code">`daily_text_surrounded_by_whitespace_percent`</span>](./text-surrounded-by-whitespace-percent.md#daily-text-surrounded-by-whitespace-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_surrounded_by_whitespace_percent`</span>](./text-surrounded-by-whitespace-percent.md#monthly-text-surrounded-by-whitespace-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_surrounded_by_whitespace_percent`</span>](./text-surrounded-by-whitespace-percent.md#daily-partition-text-surrounded-by-whitespace-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_surrounded_by_whitespace_percent`</span>](./text-surrounded-by-whitespace-percent.md#monthly-partition-text-surrounded-by-whitespace-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |







