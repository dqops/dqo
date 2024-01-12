# column level text data quality checks

This is a list of text column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **text**
Validates that the data in a string column match the expected format or pattern.

| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_max_length](./text-max-length.md#profile-text-max-length)|profiling|Verifies that the length of a text in a column does not exceed the maximum accepted length|standard|
|[daily_text_max_length](./text-max-length.md#daily-text-max-length)|monitoring|Verifies that the length of a text in a column does not exceed the maximum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_text_max_length](./text-max-length.md#monthly-text-max-length)|monitoring|Verifies that the length of a text in a column does not exceed the maximum accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_text_max_length](./text-max-length.md#daily-partition-text-max-length)|partitioned|Verifies that the length of a text in a column does not exceed the maximum accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|standard|
|[monthly_partition_text_max_length](./text-max-length.md#monthly-partition-text-max-length)|partitioned|Verifies that the length of a text in a column does not exceed the maximum accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_min_length](./text-min-length.md#profile-text-min-length)|profiling|Verifies that the length of a text in a column does not fall below the minimum accepted length|standard|
|[daily_text_min_length](./text-min-length.md#daily-text-min-length)|monitoring|Verifies that the length of a text in a column does not fall below the minimum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|standard|
|[monthly_text_min_length](./text-min-length.md#monthly-text-min-length)|monitoring|Verifies that the length of a text in a column does not fall below the minimum accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|standard|
|[daily_partition_text_min_length](./text-min-length.md#daily-partition-text-min-length)|partitioned|Verifies that the length of a text in a column does not fall below the minimum accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|standard|
|[monthly_partition_text_min_length](./text-min-length.md#monthly-partition-text-min-length)|partitioned|Verifies that the length of a text in a column does not fall below the minimum accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|standard|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_mean_length](./text-mean-length.md#profile-text-mean-length)|profiling|Verifies that the length of a text in a column does not exceed the mean accepted length|advanced|
|[daily_text_mean_length](./text-mean-length.md#daily-text-mean-length)|monitoring|Verifies that the length of a text in a column does not exceed the mean accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_mean_length](./text-mean-length.md#monthly-text-mean-length)|monitoring|Verifies that the length of a text in a column does not exceed the mean accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_mean_length](./text-mean-length.md#daily-partition-text-mean-length)|partitioned|Verifies that the length of a text in a column does not exceed the mean accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_mean_length](./text-mean-length.md#monthly-partition-text-mean-length)|partitioned|Verifies that the length of a text in a column does not exceed the mean accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_length_below_min_length](./text-length-below-min-length.md#profile-text-length-below-min-length)|profiling|The check counts the number of text values in the column that is below the length defined by the user as a parameter|advanced|
|[daily_text_length_below_min_length](./text-length-below-min-length.md#daily-text-length-below-min-length)|monitoring|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_length_below_min_length](./text-length-below-min-length.md#monthly-text-length-below-min-length)|monitoring|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_length_below_min_length](./text-length-below-min-length.md#daily-partition-text-length-below-min-length)|partitioned|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_length_below_min_length](./text-length-below-min-length.md#monthly-partition-text-length-below-min-length)|partitioned|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_length_below_min_length_percent](./text-length-below-min-length-percent.md#profile-text-length-below-min-length-percent)|profiling|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter|advanced|
|[daily_text_length_below_min_length_percent](./text-length-below-min-length-percent.md#daily-text-length-below-min-length-percent)|monitoring|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_length_below_min_length_percent](./text-length-below-min-length-percent.md#monthly-text-length-below-min-length-percent)|monitoring|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_length_below_min_length_percent](./text-length-below-min-length-percent.md#daily-partition-text-length-below-min-length-percent)|partitioned|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_length_below_min_length_percent](./text-length-below-min-length-percent.md#monthly-partition-text-length-below-min-length-percent)|partitioned|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_length_above_max_length](./text-length-above-max-length.md#profile-text-length-above-max-length)|profiling|The check counts the number of text values in the column that is above the length defined by the user as a parameter|advanced|
|[daily_text_length_above_max_length](./text-length-above-max-length.md#daily-text-length-above-max-length)|monitoring|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_length_above_max_length](./text-length-above-max-length.md#monthly-text-length-above-max-length)|monitoring|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_length_above_max_length](./text-length-above-max-length.md#daily-partition-text-length-above-max-length)|partitioned|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_length_above_max_length](./text-length-above-max-length.md#monthly-partition-text-length-above-max-length)|partitioned|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_length_above_max_length_percent](./text-length-above-max-length-percent.md#profile-text-length-above-max-length-percent)|profiling|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter|advanced|
|[daily_text_length_above_max_length_percent](./text-length-above-max-length-percent.md#daily-text-length-above-max-length-percent)|monitoring|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_length_above_max_length_percent](./text-length-above-max-length-percent.md#monthly-text-length-above-max-length-percent)|monitoring|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_length_above_max_length_percent](./text-length-above-max-length-percent.md#daily-partition-text-length-above-max-length-percent)|partitioned|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_length_above_max_length_percent](./text-length-above-max-length-percent.md#monthly-partition-text-length-above-max-length-percent)|partitioned|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_length_in_range_percent](./text-length-in-range-percent.md#profile-text-length-in-range-percent)|profiling|The check measures the percentage of those text values with length in the range provided by the user in the column|advanced|
|[daily_text_length_in_range_percent](./text-length-in-range-percent.md#daily-text-length-in-range-percent)|monitoring|The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_length_in_range_percent](./text-length-in-range-percent.md#monthly-text-length-in-range-percent)|monitoring|The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_length_in_range_percent](./text-length-in-range-percent.md#daily-partition-text-length-in-range-percent)|partitioned|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_length_in_range_percent](./text-length-in-range-percent.md#monthly-partition-text-length-in-range-percent)|partitioned|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_parsable_to_boolean_percent](./text-parsable-to-boolean-percent.md#profile-text-parsable-to-boolean-percent)|profiling|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n.|advanced|
|[daily_text_parsable_to_boolean_percent](./text-parsable-to-boolean-percent.md#daily-text-parsable-to-boolean-percent)|monitoring|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_parsable_to_boolean_percent](./text-parsable-to-boolean-percent.md#monthly-text-parsable-to-boolean-percent)|monitoring|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_parsable_to_boolean_percent](./text-parsable-to-boolean-percent.md#daily-partition-text-parsable-to-boolean-percent)|partitioned|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_parsable_to_boolean_percent](./text-parsable-to-boolean-percent.md#monthly-partition-text-parsable-to-boolean-percent)|partitioned|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_parsable_to_integer_percent](./text-parsable-to-integer-percent.md#profile-text-parsable-to-integer-percent)|profiling|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage|advanced|
|[daily_text_parsable_to_integer_percent](./text-parsable-to-integer-percent.md#daily-text-parsable-to-integer-percent)|monitoring|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_parsable_to_integer_percent](./text-parsable-to-integer-percent.md#monthly-text-parsable-to-integer-percent)|monitoring|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_parsable_to_integer_percent](./text-parsable-to-integer-percent.md#daily-partition-text-parsable-to-integer-percent)|partitioned|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_parsable_to_integer_percent](./text-parsable-to-integer-percent.md#monthly-partition-text-parsable-to-integer-percent)|partitioned|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_parsable_to_float_percent](./text-parsable-to-float-percent.md#profile-text-parsable-to-float-percent)|profiling|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage|advanced|
|[daily_text_parsable_to_float_percent](./text-parsable-to-float-percent.md#daily-text-parsable-to-float-percent)|monitoring|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_parsable_to_float_percent](./text-parsable-to-float-percent.md#monthly-text-parsable-to-float-percent)|monitoring|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_parsable_to_float_percent](./text-parsable-to-float-percent.md#daily-partition-text-parsable-to-float-percent)|partitioned|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_parsable_to_float_percent](./text-parsable-to-float-percent.md#monthly-partition-text-parsable-to-float-percent)|partitioned|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_parsable_to_date_percent](./text-parsable-to-date-percent.md#profile-text-parsable-to-date-percent)|profiling|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression|advanced|
|[daily_text_parsable_to_date_percent](./text-parsable-to-date-percent.md#daily-text-parsable-to-date-percent)|monitoring|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_parsable_to_date_percent](./text-parsable-to-date-percent.md#monthly-text-parsable-to-date-percent)|monitoring|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_parsable_to_date_percent](./text-parsable-to-date-percent.md#daily-partition-text-parsable-to-date-percent)|partitioned|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_parsable_to_date_percent](./text-parsable-to-date-percent.md#monthly-partition-text-parsable-to-date-percent)|partitioned|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_surrounded_by_whitespace](./text-surrounded-by-whitespace.md#profile-text-surrounded-by-whitespace)|profiling|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table|advanced|
|[daily_text_surrounded_by_whitespace](./text-surrounded-by-whitespace.md#daily-text-surrounded-by-whitespace)|monitoring|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_surrounded_by_whitespace](./text-surrounded-by-whitespace.md#monthly-text-surrounded-by-whitespace)|monitoring|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_surrounded_by_whitespace](./text-surrounded-by-whitespace.md#daily-partition-text-surrounded-by-whitespace)|partitioned|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_surrounded_by_whitespace](./text-surrounded-by-whitespace.md#monthly-partition-text-surrounded-by-whitespace)|partitioned|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_surrounded_by_whitespace_percent](./text-surrounded-by-whitespace-percent.md#profile-text-surrounded-by-whitespace-percent)|profiling|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage|advanced|
|[daily_text_surrounded_by_whitespace_percent](./text-surrounded-by-whitespace-percent.md#daily-text-surrounded-by-whitespace-percent)|monitoring|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_surrounded_by_whitespace_percent](./text-surrounded-by-whitespace-percent.md#monthly-text-surrounded-by-whitespace-percent)|monitoring|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_surrounded_by_whitespace_percent](./text-surrounded-by-whitespace-percent.md#daily-partition-text-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_surrounded_by_whitespace_percent](./text-surrounded-by-whitespace-percent.md#monthly-partition-text-surrounded-by-whitespace-percent)|partitioned|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_valid_country_code_percent](./text-valid-country-code-percent.md#profile-text-valid-country-code-percent)|profiling|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage|advanced|
|[daily_text_valid_country_code_percent](./text-valid-country-code-percent.md#daily-text-valid-country-code-percent)|monitoring|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_valid_country_code_percent](./text-valid-country-code-percent.md#monthly-text-valid-country-code-percent)|monitoring|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_valid_country_code_percent](./text-valid-country-code-percent.md#daily-partition-text-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_valid_country_code_percent](./text-valid-country-code-percent.md#monthly-partition-text-valid-country-code-percent)|partitioned|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[profile_text_valid_currency_code_percent](./text-valid-currency-code-percent.md#profile-text-valid-currency-code-percent)|profiling|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage|advanced|
|[daily_text_valid_currency_code_percent](./text-valid-currency-code-percent.md#daily-text-valid-currency-code-percent)|monitoring|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|advanced|
|[monthly_text_valid_currency_code_percent](./text-valid-currency-code-percent.md#monthly-text-valid-currency-code-percent)|monitoring|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|advanced|
|[daily_partition_text_valid_currency_code_percent](./text-valid-currency-code-percent.md#daily-partition-text-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|advanced|
|[monthly_partition_text_valid_currency_code_percent](./text-valid-currency-code-percent.md#monthly-partition-text-valid-currency-code-percent)|partitioned|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|advanced|







