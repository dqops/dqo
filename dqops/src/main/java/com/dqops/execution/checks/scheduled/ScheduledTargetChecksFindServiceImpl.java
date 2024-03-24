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
package com.dqops.execution.checks.scheduled;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.defaults.DefaultObservabilityConfigurationService;
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.scheduling.DefaultSchedulesSpec;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.metadata.search.*;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Service that finds all checks that should be executed for a given schedule.
 * Checks are divided by target tables.
 */
@Component
public class ScheduledTargetChecksFindServiceImpl implements ScheduledTargetChecksFindService {
    private HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private DefaultObservabilityConfigurationService defaultObservabilityConfigurationService;

    /**
     * Creates an instance.
     * @param hierarchyNodeTreeSearcher Hierarchy node searcher used to find target checks.
     * @param defaultObservabilityConfigurationService Service that activates default checks configured as check patterns.
     */
    @Autowired
    public ScheduledTargetChecksFindServiceImpl(
            HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
            DefaultObservabilityConfigurationService defaultObservabilityConfigurationService) {
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.defaultObservabilityConfigurationService = defaultObservabilityConfigurationService;
    }

    /**
     * Traverses the user home and finds all checks that should be executed because their schedule
     * or a schedule of their parent nodes (connection, table, column) matches teh requested schedule.
     * @param userHome User home to find target checks to execute.
     * @param schedule Schedule to match.
     * @return List of target checks, grouped by a target table.
     */
    @Override
    public ScheduledChecksCollection findChecksForSchedule(UserHome userHome, MonitoringScheduleSpec schedule) {
        ScheduledChecksCollection scheduledChecksCollection = new ScheduledChecksCollection();

        ScheduleRootsSearchFilters scheduleRootsSearchFilters = new ScheduleRootsSearchFilters();
        scheduleRootsSearchFilters.setEnabled(true);
        scheduleRootsSearchFilters.setSchedule(schedule);
        FoundResultsCollector<ScheduleRootResult> scheduleRoots = this.hierarchyNodeTreeSearcher.findScheduleRoots(
                userHome.getConnections(), scheduleRootsSearchFilters);

        for (ScheduleRootResult scheduleRoot : scheduleRoots.getResults()) {
            HierarchyNode scheduleRootNode = scheduleRoot.getScheduleRootNode();

            if (scheduleRootNode instanceof ConnectionWrapper) {
                // iterate over tables
                ConnectionWrapper scheduledConnectionWrapper = (ConnectionWrapper) scheduleRootNode;

                for (TableWrapper scheduledTableWrapper : scheduledConnectionWrapper.getTables()) {
                    TableSpec clonedTableSpec = scheduledTableWrapper.getSpec().deepClone();
                    this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableAndColumns(scheduledConnectionWrapper.getSpec(), clonedTableSpec, userHome);

                    Set<CheckRunScheduleGroup> schedulingGroupsForTable = scheduleRoot.getSchedulingGroups();
                    DefaultSchedulesSpec tableSchedulesOverride = clonedTableSpec.getSchedulesOverride();
                    if (tableSchedulesOverride != null && !tableSchedulesOverride.isDefault()) {
                        schedulingGroupsForTable = new HashSet<>(schedulingGroupsForTable);

                        if (tableSchedulesOverride.getScheduleForCheckSchedulingGroup(CheckRunScheduleGroup.profiling) != null) {
                            schedulingGroupsForTable.remove(CheckRunScheduleGroup.profiling);
                        }

                        if (tableSchedulesOverride.getScheduleForCheckSchedulingGroup(CheckRunScheduleGroup.monitoring_daily) != null) {
                            schedulingGroupsForTable.remove(CheckRunScheduleGroup.monitoring_daily);
                        }

                        if (tableSchedulesOverride.getScheduleForCheckSchedulingGroup(CheckRunScheduleGroup.monitoring_monthly) != null) {
                            schedulingGroupsForTable.remove(CheckRunScheduleGroup.monitoring_monthly);
                        }

                        if (tableSchedulesOverride.getScheduleForCheckSchedulingGroup(CheckRunScheduleGroup.partitioned_daily) != null) {
                            schedulingGroupsForTable.remove(CheckRunScheduleGroup.partitioned_daily);
                        }

                        if (tableSchedulesOverride.getScheduleForCheckSchedulingGroup(CheckRunScheduleGroup.partitioned_monthly) != null) {
                            schedulingGroupsForTable.remove(CheckRunScheduleGroup.partitioned_monthly);
                        }
                    }

                    ScheduledChecksSearchFilters scheduledChecksSearchFilters = new ScheduledChecksSearchFilters();
                    scheduledChecksSearchFilters.setEnabled(true);
                    scheduledChecksSearchFilters.setSchedule(schedule);
                    scheduledChecksSearchFilters.setSchedulingGroups(schedulingGroupsForTable);

                    Collection<AbstractCheckSpec<?,?,?,?>> scheduledChecks = this.hierarchyNodeTreeSearcher.findScheduledChecks(
                            clonedTableSpec, scheduledChecksSearchFilters);

                    for (AbstractCheckSpec<?,?,?,?> targetCheck : scheduledChecks) {
                        ScheduledTableChecksCollection tableChecks = scheduledChecksCollection.getOrAddTableChecks(clonedTableSpec);
                        tableChecks.addCheck(targetCheck);
                    }
                }

                continue;
            }

            if (scheduleRootNode instanceof TableSpec) {
                TableSpec originalTableSpec = (TableSpec) scheduleRootNode;
                ConnectionWrapper connectionWrapper = userHome.findConnectionFor(originalTableSpec.getHierarchyId());
                TableSpec clonedTableSpec = originalTableSpec.deepClone();
                this.defaultObservabilityConfigurationService.applyDefaultChecksOnTableAndColumns(connectionWrapper.getSpec(), clonedTableSpec, userHome);

                ScheduledChecksSearchFilters scheduledChecksSearchFilters = new ScheduledChecksSearchFilters();
                scheduledChecksSearchFilters.setEnabled(true);
                scheduledChecksSearchFilters.setSchedule(schedule);
                scheduledChecksSearchFilters.setSchedulingGroups(scheduleRoot.getSchedulingGroups());

                Collection<AbstractCheckSpec<?,?,?,?>> scheduledChecks = this.hierarchyNodeTreeSearcher.findScheduledChecks(
                        clonedTableSpec, scheduledChecksSearchFilters);

                for (AbstractCheckSpec<?,?,?,?> targetCheck : scheduledChecks) {
                    ScheduledTableChecksCollection tableChecks = scheduledChecksCollection.getOrAddTableChecks(clonedTableSpec);
                    tableChecks.addCheck(targetCheck);
                }

                continue;
            }

            if (scheduleRootNode instanceof AbstractCheckSpec<?,?,?,?>) {
                AbstractCheckSpec<?,?,?,?> targetCheckSpec =  (AbstractCheckSpec<?,?,?,?>)scheduleRootNode;
                TableWrapper targetTableWrapper = userHome.findTableFor(targetCheckSpec.getHierarchyId());
                ScheduledTableChecksCollection tableChecks = scheduledChecksCollection.getOrAddTableChecks(targetTableWrapper.getSpec());
                tableChecks.addCheck(targetCheckSpec);
                continue;
            }

            throw new DqoRuntimeException("Unsupported schedule root: " + scheduleRoot.getScheduleRootNode().getHierarchyId());
        }

        return scheduledChecksCollection;
    }
}
