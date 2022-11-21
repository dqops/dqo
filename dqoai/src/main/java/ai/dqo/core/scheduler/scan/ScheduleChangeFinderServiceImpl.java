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
package ai.dqo.core.scheduler.scan;

import ai.dqo.core.scheduler.schedules.RunChecksCronSchedule;
import ai.dqo.core.scheduler.schedules.UniqueSchedulesCollection;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcher;
import ai.dqo.metadata.search.RecurringScheduleSearchFilters;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.userhome.UserHome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Schedule detection service that scans the metadata and compares the current list of schedules used by the scheduler
 * with the current schedules. Removes outdated schedules.
 */
@Component
public class ScheduleChangeFinderServiceImpl implements ScheduleChangeFinderService {
    private HierarchyNodeTreeSearcher nodeTreeSearcher;
    private UserHomeContextFactory userHomeContextFactory;

    @Autowired
    public ScheduleChangeFinderServiceImpl(
            HierarchyNodeTreeSearcher nodeTreeSearcher,
            UserHomeContextFactory userHomeContextFactory) {
        this.nodeTreeSearcher = nodeTreeSearcher;
        this.userHomeContextFactory = userHomeContextFactory;
    }

    /**
     * Loads (finds) all current schedules that are specified in the metadata.
     * @return All unique schedules to run data quality checks.
     */
    public UniqueSchedulesCollection loadCurrentSchedulesForDataQualityChecks() {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        RecurringScheduleSearchFilters recurringScheduleSearchFilters = new RecurringScheduleSearchFilters();
        recurringScheduleSearchFilters.setScheduleEnabled(true);
        // we can add additional filters if this instance should only process schedules in one connection or matching a connection name pattern
        Collection<RecurringScheduleSpec> schedules = this.nodeTreeSearcher.findSchedules(userHome, recurringScheduleSearchFilters);

        UniqueSchedulesCollection uniqueSchedulesCollection = new UniqueSchedulesCollection();
        for (RecurringScheduleSpec recurringSchedule : schedules) {
            ConnectionWrapper parentConnectionWrapper = userHome.findConnectionFor(recurringSchedule.getHierarchyId());
            assert parentConnectionWrapper != null;

            RecurringScheduleSpec clonedRecurringSchedule = recurringSchedule.clone();
            clonedRecurringSchedule.setHierarchyId(null);

            RunChecksCronSchedule runChecksCronSchedule = new RunChecksCronSchedule(clonedRecurringSchedule, parentConnectionWrapper.getSpec().getTimeZone());
            uniqueSchedulesCollection.add(runChecksCronSchedule);
        }

        return uniqueSchedulesCollection;
    }

    /**
     * Loads all schedules configured in the metadata and compares the list with the current running schedules.
     * Returns two list of schedules, those new schedules to add and outdated schedules to remove.
     * @param currentRunningSchedules Current running schedules.
     * @return The delta - two lists of schedules, to add and to remove from the scheduler.
     */
    @Override
    public JobSchedulesDelta findSchedulesToAddOrRemove(UniqueSchedulesCollection currentRunningSchedules) {
        assert currentRunningSchedules != null;

        UniqueSchedulesCollection currentMetadataSchedules = loadCurrentSchedulesForDataQualityChecks();

        UniqueSchedulesCollection schedulesToAdd = currentMetadataSchedules.minus(currentRunningSchedules);
        UniqueSchedulesCollection schedulesToRemove = currentRunningSchedules.minus(currentMetadataSchedules);

        return new JobSchedulesDelta(schedulesToAdd, schedulesToRemove);
    }
}
