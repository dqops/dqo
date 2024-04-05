---
title: DQOps YAML file definitions
---
# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## TableProfilingCheckCategoriesSpec
Container of table level checks that are activated on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`result_truncation`</span>|Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the &#x27;one_per_month&#x27; configuration and store only the most recent profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.|*enum*|*store_the_most_recent_result_per_month*<br/>*store_the_most_recent_result_per_week*<br/>*store_the_most_recent_result_per_day*<br/>*store_the_most_recent_result_per_hour*<br/>*store_all_results_without_date_truncation*<br/>| | |
|<span class="no-wrap-code ">[`volume`](./table-profiling-checks.md#tablevolumeprofilingchecksspec)</span>|Configuration of volume data quality checks on a table level.|*[TableVolumeProfilingChecksSpec](./table-profiling-checks.md#tablevolumeprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`timeliness`](./table-profiling-checks.md#tabletimelinessprofilingchecksspec)</span>|Configuration of timeliness checks on a table level. Timeliness checks detect anomalies like rapid row count changes.|*[TableTimelinessProfilingChecksSpec](./table-profiling-checks.md#tabletimelinessprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`accuracy`](./table-profiling-checks.md#tableaccuracyprofilingchecksspec)</span>|Configuration of accuracy checks on a table level. Accuracy checks compare the tested table with another reference table.|*[TableAccuracyProfilingChecksSpec](./table-profiling-checks.md#tableaccuracyprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`custom_sql`](./table-profiling-checks.md#tablecustomsqlprofilingchecksspec)</span>|Configuration of data quality checks that are evaluating custom SQL conditions and aggregated expressions.|*[TableCustomSqlProfilingChecksSpec](./table-profiling-checks.md#tablecustomsqlprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`availability`](./table-profiling-checks.md#tableavailabilityprofilingchecksspec)</span>|Configuration of the table availability data quality checks on a table level.|*[TableAvailabilityProfilingChecksSpec](./table-profiling-checks.md#tableavailabilityprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`schema`](./table-profiling-checks.md#tableschemaprofilingchecksspec)</span>|Configuration of schema (column count and schema) data quality checks on a table level.|*[TableSchemaProfilingChecksSpec](./table-profiling-checks.md#tableschemaprofilingchecksspec)*| | | |
|<span class="no-wrap-code ">[`comparisons`](./table-profiling-checks.md#tablecomparisonprofilingchecksspecmap)</span>|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|*[TableComparisonProfilingChecksSpecMap](./table-profiling-checks.md#tablecomparisonprofilingchecksspecmap)*| | | |
|<span class="no-wrap-code ">[`custom`](./table-profiling-checks.md#customcheckspecmap)</span>|Dictionary of custom checks. The keys are check names within this category.|*[CustomCheckSpecMap](./table-profiling-checks.md#customcheckspecmap)*| | | |









___


## TableVolumeProfilingChecksSpec
Container of built-in preconfigured volume data quality checks on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_row_count`](../../../checks/table/volume/row-count.md)</span>|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which ensures that the table is not empty.|*[TableRowCountCheckSpec](../../../checks/table/volume/row-count.md)*| | | |
|<span class="no-wrap-code ">[`profile_row_count_anomaly`](../../../checks/table/volume/row-count-anomaly.md)</span>|Detects when the row count has changed too much since the previous day. It uses time series anomaly detection to find the biggest volume changes during the last 90 days.|*[TableRowCountAnomalyDifferencingCheckSpec](../../../checks/table/volume/row-count-anomaly.md)*| | | |
|<span class="no-wrap-code ">[`profile_row_count_change`](../../../checks/table/volume/row-count-change.md)</span>|Detects when the volume&#x27;s (row count) change since the last known row count exceeds the maximum accepted change percentage.|*[TableRowCountChangeCheckSpec](../../../checks/table/volume/row-count-change.md)*| | | |
|<span class="no-wrap-code ">[`profile_row_count_change_1_day`](../../../checks/table/volume/row-count-change-1-day.md)</span>|Detects when the volume&#x27;s change (increase or decrease of the row count) since the previous day exceeds the maximum accepted change percentage.|*[TableRowCountChange1DayCheckSpec](../../../checks/table/volume/row-count-change-1-day.md)*| | | |
|<span class="no-wrap-code ">[`profile_row_count_change_7_days`](../../../checks/table/volume/row-count-change-7-days.md)</span>|This check verifies that the percentage of change in the table&#x27;s volume (row count) since seven days ago is below the maximum accepted percentage. Verifying a volume change since a value a week ago overcomes the effect of weekly seasonability. |*[TableRowCountChange7DaysCheckSpec](../../../checks/table/volume/row-count-change-7-days.md)*| | | |
|<span class="no-wrap-code ">[`profile_row_count_change_30_days`](../../../checks/table/volume/row-count-change-30-days.md)</span>|This check verifies that the percentage of change in the table&#x27;s volume (row count) since thirty days ago is below the maximum accepted percentage. Comparing the current row count to a value 30 days ago overcomes the effect of monthly seasonability.|*[TableRowCountChange30DaysCheckSpec](../../../checks/table/volume/row-count-change-30-days.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## CustomCategoryCheckSpecMap
Dictionary of custom checks indexed by a check name that are configured on a category.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___


## TableTimelinessProfilingChecksSpec
Container of timeliness data quality checks on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_data_freshness`](../../../checks/table/timeliness/data-freshness.md)</span>|Calculates the number of days since the most recent event timestamp (freshness)|*[TableDataFreshnessCheckSpec](../../../checks/table/timeliness/data-freshness.md)*| | | |
|<span class="no-wrap-code ">[`profile_data_staleness`](../../../checks/table/timeliness/data-staleness.md)</span>|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|*[TableDataStalenessCheckSpec](../../../checks/table/timeliness/data-staleness.md)*| | | |
|<span class="no-wrap-code ">[`profile_data_ingestion_delay`](../../../checks/table/timeliness/data-ingestion-delay.md)</span>|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|*[TableDataIngestionDelayCheckSpec](../../../checks/table/timeliness/data-ingestion-delay.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableAccuracyProfilingChecksSpec
Container of built-in preconfigured accuracy data quality checks on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_total_row_count_match_percent`](../../../checks/table/accuracy/total-row-count-match-percent.md)</span>|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|*[TableAccuracyTotalRowCountMatchPercentCheckSpec](../../../checks/table/accuracy/total-row-count-match-percent.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableCustomSqlProfilingChecksSpec
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_sql_condition_failed_on_table`](../../../checks/table/custom_sql/sql-condition-failed-on-table.md)</span>|Verifies that a minimum percentage of rows passed a custom SQL condition (expression). Reference the current table by using tokens, for example: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;.|*[TableSqlConditionFailedCheckSpec](../../../checks/table/custom_sql/sql-condition-failed-on-table.md)*| | | |
|<span class="no-wrap-code ">[`profile_sql_condition_passed_percent_on_table`](../../../checks/table/custom_sql/sql-condition-passed-percent-on-table.md)</span>|Verifies that a custom SQL expression is met for each row. Counts the number of rows where the expression is not satisfied, and raises an issue if too many failures were detected. This check is used also to compare values between columns: &#x60;{alias}.col_price &gt; {alias}.col_tax&#x60;.|*[TableSqlConditionPassedPercentCheckSpec](../../../checks/table/custom_sql/sql-condition-passed-percent-on-table.md)*| | | |
|<span class="no-wrap-code ">[`profile_sql_aggregate_expression_on_table`](../../../checks/table/custom_sql/sql-aggregate-expression-on-table.md)</span>|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the expected range.|*[TableSqlAggregateExpressionCheckSpec](../../../checks/table/custom_sql/sql-aggregate-expression-on-table.md)*| | | |
|<span class="no-wrap-code ">[`profile_import_custom_result_on_table`](../../../checks/table/custom_sql/import-custom-result-on-table.md)</span>|Runs a custom query that retrieves a result of a data quality check performed in the data engineering, whose result (the severity level) is pulled from a separate table.|*[TableSqlImportCustomResultCheckSpec](../../../checks/table/custom_sql/import-custom-result-on-table.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableAvailabilityProfilingChecksSpec
Container of built-in preconfigured table availability data quality checks on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_table_availability`](../../../checks/table/availability/table-availability.md)</span>|Verifies availability of a table in a monitored database using a simple query.|*[TableAvailabilityCheckSpec](../../../checks/table/availability/table-availability.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableSchemaProfilingChecksSpec
Container of built-in preconfigured volume data quality checks on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_column_count`](../../../checks/table/schema/column-count.md)</span>|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|*[TableSchemaColumnCountCheckSpec](../../../checks/table/schema/column-count.md)*| | | |
|<span class="no-wrap-code ">[`profile_column_count_changed`](../../../checks/table/schema/column-count-changed.md)</span>|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|*[TableSchemaColumnCountChangedCheckSpec](../../../checks/table/schema/column-count-changed.md)*| | | |
|<span class="no-wrap-code ">[`profile_column_list_changed`](../../../checks/table/schema/column-list-changed.md)</span>|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|*[TableSchemaColumnListChangedCheckSpec](../../../checks/table/schema/column-list-changed.md)*| | | |
|<span class="no-wrap-code ">[`profile_column_list_or_order_changed`](../../../checks/table/schema/column-list-or-order-changed.md)</span>|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|*[TableSchemaColumnListOrOrderChangedCheckSpec](../../../checks/table/schema/column-list-or-order-changed.md)*| | | |
|<span class="no-wrap-code ">[`profile_column_types_changed`](../../../checks/table/schema/column-types-changed.md)</span>|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|*[TableSchemaColumnTypesChangedCheckSpec](../../../checks/table/schema/column-types-changed.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## TableComparisonProfilingChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`self`</span>||*Dict[string, [TableComparisonProfilingChecksSpec](./table-profiling-checks.md#tablecomparisonprofilingchecksspec)]*| | | |









___


## TableComparisonProfilingChecksSpec
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">[`profile_row_count_match`](../../../checks/table/comparisons/row-count-match.md)</span>|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause.|*[TableComparisonRowCountMatchCheckSpec](../../../checks/table/comparisons/row-count-match.md)*| | | |
|<span class="no-wrap-code ">[`profile_column_count_match`](../../../checks/table/comparisons/column-count-match.md)</span>|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping.|*[TableComparisonColumnCountMatchCheckSpec](../../../checks/table/comparisons/column-count-match.md)*| | | |
|<span class="no-wrap-code ">[`custom_checks`](./table-profiling-checks.md#customcategorycheckspecmap)</span>|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|*[CustomCategoryCheckSpecMap](./table-profiling-checks.md#customcategorycheckspecmap)*| | | |









___


## CustomCheckSpecMap
Dictionary of custom checks indexed by a check name.













___


## CustomCheckSpec
Custom check specification. This check is usable only when there is a matching custom check definition that identifies
 the sensor definition and the rule definition.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`sensor_name`</span>|Optional custom sensor name. It is a folder name inside the user&#x27;s home &#x27;sensors&#x27; folder or the DQOps Home (DQOps distribution) home/sensors folder. Sample sensor name: table/volume/row_count. When this value is set, it overrides the default sensor definition defined for the named check definition.|*string*| | | |
|<span class="no-wrap-code ">`rule_name`</span>|Optional custom rule name. It is a path to a custom rule python module that starts at the user&#x27;s home &#x27;rules&#x27; folder. The path should not end with the .py file extension. Sample rule: myrules/my_custom_rule. When this value is set, it overrides the default rule definition defined for the named check definition.|*string*| | | |
|<span class="no-wrap-code ">[`parameters`](./table-profiling-checks.md#customsensorparametersspec)</span>|Custom sensor parameters|*[CustomSensorParametersSpec](./table-profiling-checks.md#customsensorparametersspec)*| | | |
|<span class="no-wrap-code ">[`warning`](./table-profiling-checks.md#customruleparametersspec)</span>|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|*[CustomRuleParametersSpec](./table-profiling-checks.md#customruleparametersspec)*| | | |
|<span class="no-wrap-code ">[`error`](./table-profiling-checks.md#customruleparametersspec)</span>|Default alerting thresholdthat raises a data quality issue at an error severity level|*[CustomRuleParametersSpec](./table-profiling-checks.md#customruleparametersspec)*| | | |
|<span class="no-wrap-code ">[`fatal`](./table-profiling-checks.md#customruleparametersspec)</span>|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|*[CustomRuleParametersSpec](./table-profiling-checks.md#customruleparametersspec)*| | | |
|<span class="no-wrap-code ">[`schedule_override`](./table-profiling-checks.md#monitoringschedulespec)</span>|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|*[MonitoringScheduleSpec](./table-profiling-checks.md#monitoringschedulespec)*| | | |
|<span class="no-wrap-code ">[`comments`](./table-profiling-checks.md#commentslistspec)</span>|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|*[CommentsListSpec](./table-profiling-checks.md#commentslistspec)*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|*boolean*| | | |
|<span class="no-wrap-code ">`exclude_from_kpi`</span>|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|*boolean*| | | |
|<span class="no-wrap-code ">`include_in_sla`</span>|Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.|*boolean*| | | |
|<span class="no-wrap-code ">`quality_dimension`</span>|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|*string*| | | |
|<span class="no-wrap-code ">`display_name`</span>|Data quality check display name that can be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|*string*| | | |
|<span class="no-wrap-code ">`data_grouping`</span>|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|*string*| | | |









___


## CustomSensorParametersSpec
Custom sensor parameters for custom checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`filter`</span>|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|*string*| | | |









___


## CustomRuleParametersSpec
Custom data quality rule.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___


## MonitoringScheduleSpec
Monitoring job schedule specification.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`cron_expression`</span>|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|*string*| | | |
|<span class="no-wrap-code ">`disabled`</span>|Disables the schedule. When the value of this &#x27;disable&#x27; field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|*boolean*| | | |









___


## CommentsListSpec
List of comments.



___


## CommentSpec
Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|<span class="no-wrap-code ">`date`</span>|Comment date and time|*datetime*| | | |
|<span class="no-wrap-code ">`comment_by`</span>|Commented by|*string*| | | |
|<span class="no-wrap-code ">`comment`</span>|Comment text|*string*| | | |









___


