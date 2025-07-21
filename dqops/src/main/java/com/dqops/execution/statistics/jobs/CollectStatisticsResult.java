/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.statistics.jobs;

import com.dqops.execution.statistics.StatisticsCollectionExecutionSummary;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Returns the result with the summary of the statistics collected.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "CollectStatisticsResult", description = "Returns the result with the summary of the statistics collected.")
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class CollectStatisticsResult {
    // TODO: How are these two counts different?
    /**
     * The total count of all executed statistics collectors.
     */
    @JsonPropertyDescription("The total count of all executed statistics collectors.")
    private int executedStatisticsCollectors;

    /**
     * The count of columns for which DQOps executed a collector and tried to read the statistics.
     */
    @JsonPropertyDescription("The count of columns for which DQOps executed a collector and tried to read the statistics.")
    private int columnsAnalyzed;

    /**
     * The count of columns for which DQOps managed to obtain statistics.
     */
    @JsonPropertyDescription("The count of columns for which DQOps managed to obtain statistics.")
    private int columnsSuccessfullyAnalyzed;

    /**
     * The count of statistics collectors that failed to execute.
     */
    @JsonPropertyDescription("The count of statistics collectors that failed to execute.")
    private int totalCollectorsFailed;

    /**
     * The total number of results that were collected.
     */
    @JsonPropertyDescription("The total number of results that were collected.")
    private int totalCollectedResults;

    /**
     * The default parameterless constructor.
     */
    public CollectStatisticsResult() {
    }

    /**
     * Creates a collect statistics job result from the execution summary.
     * @param statisticsCollectionExecutionSummary Statistics collection execution summary.
     * @return The job result object.
     */
    public static CollectStatisticsResult fromStatisticsExecutionSummary(StatisticsCollectionExecutionSummary statisticsCollectionExecutionSummary) {
        CollectStatisticsResult runChecksQueueJobResult = new CollectStatisticsResult() {{
            setExecutedStatisticsCollectors(statisticsCollectionExecutionSummary.getTotalCollectorsExecuted());
            setColumnsAnalyzed(statisticsCollectionExecutionSummary.getColumnsAnalyzedCount());
            setColumnsSuccessfullyAnalyzed(statisticsCollectionExecutionSummary.getColumnsSuccessfullyAnalyzed());
            setTotalCollectorsFailed(statisticsCollectionExecutionSummary.getTotalCollectorsFailed());
            setTotalCollectedResults(statisticsCollectionExecutionSummary.getTotalCollectedResults());
        }};

        return runChecksQueueJobResult;
    }

    public static class CollectStatisticsResultSampleFactory implements SampleValueFactory<CollectStatisticsResult> {
        @Override
        public CollectStatisticsResult createSample() {
            // Columns analyzed, 1*3 + 2*2 + 4*1
            // One of the sensors failed for a 3 statistics column.
            // One for one of the two 2-statistics columns.
            // One for one of the three 1-statistic columns.
            return new CollectStatisticsResult() {{
                setExecutedStatisticsCollectors(11);
                setColumnsAnalyzed(7);
                setColumnsSuccessfullyAnalyzed(4);
                setTotalCollectorsFailed(3);
                setTotalCollectedResults(8);
            }};
        }
    }
}
