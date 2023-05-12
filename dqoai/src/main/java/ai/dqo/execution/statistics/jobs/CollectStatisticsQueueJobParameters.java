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
package ai.dqo.execution.statistics.jobs;

import ai.dqo.data.statistics.factory.StatisticsDataScope;
import ai.dqo.execution.statistics.progress.StatisticsCollectorExecutionProgressListener;
import ai.dqo.metadata.search.StatisticsCollectorSearchFilters;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

/**
 * Parameters object for the run the statistics collection job.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectStatisticsQueueJobParameters {
    /**
     * Statistics collectors search filters that identify the type of statistics collector to run.
     */
    @JsonPropertyDescription("Statistics collectors search filters that identify the type of statistics collector to run.")
    private StatisticsCollectorSearchFilters statisticsCollectorSearchFilters;

    /**
     * The target scope of collecting statistics. Statistics could be collected on a whole table or for each data stream separately.
     */
    @JsonPropertyDescription("The target scope of collecting statistics. Statistics could be collected on a whole table or for each data stream separately.")
    private StatisticsDataScope dataScope = StatisticsDataScope.table;

    /**
     * Progress listener that will receive events during the statistics collection.
     */
    @JsonIgnore
    private StatisticsCollectorExecutionProgressListener progressListener;

    /**
     * Boolean flag that enables a dummy statistics collection (sensors are executed, but the statistics results are not written to the parquet files).
     */
    @JsonPropertyDescription("Boolean flag that enables a dummy statistics collection (sensors are executed, but the statistics results are not written to the parquet files).")
    private boolean dummySensorExecution;

    public CollectStatisticsQueueJobParameters() {
    }

    /**
     * Creates statistics collection run parameters.
     * @param statisticsCollectorSearchFilters Statistics collector search filters.
     * @param progressListener Progress listener to receive events during the statistics collector execution.
     * @param dataScope Statistics data scope to analyze - the whole table or each data stream separately.
     * @param dummySensorExecution True when it is a dummy run, only for showing rendered sensor queries.
     */
    public CollectStatisticsQueueJobParameters(StatisticsCollectorSearchFilters statisticsCollectorSearchFilters,
                                               StatisticsCollectorExecutionProgressListener progressListener,
                                               StatisticsDataScope dataScope,
                                               boolean dummySensorExecution) {
        this.statisticsCollectorSearchFilters = statisticsCollectorSearchFilters;
        this.progressListener = progressListener;
        this.dataScope = dataScope;
        this.dummySensorExecution = dummySensorExecution;
    }

    /**
     * Returns the statistics collectors search filters.
     * @return Statistics collectors search filters.
     */
    public StatisticsCollectorSearchFilters getStatisticsCollectorSearchFilters() {
        return statisticsCollectorSearchFilters;
    }

    /**
     * Progress listener that should be used to run the statistics collectors.
     * @return Statistics collectors progress listener.
     */
    public StatisticsCollectorExecutionProgressListener getProgressListener() {
        return progressListener;
    }

    /**
     * Returns the statistics data scope (whole table or each data stream separately).
     * @return Data scope configuration.
     */
    public StatisticsDataScope getDataScope() {
        return dataScope;
    }

    /**
     * Returns true if it should be just a dummy run without actually running any queries.
     * @return true when it is a dummy run.
     */
    public boolean isDummySensorExecution() {
        return dummySensorExecution;
    }
}
