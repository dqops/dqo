
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
|[consistency](#columnconsistencymonthlypartitionedchecksspec)|Monthly partitioned checks for consistency in the column|[ColumnConsistencyMonthlyPartitionedChecksSpec](#columnconsistencymonthlypartitionedchecksspec)| | | |
|[anomaly](#columnanomalymonthlypartitionedchecksspec)|Monthly partitioned checks for anomaly in the column|[ColumnAnomalyMonthlyPartitionedChecksSpec](#columnanomalymonthlypartitionedchecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## ColumnNullsMonthlyPartitionedChecksSpec  
Container of nulls data quality partitioned checks on a column level that are checking monthly partitions or rows for each day of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_nulls_count](#columnnullscountcheckspec)|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNullsCountCheckSpec](#columnnullscountcheckspec)| | | |
|[monthly_partition_nulls_percent](#columnnullspercentcheckspec)|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNullsPercentCheckSpec](#columnnullspercentcheckspec)| | | |
|[monthly_partition_not_nulls_count](#columnnotnullscountcheckspec)|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNotNullsCountCheckSpec](#columnnotnullscountcheckspec)| | | |
|[monthly_partition_not_nulls_percent](#columnnotnullspercentcheckspec)|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNotNullsPercentCheckSpec](#columnnotnullspercentcheckspec)| | | |









___  

## ColumnNumericMonthlyPartitionedChecksSpec  
Container of numeric data quality partitioned checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_negative_count](#columnnegativecountcheckspec)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNegativeCountCheckSpec](#columnnegativecountcheckspec)| | | |
|[monthly_partition_negative_percent](#columnnegativepercentcheckspec)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNegativePercentCheckSpec](#columnnegativepercentcheckspec)| | | |
|[monthly_partition_non_negative_count](#columnnonnegativecountcheckspec)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNonNegativeCountCheckSpec](#columnnonnegativecountcheckspec)| | | |
|[monthly_partition_non_negative_percent](#columnnonnegativepercentcheckspec)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNonNegativePercentCheckSpec](#columnnonnegativepercentcheckspec)| | | |
|[monthly_partition_expected_numbers_in_use_count](#columnexpectednumbersinusecountcheckspec)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnExpectedNumbersInUseCountCheckSpec](#columnexpectednumbersinusecountcheckspec)| | | |
|[monthly_partition_number_value_in_set_percent](#columnnumbervalueinsetpercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnNumberValueInSetPercentCheckSpec](#columnnumbervalueinsetpercentcheckspec)| | | |
|[monthly_partition_values_in_range_numeric_percent](#columnvaluesinrangenumericpercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValuesInRangeNumericPercentCheckSpec](#columnvaluesinrangenumericpercentcheckspec)| | | |
|[monthly_partition_values_in_range_integers_percent](#columnvaluesinrangeintegerspercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValuesInRangeIntegersPercentCheckSpec](#columnvaluesinrangeintegerspercentcheckspec)| | | |
|[monthly_partition_value_below_min_value_count](#columnvaluebelowminvaluecountcheckspec)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueBelowMinValueCountCheckSpec](#columnvaluebelowminvaluecountcheckspec)| | | |
|[monthly_partition_value_below_min_value_percent](#columnvaluebelowminvaluepercentcheckspec)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueBelowMinValuePercentCheckSpec](#columnvaluebelowminvaluepercentcheckspec)| | | |
|[monthly_partition_value_above_max_value_count](#columnvalueabovemaxvaluecountcheckspec)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueAboveMaxValueCountCheckSpec](#columnvalueabovemaxvaluecountcheckspec)| | | |
|[monthly_partition_value_above_max_value_percent](#columnvalueabovemaxvaluepercentcheckspec)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValueAboveMaxValuePercentCheckSpec](#columnvalueabovemaxvaluepercentcheckspec)| | | |
|[monthly_partition_max_in_range](#columnmaxinrangecheckspec)|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMaxInRangeCheckSpec](#columnmaxinrangecheckspec)| | | |
|[monthly_partition_min_in_range](#columnmininrangecheckspec)|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMinInRangeCheckSpec](#columnmininrangecheckspec)| | | |
|[monthly_partition_mean_in_range](#columnmeaninrangecheckspec)|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMeanInRangeCheckSpec](#columnmeaninrangecheckspec)| | | |
|[monthly_partition_percentile_in_range](#columnpercentileinrangecheckspec)|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentileInRangeCheckSpec](#columnpercentileinrangecheckspec)| | | |
|[monthly_partition_median_in_range](#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnMedianInRangeCheckSpec](#columnmedianinrangecheckspec)| | | |
|[monthly_partition_percentile_10_in_range](#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile10InRangeCheckSpec](#columnpercentile10inrangecheckspec)| | | |
|[monthly_partition_percentile_25_in_range](#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile25InRangeCheckSpec](#columnpercentile25inrangecheckspec)| | | |
|[monthly_partition_percentile_75_in_range](#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile75InRangeCheckSpec](#columnpercentile75inrangecheckspec)| | | |
|[monthly_partition_percentile_90_in_range](#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPercentile90InRangeCheckSpec](#columnpercentile90inrangecheckspec)| | | |
|[monthly_partition_sample_stddev_in_range](#columnsamplestddevinrangecheckspec)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSampleStddevInRangeCheckSpec](#columnsamplestddevinrangecheckspec)| | | |
|[monthly_partition_population_stddev_in_range](#columnpopulationstddevinrangecheckspec)|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPopulationStddevInRangeCheckSpec](#columnpopulationstddevinrangecheckspec)| | | |
|[monthly_partition_sample_variance_in_range](#columnsamplevarianceinrangecheckspec)|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSampleVarianceInRangeCheckSpec](#columnsamplevarianceinrangecheckspec)| | | |
|[monthly_partition_population_variance_in_range](#columnpopulationvarianceinrangecheckspec)|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPopulationVarianceInRangeCheckSpec](#columnpopulationvarianceinrangecheckspec)| | | |
|[monthly_partition_sum_in_range](#columnsuminrangecheckspec)|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSumInRangeCheckSpec](#columnsuminrangecheckspec)| | | |
|[monthly_partition_invalid_latitude_count](#columninvalidlatitudecountcheckspec)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnInvalidLatitudeCountCheckSpec](#columninvalidlatitudecountcheckspec)| | | |
|[monthly_partition_valid_latitude_percent](#columnvalidlatitudepercentcheckspec)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValidLatitudePercentCheckSpec](#columnvalidlatitudepercentcheckspec)| | | |
|[monthly_partition_invalid_longitude_count](#columninvalidlongitudecountcheckspec)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnInvalidLongitudeCountCheckSpec](#columninvalidlongitudecountcheckspec)| | | |
|[monthly_partition_valid_longitude_percent](#columnvalidlongitudepercentcheckspec)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnValidLongitudePercentCheckSpec](#columnvalidlongitudepercentcheckspec)| | | |









___  

## ColumnStringsMonthlyPartitionedChecksSpec  
Container of strings data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_string_max_length](#columnstringmaxlengthcheckspec)|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMaxLengthCheckSpec](#columnstringmaxlengthcheckspec)| | | |
|[monthly_partition_string_min_length](#columnstringminlengthcheckspec)|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMinLengthCheckSpec](#columnstringminlengthcheckspec)| | | |
|[monthly_partition_string_mean_length](#columnstringmeanlengthcheckspec)|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMeanLengthCheckSpec](#columnstringmeanlengthcheckspec)| | | |
|[monthly_partition_string_length_below_min_length_count](#columnstringlengthbelowminlengthcountcheckspec)|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthBelowMinLengthCountCheckSpec](#columnstringlengthbelowminlengthcountcheckspec)| | | |
|[monthly_partition_string_length_below_min_length_percent](#columnstringlengthbelowminlengthpercentcheckspec)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](#columnstringlengthbelowminlengthpercentcheckspec)| | | |
|[monthly_partition_string_length_above_max_length_count](#columnstringlengthabovemaxlengthcountcheckspec)|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](#columnstringlengthabovemaxlengthcountcheckspec)| | | |
|[monthly_partition_string_length_above_max_length_percent](#columnstringlengthabovemaxlengthpercentcheckspec)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](#columnstringlengthabovemaxlengthpercentcheckspec)| | | |
|[monthly_partition_string_length_in_range_percent](#columnstringlengthinrangepercentcheckspec)|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringLengthInRangePercentCheckSpec](#columnstringlengthinrangepercentcheckspec)| | | |
|[monthly_partition_string_empty_count](#columnstringemptycountcheckspec)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringEmptyCountCheckSpec](#columnstringemptycountcheckspec)| | | |
|[monthly_partition_string_empty_percent](#columnstringemptypercentcheckspec)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringEmptyPercentCheckSpec](#columnstringemptypercentcheckspec)| | | |
|[monthly_partition_string_whitespace_count](#columnstringwhitespacecountcheckspec)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringWhitespaceCountCheckSpec](#columnstringwhitespacecountcheckspec)| | | |
|[monthly_partition_string_whitespace_percent](#columnstringwhitespacepercentcheckspec)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringWhitespacePercentCheckSpec](#columnstringwhitespacepercentcheckspec)| | | |
|[monthly_partition_string_surrounded_by_whitespace_count](#columnstringsurroundedbywhitespacecountcheckspec)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](#columnstringsurroundedbywhitespacecountcheckspec)| | | |
|[monthly_partition_string_surrounded_by_whitespace_percent](#columnstringsurroundedbywhitespacepercentcheckspec)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](#columnstringsurroundedbywhitespacepercentcheckspec)| | | |
|[monthly_partition_string_null_placeholder_count](#columnstringnullplaceholdercountcheckspec)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNullPlaceholderCountCheckSpec](#columnstringnullplaceholdercountcheckspec)| | | |
|[monthly_partition_string_null_placeholder_percent](#columnstringnullplaceholderpercentcheckspec)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNullPlaceholderPercentCheckSpec](#columnstringnullplaceholderpercentcheckspec)| | | |
|[monthly_partition_string_boolean_placeholder_percent](#columnstringbooleanplaceholderpercentcheckspec)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringBooleanPlaceholderPercentCheckSpec](#columnstringbooleanplaceholderpercentcheckspec)| | | |
|[monthly_partition_string_parsable_to_integer_percent](#columnstringparsabletointegerpercentcheckspec)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringParsableToIntegerPercentCheckSpec](#columnstringparsabletointegerpercentcheckspec)| | | |
|[monthly_partition_string_parsable_to_float_percent](#columnstringparsabletofloatpercentcheckspec)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringParsableToFloatPercentCheckSpec](#columnstringparsabletofloatpercentcheckspec)| | | |
|[monthly_partition_expected_strings_in_use_count](#columnexpectedstringsinusecountcheckspec)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnExpectedStringsInUseCountCheckSpec](#columnexpectedstringsinusecountcheckspec)| | | |
|[monthly_partition_string_value_in_set_percent](#columnstringvalueinsetpercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValueInSetPercentCheckSpec](#columnstringvalueinsetpercentcheckspec)| | | |
|[monthly_partition_string_valid_dates_percent](#columnstringvaliddatespercentcheckspec)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidDatesPercentCheckSpec](#columnstringvaliddatespercentcheckspec)| | | |
|[monthly_partition_string_valid_country_code_percent](#columnstringvalidcountrycodepercentcheckspec)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidCountryCodePercentCheckSpec](#columnstringvalidcountrycodepercentcheckspec)| | | |
|[monthly_partition_string_valid_currency_code_percent](#columnstringvalidcurrencycodepercentcheckspec)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidCurrencyCodePercentCheckSpec](#columnstringvalidcurrencycodepercentcheckspec)| | | |
|[monthly_partition_string_invalid_email_count](#columnstringinvalidemailcountcheckspec)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidEmailCountCheckSpec](#columnstringinvalidemailcountcheckspec)| | | |
|[monthly_partition_string_invalid_uuid_count](#columnstringinvaliduuidcountcheckspec)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidUuidCountCheckSpec](#columnstringinvaliduuidcountcheckspec)| | | |
|[monthly_partition_valid_uuid_percent](#columnstringvaliduuidpercentcheckspec)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringValidUuidPercentCheckSpec](#columnstringvaliduuidpercentcheckspec)| | | |
|[monthly_partition_string_invalid_ip4_address_count](#columnstringinvalidip4addresscountcheckspec)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidIp4AddressCountCheckSpec](#columnstringinvalidip4addresscountcheckspec)| | | |
|[monthly_partition_string_invalid_ip6_address_count](#columnstringinvalidip6addresscountcheckspec)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringInvalidIp6AddressCountCheckSpec](#columnstringinvalidip6addresscountcheckspec)| | | |
|[monthly_partition_string_not_match_regex_count](#columnstringnotmatchregexcountcheckspec)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNotMatchRegexCountCheckSpec](#columnstringnotmatchregexcountcheckspec)| | | |
|[monthly_partition_string_match_regex_percent](#columnstringmatchregexpercentcheckspec)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMatchRegexPercentCheckSpec](#columnstringmatchregexpercentcheckspec)| | | |
|[monthly_partition_string_not_match_date_regex_count](#columnstringnotmatchdateregexcountcheckspec)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringNotMatchDateRegexCountCheckSpec](#columnstringnotmatchdateregexcountcheckspec)| | | |
|[monthly_partition_string_match_date_regex_percent](#columnstringmatchdateregexpercentcheckspec)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMatchDateRegexPercentCheckSpec](#columnstringmatchdateregexpercentcheckspec)| | | |
|[monthly_partition_string_match_name_regex_percent](#columnstringmatchnameregexpercentcheckspec)|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringMatchNameRegexPercentCheckSpec](#columnstringmatchnameregexpercentcheckspec)| | | |
|[monthly_partition_expected_strings_in_top_values_count](#columnexpectedstringsintopvaluescountcheckspec)|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnExpectedStringsInTopValuesCountCheckSpec](#columnexpectedstringsintopvaluescountcheckspec)| | | |
|[monthly_partition_string_datatype_detected](#columnstringdatatypedetectedcheckspec)|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringDatatypeDetectedCheckSpec](#columnstringdatatypedetectedcheckspec)| | | |









___  

## ColumnUniquenessMonthlyPartitionedChecksSpec  
Container of uniqueness data quality partitioned checks on a column level that are checking at a monthly level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_distinct_count](#columndistinctcountcheckspec)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDistinctCountCheckSpec](#columndistinctcountcheckspec)| | | |
|[monthly_partition_distinct_percent](#columndistinctpercentcheckspec)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDistinctPercentCheckSpec](#columndistinctpercentcheckspec)| | | |
|[monthly_partition_duplicate_count](#columnduplicatecountcheckspec)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDuplicateCountCheckSpec](#columnduplicatecountcheckspec)| | | |
|[monthly_partition_duplicate_percent](#columnduplicatepercentcheckspec)|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDuplicatePercentCheckSpec](#columnduplicatepercentcheckspec)| | | |









___  

## ColumnDatetimeMonthlyPartitionedChecksSpec  
Container of date-time data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_date_values_in_future_percent](#columndatevaluesinfuturepercentcheckspec)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDateValuesInFuturePercentCheckSpec](#columndatevaluesinfuturepercentcheckspec)| | | |
|[monthly_partition_datetime_value_in_range_date_percent](#columndatetimevalueinrangedatepercentcheckspec)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](#columndatetimevalueinrangedatepercentcheckspec)| | | |









___  

## ColumnPiiMonthlyPartitionedChecksSpec  
Container of PII data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_valid_usa_phone_percent](#columnpiivalidusaphonepercentcheckspec)|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiValidUsaPhonePercentCheckSpec](#columnpiivalidusaphonepercentcheckspec)| | | |
|[monthly_partition_contains_usa_phone_percent](#columnpiicontainsusaphonepercentcheckspec)|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsUsaPhonePercentCheckSpec](#columnpiicontainsusaphonepercentcheckspec)| | | |
|[monthly_partition_valid_usa_zipcode_percent](#columnpiivalidusazipcodepercentcheckspec)|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiValidUsaZipcodePercentCheckSpec](#columnpiivalidusazipcodepercentcheckspec)| | | |
|[monthly_partition_contains_usa_zipcode_percent](#columnpiicontainsusazipcodepercentcheckspec)|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](#columnpiicontainsusazipcodepercentcheckspec)| | | |
|[monthly_partition_valid_email_percent](#columnpiivalidemailpercentcheckspec)|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiValidEmailPercentCheckSpec](#columnpiivalidemailpercentcheckspec)| | | |
|[monthly_partition_contains_email_percent](#columnpiicontainsemailpercentcheckspec)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsEmailPercentCheckSpec](#columnpiicontainsemailpercentcheckspec)| | | |
|[monthly_partition_valid_ip4_address_percent](#columnpiivalidip4addresspercentcheckspec)|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiValidIp4AddressPercentCheckSpec](#columnpiivalidip4addresspercentcheckspec)| | | |
|[monthly_partition_contains_ip4_percent](#columnpiicontainsip4percentcheckspec)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsIp4PercentCheckSpec](#columnpiicontainsip4percentcheckspec)| | | |
|[monthly_partition_valid_ip6_address_percent](#columnpiivalidip6addresspercentcheckspec)|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiValidIp6AddressPercentCheckSpec](#columnpiivalidip6addresspercentcheckspec)| | | |
|[monthly_partition_contains_ip6_percent](#columnpiicontainsip6percentcheckspec)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnPiiContainsIp6PercentCheckSpec](#columnpiicontainsip6percentcheckspec)| | | |









___  

## ColumnSqlMonthlyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_sql_condition_passed_percent_on_column](#columnsqlconditionpassedpercentcheckspec)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlConditionPassedPercentCheckSpec](#columnsqlconditionpassedpercentcheckspec)| | | |
|[monthly_partition_sql_condition_failed_count_on_column](#columnsqlconditionfailedcountcheckspec)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlConditionFailedCountCheckSpec](#columnsqlconditionfailedcountcheckspec)| | | |
|[monthly_partition_sql_aggregate_expr_column](#columnsqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnSqlAggregateExprCheckSpec](#columnsqlaggregateexprcheckspec)| | | |









___  

## ColumnBoolMonthlyPartitionedChecksSpec  
Container of boolean data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_true_percent](#columntruepercentcheckspec)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnTruePercentCheckSpec](#columntruepercentcheckspec)| | | |
|[monthly_partition_false_percent](#columnfalsepercentcheckspec)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnFalsePercentCheckSpec](#columnfalsepercentcheckspec)| | | |









___  

## ColumnIntegrityMonthlyPartitionedChecksSpec  
Container of integrity data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_foreign_key_not_match_count](#columnintegrityforeignkeynotmatchcountcheckspec)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](#columnintegrityforeignkeynotmatchcountcheckspec)| | | |
|[monthly_partition_foreign_key_match_percent](#columnintegrityforeignkeymatchpercentcheckspec)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](#columnintegrityforeignkeymatchpercentcheckspec)| | | |









___  

## ColumnAccuracyMonthlyPartitionedChecksSpec  
Container of accuracy data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___  

## ColumnConsistencyMonthlyPartitionedChecksSpec  
Container of consistency data quality partitioned checks on a column level that are checking monthly partitions or rows for each month of data.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_date_match_format_percent](#columnconsistencydatematchformatpercentcheckspec)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnConsistencyDateMatchFormatPercentCheckSpec](#columnconsistencydatematchformatpercentcheckspec)| | | |
|[monthly_partition_string_datatype_changed](#columnstringdatatypechangedcheckspec)|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each monthly partition.|[ColumnStringDatatypeChangedCheckSpec](#columnstringdatatypechangedcheckspec)| | | |









___  

## ColumnAnomalyMonthlyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_mean_change](#columnchangemeancheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](#columnchangemeancheckspec)| | | |
|[monthly_partition_median_change](#columnchangemediancheckspec)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](#columnchangemediancheckspec)| | | |
|[monthly_partition_sum_change](#columnchangesumcheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](#columnchangesumcheckspec)| | | |









___  

