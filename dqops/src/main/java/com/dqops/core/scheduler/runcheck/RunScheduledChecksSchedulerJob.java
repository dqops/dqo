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
import com.dqops.core.scheduler.quartz.JobDataMapAdapter;
import com.dqops.metadata.scheduling.RecurringScheduleSpec;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
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
public class RunScheduledChecksSchedulerJob implements Job {
    private JobDataMapAdapter jobDataMapAdapter;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private ParentDqoJobQueue dqoJobQueue;

    /**
     * Creates a data quality check run job that is executed by the job scheduler. Dependencies are injected.
     * @param jobDataMapAdapter Quartz job data adapter that extracts the original schedule definition from the job data.
     * @param dqoQueueJobFactory DQO job factory.
     * @param dqoJobQueue DQO job runner where the actual operation is executed.
     */
    @Autowired
    public RunScheduledChecksSchedulerJob(JobDataMapAdapter jobDataMapAdapter,
                                          DqoQueueJobFactory dqoQueueJobFactory,
                                          ParentDqoJobQueue dqoJobQueue) {
        this.jobDataMapAdapter = jobDataMapAdapter;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
    }

    /**
     * Executes a job that runs data quality checks for a given schedule.
     * @param jobExecutionContext Job execution context.
     * @throws JobExecutionException Exception with the error if the job execution fails.
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        final RecurringScheduleSpec runChecksCronSchedule = this.jobDataMapAdapter.getSchedule(jobExecutionContext.getMergedJobDataMap());

        try {
            RunScheduledChecksDqoJob runScheduledChecksJob = this.dqoQueueJobFactory.createRunScheduledChecksJob();
            runScheduledChecksJob.setCronSchedule(runChecksCronSchedule);
            this.dqoJobQueue.pushJob(runScheduledChecksJob);

            runScheduledChecksJob.waitForStarted();  // the job scheduler starts the jobs one by one, but they are pushed to the job queue and parallelized there
        }
        catch (Exception ex) {
            log.error("Failed to execute a job that runs the data quality checks on a job scheduler, error: " + ex.getMessage(), ex);
            throw new JobExecutionException(ex);
        }
    }
}
