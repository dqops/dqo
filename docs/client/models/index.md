---
title: Reference of all models used by DQOps REST API
---
# Reference of all models used by DQOps REST API

This is a list of the models in DQOps REST API Python client broken down by individual controllers.


## common

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CheckType*](./common.md#checktype)|Enumeration of data quality check types: profiling, monitoring, partitioned.|
|[*CheckTimeScale*](./common.md#checktimescale)|Enumeration of time scale of monitoring and partitioned data quality checks (daily, monthly, etc.)|
|[*FieldModel*](./common.md#fieldmodel)|Model of a single field that is used to edit a parameter value for a sensor or a rule. Describes the type of the field and the current value.|
|[*RuleParametersModel*](./common.md#ruleparametersmodel)|Model that returns the form definition and the form data to edit parameters (thresholds) for a rule at a single severity level (low, medium, high).|
|[*CheckConfigurationModel*](./common.md#checkconfigurationmodel)|Model containing fundamental configuration of a single data quality check.|
|[*CheckListModel*](./common.md#checklistmodel)|Simplistic model that returns a single data quality check, its name and &quot;configured&quot; flag.|
|[*CheckContainerListModel*](./common.md#checkcontainerlistmodel)|Simplistic model that returns the list of data quality checks, their names, categories and &quot;configured&quot; flag.|
|[*RuleThresholdsModel*](./common.md#rulethresholdsmodel)|Model that returns the form definition and the form data to edit a single rule with all three threshold levels (low, medium, high).|
|[*DefaultRuleSeverityLevel*](./common.md#defaultruleseveritylevel)|Default rule severity levels. Matches the severity level name (warning - 1, alert - 2, fatal - 3) with a numeric level.|
|[*CronScheduleSpec*](./common.md#cronschedulespec)|Cron job schedule specification.|
|[*CheckRunScheduleGroup*](./common.md#checkrunschedulegroup)|The run check scheduling group (profiling, daily checks, monthly checks, etc), which identifies the configuration of a schedule (cron expression) used schedule these checks on the job scheduler.|
|[*EffectiveScheduleLevelModel*](./common.md#effectiveschedulelevelmodel)|Enumeration of possible levels at which a schedule can be configured.|
|[*EffectiveScheduleModel*](./common.md#effectiveschedulemodel)|Model of a configured schedule (on connection or table) or schedule override (on check). Describes the CRON expression and the time of the upcoming execution, as well as the duration until this time.|
|[*ScheduleEnabledStatusModel*](./common.md#scheduleenabledstatusmodel)|Enumeration of possible ways a schedule can be configured.|
|[*CommentSpec*](./common.md#commentspec)|Comment entry. Comments are added when a change was made and the change should be recorded in a persisted format.|
|[*CommentsListSpec*](./common.md#commentslistspec)|List of comments.|
|[*CheckSearchFilters*](./common.md#checksearchfilters)|Target data quality checks filter, identifies which checks on which tables and columns should be executed.|
|[*CheckTargetModel*](./common.md#checktargetmodel)|Enumeration of possible targets for check model request result.|
|[*SimilarCheckModel*](./common.md#similarcheckmodel)|Describes a single check that is similar to other checks in other check types.|
|[*CheckModel*](./common.md#checkmodel)|Model that returns the form definition and the form data to edit a single data quality check.|
|[*QualityCategoryModel*](./common.md#qualitycategorymodel)|Model that returns the form definition and the form data to edit all checks within a single category.|
|[*CheckContainerModel*](./common.md#checkcontainermodel)|Model that returns the form definition and the form data to edit all data quality checks divided by categories.|
|[*CheckContainerTypeModel*](./common.md#checkcontainertypemodel)|Model identifying the check type and timescale of checks belonging to a container.|
|[*CheckTemplate*](./common.md#checktemplate)|Model depicting a named data quality check that can potentially be enabled, regardless to its position in hierarchy tree.|
|[*PhysicalTableName*](./common.md#physicaltablename)|Physical table name that is a combination of a schema name and a physical table name (without any quoting or escaping).|
|[*RuleSeverityLevel*](./common.md#ruleseveritylevel)|Rule severity levels. Matches the severity level name (warning - 1, alert - 2, fatal - 3) with a numeric level.|
|[*CheckResultStatus*](./common.md#checkresultstatus)|Enumeration of check execution statuses. It is the highest severity or an error if the sensor cannot be executed due to a configuration issue.|
|[*CheckCurrentDataQualityStatusModel*](./common.md#checkcurrentdataqualitystatusmodel)|The most recent data quality status for a single data quality check. If data grouping is enabled, this model will return the highest data quality issue status from all data quality results for all data groups.|
|[*DimensionCurrentDataQualityStatusModel*](./common.md#dimensioncurrentdataqualitystatusmodel)|A model that describes the current data quality status for a single data quality dimension.|
|[*ColumnCurrentDataQualityStatusModel*](./common.md#columncurrentdataqualitystatusmodel)|The column validity status. It is a summary of the results of the most recently executed data quality checks on the column.|
|[*ColumnListModel*](./common.md#columnlistmodel)|Column list model that returns the basic fields from a column specification, excluding nested nodes like a list of activated checks.|
|[*ProviderType*](./common.md#providertype)|Data source provider type (dialect type). We will use lower case names to avoid issues with parsing, even if the enum names are not named following the Java naming convention.|
|[*ConnectionModel*](./common.md#connectionmodel)|Connection model returned by the rest api that is limited only to the basic fields, excluding nested nodes.|
|[*DqoQueueJobId*](./common.md#dqoqueuejobid)|Identifies a single job.|
|[*HistogramDailyIssuesCount*](./common.md#histogramdailyissuescount)|A model that stores a daily number of incidents.|
|[*IssueHistogramModel*](./common.md#issuehistogrammodel)|Model that returns histograms of the data quality issue occurrences related to a data quality incident or a table. The dates in the daily histogram are using the default timezone of the DQOps server.|
|[*ProfilingTimePeriodTruncation*](./common.md#profilingtimeperiodtruncation)|The time period for profiling checks (millisecond, daily, monthly, weekly, hourly). The default profiling check stores one value per month. When profiling checks is re-executed during the month, the previous profiling checks value is overwritten and only the most recent value is stored.|
|[*TableListModel*](./common.md#tablelistmodel)|Table list model returned by the rest api that is limited only to the basic fields, excluding nested nodes.|


## check_results

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CheckResultsDetailedLoadMode*](./check_results.md#checkresultsdetailedloadmode)|The mode of loading a list of detailed check results: the most recent values for each data group, or all results of the first group.|
|[*CheckResultsListModel*](./check_results.md#checkresultslistmodel)|Check detailed results. Returned in the context of a single data group, with a supplied list of other data groups.|
|[*TableCurrentDataQualityStatusModel*](./check_results.md#tablecurrentdataqualitystatusmodel)|The table validity status. It is a summary of the results of the most recently executed data quality checks on the table.|


## check_results_overview

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*Instant*](./check_results_overview.md#instant)||
|[*CheckResultsOverviewDataModel*](./check_results_overview.md#checkresultsoverviewdatamodel)|Check recent results overview. Returns the highest severity for the last several runs.|


## checks

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CheckDefinitionListModel*](./checks.md#checkdefinitionlistmodel)|Check list model that is returned by the REST API.|
|[*CheckDefinitionFolderModel*](./checks.md#checkdefinitionfoldermodel)|Check list folder model that is returned by the REST API.|
|[*CheckDefinitionModel*](./checks.md#checkdefinitionmodel)|Check model that is returned by the REST API. Describes a single unique data quality check.|


## column_quality_policies

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*TargetColumnPatternSpec*](./column_quality_policies.md#targetcolumnpatternspec)|The configuration of a column pattern to match default column checks. Includes also the pattern for the target table.|
|[*ColumnQualityPolicyListModel*](./column_quality_policies.md#columnqualitypolicylistmodel)|The listing model of column-level default check patterns that is returned by the REST API.|
|[*ColumnMonitoringCheckCategoriesSpec*](./column_quality_policies.md#columnmonitoringcheckcategoriesspec)|Container of column level monitoring, divided by the time window (daily, monthly, etc.)|
|[*ColumnPartitionedCheckCategoriesSpec*](./column_quality_policies.md#columnpartitionedcheckcategoriesspec)|Container of column level partitioned checks, divided by the time window (daily, monthly, etc.)|
|[*ColumnQualityPolicySpec*](./column_quality_policies.md#columnqualitypolicyspec)|The default configuration of column-level data quality checks that are enabled as data observability checks to analyze basic measures and detect anomalies on columns. This configuration serves as a data quality policy that defines the data quality checks that are verified on matching columns.|
|[*ColumnQualityPolicyModel*](./column_quality_policies.md#columnqualitypolicymodel)|Default column-level checks pattern model that is returned by the REST API. Describes a configuration of data quality checks for a named pattern. DQOps applies these checks on columns that match the filter.|


## columns

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*ColumnComparisonDailyMonitoringChecksSpecMap*](./columns.md#columncomparisondailymonitoringchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.|
|[*CustomCheckSpecMap*](./columns.md#customcheckspecmap)|Dictionary of custom checks indexed by a check name.|
|[*ColumnDailyMonitoringCheckCategoriesSpec*](./columns.md#columndailymonitoringcheckcategoriesspec)|Container of column level daily monitoring checks. Contains categories of daily monitoring checks.|
|[*ColumnComparisonDailyPartitionedChecksSpecMap*](./columns.md#columncomparisondailypartitionedchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.|
|[*ColumnDailyPartitionedCheckCategoriesSpec*](./columns.md#columndailypartitionedcheckcategoriesspec)|Container of data quality partitioned checks on a column level that are checking numeric values at a daily level.|
|[*ColumnModel*](./columns.md#columnmodel)|Table model that returns the specification of a single column in the REST Api.|
|[*ColumnComparisonMonthlyMonitoringChecksSpecMap*](./columns.md#columncomparisonmonthlymonitoringchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.|
|[*ColumnMonthlyMonitoringCheckCategoriesSpec*](./columns.md#columnmonthlymonitoringcheckcategoriesspec)|Container of column level monthly monitoring checks. Contains categories of monthly monitoring checks.|
|[*ColumnComparisonMonthlyPartitionedChecksSpecMap*](./columns.md#columncomparisonmonthlypartitionedchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.|
|[*ColumnMonthlyPartitionedCheckCategoriesSpec*](./columns.md#columnmonthlypartitionedcheckcategoriesspec)|Container of data quality partitioned checks on a column level that are checking numeric values at a monthly level.|
|[*ColumnComparisonProfilingChecksSpecMap*](./columns.md#columncomparisonprofilingchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains configuration of column level comparison checks. Each column level check container also defines the name of the reference column name to which we are comparing.|
|[*ColumnProfilingCheckCategoriesSpec*](./columns.md#columnprofilingcheckcategoriesspec)|Container of column level, preconfigured profiling checks.|
|[*ColumnStatisticsModel*](./columns.md#columnstatisticsmodel)|Column model that returns the basic fields from a column specification and a summary of the most recent statistics collection.|
|[*TableColumnsStatisticsModel*](./columns.md#tablecolumnsstatisticsmodel)|Model that returns a summary of the column statistics (the basic profiling results) for a single table, showing statistics for all columns.|


## connections

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*AllChecksPatchParameters*](./connections.md#allcheckspatchparameters)||
|[*BulkCheckDeactivateParameters*](./connections.md#bulkcheckdeactivateparameters)||
|[*ConnectionSpecificationModel*](./connections.md#connectionspecificationmodel)|Connection model returned by the rest api.|


## dashboards

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*DashboardSpec*](./dashboards.md#dashboardspec)|Description of a single dashboard that is available in the platform.|
|[*AuthenticatedDashboardModel*](./dashboards.md#authenticateddashboardmodel)|Model that describes a single authenticated dashboard.|
|[*DashboardListSpec*](./dashboards.md#dashboardlistspec)|List of dashboards.|
|[*DashboardsFolderListSpec*](./dashboards.md#dashboardsfolderlistspec)|List of dashboard folders.|
|[*DashboardsFolderSpec*](./dashboards.md#dashboardsfolderspec)|Description of a folder with multiple dashboards or other folders.|


## data_domains

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*LocalDataDomainModel*](./data_domains.md#localdatadomainmodel)|Model that describes a local data domain. It is also used to create new data domains.|


## data_grouping_configurations

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*DataGroupingConfigurationListModel*](./data_grouping_configurations.md#datagroupingconfigurationlistmodel)|Basic model for data grouping configuration on a table, returned by the rest api.|
|[*DataGroupingConfigurationModel*](./data_grouping_configurations.md#datagroupingconfigurationmodel)|Model of data grouping configuration on a table returned by the rest api, including all configuration information.|
|[*DataGroupingConfigurationTrimmedModel*](./data_grouping_configurations.md#datagroupingconfigurationtrimmedmodel)|Data grouping on a table model with trimmed access path.|


## data_lineage

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*DomainConnectionTableKey*](./data_lineage.md#domainconnectiontablekey)|A key object that identifies every table. These keys are used in a cache to store the most recent table quality status for each table or a data lineage cache.|
|[*TableLineageFlowModel*](./data_lineage.md#tablelineageflowmodel)|Table lineage flow model that describes the data flow from one table to another table, and the data quality status of the source table.|
|[*TableLineageModel*](./data_lineage.md#tablelineagemodel)|The table lineage model that returns all upstream tables, downstream tables, or both.|
|[*SourceColumnsSetSpec*](./data_lineage.md#sourcecolumnssetspec)|A collection of unique names of source columns from which the current column receives data. This information is used to track column-level data lineage.|
|[*ColumnLineageSourceSpec*](./data_lineage.md#columnlineagesourcespec)|Describes the list of source columns for a column in the current table.|
|[*ColumnLineageSourceSpecMap*](./data_lineage.md#columnlineagesourcespecmap)|Dictionary of mapping of source columns to the columns in the current table. The keys in this dictionary are the column names in the current table.|
|[*TableLineageSourceSpec*](./data_lineage.md#tablelineagesourcespec)|Data lineage specification for a table to identify a source table of the current table where this object is stored.|
|[*TableLineageTableListModel*](./data_lineage.md#tablelineagetablelistmodel)|Data lineage model that describes one source or target table of the current table.|


## data_sources

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*ConnectionTestStatus*](./data_sources.md#connectionteststatus)|Tabular output format for printing the tabular results.|
|[*ConnectionTestModel*](./data_sources.md#connectiontestmodel)|Connection test status result model returned from REST API. Describes the status of testing a connection (opening a connection to verify if it usable, credentials are approved and the access was granted by the tested data source).|
|[*RemoteTableListModel*](./data_sources.md#remotetablelistmodel)|Remote table list model that is returned when a data source is introspected to retrieve the list of tables available in a data source.|
|[*SchemaRemoteModel*](./data_sources.md#schemaremotemodel)|Schema model returned from REST API. Describes a schema on the source database with established connection.|


## dictionaries

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*DataDictionaryListModel*](./dictionaries.md#datadictionarylistmodel)|Data dictionary CSV file list model with the basic information about the dictionary.|
|[*DataDictionaryModel*](./dictionaries.md#datadictionarymodel)|Data dictionary CSV full model used to create and update the dictionary file. Contains the content of the CSV file as a text field.|


## environment

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*DqoSettingsModel*](./environment.md#dqosettingsmodel)|REST API model that returns a key/value list of all DQOps configuration properties.|
|[*DqoUserRole*](./environment.md#dqouserrole)|DQOps user role within a data domain or a whole account level.|
|[*DqoUserProfileModel*](./environment.md#dqouserprofilemodel)|The model that describes the current user and his access rights.|


## error_samples

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*ErrorSampleResultDataType*](./error_samples.md#errorsampleresultdatatype)|Enumeration of data types that were detected as the error sample collector result.|
|[*TimePeriodGradient*](./error_samples.md#timeperiodgradient)|Time series gradient type (daily, monthly, quarterly, monthly, weekly, hourly).|
|[*ErrorSampleEntryModel*](./error_samples.md#errorsampleentrymodel)|Detailed error samples captured for a single check. Represent one row in the error_samples table.|
|[*ErrorSamplesListModel*](./error_samples.md#errorsampleslistmodel)|List of error samples for a single check. Returned in the context of a single data group, with a supplied list of other data groups.|


## errors

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*ErrorEntryModel*](./errors.md#errorentrymodel)|Detailed error statuses for a single check. Represent one row in the errors table.|
|[*ErrorsListModel*](./errors.md#errorslistmodel)|Error detailed statuses. Returned in the context of a single data group, with a supplied list of other data groups.|


## filtered_notifications_configurations

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*FilteredNotificationModel*](./filtered_notifications_configurations.md#filterednotificationmodel)|Named filtered notification model that represents a single entry of the FilteredNotificationSpecMap.|


## incidents

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CheckResultEntryModel*](./incidents.md#checkresultentrymodel)|Detailed results for a single check. Represent one row in the check results table.|
|[*CheckResultSortOrder*](./incidents.md#checkresultsortorder)|Enumeration of columns names on a {@link CheckResultEntryModel CheckResultEntryModel} that can be sorted.|
|[*IncidentStatus*](./incidents.md#incidentstatus)|Enumeration of the statuses used in the &quot;status&quot; field of the &quot;incidents&quot; table.|
|[*IncidentFilteredNotificationLocation*](./incidents.md#incidentfilterednotificationlocation)|Enumeration that says where a filtered notification for an incident is defined. Is it defined on a connection level, or on the global level.|
|[*IncidentModel*](./incidents.md#incidentmodel)|Data quality incident model shown on an incident details screen.|
|[*IncidentSortOrder*](./incidents.md#incidentsortorder)|Incident sort order columns.|
|[*IncidentsPerConnectionModel*](./incidents.md#incidentsperconnectionmodel)|Simple model that returns a list of connections and a number of open (new) data quality incidents per connection.|
|[*SortDirection*](./incidents.md#sortdirection)|REST api model sort direction.|
|[*TopIncidentGrouping*](./incidents.md#topincidentgrouping)|Enumeration of groupings for incidents.|
|[*IncidentCountsModel*](./incidents.md#incidentcountsmodel)|Stores numbers of incidents over a specific time period.|
|[*IncidentSeverityLevelCountsModel*](./incidents.md#incidentseveritylevelcountsmodel)|Contains the counts for each of severity level.|
|[*TopIncidentsModel*](./incidents.md#topincidentsmodel)|Summary model with the most recent incidents grouped by one attribute (data quality dimension, data quality check category, etc).|


## jobs

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*TimeWindowFilterParameters*](./jobs.md#timewindowfilterparameters)||
|[*ErrorSamplesDataScope*](./jobs.md#errorsamplesdatascope)|Enumeration of possible error samples collection scopes. &quot;table&quot; - a whole table is analyzed for error samples, &quot;data_groupings&quot; - error samples are collected for each data grouping.|
|[*ErrorSamplerResult*](./jobs.md#errorsamplerresult)||
|[*CollectErrorSamplesParameters*](./jobs.md#collecterrorsamplesparameters)||
|[*DqoJobStatus*](./jobs.md#dqojobstatus)|Job status of a job on the queue.|
|[*CollectErrorSamplesResult*](./jobs.md#collecterrorsamplesresult)||
|[*CollectStatisticsResult*](./jobs.md#collectstatisticsresult)||
|[*CollectStatisticsQueueJobResult*](./jobs.md#collectstatisticsqueuejobresult)||
|[*DeleteStoredDataQueueJobParameters*](./jobs.md#deletestoreddataqueuejobparameters)|Parameters for the &quot;delete stored data* queue job that deletes data from parquet files stored in DQOps user home&#x27;s *.data* directory.|
|[*DqoRoot*](./jobs.md#dqoroot)|DQOps root folders in the dqo use home that may be replicated to a remote file system (uploaded to DQOps Cloud or any other cloud). It is also used as a lock scope.|
|[*ParquetPartitionId*](./jobs.md#parquetpartitionid)|Identifies a single partition for hive partitioned tables stored as parquet files.|
|[*DataDeleteResultPartition*](./jobs.md#datadeleteresultpartition)|Results of the &quot;data delete&quot; job for the monthly partition.|
|[*DeleteStoredDataResult*](./jobs.md#deletestoreddataresult)|Compiled results of the &quot;data delete&quot;.|
|[*DeleteStoredDataQueueJobResult*](./jobs.md#deletestoreddataqueuejobresult)|Object returned from the operation that queues a &quot;delete stored data&quot; job. The result contains the job id that was started and optionally can also contain a dictionary of partitions that were cleared or deleted if the operation was started with wait&#x3D;true parameter to wait for the &quot;delete stored data&quot; job to finish.|
|[*DqoJobType*](./jobs.md#dqojobtype)|Job type that identifies a job by type.|
|[*FileSynchronizationDirection*](./jobs.md#filesynchronizationdirection)|Data synchronization direction between a local DQOps Home and DQOps Cloud data quality data warehouse.|
|[*SynchronizeRootFolderParameters*](./jobs.md#synchronizerootfolderparameters)|Parameter object for starting a file synchronization job. Identifies the folder and direction that should be synchronized.|
|[*SynchronizeRootFolderDqoQueueJobParameters*](./jobs.md#synchronizerootfolderdqoqueuejobparameters)|Parameters object for a job that synchronizes one folder with DQOps Cloud.|
|[*SynchronizeMultipleFoldersDqoQueueJobParameters*](./jobs.md#synchronizemultiplefoldersdqoqueuejobparameters)|Simple object for starting multiple folder synchronization jobs with the same configuration.|
|[*RunChecksTarget*](./jobs.md#runcheckstarget)||
|[*RunChecksResult*](./jobs.md#runchecksresult)||
|[*RunChecksParameters*](./jobs.md#runchecksparameters)||
|[*RunChecksOnTableParameters*](./jobs.md#runchecksontableparameters)||
|[*StatisticsCollectorTarget*](./jobs.md#statisticscollectortarget)||
|[*StatisticsCollectorSearchFilters*](./jobs.md#statisticscollectorsearchfilters)|Hierarchy node search filters for finding enabled statistics collectors (basic profilers) to be started.|
|[*StatisticsDataScope*](./jobs.md#statisticsdatascope)|Enumeration of possible statistics scopes. &quot;table&quot; - a whole table was profiled, &quot;data_groupings&quot; - groups of rows were profiled.|
|[*CollectStatisticsQueueJobParameters*](./jobs.md#collectstatisticsqueuejobparameters)||
|[*CollectStatisticsOnTableQueueJobParameters*](./jobs.md#collectstatisticsontablequeuejobparameters)||
|[*CollectErrorSamplesOnTableParameters*](./jobs.md#collecterrorsamplesontableparameters)||
|[*ImportSchemaQueueJobParameters*](./jobs.md#importschemaqueuejobparameters)|Parameters for the {@link ImportSchemaQueueJob ImportSchemaQueueJob} job that imports tables from a database.|
|[*ImportTablesQueueJobParameters*](./jobs.md#importtablesqueuejobparameters)|Parameters for the {@link ImportTablesQueueJob ImportTablesQueueJob} job that imports selected tables from the source database.|
|[*RepairStoredDataQueueJobParameters*](./jobs.md#repairstoreddataqueuejobparameters)|Parameters for the {@link RepairStoredDataQueueJob RepairStoredDataQueueJob} job that repairs data stored in user&#x27;s &quot;.data&quot; directory.|
|[*DqoJobEntryParametersModel*](./jobs.md#dqojobentryparametersmodel)|Model object returned to UI that has typed fields for each supported job parameter type.|
|[*DqoJobHistoryEntryModel*](./jobs.md#dqojobhistoryentrymodel)|Model of a single job that was scheduled or has finished. It is stored in the job monitoring service on the history list.|
|[*DqoJobChangeModel*](./jobs.md#dqojobchangemodel)|Describes a change to the job status or the job queue (such as a new job was added).|
|[*FolderSynchronizationStatus*](./jobs.md#foldersynchronizationstatus)|Enumeration of statuses identifying the synchronization status for each folder that can be synchronized with the DQOps cloud.|
|[*CloudSynchronizationFoldersStatusModel*](./jobs.md#cloudsynchronizationfoldersstatusmodel)|Model that describes the current synchronization status for each folder.|
|[*DqoJobQueueIncrementalSnapshotModel*](./jobs.md#dqojobqueueincrementalsnapshotmodel)|Job history snapshot model that returns only changes after a given change sequence.|
|[*DqoJobQueueInitialSnapshotModel*](./jobs.md#dqojobqueueinitialsnapshotmodel)|Returns the current snapshot of running jobs.|
|[*ImportTablesResult*](./jobs.md#importtablesresult)|Result object from the {@link ImportTablesQueueJob ImportTablesQueueJob} table import job that returns list of tables that have been imported.|
|[*ImportTablesQueueJobResult*](./jobs.md#importtablesqueuejobresult)|Object returned from the operation that queues a &quot;import tables&quot; job. The result contains the job id that was started and optionally can also contain the result of importing tables if the operation was started with wait&#x3D;true parameter to wait for the &quot;import tables&quot; job to finish.|
|[*RunChecksQueueJobResult*](./jobs.md#runchecksqueuejobresult)||
|[*SpringErrorPayload*](./jobs.md#springerrorpayload)|Object mapped to the default spring error payload (key/values).|
|[*SynchronizeMultipleFoldersQueueJobResult*](./jobs.md#synchronizemultiplefoldersqueuejobresult)|Object returned from the operation that queues a &quot;synchronize multiple folders&quot; job. The result contains the job id that was started and optionally can also contain the job finish status if the operation was started with wait&#x3D;true parameter to wait for the &quot;synchronize multiple folders&quot; job to finish.|


## labels

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*LabelModel*](./labels.md#labelmodel)|Label model that is returned by the REST API. A label is a tag that was assigned to a data source, table, column or a single check. Labels play the role of a business glossary.|


## log_shipping

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*ExternalLogEntry*](./log_shipping.md#externallogentry)|External log entry that would be logged on the server.|


## rule_mining

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*TargetRuleSeverityLevel*](./rule_mining.md#targetruleseveritylevel)|Default rule severity levels. Matches the severity level name (warning - 1, alert - 2, fatal - 3) with a numeric level.|
|[*CheckMiningParametersModel*](./rule_mining.md#checkminingparametersmodel)|Data quality check rule mining parameters. Configure what type of checks should be configured.|
|[*CheckMiningProposalModel*](./rule_mining.md#checkminingproposalmodel)|Model that has a proposed configuration of checks on a table and its columns generated by a data quality check mining service.|


## rules

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*RuleListModel*](./rules.md#rulelistmodel)|Rule list model that is returned by the REST API.|
|[*RuleFolderModel*](./rules.md#rulefoldermodel)|Rule folder model that is returned by the REST API.|
|[*RuleRunnerType*](./rules.md#rulerunnertype)|Implementation mode for a rule runner (rule implementation).|
|[*RuleTimeWindowMode*](./rules.md#ruletimewindowmode)|Rule historic data mode. A rule may evaluate only the current sensor readout (current_value) or use historic values.|
|[*ParameterDefinitionsListSpec*](./rules.md#parameterdefinitionslistspec)|List of parameter definitions - the parameters for custom sensors or custom rules.|
|[*RuleModel*](./rules.md#rulemodel)|Rule model that is returned by the REST API. Describes a single unique rule name.|


## schemas

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CheckTarget*](./schemas.md#checktarget)|Enumeration of targets where the check is applied. It is one of &quot;table&quot; or &quot;column&quot;.|
|[*SchemaModel*](./schemas.md#schemamodel)|Schema model that is returned by the REST API. Describes a single unique schema name.|


## search

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*DataTypeCategory*](./search.md#datatypecategory)|Enumeration of common data type categories of data types. The providers will use this information to answer which of their native data types matches a category. Some sensors (and profilers) cannot operate on some data types.|


## sensor_readouts

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*SensorReadoutEntryModel*](./sensor_readouts.md#sensorreadoutentrymodel)|Detailed results for a single sensor. Represent one row in the sensor readouts table.|
|[*SensorReadoutsListModel*](./sensor_readouts.md#sensorreadoutslistmodel)|Sensor readout detailed results. Returned in the context of a single data group, with a supplied list of other data groups.|


## sensors

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*ProviderSensorListModel*](./sensors.md#providersensorlistmodel)|Provider sensor list model that is returned by the REST API.|
|[*SensorListModel*](./sensors.md#sensorlistmodel)|Sensor list model that is returned by the REST API.|
|[*SensorFolderModel*](./sensors.md#sensorfoldermodel)|Sensor folder model that is returned by the REST API.|
|[*ProviderSensorModel*](./sensors.md#providersensormodel)|Provider sensor model returned from REST API.|
|[*SensorModel*](./sensors.md#sensormodel)|Sensor model returned from REST API.|


## shared_credentials

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*CredentialType*](./shared_credentials.md#credentialtype)|Credential type - a text credential or a binary credential that must be updated as a base64 value.|
|[*SharedCredentialListModel*](./shared_credentials.md#sharedcredentiallistmodel)|Shared credentials list model with the basic information about the credential.|
|[*SharedCredentialModel*](./shared_credentials.md#sharedcredentialmodel)|Shared credentials full model used to create and update the credential. Contains one of two forms of the credential&#x27;s value: a text or a base64 binary value.|


## table_comparison_results

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*ComparisonCheckResultModel*](./table_comparison_results.md#comparisoncheckresultmodel)|The table comparison check result model for the most recent data comparison run. Identifies the check name and the number of data groupings that passed or failed the comparison.|
|[*TableComparisonColumnResultsModel*](./table_comparison_results.md#tablecomparisoncolumnresultsmodel)|The table comparison column results model with the information about the most recent table comparison relating to a single compared column.|
|[*TableComparisonResultsModel*](./table_comparison_results.md#tablecomparisonresultsmodel)|The table comparison results model with the summary information about the most recent table comparison that was performed.|


## table_comparisons

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*TableComparisonGroupingColumnPairModel*](./table_comparisons.md#tablecomparisongroupingcolumnpairmodel)|Model that identifies a pair of column names used for grouping the data on both the compared table and the reference table. The groups are then matched (joined) by DQOps to compare aggregated results.|
|[*TableComparisonConfigurationModel*](./table_comparisons.md#tablecomparisonconfigurationmodel)|Model that contains the basic information about a table comparison configuration that specifies how the current table can be compared with another table that is a source of truth for comparison.|
|[*CompareThresholdsModel*](./table_comparisons.md#comparethresholdsmodel)|Model with the custom compare threshold levels for raising data quality issues at different severity levels when the difference between the compared (tested) table and the reference table (the source of truth) exceed given thresholds as a percentage of difference between the actual value and the expected value from the reference table.|
|[*ColumnComparisonModel*](./table_comparisons.md#columncomparisonmodel)|The column to column comparison model used to select which measures (min, max, sum, mean, null count, not nul count) are compared for this column between the compared (tested) column and the reference column from the reference table.|
|[*TableComparisonModel*](./table_comparisons.md#tablecomparisonmodel)|Model that contains the all editable information about a table-to-table comparison defined on a compared table.|


## table_quality_policies

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*TargetTablePatternSpec*](./table_quality_policies.md#targettablepatternspec)|The configuration of a table pattern to match default table checks.|
|[*TableQualityPolicyListModel*](./table_quality_policies.md#tablequalitypolicylistmodel)|The listing model of table-level default check patterns that is returned by the REST API.|
|[*TableMonitoringCheckCategoriesSpec*](./table_quality_policies.md#tablemonitoringcheckcategoriesspec)|Container of table level monitoring, divided by the time window (daily, monthly, etc.)|
|[*TablePartitionedCheckCategoriesSpec*](./table_quality_policies.md#tablepartitionedcheckcategoriesspec)|Container of table level partitioned checks, divided by the time window (daily, monthly, etc.)|
|[*TableQualityPolicySpec*](./table_quality_policies.md#tablequalitypolicyspec)|The default configuration of table-level data quality checks that are enabled as data observability checks to analyze basic measures and detect anomalies on tables. This configuration serves as a data quality policy that defines the data quality checks that are verified on matching tables.|
|[*TableQualityPolicyModel*](./table_quality_policies.md#tablequalitypolicymodel)|Default table-level checks pattern model that is returned by the REST API. Describes a configuration of data quality checks for a named pattern. DQOps applies these checks on tables that match the filter.|


## tables

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*SimilarTableModel*](./tables.md#similartablemodel)|Model that describes a table that is similar to a reference table. Similar tables are used to build the data lineage graph.|
|[*TableComparisonDailyMonitoringChecksSpecMap*](./tables.md#tablecomparisondailymonitoringchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains the daily monitoring comparison checks for each configured reference table.|
|[*CustomCheckSpecMap*](./tables.md#customcheckspecmap)|Dictionary of custom checks indexed by a check name.|
|[*TableDailyMonitoringCheckCategoriesSpec*](./tables.md#tabledailymonitoringcheckcategoriesspec)|Container of table level daily monitoring. Contains categories of daily monitoring.|
|[*TableComparisonDailyPartitionedChecksSpecMap*](./tables.md#tablecomparisondailypartitionedchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains the daily partitioned comparison checks for each configured reference table.|
|[*TableDailyPartitionedCheckCategoriesSpec*](./tables.md#tabledailypartitionedcheckcategoriesspec)|Container of table level daily partitioned checks. Contains categories of daily partitioned checks.|
|[*TableModel*](./tables.md#tablemodel)|Full table model that returns the specification of a single table in the REST Api.|
|[*TableComparisonMonthlyMonitoringChecksSpecMap*](./tables.md#tablecomparisonmonthlymonitoringchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains the monthly monitoring comparison checks for each configured reference table.|
|[*TableMonthlyMonitoringCheckCategoriesSpec*](./tables.md#tablemonthlymonitoringcheckcategoriesspec)|Container of table level monthly monitoring checks. Contains categories of monthly monitoring checks.|
|[*TableComparisonMonthlyPartitionedChecksSpecMap*](./tables.md#tablecomparisonmonthlypartitionedchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table. Contains the monthly partitioned comparison checks for each configured reference table.|
|[*TableMonthlyPartitionedCheckCategoriesSpec*](./tables.md#tablemonthlypartitionedcheckcategoriesspec)|Container of table level monthly partitioned checks. Contains categories of monthly partitioned checks.|
|[*TablePartitioningModel*](./tables.md#tablepartitioningmodel)|Rest model that returns the configuration of table partitioning information.|
|[*TableComparisonProfilingChecksSpecMap*](./tables.md#tablecomparisonprofilingchecksspecmap)|Container of comparison checks for each defined data comparison. The name of the key in this dictionary must match a name of a table comparison that is defined on the parent table.|
|[*TableProfilingCheckCategoriesSpec*](./tables.md#tableprofilingcheckcategoriesspec)|Container of table level checks that are activated on a table level.|
|[*TableProfilingSetupStatusModel*](./tables.md#tableprofilingsetupstatusmodel)|Table status model that identifies which type of information is already collected, such as data quality checks are configured, or statistics collected. DQOps user interface uses this information to activate red borders to highlight tabs in the user interface that must be clicked to continue profiling the table.|
|[*TableStatisticsModel*](./tables.md#tablestatisticsmodel)|Model that returns a summary of the table level statistics (the basic profiling results).|


## users

|&nbsp;Class&nbsp;name&nbsp;|&nbsp;Description&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
|------------|---------------------------------|
|[*DqoUserRolesModel*](./users.md#dqouserrolesmodel)|DQOps user model - identifies a user in a multi-user DQOps deployment and the user&#x27;s roles.|


