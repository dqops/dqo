/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.statistics.progress;

/**
 * Statistics collectors execution progress listener provider (factory). Returns progress listeners for the given reporting mode.
 */
public interface StatisticsCollectorExecutionProgressListenerProvider {
    /**
     * Returns a statistics collectors execution progress listener for the requested reporting level.
     *
     * @param reportingMode         Reporting level.
     * @param writeSummaryToConsole Write the summary after running the profiler to the console.
     * @return Statistics collectors execution progress listener.
     */
    StatisticsCollectorExecutionProgressListener getProgressListener(StatisticsCollectorExecutionReportingMode reportingMode,
                                                                     boolean writeSummaryToConsole);
}
