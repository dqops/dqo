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

package ai.dqo.core.synchronization.jobs;

import ai.dqo.core.jobqueue.*;
import ai.dqo.core.jobqueue.concurrency.ConcurrentJobType;
import ai.dqo.core.jobqueue.concurrency.JobConcurrencyConstraint;
import ai.dqo.core.jobqueue.concurrency.JobConcurrencyTarget;
import ai.dqo.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import ai.dqo.core.synchronization.contract.DqoRoot;
import ai.dqo.core.synchronization.listeners.SilentFileSystemSynchronizationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    private SynchronizeMultipleFoldersDqoQueueJobParameters parameters;

    /**
     * Creates an instance of a job that synchronizes multiple folder roots.
     * @param dqoJobQueue Dqo queue for child jobs, where folder level jobs will be scheduled.
     * @param dqoQueueJobFactory Factory of jobs.
     */
    @Autowired
    public SynchronizeMultipleFoldersDqoQueueJob(DqoJobQueue dqoJobQueue,
                                                 DqoQueueJobFactory dqoQueueJobFactory) {
        this.dqoJobQueue = dqoJobQueue;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
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
     * Job internal implementation method that should be implemented by derived jobs.
     *
     * @param jobExecutionContext Job execution context.
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public Void onExecute(DqoJobExecutionContext jobExecutionContext) {
        List<SynchronizeRootFolderParameters> jobParametersList = new ArrayList<>();
        if (parameters.isSources()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.sources,
                    parameters.getDirection(), parameters.isForceRefreshNativeTables()));
        }

        if (parameters.isSensors()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.sensors,
                    parameters.getDirection(), parameters.isForceRefreshNativeTables()));
        }

        if (parameters.isRules()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.rules,
                    parameters.getDirection(), parameters.isForceRefreshNativeTables()));
        }

        if (parameters.isChecks()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.checks,
                    parameters.getDirection(), parameters.isForceRefreshNativeTables()));
        }

        if (parameters.isDataSensorReadouts()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.data_sensor_readouts,
                    parameters.getDirection(), parameters.isForceRefreshNativeTables()));
        }

        if (parameters.isDataCheckResults()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.data_check_results,
                    parameters.getDirection(), parameters.isForceRefreshNativeTables()));
        }

        if (parameters.isDataStatistics()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.data_statistics,
                    parameters.getDirection(), parameters.isForceRefreshNativeTables()));
        }

        if (parameters.isDataErrors()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.data_errors,
                    parameters.getDirection(), parameters.isForceRefreshNativeTables()));
        }

        if (parameters.isDataIncidents()) {
            jobParametersList.add(new SynchronizeRootFolderParameters(DqoRoot.data_incidents,
                    parameters.getDirection(), parameters.isForceRefreshNativeTables()));
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

        ChildDqoQueueJobsContainer<Void> childJobsContainer = this.dqoJobQueue.pushChildJobs(synchronizeFolderJobs);
        childJobsContainer.waitForChildResults(jobExecutionContext.getCancellationToken());
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
