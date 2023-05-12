
## TableYaml  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|api_version||string| | | |
|kind||enum|table<br/>dashboards<br/>source<br/>sensor<br/>check<br/>rule<br/>file_index<br/>settings<br/>provider_sensor<br/>| | |
|[spec](#tablespec)||object| | | |

___  

## TableSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|disabled|Disables all data quality checks on the table. Data quality checks will not be executed.|boolean| | | |
|stage|Stage name.|string| | | |
|priority|Table priority (1, 2, 3, 4, ...). The tables could be assigned a priority level. The table priority is copied into each data quality check result and a sensor result, enabling efficient grouping of more and less important tables during a data quality improvement project, when the data quality issues on higher priority tables are fixed before data quality issues on less important tables.|integer| | | |
|filter|SQL WHERE clause added to the sensor queries. Use replacement tokens {table} to replace the content with the full table name, {alias} to replace the content with the table alias of an analyzed table or {column} to replace the content with the analyzed column name.|string| | | |
|[timestamp_columns](#timestampcolumnsspec)|Column names that store the timestamps that identify the event (transaction) timestamp and the ingestion (inserted / loaded at) timestamps. Also configures the timestamp source for the date/time partitioned data quality checks (event timestamp or ingestion timestamp).|object| | | |
|[incremental_time_window](#partitionincrementaltimewindowspec)|Configuration of the time window for analyzing daily or monthly partitions. Specifies the number of recent days and recent months that are analyzed when the partitioned data quality checks are run in an incremental mode (the default mode).|object| | | |
|[data_streams](#datastreammappingspecmap)|Data stream mappings list. Data streams are configured in two cases: (1) a tag is assigned to a table (within a data stream level hierarchy), when the data is segmented at a table level (similar tables store the same information, but for different countries, etc.). (2) the data in the table should be analyzed with a GROUP BY condition, to analyze different datasets using separate time series, for example a table contains data from multiple countries and there is a &#x27;country&#x27; column used for partitioning.|object| | | |
|[owner](#tableownerspec)|Table owner information like the data steward name or the business application name.|object| | | |
|[profiling_checks](#tableprofilingcheckcategoriesspec)|Configuration of data quality profiling checks that are enabled. Pick a check from a category, apply the parameters and rules to enable it.|object| | | |
|[recurring_checks](#tablerecurringspec)|Configuration of table level recurring checks. Recurring are data quality checks that are evaluated for each period of time (daily, weekly, monthly, etc.). A checkpoint stores only the most recent data quality check result for each period of time.|object| | | |
|[partitioned_checks](#tablepartitionedchecksrootspec)|Configuration of table level date/time partitioned checks. Partitioned data quality checks are evaluated for each partition separately, raising separate alerts at a partition level. The table does not need to be physically partitioned by date, it is possible to run data quality checks for each day or month of data separately.|object| | | |
|[statistics](#tablestatisticscollectorsrootcategoriesspec)|Configuration of table level data statistics collector (a basic profiler). Configures which statistics collectors are enabled and how they are configured.|object| | | |
|[schedules_override](#recurringschedulesspec)|Configuration of the job scheduler that runs data quality checks. The scheduler configuration is divided into types of checks that have different schedules.|object| | | |
|[columns](#columnspecmap)|Dictionary of columns, indexed by a physical column name. Column specification contains the expected column data type and a list of column level data quality checks that are enabled for a column.|object| | | |
|[labels](#labelsetspec)|Custom labels that were assigned to the table. Labels are used for searching for tables when filtered data quality checks are executed.|object| | | |
|[comments](#commentslistspec)|Comments used for change tracking and documenting changes directly in the table data quality specification file.|object| | | |

___  

## TimestampColumnsSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|event_timestamp_column|Column name that identifies an event timestamp (date/time), such as a transaction timestamp, impression timestamp, event timestamp.|column_name| | | |
|ingestion_timestamp_column|Column name that contains the timestamp (or date/time) when the row was ingested (loaded, inserted) into the table. Use a column that is filled by the data pipeline or ETL process at the time of the data loading.|column_name| | | |
|partition_by_column|Column name that contains the date, datetime or timestamp column for date/time partitioned data. Partition checks (daily partition checks and monthly partition checks) use this column in a GROUP BY clause in order to detect data quality issues in each partition separately. It should be a DATE type, DATETIME type (using a local server time zone) or a TIMESTAMP type (a UTC absolute time).|column_name| | | |

___  

## PartitionIncrementalTimeWindowSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|daily_partitioning_recent_days|Number of recent days that are analyzed by daily partitioned checks in incremental mode. The default value is 7 days back.|integer| | | |
|daily_partitioning_include_today|Analyze also today&#x27;s data by daily partitioned checks in incremental mode. The default value is false, which means that the today&#x27;s and the future partitions are not analyzed, only yesterday&#x27;s partition and earlier daily partitions are analyzed because today&#x27;s data could be still incomplete. Change the value to &#x27;true&#x27; if the current day should be also analyzed. The change may require configuring the schedule for daily checks correctly, to run after the data load.|boolean| | | |
|monthly_partitioning_recent_months|Number of recent months that are analyzed by monthly partitioned checks in incremental mode. The default value is 1 month back which means the previous calendar month.|integer| | | |
|monthly_partitioning_include_current_month|Analyze also this month&#x27;s data by monthly partitioned checks in incremental mode. The default value is false, which means that the current month is not analyzed and future data is also filtered out because the current month could be incomplete. Set the value to &#x27;true&#x27; if the current month should be analyzed before the end of the month. The schedule for running monthly checks should be also configured to run more frequently (daily, hourly, etc.).|boolean| | | |

___  

## DataStreamMappingSpecMap  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |

___  

## TableOwnerSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|data_steward|Data steward name|string| | | |
|application|Business application name|string| | | |

___  

## TableProfilingCheckCategoriesSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[standard](#tableprofilingstandardchecksspec)|Configuration of standard data quality checks on a table level.|object| | | |
|[timeliness](#tableprofilingtimelinesschecksspec)|Configuration of timeliness checks on a table level. Timeliness checks detect anomalies like rapid row count changes.|object| | | |
|[accuracy](#tableprofilingaccuracychecksspec)|Configuration of accuracy checks on a table level. Accuracy checks compare the tested table with another reference table.|object| | | |
|[sql](#tableprofilingsqlchecksspec)|Configuration of data quality checks that are evaluating custom SQL conditions and aggregated expressions.|object| | | |
|[availability](#tableprofilingavailabilitychecksspec)|Configuration of standard data quality checks on a table level.|object| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|object| | | |

___  

## TableProfilingStandardChecksSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[row_count](#tablerowcountcheckspec)|Verifies that the number of rows in a table does not exceed the minimum accepted count.|object| | | |

___  

## TableRowCountCheckSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablestandardrowcountsensorparametersspec)|Row count sensor parameters|object| | | |
|[warning](#mincountrulewarningparametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|object| | | |
|[error](#mincountrule0parametersspec)|Default alerting threshold for a row count that raises a data quality error (alert)|object| | | |
|[fatal](#mincountrulefatalparametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|object| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|object| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|object| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |

___  

## TableStandardRowCountSensorParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |

___  

## MinCountRuleWarningParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_count|Minimum accepted value for the actual_value returned by the sensor (inclusive).|long| | |0<br/>|

___  

## MinCountRule0ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_count|Minimum accepted value for the actual_value returned by the sensor (inclusive).|long| | |5<br/>|

___  

## MinCountRuleFatalParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_count|Minimum accepted value for the actual_value returned by the sensor (inclusive).|long| | |100<br/>|

___  

## RecurringScheduleSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|cron_expression|Unix style cron expression that specifies when to execute scheduled operations like running data quality checks or synchronizing the configuration with the cloud.|string| | | |
|disabled|Disables the schedule. When the value of this &#x27;disable&#x27; field is false, the schedule is stored in the metadata but it is not activated to run data quality checks.|boolean| | | |

___  

## CommentsListSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|mod_count||integer| | | |

___  

## TableProfilingTimelinessChecksSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[days_since_most_recent_event](#tabledayssincemostrecenteventcheckspec)|Calculates the number of days since the most recent event timestamp (freshness)|object| | | |
|[data_ingestion_delay](#tabledataingestiondelaycheckspec)|Calculates the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|object| | | |
|[days_since_most_recent_ingestion](#tabledayssincemostrecentingestioncheckspec)|Calculates the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|object| | | |

___  

## TableDaysSinceMostRecentEventCheckSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tabletimelinessdayssincemostrecenteventsensorparametersspec)|Max days since most recent event sensor parameters|object| | | |
|[warning](#maxdaysrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|object| | | |
|[error](#maxdaysrule2parametersspec)|Default alerting threshold for max days since most recent event that raises a data quality error (alert)|object| | | |
|[fatal](#maxdaysrule7parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|object| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|object| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|object| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |

___  

## TableTimelinessDaysSinceMostRecentEventSensorParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |

___  

## MaxDaysRule1ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_days|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |

___  

## MaxDaysRule2ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_days|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |

___  

## MaxDaysRule7ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_days|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |

___  

## TableDataIngestionDelayCheckSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tabletimelinessdataingestiondelaysensorparametersspec)|Max number of days between event and ingestion sensor parameters|object| | | |
|[warning](#maxdaysrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|object| | | |
|[error](#maxdaysrule2parametersspec)|Default alerting threshold for a max number of days between event and ingestion check that raises a data quality error (alert)|object| | | |
|[fatal](#maxdaysrule7parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|object| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|object| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|object| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |

___  

## TableTimelinessDataIngestionDelaySensorParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |

___  

## TableDaysSinceMostRecentIngestionCheckSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tabletimelinessdayssincemostrecentingestionsensorparametersspec)|Min number of days between event and ingestion sensor parameters|object| | | |
|[warning](#maxdaysrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|object| | | |
|[error](#maxdaysrule2parametersspec)|Default alerting threshold for a min number of days between event and ingestion check that raises a data quality error (alert)|object| | | |
|[fatal](#maxdaysrule7parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|object| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|object| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|object| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |

___  

## TableTimelinessDaysSinceMostRecentIngestionSensorParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |

___  

## TableProfilingAccuracyChecksSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[row_count_match_percent](#tableaccuracyrowcountmatchpercentcheckspec)|Verifies that the total row count of the tested table matches the total row count of another (reference) table.|object| | | |

___  

## TableAccuracyRowCountMatchPercentCheckSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tableaccuracyrowcountmatchpercentsensorparametersspec)|Data quality check parameters|object| | | |
|[warning](#maxdiffpercentrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|object| | | |
|[error](#maxdiffpercentrule2parametersspec)|Default alerting threshold for a maximum percentage of difference of row count of a table column and of a row count of another table column that raises a data quality error (alert).|object| | | |
|[fatal](#maxdiffpercentrule5parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|object| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|object| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|object| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |

___  

## TableAccuracyRowCountMatchPercentSensorParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|referenced_table|This field can be used to define the name of the table to be compared to. In order to define the name of the table, user should write correct name as a String.|string| | |dim_customer<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |

___  

## MaxDiffPercentRule1ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_diff_percent|Maximum accepted value for the percentage of difference between expected_value and actual_value returned by the sensor (inclusive).|double| | | |

___  

## MaxDiffPercentRule2ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_diff_percent|Maximum accepted value for the percentage of difference between expected_value and actual_value returned by the sensor (inclusive).|double| | | |

___  

## MaxDiffPercentRule5ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_diff_percent|Maximum accepted value for the percentage of difference between expected_value and actual_value returned by the sensor (inclusive).|double| | | |

___  

## TableProfilingSqlChecksSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[sql_condition_passed_percent_on_table](#tablesqlconditionpassedpercentcheckspec)|Verifies that a set percentage of rows passed a custom SQL condition (expression).|object| | | |
|[sql_condition_failed_count_on_table](#tablesqlconditionfailedcountcheckspec)|Verifies that a set number of rows failed a custom SQL condition (expression).|object| | | |
|[sql_aggregate_expr_table](#tablesqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range.|object| | | |

___  

## TableSqlConditionPassedPercentCheckSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablesqlconditionpassedpercentsensorparametersspec)|Sensor parameters with the custom SQL condition (an expression that returns true/false) which is evaluated on a each row|object| | | |
|[warning](#minpercentrule100parametersspec)|Alerting threshold that raises a data quality warning when a minimum acceptable percentage of rows did not pass the custom SQL condition (expression). The warning is considered as a passed data quality check.|object| | | |
|[error](#minpercentrule99parametersspec)|Default alerting threshold for a minimum acceptable percentage of rows passing the custom SQL condition (expression) that raises a data quality error (alert).|object| | | |
|[fatal](#minpercentrule95parametersspec)|Alerting threshold that raises a fatal data quality issue when a minimum acceptable percentage of rows did not pass the custom SQL condition (expression). A fatal issue indicates a serious data quality problem that should result in stopping the data pipelines.|object| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|object| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|object| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |

___  

## TableSqlConditionPassedPercentSensorParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string| | |SUM(col_total_impressions) &gt; SUM(col_total_clicks)<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |

___  

## MinPercentRule100ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |

___  

## MinPercentRule99ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |

___  

## MinPercentRule95ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|min_percent|Minimum accepted value for the actual_value returned by the sensor (inclusive).|double| | | |

___  

## TableSqlConditionFailedCountCheckSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablesqlconditionfailedcountsensorparametersspec)|Sensor parameters with the custom SQL condition (an expression that returns true/false) which is evaluated on a each row|object| | | |
|[warning](#maxcountrule15parametersspec)|Alerting threshold that raises a data quality warning when a given number of rows failed the custom SQL condition (expression). The warning is considered as a passed data quality check.|object| | | |
|[error](#maxcountrule10parametersspec)|Default alerting threshold for a maximum number of rows failing the custom SQL condition (expression) that raises a data quality error (alert).|object| | | |
|[fatal](#maxcountrule0parametersspec)|Alerting threshold that raises a fatal data quality issue when a given number of rows failed the custom SQL condition (expression). A fatal issue indicates a serious data quality problem that should result in stopping the data pipelines.|object| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|object| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|object| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |

___  

## TableSqlConditionFailedCountSensorParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_condition|SQL condition (expression) that returns true or false. The condition is evaluated for each row. The expression can use {table} placeholder that is replaced with a full table name.|string| | |SUM(col_total_impressions) &gt; SUM(col_total_clicks)<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |

___  

## MaxCountRule15ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_count|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| | | |

___  

## MaxCountRule10ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_count|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| | | |

___  

## MaxCountRule0ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_count|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| | | |

___  

## TableSqlAggregateExprCheckSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablesqlaggregatedexpressionsensorparametersspec)|Sensor parameters with the custom SQL aggregate expression that is evaluated on a table|object| | | |
|[warning](#maxvalueruleparametersspec)|Default alerting threshold for warnings raised when the aggregated value is above the maximum accepted value.|object| | | |
|[error](#maxvalueruleparametersspec)|Default alerting threshold for errors raised when the aggregated value is above the maximum accepted value.|object| | | |
|[fatal](#maxvalueruleparametersspec)|Default alerting threshold for fatal data quality issues raised when the aggregated value is above the maximum accepted value.|object| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|object| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|object| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |

___  

## TableSqlAggregatedExpressionSensorParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|sql_expression|SQL aggregate expression that returns a numeric value calculated from rows. The expression is evaluated on a whole table or withing a GROUP BY clause for daily partitions and/or data streams. The expression can use {table} placeholder that is replaced with a full table name.|string| | |SUM(col_net_price) + SUM(col_tax)<br/>|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |

___  

## MaxValueRuleParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_value|Maximum accepted value for the actual_value returned by the sensor (inclusive).|double| | |1.5<br/>|

___  

## TableProfilingAvailabilityChecksSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[table_availability](#tableavailabilitycheckspec)|Verifies availability on table in database using simple row count.|object| | | |

___  

## TableAvailabilityCheckSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tableavailabilitysensorparametersspec)|Row count sensor parameters|object| | | |
|[warning](#maxfailuresrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|object| | | |
|[error](#maxfailuresrule5parametersspec)|Default alerting threshold for a row count that raises a data quality error (alert)|object| | | |
|[fatal](#maxfailuresrule10parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|object| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|object| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|object| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |

___  

## TableAvailabilitySensorParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |

___  

## MaxFailuresRule1ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_failures|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| | | |

___  

## MaxFailuresRule5ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_failures|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| | | |

___  

## MaxFailuresRule10ParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|max_failures|Maximum accepted value for the actual_value returned by the sensor (inclusive).|long| | | |

___  

## CustomCheckSpecMap  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |

___  

## TableRecurringSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](#tabledailyrecurringcategoriesspec)|Configuration of daily recurring evaluated at a table level.|object| | | |
|[monthly](#tablemonthlyrecurringcategoriesspec)|Configuration of monthly recurring evaluated at a table level.|object| | | |

___  

## TableDailyRecurringCategoriesSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[standard](#tablestandarddailyrecurringspec)|Daily recurring standard data quality checks|object| | | |
|[timeliness](#tabletimelinessdailyrecurringspec)|Daily recurring timeliness checks|object| | | |
|[accuracy](#tableaccuracydailyrecurringspec)|Daily recurring accuracy checks|object| | | |
|[sql](#tablesqldailyrecurringspec)|Daily recurring custom SQL checks|object| | | |
|[availability](#tableavailabilitydailyrecurringspec)|Daily recurring availability checks|object| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|object| | | |

___  

## TableStandardDailyRecurringSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_row_count](#tablerowcountcheckspec)|Verifies that the number of rows in a table does not exceed the minimum accepted count. Stores the most recent row count for each day when the data quality check was evaluated.|object| | | |

___  

## TableTimelinessDailyRecurringSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_days_since_most_recent_event](#tabledayssincemostrecenteventcheckspec)|Daily  calculating the number of days since the most recent event timestamp (freshness)|object| | | |
|[daily_data_ingestion_delay](#tabledataingestiondelaycheckspec)|Daily  calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|object| | | |
|[daily_days_since_most_recent_ingestion](#tabledayssincemostrecentingestioncheckspec)|Daily  calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|object| | | |

___  

## TableAccuracyDailyRecurringSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_row_count_match_percent](#tableaccuracyrowcountmatchpercentcheckspec)|Verifies the row count of a tested table and compares it to a row count of a reference table. Stores the most recent row count for each day when the data quality check was evaluated.|object| | | |

___  

## TableSqlDailyRecurringSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_sql_condition_passed_percent_on_table](#tablesqlconditionpassedpercentcheckspec)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each day when the data quality check was evaluated.|object| | | |
|[daily_sql_condition_failed_count_on_table](#tablesqlconditionfailedcountcheckspec)|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent row count for each day when the data quality check was evaluated.|object| | | |
|[daily_sql_aggregate_expr_table](#tablesqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each day when the data quality check was evaluated.|object| | | |

___  

## TableAvailabilityDailyRecurringSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_table_availability](#tableavailabilitycheckspec)|Verifies availability on table in database using simple row count. Stores the most recent row count for each day when the data quality check was evaluated.|object| | | |

___  

## TableMonthlyRecurringCategoriesSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[standard](#tablestandardmonthlyrecurringspec)|Monthly recurring of standard data quality checks|object| | | |
|[timeliness](#tabletimelinessmonthlyrecurringspec)|Monthly recurring of timeliness checks|object| | | |
|[accuracy](#tableaccuracymonthlyrecurringspec)|Monthly recurring accuracy checks|object| | | |
|[sql](#tablesqlmonthlyrecurringspec)|Monthly recurring of custom SQL checks|object| | | |
|[availability](#tableavailabilitymonthlyrecurringspec)|Daily partitioned availability checks|object| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|object| | | |

___  

## TableStandardMonthlyRecurringSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_row_count](#tablerowcountcheckspec)|Verifies that the number of rows in a table does not exceed the minimum accepted count. Stores the most recent row count for each month when the data quality check was evaluated.|object| | | |

___  

## TableTimelinessMonthlyRecurringSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_days_since_most_recent_event](#tabledayssincemostrecenteventcheckspec)|Monthly recurring calculating the number of days since the most recent event timestamp (freshness)|object| | | |
|[monthly_data_ingestion_delay](#tabledataingestiondelaycheckspec)|Monthly recurring calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|object| | | |
|[monthly_days_since_most_recent_ingestion](#tabledayssincemostrecentingestioncheckspec)|Monthly recurring calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|object| | | |

___  

## TableAccuracyMonthlyRecurringSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_row_count_match_percent](#tableaccuracyrowcountmatchpercentcheckspec)|Verifies the row count of a tested table and compares it to a row count of a reference table. Stores the most recent row count for each month when the data quality check was evaluated.|object| | | |

___  

## TableSqlMonthlyRecurringSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_sql_condition_passed_percent_on_table](#tablesqlconditionpassedpercentcheckspec)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|object| | | |
|[monthly_sql_condition_failed_count_on_table](#tablesqlconditionfailedcountcheckspec)|Verifies that a set number of rows failed a custom SQL condition (expression). Stores the most recent row count for each month when the data quality check was evaluated.|object| | | |
|[monthly_sql_aggregate_expr_table](#tablesqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Stores the most recent row count for each month when the data quality check was evaluated.|object| | | |

___  

## TableAvailabilityMonthlyRecurringSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_table_availability](#tableavailabilitycheckspec)|Verifies availability on table in database using simple row count. Stores the most recent row count for each month when the data quality check was evaluated.|object| | | |

___  

## TablePartitionedChecksRootSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily](#tabledailypartitionedcheckcategoriesspec)|Configuration of day partitioned data quality checks evaluated at a table level.|object| | | |
|[monthly](#tablemonthlypartitionedcheckcategoriesspec)|Configuration of monthly partitioned data quality checks evaluated at a table level..|object| | | |

___  

## TableDailyPartitionedCheckCategoriesSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[standard](#tablestandarddailypartitionedchecksspec)|Standard daily partitioned data quality checks that verify the quality of every day of data separately|object| | | |
|[timeliness](#tabletimelinessdailypartitionedchecksspec)|Daily partitioned timeliness checks|object| | | |
|[sql](#tablesqldailypartitionedspec)|Custom SQL daily partitioned data quality checks that verify the quality of every day of data separately|object| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|object| | | |

___  

## TableStandardDailyPartitionedChecksSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_row_count](#tablerowcountcheckspec)|Verifies that the number of rows in a table does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each daily partition.|object| | | |

___  

## TableTimelinessDailyPartitionedChecksSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_days_since_most_recent_event](#tabledayssincemostrecenteventcheckspec)|Daily partition checkpoint calculating the number of days since the most recent event timestamp (freshness)|object| | | |
|[daily_partition_data_ingestion_delay](#tabledataingestiondelaycheckspec)|Daily partition checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|object| | | |
|[daily_partition_days_since_most_recent_ingestion](#tabledayssincemostrecentingestioncheckspec)|Daily partition checkpoint calculating the time difference in days between the current date and the most recent data ingestion timestamp (staleness)|object| | | |
|[daily_partition_reload_lag](#tablepartitionreloadlagcheckspec)|Daily partition checkpoint calculating the longest time a row waited to be load|object| | | |

___  

## TablePartitionReloadLagCheckSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tabletimelinesspartitionreloadlagsensorparametersspec)|Partition reload lag sensor parameters|object| | | |
|[warning](#maxdaysrule1parametersspec)|Alerting threshold that raises a data quality warning that is considered as a passed data quality check|object| | | |
|[error](#maxdaysrule2parametersspec)|Default alerting threshold for partition reload lag that raises a data quality error (alert)|object| | | |
|[fatal](#maxdaysrule7parametersspec)|Alerting threshold that raises a fatal data quality issue which indicates a serious data quality problem|object| | | |
|[schedule_override](#recurringschedulespec)|Run check scheduling configuration. Specifies the schedule (a cron expression) when the data quality checks are executed by the scheduler.|object| | | |
|[comments](#commentslistspec)|Comments for change tracking. Please put comments in this collection because YAML comments may be removed when the YAML file is modified by the tool (serialization and deserialization will remove non tracked comments).|object| | | |
|disabled|Disables the data quality check. Only enabled data quality checks and recurrings are executed. The check should be disabled if it should not work, but the configuration of the sensor and rules should be preserved in the configuration.|boolean| | | |
|exclude_from_kpi|Data quality check results (alerts) are included in the data quality KPI calculation by default. Set this field to true in order to exclude this data quality check from the data quality KPI calculation.|boolean| | | |
|include_in_sla|Marks the data quality check as part of a data quality SLA. The data quality SLA is a set of critical data quality checks that must always pass and are considered as a data contract for the dataset.|boolean| | | |
|quality_dimension|Configures a custom data quality dimension name that is different than the built-in dimensions (Timeliness, Validity, etc.).|string| | | |
|display_name|Data quality check display name that could be assigned to the check, otherwise the check_display_name stored in the parquet result files is the check_name.|string| | | |
|data_stream|Data stream name that should be applied to this data quality check. The data stream is used to group checks on similar tables using tags or use dynamic data segmentation to execute the data quality check for different groups of rows (by using a GROUP BY clause in the SQL SELECT statement executed by the data quality check). Use a name of one of known data streams defined on the parent table.|string| | | |

___  

## TableTimelinessPartitionReloadLagSensorParametersSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|filter|SQL WHERE clause added to the sensor query. Both the table level filter and a sensor query filter are added, separated by an AND operator.|string| | | |

___  

## TableSqlDailyPartitionedSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[daily_partition_sql_condition_passed_percent_on_table](#tablesqlconditionpassedpercentcheckspec)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|object| | | |
|[daily_partition_sql_condition_failed_count_on_table](#tablesqlconditionfailedcountcheckspec)|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each daily partition.|object| | | |
|[daily_partition_sql_aggregate_expr_table](#tablesqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each daily partition.|object| | | |

___  

## TableMonthlyPartitionedCheckCategoriesSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[standard](#tablestandardmonthlypartitionedchecksspec)|Standard monthly partitioned data quality checks that verify the quality of every month of data separately|object| | | |
|[timeliness](#tabletimelinessmonthlypartitionedchecksspec)|Monthly partitioned timeliness checks|object| | | |
|[sql](#tablesqlmonthlypartitionedspec)|Custom SQL daily partitioned data quality checks that verify the quality of every month of data separately|object| | | |
|[custom](#customcheckspecmap)|Dictionary of custom checks. The keys are check names.|object| | | |

___  

## TableStandardMonthlyPartitionedChecksSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_min_row_count](#tablerowcountcheckspec)|Verifies that the number of rows in a table does not exceed the minimum accepted count. Creates a separate data quality check (and an alert) for each monthly partition.|object| | | |

___  

## TableTimelinessMonthlyPartitionedChecksSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_days_since_most_recent_event](#tabledayssincemostrecenteventcheckspec)|Monthly partition checkpoint calculating the number of days since the most recent event (freshness)|object| | | |
|[monthly_partition_data_ingestion_delay](#tabledataingestiondelaycheckspec)|Monthly partition checkpoint calculating the time difference in days between the most recent event timestamp and the most recent ingestion timestamp|object| | | |
|[monthly_partition_days_since_most_recent_ingestion](#tabledayssincemostrecentingestioncheckspec)|Monthly partition checkpoint calculating the time difference in days between the current date and the most recent data data ingestion timestamp (staleness)|object| | | |
|[monthly_partition_reload_lag](#tablepartitionreloadlagcheckspec)|Monthly partition checkpoint calculating the longest time a row waited to be load|object| | | |

___  

## TableSqlMonthlyPartitionedSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[monthly_partition_sql_condition_passed_percent_on_table](#tablesqlconditionpassedpercentcheckspec)|Verifies that a set percentage of rows passed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|object| | | |
|[monthly_partition_sql_condition_failed_count_on_table](#tablesqlconditionfailedcountcheckspec)|Verifies that a set number of rows failed a custom SQL condition (expression). Creates a separate data quality check (and an alert) for each monthly partition.|object| | | |
|[monthly_partition_sql_aggregate_expr_table](#tablesqlaggregateexprcheckspec)|Verifies that a custom aggregated SQL expression (MIN, MAX, etc.) is not outside the set range. Creates a separate data quality check (and an alert) for each monthly partition.|object| | | |

___  

## TableStatisticsCollectorsRootCategoriesSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[standard](#tablestandardstatisticscollectorsspec)|Configuration of standard statistics collectors on a table level.|object| | | |

___  

## TableStandardStatisticsCollectorsSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[row_count](#tablestandardrowcountstatisticscollectorspec)|Configuration of the row count profiler.|object| | | |

___  

## TableStandardRowCountStatisticsCollectorSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[parameters](#tablestandardrowcountsensorparametersspec)|Profiler parameters|object| | | |
|disabled|Disables this profiler. Only enabled profilers are executed during a profiling process.|boolean| | | |

___  

## RecurringSchedulesSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|[profiling](#recurringschedulespec)|Schedule for running profiling data quality checks.|object| | | |
|[recurring_daily](#recurringschedulespec)|Schedule for running daily recurring checks.|object| | | |
|[recurring_monthly](#recurringschedulespec)|Schedule for running monthly recurring checks.|object| | | |
|[partitioned_daily](#recurringschedulespec)|Schedule for running daily partitioned checks.|object| | | |
|[partitioned_monthly](#recurringschedulespec)|Schedule for running monthly partitioned checks.|object| | | |

___  

## ColumnSpecMap  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|
|access_order||boolean| | | |
|size||integer| | | |
|mod_count||integer| | | |
|threshold||integer| | | |

___  

## LabelSetSpec  
**The structure of this object is described below**  
  
|&nbsp;Property&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;Data&nbsp;type&nbsp;|&nbsp;Enum&nbsp;values&nbsp;|&nbsp;Default&nbsp;value&nbsp;|&nbsp;Sample&nbsp;values&nbsp;|
|---------------|---------------------------------|-----------|-------------|---------------|---------------|

___  

