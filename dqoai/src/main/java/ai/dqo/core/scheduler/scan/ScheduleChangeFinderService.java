package ai.dqo.core.scheduler.scan;

/**
 * Schedule detection service that scans the metadata and compares the current list of schedules used by the scheduler
 * with the current schedules. Removes outdated schedules.
 */
public interface ScheduleChangeFinderService {
    /**
     * Loads all schedules configured in the metadata and compares the list with the current running schedules.
     * Returns two list of schedules, those new schedules to add and outdated schedules to remove.
     *
     * @param currentRunningSchedules Current running schedules.
     * @return The delta - two lists of schedules, to add and to remove from the scheduler.
     */
    JobSchedulesDelta findSchedulesToAddOrRemove(DetectedUniqueSchedulesCollection currentRunningSchedules);
}
