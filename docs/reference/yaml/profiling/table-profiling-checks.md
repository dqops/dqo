
## CustomRuleParametersSpec  
Custom data quality rule.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___  

## TableAvailabilityProfilingChecksSpec  
Container of built-in preconfigured table availability data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table_availability](\docs\checks\table\availability\table-availability)|Verifies availability of the table in a database using a simple row count.|[TableAvailabilityCheckSpec](\docs\checks\table\availability\table-availability)| | | |









___  

## TableProfilingCheckCategoriesSpec  
Container of table level checks that are activated on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[volume](#tablevolumeprofilingchecksspec)|Configuration of volume data quality checks on a table level.|[TableVolumeProfilingChecksSpec](#tablevolumeprofilingchecksspec)| | | |
|[timeliness](#tabletimelinessprofilingchecksspec)|Configuration of timeliness checks on a table level. Timeliness checks detect anomalies like rapid row count changes.|[TableTimelinessProfilingChecksSpec](#tabletimelinessprofilingchecksspec)| | | |
|[accuracy](#tableaccuracyprofilingchecksspec)|Configuration of accuracy checks on a table level. Accuracy checks compare the tested table with another reference table.|[TableAccuracyProfilingChecksSpec](#tableaccuracyprofilingchecksspec)| | | |
|[sql](#tablesqlprofilingchecksspec)|Configuration of data quality checks that are evaluating custom SQL conditions and aggregated expressions.|[TableSqlProfilingChecksSpec](#tablesqlprofilingchecksspec)| | | |
|[availability](#tableavailabilityprofilingchecksspec)|Configuration of the table availability data quality checks on a table level.|[TableAvailabilityProfilingChecksSpec](#tableavailabilityprofilingchecksspec)| | | |
|[schema](#tableschemaprofilingchecksspec)|Configuration of schema (column count and schema) data quality checks on a table level.|[TableSchemaProfilingChecksSpec](#tableschemaprofilingchecksspec)| | | |
|[comparisons](#tablecomparisonprofilingchecksspecmap)|Dictionary of configuration of checks for table comparisons. The key that identifies each comparison must match the name of a data comparison that is configured on the parent table.|[TableComparisonProfilingChecksSpecMap](#tablecomparisonprofilingchecksspecmap)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## TableSqlProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[sql_condition_passed_percent_on_table](\docs\checks\table\sql\sql-condition-passed-percent-on-table)|Verifies that a set percentage of rows passed a custom SQL condition (expression).|[TableSqlConditionPassedPercentCheckSpec](\docs\checks\table\sql\sql-condition-passed-percent-on-table)| | | |
|[sql_condition_failed_count_on_table](\docs\checks\table\sql\sql-condition-failed-count-on-table)|Verifies that a set number of rows failed a custom SQL condition (expression).|[TableSqlConditionFailedCountCheckSpec](\docs\checks\table\sql\sql-condition-failed-count-on-table)| | | |
|[sql_aggregate_expr_table](\docs\checks\table\sql\sql-aggregate-expr-table)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|[TableSqlAggregateExprCheckSpec](\docs\checks\table\sql\sql-aggregate-expr-table)| | | |









___  

## CustomCheckSpecMap  
Dictionary of custom checks indexed by a check name.  
  












___  

## TableComparisonProfilingChecksSpecMap  
Container of comparison checks for each defined data comparison. The name of the key in this dictionary
 must match a name of a table comparison that is defined on the parent table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |









___  

## TableComparisonProfilingChecksSpec  
Container of built-in comparison (accuracy) checks on a table level that are using a defined comparison to identify the reference table and the data grouping configuration.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[row_count_match](\docs\checks\table\comparisons\row-count-match)|Verifies that the row count of the tested (parent) table matches the row count of the reference table. Compares each group of data with a GROUP BY clause.|[TableComparisonRowCountMatchCheckSpec](\docs\checks\table\comparisons\row-count-match)| | | |









___  

## RecurringScheduleSpec  
Recurring job schedule specification.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|cron_expression|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|string| | | |
|disabled|Disables the schedule. When the value of this &#x27;disable&#x27; field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|boolean| | | |









___  

## CustomSensorParametersSpec  
Custom sensor parameters for custom checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## CustomCheckSpec  
Custom check specification. This check is usable only when there is a matching custom check definition that identifies
 the sensor definition and the rule definition.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sensor_name|Optional custom sensor name. It is a folder name inside the user&#x27;s home &#x27;sensors&#x27; folder or the DQO Home (DQO distribution) home/sensors folder. Sample sensor name: table/volume/row_count. When this value is set, it overrides the default sensor definition defined for the named check definition.|string| | | |
|rule_name|Optional custom rule name. It is a path to a custom rule python module that starts at the user&#x27;s home &#x27;rules&#x27; folder. The path should not end with the .py file extension. Sample rule: myrules/my_custom_rule. When this value is set, it overrides the default rule definition defined for the named check definition.|string| | | |
|[parameters](#customsensorparametersspec)|Custom sensor parameters|[CustomSensorParametersSpec](#customsensorparametersspec)| | | |
|[warning](#customruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[CustomRuleParametersSpec](#customruleparametersspec)| | | |
|[error](#customruleparametersspec)|Default alerting threshold for a row count that raises a data quality error (alert)|[CustomRuleParametersSpec](#customruleparametersspec)| | | |
|[fatal](#customruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[CustomRuleParametersSpec](#customruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_grouping|Data grouping configuration name that should be applied to this data quality check. The data grouping is used to group the check&#x27;s result by a GROUP BY clause in SQL, evaluating the data quality check for each group of rows. Use the name of one of data grouping configurations defined on the parent table.|string| | | |









___  

## TableVolumeProfilingChecksSpec  
Container of built-in preconfigured volume data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[row_count](\docs\checks\table\volume\row-count)|Verifies that the number of rows in a table does not exceed the minimum accepted count.|[TableRowCountCheckSpec](\docs\checks\table\volume\row-count)| | | |
|[row_count_anomaly_differencing_30_days](\docs\checks\table\volume\row-count-anomaly-differencing-30-days)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|[TableAnomalyDifferencingRowCount30DaysCheckSpec](\docs\checks\table\volume\row-count-anomaly-differencing-30-days)| | | |
|[row_count_anomaly_differencing](\docs\checks\table\volume\row-count-anomaly-differencing)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 90 days.|[TableAnomalyDifferencingRowCountCheckSpec](\docs\checks\table\volume\row-count-anomaly-differencing)| | | |
|[row_count_change](\docs\checks\table\volume\row-count-change)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|[TableChangeRowCountCheckSpec](\docs\checks\table\volume\row-count-change)| | | |
|[row_count_change_yesterday](\docs\checks\table\volume\row-count-change-yesterday)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|[TableChangeRowCountSinceYesterdayCheckSpec](\docs\checks\table\volume\row-count-change-yesterday)| | | |
|[row_count_change_7_days](\docs\checks\table\volume\row-count-change-7-days)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|[TableChangeRowCountSince7DaysCheckSpec](\docs\checks\table\volume\row-count-change-7-days)| | | |
|[row_count_change_30_days](\docs\checks\table\volume\row-count-change-30-days)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|[TableChangeRowCountSince30DaysCheckSpec](\docs\checks\table\volume\row-count-change-30-days)| | | |









___  

## TableSchemaProfilingChecksSpec  
Container of built-in preconfigured volume data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[column_count](\docs\checks\table\schema\column-count)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|[TableSchemaColumnCountCheckSpec](\docs\checks\table\schema\column-count)| | | |
|[column_count_changed](\docs\checks\table\schema\column-count-changed)|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|[TableSchemaColumnCountChangedCheckSpec](\docs\checks\table\schema\column-count-changed)| | | |
|[column_list_changed](\docs\checks\table\schema\column-list-changed)|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|[TableSchemaColumnListChangedCheckSpec](\docs\checks\table\schema\column-list-changed)| | | |
|[column_list_or_order_changed](\docs\checks\table\schema\column-list-or-order-changed)|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|[TableSchemaColumnListOrOrderChangedCheckSpec](\docs\checks\table\schema\column-list-or-order-changed)| | | |
|[column_types_changed](\docs\checks\table\schema\column-types-changed)|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|[TableSchemaColumnTypesChangedCheckSpec](\docs\checks\table\schema\column-types-changed)| | | |









___  

## TableTimelinessProfilingChecksSpec  
Container of timeliness data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[data_freshness](\docs\checks\table\timeliness\data-freshness)|Calculates the number of days since the most recent event timestamp (freshness)|[TableDataFreshnessCheckSpec](\docs\checks\table\timeliness\data-freshness)| | | |
|[data_staleness](\docs\checks\table\timeliness\data-staleness)|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|[TableDataStalenessCheckSpec](\docs\checks\table\timeliness\data-staleness)| | | |
|[data_ingestion_delay](\docs\checks\table\timeliness\data-ingestion-delay)|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](\docs\checks\table\timeliness\data-ingestion-delay)| | | |









___  

## TableAccuracyProfilingChecksSpec  
Container of built-in preconfigured accuracy data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[total_row_count_match_percent](\docs\checks\table\accuracy\total-row-count-match-percent)|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|[TableAccuracyTotalRowCountMatchPercentCheckSpec](\docs\checks\table\accuracy\total-row-count-match-percent)| | | |









___  

## CommentSpec  
Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|comment_by|Commented by|string| | | |
|comment|Comment text|string| | | |









___  

## CommentsListSpec  
List of comments.  
  


___  
