
## ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec  
Column-level check that ensures that the distinct count value in a monitored column is within a two-tailed percentile from measurements made during the last 30 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../../../sensors/column/uniqueness-column-sensors/#distinct-count)|Data quality check parameters|[ColumnUniquenessDistinctCountSensorParametersSpec](../../../sensors/column/uniqueness-column-sensors/#distinct-count)| | | |
|[warning](../column-daily-partitioned-checks/#AnomalyStationaryPercentileMovingAverage30DaysRule1ParametersSpec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[AnomalyStationaryPercentileMovingAverage30DaysRule1ParametersSpec](../column-daily-partitioned-checks/#AnomalyStationaryPercentileMovingAverage30DaysRule1ParametersSpec)| | | |
|[error](../column-daily-partitioned-checks/#AnomalyStationaryPercentileMovingAverage30DaysRule05ParametersSpec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[AnomalyStationaryPercentileMovingAverage30DaysRule05ParametersSpec](../column-daily-partitioned-checks/#AnomalyStationaryPercentileMovingAverage30DaysRule05ParametersSpec)| | | |
|[fatal](../../../rules/Percentile/#anomaly-stationary-percentile-moving-average-30-days)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[AnomalyStationaryPercentileMovingAverage30DaysRule01ParametersSpec](../../../rules/Percentile/#anomaly-stationary-percentile-moving-average-30-days)| | | |
|[schedule_override](../../profiling/table-profiling-checks/#MonitoringScheduleSpec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](../../profiling/table-profiling-checks/#MonitoringScheduleSpec)| | | |
|[comments](../../profiling/table-profiling-checks/#CommentsListSpec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](../../profiling/table-profiling-checks/#CommentsListSpec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___  

## ColumnDatetimeDailyPartitionedChecksSpec  
Container of date-time data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_date_match_format_percent](../../../../checks/column/datetime/date-match-format-percent/)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDatetimeDateMatchFormatPercentCheckSpec](../../../../checks/column/datetime/date-match-format-percent/)| | | |
|[daily_partition_date_values_in_future_percent](../../../../checks/column/datetime/date-values-in-future-percent/)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDateValuesInFuturePercentCheckSpec](../../../../checks/column/datetime/date-values-in-future-percent/)| | | |
|[daily_partition_datetime_value_in_range_date_percent](../../../../checks/column/datetime/datetime-value-in-range-date-percent/)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](../../../../checks/column/datetime/datetime-value-in-range-date-percent/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnComparisonDailyPartitionedChecksSpec  
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 This is the configuration for daily partitioned checks that are counted in KPIs.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_sum_match](../../../../checks/column/comparisons/sum-match/)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonSumMatchCheckSpec](../../../../checks/column/comparisons/sum-match/)| | | |
|[daily_partition_min_match](../../../../checks/column/comparisons/min-match/)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMinMatchCheckSpec](../../../../checks/column/comparisons/min-match/)| | | |
|[daily_partition_max_match](../../../../checks/column/comparisons/max-match/)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMaxMatchCheckSpec](../../../../checks/column/comparisons/max-match/)| | | |
|[daily_partition_mean_match](../../../../checks/column/comparisons/mean-match/)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMeanMatchCheckSpec](../../../../checks/column/comparisons/mean-match/)| | | |
|[daily_partition_not_null_count_match](../../../../checks/column/comparisons/not-null-count-match/)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonNotNullCountMatchCheckSpec](../../../../checks/column/comparisons/not-null-count-match/)| | | |
|[daily_partition_null_count_match](../../../../checks/column/comparisons/null-count-match/)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonNullCountMatchCheckSpec](../../../../checks/column/comparisons/null-count-match/)| | | |
|reference_column|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|string| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnUniquenessDailyPartitionedChecksSpec  
Container of uniqueness data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_distinct_count](../../../../checks/column/uniqueness/distinct-count/)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDistinctCountCheckSpec](../../../../checks/column/uniqueness/distinct-count/)| | | |
|[daily_partition_distinct_percent](../../../../checks/column/uniqueness/distinct-percent/)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDistinctPercentCheckSpec](../../../../checks/column/uniqueness/distinct-percent/)| | | |
|[daily_partition_duplicate_count](../../../../checks/column/uniqueness/duplicate-count/)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDuplicateCountCheckSpec](../../../../checks/column/uniqueness/duplicate-count/)| | | |
|[daily_partition_duplicate_percent](../../../../checks/column/uniqueness/duplicate-percent/)|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDuplicatePercentCheckSpec](../../../../checks/column/uniqueness/duplicate-percent/)| | | |
|[daily_partition_anomaly_stationary_distinct_count_30_days](../column-daily-partitioned-checks/#ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec](../column-daily-partitioned-checks/#ColumnAnomalyStationaryPartitionDistinctCount30DaysCheckSpec)| | | |
|[daily_partition_anomaly_stationary_distinct_count](../column-daily-partitioned-checks/#ColumnAnomalyStationaryPartitionDistinctCountCheckSpec)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryPartitionDistinctCountCheckSpec](../column-daily-partitioned-checks/#ColumnAnomalyStationaryPartitionDistinctCountCheckSpec)| | | |
|[daily_partition_anomaly_stationary_distinct_percent_30_days](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent-30-days/)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent-30-days/)| | | |
|[daily_partition_anomaly_stationary_distinct_percent](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent/)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryDistinctPercentCheckSpec](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent/)| | | |
|[daily_partition_change_distinct_count](../../../../checks/column/uniqueness/change-distinct-count/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctCountCheckSpec](../../../../checks/column/uniqueness/change-distinct-count/)| | | |
|[daily_partition_change_distinct_count_since_7_days](../../../../checks/column/uniqueness/change-distinct-count-since-7-days/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctCountSince7DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-7-days/)| | | |
|[daily_partition_change_distinct_count_since_30_days](../../../../checks/column/uniqueness/change-distinct-count-since-30-days/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctCountSince30DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-30-days/)| | | |
|[daily_partition_change_distinct_count_since_yesterday](../../../../checks/column/uniqueness/change-distinct-count-since-yesterday/)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctCountSinceYesterdayCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-yesterday/)| | | |
|[daily_partition_change_distinct_percent](../../../../checks/column/uniqueness/change-distinct-percent/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctPercentCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent/)| | | |
|[daily_partition_change_distinct_percent_since_7_days](../../../../checks/column/uniqueness/change-distinct-percent-since-7-days/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctPercentSince7DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-7-days/)| | | |
|[daily_partition_change_distinct_percent_since_30_days](../../../../checks/column/uniqueness/change-distinct-percent-since-30-days/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctPercentSince30DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-30-days/)| | | |
|[daily_partition_change_distinct_percent_since_yesterday](../../../../checks/column/uniqueness/change-distinct-percent-since-yesterday/)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctPercentSinceYesterdayCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-yesterday/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## AnomalyStationaryPercentileMovingAverage30DaysRule1ParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|anomaly_percent|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|double| | |0.1<br/>|









___  

## AnomalyStationaryPercentileMovingAverageRule1ParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|anomaly_percent|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a time window of 90 periods (days, etc.), but at least 30 readouts must exist to run the calculation. You can change the default value by modifying prediction_time_window parameterin Definitions section.|double| | |0.1<br/>|









___  

## ColumnAccuracyDailyPartitionedChecksSpec  
Container of accuracy data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnPiiDailyPartitionedChecksSpec  
Container of PII data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_contains_usa_phone_percent](../../../../checks/column/pii/contains-usa-phone-percent/)|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsUsaPhonePercentCheckSpec](../../../../checks/column/pii/contains-usa-phone-percent/)| | | |
|[daily_partition_contains_usa_zipcode_percent](../../../../checks/column/pii/contains-usa-zipcode-percent/)|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](../../../../checks/column/pii/contains-usa-zipcode-percent/)| | | |
|[daily_partition_contains_email_percent](../../../../checks/column/pii/contains-email-percent/)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsEmailPercentCheckSpec](../../../../checks/column/pii/contains-email-percent/)| | | |
|[daily_partition_contains_ip4_percent](../../../../checks/column/pii/contains-ip4-percent/)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsIp4PercentCheckSpec](../../../../checks/column/pii/contains-ip4-percent/)| | | |
|[daily_partition_contains_ip6_percent](../../../../checks/column/pii/contains-ip6-percent/)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsIp6PercentCheckSpec](../../../../checks/column/pii/contains-ip6-percent/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnSqlDailyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_sql_condition_passed_percent_on_column](../../../../checks/column/sql/sql-condition-passed-percent-on-column/)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlConditionPassedPercentCheckSpec](../../../../checks/column/sql/sql-condition-passed-percent-on-column/)| | | |
|[daily_partition_sql_condition_failed_count_on_column](../../../../checks/column/sql/sql-condition-failed-count-on-column/)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlConditionFailedCountCheckSpec](../../../../checks/column/sql/sql-condition-failed-count-on-column/)| | | |
|[daily_partition_sql_aggregate_expr_column](../../../../checks/column/sql/sql-aggregate-expr-column/)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlAggregateExprCheckSpec](../../../../checks/column/sql/sql-aggregate-expr-column/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnDailyPartitionedCheckCategoriesSpec  
Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](../column-daily-partitioned-checks/#ColumnNullsDailyPartitionedChecksSpec)|Daily partitioned checks of nulls in the column|[ColumnNullsDailyPartitionedChecksSpec](../column-daily-partitioned-checks/#ColumnNullsDailyPartitionedChecksSpec)| | | |
|[numeric](../column-daily-partitioned-checks/#ColumnNumericDailyPartitionedChecksSpec)|Daily partitioned checks of numeric in the column|[ColumnNumericDailyPartitionedChecksSpec](../column-daily-partitioned-checks/#ColumnNumericDailyPartitionedChecksSpec)| | | |
|[strings](../column-daily-partitioned-checks/#ColumnStringsDailyPartitionedChecksSpec)|Daily partitioned checks of strings in the column|[ColumnStringsDailyPartitionedChecksSpec](../column-daily-partitioned-checks/#ColumnStringsDailyPartitionedChecksSpec)| | | |
|[uniqueness](../column-daily-partitioned-checks/#ColumnUniquenessDailyPartitionedChecksSpec)|Daily partitioned checks of uniqueness in the column|[ColumnUniquenessDailyPartitionedChecksSpec](../column-daily-partitioned-checks/#ColumnUniquenessDailyPartitionedChecksSpec)| | | |
|[datetime](../column-daily-partitioned-checks/#ColumnDatetimeDailyPartitionedChecksSpec)|Daily partitioned checks of datetime in the column|[ColumnDatetimeDailyPartitionedChecksSpec](../column-daily-partitioned-checks/#ColumnDatetimeDailyPartitionedChecksSpec)| | | |
|[pii](../column-daily-partitioned-checks/#ColumnPiiDailyPartitionedChecksSpec)|Daily partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyPartitionedChecksSpec](../column-daily-partitioned-checks/#ColumnPiiDailyPartitionedChecksSpec)| | | |
|[sql](../column-daily-partitioned-checks/#ColumnSqlDailyPartitionedChecksSpec)|Daily partitioned checks using custom SQL expressions evaluated on the column|[ColumnSqlDailyPartitionedChecksSpec](../column-daily-partitioned-checks/#ColumnSqlDailyPartitionedChecksSpec)| | | |
|[bool](../column-daily-partitioned-checks/#ColumnBoolDailyPartitionedChecksSpec)|Daily partitioned checks for booleans in the column|[ColumnBoolDailyPartitionedChecksSpec](../column-daily-partitioned-checks/#ColumnBoolDailyPartitionedChecksSpec)| | | |
|[integrity](../column-daily-partitioned-checks/#ColumnIntegrityDailyPartitionedChecksSpec)|Daily partitioned checks for integrity in the column|[ColumnIntegrityDailyPartitionedChecksSpec](../column-daily-partitioned-checks/#ColumnIntegrityDailyPartitionedChecksSpec)| | | |
|[accuracy](../column-daily-partitioned-checks/#ColumnAccuracyDailyPartitionedChecksSpec)|Daily partitioned checks for accuracy in the column|[ColumnAccuracyDailyPartitionedChecksSpec](../column-daily-partitioned-checks/#ColumnAccuracyDailyPartitionedChecksSpec)| | | |
|[datatype](../column-daily-partitioned-checks/#ColumnDatatypeDailyPartitionedChecksSpec)|Daily partitioned checks for datatype in the column|[ColumnDatatypeDailyPartitionedChecksSpec](../column-daily-partitioned-checks/#ColumnDatatypeDailyPartitionedChecksSpec)| | | |
|[anomaly](../column-daily-partitioned-checks/#ColumnAnomalyDailyPartitionedChecksSpec)|Daily partitioned checks for anomaly in the column|[ColumnAnomalyDailyPartitionedChecksSpec](../column-daily-partitioned-checks/#ColumnAnomalyDailyPartitionedChecksSpec)| | | |
|[comparisons](../column-daily-partitioned-checks/#ColumnComparisonDailyPartitionedChecksSpecMap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonDailyPartitionedChecksSpecMap](../column-daily-partitioned-checks/#ColumnComparisonDailyPartitionedChecksSpecMap)| | | |
|[custom](../../profiling/table-profiling-checks/#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../../profiling/table-profiling-checks/#CustomCheckSpecMap)| | | |









___  

## ColumnComparisonDailyPartitionedChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [ColumnComparisonDailyPartitionedChecksSpec](../column-daily-partitioned-checks/#ColumnComparisonDailyPartitionedChecksSpec)]| | | |









___  

## ColumnBoolDailyPartitionedChecksSpec  
Container of boolean data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_true_percent](../../../../checks/column/bool/true-percent/)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnTruePercentCheckSpec](../../../../checks/column/bool/true-percent/)| | | |
|[daily_partition_false_percent](../../../../checks/column/bool/false-percent/)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnFalsePercentCheckSpec](../../../../checks/column/bool/false-percent/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnIntegrityDailyPartitionedChecksSpec  
Container of integrity data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_foreign_key_not_match_count](../../../../checks/column/integrity/foreign-key-not-match-count/)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](../../../../checks/column/integrity/foreign-key-not-match-count/)| | | |
|[daily_partition_foreign_key_match_percent](../../../../checks/column/integrity/foreign-key-match-percent/)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](../../../../checks/column/integrity/foreign-key-match-percent/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## AnomalyStationaryPercentileMovingAverageRule05ParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|anomaly_percent|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a time window of 90 periods (days, etc.), but at least 30 readouts must exist to run the calculation. You can change the default value by modifying prediction_time_window parameterin Definitions section.|double| | |0.1<br/>|









___  

## AnomalyStationaryPercentileMovingAverage30DaysRule05ParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the previous values gathered within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|anomaly_percent|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|double| | |0.1<br/>|









___  

## ColumnNullsDailyPartitionedChecksSpec  
Container of nulls data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_nulls_count](../../../../checks/column/nulls/nulls-count/)|Verifies that the number of null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNullsCountCheckSpec](../../../../checks/column/nulls/nulls-count/)| | | |
|[daily_partition_nulls_percent](../../../../checks/column/nulls/nulls-percent/)|Verifies that the percentage of null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNullsPercentCheckSpec](../../../../checks/column/nulls/nulls-percent/)| | | |
|[daily_partition_nulls_percent_anomaly_stationary_30_days](../../../../checks/column/nulls/nulls-percent-anomaly-stationary-30-days/)|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyStationaryNullPercent30DaysCheckSpec](../../../../checks/column/nulls/nulls-percent-anomaly-stationary-30-days/)| | | |
|[daily_partition_nulls_percent_anomaly_stationary](../../../../checks/column/nulls/nulls-percent-anomaly-stationary/)|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnAnomalyStationaryNullPercentCheckSpec](../../../../checks/column/nulls/nulls-percent-anomaly-stationary/)| | | |
|[daily_partition_nulls_percent_change](../../../../checks/column/nulls/nulls-percent-change/)|Verifies that the null percent value in a column changed in a fixed rate since last readout.|[ColumnChangeNullPercentCheckSpec](../../../../checks/column/nulls/nulls-percent-change/)| | | |
|[daily_partition_nulls_percent_change_yesterday](../../../../checks/column/nulls/nulls-percent-change-yesterday/)|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeNullPercentSinceYesterdayCheckSpec](../../../../checks/column/nulls/nulls-percent-change-yesterday/)| | | |
|[daily_partition_nulls_percent_change_7_days](../../../../checks/column/nulls/nulls-percent-change-7-days/)|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|[ColumnChangeNullPercentSince7DaysCheckSpec](../../../../checks/column/nulls/nulls-percent-change-7-days/)| | | |
|[daily_partition_nulls_percent_change_30_days](../../../../checks/column/nulls/nulls-percent-change-30-days/)|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|[ColumnChangeNullPercentSince30DaysCheckSpec](../../../../checks/column/nulls/nulls-percent-change-30-days/)| | | |
|[daily_partition_not_nulls_count](../../../../checks/column/nulls/not-nulls-count/)|Verifies that the number of not null values in a column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNotNullsCountCheckSpec](../../../../checks/column/nulls/not-nulls-count/)| | | |
|[daily_partition_not_nulls_percent](../../../../checks/column/nulls/not-nulls-percent/)|Verifies that the percentage of not null values in a column does not exceed the set percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNotNullsPercentCheckSpec](../../../../checks/column/nulls/not-nulls-percent/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnDatatypeDailyPartitionedChecksSpec  
Container of datatype data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_string_datatype_detected](../../../../checks/column/datatype/string-datatype-detected/)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDatatypeStringDatatypeDetectedCheckSpec](../../../../checks/column/datatype/string-datatype-detected/)| | | |
|[daily_partition_string_datatype_changed](../../../../checks/column/datatype/string-datatype-changed/)|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDatatypeStringDatatypeChangedCheckSpec](../../../../checks/column/datatype/string-datatype-changed/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnNumericDailyPartitionedChecksSpec  
Container of numeric data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_negative_count](../../../../checks/column/numeric/negative-count/)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNegativeCountCheckSpec](../../../../checks/column/numeric/negative-count/)| | | |
|[daily_partition_negative_percent](../../../../checks/column/numeric/negative-percent/)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNegativePercentCheckSpec](../../../../checks/column/numeric/negative-percent/)| | | |
|[daily_partition_non_negative_count](../../../../checks/column/numeric/non-negative-count/)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNonNegativeCountCheckSpec](../../../../checks/column/numeric/non-negative-count/)| | | |
|[daily_partition_non_negative_percent](../../../../checks/column/numeric/non-negative-percent/)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNonNegativePercentCheckSpec](../../../../checks/column/numeric/non-negative-percent/)| | | |
|[daily_partition_expected_numbers_in_use_count](../../../../checks/column/numeric/expected-numbers-in-use-count/)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnExpectedNumbersInUseCountCheckSpec](../../../../checks/column/numeric/expected-numbers-in-use-count/)| | | |
|[daily_partition_number_value_in_set_percent](../../../../checks/column/numeric/number-value-in-set-percent/)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNumberValueInSetPercentCheckSpec](../../../../checks/column/numeric/number-value-in-set-percent/)| | | |
|[daily_partition_values_in_range_numeric_percent](../../../../checks/column/numeric/values-in-range-numeric-percent/)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValuesInRangeNumericPercentCheckSpec](../../../../checks/column/numeric/values-in-range-numeric-percent/)| | | |
|[daily_partition_values_in_range_integers_percent](../../../../checks/column/numeric/values-in-range-integers-percent/)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValuesInRangeIntegersPercentCheckSpec](../../../../checks/column/numeric/values-in-range-integers-percent/)| | | |
|[daily_partition_value_below_min_value_count](../../../../checks/column/numeric/value-below-min-value-count/)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueBelowMinValueCountCheckSpec](../../../../checks/column/numeric/value-below-min-value-count/)| | | |
|[daily_partition_value_below_min_value_percent](../../../../checks/column/numeric/value-below-min-value-percent/)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueBelowMinValuePercentCheckSpec](../../../../checks/column/numeric/value-below-min-value-percent/)| | | |
|[daily_partition_value_above_max_value_count](../../../../checks/column/numeric/value-above-max-value-count/)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueAboveMaxValueCountCheckSpec](../../../../checks/column/numeric/value-above-max-value-count/)| | | |
|[daily_partition_value_above_max_value_percent](../../../../checks/column/numeric/value-above-max-value-percent/)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValueAboveMaxValuePercentCheckSpec](../../../../checks/column/numeric/value-above-max-value-percent/)| | | |
|[daily_partition_max_in_range](../../../../checks/column/numeric/max-in-range/)|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMaxInRangeCheckSpec](../../../../checks/column/numeric/max-in-range/)| | | |
|[daily_partition_min_in_range](../../../../checks/column/numeric/min-in-range/)|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMinInRangeCheckSpec](../../../../checks/column/numeric/min-in-range/)| | | |
|[daily_partition_mean_in_range](../../../../checks/column/numeric/mean-in-range/)|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMeanInRangeCheckSpec](../../../../checks/column/numeric/mean-in-range/)| | | |
|[daily_partition_percentile_in_range](../../../../checks/column/numeric/percentile-in-range/)|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentileInRangeCheckSpec](../../../../checks/column/numeric/percentile-in-range/)| | | |
|[daily_partition_median_in_range](../../profiling/column-profiling-checks/#ColumnMedianInRangeCheckSpec)|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMedianInRangeCheckSpec](../../profiling/column-profiling-checks/#ColumnMedianInRangeCheckSpec)| | | |
|[daily_partition_percentile_10_in_range](../../profiling/column-profiling-checks/#ColumnPercentile10InRangeCheckSpec)|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile10InRangeCheckSpec](../../profiling/column-profiling-checks/#ColumnPercentile10InRangeCheckSpec)| | | |
|[daily_partition_percentile_25_in_range](../../profiling/column-profiling-checks/#ColumnPercentile25InRangeCheckSpec)|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile25InRangeCheckSpec](../../profiling/column-profiling-checks/#ColumnPercentile25InRangeCheckSpec)| | | |
|[daily_partition_percentile_75_in_range](../../profiling/column-profiling-checks/#ColumnPercentile75InRangeCheckSpec)|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile75InRangeCheckSpec](../../profiling/column-profiling-checks/#ColumnPercentile75InRangeCheckSpec)| | | |
|[daily_partition_percentile_90_in_range](../../profiling/column-profiling-checks/#ColumnPercentile90InRangeCheckSpec)|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile90InRangeCheckSpec](../../profiling/column-profiling-checks/#ColumnPercentile90InRangeCheckSpec)| | | |
|[daily_partition_sample_stddev_in_range](../../../../checks/column/numeric/sample-stddev-in-range/)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSampleStddevInRangeCheckSpec](../../../../checks/column/numeric/sample-stddev-in-range/)| | | |
|[daily_partition_population_stddev_in_range](../../../../checks/column/numeric/population-stddev-in-range/)|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPopulationStddevInRangeCheckSpec](../../../../checks/column/numeric/population-stddev-in-range/)| | | |
|[daily_partition_sample_variance_in_range](../../../../checks/column/numeric/sample-variance-in-range/)|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSampleVarianceInRangeCheckSpec](../../../../checks/column/numeric/sample-variance-in-range/)| | | |
|[daily_partition_population_variance_in_range](../../../../checks/column/numeric/population-variance-in-range/)|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPopulationVarianceInRangeCheckSpec](../../../../checks/column/numeric/population-variance-in-range/)| | | |
|[daily_partition_sum_in_range](../../../../checks/column/numeric/sum-in-range/)|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSumInRangeCheckSpec](../../../../checks/column/numeric/sum-in-range/)| | | |
|[daily_partition_invalid_latitude_count](../../../../checks/column/numeric/invalid-latitude-count/)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnInvalidLatitudeCountCheckSpec](../../../../checks/column/numeric/invalid-latitude-count/)| | | |
|[daily_partition_valid_latitude_percent](../../../../checks/column/numeric/valid-latitude-percent/)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValidLatitudePercentCheckSpec](../../../../checks/column/numeric/valid-latitude-percent/)| | | |
|[daily_partition_invalid_longitude_count](../../../../checks/column/numeric/invalid-longitude-count/)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnInvalidLongitudeCountCheckSpec](../../../../checks/column/numeric/invalid-longitude-count/)| | | |
|[daily_partition_valid_longitude_percent](../../../../checks/column/numeric/valid-longitude-percent/)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValidLongitudePercentCheckSpec](../../../../checks/column/numeric/valid-longitude-percent/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnAnomalyStationaryPartitionDistinctCountCheckSpec  
Column-level check that ensures that the distinct count value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days. Use in partitioned checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../../../sensors/column/uniqueness-column-sensors/#distinct-count)|Data quality check parameters|[ColumnUniquenessDistinctCountSensorParametersSpec](../../../sensors/column/uniqueness-column-sensors/#distinct-count)| | | |
|[warning](../column-daily-partitioned-checks/#AnomalyStationaryPercentileMovingAverageRule1ParametersSpec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[AnomalyStationaryPercentileMovingAverageRule1ParametersSpec](../column-daily-partitioned-checks/#AnomalyStationaryPercentileMovingAverageRule1ParametersSpec)| | | |
|[error](../column-daily-partitioned-checks/#AnomalyStationaryPercentileMovingAverageRule05ParametersSpec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[AnomalyStationaryPercentileMovingAverageRule05ParametersSpec](../column-daily-partitioned-checks/#AnomalyStationaryPercentileMovingAverageRule05ParametersSpec)| | | |
|[fatal](../../../rules/Percentile/#anomaly-stationary-percentile-moving-average)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[AnomalyStationaryPercentileMovingAverageRule01ParametersSpec](../../../rules/Percentile/#anomaly-stationary-percentile-moving-average)| | | |
|[schedule_override](../../profiling/table-profiling-checks/#MonitoringScheduleSpec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](../../profiling/table-profiling-checks/#MonitoringScheduleSpec)| | | |
|[comments](../../profiling/table-profiling-checks/#CommentsListSpec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](../../profiling/table-profiling-checks/#CommentsListSpec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___  

## ColumnStringsDailyPartitionedChecksSpec  
Container of strings data quality partitioned checks on a column level that are checking at a daily level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_string_max_length](../../../../checks/column/strings/string-max-length/)|Verifies that the length of string in a column does not exceed the maximum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMaxLengthCheckSpec](../../../../checks/column/strings/string-max-length/)| | | |
|[daily_partition_string_min_length](../../../../checks/column/strings/string-min-length/)|Verifies that the length of string in a column does not fall below the minimum accepted length. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMinLengthCheckSpec](../../../../checks/column/strings/string-min-length/)| | | |
|[daily_partition_string_mean_length](../../../../checks/column/strings/string-mean-length/)|Verifies that the length of string in a column does not exceed the mean accepted length. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMeanLengthCheckSpec](../../../../checks/column/strings/string-mean-length/)| | | |
|[daily_partition_string_length_below_min_length_count](../../../../checks/column/strings/string-length-below-min-length-count/)|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthBelowMinLengthCountCheckSpec](../../../../checks/column/strings/string-length-below-min-length-count/)| | | |
|[daily_partition_string_length_below_min_length_percent](../../../../checks/column/strings/string-length-below-min-length-percent/)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](../../../../checks/column/strings/string-length-below-min-length-percent/)| | | |
|[daily_partition_string_length_above_max_length_count](../../../../checks/column/strings/string-length-above-max-length-count/)|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](../../../../checks/column/strings/string-length-above-max-length-count/)| | | |
|[daily_partition_string_length_above_max_length_percent](../../../../checks/column/strings/string-length-above-max-length-percent/)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](../../../../checks/column/strings/string-length-above-max-length-percent/)| | | |
|[daily_partition_string_length_in_range_percent](../../../../checks/column/strings/string-length-in-range-percent/)|The check counts the percentage of those strings with length in the range provided by the user in the column. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringLengthInRangePercentCheckSpec](../../../../checks/column/strings/string-length-in-range-percent/)| | | |
|[daily_partition_string_empty_count](../../../../checks/column/strings/string-empty-count/)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringEmptyCountCheckSpec](../../../../checks/column/strings/string-empty-count/)| | | |
|[daily_partition_string_empty_percent](../../../../checks/column/strings/string-empty-percent/)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringEmptyPercentCheckSpec](../../../../checks/column/strings/string-empty-percent/)| | | |
|[daily_partition_string_whitespace_count](../../../../checks/column/strings/string-whitespace-count/)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringWhitespaceCountCheckSpec](../../../../checks/column/strings/string-whitespace-count/)| | | |
|[daily_partition_string_whitespace_percent](../../../../checks/column/strings/string-whitespace-percent/)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringWhitespacePercentCheckSpec](../../../../checks/column/strings/string-whitespace-percent/)| | | |
|[daily_partition_string_surrounded_by_whitespace_count](../../../../checks/column/strings/string-surrounded-by-whitespace-count/)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](../../../../checks/column/strings/string-surrounded-by-whitespace-count/)| | | |
|[daily_partition_string_surrounded_by_whitespace_percent](../../../../checks/column/strings/string-surrounded-by-whitespace-percent/)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](../../../../checks/column/strings/string-surrounded-by-whitespace-percent/)| | | |
|[daily_partition_string_null_placeholder_count](../../../../checks/column/strings/string-null-placeholder-count/)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNullPlaceholderCountCheckSpec](../../../../checks/column/strings/string-null-placeholder-count/)| | | |
|[daily_partition_string_null_placeholder_percent](../../../../checks/column/strings/string-null-placeholder-percent/)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNullPlaceholderPercentCheckSpec](../../../../checks/column/strings/string-null-placeholder-percent/)| | | |
|[daily_partition_string_boolean_placeholder_percent](../../../../checks/column/strings/string-boolean-placeholder-percent/)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringBooleanPlaceholderPercentCheckSpec](../../../../checks/column/strings/string-boolean-placeholder-percent/)| | | |
|[daily_partition_string_parsable_to_integer_percent](../../../../checks/column/strings/string-parsable-to-integer-percent/)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringParsableToIntegerPercentCheckSpec](../../../../checks/column/strings/string-parsable-to-integer-percent/)| | | |
|[daily_partition_string_parsable_to_float_percent](../../../../checks/column/strings/string-parsable-to-float-percent/)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringParsableToFloatPercentCheckSpec](../../../../checks/column/strings/string-parsable-to-float-percent/)| | | |
|[daily_partition_expected_strings_in_use_count](../../../../checks/column/strings/expected-strings-in-use-count/)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnExpectedStringsInUseCountCheckSpec](../../../../checks/column/strings/expected-strings-in-use-count/)| | | |
|[daily_partition_string_value_in_set_percent](../../../../checks/column/strings/string-value-in-set-percent/)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValueInSetPercentCheckSpec](../../../../checks/column/strings/string-value-in-set-percent/)| | | |
|[daily_partition_string_valid_dates_percent](../../../../checks/column/strings/string-valid-dates-percent/)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidDatesPercentCheckSpec](../../../../checks/column/strings/string-valid-dates-percent/)| | | |
|[daily_partition_string_valid_country_code_percent](../../../../checks/column/strings/string-valid-country-code-percent/)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidCountryCodePercentCheckSpec](../../../../checks/column/strings/string-valid-country-code-percent/)| | | |
|[daily_partition_string_valid_currency_code_percent](../../../../checks/column/strings/string-valid-currency-code-percent/)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidCurrencyCodePercentCheckSpec](../../../../checks/column/strings/string-valid-currency-code-percent/)| | | |
|[daily_partition_string_invalid_email_count](../../../../checks/column/strings/string-invalid-email-count/)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidEmailCountCheckSpec](../../../../checks/column/strings/string-invalid-email-count/)| | | |
|[daily_partition_string_invalid_uuid_count](../../../../checks/column/strings/string-invalid-uuid-count/)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidUuidCountCheckSpec](../../../../checks/column/strings/string-invalid-uuid-count/)| | | |
|[daily_partition_valid_uuid_percent](../../../../checks/column/strings/string-valid-uuid-percent/)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringValidUuidPercentCheckSpec](../../../../checks/column/strings/string-valid-uuid-percent/)| | | |
|[daily_partition_string_invalid_ip4_address_count](../../../../checks/column/strings/string-invalid-ip4-address-count/)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidIp4AddressCountCheckSpec](../../../../checks/column/strings/string-invalid-ip4-address-count/)| | | |
|[daily_partition_string_invalid_ip6_address_count](../../../../checks/column/strings/string-invalid-ip6-address-count/)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringInvalidIp6AddressCountCheckSpec](../../../../checks/column/strings/string-invalid-ip6-address-count/)| | | |
|[daily_partition_string_not_match_regex_count](../../../../checks/column/strings/string-not-match-regex-count/)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNotMatchRegexCountCheckSpec](../../../../checks/column/strings/string-not-match-regex-count/)| | | |
|[daily_partition_string_match_regex_percent](../../../../checks/column/strings/string-match-regex-percent/)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMatchRegexPercentCheckSpec](../../../../checks/column/strings/string-match-regex-percent/)| | | |
|[daily_partition_string_not_match_date_regex_count](../../../../checks/column/strings/string-not-match-date-regex-count/)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringNotMatchDateRegexCountCheckSpec](../../../../checks/column/strings/string-not-match-date-regex-count/)| | | |
|[daily_partition_string_match_date_regex_percent](../../../../checks/column/strings/string-match-date-regex-percent/)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMatchDateRegexPercentCheckSpec](../../../../checks/column/strings/string-match-date-regex-percent/)| | | |
|[daily_partition_string_match_name_regex_percent](../../../../checks/column/strings/string-match-name-regex-percent/)|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnStringMatchNameRegexPercentCheckSpec](../../../../checks/column/strings/string-match-name-regex-percent/)| | | |
|[daily_partition_expected_strings_in_top_values_count](../../../../checks/column/strings/expected-strings-in-top-values-count/)|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnExpectedStringsInTopValuesCountCheckSpec](../../../../checks/column/strings/expected-strings-in-top-values-count/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## ColumnAnomalyDailyPartitionedChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_mean_anomaly_stationary_30_days](../../../../checks/column/anomaly/mean-anomaly-stationary-30-days/)|Verifies that the mean value in a column is within a percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryMean30DaysCheckSpec](../../../../checks/column/anomaly/mean-anomaly-stationary-30-days/)| | | |
|[daily_partition_mean_anomaly_stationary](../../../../checks/column/anomaly/mean-anomaly-stationary/)|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryMeanCheckSpec](../../../../checks/column/anomaly/mean-anomaly-stationary/)| | | |
|[daily_partition_median_anomaly_stationary_30_days](../../../../checks/column/anomaly/median-anomaly-stationary-30-days/)|Verifies that the median in a column is within a percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryMedian30DaysCheckSpec](../../../../checks/column/anomaly/median-anomaly-stationary-30-days/)| | | |
|[daily_partition_median_anomaly_stationary](../../../../checks/column/anomaly/median-anomaly-stationary/)|Verifies that the median in a column is within a percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryMedianCheckSpec](../../../../checks/column/anomaly/median-anomaly-stationary/)| | | |
|[daily_partition_sum_anomaly_stationary_30_days](../../../../checks/column/anomaly/sum-anomaly-stationary-30-days/)|Verifies that the sum in a column is within a percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryPartitionSum30DaysCheckSpec](../../../../checks/column/anomaly/sum-anomaly-stationary-30-days/)| | | |
|[daily_partition_sum_anomaly_stationary](../../../../checks/column/anomaly/sum-anomaly-stationary/)|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryPartitionSumCheckSpec](../../../../checks/column/anomaly/sum-anomaly-stationary/)| | | |
|[daily_partition_mean_change](../../../../checks/column/anomaly/mean-change/)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](../../../../checks/column/anomaly/mean-change/)| | | |
|[daily_partition_mean_change_yesterday](../../../../checks/column/anomaly/mean-change-yesterday/)|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMeanSinceYesterdayCheckSpec](../../../../checks/column/anomaly/mean-change-yesterday/)| | | |
|[daily_partition_mean_change_7_days](../../../../checks/column/anomaly/mean-change-7-days/)|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMeanSince7DaysCheckSpec](../../../../checks/column/anomaly/mean-change-7-days/)| | | |
|[daily_partition_mean_change_30_days](../../../../checks/column/anomaly/mean-change-30-days/)|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMeanSince30DaysCheckSpec](../../../../checks/column/anomaly/mean-change-30-days/)| | | |
|[daily_partition_median_change](../../../../checks/column/anomaly/median-change/)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](../../../../checks/column/anomaly/median-change/)| | | |
|[daily_partition_median_change_yesterday](../../../../checks/column/anomaly/median-change-yesterday/)|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMedianSinceYesterdayCheckSpec](../../../../checks/column/anomaly/median-change-yesterday/)| | | |
|[daily_partition_median_change_7_days](../../../../checks/column/anomaly/median-change-7-days/)|Verifies that the median in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMedianSince7DaysCheckSpec](../../../../checks/column/anomaly/median-change-7-days/)| | | |
|[daily_partition_median_change_30_days](../../../../checks/column/anomaly/median-change-30-days/)|Verifies that the median in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMedianSince30DaysCheckSpec](../../../../checks/column/anomaly/median-change-30-days/)| | | |
|[daily_partition_sum_change](../../../../checks/column/anomaly/sum-change/)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](../../../../checks/column/anomaly/sum-change/)| | | |
|[daily_partition_sum_change_yesterday](../../../../checks/column/anomaly/sum-change-yesterday/)|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeSumSinceYesterdayCheckSpec](../../../../checks/column/anomaly/sum-change-yesterday/)| | | |
|[daily_partition_sum_change_7_days](../../../../checks/column/anomaly/sum-change-7-days/)|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|[ColumnChangeSumSince7DaysCheckSpec](../../../../checks/column/anomaly/sum-change-7-days/)| | | |
|[daily_partition_sum_change_30_days](../../../../checks/column/anomaly/sum-change-30-days/)|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|[ColumnChangeSumSince30DaysCheckSpec](../../../../checks/column/anomaly/sum-change-30-days/)| | | |
|[custom_checks](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

