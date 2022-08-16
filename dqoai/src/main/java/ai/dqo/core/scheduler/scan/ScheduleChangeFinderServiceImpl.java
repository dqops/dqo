package ai.dqo.core.scheduler.scan;

import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcher;
import ai.dqo.metadata.search.RecurringScheduleSearchFilters;
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
    public DetectedUniqueSchedulesCollection loadCurrentSchedulesForDataQualityChecks() {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        UserHome userHome = userHomeContext.getUserHome();

        RecurringScheduleSearchFilters recurringScheduleSearchFilters = new RecurringScheduleSearchFilters();
        Collection<RecurringScheduleSpec> schedules = this.nodeTreeSearcher.findSchedules(userHome, recurringScheduleSearchFilters);

        DetectedUniqueSchedulesCollection uniqueSchedulesCollection = new DetectedUniqueSchedulesCollection();
        uniqueSchedulesCollection.addAll(schedules);

        return uniqueSchedulesCollection;
    }

    /**
     * Loads all schedules configured in the metadata and compares the list with the current running schedules.
     * Returns two list of schedules, those new schedules to add and outdated schedules to remove.
     * @param currentRunningSchedules Current running schedules.
     * @return The delta - two lists of schedules, to add and to remove from the scheduler.
     */
    @Override
    public JobSchedulesDelta findSchedulesToAddOrRemove(DetectedUniqueSchedulesCollection currentRunningSchedules) {
        assert currentRunningSchedules != null;

        DetectedUniqueSchedulesCollection currentMetadataSchedules = loadCurrentSchedulesForDataQualityChecks();

        DetectedUniqueSchedulesCollection schedulesToAdd = currentMetadataSchedules.minus(currentRunningSchedules);
        DetectedUniqueSchedulesCollection schedulesToRemove = currentRunningSchedules.minus(currentMetadataSchedules);

        return new JobSchedulesDelta(schedulesToAdd, schedulesToRemove);
    }
}
