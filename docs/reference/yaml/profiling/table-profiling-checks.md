
## CustomCheckSpec  
Custom check specification. This check is usable only when there is a matching custom check definition that identifies
 the sensor definition and the rule definition.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sensor_name|Optional custom sensor name. It is a folder name inside the user&#x27;s home &#x27;sensors&#x27; folder or the DQOps Home (DQOps distribution) home/sensors folder. Sample sensor name: table/volume/row_count. When this value is set, it overrides the default sensor definition defined for the named check definition.|string| | | |
|rule_name|Optional custom rule name. It is a path to a custom rule python module that starts at the user&#x27;s home &#x27;rules&#x27; folder. The path should not end with the .py file extension. Sample rule: myrules/my_custom_rule. When this value is set, it overrides the default rule definition defined for the named check definition.|string| | | |
|[parameters](../table-profiling-checks/#CustomSensorParametersSpec)|Custom sensor parameters|[CustomSensorParametersSpec](../table-profiling-checks/#CustomSensorParametersSpec)| | | |
|[warning](../table-profiling-checks/#CustomRuleParametersSpec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[CustomRuleParametersSpec](../table-profiling-checks/#CustomRuleParametersSpec)| | | |
|[error](../table-profiling-checks/#CustomRuleParametersSpec)|Default alerting threshold for a row count that raises a data quality error (alert)|[CustomRuleParametersSpec](../table-profiling-checks/#CustomRuleParametersSpec)| | | |
|[fatal](../table-profiling-checks/#CustomRuleParametersSpec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[CustomRuleParametersSpec](../table-profiling-checks/#CustomRuleParametersSpec)| | | |
|[schedule_override](../table-profiling-checks/#MonitoringScheduleSpec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[MonitoringScheduleSpec](../table-profiling-checks/#MonitoringScheduleSpec)| | | |
|[comments](../table-profiling-checks/#CommentsListSpec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](../table-profiling-checks/#CommentsListSpec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and monitorings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___  

## MonitoringScheduleSpec  
Monitoring job schedule specification.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|cron_expression|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|string| | | |
|disabled|Disables the schedule. When the value of this &#x27;disable&#x27; field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|boolean| | | |









___  

## CommentSpec  
Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|date|Comment date and time|datetime| | | |
|comment_by|Commented by|string| | | |
|comment|Comment text|string| | | |









___  

## CustomCategoryCheckSpecMap  
Dictionary of custom checks indexed by a check name that are configured on a category.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___  

## TableTimelinessProfilingChecksSpec  
Container of timeliness data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_data_freshness](../../../../checks/table/timeliness/data-freshness/)|Calculates the number of days since the most recent event timestamp (freshness)|[TableDataFreshnessCheckSpec](../../../../checks/table/timeliness/data-freshness/)| | | |
|[profile_data_staleness](../../../../checks/table/timeliness/data-staleness/)|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|[TableDataStalenessCheckSpec](../../../../checks/table/timeliness/data-staleness/)| | | |
|[profile_data_ingestion_delay](../../../../checks/table/timeliness/data-ingestion-delay/)|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](../../../../checks/table/timeliness/data-ingestion-delay/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## CustomSensorParametersSpec  
Custom sensor parameters for custom checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## TableComparisonProfilingChecksSpec  
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_row_count_match](../../../../checks/table/comparisons/row-count-match/)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause.|[TableComparisonRowCountMatchCheckSpec](../../../../checks/table/comparisons/row-count-match/)| | | |
|[profile_column_count_match](../../../../checks/table/comparisons/column-count-match/)|Verifies that the column count of the tested (parent) table matches the column count of the reference table. Only one comparison result is returned, without data grouping.|[TableComparisonColumnCountMatchCheckSpec](../../../../checks/table/comparisons/column-count-match/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## TableProfilingCheckCategoriesSpec  
Container of table level checks that are activated on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|result_truncation|Defines how many profiling checks results are stored for the table monthly. By default, DQOps will use the &#x27;one_per_month&#x27; configuration and store only the most recent profiling checks result executed during the month. By changing this value, it is possible to store one value per day or even store all profiling checks results.|enum|one_per_week<br/>all_results<br/>one_per_hour<br/>one_per_month<br/>one_per_day<br/>| | |
|[volume](../table-profiling-checks/#TableVolumeProfilingChecksSpec)|Configuration of volume data quality checks on a table level.|[TableVolumeProfilingChecksSpec](../table-profiling-checks/#TableVolumeProfilingChecksSpec)| | | |
|[timeliness](../table-profiling-checks/#TableTimelinessProfilingChecksSpec)|Configuration of timeliness checks on a table level. Timeliness checks detect anomalies like rapid row count changes.|[TableTimelinessProfilingChecksSpec](../table-profiling-checks/#TableTimelinessProfilingChecksSpec)| | | |
|[accuracy](../table-profiling-checks/#TableAccuracyProfilingChecksSpec)|Configuration of accuracy checks on a table level. Accuracy checks compare the tested table with another reference table.|[TableAccuracyProfilingChecksSpec](../table-profiling-checks/#TableAccuracyProfilingChecksSpec)| | | |
|[sql](../table-profiling-checks/#TableSqlProfilingChecksSpec)|Configuration of data quality checks that are evaluating custom SQL conditions and aggregated expressions.|[TableSqlProfilingChecksSpec](../table-profiling-checks/#TableSqlProfilingChecksSpec)| | | |
|[availability](../table-profiling-checks/#TableAvailabilityProfilingChecksSpec)|Configuration of the table availability data quality checks on a table level.|[TableAvailabilityProfilingChecksSpec](../table-profiling-checks/#TableAvailabilityProfilingChecksSpec)| | | |
|[schema](../table-profiling-checks/#TableSchemaProfilingChecksSpec)|Configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaProfilingChecksSpec](../table-profiling-checks/#TableSchemaProfilingChecksSpec)| | | |
|[comparisons](../table-profiling-checks/#TableComparisonProfilingChecksSpecMap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonProfilingChecksSpecMap](../table-profiling-checks/#TableComparisonProfilingChecksSpecMap)| | | |
|[custom](../table-profiling-checks/#CustomCheckSpecMap)|Dictionary of custom checks. The keys are check names within this category.|[CustomCheckSpecMap](../table-profiling-checks/#CustomCheckSpecMap)| | | |









___  

## CustomCheckSpecMap  
Dictionary of custom checks indexed by a check name.  
  












___  

## TableVolumeProfilingChecksSpec  
Container of built-in preconfigured volume data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_row_count](../../../../checks/table/volume/row-count/)|Verifies that the tested table has at least a minimum accepted number of rows. The default configuration of the warning, error and fatal severity rules verifies a minimum row count of one row, which checks if the table is not empty. When the data grouping is configured, this check will count rows using a GROUP BY clause and verify that each data grouping has an expected minimum number of rows.|[TableRowCountCheckSpec](../../../../checks/table/volume/row-count/)| | | |
|[profile_row_count_anomaly_differencing_30_days](../../../../checks/table/volume/row-count-anomaly-differencing-30-days/)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|[TableAnomalyDifferencingRowCount30DaysCheckSpec](../../../../checks/table/volume/row-count-anomaly-differencing-30-days/)| | | |
|[profile_row_count_anomaly_differencing](../../../../checks/table/volume/row-count-anomaly-differencing/)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|[TableAnomalyDifferencingRowCountCheckSpec](../../../../checks/table/volume/row-count-anomaly-differencing/)| | | |
|[profile_row_count_change](../../../../checks/table/volume/row-count-change/)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|[TableChangeRowCountCheckSpec](../../../../checks/table/volume/row-count-change/)| | | |
|[profile_row_count_change_yesterday](../../../../checks/table/volume/row-count-change-yesterday/)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|[TableChangeRowCountSinceYesterdayCheckSpec](../../../../checks/table/volume/row-count-change-yesterday/)| | | |
|[profile_row_count_change_7_days](../../../../checks/table/volume/row-count-change-7-days/)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|[TableChangeRowCountSince7DaysCheckSpec](../../../../checks/table/volume/row-count-change-7-days/)| | | |
|[profile_row_count_change_30_days](../../../../checks/table/volume/row-count-change-30-days/)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|[TableChangeRowCountSince30DaysCheckSpec](../../../../checks/table/volume/row-count-change-30-days/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## TableSchemaProfilingChecksSpec  
Container of built-in preconfigured volume data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_column_count](../../../../checks/table/schema/column-count/)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|[TableSchemaColumnCountCheckSpec](../../../../checks/table/schema/column-count/)| | | |
|[profile_column_count_changed](../../../../checks/table/schema/column-count-changed/)|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|[TableSchemaColumnCountChangedCheckSpec](../../../../checks/table/schema/column-count-changed/)| | | |
|[profile_column_list_changed](../../../../checks/table/schema/column-list-changed/)|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|[TableSchemaColumnListChangedCheckSpec](../../../../checks/table/schema/column-list-changed/)| | | |
|[profile_column_list_or_order_changed](../../../../checks/table/schema/column-list-or-order-changed/)|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|[TableSchemaColumnListOrOrderChangedCheckSpec](../../../../checks/table/schema/column-list-or-order-changed/)| | | |
|[profile_column_types_changed](../../../../checks/table/schema/column-types-changed/)|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|[TableSchemaColumnTypesChangedCheckSpec](../../../../checks/table/schema/column-types-changed/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## CustomRuleParametersSpec  
Custom data quality rule.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___  

## TableAccuracyProfilingChecksSpec  
Container of built-in preconfigured accuracy data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_total_row_count_match_percent](../../../../checks/table/accuracy/total-row-count-match-percent/)|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|[TableAccuracyTotalRowCountMatchPercentCheckSpec](../../../../checks/table/accuracy/total-row-count-match-percent/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## TableSqlProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_sql_condition_passed_percent_on_table](../../../../checks/table/sql/sql-condition-passed-percent-on-table/)|Verifies that a set percentage of rows passed a custom SQL condition (expression).|[TableSqlConditionPassedPercentCheckSpec](../../../../checks/table/sql/sql-condition-passed-percent-on-table/)| | | |
|[profile_sql_condition_failed_count_on_table](../../../../checks/table/sql/sql-condition-failed-count-on-table/)|Verifies that a set number of rows failed a custom SQL condition (expression).|[TableSqlConditionFailedCountCheckSpec](../../../../checks/table/sql/sql-condition-failed-count-on-table/)| | | |
|[profile_sql_aggregate_expr_table](../../../../checks/table/sql/sql-aggregate-expr-table/)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|[TableSqlAggregateExprCheckSpec](../../../../checks/table/sql/sql-aggregate-expr-table/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

## CommentsListSpec  
List of comments.  
  


___  

## TableComparisonProfilingChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|self||Dict[string, [TableComparisonProfilingChecksSpec](../table-profiling-checks/#TableComparisonProfilingChecksSpec)]| | | |









___  

## TableAvailabilityProfilingChecksSpec  
Container of built-in preconfigured table availability data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profile_table_availability](../../../../checks/table/availability/table-availability/)|Verifies availability of the table in a database using a simple row count.|[TableAvailabilityCheckSpec](../../../../checks/table/availability/table-availability/)| | | |
|[custom_checks](../table-profiling-checks/#CustomCategoryCheckSpecMap)|Dictionary of additional custom checks within this category. The keys are check names defined in the definition section. The sensor parameters and rules should match the type of the configured sensor and rule for the custom check.|[CustomCategoryCheckSpecMap](../table-profiling-checks/#CustomCategoryCheckSpecMap)| | | |









___  

