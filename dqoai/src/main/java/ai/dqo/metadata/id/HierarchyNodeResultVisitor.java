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
package ai.dqo.metadata.id;

import ai.dqo.checks.AbstractCheckSpec;
import ai.dqo.checks.AbstractRuleSetSpec;
import ai.dqo.checks.column.ColumnCheckCategoriesSpec;
import ai.dqo.checks.column.completeness.BuiltInColumnCompletenessChecksSpec;
import ai.dqo.checks.column.consistency.BuiltInColumnConsistencyChecksSpec;
import ai.dqo.checks.column.custom.CustomColumnCheckSpecMap;
import ai.dqo.checks.column.uniqueness.BuiltInColumnUniquenessChecksSpec;
import ai.dqo.checks.column.validity.BuiltInColumnValidityChecksSpec;
import ai.dqo.checks.table.TableCheckCategoriesSpec;
import ai.dqo.checks.table.consistency.BuiltInTableConsistencyChecksSpec;
import ai.dqo.checks.table.custom.CustomTableCheckSpecMap;
import ai.dqo.checks.table.timeliness.BuiltInTableTimelinessChecksSpec;
import ai.dqo.checks.table.relevance.BuiltInTableRelevanceChecksSpec;
import ai.dqo.checks.table.validity.BuiltInTableValidityChecksSpec;
import ai.dqo.metadata.comments.CommentSpec;
import ai.dqo.metadata.comments.CommentsListSpec;
import ai.dqo.metadata.definitions.rules.RuleDefinitionList;
import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.*;
import ai.dqo.metadata.dqohome.DqoHomeImpl;
import ai.dqo.metadata.fileindices.FileIndexListImpl;
import ai.dqo.metadata.fileindices.FileIndexSpec;
import ai.dqo.metadata.fileindices.FileIndexWrapperImpl;
import ai.dqo.metadata.groupings.DimensionMappingSpec;
import ai.dqo.metadata.groupings.DimensionsConfigurationSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rules.AbstractRuleParametersSpec;
import ai.dqo.rules.AbstractRuleThresholdsSpec;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import ai.dqo.rules.custom.CustomRuleThresholdsMap;
import ai.dqo.sensors.column.AbstractColumnSensorParametersSpec;
import ai.dqo.sensors.column.AllColumnSensorsSpec;
import ai.dqo.sensors.table.AbstractTableSensorParametersSpec;
import ai.dqo.sensors.table.AllTableSensorsSpec;

/**
 * Hierarchy node visitor (for the visitor design pattern) whose "accept" methods return a result.
 * @param <P> Accept parameter type.
 * @param <R> Result type of the "accept" method.
 */
public interface HierarchyNodeResultVisitor<P, R> {
    /**
     * Accepts a user home.
     * @param userHome User home instance.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(UserHome userHome, P parameter);

    /**
     * Accepts a list of connections.
     * @param connectionList List of connections.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(ConnectionList connectionList, P parameter);

    /**
     * Accepts a connection wrapper (lazy loader).
     * @param connectionWrapper Connection wrapper.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(ConnectionWrapper connectionWrapper, P parameter);

    /**
     * Accept a connection spec.
     * @param connectionSpec Connection spec.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(ConnectionSpec connectionSpec, P parameter);

    /**
     * Accepts a collection of tables inside a connection.
     * @param tableList Table list.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(TableList tableList, P parameter);

    /**
     * Accepts a table wrapper (lazy loader).
     * @param tableWrapper Table wrapper.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(TableWrapper tableWrapper, P parameter);

    /**
     * Accepts a table specification.
     * @param tableSpec Table specification.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(TableSpec tableSpec, P parameter);

    /**
     * Accepts a table target (physical table information).
     * @param tableTargetSpec Physical target table specification.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(TableTargetSpec tableTargetSpec, P parameter);

    /**
     * Accepts a column collection (map).
     * @param columnSpecMap Column collection.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(ColumnSpecMap columnSpecMap, P parameter);

    /**
     * Accepts a column specification.
     * @param columnSpec Column specification.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(ColumnSpec columnSpec, P parameter);

    /**
     * Accepts a column data type snapshot.
     * @param columnTypeSnapshotSpec Column data type snapshot specification.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(ColumnTypeSnapshotSpec columnTypeSnapshotSpec, P parameter);

    /**
     * Accepts a label set specification.
     * @param strings Label set.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(LabelSetSpec strings, P parameter);

    /**
     * Accepts a rule parameters (abstract).
     * @param abstractRuleParametersSpec Base class for rule parameters.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(AbstractRuleParametersSpec abstractRuleParametersSpec, P parameter);

    /**
     * Accepts a configuration of built-in table level checks.
     * @param tableCheckCategoriesSpec Built-in table level checks.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(TableCheckCategoriesSpec tableCheckCategoriesSpec, P parameter);

    /**
     * Accepts a configuration of built-in column level checks.
     * @param columnCheckCategoriesSpec Built-in column level checks.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(ColumnCheckCategoriesSpec columnCheckCategoriesSpec, P parameter);

    /**
     * Accepts a collection (dictionary) of custom table level data quality checks.
     * @param customTableCheckSpecMap Dictionary of custom checks.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(CustomTableCheckSpecMap customTableCheckSpecMap, P parameter);

    /**
     * Accepts a custom column check specification.
     * @param customColumnCheckSpecMap Custom column check specification.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(CustomColumnCheckSpecMap customColumnCheckSpecMap, P parameter);

    /**
     * Accepts any check specification.
     * @param abstractCheckSpec Data quality check specification (any).
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(AbstractCheckSpec abstractCheckSpec, P parameter);

    /**
     * Accepts a container object with all possible built-in table level data quality sensors.
     * @param allTableSensorsSpec All possible table sensors.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(AllTableSensorsSpec allTableSensorsSpec, P parameter);

    /**
     * Accepts a list of all supported built-in column sensors.
     * @param allColumnSensorsSpec List of all supported column sensors.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(AllColumnSensorsSpec allColumnSensorsSpec, P parameter);

    /**
     * Accepts any table level sensor specification (sensor call parameters).
     * @param abstractTableSensorParameters Table level sensor specification (parameters).
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(AbstractTableSensorParametersSpec abstractTableSensorParameters, P parameter);

    /**
     * Accepts any column level sensor specification (sensor call parameters).
     * @param abstractColumnSensorParameters Column level sensor specification (parameters).
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(AbstractColumnSensorParametersSpec abstractColumnSensorParameters, P parameter);

    /**
     * Accepts a container of table level consistency data quality checks.
     * @param builtInTableConsistencyChecksSpec Consistency checks.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(BuiltInTableConsistencyChecksSpec builtInTableConsistencyChecksSpec, P parameter);

    /**
     * Accepts a configuration of built-in validity sensors on a table level.
     * @param builtInTableValidityChecksSpec Built-in validity sensors on a table level.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(BuiltInTableValidityChecksSpec builtInTableValidityChecksSpec, P parameter);

    /**
     * Accepts a configuration of built-in relevance sensors on a table level.
     * @param builtInTableRelevanceChecksSpec Built-in relevance sensors on a table level.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(BuiltInTableRelevanceChecksSpec builtInTableRelevanceChecksSpec, P parameter);

    /**
     * Accepts a configuration of built-in validity sensors on a column level.
     * @param builtInColumnValidityChecksSpec Built-in validity sensors on a column level.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(BuiltInColumnValidityChecksSpec builtInColumnValidityChecksSpec, P parameter);

    /**
     * Accepts a configuration of built-in validity sensors on a column level.
     * @param builtInTableTimelinessChecksSpec Built-in validity sensors on a column level.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(BuiltInTableTimelinessChecksSpec builtInTableTimelinessChecksSpec, P parameter);

    /*
     * Accepts a configuration of built-in uniqueness sensors on a column level.
     * @param builtInColumnUniquenessChecksSpec Built-in uniqueness sensors on a column level.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(BuiltInColumnUniquenessChecksSpec builtInColumnUniquenessChecksSpec, P parameter);

    /**
     * Accepts a configuration of built-in completeness sensors on a column level.
     * @param builtInColumnCompletenessChecksSpec Built-in completeness sensors on a column level.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(BuiltInColumnCompletenessChecksSpec builtInColumnCompletenessChecksSpec, P parameter);

    /**
     * Accepts a configuration of built-in consistency sensors on a column level.
     * @param builtInColumnConsistencyChecksSpec Built-in consistency sensors on a column level.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(BuiltInColumnConsistencyChecksSpec builtInColumnConsistencyChecksSpec, P parameter);

    /**
     * Accepts a list of sensor definitions.
     * @param sensorDefinitionWrappers Sensor definitions list.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(SensorDefinitionList sensorDefinitionWrappers, P parameter);

    /**
     * Accepts a sensor definition wrapper.
     * @param sensorDefinitionWrapper Sensor definition wrapper.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(SensorDefinitionWrapper sensorDefinitionWrapper, P parameter);

    /**
     * Accepts a sensor definition specification.
     * @param sensorDefinitionSpec Sensor definition.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(SensorDefinitionSpec sensorDefinitionSpec, P parameter);

    /**
     * Accepts a list of sensor definitions for different providers.
     * @param providerSensorDefinitionList List of sensor definitions per provider.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(ProviderSensorDefinitionList providerSensorDefinitionList, P parameter);

    /**
     * Accepts a provider specific sensor definition wrapper (lazy loader).
     * @param providerSensorDefinitionWrapper Provider specific table wrapper.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper, P parameter);

    /**
     * Accepts a provider specific sensor definition.
     * @param providerSensorDefinitionSpec Provider specific sensor definition.
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(ProviderSensorDefinitionSpec providerSensorDefinitionSpec, P parameter);

    /**
     * Accepts a wrapper (lazy loader) for a custom rule definition.
     * @param ruleDefinitionWrapper Custom rule wrapper.
     * @param parameter Additional Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(RuleDefinitionWrapper ruleDefinitionWrapper, P parameter);

    /**
     * Accepts a custom rule definition wrapper list that stores a list of custom rules.
     * @param ruleDefinitionList Custom rule list.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(RuleDefinitionList ruleDefinitionList, P parameter);

    /**
     * Accepts a dqo.io home root node with the DQO_HOME built-in definitions.
     * @param dqoHome Dqo home root.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(DqoHomeImpl dqoHome, P parameter);

    /**
     * Accepts a rule set for a single check.
     * @param abstractRuleSetSpec Rule set specification.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(AbstractRuleSetSpec abstractRuleSetSpec, P parameter);

    /**
     * Accepts an abstract rule threshold object with multiple level of thresholds.
     * @param abstractRuleThresholdsSpec Abstract rule thresholds.
     * @param parameter Visitor parameter.
     * @return Accept's result.
     */
    R accept(AbstractRuleThresholdsSpec abstractRuleThresholdsSpec, P parameter);

    /**
     * Accepts a custom rule definition specification. Those are the rule requirements.
     * @param ruleDefinitionSpec Rule definition specification.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(RuleDefinitionSpec ruleDefinitionSpec, P parameter);

    /**
     * Accepts a time series configuration specification on a table level.
     * @param timeSeriesConfigurationSpec Time series specification.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(TimeSeriesConfigurationSpec timeSeriesConfigurationSpec, P parameter);

    /**
     * Accepts a time series configuration specification on a table level.
     * @param dimensionsConfigurationSpec Time series specification.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(DimensionsConfigurationSpec dimensionsConfigurationSpec, P parameter);

    /**
     * Accepts a table owner specification on a table level.
     * @param tableOwnerSpec Table owner specification.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(TableOwnerSpec tableOwnerSpec, P parameter);

    /**
     * Accepts a comment specification.
     * @param commentSpec Single comment entry.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(CommentSpec commentSpec, P parameter);

    /**
     * Accepts a list of comments specification.
     * @param commentSpecs List of comments.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(CommentsListSpec commentSpecs, P parameter);

    /**
     * Accepts a configuration of a single dimension.
     * @param dimensionMappingSpec Dimension mapping specification.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(DimensionMappingSpec dimensionMappingSpec, P parameter);

    /**
     * Accepts a dictionary of custom rules.
     * @param customRuleThresholdsMap Dictionary of custom rules.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(CustomRuleThresholdsMap customRuleThresholdsMap, P parameter);

    /**
     * Accepts a provider specific connection specification nested specification.
     * @param providerParametersSpec Provider specific configuration.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(BaseProviderParametersSpec providerParametersSpec, P parameter);

    /**
     * Accepts a rule time window configuration. This configuration is used on a rule threshold and in the sensor definition.
     * @param ruleTimeWindowSettingsSpec Rule time window settings.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(RuleTimeWindowSettingsSpec ruleTimeWindowSettingsSpec, P parameter);

    /**
     * Accepts a settings specific specification.
     * @param settingsSpec Settings specific configuration.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(SettingsSpec settingsSpec, P parameter);

    /**
     * Accepts a file index list.
     * @param fileIndexWrappers File index list.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(FileIndexListImpl fileIndexWrappers, P parameter);

    /**
     * Accepts a file index specification.
     * @param fileIndexSpec File index specification.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(FileIndexSpec fileIndexSpec, P parameter);

    /**
     * Accepts a file index wrapper.
     * @param fileIndexWrapper File index wrapper.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(FileIndexWrapperImpl fileIndexWrapper, P parameter);

    /**
     * Accepts a recurring schedule specification, it is the cron expression how to schedule the job.
     * @param recurringScheduleSpec Recurring schedule.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(RecurringScheduleSpec recurringScheduleSpec, P parameter);
}
