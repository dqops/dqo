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

import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.scheduler.JobSchedulerService;
import ai.dqo.core.scheduler.quartz.JobKeys;
import ai.dqo.core.scheduler.schedules.UniqueSchedulesCollection;
import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.core.synchronization.fileexchange.FileSynchronizationDirection;
import ai.dqo.core.synchronization.jobs.SynchronizeRootFolderDqoQueueJob;
import ai.dqo.core.synchronization.jobs.SynchronizeRootFolderJobStarter;
import ai.dqo.core.synchronization.listeners.FileSystemSynchronizationReportingMode;
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
    private SynchronizeRootFolderJobStarter synchronizeRootFolderJobStarter;
    private DqoJobQueue dqoJobQueue;
    private ScheduleChangeFinderService scheduleChangeFinderService;
    private JobSchedulerService jobSchedulerService;

    /**
     * Creates a schedule metadata job instance using dependencies.
     * @param synchronizeRootFolderJobStarter Folder synchronization job starter, creates jobs.
     * @param dqoJobQueue DQO job queue to push the actual job to execute.
     * @param scheduleChangeFinderService Service that finds which cron expression are new or are no longer used.
     * @param jobSchedulerService Job scheduler service that manages scheduled jobs.
     */
    @Autowired
    public SynchronizeMetadataSchedulerJob(SynchronizeRootFolderJobStarter synchronizeRootFolderJobStarter,
                                           DqoJobQueue dqoJobQueue,
                                           ScheduleChangeFinderService scheduleChangeFinderService,
                                           JobSchedulerService jobSchedulerService) {
        this.synchronizeRootFolderJobStarter = synchronizeRootFolderJobStarter;
        this.dqoJobQueue = dqoJobQueue;
        this.scheduleChangeFinderService = scheduleChangeFinderService;
        this.jobSchedulerService = jobSchedulerService;
    }

    /**
     * Starts a DQO folder synchronization job in the background and returns without waiting.
     * @param dqoRoot DQO User home's folder to synchronize.
     */
    protected SynchronizeRootFolderDqoQueueJob startSynchronizationJob(DqoRoot dqoRoot) {
        SynchronizeRootFolderDqoQueueJob synchronizeRootFolderDqoQueueJob = this.synchronizeRootFolderJobStarter.startSynchronizeFolderJob(dqoRoot,
                FileSystemSynchronizationReportingMode.silent,
                FileSynchronizationDirection.full,
                false);

        return synchronizeRootFolderDqoQueueJob;
    }

    /**
     * Finds new or deleted CRON schedules (cron expressions) and updates the triggers in the quartz scheduler.
     */
    protected void updateListOfSchedulesInQuartzScheduler() {
        UniqueSchedulesCollection activeSchedules = this.jobSchedulerService.getActiveSchedules(JobKeys.RUN_CHECKS);
        JobSchedulesDelta schedulesToAddOrRemove = this.scheduleChangeFinderService.findSchedulesToAddOrRemove(activeSchedules);
        this.jobSchedulerService.applyScheduleDeltaToJob(schedulesToAddOrRemove, JobKeys.RUN_CHECKS);
    }

    /**
     * Executes a job that detects new schedules and manages Quartz triggers.
     * @param jobExecutionContext Job execution context.
     * @throws JobExecutionException Exception with the error if the job execution fails.
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            SynchronizeRootFolderDqoQueueJob synchronizeSourcesJob = this.synchronizeRootFolderJobStarter.createSynchronizeFolderJob(DqoRoot.sources,
                    FileSystemSynchronizationReportingMode.silent,
                    FileSynchronizationDirection.full,
                    false);
            this.dqoJobQueue.pushJob(synchronizeSourcesJob);

            // todo: start synchronization of other folder, add support for notifications to a job, because we need to execute extra code after the job finished (update schedules)

            this.startSynchronizationJob(DqoRoot.sensors);
            this.startSynchronizationJob(DqoRoot.rules);
            this.startSynchronizationJob(DqoRoot.checks);

            this.startSynchronizationJob(DqoRoot.data_sensor_readouts);
            this.startSynchronizationJob(DqoRoot.data_check_results);
            this.startSynchronizationJob(DqoRoot.data_errors);
            this.startSynchronizationJob(DqoRoot.data_statistics);
            this.startSynchronizationJob(DqoRoot.data_incidents);

            synchronizeSourcesJob.waitForFinish();
            updateListOfSchedulesInQuartzScheduler(); // should be started when the schedule source job finishes
        }
        catch (Exception ex) {
            log.error("Failed to execute a metadata synchronization job", ex);
            throw new JobExecutionException(ex);
        }
    }
}
