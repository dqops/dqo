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

package com.dqops.core.synchronization.jobs;

import com.dqops.core.jobqueue.*;
import com.dqops.core.jobqueue.concurrency.ConcurrentJobType;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyTarget;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.scheduler.JobSchedulerService;
import com.dqops.core.scheduler.quartz.JobKeys;
import com.dqops.core.scheduler.synchronize.JobSchedulesDelta;
import com.dqops.core.scheduler.synchronize.ScheduleChangeFinderService;
import com.dqops.core.scheduler.schedules.UniqueSchedulesCollection;
import com.dqops.core.synchronization.contract.DqoRoot;
import com.dqops.core.synchronization.listeners.SilentFileSystemSynchronizationListener;
import com.dqops.core.synchronization.status.CloudSynchronizationFoldersStatusModel;
import com.dqops.core.synchronization.status.SynchronizationStatusTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DQO parent queue job that will start multiple child jobs, each child job will synchronize all DQO User home folder.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SynchronizeMultipleFoldersDqoQueueJob extends ParentDqoQueueJob<Void> {
    private DqoJobQueue dqoJobQueue;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private ScheduleChangeFinderService scheduleChangeFinderService;
    private JobSchedulerService jobSchedulerService;
    private SynchronizationStatusTracker synchronizationStatusTracker;
    private SynchronizeMultipleFoldersDqoQueueJobParameters parameters;

    /**
     * Creates an instance of a job that synchronizes multiple folder roots.
     * @param dqoJobQueue Dqo queue for child jobs, where folder level jobs will be scheduled.
     * @param dqoQueueJobFactory Factory of jobs.
     * @param scheduleChangeFinderService Cron schedule finder that will find new schedules.
     * @param jobSchedulerService Cron job scheduler, used to update the scheduling configuration.
     * @param synchronizationStatusTracker Folder synchronization tracker service. Used to detect which folders were updated and synchronize only folders with local updates.
     */
    @Autowired
    public SynchronizeMultipleFoldersDqoQueueJob(DqoJobQueue dqoJobQueue,
                                                 DqoQueueJobFactory dqoQueueJobFactory,
                                                 ScheduleChangeFinderService scheduleChangeFinderService,
                                                 JobSchedulerService jobSchedulerService,
                                                 SynchronizationStatusTracker synchronizationStatusTracker) {
        this.dqoJobQueue = dqoJobQueue;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.scheduleChangeFinderService = scheduleChangeFinderService;
        this.jobSchedulerService = jobSchedulerService;
        this.synchronizationStatusTracker = synchronizationStatusTracker;
    }

    /**
     * Returns the job parameters with a selection of folders that should be synchronized.
     * @return Job parameters.
     */
    public SynchronizeMultipleFoldersDqoQueueJobParameters getParameters() {
        return parameters;
    }

    /**
     * Sets the object with the job parameters.
     * @param parameters Job parameters.
     */
    public void setParameters(SynchronizeMultipleFoldersDqoQueueJobParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Finds new or deleted CRON schedules (cron expressions) and updates the triggers in the quartz scheduler.
     */
    protected void updateListOfSchedulesInQuartzScheduler() {
        if (this.jobSchedulerService.isStarted()) {
            UniqueSchedulesCollection activeSchedules = this.jobSchedulerService.getActiveSchedules(JobKeys.RUN_CHECKS);
            JobSchedulesDelta schedulesToAddOrRemove = this.scheduleChangeFinderService.findSchedulesToAddOrRemove(activeSchedules);
            this.jobSchedulerService.applyScheduleDeltaToJob(schedulesToAddOrRemove, JobKeys.RUN_CHECKS);
        }
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     *
     * @param jobExecutionContext Job execution context.
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public Void onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.getPrincipal().throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        List<SynchronizeRootFolderParameters> jobParametersList = new ArrayList<>();

        SynchronizeMultipleFoldersDqoQueueJobParameters clonedParameters = this.parameters.clone();
        if (clonedParameters.isSynchronizeFolderWithLocalChanges()) {
            CloudSynchronizationFoldersStatusModel currentSynchronizationStatus = this.synchronizationStatusTracker.getCurrentSynchronizationStatus();
            clonedParameters.synchronizeFoldersWithLocalChanges(currentSynchronizationStatus);
        }

        if (clonedParameters.isSources()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.sources,
                    clonedParameters.getDirection(), clonedParameters.isForceRefreshNativeTables()));
        }

        if (clonedParameters.isSensors()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.sensors,
                    clonedParameters.getDirection(), clonedParameters.isForceRefreshNativeTables()));
        }

        if (clonedParameters.isRules()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.rules,
                    clonedParameters.getDirection(), clonedParameters.isForceRefreshNativeTables()));
        }

        if (clonedParameters.isChecks()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.checks,
                    clonedParameters.getDirection(), clonedParameters.isForceRefreshNativeTables()));
        }

        if (clonedParameters.isSettings()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.settings,
                    clonedParameters.getDirection(), clonedParameters.isForceRefreshNativeTables()));
        }

        if (clonedParameters.isCredentials()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.credentials,
                    clonedParameters.getDirection(), clonedParameters.isForceRefreshNativeTables()));
        }

        if (clonedParameters.isDataSensorReadouts()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.data_sensor_readouts,
                    clonedParameters.getDirection(), clonedParameters.isForceRefreshNativeTables()));
        }

        if (clonedParameters.isDataCheckResults()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.data_check_results,
                    clonedParameters.getDirection(), clonedParameters.isForceRefreshNativeTables()));
        }

        if (clonedParameters.isDataStatistics()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.data_statistics,
                    clonedParameters.getDirection(), clonedParameters.isForceRefreshNativeTables()));
        }

        if (clonedParameters.isDataErrors()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.data_errors,
                    clonedParameters.getDirection(), clonedParameters.isForceRefreshNativeTables()));
        }

        if (clonedParameters.isDataIncidents()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.data_incidents,
                    clonedParameters.getDirection(), clonedParameters.isForceRefreshNativeTables()));
        }

        Collection<DqoQueueJob<Void>> synchronizeFolderJobs = jobParametersList.stream()
                .map(synchronizeFolderParameter -> {
                    SynchronizeRootFolderDqoQueueJob synchronizeFolderJob = this.dqoQueueJobFactory.createSynchronizeRootFolderJob();
                    SynchronizeRootFolderDqoQueueJobParameters jobParameters = new SynchronizeRootFolderDqoQueueJobParameters(synchronizeFolderParameter,
                            new SilentFileSystemSynchronizationListener());
                    synchronizeFolderJob.setParameters(jobParameters);
                    return (DqoQueueJob<Void>)synchronizeFolderJob;
                })
                .collect(Collectors.toList());

        ChildDqoQueueJobsContainer<Void> childJobsContainer = this.dqoJobQueue.pushChildJobs(synchronizeFolderJobs, jobExecutionContext.getJobId(),
                this.getPrincipal());
        childJobsContainer.waitForChildResults(jobExecutionContext.getCancellationToken());

        // TODO: the child folder synchronization jobs should return a summary of files uploaded and downloaded, when the "sources" folder has incoming changes, we should always update the schedules

        if (clonedParameters.isDetectCronSchedules() || clonedParameters.isSources()) {
            this.updateListOfSchedulesInQuartzScheduler();
        }

        return null;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.SYNCHRONIZE_MULTIPLE_FOLDERS;
    }

    /**
     * Creates a typed parameters model that could be sent back to the UI.
     * The parameters model could contain a subset of parameters.
     *
     * @return Job queue parameters that are easy to serialize and shown in the UI.
     */
    @Override
    public DqoJobEntryParametersModel createParametersModel() {
        return new DqoJobEntryParametersModel() {{
            setSynchronizeMultipleFoldersParameters(parameters);
        }};
    }

    /**
     * Returns an array of concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint[] getConcurrencyConstraints() {
        JobConcurrencyTarget concurrencyTarget = new JobConcurrencyTarget(
                ConcurrentJobType.SYNCHRONIZE_MULTIPLE_FOLDERS,
                ConcurrentJobType.SYNCHRONIZE_MULTIPLE_FOLDERS // used also as a target, because an enum is a singleton
        );
        JobConcurrencyConstraint synchronizationLimit = new JobConcurrencyConstraint(concurrencyTarget, 1);
        return new JobConcurrencyConstraint[] { synchronizationLimit };
    }
}
