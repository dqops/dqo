/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.core.scheduler.scan;

import ai.dqo.core.filesystem.synchronization.listeners.FileSystemSynchronizationReportingMode;
import ai.dqo.core.jobqueue.DelegateDqoQueueJob;
import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.scheduler.JobSchedulerService;
import ai.dqo.core.scheduler.quartz.JobKeys;
import ai.dqo.core.scheduler.schedules.UniqueSchedulesCollection;
import ai.dqo.core.scheduler.synchronization.SchedulerFileSynchronizationService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Quartz job implementation that scans the metadata and activates new schedules or stops unused schedules.
 */
@Component
@DisallowConcurrentExecution
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class SynchronizeMetadataSchedulerJob implements Job {
    private ScheduleChangeFinderService scheduleChangeFinderService;
    private JobSchedulerService jobSchedulerService;
    private SchedulerFileSynchronizationService schedulerFileSynchronizationService;
    private DqoJobQueue dqoJobQueue;

    /**
     * Creates a schedule metadata job instance using dependencies.
     * @param scheduleChangeFinderService Schedule finder that knows how to identify schedules (cron expressions) in the metadata.
     * @param jobSchedulerService Job scheduler used to update a list of schedules.
     * @param schedulerFileSynchronizationService Cloud synchronization service.
     * @param dqoJobQueue DQO job queue to push the actual job to execute.
     */
    @Autowired
    public SynchronizeMetadataSchedulerJob(ScheduleChangeFinderService scheduleChangeFinderService,
                                           JobSchedulerService jobSchedulerService,
                                           SchedulerFileSynchronizationService schedulerFileSynchronizationService,
                                           DqoJobQueue dqoJobQueue) {
        this.scheduleChangeFinderService = scheduleChangeFinderService;
        this.jobSchedulerService = jobSchedulerService;
        this.schedulerFileSynchronizationService = schedulerFileSynchronizationService;
        this.dqoJobQueue = dqoJobQueue;
    }

    /**
     * Executes a job that detects new schedules and manages Quartz triggers.
     * @param jobExecutionContext Job execution context.
     * @throws JobExecutionException Exception with the error if the job execution fails.
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            DelegateDqoQueueJob<Void> dqoQueueJob = new DelegateDqoQueueJob<>(this::runJobOperation);
            this.dqoJobQueue.pushJob(dqoQueueJob);
            
            dqoQueueJob.waitForFinish(); // waits for the result, hanging the current thread
        }
        catch (Exception ex) {
            log.error("Failed to execute a metadata synchronization job", ex);
            throw new JobExecutionException(ex);
        }
    }

    /**
     * The core implementation of the operation performed by the scheduled job.
     */
    public Void runJobOperation() {
        FileSystemSynchronizationReportingMode synchronizationMode = this.jobSchedulerService.getSynchronizationMode();
        this.schedulerFileSynchronizationService.synchronizeAll(synchronizationMode);

        UniqueSchedulesCollection activeSchedules = this.jobSchedulerService.getActiveSchedules(JobKeys.RUN_CHECKS);
        JobSchedulesDelta schedulesToAddOrRemove = this.scheduleChangeFinderService.findSchedulesToAddOrRemove(activeSchedules);
        this.jobSchedulerService.applyScheduleDeltaToJob(schedulesToAddOrRemove, JobKeys.RUN_CHECKS);

        return null;
    }
}
