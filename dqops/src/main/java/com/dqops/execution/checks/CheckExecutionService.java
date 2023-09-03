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
package com.dqops.execution.checks;

import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.checks.progress.CheckExecutionProgressListener;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.scheduling.MonitoringScheduleSpec;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;

/**
 * Service that executes data quality checks.
 */
public interface CheckExecutionService {
    /**
     * Executes data quality checks. Reports progress and saves the results.
     * @param executionContext Check execution context with access to the user home and dqo home.
     * @param checkSearchFilters Check search filters to find the right checks.
     * @param userTimeWindowFilters Optional user provided time window filters to restrict the range of dates that are analyzed.
     * @param progressListener Progress listener that receives progress calls.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param startChildJobsPerTable True - starts parallel jobs per table, false - runs all checks without starting additional jobs.
     * @param parentJobId Parent job id.
     * @param jobCancellationToken Job cancellation token.
     * @param principal Principal that will be used to run the job.
     * @return Check summary table with the count of alerts, checks and rules for each table.
     */
    CheckExecutionSummary executeChecks(ExecutionContext executionContext,
                                        CheckSearchFilters checkSearchFilters,
                                        TimeWindowFilterParameters userTimeWindowFilters,
                                        CheckExecutionProgressListener progressListener,
                                        boolean dummySensorExecution,
                                        boolean startChildJobsPerTable,
                                        DqoQueueJobId parentJobId,
                                        JobCancellationToken jobCancellationToken,
                                        DqoUserPrincipal principal);

    /**
     * Executes scheduled data quality checks. A list of checks divided by tables must be provided.
     * @param executionContext Check execution context with access to the user home and dqo home.
     * @param targetSchedule Target schedule to match, when finding checks that should be executed.
     * @param progressListener Progress listener that receives progress calls.
     * @param parentJobId Parent job id.
     * @param jobCancellationToken Job cancellation token.
     * @param principal Principal that will be used to run the job.
     * @return Check summary table with the count of alerts, checks and rules for each table.
     */
    CheckExecutionSummary executeChecksForSchedule(ExecutionContext executionContext,
                                                   MonitoringScheduleSpec targetSchedule,
                                                   CheckExecutionProgressListener progressListener,
                                                   DqoQueueJobId parentJobId,
                                                   JobCancellationToken jobCancellationToken,
                                                   DqoUserPrincipal principal);

    /**
     * Executes selected checks on a table. This method is called from {@link com.dqops.execution.checks.jobs.RunChecksOnTableQueueJob}
     * that is a child job which runs checks for each table in parallel.
     * @param executionContext Execution context that provides access to the user home.
     * @param connectionName Connection name on which the checks are executed.
     * @param targetTable Full name of the target table.
     * @param checkSearchFilters Check search filters, may not specify the connection and the table name.
     * @param userTimeWindowFilters Optional user provided time window filters to restrict the range of dates that are analyzed.
     * @param progressListener Progress listener that receives progress calls.
     * @param dummySensorExecution When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken Job cancellation token.
     * @return Check summary table with the count of alerts, checks and rules for each table, but having only one row for the target table. The result could be empty if the table was not found.
     */
    CheckExecutionSummary executeSelectedChecksOnTable(ExecutionContext executionContext,
                                                       String connectionName,
                                                       PhysicalTableName targetTable,
                                                       CheckSearchFilters checkSearchFilters,
                                                       TimeWindowFilterParameters userTimeWindowFilters,
                                                       CheckExecutionProgressListener progressListener,
                                                       boolean dummySensorExecution,
                                                       JobCancellationToken jobCancellationToken);
}
