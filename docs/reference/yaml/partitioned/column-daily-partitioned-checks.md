
## ColumnDailyPartitionedCheckCategoriesSpec  
Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](#columnnullsdailypartitionedchecksspec)|Daily partitioned checks of nulls in the column|[ColumnNullsDailyPartitionedChecksSpec](#columnnullsdailypartitionedchecksspec)| | | |
|[numeric](#columnnumericdailypartitionedchecksspec)|Daily partitioned checks of numeric in the column|[ColumnNumericDailyPartitionedChecksSpec](#columnnumericdailypartitionedchecksspec)| | | |
|[strings](#columnstringsdailypartitionedchecksspec)|Daily partitioned checks of strings in the column|[ColumnStringsDailyPartitionedChecksSpec](#columnstringsdailypartitionedchecksspec)| | | |
|[uniqueness](#columnuniquenessdailypartitionedchecksspec)|Daily partitioned checks of uniqueness in the column|[ColumnUniquenessDailyPartitionedChecksSpec](#columnuniquenessdailypartitionedchecksspec)| | | |
|[datetime](#columndatetimedailypartitionedchecksspec)|Daily partitioned checks of datetime in the column|[ColumnDatetimeDailyPartitionedChecksSpec](#columndatetimedailypartitionedchecksspec)| | | |
|[pii](#columnpiidailypartitionedchecksspec)|Daily partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyPartitionedChecksSpec](#columnpiidailypartitionedchecksspec)| | | |
|[sql](#columnsqldailypartitionedchecksspec)|Daily partitioned checks using custom SQL expressions evaluated on the column|[ColumnSqlDailyPartitionedChecksSpec](#columnsqldailypartitionedchecksspec)| | | |
|[bool](#columnbooldailypartitionedchecksspec)|Daily partitioned checks for booleans in the column|[ColumnBoolDailyPartitionedChecksSpec](#columnbooldailypartitionedchecksspec)| | | |
|[integrity](#columnintegritydailypartitionedchecksspec)|Daily partitioned checks for integrity in the column|[ColumnIntegrityDailyPartitionedChecksSpec](#columnintegritydailypartitionedchecksspec)| | | |
|[accuracy](#columnaccuracydailypartitionedchecksspec)|Daily partitioned checks for accuracy in the column|[ColumnAccuracyDailyPartitionedChecksSpec](#columnaccuracydailypartitionedchecksspec)| | | |
|[consistency](#columnconsistencydailypartitionedchecksspec)|Daily partitioned checks for consistency in the column|[ColumnConsistencyDailyPartitionedChecksSpec](#columnconsistencydailypartitionedchecksspec)| | | |
|[anomaly](#columnanomalydailypartitionedchecksspec)|Daily partitioned checks for anomaly in the column|[ColumnAnomalyDailyPartitionedChecksSpec](#columnanomalydailypartitionedchecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## ColumnNullsDailyPartitionedChecksSpec  
Container of nulls data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_nulls_count](#columnnullscountcheckspec)|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNullsCountCheckSpec](#columnnullscountcheckspec)| | | |
|[daily_partition_nulls_percent](#columnnullspercentcheckspec)|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNullsPercentCheckSpec](#columnnullspercentcheckspec)| | | |
|[daily_partition_not_nulls_count](#columnnotnullscountcheckspec)|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNotNullsCountCheckSpec](#columnnotnullscountcheckspec)| | | |
|[daily_partition_not_nulls_percent](#columnnotnullspercentcheckspec)|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNotNullsPercentCheckSpec](#columnnotnullspercentcheckspec)| | | |









___  

## ColumnNumericDailyPartitionedChecksSpec  
Container of numeric data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_negative_count](#columnnegativecountcheckspec)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNegativeCountCheckSpec](#columnnegativecountcheckspec)| | | |
|[daily_partition_negative_percent](#columnnegativepercentcheckspec)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNegativePercentCheckSpec](#columnnegativepercentcheckspec)| | | |
|[daily_partition_non_negative_count](#columnnonnegativecountcheckspec)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNonNegativeCountCheckSpec](#columnnonnegativecountcheckspec)| | | |
|[daily_partition_non_negative_percent](#columnnonnegativepercentcheckspec)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNonNegativePercentCheckSpec](#columnnonnegativepercentcheckspec)| | | |
|[daily_partition_expected_numbers_in_use_count](#columnexpectednumbersinusecountcheckspec)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnExpectedNumbersInUseCountCheckSpec](#columnexpectednumbersinusecountcheckspec)| | | |
|[daily_partition_number_value_in_set_percent](#columnnumbervalueinsetpercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNumberValueInSetPercentCheckSpec](#columnnumbervalueinsetpercentcheckspec)| | | |
|[daily_partition_values_in_range_numeric_percent](#columnvaluesinrangenumericpercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValuesInRangeNumericPercentCheckSpec](#columnvaluesinrangenumericpercentcheckspec)| | | |
|[daily_partition_values_in_range_integers_percent](#columnvaluesinrangeintegerspercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValuesInRangeIntegersPercentCheckSpec](#columnvaluesinrangeintegerspercentcheckspec)| | | |
|[daily_partition_value_below_min_value_count](#columnvaluebelowminvaluecountcheckspec)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueBelowMinValueCountCheckSpec](#columnvaluebelowminvaluecountcheckspec)| | | |
|[daily_partition_value_below_min_value_percent](#columnvaluebelowminvaluepercentcheckspec)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueBelowMinValuePercentCheckSpec](#columnvaluebelowminvaluepercentcheckspec)| | | |
|[daily_partition_value_above_max_value_count](#columnvalueabovemaxvaluecountcheckspec)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueAboveMaxValueCountCheckSpec](#columnvalueabovemaxvaluecountcheckspec)| | | |
|[daily_partition_value_above_max_value_percent](#columnvalueabovemaxvaluepercentcheckspec)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueAboveMaxValuePercentCheckSpec](#columnvalueabovemaxvaluepercentcheckspec)| | | |
|[daily_partition_max_in_range](#columnmaxinrangecheckspec)|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMaxInRangeCheckSpec](#columnmaxinrangecheckspec)| | | |
|[daily_partition_min_in_range](#columnmininrangecheckspec)|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMinInRangeCheckSpec](#columnmininrangecheckspec)| | | |
|[daily_partition_mean_in_range](#columnmeaninrangecheckspec)|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMeanInRangeCheckSpec](#columnmeaninrangecheckspec)| | | |
|[daily_partition_percentile_in_range](#columnpercentileinrangecheckspec)|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentileInRangeCheckSpec](#columnpercentileinrangecheckspec)| | | |
|[daily_partition_median_in_range](#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMedianInRangeCheckSpec](#columnmedianinrangecheckspec)| | | |
|[daily_partition_percentile_10_in_range](#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile10InRangeCheckSpec](#columnpercentile10inrangecheckspec)| | | |
|[daily_partition_percentile_25_in_range](#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile25InRangeCheckSpec](#columnpercentile25inrangecheckspec)| | | |
|[daily_partition_percentile_75_in_range](#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile75InRangeCheckSpec](#columnpercentile75inrangecheckspec)| | | |
|[daily_partition_percentile_90_in_range](#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile90InRangeCheckSpec](#columnpercentile90inrangecheckspec)| | | |
|[daily_partition_sample_stddev_in_range](#columnsamplestddevinrangecheckspec)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSampleStddevInRangeCheckSpec](#columnsamplestddevinrangecheckspec)| | | |
|[daily_partition_population_stddev_in_range](#columnpopulationstddevinrangecheckspec)|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPopulationStddevInRangeCheckSpec](#columnpopulationstddevinrangecheckspec)| | | |
|[daily_partition_sample_variance_in_range](#columnsamplevarianceinrangecheckspec)|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSampleVarianceInRangeCheckSpec](#columnsamplevarianceinrangecheckspec)| | | |
|[daily_partition_population_variance_in_range](#columnpopulationvarianceinrangecheckspec)|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPopulationVarianceInRangeCheckSpec](#columnpopulationvarianceinrangecheckspec)| | | |
|[daily_partition_sum_in_range](#columnsuminrangecheckspec)|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSumInRangeCheckSpec](#columnsuminrangecheckspec)| | | |
|[daily_partition_invalid_latitude_count](#columninvalidlatitudecountcheckspec)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnInvalidLatitudeCountCheckSpec](#columninvalidlatitudecountcheckspec)| | | |
|[daily_partition_valid_latitude_percent](#columnvalidlatitudepercentcheckspec)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValidLatitudePercentCheckSpec](#columnvalidlatitudepercentcheckspec)| | | |
|[daily_partition_invalid_longitude_count](#columninvalidlongitudecountcheckspec)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnInvalidLongitudeCountCheckSpec](#columninvalidlongitudecountcheckspec)| | | |
|[daily_partition_valid_longitude_percent](#columnvalidlongitudepercentcheckspec)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValidLongitudePercentCheckSpec](#columnvalidlongitudepercentcheckspec)| | | |









___  

## ColumnStringsDailyPartitionedChecksSpec  
Container of strings data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_string_max_length](#columnstringmaxlengthcheckspec)|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMaxLengthCheckSpec](#columnstringmaxlengthcheckspec)| | | |
|[daily_partition_string_min_length](#columnstringminlengthcheckspec)|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMinLengthCheckSpec](#columnstringminlengthcheckspec)| | | |
|[daily_partition_string_mean_length](#columnstringmeanlengthcheckspec)|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMeanLengthCheckSpec](#columnstringmeanlengthcheckspec)| | | |
|[daily_partition_string_length_below_min_length_count](#columnstringlengthbelowminlengthcountcheckspec)|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthBelowMinLengthCountCheckSpec](#columnstringlengthbelowminlengthcountcheckspec)| | | |
|[daily_partition_string_length_below_min_length_percent](#columnstringlengthbelowminlengthpercentcheckspec)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](#columnstringlengthbelowminlengthpercentcheckspec)| | | |
|[daily_partition_string_length_above_max_length_count](#columnstringlengthabovemaxlengthcountcheckspec)|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](#columnstringlengthabovemaxlengthcountcheckspec)| | | |
|[daily_partition_string_length_above_max_length_percent](#columnstringlengthabovemaxlengthpercentcheckspec)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](#columnstringlengthabovemaxlengthpercentcheckspec)| | | |
|[daily_partition_string_length_in_range_percent](#columnstringlengthinrangepercentcheckspec)|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthInRangePercentCheckSpec](#columnstringlengthinrangepercentcheckspec)| | | |
|[daily_partition_string_empty_count](#columnstringemptycountcheckspec)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringEmptyCountCheckSpec](#columnstringemptycountcheckspec)| | | |
|[daily_partition_string_empty_percent](#columnstringemptypercentcheckspec)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringEmptyPercentCheckSpec](#columnstringemptypercentcheckspec)| | | |
|[daily_partition_string_whitespace_count](#columnstringwhitespacecountcheckspec)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringWhitespaceCountCheckSpec](#columnstringwhitespacecountcheckspec)| | | |
|[daily_partition_string_whitespace_percent](#columnstringwhitespacepercentcheckspec)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringWhitespacePercentCheckSpec](#columnstringwhitespacepercentcheckspec)| | | |
|[daily_partition_string_surrounded_by_whitespace_count](#columnstringsurroundedbywhitespacecountcheckspec)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](#columnstringsurroundedbywhitespacecountcheckspec)| | | |
|[daily_partition_string_surrounded_by_whitespace_percent](#columnstringsurroundedbywhitespacepercentcheckspec)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](#columnstringsurroundedbywhitespacepercentcheckspec)| | | |
|[daily_partition_string_null_placeholder_count](#columnstringnullplaceholdercountcheckspec)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNullPlaceholderCountCheckSpec](#columnstringnullplaceholdercountcheckspec)| | | |
|[daily_partition_string_null_placeholder_percent](#columnstringnullplaceholderpercentcheckspec)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNullPlaceholderPercentCheckSpec](#columnstringnullplaceholderpercentcheckspec)| | | |
|[daily_partition_string_boolean_placeholder_percent](#columnstringbooleanplaceholderpercentcheckspec)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringBooleanPlaceholderPercentCheckSpec](#columnstringbooleanplaceholderpercentcheckspec)| | | |
|[daily_partition_string_parsable_to_integer_percent](#columnstringparsabletointegerpercentcheckspec)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringParsableToIntegerPercentCheckSpec](#columnstringparsabletointegerpercentcheckspec)| | | |
|[daily_partition_string_parsable_to_float_percent](#columnstringparsabletofloatpercentcheckspec)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringParsableToFloatPercentCheckSpec](#columnstringparsabletofloatpercentcheckspec)| | | |
|[daily_partition_expected_strings_in_use_count](#columnexpectedstringsinusecountcheckspec)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnExpectedStringsInUseCountCheckSpec](#columnexpectedstringsinusecountcheckspec)| | | |
|[daily_partition_string_value_in_set_percent](#columnstringvalueinsetpercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValueInSetPercentCheckSpec](#columnstringvalueinsetpercentcheckspec)| | | |
|[daily_partition_string_valid_dates_percent](#columnstringvaliddatespercentcheckspec)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidDatesPercentCheckSpec](#columnstringvaliddatespercentcheckspec)| | | |
|[daily_partition_string_valid_country_code_percent](#columnstringvalidcountrycodepercentcheckspec)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidCountryCodePercentCheckSpec](#columnstringvalidcountrycodepercentcheckspec)| | | |
|[daily_partition_string_valid_currency_code_percent](#columnstringvalidcurrencycodepercentcheckspec)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidCurrencyCodePercentCheckSpec](#columnstringvalidcurrencycodepercentcheckspec)| | | |
|[daily_partition_string_invalid_email_count](#columnstringinvalidemailcountcheckspec)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidEmailCountCheckSpec](#columnstringinvalidemailcountcheckspec)| | | |
|[daily_partition_string_invalid_uuid_count](#columnstringinvaliduuidcountcheckspec)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidUuidCountCheckSpec](#columnstringinvaliduuidcountcheckspec)| | | |
|[daily_partition_valid_uuid_percent](#columnstringvaliduuidpercentcheckspec)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidUuidPercentCheckSpec](#columnstringvaliduuidpercentcheckspec)| | | |
|[daily_partition_string_invalid_ip4_address_count](#columnstringinvalidip4addresscountcheckspec)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidIp4AddressCountCheckSpec](#columnstringinvalidip4addresscountcheckspec)| | | |
|[daily_partition_string_invalid_ip6_address_count](#columnstringinvalidip6addresscountcheckspec)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidIp6AddressCountCheckSpec](#columnstringinvalidip6addresscountcheckspec)| | | |
|[daily_partition_string_not_match_regex_count](#columnstringnotmatchregexcountcheckspec)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNotMatchRegexCountCheckSpec](#columnstringnotmatchregexcountcheckspec)| | | |
|[daily_partition_string_match_regex_percent](#columnstringmatchregexpercentcheckspec)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMatchRegexPercentCheckSpec](#columnstringmatchregexpercentcheckspec)| | | |
|[daily_partition_string_not_match_date_regex_count](#columnstringnotmatchdateregexcountcheckspec)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNotMatchDateRegexCountCheckSpec](#columnstringnotmatchdateregexcountcheckspec)| | | |
|[daily_partition_string_match_date_regex_percent](#columnstringmatchdateregexpercentcheckspec)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMatchDateRegexPercentCheckSpec](#columnstringmatchdateregexpercentcheckspec)| | | |
|[daily_partition_string_match_name_regex_percent](#columnstringmatchnameregexpercentcheckspec)|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMatchNameRegexPercentCheckSpec](#columnstringmatchnameregexpercentcheckspec)| | | |
|[daily_partition_expected_strings_in_top_values_count](#columnexpectedstringsintopvaluescountcheckspec)|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnExpectedStringsInTopValuesCountCheckSpec](#columnexpectedstringsintopvaluescountcheckspec)| | | |
|[daily_partition_string_datatype_detected](#columnstringdatatypedetectedcheckspec)|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringDatatypeDetectedCheckSpec](#columnstringdatatypedetectedcheckspec)| | | |









___  

## ColumnUniquenessDailyPartitionedChecksSpec  
Container of uniqueness data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_distinct_count](#columndistinctcountcheckspec)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDistinctCountCheckSpec](#columndistinctcountcheckspec)| | | |
|[daily_partition_distinct_percent](#columndistinctpercentcheckspec)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDistinctPercentCheckSpec](#columndistinctpercentcheckspec)| | | |
|[daily_partition_duplicate_count](#columnduplicatecountcheckspec)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDuplicateCountCheckSpec](#columnduplicatecountcheckspec)| | | |
|[daily_partition_duplicate_percent](#columnduplicatepercentcheckspec)|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDuplicatePercentCheckSpec](#columnduplicatepercentcheckspec)| | | |









___  

## ColumnDatetimeDailyPartitionedChecksSpec  
Container of date-time data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_date_values_in_future_percent](#columndatevaluesinfuturepercentcheckspec)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDateValuesInFuturePercentCheckSpec](#columndatevaluesinfuturepercentcheckspec)| | | |
|[daily_partition_datetime_value_in_range_date_percent](#columndatetimevalueinrangedatepercentcheckspec)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](#columndatetimevalueinrangedatepercentcheckspec)| | | |









___  

## ColumnPiiDailyPartitionedChecksSpec  
Container of PII data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_valid_usa_phone_percent](#columnpiivalidusaphonepercentcheckspec)|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiValidUsaPhonePercentCheckSpec](#columnpiivalidusaphonepercentcheckspec)| | | |
|[daily_partition_contains_usa_phone_percent](#columnpiicontainsusaphonepercentcheckspec)|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsUsaPhonePercentCheckSpec](#columnpiicontainsusaphonepercentcheckspec)| | | |
|[daily_partition_valid_usa_zipcode_percent](#columnpiivalidusazipcodepercentcheckspec)|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiValidUsaZipcodePercentCheckSpec](#columnpiivalidusazipcodepercentcheckspec)| | | |
|[daily_partition_contains_usa_zipcode_percent](#columnpiicontainsusazipcodepercentcheckspec)|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](#columnpiicontainsusazipcodepercentcheckspec)| | | |
|[daily_partition_valid_email_percent](#columnpiivalidemailpercentcheckspec)|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiValidEmailPercentCheckSpec](#columnpiivalidemailpercentcheckspec)| | | |
|[daily_partition_contains_email_percent](#columnpiicontainsemailpercentcheckspec)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsEmailPercentCheckSpec](#columnpiicontainsemailpercentcheckspec)| | | |
|[daily_partition_valid_ip4_address_percent](#columnpiivalidip4addresspercentcheckspec)|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiValidIp4AddressPercentCheckSpec](#columnpiivalidip4addresspercentcheckspec)| | | |
|[daily_partition_contains_ip4_percent](#columnpiicontainsip4percentcheckspec)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsIp4PercentCheckSpec](#columnpiicontainsip4percentcheckspec)| | | |
|[daily_partition_valid_ip6_address_percent](#columnpiivalidip6addresspercentcheckspec)|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiValidIp6AddressPercentCheckSpec](#columnpiivalidip6addresspercentcheckspec)| | | |
|[daily_partition_contains_ip6_percent](#columnpiicontainsip6percentcheckspec)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsIp6PercentCheckSpec](#columnpiicontainsip6percentcheckspec)| | | |









___  

## ColumnSqlDailyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_sql_condition_passed_percent_on_column](#columnsqlconditionpassedpercentcheckspec)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlConditionPassedPercentCheckSpec](#columnsqlconditionpassedpercentcheckspec)| | | |
|[daily_partition_sql_condition_failed_count_on_column](#columnsqlconditionfailedcountcheckspec)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlConditionFailedCountCheckSpec](#columnsqlconditionfailedcountcheckspec)| | | |
|[daily_partition_sql_aggregate_expr_column](#columnsqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlAggregateExprCheckSpec](#columnsqlaggregateexprcheckspec)| | | |









___  

## ColumnBoolDailyPartitionedChecksSpec  
Container of boolean data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_true_percent](#columntruepercentcheckspec)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnTruePercentCheckSpec](#columntruepercentcheckspec)| | | |
|[daily_partition_false_percent](#columnfalsepercentcheckspec)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnFalsePercentCheckSpec](#columnfalsepercentcheckspec)| | | |









___  

## ColumnIntegrityDailyPartitionedChecksSpec  
Container of integrity data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_foreign_key_not_match_count](#columnintegrityforeignkeynotmatchcountcheckspec)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](#columnintegrityforeignkeynotmatchcountcheckspec)| | | |
|[daily_partition_foreign_key_match_percent](#columnintegrityforeignkeymatchpercentcheckspec)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](#columnintegrityforeignkeymatchpercentcheckspec)| | | |









___  

## ColumnAccuracyDailyPartitionedChecksSpec  
Container of accuracy data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___  

## ColumnConsistencyDailyPartitionedChecksSpec  
Container of consistency data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_date_match_format_percent](#columnconsistencydatematchformatpercentcheckspec)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnConsistencyDateMatchFormatPercentCheckSpec](#columnconsistencydatematchformatpercentcheckspec)| | | |
|[daily_partition_string_datatype_changed](#columnstringdatatypechangedcheckspec)|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringDatatypeChangedCheckSpec](#columnstringdatatypechangedcheckspec)| | | |









___  

## ColumnAnomalyDailyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_mean_anomaly_7_days](#columnanomalymean7dayscheckspec)|Verifies that the mean value in a column is within a percentile from measurements made during the last 7 days.|[ColumnAnomalyMean7DaysCheckSpec](#columnanomalymean7dayscheckspec)| | | |
|[daily_partition_mean_anomaly_30_days](#columnanomalymean30dayscheckspec)|Verifies that the mean value in a column is within a percentile from measurements made during the last 30 days.|[ColumnAnomalyMean30DaysCheckSpec](#columnanomalymean30dayscheckspec)| | | |
|[daily_partition_mean_anomaly_60_days](#columnanomalymean60dayscheckspec)|Verifies that the mean value in a column is within a percentile from measurements made during the last 60 days.|[ColumnAnomalyMean60DaysCheckSpec](#columnanomalymean60dayscheckspec)| | | |
|[daily_partition_median_anomaly_7_days](#columnanomalymedian7dayscheckspec)|Verifies that the median in a column is within a percentile from measurements made during the last 7 days.|[ColumnAnomalyMedian7DaysCheckSpec](#columnanomalymedian7dayscheckspec)| | | |
|[daily_partition_median_anomaly_30_days](#columnanomalymedian30dayscheckspec)|Verifies that the median in a column is within a percentile from measurements made during the last 30 days.|[ColumnAnomalyMedian30DaysCheckSpec](#columnanomalymedian30dayscheckspec)| | | |
|[daily_partition_median_anomaly_60_days](#columnanomalymedian60dayscheckspec)|Verifies that the median in a column is within a percentile from measurements made during the last 60 days.|[ColumnAnomalyMedian60DaysCheckSpec](#columnanomalymedian60dayscheckspec)| | | |
|[daily_partition_sum_anomaly_7_days](#columnanomalysum7dayscheckspec)|Verifies that the sum in a column is within a percentile from measurements made during the last 7 days.|[ColumnAnomalySum7DaysCheckSpec](#columnanomalysum7dayscheckspec)| | | |
|[daily_partition_sum_anomaly_30_days](#columnanomalysum30dayscheckspec)|Verifies that the sum in a column is within a percentile from measurements made during the last 30 days.|[ColumnAnomalySum30DaysCheckSpec](#columnanomalysum30dayscheckspec)| | | |
|[daily_partition_sum_anomaly_60_days](#columnanomalysum60dayscheckspec)|Verifies that the sum in a column is within a percentile from measurements made during the last 60 days.|[ColumnAnomalySum60DaysCheckSpec](#columnanomalysum60dayscheckspec)| | | |
|[daily_partition_mean_change](#columnchangemeancheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](#columnchangemeancheckspec)| | | |
|[daily_partition_mean_change_yesterday](#columnchangemeansinceyesterdaycheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMeanSinceYesterdayCheckSpec](#columnchangemeansinceyesterdaycheckspec)| | | |
|[daily_partition_mean_change_7_days](#columnchangemeansince7dayscheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMeanSince7DaysCheckSpec](#columnchangemeansince7dayscheckspec)| | | |
|[daily_partition_mean_change_30_days](#columnchangemeansince30dayscheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMeanSince30DaysCheckSpec](#columnchangemeansince30dayscheckspec)| | | |
|[daily_partition_median_change](#columnchangemediancheckspec)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](#columnchangemediancheckspec)| | | |
|[daily_partition_median_change_yesterday](#columnchangemediansinceyesterdaycheckspec)|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMedianSinceYesterdayCheckSpec](#columnchangemediansinceyesterdaycheckspec)| | | |
|[daily_partition_median_change_7_days](#columnchangemediansince7dayscheckspec)|Verifies that the median in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMedianSince7DaysCheckSpec](#columnchangemediansince7dayscheckspec)| | | |
|[daily_partition_median_change_30_days](#columnchangemediansince30dayscheckspec)|Verifies that the median in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMedianSince30DaysCheckSpec](#columnchangemediansince30dayscheckspec)| | | |
|[daily_partition_sum_change](#columnchangesumcheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](#columnchangesumcheckspec)| | | |
|[daily_partition_sum_change_yesterday](#columnchangesumsinceyesterdaycheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeSumSinceYesterdayCheckSpec](#columnchangesumsinceyesterdaycheckspec)| | | |
|[daily_partition_sum_change_7_days](#columnchangesumsince7dayscheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|[ColumnChangeSumSince7DaysCheckSpec](#columnchangesumsince7dayscheckspec)| | | |
|[daily_partition_sum_change_30_days](#columnchangesumsince30dayscheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|[ColumnChangeSumSince30DaysCheckSpec](#columnchangesumsince30dayscheckspec)| | | |









___  

## ColumnAnomalyMean7DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column is within a two-tailed percentile from measurements made during the last 7 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
|[warning](#percentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[PercentileMovingWithin7DaysRuleParametersSpec](#percentilemovingwithin7daysruleparametersspec)| | | |
|[error](#percentilemovingwithin7daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[PercentileMovingWithin7DaysRuleParametersSpec](#percentilemovingwithin7daysruleparametersspec)| | | |
|[fatal](#percentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[PercentileMovingWithin7DaysRuleParametersSpec](#percentilemovingwithin7daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalyMean30DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column is within a two-tailed percentile from measurements made during the last 30 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
|[warning](#percentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[PercentileMovingWithin30DaysRuleParametersSpec](#percentilemovingwithin30daysruleparametersspec)| | | |
|[error](#percentilemovingwithin30daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[PercentileMovingWithin30DaysRuleParametersSpec](#percentilemovingwithin30daysruleparametersspec)| | | |
|[fatal](#percentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[PercentileMovingWithin30DaysRuleParametersSpec](#percentilemovingwithin30daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalyMean60DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column is within a two-tailed percentile from measurements made during the last 60 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
|[warning](#percentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[PercentileMovingWithin60DaysRuleParametersSpec](#percentilemovingwithin60daysruleparametersspec)| | | |
|[error](#percentilemovingwithin60daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[PercentileMovingWithin60DaysRuleParametersSpec](#percentilemovingwithin60daysruleparametersspec)| | | |
|[fatal](#percentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[PercentileMovingWithin60DaysRuleParametersSpec](#percentilemovingwithin60daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalyMedian7DaysCheckSpec  
Column level check that ensures that the median in a monitored column is within a two-tailed percentile from measurements made during the last 7 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
|[warning](#percentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[PercentileMovingWithin7DaysRuleParametersSpec](#percentilemovingwithin7daysruleparametersspec)| | | |
|[error](#percentilemovingwithin7daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[PercentileMovingWithin7DaysRuleParametersSpec](#percentilemovingwithin7daysruleparametersspec)| | | |
|[fatal](#percentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[PercentileMovingWithin7DaysRuleParametersSpec](#percentilemovingwithin7daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalyMedian30DaysCheckSpec  
Column level check that ensures that the median in a monitored column is within a two-tailed percentile from measurements made during the last 30 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
|[warning](#percentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[PercentileMovingWithin30DaysRuleParametersSpec](#percentilemovingwithin30daysruleparametersspec)| | | |
|[error](#percentilemovingwithin30daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[PercentileMovingWithin30DaysRuleParametersSpec](#percentilemovingwithin30daysruleparametersspec)| | | |
|[fatal](#percentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[PercentileMovingWithin30DaysRuleParametersSpec](#percentilemovingwithin30daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalyMedian60DaysCheckSpec  
Column level check that ensures that the median in a monitored column is within a two-tailed percentile from measurements made during the last 60 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
|[warning](#percentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[PercentileMovingWithin60DaysRuleParametersSpec](#percentilemovingwithin60daysruleparametersspec)| | | |
|[error](#percentilemovingwithin60daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[PercentileMovingWithin60DaysRuleParametersSpec](#percentilemovingwithin60daysruleparametersspec)| | | |
|[fatal](#percentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[PercentileMovingWithin60DaysRuleParametersSpec](#percentilemovingwithin60daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalySum7DaysCheckSpec  
Column level check that ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 7 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
|[warning](#percentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[PercentileMovingWithin7DaysRuleParametersSpec](#percentilemovingwithin7daysruleparametersspec)| | | |
|[error](#percentilemovingwithin7daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[PercentileMovingWithin7DaysRuleParametersSpec](#percentilemovingwithin7daysruleparametersspec)| | | |
|[fatal](#percentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[PercentileMovingWithin7DaysRuleParametersSpec](#percentilemovingwithin7daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalySum30DaysCheckSpec  
Column level check that ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 30 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
|[warning](#percentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[PercentileMovingWithin30DaysRuleParametersSpec](#percentilemovingwithin30daysruleparametersspec)| | | |
|[error](#percentilemovingwithin30daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[PercentileMovingWithin30DaysRuleParametersSpec](#percentilemovingwithin30daysruleparametersspec)| | | |
|[fatal](#percentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[PercentileMovingWithin30DaysRuleParametersSpec](#percentilemovingwithin30daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalySum60DaysCheckSpec  
Column level check that ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 60 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
|[warning](#percentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[PercentileMovingWithin60DaysRuleParametersSpec](#percentilemovingwithin60daysruleparametersspec)| | | |
|[error](#percentilemovingwithin60daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[PercentileMovingWithin60DaysRuleParametersSpec](#percentilemovingwithin60daysruleparametersspec)| | | |
|[fatal](#percentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[PercentileMovingWithin60DaysRuleParametersSpec](#percentilemovingwithin60daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

