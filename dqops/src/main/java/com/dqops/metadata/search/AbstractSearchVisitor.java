/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.search;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnPartitionedCheckCategoriesSpec;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.checks.custom.CustomCategoryCheckSpecMap;
import com.dqops.checks.custom.CustomCheckSpecMap;
import com.dqops.checks.table.monitoring.TableMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TablePartitionedCheckCategoriesSpec;
import com.dqops.metadata.comments.CommentSpec;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpecMap;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairSpec;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairsListSpec;
import com.dqops.metadata.credentials.SharedCredentialList;
import com.dqops.metadata.credentials.SharedCredentialWrapper;
import com.dqops.metadata.dashboards.*;
import com.dqops.metadata.settings.DataCatalogUrlsSetSpec;
import com.dqops.metadata.settings.domains.LocalDataDomainSpec;
import com.dqops.metadata.settings.domains.LocalDataDomainSpecMap;
import com.dqops.metadata.policies.column.ColumnQualityPolicyList;
import com.dqops.metadata.policies.column.ColumnQualityPolicySpec;
import com.dqops.metadata.policies.column.ColumnQualityPolicyWrapper;
import com.dqops.metadata.policies.table.TableQualityPolicyList;
import com.dqops.metadata.policies.table.TableQualityPolicySpec;
import com.dqops.metadata.policies.table.TableQualityPolicyWrapper;
import com.dqops.metadata.policies.table.TargetTablePatternSpec;
import com.dqops.metadata.definitions.checks.CheckDefinitionListImpl;
import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.definitions.checks.CheckDefinitionWrapperImpl;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionSpec;
import com.dqops.metadata.definitions.rules.RuleDefinitionWrapper;
import com.dqops.metadata.definitions.sensors.*;
import com.dqops.metadata.dictionaries.DictionaryListImpl;
import com.dqops.metadata.dictionaries.DictionaryWrapperImpl;
import com.dqops.metadata.dqohome.DqoHomeImpl;
import com.dqops.metadata.fields.ParameterDefinitionSpec;
import com.dqops.metadata.fields.ParameterDefinitionsListSpec;
import com.dqops.metadata.fileindices.FileIndexListImpl;
import com.dqops.metadata.fileindices.FileIndexSpec;
import com.dqops.metadata.fileindices.FileIndexWrapperImpl;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecMap;
import com.dqops.metadata.groupings.DataGroupingDimensionSpec;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.incidents.*;
import com.dqops.metadata.incidents.defaultnotifications.DefaultIncidentNotificationsWrapper;
import com.dqops.metadata.labels.LabelSetSpec;
import com.dqops.metadata.lineage.*;
import com.dqops.metadata.scheduling.CronSchedulesSpec;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.scheduling.MonitoringSchedulesWrapper;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.settings.SmtpServerConfigurationSpec;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexListImpl;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexSpec;
import com.dqops.metadata.similarity.ConnectionSimilarityIndexWrapperImpl;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.sources.fileformat.FileFormatSpec;
import com.dqops.metadata.sources.fileformat.FilePathListSpec;
import com.dqops.metadata.sources.fileformat.ParquetFileFormatSpec;
import com.dqops.metadata.sources.fileformat.avro.AvroFileFormatSpec;
import com.dqops.metadata.sources.fileformat.csv.CsvFileFormatSpec;
import com.dqops.metadata.sources.fileformat.deltalake.DeltaLakeFileFormatSpec;
import com.dqops.metadata.sources.fileformat.iceberg.IcebergFileFormatSpec;
import com.dqops.metadata.sources.fileformat.json.JsonFileFormatSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.rules.RuleTimeWindowSettingsSpec;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.statistics.AbstractRootStatisticsCollectorsContainerSpec;
import com.dqops.statistics.AbstractStatisticsCollectorCategorySpec;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;

/**
 * Base class for search visitors that simply visits all nodes.
 */
public abstract class AbstractSearchVisitor<T> implements HierarchyNodeResultVisitor<T, TreeNodeTraversalResult> {
    /**
     * Accepts a user home.
     *
     * @param userHome User home instance.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(UserHome userHome, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a list of connections.
     *
     * @param connectionList List of connections.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionList connectionList, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a connection wrapper (lazy loader).
     *
     * @param connectionWrapper Connection wrapper.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionWrapper connectionWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accept a connection spec.
     *
     * @param connectionSpec Connection spec.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionSpec connectionSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a provider specific connection specification nested specification.
     *
     * @param providerParametersSpec Provider specific configuration.
     * @param parameter              Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(BaseProviderParametersSpec providerParametersSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a collection of tables inside a connection.
     *
     * @param tableList Table list.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableList tableList, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a table wrapper (lazy loader).
     *
     * @param tableWrapper Table wrapper.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableWrapper tableWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a table specification.
     *
     * @param tableSpec Table specification.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableSpec tableSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a column collection (map).
     *
     * @param columnSpecMap Column collection.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnSpecMap columnSpecMap, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a column specification.
     *
     * @param columnSpec Column specification.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnSpec columnSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a column data type snapshot.
     *
     * @param columnTypeSnapshotSpec Column data type snapshot specification.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnTypeSnapshotSpec columnTypeSnapshotSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a label set specification.
     *
     * @param strings Label set.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(LabelSetSpec strings, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a rule parameters (abstract).
     *
     * @param abstractRuleParametersSpec Base class for rule parameters.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractRuleParametersSpec abstractRuleParametersSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts any sensor specification (sensor call parameters).
     *
     * @param abstractSensorParameters Sensor specification (parameters).
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractSensorParametersSpec abstractSensorParameters, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a list of sensor definitions.
     *
     * @param sensorDefinitionWrappers Sensor definitions list.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SensorDefinitionList sensorDefinitionWrappers, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a sensor definition wrapper.
     *
     * @param sensorDefinitionWrapper Sensor definition wrapper.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SensorDefinitionWrapper sensorDefinitionWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a sensor definition specification.
     *
     * @param sensorDefinitionSpec Sensor definition.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SensorDefinitionSpec sensorDefinitionSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a list of sensor definitions for different providers.
     *
     * @param providerSensorDefinitionList List of sensor definitions per provider.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ProviderSensorDefinitionList providerSensorDefinitionList, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a provider specific sensor definition wrapper (lazy loader).
     *
     * @param providerSensorDefinitionWrapper Provider specific table wrapper.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a provider specific sensor definition.
     *
     * @param providerSensorDefinitionSpec Provider specific sensor definition.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ProviderSensorDefinitionSpec providerSensorDefinitionSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a custom rule definition wrapper list that stores a list of custom rules.
     *
     * @param ruleDefinitionList Custom rule list.
     * @param parameter      Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RuleDefinitionList ruleDefinitionList, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a wrapper (lazy loader) for a custom rule definition.
     *
     * @param ruleDefinitionWrapper Custom rule wrapper.
     * @param parameter         Additional Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RuleDefinitionWrapper ruleDefinitionWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a dqo.io home root node with the DQO_HOME built-in definitions.
     *
     * @param dqoHome Dqo home root.
     * @param parameter    Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DqoHomeImpl dqoHome, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a custom rule definition specification. Those are the rule requirements.
     *
     * @param ruleDefinitionSpec Rule definition specification.
     * @param parameter                Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RuleDefinitionSpec ruleDefinitionSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a time series configuration specification on a table level.
     *
     * @param timeSeriesConfigurationSpec Time series specification.
     * @param parameter                   Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TimeSeriesConfigurationSpec timeSeriesConfigurationSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a data streams mapping configuration specification on a table level.
     *
     * @param dataGroupingConfigurationSpec Data streams mapping specification.
     * @param parameter             Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DataGroupingConfigurationSpec dataGroupingConfigurationSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a configuration of a single data stream level.
     *
     * @param dataGroupingDimensionSpec Data stream level mapping specification.
     * @param parameter           Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DataGroupingDimensionSpec dataGroupingDimensionSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a table owner specification on a table level.
     *
     * @param tableOwnerSpec Table owner specification.
     * @param parameter      Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableOwnerSpec tableOwnerSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a comment specification.
     *
     * @param commentSpec Single comment entry.
     * @param parameter   Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CommentSpec commentSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a list of comments specification.
     *
     * @param commentSpecs List of comments.
     * @param parameter    Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CommentsListSpec commentSpecs, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a rule time window configuration. This configuration is used on a rule threshold and in the sensor definition.
     *
     * @param ruleTimeWindowSettingsSpec Rule time window settings.
     * @param parameter                  Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RuleTimeWindowSettingsSpec ruleTimeWindowSettingsSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a settings specific specification.
     * @param localSettingsSpec Settings specific configuration.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(LocalSettingsSpec localSettingsSpec, T parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;//we don't want to enter SettingSpec anymore
    }

    /**
     * Accepts a file index list.
     *
     * @param fileIndexWrappers File index list.
     * @param parameter         Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(FileIndexListImpl fileIndexWrappers, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a file index specification.
     *
     * @param fileIndexSpec File index specification.
     * @param parameter     Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(FileIndexSpec fileIndexSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a file index wrapper.
     *
     * @param fileIndexWrapper File index wrapper.
     * @param parameter        Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(FileIndexWrapperImpl fileIndexWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a monitoring schedule specification, it is the cron expression how to schedule the job.
     *
     * @param cronScheduleSpec Monitoring schedule.
     * @param parameter             Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CronScheduleSpec cronScheduleSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a parameter definition specification, it describes a single parameter for custom sensors and rules.
     *
     * @param parameterDefinitionSpec Parameter definition specification.
     * @param parameter               Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ParameterDefinitionSpec parameterDefinitionSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a list of parameter definitions, it describes all parameters for custom sensors and rules.
     *
     * @param parameterDefinitionSpecs Parameter definitions list.
     * @param parameter                Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ParameterDefinitionsListSpec parameterDefinitionSpecs, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a base class for all data quality checks.
     *
     * @param checkSpec Data quality check instance.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractCheckSpec<?,?,?,?> checkSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container of categories of data quality checks.
     *
     * @param checksContainerSpec Container of data quality checks that has nested categories (and categories contain checks).
     * @param parameter           Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractRootChecksContainerSpec checksContainerSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container of data quality checks for a single category.
     *
     * @param abstractCheckCategorySpec Container of data quality checks for a single category.
     * @param parameter                 Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractCheckCategorySpec abstractCheckCategorySpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container of table level monitoring checks (daily, monthly, etc.)
     *
     * @param tableMonitoringCheckCategoriesSpec Table level monitoring checks container.
     * @param parameter            Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableMonitoringCheckCategoriesSpec tableMonitoringCheckCategoriesSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container of table level partitioned checks (daily, monthly, etc.)
     *
     * @param tablePartitionedCheckCategoriesSpec Table level partitioned checks container.
     * @param parameter                      Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TablePartitionedCheckCategoriesSpec tablePartitionedCheckCategoriesSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container of column level monitoring checks (daily, monthly, etc.)
     *
     * @param columnMonitoringCheckCategoriesSpec Column level monitoring checks container.
     * @param parameter             Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnMonitoringCheckCategoriesSpec columnMonitoringCheckCategoriesSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container of column level partitioned checks (daily, monthly, etc.)
     *
     * @param columnPartitionedCheckCategoriesSpec Column level partitioned checks container.
     * @param parameter                       Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnPartitionedCheckCategoriesSpec columnPartitionedCheckCategoriesSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container of timestamp related columns on a table level.
     *
     * @param timestampColumnsSpec Configuration of timestamp related columns.
     * @param parameter            Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TimestampColumnsSpec timestampColumnsSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a map (hashtable) of named data grouping configurations map.
     *
     * @param dataGroupingConfigurationSpecMap Data grouping configurations map.
     * @param parameter                Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DataGroupingConfigurationSpecMap dataGroupingConfigurationSpecMap, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a profiler check instance.
     *
     * @param profileSpec Profiler instance.
     * @param parameter   Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractStatisticsCollectorSpec<?> profileSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container of profiling checks (a profiling category) instance.
     *
     * @param profileCategorySpec Profiling category instance that contains profilers.
     * @param parameter           Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractStatisticsCollectorCategorySpec profileCategorySpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a root container of profiling checks (a profiling category) instance.
     *
     * @param rootProfilerContainerSpec Profiling root container instance that contains profilers.
     * @param parameter                 Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractRootStatisticsCollectorsContainerSpec rootProfilerContainerSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a dashboard configuration object.
     *
     * @param dashboardSpec Dashboard configuration.
     * @param parameter     Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DashboardSpec dashboardSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a list of dashboard configuration objects.
     *
     * @param dashboardListSpec Dashboard configuration objects.
     * @param parameter         Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DashboardListSpec dashboardListSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a folder with a list of dashboards.
     *
     * @param dashboardsFolderSpec Dashboard folder.
     * @param parameter            Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DashboardsFolderSpec dashboardsFolderSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a list of dashboard folders.
     *
     * @param dashboardsFolderSpecs List of dashboard folders.
     * @param parameter             Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DashboardsFolderListSpec dashboardsFolderSpecs, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a list of dashboards list.
     *
     * @param dashboardDefinitionWrapper List of dashboards list.
     * @param parameter             Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DashboardFolderListSpecWrapperImpl dashboardDefinitionWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container of schedules, divided by the time range.
     *
     * @param cronSchedulesSpec Container of schedule categories.
     * @param parameter                     Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CronSchedulesSpec cronSchedulesSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a configuration of incremental partition checks..
     *
     * @param partitionIncrementalTimeWindowSpec Configuration of incremental partition checks.
     * @param parameter                          Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(PartitionIncrementalTimeWindowSpec partitionIncrementalTimeWindowSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a dictionary of custom checks. The keys must be names of configured custom checks.
     *
     * @param customCheckSpecMap Dictionary of custom checks.
     * @param parameter          Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CustomCheckSpecMap customCheckSpecMap, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a dictionary of custom checks configured as a child of a category.
     *
     * @param customCategoryCheckSpecMap Dictionary of custom checks at a category level.
     * @param parameter                  Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CustomCategoryCheckSpecMap customCategoryCheckSpecMap, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a definition of a custom check.
     *
     * @param checkDefinitionSpec Custom check specification.
     * @param parameter           Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CheckDefinitionSpec checkDefinitionSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a wrapper for a definition of a custom check.
     *
     * @param checkDefinitionWrapper Custom check specification wrapper.
     * @param parameter              Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CheckDefinitionWrapperImpl checkDefinitionWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a list of custom checks.
     *
     * @param checkDefinitionWrappers Custom check list.
     * @param parameter               Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CheckDefinitionListImpl checkDefinitionWrappers, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts an incident grouping configuration.
     *
     * @param connectionIncidentGroupingSpec Incident grouping configuration.
     * @param parameter            Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionIncidentGroupingSpec connectionIncidentGroupingSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts an incident notifications using addresses configuration.
     *
     * @param incidentNotificationSpec Addresses for incident notifications.
     * @param parameter                        Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(IncidentNotificationSpec incidentNotificationSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts an incident configuration on a table level.
     *
     * @param tableIncidentGroupingSpec Incident grouping configuration.
     * @param parameter                 Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableIncidentGroupingSpec tableIncidentGroupingSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a description of the reference table to which the current table is compared.
     *
     * @param tableComparisonConfigurationSpec Reference table specification.
     * @param parameter           Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableComparisonConfigurationSpec tableComparisonConfigurationSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a dictionary of reference table comparisons.
     *
     * @param tableComparisonConfigurationSpecMap Dictionary of reference table comparisons.
     * @param parameter                       Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a map of comparison checks for a named comparison.
     *
     * @param abstractComparisonCheckCategorySpecMap Comparison map with checks.
     * @param parameter                              Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractComparisonCheckCategorySpecMap<?> abstractComparisonCheckCategorySpecMap, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a configuration of a pair of column names that are used for joining and grouping.
     *
     * @param tableComparisonGroupingColumnsPairSpec Configuration of grouping columns.
     * @param parameter                              Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableComparisonGroupingColumnsPairSpec tableComparisonGroupingColumnsPairSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a list of a pair of column names that are used for joining and grouping.
     *
     * @param tableComparisonGroupingColumnsPairSpecs A list of a pairs of columns used for grouping and joining in table comparison checks.
     * @param parameter                               Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableComparisonGroupingColumnsPairsListSpec tableComparisonGroupingColumnsPairSpecs, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a shared credential wrapper instance.
     *
     * @param sharedCredentialWrapper Shared credential wrapper.
     * @param parameter               Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SharedCredentialWrapper sharedCredentialWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a shared credential list.
     *
     * @param sharedCredentialWrappers Shared credentials list.
     * @param parameter                Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SharedCredentialList sharedCredentialWrappers, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a default monitoring schedule wrapper instance.
     *
     * @param monitoringSchedulesWrapper Default monitoring schedule wrapper instance.
     * @param parameter                  Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(MonitoringSchedulesWrapper monitoringSchedulesWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a default incident notification wrapper instance.
     *
     * @param defaultIncidentNotificationsWrapper Default incident notification wrapper instance.
     * @param parameter                Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DefaultIncidentNotificationsWrapper defaultIncidentNotificationsWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a data dictionary list.
     *
     * @param dictionaryWrappers Data dictionary list.
     * @param parameter          Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DictionaryListImpl dictionaryWrappers, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a single data dictionary wrapper.
     *
     * @param dictionaryWrapper Data dictionary wrapper.
     * @param parameter         Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DictionaryWrapperImpl dictionaryWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a file format specification.
     *
     * @param fileFormatSpec File format specification.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(FileFormatSpec fileFormatSpec, T parameter){
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a csv file format specification.
     *
     * @param csvFileFormatSpec Csv file format specification.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CsvFileFormatSpec csvFileFormatSpec, T parameter){
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a json file format specification.
     *
     * @param jsonFileFormatSpec Json file format specification.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(JsonFileFormatSpec jsonFileFormatSpec, T parameter){
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a parquet file format specification.
     *
     * @param parquetFileFormatSpec Parquet file format specification.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ParquetFileFormatSpec parquetFileFormatSpec, T parameter){
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts an iceberg table format specification.
     *
     * @param icebergFileFormatSpec Iceberg table format specification.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(IcebergFileFormatSpec icebergFileFormatSpec, T parameter){
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a Delta Lake table format specification.
     *
     * @param deltaLakeFileFormatSpec Delta Lake table format specification.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DeltaLakeFileFormatSpec deltaLakeFileFormatSpec, T parameter){
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a file path list spec
     *
     * @param filePathListSpec   File path list spec
     * @param parameter          Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(FilePathListSpec filePathListSpec, T parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a default configuration of column observability checks specification.
     *
     * @param columnQualityPolicySpec Column observability default checks specification.
     * @param parameter                                Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnQualityPolicySpec columnQualityPolicySpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a default configuration of table observability checks specification.
     *
     * @param tableQualityPolicySpec Table observability default checks specification.
     * @param parameter                               Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableQualityPolicySpec tableQualityPolicySpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a default configuration of table observability checks wrapper.
     *
     * @param tableQualityPolicyWrapper Table observability default checks specification.
     * @param parameter                        Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableQualityPolicyWrapper tableQualityPolicyWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a list of default configuration of table observability checks wrappers.
     *
     * @param tableDefaultChecksPatternWrappers Table observability default checks list.
     * @param parameter                         Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableQualityPolicyList tableDefaultChecksPatternWrappers, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a default configuration of column observability checks wrapper.
     *
     * @param columnQualityPolicyWrapper Column observability default checks specification.
     * @param parameter                         Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnQualityPolicyWrapper columnQualityPolicyWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a default configuration of column observability checks wrapper.
     *
     * @param columnDefaultChecksPatternWrappers Column observability default checks specification.
     * @param parameter                          Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnQualityPolicyList columnDefaultChecksPatternWrappers, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accept a table filter for a default check pattern.
     *
     * @param targetTablePatternSpec Table pattern specification.
     * @param parameter        Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TargetTablePatternSpec targetTablePatternSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }


    /**
     * Accepts a default configuration of column observability checks wrapper.
     *
     * @param smtpServerConfigurationSpec SMTP server configuration spec
     * @param parameter                   Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SmtpServerConfigurationSpec smtpServerConfigurationSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a filtered notification.
     *
     * @param filteredNotificationSpec Filtered notification specification.
     * @param parameter                   Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(FilteredNotificationSpec filteredNotificationSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a map (hashtable) of filtered notification mappings.
     *
     * @param filteredNotificationSpecMap Filtered notification mappings map.
     * @param parameter                   Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(FilteredNotificationSpecMap filteredNotificationSpecMap, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accept a notification filter.
     *
     * @param notificationFilterSpec Notification filter specification.
     * @param parameter                   Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(NotificationFilterSpec notificationFilterSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a filtered notification target.
     *
     * @param incidentNotificationTargetSpec Filtered notification target specification.
     * @param parameter                   Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(IncidentNotificationTargetSpec incidentNotificationTargetSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accept a source table data lineage specification.
     *
     * @param tableLineageSourceSpec Source table on the data lineage.
     * @param parameter              Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableLineageSourceSpec tableLineageSourceSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accept a list of source table data lineage specifications.
     *
     * @param tableLineageSourceSpecs List of source table on the data lineages.
     * @param parameter               Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableLineageSourceSpecList tableLineageSourceSpecs, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accept a source columns data lineage specification.
     *
     * @param columnLineageSourceSpec Source columns on the data lineage.
     * @param parameter               Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnLineageSourceSpec columnLineageSourceSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accept a dictionary of source columns data lineage specification.
     *
     * @param columnLineageSourceSpecMap Dictionary of source columns on the data lineage.
     * @param parameter                  Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnLineageSourceSpecMap columnLineageSourceSpecMap, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accept a list of source column names in the data lineage.
     *
     * @param sourceColumnsSetSpec Source column names for a single target column.
     * @param parameter            Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SourceColumnsSetSpec sourceColumnsSetSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts the configuration object of a local data domain.
     *
     * @param localDataDomainSpec Local data domain specification.
     * @param parameter           Visitor parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(LocalDataDomainSpec localDataDomainSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts the map (dictionary) object containing the configuration of local data domain.
     *
     * @param localDataDomainSpecMap Local data domain map.
     * @param parameter              Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(LocalDataDomainSpecMap localDataDomainSpecMap, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts the set of data catalog urls.
     *
     * @param urlsSetSpec Collection of data quality urls to send data quality health statuses.
     * @param parameter   Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DataCatalogUrlsSetSpec urlsSetSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts an auto table import configuration object that is configured on a connection level.
     *
     * @param autoImportTablesSpec Auto import tables specification.
     * @param parameter            Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AutoImportTablesSpec autoImportTablesSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a table similarity score holder.
     *
     * @param connectionSimilarityIndexSpec Table similarity score holder.
     * @param parameter                Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionSimilarityIndexSpec connectionSimilarityIndexSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a wrapper over a connection similarity score holder.
     *
     * @param connectionTableSimilarityScoresWrapper Connection table similarity score wrapper.
     * @param parameter                              Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionSimilarityIndexWrapperImpl connectionTableSimilarityScoresWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a list of connection similarity wrappers.
     *
     * @param connectionSimilarityIndexWrappers Connection similarity wrappers.
     * @param parameter                         Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionSimilarityIndexListImpl connectionSimilarityIndexWrappers, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts an Avro file configuration settings.
     *
     * @param avroFileFormatSpec Avro connection settings.
     * @param parameter          Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AvroFileFormatSpec avroFileFormatSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }
}
