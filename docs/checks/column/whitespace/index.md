# column level whitespace data quality checks

This is a list of whitespace column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level whitespace checks
Detects text columns that contain blank values, or values that are used as placeholders for missing values: &#x27;n/a&#x27;, &#x27;None&#x27;, etc.

### [empty text found](./empty-text-found.md)
This check detects empty texts that are not null. Empty texts have a length of zero.
 The database treats them as values different than nulls, and some databases allow the storage of both null and empty values.
 This check counts empty texts and raises a data quality issue when the number of empty values exceeds a *max_count* parameter value.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_empty_text_found`</span>](./empty-text-found.md#profile-empty-text-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects empty texts (not null, zero-length texts). This check counts empty and raises a data quality issue when their count exceeds a *max_count* parameter value.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_empty_text_found`</span>](./empty-text-found.md#daily-empty-text-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects empty texts (not null, zero-length texts). This check counts empty and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_empty_text_found`</span>](./empty-text-found.md#monthly-empty-text-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects empty texts (not null, zero-length texts). This check counts empty and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_empty_text_found`</span>](./empty-text-found.md#daily-partition-empty-text-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects empty texts (not null, zero-length texts). This check counts empty and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_empty_text_found`</span>](./empty-text-found.md#monthly-partition-empty-text-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects empty texts (not null, zero-length texts). This check counts empty and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [whitespace text found](./whitespace-text-found.md)
This check detects empty texts containing only spaces and other whitespace characters.
 This check counts whitespace-only texts and raises a data quality issue when their count exceeds a *max_count* parameter value.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_whitespace_text_found`</span>](./whitespace-text-found.md#profile-whitespace-text-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects texts that contain only spaces and other whitespace characters. It raises a data quality issue when their count exceeds a *max_count* parameter value.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_whitespace_text_found`</span>](./whitespace-text-found.md#daily-whitespace-text-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects texts that contain only spaces and other whitespace characters. It raises a data quality issue when their count exceeds a *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_whitespace_text_found`</span>](./whitespace-text-found.md#monthly-whitespace-text-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects texts that contain only spaces and other whitespace characters. It raises a data quality issue when their count exceeds a *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_whitespace_text_found`</span>](./whitespace-text-found.md#daily-partition-whitespace-text-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects texts that contain only spaces and other whitespace characters. It raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_whitespace_text_found`</span>](./whitespace-text-found.md#monthly-partition-whitespace-text-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects texts that contain only spaces and other whitespace characters. It raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [null placeholder text found](./null-placeholder-text-found.md)
This check detects text values that are well-known equivalents (placeholders) of a null value, such as *null*, *None*, *n/a*.
 This check counts null placeholder values and raises a data quality issue when their count exceeds a *max_count* parameter value.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_null_placeholder_text_found`</span>](./null-placeholder-text-found.md#profile-null-placeholder-text-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*. It counts null placeholders and raises a data quality issue when their count exceeds a *max_count* parameter value.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_null_placeholder_text_found`</span>](./null-placeholder-text-found.md#daily-null-placeholder-text-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*. It counts null placeholders and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_null_placeholder_text_found`</span>](./null-placeholder-text-found.md#monthly-null-placeholder-text-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*. It counts null placeholders and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_null_placeholder_text_found`</span>](./null-placeholder-text-found.md#daily-partition-null-placeholder-text-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*. It counts null placeholders and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_null_placeholder_text_found`</span>](./null-placeholder-text-found.md#monthly-partition-null-placeholder-text-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*. It counts null placeholders and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [empty text percent](./empty-text-percent.md)
This check detects empty texts that are not null. Empty texts have a length of zero.
 This check measures the percentage of empty texts and raises a data quality issue when the rate of empty values exceeds a *max_percent* parameter value.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_empty_text_percent`</span>](./empty-text-percent.md#profile-empty-text-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects empty texts (not null, zero-length texts) and measures their percentage in the column. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage.| |
|[<span class="no-wrap-code">`daily_empty_text_percent`</span>](./empty-text-percent.md#daily-empty-text-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects empty texts (not null, zero-length texts) and measures their percentage in the column. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_empty_text_percent`</span>](./empty-text-percent.md#monthly-empty-text-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects empty texts (not null, zero-length texts) and measures their percentage in the column. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_empty_text_percent`</span>](./empty-text-percent.md#daily-partition-empty-text-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects empty texts (not null, zero-length texts) and measures their percentage in the column. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_empty_text_percent`</span>](./empty-text-percent.md#monthly-partition-empty-text-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects empty texts (not null, zero-length texts) and measures their percentage in the column. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.| |



### [whitespace text percent](./whitespace-text-percent.md)
This check detects empty texts containing only spaces and other whitespace characters.
 This check measures the percentage of whitespace-only texts and raises a data quality issue when their rate exceeds a *max_percent* parameter value.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_whitespace_text_percent`</span>](./whitespace-text-percent.md#profile-whitespace-text-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects texts that contain only spaces and other whitespace characters and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value.| |
|[<span class="no-wrap-code">`daily_whitespace_text_percent`</span>](./whitespace-text-percent.md#daily-whitespace-text-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects texts that contain only spaces and other whitespace characters and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_whitespace_text_percent`</span>](./whitespace-text-percent.md#monthly-whitespace-text-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects texts that contain only spaces and other whitespace characters and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_whitespace_text_percent`</span>](./whitespace-text-percent.md#daily-partition-whitespace-text-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects texts that contain only spaces and other whitespace characters and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_whitespace_text_percent`</span>](./whitespace-text-percent.md#monthly-partition-whitespace-text-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects texts that contain only spaces and other whitespace characters and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores a separate data quality check result for each monthly partition.| |



### [null placeholder text percent](./null-placeholder-text-percent.md)
This check detects text values that are well-known equivalents (placeholders) of a null value, such as *null*, *None*, *n/a*.
 This check measures the percentage of null placeholder values and raises a data quality issue when their rate exceeds a *max_percent* parameter value.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_null_placeholder_text_percent`</span>](./null-placeholder-text-percent.md#profile-null-placeholder-text-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*, and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value.| |
|[<span class="no-wrap-code">`daily_null_placeholder_text_percent`</span>](./null-placeholder-text-percent.md#daily-null-placeholder-text-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*, and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_null_placeholder_text_percent`</span>](./null-placeholder-text-percent.md#monthly-null-placeholder-text-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*, and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_null_placeholder_text_percent`</span>](./null-placeholder-text-percent.md#daily-partition-null-placeholder-text-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*, and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_null_placeholder_text_percent`</span>](./null-placeholder-text-percent.md#monthly-partition-null-placeholder-text-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*, and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores a separate data quality check result for each monthly partition.| |



### [text surrounded by whitespace found](./text-surrounded-by-whitespace-found.md)
This check detects text values that contain additional whitespace characters before or after the text.
 This check counts text values surrounded by whitespace characters (on any side) and
 raises a data quality issue when their count exceeds a *max_count* parameter value.
 Whitespace-surrounded texts should be trimmed before loading to another table.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_surrounded_by_whitespace_found`</span>](./text-surrounded-by-whitespace-found.md#profile-text-surrounded-by-whitespace-found)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Detects text values that are surrounded by whitespace characters on any side. This check counts whitespace-surrounded texts and raises a data quality issue when their count exceeds the *max_count* parameter value.| |
|[<span class="no-wrap-code">`daily_text_surrounded_by_whitespace_found`</span>](./text-surrounded-by-whitespace-found.md#daily-text-surrounded-by-whitespace-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects text values that are surrounded by whitespace characters on any side. This check counts whitespace-surrounded texts and raises a data quality issue when their count exceeds the *max_count* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_surrounded_by_whitespace_found`</span>](./text-surrounded-by-whitespace-found.md#monthly-text-surrounded-by-whitespace-found)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Detects text values that are surrounded by whitespace characters on any side. This check counts whitespace-surrounded texts and raises a data quality issue when their count exceeds the *max_count* parameter value. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_surrounded_by_whitespace_found`</span>](./text-surrounded-by-whitespace-found.md#daily-partition-text-surrounded-by-whitespace-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects text values that are surrounded by whitespace characters on any side. This check counts whitespace-surrounded texts and raises a data quality issue when their count exceeds the *max_count* parameter value. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_surrounded_by_whitespace_found`</span>](./text-surrounded-by-whitespace-found.md#monthly-partition-text-surrounded-by-whitespace-found)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Detects text values that are surrounded by whitespace characters on any side. This check counts whitespace-surrounded texts and raises a data quality issue when their count exceeds the *max_count* parameter value. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text surrounded by whitespace percent](./text-surrounded-by-whitespace-percent.md)
This check detects text values that contain additional whitespace characters before or after the text.
 This check measures the percentage of text value surrounded by whitespace characters (on any side) and
 raises a data quality issue when their rate exceeds a *max_percent* parameter value.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_surrounded_by_whitespace_percent`</span>](./text-surrounded-by-whitespace-percent.md#profile-text-surrounded-by-whitespace-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|This check detects text values that are surrounded by whitespace characters on any side and measures their percentage. This check raises a data quality issue when their percentage exceeds the *max_percent* parameter value.| |
|[<span class="no-wrap-code">`daily_text_surrounded_by_whitespace_percent`</span>](./text-surrounded-by-whitespace-percent.md#daily-text-surrounded-by-whitespace-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|This check detects text values that are surrounded by whitespace characters on any side and measures their percentage. This check raises a data quality issue when their percentage exceeds the *max_percent* parameter value. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_surrounded_by_whitespace_percent`</span>](./text-surrounded-by-whitespace-percent.md#monthly-text-surrounded-by-whitespace-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|This check detects text values that are surrounded by whitespace characters on any side and measures their percentage. This check raises a data quality issue when their percentage exceeds the *max_percent* parameter value. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_surrounded_by_whitespace_percent`</span>](./text-surrounded-by-whitespace-percent.md#daily-partition-text-surrounded-by-whitespace-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|This check detects text values that are surrounded by whitespace characters on any side and measures their percentage. This check raises a data quality issue when their percentage exceeds the *max_percent* parameter value. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_surrounded_by_whitespace_percent`</span>](./text-surrounded-by-whitespace-percent.md#monthly-partition-text-surrounded-by-whitespace-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|This check detects text values that are surrounded by whitespace characters on any side and measures their percentage. This check raises a data quality issue when their percentage exceeds the *max_percent* parameter value. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |







