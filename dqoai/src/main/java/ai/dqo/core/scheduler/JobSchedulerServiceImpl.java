package ai.dqo.core.scheduler;

import ai.dqo.core.configuration.DqoSchedulerConfigurationProperties;
import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationReportingMode;
import ai.dqo.core.scheduler.quartz.*;
import ai.dqo.core.scheduler.runcheck.RunChecksSchedulerJob;
import ai.dqo.core.scheduler.scan.JobSchedulesDelta;
import ai.dqo.core.scheduler.scan.SynchronizeMetadataSchedulerJob;
import ai.dqo.core.scheduler.schedules.RunChecksSchedule;
import ai.dqo.execution.checks.progress.CheckRunReportingMode;
import ai.dqo.metadata.scheduling.RecurringScheduleSpec;
import org.quartz.*;
import ai.dqo.core.scheduler.schedules.UniqueSchedulesCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.quartz.JobBuilder.newJob;
import java.util.List;


/**
 * Job scheduling root class that manages an instance of a Quartz scheduler.
 */
@Component
public class JobSchedulerServiceImpl implements JobSchedulerService {
    private static final Logger LOG = LoggerFactory.getLogger(JobSchedulerServiceImpl.class);

    private Scheduler scheduler;
    private DqoSchedulerConfigurationProperties schedulerConfigurationProperties;
    private SchedulerFactory schedulerFactory;
    private SpringIoCJobFactory jobFactory;
    private TriggerFactory triggerFactory;
    private JobDataMapAdapter jobDataMapAdapter;
    private ScheduledJobListener scheduledJobListener;
    private JobDetail runChecksJob;
    private JobDetail synchronizeMetadataJob;
    private FileSystemSynchronizationReportingMode synchronizationMode = FileSystemSynchronizationReportingMode.summary;
    private CheckRunReportingMode checkRunReportingMode = CheckRunReportingMode.summary;

    /**
     * Job scheduler service constructor.
     * @param schedulerConfigurationProperties Scheduler configuration properties.
     * @param schedulerFactory Quartz scheduler factory (new).
     * @param jobFactory Custom job factory that uses Spring IoC for instantiating job instances.
     * @param triggerFactory Trigger factory that creates quartz triggers for the schedules defined in the metadata.
     * @param jobDataMapAdapter Job data adapter that can retrieve the original schedule from the trigger arguments.
     * @param scheduledJobListener  Job listener that is notified when a job starts or finishes.
     */
    @Autowired
    public JobSchedulerServiceImpl(DqoSchedulerConfigurationProperties schedulerConfigurationProperties,
                                   SchedulerFactory schedulerFactory,
                                   SpringIoCJobFactory jobFactory,
                                   TriggerFactory triggerFactory,
                                   JobDataMapAdapter jobDataMapAdapter,
                                   ScheduledJobListener scheduledJobListener) {
        this.schedulerConfigurationProperties = schedulerConfigurationProperties;
        this.schedulerFactory = schedulerFactory;
        this.jobFactory = jobFactory;
        this.triggerFactory = triggerFactory;
        this.jobDataMapAdapter = jobDataMapAdapter;
        this.scheduledJobListener = scheduledJobListener;
    }

    /**
     * Returns the file synchronization mode that was configured for the scheduler.
     * @return File synchronization reporting mode.
     */
    public FileSystemSynchronizationReportingMode getSynchronizationMode() {
        return synchronizationMode;
    }

    /**
     * Returns the reporting mode for running the checks by the scheduler.
     * @return Reporting mode during the check execution.
     */
    public CheckRunReportingMode getCheckRunReportingMode() {
        return checkRunReportingMode;
    }

    /**
     * Initializes and starts the scheduler.
     * @param synchronizationMode Cloud sync reporting mode.
     * @param checkRunReportingMode Check execution reporting mode.
     */
    @Override
    public void start(FileSystemSynchronizationReportingMode synchronizationMode, CheckRunReportingMode checkRunReportingMode) {
        this.synchronizationMode = synchronizationMode;
        this.checkRunReportingMode = checkRunReportingMode;
        createAndStartScheduler();
        defineDefaultJobs();
    }

    /**
     * Creates and starts a scheduler, but without any jobs.
     */
    public void createAndStartScheduler() {
        assert scheduler == null;
        try {
            this.scheduler = schedulerFactory.getScheduler();
            this.scheduler.setJobFactory(this.jobFactory);
            this.scheduler.getListenerManager().addJobListener(this.scheduledJobListener);
            this.scheduler.start();
        }
        catch (Exception ex) {
            LOG.error("Failed to start the job scheduler because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Defines the default jobs like running checks or synchronizing the metadata.
     */
    public void defineDefaultJobs() {
        try {
            this.runChecksJob = newJob(RunChecksSchedulerJob.class)
                .withIdentity(JobKeys.RUN_CHECKS)
                .storeDurably()
                .build();
            this.scheduler.addJob(this.runChecksJob, true);

            this.synchronizeMetadataJob = newJob(SynchronizeMetadataSchedulerJob.class)
                .withIdentity(JobKeys.SYNCHRONIZE_METADATA)
                .storeDurably()
                .build();
            this.scheduler.addJob(this.synchronizeMetadataJob, true);

            String scanMetadataCronSchedule = this.schedulerConfigurationProperties.getScanMetadataCronSchedule();
            RunChecksSchedule scanMetadataScheduleWithTZ = new RunChecksSchedule(new RecurringScheduleSpec(scanMetadataCronSchedule), "UTC");
            Trigger scanMetadataTrigger = this.triggerFactory.createTrigger(scanMetadataScheduleWithTZ, JobKeys.SYNCHRONIZE_METADATA);

            this.scheduler.scheduleJob(scanMetadataTrigger);
        } catch (SchedulerException ex) {
            LOG.error("Failed to define the default jobs because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Stops the scheduler.
     */
    @Override
    public void shutdown() {
        try {
            if (this.scheduler != null) {
                this.scheduler.shutdown();
            }
            this.scheduler = null;
        }
        catch (Exception ex) {
            LOG.error("Failed to stop the job scheduler because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Triggers the metadata synchronization job on the job scheduler. Calls the cloud sync and reload the metadata to detect new schedules.
     */
    @Override
    public void triggerMetadataSynchronization() {
        try {
            this.scheduler.triggerJob(JobKeys.SYNCHRONIZE_METADATA);
        }
        catch (Exception ex) {
            LOG.error("Failed to trigger the metadata synchronization in the job scheduler because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Returns the default job scheduler. The scheduler must be started first.
     * @return Quartz scheduler instance.
     */
    @Override
    public Scheduler getScheduler() {
        assert this.scheduler != null : "The scheduler must be first started";
        return this.scheduler;
    }

    /**
     * Retrieves all schedules configured on triggers for a given job.
     * @param jobKey Job key for one of the known jobs like the run checks or scan metadata.
     * @return Returns a list of active schedules.
     */
    @Override
    public UniqueSchedulesCollection getActiveSchedules(JobKey jobKey) {
        try {
            List<? extends Trigger> triggersOfJob = this.scheduler.getTriggersOfJob(jobKey);
            UniqueSchedulesCollection schedulesCollection = new UniqueSchedulesCollection();

            for (Trigger trigger : triggersOfJob) {
                RunChecksSchedule schedule = this.jobDataMapAdapter.getSchedule(trigger.getJobDataMap());
                schedulesCollection.add(schedule);
            }

            return schedulesCollection;
        }
        catch (Exception ex) {
            LOG.error("Failed to list active schedules in the job scheduler " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Applies changes for a new list of schedules, new triggers are added, unused triggers are removed.
     * @param schedulesDelta Schedule delta with schedules to add and schedules to remove.
     * @param jobKey Target job to configure. Jobs are specified in the {@link JobKeys}
     */
    @Override
    public void applyScheduleDeltaToJob(JobSchedulesDelta schedulesDelta, JobKey jobKey) {
        Exception firstException = null;
        List<? extends Trigger> triggersOfJob = null;

        try {
            triggersOfJob = this.scheduler.getTriggersOfJob(jobKey);
        }
        catch (Exception ex) {
            LOG.error("Failed to list scheduled jobs because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }

        for (Trigger trigger : triggersOfJob) {
            RunChecksSchedule scheduleOnTrigger = this.jobDataMapAdapter.getSchedule(trigger.getJobDataMap());

            if (schedulesDelta.getSchedulesToDelete().contains(scheduleOnTrigger)) {
                try {
                    this.scheduler.unscheduleJob(trigger.getKey());
                }
                catch (Exception ex) {
                    LOG.error("Failed to unschedule a job because " + ex.getMessage(), ex);
                    if (firstException != null) {
                        firstException = ex;
                    }
                }
            }
        }

        for (RunChecksSchedule scheduleToAdd : schedulesDelta.getSchedulesToAdd().getUniqueSchedules()) {
            Trigger triggerToAdd = this.triggerFactory.createTrigger(scheduleToAdd, jobKey);
            try {
                if (!this.scheduler.checkExists(triggerToAdd.getKey())) {
                    this.scheduler.scheduleJob(triggerToAdd);
                }
            }
            catch (Exception ex) {
                LOG.error("Failed to schedule a job because " + ex.getMessage(), ex);
                if (firstException != null) {
                    firstException = ex;
                }
            }
        }

        if (firstException != null) {
            LOG.error("Failed to activate new schedules the job scheduler because " + firstException.getMessage(), firstException);
            throw new JobSchedulerException(firstException);
        }
    }
}
