/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
     * @param samplesLimit The limit of column samples to capture.
     * @param configureTable Configure a table.
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
                                                                     Integer samplesLimit,
                                                                     boolean configureTable,
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
     * @param samplesLimit The limit of column samples to capture.
     * @param configureTable Configure the table.
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
                                                                            Integer samplesLimit,
                                                                            boolean configureTable,
                                                                            boolean dummySensorExecution,
                                                                            JobCancellationToken jobCancellationToken);
}
