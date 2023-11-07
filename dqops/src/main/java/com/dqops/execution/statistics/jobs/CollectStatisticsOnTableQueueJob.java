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
package com.dqops.execution.statistics.jobs;

import com.dqops.core.jobqueue.DqoJobExecutionContext;
import com.dqops.core.jobqueue.DqoJobType;
import com.dqops.core.jobqueue.DqoQueueJob;
import com.dqops.core.jobqueue.concurrency.ConcurrentJobType;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyConstraint;
import com.dqops.core.jobqueue.concurrency.JobConcurrencyTarget;
import com.dqops.core.jobqueue.monitoring.DqoJobEntryParametersModel;
import com.dqops.core.principal.DqoPermissionGrantedAuthorities;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.execution.statistics.StatisticsCollectionExecutionSummary;
import com.dqops.execution.statistics.StatisticsCollectorsExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Run statistics collection queue job that is a child job and collects statistics on a single table.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CollectStatisticsOnTableQueueJob extends DqoQueueJob<StatisticsCollectionExecutionSummary> {
    private ExecutionContextFactory executionContextFactory;
    private StatisticsCollectorsExecutionService statisticsCollectorsExecutionService;
    private CollectStatisticsOnTableQueueJobParameters parameters;

    @Autowired
    public CollectStatisticsOnTableQueueJob(
            ExecutionContextFactory executionContextFactory,
            StatisticsCollectorsExecutionService statisticsCollectorsExecutionService) {
        this.executionContextFactory = executionContextFactory;
        this.statisticsCollectorsExecutionService = statisticsCollectorsExecutionService;
    }

    /**
     * Returns the job parameters.
     * @return Job parameters.
     */
    public CollectStatisticsOnTableQueueJobParameters getParameters() {
        return parameters;
    }

    /**
     * Sets the job parameters.
     * @param parameters Job parameters.
     */
    public void setParameters(CollectStatisticsOnTableQueueJobParameters parameters) {
        this.parameters = parameters;
    }

    /**
     * Job internal implementation method that should be implemented by derived jobs.
     * @param jobExecutionContext Job execution context.
     *
     * @return Optional result value that could be returned by the job.
     */
    @Override
    public StatisticsCollectionExecutionSummary onExecute(DqoJobExecutionContext jobExecutionContext) {
        this.getPrincipal().throwIfNotHavingPrivilege(DqoPermissionGrantedAuthorities.OPERATE);

        ExecutionContext executionContext = this.executionContextFactory.create();
        StatisticsCollectionExecutionSummary statisticsCollectionExecutionSummary = this.statisticsCollectorsExecutionService.executeStatisticsCollectorsOnTable(
                executionContext,
                this.parameters.getConnection(),
                this.parameters.getTable(),
                this.parameters.getStatisticsCollectorSearchFilters(),
                this.parameters.getProgressListener(),
                this.parameters.getDataScope(),
                this.parameters.isDummySensorExecution(),
                jobExecutionContext.getCancellationToken());

        if (statisticsCollectionExecutionSummary.getAllChecksFailed()) {
            String firstConnectionName = statisticsCollectionExecutionSummary.getFirstConnectionName();
            String firstTableName = statisticsCollectionExecutionSummary.getFirstTableName();
            String firstErrorMessage = statisticsCollectionExecutionSummary.getFirstException() != null ?
                    statisticsCollectionExecutionSummary.getFirstException().getMessage() : "";
            throw new DqoStatisticsCollectionJobFailedException("Cannot collect statistics on the table " + firstTableName +
                    " on the connection " + firstConnectionName + ", the first error: " + firstErrorMessage,
                    statisticsCollectionExecutionSummary.getFirstException());
        }

        CollectStatisticsResult collectStatisticsResult =
                CollectStatisticsResult.fromStatisticsExecutionSummary(statisticsCollectionExecutionSummary);
        CollectStatisticsOnTableQueueJobParameters clonedParameters = this.getParameters().clone();
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
        return DqoJobType.collect_statistics_on_table;
    }

    /**
     * Returns a concurrency constraint that will limit the number of parallel running jobs.
     * Return null when the job has no concurrency limits (an unlimited number of jobs can run at the same time).
     *
     * @return Optional concurrency constraint that limits the number of parallel jobs or null, when no limits are required.
     */
    @Override
    public JobConcurrencyConstraint[] getConcurrencyConstraints() {
        Integer maxJobsPerConnection = this.parameters.getMaxJobsPerConnection();

        if (maxJobsPerConnection == null) {
            return null; // no limits
        }

        return new JobConcurrencyConstraint[] {
                new JobConcurrencyConstraint(
                        new JobConcurrencyTarget(ConcurrentJobType.RUN_SENSORS_ON_CONNECTION, this.parameters.getConnection()),
                        maxJobsPerConnection)
        };
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
            setCollectStatisticsOnTableParameters(parameters);
        }};
    }
}
