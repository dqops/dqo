
## ColumnAnomalyMonthlyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_mean_change](../../../../checks/column/anomaly/mean-change/)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](../../../../checks/column/anomaly/mean-change/)| | | |
|[monthly_partition_median_change](../../../../checks/column/anomaly/median-change/)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](../../../../checks/column/anomaly/median-change/)| | | |
|[monthly_partition_sum_change](../../../../checks/column/anomaly/sum-change/)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](../../../../checks/column/anomaly/sum-change/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnComparisonMonthlyPartitionedChecksSpec  
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 This is the configuration for daily partitioned checks that are counted in KPIs.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_sum_match](../../../../checks/column/comparisons/sum-match/)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonSumMatchCheckSpec](../../../../checks/column/comparisons/sum-match/)| | | |
|[monthly_partition_min_match](../../../../checks/column/comparisons/min-match/)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMinMatchCheckSpec](../../../../checks/column/comparisons/min-match/)| | | |
|[monthly_partition_max_match](../../../../checks/column/comparisons/max-match/)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMaxMatchCheckSpec](../../../../checks/column/comparisons/max-match/)| | | |
|[monthly_partition_mean_match](../../../../checks/column/comparisons/mean-match/)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMeanMatchCheckSpec](../../../../checks/column/comparisons/mean-match/)| | | |
|[monthly_partition_not_null_count_match](../../../../checks/column/comparisons/not-null-count-match/)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonNotNullCountMatchCheckSpec](../../../../checks/column/comparisons/not-null-count-match/)| | | |
|[monthly_partition_null_count_match](../../../../checks/column/comparisons/null-count-match/)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonNullCountMatchCheckSpec](../../../../checks/column/comparisons/null-count-match/)| | | |
|reference_column|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|string| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnMonthlyPartitionedCheckCategoriesSpec  
Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](../column-monthly-partitioned-checks/#ColumnNullsMonthlyPartitionedChecksSpec)|Monthly partitioned checks of nulls values in the column|[ColumnNullsMonthlyPartitionedChecksSpec](../column-monthly-partitioned-checks/#ColumnNullsMonthlyPartitionedChecksSpec)| | | |
|[numeric](../column-monthly-partitioned-checks/#ColumnNumericMonthlyPartitionedChecksSpec)|Monthly partitioned checks of numeric values in the column|[ColumnNumericMonthlyPartitionedChecksSpec](../column-monthly-partitioned-checks/#ColumnNumericMonthlyPartitionedChecksSpec)| | | |
|[strings](../column-monthly-partitioned-checks/#ColumnStringsMonthlyPartitionedChecksSpec)|Monthly partitioned checks of strings values in the column|[ColumnStringsMonthlyPartitionedChecksSpec](../column-monthly-partitioned-checks/#ColumnStringsMonthlyPartitionedChecksSpec)| | | |
|[uniqueness](../column-monthly-partitioned-checks/#ColumnUniquenessMonthlyPartitionedChecksSpec)|Monthly partitioned checks of uniqueness values in the column|[ColumnUniquenessMonthlyPartitionedChecksSpec](../column-monthly-partitioned-checks/#ColumnUniquenessMonthlyPartitionedChecksSpec)| | | |
|[datetime](../column-monthly-partitioned-checks/#ColumnDatetimeMonthlyPartitionedChecksSpec)|Monthly partitioned checks of datetime values in the column|[ColumnDatetimeMonthlyPartitionedChecksSpec](../column-monthly-partitioned-checks/#ColumnDatetimeMonthlyPartitionedChecksSpec)| | | |
|[pii](../column-monthly-partitioned-checks/#ColumnPiiMonthlyPartitionedChecksSpec)|Monthly partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyPartitionedChecksSpec](../column-monthly-partitioned-checks/#ColumnPiiMonthlyPartitionedChecksSpec)| | | |
|[sql](../column-monthly-partitioned-checks/#ColumnSqlMonthlyPartitionedChecksSpec)|Monthly partitioned checks using custom SQL expressions and conditions on the column|[ColumnSqlMonthlyPartitionedChecksSpec](../column-monthly-partitioned-checks/#ColumnSqlMonthlyPartitionedChecksSpec)| | | |
|[bool](../column-monthly-partitioned-checks/#ColumnBoolMonthlyPartitionedChecksSpec)|Monthly partitioned checks for booleans in the column|[ColumnBoolMonthlyPartitionedChecksSpec](../column-monthly-partitioned-checks/#ColumnBoolMonthlyPartitionedChecksSpec)| | | |
|[integrity](../column-monthly-partitioned-checks/#ColumnIntegrityMonthlyPartitionedChecksSpec)|Monthly partitioned checks for integrity in the column|[ColumnIntegrityMonthlyPartitionedChecksSpec](../column-monthly-partitioned-checks/#ColumnIntegrityMonthlyPartitionedChecksSpec)| | | |
|[accuracy](../column-monthly-partitioned-checks/#ColumnAccuracyMonthlyPartitionedChecksSpec)|Monthly partitioned checks for accuracy in the column|[ColumnAccuracyMonthlyPartitionedChecksSpec](../column-monthly-partitioned-checks/#ColumnAccuracyMonthlyPartitionedChecksSpec)| | | |
|[datatype](../column-monthly-partitioned-checks/#ColumnDatatypeMonthlyPartitionedChecksSpec)|Monthly partitioned checks for datatype in the column|[ColumnDatatypeMonthlyPartitionedChecksSpec](../column-monthly-partitioned-checks/#ColumnDatatypeMonthlyPartitionedChecksSpec)| | | |
|[anomaly](../column-monthly-partitioned-checks/#ColumnAnomalyMonthlyPartitionedChecksSpec)|Monthly partitioned checks for anomaly in the column|[ColumnAnomalyMonthlyPartitionedChecksSpec](../column-monthly-partitioned-checks/#ColumnAnomalyMonthlyPartitionedChecksSpec)| | | |
|[comparisons](../column-monthly-partitioned-checks/#ColumnComparisonMonthlyPartitionedChecksSpecMap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonMonthlyPartitionedChecksSpecMap](../column-monthly-partitioned-checks/#ColumnComparisonMonthlyPartitionedChecksSpecMap)| | | |
|[custom](../../profiling_checks/table-profiling-checks/#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCheckSpecMap)| | | |









___  

## ColumnPiiMonthlyPartitionedChecksSpec  
Container of PII data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_contains_usa_phone_percent](../../../../checks/column/pii/contains-usa-phone-percent/)|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsUsaPhonePercentCheckSpec](../../../../checks/column/pii/contains-usa-phone-percent/)| | | |
|[monthly_partition_contains_usa_zipcode_percent](../../../../checks/column/pii/contains-usa-zipcode-percent/)|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](../../../../checks/column/pii/contains-usa-zipcode-percent/)| | | |
|[monthly_partition_contains_email_percent](../../../../checks/column/pii/contains-email-percent/)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsEmailPercentCheckSpec](../../../../checks/column/pii/contains-email-percent/)| | | |
|[monthly_partition_contains_ip4_percent](../../../../checks/column/pii/contains-ip4-percent/)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsIp4PercentCheckSpec](../../../../checks/column/pii/contains-ip4-percent/)| | | |
|[monthly_partition_contains_ip6_percent](../../../../checks/column/pii/contains-ip6-percent/)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsIp6PercentCheckSpec](../../../../checks/column/pii/contains-ip6-percent/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnStringsMonthlyPartitionedChecksSpec  
Container of strings data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_string_max_length](../../../../checks/column/strings/string-max-length/)|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMaxLengthCheckSpec](../../../../checks/column/strings/string-max-length/)| | | |
|[monthly_partition_string_min_length](../../../../checks/column/strings/string-min-length/)|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMinLengthCheckSpec](../../../../checks/column/strings/string-min-length/)| | | |
|[monthly_partition_string_mean_length](../../../../checks/column/strings/string-mean-length/)|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMeanLengthCheckSpec](../../../../checks/column/strings/string-mean-length/)| | | |
|[monthly_partition_string_length_below_min_length_count](../../../../checks/column/strings/string-length-below-min-length-count/)|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthBelowMinLengthCountCheckSpec](../../../../checks/column/strings/string-length-below-min-length-count/)| | | |
|[monthly_partition_string_length_below_min_length_percent](../../../../checks/column/strings/string-length-below-min-length-percent/)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](../../../../checks/column/strings/string-length-below-min-length-percent/)| | | |
|[monthly_partition_string_length_above_max_length_count](../../../../checks/column/strings/string-length-above-max-length-count/)|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](../../../../checks/column/strings/string-length-above-max-length-count/)| | | |
|[monthly_partition_string_length_above_max_length_percent](../../../../checks/column/strings/string-length-above-max-length-percent/)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](../../../../checks/column/strings/string-length-above-max-length-percent/)| | | |
|[monthly_partition_string_length_in_range_percent](../../../../checks/column/strings/string-length-in-range-percent/)|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthInRangePercentCheckSpec](../../../../checks/column/strings/string-length-in-range-percent/)| | | |
|[monthly_partition_string_empty_count](../../../../checks/column/strings/string-empty-count/)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringEmptyCountCheckSpec](../../../../checks/column/strings/string-empty-count/)| | | |
|[monthly_partition_string_empty_percent](../../../../checks/column/strings/string-empty-percent/)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringEmptyPercentCheckSpec](../../../../checks/column/strings/string-empty-percent/)| | | |
|[monthly_partition_string_whitespace_count](../../../../checks/column/strings/string-whitespace-count/)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringWhitespaceCountCheckSpec](../../../../checks/column/strings/string-whitespace-count/)| | | |
|[monthly_partition_string_whitespace_percent](../../../../checks/column/strings/string-whitespace-percent/)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringWhitespacePercentCheckSpec](../../../../checks/column/strings/string-whitespace-percent/)| | | |
|[monthly_partition_string_surrounded_by_whitespace_count](../../../../checks/column/strings/string-surrounded-by-whitespace-count/)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](../../../../checks/column/strings/string-surrounded-by-whitespace-count/)| | | |
|[monthly_partition_string_surrounded_by_whitespace_percent](../../../../checks/column/strings/string-surrounded-by-whitespace-percent/)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](../../../../checks/column/strings/string-surrounded-by-whitespace-percent/)| | | |
|[monthly_partition_string_null_placeholder_count](../../../../checks/column/strings/string-null-placeholder-count/)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNullPlaceholderCountCheckSpec](../../../../checks/column/strings/string-null-placeholder-count/)| | | |
|[monthly_partition_string_null_placeholder_percent](../../../../checks/column/strings/string-null-placeholder-percent/)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNullPlaceholderPercentCheckSpec](../../../../checks/column/strings/string-null-placeholder-percent/)| | | |
|[monthly_partition_string_boolean_placeholder_percent](../../../../checks/column/strings/string-boolean-placeholder-percent/)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringBooleanPlaceholderPercentCheckSpec](../../../../checks/column/strings/string-boolean-placeholder-percent/)| | | |
|[monthly_partition_string_parsable_to_integer_percent](../../../../checks/column/strings/string-parsable-to-integer-percent/)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringParsableToIntegerPercentCheckSpec](../../../../checks/column/strings/string-parsable-to-integer-percent/)| | | |
|[monthly_partition_string_parsable_to_float_percent](../../../../checks/column/strings/string-parsable-to-float-percent/)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringParsableToFloatPercentCheckSpec](../../../../checks/column/strings/string-parsable-to-float-percent/)| | | |
|[monthly_partition_expected_strings_in_use_count](../../../../checks/column/strings/expected-strings-in-use-count/)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnExpectedStringsInUseCountCheckSpec](../../../../checks/column/strings/expected-strings-in-use-count/)| | | |
|[monthly_partition_string_value_in_set_percent](../../../../checks/column/strings/string-value-in-set-percent/)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValueInSetPercentCheckSpec](../../../../checks/column/strings/string-value-in-set-percent/)| | | |
|[monthly_partition_string_valid_dates_percent](../../../../checks/column/strings/string-valid-dates-percent/)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidDatesPercentCheckSpec](../../../../checks/column/strings/string-valid-dates-percent/)| | | |
|[monthly_partition_string_valid_country_code_percent](../../../../checks/column/strings/string-valid-country-code-percent/)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidCountryCodePercentCheckSpec](../../../../checks/column/strings/string-valid-country-code-percent/)| | | |
|[monthly_partition_string_valid_currency_code_percent](../../../../checks/column/strings/string-valid-currency-code-percent/)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidCurrencyCodePercentCheckSpec](../../../../checks/column/strings/string-valid-currency-code-percent/)| | | |
|[monthly_partition_string_invalid_email_count](../../../../checks/column/strings/string-invalid-email-count/)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidEmailCountCheckSpec](../../../../checks/column/strings/string-invalid-email-count/)| | | |
|[monthly_partition_string_invalid_uuid_count](../../../../checks/column/strings/string-invalid-uuid-count/)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidUuidCountCheckSpec](../../../../checks/column/strings/string-invalid-uuid-count/)| | | |
|[monthly_partition_valid_uuid_percent](../../../../checks/column/strings/string-valid-uuid-percent/)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidUuidPercentCheckSpec](../../../../checks/column/strings/string-valid-uuid-percent/)| | | |
|[monthly_partition_string_invalid_ip4_address_count](../../../../checks/column/strings/string-invalid-ip4-address-count/)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidIp4AddressCountCheckSpec](../../../../checks/column/strings/string-invalid-ip4-address-count/)| | | |
|[monthly_partition_string_invalid_ip6_address_count](../../../../checks/column/strings/string-invalid-ip6-address-count/)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidIp6AddressCountCheckSpec](../../../../checks/column/strings/string-invalid-ip6-address-count/)| | | |
|[monthly_partition_string_not_match_regex_count](../../../../checks/column/strings/string-not-match-regex-count/)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNotMatchRegexCountCheckSpec](../../../../checks/column/strings/string-not-match-regex-count/)| | | |
|[monthly_partition_string_match_regex_percent](../../../../checks/column/strings/string-match-regex-percent/)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMatchRegexPercentCheckSpec](../../../../checks/column/strings/string-match-regex-percent/)| | | |
|[monthly_partition_string_not_match_date_regex_count](../../../../checks/column/strings/string-not-match-date-regex-count/)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNotMatchDateRegexCountCheckSpec](../../../../checks/column/strings/string-not-match-date-regex-count/)| | | |
|[monthly_partition_string_match_date_regex_percent](../../../../checks/column/strings/string-match-date-regex-percent/)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMatchDateRegexPercentCheckSpec](../../../../checks/column/strings/string-match-date-regex-percent/)| | | |
|[monthly_partition_string_match_name_regex_percent](../../../../checks/column/strings/string-match-name-regex-percent/)|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMatchNameRegexPercentCheckSpec](../../../../checks/column/strings/string-match-name-regex-percent/)| | | |
|[monthly_partition_expected_strings_in_top_values_count](../../../../checks/column/strings/expected-strings-in-top-values-count/)|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnExpectedStringsInTopValuesCountCheckSpec](../../../../checks/column/strings/expected-strings-in-top-values-count/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnUniquenessMonthlyPartitionedChecksSpec  
Container of uniqueness data quality partitioned checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_distinct_count](../../../../checks/column/uniqueness/distinct-count/)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDistinctCountCheckSpec](../../../../checks/column/uniqueness/distinct-count/)| | | |
|[monthly_partition_distinct_percent](../../../../checks/column/uniqueness/distinct-percent/)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDistinctPercentCheckSpec](../../../../checks/column/uniqueness/distinct-percent/)| | | |
|[monthly_partition_duplicate_count](../../../../checks/column/uniqueness/duplicate-count/)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDuplicateCountCheckSpec](../../../../checks/column/uniqueness/duplicate-count/)| | | |
|[monthly_partition_duplicate_percent](../../../../checks/column/uniqueness/duplicate-percent/)|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDuplicatePercentCheckSpec](../../../../checks/column/uniqueness/duplicate-percent/)| | | |
|[monthly_partition_anomaly_stationary_distinct_count_30_days](../column-daily-partitioned-checks/#ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec](../column-daily-partitioned-checks/#ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec)| | | |
|[monthly_partition_anomaly_stationary_distinct_count](../column-daily-partitioned-checks/#ColumnAnomalyStationaryPartitionDistinctCountCheckSpec)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryPartitionDistinctCountCheckSpec](../column-daily-partitioned-checks/#ColumnAnomalyStationaryPartitionDistinctCountCheckSpec)| | | |
|[monthly_partition_anomaly_stationary_distinct_percent_30_days](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent-30-days/)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent-30-days/)| | | |
|[monthly_partition_anomaly_stationary_distinct_percent](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent/)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryDistinctPercentCheckSpec](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent/)| | | |
|[monthly_partition_change_distinct_count](../../../../checks/column/uniqueness/change-distinct-count/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctCountCheckSpec](../../../../checks/column/uniqueness/change-distinct-count/)| | | |
|[monthly_partition_change_distinct_count_since_7_days](../../../../checks/column/uniqueness/change-distinct-count-since-7-days/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctCountSince7DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-7-days/)| | | |
|[monthly_partition_change_distinct_count_since_30_days](../../../../checks/column/uniqueness/change-distinct-count-since-30-days/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctCountSince30DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-30-days/)| | | |
|[monthly_partition_change_distinct_count_since_yesterday](../../../../checks/column/uniqueness/change-distinct-count-since-yesterday/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctCountSinceYesterdayCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-yesterday/)| | | |
|[monthly_partition_change_distinct_percent](../../../../checks/column/uniqueness/change-distinct-percent/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctPercentCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent/)| | | |
|[monthly_partition_change_distinct_percent_since_7_days](../../../../checks/column/uniqueness/change-distinct-percent-since-7-days/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctPercentSince7DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-7-days/)| | | |
|[monthly_partition_change_distinct_percent_since_30_days](../../../../checks/column/uniqueness/change-distinct-percent-since-30-days/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctPercentSince30DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-30-days/)| | | |
|[monthly_partition_change_distinct_percent_since_yesterday](../../../../checks/column/uniqueness/change-distinct-percent-since-yesterday/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctPercentSinceYesterdayCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-yesterday/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnDatetimeMonthlyPartitionedChecksSpec  
Container of date-time data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_date_match_format_percent](../../../../checks/column/datetime/date-match-format-percent/)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDatetimeDateMatchFormatPercentCheckSpec](../../../../checks/column/datetime/date-match-format-percent/)| | | |
|[monthly_partition_date_values_in_future_percent](../../../../checks/column/datetime/date-values-in-future-percent/)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDateValuesInFuturePercentCheckSpec](../../../../checks/column/datetime/date-values-in-future-percent/)| | | |
|[monthly_partition_datetime_value_in_range_date_percent](../../../../checks/column/datetime/datetime-value-in-range-date-percent/)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](../../../../checks/column/datetime/datetime-value-in-range-date-percent/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnNullsMonthlyPartitionedChecksSpec  
Container of nulls data quality partitioned checks on a column level that are checking monthly partitions or rows for each day of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_nulls_count](../../../../checks/column/nulls/nulls-count/)|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNullsCountCheckSpec](../../../../checks/column/nulls/nulls-count/)| | | |
|[monthly_partition_nulls_percent](../../../../checks/column/nulls/nulls-percent/)|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNullsPercentCheckSpec](../../../../checks/column/nulls/nulls-percent/)| | | |
|[monthly_partition_not_nulls_count](../../../../checks/column/nulls/not-nulls-count/)|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNotNullsCountCheckSpec](../../../../checks/column/nulls/not-nulls-count/)| | | |
|[monthly_partition_not_nulls_percent](../../../../checks/column/nulls/not-nulls-percent/)|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNotNullsPercentCheckSpec](../../../../checks/column/nulls/not-nulls-percent/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnDatatypeMonthlyPartitionedChecksSpec  
Container of datatype data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_string_datatype_detected](../../../../checks/column/datatype/string-datatype-detected/)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDatatypeStringDatatypeDetectedCheckSpec](../../../../checks/column/datatype/string-datatype-detected/)| | | |
|[monthly_partition_string_datatype_changed](../../../../checks/column/datatype/string-datatype-changed/)|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDatatypeStringDatatypeChangedCheckSpec](../../../../checks/column/datatype/string-datatype-changed/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnNumericMonthlyPartitionedChecksSpec  
Container of numeric data quality partitioned checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_negative_count](../../../../checks/column/numeric/negative-count/)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNegativeCountCheckSpec](../../../../checks/column/numeric/negative-count/)| | | |
|[monthly_partition_negative_percent](../../../../checks/column/numeric/negative-percent/)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNegativePercentCheckSpec](../../../../checks/column/numeric/negative-percent/)| | | |
|[monthly_partition_non_negative_count](../../../../checks/column/numeric/non-negative-count/)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNonNegativeCountCheckSpec](../../../../checks/column/numeric/non-negative-count/)| | | |
|[monthly_partition_non_negative_percent](../../../../checks/column/numeric/non-negative-percent/)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNonNegativePercentCheckSpec](../../../../checks/column/numeric/non-negative-percent/)| | | |
|[monthly_partition_expected_numbers_in_use_count](../../../../checks/column/numeric/expected-numbers-in-use-count/)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnExpectedNumbersInUseCountCheckSpec](../../../../checks/column/numeric/expected-numbers-in-use-count/)| | | |
|[monthly_partition_number_value_in_set_percent](../../../../checks/column/numeric/number-value-in-set-percent/)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNumberValueInSetPercentCheckSpec](../../../../checks/column/numeric/number-value-in-set-percent/)| | | |
|[monthly_partition_values_in_range_numeric_percent](../../../../checks/column/numeric/values-in-range-numeric-percent/)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValuesInRangeNumericPercentCheckSpec](../../../../checks/column/numeric/values-in-range-numeric-percent/)| | | |
|[monthly_partition_values_in_range_integers_percent](../../../../checks/column/numeric/values-in-range-integers-percent/)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValuesInRangeIntegersPercentCheckSpec](../../../../checks/column/numeric/values-in-range-integers-percent/)| | | |
|[monthly_partition_value_below_min_value_count](../../../../checks/column/numeric/value-below-min-value-count/)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueBelowMinValueCountCheckSpec](../../../../checks/column/numeric/value-below-min-value-count/)| | | |
|[monthly_partition_value_below_min_value_percent](../../../../checks/column/numeric/value-below-min-value-percent/)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueBelowMinValuePercentCheckSpec](../../../../checks/column/numeric/value-below-min-value-percent/)| | | |
|[monthly_partition_value_above_max_value_count](../../../../checks/column/numeric/value-above-max-value-count/)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueAboveMaxValueCountCheckSpec](../../../../checks/column/numeric/value-above-max-value-count/)| | | |
|[monthly_partition_value_above_max_value_percent](../../../../checks/column/numeric/value-above-max-value-percent/)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueAboveMaxValuePercentCheckSpec](../../../../checks/column/numeric/value-above-max-value-percent/)| | | |
|[monthly_partition_max_in_range](../../../../checks/column/numeric/max-in-range/)|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMaxInRangeCheckSpec](../../../../checks/column/numeric/max-in-range/)| | | |
|[monthly_partition_min_in_range](../../../../checks/column/numeric/min-in-range/)|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMinInRangeCheckSpec](../../../../checks/column/numeric/min-in-range/)| | | |
|[monthly_partition_mean_in_range](../../../../checks/column/numeric/mean-in-range/)|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMeanInRangeCheckSpec](../../../../checks/column/numeric/mean-in-range/)| | | |
|[monthly_partition_percentile_in_range](../../../../checks/column/numeric/percentile-in-range/)|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentileInRangeCheckSpec](../../../../checks/column/numeric/percentile-in-range/)| | | |
|[monthly_partition_median_in_range](../../profiling_checks/column-profiling-checks/#ColumnMedianInRangeCheckSpec)|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMedianInRangeCheckSpec](../../profiling_checks/column-profiling-checks/#ColumnMedianInRangeCheckSpec)| | | |
|[monthly_partition_percentile_10_in_range](../../profiling_checks/column-profiling-checks/#ColumnPercentile10InRangeCheckSpec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile10InRangeCheckSpec](../../profiling_checks/column-profiling-checks/#ColumnPercentile10InRangeCheckSpec)| | | |
|[monthly_partition_percentile_25_in_range](../../profiling_checks/column-profiling-checks/#ColumnPercentile25InRangeCheckSpec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile25InRangeCheckSpec](../../profiling_checks/column-profiling-checks/#ColumnPercentile25InRangeCheckSpec)| | | |
|[monthly_partition_percentile_75_in_range](../../profiling_checks/column-profiling-checks/#ColumnPercentile75InRangeCheckSpec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile75InRangeCheckSpec](../../profiling_checks/column-profiling-checks/#ColumnPercentile75InRangeCheckSpec)| | | |
|[monthly_partition_percentile_90_in_range](../../profiling_checks/column-profiling-checks/#ColumnPercentile90InRangeCheckSpec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile90InRangeCheckSpec](../../profiling_checks/column-profiling-checks/#ColumnPercentile90InRangeCheckSpec)| | | |
|[monthly_partition_sample_stddev_in_range](../../../../checks/column/numeric/sample-stddev-in-range/)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSampleStddevInRangeCheckSpec](../../../../checks/column/numeric/sample-stddev-in-range/)| | | |
|[monthly_partition_population_stddev_in_range](../../../../checks/column/numeric/population-stddev-in-range/)|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPopulationStddevInRangeCheckSpec](../../../../checks/column/numeric/population-stddev-in-range/)| | | |
|[monthly_partition_sample_variance_in_range](../../../../checks/column/numeric/sample-variance-in-range/)|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSampleVarianceInRangeCheckSpec](../../../../checks/column/numeric/sample-variance-in-range/)| | | |
|[monthly_partition_population_variance_in_range](../../../../checks/column/numeric/population-variance-in-range/)|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPopulationVarianceInRangeCheckSpec](../../../../checks/column/numeric/population-variance-in-range/)| | | |
|[monthly_partition_sum_in_range](../../../../checks/column/numeric/sum-in-range/)|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSumInRangeCheckSpec](../../../../checks/column/numeric/sum-in-range/)| | | |
|[monthly_partition_invalid_latitude_count](../../../../checks/column/numeric/invalid-latitude-count/)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnInvalidLatitudeCountCheckSpec](../../../../checks/column/numeric/invalid-latitude-count/)| | | |
|[monthly_partition_valid_latitude_percent](../../../../checks/column/numeric/valid-latitude-percent/)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValidLatitudePercentCheckSpec](../../../../checks/column/numeric/valid-latitude-percent/)| | | |
|[monthly_partition_invalid_longitude_count](../../../../checks/column/numeric/invalid-longitude-count/)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnInvalidLongitudeCountCheckSpec](../../../../checks/column/numeric/invalid-longitude-count/)| | | |
|[monthly_partition_valid_longitude_percent](../../../../checks/column/numeric/valid-longitude-percent/)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValidLongitudePercentCheckSpec](../../../../checks/column/numeric/valid-longitude-percent/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnAccuracyMonthlyPartitionedChecksSpec  
Container of accuracy data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnSqlMonthlyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_sql_condition_passed_percent_on_column](../../../../checks/column/sql/sql-condition-passed-percent-on-column/)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlConditionPassedPercentCheckSpec](../../../../checks/column/sql/sql-condition-passed-percent-on-column/)| | | |
|[monthly_partition_sql_condition_failed_count_on_column](../../../../checks/column/sql/sql-condition-failed-count-on-column/)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlConditionFailedCountCheckSpec](../../../../checks/column/sql/sql-condition-failed-count-on-column/)| | | |
|[monthly_partition_sql_aggregate_expr_column](../../../../checks/column/sql/sql-aggregate-expr-column/)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlAggregateExprCheckSpec](../../../../checks/column/sql/sql-aggregate-expr-column/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnBoolMonthlyPartitionedChecksSpec  
Container of boolean data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_true_percent](../../../../checks/column/bool/true-percent/)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnTruePercentCheckSpec](../../../../checks/column/bool/true-percent/)| | | |
|[monthly_partition_false_percent](../../../../checks/column/bool/false-percent/)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnFalsePercentCheckSpec](../../../../checks/column/bool/false-percent/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnComparisonMonthlyPartitionedChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [ColumnComparisonMonthlyPartitionedChecksSpec](../column-monthly-partitioned-checks/#ColumnComparisonMonthlyPartitionedChecksSpec)]| | | |









___  

## ColumnIntegrityMonthlyPartitionedChecksSpec  
Container of integrity data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_foreign_key_not_match_count](../../../../checks/column/integrity/foreign-key-not-match-count/)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](../../../../checks/column/integrity/foreign-key-not-match-count/)| | | |
|[monthly_partition_foreign_key_match_percent](../../../../checks/column/integrity/foreign-key-match-percent/)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](../../../../checks/column/integrity/foreign-key-match-percent/)| | | |
|[custom_checks](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling_checks/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

