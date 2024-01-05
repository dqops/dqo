
## ColumnComparisonDailyMonitoringChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [ColumnComparisonDailyMonitoringChecksSpec](#ColumnComparisonDailyMonitoringChecksSpec)]| | | |









___

## ColumnUniquenessDailyMonitoringChecksSpec
Container of uniqueness data quality monitoring checks on a column level that are checking at a daily level.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_distinct_count](../../../../checks/column/uniqueness/distinct-count.md)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDistinctCountCheckSpec](../../../../checks/column/uniqueness/distinct-count.md)| | | |
|[daily_distinct_percent](../../../../checks/column/uniqueness/distinct-percent.md)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDistinctPercentCheckSpec](../../../../checks/column/uniqueness/distinct-percent.md)| | | |
|[daily_duplicate_count](../../../../checks/column/uniqueness/duplicate-count.md)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDuplicateCountCheckSpec](../../../../checks/column/uniqueness/duplicate-count.md)| | | |
|[daily_duplicate_percent](../../../../checks/column/uniqueness/duplicate-percent.md)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDuplicatePercentCheckSpec](../../../../checks/column/uniqueness/duplicate-percent.md)| | | |
|[daily_anomaly_differencing_distinct_count_30_days](../../../../checks/column/uniqueness/anomaly-differencing-distinct-count-30-days.md)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyDifferencingDistinctCount30DaysCheckSpec](../../../../checks/column/uniqueness/anomaly-differencing-distinct-count-30-days.md)| | | |
|[daily_anomaly_differencing_distinct_count](../../../../checks/column/uniqueness/anomaly-differencing-distinct-count.md)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyDifferencingDistinctCountCheckSpec](../../../../checks/column/uniqueness/anomaly-differencing-distinct-count.md)| | | |
|[daily_anomaly_stationary_distinct_percent_30_days](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent-30-days.md)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 30 days.|[ColumnAnomalyStationaryDistinctPercent30DaysCheckSpec](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent-30-days.md)| | | |
|[daily_anomaly_stationary_distinct_percent](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent.md)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnAnomalyStationaryDistinctPercentCheckSpec](../../../../checks/column/uniqueness/anomaly-stationary-distinct-percent.md)| | | |
|[daily_change_distinct_count](../../../../checks/column/uniqueness/change-distinct-count.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctCountCheckSpec](../../../../checks/column/uniqueness/change-distinct-count.md)| | | |
|[daily_change_distinct_count_since_7_days](../../../../checks/column/uniqueness/change-distinct-count-since-7-days.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctCountSince7DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-7-days.md)| | | |
|[daily_change_distinct_count_since_30_days](../../../../checks/column/uniqueness/change-distinct-count-since-30-days.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctCountSince30DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-30-days.md)| | | |
|[daily_change_distinct_count_since_yesterday](../../../../checks/column/uniqueness/change-distinct-count-since-yesterday.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctCountSinceYesterdayCheckSpec](../../../../checks/column/uniqueness/change-distinct-count-since-yesterday.md)| | | |
|[daily_change_distinct_percent](../../../../checks/column/uniqueness/change-distinct-percent.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|[ColumnChangeDistinctPercentCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent.md)| | | |
|[daily_change_distinct_percent_since_7_days](../../../../checks/column/uniqueness/change-distinct-percent-since-7-days.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last week.|[ColumnChangeDistinctPercentSince7DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-7-days.md)| | | |
|[daily_change_distinct_percent_since_30_days](../../../../checks/column/uniqueness/change-distinct-percent-since-30-days.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from last month.|[ColumnChangeDistinctPercentSince30DaysCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-30-days.md)| | | |
|[daily_change_distinct_percent_since_yesterday](../../../../checks/column/uniqueness/change-distinct-percent-since-yesterday.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnChangeDistinctPercentSinceYesterdayCheckSpec](../../../../checks/column/uniqueness/change-distinct-percent-since-yesterday.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## ColumnDailyMonitoringCheckCategoriesSpec
Container of column level daily monitoring checks. Contains categories of daily monitoring checks.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](#ColumnNullsDailyMonitoringChecksSpec)|Daily monitoring checks of nulls in the column|[ColumnNullsDailyMonitoringChecksSpec](#ColumnNullsDailyMonitoringChecksSpec)| | | |
|[numeric](#ColumnNumericDailyMonitoringChecksSpec)|Daily monitoring checks of numeric in the column|[ColumnNumericDailyMonitoringChecksSpec](#ColumnNumericDailyMonitoringChecksSpec)| | | |
|[strings](#ColumnStringsDailyMonitoringChecksSpec)|Daily monitoring checks of strings in the column|[ColumnStringsDailyMonitoringChecksSpec](#ColumnStringsDailyMonitoringChecksSpec)| | | |
|[uniqueness](#ColumnUniquenessDailyMonitoringChecksSpec)|Daily monitoring checks of uniqueness in the column|[ColumnUniquenessDailyMonitoringChecksSpec](#ColumnUniquenessDailyMonitoringChecksSpec)| | | |
|[datetime](#ColumnDatetimeDailyMonitoringChecksSpec)|Daily monitoring checks of datetime in the column|[ColumnDatetimeDailyMonitoringChecksSpec](#ColumnDatetimeDailyMonitoringChecksSpec)| | | |
|[pii](#ColumnPiiDailyMonitoringChecksSpec)|Daily monitoring checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyMonitoringChecksSpec](#ColumnPiiDailyMonitoringChecksSpec)| | | |
|[sql](#ColumnSqlDailyMonitoringChecksSpec)|Daily monitoring checks of custom SQL checks in the column|[ColumnSqlDailyMonitoringChecksSpec](#ColumnSqlDailyMonitoringChecksSpec)| | | |
|[bool](#ColumnBoolDailyMonitoringChecksSpec)|Daily monitoring checks of booleans in the column|[ColumnBoolDailyMonitoringChecksSpec](#ColumnBoolDailyMonitoringChecksSpec)| | | |
|[integrity](#ColumnIntegrityDailyMonitoringChecksSpec)|Daily monitoring checks of integrity in the column|[ColumnIntegrityDailyMonitoringChecksSpec](#ColumnIntegrityDailyMonitoringChecksSpec)| | | |
|[accuracy](#ColumnAccuracyDailyMonitoringChecksSpec)|Daily monitoring checks of accuracy in the column|[ColumnAccuracyDailyMonitoringChecksSpec](#ColumnAccuracyDailyMonitoringChecksSpec)| | | |
|[datatype](#ColumnDatatypeDailyMonitoringChecksSpec)|Daily monitoring checks of datatype in the column|[ColumnDatatypeDailyMonitoringChecksSpec](#ColumnDatatypeDailyMonitoringChecksSpec)| | | |
|[anomaly](#ColumnAnomalyDailyMonitoringChecksSpec)|Daily monitoring checks of anomaly in the column|[ColumnAnomalyDailyMonitoringChecksSpec](#ColumnAnomalyDailyMonitoringChecksSpec)| | | |
|[schema](#ColumnSchemaDailyMonitoringChecksSpec)|Daily monitoring column schema checks|[ColumnSchemaDailyMonitoringChecksSpec](#ColumnSchemaDailyMonitoringChecksSpec)| | | |
|[comparisons](#ColumnComparisonDailyMonitoringChecksSpecMap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonDailyMonitoringChecksSpecMap](#ColumnComparisonDailyMonitoringChecksSpecMap)| | | |
|[custom](../../profiling/table-profiling-checks.md#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCheckSpecMap)| | | |









___

## ColumnDatetimeDailyMonitoringChecksSpec
Container of date-time data quality monitoring checks on a column level that are checking at a daily level.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_date_match_format_percent](../../../../checks/column/datetime/date-match-format-percent.md)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily monitoring.|[ColumnDatetimeDateMatchFormatPercentCheckSpec](../../../../checks/column/datetime/date-match-format-percent.md)| | | |
|[daily_date_values_in_future_percent](../../../../checks/column/datetime/date-values-in-future-percent.md)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDateValuesInFuturePercentCheckSpec](../../../../checks/column/datetime/date-values-in-future-percent.md)| | | |
|[daily_datetime_value_in_range_date_percent](../../../../checks/column/datetime/datetime-value-in-range-date-percent.md)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](../../../../checks/column/datetime/datetime-value-in-range-date-percent.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## ColumnNullsDailyMonitoringChecksSpec
Container of nulls data quality monitoring checks on a column level that are checking at a daily level.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_nulls_count](../../../../checks/column/nulls/nulls-count.md)|Detects columns with any null values when the max_count&#x3D;0. Verifies that the number of null values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNullsCountCheckSpec](../../../../checks/column/nulls/nulls-count.md)| | | |
|[daily_nulls_percent](../../../../checks/column/nulls/nulls-percent.md)|Verifies that the percentage of nulls in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNullsPercentCheckSpec](../../../../checks/column/nulls/nulls-percent.md)| | | |
|[daily_nulls_percent_anomaly_stationary_30_days](../../../../checks/column/nulls/nulls-percent-anomaly-stationary-30-days.md)|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyStationaryNullPercent30DaysCheckSpec](../../../../checks/column/nulls/nulls-percent-anomaly-stationary-30-days.md)| | | |
|[daily_nulls_percent_anomaly_stationary](../../../../checks/column/nulls/nulls-percent-anomaly-stationary.md)|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnAnomalyStationaryNullPercentCheckSpec](../../../../checks/column/nulls/nulls-percent-anomaly-stationary.md)| | | |
|[daily_nulls_percent_change](../../../../checks/column/nulls/nulls-percent-change.md)|Verifies that the null percent value in a column changed in a fixed rate since last readout.|[ColumnChangeNullPercentCheckSpec](../../../../checks/column/nulls/nulls-percent-change.md)| | | |
|[daily_nulls_percent_change_yesterday](../../../../checks/column/nulls/nulls-percent-change-yesterday.md)|Verifies that the null percent value in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeNullPercentSinceYesterdayCheckSpec](../../../../checks/column/nulls/nulls-percent-change-yesterday.md)| | | |
|[daily_nulls_percent_change_7_days](../../../../checks/column/nulls/nulls-percent-change-7-days.md)|Verifies that the null percent value in a column changed in a fixed rate since last readout from last week.|[ColumnChangeNullPercentSince7DaysCheckSpec](../../../../checks/column/nulls/nulls-percent-change-7-days.md)| | | |
|[daily_nulls_percent_change_30_days](../../../../checks/column/nulls/nulls-percent-change-30-days.md)|Verifies that the null percent value in a column changed in a fixed rate since last readout from last month.|[ColumnChangeNullPercentSince30DaysCheckSpec](../../../../checks/column/nulls/nulls-percent-change-30-days.md)| | | |
|[daily_not_nulls_count](../../../../checks/column/nulls/not-nulls-count.md)|Detects empty columns with the min_count&#x3D;0 rule. Verifies that the number of not null values in a column does not fall below the minimum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNotNullsCountCheckSpec](../../../../checks/column/nulls/not-nulls-count.md)| | | |
|[daily_not_nulls_percent](../../../../checks/column/nulls/not-nulls-percent.md)|Verifies that the percentage of not nulls in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNotNullsPercentCheckSpec](../../../../checks/column/nulls/not-nulls-percent.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## ColumnSchemaDailyMonitoringChecksSpec
Container of built-in preconfigured data quality checks on a column level that are checking the column schema at a daily level.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_column_exists](../../../../checks/column/schema/column-exists.md)|Checks the metadata of the monitored table and verifies if the column exists. Stores the most recent value for each day when the data quality check was evaluated.|[ColumnSchemaColumnExistsCheckSpec](../../../../checks/column/schema/column-exists.md)| | | |
|[daily_column_type_changed](../../../../checks/column/schema/column-type-changed.md)|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed since the last day. Stores the most recent hash for each day when the data quality check was evaluated.|[ColumnSchemaTypeChangedCheckSpec](../../../../checks/column/schema/column-type-changed.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## ColumnComparisonDailyMonitoringChecksSpec
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 This is the configuration for daily monitoring checks that are counted in KPIs.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_sum_match](../../../../checks/column/comparisons/sum-match.md)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnComparisonSumMatchCheckSpec](../../../../checks/column/comparisons/sum-match.md)| | | |
|[daily_min_match](../../../../checks/column/comparisons/min-match.md)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnComparisonMinMatchCheckSpec](../../../../checks/column/comparisons/min-match.md)| | | |
|[daily_max_match](../../../../checks/column/comparisons/max-match.md)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnComparisonMaxMatchCheckSpec](../../../../checks/column/comparisons/max-match.md)| | | |
|[daily_mean_match](../../../../checks/column/comparisons/mean-match.md)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnComparisonMeanMatchCheckSpec](../../../../checks/column/comparisons/mean-match.md)| | | |
|[daily_not_null_count_match](../../../../checks/column/comparisons/not-null-count-match.md)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnComparisonNotNullCountMatchCheckSpec](../../../../checks/column/comparisons/not-null-count-match.md)| | | |
|[daily_null_count_match](../../../../checks/column/comparisons/null-count-match.md)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnComparisonNullCountMatchCheckSpec](../../../../checks/column/comparisons/null-count-match.md)| | | |
|reference_column|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|string| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## ColumnPiiDailyMonitoringChecksSpec
Container of PII data quality monitoring checks on a column level that are checking at a daily level.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_contains_usa_phone_percent](../../../../checks/column/pii/contains-usa-phone-percent.md)|Verifies that the percentage of rows that contains a USA phone number in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsUsaPhonePercentCheckSpec](../../../../checks/column/pii/contains-usa-phone-percent.md)| | | |
|[daily_contains_usa_zipcode_percent](../../../../checks/column/pii/contains-usa-zipcode-percent.md)|Verifies that the percentage of rows that contains a USA zip code in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](../../../../checks/column/pii/contains-usa-zipcode-percent.md)| | | |
|[daily_contains_email_percent](../../../../checks/column/pii/contains-email-percent.md)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsEmailPercentCheckSpec](../../../../checks/column/pii/contains-email-percent.md)| | | |
|[daily_contains_ip4_percent](../../../../checks/column/pii/contains-ip4-percent.md)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsIp4PercentCheckSpec](../../../../checks/column/pii/contains-ip4-percent.md)| | | |
|[daily_contains_ip6_percent](../../../../checks/column/pii/contains-ip6-percent.md)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPiiContainsIp6PercentCheckSpec](../../../../checks/column/pii/contains-ip6-percent.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## ColumnBoolDailyMonitoringChecksSpec
Container of boolean data quality monitoring checks on a column level that are checking at a daily level.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_true_percent](../../../../checks/column/bool/true-percent.md)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnTruePercentCheckSpec](../../../../checks/column/bool/true-percent.md)| | | |
|[daily_false_percent](../../../../checks/column/bool/false-percent.md)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnFalsePercentCheckSpec](../../../../checks/column/bool/false-percent.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## ColumnDatatypeDailyMonitoringChecksSpec
Container of datatype data quality monitoring checks on a column level that are checking at a daily level.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_string_datatype_detected](../../../../checks/column/datatype/string-datatype-detected.md)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDatatypeStringDatatypeDetectedCheckSpec](../../../../checks/column/datatype/string-datatype-detected.md)| | | |
|[daily_string_datatype_changed](../../../../checks/column/datatype/string-datatype-changed.md)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnDatatypeStringDatatypeChangedCheckSpec](../../../../checks/column/datatype/string-datatype-changed.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## ColumnSqlDailyMonitoringChecksSpec
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_sql_condition_passed_percent_on_column](../../../../checks/column/sql/sql-condition-passed-percent-on-column.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSqlConditionPassedPercentCheckSpec](../../../../checks/column/sql/sql-condition-passed-percent-on-column.md)| | | |
|[daily_sql_condition_failed_count_on_column](../../../../checks/column/sql/sql-condition-failed-count-on-column.md)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSqlConditionFailedCountCheckSpec](../../../../checks/column/sql/sql-condition-failed-count-on-column.md)| | | |
|[daily_sql_aggregate_expr_column](../../../../checks/column/sql/sql-aggregate-expr-column.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSqlAggregateExprCheckSpec](../../../../checks/column/sql/sql-aggregate-expr-column.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## ColumnAccuracyDailyMonitoringChecksSpec
Container of accuracy data quality monitoring checks on a column level that are checking at a daily level.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_total_sum_match_percent](../../../../checks/column/accuracy/total-sum-match-percent.md)|Verifies that the percentage of difference in total sum of a column in a table and total sum of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyTotalSumMatchPercentCheckSpec](../../../../checks/column/accuracy/total-sum-match-percent.md)| | | |
|[daily_total_min_match_percent](../../../../checks/column/accuracy/total-min-match-percent.md)|Verifies that the percentage of difference in total min of a column in a table and total min of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyTotalMinMatchPercentCheckSpec](../../../../checks/column/accuracy/total-min-match-percent.md)| | | |
|[daily_total_max_match_percent](../../../../checks/column/accuracy/total-max-match-percent.md)|Verifies that the percentage of difference in total max of a column in a table and total max of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyTotalMaxMatchPercentCheckSpec](../../../../checks/column/accuracy/total-max-match-percent.md)| | | |
|[daily_total_average_match_percent](../../../../checks/column/accuracy/total-average-match-percent.md)|Verifies that the percentage of difference in total average of a column in a table and total average of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyTotalAverageMatchPercentCheckSpec](../../../../checks/column/accuracy/total-average-match-percent.md)| | | |
|[daily_total_not_null_count_match_percent](../../../../checks/column/accuracy/total-not-null-count-match-percent.md)|Verifies that the percentage of difference in total not null count of a column in a table and total not null count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyTotalNotNullCountMatchPercentCheckSpec](../../../../checks/column/accuracy/total-not-null-count-match-percent.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## ColumnNumericDailyMonitoringChecksSpec
Container of built-in preconfigured data quality monitoring on a column level that are checking numeric values at a daily level.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_negative_count](../../../../checks/column/numeric/negative-count.md)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNegativeCountCheckSpec](../../../../checks/column/numeric/negative-count.md)| | | |
|[daily_negative_percent](../../../../checks/column/numeric/negative-percent.md)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNegativePercentCheckSpec](../../../../checks/column/numeric/negative-percent.md)| | | |
|[daily_non_negative_count](../../../../checks/column/numeric/non-negative-count.md)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNonNegativeCountCheckSpec](../../../../checks/column/numeric/non-negative-count.md)| | | |
|[daily_non_negative_percent](../../../../checks/column/numeric/non-negative-percent.md)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNonNegativePercentCheckSpec](../../../../checks/column/numeric/non-negative-percent.md)| | | |
|[daily_expected_numbers_in_use_count](../../../../checks/column/numeric/expected-numbers-in-use-count.md)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnExpectedNumbersInUseCountCheckSpec](../../../../checks/column/numeric/expected-numbers-in-use-count.md)| | | |
|[daily_number_value_in_set_percent](../../../../checks/column/numeric/number-value-in-set-percent.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnNumberValueInSetPercentCheckSpec](../../../../checks/column/numeric/number-value-in-set-percent.md)| | | |
|[daily_values_in_range_numeric_percent](../../../../checks/column/numeric/values-in-range-numeric-percent.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValuesInRangeNumericPercentCheckSpec](../../../../checks/column/numeric/values-in-range-numeric-percent.md)| | | |
|[daily_values_in_range_integers_percent](../../../../checks/column/numeric/values-in-range-integers-percent.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValuesInRangeIntegersPercentCheckSpec](../../../../checks/column/numeric/values-in-range-integers-percent.md)| | | |
|[daily_value_below_min_value_count](../../../../checks/column/numeric/value-below-min-value-count.md)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValueBelowMinValueCountCheckSpec](../../../../checks/column/numeric/value-below-min-value-count.md)| | | |
|[daily_value_below_min_value_percent](../../../../checks/column/numeric/value-below-min-value-percent.md)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValueBelowMinValuePercentCheckSpec](../../../../checks/column/numeric/value-below-min-value-percent.md)| | | |
|[daily_value_above_max_value_count](../../../../checks/column/numeric/value-above-max-value-count.md)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValueAboveMaxValueCountCheckSpec](../../../../checks/column/numeric/value-above-max-value-count.md)| | | |
|[daily_value_above_max_value_percent](../../../../checks/column/numeric/value-above-max-value-percent.md)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValueAboveMaxValuePercentCheckSpec](../../../../checks/column/numeric/value-above-max-value-percent.md)| | | |
|[daily_max_in_range](../../../../checks/column/numeric/max-in-range.md)|Verifies that the maximal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnMaxInRangeCheckSpec](../../../../checks/column/numeric/max-in-range.md)| | | |
|[daily_min_in_range](../../../../checks/column/numeric/min-in-range.md)|Verifies that the minimal value in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnMinInRangeCheckSpec](../../../../checks/column/numeric/min-in-range.md)| | | |
|[daily_mean_in_range](../../../../checks/column/numeric/mean-in-range.md)|Verifies that the average (mean) of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnMeanInRangeCheckSpec](../../../../checks/column/numeric/mean-in-range.md)| | | |
|[daily_percentile_in_range](../../../../checks/column/numeric/percentile-in-range.md)|Verifies that the percentile of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentileInRangeCheckSpec](../../../../checks/column/numeric/percentile-in-range.md)| | | |
|[daily_median_in_range](../../../../checks/column/numeric/median-in-range.md)|Verifies that the median of all values in a column is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|[ColumnMedianInRangeCheckSpec](../../../../checks/column/numeric/median-in-range.md)| | | |
|[daily_percentile_10_in_range](../../../../checks/column/numeric/percentile-10-in-range.md)|Verifies that the percentile 10 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentile10InRangeCheckSpec](../../../../checks/column/numeric/percentile-10-in-range.md)| | | |
|[daily_percentile_25_in_range](../../../../checks/column/numeric/percentile-25-in-range.md)|Verifies that the percentile 25 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentile25InRangeCheckSpec](../../../../checks/column/numeric/percentile-25-in-range.md)| | | |
|[daily_percentile_75_in_range](../../../../checks/column/numeric/percentile-75-in-range.md)|Verifies that the percentile 75 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentile75InRangeCheckSpec](../../../../checks/column/numeric/percentile-75-in-range.md)| | | |
|[daily_percentile_90_in_range](../../../../checks/column/numeric/percentile-90-in-range.md)|Verifies that the percentile 90 of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPercentile90InRangeCheckSpec](../../../../checks/column/numeric/percentile-90-in-range.md)| | | |
|[daily_sample_stddev_in_range](../../../../checks/column/numeric/sample-stddev-in-range.md)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSampleStddevInRangeCheckSpec](../../../../checks/column/numeric/sample-stddev-in-range.md)| | | |
|[daily_population_stddev_in_range](../../../../checks/column/numeric/population-stddev-in-range.md)|Verifies that the population standard deviation of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPopulationStddevInRangeCheckSpec](../../../../checks/column/numeric/population-stddev-in-range.md)| | | |
|[daily_sample_variance_in_range](../../../../checks/column/numeric/sample-variance-in-range.md)|Verifies that the sample variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSampleVarianceInRangeCheckSpec](../../../../checks/column/numeric/sample-variance-in-range.md)| | | |
|[daily_population_variance_in_range](../../../../checks/column/numeric/population-variance-in-range.md)|Verifies that the population variance of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnPopulationVarianceInRangeCheckSpec](../../../../checks/column/numeric/population-variance-in-range.md)| | | |
|[daily_sum_in_range](../../../../checks/column/numeric/sum-in-range.md)|Verifies that the sum of all values in a column is not outside the set range. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnSumInRangeCheckSpec](../../../../checks/column/numeric/sum-in-range.md)| | | |
|[daily_invalid_latitude_count](../../../../checks/column/numeric/invalid-latitude-count.md)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnInvalidLatitudeCountCheckSpec](../../../../checks/column/numeric/invalid-latitude-count.md)| | | |
|[daily_valid_latitude_percent](../../../../checks/column/numeric/valid-latitude-percent.md)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValidLatitudePercentCheckSpec](../../../../checks/column/numeric/valid-latitude-percent.md)| | | |
|[daily_invalid_longitude_count](../../../../checks/column/numeric/invalid-longitude-count.md)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnInvalidLongitudeCountCheckSpec](../../../../checks/column/numeric/invalid-longitude-count.md)| | | |
|[daily_valid_longitude_percent](../../../../checks/column/numeric/valid-longitude-percent.md)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnValidLongitudePercentCheckSpec](../../../../checks/column/numeric/valid-longitude-percent.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## ColumnAnomalyDailyMonitoringChecksSpec
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_mean_anomaly_stationary_30_days](../../../../checks/column/anomaly/mean-anomaly-stationary-30-days.md)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyStationaryMean30DaysCheckSpec](../../../../checks/column/anomaly/mean-anomaly-stationary-30-days.md)| | | |
|[daily_mean_anomaly_stationary](../../../../checks/column/anomaly/mean-anomaly-stationary.md)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnAnomalyStationaryMeanCheckSpec](../../../../checks/column/anomaly/mean-anomaly-stationary.md)| | | |
|[daily_median_anomaly_stationary_30_days](../../../../checks/column/anomaly/median-anomaly-stationary-30-days.md)|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyStationaryMedian30DaysCheckSpec](../../../../checks/column/anomaly/median-anomaly-stationary-30-days.md)| | | |
|[daily_median_anomaly_stationary](../../../../checks/column/anomaly/median-anomaly-stationary.md)|Verifies that the median in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnAnomalyStationaryMedianCheckSpec](../../../../checks/column/anomaly/median-anomaly-stationary.md)| | | |
|[daily_sum_anomaly_differencing_30_days](../../../../checks/column/anomaly/sum-anomaly-differencing-30-days.md)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyDifferencingSum30DaysCheckSpec](../../../../checks/column/anomaly/sum-anomaly-differencing-30-days.md)| | | |
|[daily_sum_anomaly_differencing](../../../../checks/column/anomaly/sum-anomaly-differencing.md)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnAnomalyDifferencingSumCheckSpec](../../../../checks/column/anomaly/sum-anomaly-differencing.md)| | | |
|[daily_mean_change](../../../../checks/column/anomaly/mean-change.md)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](../../../../checks/column/anomaly/mean-change.md)| | | |
|[daily_mean_change_yesterday](../../../../checks/column/anomaly/mean-change-yesterday.md)|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMeanSinceYesterdayCheckSpec](../../../../checks/column/anomaly/mean-change-yesterday.md)| | | |
|[daily_mean_change_7_days](../../../../checks/column/anomaly/mean-change-7-days.md)|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMeanSince7DaysCheckSpec](../../../../checks/column/anomaly/mean-change-7-days.md)| | | |
|[daily_mean_change_30_days](../../../../checks/column/anomaly/mean-change-30-days.md)|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMeanSince30DaysCheckSpec](../../../../checks/column/anomaly/mean-change-30-days.md)| | | |
|[daily_median_change](../../../../checks/column/anomaly/median-change.md)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](../../../../checks/column/anomaly/median-change.md)| | | |
|[daily_median_change_yesterday](../../../../checks/column/anomaly/median-change-yesterday.md)|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMedianSinceYesterdayCheckSpec](../../../../checks/column/anomaly/median-change-yesterday.md)| | | |
|[daily_median_change_7_days](../../../../checks/column/anomaly/median-change-7-days.md)|Verifies that the median in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMedianSince7DaysCheckSpec](../../../../checks/column/anomaly/median-change-7-days.md)| | | |
|[daily_median_change_30_days](../../../../checks/column/anomaly/median-change-30-days.md)|Verifies that the median in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMedianSince30DaysCheckSpec](../../../../checks/column/anomaly/median-change-30-days.md)| | | |
|[daily_sum_change](../../../../checks/column/anomaly/sum-change.md)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](../../../../checks/column/anomaly/sum-change.md)| | | |
|[daily_sum_change_yesterday](../../../../checks/column/anomaly/sum-change-yesterday.md)|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeSumSinceYesterdayCheckSpec](../../../../checks/column/anomaly/sum-change-yesterday.md)| | | |
|[daily_sum_change_7_days](../../../../checks/column/anomaly/sum-change-7-days.md)|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|[ColumnChangeSumSince7DaysCheckSpec](../../../../checks/column/anomaly/sum-change-7-days.md)| | | |
|[daily_sum_change_30_days](../../../../checks/column/anomaly/sum-change-30-days.md)|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|[ColumnChangeSumSince30DaysCheckSpec](../../../../checks/column/anomaly/sum-change-30-days.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## ColumnIntegrityDailyMonitoringChecksSpec
Container of integrity data quality monitoring checks on a column level that are checking at a daily level.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_foreign_key_not_match_count](../../../../checks/column/integrity/foreign-key-not-match-count.md)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](../../../../checks/column/integrity/foreign-key-not-match-count.md)| | | |
|[daily_foreign_key_match_percent](../../../../checks/column/integrity/foreign-key-match-percent.md)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](../../../../checks/column/integrity/foreign-key-match-percent.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

## ColumnStringsDailyMonitoringChecksSpec
Container of strings data quality monitoring checks on a column level that are checking at a daily level.









**The structure of this object is described below**

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_string_max_length](../../../../checks/column/strings/string-max-length.md)|Verifies that the length of string in a column does not exceed the maximum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMaxLengthCheckSpec](../../../../checks/column/strings/string-max-length.md)| | | |
|[daily_string_min_length](../../../../checks/column/strings/string-min-length.md)|Verifies that the length of string in a column does not fall below the minimum accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMinLengthCheckSpec](../../../../checks/column/strings/string-min-length.md)| | | |
|[daily_string_mean_length](../../../../checks/column/strings/string-mean-length.md)|Verifies that the length of string in a column does not exceed the mean accepted length. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMeanLengthCheckSpec](../../../../checks/column/strings/string-mean-length.md)| | | |
|[daily_string_length_below_min_length_count](../../../../checks/column/strings/string-length-below-min-length-count.md)|The check counts the number of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthCountCheckSpec](../../../../checks/column/strings/string-length-below-min-length-count.md)| | | |
|[daily_string_length_below_min_length_percent](../../../../checks/column/strings/string-length-below-min-length-percent.md)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](../../../../checks/column/strings/string-length-below-min-length-percent.md)| | | |
|[daily_string_length_above_max_length_count](../../../../checks/column/strings/string-length-above-max-length-count.md)|The check counts the number of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](../../../../checks/column/strings/string-length-above-max-length-count.md)| | | |
|[daily_string_length_above_max_length_percent](../../../../checks/column/strings/string-length-above-max-length-percent.md)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](../../../../checks/column/strings/string-length-above-max-length-percent.md)| | | |
|[daily_string_length_in_range_percent](../../../../checks/column/strings/string-length-in-range-percent.md)|The check counts the percentage of those strings with length in the range provided by the user in the column. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringLengthInRangePercentCheckSpec](../../../../checks/column/strings/string-length-in-range-percent.md)| | | |
|[daily_string_empty_count](../../../../checks/column/strings/string-empty-count.md)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringEmptyCountCheckSpec](../../../../checks/column/strings/string-empty-count.md)| | | |
|[daily_string_empty_percent](../../../../checks/column/strings/string-empty-percent.md)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringEmptyPercentCheckSpec](../../../../checks/column/strings/string-empty-percent.md)| | | |
|[daily_string_whitespace_count](../../../../checks/column/strings/string-whitespace-count.md)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringWhitespaceCountCheckSpec](../../../../checks/column/strings/string-whitespace-count.md)| | | |
|[daily_string_whitespace_percent](../../../../checks/column/strings/string-whitespace-percent.md)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringWhitespacePercentCheckSpec](../../../../checks/column/strings/string-whitespace-percent.md)| | | |
|[daily_string_surrounded_by_whitespace_count](../../../../checks/column/strings/string-surrounded-by-whitespace-count.md)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](../../../../checks/column/strings/string-surrounded-by-whitespace-count.md)| | | |
|[daily_string_surrounded_by_whitespace_percent](../../../../checks/column/strings/string-surrounded-by-whitespace-percent.md)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](../../../../checks/column/strings/string-surrounded-by-whitespace-percent.md)| | | |
|[daily_string_null_placeholder_count](../../../../checks/column/strings/string-null-placeholder-count.md)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringNullPlaceholderCountCheckSpec](../../../../checks/column/strings/string-null-placeholder-count.md)| | | |
|[daily_string_null_placeholder_percent](../../../../checks/column/strings/string-null-placeholder-percent.md)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringNullPlaceholderPercentCheckSpec](../../../../checks/column/strings/string-null-placeholder-percent.md)| | | |
|[daily_string_boolean_placeholder_percent](../../../../checks/column/strings/string-boolean-placeholder-percent.md)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringBooleanPlaceholderPercentCheckSpec](../../../../checks/column/strings/string-boolean-placeholder-percent.md)| | | |
|[daily_string_parsable_to_integer_percent](../../../../checks/column/strings/string-parsable-to-integer-percent.md)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringParsableToIntegerPercentCheckSpec](../../../../checks/column/strings/string-parsable-to-integer-percent.md)| | | |
|[daily_string_parsable_to_float_percent](../../../../checks/column/strings/string-parsable-to-float-percent.md)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringParsableToFloatPercentCheckSpec](../../../../checks/column/strings/string-parsable-to-float-percent.md)| | | |
|[daily_expected_strings_in_use_count](../../../../checks/column/strings/expected-strings-in-use-count.md)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnExpectedStringsInUseCountCheckSpec](../../../../checks/column/strings/expected-strings-in-use-count.md)| | | |
|[daily_string_value_in_set_percent](../../../../checks/column/strings/string-value-in-set-percent.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValueInSetPercentCheckSpec](../../../../checks/column/strings/string-value-in-set-percent.md)| | | |
|[daily_string_valid_dates_percent](../../../../checks/column/strings/string-valid-dates-percent.md)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValidDatesPercentCheckSpec](../../../../checks/column/strings/string-valid-dates-percent.md)| | | |
|[daily_string_valid_country_code_percent](../../../../checks/column/strings/string-valid-country-code-percent.md)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValidCountryCodePercentCheckSpec](../../../../checks/column/strings/string-valid-country-code-percent.md)| | | |
|[daily_string_valid_currency_code_percent](../../../../checks/column/strings/string-valid-currency-code-percent.md)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValidCurrencyCodePercentCheckSpec](../../../../checks/column/strings/string-valid-currency-code-percent.md)| | | |
|[daily_string_invalid_email_count](../../../../checks/column/strings/string-invalid-email-count.md)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringInvalidEmailCountCheckSpec](../../../../checks/column/strings/string-invalid-email-count.md)| | | |
|[daily_string_invalid_uuid_count](../../../../checks/column/strings/string-invalid-uuid-count.md)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringInvalidUuidCountCheckSpec](../../../../checks/column/strings/string-invalid-uuid-count.md)| | | |
|[daily_string_valid_uuid_percent](../../../../checks/column/strings/string-valid-uuid-percent.md)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringValidUuidPercentCheckSpec](../../../../checks/column/strings/string-valid-uuid-percent.md)| | | |
|[daily_string_invalid_ip4_address_count](../../../../checks/column/strings/string-invalid-ip4-address-count.md)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringInvalidIp4AddressCountCheckSpec](../../../../checks/column/strings/string-invalid-ip4-address-count.md)| | | |
|[daily_string_invalid_ip6_address_count](../../../../checks/column/strings/string-invalid-ip6-address-count.md)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringInvalidIp6AddressCountCheckSpec](../../../../checks/column/strings/string-invalid-ip6-address-count.md)| | | |
|[daily_string_not_match_regex_count](../../../../checks/column/strings/string-not-match-regex-count.md)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringNotMatchRegexCountCheckSpec](../../../../checks/column/strings/string-not-match-regex-count.md)| | | |
|[daily_string_match_regex_percent](../../../../checks/column/strings/string-match-regex-percent.md)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMatchRegexPercentCheckSpec](../../../../checks/column/strings/string-match-regex-percent.md)| | | |
|[daily_string_not_match_date_regex_count](../../../../checks/column/strings/string-not-match-date-regex-count.md)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringNotMatchDateRegexCountCheckSpec](../../../../checks/column/strings/string-not-match-date-regex-count.md)| | | |
|[daily_string_match_date_regex_percent](../../../../checks/column/strings/string-match-date-regex-percent.md)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMatchDateRegexPercentCheckSpec](../../../../checks/column/strings/string-match-date-regex-percent.md)| | | |
|[daily_string_match_name_regex_percent](../../../../checks/column/strings/string-match-name-regex-percent.md)|Verifies that the percentage of strings matching the name format regex in a column does not fall below the minimum accepted percentage. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnStringMatchNameRegexPercentCheckSpec](../../../../checks/column/strings/string-match-name-regex-percent.md)| | | |
|[daily_expected_strings_in_top_values_count](../../../../checks/column/strings/expected-strings-in-top-values-count.md)|Verifies that the top X most popular column values contain all values from a list of expected values. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnExpectedStringsInTopValuesCountCheckSpec](../../../../checks/column/strings/expected-strings-in-top-values-count.md)| | | |
|[custom_checks](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___

