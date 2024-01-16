# column level accepted values data quality checks

This is a list of accepted_values column data quality checks supported by DQOps and a brief description of what data quality issued they detect.





## **accepted_values**


| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_text_found_in_set_percent`</span>](./text-found-in-set-percent.md#profile-text-found-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|*standard*|
|[<span class="no-wrap-code">`daily_text_found_in_set_percent`</span>](./text-found-in-set-percent.md#daily-text-found-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|*standard*|
|[<span class="no-wrap-code">`monthly_text_found_in_set_percent`</span>](./text-found-in-set-percent.md#monthly-text-found-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|*standard*|
|[<span class="no-wrap-code">`daily_partition_text_found_in_set_percent`</span>](./text-found-in-set-percent.md#daily-partition-text-found-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|*standard*|
|[<span class="no-wrap-code">`monthly_partition_text_found_in_set_percent`</span>](./text-found-in-set-percent.md#monthly-partition-text-found-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|*standard*|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_number_found_in_set_percent`</span>](./number-found-in-set-percent.md#profile-number-found-in-set-percent)|profiling|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|*standard*|
|[<span class="no-wrap-code">`daily_number_found_in_set_percent`</span>](./number-found-in-set-percent.md#daily-number-found-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|*standard*|
|[<span class="no-wrap-code">`monthly_number_found_in_set_percent`</span>](./number-found-in-set-percent.md#monthly-number-found-in-set-percent)|monitoring|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|*standard*|
|[<span class="no-wrap-code">`daily_partition_number_found_in_set_percent`</span>](./number-found-in-set-percent.md#daily-partition-number-found-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|*standard*|
|[<span class="no-wrap-code">`monthly_partition_number_found_in_set_percent`</span>](./number-found-in-set-percent.md#monthly-partition-number-found-in-set-percent)|partitioned|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|*standard*|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_expected_text_values_in_use_count`</span>](./expected-text-values-in-use-count.md#profile-expected-text-values-in-use-count)|profiling|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|*advanced*|
|[<span class="no-wrap-code">`daily_expected_text_values_in_use_count`</span>](./expected-text-values-in-use-count.md#daily-expected-text-values-in-use-count)|monitoring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|*advanced*|
|[<span class="no-wrap-code">`monthly_expected_text_values_in_use_count`</span>](./expected-text-values-in-use-count.md#monthly-expected-text-values-in-use-count)|monitoring|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each month when the data quality check was evaluated.|*advanced*|
|[<span class="no-wrap-code">`daily_partition_expected_text_values_in_use_count`</span>](./expected-text-values-in-use-count.md#daily-partition-expected-text-values-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each daily partition.|*advanced*|
|[<span class="no-wrap-code">`monthly_partition_expected_text_values_in_use_count`</span>](./expected-text-values-in-use-count.md#monthly-partition-expected-text-values-in-use-count)|partitioned|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each monthly partition.|*advanced*|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_expected_texts_in_top_values_count`</span>](./expected-texts-in-top-values-count.md#profile-expected-texts-in-top-values-count)|profiling|Verifies that the top X most popular column values contain all values from a list of expected values.|*advanced*|
|[<span class="no-wrap-code">`daily_expected_texts_in_top_values_count`</span>](./expected-texts-in-top-values-count.md#daily-expected-texts-in-top-values-count)|monitoring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.|*advanced*|
|[<span class="no-wrap-code">`monthly_expected_texts_in_top_values_count`</span>](./expected-texts-in-top-values-count.md#monthly-expected-texts-in-top-values-count)|monitoring|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each month when the data quality check was evaluated.|*advanced*|
|[<span class="no-wrap-code">`daily_partition_expected_texts_in_top_values_count`</span>](./expected-texts-in-top-values-count.md#daily-partition-expected-texts-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Stores a separate data quality check result for each daily partition.|*advanced*|
|[<span class="no-wrap-code">`monthly_partition_expected_texts_in_top_values_count`</span>](./expected-texts-in-top-values-count.md#monthly-partition-expected-texts-in-top-values-count)|partitioned|Verifies that the top X most popular column values contain all values from a list of expected values. Stores a separate data quality check result for each monthly partition.|*advanced*|



| Data quality check name | Check type | Description | Class |
|-------------------------|------------|-------------|-------|
|[<span class="no-wrap-code">`profile_expected_numbers_in_use_count`</span>](./expected-numbers-in-use-count.md#profile-expected-numbers-in-use-count)|profiling|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|*advanced*|
|[<span class="no-wrap-code">`daily_expected_numbers_in_use_count`</span>](./expected-numbers-in-use-count.md#daily-expected-numbers-in-use-count)|monitoring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|*advanced*|
|[<span class="no-wrap-code">`monthly_expected_numbers_in_use_count`</span>](./expected-numbers-in-use-count.md#monthly-expected-numbers-in-use-count)|monitoring|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each month when the data quality check was evaluated.|*advanced*|
|[<span class="no-wrap-code">`daily_partition_expected_numbers_in_use_count`</span>](./expected-numbers-in-use-count.md#daily-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each daily partition.|*advanced*|
|[<span class="no-wrap-code">`monthly_partition_expected_numbers_in_use_count`</span>](./expected-numbers-in-use-count.md#monthly-partition-expected-numbers-in-use-count)|partitioned|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each monthly partition.|*advanced*|







