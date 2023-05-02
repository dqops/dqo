/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.execution.statistics;

import ai.dqo.data.statistics.factory.StatisticsDataScope;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import ai.dqo.metadata.search.StatisticsCollectorSearchFilters;

/**
 * Statistics collector execution service. Executes statistics collectors on tables and columns.
 */
public interface StatisticsCollectorsExecutionService {
    /**
     * Executes data statistics collectors on tables and columns. Reports progress and saves the results.
     *
     * @param executionContext      Check/collector execution context with access to the user home and dqo home.
     * @param statisticsCollectorSearchFilters Collector search filters to find the right checks.
     * @param progressListener      Progress listener that receives progress calls.
     * @param statisticsDataScope Collector data scope to analyze - the whole table or each data stream separately.
     * @param dummySensorExecution  When true, the sensor is not executed and dummy results are returned. Dummy run will report progress and show a rendered template, but will not touch the target system.
     * @return Collector summary table with the count of executed and successful profile executions for each table.
     */
    StatisticsCollectionExecutionSummary executeStatisticsCollectors(ExecutionContext executionContext,
                                                                     StatisticsCollectorSearchFilters statisticsCollectorSearchFilters,
                                                                     StatisticsCollectorExecutionProgressListener progressListener,
                                                                     StatisticsDataScope statisticsDataScope,
                                                                     boolean dummySensorExecution);
}
