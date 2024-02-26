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

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.metadata.comparisons.TableComparisonConfigurationSpecMap;
import com.dqops.metadata.comparisons.TableComparisonGroupingColumnsPairsListSpec;
import com.dqops.metadata.credentials.SharedCredentialList;
import com.dqops.metadata.dashboards.DashboardFolderListSpecWrapperImpl;
import com.dqops.metadata.defaultchecks.column.ColumnDefaultChecksPatternList;
import com.dqops.metadata.defaultchecks.table.TableDefaultChecksPatternList;
import com.dqops.metadata.definitions.checks.CheckDefinitionListImpl;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionList;
import com.dqops.metadata.dictionaries.DictionaryListImpl;
import com.dqops.metadata.incidents.defaultnotifications.DefaultIncidentWebhookNotificationsWrapper;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.metadata.scheduling.DefaultSchedulesSpec;
import com.dqops.metadata.scheduling.MonitoringSchedulesWrapper;
import com.dqops.metadata.settings.LocalSettingsSpec;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;

import java.util.Objects;

/**
 * Visitor for {@link ScheduleRootsSearchFilters} that finds any node (connection, table, column, check) that has a given filter configured.
 */
public class ScheduleRootsSearchFiltersVisitor extends AbstractSearchVisitor<FoundResultsCollector<ScheduleRootResult>> {
    private final ScheduleRootsSearchFilters filters;

    /**
     * Creates a visitor for the given filters.
     * @param filters Check search filters.
     */
    public ScheduleRootsSearchFiltersVisitor(ScheduleRootsSearchFilters filters) {
        this.filters = filters;
    }

    /**
     * Accepts a connection wrapper (lazy loader).
     *
     * @param connectionWrapper Connection wrapper.
     * @param foundNodes        Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ConnectionWrapper connectionWrapper, FoundResultsCollector<ScheduleRootResult> foundNodes) {
        ConnectionSpec connectionSpec = connectionWrapper.getSpec();
        DefaultSchedulesSpec schedules = connectionSpec.getSchedules();
        assert this.filters.getSchedule() != null;
        assert this.filters.getSchedule() != null;

        if (schedules != null) {
            if (Objects.equals(schedules.getProfiling(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunScheduleGroup.profiling, connectionWrapper));
            }

            if (Objects.equals(schedules.getMonitoringDaily(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunScheduleGroup.monitoring_daily, connectionWrapper));
            }

            if (Objects.equals(schedules.getMonitoringMonthly(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunScheduleGroup.monitoring_monthly, connectionWrapper));
            }

            if (Objects.equals(schedules.getPartitionedDaily(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunScheduleGroup.partitioned_daily, connectionWrapper));
            }

            if (Objects.equals(schedules.getPartitionedMonthly(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunScheduleGroup.partitioned_monthly, connectionWrapper));
            }
        }

        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts a table specification.
     *
     * @param tableSpec  Table specification.
     * @param foundNodes Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableSpec tableSpec, FoundResultsCollector<ScheduleRootResult> foundNodes) {
        Boolean enabledFilter = this.filters.getEnabled();
        if (enabledFilter != null) {
            boolean tableIsEnabled = !tableSpec.isDisabled();
            if (enabledFilter != tableIsEnabled) {
                return TreeNodeTraversalResult.SKIP_CHILDREN;
            }
        }

        DefaultSchedulesSpec schedulesOverride = tableSpec.getSchedulesOverride();
        assert this.filters.getSchedule() != null;

        if (schedulesOverride != null) {
            if (Objects.equals(schedulesOverride.getProfiling(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunScheduleGroup.profiling, tableSpec));
            }

            if (Objects.equals(schedulesOverride.getMonitoringDaily(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunScheduleGroup.monitoring_daily, tableSpec));
            }

            if (Objects.equals(schedulesOverride.getMonitoringMonthly(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunScheduleGroup.monitoring_monthly, tableSpec));
            }

            if (Objects.equals(schedulesOverride.getPartitionedDaily(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunScheduleGroup.partitioned_daily, tableSpec));
            }

            if (Objects.equals(schedulesOverride.getPartitionedMonthly(), this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(CheckRunScheduleGroup.partitioned_monthly, tableSpec));
            }
        }

        return TreeNodeTraversalResult.TRAVERSE_CHILDREN;
    }

    /**
     * Accepts any check specification.
     *
     * @param abstractCheckSpec Data quality check specification (any).
     * @param foundNodes        Target list where found hierarchy nodes should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(AbstractCheckSpec<?,?,?,?> abstractCheckSpec, FoundResultsCollector<ScheduleRootResult> foundNodes) {
        MonitoringScheduleSpec checkSchedule = abstractCheckSpec.getScheduleOverride();
        assert this.filters.getSchedule() != null;

        if (checkSchedule != null && !checkSchedule.isDefault()) {
            if (Objects.equals(checkSchedule, this.filters.getSchedule())) {
                foundNodes.add(new ScheduleRootResult(null, abstractCheckSpec));
            }
        }

        return TreeNodeTraversalResult.SKIP_CHILDREN; // no need to traverse deeper
    }

    /**
     * Accepts a list of sensor definitions for different providers.
     *
     * @param providerSensorDefinitionList List of sensor definitions per provider.
     * @param parameter                    Target object where found hierarchy nodes, dimensions and labels should be added.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ProviderSensorDefinitionList providerSensorDefinitionList, FoundResultsCollector<ScheduleRootResult> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a custom rule definition wrapper list that stores a list of custom rules.
     *
     * @param ruleDefinitionList Custom rule list.
     * @param parameter          Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(RuleDefinitionList ruleDefinitionList, FoundResultsCollector<ScheduleRootResult> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a settings specific specification.
     *
     * @param localSettingsSpec Settings specific configuration.
     * @param parameter         Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(LocalSettingsSpec localSettingsSpec, FoundResultsCollector<ScheduleRootResult> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a list of dashboards list.
     *
     * @param dashboardDefinitionWrapper List of dashboards list.
     * @param parameter                  Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DashboardFolderListSpecWrapperImpl dashboardDefinitionWrapper, FoundResultsCollector<ScheduleRootResult> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a list of custom checks.
     *
     * @param checkDefinitionWrappers Custom check list.
     * @param parameter               Additional visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(CheckDefinitionListImpl checkDefinitionWrappers, FoundResultsCollector<ScheduleRootResult> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a dictionary of reference table comparisons.
     *
     * @param tableComparisonConfigurationSpecMap Dictionary of reference table comparisons.
     * @param parameter                           Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableComparisonConfigurationSpecMap tableComparisonConfigurationSpecMap, FoundResultsCollector<ScheduleRootResult> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a list of a pair of column names that are used for joining and grouping.
     *
     * @param tableComparisonGroupingColumnsPairSpecs A list of a pairs of columns used for grouping and joining in table comparison checks.
     * @param parameter                               Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableComparisonGroupingColumnsPairsListSpec tableComparisonGroupingColumnsPairSpecs, FoundResultsCollector<ScheduleRootResult> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a shared credential list.
     *
     * @param sharedCredentialWrappers Shared credentials list.
     * @param parameter                Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(SharedCredentialList sharedCredentialWrappers, FoundResultsCollector<ScheduleRootResult> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a default monitoring schedule wrapper instance.
     *
     * @param monitoringSchedulesWrapper Default monitoring schedule wrapper instance.
     * @param parameter                  Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(MonitoringSchedulesWrapper monitoringSchedulesWrapper, FoundResultsCollector<ScheduleRootResult> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a default incident webhook notification wrapper instance.
     *
     * @param defaultIncidentWebhookNotificationsWrapper Default incident webhook notification wrapper instance.
     * @param parameter                                  Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DefaultIncidentWebhookNotificationsWrapper defaultIncidentWebhookNotificationsWrapper, FoundResultsCollector<ScheduleRootResult> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a data dictionary list.
     *
     * @param dictionaryWrappers Data dictionary list.
     * @param parameter          Visitor's parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(DictionaryListImpl dictionaryWrappers, FoundResultsCollector<ScheduleRootResult> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a list of default configuration of table observability checks wrappers.
     *
     * @param tableDefaultChecksPatternWrappers Table observability default checks list.
     * @param parameter                         Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(TableDefaultChecksPatternList tableDefaultChecksPatternWrappers, FoundResultsCollector<ScheduleRootResult> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }

    /**
     * Accepts a default configuration of column observability checks wrapper.
     *
     * @param columnDefaultChecksPatternWrappers Column observability default checks specification.
     * @param parameter                          Additional parameter.
     * @return Accept's result.
     */
    @Override
    public TreeNodeTraversalResult accept(ColumnDefaultChecksPatternList columnDefaultChecksPatternWrappers, FoundResultsCollector<ScheduleRootResult> parameter) {
        return TreeNodeTraversalResult.SKIP_CHILDREN;
    }
}
