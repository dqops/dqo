/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */

package com.dqops.execution.errorsampling;

import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.data.errorsamples.factory.ErrorSamplesDataScope;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionProgressListener;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHome;

import java.time.LocalDateTime;

/**
 * Error sampler execution service that collects error samples on a single table.
 */
public interface TableErrorSamplerExecutionService {
    /**
     * Execute error samplers on a single table.
     *
     * @param executionContext            Execution context with access to the user home and dqo home.
     * @param userHome                    User home with all metadata and checks.
     * @param connectionWrapper           Target connection.
     * @param targetTable                 Target table.
     * @param checkSearchFilters          Check search filters for which we are collecting error samples.
     * @param userTimeWindowFilters       Optional user provided time filter to limit the samples time range.
     * @param progressListener            Progress listener.
     * @param dummySensorExecution        When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param errorSamplingSessionStartAt Timestamp when the error sampling collection session started. All collected error samples results will be saved with the same timestamp.
     * @param errorSamplesDataScope       Error sampler scope to analyze - the whole table or each data stream separately.
     * @param jobCancellationToken        Job cancellation token, used to detect if the job should be cancelled.
     * @return Table level error sampling summary.
     */
    ErrorSamplerExecutionSummary captureErrorSamplesOnTable(ExecutionContext executionContext,
                                                            UserHome userHome,
                                                            ConnectionWrapper connectionWrapper,
                                                            TableWrapper targetTable,
                                                            CheckSearchFilters checkSearchFilters,
                                                            TimeWindowFilterParameters userTimeWindowFilters,
                                                            ErrorSamplerExecutionProgressListener progressListener,
                                                            boolean dummySensorExecution,
                                                            LocalDateTime errorSamplingSessionStartAt,
                                                            ErrorSamplesDataScope errorSamplesDataScope,
                                                            JobCancellationToken jobCancellationToken);
}
