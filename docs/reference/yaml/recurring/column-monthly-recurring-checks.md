
## ColumnMonthlyRecurringCheckCategoriesSpec  
Container of column level monthly recurring checks. Contains categories of monthly recurring checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](#columnnullsmonthlyrecurringchecksspec)|Monthly recurring checks of nulls in the column|[ColumnNullsMonthlyRecurringChecksSpec](#columnnullsmonthlyrecurringchecksspec)| | | |
|[numeric](#columnnumericmonthlyrecurringchecksspec)|Monthly recurring checks of numeric in the column|[ColumnNumericMonthlyRecurringChecksSpec](#columnnumericmonthlyrecurringchecksspec)| | | |
|[strings](#columnstringsmonthlyrecurringchecksspec)|Monthly recurring checks of strings in the column|[ColumnStringsMonthlyRecurringChecksSpec](#columnstringsmonthlyrecurringchecksspec)| | | |
|[uniqueness](#columnuniquenessmonthlyrecurringchecksspec)|Monthly recurring checks of uniqueness in the column|[ColumnUniquenessMonthlyRecurringChecksSpec](#columnuniquenessmonthlyrecurringchecksspec)| | | |
|[datetime](#columndatetimemonthlyrecurringchecksspec)|Monthly recurring checks of datetime in the column|[ColumnDatetimeMonthlyRecurringChecksSpec](#columndatetimemonthlyrecurringchecksspec)| | | |
|[pii](#columnpiimonthlyrecurringchecksspec)|Monthly recurring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiMonthlyRecurringChecksSpec](#columnpiimonthlyrecurringchecksspec)| | | |
|[sql](#columnsqlmonthlyrecurringchecksspec)|Monthly recurring checks of custom SQL checks in the column|[ColumnSqlMonthlyRecurringChecksSpec](#columnsqlmonthlyrecurringchecksspec)| | | |
|[bool](#columnboolmonthlyrecurringchecksspec)|Monthly recurring checks of booleans in the column|[ColumnBoolMonthlyRecurringChecksSpec](#columnboolmonthlyrecurringchecksspec)| | | |
|[integrity](#columnintegritymonthlyrecurringchecksspec)|Monthly recurring checks of integrity in the column|[ColumnIntegrityMonthlyRecurringChecksSpec](#columnintegritymonthlyrecurringchecksspec)| | | |
|[accuracy](#columnaccuracymonthlyrecurringchecksspec)|Monthly recurring checks of accuracy in the column|[ColumnAccuracyMonthlyRecurringChecksSpec](#columnaccuracymonthlyrecurringchecksspec)| | | |
|[consistency](#columnconsistencymonthlyrecurringchecksspec)|Monthly recurring checks of consistency in the column|[ColumnConsistencyMonthlyRecurringChecksSpec](#columnconsistencymonthlyrecurringchecksspec)| | | |
|[anomaly](#columnanomalymonthlyrecurringchecksspec)|Monthly recurring checks of anomaly in the column|[ColumnAnomalyMonthlyRecurringChecksSpec](#columnanomalymonthlyrecurringchecksspec)| | | |
|[schema](#columnschemamonthlyrecurringchecksspec)|Monthly recurring column schema checks|[ColumnSchemaMonthlyRecurringChecksSpec](#columnschemamonthlyrecurringchecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## ColumnNullsMonthlyRecurringChecksSpec  
Container of nulls data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_nulls_count](#columnnullscountcheckspec)|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNullsCountCheckSpec](#columnnullscountcheckspec)| | | |
|[monthly_nulls_percent](#columnnullspercentcheckspec)|Verifies that the percentage of null values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNullsPercentCheckSpec](#columnnullspercentcheckspec)| | | |
|[monthly_not_nulls_count](#columnnotnullscountcheckspec)|Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNotNullsCountCheckSpec](#columnnotnullscountcheckspec)| | | |
|[monthly_not_nulls_percent](#columnnotnullspercentcheckspec)|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNotNullsPercentCheckSpec](#columnnotnullspercentcheckspec)| | | |









___  

## ColumnNumericMonthlyRecurringChecksSpec  
Container of built-in preconfigured data quality recurring on a column level that are checking numeric values at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_negative_count](#columnnegativecountcheckspec)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNegativeCountCheckSpec](#columnnegativecountcheckspec)| | | |
|[monthly_negative_percent](#columnnegativepercentcheckspec)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNegativePercentCheckSpec](#columnnegativepercentcheckspec)| | | |
|[monthly_non_negative_count](#columnnonnegativecountcheckspec)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNonNegativeCountCheckSpec](#columnnonnegativecountcheckspec)| | | |
|[monthly_non_negative_percent](#columnnonnegativepercentcheckspec)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNonNegativePercentCheckSpec](#columnnonnegativepercentcheckspec)| | | |
|[monthly_expected_numbers_in_use_count](#columnexpectednumbersinusecountcheckspec)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnExpectedNumbersInUseCountCheckSpec](#columnexpectednumbersinusecountcheckspec)| | | |
|[monthly_number_value_in_set_percent](#columnnumbervalueinsetpercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnNumberValueInSetPercentCheckSpec](#columnnumbervalueinsetpercentcheckspec)| | | |
|[monthly_values_in_range_numeric_percent](#columnvaluesinrangenumericpercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValuesInRangeNumericPercentCheckSpec](#columnvaluesinrangenumericpercentcheckspec)| | | |
|[monthly_values_in_range_integers_percent](#columnvaluesinrangeintegerspercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValuesInRangeIntegersPercentCheckSpec](#columnvaluesinrangeintegerspercentcheckspec)| | | |
|[monthly_value_below_min_value_count](#columnvaluebelowminvaluecountcheckspec)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueBelowMinValueCountCheckSpec](#columnvaluebelowminvaluecountcheckspec)| | | |
|[monthly_value_below_min_value_percent](#columnvaluebelowminvaluepercentcheckspec)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueBelowMinValuePercentCheckSpec](#columnvaluebelowminvaluepercentcheckspec)| | | |
|[monthly_value_above_max_value_count](#columnvalueabovemaxvaluecountcheckspec)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueAboveMaxValueCountCheckSpec](#columnvalueabovemaxvaluecountcheckspec)| | | |
|[monthly_value_above_max_value_percent](#columnvalueabovemaxvaluepercentcheckspec)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValueAboveMaxValuePercentCheckSpec](#columnvalueabovemaxvaluepercentcheckspec)| | | |
|[monthly_max_in_range](#columnmaxinrangecheckspec)|Verifies that the maximal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMaxInRangeCheckSpec](#columnmaxinrangecheckspec)| | | |
|[monthly_min_in_range](#columnmininrangecheckspec)|Verifies that the minimal value in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMinInRangeCheckSpec](#columnmininrangecheckspec)| | | |
|[monthly_mean_in_range](#columnmeaninrangecheckspec)|Verifies that the average (mean) of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMeanInRangeCheckSpec](#columnmeaninrangecheckspec)| | | |
|[monthly_percentile_in_range](#columnpercentileinrangecheckspec)|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentileInRangeCheckSpec](#columnpercentileinrangecheckspec)| | | |
|[monthly_median_in_range](#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMedianInRangeCheckSpec](#columnmedianinrangecheckspec)| | | |
|[monthly_percentile_10_in_range](#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile10InRangeCheckSpec](#columnpercentile10inrangecheckspec)| | | |
|[monthly_percentile_25_in_range](#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile25InRangeCheckSpec](#columnpercentile25inrangecheckspec)| | | |
|[monthly_percentile_75_in_range](#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile75InRangeCheckSpec](#columnpercentile75inrangecheckspec)| | | |
|[monthly_percentile_90_in_range](#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPercentile90InRangeCheckSpec](#columnpercentile90inrangecheckspec)| | | |
|[monthly_sample_stddev_in_range](#columnsamplestddevinrangecheckspec)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSampleStddevInRangeCheckSpec](#columnsamplestddevinrangecheckspec)| | | |
|[monthly_population_stddev_in_range](#columnpopulationstddevinrangecheckspec)|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPopulationStddevInRangeCheckSpec](#columnpopulationstddevinrangecheckspec)| | | |
|[monthly_sample_variance_in_range](#columnsamplevarianceinrangecheckspec)|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSampleVarianceInRangeCheckSpec](#columnsamplevarianceinrangecheckspec)| | | |
|[monthly_population_variance_in_range](#columnpopulationvarianceinrangecheckspec)|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPopulationVarianceInRangeCheckSpec](#columnpopulationvarianceinrangecheckspec)| | | |
|[monthly_sum_in_range](#columnsuminrangecheckspec)|Verifies that the sum of all values in a column does not exceed the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSumInRangeCheckSpec](#columnsuminrangecheckspec)| | | |
|[monthly_invalid_latitude_count](#columninvalidlatitudecountcheckspec)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnInvalidLatitudeCountCheckSpec](#columninvalidlatitudecountcheckspec)| | | |
|[monthly_valid_latitude_percent](#columnvalidlatitudepercentcheckspec)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValidLatitudePercentCheckSpec](#columnvalidlatitudepercentcheckspec)| | | |
|[monthly_invalid_longitude_count](#columninvalidlongitudecountcheckspec)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnInvalidLongitudeCountCheckSpec](#columninvalidlongitudecountcheckspec)| | | |
|[monthly_valid_longitude_percent](#columnvalidlongitudepercentcheckspec)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnValidLongitudePercentCheckSpec](#columnvalidlongitudepercentcheckspec)| | | |









___  

## ColumnStringsMonthlyRecurringChecksSpec  
Container of strings data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_string_max_length](#columnstringmaxlengthcheckspec)|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMaxLengthCheckSpec](#columnstringmaxlengthcheckspec)| | | |
|[monthly_string_min_length](#columnstringminlengthcheckspec)|Verifies that the length of string in a column does not exceed the minimum accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMinLengthCheckSpec](#columnstringminlengthcheckspec)| | | |
|[monthly_string_mean_length](#columnstringmeanlengthcheckspec)|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMeanLengthCheckSpec](#columnstringmeanlengthcheckspec)| | | |
|[monthly_string_length_below_min_length_count](#columnstringlengthbelowminlengthcountcheckspec)|The check counts those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthCountCheckSpec](#columnstringlengthbelowminlengthcountcheckspec)| | | |
|[monthly_string_length_below_min_length_percent](#columnstringlengthbelowminlengthpercentcheckspec)|The check counts percentage of those strings with length below the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](#columnstringlengthbelowminlengthpercentcheckspec)| | | |
|[monthly_string_length_above_max_length_count](#columnstringlengthabovemaxlengthcountcheckspec)|The check counts those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](#columnstringlengthabovemaxlengthcountcheckspec)| | | |
|[monthly_string_length_above_max_length_percent](#columnstringlengthabovemaxlengthpercentcheckspec)|The check counts percentage of those strings with length above the one provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](#columnstringlengthabovemaxlengthpercentcheckspec)| | | |
|[monthly_string_length_in_range_percent](#columnstringlengthinrangepercentcheckspec)|The check counts percentage of those strings with length in the range provided by the user in a column. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringLengthInRangePercentCheckSpec](#columnstringlengthinrangepercentcheckspec)| | | |
|[monthly_string_empty_count](#columnstringemptycountcheckspec)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringEmptyCountCheckSpec](#columnstringemptycountcheckspec)| | | |
|[monthly_string_empty_percent](#columnstringemptypercentcheckspec)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringEmptyPercentCheckSpec](#columnstringemptypercentcheckspec)| | | |
|[monthly_string_valid_dates_percent](#columnstringvaliddatespercentcheckspec)|Verifies that the percentage of valid dates in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidDatesPercentCheckSpec](#columnstringvaliddatespercentcheckspec)| | | |
|[monthly_string_whitespace_count](#columnstringwhitespacecountcheckspec)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringWhitespaceCountCheckSpec](#columnstringwhitespacecountcheckspec)| | | |
|[monthly_string_whitespace_percent](#columnstringwhitespacepercentcheckspec)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringWhitespacePercentCheckSpec](#columnstringwhitespacepercentcheckspec)| | | |
|[monthly_string_surrounded_by_whitespace_count](#columnstringsurroundedbywhitespacecountcheckspec)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](#columnstringsurroundedbywhitespacecountcheckspec)| | | |
|[monthly_string_surrounded_by_whitespace_percent](#columnstringsurroundedbywhitespacepercentcheckspec)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](#columnstringsurroundedbywhitespacepercentcheckspec)| | | |
|[monthly_string_null_placeholder_count](#columnstringnullplaceholdercountcheckspec)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNullPlaceholderCountCheckSpec](#columnstringnullplaceholdercountcheckspec)| | | |
|[monthly_string_null_placeholder_percent](#columnstringnullplaceholderpercentcheckspec)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNullPlaceholderPercentCheckSpec](#columnstringnullplaceholderpercentcheckspec)| | | |
|[monthly_string_boolean_placeholder_percent](#columnstringbooleanplaceholderpercentcheckspec)|Verifies that the percentage of boolean placeholder for strings in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringBooleanPlaceholderPercentCheckSpec](#columnstringbooleanplaceholderpercentcheckspec)| | | |
|[monthly_string_parsable_to_integer_percent](#columnstringparsabletointegerpercentcheckspec)|Verifies that the percentage of parsable to integer string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringParsableToIntegerPercentCheckSpec](#columnstringparsabletointegerpercentcheckspec)| | | |
|[monthly_string_parsable_to_float_percent](#columnstringparsabletofloatpercentcheckspec)|Verifies that the percentage of parsable to float string in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringParsableToFloatPercentCheckSpec](#columnstringparsabletofloatpercentcheckspec)| | | |
|[monthly_expected_strings_in_use_count](#columnexpectedstringsinusecountcheckspec)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnExpectedStringsInUseCountCheckSpec](#columnexpectedstringsinusecountcheckspec)| | | |
|[monthly_string_value_in_set_percent](#columnstringvalueinsetpercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValueInSetPercentCheckSpec](#columnstringvalueinsetpercentcheckspec)| | | |
|[monthly_string_valid_country_code_percent](#columnstringvalidcountrycodepercentcheckspec)|Verifies that the percentage of valid country code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidCountryCodePercentCheckSpec](#columnstringvalidcountrycodepercentcheckspec)| | | |
|[monthly_string_valid_currency_code_percent](#columnstringvalidcurrencycodepercentcheckspec)|Verifies that the percentage of valid currency code in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidCurrencyCodePercentCheckSpec](#columnstringvalidcurrencycodepercentcheckspec)| | | |
|[monthly_string_invalid_email_count](#columnstringinvalidemailcountcheckspec)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidEmailCountCheckSpec](#columnstringinvalidemailcountcheckspec)| | | |
|[monthly_string_invalid_uuid_count](#columnstringinvaliduuidcountcheckspec)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidUuidCountCheckSpec](#columnstringinvaliduuidcountcheckspec)| | | |
|[monthly_string_valid_uuid_percent](#columnstringvaliduuidpercentcheckspec)|Verifies that the percentage of valid UUID in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringValidUuidPercentCheckSpec](#columnstringvaliduuidpercentcheckspec)| | | |
|[monthly_string_invalid_ip4_address_count](#columnstringinvalidip4addresscountcheckspec)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidIp4AddressCountCheckSpec](#columnstringinvalidip4addresscountcheckspec)| | | |
|[monthly_string_invalid_ip6_address_count](#columnstringinvalidip6addresscountcheckspec)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringInvalidIp6AddressCountCheckSpec](#columnstringinvalidip6addresscountcheckspec)| | | |
|[monthly_string_not_match_regex_count](#columnstringnotmatchregexcountcheckspec)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNotMatchRegexCountCheckSpec](#columnstringnotmatchregexcountcheckspec)| | | |
|[monthly_string_match_regex_percent](#columnstringmatchregexpercentcheckspec)|Verifies that the percentage of strings matching the custom regex in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMatchRegexPercentCheckSpec](#columnstringmatchregexpercentcheckspec)| | | |
|[monthly_string_not_match_date_regex_count](#columnstringnotmatchdateregexcountcheckspec)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringNotMatchDateRegexCountCheckSpec](#columnstringnotmatchdateregexcountcheckspec)| | | |
|[monthly_string_match_date_regex_percent](#columnstringmatchdateregexpercentcheckspec)|Verifies that the percentage of strings matching the date format regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMatchDateRegexPercentCheckSpec](#columnstringmatchdateregexpercentcheckspec)| | | |
|[monthly_string_match_name_regex_percent](#columnstringmatchnameregexpercentcheckspec)|Verifies that the percentage of strings matching the name regex in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringMatchNameRegexPercentCheckSpec](#columnstringmatchnameregexpercentcheckspec)| | | |
|[monthly_expected_strings_in_top_values_count](#columnexpectedstringsintopvaluescountcheckspec)|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnExpectedStringsInTopValuesCountCheckSpec](#columnexpectedstringsintopvaluescountcheckspec)| | | |
|[monthly_string_datatype_detected](#columnstringdatatypedetectedcheckspec)|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnStringDatatypeDetectedCheckSpec](#columnstringdatatypedetectedcheckspec)| | | |









___  

## ColumnUniquenessMonthlyRecurringChecksSpec  
Container of uniqueness data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_distinct_count](#columndistinctcountcheckspec)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDistinctCountCheckSpec](#columndistinctcountcheckspec)| | | |
|[monthly_distinct_percent](#columndistinctpercentcheckspec)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDistinctPercentCheckSpec](#columndistinctpercentcheckspec)| | | |
|[monthly_duplicate_count](#columnduplicatecountcheckspec)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDuplicateCountCheckSpec](#columnduplicatecountcheckspec)| | | |
|[monthly_duplicate_percent](#columnduplicatepercentcheckspec)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDuplicatePercentCheckSpec](#columnduplicatepercentcheckspec)| | | |









___  

## ColumnDatetimeMonthlyRecurringChecksSpec  
Container of date-time data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_date_values_in_future_percent](#columndatevaluesinfuturepercentcheckspec)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDateValuesInFuturePercentCheckSpec](#columndatevaluesinfuturepercentcheckspec)| | | |
|[monthly_datetime_value_in_range_date_percent](#columndatetimevalueinrangedatepercentcheckspec)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](#columndatetimevalueinrangedatepercentcheckspec)| | | |









___  

## ColumnPiiMonthlyRecurringChecksSpec  
Container of PII data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_valid_usa_phone_percent](#columnpiivalidusaphonepercentcheckspec)|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiValidUsaPhonePercentCheckSpec](#columnpiivalidusaphonepercentcheckspec)| | | |
|[monthly_contains_usa_phone_percent](#columnpiicontainsusaphonepercentcheckspec)|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsUsaPhonePercentCheckSpec](#columnpiicontainsusaphonepercentcheckspec)| | | |
|[monthly_valid_usa_zipcode_percent](#columnpiivalidusazipcodepercentcheckspec)|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiValidUsaZipcodePercentCheckSpec](#columnpiivalidusazipcodepercentcheckspec)| | | |
|[monthly_contains_usa_zipcode_percent](#columnpiicontainsusazipcodepercentcheckspec)|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](#columnpiicontainsusazipcodepercentcheckspec)| | | |
|[monthly_valid_email_percent](#columnpiivalidemailpercentcheckspec)|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiValidEmailPercentCheckSpec](#columnpiivalidemailpercentcheckspec)| | | |
|[monthly_contains_email_percent](#columnpiicontainsemailpercentcheckspec)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsEmailPercentCheckSpec](#columnpiicontainsemailpercentcheckspec)| | | |
|[monthly_valid_ip4_address_percent](#columnpiivalidip4addresspercentcheckspec)|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiValidIp4AddressPercentCheckSpec](#columnpiivalidip4addresspercentcheckspec)| | | |
|[monthly_contains_ip4_percent](#columnpiicontainsip4percentcheckspec)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsIp4PercentCheckSpec](#columnpiicontainsip4percentcheckspec)| | | |
|[monthly_valid_ip6_address_percent](#columnpiivalidip6addresspercentcheckspec)|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiValidIp6AddressPercentCheckSpec](#columnpiivalidip6addresspercentcheckspec)| | | |
|[monthly_contains_ip6_percent](#columnpiicontainsip6percentcheckspec)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnPiiContainsIp6PercentCheckSpec](#columnpiicontainsip6percentcheckspec)| | | |









___  

## ColumnSqlMonthlyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_sql_condition_passed_percent_on_column](#columnsqlconditionpassedpercentcheckspec)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSqlConditionPassedPercentCheckSpec](#columnsqlconditionpassedpercentcheckspec)| | | |
|[monthly_sql_condition_failed_count_on_column](#columnsqlconditionfailedcountcheckspec)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSqlConditionFailedCountCheckSpec](#columnsqlconditionfailedcountcheckspec)| | | |
|[monthly_sql_aggregate_expr_column](#columnsqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnSqlAggregateExprCheckSpec](#columnsqlaggregateexprcheckspec)| | | |









___  

## ColumnBoolMonthlyRecurringChecksSpec  
Container of boolean recurring data quality checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_true_percent](#columntruepercentcheckspec)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnTruePercentCheckSpec](#columntruepercentcheckspec)| | | |
|[monthly_false_percent](#columnfalsepercentcheckspec)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnFalsePercentCheckSpec](#columnfalsepercentcheckspec)| | | |









___  

## ColumnIntegrityMonthlyRecurringChecksSpec  
Container of integrity data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_foreign_key_not_match_count](#columnintegrityforeignkeynotmatchcountcheckspec)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](#columnintegrityforeignkeynotmatchcountcheckspec)| | | |
|[monthly_foreign_key_match_percent](#columnintegrityforeignkeymatchpercentcheckspec)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](#columnintegrityforeignkeymatchpercentcheckspec)| | | |









___  

## ColumnAccuracyMonthlyRecurringChecksSpec  
Container of accuracy data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_total_sum_match_percent](#columnaccuracytotalsummatchpercentcheckspec)|Verifies that the percentage of difference in sum of a column in a table and sum of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyTotalSumMatchPercentCheckSpec](#columnaccuracytotalsummatchpercentcheckspec)| | | |
|[monthly_min_match_percent](#columnaccuracyminmatchpercentcheckspec)|Verifies that the percentage of difference in min of a column in a table and min of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyMinMatchPercentCheckSpec](#columnaccuracyminmatchpercentcheckspec)| | | |
|[monthly_max_match_percent](#columnaccuracymaxmatchpercentcheckspec)|Verifies that the percentage of difference in max of a column in a table and max of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyMaxMatchPercentCheckSpec](#columnaccuracymaxmatchpercentcheckspec)| | | |
|[monthly_average_match_percent](#columnaccuracyaveragematchpercentcheckspec)|Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyAverageMatchPercentCheckSpec](#columnaccuracyaveragematchpercentcheckspec)| | | |
|[monthly_not_null_count_match_percent](#columnaccuracynotnullcountmatchpercentcheckspec)|Verifies that the percentage of difference in row count of a column in a table and row count of a column of another table does not exceed the set number. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnAccuracyNotNullCountMatchPercentCheckSpec](#columnaccuracynotnullcountmatchpercentcheckspec)| | | |









___  

## ColumnConsistencyMonthlyRecurringChecksSpec  
Container of consistency data quality recurring checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_date_match_format_percent](#columnconsistencydatematchformatpercentcheckspec)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly recurring.|[ColumnConsistencyDateMatchFormatPercentCheckSpec](#columnconsistencydatematchformatpercentcheckspec)| | | |
|[monthly_string_datatype_changed](#columnstringdatatypechangedcheckspec)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringDatatypeChangedCheckSpec](#columnstringdatatypechangedcheckspec)| | | |









___  

## ColumnAnomalyMonthlyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_mean_change](#columnchangemeancheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](#columnchangemeancheckspec)| | | |
|[monthly_median_change](#columnchangemediancheckspec)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](#columnchangemediancheckspec)| | | |
|[monthly_sum_change](#columnchangesumcheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](#columnchangesumcheckspec)| | | |









___  

## ColumnSchemaMonthlyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking the column schema at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_column_exists](#columnschemacolumnexistscheckspec)|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each month when the data quality check was evaluated.|[ColumnSchemaColumnExistsCheckSpec](#columnschemacolumnexistscheckspec)| | | |
|[monthly_column_type_changed](#columnschematypechangedcheckspec)|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last month. Stores the most recent hash for each month when the data quality check was evaluated.|[ColumnSchemaTypeChangedCheckSpec](#columnschematypechangedcheckspec)| | | |









___  

