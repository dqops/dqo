
## ColumnProfilingCheckCategoriesSpec  
Container of column level, preconfigured checks.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls](#columnnullsprofilingchecksspec)|Configuration of column level checks that verify nulls and blanks.|[ColumnNullsProfilingChecksSpec](#columnnullsprofilingchecksspec)| | | |
|[numeric](#columnnumericprofilingchecksspec)|Configuration of column level checks that verify negative values.|[ColumnNumericProfilingChecksSpec](#columnnumericprofilingchecksspec)| | | |
|[strings](#columnstringsprofilingchecksspec)|Configuration of strings checks on a column level.|[ColumnStringsProfilingChecksSpec](#columnstringsprofilingchecksspec)| | | |
|[uniqueness](#columnuniquenessprofilingchecksspec)|Configuration of uniqueness checks on a column level.|[ColumnUniquenessProfilingChecksSpec](#columnuniquenessprofilingchecksspec)| | | |
|[datetime](#columndatetimeprofilingchecksspec)|Configuration of datetime checks on a column level.|[ColumnDatetimeProfilingChecksSpec](#columndatetimeprofilingchecksspec)| | | |
|[pii](#columnpiiprofilingchecksspec)|Configuration of Personal Identifiable Information (PII) checks on a column level.|[ColumnPiiProfilingChecksSpec](#columnpiiprofilingchecksspec)| | | |
|[sql](#columnsqlprofilingchecksspec)|Configuration of SQL checks that use custom SQL aggregated expressions and SQL conditions in data quality checks.|[ColumnSqlProfilingChecksSpec](#columnsqlprofilingchecksspec)| | | |
|[bool](#columnboolprofilingchecksspec)|Configuration of booleans checks on a column level.|[ColumnBoolProfilingChecksSpec](#columnboolprofilingchecksspec)| | | |
|[integrity](#columnintegrityprofilingchecksspec)|Configuration of integrity checks on a column level.|[ColumnIntegrityProfilingChecksSpec](#columnintegrityprofilingchecksspec)| | | |
|[accuracy](#columnaccuracyprofilingchecksspec)|Configuration of accuracy checks on a column level.|[ColumnAccuracyProfilingChecksSpec](#columnaccuracyprofilingchecksspec)| | | |
|[consistency](#columnconsistencyprofilingchecksspec)|Configuration of consistency checks on a column level.|[ColumnConsistencyProfilingChecksSpec](#columnconsistencyprofilingchecksspec)| | | |
|[anomaly](#columnanomalyprofilingchecksspec)|Configuration of anomaly checks on a column level.|[ColumnAnomalyProfilingChecksSpec](#columnanomalyprofilingchecksspec)| | | |
|[schema](#columnschemaprofilingchecksspec)|Configuration of schema checks on a column level.|[ColumnSchemaProfilingChecksSpec](#columnschemaprofilingchecksspec)| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|[CustomCheckSpecMap](#customcheckspecmap)| | | |









___  

## ColumnNullsProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for nulls.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[nulls_count](#columnnullscountcheckspec)|Verifies that the number of null values in a column does not exceed the maximum accepted count.|[ColumnNullsCountCheckSpec](#columnnullscountcheckspec)| | | |
|[nulls_percent](#columnnullspercentcheckspec)|Verifies that the percent of null values in a column does not exceed the maximum accepted percentage.|[ColumnNullsPercentCheckSpec](#columnnullspercentcheckspec)| | | |
|[not_nulls_count](#columnnotnullscountcheckspec)|Verifies that the number of not null values in a column does not exceed the minimum accepted count.|[ColumnNotNullsCountCheckSpec](#columnnotnullscountcheckspec)| | | |
|[not_nulls_percent](#columnnotnullspercentcheckspec)|Verifies that the percent of not null values in a column does not exceed the minimum accepted percentage.|[ColumnNotNullsPercentCheckSpec](#columnnotnullspercentcheckspec)| | | |









___  

## ColumnNullsCountCheckSpec  
Column-level check that ensures that there are no more than a set number of null values in the monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnullsnullscountsensorparametersspec)|Data quality check parameters|[ColumnNullsNullsCountSensorParametersSpec](#columnnullsnullscountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a set number of rows with null values in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNullsNullsCountSensorParametersSpec  
Column-level sensor that calculates the number of rows with null values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnNullsPercentCheckSpec  
Column-level check that ensures that there are no more than a set percentage of null values in the monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnullsnullspercentsensorparametersspec)|Data quality check parameters|[ColumnNullsNullsPercentSensorParametersSpec](#columnnullsnullspercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a set percentage of rows with null values in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNullsNullsPercentSensorParametersSpec  
Column-level sensor that calculates the percentage of rows with null values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxPercentRule1ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxPercentRule2ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxPercentRule5ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## ColumnNotNullsCountCheckSpec  
Column-level check that ensures that there are no more than a set number of null values in the monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnullsnotnullscountsensorparametersspec)|Data quality check parameters|[ColumnNullsNotNullsCountSensorParametersSpec](#columnnullsnotnullscountsensorparametersspec)| | | |
|[warning](#mincountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinCountRule0ParametersSpec](#mincountrule0parametersspec)| | | |
|[error](#mincountrulewarningparametersspec)|Default alerting threshold for a set number of rows with not null values in a column that raises a data quality error (alert).|[MinCountRuleWarningParametersSpec](#mincountrulewarningparametersspec)| | | |
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

## ColumnNullsNotNullsCountSensorParametersSpec  
Column-level sensor that calculates the number of rows with not null values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnNotNullsPercentCheckSpec  
Column-level check that ensures that there are no more than a set percentage of not null values in the monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnullsnotnullspercentsensorparametersspec)|Data quality check parameters|[ColumnNullsNotNullsPercentSensorParametersSpec](#columnnullsnotnullspercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a set percentage of rows with null values in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNullsNotNullsPercentSensorParametersSpec  
Column level sensor that calculates the percentage of not null values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnNumericProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level for numeric values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[negative_count](#columnnegativecountcheckspec)|Verifies that the number of negative values in a column does not exceed the maximum accepted count.|[ColumnNegativeCountCheckSpec](#columnnegativecountcheckspec)| | | |
|[negative_percent](#columnnegativepercentcheckspec)|Verifies that the percentage of negative values in a column does not exceed the maximum accepted percentage.|[ColumnNegativePercentCheckSpec](#columnnegativepercentcheckspec)| | | |
|[non_negative_count](#columnnonnegativecountcheckspec)|Verifies that the number of non-negative values in a column does not exceed the maximum accepted count.|[ColumnNonNegativeCountCheckSpec](#columnnonnegativecountcheckspec)| | | |
|[non_negative_percent](#columnnonnegativepercentcheckspec)|Verifies that the percentage of non-negative values in a column does not exceed the maximum accepted percentage.|[ColumnNonNegativePercentCheckSpec](#columnnonnegativepercentcheckspec)| | | |
|[expected_numbers_in_use_count](#columnexpectednumbersinusecountcheckspec)|Verifies that the expected numeric values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|[ColumnExpectedNumbersInUseCountCheckSpec](#columnexpectednumbersinusecountcheckspec)| | | |
|[number_value_in_set_percent](#columnnumbervalueinsetpercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|[ColumnNumberValueInSetPercentCheckSpec](#columnnumbervalueinsetpercentcheckspec)| | | |
|[values_in_range_numeric_percent](#columnvaluesinrangenumericpercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|[ColumnValuesInRangeNumericPercentCheckSpec](#columnvaluesinrangenumericpercentcheckspec)| | | |
|[values_in_range_integers_percent](#columnvaluesinrangeintegerspercentcheckspec)|Verifies that the percentage of values from range in a column does not exceed the minimum accepted percentage.|[ColumnValuesInRangeIntegersPercentCheckSpec](#columnvaluesinrangeintegerspercentcheckspec)| | | |
|[value_below_min_value_count](#columnvaluebelowminvaluecountcheckspec)|The check counts the number of values in the column that is below the value defined by the user as a parameter.|[ColumnValueBelowMinValueCountCheckSpec](#columnvaluebelowminvaluecountcheckspec)| | | |
|[value_below_min_value_percent](#columnvaluebelowminvaluepercentcheckspec)|The check counts the percentage of values in the column that is below the value defined by the user as a parameter.|[ColumnValueBelowMinValuePercentCheckSpec](#columnvaluebelowminvaluepercentcheckspec)| | | |
|[value_above_max_value_count](#columnvalueabovemaxvaluecountcheckspec)|The check counts the number of values in the column that is above the value defined by the user as a parameter.|[ColumnValueAboveMaxValueCountCheckSpec](#columnvalueabovemaxvaluecountcheckspec)| | | |
|[value_above_max_value_percent](#columnvalueabovemaxvaluepercentcheckspec)|The check counts the percentage of values in the column that is above the value defined by the user as a parameter.|[ColumnValueAboveMaxValuePercentCheckSpec](#columnvalueabovemaxvaluepercentcheckspec)| | | |
|[max_in_range](#columnmaxinrangecheckspec)|Verifies that the maximal value in a column is not outside the set range.|[ColumnMaxInRangeCheckSpec](#columnmaxinrangecheckspec)| | | |
|[min_in_range](#columnmininrangecheckspec)|Verifies that the minimal value in a column is not outside the set range.|[ColumnMinInRangeCheckSpec](#columnmininrangecheckspec)| | | |
|[mean_in_range](#columnmeaninrangecheckspec)|Verifies that the average (mean) of all values in a column is not outside the set range.|[ColumnMeanInRangeCheckSpec](#columnmeaninrangecheckspec)| | | |
|[percentile_in_range](#columnpercentileinrangecheckspec)|Verifies that the percentile of all values in a column is not outside the set range.|[ColumnPercentileInRangeCheckSpec](#columnpercentileinrangecheckspec)| | | |
|[median_in_range](#columnmedianinrangecheckspec)|Verifies that the median of all values in a column is not outside the set range.|[ColumnMedianInRangeCheckSpec](#columnmedianinrangecheckspec)| | | |
|[percentile_10_in_range](#columnpercentile10inrangecheckspec)|Verifies that the percentile 10 of all values in a column is not outside the set range.|[ColumnPercentile10InRangeCheckSpec](#columnpercentile10inrangecheckspec)| | | |
|[percentile_25_in_range](#columnpercentile25inrangecheckspec)|Verifies that the percentile 25 of all values in a column is not outside the set range.|[ColumnPercentile25InRangeCheckSpec](#columnpercentile25inrangecheckspec)| | | |
|[percentile_75_in_range](#columnpercentile75inrangecheckspec)|Verifies that the percentile 75 of all values in a column is not outside the set range.|[ColumnPercentile75InRangeCheckSpec](#columnpercentile75inrangecheckspec)| | | |
|[percentile_90_in_range](#columnpercentile90inrangecheckspec)|Verifies that the percentile 90 of all values in a column is not outside the set range.|[ColumnPercentile90InRangeCheckSpec](#columnpercentile90inrangecheckspec)| | | |
|[sample_stddev_in_range](#columnsamplestddevinrangecheckspec)|Verifies that the sample standard deviation of all values in a column is not outside the set range.|[ColumnSampleStddevInRangeCheckSpec](#columnsamplestddevinrangecheckspec)| | | |
|[population_stddev_in_range](#columnpopulationstddevinrangecheckspec)|Verifies that the population standard deviation of all values in a column is not outside the set range.|[ColumnPopulationStddevInRangeCheckSpec](#columnpopulationstddevinrangecheckspec)| | | |
|[sample_variance_in_range](#columnsamplevarianceinrangecheckspec)|Verifies that the sample variance of all values in a column is not outside the set range.|[ColumnSampleVarianceInRangeCheckSpec](#columnsamplevarianceinrangecheckspec)| | | |
|[population_variance_in_range](#columnpopulationvarianceinrangecheckspec)|Verifies that the population variance of all values in a column is not outside the set range.|[ColumnPopulationVarianceInRangeCheckSpec](#columnpopulationvarianceinrangecheckspec)| | | |
|[sum_in_range](#columnsuminrangecheckspec)|Verifies that the sum of all values in a column is not outside the set range.|[ColumnSumInRangeCheckSpec](#columnsuminrangecheckspec)| | | |
|[invalid_latitude_count](#columninvalidlatitudecountcheckspec)|Verifies that the number of invalid latitude values in a column does not exceed the maximum accepted count.|[ColumnInvalidLatitudeCountCheckSpec](#columninvalidlatitudecountcheckspec)| | | |
|[valid_latitude_percent](#columnvalidlatitudepercentcheckspec)|Verifies that the percentage of valid latitude values in a column does not fall below the minimum accepted percentage.|[ColumnValidLatitudePercentCheckSpec](#columnvalidlatitudepercentcheckspec)| | | |
|[invalid_longitude_count](#columninvalidlongitudecountcheckspec)|Verifies that the number of invalid longitude values in a column does not exceed the maximum accepted count.|[ColumnInvalidLongitudeCountCheckSpec](#columninvalidlongitudecountcheckspec)| | | |
|[valid_longitude_percent](#columnvalidlongitudepercentcheckspec)|Verifies that the percentage of valid longitude values in a column does not fall below the minimum accepted percentage.|[ColumnValidLongitudePercentCheckSpec](#columnvalidlongitudepercentcheckspec)| | | |









___  

## ColumnNegativeCountCheckSpec  
Column level check that ensures that there are no more than a set number of negative values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericnegativecountsensorparametersspec)|Data quality check parameters|[ColumnNumericNegativeCountSensorParametersSpec](#columnnumericnegativecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a set number of rows with negative value in a column that raises a data quality alert|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericNegativeCountSensorParametersSpec  
Column level sensor that counts negative values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnNegativePercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of negative values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericnegativepercentsensorparametersspec)|Data quality check parameters|[ColumnNumericNegativePercentSensorParametersSpec](#columnnumericnegativepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule0ParametersSpec](#maxpercentrule0parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a set percentage of rows with negative value in a column that raises a data quality alert|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericNegativePercentSensorParametersSpec  
Column level sensor that counts percentage of negative values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxPercentRule0ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## ColumnNonNegativeCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of non-negative values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericnonnegativecountsensorparametersspec)|Data quality check parameters|[ColumnNumericNonNegativeCountSensorParametersSpec](#columnnumericnonnegativecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with non-negative values in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericNonNegativeCountSensorParametersSpec  
Column level sensor that counts non negative values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnNonNegativePercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of negative values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericnonnegativepercentsensorparametersspec)|Data quality check parameters|[ColumnNumericNonNegativePercentSensorParametersSpec](#columnnumericnonnegativepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule100ParametersSpec](#maxpercentrule100parametersspec)| | | |
|[error](#maxpercentrule99parametersspec)|Default alerting threshold for a set percentage of rows with non-negative value in a column that raises a data quality alert|[MaxPercentRule99ParametersSpec](#maxpercentrule99parametersspec)| | | |
|[fatal](#maxpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule95ParametersSpec](#maxpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericNonNegativePercentSensorParametersSpec  
Column level sensor that calculates the percent of non-negative values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxPercentRule100ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxPercentRule99ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MaxPercentRule95ParametersSpec  
Data quality rule that verifies if a data quality check readout is less or equal a maximum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_percent|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## ColumnExpectedNumbersInUseCountCheckSpec  
Column level check that counts unique values in a numeric column and counts how many values out of a list of expected numeric values were found in the column.
 The check raises a data quality issue when the threshold of maximum number of missing values was exceeded (too many expected values were not found in the column).
 This check is useful for analysing columns with a low number of unique values, such as status codes, to detect that all status codes are in use in any row.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericexpectednumbersinusecountsensorparametersspec)|Data quality check parameters that specify a list of expected numeric values that must be present in the column.|[ColumnNumericExpectedNumbersInUseCountSensorParametersSpec](#columnnumericexpectednumbersinusecountsensorparametersspec)| | | |
|[warning](#maxmissingrule0parametersspec)|Alerting threshold that raises a data quality warning when too many expected values were not found in the column.|[MaxMissingRule0ParametersSpec](#maxmissingrule0parametersspec)| | | |
|[error](#maxmissingrule1parametersspec)|Alerting threshold that raises a data quality error when too many expected values were not found in the column.|[MaxMissingRule1ParametersSpec](#maxmissingrule1parametersspec)| | | |
|[fatal](#maxmissingrule2parametersspec)|Alerting threshold that raises a data quality fatal issue when too many expected values were not found in the column.|[MaxMissingRule2ParametersSpec](#maxmissingrule2parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericExpectedNumbersInUseCountSensorParametersSpec  
Column level sensor that counts how many expected numeric values are used in a tested column. Finds unique column values from the set of expected numeric values and counts them.
 This sensor is useful to analyze numeric columns that have a low number of unique values and it should be tested if all possible values from the list of expected values are used in any row.
 The typical types of tested columns are numeric status or type columns.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|expected_values|List of expected numeric values that should be found in the tested column.|integer_list| | |2<br/>3<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MaxMissingRule0ParametersSpec  
Data quality rule that verifies the results of the data quality checks that count the number of values
 present in a column, comparing it to a list of expected values. The rule compares the count of expected values (received as expected_value)
 to the count of values found in the column (as the actual_value). The rule fails when the difference is higher than
 the expected max_missing, which is the maximum difference between the expected_value (the count of values in the expected_values list)
 and the actual number of values found in the column that match the list.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_missing|The maximum number of values from the expected_values list that were not found in the column (inclusive).|long| | |1<br/>|









___  

## MaxMissingRule1ParametersSpec  
Data quality rule that verifies the results of the data quality checks that count the number of values
 present in a column, comparing it to a list of expected values. The rule compares the count of expected values (received as expected_value)
 to the count of values found in the column (as the actual_value). The rule fails when the difference is higher than
 the expected max_missing, which is the maximum difference between the expected_value (the count of values in the expected_values list)
 and the actual number of values found in the column that match the list.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_missing|The maximum number of values from the expected_values list that were not found in the column (inclusive).|long| | |1<br/>|









___  

## MaxMissingRule2ParametersSpec  
Data quality rule that verifies the results of the data quality checks that count the number of values
 present in a column, comparing it to a list of expected values. The rule compares the count of expected values (received as expected_value)
 to the count of values found in the column (as the actual_value). The rule fails when the difference is higher than
 the expected max_missing, which is the maximum difference between the expected_value (the count of values in the expected_values list)
 and the actual number of values found in the column that match the list.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_missing|The maximum number of values from the expected_values list that were not found in the column (inclusive).|long| | |2<br/>|









___  

## ColumnNumberValueInSetPercentCheckSpec  
Column level check that calculates the percentage of rows for which the tested numeric column contains a value from the set of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 The check raises a data quality issue when the percentage of rows with a not null column value that is not expected (not one of the values in the expected_values set)
 is below an expected threshold, for example 99% of rows should have values from the defined domain.
 This data quality check is useful for checking numeric columns that store numeric codes (such as status codes) that the only values found in the column are from a set of expected values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericnumbervalueinsetpercentsensorparametersspec)|Data quality check parameters that specify a list of expected values that are compared to the values in the tested numeric column.|[ColumnNumericNumberValueInSetPercentSensorParametersSpec](#columnnumericnumbervalueinsetpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Default alerting threshold for a percentage of rows with valid values in a column (from the set of expected values). Raises a data quality issue with at a warning severity level when the percentage of valid rows is below the minimum percentage threshold.|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a percentage of rows with valid values in a column (from the set of expected values). Raises a data quality issue with at an error severity level when the percentage of valid rows is below the minimum percentage threshold|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Default alerting threshold for a percentage of rows with valid values in a column (from the set of expected values). Raises a data quality issue with at a fatal severity level when the percentage of valid rows is below the minimum percentage threshold|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericNumberValueInSetPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows for which the tested numeric column contains a value from the list of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 This sensor is useful for checking numeric columns that store numeric codes (such as status codes) that the only values found in the column are from a set of expected values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|expected_values|A list of expected values that must be present in a numeric column, only values from this list are accepted and rows having these values in the tested column are counted as valid rows.|integer_list| | |2<br/>3<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValuesInRangeNumericPercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of values from range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvaluesinrangenumericpercentsensorparametersspec)|Data quality check parameters|[ColumnNumericValuesInRangeNumericPercentSensorParametersSpec](#columnnumericvaluesinrangenumericpercentsensorparametersspec)| | | |
|[warning](#minpercentrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule0ParametersSpec](#minpercentrule0parametersspec)| | | |
|[error](#minpercentrule2parametersspec)|Default alerting threshold for set percentage of values from range in a column that raises a data quality error (alert).|[MinPercentRule2ParametersSpec](#minpercentrule2parametersspec)| | | |
|[fatal](#minpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule5ParametersSpec](#minpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValuesInRangeNumericPercentSensorParametersSpec  
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_value|Minimal value range variable.|double| | | |
|max_value|Maximal value range variable.|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MinPercentRule0ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MinPercentRule2ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## MinPercentRule5ParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |









___  

## ColumnValuesInRangeIntegersPercentCheckSpec  
Column level check that ensures that there are no more than a set number of values from range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvaluesinrangeintegerspercentsensorparametersspec)|Data quality check parameters|[ColumnNumericValuesInRangeIntegersPercentSensorParametersSpec](#columnnumericvaluesinrangeintegerspercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a set number of values from range in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValuesInRangeIntegersPercentSensorParametersSpec  
Column level sensor that finds the maximum value. It works on any data type that supports the MAX functions.
 The returned data type matches the data type of the column (it could return date, integer, string, datetime, etc.).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_value|Minimal value range variable.|long| | | |
|max_value|Maximal value range variable.|long| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValueBelowMinValueCountCheckSpec  
Column level check that ensures that the number of values in the monitored column with a value below the value defined by the user as a parameter does not exceed set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvaluebelowminvaluecountsensorparametersspec)|Data quality check parameters|[ColumnNumericValueBelowMinValueCountSensorParametersSpec](#columnnumericvaluebelowminvaluecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with values with a value below the indicated by the user value in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValueBelowMinValueCountSensorParametersSpec  
Column level sensor that calculates the count of values that are below than a given value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_value|This field can be used to define custom value. In order to define custom value, user should write correct value as an integer. If value is not defined by user then default value is 0|integer| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValueBelowMinValuePercentCheckSpec  
Column level check that ensures that the percentage of values in the monitored column with a value below the value defined by the user as a parameter does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvaluebelowminvaluepercentsensorparametersspec)|Data quality check parameters|[ColumnNumericValueBelowMinValuePercentSensorParametersSpec](#columnnumericvaluebelowminvaluepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule100ParametersSpec](#maxpercentrule100parametersspec)| | | |
|[error](#maxpercentrule99parametersspec)|Default alerting threshold for a maximum number of rows with values with a value below the indicated by the user value in a column that raises a data quality error (alert).|[MaxPercentRule99ParametersSpec](#maxpercentrule99parametersspec)| | | |
|[fatal](#maxpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule95ParametersSpec](#maxpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValueBelowMinValuePercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that are below than a given value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_value|This field can be used to define custom value. In order to define custom value, user should write correct value as an integer. If value is not defined by user then default value is 0|integer| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValueAboveMaxValueCountCheckSpec  
Column level check that ensures that the number of values in the monitored column with a value above the value defined by the user as a parameter does not exceed set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvalueabovemaxvaluecountsensorparametersspec)|Data quality check parameters|[ColumnNumericValueAboveMaxValueCountSensorParametersSpec](#columnnumericvalueabovemaxvaluecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with values with a value above the indicated by the user value in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValueAboveMaxValueCountSensorParametersSpec  
Column level sensor that calculates the count of values that are above than a given value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_value|This field can be used to define custom value. In order to define custom value, user should write correct value as an integer. If value is not defined by user then default value is 0|integer| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValueAboveMaxValuePercentCheckSpec  
Column level check that ensures that the percentage of values in the monitored column with a value above the value defined by the user as a parameter does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvalueabovemaxvaluepercentsensorparametersspec)|Data quality check parameters|[ColumnNumericValueAboveMaxValuePercentSensorParametersSpec](#columnnumericvalueabovemaxvaluepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule100ParametersSpec](#maxpercentrule100parametersspec)| | | |
|[error](#maxpercentrule99parametersspec)|Default alerting threshold for a maximum number of rows with values with a value above the indicated by the user value in a column that raises a data quality error (alert).|[MaxPercentRule99ParametersSpec](#maxpercentrule99parametersspec)| | | |
|[fatal](#maxpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule95ParametersSpec](#maxpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValueAboveMaxValuePercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that are above than a given value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_value|This field can be used to define custom value. In order to define custom value, user should write correct value as an integer. If value is not defined by user then default value is 0|integer| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnMaxInRangeCheckSpec  
Column level check that ensures that the maximal values are in a set range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmaxsensorparametersspec)|Data quality check parameters|[ColumnNumericMaxSensorParametersSpec](#columnnumericmaxsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a maximal values in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericMaxSensorParametersSpec  
Column level sensor that counts maximum value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## BetweenFloatsRuleParametersSpec  
Data quality rule that verifies if a data quality check readout is between from and to values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|from|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | |10.0<br/>|
|to|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | |20.5<br/>|









___  

## ColumnMinInRangeCheckSpec  
Column level check that ensures that the minimal values are in a set range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericminsensorparametersspec)|Data quality check parameters|[ColumnNumericMinSensorParametersSpec](#columnnumericminsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a minimal values in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericMinSensorParametersSpec  
Column level sensor that counts minimum value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnMeanInRangeCheckSpec  
Column level check that ensures that the average (mean) of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a mean in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericMeanSensorParametersSpec  
Column level sensor that counts the average (mean) of values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPercentileInRangeCheckSpec  
Column level check that ensures that the percentile of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpercentilesensorparametersspec)|Data quality check parameters|[ColumnNumericPercentileSensorParametersSpec](#columnnumericpercentilesensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a percentile in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPercentileSensorParametersSpec  
Column level sensor that finds the percentile in a given column. The percentile value is defined by the user. It works only on numeric, big numeric and float data types.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|Must be a literal in the range [0, 1].|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnMedianInRangeCheckSpec  
Column level check that ensures that the median of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a median in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericMedianSensorParametersSpec  
Column level sensor that finds the median in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|Median (50th percentile), must equal 0.5|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPercentile10InRangeCheckSpec  
Column level check that ensures that the percentile 10 of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpercentile10sensorparametersspec)|Data quality check parameters|[ColumnNumericPercentile10SensorParametersSpec](#columnnumericpercentile10sensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a percentile 10 in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPercentile10SensorParametersSpec  
Column level sensor that finds the percentile 10 in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|10th percentile, must equal 0.1|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPercentile25InRangeCheckSpec  
Column level check that ensures that the percentile 25 of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpercentile25sensorparametersspec)|Data quality check parameters|[ColumnNumericPercentile25SensorParametersSpec](#columnnumericpercentile25sensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a percentile 25 in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPercentile25SensorParametersSpec  
Column level sensor that finds the percentile 25 in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|25th percentile, must equal 0.25|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPercentile75InRangeCheckSpec  
Column level check that ensures that the percentile 75 of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpercentile75sensorparametersspec)|Data quality check parameters|[ColumnNumericPercentile75SensorParametersSpec](#columnnumericpercentile75sensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a percentile 75 in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPercentile75SensorParametersSpec  
Column level sensor that finds the percentile 75 in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|75th percentile, must equal 0.75|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPercentile90InRangeCheckSpec  
Column level check that ensures that the percentile 90 of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpercentile90sensorparametersspec)|Data quality check parameters|[ColumnNumericPercentile90SensorParametersSpec](#columnnumericpercentile90sensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a percentile 90 in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPercentile90SensorParametersSpec  
Column level sensor that finds the percentile 90 in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|percentile_value|90th percentile, must equal 0.9|double| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnSampleStddevInRangeCheckSpec  
Column level check that ensures the sample standard deviation is in a set range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsamplestddevsensorparametersspec)|Data quality check parameters|[ColumnNumericSampleStddevSensorParametersSpec](#columnnumericsamplestddevsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a sample (unbiased) maximal values in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericSampleStddevSensorParametersSpec  
Column level sensor that calculates sample standard deviation in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPopulationStddevInRangeCheckSpec  
Column level check that ensures that the population standard deviation is in a set range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpopulationstddevsensorparametersspec)|Data quality check parameters|[ColumnNumericPopulationStddevSensorParametersSpec](#columnnumericpopulationstddevsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a population (biased) standard deviation in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPopulationStddevSensorParametersSpec  
Column level sensor that calculates population standard deviation in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnSampleVarianceInRangeCheckSpec  
Column level check that ensures the sample variance is in a set range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsamplevariancesensorparametersspec)|Data quality check parameters|[ColumnNumericSampleVarianceSensorParametersSpec](#columnnumericsamplevariancesensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a sample (unbiased) maximal values in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericSampleVarianceSensorParametersSpec  
Column level sensor that calculates sample variance in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPopulationVarianceInRangeCheckSpec  
Column level check that ensures that the population variance is in a set range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericpopulationvariancesensorparametersspec)|Data quality check parameters|[ColumnNumericPopulationVarianceSensorParametersSpec](#columnnumericpopulationvariancesensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a population (biased) standard deviation in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericPopulationVarianceSensorParametersSpec  
Column level sensor that calculates population variance in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnSumInRangeCheckSpec  
Column level check that ensures that the sum of values in a monitored column is in a set range.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a sum in range in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericSumSensorParametersSpec  
Column level sensor that counts the sum of values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnInvalidLatitudeCountCheckSpec  
Column level check that ensures that there are no more than a set number of invalid latitude values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericinvalidlatitudecountsensorparametersspec)|Data quality check parameters|[ColumnNumericInvalidLatitudeCountSensorParametersSpec](#columnnumericinvalidlatitudecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a set number of rows with invalid latitude value in a column that raises a data quality alert|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericInvalidLatitudeCountSensorParametersSpec  
Column level sensor that counts invalid latitude in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValidLatitudePercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of valid latitude values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvalidlatitudepercentsensorparametersspec)|Data quality check parameters|[ColumnNumericValidLatitudePercentSensorParametersSpec](#columnnumericvalidlatitudepercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a set percentage of rows with valid latitude value in a column that raises a data quality alert|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValidLatitudePercentSensorParametersSpec  
Column level sensor that counts percentage of valid latitude in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnInvalidLongitudeCountCheckSpec  
Column level check that ensures that there are no more than a set number of invalid longitude values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericinvalidlongitudecountsensorparametersspec)|Data quality check parameters|[ColumnNumericInvalidLongitudeCountSensorParametersSpec](#columnnumericinvalidlongitudecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a set number of rows with invalid longitude value in a column that raises a data quality alert|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericInvalidLongitudeCountSensorParametersSpec  
Column level sensor that counts invalid longitude in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnValidLongitudePercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of valid longitude values in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericvalidlongitudepercentsensorparametersspec)|Data quality check parameters|[ColumnNumericValidLongitudePercentSensorParametersSpec](#columnnumericvalidlongitudepercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a set percentage of rows with valid longitude value in a column that raises a data quality alert|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnNumericValidLongitudePercentSensorParametersSpec  
Column level sensor that counts percentage of valid longitude in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringsProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for string.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[string_max_length](#columnstringmaxlengthcheckspec)|Verifies that the length of string in a column does not exceed the maximum accepted length.|[ColumnStringMaxLengthCheckSpec](#columnstringmaxlengthcheckspec)| | | |
|[string_min_length](#columnstringminlengthcheckspec)|Verifies that the length of string in a column does not fall below the minimum accepted length.|[ColumnStringMinLengthCheckSpec](#columnstringminlengthcheckspec)| | | |
|[string_mean_length](#columnstringmeanlengthcheckspec)|Verifies that the length of string in a column does not exceed the mean accepted length.|[ColumnStringMeanLengthCheckSpec](#columnstringmeanlengthcheckspec)| | | |
|[string_length_below_min_length_count](#columnstringlengthbelowminlengthcountcheckspec)|The check counts the number of strings in the column that is below the length defined by the user as a parameter.|[ColumnStringLengthBelowMinLengthCountCheckSpec](#columnstringlengthbelowminlengthcountcheckspec)| | | |
|[string_length_below_min_length_percent](#columnstringlengthbelowminlengthpercentcheckspec)|The check counts the percentage of strings in the column that is below the length defined by the user as a parameter.|[ColumnStringLengthBelowMinLengthPercentCheckSpec](#columnstringlengthbelowminlengthpercentcheckspec)| | | |
|[string_length_above_max_length_count](#columnstringlengthabovemaxlengthcountcheckspec)|The check counts the number of strings in the column that is above the length defined by the user as a parameter.|[ColumnStringLengthAboveMaxLengthCountCheckSpec](#columnstringlengthabovemaxlengthcountcheckspec)| | | |
|[string_length_above_max_length_percent](#columnstringlengthabovemaxlengthpercentcheckspec)|The check counts the percentage of strings in the column that is above the length defined by the user as a parameter.|[ColumnStringLengthAboveMaxLengthPercentCheckSpec](#columnstringlengthabovemaxlengthpercentcheckspec)| | | |
|[string_length_in_range_percent](#columnstringlengthinrangepercentcheckspec)|The check counts the percentage of those strings with length in the range provided by the user in the column. |[ColumnStringLengthInRangePercentCheckSpec](#columnstringlengthinrangepercentcheckspec)| | | |
|[string_empty_count](#columnstringemptycountcheckspec)|Verifies that empty strings in a column does not exceed the maximum accepted count.|[ColumnStringEmptyCountCheckSpec](#columnstringemptycountcheckspec)| | | |
|[string_empty_percent](#columnstringemptypercentcheckspec)|Verifies that the percentage of empty strings in a column does not exceed the maximum accepted percentage.|[ColumnStringEmptyPercentCheckSpec](#columnstringemptypercentcheckspec)| | | |
|[string_whitespace_count](#columnstringwhitespacecountcheckspec)|Verifies that the number of whitespace strings in a column does not exceed the maximum accepted count.|[ColumnStringWhitespaceCountCheckSpec](#columnstringwhitespacecountcheckspec)| | | |
|[string_whitespace_percent](#columnstringwhitespacepercentcheckspec)|Verifies that the percentage of whitespace strings in a column does not exceed the minimum accepted percentage.|[ColumnStringWhitespacePercentCheckSpec](#columnstringwhitespacepercentcheckspec)| | | |
|[string_surrounded_by_whitespace_count](#columnstringsurroundedbywhitespacecountcheckspec)|Verifies that the number of strings surrounded by whitespace in a column does not exceed the maximum accepted count.|[ColumnStringSurroundedByWhitespaceCountCheckSpec](#columnstringsurroundedbywhitespacecountcheckspec)| | | |
|[string_surrounded_by_whitespace_percent](#columnstringsurroundedbywhitespacepercentcheckspec)|Verifies that the percentage of strings surrounded by whitespace in a column does not exceed the maximum accepted percentage.|[ColumnStringSurroundedByWhitespacePercentCheckSpec](#columnstringsurroundedbywhitespacepercentcheckspec)| | | |
|[string_null_placeholder_count](#columnstringnullplaceholdercountcheckspec)|Verifies that the number of null placeholders in a column does not exceed the maximum accepted count.|[ColumnStringNullPlaceholderCountCheckSpec](#columnstringnullplaceholdercountcheckspec)| | | |
|[string_null_placeholder_percent](#columnstringnullplaceholderpercentcheckspec)|Verifies that the percentage of null placeholders in a column does not exceed the maximum accepted percentage.|[ColumnStringNullPlaceholderPercentCheckSpec](#columnstringnullplaceholderpercentcheckspec)| | | |
|[string_boolean_placeholder_percent](#columnstringbooleanplaceholderpercentcheckspec)|Verifies that the percentage of boolean placeholder for strings in a column does not fall below the minimum accepted percentage.|[ColumnStringBooleanPlaceholderPercentCheckSpec](#columnstringbooleanplaceholderpercentcheckspec)| | | |
|[string_parsable_to_integer_percent](#columnstringparsabletointegerpercentcheckspec)|Verifies that the percentage of parsable to integer string in a column does not fall below the minimum accepted percentage.|[ColumnStringParsableToIntegerPercentCheckSpec](#columnstringparsabletointegerpercentcheckspec)| | | |
|[string_parsable_to_float_percent](#columnstringparsabletofloatpercentcheckspec)|Verifies that the percentage of parsable to float string in a column does not fall below the minimum accepted percentage.|[ColumnStringParsableToFloatPercentCheckSpec](#columnstringparsabletofloatpercentcheckspec)| | | |
|[expected_strings_in_use_count](#columnexpectedstringsinusecountcheckspec)|Verifies that the expected string values were found in the column. Raises a data quality issue when too many expected values were not found (were missing).|[ColumnExpectedStringsInUseCountCheckSpec](#columnexpectedstringsinusecountcheckspec)| | | |
|[string_value_in_set_percent](#columnstringvalueinsetpercentcheckspec)|The check measures the percentage of rows whose value in a tested column is one of values from a list of expected values or the column value is null. Verifies that the percentage of rows having a valid column value does not exceed the minimum accepted percentage.|[ColumnStringValueInSetPercentCheckSpec](#columnstringvalueinsetpercentcheckspec)| | | |
|[string_valid_dates_percent](#columnstringvaliddatespercentcheckspec)|Verifies that the percentage of valid dates in a column does not fall below the minimum accepted percentage.|[ColumnStringValidDatesPercentCheckSpec](#columnstringvaliddatespercentcheckspec)| | | |
|[string_valid_country_code_percent](#columnstringvalidcountrycodepercentcheckspec)|Verifies that the percentage of valid country code in a column does not fall below the minimum accepted percentage.|[ColumnStringValidCountryCodePercentCheckSpec](#columnstringvalidcountrycodepercentcheckspec)| | | |
|[string_valid_currency_code_percent](#columnstringvalidcurrencycodepercentcheckspec)|Verifies that the percentage of valid currency code in a column does not fall below the minimum accepted percentage.|[ColumnStringValidCurrencyCodePercentCheckSpec](#columnstringvalidcurrencycodepercentcheckspec)| | | |
|[string_invalid_email_count](#columnstringinvalidemailcountcheckspec)|Verifies that the number of invalid emails in a column does not exceed the maximum accepted count.|[ColumnStringInvalidEmailCountCheckSpec](#columnstringinvalidemailcountcheckspec)| | | |
|[string_invalid_uuid_count](#columnstringinvaliduuidcountcheckspec)|Verifies that the number of invalid UUID in a column does not exceed the maximum accepted count.|[ColumnStringInvalidUuidCountCheckSpec](#columnstringinvaliduuidcountcheckspec)| | | |
|[string_valid_uuid_percent](#columnstringvaliduuidpercentcheckspec)|Verifies that the percentage of valid UUID in a column does not fall below the minimum accepted percentage.|[ColumnStringValidUuidPercentCheckSpec](#columnstringvaliduuidpercentcheckspec)| | | |
|[string_invalid_ip4_address_count](#columnstringinvalidip4addresscountcheckspec)|Verifies that the number of invalid IP4 address in a column does not exceed the maximum accepted count.|[ColumnStringInvalidIp4AddressCountCheckSpec](#columnstringinvalidip4addresscountcheckspec)| | | |
|[string_invalid_ip6_address_count](#columnstringinvalidip6addresscountcheckspec)|Verifies that the number of invalid IP6 address in a column does not exceed the maximum accepted count.|[ColumnStringInvalidIp6AddressCountCheckSpec](#columnstringinvalidip6addresscountcheckspec)| | | |
|[string_not_match_regex_count](#columnstringnotmatchregexcountcheckspec)|Verifies that the number of strings not matching the custom regex in a column does not exceed the maximum accepted count.|[ColumnStringNotMatchRegexCountCheckSpec](#columnstringnotmatchregexcountcheckspec)| | | |
|[string_match_regex_percent](#columnstringmatchregexpercentcheckspec)|Verifies that the percentage of strings matching the custom regex in a column does not fall below the minimum accepted percentage.|[ColumnStringMatchRegexPercentCheckSpec](#columnstringmatchregexpercentcheckspec)| | | |
|[string_not_match_date_regex_count](#columnstringnotmatchdateregexcountcheckspec)|Verifies that the number of strings not matching the date format regex in a column does not exceed the maximum accepted count.|[ColumnStringNotMatchDateRegexCountCheckSpec](#columnstringnotmatchdateregexcountcheckspec)| | | |
|[string_match_date_regex_percent](#columnstringmatchdateregexpercentcheckspec)|Verifies that the percentage of strings matching the date format regex in a column does not fall below the minimum accepted percentage.|[ColumnStringMatchDateRegexPercentCheckSpec](#columnstringmatchdateregexpercentcheckspec)| | | |
|[string_match_name_regex_percent](#columnstringmatchnameregexpercentcheckspec)|Verifies that the percentage of strings matching the name regex in a column does not fall below the minimum accepted percentage.|[ColumnStringMatchNameRegexPercentCheckSpec](#columnstringmatchnameregexpercentcheckspec)| | | |
|[expected_strings_in_top_values_count](#columnexpectedstringsintopvaluescountcheckspec)|Verifies that the top X most popular column values contain all values from a list of expected values.|[ColumnExpectedStringsInTopValuesCountCheckSpec](#columnexpectedstringsintopvaluescountcheckspec)| | | |
|[string_datatype_detected](#columnstringdatatypedetectedcheckspec)|Detects the data type of text values stored in the column. The sensor returns the code of the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types. Raises a data quality issue when the detected data type does not match the expected data type.|[ColumnStringDatatypeDetectedCheckSpec](#columnstringdatatypedetectedcheckspec)| | | |









___  

## ColumnStringMaxLengthCheckSpec  
Column level check that ensures that the length of string in a column does not exceed the maximum accepted length.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringmaxlengthsensorparametersspec)|Data quality check parameters|[ColumnStringsStringMaxLengthSensorParametersSpec](#columnstringsstringmaxlengthsensorparametersspec)| | | |
|[warning](#maxvalueruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxValueRuleParametersSpec](#maxvalueruleparametersspec)| | | |
|[error](#maxvalueruleparametersspec)|Default alerting threshold for a maximum number of rows with nulls in a column that raises a data quality error (alert).|[MaxValueRuleParametersSpec](#maxvalueruleparametersspec)| | | |
|[fatal](#maxvalueruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxValueRuleParametersSpec](#maxvalueruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringMaxLengthSensorParametersSpec  
Column level sensor that ensures that the length of string in a column does not exceed the maximum accepted length.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringMinLengthCheckSpec  
Column level check that ensures that the length of string in a column does not fall below the minimum accepted length.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringminlengthsensorparametersspec)|Data quality check parameters|[ColumnStringsStringMinLengthSensorParametersSpec](#columnstringsstringminlengthsensorparametersspec)| | | |
|[warning](#minvalueruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinValueRuleParametersSpec](#minvalueruleparametersspec)| | | |
|[error](#minvalueruleparametersspec)|Default alerting threshold for a minimum length of string in a column that raises a data quality error (alert).|[MinValueRuleParametersSpec](#minvalueruleparametersspec)| | | |
|[fatal](#minvalueruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinValueRuleParametersSpec](#minvalueruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringMinLengthSensorParametersSpec  
Column level sensor that ensures that the length of string in a column does not exceed the minimum accepted length.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## MinValueRuleParametersSpec  
Data quality rule that verifies if a data quality check readout is greater or equal a minimum value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_value|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | |1.5<br/>|









___  

## ColumnStringMeanLengthCheckSpec  
Column level check that ensures that the length of string in a column does not exceed the mean accepted length.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringmeanlengthsensorparametersspec)|Data quality check parameters|[ColumnStringsStringMeanLengthSensorParametersSpec](#columnstringsstringmeanlengthsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for a maximum number of rows with nulls in a column that raises a data quality error (alert).|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringMeanLengthSensorParametersSpec  
Column level sensor that ensures that the length of string in a column does not exceed the mean accepted length.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringLengthBelowMinLengthCountCheckSpec  
Column level check that ensures that the number of strings in the monitored column with a length below the length defined by the user as a parameter does not exceed set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringlengthbelowminlengthcountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringLengthBelowMinLengthCountSensorParametersSpec](#columnstringsstringlengthbelowminlengthcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with strings with a length below the indicated by the user length in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringLengthBelowMinLengthCountSensorParametersSpec  
Column level sensor that calculates the count of values that are shorter than a given length in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer| | |5<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringLengthBelowMinLengthPercentCheckSpec  
Column level check that ensures that the percentage of strings in the monitored column with a length below the length defined by the user as a parameter does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringlengthbelowminlengthpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringLengthBelowMinLengthPercentSensorParametersSpec](#columnstringsstringlengthbelowminlengthpercentsensorparametersspec)| | | |
|[warning](#maxpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule100ParametersSpec](#maxpercentrule100parametersspec)| | | |
|[error](#maxpercentrule99parametersspec)|Default alerting threshold for a maximum percentage of rows with strings with a length below the indicated by the user length in a column that raises a data quality error (alert).|[MaxPercentRule99ParametersSpec](#maxpercentrule99parametersspec)| | | |
|[fatal](#maxpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule95ParametersSpec](#maxpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringLengthBelowMinLengthPercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that are shorter than a given length in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer| | |5<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringLengthAboveMaxLengthCountCheckSpec  
Column level check that ensures that the number of strings in the monitored column with a length above the length defined by the user as a parameter does not exceed set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringlengthabovemaxlengthcountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringLengthAboveMaxLengthCountSensorParametersSpec](#columnstringsstringlengthabovemaxlengthcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with strings with a length above the indicated by the user length in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringLengthAboveMaxLengthCountSensorParametersSpec  
Column level sensor that calculates the count of values that are longer than a given length in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer| | |5<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringLengthAboveMaxLengthPercentCheckSpec  
Column level check that ensures that the percentage of strings in the monitored column with a length above the length defined by the user as a parameter does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringlengthabovemaxlengthpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringLengthAboveMaxLengthPercentSensorParametersSpec](#columnstringsstringlengthabovemaxlengthpercentsensorparametersspec)| | | |
|[warning](#maxpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule100ParametersSpec](#maxpercentrule100parametersspec)| | | |
|[error](#maxpercentrule99parametersspec)|Default alerting threshold for a maximum percentage of rows with strings with a length above the indicated by the user length in a column that raises a data quality error (alert).|[MaxPercentRule99ParametersSpec](#maxpercentrule99parametersspec)| | | |
|[fatal](#maxpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule95ParametersSpec](#maxpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringLengthAboveMaxLengthPercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that are longer than a given length in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_length|This field can be used to define custom length. In order to define custom length, user should write correct length as a integer. If length is not defined by user then default length is 0|integer| | |5<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringLengthInRangePercentCheckSpec  
Column check that calculates percentage of strings with a length below the indicated by the user length in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringlengthinrangepercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringLengthInRangePercentSensorParametersSpec](#columnstringsstringlengthinrangepercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a maximum percentage of rows with strings with a length in the range indicated by the user in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringLengthInRangePercentSensorParametersSpec  
Column level sensor that calculates percentage of strings with a length below the indicated length in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_length|Sets a minimal string length|integer| | |5<br/>|
|max_length|Sets a maximal string length.|integer| | |10<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringEmptyCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of empty strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringemptycountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringEmptyCountSensorParametersSpec](#columnstringsstringemptycountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with empty strings in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringEmptyCountSensorParametersSpec  
Column level sensor that calculates the number of rows with an empty string.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringEmptyPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percent of empty strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringemptypercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringEmptyPercentSensorParametersSpec](#columnstringsstringemptypercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a maximum number of rows with empty strings in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringEmptyPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with an empty string.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringWhitespaceCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of whitespace strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringwhitespacecountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringWhitespaceCountSensorParametersSpec](#columnstringsstringwhitespacecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with whitespace strings in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringWhitespaceCountSensorParametersSpec  
Column level sensor that calculates the number of rows with an whitespace string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringWhitespacePercentCheckSpec  
Column level check that ensures that there are no more than a maximum percent of whitespace strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringwhitespacepercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringWhitespacePercentSensorParametersSpec](#columnstringsstringwhitespacepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a maximum number of rows with whitespace strings in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringWhitespacePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a whitespace string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringSurroundedByWhitespaceCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of surrounded by whitespace strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringsurroundedbywhitespacecountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringSurroundedByWhitespaceCountSensorParametersSpec](#columnstringsstringsurroundedbywhitespacecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with surrounded by whitespace strings in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringSurroundedByWhitespaceCountSensorParametersSpec  
Column level sensor that calculates the number of rows with string surrounded by whitespace column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringSurroundedByWhitespacePercentCheckSpec  
Column level check that ensures that there are no more than a maximum percent of surrounded by whitespace strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringsurroundedbywhitespacepercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringSurroundedByWhitespacePercentSensorParametersSpec](#columnstringsstringsurroundedbywhitespacepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a maximum number of rows with surrounded by whitespace strings in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringSurroundedByWhitespacePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with string surrounded by whitespace column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringNullPlaceholderCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of rows with a null placeholder strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringnullplaceholdercountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringNullPlaceholderCountSensorParametersSpec](#columnstringsstringnullplaceholdercountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with a null placeholder strings in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringNullPlaceholderCountSensorParametersSpec  
Column level sensor that calculates the number of rows with a null placeholder string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringNullPlaceholderPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percent of rows with a null placeholder strings in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringnullplaceholderpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringNullPlaceholderPercentSensorParametersSpec](#columnstringsstringnullplaceholderpercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of rows with a null placeholder strings in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringNullPlaceholderPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a null placeholder string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringBooleanPlaceholderPercentCheckSpec  
Column level check that ensures that the percentage of boolean placeholder strings in the monitored column does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringbooleanplaceholderpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringBooleanPlaceholderPercentSensorParametersSpec](#columnstringsstringbooleanplaceholderpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows with a boolean placeholder strings in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringBooleanPlaceholderPercentSensorParametersSpec  
Column level sensor that calculates the number of rows with a boolean placeholder string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringParsableToIntegerPercentCheckSpec  
Column level check that ensures that the percentage of parsable to integer strings in the monitored column does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringparsabletointegerpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringParsableToIntegerPercentSensorParametersSpec](#columnstringsstringparsabletointegerpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows with a parsable to integer strings in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringParsableToIntegerPercentSensorParametersSpec  
Column level sensor that calculates the number of rows with parsable to integer string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringParsableToFloatPercentCheckSpec  
Column level check that ensures that the percentage of parsable to float strings in the monitored column does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringparsabletofloatpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringParsableToFloatPercentSensorParametersSpec](#columnstringsstringparsabletofloatpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows with a parsable to float strings in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringParsableToFloatPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with parsable to float string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnExpectedStringsInUseCountCheckSpec  
Column level check that counts unique values in a string column and counts how many values out of a list of expected string values were found in the column.
 The check raises a data quality issue when the threshold of maximum number of missing values was exceeded (too many expected values were not found in the column).
 This check is useful for analysing columns with a low number of unique values, such as status codes, to detect that all status codes are in use in any row.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsexpectedstringsinusecountsensorparametersspec)|Data quality check parameters that specify a list of expected string values that must be present in the column.|[ColumnStringsExpectedStringsInUseCountSensorParametersSpec](#columnstringsexpectedstringsinusecountsensorparametersspec)| | | |
|[warning](#maxmissingrule0parametersspec)|Alerting threshold that raises a data quality warning when too many expected values were not found in the column.|[MaxMissingRule0ParametersSpec](#maxmissingrule0parametersspec)| | | |
|[error](#maxmissingrule1parametersspec)|Alerting threshold that raises a data quality error when too many expected values were not found in the column.|[MaxMissingRule1ParametersSpec](#maxmissingrule1parametersspec)| | | |
|[fatal](#maxmissingrule2parametersspec)|Alerting threshold that raises a data quality fatal issue when too many expected values were not found in the column.|[MaxMissingRule2ParametersSpec](#maxmissingrule2parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsExpectedStringsInUseCountSensorParametersSpec  
Column level sensor that counts how many expected string values are used in a tested column. Finds unique column values from the set of expected string values and counts them.
 This sensor is useful to analyze string columns that have a low number of unique values and it should be tested if all possible values from the list of expected values are used in any row.
 The typical type of columns analyzed using this sensor are currency, country, status or gender columns.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|expected_values|List of expected string values that should be found in the tested column.|string_list| | |USD<br/>GBP<br/>EUR<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringValueInSetPercentCheckSpec  
Column level check that calculates the percentage of rows for which the tested string column contains a value from the set of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 The check raises a data quality issue when the percentage of rows with a not null column value that is not expected (not one of the values in the expected_values set)
 is below an expected threshold, for example 99% of rows should have values from the defined domain.
 This data quality check is useful for checking string columns that have a low number of unique values and all the values should be from a set of expected values.
 For example, testing a country, state, currency, gender, type, department columns whose expected values are known.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringvalueinsetpercentsensorparametersspec)|Data quality check parameters that specify a list of expected values that are compared to the values in the tested string column.|[ColumnStringsStringValueInSetPercentSensorParametersSpec](#columnstringsstringvalueinsetpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Default alerting threshold for a percentage of rows with valid values in a column (from the set of expected values). Raises a data quality issue with at a warning severity level when the percentage of valid rows is below the minimum percentage threshold.|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a percentage of rows with valid values in a column (from the set of expected values). Raises a data quality issue with at an error severity level when the percentage of valid rows is below the minimum percentage threshold.|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Default alerting threshold for a percentage of rows with valid values in a column (from the set of expected values). Raises a data quality issue with at a fatal severity level when the percentage of valid rows is below the minimum percentage threshold.|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringValueInSetPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows for which the tested string (text) column contains a value from the list of expected values.
 Columns with null values are also counted as a passing value (the sensor assumes that a &#x27;null&#x27; is also an expected and accepted value).
 This sensor is useful for testing that a string column with a low number of unique values (country, currency, state, gender, etc.) contains only values from a set of expected values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|expected_values|A list of expected values that must be present in a string column, only values from this list are accepted and rows having these values in the tested column are counted as valid rows.|string_list| | |USD<br/>GBP<br/>EUR<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringValidDatesPercentCheckSpec  
Column level check that ensures that there is at least a minimum percentage of valid dates in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringvaliddatepercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringValidDatePercentSensorParametersSpec](#columnstringsstringvaliddatepercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a maximum number of rows with nulls in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringValidDatePercentSensorParametersSpec  
Column level sensor that ensures that there is at least a minimum percentage of valid dates in a monitored column..  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringValidCountryCodePercentCheckSpec  
Column level check that ensures that the percentage of valid country code strings in the monitored column does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringvalidcountrycodepercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringValidCountryCodePercentSensorParametersSpec](#columnstringsstringvalidcountrycodepercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a maximum percentage of rows with a valid country code strings in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringValidCountryCodePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid country code string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringValidCurrencyCodePercentCheckSpec  
Column level check that ensures that the percentage of valid currency code strings in the monitored column does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringvalidcurrencycodepercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringValidCurrencyCodePercentSensorParametersSpec](#columnstringsstringvalidcurrencycodepercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a maximum percentage of rows with a valid currency code strings in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringValidCurrencyCodePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid currency code string column value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringInvalidEmailCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of invalid email in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringinvalidemailcountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringInvalidEmailCountSensorParametersSpec](#columnstringsstringinvalidemailcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with invalid emails in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringInvalidEmailCountSensorParametersSpec  
Column level sensor that calculates the number of rows with an invalid emails value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringInvalidUuidCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of invalid UUID in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringinvaliduuidcountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringInvalidUuidCountSensorParametersSpec](#columnstringsstringinvaliduuidcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with invalid uuid in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringInvalidUuidCountSensorParametersSpec  
Column level sensor that calculates the number of rows with an invalid uuid value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringValidUuidPercentCheckSpec  
Column level check that ensures that the percentage of valid UUID strings in the monitored column does not fall below set thresholds.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringvaliduuidpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringValidUuidPercentSensorParametersSpec](#columnstringsstringvaliduuidpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows with a valid UUID in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringValidUuidPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid UUID value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringInvalidIp4AddressCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of invalid IP4 address in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringinvalidip4addresscountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringInvalidIp4AddressCountSensorParametersSpec](#columnstringsstringinvalidip4addresscountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with invalid IP4 address in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringInvalidIp4AddressCountSensorParametersSpec  
Column level sensor that calculates the number of rows with an invalid IP4 address value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringInvalidIp6AddressCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of invalid IP6 address in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringinvalidip6addresscountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringInvalidIp6AddressCountSensorParametersSpec](#columnstringsstringinvalidip6addresscountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with invalid IP6 address in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringInvalidIp6AddressCountSensorParametersSpec  
Column level sensor that calculates the number of rows with an invalid IP6 address value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringNotMatchRegexCountCheckSpec  
Column check that calculates quantity of values that does not match the custom regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringnotmatchregexcountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringNotMatchRegexCountSensorParametersSpec](#columnstringsstringnotmatchregexcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with not matching regex in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringNotMatchRegexCountSensorParametersSpec  
Column level sensor that calculates the number of values that does not fit to a regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|regex|This field can be used to define custom regex. In order to define custom regex, user should write correct regex as a string. If regex is not defined by user then default regex is null|string| | |^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringMatchRegexPercentCheckSpec  
Column check that calculates percentage of values that matches the custom regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringmatchregexpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringMatchRegexPercentSensorParametersSpec](#columnstringsstringmatchregexpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows with matching regex in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringMatchRegexPercentSensorParametersSpec  
Column level sensor that calculates the percent of values that fit to a regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|regex|This field can be used to define custom regex. In order to define custom regex, user should write correct regex as a string. If regex is not defined by user then default regex is null|string| | |^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringNotMatchDateRegexCountCheckSpec  
Column check that calculates quantity of values that does not match the date regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringnotmatchdateregexcountsensorparametersspec)|Data quality check parameters|[ColumnStringsStringNotMatchDateRegexCountSensorParametersSpec](#columnstringsstringnotmatchdateregexcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with not matching date regex in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringNotMatchDateRegexCountSensorParametersSpec  
Column level sensor that calculates the number of values that does not fit to a date regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|date_formats|Desired date format. Sensor will try to parse the column records and cast the data using this format.|enum|YYYY-MM-DD<br/>DD/MM/YYYY<br/>Month D, YYYY<br/>YYYY/MM/DD<br/>MM/DD/YYYY<br/>| | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringMatchDateRegexPercentCheckSpec  
Column check that calculates percentage of values that match the date regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringmatchdateregexpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringMatchDateRegexPercentSensorParametersSpec](#columnstringsstringmatchdateregexpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a maximum percentage of rows with matching date regex in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringMatchDateRegexPercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that does fit a given date regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|date_formats|Desired date format. Sensor will try to parse the column records and cast the data using this format.|enum|YYYY-MM-DD<br/>DD/MM/YYYY<br/>Month D, YYYY<br/>YYYY/MM/DD<br/>MM/DD/YYYY<br/>| | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringMatchNameRegexPercentCheckSpec  
Column check that calculates percentage of values that match the name regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringmatchnameregexpercentsensorparametersspec)|Data quality check parameters|[ColumnStringsStringMatchNameRegexPercentSensorParametersSpec](#columnstringsstringmatchnameregexpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a maximum percentage of rows with matching name regex in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringMatchNameRegexPercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that does fit a given name regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnExpectedStringsInTopValuesCountCheckSpec  
Column level check that counts how many expected string values are among the TOP most popular values in the column.
 The check will first count the number of occurrences of each column&#x27;s value and will pick the TOP X most popular values (configurable by the &#x27;top&#x27; parameter).
 Then, it will compare the list of most popular values to the given list of expected values that should be most popular.
 This check will verify how many supposed most popular values (provided in the &#x27;expected_values&#x27; list) were not found in the top X most popular values in the column.
 This check is useful for analyzing string columns that have several very popular values, these could be the country codes of the countries with the most number of customers.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsexpectedstringsintopvaluescountsensorparametersspec)|Data quality check parameters that specify a list of expected most popular string values that should be found in the column. The second parameter is &#x27;top&#x27;, which is the limit of the most popular column values to find in the tested column.|[ColumnStringsExpectedStringsInTopValuesCountSensorParametersSpec](#columnstringsexpectedstringsintopvaluescountsensorparametersspec)| | | |
|[warning](#maxmissingrule0parametersspec)|Alerting threshold that raises a data quality warning when too many expected values were not found among the TOP most popular values in the tested column.|[MaxMissingRule0ParametersSpec](#maxmissingrule0parametersspec)| | | |
|[error](#maxmissingrule1parametersspec)|Alerting threshold that raises a data quality error when too many expected values were not found among the TOP most popular values in the tested column.|[MaxMissingRule1ParametersSpec](#maxmissingrule1parametersspec)| | | |
|[fatal](#maxmissingrule2parametersspec)|Alerting threshold that raises a data quality fatal issue when too many expected values were not found among the TOP most popular values in the tested column.|[MaxMissingRule2ParametersSpec](#maxmissingrule2parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsExpectedStringsInTopValuesCountSensorParametersSpec  
Column level sensor that counts how many expected string values are among the TOP most popular values in the column.
 The sensor will first count the number of occurrences of each column&#x27;s value and will pick the TOP X most popular values (configurable by the &#x27;top&#x27; parameter).
 Then, it will compare the list of most popular values to the given list of expected values that should be most popular.
 This sensor will return the number of expected values that were found within the &#x27;top&#x27; most popular column values.
 This sensor is useful for analyzing string columns that have several very popular values, these could be the country codes of the countries with the most number of customers.
 The sensor can detect if any of the most popular value (an expected value) is no longer one of the top X most popular values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|expected_values|List of expected string values that should be found in the tested column among the TOP most popular (highest distinct count) column values.|string_list| | |USD<br/>GBP<br/>EUR<br/>|
|top|The number of the most popular values (with the highest distinct count) that are analyzed to find the expected values.|long| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringDatatypeDetectedCheckSpec  
Table level check that scans all values in a string column and detects the data type of all values in a column. The actual_value returned from the sensor is one of: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.
 The check compares the data type detected in all non-null columns to an expected data type. The rule compares the value using equals and requires values in the range 1..7, which are the codes of detected data types.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringdatatypedetectsensorparametersspec)|The sensor parameters for a sensor that returns a value that identifies the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|[ColumnStringsStringDatatypeDetectSensorParametersSpec](#columnstringsstringdatatypedetectsensorparametersspec)| | | |
|[warning](#datatypeequalsruleparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check, detects that the data type of values stored in a column matches an expected data type code (1..7).|[DatatypeEqualsRuleParametersSpec](#datatypeequalsruleparametersspec)| | | |
|[error](#datatypeequalsruleparametersspec)|Default alerting threshold for a row count that raises a data quality error (alert), detects that the data type of values stored in a column matches an expected data type code (1..7).|[DatatypeEqualsRuleParametersSpec](#datatypeequalsruleparametersspec)| | | |
|[fatal](#datatypeequalsruleparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem, detects that the data type of values stored in a column matches an expected data type code (1..7).|[DatatypeEqualsRuleParametersSpec](#datatypeequalsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnStringsStringDatatypeDetectSensorParametersSpec  
Column level sensor that analyzes all values in a text column and detects the data type of the values.
 The sensor returns a value that identifies the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## DatatypeEqualsRuleParametersSpec  
Data quality rule that verifies that a data quality check readout of a string_datatype_detect (the data type detection) matches an expected data type.
 The supported values are in the range 1..7, which are: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|expected_datatype|Expected data type code, the data type codes are: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|integer| | |1<br/>|









___  

## ColumnUniquenessProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for negative values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[distinct_count](#columndistinctcountcheckspec)|Verifies that the number of distinct values in a column does not fall below the minimum accepted count.|[ColumnDistinctCountCheckSpec](#columndistinctcountcheckspec)| | | |
|[distinct_percent](#columndistinctpercentcheckspec)|Verifies that the percentage of distinct values in a column does not fall below the minimum accepted percent.|[ColumnDistinctPercentCheckSpec](#columndistinctpercentcheckspec)| | | |
|[duplicate_count](#columnduplicatecountcheckspec)|Verifies that the number of duplicate values in a column does not exceed the maximum accepted count.|[ColumnDuplicateCountCheckSpec](#columnduplicatecountcheckspec)| | | |
|[duplicate_percent](#columnduplicatepercentcheckspec)|Verifies that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.|[ColumnDuplicatePercentCheckSpec](#columnduplicatepercentcheckspec)| | | |









___  

## ColumnDistinctCountCheckSpec  
Column level check that ensures that the number of unique values in a column does not fall below the minimum accepted count.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessdistinctcountsensorparametersspec)|Data quality check parameters|[ColumnUniquenessDistinctCountSensorParametersSpec](#columnuniquenessdistinctcountsensorparametersspec)| | | |
|[warning](#mincountrulewarningparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinCountRuleWarningParametersSpec](#mincountrulewarningparametersspec)| | | |
|[error](#mincountrule0parametersspec)|Default alerting threshold for a maximum number of rows with nulls in a column that raises a data quality error (alert).|[MinCountRule0ParametersSpec](#mincountrule0parametersspec)| | | |
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

## ColumnUniquenessDistinctCountSensorParametersSpec  
Column level sensor that calculates the number of unique non-null values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnDistinctPercentCheckSpec  
Column level check that ensures that the percentage of unique values in a column does not fall below the minimum accepted percentage.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessdistinctpercentsensorparametersspec)|Data quality check parameters|[ColumnUniquenessDistinctPercentSensorParametersSpec](#columnuniquenessdistinctpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows with unique value in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnUniquenessDistinctPercentSensorParametersSpec  
Column level sensor that calculates the percentage of unique values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnDuplicateCountCheckSpec  
Column level check that ensures that the number of duplicate values in a column does not exceed the maximum accepted count.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessduplicatecountsensorparametersspec)|Data quality check parameters|[ColumnUniquenessDuplicateCountSensorParametersSpec](#columnuniquenessduplicatecountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with nulls in a column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnUniquenessDuplicateCountSensorParametersSpec  
Column level sensor that calculates the number of duplicate values in a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnDuplicatePercentCheckSpec  
Column level check that ensures that the percentage of duplicate values in a column does not exceed the maximum accepted percentage.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnuniquenessduplicatepercentsensorparametersspec)|Data quality check parameters|[ColumnUniquenessDuplicatePercentSensorParametersSpec](#columnuniquenessduplicatepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a maximum number of rows with nulls in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnUniquenessDuplicatePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows that are duplicates.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnDatetimeProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for datetime.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[date_values_in_future_percent](#columndatevaluesinfuturepercentcheckspec)|Verifies that the percentage of date values in future in a column does not exceed the maximum accepted percentage.|[ColumnDateValuesInFuturePercentCheckSpec](#columndatevaluesinfuturepercentcheckspec)| | | |
|[datetime_value_in_range_date_percent](#columndatetimevalueinrangedatepercentcheckspec)|Verifies that the percentage of date values in the range defined by the user in a column does not exceed the maximum accepted percentage.|[ColumnDatetimeValueInRangeDatePercentCheckSpec](#columndatetimevalueinrangedatepercentcheckspec)| | | |









___  

## ColumnDateValuesInFuturePercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of date values in future in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columndatetimedatevaluesinfuturepercentsensorparametersspec)|Data quality check parameters|[ColumnDatetimeDateValuesInFuturePercentSensorParametersSpec](#columndatetimedatevaluesinfuturepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a set percentage of date values in future in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnDatetimeDateValuesInFuturePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a date value in the future, compared with the current date.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnDatetimeValueInRangeDatePercentCheckSpec  
Column level check that ensures that there are no more than a set percentage of date values in given range in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columndatetimevalueinrangedatepercentsensorparametersspec)|Data quality check parameters|[ColumnDatetimeValueInRangeDatePercentSensorParametersSpec](#columndatetimevalueinrangedatepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for a set percentage of date values in the range defined by the user in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnDatetimeValueInRangeDatePercentSensorParametersSpec  
Column level sensor that calculates the percent of non-negative values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_value|Lower bound range variable.|date| | | |
|max_value|Upper bound range variable.|date| | | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for Personal Identifiable Information (PII).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[valid_usa_phone_percent](#columnpiivalidusaphonepercentcheckspec)|Verifies that the percentage of valid USA phone values in a column does not fall below the minimum accepted percentage.|[ColumnPiiValidUsaPhonePercentCheckSpec](#columnpiivalidusaphonepercentcheckspec)| | | |
|[contains_usa_phone_percent](#columnpiicontainsusaphonepercentcheckspec)|Verifies that the percentage of rows that contains USA phone number in a column does not exceed the maximum accepted percentage.|[ColumnPiiContainsUsaPhonePercentCheckSpec](#columnpiicontainsusaphonepercentcheckspec)| | | |
|[valid_usa_zipcode_percent](#columnpiivalidusazipcodepercentcheckspec)|Verifies that the percentage of valid USA zip code values in a column does not fall below the minimum accepted percentage.|[ColumnPiiValidUsaZipcodePercentCheckSpec](#columnpiivalidusazipcodepercentcheckspec)| | | |
|[contains_usa_zipcode_percent](#columnpiicontainsusazipcodepercentcheckspec)|Verifies that the percentage of rows that contains USA zip code in a column does not exceed the maximum accepted percentage.|[ColumnPiiContainsUsaZipcodePercentCheckSpec](#columnpiicontainsusazipcodepercentcheckspec)| | | |
|[valid_email_percent](#columnpiivalidemailpercentcheckspec)|Verifies that the percentage of valid emails values in a column does not fall below the minimum accepted percentage.|[ColumnPiiValidEmailPercentCheckSpec](#columnpiivalidemailpercentcheckspec)| | | |
|[contains_email_percent](#columnpiicontainsemailpercentcheckspec)|Verifies that the percentage of rows that contains valid emails in a column does not exceed the minimum accepted percentage.|[ColumnPiiContainsEmailPercentCheckSpec](#columnpiicontainsemailpercentcheckspec)| | | |
|[valid_ip4_address_percent](#columnpiivalidip4addresspercentcheckspec)|Verifies that the percentage of valid IP4 address values in a column does not fall below the minimum accepted percentage.|[ColumnPiiValidIp4AddressPercentCheckSpec](#columnpiivalidip4addresspercentcheckspec)| | | |
|[contains_ip4_percent](#columnpiicontainsip4percentcheckspec)|Verifies that the percentage of rows that contains valid IP4 address values in a column does not fall below the minimum accepted percentage.|[ColumnPiiContainsIp4PercentCheckSpec](#columnpiicontainsip4percentcheckspec)| | | |
|[valid_ip6_address_percent](#columnpiivalidip6addresspercentcheckspec)|Verifies that the percentage of valid IP6 address values in a column does not fall below the minimum accepted percentage.|[ColumnPiiValidIp6AddressPercentCheckSpec](#columnpiivalidip6addresspercentcheckspec)| | | |
|[contains_ip6_percent](#columnpiicontainsip6percentcheckspec)|Verifies that the percentage of rows that contains valid IP6 address values in a column does not fall below the minimum accepted percentage.|[ColumnPiiContainsIp6PercentCheckSpec](#columnpiicontainsip6percentcheckspec)| | | |









___  

## ColumnPiiValidUsaPhonePercentCheckSpec  
Column check that calculates percent of valid USA phone values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiivalidusaphonepercentsensorparametersspec)|Numerical value in range percent sensor parameters|[ColumnPiiValidUsaPhonePercentSensorParametersSpec](#columnpiivalidusaphonepercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows with a parsable to integer strings in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiValidUsaPhonePercentSensorParametersSpec  
Column level sensor that calculates the percent of values that fit to a USA phone regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiContainsUsaPhonePercentCheckSpec  
Column check that calculates the percentage of rows that contains USA phone number values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiicontainsusaphonepercentsensorparametersspec)|Numerical value in range percent sensor parameters|[ColumnPiiContainsUsaPhonePercentSensorParametersSpec](#columnpiicontainsusaphonepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for the minimum percentage of rows that contains a USA phone number in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiContainsUsaPhonePercentSensorParametersSpec  
Column level sensor that calculates the percent of values that contains a USA phone number in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiValidUsaZipcodePercentCheckSpec  
Column check that calculates percent of valid USA Zip code values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiivalidusazipcodepercentsensorparametersspec)|Data quality check parameters|[ColumnPiiValidUsaZipcodePercentSensorParametersSpec](#columnpiivalidusazipcodepercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows with a parsable to integer strings in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiValidUsaZipcodePercentSensorParametersSpec  
Column level sensor that calculates the percent of values that fit to a USA ZIP code regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiContainsUsaZipcodePercentCheckSpec  
Column check that calculates the percentage of rows that contains USA zip code values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiicontainsusazipcodepercentsensorparametersspec)|Numerical value in range percent sensor parameters|[ColumnPiiContainsUsaZipcodePercentSensorParametersSpec](#columnpiicontainsusazipcodepercentsensorparametersspec)| | | |
|[warning](#maxpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxPercentRule1ParametersSpec](#maxpercentrule1parametersspec)| | | |
|[error](#maxpercentrule2parametersspec)|Default alerting threshold for the minimum percentage of rows that contains a USA zip code number in a column that raises a data quality error (alert).|[MaxPercentRule2ParametersSpec](#maxpercentrule2parametersspec)| | | |
|[fatal](#maxpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxPercentRule5ParametersSpec](#maxpercentrule5parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiContainsUsaZipcodePercentSensorParametersSpec  
Column level sensor that calculates the percent of values that contain a USA ZIP code number in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiValidEmailPercentCheckSpec  
Column check that calculates the percentage of rows that contains valid email values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiivalidemailpercentsensorparametersspec)|Data quality check parameters|[ColumnPiiValidEmailPercentSensorParametersSpec](#columnpiivalidemailpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows with a valid email in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiValidEmailPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid email value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiContainsEmailPercentCheckSpec  
Column check that calculates the percentage of rows that contains valid email values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiicontainsemailpercentsensorparametersspec)|Data quality check parameters|[ColumnPiiContainsEmailPercentSensorParametersSpec](#columnpiicontainsemailpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows that contains email values in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiContainsEmailPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid email value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiValidIp4AddressPercentCheckSpec  
Column check that calculates percent of valid IP4 address values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiivalidip4addresspercentsensorparametersspec)|Data quality check parameters|[ColumnPiiValidIp4AddressPercentSensorParametersSpec](#columnpiivalidip4addresspercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows with a valid IP4 addresses in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiValidIp4AddressPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid IP4 address value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiContainsIp4PercentCheckSpec  
Column check that calculates the percentage of rows that contains valid IP4 address values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiicontainsip4percentsensorparametersspec)|Data quality check parameters|[ColumnPiiContainsIp4PercentSensorParametersSpec](#columnpiicontainsip4percentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows that contains IP4 values in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiContainsIp4PercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid IP4 value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiValidIp6AddressPercentCheckSpec  
Column check that calculates the percent of valid IP6 address values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiivalidip6addresspercentsensorparametersspec)|Data quality check parameters|[ColumnPiiValidIp6AddressPercentSensorParametersSpec](#columnpiivalidip6addresspercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows with a valid IP6 addresses in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiValidIp6AddressPercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid IP6 address value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnPiiContainsIp6PercentCheckSpec  
Column check that calculates the percentage of rows that contains valid IP6 address values in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnpiicontainsip6percentsensorparametersspec)|Data quality check parameters|[ColumnPiiContainsIp6PercentSensorParametersSpec](#columnpiicontainsip6percentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows that contains IP6 values in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnPiiContainsIp6PercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a valid IP6 value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnSqlProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are using custom SQL expressions (conditions).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[sql_condition_passed_percent_on_column](#columnsqlconditionpassedpercentcheckspec)|Verifies that a minimum percentage of rows passed a custom SQL condition (expression).|[ColumnSqlConditionPassedPercentCheckSpec](#columnsqlconditionpassedpercentcheckspec)| | | |
|[sql_condition_failed_count_on_column](#columnsqlconditionfailedcountcheckspec)|Verifies that a number of rows failed a custom SQL condition(expression) does not exceed the maximum accepted count.|[ColumnSqlConditionFailedCountCheckSpec](#columnsqlconditionfailedcountcheckspec)| | | |
|[sql_aggregate_expr_column](#columnsqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|[ColumnSqlAggregateExprCheckSpec](#columnsqlaggregateexprcheckspec)| | | |









___  

## ColumnSqlConditionPassedPercentCheckSpec  
Column level check that ensures that a set percentage of rows passed a custom SQL condition (expression).  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnsqlconditionpassedpercentsensorparametersspec)|Sensor parameters with the custom SQL condition (an expression that returns true/false) which is evaluated on a each row for the given column, using a {column} placeholder to reference the current column.|[ColumnSqlConditionPassedPercentSensorParametersSpec](#columnsqlconditionpassedpercentsensorparametersspec)| | | |
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

## ColumnSqlConditionPassedPercentSensorParametersSpec  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count the percentage of rows that meet the condition.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string| | |{column} + col_tax &#x3D; col_total_price_with_tax<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnSqlConditionFailedCountCheckSpec  
Column level check that ensures that there are no more than a set number of rows fail a custom SQL condition (expression) evaluated for a given column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnsqlconditionfailedcountsensorparametersspec)|Sensor parameters with the custom SQL condition (an expression that returns true/false) which is evaluated on a each row, using a {column} placeholder to reference the current column.|[ColumnSqlConditionFailedCountSensorParametersSpec](#columnsqlconditionfailedcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning when a given number of rows failed the custom SQL condition (expression). The warning is considered as a passed data quality check.|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows failing the custom SQL condition (expression) that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue when a given number of rows failed the custom SQL condition (expression). A fatal issue indicates a serious data quality problem that should result in stopping the data pipelines.|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnSqlConditionFailedCountSensorParametersSpec  
Column level sensor that uses a custom SQL condition (an SQL expression that returns a boolean value) to count rows that do not meet the condition.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string| | |{column} + col_tax &#x3D; col_total_price_with_tax<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnSqlAggregateExprCheckSpec  
Column level check that calculates a given SQL aggregate expression on a column and compares it with a maximum accepted value.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnsqlaggregatedexpressionsensorparametersspec)|Sensor parameters with the custom SQL aggregate expression that is evaluated on a column|[ColumnSqlAggregatedExpressionSensorParametersSpec](#columnsqlaggregatedexpressionsensorparametersspec)| | | |
|[warning](#betweenfloatsruleparametersspec)|Default alerting threshold for warnings raised when the aggregated value is above the maximum accepted value.|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[error](#betweenfloatsruleparametersspec)|Default alerting threshold for errors raised when the aggregated value is above the maximum accepted value.|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[fatal](#betweenfloatsruleparametersspec)|Default alerting threshold for fatal data quality issues raised when the aggregated value is above the maximum accepted value.|[BetweenFloatsRuleParametersSpec](#betweenfloatsruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnSqlAggregatedExpressionSensorParametersSpec  
Column level sensor that executes a given SQL expression on a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_expression|SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or withing a GROUP BY clause for daily partitions and/or data streams. The expression can use {table} and {column} placeholder that are replaced with a full table name and the analyzed column name.|string| | |MAX({column})<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnBoolProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for booleans.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[true_percent](#columntruepercentcheckspec)|Verifies that the percentage of true values in a column does not exceed the minimum accepted percentage.|[ColumnTruePercentCheckSpec](#columntruepercentcheckspec)| | | |
|[false_percent](#columnfalsepercentcheckspec)|Verifies that the percentage of false values in a column does not exceed the minimum accepted percentage.|[ColumnFalsePercentCheckSpec](#columnfalsepercentcheckspec)| | | |









___  

## ColumnTruePercentCheckSpec  
Column level check that ensures that there are at least percentage of rows with a true value in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnbooltruepercentsensorparametersspec)|Data quality check parameters|[ColumnBoolTruePercentSensorParametersSpec](#columnbooltruepercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a set percentage of true value in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnBoolTruePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a true value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnFalsePercentCheckSpec  
Column level check that ensures that there are at least a minimum percentage of rows with a false value in a monitored column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnboolfalsepercentsensorparametersspec)|Data quality check parameters|[ColumnBoolFalsePercentSensorParametersSpec](#columnboolfalsepercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a set percentage of false value in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnBoolFalsePercentSensorParametersSpec  
Column level sensor that calculates the percentage of rows with a false value in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnIntegrityProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for integrity.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[foreign_key_not_match_count](#columnintegrityforeignkeynotmatchcountcheckspec)|Verifies that the number of values in a column that does not match values in another table column does not exceed the set count.|[ColumnIntegrityForeignKeyNotMatchCountCheckSpec](#columnintegrityforeignkeynotmatchcountcheckspec)| | | |
|[foreign_key_match_percent](#columnintegrityforeignkeymatchpercentcheckspec)|Verifies that the percentage of values in a column that matches values in another table column does not exceed the set count.|[ColumnIntegrityForeignKeyMatchPercentCheckSpec](#columnintegrityforeignkeymatchpercentcheckspec)| | | |









___  

## ColumnIntegrityForeignKeyNotMatchCountCheckSpec  
Column level check that ensures that there are no more than a maximum number of values not matching values in another table column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnintegrityforeignkeynotmatchcountsensorparametersspec)|Data quality check parameters|[ColumnIntegrityForeignKeyNotMatchCountSensorParametersSpec](#columnintegrityforeignkeynotmatchcountsensorparametersspec)| | | |
|[warning](#maxcountrule0parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxCountRule0ParametersSpec](#maxcountrule0parametersspec)| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows with values not matching values in another table column that raises a data quality error (alert).|[MaxCountRule10ParametersSpec](#maxcountrule10parametersspec)| | | |
|[fatal](#maxcountrule15parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MaxCountRule15ParametersSpec](#maxcountrule15parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnIntegrityForeignKeyNotMatchCountSensorParametersSpec  
Column level sensor that calculates the count of values that does not match values in column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|foreign_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|foreign_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnIntegrityForeignKeyMatchPercentCheckSpec  
Column level check that ensures that there are no more than a minimum percentage of values matching values in another table column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnintegrityforeignkeymatchpercentsensorparametersspec)|Data quality check parameters|[ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec](#columnintegrityforeignkeymatchpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum percentage of rows with values matching values in another table column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnIntegrityForeignKeyMatchPercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that match values in column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|foreign_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|foreign_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnAccuracyProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for accuracy.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[total_sum_match_percent](#columnaccuracytotalsummatchpercentcheckspec)|Verifies that percentage of the difference in sum of a column in a table and sum of a column of another table does not exceed the set number.|[ColumnAccuracyTotalSumMatchPercentCheckSpec](#columnaccuracytotalsummatchpercentcheckspec)| | | |
|[min_match_percent](#columnaccuracyminmatchpercentcheckspec)|Verifies that the percentage of difference in min of a column in a table and min of a column of another table does not exceed the set number.|[ColumnAccuracyMinMatchPercentCheckSpec](#columnaccuracyminmatchpercentcheckspec)| | | |
|[max_match_percent](#columnaccuracymaxmatchpercentcheckspec)|Verifies that the percentage of difference in max of a column in a table and max of a column of another table does not exceed the set number.|[ColumnAccuracyMaxMatchPercentCheckSpec](#columnaccuracymaxmatchpercentcheckspec)| | | |
|[average_match_percent](#columnaccuracyaveragematchpercentcheckspec)|Verifies that the percentage of difference in average of a column in a table and average of a column of another table does not exceed the set number.|[ColumnAccuracyAverageMatchPercentCheckSpec](#columnaccuracyaveragematchpercentcheckspec)| | | |
|[not_null_count_match_percent](#columnaccuracynotnullcountmatchpercentcheckspec)|Verifies that the percentage of difference in row count of a column in a table and row count of a column of another table does not exceed the set number. Stores the most recent captured value for each day when the data quality check was evaluated.|[ColumnAccuracyNotNullCountMatchPercentCheckSpec](#columnaccuracynotnullcountmatchpercentcheckspec)| | | |









___  

## ColumnAccuracyTotalSumMatchPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percentage of difference of sum of a table column and of a sum of another table column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnaccuracytotalsummatchpercentsensorparametersspec)|Data quality check parameters|[ColumnAccuracyTotalSumMatchPercentSensorParametersSpec](#columnaccuracytotalsummatchpercentsensorparametersspec)| | | |
|[warning](#maxdiffpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDiffPercentRule1ParametersSpec](#maxdiffpercentrule1parametersspec)| | | |
|[error](#maxdiffpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of difference of sum of a table column and of a sum of another table column that raises a data quality error (alert).|[MaxDiffPercentRule2ParametersSpec](#maxdiffpercentrule2parametersspec)| | | |
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

## ColumnAccuracyTotalSumMatchPercentSensorParametersSpec  
Column level sensor that calculates percentage of the difference in sum of a column in a table and sum of a column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnAccuracyMinMatchPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percentage of difference of min of a table column and of a min of another table column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnaccuracyminmatchpercentsensorparametersspec)|Data quality check parameters|[ColumnAccuracyMinMatchPercentSensorParametersSpec](#columnaccuracyminmatchpercentsensorparametersspec)| | | |
|[warning](#maxdiffpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDiffPercentRule1ParametersSpec](#maxdiffpercentrule1parametersspec)| | | |
|[error](#maxdiffpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of difference of min of a table column and of a min of another table column that raises a data quality error (alert).|[MaxDiffPercentRule2ParametersSpec](#maxdiffpercentrule2parametersspec)| | | |
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

## ColumnAccuracyMinMatchPercentSensorParametersSpec  
Column level sensor that calculates percentage of the difference in min of a column in a table and min of a column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnAccuracyMaxMatchPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percentage of difference of max of a table column and of a max of another table column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnaccuracymaxmatchpercentsensorparametersspec)|Data quality check parameters|[ColumnAccuracyMaxMatchPercentSensorParametersSpec](#columnaccuracymaxmatchpercentsensorparametersspec)| | | |
|[warning](#maxdiffpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDiffPercentRule1ParametersSpec](#maxdiffpercentrule1parametersspec)| | | |
|[error](#maxdiffpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of difference of max of a table column and of a max of another table column that raises a data quality error (alert).|[MaxDiffPercentRule2ParametersSpec](#maxdiffpercentrule2parametersspec)| | | |
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

## ColumnAccuracyMaxMatchPercentSensorParametersSpec  
Column level sensor that calculates percentage of the difference in max of a column in a table and max of a column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnAccuracyAverageMatchPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percentage of difference of average of a table column and of an average of another table column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnaccuracyaveragematchpercentsensorparametersspec)|Data quality check parameters|[ColumnAccuracyAverageMatchPercentSensorParametersSpec](#columnaccuracyaveragematchpercentsensorparametersspec)| | | |
|[warning](#maxdiffpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MaxDiffPercentRule1ParametersSpec](#maxdiffpercentrule1parametersspec)| | | |
|[error](#maxdiffpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of difference of average of a table column and of a average of another table column that raises a data quality error (alert).|[MaxDiffPercentRule2ParametersSpec](#maxdiffpercentrule2parametersspec)| | | |
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

## ColumnAccuracyAverageMatchPercentSensorParametersSpec  
Column level sensor that calculates percentage of the difference in average of a column in a table and average of a column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnAccuracyNotNullCountMatchPercentCheckSpec  
Column level check that ensures that there are no more than a maximum percentage of difference of the row count of a tested table&#x27;s column (counting the not null values) and of an row count of another (reference) table, also counting all rows with not null values.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnaccuracynotnullcountmatchpercentsensorparametersspec)|Data quality check parameters|[ColumnAccuracyNotNullCountMatchPercentSensorParametersSpec](#columnaccuracynotnullcountmatchpercentsensorparametersspec)| | | |
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

## ColumnAccuracyNotNullCountMatchPercentSensorParametersSpec  
Column level sensor that calculates percentage of the difference in row count of a column in a table and row count of a column of another table.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|referenced_column|This field can be used to define the name of the column to be compared to. In order to define the name of the column, user should write correct name as a String.|string| | |customer_id<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnConsistencyProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking for consistency.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[date_match_format_percent](#columnconsistencydatematchformatpercentcheckspec)|Verifies that the percentage of date values matching the given format in a column does not exceed the minimum accepted percentage.|[ColumnConsistencyDateMatchFormatPercentCheckSpec](#columnconsistencydatematchformatpercentcheckspec)| | | |
|[string_datatype_changed](#columnstringdatatypechangedcheckspec)|Detects that the data type of texts stored in a text column has changed since the last verification. The sensor returns the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|[ColumnStringDatatypeChangedCheckSpec](#columnstringdatatypechangedcheckspec)| | | |









___  

## ColumnConsistencyDateMatchFormatPercentCheckSpec  
Column check that calculates percentage of values that match the date format in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnconsistencydatematchformatpercentsensorparametersspec)|Data quality check parameters|[ColumnConsistencyDateMatchFormatPercentSensorParametersSpec](#columnconsistencydatematchformatpercentsensorparametersspec)| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|[MinPercentRule100ParametersSpec](#minpercentrule100parametersspec)| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a maximum percentage of rows with matching date format in a column that raises a data quality error (alert).|[MinPercentRule99ParametersSpec](#minpercentrule99parametersspec)| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|[MinPercentRule95ParametersSpec](#minpercentrule95parametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnConsistencyDateMatchFormatPercentSensorParametersSpec  
Column level sensor that calculates the percentage of values that does fit a given date regex in a column.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|date_formats|Desired date format. Sensor will try to parse the column records and cast the data using this format.|enum|DD.MM.YYYY<br/>DD-MM-YYYY<br/>YYYY-MM-DD<br/>DD/MM/YYYY<br/>| | |
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## ColumnStringDatatypeChangedCheckSpec  
Table level check that scans all values in a string column and detects the data type of all values in a column. The actual_value returned from the sensor is one of: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.
 The check compares the data type detected during the current run to the last known data type detected during a previous run. For daily recurring checks, it will compare the value to yesterday&#x27;s value (or an earlier date).
 For partitioned checks, it will compare the current data type to the data type in the previous daily or monthly partition. The last partition with data is used for comparison.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnstringsstringdatatypedetectsensorparametersspec)|The sensor parameters for a sensor that returns a value that identifies the detected data type of a column: 1 - integers, 2 - floats, 3 - dates, 4 - timestamps, 5 - booleans, 6 - strings, 7 - mixed data types.|[ColumnStringsStringDatatypeDetectSensorParametersSpec](#columnstringsstringdatatypedetectsensorparametersspec)| | | |
|[warning](#valuechangedparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check, detects that the data type of values stored in a column has changed since the last time it was evaluated or the data type in the current daily/monthly partition differs from the data type in the previous partition.|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[error](#valuechangedparametersspec)|Default alerting threshold for a row count that raises a data quality error (alert), detects that the data type of values stored in a column has changed since the last time it was evaluated or the data type in the current daily/monthly partition differs from the data type in the previous partition.|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[fatal](#valuechangedparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem, detects that the data type of values stored in a column has changed since the last time it was evaluated or the data type in the current daily/monthly partition differs from the data type in the previous partition.|[ValueChangedParametersSpec](#valuechangedparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnAnomalyProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level for detecting anomalies.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[mean_anomaly_7_days](#columnanomalymeanchange7dayscheckspec)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 7 days.|[ColumnAnomalyMeanChange7DaysCheckSpec](#columnanomalymeanchange7dayscheckspec)| | | |
|[mean_anomaly_30_days](#columnanomalymeanchange30dayscheckspec)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyMeanChange30DaysCheckSpec](#columnanomalymeanchange30dayscheckspec)| | | |
|[mean_anomaly_60_days](#columnanomalymeanchange60dayscheckspec)|Verifies that the mean value in a column changes in a rate within a percentile boundary during last 60 days.|[ColumnAnomalyMeanChange60DaysCheckSpec](#columnanomalymeanchange60dayscheckspec)| | | |
|[median_anomaly_7_days](#columnanomalymedianchange7dayscheckspec)|Verifies that the median in a column changes in a rate within a percentile boundary during last 7 days.|[ColumnAnomalyMedianChange7DaysCheckSpec](#columnanomalymedianchange7dayscheckspec)| | | |
|[median_anomaly_30_days](#columnanomalymedianchange30dayscheckspec)|Verifies that the median in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalyMedianChange30DaysCheckSpec](#columnanomalymedianchange30dayscheckspec)| | | |
|[median_anomaly_60_days](#columnanomalymedianchange60dayscheckspec)|Verifies that the median in a column changes in a rate within a percentile boundary during last 60 days.|[ColumnAnomalyMedianChange60DaysCheckSpec](#columnanomalymedianchange60dayscheckspec)| | | |
|[sum_anomaly_7_days](#columnanomalysumchange7dayscheckspec)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 7 days.|[ColumnAnomalySumChange7DaysCheckSpec](#columnanomalysumchange7dayscheckspec)| | | |
|[sum_anomaly_30_days](#columnanomalysumchange30dayscheckspec)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 30 days.|[ColumnAnomalySumChange30DaysCheckSpec](#columnanomalysumchange30dayscheckspec)| | | |
|[sum_anomaly_60_days](#columnanomalysumchange60dayscheckspec)|Verifies that the sum in a column changes in a rate within a percentile boundary during last 60 days.|[ColumnAnomalySumChange60DaysCheckSpec](#columnanomalysumchange60dayscheckspec)| | | |
|[mean_change](#columnchangemeancheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout.|[ColumnChangeMeanCheckSpec](#columnchangemeancheckspec)| | | |
|[mean_change_yesterday](#columnchangemeansinceyesterdaycheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMeanSinceYesterdayCheckSpec](#columnchangemeansinceyesterdaycheckspec)| | | |
|[mean_change_7_days](#columnchangemeansince7dayscheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMeanSince7DaysCheckSpec](#columnchangemeansince7dayscheckspec)| | | |
|[mean_change_30_days](#columnchangemeansince30dayscheckspec)|Verifies that the mean value in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMeanSince30DaysCheckSpec](#columnchangemeansince30dayscheckspec)| | | |
|[median_change](#columnchangemediancheckspec)|Verifies that the median in a column changed in a fixed rate since last readout.|[ColumnChangeMedianCheckSpec](#columnchangemediancheckspec)| | | |
|[median_change_yesterday](#columnchangemediansinceyesterdaycheckspec)|Verifies that the median in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeMedianSinceYesterdayCheckSpec](#columnchangemediansinceyesterdaycheckspec)| | | |
|[median_change_7_days](#columnchangemediansince7dayscheckspec)|Verifies that the median in a column changed in a fixed rate since last readout from last week.|[ColumnChangeMedianSince7DaysCheckSpec](#columnchangemediansince7dayscheckspec)| | | |
|[median_change_30_days](#columnchangemediansince30dayscheckspec)|Verifies that the median in a column changed in a fixed rate since last readout from last month.|[ColumnChangeMedianSince30DaysCheckSpec](#columnchangemediansince30dayscheckspec)| | | |
|[sum_change](#columnchangesumcheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout.|[ColumnChangeSumCheckSpec](#columnchangesumcheckspec)| | | |
|[sum_change_yesterday](#columnchangesumsinceyesterdaycheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout from yesterday.|[ColumnChangeSumSinceYesterdayCheckSpec](#columnchangesumsinceyesterdaycheckspec)| | | |
|[sum_change_7_days](#columnchangesumsince7dayscheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout from last week.|[ColumnChangeSumSince7DaysCheckSpec](#columnchangesumsince7dayscheckspec)| | | |
|[sum_change_30_days](#columnchangesumsince30dayscheckspec)|Verifies that the sum in a column changed in a fixed rate since last readout from last month.|[ColumnChangeSumSince30DaysCheckSpec](#columnchangesumsince30dayscheckspec)| | | |









___  

## ColumnAnomalyMeanChange7DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column changes in a rate within a two-tailed percentile during last 7 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
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

## ColumnAnomalyMeanChange30DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column changes in a rate within a two-tailed percentile during last 30 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
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

## ColumnAnomalyMeanChange60DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column changes in a rate within a two-tailed percentile during last 60 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
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

## ColumnAnomalyMedianChange7DaysCheckSpec  
Column level check that ensures that the median in a monitored column changes in a rate within a two-tailed percentile during last 7 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
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

## ColumnAnomalyMedianChange30DaysCheckSpec  
Column level check that ensures that the median in a monitored column changes in a rate within a two-tailed percentile during last 30 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
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

## ColumnAnomalyMedianChange60DaysCheckSpec  
Column level check that ensures that the median in a monitored column changes in a rate within a two-tailed percentile during last 60 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
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

## ColumnAnomalySumChange7DaysCheckSpec  
Column level check that ensures that the sum in a monitored column changes in a rate within a two-tailed percentile during last 7 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
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

## ColumnAnomalySumChange30DaysCheckSpec  
Column level check that ensures that the sum in a monitored column changes in a rate within a two-tailed percentile during last 30 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
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

## ColumnAnomalySumChange60DaysCheckSpec  
Column level check that ensures that the sum in a monitored column changes in a rate within a two-tailed percentile during last 60 days.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
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

## ColumnChangeMeanCheckSpec  
Column level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
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

## ColumnChangeMeanSinceYesterdayCheckSpec  
Column level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from yesterday.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
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

## ColumnChangeMeanSince7DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from last week.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
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

## ColumnChangeMeanSince30DaysCheckSpec  
Column level check that ensures that the mean value in a monitored column has changed by a fixed rate since the last readout from last month.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmeansensorparametersspec)|Data quality check parameters|[ColumnNumericMeanSensorParametersSpec](#columnnumericmeansensorparametersspec)| | | |
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

## ColumnChangeMedianCheckSpec  
Column level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
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

## ColumnChangeMedianSinceYesterdayCheckSpec  
Column level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from yesterday.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
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

## ColumnChangeMedianSince7DaysCheckSpec  
Column level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from last week.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
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

## ColumnChangeMedianSince30DaysCheckSpec  
Column level check that ensures that the median in a monitored column has changed by a fixed rate since the last readout from last month.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericmediansensorparametersspec)|Data quality check parameters|[ColumnNumericMedianSensorParametersSpec](#columnnumericmediansensorparametersspec)| | | |
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

## ColumnChangeSumCheckSpec  
Column level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
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

## ColumnChangeSumSinceYesterdayCheckSpec  
Column level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from yesterday.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
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

## ColumnChangeSumSince7DaysCheckSpec  
Column level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from last week.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
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

## ColumnChangeSumSince30DaysCheckSpec  
Column level check that ensures that the sum in a monitored column has changed by a fixed rate since the last readout from last month.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columnnumericsumsensorparametersspec)|Data quality check parameters|[ColumnNumericSumSensorParametersSpec](#columnnumericsumsensorparametersspec)| | | |
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

## ColumnSchemaProfilingChecksSpec  
Container of built-in preconfigured data quality checks on a column level that are checking the column schema.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[column_exists](#columnschemacolumnexistscheckspec)|Checks the metadata of the monitored table and verifies if the column exists.|[ColumnSchemaColumnExistsCheckSpec](#columnschemacolumnexistscheckspec)| | | |
|[column_type_changed](#columnschematypechangedcheckspec)|Checks the metadata of the monitored column and detects if the data type (including the length, precision, scale, nullability) has changed.|[ColumnSchemaTypeChangedCheckSpec](#columnschematypechangedcheckspec)| | | |









___  

## ColumnSchemaColumnExistsCheckSpec  
Column level check that reads the metadata of the monitored table and verifies that the column still exists in the data source.
 The data quality sensor returns 1.0 when the column was found or 0.0 when the column was not found.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columncolumnexistssensorparametersspec)|Data quality check parameters for a column exists sensor|[ColumnColumnExistsSensorParametersSpec](#columncolumnexistssensorparametersspec)| | | |
|[warning](#equalsinteger1ruleparametersspec)|Alerting threshold that raises a data quality warning when the column was not found.|[EqualsInteger1RuleParametersSpec](#equalsinteger1ruleparametersspec)| | | |
|[error](#equalsinteger1ruleparametersspec)|Alerting threshold that raises a data quality error when the column was not found.|[EqualsInteger1RuleParametersSpec](#equalsinteger1ruleparametersspec)| | | |
|[fatal](#equalsinteger1ruleparametersspec)|Alerting threshold that raises a data quality fatal issue when the column was not found.|[EqualsInteger1RuleParametersSpec](#equalsinteger1ruleparametersspec)| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|[RecurringScheduleSpec](#recurringschedulespec)| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|[CommentsListSpec](#commentslistspec)| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |









___  

## ColumnColumnExistsSensorParametersSpec  
Column level data quality sensor that reads the metadata of the table from the data source and checks if the column name exists on the table.
 Returns 1.0 when the column was found, 0.0 when the column is missing.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

## EqualsInteger1RuleParametersSpec  
Data quality rule that verifies that a data quality check readout equals a given integer value, with an expected value preconfigured as 1.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|expected_value|Expected value for the actual_value returned by the sensor. It must be an integer value.|long| | |1<br/>|









___  

## ColumnSchemaTypeChangedCheckSpec  
Column level check that detects if the data type of the column has changed since the last time it was retrieved.
 This check will calculate a hash of all the components of the column&#x27;s data type: the data type name, length, scale, precision and nullability.
 A data quality issue will be detected if the hash of the column&#x27;s data types has changed.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#columncolumntypehashsensorparametersspec)|Column data type hash sensor parameters|[ColumnColumnTypeHashSensorParametersSpec](#columncolumntypehashsensorparametersspec)| | | |
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

## ColumnColumnTypeHashSensorParametersSpec  
Column level data quality sensor that reads the metadata of the table from the data source and calculates a hash of the detected data type (also including the length, scale and precision)
 of the target colum.
 Returns a 15-16 decimal digit hash of the column data type.  
  








**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |









___  

