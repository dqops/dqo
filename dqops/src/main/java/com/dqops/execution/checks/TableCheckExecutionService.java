/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.checks;

import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.checks.progress.CheckExecutionProgressListener;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHome;

/**
 * Service that executes data quality checks on a single table.
 */
public interface TableCheckExecutionService {
    /**
     * Execute checks on a single table.
     * @param executionContext Check execution context with access to the user home and dqo home.
     * @param userHome User home with all metadata and checks.
     * @param connectionWrapper  Target connection.
     * @param targetTable Target table.
     * @param checkSearchFilters Check search filters.
     * @param userTimeWindowFilters Optional user provided time window filters to restrict the range of dates that are analyzed.
     * @param collectErrorSamples Collect error samples for failed checks. Can disable or enable sample collection independent of other parameters.
     * @param progressListener Progress listener.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param executionTarget Check execution mode (sensors, rules, or both).
     * @param jobCancellationToken Job cancellation token.
     * @return Check execution summary that has an overview information about executed checks.
     */
    CheckExecutionSummary executeChecksOnTable(ExecutionContext executionContext,
                                               UserHome userHome,
                                               ConnectionWrapper connectionWrapper,
                                               TableWrapper targetTable,
                                               CheckSearchFilters checkSearchFilters,
                                               TimeWindowFilterParameters userTimeWindowFilters,
                                               Boolean collectErrorSamples,
                                               CheckExecutionProgressListener progressListener,
                                               boolean dummySensorExecution,
                                               RunChecksTarget executionTarget,
                                               JobCancellationToken jobCancellationToken);
}
