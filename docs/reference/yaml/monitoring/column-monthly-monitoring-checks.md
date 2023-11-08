
## ColumnComparisonMonthlyMonitoringChecksSpec  
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 This is the configuration for monthly monitoring checks that are counted in KPIs.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_sum_match](/docs/checks/column/comparisons/sum-match)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonSumMatchCheckSpec](/docs/checks/column/comparisons/sum-match)| | | |
|[monthly_min_match](/docs/checks/column/comparisons/min-match)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonMinMatchCheckSpec](/docs/checks/column/comparisons/min-match)| | | |
|[monthly_max_match](/docs/checks/column/comparisons/max-match)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonMaxMatchCheckSpec](/docs/checks/column/comparisons/max-match)| | | |
|[monthly_mean_match](/docs/checks/column/comparisons/mean-match)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonMeanMatchCheckSpec](/docs/checks/column/comparisons/mean-match)| | | |
|[monthly_not_null_count_match](/docs/checks/column/comparisons/not-null-count-match)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonNotNullCountMatchCheckSpec](/docs/checks/column/comparisons/not-null-count-match)| | | |
|[monthly_null_count_match](/docs/checks/column/comparisons/null-count-match)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonNullCountMatchCheckSpec](/docs/checks/column/comparisons/null-count-match)| | | |
|reference_column|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|string| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

## ColumnPiiMonthlyMonitoringChecksSpec  
Container of PII data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_contains_usa_phone_percent](/docs/checks/column/pii/contains-usa-phone-percent)|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsUsaPhonePercentCheckSpec](/docs/checks/column/pii/contains-usa-phone-percent)| | | |
|[monthly_contains_usa_zipcode_percent](/docs/checks/column/pii/contains-usa-zipcode-percent)|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](/docs/checks/column/pii/contains-usa-zipcode-percent)| | | |
|[monthly_contains_email_percent](/docs/checks/column/pii/contains-email-percent)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsEmailPercentCheckSpec](/docs/checks/column/pii/contains-email-percent)| | | |
|[monthly_contains_ip4_percent](/docs/checks/column/pii/contains-ip4-percent)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsIp4PercentCheckSpec](/docs/checks/column/pii/contains-ip4-percent)| | | |
|[monthly_contains_ip6_percent](/docs/checks/column/pii/contains-ip6-percent)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsIp6PercentCheckSpec](/docs/checks/column/pii/contains-ip6-percent)| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

## ColumnAnomalyMonthlyMonitoringChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_mean_change](/docs/checks/column/anomaly/mean-change)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](/docs/checks/column/anomaly/mean-change)| | | |
|[monthly_median_change](/docs/checks/column/anomaly/median-change)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](/docs/checks/column/anomaly/median-change)| | | |
|[monthly_sum_change](/docs/checks/column/anomaly/sum-change)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](/docs/checks/column/anomaly/sum-change)| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

## ColumnStringsMonthlyMonitoringChecksSpec  
Container of strings data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_string_max_length](/docs/checks/column/strings/string-max-length)|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMaxLengthCheckSpec](/docs/checks/column/strings/string-max-length)| | | |
|[monthly_string_min_length](/docs/checks/column/strings/string-min-length)|Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMinLengthCheckSpec](/docs/checks/column/strings/string-min-length)| | | |
|[monthly_string_mean_length](/docs/checks/column/strings/string-mean-length)|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMeanLengthCheckSpec](/docs/checks/column/strings/string-mean-length)| | | |
|[monthly_string_length_below_min_length_count](/docs/checks/column/strings/string-length-below-min-length-count)|The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthCountCheckSpec](/docs/checks/column/strings/string-length-below-min-length-count)| | | |
|[monthly_string_length_below_min_length_percent](/docs/checks/column/strings/string-length-below-min-length-percent)|The check counts percentage of those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](/docs/checks/column/strings/string-length-below-min-length-percent)| | | |
|[monthly_string_length_above_max_length_count](/docs/checks/column/strings/string-length-above-max-length-count)|The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](/docs/checks/column/strings/string-length-above-max-length-count)| | | |
|[monthly_string_length_above_max_length_percent](/docs/checks/column/strings/string-length-above-max-length-percent)|The check counts percentage of those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](/docs/checks/column/strings/string-length-above-max-length-percent)| | | |
|[monthly_string_length_in_range_percent](/docs/checks/column/strings/string-length-in-range-percent)|The check counts percentage of those strings with length in the range provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthInRangePercentCheckSpec](/docs/checks/column/strings/string-length-in-range-percent)| | | |
|[monthly_string_empty_count](/docs/checks/column/strings/string-empty-count)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringEmptyCountCheckSpec](/docs/checks/column/strings/string-empty-count)| | | |
|[monthly_string_empty_percent](/docs/checks/column/strings/string-empty-percent)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringEmptyPercentCheckSpec](/docs/checks/column/strings/string-empty-percent)| | | |
|[monthly_string_valid_dates_percent](/docs/checks/column/strings/string-valid-dates-percent)|Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidDatesPercentCheckSpec](/docs/checks/column/strings/string-valid-dates-percent)| | | |
|[monthly_string_whitespace_count](/docs/checks/column/strings/string-whitespace-count)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringWhitespaceCountCheckSpec](/docs/checks/column/strings/string-whitespace-count)| | | |
|[monthly_string_whitespace_percent](/docs/checks/column/strings/string-whitespace-percent)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringWhitespacePercentCheckSpec](/docs/checks/column/strings/string-whitespace-percent)| | | |
|[monthly_string_surrounded_by_whitespace_count](/docs/checks/column/strings/string-surrounded-by-whitespace-count)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](/docs/checks/column/strings/string-surrounded-by-whitespace-count)| | | |
|[monthly_string_surrounded_by_whitespace_percent](/docs/checks/column/strings/string-surrounded-by-whitespace-percent)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](/docs/checks/column/strings/string-surrounded-by-whitespace-percent)| | | |
|[monthly_string_null_placeholder_count](/docs/checks/column/strings/string-null-placeholder-count)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNullPlaceholderCountCheckSpec](/docs/checks/column/strings/string-null-placeholder-count)| | | |
|[monthly_string_null_placeholder_percent](/docs/checks/column/strings/string-null-placeholder-percent)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNullPlaceholderPercentCheckSpec](/docs/checks/column/strings/string-null-placeholder-percent)| | | |
|[monthly_string_boolean_placeholder_percent](/docs/checks/column/strings/string-boolean-placeholder-percent)|Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringBooleanPlaceholderPercentCheckSpec](/docs/checks/column/strings/string-boolean-placeholder-percent)| | | |
|[monthly_string_parsable_to_integer_percent](/docs/checks/column/strings/string-parsable-to-integer-percent)|Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringParsableToIntegerPercentCheckSpec](/docs/checks/column/strings/string-parsable-to-integer-percent)| | | |
|[monthly_string_parsable_to_float_percent](/docs/checks/column/strings/string-parsable-to-float-percent)|Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringParsableToFloatPercentCheckSpec](/docs/checks/column/strings/string-parsable-to-float-percent)| | | |
|[monthly_expected_strings_in_use_count](/docs/checks/column/strings/expected-strings-in-use-count)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnExpectedStringsInUseCountCheckSpec](/docs/checks/column/strings/expected-strings-in-use-count)| | | |
|[monthly_string_value_in_set_percent](/docs/checks/column/strings/string-value-in-set-percent)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValueInSetPercentCheckSpec](/docs/checks/column/strings/string-value-in-set-percent)| | | |
|[monthly_string_valid_country_code_percent](/docs/checks/column/strings/string-valid-country-code-percent)|Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidCountryCodePercentCheckSpec](/docs/checks/column/strings/string-valid-country-code-percent)| | | |
|[monthly_string_valid_currency_code_percent](/docs/checks/column/strings/string-valid-currency-code-percent)|Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidCurrencyCodePercentCheckSpec](/docs/checks/column/strings/string-valid-currency-code-percent)| | | |
|[monthly_string_invalid_email_count](/docs/checks/column/strings/string-invalid-email-count)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidEmailCountCheckSpec](/docs/checks/column/strings/string-invalid-email-count)| | | |
|[monthly_string_invalid_uuid_count](/docs/checks/column/strings/string-invalid-uuid-count)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidUuidCountCheckSpec](/docs/checks/column/strings/string-invalid-uuid-count)| | | |
|[monthly_string_valid_uuid_percent](/docs/checks/column/strings/string-valid-uuid-percent)|Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidUuidPercentCheckSpec](/docs/checks/column/strings/string-valid-uuid-percent)| | | |
|[monthly_string_invalid_ip4_address_count](/docs/checks/column/strings/string-invalid-ip4-address-count)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidIp4AddressCountCheckSpec](/docs/checks/column/strings/string-invalid-ip4-address-count)| | | |
|[monthly_string_invalid_ip6_address_count](/docs/checks/column/strings/string-invalid-ip6-address-count)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidIp6AddressCountCheckSpec](/docs/checks/column/strings/string-invalid-ip6-address-count)| | | |
|[monthly_string_not_match_regex_count](/docs/checks/column/strings/string-not-match-regex-count)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNotMatchRegexCountCheckSpec](/docs/checks/column/strings/string-not-match-regex-count)| | | |
|[monthly_string_match_regex_percent](/docs/checks/column/strings/string-match-regex-percent)|Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMatchRegexPercentCheckSpec](/docs/checks/column/strings/string-match-regex-percent)| | | |
|[monthly_string_not_match_date_regex_count](/docs/checks/column/strings/string-not-match-date-regex-count)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNotMatchDateRegexCountCheckSpec](/docs/checks/column/strings/string-not-match-date-regex-count)| | | |
|[monthly_string_match_date_regex_percent](/docs/checks/column/strings/string-match-date-regex-percent)|Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMatchDateRegexPercentCheckSpec](/docs/checks/column/strings/string-match-date-regex-percent)| | | |
|[monthly_string_match_name_regex_percent](/docs/checks/column/strings/string-match-name-regex-percent)|Verifies that the percentage of strings matching the name regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMatchNameRegexPercentCheckSpec](/docs/checks/column/strings/string-match-name-regex-percent)| | | |
|[monthly_expected_strings_in_top_values_count](/docs/checks/column/strings/expected-strings-in-top-values-count)|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnExpectedStringsInTopValuesCountCheckSpec](/docs/checks/column/strings/expected-strings-in-top-values-count)| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

## ColumnNumericMonthlyMonitoringChecksSpec  
Container of built-in preconfigured data quality monitoring on a column level that are checking numeric values at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_negative_count](/docs/checks/column/numeric/negative-count)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNegativeCountCheckSpec](/docs/checks/column/numeric/negative-count)| | | |
|[monthly_negative_percent](/docs/checks/column/numeric/negative-percent)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNegativePercentCheckSpec](/docs/checks/column/numeric/negative-percent)| | | |
|[monthly_non_negative_count](/docs/checks/column/numeric/non-negative-count)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNonNegativeCountCheckSpec](/docs/checks/column/numeric/non-negative-count)| | | |
|[monthly_non_negative_percent](/docs/checks/column/numeric/non-negative-percent)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNonNegativePercentCheckSpec](/docs/checks/column/numeric/non-negative-percent)| | | |
|[monthly_expected_numbers_in_use_count](/docs/checks/column/numeric/expected-numbers-in-use-count)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnExpectedNumbersInUseCountCheckSpec](/docs/checks/column/numeric/expected-numbers-in-use-count)| | | |
|[monthly_number_value_in_set_percent](/docs/checks/column/numeric/number-value-in-set-percent)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNumberValueInSetPercentCheckSpec](/docs/checks/column/numeric/number-value-in-set-percent)| | | |
|[monthly_values_in_range_numeric_percent](/docs/checks/column/numeric/values-in-range-numeric-percent)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValuesInRangeNumericPercentCheckSpec](/docs/checks/column/numeric/values-in-range-numeric-percent)| | | |
|[monthly_values_in_range_integers_percent](/docs/checks/column/numeric/values-in-range-integers-percent)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValuesInRangeIntegersPercentCheckSpec](/docs/checks/column/numeric/values-in-range-integers-percent)| | | |
|[monthly_value_below_min_value_count](/docs/checks/column/numeric/value-below-min-value-count)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueBelowMinValueCountCheckSpec](/docs/checks/column/numeric/value-below-min-value-count)| | | |
|[monthly_value_below_min_value_percent](/docs/checks/column/numeric/value-below-min-value-percent)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueBelowMinValuePercentCheckSpec](/docs/checks/column/numeric/value-below-min-value-percent)| | | |
|[monthly_value_above_max_value_count](/docs/checks/column/numeric/value-above-max-value-count)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueAboveMaxValueCountCheckSpec](/docs/checks/column/numeric/value-above-max-value-count)| | | |
|[monthly_value_above_max_value_percent](/docs/checks/column/numeric/value-above-max-value-percent)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueAboveMaxValuePercentCheckSpec](/docs/checks/column/numeric/value-above-max-value-percent)| | | |
|[monthly_max_in_range](/docs/checks/column/numeric/max-in-range)|Verifies that the maximal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMaxInRangeCheckSpec](/docs/checks/column/numeric/max-in-range)| | | |
|[monthly_min_in_range](/docs/checks/column/numeric/min-in-range)|Verifies that the minimal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMinInRangeCheckSpec](/docs/checks/column/numeric/min-in-range)| | | |
|[monthly_mean_in_range](/docs/checks/column/numeric/mean-in-range)|Verifies that the average (mean) of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMeanInRangeCheckSpec](/docs/checks/column/numeric/mean-in-range)| | | |
|[monthly_percentile_in_range](/docs/checks/column/numeric/percentile-in-range)|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentileInRangeCheckSpec](/docs/checks/column/numeric/percentile-in-range)| | | |
|[monthly_median_in_range](/docs/reference/yaml/profiling/column-profiling-checks/#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMedianInRangeCheckSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnmedianinrangecheckspec)| | | |
|[monthly_percentile_10_in_range](/docs/reference/yaml/profiling/column-profiling-checks/#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile10InRangeCheckSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnpercentile10inrangecheckspec)| | | |
|[monthly_percentile_25_in_range](/docs/reference/yaml/profiling/column-profiling-checks/#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile25InRangeCheckSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnpercentile25inrangecheckspec)| | | |
|[monthly_percentile_75_in_range](/docs/reference/yaml/profiling/column-profiling-checks/#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile75InRangeCheckSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnpercentile75inrangecheckspec)| | | |
|[monthly_percentile_90_in_range](/docs/reference/yaml/profiling/column-profiling-checks/#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile90InRangeCheckSpec](/docs/reference/yaml/profiling/column-profiling-checks/#columnpercentile90inrangecheckspec)| | | |
|[monthly_sample_stddev_in_range](/docs/checks/column/numeric/sample-stddev-in-range)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSampleStddevInRangeCheckSpec](/docs/checks/column/numeric/sample-stddev-in-range)| | | |
|[monthly_population_stddev_in_range](/docs/checks/column/numeric/population-stddev-in-range)|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPopulationStddevInRangeCheckSpec](/docs/checks/column/numeric/population-stddev-in-range)| | | |
|[monthly_sample_variance_in_range](/docs/checks/column/numeric/sample-variance-in-range)|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSampleVarianceInRangeCheckSpec](/docs/checks/column/numeric/sample-variance-in-range)| | | |
|[monthly_population_variance_in_range](/docs/checks/column/numeric/population-variance-in-range)|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPopulationVarianceInRangeCheckSpec](/docs/checks/column/numeric/population-variance-in-range)| | | |
|[monthly_sum_in_range](/docs/checks/column/numeric/sum-in-range)|Verifies that the sum of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSumInRangeCheckSpec](/docs/checks/column/numeric/sum-in-range)| | | |
|[monthly_invalid_latitude_count](/docs/checks/column/numeric/invalid-latitude-count)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnInvalidLatitudeCountCheckSpec](/docs/checks/column/numeric/invalid-latitude-count)| | | |
|[monthly_valid_latitude_percent](/docs/checks/column/numeric/valid-latitude-percent)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValidLatitudePercentCheckSpec](/docs/checks/column/numeric/valid-latitude-percent)| | | |
|[monthly_invalid_longitude_count](/docs/checks/column/numeric/invalid-longitude-count)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnInvalidLongitudeCountCheckSpec](/docs/checks/column/numeric/invalid-longitude-count)| | | |
|[monthly_valid_longitude_percent](/docs/checks/column/numeric/valid-longitude-percent)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValidLongitudePercentCheckSpec](/docs/checks/column/numeric/valid-longitude-percent)| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

## ColumnComparisonMonthlyMonitoringChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [ColumnComparisonMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columncomparisonmonthlymonitoringchecksspec)]| | | |









___  

## ColumnUniquenessMonthlyMonitoringChecksSpec  
Container of uniqueness data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_distinct_count](/docs/checks/column/uniqueness/distinct-count)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDistinctCountCheckSpec](/docs/checks/column/uniqueness/distinct-count)| | | |
|[monthly_distinct_percent](/docs/checks/column/uniqueness/distinct-percent)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDistinctPercentCheckSpec](/docs/checks/column/uniqueness/distinct-percent)| | | |
|[monthly_duplicate_count](/docs/checks/column/uniqueness/duplicate-count)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDuplicateCountCheckSpec](/docs/checks/column/uniqueness/duplicate-count)| | | |
|[monthly_duplicate_percent](/docs/checks/column/uniqueness/duplicate-percent)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDuplicatePercentCheckSpec](/docs/checks/column/uniqueness/duplicate-percent)| | | |
|[monthly_anomaly_differencing_distinct_count_30_days](/docs/checks/column/uniqueness/anomaly-differencing-distinct-count-30-days)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec](/docs/checks/column/uniqueness/anomaly-differencing-distinct-count-30-days)| | | |
|[monthly_anomaly_differencing_distinct_count](/docs/checks/column/uniqueness/anomaly-differencing-distinct-count)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyDifferencingDistinctCountCheckSpec](/docs/checks/column/uniqueness/anomaly-differencing-distinct-count)| | | |
|[monthly_anomaly_stationary_distinct_percent_30_days](/docs/checks/column/uniqueness/anomaly-stationary-distinct-percent-30-days)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec](/docs/checks/column/uniqueness/anomaly-stationary-distinct-percent-30-days)| | | |
|[monthly_anomaly_stationary_distinct_percent](/docs/checks/column/uniqueness/anomaly-stationary-distinct-percent)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryDistinctPercentCheckSpec](/docs/checks/column/uniqueness/anomaly-stationary-distinct-percent)| | | |
|[monthly_change_distinct_count](/docs/checks/column/uniqueness/change-distinct-count)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctCountCheckSpec](/docs/checks/column/uniqueness/change-distinct-count)| | | |
|[monthly_change_distinct_count_since_7_days](/docs/checks/column/uniqueness/change-distinct-count-since-7-days)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctCountSince7DaysCheckSpec](/docs/checks/column/uniqueness/change-distinct-count-since-7-days)| | | |
|[monthly_change_distinct_count_since_30_days](/docs/checks/column/uniqueness/change-distinct-count-since-30-days)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctCountSince30DaysCheckSpec](/docs/checks/column/uniqueness/change-distinct-count-since-30-days)| | | |
|[monthly_change_distinct_count_since_yesterday](/docs/checks/column/uniqueness/change-distinct-count-since-yesterday)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctCountSinceYesterdayCheckSpec](/docs/checks/column/uniqueness/change-distinct-count-since-yesterday)| | | |
|[monthly_change_distinct_percent](/docs/checks/column/uniqueness/change-distinct-percent)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctPercentCheckSpec](/docs/checks/column/uniqueness/change-distinct-percent)| | | |
|[monthly_change_distinct_percent_since_7_days](/docs/checks/column/uniqueness/change-distinct-percent-since-7-days)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctPercentSince7DaysCheckSpec](/docs/checks/column/uniqueness/change-distinct-percent-since-7-days)| | | |
|[monthly_change_distinct_percent_since_30_days](/docs/checks/column/uniqueness/change-distinct-percent-since-30-days)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctPercentSince30DaysCheckSpec](/docs/checks/column/uniqueness/change-distinct-percent-since-30-days)| | | |
|[monthly_change_distinct_percent_since_yesterday](/docs/checks/column/uniqueness/change-distinct-percent-since-yesterday)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctPercentSinceYesterdayCheckSpec](/docs/checks/column/uniqueness/change-distinct-percent-since-yesterday)| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

## ColumnIntegrityMonthlyMonitoringChecksSpec  
Container of integrity data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_foreign_key_not_match_count](/docs/checks/column/integrity/foreign-key-not-match-count)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](/docs/checks/column/integrity/foreign-key-not-match-count)| | | |
|[monthly_foreign_key_match_percent](/docs/checks/column/integrity/foreign-key-match-percent)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](/docs/checks/column/integrity/foreign-key-match-percent)| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

## ColumnSchemaMonthlyMonitoringChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking the column schema at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_column_exists](/docs/checks/column/schema/column-exists)|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnSchemaColumnExistsCheckSpec](/docs/checks/column/schema/column-exists)| | | |
|[monthly_column_type_changed](/docs/checks/column/schema/column-type-changed)|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.|[ColumnSchemaTypeChangedCheckSpec](/docs/checks/column/schema/column-type-changed)| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

## ColumnAccuracyMonthlyMonitoringChecksSpec  
Container of accuracy data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_total_sum_match_percent](/docs/checks/column/accuracy/total-sum-match-percent)|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyTotalSumMatchPercentCheckSpec](/docs/checks/column/accuracy/total-sum-match-percent)| | | |
|[monthly_total_min_match_percent](/docs/checks/column/accuracy/total-min-match-percent)|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyTotalMinMatchPercentCheckSpec](/docs/checks/column/accuracy/total-min-match-percent)| | | |
|[monthly_total_max_match_percent](/docs/checks/column/accuracy/total-max-match-percent)|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyTotalMaxMatchPercentCheckSpec](/docs/checks/column/accuracy/total-max-match-percent)| | | |
|[monthly_total_average_match_percent](/docs/checks/column/accuracy/total-average-match-percent)|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyTotalAverageMatchPercentCheckSpec](/docs/checks/column/accuracy/total-average-match-percent)| | | |
|[monthly_total_not_null_count_match_percent](/docs/checks/column/accuracy/total-not-null-count-match-percent)|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec](/docs/checks/column/accuracy/total-not-null-count-match-percent)| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

## ColumnMonthlyMonitoringCheckCategoriesSpec  
Container of column level monthly monitoring checks. Contains categories of monthly monitoring checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnnullsmonthlymonitoringchecksspec)|Monthly monitoring checks of nulls in the column|[ColumnNullsMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnnullsmonthlymonitoringchecksspec)| | | |
|[numeric](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnnumericmonthlymonitoringchecksspec)|Monthly monitoring checks of numeric in the column|[ColumnNumericMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnnumericmonthlymonitoringchecksspec)| | | |
|[strings](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnstringsmonthlymonitoringchecksspec)|Monthly monitoring checks of strings in the column|[ColumnStringsMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnstringsmonthlymonitoringchecksspec)| | | |
|[uniqueness](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnuniquenessmonthlymonitoringchecksspec)|Monthly monitoring checks of uniqueness in the column|[ColumnUniquenessMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnuniquenessmonthlymonitoringchecksspec)| | | |
|[datetime](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columndatetimemonthlymonitoringchecksspec)|Monthly monitoring checks of datetime in the column|[ColumnDatetimeMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columndatetimemonthlymonitoringchecksspec)| | | |
|[pii](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnpiimonthlymonitoringchecksspec)|Monthly monitoring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnpiimonthlymonitoringchecksspec)| | | |
|[sql](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnsqlmonthlymonitoringchecksspec)|Monthly monitoring checks of custom SQL checks in the column|[ColumnSqlMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnsqlmonthlymonitoringchecksspec)| | | |
|[bool](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnboolmonthlymonitoringchecksspec)|Monthly monitoring checks of booleans in the column|[ColumnBoolMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnboolmonthlymonitoringchecksspec)| | | |
|[integrity](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnintegritymonthlymonitoringchecksspec)|Monthly monitoring checks of integrity in the column|[ColumnIntegrityMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnintegritymonthlymonitoringchecksspec)| | | |
|[accuracy](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnaccuracymonthlymonitoringchecksspec)|Monthly monitoring checks of accuracy in the column|[ColumnAccuracyMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnaccuracymonthlymonitoringchecksspec)| | | |
|[datatype](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columndatatypemonthlymonitoringchecksspec)|Monthly monitoring checks of datatype in the column|[ColumnDatatypeMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columndatatypemonthlymonitoringchecksspec)| | | |
|[anomaly](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnanomalymonthlymonitoringchecksspec)|Monthly monitoring checks of anomaly in the column|[ColumnAnomalyMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnanomalymonthlymonitoringchecksspec)| | | |
|[schema](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnschemamonthlymonitoringchecksspec)|Monthly monitoring column schema checks|[ColumnSchemaMonthlyMonitoringChecksSpec](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columnschemamonthlymonitoringchecksspec)| | | |
|[comparisons](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columncomparisonmonthlymonitoringchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonMonthlyMonitoringChecksSpecMap](/docs/reference/yaml/monitoring/column-monthly-monitoring-checks/#columncomparisonmonthlymonitoringchecksspecmap)| | | |
|[custom](/docs/reference/yaml/profiling/table-profiling-checks/#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcheckspecmap)| | | |









___  

## ColumnNullsMonthlyMonitoringChecksSpec  
Container of nulls data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_nulls_count](/docs/checks/column/nulls/nulls-count)|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNullsCountCheckSpec](/docs/checks/column/nulls/nulls-count)| | | |
|[monthly_nulls_percent](/docs/checks/column/nulls/nulls-percent)|Verifies that the percentage of null values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNullsPercentCheckSpec](/docs/checks/column/nulls/nulls-percent)| | | |
|[monthly_not_nulls_count](/docs/checks/column/nulls/not-nulls-count)|Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNotNullsCountCheckSpec](/docs/checks/column/nulls/not-nulls-count)| | | |
|[monthly_not_nulls_percent](/docs/checks/column/nulls/not-nulls-percent)|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNotNullsPercentCheckSpec](/docs/checks/column/nulls/not-nulls-percent)| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

## ColumnDatetimeMonthlyMonitoringChecksSpec  
Container of date-time data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_date_match_format_percent](/docs/checks/column/datetime/date-match-format-percent)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly monitoring.|[ColumnDatetimeDateMatchFormatPercentCheckSpec](/docs/checks/column/datetime/date-match-format-percent)| | | |
|[monthly_date_values_in_future_percent](/docs/checks/column/datetime/date-values-in-future-percent)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDateValuesInFuturePercentCheckSpec](/docs/checks/column/datetime/date-values-in-future-percent)| | | |
|[monthly_datetime_value_in_range_date_percent](/docs/checks/column/datetime/datetime-value-in-range-date-percent)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](/docs/checks/column/datetime/datetime-value-in-range-date-percent)| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

## ColumnBoolMonthlyMonitoringChecksSpec  
Container of boolean monitoring data quality checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_true_percent](/docs/checks/column/bool/true-percent)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnTruePercentCheckSpec](/docs/checks/column/bool/true-percent)| | | |
|[monthly_false_percent](/docs/checks/column/bool/false-percent)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnFalsePercentCheckSpec](/docs/checks/column/bool/false-percent)| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

## ColumnDatatypeMonthlyMonitoringChecksSpec  
Container of datatype data quality monitoring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_string_datatype_detected](/docs/checks/column/datatype/string-datatype-detected)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDatatypeStringDatatypeDetectedCheckSpec](/docs/checks/column/datatype/string-datatype-detected)| | | |
|[monthly_string_datatype_changed](/docs/checks/column/datatype/string-datatype-changed)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDatatypeStringDatatypeChangedCheckSpec](/docs/checks/column/datatype/string-datatype-changed)| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

## ColumnSqlMonthlyMonitoringChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_sql_condition_passed_percent_on_column](/docs/checks/column/sql/sql-condition-passed-percent-on-column)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSqlConditionPassedPercentCheckSpec](/docs/checks/column/sql/sql-condition-passed-percent-on-column)| | | |
|[monthly_sql_condition_failed_count_on_column](/docs/checks/column/sql/sql-condition-failed-count-on-column)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSqlConditionFailedCountCheckSpec](/docs/checks/column/sql/sql-condition-failed-count-on-column)| | | |
|[monthly_sql_aggregate_expr_column](/docs/checks/column/sql/sql-aggregate-expr-column)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSqlAggregateExprCheckSpec](/docs/checks/column/sql/sql-aggregate-expr-column)| | | |
|[custom_checks](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](/docs/reference/yaml/profiling/table-profiling-checks/#customcategorycheckspecmap)| | | |









___  

