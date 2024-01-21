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
import com.dqops.core.dqocloud.apikey.DqoCloudApiKeyPayload;
import com.dqops.core.dqocloud.apikey.DqoCloudLicenseType;
import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.ParentDqoJobQueue;
import com.dqops.core.jobqueue.PushJobResult;
import com.dqops.core.principal.DqoUserPrincipalProvider;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.synchronization.jobs.SynchronizeMultipleFoldersDqoQueueJob;
import com.dqops.core.synchronization.jobs.SynchronizeMultipleFoldersDqoQueueJobParameters;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.utils.datetime.LocalDateTimeTruncateUtility;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Random;

/**
 * Quartz job implementation that scans the metadata and activates new schedules or stops unused schedules.
 */
@Component
@DisallowConcurrentExecution
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class SynchronizeMetadataSchedulerJob implements Job, InterruptableJob {
    /**
     * The number of seconds to sleep before starting.
     */
    public static final int MINIMUM_SYNCHRONIZATION_DELAY_SECONDS = 60;

    private DqoQueueJobFactory dqoQueueJobFactory;
    private ParentDqoJobQueue dqoJobQueue;
    private DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties;
    private DqoUserPrincipalProvider principalProvider;
    private static LocalDateTime lastExecutedAtHour;
    private static int jobRunCount;
    private static Random random = new Random();
    private volatile boolean interrupted;
    private volatile SynchronizeMultipleFoldersDqoQueueJob synchronizeMultipleFoldersJob;

    /**
     * Creates a schedule metadata job instance using dependencies.
     * @param dqoQueueJobFactory DQOps job queue factory.
     * @param dqoJobQueue DQOps job queue to push the actual job to execute.
     * @param dqoSchedulerConfigurationProperties DQOps cron scheduler configuration properties.
     * @param principalProvider Principal provider for the local instance.
     */
    @Autowired
    public SynchronizeMetadataSchedulerJob(DqoQueueJobFactory dqoQueueJobFactory,
                                           ParentDqoJobQueue dqoJobQueue,
                                           DqoSchedulerConfigurationProperties dqoSchedulerConfigurationProperties,
                                           DqoUserPrincipalProvider principalProvider) {
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
            DqoUserPrincipal principal = this.principalProvider.createUserPrincipalForAdministrator(); // TODO: get the principal from the job
            DqoCloudApiKeyPayload cloudApiKeyPayload = principal.getApiKeyPayload();
            if (cloudApiKeyPayload == null) {
                return;
            }

            if (jobRunCount > 0 && (cloudApiKeyPayload.getLicenseType() == null ||
                    cloudApiKeyPayload.getLicenseType() == DqoCloudLicenseType.FREE ||
                    cloudApiKeyPayload.getExpiresAt() != null)) {
                // free user

                LocalDateTime executionHour = LocalDateTimeTruncateUtility.truncateTimePeriod(LocalDateTime.now(), TimePeriodGradient.hour);
                if (Objects.equals(executionHour, lastExecutedAtHour)) {
                    return;
                }

                lastExecutedAtHour = executionHour;
                if (!waitRandomTime(jobExecutionContext, 3600 - MINIMUM_SYNCHRONIZATION_DELAY_SECONDS)) {
                    return;
                }
            }

            if (jobRunCount > 0 && !interrupted && !waitRandomTime(jobExecutionContext, MINIMUM_SYNCHRONIZATION_DELAY_SECONDS)) {
                return;
            }

            this.synchronizeMultipleFoldersJob = this.dqoQueueJobFactory.createSynchronizeMultipleFoldersJob();
            SynchronizeMultipleFoldersDqoQueueJobParameters jobParameters = new SynchronizeMultipleFoldersDqoQueueJobParameters();

            if (this.dqoSchedulerConfigurationProperties.isEnableCloudSync()) {
                if (jobRunCount == 0 ||  // first synchronization when the application starts, must download everything
                        this.dqoSchedulerConfigurationProperties.getSynchronizedFolders() == ScheduledSynchronizationFolderSelectionMode.all) {
                    jobParameters.synchronizeAllFolders();
                } else {
                    jobParameters.setSynchronizeFolderWithLocalChanges(true);
                }
            }

            jobRunCount++;
            jobParameters.setDetectCronSchedules(true);
            synchronizeMultipleFoldersJob.setParameters(jobParameters);

            PushJobResult<Void> pushJobResult = this.dqoJobQueue.pushJob(synchronizeMultipleFoldersJob, principal);
            pushJobResult.getFinishedFuture().get();
            this.synchronizeMultipleFoldersJob = null;
        }
        catch (Exception ex) {
            log.error("Failed to execute a metadata synchronization job", ex);
            throw new JobExecutionException(ex);
        }
    }

    /**
     * Waits a random number of seconds before starting a synchronization, to distribute load.
     * @param jobExecutionContext Quartz job context.
     * @param waitSeconds Number of seconds to wait.
     * @return True - run the job, false - scheduler was stopped and the job was cancelled.
     * @throws InterruptedException
     * @throws SchedulerException
     */
    public boolean waitRandomTime(JobExecutionContext jobExecutionContext,
                                  int waitSeconds)
            throws InterruptedException, SchedulerException {
        int startSecondOffset = random.nextInt() % waitSeconds;

        Instant expectedRunAt = Instant.now().plus(startSecondOffset, ChronoUnit.SECONDS);
        while (Instant.now().isBefore(expectedRunAt)) {
            Thread.sleep(100);

            if (this.interrupted) {
                return false;
            }

            if (jobExecutionContext.getScheduler().isShutdown()) {
                return false;
            }

            if (!jobExecutionContext.getScheduler().checkExists(jobExecutionContext.getTrigger().getKey())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Called by the scheduler when the job is asked to exit.
     * @throws UnableToInterruptJobException
     */
    @Override
    public void interrupt() throws UnableToInterruptJobException {
        this.interrupted = true;
        SynchronizeMultipleFoldersDqoQueueJob job = this.synchronizeMultipleFoldersJob;
        if (job != null) {
            this.dqoJobQueue.cancelJob(job.getJobId());
        }
    }
}
