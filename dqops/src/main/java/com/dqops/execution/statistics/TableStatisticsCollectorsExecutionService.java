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

import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.data.statistics.factory.StatisticsDataScope;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import com.dqops.metadata.search.StatisticsCollectorSearchFilters;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHome;

import java.time.LocalDateTime;

/**
 * Statistics collectors execution service that collects basic profiling statistics on a single table.
 */
public interface TableStatisticsCollectorsExecutionService {
    /**
     * Execute statistics collectors on a single table.
     *
     * @param executionContext                 Execution context with access to the user home and dqo home.
     * @param userHome                         User home with all metadata and checks.
     * @param connectionWrapper                Target connection.
     * @param targetTable                      Target table.
     * @param statisticsCollectorSearchFilters Statistics collectors search filters.
     * @param progressListener                 Progress listener.
     * @param dummySensorExecution             When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param collectionSessionStartAt         Timestamp when the statistics collection session started. All statistics results will be saved with the same timestamp.
     * @param statisticsDataScope              Collector data scope to analyze - the whole table or each data stream separately.
     * @param samplesLimit                     The limit of column samples to capture.
     * @param configureTable                   True when the table should be configured (timestamp and ID columns).
     * @param jobCancellationToken             Job cancellation token, used to detect if the job should be cancelled.
     * @return Table level statistics.
     */
    StatisticsCollectionExecutionSummary executeCollectorsOnTable(ExecutionContext executionContext,
                                                                  UserHome userHome,
                                                                  ConnectionWrapper connectionWrapper,
                                                                  TableWrapper targetTable,
                                                                  StatisticsCollectorSearchFilters statisticsCollectorSearchFilters,
                                                                  StatisticsCollectorExecutionProgressListener progressListener,
                                                                  boolean dummySensorExecution,
                                                                  LocalDateTime collectionSessionStartAt,
                                                                  StatisticsDataScope statisticsDataScope,
                                                                  Integer samplesLimit,
                                                                  boolean configureTable,
                                                                  JobCancellationToken jobCancellationToken);
}
