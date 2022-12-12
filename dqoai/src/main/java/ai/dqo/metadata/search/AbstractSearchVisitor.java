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
import ai.dqo.checks.column.custom.CustomColumnCheckSpecMap;
import ai.dqo.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import ai.dqo.checks.table.adhoc.TableAdHocCheckCategoriesSpec;
import ai.dqo.checks.table.checkpoints.TableCheckpointsSpec;
import ai.dqo.checks.table.custom.CustomTableCheckSpecMap;
import ai.dqo.checks.table.partitioned.TablePartitionedChecksRootSpec;
import ai.dqo.metadata.comments.CommentSpec;
import ai.dqo.metadata.comments.CommentsListSpec;
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
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.profiling.AbstractProfilerCategorySpec;
import ai.dqo.profiling.AbstractProfilerSpec;
import ai.dqo.profiling.AbstractRootProfilerContainerSpec;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.rules.AbstractRuleThresholdsSpec;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import ai.dqo.rules.custom.CustomRuleThresholdsMap;
import ai.dqo.sensors.AbstractSensorParametersSpec;
import ai.dqo.sensors.column.AbstractColumnSensorParametersSpec;
import ai.dqo.sensors.column.AllColumnSensorsSpec;
import ai.dqo.sensors.table.AbstractTableSensorParametersSpec;
import ai.dqo.sensors.table.AllTableSensorsSpec;

/**
 * Base class for search visitors that simply visits all nodes.
 */
public abstract class AbstractSearchVisitor implements HierarchyNodeResultVisitor<SearchParameterObject, TreeNodeTraversalResult> {
    /**
     * Accepts a user home.
     *
     * @param userHome User home instance.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(UserHome userHome, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ConnectionList connectionList, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ConnectionWrapper connectionWrapper, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ConnectionSpec connectionSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(BaseProviderParametersSpec providerParametersSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(TableList tableList, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(TableWrapper tableWrapper, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(TableSpec tableSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(TableTargetSpec tableTargetSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ColumnSpecMap columnSpecMap, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ColumnSpec columnSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ColumnTypeSnapshotSpec columnTypeSnapshotSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(LabelSetSpec strings, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(AbstractRuleParametersSpec abstractRuleParametersSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(TableAdHocCheckCategoriesSpec tableCheckCategoriesSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ColumnAdHocCheckCategoriesSpec columnCheckCategoriesSpec, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a collection (dictionary) of custom table level data quality checks.
     *
     * @param customTableCheckSpecMap Dictionary of custom checks.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CustomTableCheckSpecMap customTableCheckSpecMap, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a custom column check specification.
     *
     * @param customColumnCheckSpecMap Custom column check specification.
     * @param parameter                Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CustomColumnCheckSpecMap customColumnCheckSpecMap, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts any check specification.
     *
     * @param abstractCheckSpec Data quality check specification (any).
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractCheckDeprecatedSpec abstractCheckSpec, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a rule set for a single check.
     *
     * @param abstractRuleSetSpec Rule set specification.
     * @param parameter           Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractRuleSetSpec abstractRuleSetSpec, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a container object with all possible built-in table level data quality sensors.
     *
     * @param allTableSensorsSpec All possible table sensors.
     * @param parameter Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AllTableSensorsSpec allTableSensorsSpec, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a list of all supported built-in column sensors.
     *
     * @param allColumnSensorsSpec List of all supported column sensors.
     * @param parameter            Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AllColumnSensorsSpec allColumnSensorsSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(AbstractTableSensorParametersSpec abstractTableSensorParameters, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(AbstractColumnSensorParametersSpec abstractColumnSensorParameters, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(SensorDefinitionList sensorDefinitionWrappers, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(SensorDefinitionWrapper sensorDefinitionWrapper, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(SensorDefinitionSpec sensorDefinitionSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ProviderSensorDefinitionList providerSensorDefinitionList, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ProviderSensorDefinitionSpec providerSensorDefinitionSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(RuleDefinitionList ruleDefinitionList, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(RuleDefinitionWrapper ruleDefinitionWrapper, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(DqoHomeImpl dqoHome, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts an abstract rule threshold object with multiple level of thresholds.
     *
     * @param abstractRuleThresholdsSpec Abstract rule thresholds.
     * @param parameter                  Visitor parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractRuleThresholdsSpec abstractRuleThresholdsSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(RuleDefinitionSpec ruleDefinitionSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(TimeSeriesConfigurationSpec timeSeriesConfigurationSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(DataStreamMappingSpec dataStreamMappingSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(DataStreamLevelSpec dataStreamLevelSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(TableOwnerSpec tableOwnerSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(CommentSpec commentSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(CommentsListSpec commentSpecs, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a dictionary of custom rules.
     *
     * @param customRuleThresholdsMap Dictionary of custom rules.
     * @param parameter               Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CustomRuleThresholdsMap customRuleThresholdsMap, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(RuleTimeWindowSettingsSpec ruleTimeWindowSettingsSpec, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a settings specific specification.
     * @param settingsSpec Settings specific configuration.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SettingsSpec settingsSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(FileIndexListImpl fileIndexWrappers, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(FileIndexSpec fileIndexSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(FileIndexWrapperImpl fileIndexWrapper, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(RecurringScheduleSpec recurringScheduleSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ParameterDefinitionSpec parameterDefinitionSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ParameterDefinitionsListSpec parameterDefinitionSpecs, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(AbstractCheckSpec<?,?,?,?> checkSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(AbstractRootChecksContainerSpec checksContainerSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(AbstractCheckCategorySpec abstractCheckCategorySpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(TableCheckpointsSpec tableCheckpointsSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(TablePartitionedChecksRootSpec tablePartitionedChecksRootSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ColumnCheckpointsSpec columnCheckpointsSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(ColumnPartitionedChecksRootSpec columnPartitionedChecksRootSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(TimestampColumnsSpec timestampColumnsSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(DataStreamMappingSpecMap dataStreamMappingSpecMap, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(AbstractProfilerSpec<?> profileSpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(AbstractProfilerCategorySpec profileCategorySpec, SearchParameterObject parameter) {
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
    public TreeNodeTraversalResult accept(AbstractRootProfilerContainerSpec rootProfilerContainerSpec, SearchParameterObject parameter) {
        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }
}
