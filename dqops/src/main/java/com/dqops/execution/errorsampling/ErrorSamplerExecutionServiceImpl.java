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
package com.dqops.execution.errorsampling;

import com.dqops.core.jobqueue.*;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.errorsamples.factory.ErrorSamplesDataScope;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.errorsampling.jobs.CollectErrorSamplesOnTableQueueJob;
import com.dqops.execution.errorsampling.jobs.CollectErrorSamplesOnTableParameters;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionProgressListener;
import com.dqops.execution.errorsampling.progress.ErrorSamplersExecutionFinishedEvent;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Error samples collectors (error sampler) execution service. Executes error sampling queries on tables and columns.
 */
@Service
@Slf4j
public class ErrorSamplerExecutionServiceImpl implements ErrorSamplerExecutionService {
    private HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private DqoJobQueue dqoJobQueue;
    private TableErrorSamplerExecutionService tableErrorSamplerExecutionService;

    /**
     * Creates an error sampler execution service with given dependencies.
     * @param hierarchyNodeTreeSearcher Target node search service.
     * @param dqoQueueJobFactory DQOps job factory to create a child job.
     * @param dqoJobQueue DQOps job queue where the child job is started.
     * @param tableErrorSamplerExecutionService Error sampler execution service that collects error samples on a single table.
     */
    @Autowired
    public ErrorSamplerExecutionServiceImpl(HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                                            DqoQueueJobFactory dqoQueueJobFactory,
                                            DqoJobQueue dqoJobQueue,
                                            TableErrorSamplerExecutionService tableErrorSamplerExecutionService) {
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
        this.tableErrorSamplerExecutionService = tableErrorSamplerExecutionService;
    }

    /**
     * Executes error samplers on tables and columns. Reports progress and saves the results.
     * @param executionContext Check execution context with access to the user home and dqo home.
     * @param checkSearchFilters Statistics collector search filters to find the right checks.
     * @param userTimeWindowFilters Optional, user provided time window filters to limit the time range of analyzed rows.
     * @param progressListener Progress listener that receives progress calls.
     * @param errorSamplesDataScope Errors samples data scope to analyze - the whole table or each data stream separately.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param startChildJobsPerTable True - starts parallel jobs per table, false - runs all collectors without starting additional jobs.
     * @param parentJobId Parent job id.
     * @param jobCancellationToken Job cancellation token, used to detect if the job should be cancelled.
     * @param principal Principal that will be used to run the job.
     * @return Error sampler summary table with the count of executed and successful error sampler executions for each table.
     */
    @Override
    public ErrorSamplerExecutionSummary executeStatisticsCollectors(ExecutionContext executionContext,
                                                                    CheckSearchFilters checkSearchFilters,
                                                                    TimeWindowFilterParameters userTimeWindowFilters,
                                                                    ErrorSamplerExecutionProgressListener progressListener,
                                                                    ErrorSamplesDataScope errorSamplesDataScope,
                                                                    boolean dummySensorExecution,
                                                                    boolean startChildJobsPerTable,
                                                                    DqoQueueJobId parentJobId,
                                                                    JobCancellationToken jobCancellationToken,
                                                                    DqoUserPrincipal principal) {
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();
        Collection<TableWrapper> targetTables = listTargetTables(userHome, checkSearchFilters);
        ErrorSamplerExecutionSummary errorSamplerExecutionSummary = null;
        LocalDateTime errorSamplingSessionStartAt = LocalDateTime.now();
        jobCancellationToken.throwIfCancelled();

        if (startChildJobsPerTable) {
            List<DqoQueueJob<ErrorSamplerExecutionSummary>> childTableJobs = new ArrayList<>();

            for (TableWrapper targetTable : targetTables) {
                ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetTable.getHierarchyId());

                CollectErrorSamplesOnTableParameters runErrorSamplersOnTableQueueJobParameters = new CollectErrorSamplesOnTableParameters() {{
                    setConnection(connectionWrapper.getName());
                    setMaxJobsPerConnection(connectionWrapper.getSpec().getParallelJobsLimit());
                    setTable(targetTable.getPhysicalTableName());
                    setCheckSearchFilters(checkSearchFilters);
                    setTimeWindowFilter(userTimeWindowFilters);
                    setDataScope(errorSamplesDataScope);
                    setProgressListener(progressListener);
                    setDummySensorExecution(dummySensorExecution);
                }};
                CollectErrorSamplesOnTableQueueJob collectErrorSamplesOnTableQueueJob = this.dqoQueueJobFactory.createCollectErrorSamplesOnTableJob();
                collectErrorSamplesOnTableQueueJob.setParameters(runErrorSamplersOnTableQueueJobParameters);
                childTableJobs.add(collectErrorSamplesOnTableQueueJob);
            }

            ChildDqoQueueJobsContainer<ErrorSamplerExecutionSummary> childTableJobsContainer = this.dqoJobQueue.pushChildJobs(childTableJobs, parentJobId, principal);
            List<ErrorSamplerExecutionSummary> collectorExecutionSummaries = childTableJobsContainer.waitForChildResults(jobCancellationToken);

            ErrorSamplerExecutionSummary executionSummaryForMerge = new ErrorSamplerExecutionSummary();
            collectorExecutionSummaries.forEach(tableSummary -> executionSummaryForMerge.append(tableSummary));
            errorSamplerExecutionSummary = executionSummaryForMerge;
        }
        else {
            for (TableWrapper targetTable : targetTables) {
                ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetTable.getHierarchyId());
                errorSamplerExecutionSummary = this.tableErrorSamplerExecutionService.captureErrorSamplesOnTable(executionContext, userHome,
                        connectionWrapper, targetTable, checkSearchFilters, userTimeWindowFilters, progressListener,
                        dummySensorExecution, errorSamplingSessionStartAt, errorSamplesDataScope, jobCancellationToken);
            }
        }

        progressListener.onErrorSamplersExecutionFinished(new ErrorSamplersExecutionFinishedEvent(errorSamplerExecutionSummary));
        return errorSamplerExecutionSummary;
    }

    /**
     * Executes data statistics collectors on a single table (as a child job). Reports progress and saves the results.
     *
     * @param executionContext                 Check/collector execution context with access to the user home and dqo home.
     * @param connectionName                   Connection name on which the checks are executed.
     * @param targetTable                      Full name of the target table.
     * @param checkSearchFilters               Check search filters to find the right checks.
     * @param userTimeWindowFilters            Optional user defined time window filter.
     * @param progressListener                 Progress listener that receives progress calls.
     * @param errorSamplesDataScope            Error sampler data scope to analyze - the whole table or each data stream separately.
     * @param dummySensorExecution             When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken             Job cancellation token, used to detect if the job should be cancelled.
     * @return Collector summary table with the count of executed and successful error sampler executions for the selected table. The result should have 0 or 1 rows.
     */
    @Override
    public ErrorSamplerExecutionSummary executeErrorSamplersOnTable(ExecutionContext executionContext,
                                                                    String connectionName,
                                                                    PhysicalTableName targetTable,
                                                                    CheckSearchFilters checkSearchFilters,
                                                                    TimeWindowFilterParameters userTimeWindowFilters,
                                                                    ErrorSamplerExecutionProgressListener progressListener,
                                                                    ErrorSamplesDataScope errorSamplesDataScope,
                                                                    boolean dummySensorExecution,
                                                                    JobCancellationToken jobCancellationToken) {
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();

        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new ErrorSamplerExecutionSummary(); // the connection was probably deleted in the meantime
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(targetTable, true);
        if (tableWrapper == null) {
            return new ErrorSamplerExecutionSummary(); // the table was probably deleted in the meantime
        }

        LocalDateTime profilingSessionStartAt = LocalDateTime.now();
        ErrorSamplerExecutionSummary errorSamplerExecutionSummary =
                this.tableErrorSamplerExecutionService.captureErrorSamplesOnTable(executionContext, userHome,
                        connectionWrapper, tableWrapper, checkSearchFilters, userTimeWindowFilters, progressListener,
                        dummySensorExecution, profilingSessionStartAt, errorSamplesDataScope, jobCancellationToken);

        return errorSamplerExecutionSummary;
    }

    /**
     * Lists all target tables that were not excluded from the filter and may have checks to be executed.
     * @param userHome User home.
     * @param checkSearchFilters Check search filter.
     * @return Collection of table wrappers.
     */
    public Collection<TableWrapper> listTargetTables(UserHome userHome, CheckSearchFilters checkSearchFilters) {
        Collection<TableWrapper> tables = this.hierarchyNodeTreeSearcher.findTables(userHome.getConnections(), checkSearchFilters);
        return tables;
    }
}
