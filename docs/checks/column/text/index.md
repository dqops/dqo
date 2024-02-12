# column level text data quality checks

This is a list of text column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level text checks
Validates that the data in a text column has a valid range.

### [text max length](./text-max-length.md)
A column-level check that ensures that the length of text values in a column does not exceed the maximum accepted length.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_max_length`</span>](./text-max-length.md#profile-text-max-length)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the length of a text in a column does not exceed the maximum accepted length|:material-check-bold:|
|[<span class="no-wrap-code">`daily_text_max_length`</span>](./text-max-length.md#daily-text-max-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the length of a text in a column does not exceed the maximum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_text_max_length`</span>](./text-max-length.md#monthly-text-max-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the length of a text in a column does not exceed the maximum accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_text_max_length`</span>](./text-max-length.md#daily-partition-text-max-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the length of a text in a column does not exceed the maximum accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_text_max_length`</span>](./text-max-length.md#monthly-partition-text-max-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the length of a text in a column does not exceed the maximum accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|:material-check-bold:|



### [text min length](./text-min-length.md)
A column-level check that ensures that the length of text in a column does not fall below the minimum accepted length.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_min_length`</span>](./text-min-length.md#profile-text-min-length)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the length of a text in a column does not fall below the minimum accepted length|:material-check-bold:|
|[<span class="no-wrap-code">`daily_text_min_length`</span>](./text-min-length.md#daily-text-min-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the length of a text in a column does not fall below the minimum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_text_min_length`</span>](./text-min-length.md#monthly-text-min-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the length of a text in a column does not fall below the minimum accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_text_min_length`</span>](./text-min-length.md#daily-partition-text-min-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the length of a text in a column does not fall below the minimum accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_text_min_length`</span>](./text-min-length.md#monthly-partition-text-min-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the length of a text in a column does not fall below the minimum accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|:material-check-bold:|



### [text mean length](./text-mean-length.md)
A column-level check that ensures that the length of text values in a column does not exceed the mean accepted length.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_mean_length`</span>](./text-mean-length.md#profile-text-mean-length)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the length of a text in a column does not exceed the mean accepted length| |
|[<span class="no-wrap-code">`daily_text_mean_length`</span>](./text-mean-length.md#daily-text-mean-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the length of a text in a column does not exceed the mean accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_mean_length`</span>](./text-mean-length.md#monthly-text-mean-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the length of a text in a column does not exceed the mean accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_mean_length`</span>](./text-mean-length.md#daily-partition-text-mean-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the length of a text in a column does not exceed the mean accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_mean_length`</span>](./text-mean-length.md#monthly-partition-text-mean-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the length of a text in a column does not exceed the mean accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text length below min length](./text-length-below-min-length.md)
A column-level check that ensures that the number of text values in the monitored column with a length below the length defined by the user as a parameter does not exceed set thresholds.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_length_below_min_length`</span>](./text-length-below-min-length.md#profile-text-length-below-min-length)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check counts the number of text values in the column that is below the length defined by the user as a parameter| |
|[<span class="no-wrap-code">`daily_text_length_below_min_length`</span>](./text-length-below-min-length.md#daily-text-length-below-min-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_length_below_min_length`</span>](./text-length-below-min-length.md#monthly-text-length-below-min-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_length_below_min_length`</span>](./text-length-below-min-length.md#daily-partition-text-length-below-min-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_length_below_min_length`</span>](./text-length-below-min-length.md#monthly-partition-text-length-below-min-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text length below min length percent](./text-length-below-min-length-percent.md)
A column-level check that ensures that the percentage of text values in the monitored column with a length below the length defined by the user as a parameter does not fall below set thresholds.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_length_below_min_length_percent`</span>](./text-length-below-min-length-percent.md#profile-text-length-below-min-length-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter| |
|[<span class="no-wrap-code">`daily_text_length_below_min_length_percent`</span>](./text-length-below-min-length-percent.md#daily-text-length-below-min-length-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_length_below_min_length_percent`</span>](./text-length-below-min-length-percent.md#monthly-text-length-below-min-length-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_length_below_min_length_percent`</span>](./text-length-below-min-length-percent.md#daily-partition-text-length-below-min-length-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_length_below_min_length_percent`</span>](./text-length-below-min-length-percent.md#monthly-partition-text-length-below-min-length-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text length above max length](./text-length-above-max-length.md)
A column-level check that ensures that the number of text values in the monitored column with a length above the length defined by the user as a parameter does not exceed set thresholds.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_length_above_max_length`</span>](./text-length-above-max-length.md#profile-text-length-above-max-length)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check counts the number of text values in the column that is above the length defined by the user as a parameter| |
|[<span class="no-wrap-code">`daily_text_length_above_max_length`</span>](./text-length-above-max-length.md#daily-text-length-above-max-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_length_above_max_length`</span>](./text-length-above-max-length.md#monthly-text-length-above-max-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_length_above_max_length`</span>](./text-length-above-max-length.md#daily-partition-text-length-above-max-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_length_above_max_length`</span>](./text-length-above-max-length.md#monthly-partition-text-length-above-max-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text length above max length percent](./text-length-above-max-length-percent.md)
A column-level check that ensures that the percentage of text values in the monitored column with a length above the length defined by the user as a parameter does not fall below set thresholds.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_length_above_max_length_percent`</span>](./text-length-above-max-length-percent.md#profile-text-length-above-max-length-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter| |
|[<span class="no-wrap-code">`daily_text_length_above_max_length_percent`</span>](./text-length-above-max-length-percent.md#daily-text-length-above-max-length-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_length_above_max_length_percent`</span>](./text-length-above-max-length-percent.md#monthly-text-length-above-max-length-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_length_above_max_length_percent`</span>](./text-length-above-max-length-percent.md#daily-partition-text-length-above-max-length-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_length_above_max_length_percent`</span>](./text-length-above-max-length-percent.md#monthly-partition-text-length-above-max-length-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text length in range percent](./text-length-in-range-percent.md)
Column check that calculates the percentage of text values with a length below the indicated by the user length in a monitored column.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_length_in_range_percent`</span>](./text-length-in-range-percent.md#profile-text-length-in-range-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check measures the percentage of those text values with length in the range provided by the user in the column| |
|[<span class="no-wrap-code">`daily_text_length_in_range_percent`</span>](./text-length-in-range-percent.md#daily-text-length-in-range-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_length_in_range_percent`</span>](./text-length-in-range-percent.md#monthly-text-length-in-range-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_length_in_range_percent`</span>](./text-length-in-range-percent.md#daily-partition-text-length-in-range-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_length_in_range_percent`</span>](./text-length-in-range-percent.md#monthly-partition-text-length-in-range-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



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



### [text valid country code percent](./text-valid-country-code-percent.md)
A column-level check that ensures that the percentage of text values that are valid country codes in the monitored column does not fall below set thresholds.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_valid_country_code_percent`</span>](./text-valid-country-code-percent.md#profile-text-valid-country-code-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage| |
|[<span class="no-wrap-code">`daily_text_valid_country_code_percent`</span>](./text-valid-country-code-percent.md#daily-text-valid-country-code-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_valid_country_code_percent`</span>](./text-valid-country-code-percent.md#monthly-text-valid-country-code-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_valid_country_code_percent`</span>](./text-valid-country-code-percent.md#daily-partition-text-valid-country-code-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_valid_country_code_percent`</span>](./text-valid-country-code-percent.md#monthly-partition-text-valid-country-code-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text valid currency code percent](./text-valid-currency-code-percent.md)
A column-level check that ensures that the percentage of text values that are valid currency codes in the monitored column does not fall below set thresholds.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_valid_currency_code_percent`</span>](./text-valid-currency-code-percent.md#profile-text-valid-currency-code-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage| |
|[<span class="no-wrap-code">`daily_text_valid_currency_code_percent`</span>](./text-valid-currency-code-percent.md#daily-text-valid-currency-code-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_valid_currency_code_percent`</span>](./text-valid-currency-code-percent.md#monthly-text-valid-currency-code-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_valid_currency_code_percent`</span>](./text-valid-currency-code-percent.md#daily-partition-text-valid-currency-code-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_valid_currency_code_percent`</span>](./text-valid-currency-code-percent.md#monthly-partition-text-valid-currency-code-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |







