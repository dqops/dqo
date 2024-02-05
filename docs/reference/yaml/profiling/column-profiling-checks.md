# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## ColumnProfilingCheckCategoriesSpec
Container of column level, preconfigured profiling checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`nulls`](./column-profiling-checks.md#columnnullsprofilingchecksspec)</span>|Configuration of column level checks that detect null values.|*[ColumnNullsProfilingChecksSpec](./column-profiling-checks.md#columnnullsprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`uniqueness`](./column-profiling-checks.md#columnuniquenessprofilingchecksspec)</span>|Configuration of uniqueness checks on a column level.|*[ColumnUniquenessProfilingChecksSpec](./column-profiling-checks.md#columnuniquenessprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`accepted_values`](./column-profiling-checks.md#columnacceptedvaluesprofilingchecksspec)</span>|Configuration of accepted values checks on a column level.|*[ColumnAcceptedValuesProfilingChecksSpec](./column-profiling-checks.md#columnacceptedvaluesprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`text`](./column-profiling-checks.md#columntextprofilingchecksspec)</span>|Configuration of column level checks that verify text values.|*[ColumnTextProfilingChecksSpec](./column-profiling-checks.md#columntextprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`blanks`](./column-profiling-checks.md#columnblanksprofilingchecksspec)</span>|Configuration of column level checks that detect blank and whitespace values.|*[ColumnBlanksProfilingChecksSpec](./column-profiling-checks.md#columnblanksprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`patterns`](./column-profiling-checks.md#columnpatternsprofilingchecksspec)</span>|Configuration of pattern match checks on a column level.|*[ColumnPatternsProfilingChecksSpec](./column-profiling-checks.md#columnpatternsprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`pii`](./column-profiling-checks.md#columnpiiprofilingchecksspec)</span>|Configuration of Personal Identifiable Information (PII) checks on a column level.|*[ColumnPiiProfilingChecksSpec](./column-profiling-checks.md#columnpiiprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`numeric`](./column-profiling-checks.md#columnnumericprofilingchecksspec)</span>|Configuration of column level checks that verify numeric values.|*[ColumnNumericProfilingChecksSpec](./column-profiling-checks.md#columnnumericprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`anomaly`](./column-profiling-checks.md#columnanomalyprofilingchecksspec)</span>|Configuration of anomaly checks on a column level that detect anomalies in numeric columns.|*[ColumnAnomalyProfilingChecksSpec](./column-profiling-checks.md#columnanomalyprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`datetime`](./column-profiling-checks.md#columndatetimeprofilingchecksspec)</span>|Configuration of datetime checks on a column level.|*[ColumnDatetimeProfilingChecksSpec](./column-profiling-checks.md#columndatetimeprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`bool`](./column-profiling-checks.md#columnboolprofilingchecksspec)</span>|Configuration of booleans checks on a column level.|*[ColumnBoolProfilingChecksSpec](./column-profiling-checks.md#columnboolprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`integrity`](./column-profiling-checks.md#columnintegrityprofilingchecksspec)</span>|Configuration of integrity checks on a column level.|*[ColumnIntegrityProfilingChecksSpec](./column-profiling-checks.md#columnintegrityprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`accuracy`](./column-profiling-checks.md#columnaccuracyprofilingchecksspec)</span>|Configuration of accuracy checks on a column level.|*[ColumnAccuracyProfilingChecksSpec](./column-profiling-checks.md#columnaccuracyprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`custom_sql`](./column-profiling-checks.md#columncustomsqlprofilingchecksspec)</span>|Configuration of SQL checks that use custom SQL aggregated expressions and SQL conditions in data quality checks.|*[ColumnCustomSqlProfilingChecksSpec](./column-profiling-checks.md#columncustomsqlprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`datatype`](./column-profiling-checks.md#columndatatypeprofilingchecksspec)</span>|Configuration of datatype checks on a column level.|*[ColumnDatatypeProfilingChecksSpec](./column-profiling-checks.md#columndatatypeprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`schema`](./column-profiling-checks.md#columnschemaprofilingchecksspec)</span>|Configuration of schema checks on a column level.|*[ColumnSchemaProfilingChecksSpec](./column-profiling-checks.md#columnschemaprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`comparisons`](./column-profiling-checks.md#columncomparisonprofilingchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[ColumnComparisonProfilingChecksSpecMap](./column-profiling-checks.md#columncomparisonprofilingchecksspecmap)*| | | |
|<span class="no-wrap-code ">[`custom`](./table-profiling-checks.md#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](./table-profiling-checks.md#customcheckspecmap)*| | | |









___


## ColumnNullsProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking for nulls.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_nulls_count`](../../../checks/column/nulls/nulls-count.md)</span>|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count.|*[ColumnNullsCountCheckSpec](../../../checks/column/nulls/nulls-count.md)*| | | |
|<span class="no-wrap-code ">[`profile_nulls_percent`](../../../checks/column/nulls/nulls-percent.md)</span>|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage.|*[ColumnNullsPercentCheckSpec](../../../checks/column/nulls/nulls-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_not_nulls_count`](../../../checks/column/nulls/not-nulls-count.md)</span>|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count.|*[ColumnNotNullsCountCheckSpec](../../../checks/column/nulls/not-nulls-count.md)*| | | |
|<span class="no-wrap-code ">[`profile_not_nulls_percent`](../../../checks/column/nulls/not-nulls-percent.md)</span>|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage.|*[ColumnNotNullsPercentCheckSpec](../../../checks/column/nulls/not-nulls-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_nulls_percent_anomaly`](../../../checks/column/nulls/nulls-percent-anomaly.md)</span>|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|*[ColumnNullPercentAnomalyStationaryCheckSpec](../../../checks/column/nulls/nulls-percent-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`profile_nulls_percent_change`](../../../checks/column/nulls/nulls-percent-change.md)</span>|Verifies that the null percent value in a column changed in a fixed rate since last readout.|*[ColumnNullPercentChangeCheckSpec](../../../checks/column/nulls/nulls-percent-change.md)*| | | |
|<span class="no-wrap-code ">[`profile_nulls_percent_change_1_day`](../../../checks/column/nulls/nulls-percent-change-1-day.md)</span>|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|*[ColumnNullPercentChange1DayCheckSpec](../../../checks/column/nulls/nulls-percent-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`profile_nulls_percent_change_7_days`](../../../checks/column/nulls/nulls-percent-change-7-days.md)</span>|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|*[ColumnNullPercentChange7DaysCheckSpec](../../../checks/column/nulls/nulls-percent-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`profile_nulls_percent_change_30_days`](../../../checks/column/nulls/nulls-percent-change-30-days.md)</span>|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|*[ColumnNullPercentChange30DaysCheckSpec](../../../checks/column/nulls/nulls-percent-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnUniquenessProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking for negative values.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_distinct_count`](../../../checks/column/uniqueness/distinct-count.md)</span>|Verifies that the number of distinct values in a column does not fall below the minimum accepted count.|*[ColumnDistinctCountCheckSpec](../../../checks/column/uniqueness/distinct-count.md)*| | | |
|<span class="no-wrap-code ">[`profile_distinct_percent`](../../../checks/column/uniqueness/distinct-percent.md)</span>|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.|*[ColumnDistinctPercentCheckSpec](../../../checks/column/uniqueness/distinct-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_duplicate_count`](../../../checks/column/uniqueness/duplicate-count.md)</span>|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.|*[ColumnDuplicateCountCheckSpec](../../../checks/column/uniqueness/duplicate-count.md)*| | | |
|<span class="no-wrap-code ">[`profile_duplicate_percent`](../../../checks/column/uniqueness/duplicate-percent.md)</span>|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.|*[ColumnDuplicatePercentCheckSpec](../../../checks/column/uniqueness/duplicate-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_distinct_count_anomaly`](../../../checks/column/uniqueness/distinct-count-anomaly.md)</span>|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|*[ColumnDistinctCountAnomalyDifferencingCheckSpec](../../../checks/column/uniqueness/distinct-count-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`profile_distinct_percent_anomaly`](../../../checks/column/uniqueness/distinct-percent-anomaly.md)</span>|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|*[ColumnDistinctPercentAnomalyStationaryCheckSpec](../../../checks/column/uniqueness/distinct-percent-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`profile_distinct_count_change`](../../../checks/column/uniqueness/distinct-count-change.md)</span>|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|*[ColumnDistinctCountChangeCheckSpec](../../../checks/column/uniqueness/distinct-count-change.md)*| | | |
|<span class="no-wrap-code ">[`profile_distinct_count_change_1_day`](../../../checks/column/uniqueness/distinct-count-change-1-day.md)</span>|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|*[ColumnDistinctCountChange1DayCheckSpec](../../../checks/column/uniqueness/distinct-count-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`profile_distinct_count_change_7_days`](../../../checks/column/uniqueness/distinct-count-change-7-days.md)</span>|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|*[ColumnDistinctCountChange7DaysCheckSpec](../../../checks/column/uniqueness/distinct-count-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`profile_distinct_count_change_30_days`](../../../checks/column/uniqueness/distinct-count-change-30-days.md)</span>|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|*[ColumnDistinctCountChange30DaysCheckSpec](../../../checks/column/uniqueness/distinct-count-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`profile_distinct_percent_change`](../../../checks/column/uniqueness/distinct-percent-change.md)</span>|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|*[ColumnDistinctPercentChangeCheckSpec](../../../checks/column/uniqueness/distinct-percent-change.md)*| | | |
|<span class="no-wrap-code ">[`profile_distinct_percent_change_1_day`](../../../checks/column/uniqueness/distinct-percent-change-1-day.md)</span>|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|*[ColumnDistinctPercentChange1DayCheckSpec](../../../checks/column/uniqueness/distinct-percent-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`profile_distinct_percent_change_7_days`](../../../checks/column/uniqueness/distinct-percent-change-7-days.md)</span>|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|*[ColumnDistinctPercentChange7DaysCheckSpec](../../../checks/column/uniqueness/distinct-percent-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`profile_distinct_percent_change_30_days`](../../../checks/column/uniqueness/distinct-percent-change-30-days.md)</span>|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|*[ColumnDistinctPercentChange30DaysCheckSpec](../../../checks/column/uniqueness/distinct-percent-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnAcceptedValuesProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking for accepted values.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_text_found_in_set_percent`](../../../checks/column/accepted_values/text-found-in-set-percent.md)</span>|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|*[ColumnTextFoundInSetPercentCheckSpec](../../../checks/column/accepted_values/text-found-in-set-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_number_found_in_set_percent`](../../../checks/column/accepted_values/number-found-in-set-percent.md)</span>|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|*[ColumnNumberFoundInSetPercentCheckSpec](../../../checks/column/accepted_values/number-found-in-set-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_expected_text_values_in_use_count`](../../../checks/column/accepted_values/expected-text-values-in-use-count.md)</span>|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|*[ColumnExpectedTextValuesInUseCountCheckSpec](../../../checks/column/accepted_values/expected-text-values-in-use-count.md)*| | | |
|<span class="no-wrap-code ">[`profile_expected_texts_in_top_values_count`](../../../checks/column/accepted_values/expected-texts-in-top-values-count.md)</span>|Verifies that the top X most popular column values contain all values from a list of expected values.|*[ColumnExpectedTextsInTopValuesCountCheckSpec](../../../checks/column/accepted_values/expected-texts-in-top-values-count.md)*| | | |
|<span class="no-wrap-code ">[`profile_expected_numbers_in_use_count`](../../../checks/column/accepted_values/expected-numbers-in-use-count.md)</span>|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|*[ColumnExpectedNumbersInUseCountCheckSpec](../../../checks/column/accepted_values/expected-numbers-in-use-count.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnTextProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking text values.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_text_max_length`](../../../checks/column/text/text-max-length.md)</span>|Verifies that the length of a text in a column does not exceed the maximum accepted length|*[ColumnTextMaxLengthCheckSpec](../../../checks/column/text/text-max-length.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_min_length`](../../../checks/column/text/text-min-length.md)</span>|Verifies that the length of a text in a column does not fall below the minimum accepted length|*[ColumnTextMinLengthCheckSpec](../../../checks/column/text/text-min-length.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_mean_length`](../../../checks/column/text/text-mean-length.md)</span>|Verifies that the length of a text in a column does not exceed the mean accepted length|*[ColumnTextMeanLengthCheckSpec](../../../checks/column/text/text-mean-length.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_length_below_min_length`](../../../checks/column/text/text-length-below-min-length.md)</span>|The check counts the number of text values in the column that is below the length defined by the user as a parameter|*[ColumnTextLengthBelowMinLengthCheckSpec](../../../checks/column/text/text-length-below-min-length.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_length_below_min_length_percent`](../../../checks/column/text/text-length-below-min-length-percent.md)</span>|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter|*[ColumnTextLengthBelowMinLengthPercentCheckSpec](../../../checks/column/text/text-length-below-min-length-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_length_above_max_length`](../../../checks/column/text/text-length-above-max-length.md)</span>|The check counts the number of text values in the column that is above the length defined by the user as a parameter|*[ColumnTextLengthAboveMaxLengthCheckSpec](../../../checks/column/text/text-length-above-max-length.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_length_above_max_length_percent`](../../../checks/column/text/text-length-above-max-length-percent.md)</span>|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter|*[ColumnTextLengthAboveMaxLengthPercentCheckSpec](../../../checks/column/text/text-length-above-max-length-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_length_in_range_percent`](../../../checks/column/text/text-length-in-range-percent.md)</span>|The check measures the percentage of those text values with length in the range provided by the user in the column|*[ColumnTextLengthInRangePercentCheckSpec](../../../checks/column/text/text-length-in-range-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_parsable_to_boolean_percent`](../../../checks/column/text/text-parsable-to-boolean-percent.md)</span>|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n.|*[ColumnTextParsableToBooleanPercentCheckSpec](../../../checks/column/text/text-parsable-to-boolean-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_parsable_to_integer_percent`](../../../checks/column/text/text-parsable-to-integer-percent.md)</span>|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage|*[ColumnTextParsableToIntegerPercentCheckSpec](../../../checks/column/text/text-parsable-to-integer-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_parsable_to_float_percent`](../../../checks/column/text/text-parsable-to-float-percent.md)</span>|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage|*[ColumnTextParsableToFloatPercentCheckSpec](../../../checks/column/text/text-parsable-to-float-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_parsable_to_date_percent`](../../../checks/column/text/text-parsable-to-date-percent.md)</span>|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression|*[ColumnTextParsableToDatePercentCheckSpec](../../../checks/column/text/text-parsable-to-date-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_surrounded_by_whitespace`](../../../checks/column/text/text-surrounded-by-whitespace.md)</span>|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table|*[ColumnTextSurroundedByWhitespaceCheckSpec](../../../checks/column/text/text-surrounded-by-whitespace.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_surrounded_by_whitespace_percent`](../../../checks/column/text/text-surrounded-by-whitespace-percent.md)</span>|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage|*[ColumnTextSurroundedByWhitespacePercentCheckSpec](../../../checks/column/text/text-surrounded-by-whitespace-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_valid_country_code_percent`](../../../checks/column/text/text-valid-country-code-percent.md)</span>|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage|*[ColumnTextValidCountryCodePercentCheckSpec](../../../checks/column/text/text-valid-country-code-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_valid_currency_code_percent`](../../../checks/column/text/text-valid-currency-code-percent.md)</span>|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage|*[ColumnTextValidCurrencyCodePercentCheckSpec](../../../checks/column/text/text-valid-currency-code-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnBlanksProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking for blank values in text columns.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_empty_text_found`](../../../checks/column/blanks/empty-text-found.md)</span>|Verifies that empty strings in a column does not exceed the maximum accepted count.|*[ColumnBlanksEmptyTextFoundCheckSpec](../../../checks/column/blanks/empty-text-found.md)*| | | |
|<span class="no-wrap-code ">[`profile_whitespace_text_found`](../../../checks/column/blanks/whitespace-text-found.md)</span>|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.|*[ColumnBlanksWhitespaceTextFoundCheckSpec](../../../checks/column/blanks/whitespace-text-found.md)*| | | |
|<span class="no-wrap-code ">[`profile_null_placeholder_text_found`](../../../checks/column/blanks/null-placeholder-text-found.md)</span>|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.|*[ColumnBlanksNullPlaceholderTextFoundCheckSpec](../../../checks/column/blanks/null-placeholder-text-found.md)*| | | |
|<span class="no-wrap-code ">[`profile_empty_text_percent`](../../../checks/column/blanks/empty-text-percent.md)</span>|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.|*[ColumnBlanksEmptyTextPercentCheckSpec](../../../checks/column/blanks/empty-text-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_whitespace_text_percent`](../../../checks/column/blanks/whitespace-text-percent.md)</span>|Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.|*[ColumnBlanksWhitespaceTextPercentCheckSpec](../../../checks/column/blanks/whitespace-text-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_null_placeholder_text_percent`](../../../checks/column/blanks/null-placeholder-text-percent.md)</span>|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.|*[ColumnBlanksNullPlaceholderTextPercentCheckSpec](../../../checks/column/blanks/null-placeholder-text-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnPatternsProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking for values matching patterns (regular expressions) in text columns.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_text_not_matching_regex_found`](../../../checks/column/patterns/text-not-matching-regex-found.md)</span>|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|*[ColumnTextNotMatchingRegexFoundCheckSpec](../../../checks/column/patterns/text-not-matching-regex-found.md)*| | | |
|<span class="no-wrap-code ">[`profile_texts_matching_regex_percent`](../../../checks/column/patterns/texts-matching-regex-percent.md)</span>|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|*[ColumnTextsMatchingRegexPercentCheckSpec](../../../checks/column/patterns/texts-matching-regex-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_invalid_email_format_found`](../../../checks/column/patterns/invalid-email-format-found.md)</span>|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|*[ColumnInvalidEmailFormatFoundCheckSpec](../../../checks/column/patterns/invalid-email-format-found.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_not_matching_date_pattern_found`](../../../checks/column/patterns/text-not-matching-date-pattern-found.md)</span>|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|*[ColumnTextNotMatchingDatePatternFoundCheckSpec](../../../checks/column/patterns/text-not-matching-date-pattern-found.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_matching_date_pattern_percent`](../../../checks/column/patterns/text-matching-date-pattern-percent.md)</span>|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|*[ColumnTextMatchingDatePatternPercentCheckSpec](../../../checks/column/patterns/text-matching-date-pattern-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_text_matching_name_pattern_percent`](../../../checks/column/patterns/text-matching-name-pattern-percent.md)</span>|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|*[ColumnTextMatchingNamePatternPercentCheckSpec](../../../checks/column/patterns/text-matching-name-pattern-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_invalid_uuid_format_found`](../../../checks/column/patterns/invalid-uuid-format-found.md)</span>|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|*[ColumnInvalidUuidFormatFoundCheckSpec](../../../checks/column/patterns/invalid-uuid-format-found.md)*| | | |
|<span class="no-wrap-code ">[`profile_valid_uuid_format_percent`](../../../checks/column/patterns/valid-uuid-format-percent.md)</span>|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|*[ColumnValidUuidFormatPercentCheckSpec](../../../checks/column/patterns/valid-uuid-format-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_invalid_ip4_address_format_found`](../../../checks/column/patterns/invalid-ip4-address-format-found.md)</span>|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|*[ColumnInvalidIp4AddressFormatFoundCheckSpec](../../../checks/column/patterns/invalid-ip4-address-format-found.md)*| | | |
|<span class="no-wrap-code ">[`profile_invalid_ip6_address_format_found`](../../../checks/column/patterns/invalid-ip6-address-format-found.md)</span>|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|*[ColumnInvalidIp6AddressFormatFoundCheckSpec](../../../checks/column/patterns/invalid-ip6-address-format-found.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnPiiProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking for Personal Identifiable Information (PII).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_contains_usa_phone_percent`](../../../checks/column/pii/contains-usa-phone-percent.md)</span>|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.|*[ColumnPiiContainsUsaPhonePercentCheckSpec](../../../checks/column/pii/contains-usa-phone-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_contains_email_percent`](../../../checks/column/pii/contains-email-percent.md)</span>|Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.|*[ColumnPiiContainsEmailPercentCheckSpec](../../../checks/column/pii/contains-email-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_contains_usa_zipcode_percent`](../../../checks/column/pii/contains-usa-zipcode-percent.md)</span>|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.|*[ColumnPiiContainsUsaZipcodePercentCheckSpec](../../../checks/column/pii/contains-usa-zipcode-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_contains_ip4_percent`](../../../checks/column/pii/contains-ip4-percent.md)</span>|Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.|*[ColumnPiiContainsIp4PercentCheckSpec](../../../checks/column/pii/contains-ip4-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_contains_ip6_percent`](../../../checks/column/pii/contains-ip6-percent.md)</span>|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.|*[ColumnPiiContainsIp6PercentCheckSpec](../../../checks/column/pii/contains-ip6-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnNumericProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level for numeric values.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_number_below_min_value`](../../../checks/column/numeric/number-below-min-value.md)</span>|The check counts the number of values in the column that is below the value defined by the user as a parameter.|*[ColumnNumberBelowMinValueCheckSpec](../../../checks/column/numeric/number-below-min-value.md)*| | | |
|<span class="no-wrap-code ">[`profile_number_above_max_value`](../../../checks/column/numeric/number-above-max-value.md)</span>|The check counts the number of values in the column that is above the value defined by the user as a parameter.|*[ColumnNumberAboveMaxValueCheckSpec](../../../checks/column/numeric/number-above-max-value.md)*| | | |
|<span class="no-wrap-code ">[`profile_negative_values`](../../../checks/column/numeric/negative-values.md)</span>|Verifies that the number of negative values in a column does not exceed the maximum accepted count.|*[ColumnNegativeCountCheckSpec](../../../checks/column/numeric/negative-values.md)*| | | |
|<span class="no-wrap-code ">[`profile_negative_values_percent`](../../../checks/column/numeric/negative-values-percent.md)</span>|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.|*[ColumnNegativePercentCheckSpec](../../../checks/column/numeric/negative-values-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_number_below_min_value_percent`](../../../checks/column/numeric/number-below-min-value-percent.md)</span>|The check counts the percentage of values in the column that is below the value defined by the user as a parameter.|*[ColumnNumberBelowMinValuePercentCheckSpec](../../../checks/column/numeric/number-below-min-value-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_number_above_max_value_percent`](../../../checks/column/numeric/number-above-max-value-percent.md)</span>|The check counts the percentage of values in the column that is above the value defined by the user as a parameter.|*[ColumnNumberAboveMaxValuePercentCheckSpec](../../../checks/column/numeric/number-above-max-value-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_number_in_range_percent`](../../../checks/column/numeric/number-in-range-percent.md)</span>|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|*[ColumnNumberInRangePercentCheckSpec](../../../checks/column/numeric/number-in-range-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_integer_in_range_percent`](../../../checks/column/numeric/integer-in-range-percent.md)</span>|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|*[ColumnIntegerInRangePercentCheckSpec](../../../checks/column/numeric/integer-in-range-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_min_in_range`](../../../checks/column/numeric/min-in-range.md)</span>|Verifies that the minimum value in a column is not outside the expected range.|*[ColumnMinInRangeCheckSpec](../../../checks/column/numeric/min-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_max_in_range`](../../../checks/column/numeric/max-in-range.md)</span>|Verifies that the maximum value in a column is not outside the expected range.|*[ColumnMaxInRangeCheckSpec](../../../checks/column/numeric/max-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_sum_in_range`](../../../checks/column/numeric/sum-in-range.md)</span>|Verifies that the sum of all values in a column is not outside the expected range.|*[ColumnSumInRangeCheckSpec](../../../checks/column/numeric/sum-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_mean_in_range`](../../../checks/column/numeric/mean-in-range.md)</span>|Verifies that the average (mean) of all values in a column is not outside the expected range.|*[ColumnMeanInRangeCheckSpec](../../../checks/column/numeric/mean-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_median_in_range`](../../../checks/column/numeric/median-in-range.md)</span>|Verifies that the median of all values in a column is not outside the expected range.|*[ColumnMedianInRangeCheckSpec](../../../checks/column/numeric/median-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_percentile_in_range`](../../../checks/column/numeric/percentile-in-range.md)</span>|Verifies that the percentile of all values in a column is not outside the expected range.|*[ColumnPercentileInRangeCheckSpec](../../../checks/column/numeric/percentile-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_percentile_10_in_range`](../../../checks/column/numeric/percentile-10-in-range.md)</span>|Verifies that the percentile 10 of all values in a column is not outside the expected range.|*[ColumnPercentile10InRangeCheckSpec](../../../checks/column/numeric/percentile-10-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_percentile_25_in_range`](../../../checks/column/numeric/percentile-25-in-range.md)</span>|Verifies that the percentile 25 of all values in a column is not outside the expected range.|*[ColumnPercentile25InRangeCheckSpec](../../../checks/column/numeric/percentile-25-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_percentile_75_in_range`](../../../checks/column/numeric/percentile-75-in-range.md)</span>|Verifies that the percentile 75 of all values in a column is not outside the expected range.|*[ColumnPercentile75InRangeCheckSpec](../../../checks/column/numeric/percentile-75-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_percentile_90_in_range`](../../../checks/column/numeric/percentile-90-in-range.md)</span>|Verifies that the percentile 90 of all values in a column is not outside the expected range.|*[ColumnPercentile90InRangeCheckSpec](../../../checks/column/numeric/percentile-90-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_sample_stddev_in_range`](../../../checks/column/numeric/sample-stddev-in-range.md)</span>|Verifies that the sample standard deviation of all values in a column is not outside the expected range.|*[ColumnSampleStddevInRangeCheckSpec](../../../checks/column/numeric/sample-stddev-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_population_stddev_in_range`](../../../checks/column/numeric/population-stddev-in-range.md)</span>|Verifies that the population standard deviation of all values in a column is not outside the expected range.|*[ColumnPopulationStddevInRangeCheckSpec](../../../checks/column/numeric/population-stddev-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_sample_variance_in_range`](../../../checks/column/numeric/sample-variance-in-range.md)</span>|Verifies that the sample variance of all values in a column is not outside the expected range.|*[ColumnSampleVarianceInRangeCheckSpec](../../../checks/column/numeric/sample-variance-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_population_variance_in_range`](../../../checks/column/numeric/population-variance-in-range.md)</span>|Verifies that the population variance of all values in a column is not outside the expected range.|*[ColumnPopulationVarianceInRangeCheckSpec](../../../checks/column/numeric/population-variance-in-range.md)*| | | |
|<span class="no-wrap-code ">[`profile_invalid_latitude`](../../../checks/column/numeric/invalid-latitude.md)</span>|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.|*[ColumnInvalidLatitudeCountCheckSpec](../../../checks/column/numeric/invalid-latitude.md)*| | | |
|<span class="no-wrap-code ">[`profile_valid_latitude_percent`](../../../checks/column/numeric/valid-latitude-percent.md)</span>|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.|*[ColumnValidLatitudePercentCheckSpec](../../../checks/column/numeric/valid-latitude-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_invalid_longitude`](../../../checks/column/numeric/invalid-longitude.md)</span>|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.|*[ColumnInvalidLongitudeCountCheckSpec](../../../checks/column/numeric/invalid-longitude.md)*| | | |
|<span class="no-wrap-code ">[`profile_valid_longitude_percent`](../../../checks/column/numeric/valid-longitude-percent.md)</span>|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.|*[ColumnValidLongitudePercentCheckSpec](../../../checks/column/numeric/valid-longitude-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_non_negative_values`](../../../checks/column/numeric/non-negative-values.md)</span>|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.|*[ColumnNonNegativeCountCheckSpec](../../../checks/column/numeric/non-negative-values.md)*| | | |
|<span class="no-wrap-code ">[`profile_non_negative_values_percent`](../../../checks/column/numeric/non-negative-values-percent.md)</span>|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.|*[ColumnNonNegativePercentCheckSpec](../../../checks/column/numeric/non-negative-values-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnAnomalyProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_sum_anomaly`](../../../checks/column/anomaly/sum-anomaly.md)</span>|Verifies that the sum in a column changes in a rate within a percentile boundary during the last 90 days.|*[ColumnSumAnomalyDifferencingCheckSpec](../../../checks/column/anomaly/sum-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`profile_mean_anomaly`](../../../checks/column/anomaly/mean-anomaly.md)</span>|Verifies that the mean value in a column changes in a rate within a percentile boundary during the last 90 days.|*[ColumnMeanAnomalyStationaryCheckSpec](../../../checks/column/anomaly/mean-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`profile_median_anomaly`](../../../checks/column/anomaly/median-anomaly.md)</span>|Verifies that the median in a column changes in a rate within a percentile boundary during the last 90 days.|*[ColumnMedianAnomalyStationaryCheckSpec](../../../checks/column/anomaly/median-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`profile_min_anomaly`](../../../checks/column/anomaly/min-anomaly.md)</span>|Detects new outliers, which are new minimum values, much below the last known minimum value. If the minimum value is constantly changing, detects outliers as the biggest change of the minimum value during the last 90 days.|*[ColumnMinAnomalyDifferencingCheckSpec](../../../checks/column/anomaly/min-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`profile_max_anomaly`](../../../checks/column/anomaly/max-anomaly.md)</span>|Detects new outliers, which are new maximum values, much above the last known maximum value. If the maximum value is constantly changing, detects outliers as the biggest change of the maximum value during the last 90 days.|*[ColumnMaxAnomalyDifferencingCheckSpec](../../../checks/column/anomaly/max-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`profile_mean_change`](../../../checks/column/anomaly/mean-change.md)</span>|Verifies that the mean value in a column changed in a fixed rate since the last readout.|*[ColumnMeanChangeCheckSpec](../../../checks/column/anomaly/mean-change.md)*| | | |
|<span class="no-wrap-code ">[`profile_mean_change_1_day`](../../../checks/column/anomaly/mean-change-1-day.md)</span>|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.|*[ColumnMeanChange1DayCheckSpec](../../../checks/column/anomaly/mean-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`profile_mean_change_7_days`](../../../checks/column/anomaly/mean-change-7-days.md)</span>|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.|*[ColumnMeanChange7DaysCheckSpec](../../../checks/column/anomaly/mean-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`profile_mean_change_30_days`](../../../checks/column/anomaly/mean-change-30-days.md)</span>|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.|*[ColumnMeanChange30DaysCheckSpec](../../../checks/column/anomaly/mean-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`profile_median_change`](../../../checks/column/anomaly/median-change.md)</span>|Verifies that the median in a column changed in a fixed rate since the last readout.|*[ColumnMedianChangeCheckSpec](../../../checks/column/anomaly/median-change.md)*| | | |
|<span class="no-wrap-code ">[`profile_median_change_1_day`](../../../checks/column/anomaly/median-change-1-day.md)</span>|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|*[ColumnMedianChange1DayCheckSpec](../../../checks/column/anomaly/median-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`profile_median_change_7_days`](../../../checks/column/anomaly/median-change-7-days.md)</span>|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|*[ColumnMedianChange7DaysCheckSpec](../../../checks/column/anomaly/median-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`profile_median_change_30_days`](../../../checks/column/anomaly/median-change-30-days.md)</span>|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|*[ColumnMedianChange30DaysCheckSpec](../../../checks/column/anomaly/median-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`profile_sum_change`](../../../checks/column/anomaly/sum-change.md)</span>|Verifies that the sum in a column changed in a fixed rate since the last readout.|*[ColumnSumChangeCheckSpec](../../../checks/column/anomaly/sum-change.md)*| | | |
|<span class="no-wrap-code ">[`profile_sum_change_1_day`](../../../checks/column/anomaly/sum-change-1-day.md)</span>|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|*[ColumnSumChange1DayCheckSpec](../../../checks/column/anomaly/sum-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`profile_sum_change_7_days`](../../../checks/column/anomaly/sum-change-7-days.md)</span>|Verifies that the sum in a column changed in a fixed rate since the last readout from last week.|*[ColumnSumChange7DaysCheckSpec](../../../checks/column/anomaly/sum-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`profile_sum_change_30_days`](../../../checks/column/anomaly/sum-change-30-days.md)</span>|Verifies that the sum in a column changed in a fixed rate since the last readout from last month.|*[ColumnSumChange30DaysCheckSpec](../../../checks/column/anomaly/sum-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnDatetimeProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking for datetime.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_date_values_in_future_percent`](../../../checks/column/datetime/date-values-in-future-percent.md)</span>|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage.|*[ColumnDateValuesInFuturePercentCheckSpec](../../../checks/column/datetime/date-values-in-future-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_date_in_range_percent`](../../../checks/column/datetime/date-in-range-percent.md)</span>|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage.|*[ColumnDateInRangePercentCheckSpec](../../../checks/column/datetime/date-in-range-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_date_match_format_percent`](../../../checks/column/datetime/date-match-format-percent.md)</span>|Verifies that the percentage of date values matching the given format in a text column does not exceed the maximum accepted percentage.|*[ColumnDatetimeDateMatchFormatPercentCheckSpec](../../../checks/column/datetime/date-match-format-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnBoolProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking for booleans.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_true_percent`](../../../checks/column/bool/true-percent.md)</span>|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage.|*[ColumnTruePercentCheckSpec](../../../checks/column/bool/true-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_false_percent`](../../../checks/column/bool/false-percent.md)</span>|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage.|*[ColumnFalsePercentCheckSpec](../../../checks/column/bool/false-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnIntegrityProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking for integrity.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_lookup_key_not_found`](../../../checks/column/integrity/lookup-key-not-found.md)</span>|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.|*[ColumnIntegrityLookupKeyNotFoundCountCheckSpec](../../../checks/column/integrity/lookup-key-not-found.md)*| | | |
|<span class="no-wrap-code ">[`profile_lookup_key_found_percent`](../../../checks/column/integrity/lookup-key-found-percent.md)</span>|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.|*[ColumnIntegrityForeignKeyMatchPercentCheckSpec](../../../checks/column/integrity/lookup-key-found-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnAccuracyProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking for accuracy.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_total_sum_match_percent`](../../../checks/column/accuracy/total-sum-match-percent.md)</span>|Verifies that percentage of the difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number.|*[ColumnAccuracyTotalSumMatchPercentCheckSpec](../../../checks/column/accuracy/total-sum-match-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_total_min_match_percent`](../../../checks/column/accuracy/total-min-match-percent.md)</span>|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number.|*[ColumnAccuracyTotalMinMatchPercentCheckSpec](../../../checks/column/accuracy/total-min-match-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_total_max_match_percent`](../../../checks/column/accuracy/total-max-match-percent.md)</span>|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number.|*[ColumnAccuracyTotalMaxMatchPercentCheckSpec](../../../checks/column/accuracy/total-max-match-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_total_average_match_percent`](../../../checks/column/accuracy/total-average-match-percent.md)</span>|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number.|*[ColumnAccuracyTotalAverageMatchPercentCheckSpec](../../../checks/column/accuracy/total-average-match-percent.md)*| | | |
|<span class="no-wrap-code ">[`profile_total_not_null_count_match_percent`](../../../checks/column/accuracy/total-not-null-count-match-percent.md)</span>|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|*[ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec](../../../checks/column/accuracy/total-not-null-count-match-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnCustomSqlProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_sql_condition_failed_on_column`](../../../checks/column/custom_sql/sql-condition-failed-on-column.md)</span>|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; col_tax&#x60;.|*[ColumnSqlConditionFailedCheckSpec](../../../checks/column/custom_sql/sql-condition-failed-on-column.md)*| | | |
|<span class="no-wrap-code ">[`profile_sql_condition_passed_percent_on_column`](../../../checks/column/custom_sql/sql-condition-passed-percent-on-column.md)</span>|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;.|*[ColumnSqlConditionPassedPercentCheckSpec](../../../checks/column/custom_sql/sql-condition-passed-percent-on-column.md)*| | | |
|<span class="no-wrap-code ">[`profile_sql_aggregate_expression_on_column`](../../../checks/column/custom_sql/sql-aggregate-expression-on-column.md)</span>|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range.|*[ColumnSqlAggregateExpressionCheckSpec](../../../checks/column/custom_sql/sql-aggregate-expression-on-column.md)*| | | |
|<span class="no-wrap-code ">[`profile_import_custom_result_on_column`](../../../checks/column/custom_sql/import-custom-result-on-column.md)</span>|Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.|*[ColumnSqlImportCustomResultCheckSpec](../../../checks/column/custom_sql/import-custom-result-on-column.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnDatatypeProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking for datatype.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_detected_datatype_in_text`](../../../checks/column/datatype/detected-datatype-in-text.md)</span>|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type.|*[ColumnDetectedDatatypeInTextCheckSpec](../../../checks/column/datatype/detected-datatype-in-text.md)*| | | |
|<span class="no-wrap-code ">[`profile_detected_datatype_in_text_changed`](../../../checks/column/datatype/detected-datatype-in-text-changed.md)</span>|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 6 - booleans, 7 - strings, 8 - mixed data types.|*[ColumnDatatypeStringDatatypeChangedCheckSpec](../../../checks/column/datatype/detected-datatype-in-text-changed.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnSchemaProfilingChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking the column schema.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_column_exists`](../../../checks/column/schema/column-exists.md)</span>|Checks the metadata of the monitored table and verifies if the column exists.|*[ColumnSchemaColumnExistsCheckSpec](../../../checks/column/schema/column-exists.md)*| | | |
|<span class="no-wrap-code ">[`profile_column_type_changed`](../../../checks/column/schema/column-type-changed.md)</span>|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed.|*[ColumnSchemaTypeChangedCheckSpec](../../../checks/column/schema/column-type-changed.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## ColumnComparisonProfilingChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`self`</span>||*Dict[string, [ColumnComparisonProfilingChecksSpec](./column-profiling-checks.md#columncomparisonprofilingchecksspec)]*| | | |









___


## ColumnComparisonProfilingChecksSpec
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_sum_match`](../../../checks/column/comparisons/sum-match.md)</span>|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds.|*[ColumnComparisonSumMatchCheckSpec](../../../checks/column/comparisons/sum-match.md)*| | | |
|<span class="no-wrap-code ">[`profile_min_match`](../../../checks/column/comparisons/min-match.md)</span>|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds.|*[ColumnComparisonMinMatchCheckSpec](../../../checks/column/comparisons/min-match.md)*| | | |
|<span class="no-wrap-code ">[`profile_max_match`](../../../checks/column/comparisons/max-match.md)</span>|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds.|*[ColumnComparisonMaxMatchCheckSpec](../../../checks/column/comparisons/max-match.md)*| | | |
|<span class="no-wrap-code ">[`profile_mean_match`](../../../checks/column/comparisons/mean-match.md)</span>|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds.|*[ColumnComparisonMeanMatchCheckSpec](../../../checks/column/comparisons/mean-match.md)*| | | |
|<span class="no-wrap-code ">[`profile_not_null_count_match`](../../../checks/column/comparisons/not-null-count-match.md)</span>|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds.|*[ColumnComparisonNotNullCountMatchCheckSpec](../../../checks/column/comparisons/not-null-count-match.md)*| | | |
|<span class="no-wrap-code ">[`profile_null_count_match`](../../../checks/column/comparisons/null-count-match.md)</span>|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds.|*[ColumnComparisonNullCountMatchCheckSpec](../../../checks/column/comparisons/null-count-match.md)*| | | |
|<span class="no-wrap-code ">`reference_column`</span>|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|*string*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


