package ai.dqo.core.scheduler.scan;

import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationReportingMode;
import ai.dqo.core.jobqueue.BaseDqoQueueJob;
import ai.dqo.core.jobqueue.DqoJobType;
import ai.dqo.core.scheduler.JobSchedulerService;
import ai.dqo.core.scheduler.quartz.JobKeys;
import ai.dqo.core.scheduler.schedules.UniqueSchedulesCollection;
import ai.dqo.core.scheduler.synchronization.SchedulerFileSynchronizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * DQO job that synchronizes metadata and refreshes known cron schedules in the quartz scheduler.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RunPeriodicMetadataSynchronizationDqoJob extends BaseDqoQueueJob<Void> {
    private ScheduleChangeFinderService scheduleChangeFinderService;
    private JobSchedulerService jobSchedulerService;
    private SchedulerFileSynchronizationService schedulerFileSynchronizationService;

    /**
     * Creates a DQO job that synchronizes the metadata and refreshes known schedules in the quartz scheduler.
     * @param scheduleChangeFinderService Schedule finder that knows how to identify schedules (cron expressions) in the metadata.
     * @param jobSchedulerService Job scheduler used to update a list of schedules.
     * @param schedulerFileSynchronizationService Cloud synchronization service.
     */
    @Autowired
    public RunPeriodicMetadataSynchronizationDqoJob(ScheduleChangeFinderService scheduleChangeFinderService,
                                                    JobSchedulerService jobSchedulerService,
                                                    SchedulerFileSynchronizationService schedulerFileSynchronizationService) {
        this.scheduleChangeFinderService = scheduleChangeFinderService;
        this.jobSchedulerService = jobSchedulerService;
        this.schedulerFileSynchronizationService = schedulerFileSynchronizationService;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     *
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public Void onExecute() {
        FileSystemSynchronizationReportingMode synchronizationMode = this.jobSchedulerService.getSynchronizationMode();
        this.schedulerFileSynchronizationService.synchronizeAll(synchronizationMode);

        UniqueSchedulesCollection activeSchedules = this.jobSchedulerService.getActiveSchedules(JobKeys.RUN_CHECKS);
        JobSchedulesDelta schedulesToAddOrRemove = this.scheduleChangeFinderService.findSchedulesToAddOrRemove(activeSchedules);
        this.jobSchedulerService.applyScheduleDeltaToJob(schedulesToAddOrRemove, JobKeys.RUN_CHECKS);

        return null;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.SCHEDULED_SYNCHRONIZATION;
    }
}
