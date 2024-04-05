---
title: DQOps YAML file definitions
---
# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## ColumnMonthlyPartitionedCheckCategoriesSpec
Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`nulls`](./column-monthly-partitioned-checks.md#columnnullsmonthlypartitionedchecksspec)</span>|Monthly partitioned checks of nulls in the column|*[ColumnNullsMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columnnullsmonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`uniqueness`](./column-monthly-partitioned-checks.md#columnuniquenessmonthlypartitionedchecksspec)</span>|Monthly partitioned checks of uniqueness in the column|*[ColumnUniquenessMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columnuniquenessmonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`accepted_values`](./column-monthly-partitioned-checks.md#columnacceptedvaluesmonthlypartitionedchecksspec)</span>|Configuration of accepted values checks on a column level|*[ColumnAcceptedValuesMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columnacceptedvaluesmonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`text`](./column-monthly-partitioned-checks.md#columntextmonthlypartitionedchecksspec)</span>|Monthly partitioned checks of text values in the column|*[ColumnTextMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columntextmonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`whitespace`](./column-monthly-partitioned-checks.md#columnwhitespacemonthlypartitionedchecksspec)</span>|Configuration of column level checks that detect blank and whitespace values|*[ColumnWhitespaceMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columnwhitespacemonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`conversions`](./column-monthly-partitioned-checks.md#columnconversionsmonthlypartitionedchecksspec)</span>|Configuration of conversion testing checks on a column level.|*[ColumnConversionsMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columnconversionsmonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`patterns`](./column-monthly-partitioned-checks.md#columnpatternsmonthlypartitionedchecksspec)</span>|Monthly partitioned pattern match checks on a column level|*[ColumnPatternsMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columnpatternsmonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`pii`](./column-monthly-partitioned-checks.md#columnpiimonthlypartitionedchecksspec)</span>|Monthly partitioned checks of Personal Identifiable Information (PII) in the column|*[ColumnPiiMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columnpiimonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`numeric`](./column-monthly-partitioned-checks.md#columnnumericmonthlypartitionedchecksspec)</span>|Monthly partitioned checks of numeric values in the column|*[ColumnNumericMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columnnumericmonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`datetime`](./column-monthly-partitioned-checks.md#columndatetimemonthlypartitionedchecksspec)</span>|Monthly partitioned checks of datetime in the column|*[ColumnDatetimeMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columndatetimemonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`bool`](./column-monthly-partitioned-checks.md#columnboolmonthlypartitionedchecksspec)</span>|Monthly partitioned checks for booleans in the column|*[ColumnBoolMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columnboolmonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`integrity`](./column-monthly-partitioned-checks.md#columnintegritymonthlypartitionedchecksspec)</span>|Monthly partitioned checks for integrity in the column|*[ColumnIntegrityMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columnintegritymonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`custom_sql`](./column-monthly-partitioned-checks.md#columncustomsqlmonthlypartitionedchecksspec)</span>|Monthly partitioned checks using custom SQL expressions evaluated on the column|*[ColumnCustomSqlMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columncustomsqlmonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`datatype`](./column-monthly-partitioned-checks.md#columndatatypemonthlypartitionedchecksspec)</span>|Monthly partitioned checks for datatype in the column|*[ColumnDatatypeMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columndatatypemonthlypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`comparisons`](./column-monthly-partitioned-checks.md#columncomparisonmonthlypartitionedchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[ColumnComparisonMonthlyPartitionedChecksSpecMap](./column-monthly-partitioned-checks.md#columncomparisonmonthlypartitionedchecksspecmap)*| | | |
|<span class="no-wrap-code ">[`custom`](../profiling/table-profiling-checks.md#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](../profiling/table-profiling-checks.md#customcheckspecmap)*| | | |









___


## ColumnNullsMonthlyPartitionedChecksSpec
Container of nulls data quality partitioned checks on a column level that are checking monthly partitions or rows for each day of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_nulls_count`](../../../checks/column/nulls/nulls-count.md)</span>|Detects incomplete columns that contain any null values. Counts the number of rows having a null value. Raises a data quality issue when the count of null values is above a max_count threshold. Stores a separate data quality check result for each monthly partition.|*[ColumnNullsCountCheckSpec](../../../checks/column/nulls/nulls-count.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_nulls_percent`](../../../checks/column/nulls/nulls-percent.md)</span>|Detects incomplete columns that contain any null values. Measures the percentage of rows having a null value. Raises a data quality issue when the percentage of null values is above a max_percent threshold. Stores a separate data quality check result for each monthly partition.|*[ColumnNullsPercentCheckSpec](../../../checks/column/nulls/nulls-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_not_nulls_count`](../../../checks/column/nulls/not-nulls-count.md)</span>|Detects empty columns that contain only null values. Counts the number of rows that have non-null values. Raises a data quality issue when the count of non-null values is below min_count. Stores a separate data quality check result for each monthly partition.|*[ColumnNotNullsCountCheckSpec](../../../checks/column/nulls/not-nulls-count.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_not_nulls_percent`](../../../checks/column/nulls/not-nulls-percent.md)</span>|Detects incomplete columns that contain too few non-null values. Measures the percentage of rows that have non-null values. Raises a data quality issue when the percentage of non-null values is below min_percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnNotNullsPercentCheckSpec](../../../checks/column/nulls/not-nulls-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnUniquenessMonthlyPartitionedChecksSpec
Container of uniqueness data quality partitioned checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_distinct_count`](../../../checks/column/uniqueness/distinct-count.md)</span>|Verifies  that the number of distinct values stays within an accepted range. Stores a separate data quality check result for each monthly partition.|*[ColumnDistinctCountCheckSpec](../../../checks/column/uniqueness/distinct-count.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_distinct_percent`](../../../checks/column/uniqueness/distinct-percent.md)</span>|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores a separate data quality check result for each monthly partition.|*[ColumnDistinctPercentCheckSpec](../../../checks/column/uniqueness/distinct-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_duplicate_count`](../../../checks/column/uniqueness/duplicate-count.md)</span>|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|*[ColumnDuplicateCountCheckSpec](../../../checks/column/uniqueness/duplicate-count.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_duplicate_percent`](../../../checks/column/uniqueness/duplicate-percent.md)</span>|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each monthly partition.|*[ColumnDuplicatePercentCheckSpec](../../../checks/column/uniqueness/duplicate-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_distinct_count_change`](../../../checks/column/uniqueness/distinct-count-change.md)</span>|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|*[ColumnDistinctCountChangeCheckSpec](../../../checks/column/uniqueness/distinct-count-change.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_distinct_percent_change`](../../../checks/column/uniqueness/distinct-percent-change.md)</span>|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|*[ColumnDistinctPercentChangeCheckSpec](../../../checks/column/uniqueness/distinct-percent-change.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnAcceptedValuesMonthlyPartitionedChecksSpec
Container of accepted values data quality partitioned checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_text_found_in_set_percent`](../../../checks/column/accepted_values/text-found-in-set-percent.md)</span>|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnTextFoundInSetPercentCheckSpec](../../../checks/column/accepted_values/text-found-in-set-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_number_found_in_set_percent`](../../../checks/column/accepted_values/number-found-in-set-percent.md)</span>|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnNumberFoundInSetPercentCheckSpec](../../../checks/column/accepted_values/number-found-in-set-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_expected_text_values_in_use_count`](../../../checks/column/accepted_values/expected-text-values-in-use-count.md)</span>|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each monthly partition.|*[ColumnExpectedTextValuesInUseCountCheckSpec](../../../checks/column/accepted_values/expected-text-values-in-use-count.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_expected_texts_in_top_values_count`](../../../checks/column/accepted_values/expected-texts-in-top-values-count.md)</span>|Verifies that the top X most popular column values contain all values from a list of expected values. Stores a separate data quality check result for each monthly partition.|*[ColumnExpectedTextsInTopValuesCountCheckSpec](../../../checks/column/accepted_values/expected-texts-in-top-values-count.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_expected_numbers_in_use_count`](../../../checks/column/accepted_values/expected-numbers-in-use-count.md)</span>|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each monthly partition.|*[ColumnExpectedNumbersInUseCountCheckSpec](../../../checks/column/accepted_values/expected-numbers-in-use-count.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_valid_country_code_percent`](../../../checks/column/accepted_values/text-valid-country-code-percent.md)</span>|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextValidCountryCodePercentCheckSpec](../../../checks/column/accepted_values/text-valid-country-code-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_valid_currency_code_percent`](../../../checks/column/accepted_values/text-valid-currency-code-percent.md)</span>|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextValidCurrencyCodePercentCheckSpec](../../../checks/column/accepted_values/text-valid-currency-code-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnTextMonthlyPartitionedChecksSpec
Container of text data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_text_min_length`](../../../checks/column/text/text-min-length.md)</span>|This check finds the length of the shortest text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the shortest text is too short. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextMinLengthCheckSpec](../../../checks/column/text/text-min-length.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_max_length`](../../../checks/column/text/text-max-length.md)</span>|This check finds the length of the longest text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the texts are too long or not long enough. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextMaxLengthCheckSpec](../../../checks/column/text/text-max-length.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_mean_length`](../../../checks/column/text/text-mean-length.md)</span>|Verifies that the mean (average) length of texts in a column is within an accepted range. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextMeanLengthCheckSpec](../../../checks/column/text/text-mean-length.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_length_below_min_length`](../../../checks/column/text/text-length-below-min-length.md)</span>|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextLengthBelowMinLengthCheckSpec](../../../checks/column/text/text-length-below-min-length.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_length_below_min_length_percent`](../../../checks/column/text/text-length-below-min-length-percent.md)</span>|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextLengthBelowMinLengthPercentCheckSpec](../../../checks/column/text/text-length-below-min-length-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_length_above_max_length`](../../../checks/column/text/text-length-above-max-length.md)</span>|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextLengthAboveMaxLengthCheckSpec](../../../checks/column/text/text-length-above-max-length.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_length_above_max_length_percent`](../../../checks/column/text/text-length-above-max-length-percent.md)</span>|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextLengthAboveMaxLengthPercentCheckSpec](../../../checks/column/text/text-length-above-max-length-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_length_in_range_percent`](../../../checks/column/text/text-length-in-range-percent.md)</span>|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextLengthInRangePercentCheckSpec](../../../checks/column/text/text-length-in-range-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnWhitespaceMonthlyPartitionedChecksSpec
Container of whitespace values detection data quality partitioned checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_empty_text_found`](../../../checks/column/whitespace/empty-text-found.md)</span>|Detects empty texts (not null, zero-length texts). This check counts empty and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each monthly partition.|*[ColumnWhitespaceEmptyTextFoundCheckSpec](../../../checks/column/whitespace/empty-text-found.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_whitespace_text_found`](../../../checks/column/whitespace/whitespace-text-found.md)</span>|Detects texts that contain only spaces and other whitespace characters. It raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each monthly partition.|*[ColumnWhitespaceWhitespaceTextFoundCheckSpec](../../../checks/column/whitespace/whitespace-text-found.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_null_placeholder_text_found`](../../../checks/column/whitespace/null-placeholder-text-found.md)</span>|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*. It counts null placeholders and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each monthly partition.|*[ColumnWhitespaceNullPlaceholderTextFoundCheckSpec](../../../checks/column/whitespace/null-placeholder-text-found.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_empty_text_percent`](../../../checks/column/whitespace/empty-text-percent.md)</span>|Detects empty texts (not null, zero-length texts) and measures their percentage in the column. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnWhitespaceEmptyTextPercentCheckSpec](../../../checks/column/whitespace/empty-text-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_whitespace_text_percent`](../../../checks/column/whitespace/whitespace-text-percent.md)</span>|Detects texts that contain only spaces and other whitespace characters and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores a separate data quality check result for each monthly partition.|*[ColumnWhitespaceWhitespaceTextPercentCheckSpec](../../../checks/column/whitespace/whitespace-text-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_null_placeholder_text_percent`](../../../checks/column/whitespace/null-placeholder-text-percent.md)</span>|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*, and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores a separate data quality check result for each monthly partition.|*[ColumnWhitespaceNullPlaceholderTextPercentCheckSpec](../../../checks/column/whitespace/null-placeholder-text-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_surrounded_by_whitespace_found`](../../../checks/column/whitespace/text-surrounded-by-whitespace-found.md)</span>|Detects text values that are surrounded by whitespace characters on any side. This check counts whitespace-surrounded texts and raises a data quality issue when their count exceeds the *max_count* parameter value. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec](../../../checks/column/whitespace/text-surrounded-by-whitespace-found.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_surrounded_by_whitespace_percent`](../../../checks/column/whitespace/text-surrounded-by-whitespace-percent.md)</span>|This check detects text values that are surrounded by whitespace characters on any side and measures their percentage. This check raises a data quality issue when their percentage exceeds the *max_percent* parameter value. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec](../../../checks/column/whitespace/text-surrounded-by-whitespace-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnConversionsMonthlyPartitionedChecksSpec
Container of conversion test checks that are monitoring if text values are convertible to a target data type at a monthly partition level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_text_parsable_to_boolean_percent`](../../../checks/column/conversions/text-parsable-to-boolean-percent.md)</span>|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextParsableToBooleanPercentCheckSpec](../../../checks/column/conversions/text-parsable-to-boolean-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_parsable_to_integer_percent`](../../../checks/column/conversions/text-parsable-to-integer-percent.md)</span>|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextParsableToIntegerPercentCheckSpec](../../../checks/column/conversions/text-parsable-to-integer-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_parsable_to_float_percent`](../../../checks/column/conversions/text-parsable-to-float-percent.md)</span>|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextParsableToFloatPercentCheckSpec](../../../checks/column/conversions/text-parsable-to-float-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_parsable_to_date_percent`](../../../checks/column/conversions/text-parsable-to-date-percent.md)</span>|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|*[ColumnTextParsableToDatePercentCheckSpec](../../../checks/column/conversions/text-parsable-to-date-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnPatternsMonthlyPartitionedChecksSpec
Container of built-in preconfigured monthly partition checks on a column level that are checking for values matching patterns (regular expressions) in text columns.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_text_not_matching_regex_found`](../../../checks/column/patterns/text-not-matching-regex-found.md)</span>|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|*[ColumnTextNotMatchingRegexFoundCheckSpec](../../../checks/column/patterns/text-not-matching-regex-found.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_texts_matching_regex_percent`](../../../checks/column/patterns/texts-matching-regex-percent.md)</span>|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|*[ColumnTextsMatchingRegexPercentCheckSpec](../../../checks/column/patterns/texts-matching-regex-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_invalid_email_format_found`](../../../checks/column/patterns/invalid-email-format-found.md)</span>|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|*[ColumnInvalidEmailFormatFoundCheckSpec](../../../checks/column/patterns/invalid-email-format-found.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_not_matching_date_pattern_found`](../../../checks/column/patterns/text-not-matching-date-pattern-found.md)</span>|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|*[ColumnTextNotMatchingDatePatternFoundCheckSpec](../../../checks/column/patterns/text-not-matching-date-pattern-found.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_matching_date_pattern_percent`](../../../checks/column/patterns/text-matching-date-pattern-percent.md)</span>|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|*[ColumnTextMatchingDatePatternPercentCheckSpec](../../../checks/column/patterns/text-matching-date-pattern-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_matching_name_pattern_percent`](../../../checks/column/patterns/text-matching-name-pattern-percent.md)</span>|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|*[ColumnTextMatchingNamePatternPercentCheckSpec](../../../checks/column/patterns/text-matching-name-pattern-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_invalid_uuid_format_found`](../../../checks/column/patterns/invalid-uuid-format-found.md)</span>|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|*[ColumnInvalidUuidFormatFoundCheckSpec](../../../checks/column/patterns/invalid-uuid-format-found.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_valid_uuid_format_percent`](../../../checks/column/patterns/valid-uuid-format-percent.md)</span>|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|*[ColumnValidUuidFormatPercentCheckSpec](../../../checks/column/patterns/valid-uuid-format-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_invalid_ip4_address_format_found`](../../../checks/column/patterns/invalid-ip4-address-format-found.md)</span>|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|*[ColumnInvalidIp4AddressFormatFoundCheckSpec](../../../checks/column/patterns/invalid-ip4-address-format-found.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_invalid_ip6_address_format_found`](../../../checks/column/patterns/invalid-ip6-address-format-found.md)</span>|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|*[ColumnInvalidIp6AddressFormatFoundCheckSpec](../../../checks/column/patterns/invalid-ip6-address-format-found.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnPiiMonthlyPartitionedChecksSpec
Container of PII data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_contains_usa_phone_percent`](../../../checks/column/pii/contains-usa-phone-percent.md)</span>|Detects USA phone numbers in text columns. Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnPiiContainsUsaPhonePercentCheckSpec](../../../checks/column/pii/contains-usa-phone-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_contains_email_percent`](../../../checks/column/pii/contains-email-percent.md)</span>|Detects emails in text columns. Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnPiiContainsEmailPercentCheckSpec](../../../checks/column/pii/contains-email-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_contains_usa_zipcode_percent`](../../../checks/column/pii/contains-usa-zipcode-percent.md)</span>|Detects USA zip codes in text columns. Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnPiiContainsUsaZipcodePercentCheckSpec](../../../checks/column/pii/contains-usa-zipcode-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_contains_ip4_percent`](../../../checks/column/pii/contains-ip4-percent.md)</span>|Detects IP4 addresses in text columns. Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnPiiContainsIp4PercentCheckSpec](../../../checks/column/pii/contains-ip4-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_contains_ip6_percent`](../../../checks/column/pii/contains-ip6-percent.md)</span>|Detects IP6 addresses in text columns. Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnPiiContainsIp6PercentCheckSpec](../../../checks/column/pii/contains-ip6-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnNumericMonthlyPartitionedChecksSpec
Container of numeric data quality partitioned checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_number_below_min_value`](../../../checks/column/numeric/number-below-min-value.md)</span>|The check counts the number of values in the column that are below the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.|*[ColumnNumberBelowMinValueCheckSpec](../../../checks/column/numeric/number-below-min-value.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_number_above_max_value`](../../../checks/column/numeric/number-above-max-value.md)</span>|The check counts the number of values in the column that are above the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.|*[ColumnNumberAboveMaxValueCheckSpec](../../../checks/column/numeric/number-above-max-value.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_negative_values`](../../../checks/column/numeric/negative-values.md)</span>|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|*[ColumnNegativeCountCheckSpec](../../../checks/column/numeric/negative-values.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_negative_values_percent`](../../../checks/column/numeric/negative-values-percent.md)</span>|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnNegativePercentCheckSpec](../../../checks/column/numeric/negative-values-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_number_below_min_value_percent`](../../../checks/column/numeric/number-below-min-value-percent.md)</span>|The check counts the percentage of values in the column that are below the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.|*[ColumnNumberBelowMinValuePercentCheckSpec](../../../checks/column/numeric/number-below-min-value-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_number_above_max_value_percent`](../../../checks/column/numeric/number-above-max-value-percent.md)</span>|The check counts the percentage of values in the column that are above the value defined by the user as a parameter. Stores a separate data quality check result for each monthly partition.|*[ColumnNumberAboveMaxValuePercentCheckSpec](../../../checks/column/numeric/number-above-max-value-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_number_in_range_percent`](../../../checks/column/numeric/number-in-range-percent.md)</span>|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnNumberInRangePercentCheckSpec](../../../checks/column/numeric/number-in-range-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_integer_in_range_percent`](../../../checks/column/numeric/integer-in-range-percent.md)</span>|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnIntegerInRangePercentCheckSpec](../../../checks/column/numeric/integer-in-range-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_min_in_range`](../../../checks/column/numeric/min-in-range.md)</span>|Verifies that the minimum value in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnMinInRangeCheckSpec](../../../checks/column/numeric/min-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_max_in_range`](../../../checks/column/numeric/max-in-range.md)</span>|Verifies that the maximum value in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnMaxInRangeCheckSpec](../../../checks/column/numeric/max-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_sum_in_range`](../../../checks/column/numeric/sum-in-range.md)</span>|Verifies that the sum of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnSumInRangeCheckSpec](../../../checks/column/numeric/sum-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_mean_in_range`](../../../checks/column/numeric/mean-in-range.md)</span>|Verifies that the average (mean) of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnMeanInRangeCheckSpec](../../../checks/column/numeric/mean-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_median_in_range`](../../../checks/column/numeric/median-in-range.md)</span>|Verifies that the median of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnMedianInRangeCheckSpec](../../../checks/column/numeric/median-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_percentile_in_range`](../../../checks/column/numeric/percentile-in-range.md)</span>|Verifies that the percentile of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnPercentileInRangeCheckSpec](../../../checks/column/numeric/percentile-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_percentile_10_in_range`](../../../checks/column/numeric/percentile-10-in-range.md)</span>|Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnPercentile10InRangeCheckSpec](../../../checks/column/numeric/percentile-10-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_percentile_25_in_range`](../../../checks/column/numeric/percentile-25-in-range.md)</span>|Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnPercentile25InRangeCheckSpec](../../../checks/column/numeric/percentile-25-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_percentile_75_in_range`](../../../checks/column/numeric/percentile-75-in-range.md)</span>|Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnPercentile75InRangeCheckSpec](../../../checks/column/numeric/percentile-75-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_percentile_90_in_range`](../../../checks/column/numeric/percentile-90-in-range.md)</span>|Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnPercentile90InRangeCheckSpec](../../../checks/column/numeric/percentile-90-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_sample_stddev_in_range`](../../../checks/column/numeric/sample-stddev-in-range.md)</span>|Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnSampleStddevInRangeCheckSpec](../../../checks/column/numeric/sample-stddev-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_population_stddev_in_range`](../../../checks/column/numeric/population-stddev-in-range.md)</span>|Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnPopulationStddevInRangeCheckSpec](../../../checks/column/numeric/population-stddev-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_sample_variance_in_range`](../../../checks/column/numeric/sample-variance-in-range.md)</span>|Verifies that the sample variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnSampleVarianceInRangeCheckSpec](../../../checks/column/numeric/sample-variance-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_population_variance_in_range`](../../../checks/column/numeric/population-variance-in-range.md)</span>|Verifies that the population variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnPopulationVarianceInRangeCheckSpec](../../../checks/column/numeric/population-variance-in-range.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_invalid_latitude`](../../../checks/column/numeric/invalid-latitude.md)</span>|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|*[ColumnInvalidLatitudeCountCheckSpec](../../../checks/column/numeric/invalid-latitude.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_valid_latitude_percent`](../../../checks/column/numeric/valid-latitude-percent.md)</span>|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnValidLatitudePercentCheckSpec](../../../checks/column/numeric/valid-latitude-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_invalid_longitude`](../../../checks/column/numeric/invalid-longitude.md)</span>|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|*[ColumnInvalidLongitudeCountCheckSpec](../../../checks/column/numeric/invalid-longitude.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_valid_longitude_percent`](../../../checks/column/numeric/valid-longitude-percent.md)</span>|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnValidLongitudePercentCheckSpec](../../../checks/column/numeric/valid-longitude-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_non_negative_values`](../../../checks/column/numeric/non-negative-values.md)</span>|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each monthly partition.|*[ColumnNonNegativeCountCheckSpec](../../../checks/column/numeric/non-negative-values.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_non_negative_values_percent`](../../../checks/column/numeric/non-negative-values-percent.md)</span>|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each monthly partition.|*[ColumnNonNegativePercentCheckSpec](../../../checks/column/numeric/non-negative-values-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnDatetimeMonthlyPartitionedChecksSpec
Container of date-time data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_date_values_in_future_percent`](../../../checks/column/datetime/date-values-in-future-percent.md)</span>|Detects dates in the future in date, datetime and timestamp columns. Measures a percentage of dates in the future. Raises a data quality issue when too many future dates are found. Stores a separate data quality check result for each monthly partition.|*[ColumnDateValuesInFuturePercentCheckSpec](../../../checks/column/datetime/date-values-in-future-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_date_in_range_percent`](../../../checks/column/datetime/date-in-range-percent.md)</span>|Verifies that the dates in date, datetime, or timestamp columns are within a reasonable range of dates. The default configuration detects fake dates such as 1900-01-01 and 2099-12-31. Measures the percentage of valid dates and raises a data quality issue when too many dates are found. Stores a separate data quality check result for each monthly partition.|*[ColumnDateInRangePercentCheckSpec](../../../checks/column/datetime/date-in-range-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_text_match_date_format_percent`](../../../checks/column/datetime/text-match-date-format-percent.md)</span>|Verifies that the values in text columns match one of the predefined date formats, such as an ISO 8601 date. Measures the percentage of valid date strings and raises a data quality issue when too many invalid date strings are found. Stores a separate data quality check result for each monthly partition.|*[ColumnTextMatchDateFormatPercentCheckSpec](../../../checks/column/datetime/text-match-date-format-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnBoolMonthlyPartitionedChecksSpec
Container of boolean data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_true_percent`](../../../checks/column/bool/true-percent.md)</span>|Measures the percentage of **true** values in a boolean column and verifies that it is within the accepted range. Stores a separate data quality check result for each monthly partition.|*[ColumnTruePercentCheckSpec](../../../checks/column/bool/true-percent.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_false_percent`](../../../checks/column/bool/false-percent.md)</span>|Measures the percentage of **false** values in a boolean column and verifies that it is within the accepted range. Stores a separate data quality check result for each monthly partition.|*[ColumnFalsePercentCheckSpec](../../../checks/column/bool/false-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnIntegrityMonthlyPartitionedChecksSpec
Container of integrity data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_lookup_key_not_found`](../../../checks/column/integrity/lookup-key-not-found.md)</span>|Detects invalid values that are not present in a dictionary table using an outer join query. Counts the number of invalid keys. Stores a separate data quality check result for each monthly partition.|*[ColumnIntegrityLookupKeyNotFoundCountCheckSpec](../../../checks/column/integrity/lookup-key-not-found.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_lookup_key_found_percent`](../../../checks/column/integrity/lookup-key-found-percent.md)</span>|Measures the percentage of valid values that are present in a dictionary table. Joins this table to a dictionary table using an outer join. Stores a separate data quality check result for each monthly partition.|*[ColumnIntegrityForeignKeyMatchPercentCheckSpec](../../../checks/column/integrity/lookup-key-found-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnCustomSqlMonthlyPartitionedChecksSpec
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_sql_condition_failed_on_column`](../../../checks/column/custom_sql/sql-condition-failed-on-column.md)</span>|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.|*[ColumnSqlConditionFailedCheckSpec](../../../checks/column/custom_sql/sql-condition-failed-on-column.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_sql_condition_passed_percent_on_column`](../../../checks/column/custom_sql/sql-condition-passed-percent-on-column.md)</span>|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each monthly partition.|*[ColumnSqlConditionPassedPercentCheckSpec](../../../checks/column/custom_sql/sql-condition-passed-percent-on-column.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_sql_aggregate_expression_on_column`](../../../checks/column/custom_sql/sql-aggregate-expression-on-column.md)</span>|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each monthly partition.|*[ColumnSqlAggregateExpressionCheckSpec](../../../checks/column/custom_sql/sql-aggregate-expression-on-column.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_import_custom_result_on_column`](../../../checks/column/custom_sql/import-custom-result-on-column.md)</span>|Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.|*[ColumnSqlImportCustomResultCheckSpec](../../../checks/column/custom_sql/import-custom-result-on-column.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnDatatypeMonthlyPartitionedChecksSpec
Container of datatype data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_detected_datatype_in_text`](../../../checks/column/datatype/detected-datatype-in-text.md)</span>|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores a separate data quality check result for each monthly partition.|*[ColumnDetectedDatatypeInTextCheckSpec](../../../checks/column/datatype/detected-datatype-in-text.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_detected_datatype_in_text_changed`](../../../checks/column/datatype/detected-datatype-in-text-changed.md)</span>|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types. Stores a separate data quality check result for each monthly partition.|*[ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec](../../../checks/column/datatype/detected-datatype-in-text-changed.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnComparisonMonthlyPartitionedChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`self`</span>||*Dict[string, [ColumnComparisonMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#columncomparisonmonthlypartitionedchecksspec)]*| | | |









___


## ColumnComparisonMonthlyPartitionedChecksSpec
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 This is the configuration for daily partitioned checks that are counted in KPIs.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`monthly_partition_sum_match`](../../../checks/column/comparisons/sum-match.md)</span>|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|*[ColumnComparisonSumMatchCheckSpec](../../../checks/column/comparisons/sum-match.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_min_match`](../../../checks/column/comparisons/min-match.md)</span>|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|*[ColumnComparisonMinMatchCheckSpec](../../../checks/column/comparisons/min-match.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_max_match`](../../../checks/column/comparisons/max-match.md)</span>|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|*[ColumnComparisonMaxMatchCheckSpec](../../../checks/column/comparisons/max-match.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_mean_match`](../../../checks/column/comparisons/mean-match.md)</span>|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|*[ColumnComparisonMeanMatchCheckSpec](../../../checks/column/comparisons/mean-match.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_not_null_count_match`](../../../checks/column/comparisons/not-null-count-match.md)</span>|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|*[ColumnComparisonNotNullCountMatchCheckSpec](../../../checks/column/comparisons/not-null-count-match.md)*| | | |
|<span class="no-wrap-code ">[`monthly_partition_null_count_match`](../../../checks/column/comparisons/null-count-match.md)</span>|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|*[ColumnComparisonNullCountMatchCheckSpec](../../../checks/column/comparisons/null-count-match.md)*| | | |
|<span class="no-wrap-code ">`reference_column`</span>|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|*string*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


