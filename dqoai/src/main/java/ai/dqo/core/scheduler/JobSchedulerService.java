package ai.dqo.core.scheduler;

import ai.dqo.core.scheduler.quartz.JobKeys;
import ai.dqo.core.scheduler.scan.JobSchedulesDelta;
import ai.dqo.core.scheduler.schedules.UniqueSchedulesCollection;
import org.quartz.JobKey;
import org.quartz.Scheduler;

/**
 * Job scheduling root class that manages an instance of a Quartz scheduler.
 */
public interface JobSchedulerService {
    /**
     * Initializes and starts the scheduler.
     */
    void start();

    /**
     * Stops the scheduler.
     */
    void shutdown();

    /**
     * Triggers the metadata synchronization job on the job scheduler. Calls the cloud sync and reload the metadata to detect new schedules.
     */
    void triggerMetadataSynchronization();

    /**
     * Returns the default job scheduler. The scheduler must be started first.
     *
     * @return Quartz scheduler instance.
     */
    Scheduler getScheduler();

    /**
     * Retrieves all schedules configured on triggers for a given job.
     *
     * @param jobKey Job key for one of the known jobs like the run checks or scan metadata.
     * @return Returns a list of active schedules.
     */
    UniqueSchedulesCollection getActiveSchedules(JobKey jobKey);

    /**
     * Applies changes for a new list of schedules, new triggers are added, unused triggers are removed.
     *
     * @param schedulesDelta Schedule delta with schedules to add and schedules to remove.
     * @param jobKey         Target job to configure. Jobs are specified in the {@link JobKeys}
     */
    void applyScheduleDeltaToJob(JobSchedulesDelta schedulesDelta, JobKey jobKey);
}
