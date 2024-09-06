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

package com.dqops.services.check.mining;

import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.sources.TableSpec;

/**
 * Service that loads the results from profiling checks and statistics, to be used for data quality rule mining (suggesting the rule thresholds for checks).
 */
public interface TableProfilingResultsReadService {
    /**
     * Loads all profiling results and statistics for a table. The data is organized by check names.
     *
     * @param executionContext Execution context with access to the user home.
     * @param connectionSpec   Connection specification.
     * @param tableSpec        Table specification of the table that is analyzed.
     * @param importStatistics Import statistics to be used by the rule miner. Without the statistics, the miner can only configure current checks or copy profiling checks.
     * @param importDefaultChecks Imports the results of default checks. When we disable it, the rule miner will not see their results and will not propose configuring them. It is important when configuring the monitoring and partition checks to not copy them.
     * @return All loaded results for a table.
     */
    TableProfilingResults loadTableProfilingResults(ExecutionContext executionContext,
                                                    ConnectionSpec connectionSpec,
                                                    TableSpec tableSpec,
                                                    boolean importStatistics,
                                                    boolean importDefaultChecks);
}
