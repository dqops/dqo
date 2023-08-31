
## ColumnPiiMonthlyPartitionedChecksSpec  
Container of PII data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_contains_usa_phone_percent](\docs\checks\column\pii\contains-usa-phone-percent)|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsUsaPhonePercentCheckSpec](\docs\checks\column\pii\contains-usa-phone-percent)| | | |
|[monthly_partition_contains_usa_zipcode_percent](\docs\checks\column\pii\contains-usa-zipcode-percent)|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](\docs\checks\column\pii\contains-usa-zipcode-percent)| | | |
|[monthly_partition_contains_email_percent](\docs\checks\column\pii\contains-email-percent)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsEmailPercentCheckSpec](\docs\checks\column\pii\contains-email-percent)| | | |
|[monthly_partition_contains_ip4_percent](\docs\checks\column\pii\contains-ip4-percent)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsIp4PercentCheckSpec](\docs\checks\column\pii\contains-ip4-percent)| | | |
|[monthly_partition_contains_ip6_percent](\docs\checks\column\pii\contains-ip6-percent)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsIp6PercentCheckSpec](\docs\checks\column\pii\contains-ip6-percent)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnComparisonMonthlyPartitionedChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |









___  

## ColumnIntegrityMonthlyPartitionedChecksSpec  
Container of integrity data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_foreign_key_not_match_count](\docs\checks\column\integrity\foreign-key-not-match-count)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](\docs\checks\column\integrity\foreign-key-not-match-count)| | | |
|[monthly_partition_foreign_key_match_percent](\docs\checks\column\integrity\foreign-key-match-percent)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](\docs\checks\column\integrity\foreign-key-match-percent)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnNumericMonthlyPartitionedChecksSpec  
Container of numeric data quality partitioned checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_negative_count](\docs\checks\column\numeric\negative-count)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNegativeCountCheckSpec](\docs\checks\column\numeric\negative-count)| | | |
|[monthly_partition_negative_percent](\docs\checks\column\numeric\negative-percent)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNegativePercentCheckSpec](\docs\checks\column\numeric\negative-percent)| | | |
|[monthly_partition_non_negative_count](\docs\checks\column\numeric\non-negative-count)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNonNegativeCountCheckSpec](\docs\checks\column\numeric\non-negative-count)| | | |
|[monthly_partition_non_negative_percent](\docs\checks\column\numeric\non-negative-percent)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNonNegativePercentCheckSpec](\docs\checks\column\numeric\non-negative-percent)| | | |
|[monthly_partition_expected_numbers_in_use_count](\docs\checks\column\numeric\expected-numbers-in-use-count)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnExpectedNumbersInUseCountCheckSpec](\docs\checks\column\numeric\expected-numbers-in-use-count)| | | |
|[monthly_partition_number_value_in_set_percent](\docs\checks\column\numeric\number-value-in-set-percent)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNumberValueInSetPercentCheckSpec](\docs\checks\column\numeric\number-value-in-set-percent)| | | |
|[monthly_partition_values_in_range_numeric_percent](\docs\checks\column\numeric\values-in-range-numeric-percent)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValuesInRangeNumericPercentCheckSpec](\docs\checks\column\numeric\values-in-range-numeric-percent)| | | |
|[monthly_partition_values_in_range_integers_percent](\docs\checks\column\numeric\values-in-range-integers-percent)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValuesInRangeIntegersPercentCheckSpec](\docs\checks\column\numeric\values-in-range-integers-percent)| | | |
|[monthly_partition_value_below_min_value_count](\docs\checks\column\numeric\value-below-min-value-count)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueBelowMinValueCountCheckSpec](\docs\checks\column\numeric\value-below-min-value-count)| | | |
|[monthly_partition_value_below_min_value_percent](\docs\checks\column\numeric\value-below-min-value-percent)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueBelowMinValuePercentCheckSpec](\docs\checks\column\numeric\value-below-min-value-percent)| | | |
|[monthly_partition_value_above_max_value_count](\docs\checks\column\numeric\value-above-max-value-count)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueAboveMaxValueCountCheckSpec](\docs\checks\column\numeric\value-above-max-value-count)| | | |
|[monthly_partition_value_above_max_value_percent](\docs\checks\column\numeric\value-above-max-value-percent)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueAboveMaxValuePercentCheckSpec](\docs\checks\column\numeric\value-above-max-value-percent)| | | |
|[monthly_partition_max_in_range](\docs\checks\column\numeric\max-in-range)|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMaxInRangeCheckSpec](\docs\checks\column\numeric\max-in-range)| | | |
|[monthly_partition_min_in_range](\docs\checks\column\numeric\min-in-range)|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMinInRangeCheckSpec](\docs\checks\column\numeric\min-in-range)| | | |
|[monthly_partition_mean_in_range](\docs\checks\column\numeric\mean-in-range)|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMeanInRangeCheckSpec](\docs\checks\column\numeric\mean-in-range)| | | |
|[monthly_partition_percentile_in_range](\docs\checks\column\numeric\percentile-in-range)|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentileInRangeCheckSpec](\docs\checks\column\numeric\percentile-in-range)| | | |
|[monthly_partition_median_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMedianInRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnmedianinrangecheckspec)| | | |
|[monthly_partition_percentile_10_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile10InRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile10inrangecheckspec)| | | |
|[monthly_partition_percentile_25_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile25InRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile25inrangecheckspec)| | | |
|[monthly_partition_percentile_75_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile75InRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile75inrangecheckspec)| | | |
|[monthly_partition_percentile_90_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile90InRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile90inrangecheckspec)| | | |
|[monthly_partition_sample_stddev_in_range](\docs\checks\column\numeric\sample-stddev-in-range)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSampleStddevInRangeCheckSpec](\docs\checks\column\numeric\sample-stddev-in-range)| | | |
|[monthly_partition_population_stddev_in_range](\docs\checks\column\numeric\population-stddev-in-range)|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPopulationStddevInRangeCheckSpec](\docs\checks\column\numeric\population-stddev-in-range)| | | |
|[monthly_partition_sample_variance_in_range](\docs\checks\column\numeric\sample-variance-in-range)|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSampleVarianceInRangeCheckSpec](\docs\checks\column\numeric\sample-variance-in-range)| | | |
|[monthly_partition_population_variance_in_range](\docs\checks\column\numeric\population-variance-in-range)|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPopulationVarianceInRangeCheckSpec](\docs\checks\column\numeric\population-variance-in-range)| | | |
|[monthly_partition_sum_in_range](\docs\checks\column\numeric\sum-in-range)|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSumInRangeCheckSpec](\docs\checks\column\numeric\sum-in-range)| | | |
|[monthly_partition_invalid_latitude_count](\docs\checks\column\numeric\invalid-latitude-count)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnInvalidLatitudeCountCheckSpec](\docs\checks\column\numeric\invalid-latitude-count)| | | |
|[monthly_partition_valid_latitude_percent](\docs\checks\column\numeric\valid-latitude-percent)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValidLatitudePercentCheckSpec](\docs\checks\column\numeric\valid-latitude-percent)| | | |
|[monthly_partition_invalid_longitude_count](\docs\checks\column\numeric\invalid-longitude-count)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnInvalidLongitudeCountCheckSpec](\docs\checks\column\numeric\invalid-longitude-count)| | | |
|[monthly_partition_valid_longitude_percent](\docs\checks\column\numeric\valid-longitude-percent)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValidLongitudePercentCheckSpec](\docs\checks\column\numeric\valid-longitude-percent)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnSqlMonthlyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_sql_condition_passed_percent_on_column](\docs\checks\column\sql\sql-condition-passed-percent-on-column)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlConditionPassedPercentCheckSpec](\docs\checks\column\sql\sql-condition-passed-percent-on-column)| | | |
|[monthly_partition_sql_condition_failed_count_on_column](\docs\checks\column\sql\sql-condition-failed-count-on-column)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlConditionFailedCountCheckSpec](\docs\checks\column\sql\sql-condition-failed-count-on-column)| | | |
|[monthly_partition_sql_aggregate_expr_column](\docs\checks\column\sql\sql-aggregate-expr-column)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlAggregateExprCheckSpec](\docs\checks\column\sql\sql-aggregate-expr-column)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnBoolMonthlyPartitionedChecksSpec  
Container of boolean data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_true_percent](\docs\checks\column\bool\true-percent)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnTruePercentCheckSpec](\docs\checks\column\bool\true-percent)| | | |
|[monthly_partition_false_percent](\docs\checks\column\bool\false-percent)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnFalsePercentCheckSpec](\docs\checks\column\bool\false-percent)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnDatatypeMonthlyPartitionedChecksSpec  
Container of datatype data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_string_datatype_detected](\docs\checks\column\datatype\string-datatype-detected)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDatatypeStringDatatypeDetectedCheckSpec](\docs\checks\column\datatype\string-datatype-detected)| | | |
|[monthly_partition_string_datatype_changed](\docs\checks\column\datatype\string-datatype-changed)|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDatatypeStringDatatypeChangedCheckSpec](\docs\checks\column\datatype\string-datatype-changed)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnNullsMonthlyPartitionedChecksSpec  
Container of nulls data quality partitioned checks on a column level that are checking monthly partitions or rows for each day of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_nulls_count](\docs\checks\column\nulls\nulls-count)|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNullsCountCheckSpec](\docs\checks\column\nulls\nulls-count)| | | |
|[monthly_partition_nulls_percent](\docs\checks\column\nulls\nulls-percent)|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNullsPercentCheckSpec](\docs\checks\column\nulls\nulls-percent)| | | |
|[monthly_partition_not_nulls_count](\docs\checks\column\nulls\not-nulls-count)|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNotNullsCountCheckSpec](\docs\checks\column\nulls\not-nulls-count)| | | |
|[monthly_partition_not_nulls_percent](\docs\checks\column\nulls\not-nulls-percent)|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNotNullsPercentCheckSpec](\docs\checks\column\nulls\not-nulls-percent)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnStringsMonthlyPartitionedChecksSpec  
Container of strings data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_string_max_length](\docs\checks\column\strings\string-max-length)|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMaxLengthCheckSpec](\docs\checks\column\strings\string-max-length)| | | |
|[monthly_partition_string_min_length](\docs\checks\column\strings\string-min-length)|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMinLengthCheckSpec](\docs\checks\column\strings\string-min-length)| | | |
|[monthly_partition_string_mean_length](\docs\checks\column\strings\string-mean-length)|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMeanLengthCheckSpec](\docs\checks\column\strings\string-mean-length)| | | |
|[monthly_partition_string_length_below_min_length_count](\docs\checks\column\strings\string-length-below-min-length-count)|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthBelowMinLengthCountCheckSpec](\docs\checks\column\strings\string-length-below-min-length-count)| | | |
|[monthly_partition_string_length_below_min_length_percent](\docs\checks\column\strings\string-length-below-min-length-percent)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](\docs\checks\column\strings\string-length-below-min-length-percent)| | | |
|[monthly_partition_string_length_above_max_length_count](\docs\checks\column\strings\string-length-above-max-length-count)|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](\docs\checks\column\strings\string-length-above-max-length-count)| | | |
|[monthly_partition_string_length_above_max_length_percent](\docs\checks\column\strings\string-length-above-max-length-percent)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](\docs\checks\column\strings\string-length-above-max-length-percent)| | | |
|[monthly_partition_string_length_in_range_percent](\docs\checks\column\strings\string-length-in-range-percent)|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthInRangePercentCheckSpec](\docs\checks\column\strings\string-length-in-range-percent)| | | |
|[monthly_partition_string_empty_count](\docs\checks\column\strings\string-empty-count)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringEmptyCountCheckSpec](\docs\checks\column\strings\string-empty-count)| | | |
|[monthly_partition_string_empty_percent](\docs\checks\column\strings\string-empty-percent)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringEmptyPercentCheckSpec](\docs\checks\column\strings\string-empty-percent)| | | |
|[monthly_partition_string_whitespace_count](\docs\checks\column\strings\string-whitespace-count)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringWhitespaceCountCheckSpec](\docs\checks\column\strings\string-whitespace-count)| | | |
|[monthly_partition_string_whitespace_percent](\docs\checks\column\strings\string-whitespace-percent)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringWhitespacePercentCheckSpec](\docs\checks\column\strings\string-whitespace-percent)| | | |
|[monthly_partition_string_surrounded_by_whitespace_count](\docs\checks\column\strings\string-surrounded-by-whitespace-count)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](\docs\checks\column\strings\string-surrounded-by-whitespace-count)| | | |
|[monthly_partition_string_surrounded_by_whitespace_percent](\docs\checks\column\strings\string-surrounded-by-whitespace-percent)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](\docs\checks\column\strings\string-surrounded-by-whitespace-percent)| | | |
|[monthly_partition_string_null_placeholder_count](\docs\checks\column\strings\string-null-placeholder-count)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNullPlaceholderCountCheckSpec](\docs\checks\column\strings\string-null-placeholder-count)| | | |
|[monthly_partition_string_null_placeholder_percent](\docs\checks\column\strings\string-null-placeholder-percent)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNullPlaceholderPercentCheckSpec](\docs\checks\column\strings\string-null-placeholder-percent)| | | |
|[monthly_partition_string_boolean_placeholder_percent](\docs\checks\column\strings\string-boolean-placeholder-percent)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringBooleanPlaceholderPercentCheckSpec](\docs\checks\column\strings\string-boolean-placeholder-percent)| | | |
|[monthly_partition_string_parsable_to_integer_percent](\docs\checks\column\strings\string-parsable-to-integer-percent)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringParsableToIntegerPercentCheckSpec](\docs\checks\column\strings\string-parsable-to-integer-percent)| | | |
|[monthly_partition_string_parsable_to_float_percent](\docs\checks\column\strings\string-parsable-to-float-percent)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringParsableToFloatPercentCheckSpec](\docs\checks\column\strings\string-parsable-to-float-percent)| | | |
|[monthly_partition_expected_strings_in_use_count](\docs\checks\column\strings\expected-strings-in-use-count)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnExpectedStringsInUseCountCheckSpec](\docs\checks\column\strings\expected-strings-in-use-count)| | | |
|[monthly_partition_string_value_in_set_percent](\docs\checks\column\strings\string-value-in-set-percent)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValueInSetPercentCheckSpec](\docs\checks\column\strings\string-value-in-set-percent)| | | |
|[monthly_partition_string_valid_dates_percent](\docs\checks\column\strings\string-valid-dates-percent)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidDatesPercentCheckSpec](\docs\checks\column\strings\string-valid-dates-percent)| | | |
|[monthly_partition_string_valid_country_code_percent](\docs\checks\column\strings\string-valid-country-code-percent)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidCountryCodePercentCheckSpec](\docs\checks\column\strings\string-valid-country-code-percent)| | | |
|[monthly_partition_string_valid_currency_code_percent](\docs\checks\column\strings\string-valid-currency-code-percent)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidCurrencyCodePercentCheckSpec](\docs\checks\column\strings\string-valid-currency-code-percent)| | | |
|[monthly_partition_string_invalid_email_count](\docs\checks\column\strings\string-invalid-email-count)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidEmailCountCheckSpec](\docs\checks\column\strings\string-invalid-email-count)| | | |
|[monthly_partition_string_invalid_uuid_count](\docs\checks\column\strings\string-invalid-uuid-count)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidUuidCountCheckSpec](\docs\checks\column\strings\string-invalid-uuid-count)| | | |
|[monthly_partition_valid_uuid_percent](\docs\checks\column\strings\string-valid-uuid-percent)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidUuidPercentCheckSpec](\docs\checks\column\strings\string-valid-uuid-percent)| | | |
|[monthly_partition_string_invalid_ip4_address_count](\docs\checks\column\strings\string-invalid-ip4-address-count)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidIp4AddressCountCheckSpec](\docs\checks\column\strings\string-invalid-ip4-address-count)| | | |
|[monthly_partition_string_invalid_ip6_address_count](\docs\checks\column\strings\string-invalid-ip6-address-count)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidIp6AddressCountCheckSpec](\docs\checks\column\strings\string-invalid-ip6-address-count)| | | |
|[monthly_partition_string_not_match_regex_count](\docs\checks\column\strings\string-not-match-regex-count)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNotMatchRegexCountCheckSpec](\docs\checks\column\strings\string-not-match-regex-count)| | | |
|[monthly_partition_string_match_regex_percent](\docs\checks\column\strings\string-match-regex-percent)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMatchRegexPercentCheckSpec](\docs\checks\column\strings\string-match-regex-percent)| | | |
|[monthly_partition_string_not_match_date_regex_count](\docs\checks\column\strings\string-not-match-date-regex-count)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNotMatchDateRegexCountCheckSpec](\docs\checks\column\strings\string-not-match-date-regex-count)| | | |
|[monthly_partition_string_match_date_regex_percent](\docs\checks\column\strings\string-match-date-regex-percent)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMatchDateRegexPercentCheckSpec](\docs\checks\column\strings\string-match-date-regex-percent)| | | |
|[monthly_partition_string_match_name_regex_percent](\docs\checks\column\strings\string-match-name-regex-percent)|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMatchNameRegexPercentCheckSpec](\docs\checks\column\strings\string-match-name-regex-percent)| | | |
|[monthly_partition_expected_strings_in_top_values_count](\docs\checks\column\strings\expected-strings-in-top-values-count)|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnExpectedStringsInTopValuesCountCheckSpec](\docs\checks\column\strings\expected-strings-in-top-values-count)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnComparisonMonthlyPartitionedChecksSpec  
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 This is the configuration for daily partitioned checks that are counted in KPIs.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_sum_match](\docs\checks\column\comparisons\sum-match)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonSumMatchCheckSpec](\docs\checks\column\comparisons\sum-match)| | | |
|[monthly_partition_min_match](\docs\checks\column\comparisons\min-match)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMinMatchCheckSpec](\docs\checks\column\comparisons\min-match)| | | |
|[monthly_partition_max_match](\docs\checks\column\comparisons\max-match)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMaxMatchCheckSpec](\docs\checks\column\comparisons\max-match)| | | |
|[monthly_partition_mean_match](\docs\checks\column\comparisons\mean-match)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMeanMatchCheckSpec](\docs\checks\column\comparisons\mean-match)| | | |
|[monthly_partition_not_null_count_match](\docs\checks\column\comparisons\not-null-count-match)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonNotNullCountMatchCheckSpec](\docs\checks\column\comparisons\not-null-count-match)| | | |
|[monthly_partition_null_count_match](\docs\checks\column\comparisons\null-count-match)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each monthly partition (each month of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonNullCountMatchCheckSpec](\docs\checks\column\comparisons\null-count-match)| | | |
|reference_column|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|string| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnAnomalyMonthlyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_mean_change](\docs\checks\column\anomaly\mean-change)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](\docs\checks\column\anomaly\mean-change)| | | |
|[monthly_partition_median_change](\docs\checks\column\anomaly\median-change)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](\docs\checks\column\anomaly\median-change)| | | |
|[monthly_partition_sum_change](\docs\checks\column\anomaly\sum-change)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](\docs\checks\column\anomaly\sum-change)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnAccuracyMonthlyPartitionedChecksSpec  
Container of accuracy data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnMonthlyPartitionedCheckCategoriesSpec  
Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](#columnnullsmonthlypartitionedchecksspec)|Monthly partitioned checks of nulls values in the column|[ColumnNullsMonthlyPartitionedChecksSpec](#columnnullsmonthlypartitionedchecksspec)| | | |
|[numeric](#columnnumericmonthlypartitionedchecksspec)|Monthly partitioned checks of numeric values in the column|[ColumnNumericMonthlyPartitionedChecksSpec](#columnnumericmonthlypartitionedchecksspec)| | | |
|[strings](#columnstringsmonthlypartitionedchecksspec)|Monthly partitioned checks of strings values in the column|[ColumnStringsMonthlyPartitionedChecksSpec](#columnstringsmonthlypartitionedchecksspec)| | | |
|[uniqueness](#columnuniquenessmonthlypartitionedchecksspec)|Monthly partitioned checks of uniqueness values in the column|[ColumnUniquenessMonthlyPartitionedChecksSpec](#columnuniquenessmonthlypartitionedchecksspec)| | | |
|[datetime](#columndatetimemonthlypartitionedchecksspec)|Monthly partitioned checks of datetime values in the column|[ColumnDatetimeMonthlyPartitionedChecksSpec](#columndatetimemonthlypartitionedchecksspec)| | | |
|[pii](#columnpiimonthlypartitionedchecksspec)|Monthly partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyPartitionedChecksSpec](#columnpiimonthlypartitionedchecksspec)| | | |
|[sql](#columnsqlmonthlypartitionedchecksspec)|Monthly partitioned checks using custom SQL expressions and conditions on the column|[ColumnSqlMonthlyPartitionedChecksSpec](#columnsqlmonthlypartitionedchecksspec)| | | |
|[bool](#columnboolmonthlypartitionedchecksspec)|Monthly partitioned checks for booleans in the column|[ColumnBoolMonthlyPartitionedChecksSpec](#columnboolmonthlypartitionedchecksspec)| | | |
|[integrity](#columnintegritymonthlypartitionedchecksspec)|Monthly partitioned checks for integrity in the column|[ColumnIntegrityMonthlyPartitionedChecksSpec](#columnintegritymonthlypartitionedchecksspec)| | | |
|[accuracy](#columnaccuracymonthlypartitionedchecksspec)|Monthly partitioned checks for accuracy in the column|[ColumnAccuracyMonthlyPartitionedChecksSpec](#columnaccuracymonthlypartitionedchecksspec)| | | |
|[datatype](#columndatatypemonthlypartitionedchecksspec)|Monthly partitioned checks for datatype in the column|[ColumnDatatypeMonthlyPartitionedChecksSpec](#columndatatypemonthlypartitionedchecksspec)| | | |
|[anomaly](#columnanomalymonthlypartitionedchecksspec)|Monthly partitioned checks for anomaly in the column|[ColumnAnomalyMonthlyPartitionedChecksSpec](#columnanomalymonthlypartitionedchecksspec)| | | |
|[comparisons](#columncomparisonmonthlypartitionedchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonMonthlyPartitionedChecksSpecMap](#columncomparisonmonthlypartitionedchecksspecmap)| | | |
|[custom](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)| | | |









___  

## ColumnUniquenessMonthlyPartitionedChecksSpec  
Container of uniqueness data quality partitioned checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_distinct_count](\docs\checks\column\uniqueness\distinct-count)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDistinctCountCheckSpec](\docs\checks\column\uniqueness\distinct-count)| | | |
|[monthly_partition_distinct_percent](\docs\checks\column\uniqueness\distinct-percent)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDistinctPercentCheckSpec](\docs\checks\column\uniqueness\distinct-percent)| | | |
|[monthly_partition_duplicate_count](\docs\checks\column\uniqueness\duplicate-count)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDuplicateCountCheckSpec](\docs\checks\column\uniqueness\duplicate-count)| | | |
|[monthly_partition_duplicate_percent](\docs\checks\column\uniqueness\duplicate-percent)|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDuplicatePercentCheckSpec](\docs\checks\column\uniqueness\duplicate-percent)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnDatetimeMonthlyPartitionedChecksSpec  
Container of date-time data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_date_match_format_percent](\docs\checks\column\datetime\date-match-format-percent)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDatetimeDateMatchFormatPercentCheckSpec](\docs\checks\column\datetime\date-match-format-percent)| | | |
|[monthly_partition_date_values_in_future_percent](\docs\checks\column\datetime\date-values-in-future-percent)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDateValuesInFuturePercentCheckSpec](\docs\checks\column\datetime\date-values-in-future-percent)| | | |
|[monthly_partition_datetime_value_in_range_date_percent](\docs\checks\column\datetime\datetime-value-in-range-date-percent)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](\docs\checks\column\datetime\datetime-value-in-range-date-percent)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

