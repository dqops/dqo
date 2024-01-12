# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## ColumnBlanksDailyPartitionedChecksSpec
Container of blank values detection data quality partitioned checks on a column level that are checking at a daily level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_empty_text_found](../../../checks/column/blanks/empty-text-found.md)|Verifies that the number of empty strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnBlanksEmptyTextFoundCheckSpec](../../../checks/column/blanks/empty-text-found.md)| | | |
|[daily_partition_whitespace_text_found](../../../checks/column/blanks/whitespace-text-found.md)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnBlanksWhitespaceTextFoundCheckSpec](../../../checks/column/blanks/whitespace-text-found.md)| | | |
|[daily_partition_null_placeholder_text_found](../../../checks/column/blanks/null-placeholder-text-found.md)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnBlanksNullPlaceholderTextFoundCheckSpec](../../../checks/column/blanks/null-placeholder-text-found.md)| | | |
|[daily_partition_empty_text_percent](../../../checks/column/blanks/empty-text-percent.md)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnBlanksEmptyTextPercentCheckSpec](../../../checks/column/blanks/empty-text-percent.md)| | | |
|[daily_partition_whitespace_text_percent](../../../checks/column/blanks/whitespace-text-percent.md)|Verifies that the percentage of whitespace strings in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnBlanksWhitespaceTextPercentCheckSpec](../../../checks/column/blanks/whitespace-text-percent.md)| | | |
|[daily_partition_null_placeholder_text_percent](../../../checks/column/blanks/null-placeholder-text-percent.md)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnBlanksNullPlaceholderTextPercentCheckSpec](../../../checks/column/blanks/null-placeholder-text-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnIntegrityDailyPartitionedChecksSpec
Container of integrity data quality partitioned checks on a column level that are checking at a daily level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_lookup_key_not_found](../../../checks/column/integrity/lookup-key-not-found.md)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnIntegrityLookupKeyNotFoundCountCheckSpec](../../../checks/column/integrity/lookup-key-not-found.md)| | | |
|[daily_partition_lookup_key_found_percent](../../../checks/column/integrity/lookup-key-found-percent.md)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](../../../checks/column/integrity/lookup-key-found-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnDatatypeDailyPartitionedChecksSpec
Container of datatype data quality partitioned checks on a column level that are checking at a daily level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_detected_datatype_in_text](../../../checks/column/datatype/detected-datatype-in-text.md)|Detects the data type of text values stored in the column. The sensor returns the code of the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDetectedDatatypeInTextCheckSpec](../../../checks/column/datatype/detected-datatype-in-text.md)| | | |
|[daily_partition_detected_datatype_in_text_changed](../../../checks/column/datatype/detected-datatype-in-text-changed.md)|Detects that the data type of texts stored in a text column has changed when compared to an earlier not empty partition. The sensor returns the detected type of column data: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDatatypeStringDatatypeChangedCheckSpec](../../../checks/column/datatype/detected-datatype-in-text-changed.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnSumAnomalyStationaryPartitionCheckSpec
Column level check that ensures that the sum in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../../sensors/column/numeric-column-sensors.md#sum)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](../../sensors/column/numeric-column-sensors.md#sum)| | | |
|[warning](./table-daily-partitioned-checks.md#AnomalyStationaryPercentileMovingAverageRule1ParametersSpec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[AnomalyStationaryPercentileMovingAverageRule1ParametersSpec](./table-daily-partitioned-checks.md#AnomalyStationaryPercentileMovingAverageRule1ParametersSpec)| | | |
|[error](./table-daily-partitioned-checks.md#AnomalyStationaryPercentileMovingAverageRule05ParametersSpec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[AnomalyStationaryPercentileMovingAverageRule05ParametersSpec](./table-daily-partitioned-checks.md#AnomalyStationaryPercentileMovingAverageRule05ParametersSpec)| | | |
|[fatal](../../rules/Percentile.md#anomaly-stationary-percentile-moving-average)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[AnomalyStationaryPercentileMovingAverageRule01ParametersSpec](../../rules/Percentile.md#anomaly-stationary-percentile-moving-average)| | | |
|[schedule_override](../profiling/table-profiling-checks.md#MonitoringScheduleSpec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](../profiling/table-profiling-checks.md#MonitoringScheduleSpec)| | | |
|[comments](../profiling/table-profiling-checks.md#CommentsListSpec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](../profiling/table-profiling-checks.md#CommentsListSpec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___


## ColumnPatternsDailyPartitionedChecksSpec
Container of built-in preconfigured daily partition checks on a column level that are checking for values matching patterns (regular expressions) in text columns.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_text_not_matching_regex_found](../../../checks/column/patterns/text-not-matching-regex-found.md)|Verifies that the number of text values not matching the custom regular expression pattern does not exceed the maximum accepted count.|[ColumnTextNotMatchingRegexFoundCheckSpec](../../../checks/column/patterns/text-not-matching-regex-found.md)| | | |
|[daily_partition_texts_matching_regex_percent](../../../checks/column/patterns/texts-matching-regex-percent.md)|Verifies that the percentage of strings matching the custom regular expression pattern does not fall below the minimum accepted percentage.|[ColumnTextsMatchingRegexPercentCheckSpec](../../../checks/column/patterns/texts-matching-regex-percent.md)| | | |
|[daily_partition_invalid_email_format_found](../../../checks/column/patterns/invalid-email-format-found.md)|Verifies that the number of invalid emails in a text column does not exceed the maximum accepted count.|[ColumnInvalidEmailFormatFoundCheckSpec](../../../checks/column/patterns/invalid-email-format-found.md)| | | |
|[daily_partition_text_not_matching_date_pattern_found](../../../checks/column/patterns/text-not-matching-date-pattern-found.md)|Verifies that the number of texts not matching the date format regular expression does not exceed the maximum accepted count.|[ColumnTextNotMatchingDatePatternFoundCheckSpec](../../../checks/column/patterns/text-not-matching-date-pattern-found.md)| | | |
|[daily_partition_text_matching_date_pattern_percent](../../../checks/column/patterns/text-matching-date-pattern-percent.md)|Verifies that the percentage of texts matching the date format regular expression in a column does not fall below the minimum accepted percentage.|[ColumnTextMatchingDatePatternPercentCheckSpec](../../../checks/column/patterns/text-matching-date-pattern-percent.md)| | | |
|[daily_partition_text_matching_name_pattern_percent](../../../checks/column/patterns/text-matching-name-pattern-percent.md)|Verifies that the percentage of texts matching the name regular expression does not fall below the minimum accepted percentage.|[ColumnTextMatchingNamePatternPercentCheckSpec](../../../checks/column/patterns/text-matching-name-pattern-percent.md)| | | |
|[daily_partition_invalid_uuid_format_found](../../../checks/column/patterns/invalid-uuid-format-found.md)|Verifies that the number of invalid UUIDs in a text column does not exceed the maximum accepted count.|[ColumnInvalidUuidFormatFoundCheckSpec](../../../checks/column/patterns/invalid-uuid-format-found.md)| | | |
|[daily_partition_valid_uuid_format_percent](../../../checks/column/patterns/valid-uuid-format-percent.md)|Verifies that the percentage of valid UUID in a text column does not fall below the minimum accepted percentage.|[ColumnValidUuidFormatPercentCheckSpec](../../../checks/column/patterns/valid-uuid-format-percent.md)| | | |
|[daily_partition_invalid_ip4_address_format_found](../../../checks/column/patterns/invalid-ip4-address-format-found.md)|Verifies that the number of invalid IP4 addresses in a text column does not exceed the maximum accepted count.|[ColumnInvalidIp4AddressFormatFoundCheckSpec](../../../checks/column/patterns/invalid-ip4-address-format-found.md)| | | |
|[daily_partition_invalid_ip6_address_format_found](../../../checks/column/patterns/invalid-ip6-address-format-found.md)|Verifies that the number of invalid IP6 addresses in a text column does not exceed the maximum accepted count.|[ColumnInvalidIp6AddressFormatFoundCheckSpec](../../../checks/column/patterns/invalid-ip6-address-format-found.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnDailyPartitionedCheckCategoriesSpec
Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](./column-daily-partitioned-checks.md#ColumnNullsDailyPartitionedChecksSpec)|Daily partitioned checks of nulls in the column|[ColumnNullsDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnNullsDailyPartitionedChecksSpec)| | | |
|[uniqueness](./column-daily-partitioned-checks.md#ColumnUniquenessDailyPartitionedChecksSpec)|Daily partitioned checks of uniqueness in the column|[ColumnUniquenessDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnUniquenessDailyPartitionedChecksSpec)| | | |
|[accepted_values](./column-daily-partitioned-checks.md#ColumnAcceptedValuesDailyPartitionedChecksSpec)|Configuration of accepted values checks on a column level|[ColumnAcceptedValuesDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnAcceptedValuesDailyPartitionedChecksSpec)| | | |
|[text](./column-daily-partitioned-checks.md#ColumnTextDailyPartitionedChecksSpec)|Daily partitioned checks of text values in the column|[ColumnTextDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnTextDailyPartitionedChecksSpec)| | | |
|[blanks](./column-daily-partitioned-checks.md#ColumnBlanksDailyPartitionedChecksSpec)|Configuration of column level checks that detect blank and whitespace values|[ColumnBlanksDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnBlanksDailyPartitionedChecksSpec)| | | |
|[patterns](./column-daily-partitioned-checks.md#ColumnPatternsDailyPartitionedChecksSpec)|Daily partitioned pattern match checks on a column level|[ColumnPatternsDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnPatternsDailyPartitionedChecksSpec)| | | |
|[pii](./column-daily-partitioned-checks.md#ColumnPiiDailyPartitionedChecksSpec)|Daily partitioned checks of Personal Identifiable Information (PII) in the column|[ColumnPiiDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnPiiDailyPartitionedChecksSpec)| | | |
|[numeric](./column-daily-partitioned-checks.md#ColumnNumericDailyPartitionedChecksSpec)|Daily partitioned checks of numeric values in the column|[ColumnNumericDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnNumericDailyPartitionedChecksSpec)| | | |
|[anomaly](./column-daily-partitioned-checks.md#ColumnAnomalyDailyPartitionedChecksSpec)|Daily partitioned checks for anomalies in numeric columns|[ColumnAnomalyDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnAnomalyDailyPartitionedChecksSpec)| | | |
|[datetime](./column-daily-partitioned-checks.md#ColumnDatetimeDailyPartitionedChecksSpec)|Daily partitioned checks of datetime in the column|[ColumnDatetimeDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnDatetimeDailyPartitionedChecksSpec)| | | |
|[bool](./column-daily-partitioned-checks.md#ColumnBoolDailyPartitionedChecksSpec)|Daily partitioned checks for booleans in the column|[ColumnBoolDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnBoolDailyPartitionedChecksSpec)| | | |
|[integrity](./column-daily-partitioned-checks.md#ColumnIntegrityDailyPartitionedChecksSpec)|Daily partitioned checks for integrity in the column|[ColumnIntegrityDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnIntegrityDailyPartitionedChecksSpec)| | | |
|[custom_sql](./column-daily-partitioned-checks.md#ColumnCustomSqlDailyPartitionedChecksSpec)|Daily partitioned checks using custom SQL expressions evaluated on the column|[ColumnCustomSqlDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnCustomSqlDailyPartitionedChecksSpec)| | | |
|[datatype](./column-daily-partitioned-checks.md#ColumnDatatypeDailyPartitionedChecksSpec)|Daily partitioned checks for datatype in the column|[ColumnDatatypeDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnDatatypeDailyPartitionedChecksSpec)| | | |
|[comparisons](./column-daily-partitioned-checks.md#ColumnComparisonDailyPartitionedChecksSpecMap)|Dictionary of configuration of checks for table comparisons at a column level. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[ColumnComparisonDailyPartitionedChecksSpecMap](./column-daily-partitioned-checks.md#ColumnComparisonDailyPartitionedChecksSpecMap)| | | |
|[custom](../profiling/table-profiling-checks.md#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../profiling/table-profiling-checks.md#CustomCheckSpecMap)| | | |









___


## ColumnPiiDailyPartitionedChecksSpec
Container of PII data quality partitioned checks on a column level that are checking at a daily level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_contains_usa_phone_percent](../../../checks/column/pii/contains-usa-phone-percent.md)|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsUsaPhonePercentCheckSpec](../../../checks/column/pii/contains-usa-phone-percent.md)| | | |
|[daily_partition_contains_usa_zipcode_percent](../../../checks/column/pii/contains-usa-zipcode-percent.md)|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](../../../checks/column/pii/contains-usa-zipcode-percent.md)| | | |
|[daily_partition_contains_email_percent](../../../checks/column/pii/contains-email-percent.md)|Verifies that the percentage of rows that contains emails in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsEmailPercentCheckSpec](../../../checks/column/pii/contains-email-percent.md)| | | |
|[daily_partition_contains_ip4_percent](../../../checks/column/pii/contains-ip4-percent.md)|Verifies that the percentage of rows that contains IP4 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsIp4PercentCheckSpec](../../../checks/column/pii/contains-ip4-percent.md)| | | |
|[daily_partition_contains_ip6_percent](../../../checks/column/pii/contains-ip6-percent.md)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPiiContainsIp6PercentCheckSpec](../../../checks/column/pii/contains-ip6-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnNullsDailyPartitionedChecksSpec
Container of nulls data quality partitioned checks on a column level that are checking at a daily level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_nulls_count](../../../checks/column/nulls/nulls-count.md)|Detects null values in a column. Verifies that the number of null values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNullsCountCheckSpec](../../../checks/column/nulls/nulls-count.md)| | | |
|[daily_partition_nulls_percent](../../../checks/column/nulls/nulls-percent.md)|Measures the percent of null values in a column. Raises a data quality exception when the percentage of null values is above the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNullsPercentCheckSpec](../../../checks/column/nulls/nulls-percent.md)| | | |
|[daily_partition_not_nulls_count](../../../checks/column/nulls/not-nulls-count.md)|Detects empty columns. The default rule min_count&#x3D;1 verifies that the column has any values. Verifies that the number of not null values in a column does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNotNullsCountCheckSpec](../../../checks/column/nulls/not-nulls-count.md)| | | |
|[daily_partition_not_nulls_percent](../../../checks/column/nulls/not-nulls-percent.md)|Measures the percent of not null values in a column. Raises a data quality exception when the percentage of not null values is below a minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNotNullsPercentCheckSpec](../../../checks/column/nulls/not-nulls-percent.md)| | | |
|[daily_partition_nulls_percent_anomaly](../../../checks/column/nulls/nulls-percent-anomaly.md)|Verifies that the null percent value in a column changes in a rate within a percentile boundary during last 90 days.|[ColumnNullPercentAnomalyStationaryCheckSpec](../../../checks/column/nulls/nulls-percent-anomaly.md)| | | |
|[daily_partition_nulls_percent_change](../../../checks/column/nulls/nulls-percent-change.md)|Verifies that the null percent value in a column changed in a fixed rate since last readout.|[ColumnNullPercentChangeCheckSpec](../../../checks/column/nulls/nulls-percent-change.md)| | | |
|[daily_partition_nulls_percent_change_1_day](../../../checks/column/nulls/nulls-percent-change-1-day.md)|Verifies that the null percent value in a column changed in a fixed rate since the last readout from yesterday.|[ColumnNullPercentChange1DayCheckSpec](../../../checks/column/nulls/nulls-percent-change-1-day.md)| | | |
|[daily_partition_nulls_percent_change_7_days](../../../checks/column/nulls/nulls-percent-change-7-days.md)|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last week.|[ColumnNullPercentChange7DaysCheckSpec](../../../checks/column/nulls/nulls-percent-change-7-days.md)| | | |
|[daily_partition_nulls_percent_change_30_days](../../../checks/column/nulls/nulls-percent-change-30-days.md)|Verifies that the null percent value in a column changed in a fixed rate since the last readout from the last month.|[ColumnNullPercentChange30DaysCheckSpec](../../../checks/column/nulls/nulls-percent-change-30-days.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnDistinctCountAnomalyStationaryPartitionCheckSpec
Column-level check that ensures that the distinct count value in a monitored column is within a two-tailed percentile from measurements made during the last 90 days. Use in partitioned checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](../../sensors/column/uniqueness-column-sensors.md#distinct-count)|Data quality check parameters|[ColumnUniquenessDistinctCountSensorParametersSpec](../../sensors/column/uniqueness-column-sensors.md#distinct-count)| | | |
|[warning](./table-daily-partitioned-checks.md#AnomalyStationaryPercentileMovingAverageRule1ParametersSpec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[AnomalyStationaryPercentileMovingAverageRule1ParametersSpec](./table-daily-partitioned-checks.md#AnomalyStationaryPercentileMovingAverageRule1ParametersSpec)| | | |
|[error](./table-daily-partitioned-checks.md#AnomalyStationaryPercentileMovingAverageRule05ParametersSpec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[AnomalyStationaryPercentileMovingAverageRule05ParametersSpec](./table-daily-partitioned-checks.md#AnomalyStationaryPercentileMovingAverageRule05ParametersSpec)| | | |
|[fatal](../../rules/Percentile.md#anomaly-stationary-percentile-moving-average)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[AnomalyStationaryPercentileMovingAverageRule01ParametersSpec](../../rules/Percentile.md#anomaly-stationary-percentile-moving-average)| | | |
|[schedule_override](../profiling/table-profiling-checks.md#MonitoringScheduleSpec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](../profiling/table-profiling-checks.md#MonitoringScheduleSpec)| | | |
|[comments](../profiling/table-profiling-checks.md#CommentsListSpec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](../profiling/table-profiling-checks.md#CommentsListSpec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___


## ColumnTextDailyPartitionedChecksSpec
Container of text data quality partitioned checks on a column level that are checking at a dailyPartition level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_text_max_length](../../../checks/column/text/text-max-length.md)|Verifies that the length of a text in a column does not exceed the maximum accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextMaxLengthCheckSpec](../../../checks/column/text/text-max-length.md)| | | |
|[daily_partition_text_min_length](../../../checks/column/text/text-min-length.md)|Verifies that the length of a text in a column does not fall below the minimum accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextMinLengthCheckSpec](../../../checks/column/text/text-min-length.md)| | | |
|[daily_partition_text_mean_length](../../../checks/column/text/text-mean-length.md)|Verifies that the length of a text in a column does not exceed the mean accepted length. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextMeanLengthCheckSpec](../../../checks/column/text/text-mean-length.md)| | | |
|[daily_partition_text_length_below_min_length](../../../checks/column/text/text-length-below-min-length.md)|The check counts the number of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextLengthBelowMinLengthCheckSpec](../../../checks/column/text/text-length-below-min-length.md)| | | |
|[daily_partition_text_length_below_min_length_percent](../../../checks/column/text/text-length-below-min-length-percent.md)|The check measures the percentage of text values in the column that is below the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextLengthBelowMinLengthPercentCheckSpec](../../../checks/column/text/text-length-below-min-length-percent.md)| | | |
|[daily_partition_text_length_above_max_length](../../../checks/column/text/text-length-above-max-length.md)|The check counts the number of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextLengthAboveMaxLengthCheckSpec](../../../checks/column/text/text-length-above-max-length.md)| | | |
|[daily_partition_text_length_above_max_length_percent](../../../checks/column/text/text-length-above-max-length-percent.md)|The check measures the percentage of text values in the column that is above the length defined by the user as a parameter. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextLengthAboveMaxLengthPercentCheckSpec](../../../checks/column/text/text-length-above-max-length-percent.md)| | | |
|[daily_partition_text_length_in_range_percent](../../../checks/column/text/text-length-in-range-percent.md)|The check measures the percentage of those text values with length in the range provided by the user in the column. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextLengthInRangePercentCheckSpec](../../../checks/column/text/text-length-in-range-percent.md)| | | |
|[daily_partition_text_parsable_to_boolean_percent](../../../checks/column/text/text-parsable-to-boolean-percent.md)|Verifies that the percentage of text values that are parsable to a boolean value does not fall below the minimum accepted percentage, text values identified as boolean placeholders are: 0, 1, true, false, t, f, yes, no, y, n. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextParsableToBooleanPercentCheckSpec](../../../checks/column/text/text-parsable-to-boolean-percent.md)| | | |
|[daily_partition_text_parsable_to_integer_percent](../../../checks/column/text/text-parsable-to-integer-percent.md)|Verifies that the percentage text values that are parsable to an integer value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextParsableToIntegerPercentCheckSpec](../../../checks/column/text/text-parsable-to-integer-percent.md)| | | |
|[daily_partition_text_parsable_to_float_percent](../../../checks/column/text/text-parsable-to-float-percent.md)|Verifies that the percentage text values that are parsable to a float value in a column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextParsableToFloatPercentCheckSpec](../../../checks/column/text/text-parsable-to-float-percent.md)| | | |
|[daily_partition_text_parsable_to_date_percent](../../../checks/column/text/text-parsable-to-date-percent.md)|Verifies that the percentage text values that are parsable to a date value in a column does not fall below the minimum accepted percentage. DQOps uses a safe_cast when possible, otherwise the text is verified using a regular expression. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextParsableToDatePercentCheckSpec](../../../checks/column/text/text-parsable-to-date-percent.md)| | | |
|[daily_partition_text_surrounded_by_whitespace](../../../checks/column/text/text-surrounded-by-whitespace.md)|The check counts the number of text values in the column that are surrounded by whitespace characters and should be trimmed before loading to another table. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextSurroundedByWhitespaceCheckSpec](../../../checks/column/text/text-surrounded-by-whitespace.md)| | | |
|[daily_partition_text_surrounded_by_whitespace_percent](../../../checks/column/text/text-surrounded-by-whitespace-percent.md)|Verifies that the percentage of text values that are surrounded by whitespace characters in a column does not exceed the maximum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextSurroundedByWhitespacePercentCheckSpec](../../../checks/column/text/text-surrounded-by-whitespace-percent.md)| | | |
|[daily_partition_text_valid_country_code_percent](../../../checks/column/text/text-valid-country-code-percent.md)|Verifies that the percentage of valid country codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextValidCountryCodePercentCheckSpec](../../../checks/column/text/text-valid-country-code-percent.md)| | | |
|[daily_partition_text_valid_currency_code_percent](../../../checks/column/text/text-valid-currency-code-percent.md)|Verifies that the percentage of valid currency codes in a text column does not fall below the minimum accepted percentage. Analyzes every daily partition and creates a separate data quality check result with the time period value that identifies the daily partition.|[ColumnTextValidCurrencyCodePercentCheckSpec](../../../checks/column/text/text-valid-currency-code-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnBoolDailyPartitionedChecksSpec
Container of boolean data quality partitioned checks on a column level that are checking at a daily level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_true_percent](../../../checks/column/bool/true-percent.md)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnTruePercentCheckSpec](../../../checks/column/bool/true-percent.md)| | | |
|[daily_partition_false_percent](../../../checks/column/bool/false-percent.md)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnFalsePercentCheckSpec](../../../checks/column/bool/false-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnNumericDailyPartitionedChecksSpec
Container of numeric data quality partitioned checks on a column level that are checking at a daily level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_number_below_min_value](../../../checks/column/numeric/number-below-min-value.md)|The check counts the number of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNumberBelowMinValueCheckSpec](../../../checks/column/numeric/number-below-min-value.md)| | | |
|[daily_partition_number_above_max_value](../../../checks/column/numeric/number-above-max-value.md)|The check counts the number of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNumberAboveMaxValueCheckSpec](../../../checks/column/numeric/number-above-max-value.md)| | | |
|[daily_partition_negative_values](../../../checks/column/numeric/negative-values.md)|Verifies that the number of negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNegativeCountCheckSpec](../../../checks/column/numeric/negative-values.md)| | | |
|[daily_partition_negative_values_percent](../../../checks/column/numeric/negative-values-percent.md)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNegativePercentCheckSpec](../../../checks/column/numeric/negative-values-percent.md)| | | |
|[daily_partition_number_below_min_value_percent](../../../checks/column/numeric/number-below-min-value-percent.md)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNumberBelowMinValuePercentCheckSpec](../../../checks/column/numeric/number-below-min-value-percent.md)| | | |
|[daily_partition_number_above_max_value_percent](../../../checks/column/numeric/number-above-max-value-percent.md)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNumberAboveMaxValuePercentCheckSpec](../../../checks/column/numeric/number-above-max-value-percent.md)| | | |
|[daily_partition_number_in_range_percent](../../../checks/column/numeric/number-in-range-percent.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNumberInRangePercentCheckSpec](../../../checks/column/numeric/number-in-range-percent.md)| | | |
|[daily_partition_integer_in_range_percent](../../../checks/column/numeric/integer-in-range-percent.md)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnIntegerInRangePercentCheckSpec](../../../checks/column/numeric/integer-in-range-percent.md)| | | |
|[daily_partition_min_in_range](../../../checks/column/numeric/min-in-range.md)|Verifies that the minimal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMinInRangeCheckSpec](../../../checks/column/numeric/min-in-range.md)| | | |
|[daily_partition_max_in_range](../../../checks/column/numeric/max-in-range.md)|Verifies that the maximal value in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMaxInRangeCheckSpec](../../../checks/column/numeric/max-in-range.md)| | | |
|[daily_partition_sum_in_range](../../../checks/column/numeric/sum-in-range.md)|Verifies that the sum of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSumInRangeCheckSpec](../../../checks/column/numeric/sum-in-range.md)| | | |
|[daily_partition_mean_in_range](../../../checks/column/numeric/mean-in-range.md)|Verifies that the average (mean) of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMeanInRangeCheckSpec](../../../checks/column/numeric/mean-in-range.md)| | | |
|[daily_partition_median_in_range](../../../checks/column/numeric/median-in-range.md)|Verifies that the median of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnMedianInRangeCheckSpec](../../../checks/column/numeric/median-in-range.md)| | | |
|[daily_partition_percentile_in_range](../../../checks/column/numeric/percentile-in-range.md)|Verifies that the percentile of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentileInRangeCheckSpec](../../../checks/column/numeric/percentile-in-range.md)| | | |
|[daily_partition_percentile_10_in_range](../../../checks/column/numeric/percentile-10-in-range.md)|Verifies that the percentile 10 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile10InRangeCheckSpec](../../../checks/column/numeric/percentile-10-in-range.md)| | | |
|[daily_partition_percentile_25_in_range](../../../checks/column/numeric/percentile-25-in-range.md)|Verifies that the percentile 25 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile25InRangeCheckSpec](../../../checks/column/numeric/percentile-25-in-range.md)| | | |
|[daily_partition_percentile_75_in_range](../../../checks/column/numeric/percentile-75-in-range.md)|Verifies that the percentile 75 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile75InRangeCheckSpec](../../../checks/column/numeric/percentile-75-in-range.md)| | | |
|[daily_partition_percentile_90_in_range](../../../checks/column/numeric/percentile-90-in-range.md)|Verifies that the percentile 90 of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPercentile90InRangeCheckSpec](../../../checks/column/numeric/percentile-90-in-range.md)| | | |
|[daily_partition_sample_stddev_in_range](../../../checks/column/numeric/sample-stddev-in-range.md)|Verifies that the sample standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSampleStddevInRangeCheckSpec](../../../checks/column/numeric/sample-stddev-in-range.md)| | | |
|[daily_partition_population_stddev_in_range](../../../checks/column/numeric/population-stddev-in-range.md)|Verifies that the population standard deviation of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPopulationStddevInRangeCheckSpec](../../../checks/column/numeric/population-stddev-in-range.md)| | | |
|[daily_partition_sample_variance_in_range](../../../checks/column/numeric/sample-variance-in-range.md)|Verifies that the sample variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSampleVarianceInRangeCheckSpec](../../../checks/column/numeric/sample-variance-in-range.md)| | | |
|[daily_partition_population_variance_in_range](../../../checks/column/numeric/population-variance-in-range.md)|Verifies that the population variance of all values in a column is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnPopulationVarianceInRangeCheckSpec](../../../checks/column/numeric/population-variance-in-range.md)| | | |
|[daily_partition_invalid_latitude](../../../checks/column/numeric/invalid-latitude.md)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnInvalidLatitudeCountCheckSpec](../../../checks/column/numeric/invalid-latitude.md)| | | |
|[daily_partition_valid_latitude_percent](../../../checks/column/numeric/valid-latitude-percent.md)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValidLatitudePercentCheckSpec](../../../checks/column/numeric/valid-latitude-percent.md)| | | |
|[daily_partition_invalid_longitude](../../../checks/column/numeric/invalid-longitude.md)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnInvalidLongitudeCountCheckSpec](../../../checks/column/numeric/invalid-longitude.md)| | | |
|[daily_partition_valid_longitude_percent](../../../checks/column/numeric/valid-longitude-percent.md)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnValidLongitudePercentCheckSpec](../../../checks/column/numeric/valid-longitude-percent.md)| | | |
|[daily_partition_non_negative_values](../../../checks/column/numeric/non-negative-values.md)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNonNegativeCountCheckSpec](../../../checks/column/numeric/non-negative-values.md)| | | |
|[daily_partition_non_negative_values_percent](../../../checks/column/numeric/non-negative-values-percent.md)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNonNegativePercentCheckSpec](../../../checks/column/numeric/non-negative-values-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnAcceptedValuesDailyPartitionedChecksSpec
Container of accepted values data quality partitioned checks on a column level that are checking at a daily level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_text_found_in_set_percent](../../../checks/column/accepted_values/text-found-in-set-percent.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnTextFoundInSetPercentCheckSpec](../../../checks/column/accepted_values/text-found-in-set-percent.md)| | | |
|[daily_partition_number_found_in_set_percent](../../../checks/column/accepted_values/number-found-in-set-percent.md)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnNumberFoundInSetPercentCheckSpec](../../../checks/column/accepted_values/number-found-in-set-percent.md)| | | |
|[daily_partition_expected_text_values_in_use_count](../../../checks/column/accepted_values/expected-text-values-in-use-count.md)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnExpectedTextValuesInUseCountCheckSpec](../../../checks/column/accepted_values/expected-text-values-in-use-count.md)| | | |
|[daily_partition_expected_texts_in_top_values_count](../../../checks/column/accepted_values/expected-texts-in-top-values-count.md)|Verifies that the top X most popular column values contain all values from a list of expected values. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnExpectedTextsInTopValuesCountCheckSpec](../../../checks/column/accepted_values/expected-texts-in-top-values-count.md)| | | |
|[daily_partition_expected_numbers_in_use_count](../../../checks/column/accepted_values/expected-numbers-in-use-count.md)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnExpectedNumbersInUseCountCheckSpec](../../../checks/column/accepted_values/expected-numbers-in-use-count.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnUniquenessDailyPartitionedChecksSpec
Container of uniqueness data quality partitioned checks on a column level that are checking at a daily level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_distinct_count](../../../checks/column/uniqueness/distinct-count.md)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDistinctCountCheckSpec](../../../checks/column/uniqueness/distinct-count.md)| | | |
|[daily_partition_distinct_percent](../../../checks/column/uniqueness/distinct-percent.md)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDistinctPercentCheckSpec](../../../checks/column/uniqueness/distinct-percent.md)| | | |
|[daily_partition_duplicate_count](../../../checks/column/uniqueness/duplicate-count.md)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDuplicateCountCheckSpec](../../../checks/column/uniqueness/duplicate-count.md)| | | |
|[daily_partition_duplicate_percent](../../../checks/column/uniqueness/duplicate-percent.md)|Verifies that the percent of duplicate values in a column does not exceed the maximum accepted percent. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDuplicatePercentCheckSpec](../../../checks/column/uniqueness/duplicate-percent.md)| | | |
|[daily_partition_distinct_count_anomaly](./column-daily-partitioned-checks.md#ColumnDistinctCountAnomalyStationaryPartitionCheckSpec)|Verifies that the distinct count in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnDistinctCountAnomalyStationaryPartitionCheckSpec](./column-daily-partitioned-checks.md#ColumnDistinctCountAnomalyStationaryPartitionCheckSpec)| | | |
|[daily_partition_distinct_percent_anomaly](../../../checks/column/uniqueness/distinct-percent-anomaly.md)|Verifies that the distinct percent in a monitored column is within a two-tailed percentile from measurements made during the last 90 days.|[ColumnDistinctPercentAnomalyStationaryCheckSpec](../../../checks/column/uniqueness/distinct-percent-anomaly.md)| | | |
|[daily_partition_distinct_count_change](../../../checks/column/uniqueness/distinct-count-change.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout.|[ColumnDistinctCountChangeCheckSpec](../../../checks/column/uniqueness/distinct-count-change.md)| | | |
|[daily_partition_distinct_count_change_1_day](../../../checks/column/uniqueness/distinct-count-change-1-day.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnDistinctCountChange1DayCheckSpec](../../../checks/column/uniqueness/distinct-count-change-1-day.md)| | | |
|[daily_partition_distinct_count_change_7_days](../../../checks/column/uniqueness/distinct-count-change-7-days.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last week.|[ColumnDistinctCountChange7DaysCheckSpec](../../../checks/column/uniqueness/distinct-count-change-7-days.md)| | | |
|[daily_partition_distinct_count_change_30_days](../../../checks/column/uniqueness/distinct-count-change-30-days.md)|Verifies that the distinct count in a monitored column has changed by a fixed rate since the last readout from the last month.|[ColumnDistinctCountChange30DaysCheckSpec](../../../checks/column/uniqueness/distinct-count-change-30-days.md)| | | |
|[daily_partition_distinct_percent_change](../../../checks/column/uniqueness/distinct-percent-change.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout.|[ColumnDistinctPercentChangeCheckSpec](../../../checks/column/uniqueness/distinct-percent-change.md)| | | |
|[daily_partition_distinct_percent_change_1_day](../../../checks/column/uniqueness/distinct-percent-change-1-day.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from yesterday.|[ColumnDistinctPercentChange1DayCheckSpec](../../../checks/column/uniqueness/distinct-percent-change-1-day.md)| | | |
|[daily_partition_distinct_percent_change_7_days](../../../checks/column/uniqueness/distinct-percent-change-7-days.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last week.|[ColumnDistinctPercentChange7DaysCheckSpec](../../../checks/column/uniqueness/distinct-percent-change-7-days.md)| | | |
|[daily_partition_distinct_percent_change_30_days](../../../checks/column/uniqueness/distinct-percent-change-30-days.md)|Verifies that the distinct percent in a monitored column has changed by a fixed rate since the last readout from the last month.|[ColumnDistinctPercentChange30DaysCheckSpec](../../../checks/column/uniqueness/distinct-percent-change-30-days.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnCustomSqlDailyPartitionedChecksSpec
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_sql_condition_passed_percent_on_column](../../../checks/column/custom_sql/sql-condition-passed-percent-on-column.md)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlConditionPassedPercentCheckSpec](../../../checks/column/custom_sql/sql-condition-passed-percent-on-column.md)| | | |
|[daily_partition_sql_condition_failed_count_on_column](../../../checks/column/custom_sql/sql-condition-failed-count-on-column.md)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlConditionFailedCountCheckSpec](../../../checks/column/custom_sql/sql-condition-failed-count-on-column.md)| | | |
|[daily_partition_sql_aggregate_expression_on_column](../../../checks/column/custom_sql/sql-aggregate-expression-on-column.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnSqlAggregateExpressionCheckSpec](../../../checks/column/custom_sql/sql-aggregate-expression-on-column.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnAnomalyDailyPartitionedChecksSpec
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_mean_anomaly](../../../checks/column/anomaly/mean-anomaly.md)|Verifies that the mean value in a column is within a percentile from measurements made during the last 90 days.|[ColumnMeanAnomalyStationaryCheckSpec](../../../checks/column/anomaly/mean-anomaly.md)| | | |
|[daily_partition_median_anomaly](../../../checks/column/anomaly/median-anomaly.md)|Verifies that the median in a column is within a percentile from measurements made during the last 90 days.|[ColumnMedianAnomalyStationaryCheckSpec](../../../checks/column/anomaly/median-anomaly.md)| | | |
|[daily_partition_sum_anomaly](./column-daily-partitioned-checks.md#ColumnSumAnomalyStationaryPartitionCheckSpec)|Verifies that the sum in a column is within a percentile from measurements made during the last 90 days.|[ColumnSumAnomalyStationaryPartitionCheckSpec](./column-daily-partitioned-checks.md#ColumnSumAnomalyStationaryPartitionCheckSpec)| | | |
|[daily_partition_mean_change](../../../checks/column/anomaly/mean-change.md)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnMeanChangeCheckSpec](../../../checks/column/anomaly/mean-change.md)| | | |
|[daily_partition_mean_change_1_day](../../../checks/column/anomaly/mean-change-1-day.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from yesterday.|[ColumnMeanChange1DayCheckSpec](../../../checks/column/anomaly/mean-change-1-day.md)| | | |
|[daily_partition_mean_change_7_days](../../../checks/column/anomaly/mean-change-7-days.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last week.|[ColumnMeanChange7DaysCheckSpec](../../../checks/column/anomaly/mean-change-7-days.md)| | | |
|[daily_partition_mean_change_30_days](../../../checks/column/anomaly/mean-change-30-days.md)|Verifies that the mean value in a column changed in a fixed rate since the last readout from the last month.|[ColumnMeanChange30DaysCheckSpec](../../../checks/column/anomaly/mean-change-30-days.md)| | | |
|[daily_partition_median_change](../../../checks/column/anomaly/median-change.md)|Verifies that the median in a column changed in a fixed rate since the last readout.|[ColumnMedianChangeCheckSpec](../../../checks/column/anomaly/median-change.md)| | | |
|[daily_partition_median_change_1_day](../../../checks/column/anomaly/median-change-1-day.md)|Verifies that the median in a column changed in a fixed rate since the last readout from yesterday.|[ColumnMedianChange1DayCheckSpec](../../../checks/column/anomaly/median-change-1-day.md)| | | |
|[daily_partition_median_change_7_days](../../../checks/column/anomaly/median-change-7-days.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last week.|[ColumnMedianChange7DaysCheckSpec](../../../checks/column/anomaly/median-change-7-days.md)| | | |
|[daily_partition_median_change_30_days](../../../checks/column/anomaly/median-change-30-days.md)|Verifies that the median in a column changed in a fixed rate since the last readout from the last month.|[ColumnMedianChange30DaysCheckSpec](../../../checks/column/anomaly/median-change-30-days.md)| | | |
|[daily_partition_sum_change](../../../checks/column/anomaly/sum-change.md)|Verifies that the sum in a column changed in a fixed rate since the last readout.|[ColumnSumChangeCheckSpec](../../../checks/column/anomaly/sum-change.md)| | | |
|[daily_partition_sum_change_1_day](../../../checks/column/anomaly/sum-change-1-day.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from yesterday.|[ColumnSumChange1DayCheckSpec](../../../checks/column/anomaly/sum-change-1-day.md)| | | |
|[daily_partition_sum_change_7_days](../../../checks/column/anomaly/sum-change-7-days.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from the last week.|[ColumnSumChange7DaysCheckSpec](../../../checks/column/anomaly/sum-change-7-days.md)| | | |
|[daily_partition_sum_change_30_days](../../../checks/column/anomaly/sum-change-30-days.md)|Verifies that the sum in a column changed in a fixed rate since the last readout from the last month.|[ColumnSumChange30DaysCheckSpec](../../../checks/column/anomaly/sum-change-30-days.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnDatetimeDailyPartitionedChecksSpec
Container of date-time data quality partitioned checks on a column level that are checking at a daily level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_date_match_format_percent](../../../checks/column/datetime/date-match-format-percent.md)|Verifies that the percentage of date values matching the given format in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDatetimeDateMatchFormatPercentCheckSpec](../../../checks/column/datetime/date-match-format-percent.md)| | | |
|[daily_partition_date_values_in_future_percent](../../../checks/column/datetime/date-values-in-future-percent.md)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDateValuesInFuturePercentCheckSpec](../../../checks/column/datetime/date-values-in-future-percent.md)| | | |
|[daily_partition_datetime_value_in_range_date_percent](../../../checks/column/datetime/datetime-value-in-range-date-percent.md)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage. Creates a separate data quality check (and an alert) for each daily partition.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](../../../checks/column/datetime/datetime-value-in-range-date-percent.md)| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## ColumnComparisonDailyPartitionedChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [ColumnComparisonDailyPartitionedChecksSpec](./column-daily-partitioned-checks.md#ColumnComparisonDailyPartitionedChecksSpec)]| | | |









___


## ColumnComparisonDailyPartitionedChecksSpec
Container of built-in preconfigured column level comparison checks that compare min/max/sum/mean/nulls measures
 between the column in the tested (parent) table and a matching reference column in the reference table (the source of truth).
 This is the configuration for daily partitioned checks that are counted in KPIs.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_sum_match](../../../checks/column/comparisons/sum-match.md)|Verifies that percentage of the difference between the sum of values in a tested column in a parent table and the sum of a values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonSumMatchCheckSpec](../../../checks/column/comparisons/sum-match.md)| | | |
|[daily_partition_min_match](../../../checks/column/comparisons/min-match.md)|Verifies that percentage of the difference between the minimum value in a tested column in a parent table and the minimum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMinMatchCheckSpec](../../../checks/column/comparisons/min-match.md)| | | |
|[daily_partition_max_match](../../../checks/column/comparisons/max-match.md)|Verifies that percentage of the difference between the maximum value in a tested column in a parent table and the maximum value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMaxMatchCheckSpec](../../../checks/column/comparisons/max-match.md)| | | |
|[daily_partition_mean_match](../../../checks/column/comparisons/mean-match.md)|Verifies that percentage of the difference between the mean (average) value in a tested column in a parent table and the mean (average) value in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonMeanMatchCheckSpec](../../../checks/column/comparisons/mean-match.md)| | | |
|[daily_partition_not_null_count_match](../../../checks/column/comparisons/not-null-count-match.md)|Verifies that percentage of the difference between the count of not null values in a tested column in a parent table and the count of not null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonNotNullCountMatchCheckSpec](../../../checks/column/comparisons/not-null-count-match.md)| | | |
|[daily_partition_null_count_match](../../../checks/column/comparisons/null-count-match.md)|Verifies that percentage of the difference between the count of null values in a tested column in a parent table and the count of null values in a column in the reference table. The difference must be below defined percentage thresholds. Compares each daily partition (each day of data) between the compared table and the reference table (the source of truth).|[ColumnComparisonNullCountMatchCheckSpec](../../../checks/column/comparisons/null-count-match.md)| | | |
|reference_column|The name of the reference column name in the reference table. It is the column to which the current column is compared to.|string| | | |
|[custom_checks](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


