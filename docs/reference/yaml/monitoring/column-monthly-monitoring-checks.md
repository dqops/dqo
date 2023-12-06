
## ColumnStringsMonthlyMonitoringChecksSpec  
Container of strings data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_string_max_length](../../../../checks/column/strings/string-max-length/)|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMaxLengthCheckSpec](../../../../checks/column/strings/string-max-length/)| | | |
|[monthly_string_min_length](../../../../checks/column/strings/string-min-length/)|Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMinLengthCheckSpec](../../../../checks/column/strings/string-min-length/)| | | |
|[monthly_string_mean_length](../../../../checks/column/strings/string-mean-length/)|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMeanLengthCheckSpec](../../../../checks/column/strings/string-mean-length/)| | | |
|[monthly_string_length_below_min_length_count](../../../../checks/column/strings/string-length-below-min-length-count/)|The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthCountCheckSpec](../../../../checks/column/strings/string-length-below-min-length-count/)| | | |
|[monthly_string_length_below_min_length_percent](../../../../checks/column/strings/string-length-below-min-length-percent/)|The check counts percentage of those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](../../../../checks/column/strings/string-length-below-min-length-percent/)| | | |
|[monthly_string_length_above_max_length_count](../../../../checks/column/strings/string-length-above-max-length-count/)|The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](../../../../checks/column/strings/string-length-above-max-length-count/)| | | |
|[monthly_string_length_above_max_length_percent](../../../../checks/column/strings/string-length-above-max-length-percent/)|The check counts percentage of those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](../../../../checks/column/strings/string-length-above-max-length-percent/)| | | |
|[monthly_string_length_in_range_percent](../../../../checks/column/strings/string-length-in-range-percent/)|The check counts percentage of those strings with length in the range provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthInRangePercentCheckSpec](../../../../checks/column/strings/string-length-in-range-percent/)| | | |
|[monthly_string_empty_count](../../../../checks/column/strings/string-empty-count/)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringEmptyCountCheckSpec](../../../../checks/column/strings/string-empty-count/)| | | |
|[monthly_string_empty_percent](../../../../checks/column/strings/string-empty-percent/)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringEmptyPercentCheckSpec](../../../../checks/column/strings/string-empty-percent/)| | | |
|[monthly_string_valid_dates_percent](../../../../checks/column/strings/string-valid-dates-percent/)|Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidDatesPercentCheckSpec](../../../../checks/column/strings/string-valid-dates-percent/)| | | |
|[monthly_string_whitespace_count](../../../../checks/column/strings/string-whitespace-count/)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringWhitespaceCountCheckSpec](../../../../checks/column/strings/string-whitespace-count/)| | | |
|[monthly_string_whitespace_percent](../../../../checks/column/strings/string-whitespace-percent/)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringWhitespacePercentCheckSpec](../../../../checks/column/strings/string-whitespace-percent/)| | | |
|[monthly_string_surrounded_by_whitespace_count](../../../../checks/column/strings/string-surrounded-by-whitespace-count/)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](../../../../checks/column/strings/string-surrounded-by-whitespace-count/)| | | |
|[monthly_string_surrounded_by_whitespace_percent](../../../../checks/column/strings/string-surrounded-by-whitespace-percent/)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](../../../../checks/column/strings/string-surrounded-by-whitespace-percent/)| | | |
|[monthly_string_null_placeholder_count](../../../../checks/column/strings/string-null-placeholder-count/)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNullPlaceholderCountCheckSpec](../../../../checks/column/strings/string-null-placeholder-count/)| | | |
|[monthly_string_null_placeholder_percent](../../../../checks/column/strings/string-null-placeholder-percent/)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNullPlaceholderPercentCheckSpec](../../../../checks/column/strings/string-null-placeholder-percent/)| | | |
|[monthly_string_boolean_placeholder_percent](../../../../checks/column/strings/string-boolean-placeholder-percent/)|Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringBooleanPlaceholderPercentCheckSpec](../../../../checks/column/strings/string-boolean-placeholder-percent/)| | | |
|[monthly_string_parsable_to_integer_percent](../../../../checks/column/strings/string-parsable-to-integer-percent/)|Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringParsableToIntegerPercentCheckSpec](../../../../checks/column/strings/string-parsable-to-integer-percent/)| | | |
|[monthly_string_parsable_to_float_percent](../../../../checks/column/strings/string-parsable-to-float-percent/)|Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringParsableToFloatPercentCheckSpec](../../../../checks/column/strings/string-parsable-to-float-percent/)| | | |
|[monthly_expected_strings_in_use_count](../../../../checks/column/strings/expected-strings-in-use-count/)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnExpectedStringsInUseCountCheckSpec](../../../../checks/column/strings/expected-strings-in-use-count/)| | | |
|[monthly_string_value_in_set_percent](../../../../checks/column/strings/string-value-in-set-percent/)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValueInSetPercentCheckSpec](../../../../checks/column/strings/string-value-in-set-percent/)| | | |
|[monthly_string_valid_country_code_percent](../../../../checks/column/strings/string-valid-country-code-percent/)|Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidCountryCodePercentCheckSpec](../../../../checks/column/strings/string-valid-country-code-percent/)| | | |
|[monthly_string_valid_currency_code_percent](../../../../checks/column/strings/string-valid-currency-code-percent/)|Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidCurrencyCodePercentCheckSpec](../../../../checks/column/strings/string-valid-currency-code-percent/)| | | |
|[monthly_string_invalid_email_count](../../../../checks/column/strings/string-invalid-email-count/)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidEmailCountCheckSpec](../../../../checks/column/strings/string-invalid-email-count/)| | | |
|[monthly_string_invalid_uuid_count](../../../../checks/column/strings/string-invalid-uuid-count/)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidUuidCountCheckSpec](../../../../checks/column/strings/string-invalid-uuid-count/)| | | |
|[monthly_string_valid_uuid_percent](../../../../checks/column/strings/string-valid-uuid-percent/)|Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidUuidPercentCheckSpec](../../../../checks/column/strings/string-valid-uuid-percent/)| | | |
|[monthly_string_invalid_ip4_address_count](../../../../checks/column/strings/string-invalid-ip4-address-count/)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidIp4AddressCountCheckSpec](../../../../checks/column/strings/string-invalid-ip4-address-count/)| | | |
|[monthly_string_invalid_ip6_address_count](../../../../checks/column/strings/string-invalid-ip6-address-count/)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidIp6AddressCountCheckSpec](../../../../checks/column/strings/string-invalid-ip6-address-count/)| | | |
|[monthly_string_not_match_regex_count](../../../../checks/column/strings/string-not-match-regex-count/)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNotMatchRegexCountCheckSpec](../../../../checks/column/strings/string-not-match-regex-count/)| | | |
|[monthly_string_match_regex_percent](../../../../checks/column/strings/string-match-regex-percent/)|Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMatchRegexPercentCheckSpec](../../../../checks/column/strings/string-match-regex-percent/)| | | |
|[monthly_string_not_match_date_regex_count](../../../../checks/column/strings/string-not-match-date-regex-count/)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNotMatchDateRegexCountCheckSpec](../../../../checks/column/strings/string-not-match-date-regex-count/)| | | |
|[monthly_string_match_date_regex_percent](../../../../checks/column/strings/string-match-date-regex-percent/)|Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMatchDateRegexPercentCheckSpec](../../../../checks/column/strings/string-match-date-regex-percent/)| | | |
|[monthly_string_match_name_regex_percent](../../../../checks/column/strings/string-match-name-regex-percent/)|Verifies that the percentage of strings matching the name regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMatchNameRegexPercentCheckSpec](../../../../checks/column/strings/string-match-name-regex-percent/)| | | |
|[monthly_expected_strings_in_top_values_count](../../../../checks/column/strings/expected-strings-in-top-values-count/)|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnExpectedStringsInTopValuesCountCheckSpec](../../../../checks/column/strings/expected-strings-in-top-values-count/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnIntegrityMonthlyMonitoringChecksSpec  
Container of integrity data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_foreign_key_not_match_count](../../../../checks/column/integrity/foreign-key-not-match-count/)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](../../../../checks/column/integrity/foreign-key-not-match-count/)| | | |
|[monthly_foreign_key_match_percent](../../../../checks/column/integrity/foreign-key-match-percent/)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](../../../../checks/column/integrity/foreign-key-match-percent/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnUniquenessMonthlyMonitoringChecksSpec  
Container of uniqueness data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_distinct_count](../../../../checks/column/uniqueness/distinct-count/)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDistinctCountCheckSpec](../../../../checks/column/uniqueness/distinct-count/)| | | |
|[monthly_distinct_percent](../../../../checks/column/uniqueness/distinct-percent/)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDistinctPercentCheckSpec](../../../../checks/column/uniqueness/distinct-percent/)| | | |
|[monthly_duplicate_count](../../../../checks/column/uniqueness/duplicate-count/)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDuplicateCountCheckSpec](../../../../checks/column/uniqueness/duplicate-count/)| | | |
|[monthly_duplicate_percent](../../../../checks/column/uniqueness/duplicate-percent/)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDuplicatePercentCheckSpec](../../../../checks/column/uniqueness/duplicate-percent/)| | | |
|[monthly_anomaly_differencing_distinct_count_30_days](../../../../checks/column/uniqueness/anomaly-differencing-distinct-count-30-days/)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec](../../../../checks/column/uniqueness/anomaly-differencing-distinct-count-30-days/)| | | |
|[monthly_anomaly_differencing_distinct_count](../../../../checks/column/uniqueness/anomaly-differencing-distinct-count/)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyDifferencingDistinctCountCheckSpec](../../../../checks/column/uniqueness/anomaly-differencing-distinct-count/)| | | |
|[monthly_anomaly_stationary_distinct_percent_30_days](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent-30-days/)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent-30-days/)| | | |
|[monthly_anomaly_stationary_distinct_percent](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent/)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryDistinctPercentCheckSpec](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent/)| | | |
|[monthly_change_distinct_count](../../../../checks/column/uniqueness/change-distinct-count/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctCountCheckSpec](../../../../checks/column/uniqueness/change-distinct-count/)| | | |
|[monthly_change_distinct_count_since_7_days](../../../../checks/column/uniqueness/change-distinct-count-since-7-days/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctCountSince7DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-7-days/)| | | |
|[monthly_change_distinct_count_since_30_days](../../../../checks/column/uniqueness/change-distinct-count-since-30-days/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctCountSince30DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-30-days/)| | | |
|[monthly_change_distinct_count_since_yesterday](../../../../checks/column/uniqueness/change-distinct-count-since-yesterday/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctCountSinceYesterdayCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-yesterday/)| | | |
|[monthly_change_distinct_percent](../../../../checks/column/uniqueness/change-distinct-percent/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctPercentCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent/)| | | |
|[monthly_change_distinct_percent_since_7_days](../../../../checks/column/uniqueness/change-distinct-percent-since-7-days/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctPercentSince7DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-7-days/)| | | |
|[monthly_change_distinct_percent_since_30_days](../../../../checks/column/uniqueness/change-distinct-percent-since-30-days/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctPercentSince30DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-30-days/)| | | |
|[monthly_change_distinct_percent_since_yesterday](../../../../checks/column/uniqueness/change-distinct-percent-since-yesterday/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctPercentSinceYesterdayCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-yesterday/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnNullsMonthlyMonitoringChecksSpec  
Container of nulls data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_nulls_count](../../../../checks/column/nulls/nulls-count/)|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNullsCountCheckSpec](../../../../checks/column/nulls/nulls-count/)| | | |
|[monthly_nulls_percent](../../../../checks/column/nulls/nulls-percent/)|Verifies that the percentage of null values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNullsPercentCheckSpec](../../../../checks/column/nulls/nulls-percent/)| | | |
|[monthly_not_nulls_count](../../../../checks/column/nulls/not-nulls-count/)|Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNotNullsCountCheckSpec](../../../../checks/column/nulls/not-nulls-count/)| | | |
|[monthly_not_nulls_percent](../../../../checks/column/nulls/not-nulls-percent/)|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNotNullsPercentCheckSpec](../../../../checks/column/nulls/not-nulls-percent/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnNumericMonthlyMonitoringChecksSpec  
Container of built-in preconfigured data quality monitoring on a column level that are checking numeric values at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_negative_count](../../../../checks/column/numeric/negative-count/)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNegativeCountCheckSpec](../../../../checks/column/numeric/negative-count/)| | | |
|[monthly_negative_percent](../../../../checks/column/numeric/negative-percent/)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNegativePercentCheckSpec](../../../../checks/column/numeric/negative-percent/)| | | |
|[monthly_non_negative_count](../../../../checks/column/numeric/non-negative-count/)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNonNegativeCountCheckSpec](../../../../checks/column/numeric/non-negative-count/)| | | |
|[monthly_non_negative_percent](../../../../checks/column/numeric/non-negative-percent/)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNonNegativePercentCheckSpec](../../../../checks/column/numeric/non-negative-percent/)| | | |
|[monthly_expected_numbers_in_use_count](../../../../checks/column/numeric/expected-numbers-in-use-count/)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnExpectedNumbersInUseCountCheckSpec](../../../../checks/column/numeric/expected-numbers-in-use-count/)| | | |
|[monthly_number_value_in_set_percent](../../../../checks/column/numeric/number-value-in-set-percent/)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNumberValueInSetPercentCheckSpec](../../../../checks/column/numeric/number-value-in-set-percent/)| | | |
|[monthly_values_in_range_numeric_percent](../../../../checks/column/numeric/values-in-range-numeric-percent/)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValuesInRangeNumericPercentCheckSpec](../../../../checks/column/numeric/values-in-range-numeric-percent/)| | | |
|[monthly_values_in_range_integers_percent](../../../../checks/column/numeric/values-in-range-integers-percent/)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValuesInRangeIntegersPercentCheckSpec](../../../../checks/column/numeric/values-in-range-integers-percent/)| | | |
|[monthly_value_below_min_value_count](../../../../checks/column/numeric/value-below-min-value-count/)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueBelowMinValueCountCheckSpec](../../../../checks/column/numeric/value-below-min-value-count/)| | | |
|[monthly_value_below_min_value_percent](../../../../checks/column/numeric/value-below-min-value-percent/)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueBelowMinValuePercentCheckSpec](../../../../checks/column/numeric/value-below-min-value-percent/)| | | |
|[monthly_value_above_max_value_count](../../../../checks/column/numeric/value-above-max-value-count/)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueAboveMaxValueCountCheckSpec](../../../../checks/column/numeric/value-above-max-value-count/)| | | |
|[monthly_value_above_max_value_percent](../../../../checks/column/numeric/value-above-max-value-percent/)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueAboveMaxValuePercentCheckSpec](../../../../checks/column/numeric/value-above-max-value-percent/)| | | |
|[monthly_max_in_range](../../../../checks/column/numeric/max-in-range/)|Verifies that the maximal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMaxInRangeCheckSpec](../../../../checks/column/numeric/max-in-range/)| | | |
|[monthly_min_in_range](../../../../checks/column/numeric/min-in-range/)|Verifies that the minimal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMinInRangeCheckSpec](../../../../checks/column/numeric/min-in-range/)| | | |
|[monthly_mean_in_range](../../../../checks/column/numeric/mean-in-range/)|Verifies that the average (mean) of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMeanInRangeCheckSpec](../../../../checks/column/numeric/mean-in-range/)| | | |
|[monthly_percentile_in_range](../../../../checks/column/numeric/percentile-in-range/)|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentileInRangeCheckSpec](../../../../checks/column/numeric/percentile-in-range/)| | | |
|[monthly_median_in_range](../../profiling/column-profiling-checks/#ColumnMedianInRangeCheckSpec)|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMedianInRangeCheckSpec](../../profiling/column-profiling-checks/#ColumnMedianInRangeCheckSpec)| | | |
|[monthly_percentile_10_in_range](../../profiling/column-profiling-checks/#ColumnPercentile10InRangeCheckSpec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile10InRangeCheckSpec](../../profiling/column-profiling-checks/#ColumnPercentile10InRangeCheckSpec)| | | |
|[monthly_percentile_25_in_range](../../profiling/column-profiling-checks/#ColumnPercentile25InRangeCheckSpec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile25InRangeCheckSpec](../../profiling/column-profiling-checks/#ColumnPercentile25InRangeCheckSpec)| | | |
|[monthly_percentile_75_in_range](../../profiling/column-profiling-checks/#ColumnPercentile75InRangeCheckSpec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile75InRangeCheckSpec](../../profiling/column-profiling-checks/#ColumnPercentile75InRangeCheckSpec)| | | |
|[monthly_percentile_90_in_range](../../profiling/column-profiling-checks/#ColumnPercentile90InRangeCheckSpec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile90InRangeCheckSpec](../../profiling/column-profiling-checks/#ColumnPercentile90InRangeCheckSpec)| | | |
|[monthly_sample_stddev_in_range](../../../../checks/column/numeric/sample-stddev-in-range/)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSampleStddevInRangeCheckSpec](../../../../checks/column/numeric/sample-stddev-in-range/)| | | |
|[monthly_population_stddev_in_range](../../../../checks/column/numeric/population-stddev-in-range/)|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPopulationStddevInRangeCheckSpec](../../../../checks/column/numeric/population-stddev-in-range/)| | | |
|[monthly_sample_variance_in_range](../../../../checks/column/numeric/sample-variance-in-range/)|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSampleVarianceInRangeCheckSpec](../../../../checks/column/numeric/sample-variance-in-range/)| | | |
|[monthly_population_variance_in_range](../../../../checks/column/numeric/population-variance-in-range/)|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPopulationVarianceInRangeCheckSpec](../../../../checks/column/numeric/population-variance-in-range/)| | | |
|[monthly_sum_in_range](../../../../checks/column/numeric/sum-in-range/)|Verifies that the sum of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSumInRangeCheckSpec](../../../../checks/column/numeric/sum-in-range/)| | | |
|[monthly_invalid_latitude_count](../../../../checks/column/numeric/invalid-latitude-count/)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnInvalidLatitudeCountCheckSpec](../../../../checks/column/numeric/invalid-latitude-count/)| | | |
|[monthly_valid_latitude_percent](../../../../checks/column/numeric/valid-latitude-percent/)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValidLatitudePercentCheckSpec](../../../../checks/column/numeric/valid-latitude-percent/)| | | |
|[monthly_invalid_longitude_count](../../../../checks/column/numeric/invalid-longitude-count/)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnInvalidLongitudeCountCheckSpec](../../../../checks/column/numeric/invalid-longitude-count/)| | | |
|[monthly_valid_longitude_percent](../../../../checks/column/numeric/valid-longitude-percent/)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValidLongitudePercentCheckSpec](../../../../checks/column/numeric/valid-longitude-percent/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnComparisonMonthlyMonitoringChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [ColumnComparisonMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnComparisonMonthlyMonitoringChecksSpec)]| | | |









___  

## ColumnBoolMonthlyMonitoringChecksSpec  
Container of boolean monitoring data quality checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_true_percent](../../../../checks/column/bool/true-percent/)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnTruePercentCheckSpec](../../../../checks/column/bool/true-percent/)| | | |
|[monthly_false_percent](../../../../checks/column/bool/false-percent/)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnFalsePercentCheckSpec](../../../../checks/column/bool/false-percent/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnDatetimeMonthlyMonitoringChecksSpec  
Container of date-time data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_date_match_format_percent](../../../../checks/column/datetime/date-match-format-percent/)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly monitoring.|[ColumnDatetimeDateMatchFormatPercentCheckSpec](../../../../checks/column/datetime/date-match-format-percent/)| | | |
|[monthly_date_values_in_future_percent](../../../../checks/column/datetime/date-values-in-future-percent/)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDateValuesInFuturePercentCheckSpec](../../../../checks/column/datetime/date-values-in-future-percent/)| | | |
|[monthly_datetime_value_in_range_date_percent](../../../../checks/column/datetime/datetime-value-in-range-date-percent/)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](../../../../checks/column/datetime/datetime-value-in-range-date-percent/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnPiiMonthlyMonitoringChecksSpec  
Container of PII data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_contains_usa_phone_percent](../../../../checks/column/pii/contains-usa-phone-percent/)|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsUsaPhonePercentCheckSpec](../../../../checks/column/pii/contains-usa-phone-percent/)| | | |
|[monthly_contains_usa_zipcode_percent](../../../../checks/column/pii/contains-usa-zipcode-percent/)|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](../../../../checks/column/pii/contains-usa-zipcode-percent/)| | | |
|[monthly_contains_email_percent](../../../../checks/column/pii/contains-email-percent/)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsEmailPercentCheckSpec](../../../../checks/column/pii/contains-email-percent/)| | | |
|[monthly_contains_ip4_percent](../../../../checks/column/pii/contains-ip4-percent/)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsIp4PercentCheckSpec](../../../../checks/column/pii/contains-ip4-percent/)| | | |
|[monthly_contains_ip6_percent](../../../../checks/column/pii/contains-ip6-percent/)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsIp6PercentCheckSpec](../../../../checks/column/pii/contains-ip6-percent/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnDatatypeMonthlyMonitoringChecksSpec  
Container of datatype data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_string_datatype_detected](../../../../checks/column/datatype/string-datatype-detected/)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDatatypeStringDatatypeDetectedCheckSpec](../../../../checks/column/datatype/string-datatype-detected/)| | | |
|[monthly_string_datatype_changed](../../../../checks/column/datatype/string-datatype-changed/)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDatatypeStringDatatypeChangedCheckSpec](../../../../checks/column/datatype/string-datatype-changed/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnSqlMonthlyMonitoringChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_sql_condition_passed_percent_on_column](../../../../checks/column/sql/sql-condition-passed-percent-on-column/)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSqlConditionPassedPercentCheckSpec](../../../../checks/column/sql/sql-condition-passed-percent-on-column/)| | | |
|[monthly_sql_condition_failed_count_on_column](../../../../checks/column/sql/sql-condition-failed-count-on-column/)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSqlConditionFailedCountCheckSpec](../../../../checks/column/sql/sql-condition-failed-count-on-column/)| | | |
|[monthly_sql_aggregate_expr_column](../../../../checks/column/sql/sql-aggregate-expr-column/)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSqlAggregateExprCheckSpec](../../../../checks/column/sql/sql-aggregate-expr-column/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnAnomalyMonthlyMonitoringChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_mean_change](../../../../checks/column/anomaly/mean-change/)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](../../../../checks/column/anomaly/mean-change/)| | | |
|[monthly_median_change](../../../../checks/column/anomaly/median-change/)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](../../../../checks/column/anomaly/median-change/)| | | |
|[monthly_sum_change](../../../../checks/column/anomaly/sum-change/)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](../../../../checks/column/anomaly/sum-change/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnAccuracyMonthlyMonitoringChecksSpec  
Container of accuracy data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_total_sum_match_percent](../../../../checks/column/accuracy/total-sum-match-percent/)|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyTotalSumMatchPercentCheckSpec](../../../../checks/column/accuracy/total-sum-match-percent/)| | | |
|[monthly_total_min_match_percent](../../../../checks/column/accuracy/total-min-match-percent/)|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyTotalMinMatchPercentCheckSpec](../../../../checks/column/accuracy/total-min-match-percent/)| | | |
|[monthly_total_max_match_percent](../../../../checks/column/accuracy/total-max-match-percent/)|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyTotalMaxMatchPercentCheckSpec](../../../../checks/column/accuracy/total-max-match-percent/)| | | |
|[monthly_total_average_match_percent](../../../../checks/column/accuracy/total-average-match-percent/)|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyTotalAverageMatchPercentCheckSpec](../../../../checks/column/accuracy/total-average-match-percent/)| | | |
|[monthly_total_not_null_count_match_percent](../../../../checks/column/accuracy/total-not-null-count-match-percent/)|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec](../../../../checks/column/accuracy/total-not-null-count-match-percent/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnSchemaMonthlyMonitoringChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking the column schema at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_column_exists](../../../../checks/column/schema/column-exists/)|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnSchemaColumnExistsCheckSpec](../../../../checks/column/schema/column-exists/)| | | |
|[monthly_column_type_changed](../../../../checks/column/schema/column-type-changed/)|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.|[ColumnSchemaTypeChangedCheckSpec](../../../../checks/column/schema/column-type-changed/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnMonthlyMonitoringCheckCategoriesSpec  
Container of column level monthly monitoring checks. Contains categories of monthly monitoring checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](../column-monthly-monitoring-checks/#ColumnNullsMonthlyMonitoringChecksSpec)|Monthly monitoring checks of nulls in the column|[ColumnNullsMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnNullsMonthlyMonitoringChecksSpec)| | | |
|[numeric](../column-monthly-monitoring-checks/#ColumnNumericMonthlyMonitoringChecksSpec)|Monthly monitoring checks of numeric in the column|[ColumnNumericMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnNumericMonthlyMonitoringChecksSpec)| | | |
|[strings](../column-monthly-monitoring-checks/#ColumnStringsMonthlyMonitoringChecksSpec)|Monthly monitoring checks of strings in the column|[ColumnStringsMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnStringsMonthlyMonitoringChecksSpec)| | | |
|[uniqueness](../column-monthly-monitoring-checks/#ColumnUniquenessMonthlyMonitoringChecksSpec)|Monthly monitoring checks of uniqueness in the column|[ColumnUniquenessMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnUniquenessMonthlyMonitoringChecksSpec)| | | |
|[datetime](../column-monthly-monitoring-checks/#ColumnDatetimeMonthlyMonitoringChecksSpec)|Monthly monitoring checks of datetime in the column|[ColumnDatetimeMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnDatetimeMonthlyMonitoringChecksSpec)| | | |
|[pii](../column-monthly-monitoring-checks/#ColumnPiiMonthlyMonitoringChecksSpec)|Monthly monitoring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnPiiMonthlyMonitoringChecksSpec)| | | |
|[sql](../column-monthly-monitoring-checks/#ColumnSqlMonthlyMonitoringChecksSpec)|Monthly monitoring checks of custom SQL checks in the column|[ColumnSqlMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnSqlMonthlyMonitoringChecksSpec)| | | |
|[bool](../column-monthly-monitoring-checks/#ColumnBoolMonthlyMonitoringChecksSpec)|Monthly monitoring checks of booleans in the column|[ColumnBoolMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnBoolMonthlyMonitoringChecksSpec)| | | |
|[integrity](../column-monthly-monitoring-checks/#ColumnIntegrityMonthlyMonitoringChecksSpec)|Monthly monitoring checks of integrity in the column|[ColumnIntegrityMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnIntegrityMonthlyMonitoringChecksSpec)| | | |
|[accuracy](../column-monthly-monitoring-checks/#ColumnAccuracyMonthlyMonitoringChecksSpec)|Monthly monitoring checks of accuracy in the column|[ColumnAccuracyMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnAccuracyMonthlyMonitoringChecksSpec)| | | |
|[datatype](../column-monthly-monitoring-checks/#ColumnDatatypeMonthlyMonitoringChecksSpec)|Monthly monitoring checks of datatype in the column|[ColumnDatatypeMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnDatatypeMonthlyMonitoringChecksSpec)| | | |
|[anomaly](../column-monthly-monitoring-checks/#ColumnAnomalyMonthlyMonitoringChecksSpec)|Monthly monitoring checks of anomaly in the column|[ColumnAnomalyMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnAnomalyMonthlyMonitoringChecksSpec)| | | |
|[schema](../column-monthly-monitoring-checks/#ColumnSchemaMonthlyMonitoringChecksSpec)|Monthly monitoring column schema checks|[ColumnSchemaMonthlyMonitoringChecksSpec](../column-monthly-monitoring-checks/#ColumnSchemaMonthlyMonitoringChecksSpec)| | | |
|[comparisons](../column-monthly-monitoring-checks/#ColumnComparisonMonthlyMonitoringChecksSpecMap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonMonthlyMonitoringChecksSpecMap](../column-monthly-monitoring-checks/#ColumnComparisonMonthlyMonitoringChecksSpecMap)| | | |
|[custom](../../profiling/table-profiling-checks/#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../../profiling/table-profiling-checks/#CustomCheckSpecMap)| | | |









___  

## ColumnComparisonMonthlyMonitoringChecksSpec  
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 This is the configuration for monthly monitoring checks that are counted in KPIs.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_sum_match](../../../../checks/column/comparisons/sum-match/)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonSumMatchCheckSpec](../../../../checks/column/comparisons/sum-match/)| | | |
|[monthly_min_match](../../../../checks/column/comparisons/min-match/)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonMinMatchCheckSpec](../../../../checks/column/comparisons/min-match/)| | | |
|[monthly_max_match](../../../../checks/column/comparisons/max-match/)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonMaxMatchCheckSpec](../../../../checks/column/comparisons/max-match/)| | | |
|[monthly_mean_match](../../../../checks/column/comparisons/mean-match/)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonMeanMatchCheckSpec](../../../../checks/column/comparisons/mean-match/)| | | |
|[monthly_not_null_count_match](../../../../checks/column/comparisons/not-null-count-match/)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonNotNullCountMatchCheckSpec](../../../../checks/column/comparisons/not-null-count-match/)| | | |
|[monthly_null_count_match](../../../../checks/column/comparisons/null-count-match/)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonNullCountMatchCheckSpec](../../../../checks/column/comparisons/null-count-match/)| | | |
|reference_column|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|string| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

