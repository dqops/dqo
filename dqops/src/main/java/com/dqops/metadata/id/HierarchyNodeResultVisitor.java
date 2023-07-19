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
package com.dqops.metadata.id;

import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import com.dqops.checks.column.recurring.ColumnRecurringChecksRootSpec;
import com.dqops.checks.comparison.AbstractComparisonCheckCategorySpecMap;
import com.dqops.checks.custom.CustomCheckSpecMap;
import com.dqops.checks.defaults.DefaultObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultProfilingObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultDailyRecurringObservabilityCheckSettingsSpec;
import com.dqops.checks.defaults.DefaultMonthlyRecurringObservabilityCheckSettingsSpec;
import com.dqops.checks.table.partitioned.TablePartitionedChecksRootSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpecMap;
import com.dqops.metadata.incidents.ConnectionIncidentGroupingSpec;
import com.dqops.checks.table.recurring.TableRecurringChecksSpec;
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
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.incidents.IncidentWebhookNotificationsSpec;
import com.dqops.metadata.incidents.TableIncidentGroupingSpec;
import com.dqops.metadata.scheduling.RecurringScheduleSpec;
import com.dqops.metadata.scheduling.RecurringSchedulesSpec;
import com.dqops.metadata.settings.SettingsSpec;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rules.AbstractRuleParametersSpec;
import com.dqops.rules.RuleTimeWindowSettingsSpec;
import com.dqops.sensors.AbstractSensorParametersSpec;
import com.dqops.statistics.AbstractRootStatisticsCollectorsContainerSpec;
import com.dqops.statistics.AbstractStatisticsCollectorCategorySpec;
import com.dqops.statistics.AbstractStatisticsCollectorSpec;


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
     * Accepts any sensor specification (sensor call parameters).
     * @param abstractSensorParameters Sensor specification (parameters).
     * @param parameter Additional parameter.
     * @return Accept's result.
     */
    R accept(AbstractSensorParametersSpec abstractSensorParameters, P parameter);

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
     * Accepts a data groupings configuration specification on a table level.
     * @param dataGroupingConfigurationSpec Data groupings configuration specification.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(DataGroupingConfigurationSpec dataGroupingConfigurationSpec, P parameter);

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
     * Accepts a configuration of a single data grouping dimension.
     * @param dataGroupingDimensionSpec Data grouping mapping specification.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(DataGroupingDimensionSpec dataGroupingDimensionSpec, P parameter);

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

    /**
     * Accepts a parameter definition specification, it describes a single parameter for custom sensors and rules.
     * @param parameterDefinitionSpec Parameter definition specification.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(ParameterDefinitionSpec parameterDefinitionSpec, P parameter);

    /**
     * Accepts a list of parameter definitions, it describes all parameters for custom sensors and rules.
     * @param parameterDefinitionSpecs Parameter definitions list.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(ParameterDefinitionsListSpec parameterDefinitionSpecs, P parameter);

    /**
     * Accepts a base class for all data quality checks.
     * @param checkSpec Data quality check instance.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(AbstractCheckSpec<?,?,?,?> checkSpec, P parameter);

    /**
     * Accepts a container of categories of data quality checks.
     * @param checksContainerSpec Container of data quality checks that has nested categories (and categories contain checks).
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(AbstractRootChecksContainerSpec checksContainerSpec, P parameter);

    /**
     * Accepts a container of data quality checks for a single category.
     * @param abstractCheckCategorySpec Container of data quality checks for a single category.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(AbstractCheckCategorySpec abstractCheckCategorySpec, P parameter);

    /**
     * Accepts a container of table level recurring checks (daily, monthly, etc.)
     * @param tableRecurringChecksSpec Table level recurring checks container.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(TableRecurringChecksSpec tableRecurringChecksSpec, P parameter);

    /**
     * Accepts a container of table level partitioned checks (daily, monthly, etc.)
     * @param tablePartitionedChecksRootSpec Table level partitioned checks container.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(TablePartitionedChecksRootSpec tablePartitionedChecksRootSpec, P parameter);

    /**
     * Accepts a container of column level recurring checks (daily, monthly, etc.)
     * @param columnRecurringChecksRootSpec Column level recurring checks container.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(ColumnRecurringChecksRootSpec columnRecurringChecksRootSpec, P parameter);

    /**
     * Accepts a container of column level partitioned checks (daily, monthly, etc.)
     * @param columnPartitionedChecksRootSpec Column level partitioned checks container.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(ColumnPartitionedChecksRootSpec columnPartitionedChecksRootSpec, P parameter);

    /**
     * Accepts a container of timestamp related columns on a table level.
     * @param timestampColumnsSpec Configuration of timestamp related columns.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(TimestampColumnsSpec timestampColumnsSpec, P parameter);

    /**
     * Accepts a map (hashtable) of named data groupings mappings.
     * @param dataGroupingConfigurationSpecMap Data groupings mappings map.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(DataGroupingConfigurationSpecMap dataGroupingConfigurationSpecMap, P parameter);

    /**
     * Accepts a profiler check instance.
     * @param profileSpec Profiler instance.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(AbstractStatisticsCollectorSpec<?> profileSpec, P parameter);

    /**
     * Accepts a container of profiling checks (a profiling category) instance.
     * @param profileCategorySpec Profiling category instance that contains profilers.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(AbstractStatisticsCollectorCategorySpec profileCategorySpec, P parameter);

    /**
     * Accepts a root container of profiling checks (a profiling category) instance.
     * @param rootProfilerContainerSpec Profiling root container instance that contains profilers.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(AbstractRootStatisticsCollectorsContainerSpec rootProfilerContainerSpec, P parameter);

    /**
     * Accepts a dashboard configuration object.
     * @param dashboardSpec Dashboard configuration.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(DashboardSpec dashboardSpec, P parameter);

    /**
     * Accepts a list of dashboard configuration objects.
     * @param dashboardListSpec Dashboard configuration objects.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(DashboardListSpec dashboardListSpec, P parameter);

    /**
     * Accepts a folder with a list of dashboards.
     * @param dashboardsFolderSpec Dashboard folder.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(DashboardsFolderSpec dashboardsFolderSpec, P parameter);

    /**
     * Accepts a list of dashboard folders.
     * @param dashboardsFolderSpecs List of dashboard folders.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(DashboardsFolderListSpec dashboardsFolderSpecs, P parameter);

    /**
     * Accepts a list of dashboards definitions wrapper.
     * @param dashboardDefinitionWrapper List of dashboards definitions wrapper.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(DashboardFolderListSpecWrapperImpl dashboardDefinitionWrapper, P parameter);

    /**
     * Accepts a container of schedules, divided by the time range.
     * @param recurringSchedulesSpec Container of schedule categories.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(RecurringSchedulesSpec recurringSchedulesSpec, P parameter);

    /**
     * Accepts a configuration of incremental partition checks.
     * @param partitionIncrementalTimeWindowSpec Configuration of incremental partition checks.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(PartitionIncrementalTimeWindowSpec partitionIncrementalTimeWindowSpec, P parameter);

    /**
     * Accepts a dictionary of custom checks. The keys must be names of configured custom checks.
     * @param customCheckSpecMap Dictionary of custom checks.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(CustomCheckSpecMap customCheckSpecMap, P parameter);

    /**
     * Accepts a definition of a custom check.
     * @param checkDefinitionSpec Custom check specification.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(CheckDefinitionSpec checkDefinitionSpec, P parameter);

    /**
     * Accepts a wrapper for a definition of a custom check.
     * @param checkDefinitionWrapper Custom check specification wrapper.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(CheckDefinitionWrapperImpl checkDefinitionWrapper, P parameter);

    /**
     * Accepts a list of custom checks.
     * @param checkDefinitionWrappers Custom check list.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(CheckDefinitionListImpl checkDefinitionWrappers, P parameter);

    /**
     * Accepts an incident grouping configuration.
     * @param connectionIncidentGroupingSpec Incident grouping configuration.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(ConnectionIncidentGroupingSpec connectionIncidentGroupingSpec, P parameter);

    /**
     * Accepts an incident notifications using webhooks configuration.
     * @param incidentWebhookNotificationsSpec Webhooks for incident notifications.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(IncidentWebhookNotificationsSpec incidentWebhookNotificationsSpec, P parameter);

    /**
     * Accepts an incident configuration on a table level.
     * @param tableIncidentGroupingSpec Incident grouping configuration.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(TableIncidentGroupingSpec tableIncidentGroupingSpec, P parameter);

    /**
     * Accepts a description of the reference table to which the current table is compared.
     * @param tableComparisonConfigurationSpec Reference table specification.
     * @param parameter Additional visitor's parameter.
     * @return Accept's result.
     */
    R accept(TableComparisonConfigurationSpec tableComparisonConfigurationSpec, P parameter);

    /**
     * Accepts a dictionary of reference table comparisons.
     * @param tableComparisonConfigurationSpecMap Dictionary of reference table comparisons.
     * @param parameter Visitor's parameter.
     * @return Accept's result.
     */
    R accept(TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap, P parameter);

    /**
     * Accepts a map of comparison checks for a named comparison.
     * @param abstractComparisonCheckCategorySpecMap Comparison map with checks.
     * @param parameter Visitor's parameter.
     * @return Accept's result.
     */
    R accept(AbstractComparisonCheckCategorySpecMap<?> abstractComparisonCheckCategorySpecMap, P parameter);

    /**
     * Accepts a configuration of default observability checks to enable on new tables and columns.
     * @param defaultObservabilityCheckSettingsSpec Default configuration of observability checks.
     * @param parameter Visitor's parameter.
     * @return Accept's result.
     */
    R accept(DefaultObservabilityCheckSettingsSpec defaultObservabilityCheckSettingsSpec, P parameter);

    /**
     * Accepts a configuration of default observability checks to enable on new tables and columns.
     * @param defaultProfilingObservabilityCheckSettingsSpec Default configuration of observability checks.
     * @param parameter Visitor's parameter.
     * @return Accept's result.
     */
    R accept(DefaultProfilingObservabilityCheckSettingsSpec defaultProfilingObservabilityCheckSettingsSpec, P parameter);

    /**
     * Accepts a configuration of default observability checks to enable on new tables and columns.
     * @param defaultDailyRecurringObservabilityCheckSettingsSpec Default configuration of observability checks.
     * @param parameter Visitor's parameter.
     * @return Accept's result.
     */

    R accept(DefaultDailyRecurringObservabilityCheckSettingsSpec defaultDailyRecurringObservabilityCheckSettingsSpec, P parameter);

    /**
     * Accepts a configuration of default observability checks to enable on new tables and columns.
     * @param defaultMonthlyRecurringObservabilityCheckSettingsSpec Default configuration of observability checks.
     * @param parameter Visitor's parameter.
     * @return Accept's result.
     */
    R accept(DefaultMonthlyRecurringObservabilityCheckSettingsSpec defaultMonthlyRecurringObservabilityCheckSettingsSpec, P parameter);
}