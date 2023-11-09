
## ColumnPercentile25InRangeCheckSpec  
Column level check that ensures that the percentile 25 of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../column-profiling-checks/#ColumnNumericPercentile25SensorParametersSpec)|Data quality check parameters|[ColumnNumericPercentile25SensorParametersSpec](../column-profiling-checks/#ColumnNumericPercentile25SensorParametersSpec)| | | |
|[warning](../../../rules/Comparison/#between-floats)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[error](../../../rules/Comparison/#between-floats)|Default alerting threshold for a percentile 25 in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[fatal](../../../rules/Comparison/#between-floats)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[schedule_override](../table-profiling-checks/#MonitoringScheduleSpec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](../table-profiling-checks/#MonitoringScheduleSpec)| | | |
|[comments](../table-profiling-checks/#CommentsListSpec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](../table-profiling-checks/#CommentsListSpec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___  

## ColumnProfilingCheckCategoriesSpec  
Container of column level, preconfigured checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](../column-profiling-checks/#ColumnNullsProfilingChecksSpec)|Configuration of column level checks that verify nulls and blanks.|[ColumnNullsProfilingChecksSpec](../column-profiling-checks/#ColumnNullsProfilingChecksSpec)| | | |
|[numeric](../column-profiling-checks/#ColumnNumericProfilingChecksSpec)|Configuration of column level checks that verify negative values.|[ColumnNumericProfilingChecksSpec](../column-profiling-checks/#ColumnNumericProfilingChecksSpec)| | | |
|[strings](../column-profiling-checks/#ColumnStringsProfilingChecksSpec)|Configuration of strings checks on a column level.|[ColumnStringsProfilingChecksSpec](../column-profiling-checks/#ColumnStringsProfilingChecksSpec)| | | |
|[uniqueness](../column-profiling-checks/#ColumnUniquenessProfilingChecksSpec)|Configuration of uniqueness checks on a column level.|[ColumnUniquenessProfilingChecksSpec](../column-profiling-checks/#ColumnUniquenessProfilingChecksSpec)| | | |
|[datetime](../column-profiling-checks/#ColumnDatetimeProfilingChecksSpec)|Configuration of datetime checks on a column level.|[ColumnDatetimeProfilingChecksSpec](../column-profiling-checks/#ColumnDatetimeProfilingChecksSpec)| | | |
|[pii](../column-profiling-checks/#ColumnPiiProfilingChecksSpec)|Configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiProfilingChecksSpec](../column-profiling-checks/#ColumnPiiProfilingChecksSpec)| | | |
|[sql](../column-profiling-checks/#ColumnSqlProfilingChecksSpec)|Configuration of SQL checks that use custom SQL aggregated expressions and SQL conditions in data quality checks.|[ColumnSqlProfilingChecksSpec](../column-profiling-checks/#ColumnSqlProfilingChecksSpec)| | | |
|[bool](../column-profiling-checks/#ColumnBoolProfilingChecksSpec)|Configuration of booleans checks on a column level.|[ColumnBoolProfilingChecksSpec](../column-profiling-checks/#ColumnBoolProfilingChecksSpec)| | | |
|[integrity](../column-profiling-checks/#ColumnIntegrityProfilingChecksSpec)|Configuration of integrity checks on a column level.|[ColumnIntegrityProfilingChecksSpec](../column-profiling-checks/#ColumnIntegrityProfilingChecksSpec)| | | |
|[accuracy](../column-profiling-checks/#ColumnAccuracyProfilingChecksSpec)|Configuration of accuracy checks on a column level.|[ColumnAccuracyProfilingChecksSpec](../column-profiling-checks/#ColumnAccuracyProfilingChecksSpec)| | | |
|[datatype](../column-profiling-checks/#ColumnDatatypeProfilingChecksSpec)|Configuration of datatype checks on a column level.|[ColumnDatatypeProfilingChecksSpec](../column-profiling-checks/#ColumnDatatypeProfilingChecksSpec)| | | |
|[anomaly](../column-profiling-checks/#ColumnAnomalyProfilingChecksSpec)|Configuration of anomaly checks on a column level.|[ColumnAnomalyProfilingChecksSpec](../column-profiling-checks/#ColumnAnomalyProfilingChecksSpec)| | | |
|[schema](../column-profiling-checks/#ColumnSchemaProfilingChecksSpec)|Configuration of schema checks on a column level.|[ColumnSchemaProfilingChecksSpec](../column-profiling-checks/#ColumnSchemaProfilingChecksSpec)| | | |
|[comparisons](../column-profiling-checks/#ColumnComparisonProfilingChecksSpecMap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonProfilingChecksSpecMap](../column-profiling-checks/#ColumnComparisonProfilingChecksSpecMap)| | | |
|[custom](../table-profiling-checks/#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../table-profiling-checks/#CustomCheckSpecMap)| | | |









___  

## ColumnDatetimeProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for datetime.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_date_match_format_percent](../../../../checks/column/datetime/date-match-format-percent/)|Verifies that the percentage of date values matching the given format in a column does not exceed the minimum accepted percentage.|[ColumnDatetimeDateMatchFormatPercentCheckSpec](../../../../checks/column/datetime/date-match-format-percent/)| | | |
|[profile_date_values_in_future_percent](../../../../checks/column/datetime/date-values-in-future-percent/)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage.|[ColumnDateValuesInFuturePercentCheckSpec](../../../../checks/column/datetime/date-values-in-future-percent/)| | | |
|[profile_datetime_value_in_range_date_percent](../../../../checks/column/datetime/datetime-value-in-range-date-percent/)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](../../../../checks/column/datetime/datetime-value-in-range-date-percent/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnPercentile75InRangeCheckSpec  
Column level check that ensures that the percentile 75 of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../column-profiling-checks/#ColumnNumericPercentile75SensorParametersSpec)|Data quality check parameters|[ColumnNumericPercentile75SensorParametersSpec](../column-profiling-checks/#ColumnNumericPercentile75SensorParametersSpec)| | | |
|[warning](../../../rules/Comparison/#between-floats)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[error](../../../rules/Comparison/#between-floats)|Default alerting threshold for a percentile 75 in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[fatal](../../../rules/Comparison/#between-floats)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[schedule_override](../table-profiling-checks/#MonitoringScheduleSpec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](../table-profiling-checks/#MonitoringScheduleSpec)| | | |
|[comments](../table-profiling-checks/#CommentsListSpec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](../table-profiling-checks/#CommentsListSpec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___  

## ColumnAnomalyProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_mean_anomaly_stationary_30_days](../../../../checks/column/anomaly/mean-anomaly-stationary-30-days/)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyStationaryMean30DaysCheckSpec](../../../../checks/column/anomaly/mean-anomaly-stationary-30-days/)| | | |
|[profile_mean_anomaly_stationary](../../../../checks/column/anomaly/mean-anomaly-stationary/)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnAnomalyStationaryMeanCheckSpec](../../../../checks/column/anomaly/mean-anomaly-stationary/)| | | |
|[profile_median_anomaly_stationary_30_days](../../../../checks/column/anomaly/median-anomaly-stationary-30-days/)|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyStationaryMedian30DaysCheckSpec](../../../../checks/column/anomaly/median-anomaly-stationary-30-days/)| | | |
|[profile_median_anomaly_stationary](../../../../checks/column/anomaly/median-anomaly-stationary/)|Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnAnomalyStationaryMedianCheckSpec](../../../../checks/column/anomaly/median-anomaly-stationary/)| | | |
|[profile_sum_anomaly_differencing_30_days](../../../../checks/column/anomaly/sum-anomaly-differencing-30-days/)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyDifferencingSum30DaysCheckSpec](../../../../checks/column/anomaly/sum-anomaly-differencing-30-days/)| | | |
|[profile_sum_anomaly_differencing](../../../../checks/column/anomaly/sum-anomaly-differencing/)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnAnomalyDifferencingSumCheckSpec](../../../../checks/column/anomaly/sum-anomaly-differencing/)| | | |
|[profile_mean_change](../../../../checks/column/anomaly/mean-change/)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](../../../../checks/column/anomaly/mean-change/)| | | |
|[profile_mean_change_yesterday](../../../../checks/column/anomaly/mean-change-yesterday/)|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMeanSinceYesterdayCheckSpec](../../../../checks/column/anomaly/mean-change-yesterday/)| | | |
|[profile_mean_change_7_days](../../../../checks/column/anomaly/mean-change-7-days/)|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMeanSince7DaysCheckSpec](../../../../checks/column/anomaly/mean-change-7-days/)| | | |
|[profile_mean_change_30_days](../../../../checks/column/anomaly/mean-change-30-days/)|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMeanSince30DaysCheckSpec](../../../../checks/column/anomaly/mean-change-30-days/)| | | |
|[profile_median_change](../../../../checks/column/anomaly/median-change/)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](../../../../checks/column/anomaly/median-change/)| | | |
|[profile_median_change_yesterday](../../../../checks/column/anomaly/median-change-yesterday/)|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMedianSinceYesterdayCheckSpec](../../../../checks/column/anomaly/median-change-yesterday/)| | | |
|[profile_median_change_7_days](../../../../checks/column/anomaly/median-change-7-days/)|Verifies that the median in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMedianSince7DaysCheckSpec](../../../../checks/column/anomaly/median-change-7-days/)| | | |
|[profile_median_change_30_days](../../../../checks/column/anomaly/median-change-30-days/)|Verifies that the median in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMedianSince30DaysCheckSpec](../../../../checks/column/anomaly/median-change-30-days/)| | | |
|[profile_sum_change](../../../../checks/column/anomaly/sum-change/)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](../../../../checks/column/anomaly/sum-change/)| | | |
|[profile_sum_change_yesterday](../../../../checks/column/anomaly/sum-change-yesterday/)|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeSumSinceYesterdayCheckSpec](../../../../checks/column/anomaly/sum-change-yesterday/)| | | |
|[profile_sum_change_7_days](../../../../checks/column/anomaly/sum-change-7-days/)|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|[ColumnChangeSumSince7DaysCheckSpec](../../../../checks/column/anomaly/sum-change-7-days/)| | | |
|[profile_sum_change_30_days](../../../../checks/column/anomaly/sum-change-30-days/)|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|[ColumnChangeSumSince30DaysCheckSpec](../../../../checks/column/anomaly/sum-change-30-days/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnUniquenessProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for negative values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_distinct_count](../../../../checks/column/uniqueness/distinct-count/)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count.|[ColumnDistinctCountCheckSpec](../../../../checks/column/uniqueness/distinct-count/)| | | |
|[profile_distinct_percent](../../../../checks/column/uniqueness/distinct-percent/)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.|[ColumnDistinctPercentCheckSpec](../../../../checks/column/uniqueness/distinct-percent/)| | | |
|[profile_duplicate_count](../../../../checks/column/uniqueness/duplicate-count/)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.|[ColumnDuplicateCountCheckSpec](../../../../checks/column/uniqueness/duplicate-count/)| | | |
|[profile_duplicate_percent](../../../../checks/column/uniqueness/duplicate-percent/)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.|[ColumnDuplicatePercentCheckSpec](../../../../checks/column/uniqueness/duplicate-percent/)| | | |
|[profile_anomaly_differencing_distinct_count_30_days](../../../../checks/column/uniqueness/anomaly-differencing-distinct-count-30-days/)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec](../../../../checks/column/uniqueness/anomaly-differencing-distinct-count-30-days/)| | | |
|[profile_anomaly_differencing_distinct_count](../../../../checks/column/uniqueness/anomaly-differencing-distinct-count/)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyDifferencingDistinctCountCheckSpec](../../../../checks/column/uniqueness/anomaly-differencing-distinct-count/)| | | |
|[profile_anomaly_stationary_distinct_percent_30_days](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent-30-days/)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent-30-days/)| | | |
|[profile_anomaly_stationary_distinct_percent](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent/)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryDistinctPercentCheckSpec](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent/)| | | |
|[profile_change_distinct_count](../../../../checks/column/uniqueness/change-distinct-count/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctCountCheckSpec](../../../../checks/column/uniqueness/change-distinct-count/)| | | |
|[profile_change_distinct_count_since_7_days](../../../../checks/column/uniqueness/change-distinct-count-since-7-days/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctCountSince7DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-7-days/)| | | |
|[profile_change_distinct_count_since_30_days](../../../../checks/column/uniqueness/change-distinct-count-since-30-days/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctCountSince30DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-30-days/)| | | |
|[profile_change_distinct_count_since_yesterday](../../../../checks/column/uniqueness/change-distinct-count-since-yesterday/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctCountSinceYesterdayCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-yesterday/)| | | |
|[profile_change_distinct_percent](../../../../checks/column/uniqueness/change-distinct-percent/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctPercentCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent/)| | | |
|[profile_change_distinct_percent_since_7_days](../../../../checks/column/uniqueness/change-distinct-percent-since-7-days/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctPercentSince7DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-7-days/)| | | |
|[profile_change_distinct_percent_since_30_days](../../../../checks/column/uniqueness/change-distinct-percent-since-30-days/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctPercentSince30DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-30-days/)| | | |
|[profile_change_distinct_percent_since_yesterday](../../../../checks/column/uniqueness/change-distinct-percent-since-yesterday/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctPercentSinceYesterdayCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-yesterday/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnNumericPercentile25SensorParametersSpec  
Column level sensor that finds the percentile 25 in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|25th percentile, must equal 0.25|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringsProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for string.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_string_max_length](../../../../checks/column/strings/string-max-length/)|Verifies that the length of string in a column does not exceed the maximum accepted length.|[ColumnStringMaxLengthCheckSpec](../../../../checks/column/strings/string-max-length/)| | | |
|[profile_string_min_length](../../../../checks/column/strings/string-min-length/)|Verifies that the length of string in a column does not fall below the minimum accepted length.|[ColumnStringMinLengthCheckSpec](../../../../checks/column/strings/string-min-length/)| | | |
|[profile_string_mean_length](../../../../checks/column/strings/string-mean-length/)|Verifies that the length of string in a column does not exceed the mean accepted length.|[ColumnStringMeanLengthCheckSpec](../../../../checks/column/strings/string-mean-length/)| | | |
|[profile_string_length_below_min_length_count](../../../../checks/column/strings/string-length-below-min-length-count/)|The check counts the number of strings in the column that is below the length defined by the user as a parameter.|[ColumnStringLengthBelowMinLengthCountCheckSpec](../../../../checks/column/strings/string-length-below-min-length-count/)| | | |
|[profile_string_length_below_min_length_percent](../../../../checks/column/strings/string-length-below-min-length-percent/)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](../../../../checks/column/strings/string-length-below-min-length-percent/)| | | |
|[profile_string_length_above_max_length_count](../../../../checks/column/strings/string-length-above-max-length-count/)|The check counts the number of strings in the column that is above the length defined by the user as a parameter.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](../../../../checks/column/strings/string-length-above-max-length-count/)| | | |
|[profile_string_length_above_max_length_percent](../../../../checks/column/strings/string-length-above-max-length-percent/)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](../../../../checks/column/strings/string-length-above-max-length-percent/)| | | |
|[profile_string_length_in_range_percent](../../../../checks/column/strings/string-length-in-range-percent/)|The check counts the percentage of those strings with length in the range provided by the user in the column. |[ColumnStringLengthInRangePercentCheckSpec](../../../../checks/column/strings/string-length-in-range-percent/)| | | |
|[profile_string_empty_count](../../../../checks/column/strings/string-empty-count/)|Verifies that empty strings in a column does not exceed the maximum accepted count.|[ColumnStringEmptyCountCheckSpec](../../../../checks/column/strings/string-empty-count/)| | | |
|[profile_string_empty_percent](../../../../checks/column/strings/string-empty-percent/)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.|[ColumnStringEmptyPercentCheckSpec](../../../../checks/column/strings/string-empty-percent/)| | | |
|[profile_string_whitespace_count](../../../../checks/column/strings/string-whitespace-count/)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.|[ColumnStringWhitespaceCountCheckSpec](../../../../checks/column/strings/string-whitespace-count/)| | | |
|[profile_string_whitespace_percent](../../../../checks/column/strings/string-whitespace-percent/)|Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.|[ColumnStringWhitespacePercentCheckSpec](../../../../checks/column/strings/string-whitespace-percent/)| | | |
|[profile_string_surrounded_by_whitespace_count](../../../../checks/column/strings/string-surrounded-by-whitespace-count/)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](../../../../checks/column/strings/string-surrounded-by-whitespace-count/)| | | |
|[profile_string_surrounded_by_whitespace_percent](../../../../checks/column/strings/string-surrounded-by-whitespace-percent/)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](../../../../checks/column/strings/string-surrounded-by-whitespace-percent/)| | | |
|[profile_string_null_placeholder_count](../../../../checks/column/strings/string-null-placeholder-count/)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.|[ColumnStringNullPlaceholderCountCheckSpec](../../../../checks/column/strings/string-null-placeholder-count/)| | | |
|[profile_string_null_placeholder_percent](../../../../checks/column/strings/string-null-placeholder-percent/)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.|[ColumnStringNullPlaceholderPercentCheckSpec](../../../../checks/column/strings/string-null-placeholder-percent/)| | | |
|[profile_string_boolean_placeholder_percent](../../../../checks/column/strings/string-boolean-placeholder-percent/)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage.|[ColumnStringBooleanPlaceholderPercentCheckSpec](../../../../checks/column/strings/string-boolean-placeholder-percent/)| | | |
|[profile_string_parsable_to_integer_percent](../../../../checks/column/strings/string-parsable-to-integer-percent/)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage.|[ColumnStringParsableToIntegerPercentCheckSpec](../../../../checks/column/strings/string-parsable-to-integer-percent/)| | | |
|[profile_string_parsable_to_float_percent](../../../../checks/column/strings/string-parsable-to-float-percent/)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage.|[ColumnStringParsableToFloatPercentCheckSpec](../../../../checks/column/strings/string-parsable-to-float-percent/)| | | |
|[profile_expected_strings_in_use_count](../../../../checks/column/strings/expected-strings-in-use-count/)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|[ColumnExpectedStringsInUseCountCheckSpec](../../../../checks/column/strings/expected-strings-in-use-count/)| | | |
|[profile_string_value_in_set_percent](../../../../checks/column/strings/string-value-in-set-percent/)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|[ColumnStringValueInSetPercentCheckSpec](../../../../checks/column/strings/string-value-in-set-percent/)| | | |
|[profile_string_valid_dates_percent](../../../../checks/column/strings/string-valid-dates-percent/)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage.|[ColumnStringValidDatesPercentCheckSpec](../../../../checks/column/strings/string-valid-dates-percent/)| | | |
|[profile_string_valid_country_code_percent](../../../../checks/column/strings/string-valid-country-code-percent/)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage.|[ColumnStringValidCountryCodePercentCheckSpec](../../../../checks/column/strings/string-valid-country-code-percent/)| | | |
|[profile_string_valid_currency_code_percent](../../../../checks/column/strings/string-valid-currency-code-percent/)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage.|[ColumnStringValidCurrencyCodePercentCheckSpec](../../../../checks/column/strings/string-valid-currency-code-percent/)| | | |
|[profile_string_invalid_email_count](../../../../checks/column/strings/string-invalid-email-count/)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count.|[ColumnStringInvalidEmailCountCheckSpec](../../../../checks/column/strings/string-invalid-email-count/)| | | |
|[profile_string_invalid_uuid_count](../../../../checks/column/strings/string-invalid-uuid-count/)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count.|[ColumnStringInvalidUuidCountCheckSpec](../../../../checks/column/strings/string-invalid-uuid-count/)| | | |
|[profile_string_valid_uuid_percent](../../../../checks/column/strings/string-valid-uuid-percent/)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage.|[ColumnStringValidUuidPercentCheckSpec](../../../../checks/column/strings/string-valid-uuid-percent/)| | | |
|[profile_string_invalid_ip4_address_count](../../../../checks/column/strings/string-invalid-ip4-address-count/)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count.|[ColumnStringInvalidIp4AddressCountCheckSpec](../../../../checks/column/strings/string-invalid-ip4-address-count/)| | | |
|[profile_string_invalid_ip6_address_count](../../../../checks/column/strings/string-invalid-ip6-address-count/)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count.|[ColumnStringInvalidIp6AddressCountCheckSpec](../../../../checks/column/strings/string-invalid-ip6-address-count/)| | | |
|[profile_string_not_match_regex_count](../../../../checks/column/strings/string-not-match-regex-count/)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count.|[ColumnStringNotMatchRegexCountCheckSpec](../../../../checks/column/strings/string-not-match-regex-count/)| | | |
|[profile_string_match_regex_percent](../../../../checks/column/strings/string-match-regex-percent/)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage.|[ColumnStringMatchRegexPercentCheckSpec](../../../../checks/column/strings/string-match-regex-percent/)| | | |
|[profile_string_not_match_date_regex_count](../../../../checks/column/strings/string-not-match-date-regex-count/)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count.|[ColumnStringNotMatchDateRegexCountCheckSpec](../../../../checks/column/strings/string-not-match-date-regex-count/)| | | |
|[profile_string_match_date_regex_percent](../../../../checks/column/strings/string-match-date-regex-percent/)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage.|[ColumnStringMatchDateRegexPercentCheckSpec](../../../../checks/column/strings/string-match-date-regex-percent/)| | | |
|[profile_string_match_name_regex_percent](../../../../checks/column/strings/string-match-name-regex-percent/)|Verifies that the percentage of strings matching the name regex in a column does not fall below the minimum accepted percentage.|[ColumnStringMatchNameRegexPercentCheckSpec](../../../../checks/column/strings/string-match-name-regex-percent/)| | | |
|[profile_expected_strings_in_top_values_count](../../../../checks/column/strings/expected-strings-in-top-values-count/)|Verifies that the top X most popular column values contain all values from a list of expected values.|[ColumnExpectedStringsInTopValuesCountCheckSpec](../../../../checks/column/strings/expected-strings-in-top-values-count/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnComparisonProfilingChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [ColumnComparisonProfilingChecksSpec](../column-profiling-checks/#ColumnComparisonProfilingChecksSpec)]| | | |









___  

## ColumnNumericPercentile90SensorParametersSpec  
Column level sensor that finds the percentile 90 in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|90th percentile, must equal 0.9|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPercentile10InRangeCheckSpec  
Column level check that ensures that the percentile 10 of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../column-profiling-checks/#ColumnNumericPercentile10SensorParametersSpec)|Data quality check parameters|[ColumnNumericPercentile10SensorParametersSpec](../column-profiling-checks/#ColumnNumericPercentile10SensorParametersSpec)| | | |
|[warning](../../../rules/Comparison/#between-floats)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[error](../../../rules/Comparison/#between-floats)|Default alerting threshold for a percentile 10 in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[fatal](../../../rules/Comparison/#between-floats)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[schedule_override](../table-profiling-checks/#MonitoringScheduleSpec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](../table-profiling-checks/#MonitoringScheduleSpec)| | | |
|[comments](../table-profiling-checks/#CommentsListSpec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](../table-profiling-checks/#CommentsListSpec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___  

## ColumnDatatypeProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for datatype.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_string_datatype_detected](../../../../checks/column/datatype/string-datatype-detected/)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type.|[ColumnDatatypeStringDatatypeDetectedCheckSpec](../../../../checks/column/datatype/string-datatype-detected/)| | | |
|[profile_string_datatype_changed](../../../../checks/column/datatype/string-datatype-changed/)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|[ColumnDatatypeStringDatatypeChangedCheckSpec](../../../../checks/column/datatype/string-datatype-changed/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnAccuracyProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for accuracy.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_total_sum_match_percent](../../../../checks/column/accuracy/total-sum-match-percent/)|Verifies that percentage of the difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number.|[ColumnAccuracyTotalSumMatchPercentCheckSpec](../../../../checks/column/accuracy/total-sum-match-percent/)| | | |
|[profile_total_min_match_percent](../../../../checks/column/accuracy/total-min-match-percent/)|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number.|[ColumnAccuracyTotalMinMatchPercentCheckSpec](../../../../checks/column/accuracy/total-min-match-percent/)| | | |
|[profile_total_max_match_percent](../../../../checks/column/accuracy/total-max-match-percent/)|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number.|[ColumnAccuracyTotalMaxMatchPercentCheckSpec](../../../../checks/column/accuracy/total-max-match-percent/)| | | |
|[profile_total_average_match_percent](../../../../checks/column/accuracy/total-average-match-percent/)|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number.|[ColumnAccuracyTotalAverageMatchPercentCheckSpec](../../../../checks/column/accuracy/total-average-match-percent/)| | | |
|[profile_total_not_null_count_match_percent](../../../../checks/column/accuracy/total-not-null-count-match-percent/)|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec](../../../../checks/column/accuracy/total-not-null-count-match-percent/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnPercentile90InRangeCheckSpec  
Column-level check that ensures that the percentile 90 of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../column-profiling-checks/#ColumnNumericPercentile90SensorParametersSpec)|Data quality check parameters|[ColumnNumericPercentile90SensorParametersSpec](../column-profiling-checks/#ColumnNumericPercentile90SensorParametersSpec)| | | |
|[warning](../../../rules/Comparison/#between-floats)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[error](../../../rules/Comparison/#between-floats)|Default alerting threshold for a percentile 90 in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[fatal](../../../rules/Comparison/#between-floats)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[schedule_override](../table-profiling-checks/#MonitoringScheduleSpec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](../table-profiling-checks/#MonitoringScheduleSpec)| | | |
|[comments](../table-profiling-checks/#CommentsListSpec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](../table-profiling-checks/#CommentsListSpec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___  

## ColumnSqlProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_sql_condition_passed_percent_on_column](../../../../checks/column/sql/sql-condition-passed-percent-on-column/)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression).|[ColumnSqlConditionPassedPercentCheckSpec](../../../../checks/column/sql/sql-condition-passed-percent-on-column/)| | | |
|[profile_sql_condition_failed_count_on_column](../../../../checks/column/sql/sql-condition-failed-count-on-column/)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count.|[ColumnSqlConditionFailedCountCheckSpec](../../../../checks/column/sql/sql-condition-failed-count-on-column/)| | | |
|[profile_sql_aggregate_expr_column](../../../../checks/column/sql/sql-aggregate-expr-column/)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|[ColumnSqlAggregateExprCheckSpec](../../../../checks/column/sql/sql-aggregate-expr-column/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnMedianInRangeCheckSpec  
Column level check that ensures that the median of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../../../sensors/column/numeric-column-sensors/#percentile)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](../../../sensors/column/numeric-column-sensors/#percentile)| | | |
|[warning](../../../rules/Comparison/#between-floats)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[error](../../../rules/Comparison/#between-floats)|Default alerting threshold for a median in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[fatal](../../../rules/Comparison/#between-floats)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](../../../rules/Comparison/#between-floats)| | | |
|[schedule_override](../table-profiling-checks/#MonitoringScheduleSpec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](../table-profiling-checks/#MonitoringScheduleSpec)| | | |
|[comments](../table-profiling-checks/#CommentsListSpec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](../table-profiling-checks/#CommentsListSpec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___  

## ColumnBoolProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for booleans.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_true_percent](../../../../checks/column/bool/true-percent/)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage.|[ColumnTruePercentCheckSpec](../../../../checks/column/bool/true-percent/)| | | |
|[profile_false_percent](../../../../checks/column/bool/false-percent/)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage.|[ColumnFalsePercentCheckSpec](../../../../checks/column/bool/false-percent/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnNumericPercentile10SensorParametersSpec  
Column level sensor that finds the percentile 10 in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|10th percentile, must equal 0.1|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnNumericPercentile75SensorParametersSpec  
Column level sensor that finds the percentile 75 in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|75th percentile, must equal 0.75|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnIntegrityProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for integrity.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_foreign_key_not_match_count](../../../../checks/column/integrity/foreign-key-not-match-count/)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](../../../../checks/column/integrity/foreign-key-not-match-count/)| | | |
|[profile_foreign_key_match_percent](../../../../checks/column/integrity/foreign-key-match-percent/)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](../../../../checks/column/integrity/foreign-key-match-percent/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnComparisonProfilingChecksSpec  
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_sum_match](../../../../checks/column/comparisons/sum-match/)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds.|[ColumnComparisonSumMatchCheckSpec](../../../../checks/column/comparisons/sum-match/)| | | |
|[profile_min_match](../../../../checks/column/comparisons/min-match/)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds.|[ColumnComparisonMinMatchCheckSpec](../../../../checks/column/comparisons/min-match/)| | | |
|[profile_max_match](../../../../checks/column/comparisons/max-match/)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds.|[ColumnComparisonMaxMatchCheckSpec](../../../../checks/column/comparisons/max-match/)| | | |
|[profile_mean_match](../../../../checks/column/comparisons/mean-match/)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds.|[ColumnComparisonMeanMatchCheckSpec](../../../../checks/column/comparisons/mean-match/)| | | |
|[profile_not_null_count_match](../../../../checks/column/comparisons/not-null-count-match/)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds.|[ColumnComparisonNotNullCountMatchCheckSpec](../../../../checks/column/comparisons/not-null-count-match/)| | | |
|[profile_null_count_match](../../../../checks/column/comparisons/null-count-match/)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds.|[ColumnComparisonNullCountMatchCheckSpec](../../../../checks/column/comparisons/null-count-match/)| | | |
|reference_column|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|string| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnPiiProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for Personal Identifiable Information (PII).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_contains_usa_phone_percent](../../../../checks/column/pii/contains-usa-phone-percent/)|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.|[ColumnPiiContainsUsaPhonePercentCheckSpec](../../../../checks/column/pii/contains-usa-phone-percent/)| | | |
|[profile_contains_usa_zipcode_percent](../../../../checks/column/pii/contains-usa-zipcode-percent/)|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](../../../../checks/column/pii/contains-usa-zipcode-percent/)| | | |
|[profile_contains_email_percent](../../../../checks/column/pii/contains-email-percent/)|Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.|[ColumnPiiContainsEmailPercentCheckSpec](../../../../checks/column/pii/contains-email-percent/)| | | |
|[profile_contains_ip4_percent](../../../../checks/column/pii/contains-ip4-percent/)|Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.|[ColumnPiiContainsIp4PercentCheckSpec](../../../../checks/column/pii/contains-ip4-percent/)| | | |
|[profile_contains_ip6_percent](../../../../checks/column/pii/contains-ip6-percent/)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.|[ColumnPiiContainsIp6PercentCheckSpec](../../../../checks/column/pii/contains-ip6-percent/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnNullsProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for nulls.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_nulls_count](../../../../checks/column/nulls/nulls-count/)|Verifies that the number of null values in a column does not exceed the maximum accepted count.|[ColumnNullsCountCheckSpec](../../../../checks/column/nulls/nulls-count/)| | | |
|[profile_nulls_percent](../../../../checks/column/nulls/nulls-percent/)|Verifies that the percent of null values in a column does not exceed the maximum accepted percentage.|[ColumnNullsPercentCheckSpec](../../../../checks/column/nulls/nulls-percent/)| | | |
|[profile_nulls_percent_anomaly_stationary_30_days](../../../../checks/column/nulls/nulls-percent-anomaly-stationary-30-days/)|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyStationaryNullPercent30DaysCheckSpec](../../../../checks/column/nulls/nulls-percent-anomaly-stationary-30-days/)| | | |
|[profile_nulls_percent_anomaly_stationary](../../../../checks/column/nulls/nulls-percent-anomaly-stationary/)|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnAnomalyStationaryNullPercentCheckSpec](../../../../checks/column/nulls/nulls-percent-anomaly-stationary/)| | | |
|[profile_nulls_percent_change](../../../../checks/column/nulls/nulls-percent-change/)|Verifies that the null percent value in a column changed in a fixed rate since last readout.|[ColumnChangeNullPercentCheckSpec](../../../../checks/column/nulls/nulls-percent-change/)| | | |
|[profile_nulls_percent_change_yesterday](../../../../checks/column/nulls/nulls-percent-change-yesterday/)|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeNullPercentSinceYesterdayCheckSpec](../../../../checks/column/nulls/nulls-percent-change-yesterday/)| | | |
|[profile_nulls_percent_change_7_days](../../../../checks/column/nulls/nulls-percent-change-7-days/)|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|[ColumnChangeNullPercentSince7DaysCheckSpec](../../../../checks/column/nulls/nulls-percent-change-7-days/)| | | |
|[profile_nulls_percent_change_30_days](../../../../checks/column/nulls/nulls-percent-change-30-days/)|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|[ColumnChangeNullPercentSince30DaysCheckSpec](../../../../checks/column/nulls/nulls-percent-change-30-days/)| | | |
|[profile_not_nulls_count](../../../../checks/column/nulls/not-nulls-count/)|Verifies that the number of not null values in a column does not exceed the minimum accepted count.|[ColumnNotNullsCountCheckSpec](../../../../checks/column/nulls/not-nulls-count/)| | | |
|[profile_not_nulls_percent](../../../../checks/column/nulls/not-nulls-percent/)|Verifies that the percent of not null values in a column does not exceed the minimum accepted percentage.|[ColumnNotNullsPercentCheckSpec](../../../../checks/column/nulls/not-nulls-percent/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnNumericProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level for numeric values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_negative_count](../../../../checks/column/numeric/negative-count/)|Verifies that the number of negative values in a column does not exceed the maximum accepted count.|[ColumnNegativeCountCheckSpec](../../../../checks/column/numeric/negative-count/)| | | |
|[profile_negative_percent](../../../../checks/column/numeric/negative-percent/)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.|[ColumnNegativePercentCheckSpec](../../../../checks/column/numeric/negative-percent/)| | | |
|[profile_non_negative_count](../../../../checks/column/numeric/non-negative-count/)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.|[ColumnNonNegativeCountCheckSpec](../../../../checks/column/numeric/non-negative-count/)| | | |
|[profile_non_negative_percent](../../../../checks/column/numeric/non-negative-percent/)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.|[ColumnNonNegativePercentCheckSpec](../../../../checks/column/numeric/non-negative-percent/)| | | |
|[profile_expected_numbers_in_use_count](../../../../checks/column/numeric/expected-numbers-in-use-count/)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|[ColumnExpectedNumbersInUseCountCheckSpec](../../../../checks/column/numeric/expected-numbers-in-use-count/)| | | |
|[profile_number_value_in_set_percent](../../../../checks/column/numeric/number-value-in-set-percent/)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|[ColumnNumberValueInSetPercentCheckSpec](../../../../checks/column/numeric/number-value-in-set-percent/)| | | |
|[profile_values_in_range_numeric_percent](../../../../checks/column/numeric/values-in-range-numeric-percent/)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|[ColumnValuesInRangeNumericPercentCheckSpec](../../../../checks/column/numeric/values-in-range-numeric-percent/)| | | |
|[profile_values_in_range_integers_percent](../../../../checks/column/numeric/values-in-range-integers-percent/)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|[ColumnValuesInRangeIntegersPercentCheckSpec](../../../../checks/column/numeric/values-in-range-integers-percent/)| | | |
|[profile_value_below_min_value_count](../../../../checks/column/numeric/value-below-min-value-count/)|The check counts the number of values in the column that is below the value defined by the user as a parameter.|[ColumnValueBelowMinValueCountCheckSpec](../../../../checks/column/numeric/value-below-min-value-count/)| | | |
|[profile_value_below_min_value_percent](../../../../checks/column/numeric/value-below-min-value-percent/)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter.|[ColumnValueBelowMinValuePercentCheckSpec](../../../../checks/column/numeric/value-below-min-value-percent/)| | | |
|[profile_value_above_max_value_count](../../../../checks/column/numeric/value-above-max-value-count/)|The check counts the number of values in the column that is above the value defined by the user as a parameter.|[ColumnValueAboveMaxValueCountCheckSpec](../../../../checks/column/numeric/value-above-max-value-count/)| | | |
|[profile_value_above_max_value_percent](../../../../checks/column/numeric/value-above-max-value-percent/)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter.|[ColumnValueAboveMaxValuePercentCheckSpec](../../../../checks/column/numeric/value-above-max-value-percent/)| | | |
|[profile_max_in_range](../../../../checks/column/numeric/max-in-range/)|Verifies that the maximal value in a column is not outside the set range.|[ColumnMaxInRangeCheckSpec](../../../../checks/column/numeric/max-in-range/)| | | |
|[profile_min_in_range](../../../../checks/column/numeric/min-in-range/)|Verifies that the minimal value in a column is not outside the set range.|[ColumnMinInRangeCheckSpec](../../../../checks/column/numeric/min-in-range/)| | | |
|[profile_mean_in_range](../../../../checks/column/numeric/mean-in-range/)|Verifies that the average (mean) of all values in a column is not outside the set range.|[ColumnMeanInRangeCheckSpec](../../../../checks/column/numeric/mean-in-range/)| | | |
|[profile_percentile_in_range](../../../../checks/column/numeric/percentile-in-range/)|Verifies that the percentile of all values in a column is not outside the set range.|[ColumnPercentileInRangeCheckSpec](../../../../checks/column/numeric/percentile-in-range/)| | | |
|[profile_median_in_range](../column-profiling-checks/#ColumnMedianInRangeCheckSpec)|Verifies that the median of all values in a column is not outside the set range.|[ColumnMedianInRangeCheckSpec](../column-profiling-checks/#ColumnMedianInRangeCheckSpec)| | | |
|[profile_percentile_10_in_range](../column-profiling-checks/#ColumnPercentile10InRangeCheckSpec)|Verifies that the percentile 10 of all values in a column is not outside the set range.|[ColumnPercentile10InRangeCheckSpec](../column-profiling-checks/#ColumnPercentile10InRangeCheckSpec)| | | |
|[profile_percentile_25_in_range](../column-profiling-checks/#ColumnPercentile25InRangeCheckSpec)|Verifies that the percentile 25 of all values in a column is not outside the set range.|[ColumnPercentile25InRangeCheckSpec](../column-profiling-checks/#ColumnPercentile25InRangeCheckSpec)| | | |
|[profile_percentile_75_in_range](../column-profiling-checks/#ColumnPercentile75InRangeCheckSpec)|Verifies that the percentile 75 of all values in a column is not outside the set range.|[ColumnPercentile75InRangeCheckSpec](../column-profiling-checks/#ColumnPercentile75InRangeCheckSpec)| | | |
|[profile_percentile_90_in_range](../column-profiling-checks/#ColumnPercentile90InRangeCheckSpec)|Verifies that the percentile 90 of all values in a column is not outside the set range.|[ColumnPercentile90InRangeCheckSpec](../column-profiling-checks/#ColumnPercentile90InRangeCheckSpec)| | | |
|[profile_sample_stddev_in_range](../../../../checks/column/numeric/sample-stddev-in-range/)|Verifies that the sample standard deviation of all values in a column is not outside the set range.|[ColumnSampleStddevInRangeCheckSpec](../../../../checks/column/numeric/sample-stddev-in-range/)| | | |
|[profile_population_stddev_in_range](../../../../checks/column/numeric/population-stddev-in-range/)|Verifies that the population standard deviation of all values in a column is not outside the set range.|[ColumnPopulationStddevInRangeCheckSpec](../../../../checks/column/numeric/population-stddev-in-range/)| | | |
|[profile_sample_variance_in_range](../../../../checks/column/numeric/sample-variance-in-range/)|Verifies that the sample variance of all values in a column is not outside the set range.|[ColumnSampleVarianceInRangeCheckSpec](../../../../checks/column/numeric/sample-variance-in-range/)| | | |
|[profile_population_variance_in_range](../../../../checks/column/numeric/population-variance-in-range/)|Verifies that the population variance of all values in a column is not outside the set range.|[ColumnPopulationVarianceInRangeCheckSpec](../../../../checks/column/numeric/population-variance-in-range/)| | | |
|[profile_sum_in_range](../../../../checks/column/numeric/sum-in-range/)|Verifies that the sum of all values in a column is not outside the set range.|[ColumnSumInRangeCheckSpec](../../../../checks/column/numeric/sum-in-range/)| | | |
|[profile_invalid_latitude_count](../../../../checks/column/numeric/invalid-latitude-count/)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.|[ColumnInvalidLatitudeCountCheckSpec](../../../../checks/column/numeric/invalid-latitude-count/)| | | |
|[profile_valid_latitude_percent](../../../../checks/column/numeric/valid-latitude-percent/)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.|[ColumnValidLatitudePercentCheckSpec](../../../../checks/column/numeric/valid-latitude-percent/)| | | |
|[profile_invalid_longitude_count](../../../../checks/column/numeric/invalid-longitude-count/)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.|[ColumnInvalidLongitudeCountCheckSpec](../../../../checks/column/numeric/invalid-longitude-count/)| | | |
|[profile_valid_longitude_percent](../../../../checks/column/numeric/valid-longitude-percent/)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.|[ColumnValidLongitudePercentCheckSpec](../../../../checks/column/numeric/valid-longitude-percent/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnSchemaProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking the column schema.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_column_exists](../../../../checks/column/schema/column-exists/)|Checks the metadata of the monitored table and verifies if the column exists.|[ColumnSchemaColumnExistsCheckSpec](../../../../checks/column/schema/column-exists/)| | | |
|[profile_column_type_changed](../../../../checks/column/schema/column-type-changed/)|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed.|[ColumnSchemaTypeChangedCheckSpec](../../../../checks/column/schema/column-type-changed/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

