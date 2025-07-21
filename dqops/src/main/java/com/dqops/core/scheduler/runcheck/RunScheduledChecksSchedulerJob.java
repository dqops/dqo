/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.scheduler.runcheck;

import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.ParentDqoJobQueue;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.scheduler.quartz.JobDataMapAdapter;
import com.dqops.metadata.scheduling.CronScheduleSpec;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Quartz job implementation that executes data quality checks for a given schedule. This is a Quartz job.
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class RunScheduledChecksSchedulerJob implements Job, InterruptableJob {
    private JobDataMapAdapter jobDataMapAdapter;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private ParentDqoJobQueue dqoJobQueue;
    private DqoUserPrincipalProvider principalProvider;
    private volatile RunScheduledChecksDqoJob runScheduledChecksJob;

    /**
     * Creates a data quality check run job that is executed by the job scheduler. Dependencies are injected.
     * @param jobDataMapAdapter Quartz job data adapter that extracts the original schedule definition from the job data.
     * @param dqoQueueJobFactory DQOps job factory.
     * @param dqoJobQueue DQOps job runner where the actual operation is executed.
     * @param principalProvider User principal provider that returns the system principal.
     */
    @Autowired
    public RunScheduledChecksSchedulerJob(JobDataMapAdapter jobDataMapAdapter,
                                          DqoQueueJobFactory dqoQueueJobFactory,
                                          ParentDqoJobQueue dqoJobQueue,
                                          DqoUserPrincipalProvider principalProvider) {
        this.jobDataMapAdapter = jobDataMapAdapter;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
        this.principalProvider = principalProvider;
    }

    /**
     * Executes a job that runs data quality checks for a given schedule.
     * @param jobExecutionContext Job execution context.
     * @throws JobExecutionException Exception with the error if the job execution fails.
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        final CronScheduleSpec runChecksCronSchedule = this.jobDataMapAdapter.getSchedule(jobExecutionContext.getMergedJobDataMap());

        try {
            this.runScheduledChecksJob = this.dqoQueueJobFactory.createRunScheduledChecksJob();
            this.runScheduledChecksJob.setCronSchedule(runChecksCronSchedule);
            String dataDomain = this.jobDataMapAdapter.getDataDomain(jobExecutionContext.getTrigger().getJobDataMap());

            DqoUserPrincipal principal = this.principalProvider.createLocalDomainAdminPrincipal(dataDomain);
            this.dqoJobQueue.pushJob(this.runScheduledChecksJob, principal);

            this.runScheduledChecksJob.waitForStarted();  // the job scheduler starts the jobs one by one, but they are pushed to the job queue and parallelized there
            this.runScheduledChecksJob = null;
        }
        catch (Exception ex) {
            log.error("Failed to execute a job that runs the data quality checks on a job scheduler, error: " + ex.getMessage(), ex);
            throw new JobExecutionException(ex);
        }
    }

    /**
     * Called by Quartz when a job is cancelled.
     * @throws UnableToInterruptJobException
     */
    @Override
    public void interrupt() throws UnableToInterruptJobException {
        RunScheduledChecksDqoJob waitingJob = this.runScheduledChecksJob;
        if (waitingJob != null) {
            this.dqoJobQueue.cancelJob(waitingJob.getJobId());
        }
    }
}
