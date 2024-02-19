# column level text data quality checks

This is a list of text column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level text checks
Validates that the data in a text column has a valid range.

### [text min length](./text-min-length.md)
This check finds the length of the shortest text in a column. DQOps validates the shortest length using a range rule.
 DQOps raises an issue when the minimum text length is outside a range of accepted values.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_min_length`</span>](./text-min-length.md#profile-text-min-length)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|This check finds the length of the shortest text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the shortest text is too short.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_text_min_length`</span>](./text-min-length.md#daily-text-min-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|This check finds the length of the shortest text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the shortest text is too short. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_text_min_length`</span>](./text-min-length.md#monthly-text-min-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|This check finds the length of the shortest text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the shortest text is too short. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_text_min_length`</span>](./text-min-length.md#daily-partition-text-min-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|This check finds the length of the shortest text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the shortest text is too short. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_text_min_length`</span>](./text-min-length.md#monthly-partition-text-min-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|This check finds the length of the shortest text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the shortest text is too short. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|:material-check-bold:|



### [text max length](./text-max-length.md)
This check finds the length of the longest text in a column. DQOps validates the maximum length using a range rule.
 DQOps raises an issue when the maximum text length is outside a range of accepted values.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_max_length`</span>](./text-max-length.md#profile-text-max-length)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|This check finds the length of the longest text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the texts are too long or not long enough.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_text_max_length`</span>](./text-max-length.md#daily-text-max-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|This check finds the length of the longest text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the texts are too long or not long enough. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_text_max_length`</span>](./text-max-length.md#monthly-text-max-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|This check finds the length of the longest text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the texts are too long or not long enough. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_text_max_length`</span>](./text-max-length.md#daily-partition-text-max-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|This check finds the length of the longest text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the texts are too long or not long enough. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_text_max_length`</span>](./text-max-length.md#monthly-partition-text-max-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|This check finds the length of the longest text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the texts are too long or not long enough. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|:material-check-bold:|



### [text mean length](./text-mean-length.md)
This check calculates the average text length in a column. DQOps validates the mean length using a range rule.
 DQOps raises an issue when the mean text length is outside a range of accepted values.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_mean_length`</span>](./text-mean-length.md#profile-text-mean-length)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the mean (average) length of texts in a column is within an accepted range.| |
|[<span class="no-wrap-code">`daily_text_mean_length`</span>](./text-mean-length.md#daily-text-mean-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the mean (average) length of texts in a column is within an accepted range. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_mean_length`</span>](./text-mean-length.md#monthly-text-mean-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the mean (average) length of texts in a column is within an accepted range. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_mean_length`</span>](./text-mean-length.md#daily-partition-text-mean-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the mean (average) length of texts in a column is within an accepted range. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_mean_length`</span>](./text-mean-length.md#monthly-partition-text-mean-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the mean (average) length of texts in a column is within an accepted range. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text length below min length](./text-length-below-min-length.md)
This check finds texts that are shorter than the minimum accepted text length. It counts the number of texts that are too short and raises a data quality issue when too many invalid texts are found.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_length_below_min_length`</span>](./text-length-below-min-length.md#profile-text-length-below-min-length)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check counts the number of text values in the column that is below the length defined by the user as a parameter.| |
|[<span class="no-wrap-code">`daily_text_length_below_min_length`</span>](./text-length-below-min-length.md#daily-text-length-below-min-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_length_below_min_length`</span>](./text-length-below-min-length.md#monthly-text-length-below-min-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_length_below_min_length`</span>](./text-length-below-min-length.md#daily-partition-text-length-below-min-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_length_below_min_length`</span>](./text-length-below-min-length.md#monthly-partition-text-length-below-min-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text length below min length percent](./text-length-below-min-length-percent.md)
This check finds texts that are shorter than the minimum accepted text length.
 It measures the percentage of too short texts and raises a data quality issue when too many invalid texts are found.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_length_below_min_length_percent`</span>](./text-length-below-min-length-percent.md#profile-text-length-below-min-length-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter.| |
|[<span class="no-wrap-code">`daily_text_length_below_min_length_percent`</span>](./text-length-below-min-length-percent.md#daily-text-length-below-min-length-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_length_below_min_length_percent`</span>](./text-length-below-min-length-percent.md#monthly-text-length-below-min-length-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_length_below_min_length_percent`</span>](./text-length-below-min-length-percent.md#daily-partition-text-length-below-min-length-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_length_below_min_length_percent`</span>](./text-length-below-min-length-percent.md#monthly-partition-text-length-below-min-length-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text length above max length](./text-length-above-max-length.md)
This check finds texts that are longer than the maximum accepted text length.
 It counts the number of texts that are too long and raises a data quality issue when too many invalid texts are found.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_length_above_max_length`</span>](./text-length-above-max-length.md#profile-text-length-above-max-length)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check counts the number of text values in the column that is above the length defined by the user as a parameter.| |
|[<span class="no-wrap-code">`daily_text_length_above_max_length`</span>](./text-length-above-max-length.md#daily-text-length-above-max-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_length_above_max_length`</span>](./text-length-above-max-length.md#monthly-text-length-above-max-length)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_length_above_max_length`</span>](./text-length-above-max-length.md#daily-partition-text-length-above-max-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_length_above_max_length`</span>](./text-length-above-max-length.md#monthly-partition-text-length-above-max-length)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text length above max length percent](./text-length-above-max-length-percent.md)
This check finds texts that are longer than the maximum accepted text length.
 It measures the percentage of texts that are too long and raises a data quality issue when too many invalid texts are found.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_length_above_max_length_percent`</span>](./text-length-above-max-length-percent.md#profile-text-length-above-max-length-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter.| |
|[<span class="no-wrap-code">`daily_text_length_above_max_length_percent`</span>](./text-length-above-max-length-percent.md#daily-text-length-above-max-length-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_length_above_max_length_percent`</span>](./text-length-above-max-length-percent.md#monthly-text-length-above-max-length-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_length_above_max_length_percent`</span>](./text-length-above-max-length-percent.md#daily-partition-text-length-above-max-length-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_length_above_max_length_percent`</span>](./text-length-above-max-length-percent.md#monthly-partition-text-length-above-max-length-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text length in range percent](./text-length-in-range-percent.md)
This check verifies that the minimum and maximum lengths of text values are in the range of accepted values.
 It measures the percentage of texts with a valid length and raises a data quality issue when an insufficient number of texts have a valid length.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_length_in_range_percent`</span>](./text-length-in-range-percent.md#profile-text-length-in-range-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check measures the percentage of those text values with length in the range provided by the user in the column.| |
|[<span class="no-wrap-code">`daily_text_length_in_range_percent`</span>](./text-length-in-range-percent.md#daily-text-length-in-range-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_length_in_range_percent`</span>](./text-length-in-range-percent.md#monthly-text-length-in-range-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_length_in_range_percent`</span>](./text-length-in-range-percent.md#daily-partition-text-length-in-range-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_length_in_range_percent`</span>](./text-length-in-range-percent.md#monthly-partition-text-length-in-range-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |







