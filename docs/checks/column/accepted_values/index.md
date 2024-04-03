# column level accepted values data quality checks

This is a list of accepted_values column data quality checks supported by DQOps and a brief description of what data quality issued they detect.




## column-level accepted_values checks
Verifies if all values in the column are from a set of known values, such as country codes.

### [text found in set percent](./text-found-in-set-percent.md)
A column-level check that calculates the percentage of rows for which the tested text column contains a value from a set of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 The check raises a data quality issue when the percentage of rows with a not null column value that is not expected (not one of the values in the expected_values set)
 is below the expected threshold. For example, 99% of rows should have values from the defined domain.
 This data quality check is useful for checking text columns that have a small number of unique values, and all the values should come from a set of expected values.
 For example, testing country, state, currency, gender, type, and department columns whose expected values are known.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_found_in_set_percent`</span>](./text-found-in-set-percent.md#profile-text-found-in-set-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_text_found_in_set_percent`</span>](./text-found-in-set-percent.md#daily-text-found-in-set-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_text_found_in_set_percent`</span>](./text-found-in-set-percent.md#monthly-text-found-in-set-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_text_found_in_set_percent`</span>](./text-found-in-set-percent.md#daily-partition-text-found-in-set-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_text_found_in_set_percent`</span>](./text-found-in-set-percent.md#monthly-partition-text-found-in-set-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [number found in set percent](./number-found-in-set-percent.md)
A column-level check that calculates the percentage of rows for which the tested numeric column contains a value from a set of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 The check raises a data quality issue when the percentage of rows with a not null column value that is not expected (not one of the values in the expected_values set)
 is below the expected threshold. For example, 99% of rows should have values from the defined domain.
 This data quality check is useful for checking numeric columns that store numeric codes (such as status codes) to see if the only values found in the column are from the set of expected values.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_number_found_in_set_percent`</span>](./number-found-in-set-percent.md#profile-number-found-in-set-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_number_found_in_set_percent`</span>](./number-found-in-set-percent.md#daily-number-found-in-set-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_number_found_in_set_percent`</span>](./number-found-in-set-percent.md#monthly-number-found-in-set-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|:material-check-bold:|
|[<span class="no-wrap-code">`daily_partition_number_found_in_set_percent`</span>](./number-found-in-set-percent.md#daily-partition-number-found-in-set-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|:material-check-bold:|
|[<span class="no-wrap-code">`monthly_partition_number_found_in_set_percent`</span>](./number-found-in-set-percent.md#monthly-partition-number-found-in-set-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|:material-check-bold:|



### [expected text values in use count](./expected-text-values-in-use-count.md)
A column-level check that counts unique values in a text column and counts how many values out of a list of expected string values were found in the column.
 The check raises a data quality issue when the threshold for the maximum number of missing has been exceeded (too many expected values were not found in the column).
 This check is useful for analysing columns with a low number of unique values, such as status codes, to detect whether all status codes are used in any row.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_expected_text_values_in_use_count`</span>](./expected-text-values-in-use-count.md#profile-expected-text-values-in-use-count)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).| |
|[<span class="no-wrap-code">`daily_expected_text_values_in_use_count`</span>](./expected-text-values-in-use-count.md#daily-expected-text-values-in-use-count)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_expected_text_values_in_use_count`</span>](./expected-text-values-in-use-count.md#monthly-expected-text-values-in-use-count)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_expected_text_values_in_use_count`</span>](./expected-text-values-in-use-count.md#daily-partition-expected-text-values-in-use-count)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_expected_text_values_in_use_count`</span>](./expected-text-values-in-use-count.md#monthly-partition-expected-text-values-in-use-count)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each monthly partition.| |



### [expected texts in top values count](./expected-texts-in-top-values-count.md)
A column-level check that counts how many expected text values are among the TOP most popular values in the column.
 The check will first count the number of occurrences of each column&#x27;s value and will pick the TOP X most popular values (configurable by the &#x27;top&#x27; parameter).
 Then, it will compare the list of most popular values to the given list of expected values that should be most popular.
 This check will verify how many supposed most popular values (provided in the &#x27;expected_values&#x27; list) were not found in the top X most popular values in the column.
 This check is helpful in analyzing string columns with frequently occurring values, such as country codes for countries with the most customers.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_expected_texts_in_top_values_count`</span>](./expected-texts-in-top-values-count.md#profile-expected-texts-in-top-values-count)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the top X most popular column values contain all values from a list of expected values.| |
|[<span class="no-wrap-code">`daily_expected_texts_in_top_values_count`</span>](./expected-texts-in-top-values-count.md#daily-expected-texts-in-top-values-count)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_expected_texts_in_top_values_count`</span>](./expected-texts-in-top-values-count.md#monthly-expected-texts-in-top-values-count)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_expected_texts_in_top_values_count`</span>](./expected-texts-in-top-values-count.md#daily-partition-expected-texts-in-top-values-count)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the top X most popular column values contain all values from a list of expected values. Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_expected_texts_in_top_values_count`</span>](./expected-texts-in-top-values-count.md#monthly-partition-expected-texts-in-top-values-count)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the top X most popular column values contain all values from a list of expected values. Stores a separate data quality check result for each monthly partition.| |



### [expected numbers in use count](./expected-numbers-in-use-count.md)
A column-level check that counts unique values in a numeric column and counts how many values out of a list of expected numeric values were found in the column.
 The check raises a data quality issue when the threshold for the maximum number of missing has been exceeded (too many expected values were not found in the column).
 This check is useful for analysing columns with a low number of unique values, such as status codes, to detect whether all status codes are used in any row.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_expected_numbers_in_use_count`</span>](./expected-numbers-in-use-count.md#profile-expected-numbers-in-use-count)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).| |
|[<span class="no-wrap-code">`daily_expected_numbers_in_use_count`</span>](./expected-numbers-in-use-count.md#daily-expected-numbers-in-use-count)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_expected_numbers_in_use_count`</span>](./expected-numbers-in-use-count.md#monthly-expected-numbers-in-use-count)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_expected_numbers_in_use_count`</span>](./expected-numbers-in-use-count.md#daily-partition-expected-numbers-in-use-count)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_expected_numbers_in_use_count`</span>](./expected-numbers-in-use-count.md#monthly-partition-expected-numbers-in-use-count)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each monthly partition.| |



### [text valid country code percent](./text-valid-country-code-percent.md)
This check measures the percentage of text values that are valid two-letter country codes.
 It raises a data quality issue when the percentage of valid country codes (excluding null values) falls below a minimum accepted rate.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_valid_country_code_percent`</span>](./text-valid-country-code-percent.md#profile-text-valid-country-code-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage| |
|[<span class="no-wrap-code">`daily_text_valid_country_code_percent`</span>](./text-valid-country-code-percent.md#daily-text-valid-country-code-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_valid_country_code_percent`</span>](./text-valid-country-code-percent.md#monthly-text-valid-country-code-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_valid_country_code_percent`</span>](./text-valid-country-code-percent.md#daily-partition-text-valid-country-code-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_valid_country_code_percent`</span>](./text-valid-country-code-percent.md#monthly-partition-text-valid-country-code-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |



### [text valid currency code percent](./text-valid-currency-code-percent.md)
This check measures the percentage of text values that are valid currency names. It raises a data quality issue when the percentage of valid currency names (excluding null values) falls below a minimum accepted rate.


| Data quality check name | Check type | Description | Standard |
|-------------------------|------------|-------------|----------|
|[<span class="no-wrap-code">`profile_text_valid_currency_code_percent`</span>](./text-valid-currency-code-percent.md#profile-text-valid-currency-code-percent)|[profiling](../../../dqo-concepts/definition-of-data-quality-checks/data-profiling-checks.md)|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage| |
|[<span class="no-wrap-code">`daily_text_valid_currency_code_percent`</span>](./text-valid-currency-code-percent.md#daily-text-valid-currency-code-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`monthly_text_valid_currency_code_percent`</span>](./text-valid-currency-code-percent.md#monthly-text-valid-currency-code-percent)|[monitoring](../../../dqo-concepts/definition-of-data-quality-checks/data-observability-monitoring-checks.md)|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.| |
|[<span class="no-wrap-code">`daily_partition_text_valid_currency_code_percent`</span>](./text-valid-currency-code-percent.md#daily-partition-text-valid-currency-code-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.| |
|[<span class="no-wrap-code">`monthly_partition_text_valid_currency_code_percent`</span>](./text-valid-currency-code-percent.md#monthly-partition-text-valid-currency-code-percent)|[partitioned](../../../dqo-concepts/definition-of-data-quality-checks/partition-checks.md)|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.| |







