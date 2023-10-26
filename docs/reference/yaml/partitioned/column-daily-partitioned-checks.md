
## AnomalyStationaryPercentileMovingAverageRule05ParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|anomaly_percent|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a time window of 90 periods (days, etc.), but at least 30 readouts must exist to run the calculation. You can change the default value by modifying prediction_time_window parameterin Definitions section.|double| | |0.1<br/>|









___  

## ColumnUniquenessDailyPartitionedChecksSpec  
Container of uniqueness data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_distinct_count](\docs\checks\column\uniqueness\distinct-count)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDistinctCountCheckSpec](\docs\checks\column\uniqueness\distinct-count)| | | |
|[daily_partition_distinct_percent](\docs\checks\column\uniqueness\distinct-percent)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDistinctPercentCheckSpec](\docs\checks\column\uniqueness\distinct-percent)| | | |
|[daily_partition_duplicate_count](\docs\checks\column\uniqueness\duplicate-count)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDuplicateCountCheckSpec](\docs\checks\column\uniqueness\duplicate-count)| | | |
|[daily_partition_duplicate_percent](\docs\checks\column\uniqueness\duplicate-percent)|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDuplicatePercentCheckSpec](\docs\checks\column\uniqueness\duplicate-percent)| | | |
|[daily_partition_anomaly_stationary_distinct_count_30_days](#columnanomalystationarypartitiondistinctcount30dayscheckspec)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec](#columnanomalystationarypartitiondistinctcount30dayscheckspec)| | | |
|[daily_partition_anomaly_stationary_distinct_count](#columnanomalystationarypartitiondistinctcountcheckspec)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryPartitionDistinctCountCheckSpec](#columnanomalystationarypartitiondistinctcountcheckspec)| | | |
|[daily_partition_anomaly_stationary_distinct_percent_30_days](\docs\checks\column\uniqueness\anomaly-stationary-distinct-percent-30-days)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec](\docs\checks\column\uniqueness\anomaly-stationary-distinct-percent-30-days)| | | |
|[daily_partition_anomaly_stationary_distinct_percent](\docs\checks\column\uniqueness\anomaly-stationary-distinct-percent)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryDistinctPercentCheckSpec](\docs\checks\column\uniqueness\anomaly-stationary-distinct-percent)| | | |
|[daily_partition_change_distinct_count](\docs\checks\column\uniqueness\change-distinct-count)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctCountCheckSpec](\docs\checks\column\uniqueness\change-distinct-count)| | | |
|[daily_partition_change_distinct_count_since_7_days](\docs\checks\column\uniqueness\change-distinct-count-since-7-days)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctCountSince7DaysCheckSpec](\docs\checks\column\uniqueness\change-distinct-count-since-7-days)| | | |
|[daily_partition_change_distinct_count_since_30_days](\docs\checks\column\uniqueness\change-distinct-count-since-30-days)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctCountSince30DaysCheckSpec](\docs\checks\column\uniqueness\change-distinct-count-since-30-days)| | | |
|[daily_partition_change_distinct_count_since_yesterday](\docs\checks\column\uniqueness\change-distinct-count-since-yesterday)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctCountSinceYesterdayCheckSpec](\docs\checks\column\uniqueness\change-distinct-count-since-yesterday)| | | |
|[daily_partition_change_distinct_percent](\docs\checks\column\uniqueness\change-distinct-percent)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctPercentCheckSpec](\docs\checks\column\uniqueness\change-distinct-percent)| | | |
|[daily_partition_change_distinct_percent_since_7_days](\docs\checks\column\uniqueness\change-distinct-percent-since-7-days)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctPercentSince7DaysCheckSpec](\docs\checks\column\uniqueness\change-distinct-percent-since-7-days)| | | |
|[daily_partition_change_distinct_percent_since_30_days](\docs\checks\column\uniqueness\change-distinct-percent-since-30-days)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctPercentSince30DaysCheckSpec](\docs\checks\column\uniqueness\change-distinct-percent-since-30-days)| | | |
|[daily_partition_change_distinct_percent_since_yesterday](\docs\checks\column\uniqueness\change-distinct-percent-since-yesterday)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctPercentSinceYesterdayCheckSpec](\docs\checks\column\uniqueness\change-distinct-percent-since-yesterday)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnPiiDailyPartitionedChecksSpec  
Container of PII data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_contains_usa_phone_percent](\docs\checks\column\pii\contains-usa-phone-percent)|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsUsaPhonePercentCheckSpec](\docs\checks\column\pii\contains-usa-phone-percent)| | | |
|[daily_partition_contains_usa_zipcode_percent](\docs\checks\column\pii\contains-usa-zipcode-percent)|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](\docs\checks\column\pii\contains-usa-zipcode-percent)| | | |
|[daily_partition_contains_email_percent](\docs\checks\column\pii\contains-email-percent)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsEmailPercentCheckSpec](\docs\checks\column\pii\contains-email-percent)| | | |
|[daily_partition_contains_ip4_percent](\docs\checks\column\pii\contains-ip4-percent)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsIp4PercentCheckSpec](\docs\checks\column\pii\contains-ip4-percent)| | | |
|[daily_partition_contains_ip6_percent](\docs\checks\column\pii\contains-ip6-percent)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsIp6PercentCheckSpec](\docs\checks\column\pii\contains-ip6-percent)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnAnomalyDailyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_mean_anomaly_stationary_30_days](\docs\checks\column\anomaly\mean-anomaly-stationary-30-days)|Verifies that the mean value in a column is within a percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryMean30DaysCheckSpec](\docs\checks\column\anomaly\mean-anomaly-stationary-30-days)| | | |
|[daily_partition_mean_anomaly_stationary](\docs\checks\column\anomaly\mean-anomaly-stationary)|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryMeanCheckSpec](\docs\checks\column\anomaly\mean-anomaly-stationary)| | | |
|[daily_partition_median_anomaly_stationary_30_days](\docs\checks\column\anomaly\median-anomaly-stationary-30-days)|Verifies that the median in a column is within a percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryMedian30DaysCheckSpec](\docs\checks\column\anomaly\median-anomaly-stationary-30-days)| | | |
|[daily_partition_median_anomaly_stationary](\docs\checks\column\anomaly\median-anomaly-stationary)|Verifies that the median in a column is within a percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryMedianCheckSpec](\docs\checks\column\anomaly\median-anomaly-stationary)| | | |
|[daily_partition_sum_anomaly_stationary_30_days](\docs\checks\column\anomaly\sum-anomaly-stationary-30-days)|Verifies that the sum in a column is within a percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryPartitionSum30DaysCheckSpec](\docs\checks\column\anomaly\sum-anomaly-stationary-30-days)| | | |
|[daily_partition_sum_anomaly_stationary](\docs\checks\column\anomaly\sum-anomaly-stationary)|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryPartitionSumCheckSpec](\docs\checks\column\anomaly\sum-anomaly-stationary)| | | |
|[daily_partition_mean_change](\docs\checks\column\anomaly\mean-change)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](\docs\checks\column\anomaly\mean-change)| | | |
|[daily_partition_mean_change_yesterday](\docs\checks\column\anomaly\mean-change-yesterday)|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMeanSinceYesterdayCheckSpec](\docs\checks\column\anomaly\mean-change-yesterday)| | | |
|[daily_partition_mean_change_7_days](\docs\checks\column\anomaly\mean-change-7-days)|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMeanSince7DaysCheckSpec](\docs\checks\column\anomaly\mean-change-7-days)| | | |
|[daily_partition_mean_change_30_days](\docs\checks\column\anomaly\mean-change-30-days)|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMeanSince30DaysCheckSpec](\docs\checks\column\anomaly\mean-change-30-days)| | | |
|[daily_partition_median_change](\docs\checks\column\anomaly\median-change)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](\docs\checks\column\anomaly\median-change)| | | |
|[daily_partition_median_change_yesterday](\docs\checks\column\anomaly\median-change-yesterday)|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMedianSinceYesterdayCheckSpec](\docs\checks\column\anomaly\median-change-yesterday)| | | |
|[daily_partition_median_change_7_days](\docs\checks\column\anomaly\median-change-7-days)|Verifies that the median in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMedianSince7DaysCheckSpec](\docs\checks\column\anomaly\median-change-7-days)| | | |
|[daily_partition_median_change_30_days](\docs\checks\column\anomaly\median-change-30-days)|Verifies that the median in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMedianSince30DaysCheckSpec](\docs\checks\column\anomaly\median-change-30-days)| | | |
|[daily_partition_sum_change](\docs\checks\column\anomaly\sum-change)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](\docs\checks\column\anomaly\sum-change)| | | |
|[daily_partition_sum_change_yesterday](\docs\checks\column\anomaly\sum-change-yesterday)|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeSumSinceYesterdayCheckSpec](\docs\checks\column\anomaly\sum-change-yesterday)| | | |
|[daily_partition_sum_change_7_days](\docs\checks\column\anomaly\sum-change-7-days)|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|[ColumnChangeSumSince7DaysCheckSpec](\docs\checks\column\anomaly\sum-change-7-days)| | | |
|[daily_partition_sum_change_30_days](\docs\checks\column\anomaly\sum-change-30-days)|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|[ColumnChangeSumSince30DaysCheckSpec](\docs\checks\column\anomaly\sum-change-30-days)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

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
|[datatype](#columndatatypedailypartitionedchecksspec)|Daily partitioned checks for datatype in the column|[ColumnDatatypeDailyPartitionedChecksSpec](#columndatatypedailypartitionedchecksspec)| | | |
|[anomaly](#columnanomalydailypartitionedchecksspec)|Daily partitioned checks for anomaly in the column|[ColumnAnomalyDailyPartitionedChecksSpec](#columnanomalydailypartitionedchecksspec)| | | |
|[comparisons](#columncomparisondailypartitionedchecksspecmap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonDailyPartitionedChecksSpecMap](#columncomparisondailypartitionedchecksspecmap)| | | |
|[custom](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcheckspecmap)| | | |









___  

## ColumnBoolDailyPartitionedChecksSpec  
Container of boolean data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_true_percent](\docs\checks\column\bool\true-percent)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnTruePercentCheckSpec](\docs\checks\column\bool\true-percent)| | | |
|[daily_partition_false_percent](\docs\checks\column\bool\false-percent)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnFalsePercentCheckSpec](\docs\checks\column\bool\false-percent)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnNullsDailyPartitionedChecksSpec  
Container of nulls data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_nulls_count](\docs\checks\column\nulls\nulls-count)|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNullsCountCheckSpec](\docs\checks\column\nulls\nulls-count)| | | |
|[daily_partition_nulls_percent](\docs\checks\column\nulls\nulls-percent)|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNullsPercentCheckSpec](\docs\checks\column\nulls\nulls-percent)| | | |
|[daily_partition_nulls_percent_anomaly_stationary_30_days](\docs\checks\column\nulls\nulls-percent-anomaly-stationary-30-days)|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyStationaryNullPercent30DaysCheckSpec](\docs\checks\column\nulls\nulls-percent-anomaly-stationary-30-days)| | | |
|[daily_partition_nulls_percent_anomaly_stationary](\docs\checks\column\nulls\nulls-percent-anomaly-stationary)|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnAnomalyStationaryNullPercentCheckSpec](\docs\checks\column\nulls\nulls-percent-anomaly-stationary)| | | |
|[daily_partition_nulls_percent_change](\docs\checks\column\nulls\nulls-percent-change)|Verifies that the null percent value in a column changed in a fixed rate since last readout.|[ColumnChangeNullPercentCheckSpec](\docs\checks\column\nulls\nulls-percent-change)| | | |
|[daily_partition_nulls_percent_change_yesterday](\docs\checks\column\nulls\nulls-percent-change-yesterday)|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeNullPercentSinceYesterdayCheckSpec](\docs\checks\column\nulls\nulls-percent-change-yesterday)| | | |
|[daily_partition_nulls_percent_change_7_days](\docs\checks\column\nulls\nulls-percent-change-7-days)|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|[ColumnChangeNullPercentSince7DaysCheckSpec](\docs\checks\column\nulls\nulls-percent-change-7-days)| | | |
|[daily_partition_nulls_percent_change_30_days](\docs\checks\column\nulls\nulls-percent-change-30-days)|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|[ColumnChangeNullPercentSince30DaysCheckSpec](\docs\checks\column\nulls\nulls-percent-change-30-days)| | | |
|[daily_partition_not_nulls_count](\docs\checks\column\nulls\not-nulls-count)|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNotNullsCountCheckSpec](\docs\checks\column\nulls\not-nulls-count)| | | |
|[daily_partition_not_nulls_percent](\docs\checks\column\nulls\not-nulls-percent)|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNotNullsPercentCheckSpec](\docs\checks\column\nulls\not-nulls-percent)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## AnomalyStationaryPercentileMovingAverage30DaysRule1ParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|anomaly_percent|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|double| | |0.1<br/>|









___  

## ColumnComparisonDailyPartitionedChecksSpec  
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 This is the configuration for daily partitioned checks that are counted in KPIs.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_sum_match](\docs\checks\column\comparisons\sum-match)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonSumMatchCheckSpec](\docs\checks\column\comparisons\sum-match)| | | |
|[daily_partition_min_match](\docs\checks\column\comparisons\min-match)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMinMatchCheckSpec](\docs\checks\column\comparisons\min-match)| | | |
|[daily_partition_max_match](\docs\checks\column\comparisons\max-match)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMaxMatchCheckSpec](\docs\checks\column\comparisons\max-match)| | | |
|[daily_partition_mean_match](\docs\checks\column\comparisons\mean-match)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMeanMatchCheckSpec](\docs\checks\column\comparisons\mean-match)| | | |
|[daily_partition_not_null_count_match](\docs\checks\column\comparisons\not-null-count-match)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonNotNullCountMatchCheckSpec](\docs\checks\column\comparisons\not-null-count-match)| | | |
|[daily_partition_null_count_match](\docs\checks\column\comparisons\null-count-match)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonNullCountMatchCheckSpec](\docs\checks\column\comparisons\null-count-match)| | | |
|reference_column|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|string| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnStringsDailyPartitionedChecksSpec  
Container of strings data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_string_max_length](\docs\checks\column\strings\string-max-length)|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMaxLengthCheckSpec](\docs\checks\column\strings\string-max-length)| | | |
|[daily_partition_string_min_length](\docs\checks\column\strings\string-min-length)|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMinLengthCheckSpec](\docs\checks\column\strings\string-min-length)| | | |
|[daily_partition_string_mean_length](\docs\checks\column\strings\string-mean-length)|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMeanLengthCheckSpec](\docs\checks\column\strings\string-mean-length)| | | |
|[daily_partition_string_length_below_min_length_count](\docs\checks\column\strings\string-length-below-min-length-count)|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthBelowMinLengthCountCheckSpec](\docs\checks\column\strings\string-length-below-min-length-count)| | | |
|[daily_partition_string_length_below_min_length_percent](\docs\checks\column\strings\string-length-below-min-length-percent)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](\docs\checks\column\strings\string-length-below-min-length-percent)| | | |
|[daily_partition_string_length_above_max_length_count](\docs\checks\column\strings\string-length-above-max-length-count)|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](\docs\checks\column\strings\string-length-above-max-length-count)| | | |
|[daily_partition_string_length_above_max_length_percent](\docs\checks\column\strings\string-length-above-max-length-percent)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](\docs\checks\column\strings\string-length-above-max-length-percent)| | | |
|[daily_partition_string_length_in_range_percent](\docs\checks\column\strings\string-length-in-range-percent)|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthInRangePercentCheckSpec](\docs\checks\column\strings\string-length-in-range-percent)| | | |
|[daily_partition_string_empty_count](\docs\checks\column\strings\string-empty-count)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringEmptyCountCheckSpec](\docs\checks\column\strings\string-empty-count)| | | |
|[daily_partition_string_empty_percent](\docs\checks\column\strings\string-empty-percent)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringEmptyPercentCheckSpec](\docs\checks\column\strings\string-empty-percent)| | | |
|[daily_partition_string_whitespace_count](\docs\checks\column\strings\string-whitespace-count)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringWhitespaceCountCheckSpec](\docs\checks\column\strings\string-whitespace-count)| | | |
|[daily_partition_string_whitespace_percent](\docs\checks\column\strings\string-whitespace-percent)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringWhitespacePercentCheckSpec](\docs\checks\column\strings\string-whitespace-percent)| | | |
|[daily_partition_string_surrounded_by_whitespace_count](\docs\checks\column\strings\string-surrounded-by-whitespace-count)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](\docs\checks\column\strings\string-surrounded-by-whitespace-count)| | | |
|[daily_partition_string_surrounded_by_whitespace_percent](\docs\checks\column\strings\string-surrounded-by-whitespace-percent)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](\docs\checks\column\strings\string-surrounded-by-whitespace-percent)| | | |
|[daily_partition_string_null_placeholder_count](\docs\checks\column\strings\string-null-placeholder-count)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNullPlaceholderCountCheckSpec](\docs\checks\column\strings\string-null-placeholder-count)| | | |
|[daily_partition_string_null_placeholder_percent](\docs\checks\column\strings\string-null-placeholder-percent)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNullPlaceholderPercentCheckSpec](\docs\checks\column\strings\string-null-placeholder-percent)| | | |
|[daily_partition_string_boolean_placeholder_percent](\docs\checks\column\strings\string-boolean-placeholder-percent)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringBooleanPlaceholderPercentCheckSpec](\docs\checks\column\strings\string-boolean-placeholder-percent)| | | |
|[daily_partition_string_parsable_to_integer_percent](\docs\checks\column\strings\string-parsable-to-integer-percent)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringParsableToIntegerPercentCheckSpec](\docs\checks\column\strings\string-parsable-to-integer-percent)| | | |
|[daily_partition_string_parsable_to_float_percent](\docs\checks\column\strings\string-parsable-to-float-percent)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringParsableToFloatPercentCheckSpec](\docs\checks\column\strings\string-parsable-to-float-percent)| | | |
|[daily_partition_expected_strings_in_use_count](\docs\checks\column\strings\expected-strings-in-use-count)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnExpectedStringsInUseCountCheckSpec](\docs\checks\column\strings\expected-strings-in-use-count)| | | |
|[daily_partition_string_value_in_set_percent](\docs\checks\column\strings\string-value-in-set-percent)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValueInSetPercentCheckSpec](\docs\checks\column\strings\string-value-in-set-percent)| | | |
|[daily_partition_string_valid_dates_percent](\docs\checks\column\strings\string-valid-dates-percent)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidDatesPercentCheckSpec](\docs\checks\column\strings\string-valid-dates-percent)| | | |
|[daily_partition_string_valid_country_code_percent](\docs\checks\column\strings\string-valid-country-code-percent)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidCountryCodePercentCheckSpec](\docs\checks\column\strings\string-valid-country-code-percent)| | | |
|[daily_partition_string_valid_currency_code_percent](\docs\checks\column\strings\string-valid-currency-code-percent)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidCurrencyCodePercentCheckSpec](\docs\checks\column\strings\string-valid-currency-code-percent)| | | |
|[daily_partition_string_invalid_email_count](\docs\checks\column\strings\string-invalid-email-count)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidEmailCountCheckSpec](\docs\checks\column\strings\string-invalid-email-count)| | | |
|[daily_partition_string_invalid_uuid_count](\docs\checks\column\strings\string-invalid-uuid-count)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidUuidCountCheckSpec](\docs\checks\column\strings\string-invalid-uuid-count)| | | |
|[daily_partition_valid_uuid_percent](\docs\checks\column\strings\string-valid-uuid-percent)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidUuidPercentCheckSpec](\docs\checks\column\strings\string-valid-uuid-percent)| | | |
|[daily_partition_string_invalid_ip4_address_count](\docs\checks\column\strings\string-invalid-ip4-address-count)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidIp4AddressCountCheckSpec](\docs\checks\column\strings\string-invalid-ip4-address-count)| | | |
|[daily_partition_string_invalid_ip6_address_count](\docs\checks\column\strings\string-invalid-ip6-address-count)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidIp6AddressCountCheckSpec](\docs\checks\column\strings\string-invalid-ip6-address-count)| | | |
|[daily_partition_string_not_match_regex_count](\docs\checks\column\strings\string-not-match-regex-count)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNotMatchRegexCountCheckSpec](\docs\checks\column\strings\string-not-match-regex-count)| | | |
|[daily_partition_string_match_regex_percent](\docs\checks\column\strings\string-match-regex-percent)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMatchRegexPercentCheckSpec](\docs\checks\column\strings\string-match-regex-percent)| | | |
|[daily_partition_string_not_match_date_regex_count](\docs\checks\column\strings\string-not-match-date-regex-count)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNotMatchDateRegexCountCheckSpec](\docs\checks\column\strings\string-not-match-date-regex-count)| | | |
|[daily_partition_string_match_date_regex_percent](\docs\checks\column\strings\string-match-date-regex-percent)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMatchDateRegexPercentCheckSpec](\docs\checks\column\strings\string-match-date-regex-percent)| | | |
|[daily_partition_string_match_name_regex_percent](\docs\checks\column\strings\string-match-name-regex-percent)|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMatchNameRegexPercentCheckSpec](\docs\checks\column\strings\string-match-name-regex-percent)| | | |
|[daily_partition_expected_strings_in_top_values_count](\docs\checks\column\strings\expected-strings-in-top-values-count)|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnExpectedStringsInTopValuesCountCheckSpec](\docs\checks\column\strings\expected-strings-in-top-values-count)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnAnomalyStationaryPartitionDistinctCountCheckSpec  
Column-level check that ensures that the distinct count value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\uniqueness-column-sensors\#distinct-count)|Data quality check parameters|[ColumnUniquenessDistinctCountSensorParametersSpec](\docs\reference\sensors\column\uniqueness-column-sensors\#distinct-count)| | | |
|[warning](#anomalystationarypercentilemovingaveragerule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[AnomalyStationaryPercentileMovingAverageRule1ParametersSpec](#anomalystationarypercentilemovingaveragerule1parametersspec)| | | |
|[error](#anomalystationarypercentilemovingaveragerule05parametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[AnomalyStationaryPercentileMovingAverageRule05ParametersSpec](#anomalystationarypercentilemovingaveragerule05parametersspec)| | | |
|[fatal](\docs\reference\rules\percentile\#anomaly-stationary-percentile-moving-average)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[AnomalyStationaryPercentileMovingAverageRule01ParametersSpec](\docs\reference\rules\percentile\#anomaly-stationary-percentile-moving-average)| | | |
|[schedule_override](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)| | | |
|[comments](\docs\reference\yaml\profiling\table-profiling-checks\#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](\docs\reference\yaml\profiling\table-profiling-checks\#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___  

## AnomalyStationaryPercentileMovingAverageRule1ParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|anomaly_percent|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a time window of 90 periods (days, etc.), but at least 30 readouts must exist to run the calculation. You can change the default value by modifying prediction_time_window parameterin Definitions section.|double| | |0.1<br/>|









___  

## ColumnIntegrityDailyPartitionedChecksSpec  
Container of integrity data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_foreign_key_not_match_count](\docs\checks\column\integrity\foreign-key-not-match-count)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](\docs\checks\column\integrity\foreign-key-not-match-count)| | | |
|[daily_partition_foreign_key_match_percent](\docs\checks\column\integrity\foreign-key-match-percent)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](\docs\checks\column\integrity\foreign-key-match-percent)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnSqlDailyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_sql_condition_passed_percent_on_column](\docs\checks\column\sql\sql-condition-passed-percent-on-column)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlConditionPassedPercentCheckSpec](\docs\checks\column\sql\sql-condition-passed-percent-on-column)| | | |
|[daily_partition_sql_condition_failed_count_on_column](\docs\checks\column\sql\sql-condition-failed-count-on-column)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlConditionFailedCountCheckSpec](\docs\checks\column\sql\sql-condition-failed-count-on-column)| | | |
|[daily_partition_sql_aggregate_expr_column](\docs\checks\column\sql\sql-aggregate-expr-column)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlAggregateExprCheckSpec](\docs\checks\column\sql\sql-aggregate-expr-column)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnDatetimeDailyPartitionedChecksSpec  
Container of date-time data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_date_match_format_percent](\docs\checks\column\datetime\date-match-format-percent)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDatetimeDateMatchFormatPercentCheckSpec](\docs\checks\column\datetime\date-match-format-percent)| | | |
|[daily_partition_date_values_in_future_percent](\docs\checks\column\datetime\date-values-in-future-percent)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDateValuesInFuturePercentCheckSpec](\docs\checks\column\datetime\date-values-in-future-percent)| | | |
|[daily_partition_datetime_value_in_range_date_percent](\docs\checks\column\datetime\datetime-value-in-range-date-percent)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](\docs\checks\column\datetime\datetime-value-in-range-date-percent)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec  
Column-level check that ensures that the distinct count value in a monitored column is within a two-tailed percentile from measurements made during the last 30 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](\docs\reference\sensors\column\uniqueness-column-sensors\#distinct-count)|Data quality check parameters|[ColumnUniquenessDistinctCountSensorParametersSpec](\docs\reference\sensors\column\uniqueness-column-sensors\#distinct-count)| | | |
|[warning](#anomalystationarypercentilemovingaverage30daysrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[AnomalyStationaryPercentileMovingAverage30DaysRule1ParametersSpec](#anomalystationarypercentilemovingaverage30daysrule1parametersspec)| | | |
|[error](#anomalystationarypercentilemovingaverage30daysrule05parametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[AnomalyStationaryPercentileMovingAverage30DaysRule05ParametersSpec](#anomalystationarypercentilemovingaverage30daysrule05parametersspec)| | | |
|[fatal](\docs\reference\rules\percentile\#anomaly-stationary-percentile-moving-average-30-days)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[AnomalyStationaryPercentileMovingAverage30DaysRule01ParametersSpec](\docs\reference\rules\percentile\#anomaly-stationary-percentile-moving-average-30-days)| | | |
|[schedule_override](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](\docs\reference\yaml\profiling\table-profiling-checks\#monitoringschedulespec)| | | |
|[comments](\docs\reference\yaml\profiling\table-profiling-checks\#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](\docs\reference\yaml\profiling\table-profiling-checks\#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___  

## ColumnAccuracyDailyPartitionedChecksSpec  
Container of accuracy data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnNumericDailyPartitionedChecksSpec  
Container of numeric data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_negative_count](\docs\checks\column\numeric\negative-count)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNegativeCountCheckSpec](\docs\checks\column\numeric\negative-count)| | | |
|[daily_partition_negative_percent](\docs\checks\column\numeric\negative-percent)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNegativePercentCheckSpec](\docs\checks\column\numeric\negative-percent)| | | |
|[daily_partition_non_negative_count](\docs\checks\column\numeric\non-negative-count)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNonNegativeCountCheckSpec](\docs\checks\column\numeric\non-negative-count)| | | |
|[daily_partition_non_negative_percent](\docs\checks\column\numeric\non-negative-percent)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNonNegativePercentCheckSpec](\docs\checks\column\numeric\non-negative-percent)| | | |
|[daily_partition_expected_numbers_in_use_count](\docs\checks\column\numeric\expected-numbers-in-use-count)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnExpectedNumbersInUseCountCheckSpec](\docs\checks\column\numeric\expected-numbers-in-use-count)| | | |
|[daily_partition_number_value_in_set_percent](\docs\checks\column\numeric\number-value-in-set-percent)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNumberValueInSetPercentCheckSpec](\docs\checks\column\numeric\number-value-in-set-percent)| | | |
|[daily_partition_values_in_range_numeric_percent](\docs\checks\column\numeric\values-in-range-numeric-percent)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValuesInRangeNumericPercentCheckSpec](\docs\checks\column\numeric\values-in-range-numeric-percent)| | | |
|[daily_partition_values_in_range_integers_percent](\docs\checks\column\numeric\values-in-range-integers-percent)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValuesInRangeIntegersPercentCheckSpec](\docs\checks\column\numeric\values-in-range-integers-percent)| | | |
|[daily_partition_value_below_min_value_count](\docs\checks\column\numeric\value-below-min-value-count)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueBelowMinValueCountCheckSpec](\docs\checks\column\numeric\value-below-min-value-count)| | | |
|[daily_partition_value_below_min_value_percent](\docs\checks\column\numeric\value-below-min-value-percent)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueBelowMinValuePercentCheckSpec](\docs\checks\column\numeric\value-below-min-value-percent)| | | |
|[daily_partition_value_above_max_value_count](\docs\checks\column\numeric\value-above-max-value-count)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueAboveMaxValueCountCheckSpec](\docs\checks\column\numeric\value-above-max-value-count)| | | |
|[daily_partition_value_above_max_value_percent](\docs\checks\column\numeric\value-above-max-value-percent)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueAboveMaxValuePercentCheckSpec](\docs\checks\column\numeric\value-above-max-value-percent)| | | |
|[daily_partition_max_in_range](\docs\checks\column\numeric\max-in-range)|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMaxInRangeCheckSpec](\docs\checks\column\numeric\max-in-range)| | | |
|[daily_partition_min_in_range](\docs\checks\column\numeric\min-in-range)|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMinInRangeCheckSpec](\docs\checks\column\numeric\min-in-range)| | | |
|[daily_partition_mean_in_range](\docs\checks\column\numeric\mean-in-range)|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMeanInRangeCheckSpec](\docs\checks\column\numeric\mean-in-range)| | | |
|[daily_partition_percentile_in_range](\docs\checks\column\numeric\percentile-in-range)|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentileInRangeCheckSpec](\docs\checks\column\numeric\percentile-in-range)| | | |
|[daily_partition_median_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMedianInRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnmedianinrangecheckspec)| | | |
|[daily_partition_percentile_10_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile10InRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile10inrangecheckspec)| | | |
|[daily_partition_percentile_25_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile25InRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile25inrangecheckspec)| | | |
|[daily_partition_percentile_75_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile75InRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile75inrangecheckspec)| | | |
|[daily_partition_percentile_90_in_range](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile90InRangeCheckSpec](\docs\reference\yaml\profiling\column-profiling-checks\#columnpercentile90inrangecheckspec)| | | |
|[daily_partition_sample_stddev_in_range](\docs\checks\column\numeric\sample-stddev-in-range)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSampleStddevInRangeCheckSpec](\docs\checks\column\numeric\sample-stddev-in-range)| | | |
|[daily_partition_population_stddev_in_range](\docs\checks\column\numeric\population-stddev-in-range)|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPopulationStddevInRangeCheckSpec](\docs\checks\column\numeric\population-stddev-in-range)| | | |
|[daily_partition_sample_variance_in_range](\docs\checks\column\numeric\sample-variance-in-range)|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSampleVarianceInRangeCheckSpec](\docs\checks\column\numeric\sample-variance-in-range)| | | |
|[daily_partition_population_variance_in_range](\docs\checks\column\numeric\population-variance-in-range)|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPopulationVarianceInRangeCheckSpec](\docs\checks\column\numeric\population-variance-in-range)| | | |
|[daily_partition_sum_in_range](\docs\checks\column\numeric\sum-in-range)|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSumInRangeCheckSpec](\docs\checks\column\numeric\sum-in-range)| | | |
|[daily_partition_invalid_latitude_count](\docs\checks\column\numeric\invalid-latitude-count)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnInvalidLatitudeCountCheckSpec](\docs\checks\column\numeric\invalid-latitude-count)| | | |
|[daily_partition_valid_latitude_percent](\docs\checks\column\numeric\valid-latitude-percent)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValidLatitudePercentCheckSpec](\docs\checks\column\numeric\valid-latitude-percent)| | | |
|[daily_partition_invalid_longitude_count](\docs\checks\column\numeric\invalid-longitude-count)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnInvalidLongitudeCountCheckSpec](\docs\checks\column\numeric\invalid-longitude-count)| | | |
|[daily_partition_valid_longitude_percent](\docs\checks\column\numeric\valid-longitude-percent)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValidLongitudePercentCheckSpec](\docs\checks\column\numeric\valid-longitude-percent)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## ColumnComparisonDailyPartitionedChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [ColumnComparisonDailyPartitionedChecksSpec](#columncomparisondailypartitionedchecksspec)]| | | |









___  

## ColumnDatatypeDailyPartitionedChecksSpec  
Container of datatype data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_string_datatype_detected](\docs\checks\column\datatype\string-datatype-detected)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDatatypeStringDatatypeDetectedCheckSpec](\docs\checks\column\datatype\string-datatype-detected)| | | |
|[daily_partition_string_datatype_changed](\docs\checks\column\datatype\string-datatype-changed)|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDatatypeStringDatatypeChangedCheckSpec](\docs\checks\column\datatype\string-datatype-changed)| | | |
|[custom_checks](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](\docs\reference\yaml\profiling\table-profiling-checks\#customcategorycheckspecmap)| | | |









___  

## AnomalyStationaryPercentileMovingAverage30DaysRule05ParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|anomaly_percent|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|double| | |0.1<br/>|









___  

