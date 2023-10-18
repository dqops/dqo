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

import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.statistics.factory.StatisticsDataScope;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;

/**
 * Statistics collector execution service. Executes statistics collectors on tables and columns.
 */
public interface StatisticsCollectorsExecutionService {
    /**
     * Executes data statistics collectors on tables and columns. Reports progress and saves the results.
     *
     * @param executionContext      Check/collector execution context with access to the user home and dqo home.
     * @param statisticsCollectorSearchFilters Collector search filters to find the right checks.
     * @param progressListener      Progress listener that receives progress calls.
     * @param statisticsDataScope Collector data scope to analyze - the whole table or each data stream separately.
     * @param dummySensorExecution  When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param startChildJobsPerTable True - starts parallel jobs per table, false - runs all collectors without starting additional jobs.
     * @param parentJobId Parent job id.
     * @param jobCancellationToken Job cancellation token, used to detect if the job should be cancelled.
     * @param principal Principal that will be used to run the job.
     * @return Collector summary table with the count of executed and successful profile executions for each table.
     */
    StatisticsCollectionExecutionSummary executeStatisticsCollectors(ExecutionContext executionContext,
                                                                     StatisticsCollectorSearchFilters statisticsCollectorSearchFilters,
                                                                     StatisticsCollectorExecutionProgressListener progressListener,
                                                                     StatisticsDataScope statisticsDataScope,
                                                                     boolean dummySensorExecution,
                                                                     boolean startChildJobsPerTable,
                                                                     DqoQueueJobId parentJobId,
                                                                     JobCancellationToken jobCancellationToken,
                                                                     DqoUserPrincipal principal);

    /**
     * Executes data statistics collectors on a single table (as a child job). Reports progress and saves the results.
     *
     * @param executionContext      Check/collector execution context with access to the user home and dqo home.
     * @param connectionName Connection name on which the checks are executed.
     * @param targetTable Full name of the target table.
     * @param statisticsCollectorSearchFilters Collector search filters to find the right checks.
     * @param progressListener      Progress listener that receives progress calls.
     * @param statisticsDataScope Collector data scope to analyze - the whole table or each data stream separately.
     * @param dummySensorExecution  When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken Job cancellation token, used to detect if the job should be cancelled.
     * @return Collector summary table with the count of executed and successful profile executions for the selected table. The result should have 0 or 1 rows.
     */
    StatisticsCollectionExecutionSummary executeStatisticsCollectorsOnTable(ExecutionContext executionContext,
                                                                            String connectionName,
                                                                            PhysicalTableName targetTable,
                                                                            StatisticsCollectorSearchFilters statisticsCollectorSearchFilters,
                                                                            StatisticsCollectorExecutionProgressListener progressListener,
                                                                            StatisticsDataScope statisticsDataScope,
                                                                            boolean dummySensorExecution,
                                                                            JobCancellationToken jobCancellationToken);
}
