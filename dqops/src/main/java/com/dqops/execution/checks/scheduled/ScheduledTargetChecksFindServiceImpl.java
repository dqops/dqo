/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.checks.scheduled;

import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.defaults.DefaultObservabilityConfigurationService;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.scheduling.CheckRunScheduleGroup;
import com.dqops.metadata.scheduling.CronSchedulesSpec;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import com.dqops.metadata.search.*;
import com.dqops.metadata.settings.instancename.InstanceNameProvider;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.utils.exceptions.DqoRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Service that finds all checks that should be executed for a given schedule.
 * Checks are divided by target tables.
 */
@Component
public class ScheduledTargetChecksFindServiceImpl implements ScheduledTargetChecksFindService {
    private HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private DefaultObservabilityConfigurationService defaultObservabilityConfigurationService;
    private InstanceNameProvider instanceNameProvider;

    /**
     * Creates an instance.
     * @param hierarchyNodeTreeSearcher Hierarchy node searcher used to find target checks.
     * @param defaultObservabilityConfigurationService Service that activates default checks configured as check patterns.
     * @param instanceNameProvider DQOps instance name provider.
     */
    @Autowired
    public ScheduledTargetChecksFindServiceImpl(
            HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
            DefaultObservabilityConfigurationService defaultObservabilityConfigurationService,
            InstanceNameProvider instanceNameProvider) {
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.defaultObservabilityConfigurationService = defaultObservabilityConfigurationService;
        this.instanceNameProvider = instanceNameProvider;
    }

    /**
     * Traverses the user home and finds all checks that should be executed because their schedule
     * or a schedule of their parent nodes (connection, table, column) matches the requested schedule.
     * @param userHome User home to find target checks to execute.
     * @param schedule Schedule to match.
     * @return List of target checks, grouped by a target table.
     */
    @Override
    public ScheduledChecksCollection findChecksForSchedule(UserHome userHome, CronScheduleSpec schedule) {
        ScheduledChecksCollection scheduledChecksCollection = new ScheduledChecksCollection();

        ScheduleRootsSearchFilters scheduleRootsSearchFilters = new ScheduleRootsSearchFilters();
        scheduleRootsSearchFilters.setEnabled(true);
        scheduleRootsSearchFilters.setSchedule(schedule);
        scheduleRootsSearchFilters.setLocalInstanceName(this.instanceNameProvider.getInstanceName());

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
                    CronSchedulesSpec tableSchedulesOverride = clonedTableSpec.getSchedulesOverride();
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
