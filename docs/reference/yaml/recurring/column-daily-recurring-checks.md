
## ColumnDailyRecurringCheckCategoriesSpec  
Container of column level daily recurring checks. Contains categories of daily recurring checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](#columnnullsdailyrecurringchecksspec)|Daily recurring checks of nulls in the column|[ColumnNullsDailyRecurringChecksSpec](#columnnullsdailyrecurringchecksspec)| | | |
|[numeric](#columnnumericdailyrecurringchecksspec)|Daily recurring checks of numeric in the column|[ColumnNumericDailyRecurringChecksSpec](#columnnumericdailyrecurringchecksspec)| | | |
|[strings](#columnstringsdailyrecurringchecksspec)|Daily recurring checks of strings in the column|[ColumnStringsDailyRecurringChecksSpec](#columnstringsdailyrecurringchecksspec)| | | |
|[uniqueness](#columnuniquenessdailyrecurringchecksspec)|Daily recurring checks of uniqueness in the column|[ColumnUniquenessDailyRecurringChecksSpec](#columnuniquenessdailyrecurringchecksspec)| | | |
|[datetime](#columndatetimedailyrecurringchecksspec)|Daily recurring checks of datetime in the column|[ColumnDatetimeDailyRecurringChecksSpec](#columndatetimedailyrecurringchecksspec)| | | |
|[pii](#columnpiidailyrecurringchecksspec)|Daily recurring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyRecurringChecksSpec](#columnpiidailyrecurringchecksspec)| | | |
|[sql](#columnsqldailyrecurringchecksspec)|Daily recurring checks of custom SQL checks in the column|[ColumnSqlDailyRecurringChecksSpec](#columnsqldailyrecurringchecksspec)| | | |
|[bool](#columnbooldailyrecurringchecksspec)|Daily recurring checks of booleans in the column|[ColumnBoolDailyRecurringChecksSpec](#columnbooldailyrecurringchecksspec)| | | |
|[integrity](#columnintegritydailyrecurringchecksspec)|Daily recurring checks of integrity in the column|[ColumnIntegrityDailyRecurringChecksSpec](#columnintegritydailyrecurringchecksspec)| | | |
|[accuracy](#columnaccuracydailyrecurringchecksspec)|Daily recurring checks of accuracy in the column|[ColumnAccuracyDailyRecurringChecksSpec](#columnaccuracydailyrecurringchecksspec)| | | |
|[consistency](#columnconsistencydailyrecurringchecksspec)|Daily recurring checks of consistency in the column|[ColumnConsistencyDailyRecurringChecksSpec](#columnconsistencydailyrecurringchecksspec)| | | |
|[anomaly](#columnanomalydailyrecurringchecksspec)|Daily recurring checks of anomaly in the column|[ColumnAnomalyDailyRecurringChecksSpec](#columnanomalydailyrecurringchecksspec)| | | |
|[schema](#columnschemadailyrecurringchecksspec)|Daily recurring column schema checks|[ColumnSchemaDailyRecurringChecksSpec](#columnschemadailyrecurringchecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## ColumnNullsDailyRecurringChecksSpec  
Container of nulls data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_nulls_count](#columnnullscountcheckspec)|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNullsCountCheckSpec](#columnnullscountcheckspec)| | | |
|[daily_nulls_percent](#columnnullspercentcheckspec)|Verifies that the percentage of nulls in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNullsPercentCheckSpec](#columnnullspercentcheckspec)| | | |
|[daily_not_nulls_count](#columnnotnullscountcheckspec)|Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNotNullsCountCheckSpec](#columnnotnullscountcheckspec)| | | |
|[daily_not_nulls_percent](#columnnotnullspercentcheckspec)|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNotNullsPercentCheckSpec](#columnnotnullspercentcheckspec)| | | |









___  

## ColumnNumericDailyRecurringChecksSpec  
Container of built-in preconfigured data quality recurring on a column level that are checking numeric values at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_negative_count](#columnnegativecountcheckspec)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNegativeCountCheckSpec](#columnnegativecountcheckspec)| | | |
|[daily_negative_percent](#columnnegativepercentcheckspec)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNegativePercentCheckSpec](#columnnegativepercentcheckspec)| | | |
|[daily_non_negative_count](#columnnonnegativecountcheckspec)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNonNegativeCountCheckSpec](#columnnonnegativecountcheckspec)| | | |
|[daily_non_negative_percent](#columnnonnegativepercentcheckspec)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNonNegativePercentCheckSpec](#columnnonnegativepercentcheckspec)| | | |
|[daily_expected_numbers_in_use_count](#columnexpectednumbersinusecountcheckspec)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnExpectedNumbersInUseCountCheckSpec](#columnexpectednumbersinusecountcheckspec)| | | |
|[daily_number_value_in_set_percent](#columnnumbervalueinsetpercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNumberValueInSetPercentCheckSpec](#columnnumbervalueinsetpercentcheckspec)| | | |
|[daily_values_in_range_numeric_percent](#columnvaluesinrangenumericpercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValuesInRangeNumericPercentCheckSpec](#columnvaluesinrangenumericpercentcheckspec)| | | |
|[daily_values_in_range_integers_percent](#columnvaluesinrangeintegerspercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValuesInRangeIntegersPercentCheckSpec](#columnvaluesinrangeintegerspercentcheckspec)| | | |
|[daily_value_below_min_value_count](#columnvaluebelowminvaluecountcheckspec)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValueBelowMinValueCountCheckSpec](#columnvaluebelowminvaluecountcheckspec)| | | |
|[daily_value_below_min_value_percent](#columnvaluebelowminvaluepercentcheckspec)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValueBelowMinValuePercentCheckSpec](#columnvaluebelowminvaluepercentcheckspec)| | | |
|[daily_value_above_max_value_count](#columnvalueabovemaxvaluecountcheckspec)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValueAboveMaxValueCountCheckSpec](#columnvalueabovemaxvaluecountcheckspec)| | | |
|[daily_value_above_max_value_percent](#columnvalueabovemaxvaluepercentcheckspec)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValueAboveMaxValuePercentCheckSpec](#columnvalueabovemaxvaluepercentcheckspec)| | | |
|[daily_max_in_range](#columnmaxinrangecheckspec)|Verifies that the maximal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnMaxInRangeCheckSpec](#columnmaxinrangecheckspec)| | | |
|[daily_min_in_range](#columnmininrangecheckspec)|Verifies that the minimal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnMinInRangeCheckSpec](#columnmininrangecheckspec)| | | |
|[daily_mean_in_range](#columnmeaninrangecheckspec)|Verifies that the average (mean) of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnMeanInRangeCheckSpec](#columnmeaninrangecheckspec)| | | |
|[daily_percentile_in_range](#columnpercentileinrangecheckspec)|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentileInRangeCheckSpec](#columnpercentileinrangecheckspec)| | | |
|[daily_median_in_range](#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMedianInRangeCheckSpec](#columnmedianinrangecheckspec)| | | |
|[daily_percentile_10_in_range](#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentile10InRangeCheckSpec](#columnpercentile10inrangecheckspec)| | | |
|[daily_percentile_25_in_range](#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentile25InRangeCheckSpec](#columnpercentile25inrangecheckspec)| | | |
|[daily_percentile_75_in_range](#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentile75InRangeCheckSpec](#columnpercentile75inrangecheckspec)| | | |
|[daily_percentile_90_in_range](#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentile90InRangeCheckSpec](#columnpercentile90inrangecheckspec)| | | |
|[daily_sample_stddev_in_range](#columnsamplestddevinrangecheckspec)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSampleStddevInRangeCheckSpec](#columnsamplestddevinrangecheckspec)| | | |
|[daily_population_stddev_in_range](#columnpopulationstddevinrangecheckspec)|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPopulationStddevInRangeCheckSpec](#columnpopulationstddevinrangecheckspec)| | | |
|[daily_sample_variance_in_range](#columnsamplevarianceinrangecheckspec)|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSampleVarianceInRangeCheckSpec](#columnsamplevarianceinrangecheckspec)| | | |
|[daily_population_variance_in_range](#columnpopulationvarianceinrangecheckspec)|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPopulationVarianceInRangeCheckSpec](#columnpopulationvarianceinrangecheckspec)| | | |
|[daily_sum_in_range](#columnsuminrangecheckspec)|Verifies that the sum of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSumInRangeCheckSpec](#columnsuminrangecheckspec)| | | |
|[daily_invalid_latitude_count](#columninvalidlatitudecountcheckspec)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnInvalidLatitudeCountCheckSpec](#columninvalidlatitudecountcheckspec)| | | |
|[daily_valid_latitude_percent](#columnvalidlatitudepercentcheckspec)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValidLatitudePercentCheckSpec](#columnvalidlatitudepercentcheckspec)| | | |
|[daily_invalid_longitude_count](#columninvalidlongitudecountcheckspec)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnInvalidLongitudeCountCheckSpec](#columninvalidlongitudecountcheckspec)| | | |
|[daily_valid_longitude_percent](#columnvalidlongitudepercentcheckspec)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValidLongitudePercentCheckSpec](#columnvalidlongitudepercentcheckspec)| | | |









___  

## ColumnStringsDailyRecurringChecksSpec  
Container of strings data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_string_max_length](#columnstringmaxlengthcheckspec)|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMaxLengthCheckSpec](#columnstringmaxlengthcheckspec)| | | |
|[daily_string_min_length](#columnstringminlengthcheckspec)|Verifies that the length of string in a column does not fall below the minimum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMinLengthCheckSpec](#columnstringminlengthcheckspec)| | | |
|[daily_string_mean_length](#columnstringmeanlengthcheckspec)|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMeanLengthCheckSpec](#columnstringmeanlengthcheckspec)| | | |
|[daily_string_length_below_min_length_count](#columnstringlengthbelowminlengthcountcheckspec)|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthCountCheckSpec](#columnstringlengthbelowminlengthcountcheckspec)| | | |
|[daily_string_length_below_min_length_percent](#columnstringlengthbelowminlengthpercentcheckspec)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](#columnstringlengthbelowminlengthpercentcheckspec)| | | |
|[daily_string_length_above_max_length_count](#columnstringlengthabovemaxlengthcountcheckspec)|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](#columnstringlengthabovemaxlengthcountcheckspec)| | | |
|[daily_string_length_above_max_length_percent](#columnstringlengthabovemaxlengthpercentcheckspec)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](#columnstringlengthabovemaxlengthpercentcheckspec)| | | |
|[daily_string_length_in_range_percent](#columnstringlengthinrangepercentcheckspec)|The check counts the percentage of those strings with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthInRangePercentCheckSpec](#columnstringlengthinrangepercentcheckspec)| | | |
|[daily_string_empty_count](#columnstringemptycountcheckspec)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringEmptyCountCheckSpec](#columnstringemptycountcheckspec)| | | |
|[daily_string_empty_percent](#columnstringemptypercentcheckspec)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringEmptyPercentCheckSpec](#columnstringemptypercentcheckspec)| | | |
|[daily_string_whitespace_count](#columnstringwhitespacecountcheckspec)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringWhitespaceCountCheckSpec](#columnstringwhitespacecountcheckspec)| | | |
|[daily_string_whitespace_percent](#columnstringwhitespacepercentcheckspec)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringWhitespacePercentCheckSpec](#columnstringwhitespacepercentcheckspec)| | | |
|[daily_string_surrounded_by_whitespace_count](#columnstringsurroundedbywhitespacecountcheckspec)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](#columnstringsurroundedbywhitespacecountcheckspec)| | | |
|[daily_string_surrounded_by_whitespace_percent](#columnstringsurroundedbywhitespacepercentcheckspec)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](#columnstringsurroundedbywhitespacepercentcheckspec)| | | |
|[daily_string_null_placeholder_count](#columnstringnullplaceholdercountcheckspec)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringNullPlaceholderCountCheckSpec](#columnstringnullplaceholdercountcheckspec)| | | |
|[daily_string_null_placeholder_percent](#columnstringnullplaceholderpercentcheckspec)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringNullPlaceholderPercentCheckSpec](#columnstringnullplaceholderpercentcheckspec)| | | |
|[daily_string_boolean_placeholder_percent](#columnstringbooleanplaceholderpercentcheckspec)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringBooleanPlaceholderPercentCheckSpec](#columnstringbooleanplaceholderpercentcheckspec)| | | |
|[daily_string_parsable_to_integer_percent](#columnstringparsabletointegerpercentcheckspec)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringParsableToIntegerPercentCheckSpec](#columnstringparsabletointegerpercentcheckspec)| | | |
|[daily_string_parsable_to_float_percent](#columnstringparsabletofloatpercentcheckspec)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringParsableToFloatPercentCheckSpec](#columnstringparsabletofloatpercentcheckspec)| | | |
|[daily_expected_strings_in_use_count](#columnexpectedstringsinusecountcheckspec)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnExpectedStringsInUseCountCheckSpec](#columnexpectedstringsinusecountcheckspec)| | | |
|[daily_string_value_in_set_percent](#columnstringvalueinsetpercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValueInSetPercentCheckSpec](#columnstringvalueinsetpercentcheckspec)| | | |
|[daily_string_valid_dates_percent](#columnstringvaliddatespercentcheckspec)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValidDatesPercentCheckSpec](#columnstringvaliddatespercentcheckspec)| | | |
|[daily_string_valid_country_code_percent](#columnstringvalidcountrycodepercentcheckspec)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValidCountryCodePercentCheckSpec](#columnstringvalidcountrycodepercentcheckspec)| | | |
|[daily_string_valid_currency_code_percent](#columnstringvalidcurrencycodepercentcheckspec)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValidCurrencyCodePercentCheckSpec](#columnstringvalidcurrencycodepercentcheckspec)| | | |
|[daily_string_invalid_email_count](#columnstringinvalidemailcountcheckspec)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringInvalidEmailCountCheckSpec](#columnstringinvalidemailcountcheckspec)| | | |
|[daily_string_invalid_uuid_count](#columnstringinvaliduuidcountcheckspec)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringInvalidUuidCountCheckSpec](#columnstringinvaliduuidcountcheckspec)| | | |
|[daily_string_valid_uuid_percent](#columnstringvaliduuidpercentcheckspec)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValidUuidPercentCheckSpec](#columnstringvaliduuidpercentcheckspec)| | | |
|[daily_string_invalid_ip4_address_count](#columnstringinvalidip4addresscountcheckspec)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringInvalidIp4AddressCountCheckSpec](#columnstringinvalidip4addresscountcheckspec)| | | |
|[daily_string_invalid_ip6_address_count](#columnstringinvalidip6addresscountcheckspec)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringInvalidIp6AddressCountCheckSpec](#columnstringinvalidip6addresscountcheckspec)| | | |
|[daily_string_not_match_regex_count](#columnstringnotmatchregexcountcheckspec)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringNotMatchRegexCountCheckSpec](#columnstringnotmatchregexcountcheckspec)| | | |
|[daily_string_match_regex_percent](#columnstringmatchregexpercentcheckspec)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMatchRegexPercentCheckSpec](#columnstringmatchregexpercentcheckspec)| | | |
|[daily_string_not_match_date_regex_count](#columnstringnotmatchdateregexcountcheckspec)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringNotMatchDateRegexCountCheckSpec](#columnstringnotmatchdateregexcountcheckspec)| | | |
|[daily_string_match_date_regex_percent](#columnstringmatchdateregexpercentcheckspec)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMatchDateRegexPercentCheckSpec](#columnstringmatchdateregexpercentcheckspec)| | | |
|[daily_string_match_name_regex_percent](#columnstringmatchnameregexpercentcheckspec)|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMatchNameRegexPercentCheckSpec](#columnstringmatchnameregexpercentcheckspec)| | | |
|[daily_expected_strings_in_top_values_count](#columnexpectedstringsintopvaluescountcheckspec)|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnExpectedStringsInTopValuesCountCheckSpec](#columnexpectedstringsintopvaluescountcheckspec)| | | |
|[daily_string_datatype_detected](#columnstringdatatypedetectedcheckspec)|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringDatatypeDetectedCheckSpec](#columnstringdatatypedetectedcheckspec)| | | |









___  

## ColumnUniquenessDailyRecurringChecksSpec  
Container of uniqueness data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_distinct_count](#columndistinctcountcheckspec)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDistinctCountCheckSpec](#columndistinctcountcheckspec)| | | |
|[daily_distinct_percent](#columndistinctpercentcheckspec)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDistinctPercentCheckSpec](#columndistinctpercentcheckspec)| | | |
|[daily_duplicate_count](#columnduplicatecountcheckspec)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDuplicateCountCheckSpec](#columnduplicatecountcheckspec)| | | |
|[daily_duplicate_percent](#columnduplicatepercentcheckspec)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDuplicatePercentCheckSpec](#columnduplicatepercentcheckspec)| | | |









___  

## ColumnDatetimeDailyRecurringChecksSpec  
Container of date-time data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_date_values_in_future_percent](#columndatevaluesinfuturepercentcheckspec)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDateValuesInFuturePercentCheckSpec](#columndatevaluesinfuturepercentcheckspec)| | | |
|[daily_datetime_value_in_range_date_percent](#columndatetimevalueinrangedatepercentcheckspec)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](#columndatetimevalueinrangedatepercentcheckspec)| | | |









___  

## ColumnPiiDailyRecurringChecksSpec  
Container of PII data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_valid_usa_phone_percent](#columnpiivalidusaphonepercentcheckspec)|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiValidUsaPhonePercentCheckSpec](#columnpiivalidusaphonepercentcheckspec)| | | |
|[daily_contains_usa_phone_percent](#columnpiicontainsusaphonepercentcheckspec)|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsUsaPhonePercentCheckSpec](#columnpiicontainsusaphonepercentcheckspec)| | | |
|[daily_valid_usa_zipcode_percent](#columnpiivalidusazipcodepercentcheckspec)|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiValidUsaZipcodePercentCheckSpec](#columnpiivalidusazipcodepercentcheckspec)| | | |
|[daily_contains_usa_zipcode_percent](#columnpiicontainsusazipcodepercentcheckspec)|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](#columnpiicontainsusazipcodepercentcheckspec)| | | |
|[daily_valid_email_percent](#columnpiivalidemailpercentcheckspec)|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiValidEmailPercentCheckSpec](#columnpiivalidemailpercentcheckspec)| | | |
|[daily_contains_email_percent](#columnpiicontainsemailpercentcheckspec)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsEmailPercentCheckSpec](#columnpiicontainsemailpercentcheckspec)| | | |
|[daily_valid_ip4_address_percent](#columnpiivalidip4addresspercentcheckspec)|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiValidIp4AddressPercentCheckSpec](#columnpiivalidip4addresspercentcheckspec)| | | |
|[daily_contains_ip4_percent](#columnpiicontainsip4percentcheckspec)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsIp4PercentCheckSpec](#columnpiicontainsip4percentcheckspec)| | | |
|[daily_valid_ip6_address_percent](#columnpiivalidip6addresspercentcheckspec)|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiValidIp6AddressPercentCheckSpec](#columnpiivalidip6addresspercentcheckspec)| | | |
|[daily_contains_ip6_percent](#columnpiicontainsip6percentcheckspec)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsIp6PercentCheckSpec](#columnpiicontainsip6percentcheckspec)| | | |









___  

## ColumnSqlDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_sql_condition_passed_percent_on_column](#columnsqlconditionpassedpercentcheckspec)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSqlConditionPassedPercentCheckSpec](#columnsqlconditionpassedpercentcheckspec)| | | |
|[daily_sql_condition_failed_count_on_column](#columnsqlconditionfailedcountcheckspec)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSqlConditionFailedCountCheckSpec](#columnsqlconditionfailedcountcheckspec)| | | |
|[daily_sql_aggregate_expr_column](#columnsqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSqlAggregateExprCheckSpec](#columnsqlaggregateexprcheckspec)| | | |









___  

## ColumnBoolDailyRecurringChecksSpec  
Container of boolean data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_true_percent](#columntruepercentcheckspec)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnTruePercentCheckSpec](#columntruepercentcheckspec)| | | |
|[daily_false_percent](#columnfalsepercentcheckspec)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnFalsePercentCheckSpec](#columnfalsepercentcheckspec)| | | |









___  

## ColumnIntegrityDailyRecurringChecksSpec  
Container of integrity data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_foreign_key_not_match_count](#columnintegrityforeignkeynotmatchcountcheckspec)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](#columnintegrityforeignkeynotmatchcountcheckspec)| | | |
|[daily_foreign_key_match_percent](#columnintegrityforeignkeymatchpercentcheckspec)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](#columnintegrityforeignkeymatchpercentcheckspec)| | | |









___  

## ColumnAccuracyDailyRecurringChecksSpec  
Container of accuracy data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_total_sum_match_percent](#columnaccuracytotalsummatchpercentcheckspec)|Verifies that the percentage of difference in sum of a column in a table and sum of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyTotalSumMatchPercentCheckSpec](#columnaccuracytotalsummatchpercentcheckspec)| | | |
|[daily_min_match_percent](#columnaccuracyminmatchpercentcheckspec)|Verifies that the percentage of difference in min of a column in a table and min of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyMinMatchPercentCheckSpec](#columnaccuracyminmatchpercentcheckspec)| | | |
|[daily_max_match_percent](#columnaccuracymaxmatchpercentcheckspec)|Verifies that the percentage of difference in max of a column in a table and max of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyMaxMatchPercentCheckSpec](#columnaccuracymaxmatchpercentcheckspec)| | | |
|[daily_average_match_percent](#columnaccuracyaveragematchpercentcheckspec)|Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyAverageMatchPercentCheckSpec](#columnaccuracyaveragematchpercentcheckspec)| | | |
|[daily_not_null_count_match_percent](#columnaccuracynotnullcountmatchpercentcheckspec)|Verifies that the percentage of difference in row count of a column in a table and row count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyNotNullCountMatchPercentCheckSpec](#columnaccuracynotnullcountmatchpercentcheckspec)| | | |









___  

## ColumnConsistencyDailyRecurringChecksSpec  
Container of consistency data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_date_match_format_percent](#columnconsistencydatematchformatpercentcheckspec)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily recurring.|[ColumnConsistencyDateMatchFormatPercentCheckSpec](#columnconsistencydatematchformatpercentcheckspec)| | | |
|[daily_string_datatype_changed](#columnstringdatatypechangedcheckspec)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringDatatypeChangedCheckSpec](#columnstringdatatypechangedcheckspec)| | | |









___  

## ColumnAnomalyDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_mean_anomaly_7_days](#columnanomalymeanchange7dayscheckspec)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 7 days.|[ColumnAnomalyMeanChange7DaysCheckSpec](#columnanomalymeanchange7dayscheckspec)| | | |
|[daily_mean_anomaly_30_days](#columnanomalymeanchange30dayscheckspec)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyMeanChange30DaysCheckSpec](#columnanomalymeanchange30dayscheckspec)| | | |
|[daily_mean_anomaly_60_days](#columnanomalymeanchange60dayscheckspec)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 60 days.|[ColumnAnomalyMeanChange60DaysCheckSpec](#columnanomalymeanchange60dayscheckspec)| | | |
|[daily_median_anomaly_7_days](#columnanomalymedianchange7dayscheckspec)|Verifies that the median in a column changes in a rate within a percentile boundary during last 7 days.|[ColumnAnomalyMedianChange7DaysCheckSpec](#columnanomalymedianchange7dayscheckspec)| | | |
|[daily_median_anomaly_30_days](#columnanomalymedianchange30dayscheckspec)|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyMedianChange30DaysCheckSpec](#columnanomalymedianchange30dayscheckspec)| | | |
|[daily_median_anomaly_60_days](#columnanomalymedianchange60dayscheckspec)|Verifies that the median in a column changes in a rate within a percentile boundary during last 60 days.|[ColumnAnomalyMedianChange60DaysCheckSpec](#columnanomalymedianchange60dayscheckspec)| | | |
|[daily_sum_anomaly_7_days](#columnanomalysumchange7dayscheckspec)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 7 days.|[ColumnAnomalySumChange7DaysCheckSpec](#columnanomalysumchange7dayscheckspec)| | | |
|[daily_sum_anomaly_30_days](#columnanomalysumchange30dayscheckspec)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalySumChange30DaysCheckSpec](#columnanomalysumchange30dayscheckspec)| | | |
|[daily_sum_anomaly_60_days](#columnanomalysumchange60dayscheckspec)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 60 days.|[ColumnAnomalySumChange60DaysCheckSpec](#columnanomalysumchange60dayscheckspec)| | | |
|[daily_mean_change](#columnchangemeancheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](#columnchangemeancheckspec)| | | |
|[daily_mean_change_yesterday](#columnchangemeansinceyesterdaycheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMeanSinceYesterdayCheckSpec](#columnchangemeansinceyesterdaycheckspec)| | | |
|[daily_mean_change_7_days](#columnchangemeansince7dayscheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMeanSince7DaysCheckSpec](#columnchangemeansince7dayscheckspec)| | | |
|[daily_mean_change_30_days](#columnchangemeansince30dayscheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMeanSince30DaysCheckSpec](#columnchangemeansince30dayscheckspec)| | | |
|[daily_median_change](#columnchangemediancheckspec)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](#columnchangemediancheckspec)| | | |
|[daily_median_change_yesterday](#columnchangemediansinceyesterdaycheckspec)|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMedianSinceYesterdayCheckSpec](#columnchangemediansinceyesterdaycheckspec)| | | |
|[daily_median_change_7_days](#columnchangemediansince7dayscheckspec)|Verifies that the median in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMedianSince7DaysCheckSpec](#columnchangemediansince7dayscheckspec)| | | |
|[daily_median_change_30_days](#columnchangemediansince30dayscheckspec)|Verifies that the median in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMedianSince30DaysCheckSpec](#columnchangemediansince30dayscheckspec)| | | |
|[daily_sum_change](#columnchangesumcheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](#columnchangesumcheckspec)| | | |
|[daily_sum_change_yesterday](#columnchangesumsinceyesterdaycheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeSumSinceYesterdayCheckSpec](#columnchangesumsinceyesterdaycheckspec)| | | |
|[daily_sum_change_7_days](#columnchangesumsince7dayscheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|[ColumnChangeSumSince7DaysCheckSpec](#columnchangesumsince7dayscheckspec)| | | |
|[daily_sum_change_30_days](#columnchangesumsince30dayscheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|[ColumnChangeSumSince30DaysCheckSpec](#columnchangesumsince30dayscheckspec)| | | |









___  

## ColumnSchemaDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking the column schema at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_column_exists](#columnschemacolumnexistscheckspec)|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.|[ColumnSchemaColumnExistsCheckSpec](#columnschemacolumnexistscheckspec)| | | |
|[daily_column_type_changed](#columnschematypechangedcheckspec)|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last day. Stores the most recent hash for each day when the data quality check was evaluated.|[ColumnSchemaTypeChangedCheckSpec](#columnschematypechangedcheckspec)| | | |









___  

