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
