/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
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
