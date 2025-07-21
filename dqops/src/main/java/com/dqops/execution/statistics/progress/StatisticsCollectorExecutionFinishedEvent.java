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

import com.dqops.execution.statistics.StatisticsCollectionExecutionSummary;

/**
 * Progress event raised after all statistics collectors were executed. Returns the summary.
 */
public class StatisticsCollectorExecutionFinishedEvent extends StatisticsCollectorsExecutionProgressEvent {
    private final StatisticsCollectionExecutionSummary collectorsExecutionSummary;

    /**
     * Creates a progress event.
     *
     * @param collectorsExecutionSummary  Summary of all executed statistics collectors.
     */
    public StatisticsCollectorExecutionFinishedEvent(StatisticsCollectionExecutionSummary collectorsExecutionSummary) {
        this.collectorsExecutionSummary = collectorsExecutionSummary;
    }

    /**
     * Returns a summary of executed statistics collectors.
     * @return Summary of executed statistics collectors.
     */
    public StatisticsCollectionExecutionSummary getCollectorsExecutionSummary() {
        return collectorsExecutionSummary;
    }
}
