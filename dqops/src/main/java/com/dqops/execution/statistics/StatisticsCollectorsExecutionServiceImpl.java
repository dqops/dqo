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
package com.dqops.execution.statistics;

import com.dqops.core.jobqueue.*;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.statistics.factory.StatisticsDataScope;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.statistics.jobs.CollectStatisticsOnTableQueueJob;
import com.dqops.execution.statistics.jobs.CollectStatisticsOnTableQueueJobParameters;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionFinishedEvent;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.userhome.UserHome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Statistics collectors execution service. Executes statistics collectors on tables and columns.
 */
@Service
@Slf4j
public class StatisticsCollectorsExecutionServiceImpl implements StatisticsCollectorsExecutionService {
    private HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher;
    private DqoQueueJobFactory dqoQueueJobFactory;
    private DqoJobQueue dqoJobQueue;
    private TableStatisticsCollectorsExecutionService tableStatisticsCollectorsExecutionService;

    /**
     * Creates a statistics collectors execution service with given dependencies.
     * @param hierarchyNodeTreeSearcher Target node search service.
     * @param dqoQueueJobFactory DQOps job factory to create a child job.
     * @param dqoJobQueue DQOps job queue where the child job is started.
     * @param tableStatisticsCollectorsExecutionService Statistics collector execution service that collects statistics on a single table.
     */
    @Autowired
    public StatisticsCollectorsExecutionServiceImpl(HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher,
                                                    DqoQueueJobFactory dqoQueueJobFactory,
                                                    DqoJobQueue dqoJobQueue,
                                                    TableStatisticsCollectorsExecutionService tableStatisticsCollectorsExecutionService) {
        this.hierarchyNodeTreeSearcher = hierarchyNodeTreeSearcher;
        this.dqoQueueJobFactory = dqoQueueJobFactory;
        this.dqoJobQueue = dqoJobQueue;
        this.tableStatisticsCollectorsExecutionService = tableStatisticsCollectorsExecutionService;
    }

    /**
     * Executes data statistics collectors on tables and columns. Reports progress and saves the results.
     * @param executionContext Check/collector execution context with access to the user home and dqo home.
     * @param statisticsCollectorSearchFilters Statistics collector search filters to find the right checks.
     * @param progressListener Progress listener that receives progress calls.
     * @param statisticsDataScope Collector data scope to analyze - the whole table or each data stream separately.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param startChildJobsPerTable True - starts parallel jobs per table, false - runs all collectors without starting additional jobs.
     * @param parentJobId Parent job id.
     * @param jobCancellationToken Job cancellation token, used to detect if the job should be cancelled.
     * @param principal Principal that will be used to run the job.
     * @return Statistics collector summary table with the count of executed and successful collectors executions for each table.
     */
    @Override
    public StatisticsCollectionExecutionSummary executeStatisticsCollectors(ExecutionContext executionContext,
                                                                            StatisticsCollectorSearchFilters statisticsCollectorSearchFilters,
                                                                            StatisticsCollectorExecutionProgressListener progressListener,
                                                                            StatisticsDataScope statisticsDataScope,
                                                                            boolean dummySensorExecution,
                                                                            boolean startChildJobsPerTable,
                                                                            DqoQueueJobId parentJobId,
                                                                            JobCancellationToken jobCancellationToken,
                                                                            DqoUserPrincipal principal) {
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();
        Collection<TableWrapper> targetTables = listTargetTables(userHome, statisticsCollectorSearchFilters);
        StatisticsCollectionExecutionSummary statisticsCollectorExecutionSummary = null;
        LocalDateTime profilingSessionStartAt = LocalDateTime.now();
        jobCancellationToken.throwIfCancelled();

        if (startChildJobsPerTable) {
            List<DqoQueueJob<StatisticsCollectionExecutionSummary>> childTableJobs = new ArrayList<>();

            for (TableWrapper targetTable : targetTables) {
                ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetTable.getHierarchyId());

                CollectStatisticsOnTableQueueJobParameters runChecksOnTableQueueJobParameters = new CollectStatisticsOnTableQueueJobParameters() {{
                    setConnection(connectionWrapper.getName());
                    setMaxJobsPerConnection(connectionWrapper.getSpec().getParallelRunsLimit());
                    setTable(targetTable.getPhysicalTableName());
                    setStatisticsCollectorSearchFilters(statisticsCollectorSearchFilters);
                    setDataScope(statisticsDataScope);
                    setProgressListener(progressListener);
                    setDummySensorExecution(dummySensorExecution);
                }};
                CollectStatisticsOnTableQueueJob collectStatisticsOnTableQueueJob = this.dqoQueueJobFactory.creteCollectStatisticsOnTableJob();
                collectStatisticsOnTableQueueJob.setParameters(runChecksOnTableQueueJobParameters);
                childTableJobs.add(collectStatisticsOnTableQueueJob);
            }

            ChildDqoQueueJobsContainer<StatisticsCollectionExecutionSummary> childTableJobsContainer = this.dqoJobQueue.pushChildJobs(childTableJobs, parentJobId, principal);
            List<StatisticsCollectionExecutionSummary> collectorExecutionSummaries = childTableJobsContainer.waitForChildResults(jobCancellationToken);

            StatisticsCollectionExecutionSummary executionSummaryForMerge = new StatisticsCollectionExecutionSummary();
            collectorExecutionSummaries.forEach(tableSummary -> executionSummaryForMerge.append(tableSummary));
            statisticsCollectorExecutionSummary = executionSummaryForMerge;
        }
        else {
            for (TableWrapper targetTable : targetTables) {
                ConnectionWrapper connectionWrapper = userHome.findConnectionFor(targetTable.getHierarchyId());
                statisticsCollectorExecutionSummary = this.tableStatisticsCollectorsExecutionService.executeCollectorsOnTable(executionContext, userHome,
                        connectionWrapper, targetTable, statisticsCollectorSearchFilters, progressListener,
                        dummySensorExecution, profilingSessionStartAt, statisticsDataScope, jobCancellationToken);
            }
        }

        progressListener.onCollectorsExecutionFinished(new StatisticsCollectorExecutionFinishedEvent(statisticsCollectorExecutionSummary));
        return statisticsCollectorExecutionSummary;
    }

    /**
     * Executes data statistics collectors on a single table (as a child job). Reports progress and saves the results.
     *
     * @param executionContext                 Check/collector execution context with access to the user home and dqo home.
     * @param connectionName                   Connection name on which the checks are executed.
     * @param targetTable                      Full name of the target table.
     * @param statisticsCollectorSearchFilters Collector search filters to find the right checks.
     * @param progressListener                 Progress listener that receives progress calls.
     * @param statisticsDataScope              Collector data scope to analyze - the whole table or each data stream separately.
     * @param dummySensorExecution             When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken             Job cancellation token, used to detect if the job should be cancelled.
     * @return Collector summary table with the count of executed and successful profile executions for the selected table. The result should have 0 or 1 rows.
     */
    @Override
    public StatisticsCollectionExecutionSummary executeStatisticsCollectorsOnTable(ExecutionContext executionContext,
                                                                                   String connectionName,
                                                                                   PhysicalTableName targetTable,
                                                                                   StatisticsCollectorSearchFilters statisticsCollectorSearchFilters,
                                                                                   StatisticsCollectorExecutionProgressListener progressListener,
                                                                                   StatisticsDataScope statisticsDataScope,
                                                                                   boolean dummySensorExecution,
                                                                                   JobCancellationToken jobCancellationToken) {
        UserHome userHome = executionContext.getUserHomeContext().getUserHome();

        ConnectionWrapper connectionWrapper = userHome.getConnections().getByObjectName(connectionName, true);
        if (connectionWrapper == null) {
            return new StatisticsCollectionExecutionSummary(); // the connection was probably deleted in the meantime
        }

        TableWrapper tableWrapper = connectionWrapper.getTables().getByObjectName(targetTable, true);
        if (tableWrapper == null) {
            return new StatisticsCollectionExecutionSummary(); // the table was probably deleted in the meantime
        }

        LocalDateTime profilingSessionStartAt = LocalDateTime.now();
        StatisticsCollectionExecutionSummary statisticsCollectionExecutionSummary =
                this.tableStatisticsCollectorsExecutionService.executeCollectorsOnTable(executionContext, userHome,
                        connectionWrapper, tableWrapper, statisticsCollectorSearchFilters, progressListener,
                        dummySensorExecution, profilingSessionStartAt,statisticsDataScope, jobCancellationToken);

        return statisticsCollectionExecutionSummary;
    }

    /**
     * Lists all target tables that were not excluded from the filter and may have statistics collectors to be executed.
     * @param userHome User home.
     * @param statisticsCollectorSearchFilters Statistics collectors search filter.
     * @return Collection of table wrappers.
     */
    public Collection<TableWrapper> listTargetTables(UserHome userHome, StatisticsCollectorSearchFilters statisticsCollectorSearchFilters) {
        Collection<TableWrapper> tables = this.hierarchyNodeTreeSearcher.findTables(userHome.getConnections(), statisticsCollectorSearchFilters);
        return tables;
    }
}
