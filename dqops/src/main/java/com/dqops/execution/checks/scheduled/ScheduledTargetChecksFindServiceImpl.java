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
import com.dqops.metadata.basespecs.AbstractSpec;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.metadata.search.*;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Service that finds all checks that should be executed for a given schedule.
 * Checks are divided by target tables.
 */
@Component
public class ScheduledTargetChecksFindServiceImpl implements ScheduledTargetChecksFindService {
    private HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;

    /**
     * Creates an instance.
     * @param hierarchyNodeTreeSearcher Hierarchy node searcher used to find target checks.
     */
    @Autowired
    public ScheduledTargetChecksFindServiceImpl(HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher) {
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
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
            ScheduledChecksSearchFilters scheduledChecksSearchFilters = new ScheduledChecksSearchFilters();
            scheduledChecksSearchFilters.setEnabled(true);
            scheduledChecksSearchFilters.setSchedule(schedule);
            scheduledChecksSearchFilters.setScheduleGroup(scheduleRoot.getScheduleGroup());
            HierarchyNode scheduleRootNode = scheduleRoot.getScheduleRootNode();

            Collection<AbstractCheckSpec<?,?,?,?>> scheduledChecks = this.hierarchyNodeTreeSearcher.findScheduledChecks(
                    scheduleRootNode, scheduledChecksSearchFilters);

            for (AbstractCheckSpec<?,?,?,?> targetCheck : scheduledChecks) {
                TableWrapper targetTableWrapper = userHome.findTableFor(targetCheck.getHierarchyId());
                ScheduledTableChecksCollection tableChecks = scheduledChecksCollection.getOrAddTableChecks(targetTableWrapper);
                tableChecks.addCheck(targetCheck);
            }
        }

        return scheduledChecksCollection;
    }
}
