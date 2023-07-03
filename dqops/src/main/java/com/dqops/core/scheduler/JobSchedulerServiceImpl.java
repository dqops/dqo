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
package com.dqops.core.scheduler;

import com.dqops.core.configuration.DqoSchedulerConfigurationProperties;
import com.dqops.core.scheduler.quartz.*;
import com.dqops.core.scheduler.runcheck.RunScheduledChecksSchedulerJob;
import com.dqops.core.scheduler.synchronize.JobSchedulesDelta;
import com.dqops.core.scheduler.synchronize.SynchronizeMetadataSchedulerJob;
import com.dqops.core.scheduler.schedules.UniqueSchedulesCollection;
import com.dqops.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
import com.dqops.execution.checks.progress.CheckRunReportingMode;
import com.dqops.metadata.scheduling.RecurringScheduleSpec;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;

import static org.quartz.JobBuilder.newJob;


/**
 * Job scheduling root class that manages an instance of a Quartz scheduler.
 */
@Component
@Slf4j
public class JobSchedulerServiceImpl implements JobSchedulerService {
    private boolean started;
    private Scheduler scheduler;
    private DqoSchedulerConfigurationProperties schedulerConfigurationProperties;
    private SchedulerFactoryBean schedulerFactory;
    private SpringIoCJobFactory jobFactory;
    private TriggerFactory triggerFactory;
    private JobDataMapAdapter jobDataMapAdapter;
    private ScheduledJobListener scheduledJobListener;
    private JobDetail runChecksJob;
    private JobDetail synchronizeMetadataJob;
    private FileSystemSynchronizationReportingMode synchronizationMode = FileSystemSynchronizationReportingMode.silent;
    private CheckRunReportingMode checkRunReportingMode = CheckRunReportingMode.silent;

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
                                   SchedulerFactoryBean schedulerFactory,
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
     * Checks if the job scheduler is started.
     *
     * @return True - the job scheduler is running, false - it is not started.
     */
    @Override
    public boolean isStarted() {
        return this.started;
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

        log.debug(String.format("Starting the job scheduler, synchronization mode: %s, check run mode: %s",
                synchronizationMode, checkRunReportingMode));

        if (this.started) {
            return;
        }

        createAndStartScheduler();
        defineDefaultJobs();
        this.started = true;
    }

    /**
     * Creates and starts a scheduler, but without any jobs.
     */
    public void createAndStartScheduler() {
        assert scheduler == null;
        try {
            this.scheduler = schedulerFactory.getScheduler();
            this.scheduler.setJobFactory(this.jobFactory);
            if (this.scheduler.getListenerManager().getJobListeners().stream()
                    .allMatch(l -> l != this.scheduledJobListener)) {
                this.scheduler.getListenerManager().addJobListener(this.scheduledJobListener);
            }
            this.schedulerFactory.start();
        }
        catch (Exception ex) {
            log.error("Failed to start the job scheduler because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Defines the default jobs like running checks or synchronizing the metadata.
     */
    public void defineDefaultJobs() {
        try {
            if (!this.scheduler.checkExists(JobKeys.RUN_CHECKS)) {
                this.runChecksJob = newJob(RunScheduledChecksSchedulerJob.class)
                        .withIdentity(JobKeys.RUN_CHECKS)
                        .storeDurably()
                        .build();
                this.scheduler.addJob(this.runChecksJob, true);
            }

            if (!this.scheduler.checkExists(JobKeys.SYNCHRONIZE_METADATA)) {
                this.synchronizeMetadataJob = newJob(SynchronizeMetadataSchedulerJob.class)
                        .withIdentity(JobKeys.SYNCHRONIZE_METADATA)
                        .storeDurably()
                        .build();
                this.scheduler.addJob(this.synchronizeMetadataJob, true);
            }

            String scanMetadataCronSchedule = this.schedulerConfigurationProperties.getSynchronizeCronSchedule();
            RecurringScheduleSpec scanMetadataRecurringScheduleSpec = new RecurringScheduleSpec(scanMetadataCronSchedule);
            Trigger scanMetadataTrigger = this.triggerFactory.createTrigger(scanMetadataRecurringScheduleSpec, JobKeys.SYNCHRONIZE_METADATA);

            this.scheduler.scheduleJob(scanMetadataTrigger);
        } catch (SchedulerException ex) {
            log.error("Failed to define the default jobs because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }
    }

    /**
     * Stops the scheduler.
     */
    @Override
    @PreDestroy
    public void shutdown() {
        if (!this.started) {
            return;
        }

        log.debug("Shutting down the job scheduler");

        try {
            if (this.scheduler != null) {
                List<? extends Trigger> triggersOfRunChecks = this.scheduler.getTriggersOfJob(JobKeys.RUN_CHECKS);
                if (triggersOfRunChecks != null) {
                    for (Trigger trigger : triggersOfRunChecks) {
                        this.scheduler.unscheduleJob(trigger.getKey());
                    }
                }

                List<? extends Trigger> triggersOfSynchronizeMeta = this.scheduler.getTriggersOfJob(JobKeys.SYNCHRONIZE_METADATA);
                if (triggersOfRunChecks != null) {
                    for (Trigger trigger : triggersOfSynchronizeMeta) {
                        this.scheduler.unscheduleJob(trigger.getKey());
                    }
                }

                this.schedulerFactory.stop();
            }
            this.scheduler = null;
        }
        catch (Exception ex) {
            log.error("Failed to stop the job scheduler because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }
        finally {
            this.started = false;
        }
    }

    /**
     * Triggers the metadata synchronization job on the job scheduler. Calls the cloud sync and reload the metadata to detect new schedules.
     */
    @Override
    public void triggerMetadataSynchronization() {
        log.debug("Triggering the SYNCHRONIZE_METADATA job on the job scheduler.");

        try {
            this.scheduler.triggerJob(JobKeys.SYNCHRONIZE_METADATA);
        }
        catch (Exception ex) {
            log.error("Failed to trigger the metadata synchronization in the job scheduler because " + ex.getMessage(), ex);
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
            UniqueSchedulesCollection schedulesCollection = new UniqueSchedulesCollection();
            if (this.scheduler == null) {
                return schedulesCollection;
            }

            List<? extends Trigger> triggersOfJob = this.scheduler.getTriggersOfJob(jobKey);

            for (Trigger trigger : triggersOfJob) {
                RecurringScheduleSpec schedule = this.jobDataMapAdapter.getSchedule(trigger.getJobDataMap());
                schedulesCollection.add(schedule);
            }

            return schedulesCollection;
        }
        catch (Exception ex) {
            log.error("Failed to list active schedules in the job scheduler " + ex.getMessage(), ex);
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

        if (this.scheduler == null) {
            return; // the scheduler is not started yet
        }

        try {
            triggersOfJob = this.scheduler.getTriggersOfJob(jobKey);
        }
        catch (Exception ex) {
            log.error("Failed to list scheduled jobs because " + ex.getMessage(), ex);
            throw new JobSchedulerException(ex);
        }

        for (Trigger trigger : triggersOfJob) {
            RecurringScheduleSpec scheduleOnTrigger = this.jobDataMapAdapter.getSchedule(trigger.getJobDataMap());

            if (schedulesDelta.getSchedulesToDelete().contains(scheduleOnTrigger)) {
                try {
                    this.scheduler.unscheduleJob(trigger.getKey());
                }
                catch (Exception ex) {
                    log.error("Failed to unschedule a job because " + ex.getMessage(), ex);
                    if (firstException != null) {
                        firstException = ex;
                    }
                }
            }
        }

        for (RecurringScheduleSpec scheduleToAdd : schedulesDelta.getSchedulesToAdd().getUniqueSchedules()) {
            try {
                Trigger triggerToAdd = this.triggerFactory.createTrigger(scheduleToAdd, jobKey);

                if (!this.scheduler.checkExists(triggerToAdd.getKey())) {
                    this.scheduler.scheduleJob(triggerToAdd);
                }
            }
            catch (Exception ex) {
                log.error("Failed to schedule a job because " + ex.getMessage(), ex);
                if (firstException != null) {
                    firstException = ex;
                }
            }
        }

        if (firstException != null) {
            log.error("Failed to activate new schedules the job scheduler because " + firstException.getMessage(), firstException);
            throw new JobSchedulerException(firstException);
        }
    }
}
