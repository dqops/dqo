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

import com.dqops.core.jobqueue.DqoQueueJobId;
import com.dqops.core.jobqueue.JobCancellationToken;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.data.errorsamples.factory.ErrorSamplesDataScope;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.errorsampling.progress.ErrorSamplerExecutionProgressListener;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.sources.PhysicalTableName;

/**
 * Error samples collectors (error sampler) execution service. Executes error sampling queries on tables and columns.
 */
public interface ErrorSamplerExecutionService {
    /**
     * Executes error samplers on tables and columns. Reports progress and saves the results.
     *
     * @param executionContext       Check execution context with access to the user home and dqo home.
     * @param checkSearchFilters     Statistics collector search filters to find the right checks.
     * @param userTimeWindowFilters  Optional, user provided time window filters to limit the time range of analyzed rows.
     * @param progressListener       Progress listener that receives progress calls.
     * @param errorSamplesDataScope  Errors samples data scope to analyze - the whole table or each data stream separately.
     * @param dummySensorExecution   When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param startChildJobsPerTable True - starts parallel jobs per table, false - runs all collectors without starting additional jobs.
     * @param parentJobId            Parent job id.
     * @param jobCancellationToken   Job cancellation token, used to detect if the job should be cancelled.
     * @param principal              Principal that will be used to run the job.
     * @return Error sampler summary table with the count of executed and successful error sampler executions for each table.
     */
    ErrorSamplerExecutionSummary executeStatisticsCollectors(ExecutionContext executionContext,
                                                             CheckSearchFilters checkSearchFilters,
                                                             TimeWindowFilterParameters userTimeWindowFilters,
                                                             ErrorSamplerExecutionProgressListener progressListener,
                                                             ErrorSamplesDataScope errorSamplesDataScope,
                                                             boolean dummySensorExecution,
                                                             boolean startChildJobsPerTable,
                                                             DqoQueueJobId parentJobId,
                                                             JobCancellationToken jobCancellationToken,
                                                             DqoUserPrincipal principal);

    /**
     * Executes data statistics collectors on a single table (as a child job). Reports progress and saves the results.
     *
     * @param executionContext      Check/collector execution context with access to the user home and dqo home.
     * @param connectionName        Connection name on which the checks are executed.
     * @param targetTable           Full name of the target table.
     * @param checkSearchFilters    Check search filters to find the right checks.
     * @param userTimeWindowFilters Optional user defined time window filter.
     * @param progressListener      Progress listener that receives progress calls.
     * @param errorSamplesDataScope Error sampler data scope to analyze - the whole table or each data stream separately.
     * @param dummySensorExecution  When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @param jobCancellationToken  Job cancellation token, used to detect if the job should be cancelled.
     * @return Collector summary table with the count of executed and successful error sampler executions for the selected table. The result should have 0 or 1 rows.
     */
    ErrorSamplerExecutionSummary executeErrorSamplersOnTable(ExecutionContext executionContext,
                                                             String connectionName,
                                                             PhysicalTableName targetTable,
                                                             CheckSearchFilters checkSearchFilters,
                                                             TimeWindowFilterParameters userTimeWindowFilters,
                                                             ErrorSamplerExecutionProgressListener progressListener,
                                                             ErrorSamplesDataScope errorSamplesDataScope,
                                                             boolean dummySensorExecution,
                                                             JobCancellationToken jobCancellationToken);
}
