/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.metadata.search;

import ai.dqo.checks.*;
import ai.dqo.checks.column.adhoc.ColumnAdHocCheckCategoriesSpec;
import ai.dqo.checks.column.checkpoints.ColumnCheckpointsSpec;
import ai.dqo.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import ai.dqo.checks.table.adhoc.TableAdHocCheckCategoriesSpec;
import ai.dqo.checks.table.checkpoints.TableCheckpointsSpec;
import ai.dqo.checks.table.partitioned.TablePartitionedChecksRootSpec;
import ai.dqo.metadata.comments.CommentSpec;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.dashboards.*;
import ai.dqo.metadata.definitions.rules.RuleDefinitionList;
import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.*;
import ai.dqo.metadata.dqohome.DqoHomeImpl;
import ai.dqo.metadata.fields.ParameterDefinitionSpec;
import ai.dqo.metadata.fields.ParameterDefinitionsListSpec;
import ai.dqo.metadata.fileindices.FileIndexListImpl;
import ai.dqo.metadata.fileindices.FileIndexSpec;
import ai.dqo.metadata.fileindices.FileIndexWrapperImpl;
import ai.dqo.metadata.groupings.DataStreamLevelSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.groupings.DataStreamMappingSpecMap;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.id.HierarchyNodeResultVisitor;
import ai.dqo.metadata.notifications.NotificationSettingsSpec;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.scheduling.RecurringSchedulesSpec;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.profiling.AbstractStatisticsCollectorCategorySpec;
import ai.dqo.profiling.AbstractStatisticsCollectorSpec;
import ai.dqo.profiling.AbstractRootStatisticsCollectorsContainerSpec;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import ai.dqo.sensors.column.AbstractColumnSensorParametersSpec;
import ai.dqo.sensors.table.AbstractTableSensorParametersSpec;

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
     * Accepts a table target (physical table information).
     *
     * @param tableTargetSpec Physical target table specification.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableTargetSpec tableTargetSpec, T parameter) {
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
     * Accepts a configuration of built-in table level checks.
     *
     * @param tableCheckCategoriesSpec Built-in table level checks.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableAdHocCheckCategoriesSpec tableCheckCategoriesSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a configuration of built-in column level checks.
     *
     * @param columnCheckCategoriesSpec Built-in column level checks.
     * @param parameter                        Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnAdHocCheckCategoriesSpec columnCheckCategoriesSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts any table level sensor specification (sensor call parameters).
     *
     * @param abstractTableSensorParameters Table level sensor specification (parameters).
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractTableSensorParametersSpec abstractTableSensorParameters, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts any column level sensor specification (sensor call parameters).
     *
     * @param abstractColumnSensorParameters Column level sensor specification (parameters).
     * @param parameter                      Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractColumnSensorParametersSpec abstractColumnSensorParameters, T parameter) {
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
     * @param dataStreamMappingSpec Data streams mapping specification.
     * @param parameter             Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DataStreamMappingSpec dataStreamMappingSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a configuration of a single data stream level.
     *
     * @param dataStreamLevelSpec Data stream level mapping specification.
     * @param parameter           Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DataStreamLevelSpec dataStreamLevelSpec, T parameter) {
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
     * @param settingsSpec Settings specific configuration.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SettingsSpec settingsSpec, T parameter) {
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
     * Accepts a recurring schedule specification, it is the cron expression how to schedule the job.
     *
     * @param recurringScheduleSpec Recurring schedule.
     * @param parameter             Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RecurringScheduleSpec recurringScheduleSpec, T parameter) {
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
     * Accepts a container of table level checkpoints (daily, monthly, etc.)
     *
     * @param tableCheckpointsSpec Table level checkpoints container.
     * @param parameter            Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableCheckpointsSpec tableCheckpointsSpec, T parameter) {
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
     * Accepts a container of column level checkpoints (daily, monthly, etc.)
     *
     * @param columnCheckpointsSpec Column level checkpoints container.
     * @param parameter             Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnCheckpointsSpec columnCheckpointsSpec, T parameter) {
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
     * Accepts a map (hashtable) of named data stream mappings.
     *
     * @param dataStreamMappingSpecMap Data stream mappings map.
     * @param parameter                Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DataStreamMappingSpecMap dataStreamMappingSpecMap, T parameter) {
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
     * Accepts a notification settings object.
     *
     * @param notificationSettingsSpec Notification settings.
     * @param parameter                Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(NotificationSettingsSpec notificationSettingsSpec, T parameter) {
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
     * @param recurringSchedulesSpec Container of schedule categories.
     * @param parameter                     Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RecurringSchedulesSpec recurringSchedulesSpec, T parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }
}
