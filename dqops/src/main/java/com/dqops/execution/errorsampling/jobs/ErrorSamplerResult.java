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
package com.dqops.execution.errorsampling.jobs;

import com.dqops.execution.errorsampling.ErrorSamplerExecutionSummary;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Returns the result with the summary of the error samples collected.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "ErrorSamplerResult", description = "Returns the result with the summary of the error samples collected.")
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class ErrorSamplerResult {
    /**
     * The total count of all executed error samplers. This count only includes data quality checks that have an error sampling template defined.
     */
    @JsonPropertyDescription("The total count of all executed error samplers. This count only includes data quality checks that have an error sampling template defined. ")
    private int executedErrorSamplers;

    /**
     * The count of columns for which DQOps executed an error sampler and tried to collect error samples.
     */
    @JsonPropertyDescription("The count of columns for which DQOps executed an error sampler and tried to collect error samples.")
    private int columnsAnalyzed;

    /**
     * The count of columns for which DQOps managed to obtain error samples.
     */
    @JsonPropertyDescription("The count of columns for which DQOps managed to obtain error samples.")
    private int columnsSuccessfullyAnalyzed;

    /**
     * The count of error samplers that failed to execute.
     */
    @JsonPropertyDescription("The count of error samplers that failed to execute.")
    private int totalErrorSamplersFailed;

    /**
     * The total number of error samples (values) that were collected.
     */
    @JsonPropertyDescription("The total number of error samples (values) that were collected.")
    private int totalErrorSamplesCollected;

    /**
     * The default parameterless constructor.
     */
    public ErrorSamplerResult() {
    }

    /**
     * Creates a collect error samples job result from the execution summary.
     * @param errorSamplerExecutionSummary Error sampler execution summary.
     * @return The job result object.
     */
    public static ErrorSamplerResult fromErrorSamplerExecutionSummary(ErrorSamplerExecutionSummary errorSamplerExecutionSummary) {
        ErrorSamplerResult runChecksQueueJobResult = new ErrorSamplerResult() {{
            setExecutedErrorSamplers(errorSamplerExecutionSummary.getTotalCollectorsExecuted());
            setColumnsAnalyzed(errorSamplerExecutionSummary.getColumnsAnalyzedCount());
            setColumnsSuccessfullyAnalyzed(errorSamplerExecutionSummary.getColumnsSuccessfullyAnalyzed());
            setTotalErrorSamplersFailed(errorSamplerExecutionSummary.getTotalCollectorsFailed());
            setTotalErrorSamplesCollected(errorSamplerExecutionSummary.getTotalCollectedResults());
        }};

        return runChecksQueueJobResult;
    }

    public static class ErrorSamplerResultResultSampleFactory implements SampleValueFactory<ErrorSamplerResult> {
        @Override
        public ErrorSamplerResult createSample() {
            // Columns analyzed, 1*3 + 2*2 + 4*1
            // One of the sensors failed for a 3 statistics column.
            // One for one of the two 2-statistics columns.
            // One for one of the three 1-statistic columns.
            return new ErrorSamplerResult() {{
                setExecutedErrorSamplers(11);
                setColumnsAnalyzed(7);
                setColumnsSuccessfullyAnalyzed(4);
                setTotalErrorSamplersFailed(3);
                setTotalErrorSamplesCollected(8);
            }};
        }
    }
}
