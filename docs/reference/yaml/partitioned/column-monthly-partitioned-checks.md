# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## ColumnUniquenessMonthlyPartitionedChecksSpec
Container of uniqueness data quality partitioned checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_distinct_count](../../../checks/column/uniqueness/distinct-count.md)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDistinctCountCheckSpec](../../../checks/column/uniqueness/distinct-count.md)| | | |
|[monthly_partition_distinct_percent](../../../checks/column/uniqueness/distinct-percent.md)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDistinctPercentCheckSpec](../../../checks/column/uniqueness/distinct-percent.md)| | | |
|[monthly_partition_duplicate_count](../../../checks/column/uniqueness/duplicate-count.md)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDuplicateCountCheckSpec](../../../checks/column/uniqueness/duplicate-count.md)| | | |
|[monthly_partition_duplicate_percent](../../../checks/column/uniqueness/duplicate-percent.md)|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDuplicatePercentCheckSpec](../../../checks/column/uniqueness/duplicate-percent.md)| | | |
|[monthly_partition_distinct_count_change](../../../checks/column/uniqueness/distinct-count-change.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|[ColumnDistinctCountChangeCheckSpec](../../../checks/column/uniqueness/distinct-count-change.md)| | | |
|[monthly_partition_distinct_percent_change](../../../checks/column/uniqueness/distinct-percent-change.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|[ColumnDistinctPercentChangeCheckSpec](../../../checks/column/uniqueness/distinct-percent-change.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnBlanksMonthlyPartitionedChecksSpec
Container of blank values detection data quality partitioned checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_empty_text_found](../../../checks/column/blanks/empty-text-found.md)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnBlanksEmptyTextFoundCheckSpec](../../../checks/column/blanks/empty-text-found.md)| | | |
|[monthly_partition_whitespace_text_found](../../../checks/column/blanks/whitespace-text-found.md)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnBlanksWhitespaceTextFoundCheckSpec](../../../checks/column/blanks/whitespace-text-found.md)| | | |
|[monthly_partition_null_placeholder_text_found](../../../checks/column/blanks/null-placeholder-text-found.md)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnBlanksNullPlaceholderTextFoundCheckSpec](../../../checks/column/blanks/null-placeholder-text-found.md)| | | |
|[monthly_partition_empty_text_percent](../../../checks/column/blanks/empty-text-percent.md)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnBlanksEmptyTextPercentCheckSpec](../../../checks/column/blanks/empty-text-percent.md)| | | |
|[monthly_partition_whitespace_text_percent](../../../checks/column/blanks/whitespace-text-percent.md)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnBlanksWhitespaceTextPercentCheckSpec](../../../checks/column/blanks/whitespace-text-percent.md)| | | |
|[monthly_partition_null_placeholder_text_percent](../../../checks/column/blanks/null-placeholder-text-percent.md)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnBlanksNullPlaceholderTextPercentCheckSpec](../../../checks/column/blanks/null-placeholder-text-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnAcceptedValuesMonthlyPartitionedChecksSpec
Container of accepted values data quality partitioned checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_text_found_in_set_percent](../../../checks/column/accepted_values/text-found-in-set-percent.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnTextFoundInSetPercentCheckSpec](../../../checks/column/accepted_values/text-found-in-set-percent.md)| | | |
|[monthly_partition_number_found_in_set_percent](../../../checks/column/accepted_values/number-found-in-set-percent.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNumberFoundInSetPercentCheckSpec](../../../checks/column/accepted_values/number-found-in-set-percent.md)| | | |
|[monthly_partition_expected_text_values_in_use_count](../../../checks/column/accepted_values/expected-text-values-in-use-count.md)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnExpectedTextValuesInUseCountCheckSpec](../../../checks/column/accepted_values/expected-text-values-in-use-count.md)| | | |
|[monthly_partition_expected_texts_in_top_values_count](../../../checks/column/accepted_values/expected-texts-in-top-values-count.md)|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnExpectedTextsInTopValuesCountCheckSpec](../../../checks/column/accepted_values/expected-texts-in-top-values-count.md)| | | |
|[monthly_partition_expected_numbers_in_use_count](../../../checks/column/accepted_values/expected-numbers-in-use-count.md)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnExpectedNumbersInUseCountCheckSpec](../../../checks/column/accepted_values/expected-numbers-in-use-count.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnPiiMonthlyPartitionedChecksSpec
Container of PII data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_contains_usa_phone_percent](../../../checks/column/pii/contains-usa-phone-percent.md)|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsUsaPhonePercentCheckSpec](../../../checks/column/pii/contains-usa-phone-percent.md)| | | |
|[monthly_partition_contains_usa_zipcode_percent](../../../checks/column/pii/contains-usa-zipcode-percent.md)|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](../../../checks/column/pii/contains-usa-zipcode-percent.md)| | | |
|[monthly_partition_contains_email_percent](../../../checks/column/pii/contains-email-percent.md)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsEmailPercentCheckSpec](../../../checks/column/pii/contains-email-percent.md)| | | |
|[monthly_partition_contains_ip4_percent](../../../checks/column/pii/contains-ip4-percent.md)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsIp4PercentCheckSpec](../../../checks/column/pii/contains-ip4-percent.md)| | | |
|[monthly_partition_contains_ip6_percent](../../../checks/column/pii/contains-ip6-percent.md)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsIp6PercentCheckSpec](../../../checks/column/pii/contains-ip6-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnBoolMonthlyPartitionedChecksSpec
Container of boolean data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_true_percent](../../../checks/column/bool/true-percent.md)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnTruePercentCheckSpec](../../../checks/column/bool/true-percent.md)| | | |
|[monthly_partition_false_percent](../../../checks/column/bool/false-percent.md)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnFalsePercentCheckSpec](../../../checks/column/bool/false-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnMonthlyPartitionedCheckCategoriesSpec
Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](./column-monthly-partitioned-checks.md#ColumnNullsMonthlyPartitionedChecksSpec)|Monthly partitioned checks of nulls in the column|[ColumnNullsMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnNullsMonthlyPartitionedChecksSpec)| | | |
|[uniqueness](./column-monthly-partitioned-checks.md#ColumnUniquenessMonthlyPartitionedChecksSpec)|Monthly partitioned checks of uniqueness in the column|[ColumnUniquenessMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnUniquenessMonthlyPartitionedChecksSpec)| | | |
|[accepted_values](./column-monthly-partitioned-checks.md#ColumnAcceptedValuesMonthlyPartitionedChecksSpec)|Configuration of accepted values checks on a column level|[ColumnAcceptedValuesMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnAcceptedValuesMonthlyPartitionedChecksSpec)| | | |
|[text](./column-monthly-partitioned-checks.md#ColumnTextMonthlyPartitionedChecksSpec)|Monthly partitioned checks of text values in the column|[ColumnTextMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnTextMonthlyPartitionedChecksSpec)| | | |
|[blanks](./column-monthly-partitioned-checks.md#ColumnBlanksMonthlyPartitionedChecksSpec)|Configuration of column level checks that detect blank and whitespace values|[ColumnBlanksMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnBlanksMonthlyPartitionedChecksSpec)| | | |
|[patterns](./column-monthly-partitioned-checks.md#ColumnPatternsMonthlyPartitionedChecksSpec)|Monthly partitioned pattern match checks on a column level|[ColumnPatternsMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnPatternsMonthlyPartitionedChecksSpec)| | | |
|[pii](./column-monthly-partitioned-checks.md#ColumnPiiMonthlyPartitionedChecksSpec)|Monthly partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnPiiMonthlyPartitionedChecksSpec)| | | |
|[numeric](./column-monthly-partitioned-checks.md#ColumnNumericMonthlyPartitionedChecksSpec)|Monthly partitioned checks of numeric values in the column|[ColumnNumericMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnNumericMonthlyPartitionedChecksSpec)| | | |
|[anomaly](./column-monthly-partitioned-checks.md#ColumnAnomalyMonthlyPartitionedChecksSpec)|Monthly partitioned checks for anomalies in numeric columns|[ColumnAnomalyMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnAnomalyMonthlyPartitionedChecksSpec)| | | |
|[datetime](./column-monthly-partitioned-checks.md#ColumnDatetimeMonthlyPartitionedChecksSpec)|Monthly partitioned checks of datetime in the column|[ColumnDatetimeMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnDatetimeMonthlyPartitionedChecksSpec)| | | |
|[bool](./column-monthly-partitioned-checks.md#ColumnBoolMonthlyPartitionedChecksSpec)|Monthly partitioned checks for booleans in the column|[ColumnBoolMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnBoolMonthlyPartitionedChecksSpec)| | | |
|[integrity](./column-monthly-partitioned-checks.md#ColumnIntegrityMonthlyPartitionedChecksSpec)|Monthly partitioned checks for integrity in the column|[ColumnIntegrityMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnIntegrityMonthlyPartitionedChecksSpec)| | | |
|[custom_sql](./column-monthly-partitioned-checks.md#ColumnCustomSqlMonthlyPartitionedChecksSpec)|Monthly partitioned checks using custom SQL expressions evaluated on the column|[ColumnCustomSqlMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnCustomSqlMonthlyPartitionedChecksSpec)| | | |
|[datatype](./column-monthly-partitioned-checks.md#ColumnDatatypeMonthlyPartitionedChecksSpec)|Monthly partitioned checks for datatype in the column|[ColumnDatatypeMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnDatatypeMonthlyPartitionedChecksSpec)| | | |
|[comparisons](./column-monthly-partitioned-checks.md#ColumnComparisonMonthlyPartitionedChecksSpecMap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonMonthlyPartitionedChecksSpecMap](./column-monthly-partitioned-checks.md#ColumnComparisonMonthlyPartitionedChecksSpecMap)| | | |
|[custom](../profiling/table-profiling-checks.md#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks.md#CustomCheckSpecMap)| | | |









___


## ColumnTextMonthlyPartitionedChecksSpec
Container of text data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_text_max_length](../../../checks/column/text/text-max-length.md)|Verifies that the length of a text in a column does not exceed the maximum accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextMaxLengthCheckSpec](../../../checks/column/text/text-max-length.md)| | | |
|[monthly_partition_text_min_length](../../../checks/column/text/text-min-length.md)|Verifies that the length of a text in a column does not fall below the minimum accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextMinLengthCheckSpec](../../../checks/column/text/text-min-length.md)| | | |
|[monthly_partition_text_mean_length](../../../checks/column/text/text-mean-length.md)|Verifies that the length of a text in a column does not exceed the mean accepted length. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextMeanLengthCheckSpec](../../../checks/column/text/text-mean-length.md)| | | |
|[monthly_partition_text_length_below_min_length](../../../checks/column/text/text-length-below-min-length.md)|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextLengthBelowMinLengthCheckSpec](../../../checks/column/text/text-length-below-min-length.md)| | | |
|[monthly_partition_text_length_below_min_length_percent](../../../checks/column/text/text-length-below-min-length-percent.md)|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextLengthBelowMinLengthPercentCheckSpec](../../../checks/column/text/text-length-below-min-length-percent.md)| | | |
|[monthly_partition_text_length_above_max_length](../../../checks/column/text/text-length-above-max-length.md)|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextLengthAboveMaxLengthCheckSpec](../../../checks/column/text/text-length-above-max-length.md)| | | |
|[monthly_partition_text_length_above_max_length_percent](../../../checks/column/text/text-length-above-max-length-percent.md)|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextLengthAboveMaxLengthPercentCheckSpec](../../../checks/column/text/text-length-above-max-length-percent.md)| | | |
|[monthly_partition_text_length_in_range_percent](../../../checks/column/text/text-length-in-range-percent.md)|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextLengthInRangePercentCheckSpec](../../../checks/column/text/text-length-in-range-percent.md)| | | |
|[monthly_partition_text_parsable_to_boolean_percent](../../../checks/column/text/text-parsable-to-boolean-percent.md)|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextParsableToBooleanPercentCheckSpec](../../../checks/column/text/text-parsable-to-boolean-percent.md)| | | |
|[monthly_partition_text_parsable_to_integer_percent](../../../checks/column/text/text-parsable-to-integer-percent.md)|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextParsableToIntegerPercentCheckSpec](../../../checks/column/text/text-parsable-to-integer-percent.md)| | | |
|[monthly_partition_text_parsable_to_float_percent](../../../checks/column/text/text-parsable-to-float-percent.md)|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextParsableToFloatPercentCheckSpec](../../../checks/column/text/text-parsable-to-float-percent.md)| | | |
|[monthly_partition_text_parsable_to_date_percent](../../../checks/column/text/text-parsable-to-date-percent.md)|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextParsableToDatePercentCheckSpec](../../../checks/column/text/text-parsable-to-date-percent.md)| | | |
|[monthly_partition_text_surrounded_by_whitespace](../../../checks/column/text/text-surrounded-by-whitespace.md)|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextSurroundedByWhitespaceCheckSpec](../../../checks/column/text/text-surrounded-by-whitespace.md)| | | |
|[monthly_partition_text_surrounded_by_whitespace_percent](../../../checks/column/text/text-surrounded-by-whitespace-percent.md)|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextSurroundedByWhitespacePercentCheckSpec](../../../checks/column/text/text-surrounded-by-whitespace-percent.md)| | | |
|[monthly_partition_text_valid_country_code_percent](../../../checks/column/text/text-valid-country-code-percent.md)|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextValidCountryCodePercentCheckSpec](../../../checks/column/text/text-valid-country-code-percent.md)| | | |
|[monthly_partition_text_valid_currency_code_percent](../../../checks/column/text/text-valid-currency-code-percent.md)|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every monthly partition and creates a separate data quality check result with the time period value that identifies the monthly partition.|[ColumnTextValidCurrencyCodePercentCheckSpec](../../../checks/column/text/text-valid-currency-code-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnDatatypeMonthlyPartitionedChecksSpec
Container of datatype data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_detected_datatype_in_text](../../../checks/column/datatype/detected-datatype-in-text.md)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDetectedDatatypeInTextCheckSpec](../../../checks/column/datatype/detected-datatype-in-text.md)| | | |
|[monthly_partition_detected_datatype_in_text_changed](../../../checks/column/datatype/detected-datatype-in-text-changed.md)|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDatatypeStringDatatypeChangedCheckSpec](../../../checks/column/datatype/detected-datatype-in-text-changed.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnDatetimeMonthlyPartitionedChecksSpec
Container of date-time data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_date_match_format_percent](../../../checks/column/datetime/date-match-format-percent.md)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDatetimeDateMatchFormatPercentCheckSpec](../../../checks/column/datetime/date-match-format-percent.md)| | | |
|[monthly_partition_date_values_in_future_percent](../../../checks/column/datetime/date-values-in-future-percent.md)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDateValuesInFuturePercentCheckSpec](../../../checks/column/datetime/date-values-in-future-percent.md)| | | |
|[monthly_partition_datetime_value_in_range_date_percent](../../../checks/column/datetime/datetime-value-in-range-date-percent.md)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](../../../checks/column/datetime/datetime-value-in-range-date-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnComparisonMonthlyPartitionedChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [ColumnComparisonMonthlyPartitionedChecksSpec](./column-monthly-partitioned-checks.md#ColumnComparisonMonthlyPartitionedChecksSpec)]| | | |









___


## ColumnNumericMonthlyPartitionedChecksSpec
Container of numeric data quality partitioned checks on a column level that are checking at a monthly level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_number_below_min_value](../../../checks/column/numeric/number-below-min-value.md)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNumberBelowMinValueCheckSpec](../../../checks/column/numeric/number-below-min-value.md)| | | |
|[monthly_partition_number_above_max_value](../../../checks/column/numeric/number-above-max-value.md)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNumberAboveMaxValueCheckSpec](../../../checks/column/numeric/number-above-max-value.md)| | | |
|[monthly_partition_negative_values](../../../checks/column/numeric/negative-values.md)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNegativeCountCheckSpec](../../../checks/column/numeric/negative-values.md)| | | |
|[monthly_partition_negative_values_percent](../../../checks/column/numeric/negative-values-percent.md)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNegativePercentCheckSpec](../../../checks/column/numeric/negative-values-percent.md)| | | |
|[monthly_partition_number_below_min_value_percent](../../../checks/column/numeric/number-below-min-value-percent.md)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNumberBelowMinValuePercentCheckSpec](../../../checks/column/numeric/number-below-min-value-percent.md)| | | |
|[monthly_partition_number_above_max_value_percent](../../../checks/column/numeric/number-above-max-value-percent.md)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNumberAboveMaxValuePercentCheckSpec](../../../checks/column/numeric/number-above-max-value-percent.md)| | | |
|[monthly_partition_number_in_range_percent](../../../checks/column/numeric/number-in-range-percent.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNumberInRangePercentCheckSpec](../../../checks/column/numeric/number-in-range-percent.md)| | | |
|[monthly_partition_integer_in_range_percent](../../../checks/column/numeric/integer-in-range-percent.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnIntegerInRangePercentCheckSpec](../../../checks/column/numeric/integer-in-range-percent.md)| | | |
|[monthly_partition_min_in_range](../../../checks/column/numeric/min-in-range.md)|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMinInRangeCheckSpec](../../../checks/column/numeric/min-in-range.md)| | | |
|[monthly_partition_max_in_range](../../../checks/column/numeric/max-in-range.md)|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMaxInRangeCheckSpec](../../../checks/column/numeric/max-in-range.md)| | | |
|[monthly_partition_sum_in_range](../../../checks/column/numeric/sum-in-range.md)|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSumInRangeCheckSpec](../../../checks/column/numeric/sum-in-range.md)| | | |
|[monthly_partition_mean_in_range](../../../checks/column/numeric/mean-in-range.md)|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMeanInRangeCheckSpec](../../../checks/column/numeric/mean-in-range.md)| | | |
|[monthly_partition_median_in_range](../../../checks/column/numeric/median-in-range.md)|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMedianInRangeCheckSpec](../../../checks/column/numeric/median-in-range.md)| | | |
|[monthly_partition_percentile_in_range](../../../checks/column/numeric/percentile-in-range.md)|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentileInRangeCheckSpec](../../../checks/column/numeric/percentile-in-range.md)| | | |
|[monthly_partition_percentile_10_in_range](../../../checks/column/numeric/percentile-10-in-range.md)|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile10InRangeCheckSpec](../../../checks/column/numeric/percentile-10-in-range.md)| | | |
|[monthly_partition_percentile_25_in_range](../../../checks/column/numeric/percentile-25-in-range.md)|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile25InRangeCheckSpec](../../../checks/column/numeric/percentile-25-in-range.md)| | | |
|[monthly_partition_percentile_75_in_range](../../../checks/column/numeric/percentile-75-in-range.md)|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile75InRangeCheckSpec](../../../checks/column/numeric/percentile-75-in-range.md)| | | |
|[monthly_partition_percentile_90_in_range](../../../checks/column/numeric/percentile-90-in-range.md)|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile90InRangeCheckSpec](../../../checks/column/numeric/percentile-90-in-range.md)| | | |
|[monthly_partition_sample_stddev_in_range](../../../checks/column/numeric/sample-stddev-in-range.md)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSampleStddevInRangeCheckSpec](../../../checks/column/numeric/sample-stddev-in-range.md)| | | |
|[monthly_partition_population_stddev_in_range](../../../checks/column/numeric/population-stddev-in-range.md)|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPopulationStddevInRangeCheckSpec](../../../checks/column/numeric/population-stddev-in-range.md)| | | |
|[monthly_partition_sample_variance_in_range](../../../checks/column/numeric/sample-variance-in-range.md)|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSampleVarianceInRangeCheckSpec](../../../checks/column/numeric/sample-variance-in-range.md)| | | |
|[monthly_partition_population_variance_in_range](../../../checks/column/numeric/population-variance-in-range.md)|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPopulationVarianceInRangeCheckSpec](../../../checks/column/numeric/population-variance-in-range.md)| | | |
|[monthly_partition_invalid_latitude](../../../checks/column/numeric/invalid-latitude.md)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnInvalidLatitudeCountCheckSpec](../../../checks/column/numeric/invalid-latitude.md)| | | |
|[monthly_partition_valid_latitude_percent](../../../checks/column/numeric/valid-latitude-percent.md)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValidLatitudePercentCheckSpec](../../../checks/column/numeric/valid-latitude-percent.md)| | | |
|[monthly_partition_invalid_longitude](../../../checks/column/numeric/invalid-longitude.md)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnInvalidLongitudeCountCheckSpec](../../../checks/column/numeric/invalid-longitude.md)| | | |
|[monthly_partition_valid_longitude_percent](../../../checks/column/numeric/valid-longitude-percent.md)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValidLongitudePercentCheckSpec](../../../checks/column/numeric/valid-longitude-percent.md)| | | |
|[monthly_partition_non_negative_values](../../../checks/column/numeric/non-negative-values.md)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNonNegativeCountCheckSpec](../../../checks/column/numeric/non-negative-values.md)| | | |
|[monthly_partition_non_negative_values_percent](../../../checks/column/numeric/non-negative-values-percent.md)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNonNegativePercentCheckSpec](../../../checks/column/numeric/non-negative-values-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnAnomalyMonthlyPartitionedChecksSpec
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_mean_change](../../../checks/column/anomaly/mean-change.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout.|[ColumnMeanChangeCheckSpec](../../../checks/column/anomaly/mean-change.md)| | | |
|[monthly_partition_median_change](../../../checks/column/anomaly/median-change.md)|Verifies that the median in a column changed in a fixed rate since the last readout.|[ColumnMedianChangeCheckSpec](../../../checks/column/anomaly/median-change.md)| | | |
|[monthly_partition_sum_change](../../../checks/column/anomaly/sum-change.md)|Verifies that the sum in a column changed in a fixed rate since the last readout.|[ColumnSumChangeCheckSpec](../../../checks/column/anomaly/sum-change.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnComparisonMonthlyPartitionedChecksSpec
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 This is the configuration for daily partitioned checks that are counted in KPIs.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_sum_match](../../../checks/column/comparisons/sum-match.md)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonSumMatchCheckSpec](../../../checks/column/comparisons/sum-match.md)| | | |
|[monthly_partition_min_match](../../../checks/column/comparisons/min-match.md)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMinMatchCheckSpec](../../../checks/column/comparisons/min-match.md)| | | |
|[monthly_partition_max_match](../../../checks/column/comparisons/max-match.md)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMaxMatchCheckSpec](../../../checks/column/comparisons/max-match.md)| | | |
|[monthly_partition_mean_match](../../../checks/column/comparisons/mean-match.md)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMeanMatchCheckSpec](../../../checks/column/comparisons/mean-match.md)| | | |
|[monthly_partition_not_null_count_match](../../../checks/column/comparisons/not-null-count-match.md)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonNotNullCountMatchCheckSpec](../../../checks/column/comparisons/not-null-count-match.md)| | | |
|[monthly_partition_null_count_match](../../../checks/column/comparisons/null-count-match.md)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonNullCountMatchCheckSpec](../../../checks/column/comparisons/null-count-match.md)| | | |
|reference_column|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|string| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnNullsMonthlyPartitionedChecksSpec
Container of nulls data quality partitioned checks on a column level that are checking monthly partitions or rows for each day of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_nulls_count](../../../checks/column/nulls/nulls-count.md)|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNullsCountCheckSpec](../../../checks/column/nulls/nulls-count.md)| | | |
|[monthly_partition_nulls_percent](../../../checks/column/nulls/nulls-percent.md)|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNullsPercentCheckSpec](../../../checks/column/nulls/nulls-percent.md)| | | |
|[monthly_partition_not_nulls_count](../../../checks/column/nulls/not-nulls-count.md)|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNotNullsCountCheckSpec](../../../checks/column/nulls/not-nulls-count.md)| | | |
|[monthly_partition_not_nulls_percent](../../../checks/column/nulls/not-nulls-percent.md)|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNotNullsPercentCheckSpec](../../../checks/column/nulls/not-nulls-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnCustomSqlMonthlyPartitionedChecksSpec
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_sql_condition_passed_percent_on_column](../../../checks/column/custom_sql/sql-condition-passed-percent-on-column.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlConditionPassedPercentCheckSpec](../../../checks/column/custom_sql/sql-condition-passed-percent-on-column.md)| | | |
|[monthly_partition_sql_condition_failed_count_on_column](../../../checks/column/custom_sql/sql-condition-failed-count-on-column.md)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlConditionFailedCountCheckSpec](../../../checks/column/custom_sql/sql-condition-failed-count-on-column.md)| | | |
|[monthly_partition_sql_aggregate_expression_on_column](../../../checks/column/custom_sql/sql-aggregate-expression-on-column.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlAggregateExpressionCheckSpec](../../../checks/column/custom_sql/sql-aggregate-expression-on-column.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnIntegrityMonthlyPartitionedChecksSpec
Container of integrity data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_lookup_key_not_found](../../../checks/column/integrity/lookup-key-not-found.md)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnIntegrityLookupKeyNotFoundCountCheckSpec](../../../checks/column/integrity/lookup-key-not-found.md)| | | |
|[monthly_partition_lookup_key_found_percent](../../../checks/column/integrity/lookup-key-found-percent.md)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](../../../checks/column/integrity/lookup-key-found-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnPatternsMonthlyPartitionedChecksSpec
Container of built-in preconfigured monthly partition checks on a column level that are checking for values matching patterns (regular expressions) in text columns.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_text_not_matching_regex_found](../../../checks/column/patterns/text-not-matching-regex-found.md)|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|[ColumnTextNotMatchingRegexFoundCheckSpec](../../../checks/column/patterns/text-not-matching-regex-found.md)| | | |
|[monthly_partition_texts_matching_regex_percent](../../../checks/column/patterns/texts-matching-regex-percent.md)|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|[ColumnTextsMatchingRegexPercentCheckSpec](../../../checks/column/patterns/texts-matching-regex-percent.md)| | | |
|[monthly_partition_invalid_email_format_found](../../../checks/column/patterns/invalid-email-format-found.md)|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|[ColumnInvalidEmailFormatFoundCheckSpec](../../../checks/column/patterns/invalid-email-format-found.md)| | | |
|[monthly_partition_text_not_matching_date_pattern_found](../../../checks/column/patterns/text-not-matching-date-pattern-found.md)|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|[ColumnTextNotMatchingDatePatternFoundCheckSpec](../../../checks/column/patterns/text-not-matching-date-pattern-found.md)| | | |
|[monthly_partition_text_matching_date_pattern_percent](../../../checks/column/patterns/text-matching-date-pattern-percent.md)|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|[ColumnTextMatchingDatePatternPercentCheckSpec](../../../checks/column/patterns/text-matching-date-pattern-percent.md)| | | |
|[monthly_partition_text_matching_name_pattern_percent](../../../checks/column/patterns/text-matching-name-pattern-percent.md)|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|[ColumnTextMatchingNamePatternPercentCheckSpec](../../../checks/column/patterns/text-matching-name-pattern-percent.md)| | | |
|[monthly_partition_invalid_uuid_format_found](../../../checks/column/patterns/invalid-uuid-format-found.md)|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|[ColumnInvalidUuidFormatFoundCheckSpec](../../../checks/column/patterns/invalid-uuid-format-found.md)| | | |
|[monthly_partition_valid_uuid_format_percent](../../../checks/column/patterns/valid-uuid-format-percent.md)|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|[ColumnValidUuidFormatPercentCheckSpec](../../../checks/column/patterns/valid-uuid-format-percent.md)| | | |
|[monthly_partition_invalid_ip4_address_format_found](../../../checks/column/patterns/invalid-ip4-address-format-found.md)|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|[ColumnInvalidIp4AddressFormatFoundCheckSpec](../../../checks/column/patterns/invalid-ip4-address-format-found.md)| | | |
|[monthly_partition_invalid_ip6_address_format_found](../../../checks/column/patterns/invalid-ip6-address-format-found.md)|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|[ColumnInvalidIp6AddressFormatFoundCheckSpec](../../../checks/column/patterns/invalid-ip6-address-format-found.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


