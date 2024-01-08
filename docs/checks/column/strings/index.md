# Checks/column/strings

**This is a list of strings column data quality checks supported by DQOps and a brief description of what they do.**





## **strings**
Validates that the data in a string column match the expected format or pattern.

| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_max_length](../string-max-length.md#profile-string-max-length)|profiling|Verifies that the length of string in a column does not exceed the maximum accepted length.|
|[daily_string_max_length](../string-max-length.md#daily-string-max-length)|monitoring|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_max_length](../string-max-length.md#monthly-string-max-length)|monitoring|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_max_length](../string-max-length.md#daily-partition-string-max-length)|partitioned|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_max_length](../string-max-length.md#monthly-partition-string-max-length)|partitioned|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_min_length](../string-min-length.md#profile-string-min-length)|profiling|Verifies that the length of string in a column does not fall below the minimum accepted length.|
|[daily_string_min_length](../string-min-length.md#daily-string-min-length)|monitoring|Verifies that the length of string in a column does not fall below the minimum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_min_length](../string-min-length.md#monthly-string-min-length)|monitoring|Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_min_length](../string-min-length.md#daily-partition-string-min-length)|partitioned|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_min_length](../string-min-length.md#monthly-partition-string-min-length)|partitioned|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_mean_length](../string-mean-length.md#profile-string-mean-length)|profiling|Verifies that the length of string in a column does not exceed the mean accepted length.|
|[daily_string_mean_length](../string-mean-length.md#daily-string-mean-length)|monitoring|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_mean_length](../string-mean-length.md#monthly-string-mean-length)|monitoring|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_mean_length](../string-mean-length.md#daily-partition-string-mean-length)|partitioned|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_mean_length](../string-mean-length.md#monthly-partition-string-mean-length)|partitioned|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_below_min_length_count](../string-length-below-min-length-count.md#profile-string-length-below-min-length-count)|profiling|The check counts the number of strings in the column that is below the length defined by the user as a parameter.|
|[daily_string_length_below_min_length_count](../string-length-below-min-length-count.md#daily-string-length-below-min-length-count)|monitoring|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_below_min_length_count](../string-length-below-min-length-count.md#monthly-string-length-below-min-length-count)|monitoring|The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_below_min_length_count](../string-length-below-min-length-count.md#daily-partition-string-length-below-min-length-count)|partitioned|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_below_min_length_count](../string-length-below-min-length-count.md#monthly-partition-string-length-below-min-length-count)|partitioned|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_below_min_length_percent](../string-length-below-min-length-percent.md#profile-string-length-below-min-length-percent)|profiling|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter.|
|[daily_string_length_below_min_length_percent](../string-length-below-min-length-percent.md#daily-string-length-below-min-length-percent)|monitoring|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_below_min_length_percent](../string-length-below-min-length-percent.md#monthly-string-length-below-min-length-percent)|monitoring|The check counts percentage of those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_below_min_length_percent](../string-length-below-min-length-percent.md#daily-partition-string-length-below-min-length-percent)|partitioned|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_below_min_length_percent](../string-length-below-min-length-percent.md#monthly-partition-string-length-below-min-length-percent)|partitioned|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_above_max_length_count](../string-length-above-max-length-count.md#profile-string-length-above-max-length-count)|profiling|The check counts the number of strings in the column that is above the length defined by the user as a parameter.|
|[daily_string_length_above_max_length_count](../string-length-above-max-length-count.md#daily-string-length-above-max-length-count)|monitoring|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_above_max_length_count](../string-length-above-max-length-count.md#monthly-string-length-above-max-length-count)|monitoring|The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_above_max_length_count](../string-length-above-max-length-count.md#daily-partition-string-length-above-max-length-count)|partitioned|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_above_max_length_count](../string-length-above-max-length-count.md#monthly-partition-string-length-above-max-length-count)|partitioned|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_above_max_length_percent](../string-length-above-max-length-percent.md#profile-string-length-above-max-length-percent)|profiling|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter.|
|[daily_string_length_above_max_length_percent](../string-length-above-max-length-percent.md#daily-string-length-above-max-length-percent)|monitoring|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_above_max_length_percent](../string-length-above-max-length-percent.md#monthly-string-length-above-max-length-percent)|monitoring|The check counts percentage of those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_above_max_length_percent](../string-length-above-max-length-percent.md#daily-partition-string-length-above-max-length-percent)|partitioned|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_above_max_length_percent](../string-length-above-max-length-percent.md#monthly-partition-string-length-above-max-length-percent)|partitioned|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_length_in_range_percent](../string-length-in-range-percent.md#profile-string-length-in-range-percent)|profiling|The check counts the percentage of those strings with length in the range provided by the user in the column. |
|[daily_string_length_in_range_percent](../string-length-in-range-percent.md#daily-string-length-in-range-percent)|monitoring|The check counts the percentage of those strings with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_length_in_range_percent](../string-length-in-range-percent.md#monthly-string-length-in-range-percent)|monitoring|The check counts percentage of those strings with length in the range provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_length_in_range_percent](../string-length-in-range-percent.md#daily-partition-string-length-in-range-percent)|partitioned|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_length_in_range_percent](../string-length-in-range-percent.md#monthly-partition-string-length-in-range-percent)|partitioned|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_empty_count](../string-empty-count.md#profile-string-empty-count)|profiling|Verifies that empty strings in a column does not exceed the maximum accepted count.|
|[daily_string_empty_count](../string-empty-count.md#daily-string-empty-count)|monitoring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_empty_count](../string-empty-count.md#monthly-string-empty-count)|monitoring|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_empty_count](../string-empty-count.md#daily-partition-string-empty-count)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_empty_count](../string-empty-count.md#monthly-partition-string-empty-count)|partitioned|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_empty_percent](../string-empty-percent.md#profile-string-empty-percent)|profiling|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.|
|[daily_string_empty_percent](../string-empty-percent.md#daily-string-empty-percent)|monitoring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_empty_percent](../string-empty-percent.md#monthly-string-empty-percent)|monitoring|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_empty_percent](../string-empty-percent.md#daily-partition-string-empty-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_empty_percent](../string-empty-percent.md#monthly-partition-string-empty-percent)|partitioned|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_whitespace_count](../string-whitespace-count.md#profile-string-whitespace-count)|profiling|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.|
|[daily_string_whitespace_count](../string-whitespace-count.md#daily-string-whitespace-count)|monitoring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_whitespace_count](../string-whitespace-count.md#monthly-string-whitespace-count)|monitoring|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_whitespace_count](../string-whitespace-count.md#daily-partition-string-whitespace-count)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_whitespace_count](../string-whitespace-count.md#monthly-partition-string-whitespace-count)|partitioned|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_whitespace_percent](../string-whitespace-percent.md#profile-string-whitespace-percent)|profiling|Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.|
|[daily_string_whitespace_percent](../string-whitespace-percent.md#daily-string-whitespace-percent)|monitoring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_whitespace_percent](../string-whitespace-percent.md#monthly-string-whitespace-percent)|monitoring|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_whitespace_percent](../string-whitespace-percent.md#daily-partition-string-whitespace-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_whitespace_percent](../string-whitespace-percent.md#monthly-partition-string-whitespace-percent)|partitioned|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_surrounded_by_whitespace_count](../string-surrounded-by-whitespace-count.md#profile-string-surrounded-by-whitespace-count)|profiling|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count.|
|[daily_string_surrounded_by_whitespace_count](../string-surrounded-by-whitespace-count.md#daily-string-surrounded-by-whitespace-count)|monitoring|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_surrounded_by_whitespace_count](../string-surrounded-by-whitespace-count.md#monthly-string-surrounded-by-whitespace-count)|monitoring|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_surrounded_by_whitespace_count](../string-surrounded-by-whitespace-count.md#daily-partition-string-surrounded-by-whitespace-count)|partitioned|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_surrounded_by_whitespace_count](../string-surrounded-by-whitespace-count.md#monthly-partition-string-surrounded-by-whitespace-count)|partitioned|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_surrounded_by_whitespace_percent](../string-surrounded-by-whitespace-percent.md#profile-string-surrounded-by-whitespace-percent)|profiling|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage.|
|[daily_string_surrounded_by_whitespace_percent](../string-surrounded-by-whitespace-percent.md#daily-string-surrounded-by-whitespace-percent)|monitoring|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_surrounded_by_whitespace_percent](../string-surrounded-by-whitespace-percent.md#monthly-string-surrounded-by-whitespace-percent)|monitoring|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_surrounded_by_whitespace_percent](../string-surrounded-by-whitespace-percent.md#daily-partition-string-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_surrounded_by_whitespace_percent](../string-surrounded-by-whitespace-percent.md#monthly-partition-string-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_null_placeholder_count](../string-null-placeholder-count.md#profile-string-null-placeholder-count)|profiling|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.|
|[daily_string_null_placeholder_count](../string-null-placeholder-count.md#daily-string-null-placeholder-count)|monitoring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_null_placeholder_count](../string-null-placeholder-count.md#monthly-string-null-placeholder-count)|monitoring|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_null_placeholder_count](../string-null-placeholder-count.md#daily-partition-string-null-placeholder-count)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_null_placeholder_count](../string-null-placeholder-count.md#monthly-partition-string-null-placeholder-count)|partitioned|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_null_placeholder_percent](../string-null-placeholder-percent.md#profile-string-null-placeholder-percent)|profiling|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.|
|[daily_string_null_placeholder_percent](../string-null-placeholder-percent.md#daily-string-null-placeholder-percent)|monitoring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_null_placeholder_percent](../string-null-placeholder-percent.md#monthly-string-null-placeholder-percent)|monitoring|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_null_placeholder_percent](../string-null-placeholder-percent.md#daily-partition-string-null-placeholder-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_null_placeholder_percent](../string-null-placeholder-percent.md#monthly-partition-string-null-placeholder-percent)|partitioned|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_boolean_placeholder_percent](../string-boolean-placeholder-percent.md#profile-string-boolean-placeholder-percent)|profiling|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage.|
|[daily_string_boolean_placeholder_percent](../string-boolean-placeholder-percent.md#daily-string-boolean-placeholder-percent)|monitoring|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_boolean_placeholder_percent](../string-boolean-placeholder-percent.md#monthly-string-boolean-placeholder-percent)|monitoring|Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_boolean_placeholder_percent](../string-boolean-placeholder-percent.md#daily-partition-string-boolean-placeholder-percent)|partitioned|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_boolean_placeholder_percent](../string-boolean-placeholder-percent.md#monthly-partition-string-boolean-placeholder-percent)|partitioned|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_parsable_to_integer_percent](../string-parsable-to-integer-percent.md#profile-string-parsable-to-integer-percent)|profiling|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage.|
|[daily_string_parsable_to_integer_percent](../string-parsable-to-integer-percent.md#daily-string-parsable-to-integer-percent)|monitoring|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_parsable_to_integer_percent](../string-parsable-to-integer-percent.md#monthly-string-parsable-to-integer-percent)|monitoring|Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_parsable_to_integer_percent](../string-parsable-to-integer-percent.md#daily-partition-string-parsable-to-integer-percent)|partitioned|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_parsable_to_integer_percent](../string-parsable-to-integer-percent.md#monthly-partition-string-parsable-to-integer-percent)|partitioned|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_parsable_to_float_percent](../string-parsable-to-float-percent.md#profile-string-parsable-to-float-percent)|profiling|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage.|
|[daily_string_parsable_to_float_percent](../string-parsable-to-float-percent.md#daily-string-parsable-to-float-percent)|monitoring|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_parsable_to_float_percent](../string-parsable-to-float-percent.md#monthly-string-parsable-to-float-percent)|monitoring|Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_parsable_to_float_percent](../string-parsable-to-float-percent.md#daily-partition-string-parsable-to-float-percent)|partitioned|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_parsable_to_float_percent](../string-parsable-to-float-percent.md#monthly-partition-string-parsable-to-float-percent)|partitioned|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_expected_strings_in_use_count](../expected-strings-in-use-count.md#profile-expected-strings-in-use-count)|profiling|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|
|[daily_expected_strings_in_use_count](../expected-strings-in-use-count.md#daily-expected-strings-in-use-count)|monitoring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_expected_strings_in_use_count](../expected-strings-in-use-count.md#monthly-expected-strings-in-use-count)|monitoring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_expected_strings_in_use_count](../expected-strings-in-use-count.md#daily-partition-expected-strings-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_expected_strings_in_use_count](../expected-strings-in-use-count.md#monthly-partition-expected-strings-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_value_in_set_percent](../string-value-in-set-percent.md#profile-string-value-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|
|[daily_string_value_in_set_percent](../string-value-in-set-percent.md#daily-string-value-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_value_in_set_percent](../string-value-in-set-percent.md#monthly-string-value-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_value_in_set_percent](../string-value-in-set-percent.md#daily-partition-string-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_value_in_set_percent](../string-value-in-set-percent.md#monthly-partition-string-value-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_valid_dates_percent](../string-valid-dates-percent.md#profile-string-valid-dates-percent)|profiling|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_dates_percent](../string-valid-dates-percent.md#daily-string-valid-dates-percent)|monitoring|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_dates_percent](../string-valid-dates-percent.md#monthly-string-valid-dates-percent)|monitoring|Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_dates_percent](../string-valid-dates-percent.md#daily-partition-string-valid-dates-percent)|partitioned|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_dates_percent](../string-valid-dates-percent.md#monthly-partition-string-valid-dates-percent)|partitioned|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_valid_country_code_percent](../string-valid-country-code-percent.md#profile-string-valid-country-code-percent)|profiling|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_country_code_percent](../string-valid-country-code-percent.md#daily-string-valid-country-code-percent)|monitoring|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_country_code_percent](../string-valid-country-code-percent.md#monthly-string-valid-country-code-percent)|monitoring|Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_country_code_percent](../string-valid-country-code-percent.md#daily-partition-string-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_country_code_percent](../string-valid-country-code-percent.md#monthly-partition-string-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_valid_currency_code_percent](../string-valid-currency-code-percent.md#profile-string-valid-currency-code-percent)|profiling|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_currency_code_percent](../string-valid-currency-code-percent.md#daily-string-valid-currency-code-percent)|monitoring|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_currency_code_percent](../string-valid-currency-code-percent.md#monthly-string-valid-currency-code-percent)|monitoring|Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_valid_currency_code_percent](../string-valid-currency-code-percent.md#daily-partition-string-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_valid_currency_code_percent](../string-valid-currency-code-percent.md#monthly-partition-string-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_invalid_email_count](../string-invalid-email-count.md#profile-string-invalid-email-count)|profiling|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_email_count](../string-invalid-email-count.md#daily-string-invalid-email-count)|monitoring|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_email_count](../string-invalid-email-count.md#monthly-string-invalid-email-count)|monitoring|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_email_count](../string-invalid-email-count.md#daily-partition-string-invalid-email-count)|partitioned|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_email_count](../string-invalid-email-count.md#monthly-partition-string-invalid-email-count)|partitioned|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_invalid_uuid_count](../string-invalid-uuid-count.md#profile-string-invalid-uuid-count)|profiling|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_uuid_count](../string-invalid-uuid-count.md#daily-string-invalid-uuid-count)|monitoring|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_uuid_count](../string-invalid-uuid-count.md#monthly-string-invalid-uuid-count)|monitoring|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_uuid_count](../string-invalid-uuid-count.md#daily-partition-string-invalid-uuid-count)|partitioned|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_uuid_count](../string-invalid-uuid-count.md#monthly-partition-string-invalid-uuid-count)|partitioned|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_valid_uuid_percent](../string-valid-uuid-percent.md#profile-string-valid-uuid-percent)|profiling|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage.|
|[daily_string_valid_uuid_percent](../string-valid-uuid-percent.md#daily-string-valid-uuid-percent)|monitoring|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_valid_uuid_percent](../string-valid-uuid-percent.md#monthly-string-valid-uuid-percent)|monitoring|Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_valid_uuid_percent](../string-valid-uuid-percent.md#daily-partition-valid-uuid-percent)|partitioned|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_valid_uuid_percent](../string-valid-uuid-percent.md#monthly-partition-valid-uuid-percent)|partitioned|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_invalid_ip4_address_count](../string-invalid-ip4-address-count.md#profile-string-invalid-ip4-address-count)|profiling|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_ip4_address_count](../string-invalid-ip4-address-count.md#daily-string-invalid-ip4-address-count)|monitoring|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_ip4_address_count](../string-invalid-ip4-address-count.md#monthly-string-invalid-ip4-address-count)|monitoring|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_ip4_address_count](../string-invalid-ip4-address-count.md#daily-partition-string-invalid-ip4-address-count)|partitioned|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_ip4_address_count](../string-invalid-ip4-address-count.md#monthly-partition-string-invalid-ip4-address-count)|partitioned|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_invalid_ip6_address_count](../string-invalid-ip6-address-count.md#profile-string-invalid-ip6-address-count)|profiling|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count.|
|[daily_string_invalid_ip6_address_count](../string-invalid-ip6-address-count.md#daily-string-invalid-ip6-address-count)|monitoring|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_invalid_ip6_address_count](../string-invalid-ip6-address-count.md#monthly-string-invalid-ip6-address-count)|monitoring|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_invalid_ip6_address_count](../string-invalid-ip6-address-count.md#daily-partition-string-invalid-ip6-address-count)|partitioned|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_invalid_ip6_address_count](../string-invalid-ip6-address-count.md#monthly-partition-string-invalid-ip6-address-count)|partitioned|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_not_match_regex_count](../string-not-match-regex-count.md#profile-string-not-match-regex-count)|profiling|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count.|
|[daily_string_not_match_regex_count](../string-not-match-regex-count.md#daily-string-not-match-regex-count)|monitoring|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_not_match_regex_count](../string-not-match-regex-count.md#monthly-string-not-match-regex-count)|monitoring|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_not_match_regex_count](../string-not-match-regex-count.md#daily-partition-string-not-match-regex-count)|partitioned|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_not_match_regex_count](../string-not-match-regex-count.md#monthly-partition-string-not-match-regex-count)|partitioned|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_match_regex_percent](../string-match-regex-percent.md#profile-string-match-regex-percent)|profiling|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage.|
|[daily_string_match_regex_percent](../string-match-regex-percent.md#daily-string-match-regex-percent)|monitoring|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_match_regex_percent](../string-match-regex-percent.md#monthly-string-match-regex-percent)|monitoring|Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_regex_percent](../string-match-regex-percent.md#daily-partition-string-match-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_regex_percent](../string-match-regex-percent.md#monthly-partition-string-match-regex-percent)|partitioned|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_not_match_date_regex_count](../string-not-match-date-regex-count.md#profile-string-not-match-date-regex-count)|profiling|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count.|
|[daily_string_not_match_date_regex_count](../string-not-match-date-regex-count.md#daily-string-not-match-date-regex-count)|monitoring|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_not_match_date_regex_count](../string-not-match-date-regex-count.md#monthly-string-not-match-date-regex-count)|monitoring|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_not_match_date_regex_count](../string-not-match-date-regex-count.md#daily-partition-string-not-match-date-regex-count)|partitioned|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_not_match_date_regex_count](../string-not-match-date-regex-count.md#monthly-partition-string-not-match-date-regex-count)|partitioned|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_match_date_regex_percent](../string-match-date-regex-percent.md#profile-string-match-date-regex-percent)|profiling|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage.|
|[daily_string_match_date_regex_percent](../string-match-date-regex-percent.md#daily-string-match-date-regex-percent)|monitoring|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_match_date_regex_percent](../string-match-date-regex-percent.md#monthly-string-match-date-regex-percent)|monitoring|Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_date_regex_percent](../string-match-date-regex-percent.md#daily-partition-string-match-date-regex-percent)|partitioned|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_date_regex_percent](../string-match-date-regex-percent.md#monthly-partition-string-match-date-regex-percent)|partitioned|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_string_match_name_regex_percent](../string-match-name-regex-percent.md#profile-string-match-name-regex-percent)|profiling|Verifies that the percentage of strings matching the name regex in a column does not fall below the minimum accepted percentage.|
|[daily_string_match_name_regex_percent](../string-match-name-regex-percent.md#daily-string-match-name-regex-percent)|monitoring|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_string_match_name_regex_percent](../string-match-name-regex-percent.md#monthly-string-match-name-regex-percent)|monitoring|Verifies that the percentage of strings matching the name regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_string_match_name_regex_percent](../string-match-name-regex-percent.md#daily-partition-string-match-name-regex-percent)|partitioned|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_string_match_name_regex_percent](../string-match-name-regex-percent.md#monthly-partition-string-match-name-regex-percent)|partitioned|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|


| Check name | Check type | Description |
|------------|------------|-------------|
|[profile_expected_strings_in_top_values_count](../expected-strings-in-top-values-count.md#profile-expected-strings-in-top-values-count)|profiling|Verifies that the top X most popular column values contain all values from a list of expected values.|
|[daily_expected_strings_in_top_values_count](../expected-strings-in-top-values-count.md#daily-expected-strings-in-top-values-count)|monitoring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.|
|[monthly_expected_strings_in_top_values_count](../expected-strings-in-top-values-count.md#monthly-expected-strings-in-top-values-count)|monitoring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent row count for each month when the data quality check was evaluated.|
|[daily_partition_expected_strings_in_top_values_count](../expected-strings-in-top-values-count.md#daily-partition-expected-strings-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each daily partition.|
|[monthly_partition_expected_strings_in_top_values_count](../expected-strings-in-top-values-count.md#monthly-partition-expected-strings-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each monthly partition.|





