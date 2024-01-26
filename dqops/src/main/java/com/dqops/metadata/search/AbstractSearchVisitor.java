/*
 * Copyright © 2021 DQOps (support@dqops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dqops.metadata.search;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringChecksRootSpec;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.checks.custom.CustomCategoryCheckSpecMap;
import com.dqops.checks.custom.CustomCheckSpecMap;
import com.dqops.checks.defaults.DefaultObservabilityChecksSpec;
import com.dqops.checks.defaults.DefaultProfilingObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultDailyMonitoringObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultMonthlyMonitoringObservabilityCheckSettingsSpec;
import com.dqops.checks.table.partitioned.TablePartitionedChecksRootSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpecMap;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairSpec;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairsListSpec;
import com.dqops.metadata.credentials.SharedCredentialList;
import com.dqops.metadata.credentials.SharedCredentialWrapper;
import com.dqops.metadata.dictionaries.DictionaryListImpl;
import com.dqops.metadata.dictionaries.DictionaryWrapperImpl;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.checks.table.monitoring.TableMonitoringChecksSpec;
import com.dqops.metadata.comments.CommentSpec;
import com.dqops.metadata.comments.CommentsListSpec;
import com.dqops.metadata.dashboards.*;
import com.dqops.metadata.definitions.checks.CheckDefinitionListImpl;
import com.dqops.metadata.definitions.checks.CheckDefinitionSpec;
import com.dqops.metadata.definitions.checks.CheckDefinitionWrapperImpl;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionSpec;
import com.dqops.metadata.definitions.rules.RuleDefinitionWrapper;
import com.dqops.metadata.definitions.sensors.*;
import com.dqops.metadata.dqohome.DqoHomeImpl;
import com.dqops.metadata.fields.ParameterDefinitionSpec;
import com.dqops.metadata.fields.ParameterDefinitionsListSpec;
import com.dqops.metadata.fileindices.FileIndexListImpl;
import com.dqops.metadata.fileindices.FileIndexSpec;
import com.dqops.metadata.fileindices.FileIndexWrapperImpl;
import com.dqops.metadata.groupings.DataGroupingDimensionSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpec;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecMap;
import com.dqops.metadata.scheduling.MonitoringSchedulesWrapper;
import com.dqops.metadata.settings.defaultchecks.DefaultObservabilityCheckWrapper;
import com.dqops.metadata.incidents.defaultnotifications.DefaultIncidentWebhookNotificationsWrapper;
import com.dqops.metadata.sources.fileformat.CsvFileFormatSpec;
import com.dqops.metadata.sources.fileformat.FileFormatSpec;
import com.dqops.metadata.sources.fileformat.JsonFileFormatSpec;
import com.dqops.metadata.sources.fileformat.ParquetFileFormatSpec;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.id.HierarchyNodeResultVisitor;
import com.dqops.metadata.incidents.IncidentWebhookNotificationsSpec;
import com.dqops.metadata.incidents.TableIncidentGroupingSpec;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.metadata.scheduling.DefaultSchedulesSpec;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.sources.*;
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
     * @param monitoringScheduleSpec Monitoring schedule.
     * @param parameter             Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(MonitoringScheduleSpec monitoringScheduleSpec, T parameter) {
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
     * @param tableMonitoringChecksSpec Table level monitoring checks container.
     * @param parameter            Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableMonitoringChecksSpec tableMonitoringChecksSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container of table level partitioned checks (daily, monthly, etc.)
     *
     * @param tablePartitionedChecksRootSpec Table level partitioned checks container.
     * @param parameter                      Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TablePartitionedChecksRootSpec tablePartitionedChecksRootSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container of column level monitoring checks (daily, monthly, etc.)
     *
     * @param columnMonitoringChecksRootSpec Column level monitoring checks container.
     * @param parameter             Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnMonitoringChecksRootSpec columnMonitoringChecksRootSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container of column level partitioned checks (daily, monthly, etc.)
     *
     * @param columnPartitionedChecksRootSpec Column level partitioned checks container.
     * @param parameter                       Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnPartitionedChecksRootSpec columnPartitionedChecksRootSpec, T parameter) {
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
     * @param defaultSchedulesSpec Container of schedule categories.
     * @param parameter                     Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DefaultSchedulesSpec defaultSchedulesSpec, T parameter) {
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
     * Accepts an incident notifications using webhooks configuration.
     *
     * @param incidentWebhookNotificationsSpec Webhooks for incident notifications.
     * @param parameter                        Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(IncidentWebhookNotificationsSpec incidentWebhookNotificationsSpec, T parameter) {
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
     * Accepts a configuration of default observability checks to enable on new tables and columns.
     *
     * @param defaultObservabilityChecksSpec Default configuration of observability checks.
     * @param parameter                             Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DefaultObservabilityChecksSpec defaultObservabilityChecksSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a configuration of default observability checks to enable on new tables and columns.
     *
     * @param defaultProfilingObservabilityCheckSettingsSpec Default configuration of observability checks.
     * @param parameter                                      Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DefaultProfilingObservabilityCheckSettingsSpec defaultProfilingObservabilityCheckSettingsSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a configuration of default observability checks to enable on new tables and columns.
     *
     * @param defaultDailyMonitoringObservabilityCheckSettingsSpec Default configuration of observability checks.
     * @param parameter                                           Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DefaultDailyMonitoringObservabilityCheckSettingsSpec defaultDailyMonitoringObservabilityCheckSettingsSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a configuration of default observability checks to enable on new tables and columns.
     *
     * @param defaultMonthlyMonitoringObservabilityCheckSettingsSpec Default configuration of observability checks.
     * @param parameter                                             Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DefaultMonthlyMonitoringObservabilityCheckSettingsSpec defaultMonthlyMonitoringObservabilityCheckSettingsSpec, T parameter) {
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
     * Accepts a default incident webhook notification wrapper instance.
     *
     * @param defaultIncidentWebhookNotificationsWrapper Default incident webhook notification wrapper instance.
     * @param parameter                Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DefaultIncidentWebhookNotificationsWrapper defaultIncidentWebhookNotificationsWrapper, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a default observability check wrapper instance.
     *
     * @param defaultObservabilityCheckWrapper Default observability check wrapper instance.
     * @param parameter                Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DefaultObservabilityCheckWrapper defaultObservabilityCheckWrapper, T parameter) {
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

}
