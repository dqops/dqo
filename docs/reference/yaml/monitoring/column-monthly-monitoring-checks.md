# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## ColumnNullsMonthlyMonitoringChecksSpec
Container of nulls data quality monitoring checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_nulls_count](../../../checks/column/nulls/nulls-count.md)|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent count check result for each month when the data quality check was evaluated.|[ColumnNullsCountCheckSpec](../../../checks/column/nulls/nulls-count.md)| | | |
|[monthly_nulls_percent](../../../checks/column/nulls/nulls-percent.md)|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnNullsPercentCheckSpec](../../../checks/column/nulls/nulls-percent.md)| | | |
|[monthly_not_nulls_count](../../../checks/column/nulls/not-nulls-count.md)|Detects columns that are empty and have no values (with the rule threshold min_count&#x3D;1). Verifies that the number of not null values in a column does not exceed the minimum accepted count. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnNotNullsCountCheckSpec](../../../checks/column/nulls/not-nulls-count.md)| | | |
|[monthly_not_nulls_percent](../../../checks/column/nulls/not-nulls-percent.md)|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnNotNullsPercentCheckSpec](../../../checks/column/nulls/not-nulls-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnBlanksMonthlyMonitoringChecksSpec
Container of blank value detection data quality monitoring checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_empty_text_found](../../../checks/column/blanks/empty-text-found.md)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnBlanksEmptyTextFoundCheckSpec](../../../checks/column/blanks/empty-text-found.md)| | | |
|[monthly_whitespace_text_found](../../../checks/column/blanks/whitespace-text-found.md)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnBlanksWhitespaceTextFoundCheckSpec](../../../checks/column/blanks/whitespace-text-found.md)| | | |
|[monthly_null_placeholder_text_found](../../../checks/column/blanks/null-placeholder-text-found.md)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnBlanksNullPlaceholderTextFoundCheckSpec](../../../checks/column/blanks/null-placeholder-text-found.md)| | | |
|[monthly_empty_text_percent](../../../checks/column/blanks/empty-text-percent.md)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnBlanksEmptyTextPercentCheckSpec](../../../checks/column/blanks/empty-text-percent.md)| | | |
|[monthly_whitespace_text_percent](../../../checks/column/blanks/whitespace-text-percent.md)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnBlanksWhitespaceTextPercentCheckSpec](../../../checks/column/blanks/whitespace-text-percent.md)| | | |
|[monthly_null_placeholder_text_percent](../../../checks/column/blanks/null-placeholder-text-percent.md)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnBlanksNullPlaceholderTextPercentCheckSpec](../../../checks/column/blanks/null-placeholder-text-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnComparisonMonthlyMonitoringChecksSpec
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 This is the configuration for monthly monitoring checks that are counted in KPIs.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_sum_match](../../../checks/column/comparisons/sum-match.md)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonSumMatchCheckSpec](../../../checks/column/comparisons/sum-match.md)| | | |
|[monthly_min_match](../../../checks/column/comparisons/min-match.md)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonMinMatchCheckSpec](../../../checks/column/comparisons/min-match.md)| | | |
|[monthly_max_match](../../../checks/column/comparisons/max-match.md)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonMaxMatchCheckSpec](../../../checks/column/comparisons/max-match.md)| | | |
|[monthly_mean_match](../../../checks/column/comparisons/mean-match.md)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonMeanMatchCheckSpec](../../../checks/column/comparisons/mean-match.md)| | | |
|[monthly_not_null_count_match](../../../checks/column/comparisons/not-null-count-match.md)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonNotNullCountMatchCheckSpec](../../../checks/column/comparisons/not-null-count-match.md)| | | |
|[monthly_null_count_match](../../../checks/column/comparisons/null-count-match.md)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnComparisonNullCountMatchCheckSpec](../../../checks/column/comparisons/null-count-match.md)| | | |
|reference_column|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|string| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnAccuracyMonthlyMonitoringChecksSpec
Container of accuracy data quality monitoring checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_total_sum_match_percent](../../../checks/column/accuracy/total-sum-match-percent.md)|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnAccuracyTotalSumMatchPercentCheckSpec](../../../checks/column/accuracy/total-sum-match-percent.md)| | | |
|[monthly_total_min_match_percent](../../../checks/column/accuracy/total-min-match-percent.md)|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnAccuracyTotalMinMatchPercentCheckSpec](../../../checks/column/accuracy/total-min-match-percent.md)| | | |
|[monthly_total_max_match_percent](../../../checks/column/accuracy/total-max-match-percent.md)|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnAccuracyTotalMaxMatchPercentCheckSpec](../../../checks/column/accuracy/total-max-match-percent.md)| | | |
|[monthly_total_average_match_percent](../../../checks/column/accuracy/total-average-match-percent.md)|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnAccuracyTotalAverageMatchPercentCheckSpec](../../../checks/column/accuracy/total-average-match-percent.md)| | | |
|[monthly_total_not_null_count_match_percent](../../../checks/column/accuracy/total-not-null-count-match-percent.md)|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec](../../../checks/column/accuracy/total-not-null-count-match-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnBoolMonthlyMonitoringChecksSpec
Container of boolean monitoring data quality checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_true_percent](../../../checks/column/bool/true-percent.md)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnTruePercentCheckSpec](../../../checks/column/bool/true-percent.md)| | | |
|[monthly_false_percent](../../../checks/column/bool/false-percent.md)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnFalsePercentCheckSpec](../../../checks/column/bool/false-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnIntegrityMonthlyMonitoringChecksSpec
Container of integrity data quality monitoring checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_lookup_key_not_found](../../../checks/column/integrity/lookup-key-not-found.md)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnIntegrityLookupKeyNotFoundCountCheckSpec](../../../checks/column/integrity/lookup-key-not-found.md)| | | |
|[monthly_lookup_key_found_percent](../../../checks/column/integrity/lookup-key-found-percent.md)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](../../../checks/column/integrity/lookup-key-found-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnAnomalyMonthlyMonitoringChecksSpec
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_sum_change](../../../checks/column/anomaly/sum-change.md)|Verifies that the sum in a column changed in a fixed rate since the last readout.|[ColumnSumChangeCheckSpec](../../../checks/column/anomaly/sum-change.md)| | | |
|[monthly_mean_change](../../../checks/column/anomaly/mean-change.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout.|[ColumnMeanChangeCheckSpec](../../../checks/column/anomaly/mean-change.md)| | | |
|[monthly_median_change](../../../checks/column/anomaly/median-change.md)|Verifies that the median in a column changed in a fixed rate since the last readout.|[ColumnMedianChangeCheckSpec](../../../checks/column/anomaly/median-change.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnPatternsMonthlyMonitoringChecksSpec
Container of built-in preconfigured monthly monitoring checks on a column level that are checking for values matching patterns (regular expressions) in text columns.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_text_not_matching_regex_found](../../../checks/column/patterns/text-not-matching-regex-found.md)|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|[ColumnTextNotMatchingRegexFoundCheckSpec](../../../checks/column/patterns/text-not-matching-regex-found.md)| | | |
|[monthly_texts_matching_regex_percent](../../../checks/column/patterns/texts-matching-regex-percent.md)|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|[ColumnTextsMatchingRegexPercentCheckSpec](../../../checks/column/patterns/texts-matching-regex-percent.md)| | | |
|[monthly_invalid_email_format_found](../../../checks/column/patterns/invalid-email-format-found.md)|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|[ColumnInvalidEmailFormatFoundCheckSpec](../../../checks/column/patterns/invalid-email-format-found.md)| | | |
|[monthly_text_not_matching_date_pattern_found](../../../checks/column/patterns/text-not-matching-date-pattern-found.md)|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|[ColumnTextNotMatchingDatePatternFoundCheckSpec](../../../checks/column/patterns/text-not-matching-date-pattern-found.md)| | | |
|[monthly_text_matching_date_pattern_percent](../../../checks/column/patterns/text-matching-date-pattern-percent.md)|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|[ColumnTextMatchingDatePatternPercentCheckSpec](../../../checks/column/patterns/text-matching-date-pattern-percent.md)| | | |
|[monthly_text_matching_name_pattern_percent](../../../checks/column/patterns/text-matching-name-pattern-percent.md)|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|[ColumnTextMatchingNamePatternPercentCheckSpec](../../../checks/column/patterns/text-matching-name-pattern-percent.md)| | | |
|[monthly_invalid_uuid_format_found](../../../checks/column/patterns/invalid-uuid-format-found.md)|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|[ColumnInvalidUuidFormatFoundCheckSpec](../../../checks/column/patterns/invalid-uuid-format-found.md)| | | |
|[monthly_valid_uuid_format_percent](../../../checks/column/patterns/valid-uuid-format-percent.md)|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|[ColumnValidUuidFormatPercentCheckSpec](../../../checks/column/patterns/valid-uuid-format-percent.md)| | | |
|[monthly_invalid_ip4_address_format_found](../../../checks/column/patterns/invalid-ip4-address-format-found.md)|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|[ColumnInvalidIp4AddressFormatFoundCheckSpec](../../../checks/column/patterns/invalid-ip4-address-format-found.md)| | | |
|[monthly_invalid_ip6_address_format_found](../../../checks/column/patterns/invalid-ip6-address-format-found.md)|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|[ColumnInvalidIp6AddressFormatFoundCheckSpec](../../../checks/column/patterns/invalid-ip6-address-format-found.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnDatetimeMonthlyMonitoringChecksSpec
Container of date-time data quality monitoring checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_date_values_in_future_percent](../../../checks/column/datetime/date-values-in-future-percent.md)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnDateValuesInFuturePercentCheckSpec](../../../checks/column/datetime/date-values-in-future-percent.md)| | | |
|[monthly_datetime_value_in_range_date_percent](../../../checks/column/datetime/datetime-value-in-range-date-percent.md)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](../../../checks/column/datetime/datetime-value-in-range-date-percent.md)| | | |
|[monthly_date_match_format_percent](../../../checks/column/datetime/date-match-format-percent.md)|Verifies that the percentage of date values matching the given format in a text column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly monitoring.|[ColumnDatetimeDateMatchFormatPercentCheckSpec](../../../checks/column/datetime/date-match-format-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnTextMonthlyMonitoringChecksSpec
Container of text data quality monitoring checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_text_max_length](../../../checks/column/text/text-max-length.md)|Verifies that the length of a text in a column does not exceed the maximum accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextMaxLengthCheckSpec](../../../checks/column/text/text-max-length.md)| | | |
|[monthly_text_min_length](../../../checks/column/text/text-min-length.md)|Verifies that the length of a text in a column does not fall below the minimum accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextMinLengthCheckSpec](../../../checks/column/text/text-min-length.md)| | | |
|[monthly_text_mean_length](../../../checks/column/text/text-mean-length.md)|Verifies that the length of a text in a column does not exceed the mean accepted length. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextMeanLengthCheckSpec](../../../checks/column/text/text-mean-length.md)| | | |
|[monthly_text_length_below_min_length](../../../checks/column/text/text-length-below-min-length.md)|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextLengthBelowMinLengthCheckSpec](../../../checks/column/text/text-length-below-min-length.md)| | | |
|[monthly_text_length_below_min_length_percent](../../../checks/column/text/text-length-below-min-length-percent.md)|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextLengthBelowMinLengthPercentCheckSpec](../../../checks/column/text/text-length-below-min-length-percent.md)| | | |
|[monthly_text_length_above_max_length](../../../checks/column/text/text-length-above-max-length.md)|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextLengthAboveMaxLengthCheckSpec](../../../checks/column/text/text-length-above-max-length.md)| | | |
|[monthly_text_length_above_max_length_percent](../../../checks/column/text/text-length-above-max-length-percent.md)|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextLengthAboveMaxLengthPercentCheckSpec](../../../checks/column/text/text-length-above-max-length-percent.md)| | | |
|[monthly_text_length_in_range_percent](../../../checks/column/text/text-length-in-range-percent.md)|The check measures the percentage of those text values with length in the range provided by the user in the column. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextLengthInRangePercentCheckSpec](../../../checks/column/text/text-length-in-range-percent.md)| | | |
|[monthly_text_parsable_to_boolean_percent](../../../checks/column/text/text-parsable-to-boolean-percent.md)|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextParsableToBooleanPercentCheckSpec](../../../checks/column/text/text-parsable-to-boolean-percent.md)| | | |
|[monthly_text_parsable_to_integer_percent](../../../checks/column/text/text-parsable-to-integer-percent.md)|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextParsableToIntegerPercentCheckSpec](../../../checks/column/text/text-parsable-to-integer-percent.md)| | | |
|[monthly_text_parsable_to_float_percent](../../../checks/column/text/text-parsable-to-float-percent.md)|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextParsableToFloatPercentCheckSpec](../../../checks/column/text/text-parsable-to-float-percent.md)| | | |
|[monthly_text_parsable_to_date_percent](../../../checks/column/text/text-parsable-to-date-percent.md)|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextParsableToDatePercentCheckSpec](../../../checks/column/text/text-parsable-to-date-percent.md)| | | |
|[monthly_text_surrounded_by_whitespace](../../../checks/column/text/text-surrounded-by-whitespace.md)|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextSurroundedByWhitespaceCheckSpec](../../../checks/column/text/text-surrounded-by-whitespace.md)| | | |
|[monthly_text_surrounded_by_whitespace_percent](../../../checks/column/text/text-surrounded-by-whitespace-percent.md)|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextSurroundedByWhitespacePercentCheckSpec](../../../checks/column/text/text-surrounded-by-whitespace-percent.md)| | | |
|[monthly_text_valid_country_code_percent](../../../checks/column/text/text-valid-country-code-percent.md)|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextValidCountryCodePercentCheckSpec](../../../checks/column/text/text-valid-country-code-percent.md)| | | |
|[monthly_text_valid_currency_code_percent](../../../checks/column/text/text-valid-currency-code-percent.md)|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextValidCurrencyCodePercentCheckSpec](../../../checks/column/text/text-valid-currency-code-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnDatatypeMonthlyMonitoringChecksSpec
Container of datatype data quality monitoring checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_detected_datatype_in_text](../../../checks/column/datatype/detected-datatype-in-text.md)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnDetectedDatatypeInTextCheckSpec](../../../checks/column/datatype/detected-datatype-in-text.md)| | | |
|[monthly_detected_datatype_in_text_changed](../../../checks/column/datatype/detected-datatype-in-text-changed.md)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDatatypeStringDatatypeChangedCheckSpec](../../../checks/column/datatype/detected-datatype-in-text-changed.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnMonthlyMonitoringCheckCategoriesSpec
Container of column level monthly monitoring checks. Contains categories of monthly monitoring checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](./column-monthly-monitoring-checks.md#ColumnNullsMonthlyMonitoringChecksSpec)|Monthly monitoring checks of nulls in the column|[ColumnNullsMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnNullsMonthlyMonitoringChecksSpec)| | | |
|[uniqueness](./column-monthly-monitoring-checks.md#ColumnUniquenessMonthlyMonitoringChecksSpec)|Monthly monitoring checks of uniqueness in the column|[ColumnUniquenessMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnUniquenessMonthlyMonitoringChecksSpec)| | | |
|[accepted_values](./column-monthly-monitoring-checks.md#ColumnAcceptedValuesMonthlyMonitoringChecksSpec)|Configuration of accepted values checks on a column level|[ColumnAcceptedValuesMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnAcceptedValuesMonthlyMonitoringChecksSpec)| | | |
|[text](./column-monthly-monitoring-checks.md#ColumnTextMonthlyMonitoringChecksSpec)|Monthly monitoring checks of text values in the column|[ColumnTextMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnTextMonthlyMonitoringChecksSpec)| | | |
|[blanks](./column-monthly-monitoring-checks.md#ColumnBlanksMonthlyMonitoringChecksSpec)|Configuration of column level checks that detect blank and whitespace values|[ColumnBlanksMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnBlanksMonthlyMonitoringChecksSpec)| | | |
|[patterns](./column-monthly-monitoring-checks.md#ColumnPatternsMonthlyMonitoringChecksSpec)|Monthly monitoring checks of pattern matching on a column level|[ColumnPatternsMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnPatternsMonthlyMonitoringChecksSpec)| | | |
|[pii](./column-monthly-monitoring-checks.md#ColumnPiiMonthlyMonitoringChecksSpec)|Monthly monitoring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnPiiMonthlyMonitoringChecksSpec)| | | |
|[numeric](./column-monthly-monitoring-checks.md#ColumnNumericMonthlyMonitoringChecksSpec)|Monthly monitoring checks of numeric values in the column|[ColumnNumericMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnNumericMonthlyMonitoringChecksSpec)| | | |
|[anomaly](./column-monthly-monitoring-checks.md#ColumnAnomalyMonthlyMonitoringChecksSpec)|Monthly monitoring checks of anomalies in numeric columns|[ColumnAnomalyMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnAnomalyMonthlyMonitoringChecksSpec)| | | |
|[datetime](./column-monthly-monitoring-checks.md#ColumnDatetimeMonthlyMonitoringChecksSpec)|Monthly monitoring checks of datetime in the column|[ColumnDatetimeMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnDatetimeMonthlyMonitoringChecksSpec)| | | |
|[bool](./column-monthly-monitoring-checks.md#ColumnBoolMonthlyMonitoringChecksSpec)|Monthly monitoring checks of booleans in the column|[ColumnBoolMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnBoolMonthlyMonitoringChecksSpec)| | | |
|[integrity](./column-monthly-monitoring-checks.md#ColumnIntegrityMonthlyMonitoringChecksSpec)|Monthly monitoring checks of integrity in the column|[ColumnIntegrityMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnIntegrityMonthlyMonitoringChecksSpec)| | | |
|[accuracy](./column-monthly-monitoring-checks.md#ColumnAccuracyMonthlyMonitoringChecksSpec)|Monthly monitoring checks of accuracy in the column|[ColumnAccuracyMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnAccuracyMonthlyMonitoringChecksSpec)| | | |
|[custom_sql](./column-monthly-monitoring-checks.md#ColumnCustomSqlMonthlyMonitoringChecksSpec)|Monthly monitoring checks of custom SQL checks in the column|[ColumnCustomSqlMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnCustomSqlMonthlyMonitoringChecksSpec)| | | |
|[datatype](./column-monthly-monitoring-checks.md#ColumnDatatypeMonthlyMonitoringChecksSpec)|Monthly monitoring checks of datatype in the column|[ColumnDatatypeMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnDatatypeMonthlyMonitoringChecksSpec)| | | |
|[schema](./column-monthly-monitoring-checks.md#ColumnSchemaMonthlyMonitoringChecksSpec)|Monthly monitoring column schema checks|[ColumnSchemaMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnSchemaMonthlyMonitoringChecksSpec)| | | |
|[comparisons](./column-monthly-monitoring-checks.md#ColumnComparisonMonthlyMonitoringChecksSpecMap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonMonthlyMonitoringChecksSpecMap](./column-monthly-monitoring-checks.md#ColumnComparisonMonthlyMonitoringChecksSpecMap)| | | |
|[custom](../profiling/table-profiling-checks.md#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks.md#CustomCheckSpecMap)| | | |









___


## ColumnSchemaMonthlyMonitoringChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking the column schema at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_column_exists](../../../checks/column/schema/column-exists.md)|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnSchemaColumnExistsCheckSpec](../../../checks/column/schema/column-exists.md)| | | |
|[monthly_column_type_changed](../../../checks/column/schema/column-type-changed.md)|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.|[ColumnSchemaTypeChangedCheckSpec](../../../checks/column/schema/column-type-changed.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnCustomSqlMonthlyMonitoringChecksSpec
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_sql_condition_failed_on_column](../../../checks/column/custom_sql/sql-condition-failed-on-column.md)|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores the most recent captured count of failed rows for each month when the data quality check was evaluated.|[ColumnSqlConditionFailedCheckSpec](../../../checks/column/custom_sql/sql-condition-failed-on-column.md)| | | |
|[monthly_sql_condition_passed_percent_on_column](../../../checks/column/custom_sql/sql-condition-passed-percent-on-column.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;.  Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnSqlConditionPassedPercentCheckSpec](../../../checks/column/custom_sql/sql-condition-passed-percent-on-column.md)| | | |
|[monthly_sql_aggregate_expression_on_column](../../../checks/column/custom_sql/sql-aggregate-expression-on-column.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnSqlAggregateExpressionCheckSpec](../../../checks/column/custom_sql/sql-aggregate-expression-on-column.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnPiiMonthlyMonitoringChecksSpec
Container of PII data quality monitoring checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_contains_usa_phone_percent](../../../checks/column/pii/contains-usa-phone-percent.md)|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnPiiContainsUsaPhonePercentCheckSpec](../../../checks/column/pii/contains-usa-phone-percent.md)| | | |
|[monthly_contains_email_percent](../../../checks/column/pii/contains-email-percent.md)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnPiiContainsEmailPercentCheckSpec](../../../checks/column/pii/contains-email-percent.md)| | | |
|[monthly_contains_usa_zipcode_percent](../../../checks/column/pii/contains-usa-zipcode-percent.md)|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](../../../checks/column/pii/contains-usa-zipcode-percent.md)| | | |
|[monthly_contains_ip4_percent](../../../checks/column/pii/contains-ip4-percent.md)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnPiiContainsIp4PercentCheckSpec](../../../checks/column/pii/contains-ip4-percent.md)| | | |
|[monthly_contains_ip6_percent](../../../checks/column/pii/contains-ip6-percent.md)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnPiiContainsIp6PercentCheckSpec](../../../checks/column/pii/contains-ip6-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnUniquenessMonthlyMonitoringChecksSpec
Container of uniqueness data quality monitoring checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_distinct_count](../../../checks/column/uniqueness/distinct-count.md)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnDistinctCountCheckSpec](../../../checks/column/uniqueness/distinct-count.md)| | | |
|[monthly_distinct_percent](../../../checks/column/uniqueness/distinct-percent.md)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnDistinctPercentCheckSpec](../../../checks/column/uniqueness/distinct-percent.md)| | | |
|[monthly_duplicate_count](../../../checks/column/uniqueness/duplicate-count.md)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnDuplicateCountCheckSpec](../../../checks/column/uniqueness/duplicate-count.md)| | | |
|[monthly_duplicate_percent](../../../checks/column/uniqueness/duplicate-percent.md)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent check result for each month when the data quality check was evaluated.|[ColumnDuplicatePercentCheckSpec](../../../checks/column/uniqueness/duplicate-percent.md)| | | |
|[monthly_distinct_count_change](../../../checks/column/uniqueness/distinct-count-change.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|[ColumnDistinctCountChangeCheckSpec](../../../checks/column/uniqueness/distinct-count-change.md)| | | |
|[monthly_distinct_percent_change](../../../checks/column/uniqueness/distinct-percent-change.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|[ColumnDistinctPercentChangeCheckSpec](../../../checks/column/uniqueness/distinct-percent-change.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnNumericMonthlyMonitoringChecksSpec
Container of built-in preconfigured data quality monitoring on a column level that are checking numeric values at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_number_below_min_value](../../../checks/column/numeric/number-below-min-value.md)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnNumberBelowMinValueCheckSpec](../../../checks/column/numeric/number-below-min-value.md)| | | |
|[monthly_number_above_max_value](../../../checks/column/numeric/number-above-max-value.md)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnNumberAboveMaxValueCheckSpec](../../../checks/column/numeric/number-above-max-value.md)| | | |
|[monthly_negative_values](../../../checks/column/numeric/negative-values.md)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnNegativeCountCheckSpec](../../../checks/column/numeric/negative-values.md)| | | |
|[monthly_negative_values_percent](../../../checks/column/numeric/negative-values-percent.md)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnNegativePercentCheckSpec](../../../checks/column/numeric/negative-values-percent.md)| | | |
|[monthly_number_below_min_value_percent](../../../checks/column/numeric/number-below-min-value-percent.md)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnNumberBelowMinValuePercentCheckSpec](../../../checks/column/numeric/number-below-min-value-percent.md)| | | |
|[monthly_number_above_max_value_percent](../../../checks/column/numeric/number-above-max-value-percent.md)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnNumberAboveMaxValuePercentCheckSpec](../../../checks/column/numeric/number-above-max-value-percent.md)| | | |
|[monthly_number_in_range_percent](../../../checks/column/numeric/number-in-range-percent.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnNumberInRangePercentCheckSpec](../../../checks/column/numeric/number-in-range-percent.md)| | | |
|[monthly_integer_in_range_percent](../../../checks/column/numeric/integer-in-range-percent.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnIntegerInRangePercentCheckSpec](../../../checks/column/numeric/integer-in-range-percent.md)| | | |
|[monthly_min_in_range](../../../checks/column/numeric/min-in-range.md)|Verifies that the minimal value in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnMinInRangeCheckSpec](../../../checks/column/numeric/min-in-range.md)| | | |
|[monthly_max_in_range](../../../checks/column/numeric/max-in-range.md)|Verifies that the maximal value in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnMaxInRangeCheckSpec](../../../checks/column/numeric/max-in-range.md)| | | |
|[monthly_sum_in_range](../../../checks/column/numeric/sum-in-range.md)|Verifies that the sum of all values in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnSumInRangeCheckSpec](../../../checks/column/numeric/sum-in-range.md)| | | |
|[monthly_mean_in_range](../../../checks/column/numeric/mean-in-range.md)|Verifies that the average (mean) of all values in a column does not exceed the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnMeanInRangeCheckSpec](../../../checks/column/numeric/mean-in-range.md)| | | |
|[monthly_median_in_range](../../../checks/column/numeric/median-in-range.md)|Verifies that the median of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnMedianInRangeCheckSpec](../../../checks/column/numeric/median-in-range.md)| | | |
|[monthly_percentile_in_range](../../../checks/column/numeric/percentile-in-range.md)|Verifies that the percentile of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnPercentileInRangeCheckSpec](../../../checks/column/numeric/percentile-in-range.md)| | | |
|[monthly_percentile_10_in_range](../../../checks/column/numeric/percentile-10-in-range.md)|Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnPercentile10InRangeCheckSpec](../../../checks/column/numeric/percentile-10-in-range.md)| | | |
|[monthly_percentile_25_in_range](../../../checks/column/numeric/percentile-25-in-range.md)|Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnPercentile25InRangeCheckSpec](../../../checks/column/numeric/percentile-25-in-range.md)| | | |
|[monthly_percentile_75_in_range](../../../checks/column/numeric/percentile-75-in-range.md)|Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnPercentile75InRangeCheckSpec](../../../checks/column/numeric/percentile-75-in-range.md)| | | |
|[monthly_percentile_90_in_range](../../../checks/column/numeric/percentile-90-in-range.md)|Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnPercentile90InRangeCheckSpec](../../../checks/column/numeric/percentile-90-in-range.md)| | | |
|[monthly_sample_stddev_in_range](../../../checks/column/numeric/sample-stddev-in-range.md)|Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnSampleStddevInRangeCheckSpec](../../../checks/column/numeric/sample-stddev-in-range.md)| | | |
|[monthly_population_stddev_in_range](../../../checks/column/numeric/population-stddev-in-range.md)|Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnPopulationStddevInRangeCheckSpec](../../../checks/column/numeric/population-stddev-in-range.md)| | | |
|[monthly_sample_variance_in_range](../../../checks/column/numeric/sample-variance-in-range.md)|Verifies that the sample variance of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnSampleVarianceInRangeCheckSpec](../../../checks/column/numeric/sample-variance-in-range.md)| | | |
|[monthly_population_variance_in_range](../../../checks/column/numeric/population-variance-in-range.md)|Verifies that the population variance of all values in a column is not outside the expected range. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnPopulationVarianceInRangeCheckSpec](../../../checks/column/numeric/population-variance-in-range.md)| | | |
|[monthly_invalid_latitude](../../../checks/column/numeric/invalid-latitude.md)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnInvalidLatitudeCountCheckSpec](../../../checks/column/numeric/invalid-latitude.md)| | | |
|[monthly_valid_latitude_percent](../../../checks/column/numeric/valid-latitude-percent.md)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnValidLatitudePercentCheckSpec](../../../checks/column/numeric/valid-latitude-percent.md)| | | |
|[monthly_invalid_longitude](../../../checks/column/numeric/invalid-longitude.md)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnInvalidLongitudeCountCheckSpec](../../../checks/column/numeric/invalid-longitude.md)| | | |
|[monthly_valid_longitude_percent](../../../checks/column/numeric/valid-longitude-percent.md)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnValidLongitudePercentCheckSpec](../../../checks/column/numeric/valid-longitude-percent.md)| | | |
|[monthly_non_negative_values](../../../checks/column/numeric/non-negative-values.md)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnNonNegativeCountCheckSpec](../../../checks/column/numeric/non-negative-values.md)| | | |
|[monthly_non_negative_values_percent](../../../checks/column/numeric/non-negative-values-percent.md)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnNonNegativePercentCheckSpec](../../../checks/column/numeric/non-negative-values-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnComparisonMonthlyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [ColumnComparisonMonthlyMonitoringChecksSpec](./column-monthly-monitoring-checks.md#ColumnComparisonMonthlyMonitoringChecksSpec)]| | | |









___


## ColumnAcceptedValuesMonthlyMonitoringChecksSpec
Container of accepted values data quality monitoring checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_text_found_in_set_percent](../../../checks/column/accepted_values/text-found-in-set-percent.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnTextFoundInSetPercentCheckSpec](../../../checks/column/accepted_values/text-found-in-set-percent.md)| | | |
|[monthly_number_found_in_set_percent](../../../checks/column/accepted_values/number-found-in-set-percent.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnNumberFoundInSetPercentCheckSpec](../../../checks/column/accepted_values/number-found-in-set-percent.md)| | | |
|[monthly_expected_text_values_in_use_count](../../../checks/column/accepted_values/expected-text-values-in-use-count.md)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnExpectedTextValuesInUseCountCheckSpec](../../../checks/column/accepted_values/expected-text-values-in-use-count.md)| | | |
|[monthly_expected_texts_in_top_values_count](../../../checks/column/accepted_values/expected-texts-in-top-values-count.md)|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnExpectedTextsInTopValuesCountCheckSpec](../../../checks/column/accepted_values/expected-texts-in-top-values-count.md)| | | |
|[monthly_expected_numbers_in_use_count](../../../checks/column/accepted_values/expected-numbers-in-use-count.md)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each month when the data quality check was evaluated.|[ColumnExpectedNumbersInUseCountCheckSpec](../../../checks/column/accepted_values/expected-numbers-in-use-count.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


