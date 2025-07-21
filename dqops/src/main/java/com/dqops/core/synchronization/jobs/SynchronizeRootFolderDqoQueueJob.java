/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.synchronization.jobs;

import com.dqops.core.jobqueue.*;
import com.dqops.core.jobqueue.concurrency.ConcurrentJobType;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyTarget;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.synchronization.service.DqoCloudSynchronizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * DQOps queue job that runs synchronization with DQOps Cloud in the background for one user home's root folder (sources, sensors, etc.).
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SynchronizeRootFolderDqoQueueJob extends DqoQueueJob<Void> {
    private DqoCloudSynchronizationService cloudSynchronizationService;
    private SynchronizeRootFolderDqoQueueJobParameters parameters;

    /**
     * Creates a synchronization job.
     * @param cloudSynchronizationService DQOps Cloud synchronization service to use (provided as a dependency).
     */
    @Autowired
    public SynchronizeRootFolderDqoQueueJob(
            DqoCloudSynchronizationService cloudSynchronizationService) {
        this.cloudSynchronizationService = cloudSynchronizationService;
    }

    /**
     * Returns the job parameters.
     * @return Job parameters.
     */
    public SynchronizeRootFolderDqoQueueJobParameters getParameters() {
        return parameters;
    }

    /**
     * Sets the job parameters.
     * @param parameters Job parameters.
     */
    public void setParameters(SynchronizeRootFolderDqoQueueJobParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @param jobExecutionContext Job execution context.
     *
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public Void onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.getPrincipal().throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        this.cloudSynchronizationService.synchronizeFolder(
                this.parameters.getSynchronizationParameter().getFolder(),
                this.getPrincipal().getDataDomainIdentity(),
                this.parameters.getSynchronizationParameter().getDirection(),
                this.parameters.getSynchronizationParameter().isForceRefreshNativeTable(),
                this.parameters.getFileSystemSynchronizationListener());
        return null;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.synchronize_folder;
    }

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint[] getConcurrencyConstraints() {
        JobConcurrencyTarget concurrencyTarget = new JobConcurrencyTarget(ConcurrentJobType.SYNCHRONIZE_FOLDER,
                this.parameters.getSynchronizationParameter().getFolder());
        JobConcurrencyConstraint synchronizationLimit = new JobConcurrencyConstraint(concurrencyTarget, 1);
        return new JobConcurrencyConstraint[] { synchronizationLimit };
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
           setSynchronizeRootFolderParameters(parameters);
        }};
    }

    @Override
    public String toString() {
        return super.toString() + ", parameters: " + this.parameters.toString();
    }
}
