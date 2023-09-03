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
package com.dqops.core.scheduler.synchronize;

import com.dqops.core.configuration.DqoSchedulerConfigurationProperties;
import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.ParentDqoJobQueue;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.principal.DqoCloudApiKeyPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.synchronization.jobs.SynchronizeMultipleFoldersDqoQueueJob;
import com.dqops.core.synchronization.jobs.SynchronizeMultipleFoldersDqoQueueJobParameters;
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
    private DqoQueueJobFactory dqoQueueJobFactory;
    private ParentDqoJobQueue dqoJobQueue;
    private DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties;
    private DqoCloudApiKeyPrincipalProvider principalProvider;

    /**
     * Creates a schedule metadata job instance using dependencies.
     * @param dqoQueueJobFactory DQO job queue factory.
     * @param dqoJobQueue DQO job queue to push the actual job to execute.
     * @param dqoSchedulerConfigurationProperties DQO cron scheduler configuration properties.
     * @param principalProvider Principal provider for the local instance.
     */
    @Autowired
    public SynchronizeMetadataSchedulerJob(DqoQueueJobFactory dqoQueueJobFactory,
                                           ParentDqoJobQueue dqoJobQueue,
                                           DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties,
                                           DqoCloudApiKeyPrincipalProvider principalProvider) {
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
        this.dqoSchedulerConfigurationProperties = dqoSchedulerConfigurationProperties;
        this.principalProvider = principalProvider;
    }

    /**
     * Executes a job that detects new schedules and manages Quartz triggers.
     * @param jobExecutionContext Job execution context.
     * @throws JobExecutionException Exception with the error if the job execution fails.
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            SynchronizeMultipleFoldersDqoQueueJob synchronizeMultipleFoldersJob = this.dqoQueueJobFactory.createSynchronizeMultipleFoldersJob();
            SynchronizeMultipleFoldersDqoQueueJobParameters jobParameters = new SynchronizeMultipleFoldersDqoQueueJobParameters();

            if (this.dqoSchedulerConfigurationProperties.isEnableCloudSync()) {
                switch (this.dqoSchedulerConfigurationProperties.getSynchronizedFolders()) {
                    case all: {
                        jobParameters.synchronizeAllFolders();
                        break;
                    }

                    case locally_changed:
                    default: {
                        jobParameters.setSynchronizeFolderWithLocalChanges(true);
                        break;
                    }
                }
            }

            jobParameters.setDetectCronSchedules(true);
            synchronizeMultipleFoldersJob.setParameters(jobParameters);

            DqoUserPrincipal principal = this.principalProvider.createUserPrincipal();
            PushJobResult<Void> pushJobResult = this.dqoJobQueue.pushJob(synchronizeMultipleFoldersJob, principal);
            pushJobResult.getFinishedFuture().get();
        }
        catch (Exception ex) {
            log.error("Failed to execute a metadata synchronization job", ex);
            throw new JobExecutionException(ex);
        }
    }
}
