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
package com.dqops.core.scheduler.runcheck;

import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.ParentDqoJobQueue;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.scheduler.quartz.JobDataMapAdapter;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
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
        final MonitoringScheduleSpec runChecksCronSchedule = this.jobDataMapAdapter.getSchedule(jobExecutionContext.getMergedJobDataMap());

        try {
            this.runScheduledChecksJob = this.dqoQueueJobFactory.createRunScheduledChecksJob();
            this.runScheduledChecksJob.setCronSchedule(runChecksCronSchedule);
            DqoUserPrincipal principal = this.principalProvider.getLocalUserPrincipal(); // TODO: user identity must be passed to the job, or at least the user identity, so we can get the principal
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
