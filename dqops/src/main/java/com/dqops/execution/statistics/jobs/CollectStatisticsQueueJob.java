/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.statistics.jobs;

import com.dqops.core.jobqueue.DqoJobExecutionContext;
import com.dqops.core.jobqueue.DqoJobType;
import com.dqops.core.jobqueue.ParentDqoQueueJob;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.execution.statistics.StatisticsCollectionExecutionSummary;
import com.dqops.execution.statistics.StatisticsCollectorsExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Run statistics collection queue job.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CollectStatisticsQueueJob extends ParentDqoQueueJob<StatisticsCollectionExecutionSummary> {
    private ExecutionContextFactory executionContextFactory;
    private StatisticsCollectorsExecutionService statisticsCollectorsExecutionService;
    private CollectStatisticsQueueJobParameters parameters;

    @Autowired
    public CollectStatisticsQueueJob(
            ExecutionContextFactory executionContextFactory,
            StatisticsCollectorsExecutionService statisticsCollectorsExecutionService) {
        this.executionContextFactory = executionContextFactory;
        this.statisticsCollectorsExecutionService = statisticsCollectorsExecutionService;
    }

    /**
     * Returns the job parameters.
     * @return Job parameters.
     */
    public CollectStatisticsQueueJobParameters getParameters() {
        return parameters;
    }

    /**
     * Sets the job parameters.
     * @param parameters Job parameters.
     */
    public void setParameters(CollectStatisticsQueueJobParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @param jobExecutionContext Job execution context.
     *
     * @return Optional result value that can be returned by the job.
     */
    @Override
    public StatisticsCollectionExecutionSummary onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.getPrincipal().throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        UserDomainIdentity userDomainIdentity = this.getPrincipal().getDataDomainIdentity();
        ExecutionContext executionContext = this.executionContextFactory.create(userDomainIdentity);
        StatisticsCollectionExecutionSummary statisticsCollectionExecutionSummary = this.statisticsCollectorsExecutionService.executeStatisticsCollectors(
                executionContext,
                this.parameters.getStatisticsCollectorSearchFilters(),
                this.parameters.getProgressListener(),
                this.parameters.getDataScope(),
                this.parameters.getSamplesLimit(),
                this.parameters.isConfigureTable(),
                this.parameters.isDummySensorExecution(),
                true,
                jobExecutionContext.getJobId(),
                jobExecutionContext.getCancellationToken(),
                this.getPrincipal());

        if (statisticsCollectionExecutionSummary.getAllChecksFailed()) {
            String firstConnectionName = statisticsCollectionExecutionSummary.getFirstConnectionName();
            String firstErrorMessage = statisticsCollectionExecutionSummary.getFirstException() != null ?
                    statisticsCollectionExecutionSummary.getFirstException().getMessage() : "";
            throw new DqoStatisticsCollectionJobFailedException("Cannot collect statistics on the connection " + firstConnectionName +
                    ", the first error: " + firstErrorMessage,
                    statisticsCollectionExecutionSummary.getFirstException());
        }

        CollectStatisticsResult collectStatisticsResult =
                CollectStatisticsResult.fromStatisticsExecutionSummary(statisticsCollectionExecutionSummary);
        CollectStatisticsQueueJobParameters clonedParameters = this.getParameters().clone();
        clonedParameters.setCollectStatisticsResult(collectStatisticsResult);
        setParameters(clonedParameters);

        return statisticsCollectionExecutionSummary;
    }

    /**
     * Returns a job type that this job class is running. Used to identify jobs.
     *
     * @return Job type.
     */
    @Override
    public DqoJobType getJobType() {
        return DqoJobType.collect_statistics;
    }

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint[] getConcurrencyConstraints() {
        return null; // user can start any number of "collect statistics" operations, the concurrency will be applied later on a table level
    }

    /**
     * Creates a typed parameters model that can be sent back to the UI.
     * The parameters model can contain a subset of parameters.
     *
     * @return Job queue parameters that are easy to serialize and shown in the UI.
     */
    @Override
    public DqoJobEntryParametersModel createParametersModel() {
        return new DqoJobEntryParametersModel() {{
            setCollectStatisticsParameters(parameters);
        }};
    }
}
