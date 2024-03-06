# Reference of all models used by DQOps REST API

This is a list of the models in DQOps REST API Python client broken down by individual controllers.


## common

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CheckTimeScale*](\docs\client\models\common.md#checktimescale)|Enumeration of time scale of monitoring and partitioned data quality checks (daily, monthly, etc.)|
|[*FieldModel*](\docs\client\models\common.md#fieldmodel)|Model of a single field that is used to edit a parameter value for a sensor or a rule. Describes the type of the field and the current value.|
|[*RuleParametersModel*](\docs\client\models\common.md#ruleparametersmodel)|Model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single severity level (low, medium, high).|
|[*CheckConfigurationModel*](\docs\client\models\common.md#checkconfigurationmodel)|Model containing fundamental configuration of a single data quality check.|
|[*CheckListModel*](\docs\client\models\common.md#checklistmodel)|Simplistic model that returns a single data quality check, its name and &quot;configured&quot; flag.|
|[*CheckContainerListModel*](\docs\client\models\common.md#checkcontainerlistmodel)|Simplistic model that returns the list of data quality checks, their names, categories and &quot;configured&quot; flag.|
|[*RuleThresholdsModel*](\docs\client\models\common.md#rulethresholdsmodel)|Model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).|
|[*MonitoringScheduleSpec*](\docs\client\models\common.md#monitoringschedulespec)|Monitoring job schedule specification.|
|[*CheckRunScheduleGroup*](\docs\client\models\common.md#checkrunschedulegroup)|The run check scheduling group (profiling, daily checks, monthly checks, etc), which identifies the configuration of a schedule (cron expression) used schedule these checks on the job scheduler.|
|[*EffectiveScheduleLevelModel*](\docs\client\models\common.md#effectiveschedulelevelmodel)|Enumeration of possible levels at which a schedule can be configured.|
|[*EffectiveScheduleModel*](\docs\client\models\common.md#effectiveschedulemodel)|Model of a configured schedule (on connection or table) or schedule override (on check). Describes the CRON expression and the time of the upcoming execution, as well as the duration until this time.|
|[*ScheduleEnabledStatusModel*](\docs\client\models\common.md#scheduleenabledstatusmodel)|Enumeration of possible ways a schedule can be configured.|
|[*CommentSpec*](\docs\client\models\common.md#commentspec)|Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.|
|[*CommentsListSpec*](\docs\client\models\common.md#commentslistspec)|List of comments.|
|[*CheckSearchFilters*](\docs\client\models\common.md#checksearchfilters)|Target data quality checks filter, identifies which checks on which tables and columns should be executed.|
|[*CheckTargetModel*](\docs\client\models\common.md#checktargetmodel)|Enumeration of possible targets for check model request result.|
|[*SimilarCheckModel*](\docs\client\models\common.md#similarcheckmodel)|Describes a single check that is similar to other checks in other check types.|
|[*CheckModel*](\docs\client\models\common.md#checkmodel)|Model that returns the form definition and the form data to edit a single data quality check.|
|[*QualityCategoryModel*](\docs\client\models\common.md#qualitycategorymodel)|Model that returns the form definition and the form data to edit all checks within a single category.|
|[*CheckContainerModel*](\docs\client\models\common.md#checkcontainermodel)|Model that returns the form definition and the form data to edit all data quality checks divided by categories.|
|[*CheckContainerTypeModel*](\docs\client\models\common.md#checkcontainertypemodel)|Model identifying the check type and timescale of checks belonging to a container.|
|[*CheckTemplate*](\docs\client\models\common.md#checktemplate)|Model depicting a named data quality check that can potentially be enabled, regardless to its position in hierarchy tree.|
|[*ProviderType*](\docs\client\models\common.md#providertype)|Data source provider type (dialect type). We will use lower case names to avoid issues with parsing, even if the enum names are not named following the Java naming convention.|
|[*ConnectionModel*](\docs\client\models\common.md#connectionmodel)|Connection model returned by the rest api that is limited only to the basic fields, excluding nested nodes.|
|[*DqoQueueJobId*](\docs\client\models\common.md#dqoqueuejobid)|Identifies a single job.|


## check_results

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CheckResultsListModel*](\docs\client\models\check_results.md#checkresultslistmodel)|Check detailed results. Returned in the context of a single data group, with a supplied list of other data groups.|
|[*RuleSeverityLevel*](\docs\client\models\check_results.md#ruleseveritylevel)|Rule severity levels. Matches the severity level name (warning - 1, alert - 2, fatal - 3) with a numeric level.|
|[*CheckResultStatus*](\docs\client\models\check_results.md#checkresultstatus)|Enumeration of check execution statuses. It is the highest severity or an error if the sensor cannot be executed due to a configuration issue.|
|[*CheckCurrentDataQualityStatusModel*](\docs\client\models\check_results.md#checkcurrentdataqualitystatusmodel)|The most recent data quality status for a single data quality check. If data grouping is enabled, this model will return the highest data quality issue status from all data quality results for all data groups.|
|[*ColumnCurrentDataQualityStatusModel*](\docs\client\models\check_results.md#columncurrentdataqualitystatusmodel)|The column validity status. It is a summary of the results of the most recently executed data quality checks on the column.|
|[*TableCurrentDataQualityStatusModel*](\docs\client\models\check_results.md#tablecurrentdataqualitystatusmodel)|The table validity status. It is a summary of the results of the most recently executed data quality checks on the table.|


## check_results_overview

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*Instant*](\docs\client\models\check_results_overview.md#instant)||
|[*CheckResultsOverviewDataModel*](\docs\client\models\check_results_overview.md#checkresultsoverviewdatamodel)|Check recent results overview. Returns the highest severity for the last several runs.|


## checks

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CheckDefinitionListModel*](\docs\client\models\checks.md#checkdefinitionlistmodel)|Check list model that is returned by the REST API.|
|[*CheckDefinitionFolderModel*](\docs\client\models\checks.md#checkdefinitionfoldermodel)|Check list folder model that is returned by the REST API.|
|[*CheckDefinitionModel*](\docs\client\models\checks.md#checkdefinitionmodel)|Check model that is returned by the REST API. Describes a single unique data quality check.|


## columns

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*ColumnComparisonDailyMonitoringChecksSpecMap*](\docs\client\models\columns.md#columncomparisondailymonitoringchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.|
|[*CustomCheckSpecMap*](\docs\client\models\columns.md#customcheckspecmap)|Dictionary of custom checks indexed by a check name.|
|[*ColumnDailyMonitoringCheckCategoriesSpec*](\docs\client\models\columns.md#columndailymonitoringcheckcategoriesspec)|Container of column level daily monitoring checks. Contains categories of daily monitoring checks.|
|[*ColumnComparisonDailyPartitionedChecksSpecMap*](\docs\client\models\columns.md#columncomparisondailypartitionedchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.|
|[*ColumnDailyPartitionedCheckCategoriesSpec*](\docs\client\models\columns.md#columndailypartitionedcheckcategoriesspec)|Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.|
|[*PhysicalTableName*](\docs\client\models\columns.md#physicaltablename)|Physical table name that is a combination of a schema name and a physical table name (without any quoting or escaping).|
|[*ColumnListModel*](\docs\client\models\columns.md#columnlistmodel)|Column list model that returns the basic fields from a column specification, excluding nested nodes like a list of activated checks.|
|[*ColumnModel*](\docs\client\models\columns.md#columnmodel)|Table model that returns the specification of a single column in the REST Api.|
|[*ColumnComparisonMonthlyMonitoringChecksSpecMap*](\docs\client\models\columns.md#columncomparisonmonthlymonitoringchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.|
|[*ColumnMonthlyMonitoringCheckCategoriesSpec*](\docs\client\models\columns.md#columnmonthlymonitoringcheckcategoriesspec)|Container of column level monthly monitoring checks. Contains categories of monthly monitoring checks.|
|[*ColumnComparisonMonthlyPartitionedChecksSpecMap*](\docs\client\models\columns.md#columncomparisonmonthlypartitionedchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.|
|[*ColumnMonthlyPartitionedCheckCategoriesSpec*](\docs\client\models\columns.md#columnmonthlypartitionedcheckcategoriesspec)|Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.|
|[*ColumnComparisonProfilingChecksSpecMap*](\docs\client\models\columns.md#columncomparisonprofilingchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.|
|[*ColumnProfilingCheckCategoriesSpec*](\docs\client\models\columns.md#columnprofilingcheckcategoriesspec)|Container of column level, preconfigured profiling checks.|
|[*ColumnStatisticsModel*](\docs\client\models\columns.md#columnstatisticsmodel)|Column model that returns the basic fields from a column specification and a summary of the most recent statistics collection.|
|[*TableColumnsStatisticsModel*](\docs\client\models\columns.md#tablecolumnsstatisticsmodel)|Model that returns a summary of the column statistics (the basic profiling results) for a single table, showing statistics for all columns.|


## connections

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*AllChecksPatchParameters*](\docs\client\models\connections.md#allcheckspatchparameters)||
|[*BulkCheckDeactivateParameters*](\docs\client\models\connections.md#bulkcheckdeactivateparameters)||
|[*ConnectionSpecificationModel*](\docs\client\models\connections.md#connectionspecificationmodel)|Connection model returned by the rest api.|


## dashboards

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*DashboardSpec*](\docs\client\models\dashboards.md#dashboardspec)|Description of a single dashboard that is available in the platform.|
|[*AuthenticatedDashboardModel*](\docs\client\models\dashboards.md#authenticateddashboardmodel)|Model that describes a single authenticated dashboard.|
|[*DashboardListSpec*](\docs\client\models\dashboards.md#dashboardlistspec)|List of dashboards.|
|[*DashboardsFolderListSpec*](\docs\client\models\dashboards.md#dashboardsfolderlistspec)|List of dashboard folders.|
|[*DashboardsFolderSpec*](\docs\client\models\dashboards.md#dashboardsfolderspec)|Description of a folder with multiple dashboards or other folders.|


## data_grouping_configurations

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*DataGroupingConfigurationListModel*](\docs\client\models\data_grouping_configurations.md#datagroupingconfigurationlistmodel)|Basic model for data grouping configuration on a table, returned by the rest api.|
|[*DataGroupingConfigurationModel*](\docs\client\models\data_grouping_configurations.md#datagroupingconfigurationmodel)|Model of data grouping configuration on a table returned by the rest api, including all configuration information.|
|[*DataGroupingConfigurationTrimmedModel*](\docs\client\models\data_grouping_configurations.md#datagroupingconfigurationtrimmedmodel)|Data grouping on a table model with trimmed access path.|


## data_sources

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*ConnectionTestStatus*](\docs\client\models\data_sources.md#connectionteststatus)|Tabular output format for printing the tabular results.|
|[*ConnectionTestModel*](\docs\client\models\data_sources.md#connectiontestmodel)|Connection test status result model returned from REST API. Describes the status of testing a connection (opening a connection to verify if it usable, credentials are approved and the access was granted by the tested data source).|
|[*RemoteTableListModel*](\docs\client\models\data_sources.md#remotetablelistmodel)|Remote table list model that is returned when a data source is introspected to retrieve the list of tables available in a data source.|
|[*SchemaRemoteModel*](\docs\client\models\data_sources.md#schemaremotemodel)|Schema model returned from REST API. Describes a schema on the source database with established connection.|


## default_column_check_patterns

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*DataTypeCategory*](\docs\client\models\default_column_check_patterns.md#datatypecategory)|Enumeration of common data type categories of data types. The providers will use this information to answer which of their native data types matches a category. Some sensors (and profilers) cannot operate on some data types.|
|[*TargetColumnPatternSpec*](\docs\client\models\default_column_check_patterns.md#targetcolumnpatternspec)|The configuration of a column pattern to match default column checks. Includes also the pattern for the target table.|
|[*DefaultColumnChecksPatternListModel*](\docs\client\models\default_column_check_patterns.md#defaultcolumncheckspatternlistmodel)|The listing model of column-level default check patterns that is returned by the REST API.|
|[*ColumnMonitoringCheckCategoriesSpec*](\docs\client\models\default_column_check_patterns.md#columnmonitoringcheckcategoriesspec)|Container of column level monitoring, divided by the time window (daily, monthly, etc.)|
|[*ColumnPartitionedCheckCategoriesSpec*](\docs\client\models\default_column_check_patterns.md#columnpartitionedcheckcategoriesspec)|Container of column level partitioned checks, divided by the time window (daily, monthly, etc.)|
|[*ColumnDefaultChecksPatternSpec*](\docs\client\models\default_column_check_patterns.md#columndefaultcheckspatternspec)|The default configuration of column-level data quality checks that are enabled as data observability checks to analyze basic measures and detect anomalies on columns.|
|[*DefaultColumnChecksPatternModel*](\docs\client\models\default_column_check_patterns.md#defaultcolumncheckspatternmodel)|Default column-level checks pattern model that is returned by the REST API. Describes a configuration of data quality checks for a named pattern. DQOps applies these checks on columns that match the filter.|


## default_table_check_patterns

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*TargetTablePatternSpec*](\docs\client\models\default_table_check_patterns.md#targettablepatternspec)|The configuration of a table pattern to match default table checks.|
|[*DefaultTableChecksPatternListModel*](\docs\client\models\default_table_check_patterns.md#defaulttablecheckspatternlistmodel)|The listing model of table-level default check patterns that is returned by the REST API.|
|[*TableMonitoringCheckCategoriesSpec*](\docs\client\models\default_table_check_patterns.md#tablemonitoringcheckcategoriesspec)|Container of table level monitoring, divided by the time window (daily, monthly, etc.)|
|[*TablePartitionedCheckCategoriesSpec*](\docs\client\models\default_table_check_patterns.md#tablepartitionedcheckcategoriesspec)|Container of table level partitioned checks, divided by the time window (daily, monthly, etc.)|
|[*TableDefaultChecksPatternSpec*](\docs\client\models\default_table_check_patterns.md#tabledefaultcheckspatternspec)|The default configuration of table-level data quality checks that are enabled as data observability checks to analyze basic measures and detect anomalies on tables.|
|[*DefaultTableChecksPatternModel*](\docs\client\models\default_table_check_patterns.md#defaulttablecheckspatternmodel)|Default table-level checks pattern model that is returned by the REST API. Describes a configuration of data quality checks for a named pattern. DQOps applies these checks on tables that match the filter.|


## dictionaries

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*DataDictionaryListModel*](\docs\client\models\dictionaries.md#datadictionarylistmodel)|Data dictionary CSV file list model with the basic information about the dictionary.|
|[*DataDictionaryModel*](\docs\client\models\dictionaries.md#datadictionarymodel)|Data dictionary CSV full model used to create and update the dictionary file. Contains the content of the CSV file as a text field.|


## environment

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*DqoSettingsModel*](\docs\client\models\environment.md#dqosettingsmodel)|REST API model that returns a key/value list of all DQOps configuration properties.|
|[*DqoUserRole*](\docs\client\models\environment.md#dqouserrole)|DQOps user role within a data domain or a whole account level.|
|[*DqoUserProfileModel*](\docs\client\models\environment.md#dqouserprofilemodel)|The model that describes the current user and his access rights.|


## errors

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*TimePeriodGradient*](\docs\client\models\errors.md#timeperiodgradient)|Time series gradient type (daily, monthly, quarterly, monthly, weekly, hourly).|
|[*ErrorEntryModel*](\docs\client\models\errors.md#errorentrymodel)|Detailed error statuses for a single check. Represent one row in the errors table.|
|[*ErrorsListModel*](\docs\client\models\errors.md#errorslistmodel)|Error detailed statuses. Returned in the context of a single data group, with a supplied list of other data groups.|


## incidents

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CheckResultEntryModel*](\docs\client\models\incidents.md#checkresultentrymodel)|Detailed results for a single check. Represent one row in the check results table.|
|[*CheckResultSortOrder*](\docs\client\models\incidents.md#checkresultsortorder)|Enumeration of columns names on a {@link CheckResultEntryModel CheckResultEntryModel} that can be sorted.|
|[*IncidentDailyIssuesCount*](\docs\client\models\incidents.md#incidentdailyissuescount)|A model that stores a daily number of incidents.|
|[*IncidentIssueHistogramModel*](\docs\client\models\incidents.md#incidentissuehistogrammodel)|Model that returns histograms of the data quality issue occurrences related to a data quality incident. The dates in the daily histogram are using the default timezone of the DQOps server.|
|[*IncidentStatus*](\docs\client\models\incidents.md#incidentstatus)|Enumeration of the statuses used in the &quot;status&quot; field of the &quot;incidents&quot; table.|
|[*IncidentModel*](\docs\client\models\incidents.md#incidentmodel)|Data quality incident model shown on an incident details screen.|
|[*IncidentSortOrder*](\docs\client\models\incidents.md#incidentsortorder)|Incident sort order columns.|
|[*IncidentsPerConnectionModel*](\docs\client\models\incidents.md#incidentsperconnectionmodel)|Simple model that returns a list of connections and a number of open (new) data quality incidents per connection.|
|[*SortDirection*](\docs\client\models\incidents.md#sortdirection)|REST api model sort direction.|


## jobs

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CollectStatisticsResult*](\docs\client\models\jobs.md#collectstatisticsresult)||
|[*DqoJobStatus*](\docs\client\models\jobs.md#dqojobstatus)|Job status of a job on the queue.|
|[*CollectStatisticsQueueJobResult*](\docs\client\models\jobs.md#collectstatisticsqueuejobresult)||
|[*DeleteStoredDataQueueJobParameters*](\docs\client\models\jobs.md#deletestoreddataqueuejobparameters)|Parameters for the &quot;delete stored data* queue job that deletes data from parquet files stored in DQOps user home&#x27;s *.data* directory.|
|[*DqoRoot*](\docs\client\models\jobs.md#dqoroot)|DQOps root folders in the dqo use home that may be replicated to a remote file system (uploaded to DQOps Cloud or any other cloud). It is also used as a lock scope.|
|[*ParquetPartitionId*](\docs\client\models\jobs.md#parquetpartitionid)|Identifies a single partition for hive partitioned tables stored as parquet files.|
|[*DataDeleteResultPartition*](\docs\client\models\jobs.md#datadeleteresultpartition)|Results of the &quot;data delete&quot; job for the monthly partition.|
|[*DeleteStoredDataResult*](\docs\client\models\jobs.md#deletestoreddataresult)|Compiled results of the &quot;data delete&quot;.|
|[*DeleteStoredDataQueueJobResult*](\docs\client\models\jobs.md#deletestoreddataqueuejobresult)|Object returned from the operation that queues a &quot;delete stored data&quot; job. The result contains the job id that was started and optionally can also contain a dictionary of partitions that were cleared or deleted if the operation was started with wait&#x3D;true parameter to wait for the &quot;delete stored data&quot; job to finish.|
|[*DqoJobType*](\docs\client\models\jobs.md#dqojobtype)|Job type that identifies a job by type.|
|[*FileSynchronizationDirection*](\docs\client\models\jobs.md#filesynchronizationdirection)|Data synchronization direction between a local DQOps Home and DQOps Cloud data quality data warehouse.|
|[*SynchronizeRootFolderParameters*](\docs\client\models\jobs.md#synchronizerootfolderparameters)|Parameter object for starting a file synchronization job. Identifies the folder and direction that should be synchronized.|
|[*SynchronizeRootFolderDqoQueueJobParameters*](\docs\client\models\jobs.md#synchronizerootfolderdqoqueuejobparameters)|Parameters object for a job that synchronizes one folder with DQOps Cloud.|
|[*SynchronizeMultipleFoldersDqoQueueJobParameters*](\docs\client\models\jobs.md#synchronizemultiplefoldersdqoqueuejobparameters)|Simple object for starting multiple folder synchronization jobs with the same configuration.|
|[*TimeWindowFilterParameters*](\docs\client\models\jobs.md#timewindowfilterparameters)||
|[*RunChecksResult*](\docs\client\models\jobs.md#runchecksresult)||
|[*RunChecksParameters*](\docs\client\models\jobs.md#runchecksparameters)||
|[*RunChecksOnTableParameters*](\docs\client\models\jobs.md#runchecksontableparameters)||
|[*StatisticsCollectorTarget*](\docs\client\models\jobs.md#statisticscollectortarget)||
|[*StatisticsCollectorSearchFilters*](\docs\client\models\jobs.md#statisticscollectorsearchfilters)|Hierarchy node search filters for finding enabled statistics collectors (basic profilers) to be started.|
|[*StatisticsDataScope*](\docs\client\models\jobs.md#statisticsdatascope)|Enumeration of possible statistics scopes. &quot;table&quot; - a whole table was profiled, &quot;data_groupings&quot; - groups of rows were profiled.|
|[*CollectStatisticsQueueJobParameters*](\docs\client\models\jobs.md#collectstatisticsqueuejobparameters)||
|[*CollectStatisticsOnTableQueueJobParameters*](\docs\client\models\jobs.md#collectstatisticsontablequeuejobparameters)||
|[*ImportSchemaQueueJobParameters*](\docs\client\models\jobs.md#importschemaqueuejobparameters)|Parameters for the {@link ImportSchemaQueueJob ImportSchemaQueueJob} job that imports tables from a database.|
|[*ImportTablesQueueJobParameters*](\docs\client\models\jobs.md#importtablesqueuejobparameters)|Parameters for the {@link ImportTablesQueueJob ImportTablesQueueJob} job that imports selected tables from the source database.|
|[*RepairStoredDataQueueJobParameters*](\docs\client\models\jobs.md#repairstoreddataqueuejobparameters)|Parameters for the {@link RepairStoredDataQueueJob RepairStoredDataQueueJob} job that repairs data stored in user&#x27;s &quot;.data&quot; directory.|
|[*DqoJobEntryParametersModel*](\docs\client\models\jobs.md#dqojobentryparametersmodel)|Model object returned to UI that has typed fields for each supported job parameter type.|
|[*DqoJobHistoryEntryModel*](\docs\client\models\jobs.md#dqojobhistoryentrymodel)|Model of a single job that was scheduled or has finished. It is stored in the job monitoring service on the history list.|
|[*DqoJobChangeModel*](\docs\client\models\jobs.md#dqojobchangemodel)|Describes a change to the job status or the job queue (such as a new job was added).|
|[*FolderSynchronizationStatus*](\docs\client\models\jobs.md#foldersynchronizationstatus)|Enumeration of statuses identifying the synchronization status for each folder that can be synchronized with the DQOps cloud.|
|[*CloudSynchronizationFoldersStatusModel*](\docs\client\models\jobs.md#cloudsynchronizationfoldersstatusmodel)|Model that describes the current synchronization status for each folder.|
|[*DqoJobQueueIncrementalSnapshotModel*](\docs\client\models\jobs.md#dqojobqueueincrementalsnapshotmodel)|Job history snapshot model that returns only changes after a given change sequence.|
|[*DqoJobQueueInitialSnapshotModel*](\docs\client\models\jobs.md#dqojobqueueinitialsnapshotmodel)|Returns the current snapshot of running jobs.|
|[*ImportTablesResult*](\docs\client\models\jobs.md#importtablesresult)|Result object from the {@link ImportTablesQueueJob ImportTablesQueueJob} table import job that returns list of tables that have been imported.|
|[*ImportTablesQueueJobResult*](\docs\client\models\jobs.md#importtablesqueuejobresult)|Object returned from the operation that queues a &quot;import tables&quot; job. The result contains the job id that was started and optionally can also contain the result of importing tables if the operation was started with wait&#x3D;true parameter to wait for the &quot;import tables&quot; job to finish.|
|[*RunChecksQueueJobResult*](\docs\client\models\jobs.md#runchecksqueuejobresult)||
|[*SpringErrorPayload*](\docs\client\models\jobs.md#springerrorpayload)|Object mapped to the default spring error payload (key/values).|
|[*SynchronizeMultipleFoldersQueueJobResult*](\docs\client\models\jobs.md#synchronizemultiplefoldersqueuejobresult)|Object returned from the operation that queues a &quot;synchronize multiple folders&quot; job. The result contains the job id that was started and optionally can also contain the job finish status if the operation was started with wait&#x3D;true parameter to wait for the &quot;synchronize multiple folders&quot; job to finish.|


## log_shipping

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*ExternalLogEntry*](\docs\client\models\log_shipping.md#externallogentry)|External log entry that would be logged on the server.|


## rules

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*RuleListModel*](\docs\client\models\rules.md#rulelistmodel)|Rule list model that is returned by the REST API.|
|[*RuleFolderModel*](\docs\client\models\rules.md#rulefoldermodel)|Rule folder model that is returned by the REST API.|
|[*RuleRunnerType*](\docs\client\models\rules.md#rulerunnertype)|Implementation mode for a rule runner (rule implementation).|
|[*RuleTimeWindowMode*](\docs\client\models\rules.md#ruletimewindowmode)|Rule historic data mode. A rule may evaluate only the current sensor readout (current_value) or use historic values.|
|[*ParameterDefinitionsListSpec*](\docs\client\models\rules.md#parameterdefinitionslistspec)|List of parameter definitions - the parameters for custom sensors or custom rules.|
|[*RuleModel*](\docs\client\models\rules.md#rulemodel)|Rule model that is returned by the REST API. Describes a single unique rule name.|


## schemas

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CheckTarget*](\docs\client\models\schemas.md#checktarget)|Enumeration of targets where the check is applied. It is one of &quot;table&quot; or &quot;column&quot;.|
|[*SchemaModel*](\docs\client\models\schemas.md#schemamodel)|Schema model that is returned by the REST API. Describes a single unique schema name.|


## sensor_readouts

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*SensorReadoutEntryModel*](\docs\client\models\sensor_readouts.md#sensorreadoutentrymodel)|Detailed results for a single sensor. Represent one row in the sensor readouts table.|
|[*SensorReadoutsListModel*](\docs\client\models\sensor_readouts.md#sensorreadoutslistmodel)|Sensor readout detailed results. Returned in the context of a single data group, with a supplied list of other data groups.|


## sensors

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*ProviderSensorListModel*](\docs\client\models\sensors.md#providersensorlistmodel)|Provider sensor list model that is returned by the REST API.|
|[*SensorListModel*](\docs\client\models\sensors.md#sensorlistmodel)|Sensor list model that is returned by the REST API.|
|[*SensorFolderModel*](\docs\client\models\sensors.md#sensorfoldermodel)|Sensor folder model that is returned by the REST API.|
|[*ProviderSensorModel*](\docs\client\models\sensors.md#providersensormodel)|Provider sensor model returned from REST API.|
|[*SensorModel*](\docs\client\models\sensors.md#sensormodel)|Sensor model returned from REST API.|


## shared_credentials

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CredentialType*](\docs\client\models\shared_credentials.md#credentialtype)|Credential type - a text credential or a binary credential that must be updated as a base64 value.|
|[*SharedCredentialListModel*](\docs\client\models\shared_credentials.md#sharedcredentiallistmodel)|Shared credentials list model with the basic information about the credential.|
|[*SharedCredentialModel*](\docs\client\models\shared_credentials.md#sharedcredentialmodel)|Shared credentials full model used to create and update the credential. Contains one of two forms of the credential&#x27;s value: a text or a base64 binary value.|


## table_comparison_results

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*ComparisonCheckResultModel*](\docs\client\models\table_comparison_results.md#comparisoncheckresultmodel)|The table comparison check result model for the most recent data comparison run. Identifies the check name and the number of data groupings that passed or failed the comparison.|
|[*TableComparisonColumnResultsModel*](\docs\client\models\table_comparison_results.md#tablecomparisoncolumnresultsmodel)|The table comparison column results model with the information about the most recent table comparison relating to a single compared column.|
|[*TableComparisonResultsModel*](\docs\client\models\table_comparison_results.md#tablecomparisonresultsmodel)|The table comparison results model with the summary information about the most recent table comparison that was performed.|


## table_comparisons

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CheckType*](\docs\client\models\table_comparisons.md#checktype)|Enumeration of data quality check types: profiling, monitoring, partitioned.|
|[*TableComparisonGroupingColumnPairModel*](\docs\client\models\table_comparisons.md#tablecomparisongroupingcolumnpairmodel)|Model that identifies a pair of column names used for grouping the data on both the compared table and the reference table. The groups are then matched (joined) by DQOps to compare aggregated results.|
|[*TableComparisonConfigurationModel*](\docs\client\models\table_comparisons.md#tablecomparisonconfigurationmodel)|Model that contains the basic information about a table comparison configuration that specifies how the current table can be compared with another table that is a source of truth for comparison.|
|[*CompareThresholdsModel*](\docs\client\models\table_comparisons.md#comparethresholdsmodel)|Model with the custom compare threshold levels for raising data quality issues at different severity levels when the difference between the compared (tested) table and the reference table (the source of truth) exceed given thresholds as a percentage of difference between the actual value and the expected value from the reference table.|
|[*ColumnComparisonModel*](\docs\client\models\table_comparisons.md#columncomparisonmodel)|The column to column comparison model used to select which measures (min, max, sum, mean, null count, not nul count) are compared for this column between the compared (tested) column and the reference column from the reference table.|
|[*TableComparisonModel*](\docs\client\models\table_comparisons.md#tablecomparisonmodel)|Model that contains the all editable information about a table-to-table comparison defined on a compared table.|


## tables

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*TableComparisonDailyMonitoringChecksSpecMap*](\docs\client\models\tables.md#tablecomparisondailymonitoringchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains the daily monitoring comparison checks for each configured reference table.|
|[*CustomCheckSpecMap*](\docs\client\models\tables.md#customcheckspecmap)|Dictionary of custom checks indexed by a check name.|
|[*TableDailyMonitoringCheckCategoriesSpec*](\docs\client\models\tables.md#tabledailymonitoringcheckcategoriesspec)|Container of table level daily monitoring. Contains categories of daily monitoring.|
|[*TableComparisonDailyPartitionedChecksSpecMap*](\docs\client\models\tables.md#tablecomparisondailypartitionedchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains the daily partitioned comparison checks for each configured reference table.|
|[*TableDailyPartitionedCheckCategoriesSpec*](\docs\client\models\tables.md#tabledailypartitionedcheckcategoriesspec)|Container of table level daily partitioned checks. Contains categories of daily partitioned checks.|
|[*ProfilingTimePeriod*](\docs\client\models\tables.md#profilingtimeperiod)|The time period for profiling checks (millisecond, daily, monthly, weekly, hourly). The default profiling check stores one value per month. When profiling checks is re-executed during the month, the previous profiling checks value is overwritten and only the most recent value is stored.|
|[*TableListModel*](\docs\client\models\tables.md#tablelistmodel)|Table list model returned by the rest api that is limited only to the basic fields, excluding nested nodes.|
|[*TableModel*](\docs\client\models\tables.md#tablemodel)|Full table model that returns the specification of a single table in the REST Api.|
|[*TableComparisonMonthlyMonitoringChecksSpecMap*](\docs\client\models\tables.md#tablecomparisonmonthlymonitoringchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains the monthly monitoring comparison checks for each configured reference table.|
|[*TableMonthlyMonitoringCheckCategoriesSpec*](\docs\client\models\tables.md#tablemonthlymonitoringcheckcategoriesspec)|Container of table level monthly monitoring checks. Contains categories of monthly monitoring checks.|
|[*TableComparisonMonthlyPartitionedChecksSpecMap*](\docs\client\models\tables.md#tablecomparisonmonthlypartitionedchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains the monthly partitioned comparison checks for each configured reference table.|
|[*TableMonthlyPartitionedCheckCategoriesSpec*](\docs\client\models\tables.md#tablemonthlypartitionedcheckcategoriesspec)|Container of table level monthly partitioned checks. Contains categories of monthly partitioned checks.|
|[*TablePartitioningModel*](\docs\client\models\tables.md#tablepartitioningmodel)|Rest model that returns the configuration of table partitioning information.|
|[*TableComparisonProfilingChecksSpecMap*](\docs\client\models\tables.md#tablecomparisonprofilingchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table.|
|[*TableProfilingCheckCategoriesSpec*](\docs\client\models\tables.md#tableprofilingcheckcategoriesspec)|Container of table level checks that are activated on a table level.|
|[*TableStatisticsModel*](\docs\client\models\tables.md#tablestatisticsmodel)|Model that returns a summary of the table level statistics (the basic profiling results).|


## users

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*DqoCloudUserModel*](\docs\client\models\users.md#dqocloudusermodel)|DQOps Cloud user model - identifies a user in a multi-user DQOps deployment.|


