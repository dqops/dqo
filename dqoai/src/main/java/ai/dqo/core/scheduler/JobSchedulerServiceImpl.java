package ai.dqo.core.scheduler;

import ai.dqo.core.scheduler.quartz.*;
import ai.dqo.core.scheduler.runcheck.RunChecksSchedulerJob;
import ai.dqo.core.scheduler.scan.JobSchedulesDelta;
import ai.dqo.core.scheduler.scan.SynchronizeMetadataSchedulerJob;
import ai.dqo.core.scheduler.schedules.RunChecksSchedule;
import org.quartz.*;
import ai.dqo.core.scheduler.schedules.UniqueSchedulesCollection;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.quartz.JobBuilder.newJob;
import java.util.List;


/**
 * Job scheduling root class that manages an instance of a Quartz scheduler.
 */
@Component
public class JobSchedulerServiceImpl implements JobSchedulerService {
    private Scheduler scheduler;
    private SchedulerFactory schedulerFactory;
    private SpringIoCJobFactory jobFactory;
    private TriggerFactory triggerFactory;
    private JobDataMapAdapter jobDataMapAdapter;
    private ScheduledJobListener scheduledJobListener;
    private JobDetail runChecksJob;
    private JobDetail synchronizeMetadataJob;

    /**
     * Job scheduler service constructor.
     * @param schedulerFactory Quartz scheduler factory (new).
     * @param jobFactory Custom job factory that uses Spring IoC for instantiating job instances.
     * @param triggerFactory Trigger factory that creates quartz triggers for the schedules defined in the metadata.
     * @param jobDataMapAdapter Job data adapter that can retrieve the original schedule from the trigger arguments.
     * @param scheduledJobListener  Job listener that is notified when a job starts or finishes.
     */
    @Autowired
    public JobSchedulerServiceImpl(SchedulerFactory schedulerFactory,
                                   SpringIoCJobFactory jobFactory,
                                   TriggerFactory triggerFactory,
                                   JobDataMapAdapter jobDataMapAdapter,
                                   ScheduledJobListener scheduledJobListener) {
        this.schedulerFactory = schedulerFactory;
        this.jobFactory = jobFactory;
        this.triggerFactory = triggerFactory;
        this.jobDataMapAdapter = jobDataMapAdapter;
        this.scheduledJobListener = scheduledJobListener;
    }

    /**
     * Initializes and starts the scheduler.
     */
    @Override
    public void start() {
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
            this.scheduler.start();
            this.scheduler.getListenerManager().addJobListener(this.scheduledJobListener);
        }
        catch (Exception ex) {
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
        } catch (SchedulerException ex) {
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
        try {
            List<? extends Trigger> triggersOfJob = this.scheduler.getTriggersOfJob(jobKey);

            for (Trigger trigger : triggersOfJob) {
                RunChecksSchedule scheduleOnTrigger = this.jobDataMapAdapter.getSchedule(trigger.getJobDataMap());

                if (schedulesDelta.getSchedulesToDelete().contains(scheduleOnTrigger)) {
                    this.scheduler.unscheduleJob(trigger.getKey());
                }
            }

            for (RunChecksSchedule scheduleToAdd : schedulesDelta.getSchedulesToAdd().getUniqueSchedules()) {
                Trigger triggerToAdd = this.triggerFactory.createTrigger(scheduleToAdd, jobKey);
                if (!this.scheduler.checkExists(triggerToAdd.getKey())) {
                    this.scheduler.scheduleJob(triggerToAdd);
                }
            }
        }
        catch (Exception ex) {
            throw new JobSchedulerException(ex);
        }
    }
}
