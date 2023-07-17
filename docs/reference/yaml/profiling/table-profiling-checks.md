
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
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## TableVolumeProfilingChecksSpec  
Container of built-in preconfigured volume data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[row_count](#tablerowcountcheckspec)|Verifies that the number of rows in a table does not exceed the minimum accepted count.|[TableRowCountCheckSpec](#tablerowcountcheckspec)| | | |
|[row_count_anomaly_7_days](#tableanomalyrowcountchange7dayscheckspec)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 7 days.|[TableAnomalyRowCountChange7DaysCheckSpec](#tableanomalyrowcountchange7dayscheckspec)| | | |
|[row_count_anomaly_30_days](#tableanomalyrowcountchange30dayscheckspec)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 30 days.|[TableAnomalyRowCountChange30DaysCheckSpec](#tableanomalyrowcountchange30dayscheckspec)| | | |
|[row_count_anomaly_60_days](#tableanomalyrowcountchange60dayscheckspec)|Verifies that the total row count of the tested table changes in a rate within a percentile boundary during last 60 days.|[TableAnomalyRowCountChange60DaysCheckSpec](#tableanomalyrowcountchange60dayscheckspec)| | | |
|[row_count_change](#tablechangerowcountcheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout.|[TableChangeRowCountCheckSpec](#tablechangerowcountcheckspec)| | | |
|[row_count_change_yesterday](#tablechangerowcountsinceyesterdaycheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from yesterday. Allows for exact match to readouts from yesterday or past readouts lookup.|[TableChangeRowCountSinceYesterdayCheckSpec](#tablechangerowcountsinceyesterdaycheckspec)| | | |
|[row_count_change_7_days](#tablechangerowcountsince7dayscheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last week. Allows for exact match to readouts from 7 days ago or past readouts lookup.|[TableChangeRowCountSince7DaysCheckSpec](#tablechangerowcountsince7dayscheckspec)| | | |
|[row_count_change_30_days](#tablechangerowcountsince30dayscheckspec)|Verifies that the total row count of the tested table has changed by a fixed rate since the last readout from last month. Allows for exact match to readouts from 30 days ago or past readouts lookup.|[TableChangeRowCountSince30DaysCheckSpec](#tablechangerowcountsince30dayscheckspec)| | | |









___  

## TableRowCountCheckSpec  
Row count (select count(*) from ...) test that runs a row_count check, obtains a count of rows and verifies the number by calling the row count rule.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Row count sensor parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#mincountrulewarningparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinCountRuleWarningParametersSpec](#mincountrulewarningparametersspec)| | | |
|[error](#mincountrule0parametersspec)|Default alerting threshold for a row count that raises a data quality error (alert)|[MinCountRule0ParametersSpec](#mincountrule0parametersspec)| | | |
|[fatal](#mincountrulefatalparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinCountRuleFatalParametersSpec](#mincountrulefatalparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableVolumeRowCountSensorParametersSpec  
Table sensor that executes a row count query.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MinCountRuleWarningParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_count|Minimum accepted value for the actual_value returned by the sensor (inclusive).|long| | |0<br/>|









___  

## MinCountRule0ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_count|Minimum accepted value for the actual_value returned by the sensor (inclusive).|long| | |5<br/>|









___  

## MinCountRuleFatalParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_count|Minimum accepted value for the actual_value returned by the sensor (inclusive).|long| | |100<br/>|









___  

## RecurringScheduleSpec  
Recurring job schedule specification.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|cron_expression|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|string| | | |
|disabled|Disables the schedule. When the value of this &#x27;disable&#x27; field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|boolean| | | |









___  

## CommentsListSpec  
List of comments.  
  


___  

## CommentSpec  
Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|comment_by|Commented by|string| | | |
|comment|Comment text|string| | | |









___  

## TableAnomalyRowCountChange7DaysCheckSpec  
Table level check that ensures that the row count changes in a rate within a two-tailed percentile during last 7 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Data quality check parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin7daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin7daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin7DaysRuleParametersSpec](#changepercentilemovingwithin7daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ChangePercentileMovingWithin7DaysRuleParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_within|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 7 time periods (days, etc.) time window, but at least 3 readouts must exist to run the calculation.|double| | |95<br/>|









___  

## TableAnomalyRowCountChange30DaysCheckSpec  
Table level check that ensures that the row count changes in a rate within a two-tailed percentile during last 30 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Data quality check parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin30daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin30daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin30DaysRuleParametersSpec](#changepercentilemovingwithin30daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ChangePercentileMovingWithin30DaysRuleParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_within|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 30 time periods (days, etc.) time window, but at least 10 readouts must exist to run the calculation.|double| | |95<br/>|









___  

## TableAnomalyRowCountChange60DaysCheckSpec  
Table level check that ensures that the row count changes in a rate within a two-tailed percentile during last 60 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Data quality check parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#changepercentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[error](#changepercentilemovingwithin60daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[fatal](#changepercentilemovingwithin60daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ChangePercentileMovingWithin60DaysRuleParametersSpec](#changepercentilemovingwithin60daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ChangePercentileMovingWithin60DaysRuleParametersSpec  
Data quality rule that verifies if a data quality sensor readout value is probable under
 the estimated normal distribution based on the increments of previous values gathered
 within a time window.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_within|Probability that the current sensor readout will achieve values within the mean according to the distribution of the previous values gathered within the time window. In other words, the inter-quantile range around the mean of the estimated normal distribution. Set the time window at the threshold level for all severity levels (warning, error, fatal) at once. The default is a 60 time periods (days, etc.) time window, but at least 20 readouts must exist to run the calculation.|double| | |95<br/>|









___  

## TableChangeRowCountCheckSpec  
Table level check that ensures that the row count changed by a fixed rate since the last readout.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Data quality check parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#withinchangeruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[WithinChangeRuleParametersSpec](#withinchangeruleparametersspec)| | | |
|[error](#withinchangeruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[WithinChangeRuleParametersSpec](#withinchangeruleparametersspec)| | | |
|[fatal](#withinchangeruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[WithinChangeRuleParametersSpec](#withinchangeruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## WithinChangeRuleParametersSpec  
Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_within|Maximal accepted absolute change with regards to the previous readout (inclusive).|double| | |10<br/>|









___  

## TableChangeRowCountSinceYesterdayCheckSpec  
Table level check that ensures that the row count changed by a fixed rate since the last readout from yesterday.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Data quality check parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#withinchange1dayruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[WithinChange1DayRuleParametersSpec](#withinchange1dayruleparametersspec)| | | |
|[error](#withinchange1dayruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[WithinChange1DayRuleParametersSpec](#withinchange1dayruleparametersspec)| | | |
|[fatal](#withinchange1dayruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[WithinChange1DayRuleParametersSpec](#withinchange1dayruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## WithinChange1DayRuleParametersSpec  
Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound compared to yesterday.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_within|Maximal accepted absolute change with regards to the previous readout (inclusive).|double| | |10<br/>|
|exact|Whether to compare the actual value to the readout exactly 1 day in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts from the current day.|boolean| | |false<br/>|









___  

## TableChangeRowCountSince7DaysCheckSpec  
Table level check that ensures that the row count changed by a fixed rate since the last readout from last week.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Data quality check parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#withinchange7daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[WithinChange7DaysRuleParametersSpec](#withinchange7daysruleparametersspec)| | | |
|[error](#withinchange7daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[WithinChange7DaysRuleParametersSpec](#withinchange7daysruleparametersspec)| | | |
|[fatal](#withinchange7daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[WithinChange7DaysRuleParametersSpec](#withinchange7daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## WithinChange7DaysRuleParametersSpec  
Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound compared to last week.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_within|Maximal accepted absolute change with regards to the previous readout (inclusive).|double| | |10<br/>|
|exact|Whether to compare the actual value to the readout exactly 7 days in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts for the last 7 days.|boolean| | |false<br/>|









___  

## TableChangeRowCountSince30DaysCheckSpec  
Table level check that ensures that the row count changed by a fixed rate since the last readout from last month.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablevolumerowcountsensorparametersspec)|Data quality check parameters|[TableVolumeRowCountSensorParametersSpec](#tablevolumerowcountsensorparametersspec)| | | |
|[warning](#withinchange30daysruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[WithinChange30DaysRuleParametersSpec](#withinchange30daysruleparametersspec)| | | |
|[error](#withinchange30daysruleparametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[WithinChange30DaysRuleParametersSpec](#withinchange30daysruleparametersspec)| | | |
|[fatal](#withinchange30daysruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[WithinChange30DaysRuleParametersSpec](#withinchange30daysruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## WithinChange30DaysRuleParametersSpec  
Data quality rule that verifies if data quality sensor readout value changed by a value within the provided bound compared to last month.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_within|Maximal accepted absolute change with regards to the previous readout (inclusive).|double| | |10<br/>|
|exact|Whether to compare the actual value to the readout exactly 30 days in the past. If the flag is false, the rule searches for the newest readout, 60 days in the past, having skipped the readouts for the last 30 days.|boolean| | |false<br/>|









___  

## TableTimelinessProfilingChecksSpec  
Container of timeliness data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[days_since_most_recent_event](#tabledayssincemostrecenteventcheckspec)|Calculates the number of days since the most recent event timestamp (freshness)|[TableDaysSinceMostRecentEventCheckSpec](#tabledayssincemostrecenteventcheckspec)| | | |
|[data_ingestion_delay](#tabledataingestiondelaycheckspec)|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|[TableDataIngestionDelayCheckSpec](#tabledataingestiondelaycheckspec)| | | |
|[days_since_most_recent_ingestion](#tabledayssincemostrecentingestioncheckspec)|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|[TableDaysSinceMostRecentIngestionCheckSpec](#tabledayssincemostrecentingestioncheckspec)| | | |









___  

## TableDaysSinceMostRecentEventCheckSpec  
Table level check that calculates the time difference between the most recent row in the table and the current time.
 The timestamp column that is used for comparison is defined as the timestamp_columns.event_timestamp_column on the table configuration.
 This check is also known as &quot;Data Freshness&quot;.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tabletimelinessdayssincemostrecenteventsensorparametersspec)|Max days since most recent event sensor parameters|[TableTimelinessDaysSinceMostRecentEventSensorParametersSpec](#tabletimelinessdayssincemostrecenteventsensorparametersspec)| | | |
|[warning](#maxdaysrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDaysRule1ParametersSpec](#maxdaysrule1parametersspec)| | | |
|[error](#maxdaysrule2parametersspec)|Default alerting threshold for max days since most recent event that raises a data quality error (alert)|[MaxDaysRule2ParametersSpec](#maxdaysrule2parametersspec)| | | |
|[fatal](#maxdaysrule7parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxDaysRule7ParametersSpec](#maxdaysrule7parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableTimelinessDaysSinceMostRecentEventSensorParametersSpec  
Table sensor that runs a query calculating maximum days since the most recent event.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxDaysRule1ParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_days|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxDaysRule2ParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_days|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxDaysRule7ParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_days|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## TableDataIngestionDelayCheckSpec  
Table level check that calculates time difference between the most recent row in the table and the most recent timestamp when the last row was loaded into the data warehouse / data lake.
 The most recent row is identified by finding the most recent (maximum) value of the timestamp column that should contain the last modification timestamp from the source.
 The timestamp when the row was loaded is identified by the most recent (maximum) value a timestamp column that was filled by the data pipeline, for example: &quot;loaded_at&quot;, &quot;updated_at&quot;, etc.
 This check requires that the data pipeline is filling an extra column with the timestamp when the data loading job has been executed.
 The names of both columns used for comparison should be specified in the &quot;timestamp_columns&quot; configuration entry on the table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tabletimelinessdataingestiondelaysensorparametersspec)|Max number of days between event and ingestion sensor parameters|[TableTimelinessDataIngestionDelaySensorParametersSpec](#tabletimelinessdataingestiondelaysensorparametersspec)| | | |
|[warning](#maxdaysrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDaysRule1ParametersSpec](#maxdaysrule1parametersspec)| | | |
|[error](#maxdaysrule2parametersspec)|Default alerting threshold for a max number of days between event and ingestion check that raises a data quality error (alert)|[MaxDaysRule2ParametersSpec](#maxdaysrule2parametersspec)| | | |
|[fatal](#maxdaysrule7parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxDaysRule7ParametersSpec](#maxdaysrule7parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableTimelinessDataIngestionDelaySensorParametersSpec  
Table sensor that runs a query calculating the time difference in days between the most recent transaction timestamp and the most recent data loading timestamp.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## TableDaysSinceMostRecentIngestionCheckSpec  
Table level check that calculates the time difference between the last timestamp when any data was loaded into a table and the current time.
 This check could be used only when the data pipeline, a ETL process or a trigger in the data warehouse is filling an extra column with the timestamp when the data loading job was loaded.
 The ingestion column that is used for comparison is defined as the timestamp_columns.ingestion_timestamp_column on the table configuration.
 This check is also known as &quot;Data Staleness&quot;.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tabletimelinessdayssincemostrecentingestionsensorparametersspec)|Min number of days between event and ingestion sensor parameters|[TableTimelinessDaysSinceMostRecentIngestionSensorParametersSpec](#tabletimelinessdayssincemostrecentingestionsensorparametersspec)| | | |
|[warning](#maxdaysrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDaysRule1ParametersSpec](#maxdaysrule1parametersspec)| | | |
|[error](#maxdaysrule2parametersspec)|Default alerting threshold for a min number of days between event and ingestion check that raises a data quality error (alert)|[MaxDaysRule2ParametersSpec](#maxdaysrule2parametersspec)| | | |
|[fatal](#maxdaysrule7parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxDaysRule7ParametersSpec](#maxdaysrule7parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableTimelinessDaysSinceMostRecentIngestionSensorParametersSpec  
Table sensor that runs a query calculating the time difference in days between the current date and most recent data loading timestamp (staleness).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## TableAccuracyProfilingChecksSpec  
Container of built-in preconfigured accuracy data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[row_count_match_percent](#tableaccuracyrowcountmatchpercentcheckspec)|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|[TableAccuracyRowCountMatchPercentCheckSpec](#tableaccuracyrowcountmatchpercentcheckspec)| | | |









___  

## TableAccuracyRowCountMatchPercentCheckSpec  
Table level check that ensures that there are no more than a maximum percentage of difference of row count of a tested table and of an row count of another (reference) table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tableaccuracyrowcountmatchpercentsensorparametersspec)|Data quality check parameters|[TableAccuracyRowCountMatchPercentSensorParametersSpec](#tableaccuracyrowcountmatchpercentsensorparametersspec)| | | |
|[warning](#maxdiffpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDiffPercentRule1ParametersSpec](#maxdiffpercentrule1parametersspec)| | | |
|[error](#maxdiffpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of difference of row count of a table column and of a row count of another table column that raises a data quality error (alert).|[MaxDiffPercentRule2ParametersSpec](#maxdiffpercentrule2parametersspec)| | | |
|[fatal](#maxdiffpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxDiffPercentRule5ParametersSpec](#maxdiffpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableAccuracyRowCountMatchPercentSensorParametersSpec  
Table level sensor that calculates percentage of the difference of the total row count of all rows in the tested table and the total row count of the other (reference) table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxDiffPercentRule1ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_diff_percent|Maximum accepted value for the percentage of difference between expected_value and actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxDiffPercentRule2ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_diff_percent|Maximum accepted value for the percentage of difference between expected_value and actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxDiffPercentRule5ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_diff_percent|Maximum accepted value for the percentage of difference between expected_value and actual_value returned by the sensor (inclusive).|double| | | |









___  

## TableSqlProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a table level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[sql_condition_passed_percent_on_table](#tablesqlconditionpassedpercentcheckspec)|Verifies that a set percentage of rows passed a custom SQL condition (expression).|[TableSqlConditionPassedPercentCheckSpec](#tablesqlconditionpassedpercentcheckspec)| | | |
|[sql_condition_failed_count_on_table](#tablesqlconditionfailedcountcheckspec)|Verifies that a set number of rows failed a custom SQL condition (expression).|[TableSqlConditionFailedCountCheckSpec](#tablesqlconditionfailedcountcheckspec)| | | |
|[sql_aggregate_expr_table](#tablesqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|[TableSqlAggregateExprCheckSpec](#tablesqlaggregateexprcheckspec)| | | |









___  

## TableSqlConditionPassedPercentCheckSpec  
Table level check that ensures that a minimum percentage of rows passed a custom SQL condition (expression).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablesqlconditionpassedpercentsensorparametersspec)|Sensor parameters with the custom SQL condition (an expression that returns true/false) which is evaluated on a each row|[TableSqlConditionPassedPercentSensorParametersSpec](#tablesqlconditionpassedpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning when a minimum acceptable percentage of rows did not pass the custom SQL condition (expression). The warning is considered as a passed data quality check.|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum acceptable percentage of rows passing the custom SQL condition (expression) that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue when a minimum acceptable percentage of rows did not pass the custom SQL condition (expression). A fatal issue indicates a serious data quality problem that should result in stopping the data pipelines.|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableSqlConditionPassedPercentSensorParametersSpec  
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string| | |SUM(col_total_impressions) &gt; SUM(col_total_clicks)<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MinPercentRule100ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MinPercentRule99ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MinPercentRule95ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## TableSqlConditionFailedCountCheckSpec  
Table level check that ensures that there are no more than a maximum number of rows fail a custom SQL condition (expression).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablesqlconditionfailedcountsensorparametersspec)|Sensor parameters with the custom SQL condition (an expression that returns true/false) which is evaluated on a each row|[TableSqlConditionFailedCountSensorParametersSpec](#tablesqlconditionfailedcountsensorparametersspec)| | | |
|[warning](#maxcountrule15parametersspec)|Alerting threshold that raises a data quality warning when a given number of rows failed the custom SQL condition (expression). The warning is considered as a passed data quality check.|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows failing the custom SQL condition (expression) that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule0parametersspec)|Alerting threshold that raises a fatal data quality issue when a given number of rows failed the custom SQL condition (expression). A fatal issue indicates a serious data quality problem that should result in stopping the data pipelines.|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableSqlConditionFailedCountSensorParametersSpec  
Table level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string| | |SUM(col_total_impressions) &gt; SUM(col_total_clicks)<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxCountRule15ParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_count|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| | | |









___  

## MaxCountRule10ParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_count|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| | | |









___  

## MaxCountRule0ParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_count|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| | | |









___  

## TableSqlAggregateExprCheckSpec  
Table level check that calculates a given SQL aggregate expression and compares it with a maximum accepted value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablesqlaggregatedexpressionsensorparametersspec)|Sensor parameters with the custom SQL aggregate expression that is evaluated on a table|[TableSqlAggregatedExpressionSensorParametersSpec](#tablesqlaggregatedexpressionsensorparametersspec)| | | |
|[warning](#maxvalueruleparametersspec)|Default alerting threshold for warnings raised when the aggregated value is above the maximum accepted value.|[MaxValueRuleParametersSpec](#maxvalueruleparametersspec)| | | |
|[error](#maxvalueruleparametersspec)|Default alerting threshold for errors raised when the aggregated value is above the maximum accepted value.|[MaxValueRuleParametersSpec](#maxvalueruleparametersspec)| | | |
|[fatal](#maxvalueruleparametersspec)|Default alerting threshold for fatal data quality issues raised when the aggregated value is above the maximum accepted value.|[MaxValueRuleParametersSpec](#maxvalueruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableSqlAggregatedExpressionSensorParametersSpec  
Table level sensor that executes a given SQL expression on a table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_expression|SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or withing a GROUP BY clause for daily partitions and/or data streams. The expression can use {table} placeholder that is replaced with a full table name.|string| | |SUM(col_net_price) + SUM(col_tax)<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxValueRuleParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_value|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | |1.5<br/>|









___  

## TableAvailabilityProfilingChecksSpec  
Container of built-in preconfigured table availability data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table_availability](#tableavailabilitycheckspec)|Verifies availability of the table in a database using a simple row count.|[TableAvailabilityCheckSpec](#tableavailabilitycheckspec)| | | |









___  

## TableAvailabilityCheckSpec  
Table level check that verifies that a query can be executed on a table and that the server does not return errors, that the table exists, and that there are accesses to it.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tableavailabilitysensorparametersspec)|Row count sensor parameters|[TableAvailabilitySensorParametersSpec](#tableavailabilitysensorparametersspec)| | | |
|[warning](#maxfailuresrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxFailuresRule0ParametersSpec](#maxfailuresrule0parametersspec)| | | |
|[error](#maxfailuresrule5parametersspec)|Default alerting threshold for a row count that raises a data quality error (alert)|[MaxFailuresRule5ParametersSpec](#maxfailuresrule5parametersspec)| | | |
|[fatal](#maxfailuresrule10parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxFailuresRule10ParametersSpec](#maxfailuresrule10parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableAvailabilitySensorParametersSpec  
Table availability sensor that executes a row count query.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxFailuresRule0ParametersSpec  
Data quality rule that verifies if the number of executive failures (the sensor returned 0) is below the max_failures. The default maximum failures is 0 failures (the first failure is reported).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_failures|Maximum number of consecutive check failures, a check is failed when the sensor&#x27;s query failed to execute due to a connection error, missing table or a corrupted table.|long| | | |









___  

## MaxFailuresRule5ParametersSpec  
Data quality rule that verifies if the number of executive failures (the sensor returned 0) is below the max_failures. The default maximum failures is 5 failures (the 6th failure is reported).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_failures|Maximum number of consecutive check failures, a check is failed when the sensor&#x27;s query failed to execute due to a connection error, missing table or a corrupted table.|long| | | |









___  

## MaxFailuresRule10ParametersSpec  
Data quality rule that verifies if the number of executive failures (the sensor returned 0) is below the max_failures. The default maximum failures is 10 failures (the 11th failure is reported).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_failures|Maximum number of consecutive check failures, a check is failed when the sensor&#x27;s query failed to execute due to a connection error, missing table or a corrupted table.|long| | | |









___  

## TableSchemaProfilingChecksSpec  
Container of built-in preconfigured volume data quality checks on a table level.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[column_count](#tableschemacolumncountcheckspec)|Detects if the number of column matches an expected number. Retrieves the metadata of the monitored table, counts the number of columns and compares it to an expected value (an expected number of columns).|[TableSchemaColumnCountCheckSpec](#tableschemacolumncountcheckspec)| | | |
|[column_count_changed](#tableschemacolumncountchangedcheckspec)|Detects if the count of columns has changed. Retrieves the metadata of the monitored table, counts the number of columns and compares it the last known column count that was captured when this data quality check was executed the last time.|[TableSchemaColumnCountChangedCheckSpec](#tableschemacolumncountchangedcheckspec)| | | |
|[column_list_changed](#tableschemacolumnlistchangedcheckspec)|Detects if new columns were added or existing columns were removed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns.|[TableSchemaColumnListChangedCheckSpec](#tableschemacolumnlistchangedcheckspec)| | | |
|[column_list_or_order_changed](#tableschemacolumnlistororderchangedcheckspec)|Detects if new columns were added, existing columns were removed or the columns were reordered. Retrieves the metadata of the monitored table and calculates an ordered hash of the column names. Compares the current hash to the previously known hash to detect any changes to the list of columns or their order.|[TableSchemaColumnListOrOrderChangedCheckSpec](#tableschemacolumnlistororderchangedcheckspec)| | | |
|[column_types_changed](#tableschemacolumntypeschangedcheckspec)|Detects if new columns were added, removed or their data types have changed. Retrieves the metadata of the monitored table and calculates an unordered hash of the column names and the data types (including the length, scale, precision, nullability). Compares the current hash to the previously known hash to detect any changes to the list of columns or their types.|[TableSchemaColumnTypesChangedCheckSpec](#tableschemacolumntypeschangedcheckspec)| | | |









___  

## TableSchemaColumnCountCheckSpec  
Table level check that retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to an expected number of columns.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablecolumncountsensorparametersspec)|Column count sensor parameters|[TableColumnCountSensorParametersSpec](#tablecolumncountsensorparametersspec)| | | |
|[warning](#equalsintegerruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[EqualsIntegerRuleParametersSpec](#equalsintegerruleparametersspec)| | | |
|[error](#equalsintegerruleparametersspec)|Default alerting threshold for a row count that raises a data quality error (alert)|[EqualsIntegerRuleParametersSpec](#equalsintegerruleparametersspec)| | | |
|[fatal](#equalsintegerruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[EqualsIntegerRuleParametersSpec](#equalsintegerruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableColumnCountSensorParametersSpec  
Table schema data quality sensor that reads the metadata from a monitored data source and counts the number of columns.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## EqualsIntegerRuleParametersSpec  
Data quality rule that verifies that a data quality check readout equals a given integer value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|expected_value|Expected value for the actual_value returned by the sensor. It must be an integer value.|long| | |10<br/>|









___  

## TableSchemaColumnCountChangedCheckSpec  
Table level check that detects if the number of columns in the table has changed since the check (checkpoint) was run the last time.
 This check retrieves the metadata of the monitored table from the data source, counts the number of columns and compares it to the last known number of columns
 that was captured and is stored in the data quality check results database.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablecolumncountsensorparametersspec)|Column count sensor parameters|[TableColumnCountSensorParametersSpec](#tablecolumncountsensorparametersspec)| | | |
|[warning](#valuechangedparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[error](#valuechangedparametersspec)|Default alerting threshold for a row count that raises a data quality error (alert)|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[fatal](#valuechangedparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ValueChangedParametersSpec  
Data quality rule that verifies if a data quality check (sensor) readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___  

## TableSchemaColumnListChangedCheckSpec  
Table level check that detects if the list of columns has changed since the last time this check was run.
 This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will not depend on the order of columns, only on the column names.
 A data quality issue will be detected if new columns were added or columns that existed during the previous test were dropped.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablecolumnlistunorderedhashsensorparametersspec)|Column list hash sensor parameters|[TableColumnListUnorderedHashSensorParametersSpec](#tablecolumnlistunorderedhashsensorparametersspec)| | | |
|[warning](#valuechangedparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[error](#valuechangedparametersspec)|Default alerting threshold for a row count that raises a data quality error (alert)|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[fatal](#valuechangedparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableColumnListUnorderedHashSensorParametersSpec  
Table schema data quality sensor detects if the list of columns have changed on the table.
 The sensor calculates a hash of the list of column names. The hash value depends on the names of the columns, but not on the order of columns.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## TableSchemaColumnListOrOrderChangedCheckSpec  
Table level check that detects if the list of columns and the order of columns have changed since the last time this check was run.
 This check will retrieve the metadata of a tested table and calculate a hash of the column names. The hash will depend on the order of columns.
 A data quality issue will be detected if new columns were added, columns that existed during the previous test were dropped or the columns were reordered.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablecolumnlistorderedhashsensorparametersspec)|Column list and order sensor parameters|[TableColumnListOrderedHashSensorParametersSpec](#tablecolumnlistorderedhashsensorparametersspec)| | | |
|[warning](#valuechangedparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[error](#valuechangedparametersspec)|Default alerting threshold for a row count that raises a data quality error (alert)|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[fatal](#valuechangedparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableColumnListOrderedHashSensorParametersSpec  
Table schema data quality sensor detects if the list and order of columns have changed on the table.
 The sensor calculates a hash of the list of column names. The hash value depends on the names of the columns and the order of the columns.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## TableSchemaColumnTypesChangedCheckSpec  
Table level check that detects if the column names or column types have changed since the last time this check was run.
 This check will calculate a hash of the column names and all the components of the column&#x27;s data type: the data type name, length, scale, precision and nullability.
 A data quality issue will be detected if the hash of the column data types has changed. This check does not depend on the order of columns, the columns could be reordered as long
 as all columns are still present and the data types match since the last time they were tested.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablecolumntypeshashsensorparametersspec)|Column list and types sensor parameters|[TableColumnTypesHashSensorParametersSpec](#tablecolumntypeshashsensorparametersspec)| | | |
|[warning](#valuechangedparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[error](#valuechangedparametersspec)|Default alerting threshold for a row count that raises a data quality error (alert)|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[fatal](#valuechangedparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## TableColumnTypesHashSensorParametersSpec  
Table schema data quality sensor detects if the list of columns has changed or any of the column has a new data type, length, scale, precision or nullability.
 The sensor calculates a hash of the list of column names and all components of the column&#x27;s type (the type name, length, scale, precision, nullability).
 The hash value does not depend on the order of columns.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## CustomCheckSpecMap  
Dictionary of custom checks indexed by a check name.  
  












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
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## CustomSensorParametersSpec  
Custom sensor parameters for custom checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## CustomRuleParametersSpec  
Custom data quality rule.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|









___  

