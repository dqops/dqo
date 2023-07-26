
## ColumnPiiDailyRecurringChecksSpec  
Container of PII data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_valid_usa_phone_percent](\docs\checks\column\pii\valid-usa-phone-percent)|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiValidUsaPhonePercentCheckSpec](\docs\checks\column\pii\valid-usa-phone-percent)| | | |
|[daily_contains_usa_phone_percent](\docs\checks\column\pii\contains-usa-phone-percent)|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsUsaPhonePercentCheckSpec](\docs\checks\column\pii\contains-usa-phone-percent)| | | |
|[daily_valid_usa_zipcode_percent](\docs\checks\column\pii\valid-usa-zipcode-percent)|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiValidUsaZipcodePercentCheckSpec](\docs\checks\column\pii\valid-usa-zipcode-percent)| | | |
|[daily_contains_usa_zipcode_percent](\docs\checks\column\pii\contains-usa-zipcode-percent)|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](\docs\checks\column\pii\contains-usa-zipcode-percent)| | | |
|[daily_valid_email_percent](\docs\checks\column\pii\valid-email-percent)|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiValidEmailPercentCheckSpec](\docs\checks\column\pii\valid-email-percent)| | | |
|[daily_contains_email_percent](\docs\checks\column\pii\contains-email-percent)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsEmailPercentCheckSpec](\docs\checks\column\pii\contains-email-percent)| | | |
|[daily_valid_ip4_address_percent](\docs\checks\column\pii\valid-ip4-address-percent)|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiValidIp4AddressPercentCheckSpec](\docs\checks\column\pii\valid-ip4-address-percent)| | | |
|[daily_contains_ip4_percent](\docs\checks\column\pii\contains-ip4-percent)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsIp4PercentCheckSpec](\docs\checks\column\pii\contains-ip4-percent)| | | |
|[daily_valid_ip6_address_percent](\docs\checks\column\pii\valid-ip6-address-percent)|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiValidIp6AddressPercentCheckSpec](\docs\checks\column\pii\valid-ip6-address-percent)| | | |
|[daily_contains_ip6_percent](\docs\checks\column\pii\contains-ip6-percent)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsIp6PercentCheckSpec](\docs\checks\column\pii\contains-ip6-percent)| | | |









___  

## ColumnNumericDailyRecurringChecksSpec  
Container of built-in preconfigured data quality recurring on a column level that are checking numeric values at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_negative_count](\docs\checks\column\numeric\negative-count)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNegativeCountCheckSpec](\docs\checks\column\numeric\negative-count)| | | |
|[daily_negative_percent](\docs\checks\column\numeric\negative-percent)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNegativePercentCheckSpec](\docs\checks\column\numeric\negative-percent)| | | |
|[daily_non_negative_count](\docs\checks\column\numeric\non-negative-count)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNonNegativeCountCheckSpec](\docs\checks\column\numeric\non-negative-count)| | | |
|[daily_non_negative_percent](\docs\checks\column\numeric\non-negative-percent)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNonNegativePercentCheckSpec](\docs\checks\column\numeric\non-negative-percent)| | | |
|[daily_expected_numbers_in_use_count](\docs\checks\column\numeric\expected-numbers-in-use-count)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnExpectedNumbersInUseCountCheckSpec](\docs\checks\column\numeric\expected-numbers-in-use-count)| | | |
|[daily_number_value_in_set_percent](\docs\checks\column\numeric\number-value-in-set-percent)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNumberValueInSetPercentCheckSpec](\docs\checks\column\numeric\number-value-in-set-percent)| | | |
|[daily_values_in_range_numeric_percent](\docs\checks\column\numeric\values-in-range-numeric-percent)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValuesInRangeNumericPercentCheckSpec](\docs\checks\column\numeric\values-in-range-numeric-percent)| | | |
|[daily_values_in_range_integers_percent](\docs\checks\column\numeric\values-in-range-integers-percent)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValuesInRangeIntegersPercentCheckSpec](\docs\checks\column\numeric\values-in-range-integers-percent)| | | |
|[daily_value_below_min_value_count](\docs\checks\column\numeric\value-below-min-value-count)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValueBelowMinValueCountCheckSpec](\docs\checks\column\numeric\value-below-min-value-count)| | | |
|[daily_value_below_min_value_percent](\docs\checks\column\numeric\value-below-min-value-percent)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValueBelowMinValuePercentCheckSpec](\docs\checks\column\numeric\value-below-min-value-percent)| | | |
|[daily_value_above_max_value_count](\docs\checks\column\numeric\value-above-max-value-count)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValueAboveMaxValueCountCheckSpec](\docs\checks\column\numeric\value-above-max-value-count)| | | |
|[daily_value_above_max_value_percent](\docs\checks\column\numeric\value-above-max-value-percent)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValueAboveMaxValuePercentCheckSpec](\docs\checks\column\numeric\value-above-max-value-percent)| | | |
|[daily_max_in_range](\docs\checks\column\numeric\max-in-range)|Verifies that the maximal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnMaxInRangeCheckSpec](\docs\checks\column\numeric\max-in-range)| | | |
|[daily_min_in_range](\docs\checks\column\numeric\min-in-range)|Verifies that the minimal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnMinInRangeCheckSpec](\docs\checks\column\numeric\min-in-range)| | | |
|[daily_mean_in_range](\docs\checks\column\numeric\mean-in-range)|Verifies that the average (mean) of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnMeanInRangeCheckSpec](\docs\checks\column\numeric\mean-in-range)| | | |
|[daily_percentile_in_range](\docs\checks\column\numeric\percentile-in-range)|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentileInRangeCheckSpec](\docs\checks\column\numeric\percentile-in-range)| | | |
|[daily_median_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMedianInRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnmedianinrangecheckspec)| | | |
|[daily_percentile_10_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentile10InRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile10inrangecheckspec)| | | |
|[daily_percentile_25_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentile25InRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile25inrangecheckspec)| | | |
|[daily_percentile_75_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentile75InRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile75inrangecheckspec)| | | |
|[daily_percentile_90_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentile90InRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile90inrangecheckspec)| | | |
|[daily_sample_stddev_in_range](\docs\checks\column\numeric\sample-stddev-in-range)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSampleStddevInRangeCheckSpec](\docs\checks\column\numeric\sample-stddev-in-range)| | | |
|[daily_population_stddev_in_range](\docs\checks\column\numeric\population-stddev-in-range)|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPopulationStddevInRangeCheckSpec](\docs\checks\column\numeric\population-stddev-in-range)| | | |
|[daily_sample_variance_in_range](\docs\checks\column\numeric\sample-variance-in-range)|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSampleVarianceInRangeCheckSpec](\docs\checks\column\numeric\sample-variance-in-range)| | | |
|[daily_population_variance_in_range](\docs\checks\column\numeric\population-variance-in-range)|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPopulationVarianceInRangeCheckSpec](\docs\checks\column\numeric\population-variance-in-range)| | | |
|[daily_sum_in_range](\docs\checks\column\numeric\sum-in-range)|Verifies that the sum of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSumInRangeCheckSpec](\docs\checks\column\numeric\sum-in-range)| | | |
|[daily_invalid_latitude_count](\docs\checks\column\numeric\invalid-latitude-count)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnInvalidLatitudeCountCheckSpec](\docs\checks\column\numeric\invalid-latitude-count)| | | |
|[daily_valid_latitude_percent](\docs\checks\column\numeric\valid-latitude-percent)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValidLatitudePercentCheckSpec](\docs\checks\column\numeric\valid-latitude-percent)| | | |
|[daily_invalid_longitude_count](\docs\checks\column\numeric\invalid-longitude-count)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnInvalidLongitudeCountCheckSpec](\docs\checks\column\numeric\invalid-longitude-count)| | | |
|[daily_valid_longitude_percent](\docs\checks\column\numeric\valid-longitude-percent)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValidLongitudePercentCheckSpec](\docs\checks\column\numeric\valid-longitude-percent)| | | |









___  

## ColumnAnomalyDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_mean_anomaly_stationary_30_days](\docs\checks\column\anomaly\mean-anomaly-stationary-30-days)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyStationaryMean30DaysCheckSpec](\docs\checks\column\anomaly\mean-anomaly-stationary-30-days)| | | |
|[daily_mean_anomaly_stationary](\docs\checks\column\anomaly\mean-anomaly-stationary)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnAnomalyStationaryMeanCheckSpec](\docs\checks\column\anomaly\mean-anomaly-stationary)| | | |
|[daily_median_anomaly_stationary_30_days](\docs\checks\column\anomaly\median-anomaly-stationary-30-days)|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyStationaryMedian30DaysCheckSpec](\docs\checks\column\anomaly\median-anomaly-stationary-30-days)| | | |
|[daily_median_anomaly_stationary](\docs\checks\column\anomaly\median-anomaly-stationary)|Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnAnomalyStationaryMedianCheckSpec](\docs\checks\column\anomaly\median-anomaly-stationary)| | | |
|[daily_sum_anomaly_differencing_30_days](\docs\checks\column\anomaly\sum-anomaly-differencing-30-days)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyDifferencingSum30DaysCheckSpec](\docs\checks\column\anomaly\sum-anomaly-differencing-30-days)| | | |
|[daily_sum_anomaly_differencing](\docs\checks\column\anomaly\sum-anomaly-differencing)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnAnomalyDifferencingSumCheckSpec](\docs\checks\column\anomaly\sum-anomaly-differencing)| | | |
|[daily_mean_change](\docs\checks\column\anomaly\mean-change)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](\docs\checks\column\anomaly\mean-change)| | | |
|[daily_mean_change_yesterday](\docs\checks\column\anomaly\mean-change-yesterday)|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMeanSinceYesterdayCheckSpec](\docs\checks\column\anomaly\mean-change-yesterday)| | | |
|[daily_mean_change_7_days](\docs\checks\column\anomaly\mean-change-7-days)|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMeanSince7DaysCheckSpec](\docs\checks\column\anomaly\mean-change-7-days)| | | |
|[daily_mean_change_30_days](\docs\checks\column\anomaly\mean-change-30-days)|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMeanSince30DaysCheckSpec](\docs\checks\column\anomaly\mean-change-30-days)| | | |
|[daily_median_change](\docs\checks\column\anomaly\median-change)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](\docs\checks\column\anomaly\median-change)| | | |
|[daily_median_change_yesterday](\docs\checks\column\anomaly\median-change-yesterday)|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMedianSinceYesterdayCheckSpec](\docs\checks\column\anomaly\median-change-yesterday)| | | |
|[daily_median_change_7_days](\docs\checks\column\anomaly\median-change-7-days)|Verifies that the median in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMedianSince7DaysCheckSpec](\docs\checks\column\anomaly\median-change-7-days)| | | |
|[daily_median_change_30_days](\docs\checks\column\anomaly\median-change-30-days)|Verifies that the median in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMedianSince30DaysCheckSpec](\docs\checks\column\anomaly\median-change-30-days)| | | |
|[daily_sum_change](\docs\checks\column\anomaly\sum-change)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](\docs\checks\column\anomaly\sum-change)| | | |
|[daily_sum_change_yesterday](\docs\checks\column\anomaly\sum-change-yesterday)|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeSumSinceYesterdayCheckSpec](\docs\checks\column\anomaly\sum-change-yesterday)| | | |
|[daily_sum_change_7_days](\docs\checks\column\anomaly\sum-change-7-days)|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|[ColumnChangeSumSince7DaysCheckSpec](\docs\checks\column\anomaly\sum-change-7-days)| | | |
|[daily_sum_change_30_days](\docs\checks\column\anomaly\sum-change-30-days)|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|[ColumnChangeSumSince30DaysCheckSpec](\docs\checks\column\anomaly\sum-change-30-days)| | | |









___  

## ColumnNullsDailyRecurringChecksSpec  
Container of nulls data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_nulls_count](\docs\checks\column\nulls\nulls-count)|Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNullsCountCheckSpec](\docs\checks\column\nulls\nulls-count)| | | |
|[daily_nulls_percent](\docs\checks\column\nulls\nulls-percent)|Verifies that the percentage of nulls in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNullsPercentCheckSpec](\docs\checks\column\nulls\nulls-percent)| | | |
|[daily_not_nulls_count](\docs\checks\column\nulls\not-nulls-count)|Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNotNullsCountCheckSpec](\docs\checks\column\nulls\not-nulls-count)| | | |
|[daily_not_nulls_percent](\docs\checks\column\nulls\not-nulls-percent)|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNotNullsPercentCheckSpec](\docs\checks\column\nulls\not-nulls-percent)| | | |









___  

## ColumnComparisonDailyRecurringChecksSpec  
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 This is the configuration for daily recurring checks that are counted in KPIs.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_sum_match](\docs\checks\column\comparisons\sum-match)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnComparisonSumMatchCheckSpec](\docs\checks\column\comparisons\sum-match)| | | |
|[daily_min_match](\docs\checks\column\comparisons\min-match)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnComparisonMinMatchCheckSpec](\docs\checks\column\comparisons\min-match)| | | |
|[daily_max_match](\docs\checks\column\comparisons\max-match)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnComparisonMaxMatchCheckSpec](\docs\checks\column\comparisons\max-match)| | | |
|[daily_mean_match](\docs\checks\column\comparisons\mean-match)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnComparisonMeanMatchCheckSpec](\docs\checks\column\comparisons\mean-match)| | | |
|[daily_not_null_count_match](\docs\checks\column\comparisons\not-null-count-match)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnComparisonNotNullCountMatchCheckSpec](\docs\checks\column\comparisons\not-null-count-match)| | | |
|[daily_null_count_match](\docs\checks\column\comparisons\null-count-match)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnComparisonNullCountMatchCheckSpec](\docs\checks\column\comparisons\null-count-match)| | | |
|reference_column|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|string| | | |









___  

## ColumnSchemaDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking the column schema at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_column_exists](\docs\checks\column\schema\column-exists)|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.|[ColumnSchemaColumnExistsCheckSpec](\docs\checks\column\schema\column-exists)| | | |
|[daily_column_type_changed](\docs\checks\column\schema\column-type-changed)|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last day. Stores the most recent hash for each day when the data quality check was evaluated.|[ColumnSchemaTypeChangedCheckSpec](\docs\checks\column\schema\column-type-changed)| | | |









___  

## ColumnStringsDailyRecurringChecksSpec  
Container of strings data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_string_max_length](\docs\checks\column\strings\string-max-length)|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMaxLengthCheckSpec](\docs\checks\column\strings\string-max-length)| | | |
|[daily_string_min_length](\docs\checks\column\strings\string-min-length)|Verifies that the length of string in a column does not fall below the minimum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMinLengthCheckSpec](\docs\checks\column\strings\string-min-length)| | | |
|[daily_string_mean_length](\docs\checks\column\strings\string-mean-length)|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMeanLengthCheckSpec](\docs\checks\column\strings\string-mean-length)| | | |
|[daily_string_length_below_min_length_count](\docs\checks\column\strings\string-length-below-min-length-count)|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthCountCheckSpec](\docs\checks\column\strings\string-length-below-min-length-count)| | | |
|[daily_string_length_below_min_length_percent](\docs\checks\column\strings\string-length-below-min-length-percent)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](\docs\checks\column\strings\string-length-below-min-length-percent)| | | |
|[daily_string_length_above_max_length_count](\docs\checks\column\strings\string-length-above-max-length-count)|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](\docs\checks\column\strings\string-length-above-max-length-count)| | | |
|[daily_string_length_above_max_length_percent](\docs\checks\column\strings\string-length-above-max-length-percent)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](\docs\checks\column\strings\string-length-above-max-length-percent)| | | |
|[daily_string_length_in_range_percent](\docs\checks\column\strings\string-length-in-range-percent)|The check counts the percentage of those strings with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthInRangePercentCheckSpec](\docs\checks\column\strings\string-length-in-range-percent)| | | |
|[daily_string_empty_count](\docs\checks\column\strings\string-empty-count)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringEmptyCountCheckSpec](\docs\checks\column\strings\string-empty-count)| | | |
|[daily_string_empty_percent](\docs\checks\column\strings\string-empty-percent)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringEmptyPercentCheckSpec](\docs\checks\column\strings\string-empty-percent)| | | |
|[daily_string_whitespace_count](\docs\checks\column\strings\string-whitespace-count)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringWhitespaceCountCheckSpec](\docs\checks\column\strings\string-whitespace-count)| | | |
|[daily_string_whitespace_percent](\docs\checks\column\strings\string-whitespace-percent)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringWhitespacePercentCheckSpec](\docs\checks\column\strings\string-whitespace-percent)| | | |
|[daily_string_surrounded_by_whitespace_count](\docs\checks\column\strings\string-surrounded-by-whitespace-count)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](\docs\checks\column\strings\string-surrounded-by-whitespace-count)| | | |
|[daily_string_surrounded_by_whitespace_percent](\docs\checks\column\strings\string-surrounded-by-whitespace-percent)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](\docs\checks\column\strings\string-surrounded-by-whitespace-percent)| | | |
|[daily_string_null_placeholder_count](\docs\checks\column\strings\string-null-placeholder-count)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringNullPlaceholderCountCheckSpec](\docs\checks\column\strings\string-null-placeholder-count)| | | |
|[daily_string_null_placeholder_percent](\docs\checks\column\strings\string-null-placeholder-percent)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringNullPlaceholderPercentCheckSpec](\docs\checks\column\strings\string-null-placeholder-percent)| | | |
|[daily_string_boolean_placeholder_percent](\docs\checks\column\strings\string-boolean-placeholder-percent)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringBooleanPlaceholderPercentCheckSpec](\docs\checks\column\strings\string-boolean-placeholder-percent)| | | |
|[daily_string_parsable_to_integer_percent](\docs\checks\column\strings\string-parsable-to-integer-percent)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringParsableToIntegerPercentCheckSpec](\docs\checks\column\strings\string-parsable-to-integer-percent)| | | |
|[daily_string_parsable_to_float_percent](\docs\checks\column\strings\string-parsable-to-float-percent)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringParsableToFloatPercentCheckSpec](\docs\checks\column\strings\string-parsable-to-float-percent)| | | |
|[daily_expected_strings_in_use_count](\docs\checks\column\strings\expected-strings-in-use-count)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnExpectedStringsInUseCountCheckSpec](\docs\checks\column\strings\expected-strings-in-use-count)| | | |
|[daily_string_value_in_set_percent](\docs\checks\column\strings\string-value-in-set-percent)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValueInSetPercentCheckSpec](\docs\checks\column\strings\string-value-in-set-percent)| | | |
|[daily_string_valid_dates_percent](\docs\checks\column\strings\string-valid-dates-percent)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValidDatesPercentCheckSpec](\docs\checks\column\strings\string-valid-dates-percent)| | | |
|[daily_string_valid_country_code_percent](\docs\checks\column\strings\string-valid-country-code-percent)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValidCountryCodePercentCheckSpec](\docs\checks\column\strings\string-valid-country-code-percent)| | | |
|[daily_string_valid_currency_code_percent](\docs\checks\column\strings\string-valid-currency-code-percent)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValidCurrencyCodePercentCheckSpec](\docs\checks\column\strings\string-valid-currency-code-percent)| | | |
|[daily_string_invalid_email_count](\docs\checks\column\strings\string-invalid-email-count)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringInvalidEmailCountCheckSpec](\docs\checks\column\strings\string-invalid-email-count)| | | |
|[daily_string_invalid_uuid_count](\docs\checks\column\strings\string-invalid-uuid-count)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringInvalidUuidCountCheckSpec](\docs\checks\column\strings\string-invalid-uuid-count)| | | |
|[daily_string_valid_uuid_percent](\docs\checks\column\strings\string-valid-uuid-percent)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValidUuidPercentCheckSpec](\docs\checks\column\strings\string-valid-uuid-percent)| | | |
|[daily_string_invalid_ip4_address_count](\docs\checks\column\strings\string-invalid-ip4-address-count)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringInvalidIp4AddressCountCheckSpec](\docs\checks\column\strings\string-invalid-ip4-address-count)| | | |
|[daily_string_invalid_ip6_address_count](\docs\checks\column\strings\string-invalid-ip6-address-count)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringInvalidIp6AddressCountCheckSpec](\docs\checks\column\strings\string-invalid-ip6-address-count)| | | |
|[daily_string_not_match_regex_count](\docs\checks\column\strings\string-not-match-regex-count)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringNotMatchRegexCountCheckSpec](\docs\checks\column\strings\string-not-match-regex-count)| | | |
|[daily_string_match_regex_percent](\docs\checks\column\strings\string-match-regex-percent)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMatchRegexPercentCheckSpec](\docs\checks\column\strings\string-match-regex-percent)| | | |
|[daily_string_not_match_date_regex_count](\docs\checks\column\strings\string-not-match-date-regex-count)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringNotMatchDateRegexCountCheckSpec](\docs\checks\column\strings\string-not-match-date-regex-count)| | | |
|[daily_string_match_date_regex_percent](\docs\checks\column\strings\string-match-date-regex-percent)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMatchDateRegexPercentCheckSpec](\docs\checks\column\strings\string-match-date-regex-percent)| | | |
|[daily_string_match_name_regex_percent](\docs\checks\column\strings\string-match-name-regex-percent)|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMatchNameRegexPercentCheckSpec](\docs\checks\column\strings\string-match-name-regex-percent)| | | |
|[daily_expected_strings_in_top_values_count](\docs\checks\column\strings\expected-strings-in-top-values-count)|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnExpectedStringsInTopValuesCountCheckSpec](\docs\checks\column\strings\expected-strings-in-top-values-count)| | | |
|[daily_string_datatype_detected](\docs\checks\column\strings\string-datatype-detected)|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringDatatypeDetectedCheckSpec](\docs\checks\column\strings\string-datatype-detected)| | | |









___  

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
|[comparisons](#columncomparisondailyrecurringchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonDailyRecurringChecksSpecMap](#columncomparisondailyrecurringchecksspecmap)| | | |
|[custom](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)| | | |









___  

## ColumnBoolDailyRecurringChecksSpec  
Container of boolean data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_true_percent](\docs\checks\column\bool\true-percent)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnTruePercentCheckSpec](\docs\checks\column\bool\true-percent)| | | |
|[daily_false_percent](\docs\checks\column\bool\false-percent)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnFalsePercentCheckSpec](\docs\checks\column\bool\false-percent)| | | |









___  

## ColumnIntegrityDailyRecurringChecksSpec  
Container of integrity data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_foreign_key_not_match_count](\docs\checks\column\integrity\foreign-key-not-match-count)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](\docs\checks\column\integrity\foreign-key-not-match-count)| | | |
|[daily_foreign_key_match_percent](\docs\checks\column\integrity\foreign-key-match-percent)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](\docs\checks\column\integrity\foreign-key-match-percent)| | | |









___  

## ColumnConsistencyDailyRecurringChecksSpec  
Container of consistency data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_date_match_format_percent](\docs\checks\column\consistency\date-match-format-percent)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily recurring.|[ColumnConsistencyDateMatchFormatPercentCheckSpec](\docs\checks\column\consistency\date-match-format-percent)| | | |
|[daily_string_datatype_changed](\docs\checks\column\consistency\string-datatype-changed)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringDatatypeChangedCheckSpec](\docs\checks\column\consistency\string-datatype-changed)| | | |









___  

## ColumnDatetimeDailyRecurringChecksSpec  
Container of date-time data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_date_values_in_future_percent](\docs\checks\column\datetime\date-values-in-future-percent)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDateValuesInFuturePercentCheckSpec](\docs\checks\column\datetime\date-values-in-future-percent)| | | |
|[daily_datetime_value_in_range_date_percent](\docs\checks\column\datetime\datetime-value-in-range-date-percent)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](\docs\checks\column\datetime\datetime-value-in-range-date-percent)| | | |









___  

## ColumnSqlDailyRecurringChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_sql_condition_passed_percent_on_column](\docs\checks\column\sql\sql-condition-passed-percent-on-column)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSqlConditionPassedPercentCheckSpec](\docs\checks\column\sql\sql-condition-passed-percent-on-column)| | | |
|[daily_sql_condition_failed_count_on_column](\docs\checks\column\sql\sql-condition-failed-count-on-column)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSqlConditionFailedCountCheckSpec](\docs\checks\column\sql\sql-condition-failed-count-on-column)| | | |
|[daily_sql_aggregate_expr_column](\docs\checks\column\sql\sql-aggregate-expr-column)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSqlAggregateExprCheckSpec](\docs\checks\column\sql\sql-aggregate-expr-column)| | | |









___  

## ColumnComparisonDailyRecurringChecksSpecMap  
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

## ColumnUniquenessDailyRecurringChecksSpec  
Container of uniqueness data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_distinct_count](\docs\checks\column\uniqueness\distinct-count)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDistinctCountCheckSpec](\docs\checks\column\uniqueness\distinct-count)| | | |
|[daily_distinct_percent](\docs\checks\column\uniqueness\distinct-percent)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDistinctPercentCheckSpec](\docs\checks\column\uniqueness\distinct-percent)| | | |
|[daily_duplicate_count](\docs\checks\column\uniqueness\duplicate-count)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDuplicateCountCheckSpec](\docs\checks\column\uniqueness\duplicate-count)| | | |
|[daily_duplicate_percent](\docs\checks\column\uniqueness\duplicate-percent)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDuplicatePercentCheckSpec](\docs\checks\column\uniqueness\duplicate-percent)| | | |









___  

## ColumnAccuracyDailyRecurringChecksSpec  
Container of accuracy data quality recurring checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_total_sum_match_percent](\docs\checks\column\accuracy\total-sum-match-percent)|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyTotalSumMatchPercentCheckSpec](\docs\checks\column\accuracy\total-sum-match-percent)| | | |
|[daily_total_min_match_percent](\docs\checks\column\accuracy\total-min-match-percent)|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyTotalMinMatchPercentCheckSpec](\docs\checks\column\accuracy\total-min-match-percent)| | | |
|[daily_total_max_match_percent](\docs\checks\column\accuracy\total-max-match-percent)|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyTotalMaxMatchPercentCheckSpec](\docs\checks\column\accuracy\total-max-match-percent)| | | |
|[daily_total_average_match_percent](\docs\checks\column\accuracy\total-average-match-percent)|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyTotalAverageMatchPercentCheckSpec](\docs\checks\column\accuracy\total-average-match-percent)| | | |
|[daily_total_not_null_count_match_percent](\docs\checks\column\accuracy\total-not-null-count-match-percent)|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec](\docs\checks\column\accuracy\total-not-null-count-match-percent)| | | |









___  

