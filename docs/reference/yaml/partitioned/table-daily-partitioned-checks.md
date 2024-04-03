# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## TableDailyPartitionedCheckCategoriesSpec
Container of table level daily partitioned checks. Contains categories of daily partitioned checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`volume`](./table-daily-partitioned-checks.md#tablevolumedailypartitionedchecksspec)</span>|Volume daily partitioned data quality checks that verify the quality of every day of data separately|*[TableVolumeDailyPartitionedChecksSpec](./table-daily-partitioned-checks.md#tablevolumedailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`timeliness`](./table-daily-partitioned-checks.md#tabletimelinessdailypartitionedchecksspec)</span>|Daily partitioned timeliness checks|*[TableTimelinessDailyPartitionedChecksSpec](./table-daily-partitioned-checks.md#tabletimelinessdailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`custom_sql`](./table-daily-partitioned-checks.md#tablecustomsqldailypartitionedchecksspec)</span>|Custom SQL daily partitioned data quality checks that verify the quality of every day of data separately|*[TableCustomSqlDailyPartitionedChecksSpec](./table-daily-partitioned-checks.md#tablecustomsqldailypartitionedchecksspec)*| | | |
|<span class="no-wrap-code ">[`comparisons`](./table-daily-partitioned-checks.md#tablecomparisondailypartitionedchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[TableComparisonDailyPartitionedChecksSpecMap](./table-daily-partitioned-checks.md#tablecomparisondailypartitionedchecksspecmap)*| | | |
|<span class="no-wrap-code ">[`custom`](../profiling/table-profiling-checks.md#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](../profiling/table-profiling-checks.md#customcheckspecmap)*| | | |









___


## TableVolumeDailyPartitionedChecksSpec
Container of table level date partitioned volume data quality checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_row_count`](../../../checks/table/volume/row-count.md)</span>|Verifies that each daily partition in the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which ensures that the partition is not empty.|*[TableRowCountCheckSpec](../../../checks/table/volume/row-count.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_row_count_anomaly`](./table-daily-partitioned-checks.md#tablerowcountanomalystationarypartitioncheckspec)</span>|Detects outstanding partitions whose volume (the row count) differs too much from the average daily partition size. It uses time series anomaly detection to find the outliers in the partition volume during the last 90 days.|*[TableRowCountAnomalyStationaryPartitionCheckSpec](./table-daily-partitioned-checks.md#tablerowcountanomalystationarypartitioncheckspec)*| | | |
|<span class="no-wrap-code ">[`daily_partition_row_count_change`](../../../checks/table/volume/row-count-change.md)</span>|Detects when the partition&#x27;s volume (row count) change between the current daily partition and the previous partition exceeds the maximum accepted change percentage.|*[TableRowCountChangeCheckSpec](../../../checks/table/volume/row-count-change.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_row_count_change_1_day`](../../../checks/table/volume/row-count-change-1-day.md)</span>|Detects when the partition volume change (increase or decrease of the row count) since yesterday&#x27;s daily partition exceeds the maximum accepted change percentage. |*[TableRowCountChange1DayCheckSpec](../../../checks/table/volume/row-count-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_row_count_change_7_days`](../../../checks/table/volume/row-count-change-7-days.md)</span>|This check verifies that the percentage of change in the partition&#x27;s volume (row count) since seven days ago is below the maximum accepted percentage. Verifying a volume change since a value a week ago overcomes the effect of weekly seasonability.|*[TableRowCountChange7DaysCheckSpec](../../../checks/table/volume/row-count-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_row_count_change_30_days`](../../../checks/table/volume/row-count-change-30-days.md)</span>|This check verifies that the percentage of change in the partition&#x27;s volume (row count) since thirty days ago is below the maximum accepted percentage. Comparing the current row count to a value 30 days ago overcomes the effect of monthly seasonability.|*[TableRowCountChange30DaysCheckSpec](../../../checks/table/volume/row-count-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableRowCountAnomalyStationaryPartitionCheckSpec
This check detects anomalies in the day-to-day changes to the table volume (the row count).
 It captures the row count for each day and compares the row count change (increase or decrease) since the previous day.
 This check raises a data quality issue when the change is in the top **anomaly_percent** percentage of the biggest day-to-day changes.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`parameters`](../../sensors/table/volume-table-sensors.md#row-count)</span>|Data quality check parameters|*[TableVolumeRowCountSensorParametersSpec](../../sensors/table/volume-table-sensors.md#row-count)*| | | |
|<span class="no-wrap-code ">[`warning`](./table-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulewarning1pctparametersspec)</span>|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|*[AnomalyStationaryPercentileMovingAverageRuleWarning1PctParametersSpec](./table-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulewarning1pctparametersspec)*| | | |
|<span class="no-wrap-code ">[`error`](../../rules/Percentile.md#anomaly-stationary-percentile-moving-average)</span>|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|*[AnomalyStationaryPercentileMovingAverageRuleError05PctParametersSpec](../../rules/Percentile.md#anomaly-stationary-percentile-moving-average)*| | | |
|<span class="no-wrap-code ">[`fatal`](./table-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulefatal01pctparametersspec)</span>|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|*[AnomalyStationaryPercentileMovingAverageRuleFatal01PctParametersSpec](./table-daily-partitioned-checks.md#anomalystationarypercentilemovingaveragerulefatal01pctparametersspec)*| | | |
|<span class="no-wrap-code ">[`schedule_override`](../profiling/table-profiling-checks.md#monitoringschedulespec)</span>|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|*[MonitoringScheduleSpec](../profiling/table-profiling-checks.md#monitoringschedulespec)*| | | |
|<span class="no-wrap-code ">[`comments`](../profiling/table-profiling-checks.md#commentslistspec)</span>|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|*[CommentsListSpec](../profiling/table-profiling-checks.md#commentslistspec)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|*boolean*| | | |
|<span class="no-wrap-code ">`exclude_from_kpi`</span>|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|*boolean*| | | |
|<span class="no-wrap-code ">`include_in_sla`</span>|Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.|*boolean*| | | |
|<span class="no-wrap-code ">`quality_dimension`</span>|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|*string*| | | |
|<span class="no-wrap-code ">`display_name`</span>|Data quality check display name that can be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|*string*| | | |
|<span class="no-wrap-code ">`data_grouping`</span>|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|*string*| | | |









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









___


## TableTimelinessDailyPartitionedChecksSpec
Container of table level date partitioned timeliness data quality checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_data_ingestion_delay`](../../../checks/table/timeliness/data-ingestion-delay.md)</span>|Daily partitioned check calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|*[TableDataIngestionDelayCheckSpec](../../../checks/table/timeliness/data-ingestion-delay.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_reload_lag`](../../../checks/table/timeliness/reload-lag.md)</span>|Daily partitioned check calculating the longest time a row waited to be loaded, it is the maximum difference in days between the ingestion timestamp and the event timestamp column on any row in the monitored partition|*[TablePartitionReloadLagCheckSpec](../../../checks/table/timeliness/reload-lag.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableCustomSqlDailyPartitionedChecksSpec
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_sql_condition_failed_on_table`](../../../checks/table/custom_sql/sql-condition-failed-on-table.md)</span>|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|*[TableSqlConditionFailedCheckSpec](../../../checks/table/custom_sql/sql-condition-failed-on-table.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_sql_condition_passed_percent_on_table`](../../../checks/table/custom_sql/sql-condition-passed-percent-on-table.md)</span>|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;. Stores a separate data quality check result for each daily partition.|*[TableSqlConditionPassedPercentCheckSpec](../../../checks/table/custom_sql/sql-condition-passed-percent-on-table.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_sql_aggregate_expression_on_table`](../../../checks/table/custom_sql/sql-aggregate-expression-on-table.md)</span>|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range. Stores a separate data quality check result for each daily partition.|*[TableSqlAggregateExpressionCheckSpec](../../../checks/table/custom_sql/sql-aggregate-expression-on-table.md)*| | | |
|<span class="no-wrap-code ">[`daily_partition_import_custom_result_on_table`](../../../checks/table/custom_sql/import-custom-result-on-table.md)</span>|Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.|*[TableSqlImportCustomResultCheckSpec](../../../checks/table/custom_sql/import-custom-result-on-table.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableComparisonDailyPartitionedChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.
 Contains the daily partitioned comparison checks for each configured reference table.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`self`</span>||*Dict[string, [TableComparisonDailyPartitionedChecksSpec](./table-daily-partitioned-checks.md#tablecomparisondailypartitionedchecksspec)]*| | | |









___


## TableComparisonDailyPartitionedChecksSpec
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.
 Contains the daily partitioned comparison checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`daily_partition_row_count_match`](../../../checks/table/comparisons/row-count-match.md)</span>|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause on the time period (the daily partition) and all other data grouping columns. Stores the most recent captured value for each daily partition that was analyzed.|*[TableComparisonRowCountMatchCheckSpec](../../../checks/table/comparisons/row-count-match.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](../profiling/table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](../profiling/table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


