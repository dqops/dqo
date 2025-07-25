---
title: DQOps YAML file definitions
---
# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## ColumnDailyPartitionedCheckCategoriesSpec
Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`nulls`](./column-daily-partitioned-checks.md#columnnullsdailypartitionedchecksspec)</span>|Daily partitioned checks of nulls in the column|*[ColumnNullsDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columnnullsdailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`uniqueness`](./column-daily-partitioned-checks.md#columnuniquenessdailypartitionedchecksspec)</span>|Daily partitioned checks of uniqueness in the column|*[ColumnUniquenessDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columnuniquenessdailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`accepted_values`](./column-daily-partitioned-checks.md#columnacceptedvaluesdailypartitionedchecksspec)</span>|Configuration of accepted values checks on a column level|*[ColumnAcceptedValuesDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columnacceptedvaluesdailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`text`](./column-daily-partitioned-checks.md#columntextdailypartitionedchecksspec)</span>|Daily partitioned checks of text values in the column|*[ColumnTextDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columntextdailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`whitespace`](./column-daily-partitioned-checks.md#columnwhitespacedailypartitionedchecksspec)</span>|Configuration of column level checks that detect blank and whitespace values|*[ColumnWhitespaceDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columnwhitespacedailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`conversions`](./column-daily-partitioned-checks.md#columnconversionsdailypartitionedchecksspec)</span>|Configuration of conversion testing checks on a column level.|*[ColumnConversionsDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columnconversionsdailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`patterns`](./column-daily-partitioned-checks.md#columnpatternsdailypartitionedchecksspec)</span>|Daily partitioned pattern match checks on a column level|*[ColumnPatternsDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columnpatternsdailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`pii`](./column-daily-partitioned-checks.md#columnpiidailypartitionedchecksspec)</span>|Daily partitioned checks of Personal Identifiable Information (PII) in the column|*[ColumnPiiDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columnpiidailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`numeric`](./column-daily-partitioned-checks.md#columnnumericdailypartitionedchecksspec)</span>|Daily partitioned checks of numeric values in the column|*[ColumnNumericDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columnnumericdailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`anomaly`](./column-daily-partitioned-checks.md#columnanomalydailypartitionedchecksspec)</span>|Daily partitioned checks for anomalies in numeric columns|*[ColumnAnomalyDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columnanomalydailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`datetime`](./column-daily-partitioned-checks.md#columndatetimedailypartitionedchecksspec)</span>|Daily partitioned checks of datetime in the column|*[ColumnDatetimeDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columndatetimedailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`bool`](./column-daily-partitioned-checks.md#columnbooldailypartitionedchecksspec)</span>|Daily partitioned checks for booleans in the column|*[ColumnBoolDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columnbooldailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`integrity`](./column-daily-partitioned-checks.md#columnintegritydailypartitionedchecksspec)</span>|Daily partitioned checks for integrity in the column|*[ColumnIntegrityDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columnintegritydailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`custom_sql`](./column-daily-partitioned-checks.md#columncustomsqldailypartitionedchecksspec)</span>|Daily partitioned checks using custom SQL expressions evaluated on the column|*[ColumnCustomSqlDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columncustomsqldailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`datatype`](./column-daily-partitioned-checks.md#columndatatypedailypartitionedchecksspec)</span>|Daily partitioned checks for datatype in the column|*[ColumnDatatypeDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columndatatypedailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`comparisons`](./column-daily-partitioned-checks.md#columncomparisondailypartitionedchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[ColumnComparisonDailyPartitionedChecksSpecMap](./column-daily-partitioned-checks.md#columncomparisondailypartitionedchecksspecmap)*| | | |
|<span class="no-wrap-code ">[`custom`](../profiling/table-profiling-checks.md#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](../profiling/table-profiling-checks.md#customcheckspecmap)*| | | |



___

## ColumnNullsDailyPartitionedChecksSpec
Container of nulls data quality partitioned checks on a column level that are checking at a daily level.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_nulls_count`](../../../checks/column/nulls/nulls-count.md)</span>|Detects incomplete columns that contain any null values. Counts the number of rows having a null value. Raises a data quality issue when the count of null values is above a max_count threshold. Stores a separate data quality check result for each daily partition.|*[ColumnNullsCountCheckSpec](../../../checks/column/nulls/nulls-count.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_nulls_percent`](../../../checks/column/nulls/nulls-percent.md)</span>|Detects incomplete columns that contain any null values. Measures the percentage of rows having a null value. Raises a data quality issue when the percentage of null values is above a max_percent threshold. Stores a separate data quality check result for each daily partition.|*[ColumnNullsPercentCheckSpec](../../../checks/column/nulls/nulls-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_nulls_percent_anomaly`](../../../checks/column/nulls/nulls-percent-anomaly.md)</span>|Detects day-to-day anomalies in the percentage of null values. Raises a data quality issue when the rate of null values increases or decreases too much during the last 90 days.|*[ColumnNullPercentAnomalyStationaryCheckSpec](../../../checks/column/nulls/nulls-percent-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_not_nulls_count`](../../../checks/column/nulls/not-nulls-count.md)</span>|Verifies that a column contains a minimum number of non-null values. The default value of the *min_count* parameter is 1 to detect at least one value in a monitored column. Raises a data quality issue when the count of non-null values is below min_count. Stores a separate data quality check result for each daily partition.|*[ColumnNotNullsCountCheckSpec](../../../checks/column/nulls/not-nulls-count.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_not_nulls_percent`](../../../checks/column/nulls/not-nulls-percent.md)</span>|Detects columns that contain too many non-null values. Measures the percentage of rows that have non-null values. Raises a data quality issue when the percentage of non-null values is above max_percentage. Stores a separate data quality check result for each daily partition.|*[ColumnNotNullsPercentCheckSpec](../../../checks/column/nulls/not-nulls-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_empty_column_found`](../../../checks/column/nulls/empty-column-found.md)</span>|Detects empty columns that contain only null values. Counts the number of rows that have non-null values. Raises a data quality issue when the column is empty. Stores a separate data quality check result for each daily partition.|*[ColumnEmptyColumnFoundCheckSpec](../../../checks/column/nulls/empty-column-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_nulls_percent_change`](../../../checks/column/nulls/nulls-percent-change.md)</span>|Verifies that the null percent value in a column changed in a fixed rate since last readout.|*[ColumnNullPercentChangeCheckSpec](../../../checks/column/nulls/nulls-percent-change.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_nulls_percent_change_1_day`](../../../checks/column/nulls/nulls-percent-change-1-day.md)</span>|Verifies that the null percent value in a column changed in a fixed rate since the last readout from yesterday.|*[ColumnNullPercentChange1DayCheckSpec](../../../checks/column/nulls/nulls-percent-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_nulls_percent_change_7_days`](../../../checks/column/nulls/nulls-percent-change-7-days.md)</span>|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last week.|*[ColumnNullPercentChange7DaysCheckSpec](../../../checks/column/nulls/nulls-percent-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_nulls_percent_change_30_days`](../../../checks/column/nulls/nulls-percent-change-30-days.md)</span>|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last month.|*[ColumnNullPercentChange30DaysCheckSpec](../../../checks/column/nulls/nulls-percent-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnUniquenessDailyPartitionedChecksSpec
Container of uniqueness data quality partitioned checks on a column level that are checking at a daily level.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_distinct_count`](../../../checks/column/uniqueness/distinct-count.md)</span>|Verifies  that the number of distinct values stays within an accepted range. Stores a separate data quality check result for each daily partition.|*[ColumnDistinctCountCheckSpec](../../../checks/column/uniqueness/distinct-count.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_distinct_percent`](../../../checks/column/uniqueness/distinct-percent.md)</span>|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores a separate data quality check result for each daily partition.|*[ColumnDistinctPercentCheckSpec](../../../checks/column/uniqueness/distinct-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_duplicate_count`](../../../checks/column/uniqueness/duplicate-count.md)</span>|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|*[ColumnDuplicateCountCheckSpec](../../../checks/column/uniqueness/duplicate-count.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_duplicate_percent`](../../../checks/column/uniqueness/duplicate-percent.md)</span>|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Stores a separate data quality check result for each daily partition.|*[ColumnDuplicatePercentCheckSpec](../../../checks/column/uniqueness/duplicate-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_distinct_count_anomaly`](./column-daily-partitioned-checks.md#columndistinctcountanomalystationarypartitioncheckspec)</span>|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|*[ColumnDistinctCountAnomalyStationaryPartitionCheckSpec](./column-daily-partitioned-checks.md#columndistinctcountanomalystationarypartitioncheckspec)*| | | |
|<span class="no-wrap-code ">[`daily_partition_distinct_percent_anomaly`](../../../checks/column/uniqueness/distinct-percent-anomaly.md)</span>|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|*[ColumnDistinctPercentAnomalyStationaryCheckSpec](../../../checks/column/uniqueness/distinct-percent-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_distinct_count_change`](../../../checks/column/uniqueness/distinct-count-change.md)</span>|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|*[ColumnDistinctCountChangeCheckSpec](../../../checks/column/uniqueness/distinct-count-change.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_distinct_count_change_1_day`](../../../checks/column/uniqueness/distinct-count-change-1-day.md)</span>|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|*[ColumnDistinctCountChange1DayCheckSpec](../../../checks/column/uniqueness/distinct-count-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_distinct_count_change_7_days`](../../../checks/column/uniqueness/distinct-count-change-7-days.md)</span>|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last week.|*[ColumnDistinctCountChange7DaysCheckSpec](../../../checks/column/uniqueness/distinct-count-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_distinct_count_change_30_days`](../../../checks/column/uniqueness/distinct-count-change-30-days.md)</span>|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last month.|*[ColumnDistinctCountChange30DaysCheckSpec](../../../checks/column/uniqueness/distinct-count-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_distinct_percent_change`](../../../checks/column/uniqueness/distinct-percent-change.md)</span>|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|*[ColumnDistinctPercentChangeCheckSpec](../../../checks/column/uniqueness/distinct-percent-change.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_distinct_percent_change_1_day`](../../../checks/column/uniqueness/distinct-percent-change-1-day.md)</span>|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|*[ColumnDistinctPercentChange1DayCheckSpec](../../../checks/column/uniqueness/distinct-percent-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_distinct_percent_change_7_days`](../../../checks/column/uniqueness/distinct-percent-change-7-days.md)</span>|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last week.|*[ColumnDistinctPercentChange7DaysCheckSpec](../../../checks/column/uniqueness/distinct-percent-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_distinct_percent_change_30_days`](../../../checks/column/uniqueness/distinct-percent-change-30-days.md)</span>|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last month.|*[ColumnDistinctPercentChange30DaysCheckSpec](../../../checks/column/uniqueness/distinct-percent-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnDistinctCountAnomalyStationaryPartitionCheckSpec
This check monitors the count of distinct values and detects anomalies in the changes of the distinct count. It monitors a 90-day time window.
 The check is configured by setting a desired percentage of anomalies to identify as data quality issues.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../../sensors/column/uniqueness-column-sensors.md#distinct-count)</span>|Data quality check parameters|*[ColumnUniquenessDistinctCountSensorParametersSpec](../../sensors/column/uniqueness-column-sensors.md#distinct-count)*| | | |
|<span class="no-wrap-code ">[`warning`](./column-daily-partitioned-checks.md#anomalystationarycountvaluesrulewarning1pctparametersspec)</span>|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|*[AnomalyStationaryCountValuesRuleWarning1PctParametersSpec](./column-daily-partitioned-checks.md#anomalystationarycountvaluesrulewarning1pctparametersspec)*| | | |
|<span class="no-wrap-code ">[`error`](../../rules/Percentile.md#anomaly-stationary-count-values)</span>|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|*[AnomalyStationaryCountValuesRuleError05PctParametersSpec](../../rules/Percentile.md#anomaly-stationary-count-values)*| | | |
|<span class="no-wrap-code ">[`fatal`](./column-daily-partitioned-checks.md#anomalystationarycountvaluesrulefatal01pctparametersspec)</span>|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|*[AnomalyStationaryCountValuesRuleFatal01PctParametersSpec](./column-daily-partitioned-checks.md#anomalystationarycountvaluesrulefatal01pctparametersspec)*| | | |
|<span class="no-wrap-code ">[`schedule_override`](../profiling/table-profiling-checks.md#cronschedulespec)</span>|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|*[CronScheduleSpec](../profiling/table-profiling-checks.md#cronschedulespec)*| | | |
|<span class="no-wrap-code ">[`comments`](../profiling/table-profiling-checks.md#commentslistspec)</span>|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|*[CommentsListSpec](../profiling/table-profiling-checks.md#commentslistspec)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|*boolean*| | | |
|<span class="no-wrap-code ">`exclude_from_kpi`</span>|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|*boolean*| | | |
|<span class="no-wrap-code ">`include_in_sla`</span>|Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.|*boolean*| | | |
|<span class="no-wrap-code ">`quality_dimension`</span>|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|*string*| | | |
|<span class="no-wrap-code ">`display_name`</span>|Data quality check display name that can be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|*string*| | | |
|<span class="no-wrap-code ">`data_grouping`</span>|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|*string*| | | |
|<span class="no-wrap-code ">`always_collect_error_samples`</span>|Forces collecting error samples for this check whenever it fails, even if it is a monitoring check that is run by a scheduler, and running an additional query to collect error samples will impose additional load on the data source.|*boolean*| | | |
|<span class="no-wrap-code ">`do_not_schedule`</span>|Disables running this check by a DQOps CRON scheduler. When a check is disabled from scheduling, it can be only triggered from the user interface or by submitting &quot;run checks&quot; job.|*boolean*| | | |



___

## AnomalyStationaryCountValuesRuleWarning1PctParametersSpec
Data quality rule that detects anomalies in a stationary time series of counts of values.
 The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 The rule uses the time window of the last 90 days, but at least 30 historical measures must be present to run the calculation.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`anomaly_percent`</span>|The probability (in percent) that the count of values (records) is an anomaly because the value is outside the regular range of counts. The default time window of 90 time periods (days, etc.) is used, but at least 30 readouts must exist to run the calculation.|*double*| | | |
|<span class="no-wrap-code ">`use_ai`</span>|Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in a trial distribution of DQOps. Please contact DQOps support to upgrade your instance to a full DQOps instance.|*boolean*| | | |



___

## AnomalyStationaryCountValuesRuleFatal01PctParametersSpec
Data quality rule that detects anomalies in a stationary time series of counts of values.
 The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 The rule uses the time window of the last 90 days, but at least 30 historical measures must be present to run the calculation.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`anomaly_percent`</span>|The probability (in percent) that the count of values (records) is an anomaly because the value is outside the regular range of counts. The default time window of 90 time periods (days, etc.) is used, but at least 30 readouts must exist to run the calculation.|*double*| | | |
|<span class="no-wrap-code ">`use_ai`</span>|Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in a trial distribution of DQOps. Please contact DQOps support to upgrade your instance to a full DQOps instance.|*boolean*| | | |



___

## ColumnAcceptedValuesDailyPartitionedChecksSpec
Container of accepted values data quality partitioned checks on a column level that are checking at a daily level.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_text_found_in_set_percent`](../../../checks/column/accepted_values/text-found-in-set-percent.md)</span>|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnTextFoundInSetPercentCheckSpec](../../../checks/column/accepted_values/text-found-in-set-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_number_found_in_set_percent`](../../../checks/column/accepted_values/number-found-in-set-percent.md)</span>|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnNumberFoundInSetPercentCheckSpec](../../../checks/column/accepted_values/number-found-in-set-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_expected_text_values_in_use_count`](../../../checks/column/accepted_values/expected-text-values-in-use-count.md)</span>|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each daily partition.|*[ColumnExpectedTextValuesInUseCountCheckSpec](../../../checks/column/accepted_values/expected-text-values-in-use-count.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_expected_texts_in_top_values_count`](../../../checks/column/accepted_values/expected-texts-in-top-values-count.md)</span>|Verifies that the top X most popular column values contain all values from a list of expected values. Stores a separate data quality check result for each daily partition.|*[ColumnExpectedTextsInTopValuesCountCheckSpec](../../../checks/column/accepted_values/expected-texts-in-top-values-count.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_expected_numbers_in_use_count`](../../../checks/column/accepted_values/expected-numbers-in-use-count.md)</span>|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores a separate data quality check result for each daily partition.|*[ColumnExpectedNumbersInUseCountCheckSpec](../../../checks/column/accepted_values/expected-numbers-in-use-count.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_valid_country_code_percent`](../../../checks/column/accepted_values/text-valid-country-code-percent.md)</span>|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextValidCountryCodePercentCheckSpec](../../../checks/column/accepted_values/text-valid-country-code-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_valid_currency_code_percent`](../../../checks/column/accepted_values/text-valid-currency-code-percent.md)</span>|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextValidCurrencyCodePercentCheckSpec](../../../checks/column/accepted_values/text-valid-currency-code-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnTextDailyPartitionedChecksSpec
Container of text data quality partitioned checks on a column level that are checking at a daily partition level.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_text_min_length`](../../../checks/column/text/text-min-length.md)</span>|This check finds the length of the shortest text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the shortest text is too short. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextMinLengthCheckSpec](../../../checks/column/text/text-min-length.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_max_length`](../../../checks/column/text/text-max-length.md)</span>|This check finds the length of the longest text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the texts are too long or not long enough. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextMaxLengthCheckSpec](../../../checks/column/text/text-max-length.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_mean_length`](../../../checks/column/text/text-mean-length.md)</span>|Verifies that the mean (average) length of texts in a column is within an accepted range. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextMeanLengthCheckSpec](../../../checks/column/text/text-mean-length.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_length_below_min_length`](../../../checks/column/text/text-length-below-min-length.md)</span>|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextLengthBelowMinLengthCheckSpec](../../../checks/column/text/text-length-below-min-length.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_length_below_min_length_percent`](../../../checks/column/text/text-length-below-min-length-percent.md)</span>|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextLengthBelowMinLengthPercentCheckSpec](../../../checks/column/text/text-length-below-min-length-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_length_above_max_length`](../../../checks/column/text/text-length-above-max-length.md)</span>|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextLengthAboveMaxLengthCheckSpec](../../../checks/column/text/text-length-above-max-length.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_length_above_max_length_percent`](../../../checks/column/text/text-length-above-max-length-percent.md)</span>|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextLengthAboveMaxLengthPercentCheckSpec](../../../checks/column/text/text-length-above-max-length-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_length_in_range_percent`](../../../checks/column/text/text-length-in-range-percent.md)</span>|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextLengthInRangePercentCheckSpec](../../../checks/column/text/text-length-in-range-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_min_word_count`](../../../checks/column/text/min-word-count.md)</span>|This check finds the lowest word count of text in a column. Then, it verifies that the minimum length is within an accepted range. It detects that the text contains too less words.|*[ColumnTextMinWordCountCheckSpec](../../../checks/column/text/min-word-count.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_max_word_count`](../../../checks/column/text/max-word-count.md)</span>|This check finds the highest word count of text in a column. Then, it verifies that the maximum length is within an accepted range. It detects that the text contains too many words.|*[ColumnTextMaxWordCountCheckSpec](../../../checks/column/text/max-word-count.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnWhitespaceDailyPartitionedChecksSpec
Container of whitespace values detection data quality partitioned checks on a column level that are checking at a daily level.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_empty_text_found`](../../../checks/column/whitespace/empty-text-found.md)</span>|Detects empty texts (not null, zero-length texts). This check counts empty and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each daily partition.|*[ColumnWhitespaceEmptyTextFoundCheckSpec](../../../checks/column/whitespace/empty-text-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_whitespace_text_found`](../../../checks/column/whitespace/whitespace-text-found.md)</span>|Detects texts that contain only spaces and other whitespace characters. It raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each daily partition.|*[ColumnWhitespaceWhitespaceTextFoundCheckSpec](../../../checks/column/whitespace/whitespace-text-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_null_placeholder_text_found`](../../../checks/column/whitespace/null-placeholder-text-found.md)</span>|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*. It counts null placeholders and raises a data quality issue when their count exceeds a *max_count* parameter value. Stores a separate data quality check result for each daily partition.|*[ColumnWhitespaceNullPlaceholderTextFoundCheckSpec](../../../checks/column/whitespace/null-placeholder-text-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_empty_text_percent`](../../../checks/column/whitespace/empty-text-percent.md)</span>|Detects empty texts (not null, zero-length texts) and measures their percentage in the column. This check verifies that the rate of empty strings in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnWhitespaceEmptyTextPercentCheckSpec](../../../checks/column/whitespace/empty-text-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_whitespace_text_percent`](../../../checks/column/whitespace/whitespace-text-percent.md)</span>|Detects texts that contain only spaces and other whitespace characters and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores a separate data quality check result for each daily partition.|*[ColumnWhitespaceWhitespaceTextPercentCheckSpec](../../../checks/column/whitespace/whitespace-text-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_null_placeholder_text_percent`](../../../checks/column/whitespace/null-placeholder-text-percent.md)</span>|Detects texts that are well-known placeholders of null values, such as *None*, *null*, *n/a*, and measures their percentage in the column. It raises a data quality issue when their rate exceeds a *max_percent* parameter value. Stores a separate data quality check result for each daily partition.|*[ColumnWhitespaceNullPlaceholderTextPercentCheckSpec](../../../checks/column/whitespace/null-placeholder-text-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_surrounded_by_whitespace_found`](../../../checks/column/whitespace/text-surrounded-by-whitespace-found.md)</span>|Detects text values that are surrounded by whitespace characters on any side. This check counts whitespace-surrounded texts and raises a data quality issue when their count exceeds the *max_count* parameter value. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnWhitespaceTextSurroundedByWhitespaceFoundCheckSpec](../../../checks/column/whitespace/text-surrounded-by-whitespace-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_surrounded_by_whitespace_percent`](../../../checks/column/whitespace/text-surrounded-by-whitespace-percent.md)</span>|This check detects text values that are surrounded by whitespace characters on any side and measures their percentage. This check raises a data quality issue when their percentage exceeds the *max_percent* parameter value. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnWhitespaceTextSurroundedByWhitespacePercentCheckSpec](../../../checks/column/whitespace/text-surrounded-by-whitespace-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnConversionsDailyPartitionedChecksSpec
Container of conversion test checks that are monitoring if text values are convertible to a target data type at a daily partition level.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_text_parsable_to_boolean_percent`](../../../checks/column/conversions/text-parsable-to-boolean-percent.md)</span>|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextParsableToBooleanPercentCheckSpec](../../../checks/column/conversions/text-parsable-to-boolean-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_parsable_to_integer_percent`](../../../checks/column/conversions/text-parsable-to-integer-percent.md)</span>|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextParsableToIntegerPercentCheckSpec](../../../checks/column/conversions/text-parsable-to-integer-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_parsable_to_float_percent`](../../../checks/column/conversions/text-parsable-to-float-percent.md)</span>|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextParsableToFloatPercentCheckSpec](../../../checks/column/conversions/text-parsable-to-float-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_parsable_to_date_percent`](../../../checks/column/conversions/text-parsable-to-date-percent.md)</span>|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|*[ColumnTextParsableToDatePercentCheckSpec](../../../checks/column/conversions/text-parsable-to-date-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnPatternsDailyPartitionedChecksSpec
Container of built-in preconfigured daily partition checks on a column level that are checking for values matching patterns (regular expressions) in text columns.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_text_not_matching_regex_found`](../../../checks/column/patterns/text-not-matching-regex-found.md)</span>|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|*[ColumnTextNotMatchingRegexFoundCheckSpec](../../../checks/column/patterns/text-not-matching-regex-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_texts_not_matching_regex_percent`](../../../checks/column/patterns/texts-not-matching-regex-percent.md)</span>|Verifies that the percentage of strings matching the custom regular expression pattern does not exceed the maximum accepted percentage.|*[ColumnTextsNotMatchingRegexPercentCheckSpec](../../../checks/column/patterns/texts-not-matching-regex-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_invalid_email_format_found`](../../../checks/column/patterns/invalid-email-format-found.md)</span>|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|*[ColumnInvalidEmailFormatFoundCheckSpec](../../../checks/column/patterns/invalid-email-format-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_invalid_email_format_percent`](../../../checks/column/patterns/invalid-email-format-percent.md)</span>|Verifies that the percentage of invalid emails in a text column does not exceed the maximum accepted percentage.|*[ColumnInvalidEmailFormatPercentCheckSpec](../../../checks/column/patterns/invalid-email-format-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_not_matching_date_pattern_found`](../../../checks/column/patterns/text-not-matching-date-pattern-found.md)</span>|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|*[ColumnTextNotMatchingDatePatternFoundCheckSpec](../../../checks/column/patterns/text-not-matching-date-pattern-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_not_matching_date_pattern_percent`](../../../checks/column/patterns/text-not-matching-date-pattern-percent.md)</span>|Verifies that the percentage of texts matching the date format regular expression in a column does not exceed the maximum accepted percentage.|*[ColumnTextNotMatchingDatePatternPercentCheckSpec](../../../checks/column/patterns/text-not-matching-date-pattern-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_not_matching_name_pattern_percent`](../../../checks/column/patterns/text-not-matching-name-pattern-percent.md)</span>|Verifies that the percentage of texts matching the name regular expression does not exceed the maximum accepted percentage.|*[ColumnTextNotMatchingNamePatternPercentCheckSpec](../../../checks/column/patterns/text-not-matching-name-pattern-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_invalid_uuid_format_found`](../../../checks/column/patterns/invalid-uuid-format-found.md)</span>|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|*[ColumnInvalidUuidFormatFoundCheckSpec](../../../checks/column/patterns/invalid-uuid-format-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_invalid_uuid_format_percent`](../../../checks/column/patterns/invalid-uuid-format-percent.md)</span>|Verifies that the percentage of invalid UUID in a text column does not exceed the maximum accepted percentage.|*[ColumnInvalidUuidFormatPercentCheckSpec](../../../checks/column/patterns/invalid-uuid-format-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_invalid_ip4_address_format_found`](../../../checks/column/patterns/invalid-ip4-address-format-found.md)</span>|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|*[ColumnInvalidIp4AddressFormatFoundCheckSpec](../../../checks/column/patterns/invalid-ip4-address-format-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_invalid_ip6_address_format_found`](../../../checks/column/patterns/invalid-ip6-address-format-found.md)</span>|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|*[ColumnInvalidIp6AddressFormatFoundCheckSpec](../../../checks/column/patterns/invalid-ip6-address-format-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_invalid_usa_phone_format_found`](../../../checks/column/patterns/invalid-usa-phone-format-found.md)</span>|Verifies that the number of invalid USA phone numbers in a text column does not exceed the maximum accepted count.|*[ColumnInvalidUsaPhoneFoundCheckSpec](../../../checks/column/patterns/invalid-usa-phone-format-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_invalid_usa_zipcode_format_found`](../../../checks/column/patterns/invalid-usa-zipcode-format-found.md)</span>|Verifies that the number of invalid zip codes in a text column does not exceed the maximum accepted count.|*[ColumnInvalidUsaZipcodeFoundCheckSpec](../../../checks/column/patterns/invalid-usa-zipcode-format-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_invalid_usa_phone_format_percent`](../../../checks/column/patterns/invalid-usa-phone-format-percent.md)</span>|Verifies that the percentage of invalid USA phones number in a text column does not exceed the maximum accepted percentage.|*[ColumnInvalidUsaPhonePercentCheckSpec](../../../checks/column/patterns/invalid-usa-phone-format-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_invalid_usa_zipcode_format_percent`](../../../checks/column/patterns/invalid-usa-zipcode-format-percent.md)</span>|Verifies that the percentage of invalid USA phones number in a text column does not exceed the maximum accepted percentage.|*[ColumnInvalidUsaZipcodePercentCheckSpec](../../../checks/column/patterns/invalid-usa-zipcode-format-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnPiiDailyPartitionedChecksSpec
Container of PII data quality partitioned checks on a column level that are checking at a daily level.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_contains_usa_phone_percent`](../../../checks/column/pii/contains-usa-phone-percent.md)</span>|Detects USA phone numbers in text columns. Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnPiiContainsUsaPhonePercentCheckSpec](../../../checks/column/pii/contains-usa-phone-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_contains_email_percent`](../../../checks/column/pii/contains-email-percent.md)</span>|Detects emails in text columns. Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnPiiContainsEmailPercentCheckSpec](../../../checks/column/pii/contains-email-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_contains_usa_zipcode_percent`](../../../checks/column/pii/contains-usa-zipcode-percent.md)</span>|Detects USA zip codes in text columns. Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnPiiContainsUsaZipcodePercentCheckSpec](../../../checks/column/pii/contains-usa-zipcode-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_contains_ip4_percent`](../../../checks/column/pii/contains-ip4-percent.md)</span>|Detects IP4 addresses in text columns. Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnPiiContainsIp4PercentCheckSpec](../../../checks/column/pii/contains-ip4-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_contains_ip6_percent`](../../../checks/column/pii/contains-ip6-percent.md)</span>|Detects IP6 addresses in text columns. Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnPiiContainsIp6PercentCheckSpec](../../../checks/column/pii/contains-ip6-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnNumericDailyPartitionedChecksSpec
Container of numeric data quality partitioned checks on a column level that are checking at a daily level.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_number_below_min_value`](../../../checks/column/numeric/number-below-min-value.md)</span>|The check counts the number of values in the column that are below the value defined by the user as a parameter. Stores a separate data quality check result for each daily partition.|*[ColumnNumberBelowMinValueCheckSpec](../../../checks/column/numeric/number-below-min-value.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_number_above_max_value`](../../../checks/column/numeric/number-above-max-value.md)</span>|The check counts the number of values in the column that are above the value defined by the user as a parameter. Stores a separate data quality check result for each daily partition.|*[ColumnNumberAboveMaxValueCheckSpec](../../../checks/column/numeric/number-above-max-value.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_negative_values`](../../../checks/column/numeric/negative-values.md)</span>|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|*[ColumnNegativeCountCheckSpec](../../../checks/column/numeric/negative-values.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_negative_values_percent`](../../../checks/column/numeric/negative-values-percent.md)</span>|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnNegativePercentCheckSpec](../../../checks/column/numeric/negative-values-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_number_below_min_value_percent`](../../../checks/column/numeric/number-below-min-value-percent.md)</span>|The check counts the percentage of values in the column that are below the value defined by the user as a parameter. Stores a separate data quality check result for each daily partition.|*[ColumnNumberBelowMinValuePercentCheckSpec](../../../checks/column/numeric/number-below-min-value-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_number_above_max_value_percent`](../../../checks/column/numeric/number-above-max-value-percent.md)</span>|The check counts the percentage of values in the column that are above the value defined by the user as a parameter. Stores a separate data quality check result for each daily partition.|*[ColumnNumberAboveMaxValuePercentCheckSpec](../../../checks/column/numeric/number-above-max-value-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_number_in_range_percent`](../../../checks/column/numeric/number-in-range-percent.md)</span>|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnNumberInRangePercentCheckSpec](../../../checks/column/numeric/number-in-range-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_integer_in_range_percent`](../../../checks/column/numeric/integer-in-range-percent.md)</span>|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnIntegerInRangePercentCheckSpec](../../../checks/column/numeric/integer-in-range-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_min_in_range`](../../../checks/column/numeric/min-in-range.md)</span>|Verifies that the minimum value in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnMinInRangeCheckSpec](../../../checks/column/numeric/min-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_max_in_range`](../../../checks/column/numeric/max-in-range.md)</span>|Verifies that the maximum value in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnMaxInRangeCheckSpec](../../../checks/column/numeric/max-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_sum_in_range`](../../../checks/column/numeric/sum-in-range.md)</span>|Verifies that the sum of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnSumInRangeCheckSpec](../../../checks/column/numeric/sum-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_mean_in_range`](../../../checks/column/numeric/mean-in-range.md)</span>|Verifies that the average (mean) of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnMeanInRangeCheckSpec](../../../checks/column/numeric/mean-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_median_in_range`](../../../checks/column/numeric/median-in-range.md)</span>|Verifies that the median of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnMedianInRangeCheckSpec](../../../checks/column/numeric/median-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_percentile_in_range`](../../../checks/column/numeric/percentile-in-range.md)</span>|Verifies that the percentile of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnPercentileInRangeCheckSpec](../../../checks/column/numeric/percentile-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_percentile_10_in_range`](../../../checks/column/numeric/percentile-10-in-range.md)</span>|Verifies that the percentile 10 of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnPercentile10InRangeCheckSpec](../../../checks/column/numeric/percentile-10-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_percentile_25_in_range`](../../../checks/column/numeric/percentile-25-in-range.md)</span>|Verifies that the percentile 25 of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnPercentile25InRangeCheckSpec](../../../checks/column/numeric/percentile-25-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_percentile_75_in_range`](../../../checks/column/numeric/percentile-75-in-range.md)</span>|Verifies that the percentile 75 of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnPercentile75InRangeCheckSpec](../../../checks/column/numeric/percentile-75-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_percentile_90_in_range`](../../../checks/column/numeric/percentile-90-in-range.md)</span>|Verifies that the percentile 90 of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnPercentile90InRangeCheckSpec](../../../checks/column/numeric/percentile-90-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_sample_stddev_in_range`](../../../checks/column/numeric/sample-stddev-in-range.md)</span>|Verifies that the sample standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnSampleStddevInRangeCheckSpec](../../../checks/column/numeric/sample-stddev-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_population_stddev_in_range`](../../../checks/column/numeric/population-stddev-in-range.md)</span>|Verifies that the population standard deviation of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnPopulationStddevInRangeCheckSpec](../../../checks/column/numeric/population-stddev-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_sample_variance_in_range`](../../../checks/column/numeric/sample-variance-in-range.md)</span>|Verifies that the sample variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnSampleVarianceInRangeCheckSpec](../../../checks/column/numeric/sample-variance-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_population_variance_in_range`](../../../checks/column/numeric/population-variance-in-range.md)</span>|Verifies that the population variance of all values in a column is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnPopulationVarianceInRangeCheckSpec](../../../checks/column/numeric/population-variance-in-range.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_invalid_latitude`](../../../checks/column/numeric/invalid-latitude.md)</span>|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|*[ColumnInvalidLatitudeCountCheckSpec](../../../checks/column/numeric/invalid-latitude.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_valid_latitude_percent`](../../../checks/column/numeric/valid-latitude-percent.md)</span>|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnValidLatitudePercentCheckSpec](../../../checks/column/numeric/valid-latitude-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_invalid_longitude`](../../../checks/column/numeric/invalid-longitude.md)</span>|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|*[ColumnInvalidLongitudeCountCheckSpec](../../../checks/column/numeric/invalid-longitude.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_valid_longitude_percent`](../../../checks/column/numeric/valid-longitude-percent.md)</span>|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnValidLongitudePercentCheckSpec](../../../checks/column/numeric/valid-longitude-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_non_negative_values`](../../../checks/column/numeric/non-negative-values.md)</span>|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores a separate data quality check result for each daily partition.|*[ColumnNonNegativeCountCheckSpec](../../../checks/column/numeric/non-negative-values.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_non_negative_values_percent`](../../../checks/column/numeric/non-negative-values-percent.md)</span>|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores a separate data quality check result for each daily partition.|*[ColumnNonNegativePercentCheckSpec](../../../checks/column/numeric/non-negative-values-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnAnomalyDailyPartitionedChecksSpec
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_sum_anomaly`](./column-daily-partitioned-checks.md#columnsumanomalystationarypartitioncheckspec)</span>|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days. Calculates the sum of each daily partition and detect anomalies between daily partitions.|*[ColumnSumAnomalyStationaryPartitionCheckSpec](./column-daily-partitioned-checks.md#columnsumanomalystationarypartitioncheckspec)*| | | |
|<span class="no-wrap-code ">[`daily_partition_mean_anomaly`](../../../checks/column/anomaly/mean-anomaly.md)</span>|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days. Calculates the mean (average) of each daily partition and detect anomalies between daily partitions.|*[ColumnMeanAnomalyStationaryCheckSpec](../../../checks/column/anomaly/mean-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_median_anomaly`](../../../checks/column/anomaly/median-anomaly.md)</span>|Verifies that the median in a column is within a percentile from measurements made during the last 90 days. Calculates the median of each daily partition and detect anomalies between daily partitions.|*[ColumnMedianAnomalyStationaryCheckSpec](../../../checks/column/anomaly/median-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_min_anomaly`](./column-daily-partitioned-checks.md#columnminanomalystationarycheckspec)</span>|Detects new outliers, which are new minimum values, much below the last known minimum value. If the minimum value is constantly changing, detects outliers as the biggest change of the minimum value during the last 90 days. Finds the minimum value of each daily partition and detect anomalies between daily partitions.|*[ColumnMinAnomalyStationaryCheckSpec](./column-daily-partitioned-checks.md#columnminanomalystationarycheckspec)*| | | |
|<span class="no-wrap-code ">[`daily_partition_max_anomaly`](./column-daily-partitioned-checks.md#columnmaxanomalystationarycheckspec)</span>|Detects new outliers, which are new maximum values, much above the last known maximum value. If the maximum value is constantly changing, detects outliers as the biggest change of the maximum value during the last 90 days. Finds the maximum value of each daily partition and detect anomalies between daily partitions.|*[ColumnMaxAnomalyStationaryCheckSpec](./column-daily-partitioned-checks.md#columnmaxanomalystationarycheckspec)*| | | |
|<span class="no-wrap-code ">[`daily_partition_mean_change`](../../../checks/column/anomaly/mean-change.md)</span>|Verifies that the mean value in a column changed in a fixed rate since last readout.|*[ColumnMeanChangeCheckSpec](../../../checks/column/anomaly/mean-change.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_mean_change_1_day`](../../../checks/column/anomaly/mean-change-1-day.md)</span>|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.|*[ColumnMeanChange1DayCheckSpec](../../../checks/column/anomaly/mean-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_mean_change_7_days`](../../../checks/column/anomaly/mean-change-7-days.md)</span>|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.|*[ColumnMeanChange7DaysCheckSpec](../../../checks/column/anomaly/mean-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_mean_change_30_days`](../../../checks/column/anomaly/mean-change-30-days.md)</span>|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.|*[ColumnMeanChange30DaysCheckSpec](../../../checks/column/anomaly/mean-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_median_change`](../../../checks/column/anomaly/median-change.md)</span>|Verifies that the median in a column changed in a fixed rate since the last readout.|*[ColumnMedianChangeCheckSpec](../../../checks/column/anomaly/median-change.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_median_change_1_day`](../../../checks/column/anomaly/median-change-1-day.md)</span>|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|*[ColumnMedianChange1DayCheckSpec](../../../checks/column/anomaly/median-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_median_change_7_days`](../../../checks/column/anomaly/median-change-7-days.md)</span>|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|*[ColumnMedianChange7DaysCheckSpec](../../../checks/column/anomaly/median-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_median_change_30_days`](../../../checks/column/anomaly/median-change-30-days.md)</span>|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|*[ColumnMedianChange30DaysCheckSpec](../../../checks/column/anomaly/median-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_sum_change`](../../../checks/column/anomaly/sum-change.md)</span>|Verifies that the sum in a column changed in a fixed rate since the last readout.|*[ColumnSumChangeCheckSpec](../../../checks/column/anomaly/sum-change.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_sum_change_1_day`](../../../checks/column/anomaly/sum-change-1-day.md)</span>|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|*[ColumnSumChange1DayCheckSpec](../../../checks/column/anomaly/sum-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_sum_change_7_days`](../../../checks/column/anomaly/sum-change-7-days.md)</span>|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.|*[ColumnSumChange7DaysCheckSpec](../../../checks/column/anomaly/sum-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_sum_change_30_days`](../../../checks/column/anomaly/sum-change-30-days.md)</span>|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.|*[ColumnSumChange30DaysCheckSpec](../../../checks/column/anomaly/sum-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnSumAnomalyStationaryPartitionCheckSpec
This check calculates a sum of values in a numeric column and detects anomalies in a time series of previous sums.
 It raises a data quality issue when the sum is in the top *anomaly_percent* percentage of the most outstanding values in the time series.
 This data quality check uses a 90-day time window and requires a history of at least 30 days.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../../sensors/column/numeric-column-sensors.md#sum)</span>|Data quality check parameters|*[ColumnNumericSumSensorParametersSpec](../../sensors/column/numeric-column-sensors.md#sum)*| | | |
|<span class="no-wrap-code ">[`warning`](./column-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulewarning1pctparametersspec)</span>|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|*[AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec](./column-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulewarning1pctparametersspec)*| | | |
|<span class="no-wrap-code ">[`error`](../../rules/Percentile.md#anomaly-stationary-percentile-moving-average)</span>|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|*[AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec](../../rules/Percentile.md#anomaly-stationary-percentile-moving-average)*| | | |
|<span class="no-wrap-code ">[`fatal`](./column-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulefatal01pctparametersspec)</span>|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|*[AnomalyStationaryPercentileMovingAverageRuleFatal01PctParametersSpec](./column-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulefatal01pctparametersspec)*| | | |
|<span class="no-wrap-code ">[`schedule_override`](../profiling/table-profiling-checks.md#cronschedulespec)</span>|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|*[CronScheduleSpec](../profiling/table-profiling-checks.md#cronschedulespec)*| | | |
|<span class="no-wrap-code ">[`comments`](../profiling/table-profiling-checks.md#commentslistspec)</span>|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|*[CommentsListSpec](../profiling/table-profiling-checks.md#commentslistspec)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|*boolean*| | | |
|<span class="no-wrap-code ">`exclude_from_kpi`</span>|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|*boolean*| | | |
|<span class="no-wrap-code ">`include_in_sla`</span>|Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.|*boolean*| | | |
|<span class="no-wrap-code ">`quality_dimension`</span>|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|*string*| | | |
|<span class="no-wrap-code ">`display_name`</span>|Data quality check display name that can be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|*string*| | | |
|<span class="no-wrap-code ">`data_grouping`</span>|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|*string*| | | |
|<span class="no-wrap-code ">`always_collect_error_samples`</span>|Forces collecting error samples for this check whenever it fails, even if it is a monitoring check that is run by a scheduler, and running an additional query to collect error samples will impose additional load on the data source.|*boolean*| | | |
|<span class="no-wrap-code ">`do_not_schedule`</span>|Disables running this check by a DQOps CRON scheduler. When a check is disabled from scheduling, it can be only triggered from the user interface or by submitting &quot;run checks&quot; job.|*boolean*| | | |



___

## AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec
Data quality rule that detects anomalies in time series of data quality measures that are stationary over time, such as a percentage of null values.
 Stationary measures stay within a well-known range of values.
 The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 The rule uses the time window of the last 90 days, but at least 30 historical measures must be present to run the calculation.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`anomaly_percent`</span>|The probability (in percent) that the current sensor readout (measure) is an anomaly, because the value is outside the regular range of previous readouts. The default time window of 90 time periods (days, etc.) is used, but at least 30 readouts must exist to run the calculation.|*double*| | | |
|<span class="no-wrap-code ">`use_ai`</span>|Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in a trial distribution of DQOps. Please contact DQOps support to upgrade your instance to a full DQOps instance.|*boolean*| | | |



___

## AnomalyStationaryPercentileMovingAverageRuleFatal01PctParametersSpec
Data quality rule that detects anomalies in time series of data quality measures that are stationary over time, such as a percentage of null values.
 Stationary measures stay within a well-known range of values.
 The rule identifies the top X% of anomalous values, based on the distribution of the changes using a standard deviation.
 The rule uses the time window of the last 90 days, but at least 30 historical measures must be present to run the calculation.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`anomaly_percent`</span>|The probability (in percent) that the current sensor readout (measure) is an anomaly, because the value is outside the regular range of previous readouts. The default time window of 90 time periods (days, etc.) is used, but at least 30 readouts must exist to run the calculation.|*double*| | | |
|<span class="no-wrap-code ">`use_ai`</span>|Use an AI model to predict anomalies. WARNING: anomaly detection by AI models is not supported in a trial distribution of DQOps. Please contact DQOps support to upgrade your instance to a full DQOps instance.|*boolean*| | | |



___

## ColumnMinAnomalyStationaryCheckSpec
This check finds a minimum value in a numeric column and detects anomalies in a time series of previous minimum values.
 It raises a data quality issue when the current minimum value is in the top *anomaly_percent* percentage of the most outstanding
 values in the time series (it is a new minimum value, far from the previous one).
 This data quality check uses a 90-day time window and requires a history of at least 30 days.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../../sensors/column/range-column-sensors.md#min-value)</span>|Data quality check parameters|*[ColumnNumericMinSensorParametersSpec](../../sensors/column/range-column-sensors.md#min-value)*| | | |
|<span class="no-wrap-code ">[`warning`](./column-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulewarning1pctparametersspec)</span>|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|*[AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec](./column-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulewarning1pctparametersspec)*| | | |
|<span class="no-wrap-code ">[`error`](../../rules/Percentile.md#anomaly-stationary-percentile-moving-average)</span>|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|*[AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec](../../rules/Percentile.md#anomaly-stationary-percentile-moving-average)*| | | |
|<span class="no-wrap-code ">[`fatal`](./column-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulefatal01pctparametersspec)</span>|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|*[AnomalyStationaryPercentileMovingAverageRuleFatal01PctParametersSpec](./column-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulefatal01pctparametersspec)*| | | |
|<span class="no-wrap-code ">[`schedule_override`](../profiling/table-profiling-checks.md#cronschedulespec)</span>|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|*[CronScheduleSpec](../profiling/table-profiling-checks.md#cronschedulespec)*| | | |
|<span class="no-wrap-code ">[`comments`](../profiling/table-profiling-checks.md#commentslistspec)</span>|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|*[CommentsListSpec](../profiling/table-profiling-checks.md#commentslistspec)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|*boolean*| | | |
|<span class="no-wrap-code ">`exclude_from_kpi`</span>|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|*boolean*| | | |
|<span class="no-wrap-code ">`include_in_sla`</span>|Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.|*boolean*| | | |
|<span class="no-wrap-code ">`quality_dimension`</span>|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|*string*| | | |
|<span class="no-wrap-code ">`display_name`</span>|Data quality check display name that can be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|*string*| | | |
|<span class="no-wrap-code ">`data_grouping`</span>|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|*string*| | | |
|<span class="no-wrap-code ">`always_collect_error_samples`</span>|Forces collecting error samples for this check whenever it fails, even if it is a monitoring check that is run by a scheduler, and running an additional query to collect error samples will impose additional load on the data source.|*boolean*| | | |
|<span class="no-wrap-code ">`do_not_schedule`</span>|Disables running this check by a DQOps CRON scheduler. When a check is disabled from scheduling, it can be only triggered from the user interface or by submitting &quot;run checks&quot; job.|*boolean*| | | |



___

## ColumnMaxAnomalyStationaryCheckSpec
This check finds a maximum value in a numeric column and detects anomalies in a time series of previous maximum values.
 It raises a data quality issue when the current maximum value is in the top *anomaly_percent* percentage of the most outstanding
 values in the time series (it is a new maximum value, far from the previous one).
 This data quality check uses a 90-day time window and requires a history of at least 30 days.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../../sensors/column/range-column-sensors.md#max-value)</span>|Data quality check parameters|*[ColumnNumericMaxSensorParametersSpec](../../sensors/column/range-column-sensors.md#max-value)*| | | |
|<span class="no-wrap-code ">[`warning`](./column-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulewarning1pctparametersspec)</span>|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|*[AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec](./column-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulewarning1pctparametersspec)*| | | |
|<span class="no-wrap-code ">[`error`](../../rules/Percentile.md#anomaly-stationary-percentile-moving-average)</span>|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|*[AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec](../../rules/Percentile.md#anomaly-stationary-percentile-moving-average)*| | | |
|<span class="no-wrap-code ">[`fatal`](./column-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulefatal01pctparametersspec)</span>|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|*[AnomalyStationaryPercentileMovingAverageRuleFatal01PctParametersSpec](./column-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulefatal01pctparametersspec)*| | | |
|<span class="no-wrap-code ">[`schedule_override`](../profiling/table-profiling-checks.md#cronschedulespec)</span>|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|*[CronScheduleSpec](../profiling/table-profiling-checks.md#cronschedulespec)*| | | |
|<span class="no-wrap-code ">[`comments`](../profiling/table-profiling-checks.md#commentslistspec)</span>|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|*[CommentsListSpec](../profiling/table-profiling-checks.md#commentslistspec)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|*boolean*| | | |
|<span class="no-wrap-code ">`exclude_from_kpi`</span>|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|*boolean*| | | |
|<span class="no-wrap-code ">`include_in_sla`</span>|Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.|*boolean*| | | |
|<span class="no-wrap-code ">`quality_dimension`</span>|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|*string*| | | |
|<span class="no-wrap-code ">`display_name`</span>|Data quality check display name that can be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|*string*| | | |
|<span class="no-wrap-code ">`data_grouping`</span>|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|*string*| | | |
|<span class="no-wrap-code ">`always_collect_error_samples`</span>|Forces collecting error samples for this check whenever it fails, even if it is a monitoring check that is run by a scheduler, and running an additional query to collect error samples will impose additional load on the data source.|*boolean*| | | |
|<span class="no-wrap-code ">`do_not_schedule`</span>|Disables running this check by a DQOps CRON scheduler. When a check is disabled from scheduling, it can be only triggered from the user interface or by submitting &quot;run checks&quot; job.|*boolean*| | | |



___

## ColumnDatetimeDailyPartitionedChecksSpec
Container of date-time data quality partitioned checks on a column level that are checking at a daily level.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_date_values_in_future_percent`](../../../checks/column/datetime/date-values-in-future-percent.md)</span>|Detects dates in the future in date, datetime and timestamp columns. Measures a percentage of dates in the future. Raises a data quality issue when too many future dates are found. Stores a separate data quality check result for each daily partition.|*[ColumnDateValuesInFuturePercentCheckSpec](../../../checks/column/datetime/date-values-in-future-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_date_in_range_percent`](../../../checks/column/datetime/date-in-range-percent.md)</span>|Verifies that the dates in date, datetime, or timestamp columns are within a reasonable range of dates. The default configuration detects fake dates such as 1900-01-01 and 2099-12-31. Measures the percentage of valid dates and raises a data quality issue when too many dates are found. Stores a separate data quality check result for each daily partition.|*[ColumnDateInRangePercentCheckSpec](../../../checks/column/datetime/date-in-range-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_text_match_date_format_percent`](../../../checks/column/datetime/text-match-date-format-percent.md)</span>|Verifies that the values in text columns match one of the predefined date formats, such as an ISO 8601 date. Measures the percentage of valid date strings and raises a data quality issue when too many invalid date strings are found. Stores a separate data quality check result for each daily partition.|*[ColumnTextMatchDateFormatPercentCheckSpec](../../../checks/column/datetime/text-match-date-format-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnBoolDailyPartitionedChecksSpec
Container of boolean data quality partitioned checks on a column level that are checking at a daily level.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_true_percent`](../../../checks/column/bool/true-percent.md)</span>|Measures the percentage of **true** values in a boolean column and verifies that it is within the accepted range. Stores a separate data quality check result for each daily partition.|*[ColumnTruePercentCheckSpec](../../../checks/column/bool/true-percent.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_false_percent`](../../../checks/column/bool/false-percent.md)</span>|Measures the percentage of **false** values in a boolean column and verifies that it is within the accepted range. Stores a separate data quality check result for each daily partition.|*[ColumnFalsePercentCheckSpec](../../../checks/column/bool/false-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnIntegrityDailyPartitionedChecksSpec
Container of integrity data quality partitioned checks on a column level that are checking at a daily level.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_lookup_key_not_found`](../../../checks/column/integrity/lookup-key-not-found.md)</span>|Detects invalid values that are not present in a dictionary table using an outer join query. Counts the number of invalid keys. Stores a separate data quality check result for each daily partition.|*[ColumnIntegrityLookupKeyNotFoundCountCheckSpec](../../../checks/column/integrity/lookup-key-not-found.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_lookup_key_found_percent`](../../../checks/column/integrity/lookup-key-found-percent.md)</span>|Measures the percentage of valid values that are present in a dictionary table. Joins this table to a dictionary table using an outer join. Stores a separate data quality check result for each daily partition.|*[ColumnIntegrityForeignKeyMatchPercentCheckSpec](../../../checks/column/integrity/lookup-key-found-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnCustomSqlDailyPartitionedChecksSpec
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_sql_condition_failed_on_column`](../../../checks/column/custom_sql/sql-condition-failed-on-column.md)</span>|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between the current column and another column: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|*[ColumnSqlConditionFailedCheckSpec](../../../checks/column/custom_sql/sql-condition-failed-on-column.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_sql_condition_passed_percent_on_column`](../../../checks/column/custom_sql/sql-condition-passed-percent-on-column.md)</span>|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current column by using tokens, for example: &#x60;{alias}.{column} &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|*[ColumnSqlConditionPassedPercentCheckSpec](../../../checks/column/custom_sql/sql-condition-passed-percent-on-column.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_sql_aggregate_expression_on_column`](../../../checks/column/custom_sql/sql-aggregate-expression-on-column.md)</span>|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[ColumnSqlAggregateExpressionCheckSpec](../../../checks/column/custom_sql/sql-aggregate-expression-on-column.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_import_custom_result_on_column`](../../../checks/column/custom_sql/import-custom-result-on-column.md)</span>|Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.|*[ColumnSqlImportCustomResultCheckSpec](../../../checks/column/custom_sql/import-custom-result-on-column.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnDatatypeDailyPartitionedChecksSpec
Container of datatype data quality partitioned checks on a column level that are checking at a daily level.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_detected_datatype_in_text`](../../../checks/column/datatype/detected-datatype-in-text.md)</span>|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores a separate data quality check result for each daily partition.|*[ColumnDetectedDatatypeInTextCheckSpec](../../../checks/column/datatype/detected-datatype-in-text.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_detected_datatype_in_text_changed`](../../../checks/column/datatype/detected-datatype-in-text-changed.md)</span>|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - datetimes, 5 - timestamps, 6 - booleans, 7 - strings, 8 - mixed data types. Stores a separate data quality check result for each daily partition.|*[ColumnDatatypeDetectedDatatypeInTextChangedCheckSpec](../../../checks/column/datatype/detected-datatype-in-text-changed.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

## ColumnComparisonDailyPartitionedChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`self`</span>||*Dict[string, [ColumnComparisonDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#columncomparisondailypartitionedchecksspec)]*| | | |



___

## ColumnComparisonDailyPartitionedChecksSpec
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 This is the configuration for daily partitioned checks that are counted in KPIs.


The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_sum_match`](../../../checks/column/comparisons/sum-match.md)</span>|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|*[ColumnComparisonSumMatchCheckSpec](../../../checks/column/comparisons/sum-match.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_min_match`](../../../checks/column/comparisons/min-match.md)</span>|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|*[ColumnComparisonMinMatchCheckSpec](../../../checks/column/comparisons/min-match.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_max_match`](../../../checks/column/comparisons/max-match.md)</span>|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|*[ColumnComparisonMaxMatchCheckSpec](../../../checks/column/comparisons/max-match.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_mean_match`](../../../checks/column/comparisons/mean-match.md)</span>|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|*[ColumnComparisonMeanMatchCheckSpec](../../../checks/column/comparisons/mean-match.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_not_null_count_match`](../../../checks/column/comparisons/not-null-count-match.md)</span>|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|*[ColumnComparisonNotNullCountMatchCheckSpec](../../../checks/column/comparisons/not-null-count-match.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_null_count_match`](../../../checks/column/comparisons/null-count-match.md)</span>|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|*[ColumnComparisonNullCountMatchCheckSpec](../../../checks/column/comparisons/null-count-match.md)*| | | |
|<span class="no-wrap-code ">`reference_column`</span>|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|*string*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |



___

