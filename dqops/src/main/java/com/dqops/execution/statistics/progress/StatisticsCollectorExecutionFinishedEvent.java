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
