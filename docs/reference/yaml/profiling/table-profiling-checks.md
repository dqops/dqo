# DQOps YAML file definitions
The definition of YAML files used by DQOps to configure the data sources, monitored tables, and the configuration of activated data quality checks.


## TableAccuracyProfilingChecksSpec
Container of built-in preconfigured accuracy data quality checks on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_total_row_count_match_percent](../../../checks/table/accuracy/total-row-count-match-percent.md)|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|[TableAccuracyTotalRowCountMatchPercentCheckSpec](../../../checks/table/accuracy/total-row-count-match-percent.md)| | | |
|[custom_checks](./table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](./table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## TableTimelinessProfilingChecksSpec
Container of timeliness data quality checks on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_data_freshness](../../../checks/table/timeliness/data-freshness.md)|Calculates the number of days since the most recent event timestamp (freshness)|[TableDataFreshnessCheckSpec](../../../checks/table/timeliness/data-freshness.md)| | | |
|[profile_data_staleness](../../../checks/table/timeliness/data-staleness.md)|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|[TableDataStalenessCheckSpec](../../../checks/table/timeliness/data-staleness.md)| | | |
|[profile_data_ingestion_delay](../../../checks/table/timeliness/data-ingestion-delay.md)|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](../../../checks/table/timeliness/data-ingestion-delay.md)| | | |
|[custom_checks](./table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](./table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## CustomCategoryCheckSpecMap
Dictionary of custom checks indexed by a check name that are configured on a category.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___


## TableVolumeProfilingChecksSpec
Container of built-in preconfigured volume data quality checks on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_row_count](../../../checks/table/volume/row-count.md)|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|[TableRowCountCheckSpec](../../../checks/table/volume/row-count.md)| | | |
|[profile_row_count_anomaly](../../../checks/table/volume/row-count-anomaly.md)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|[TableRowCountAnomalyDifferencingCheckSpec](../../../checks/table/volume/row-count-anomaly.md)| | | |
|[profile_row_count_change](../../../checks/table/volume/row-count-change.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|[TableRowCountChangeCheckSpec](../../../checks/table/volume/row-count-change.md)| | | |
|[profile_row_count_change_1_day](../../../checks/table/volume/row-count-change-1-day.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|[TableRowCountChange1DayCheckSpec](../../../checks/table/volume/row-count-change-1-day.md)| | | |
|[profile_row_count_change_7_days](../../../checks/table/volume/row-count-change-7-days.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|[TableRowCountChange7DaysCheckSpec](../../../checks/table/volume/row-count-change-7-days.md)| | | |
|[profile_row_count_change_30_days](../../../checks/table/volume/row-count-change-30-days.md)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|[TableRowCountChange30DaysCheckSpec](../../../checks/table/volume/row-count-change-30-days.md)| | | |
|[custom_checks](./table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](./table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## TableComparisonProfilingChecksSpec
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_row_count_match](../../../checks/table/comparisons/row-count-match.md)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause.|[TableComparisonRowCountMatchCheckSpec](../../../checks/table/comparisons/row-count-match.md)| | | |
|[profile_column_count_match](../../../checks/table/comparisons/column-count-match.md)|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping.|[TableComparisonColumnCountMatchCheckSpec](../../../checks/table/comparisons/column-count-match.md)| | | |
|[custom_checks](./table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](./table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## CustomCheckSpecMap
Dictionary of custom checks indexed by a check name.













___


## MonitoringScheduleSpec
Monitoring job schedule specification.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|cron_expression|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|string| | | |
|disabled|Disables the schedule. When the value of this &#x27;disable&#x27; field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|boolean| | | |









___


## CommentsListSpec
List of comments.



___


## TableSchemaProfilingChecksSpec
Container of built-in preconfigured volume data quality checks on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_column_count](../../../checks/table/schema/column-count.md)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|[TableSchemaColumnCountCheckSpec](../../../checks/table/schema/column-count.md)| | | |
|[profile_column_count_changed](../../../checks/table/schema/column-count-changed.md)|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|[TableSchemaColumnCountChangedCheckSpec](../../../checks/table/schema/column-count-changed.md)| | | |
|[profile_column_list_changed](../../../checks/table/schema/column-list-changed.md)|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|[TableSchemaColumnListChangedCheckSpec](../../../checks/table/schema/column-list-changed.md)| | | |
|[profile_column_list_or_order_changed](../../../checks/table/schema/column-list-or-order-changed.md)|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|[TableSchemaColumnListOrOrderChangedCheckSpec](../../../checks/table/schema/column-list-or-order-changed.md)| | | |
|[profile_column_types_changed](../../../checks/table/schema/column-types-changed.md)|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|[TableSchemaColumnTypesChangedCheckSpec](../../../checks/table/schema/column-types-changed.md)| | | |
|[custom_checks](./table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](./table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## TableComparisonProfilingChecksSpecMap
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [TableComparisonProfilingChecksSpec](./table-profiling-checks.md#TableComparisonProfilingChecksSpec)]| | | |









___


## TableCustomSqlProfilingChecksSpec
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_sql_condition_passed_percent_on_table](../../../checks/table/custom_sql/sql-condition-passed-percent-on-table.md)|Verifies that a set percentage of rows passed a custom SQL condition (expression).|[TableSqlConditionPassedPercentCheckSpec](../../../checks/table/custom_sql/sql-condition-passed-percent-on-table.md)| | | |
|[profile_sql_condition_failed_count_on_table](../../../checks/table/custom_sql/sql-condition-failed-count-on-table.md)|Verifies that a set number of rows failed a custom SQL condition (expression).|[TableSqlConditionFailedCountCheckSpec](../../../checks/table/custom_sql/sql-condition-failed-count-on-table.md)| | | |
|[profile_sql_aggregate_expression_on_table](../../../checks/table/custom_sql/sql-aggregate-expression-on-table.md)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|[TableSqlAggregateExpressionCheckSpec](../../../checks/table/custom_sql/sql-aggregate-expression-on-table.md)| | | |
|[custom_checks](./table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](./table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## TableAvailabilityProfilingChecksSpec
Container of built-in preconfigured table availability data quality checks on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_table_availability](../../../checks/table/availability/table-availability.md)|Verifies availability of a table in a monitored database using a simple query.|[TableAvailabilityCheckSpec](../../../checks/table/availability/table-availability.md)| | | |
|[custom_checks](./table-profiling-checks.md#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](./table-profiling-checks.md#CustomCategoryCheckSpecMap)| | | |









___


## CustomSensorParametersSpec
Custom sensor parameters for custom checks.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___


## TableProfilingCheckCategoriesSpec
Container of table level checks that are activated on a table level.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|result_truncation|Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the &#x27;one_per_month&#x27; configuration and store only the most recent profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.|enum|one_per_week<br/>all_results<br/>one_per_hour<br/>one_per_month<br/>one_per_day<br/>| | |
|[volume](./table-profiling-checks.md#TableVolumeProfilingChecksSpec)|Configuration of volume data quality checks on a table level.|[TableVolumeProfilingChecksSpec](./table-profiling-checks.md#TableVolumeProfilingChecksSpec)| | | |
|[timeliness](./table-profiling-checks.md#TableTimelinessProfilingChecksSpec)|Configuration of timeliness checks on a table level. Timeliness checks detect anomalies like rapid row count changes.|[TableTimelinessProfilingChecksSpec](./table-profiling-checks.md#TableTimelinessProfilingChecksSpec)| | | |
|[accuracy](./table-profiling-checks.md#TableAccuracyProfilingChecksSpec)|Configuration of accuracy checks on a table level. Accuracy checks compare the tested table with another reference table.|[TableAccuracyProfilingChecksSpec](./table-profiling-checks.md#TableAccuracyProfilingChecksSpec)| | | |
|[custom_sql](./table-profiling-checks.md#TableCustomSqlProfilingChecksSpec)|Configuration of data quality checks that are evaluating custom SQL conditions and aggregated expressions.|[TableCustomSqlProfilingChecksSpec](./table-profiling-checks.md#TableCustomSqlProfilingChecksSpec)| | | |
|[availability](./table-profiling-checks.md#TableAvailabilityProfilingChecksSpec)|Configuration of the table availability data quality checks on a table level.|[TableAvailabilityProfilingChecksSpec](./table-profiling-checks.md#TableAvailabilityProfilingChecksSpec)| | | |
|[schema](./table-profiling-checks.md#TableSchemaProfilingChecksSpec)|Configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaProfilingChecksSpec](./table-profiling-checks.md#TableSchemaProfilingChecksSpec)| | | |
|[comparisons](./table-profiling-checks.md#TableComparisonProfilingChecksSpecMap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonProfilingChecksSpecMap](./table-profiling-checks.md#TableComparisonProfilingChecksSpecMap)| | | |
|[custom](./table-profiling-checks.md#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](./table-profiling-checks.md#CustomCheckSpecMap)| | | |









___


## CustomCheckSpec
Custom check specification. This check is usable only when there is a matching custom check definition that identifies
 the sensor definition and the rule definition.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sensor_name|Optional custom sensor name. It is a folder name inside the user&#x27;s home &#x27;sensors&#x27; folder or the DQOps Home (DQOps distribution) home/sensors folder. Sample sensor name: table/volume/row_count. When this value is set, it overrides the default sensor definition defined for the named check definition.|string| | | |
|rule_name|Optional custom rule name. It is a path to a custom rule python module that starts at the user&#x27;s home &#x27;rules&#x27; folder. The path should not end with the .py file extension. Sample rule: myrules/my_custom_rule. When this value is set, it overrides the default rule definition defined for the named check definition.|string| | | |
|[parameters](./table-profiling-checks.md#CustomSensorParametersSpec)|Custom sensor parameters|[CustomSensorParametersSpec](./table-profiling-checks.md#CustomSensorParametersSpec)| | | |
|[warning](./table-profiling-checks.md#CustomRuleParametersSpec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[CustomRuleParametersSpec](./table-profiling-checks.md#CustomRuleParametersSpec)| | | |
|[error](./table-profiling-checks.md#CustomRuleParametersSpec)|Default alerting threshold for a row count that raises a data quality error (alert)|[CustomRuleParametersSpec](./table-profiling-checks.md#CustomRuleParametersSpec)| | | |
|[fatal](./table-profiling-checks.md#CustomRuleParametersSpec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[CustomRuleParametersSpec](./table-profiling-checks.md#CustomRuleParametersSpec)| | | |
|[schedule_override](./table-profiling-checks.md#MonitoringScheduleSpec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](./table-profiling-checks.md#MonitoringScheduleSpec)| | | |
|[comments](./table-profiling-checks.md#CommentsListSpec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](./table-profiling-checks.md#CommentsListSpec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA (Data Contract). The data quality SLA is a set of critical data quality checks that must always pass and are considered as a Data Contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___


## CustomRuleParametersSpec
Custom data quality rule.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___


## CommentSpec
Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.









The structure of this object is described below

|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|date|Comment date and time|datetime| | | |
|comment_by|Commented by|string| | | |
|comment|Comment text|string| | | |









___


