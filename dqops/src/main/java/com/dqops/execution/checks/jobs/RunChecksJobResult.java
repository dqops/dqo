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
package com.dqops.execution.checks.jobs;

import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.rules.RuleSeverityLevel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Returns the result (highest data quality check severity and the finished checks count) for the checks that were recently executed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "RunChecksJobResult", description = "Returns the result (highest data quality check severity and the finished checks count) for the checks that were recently executed.")
@EqualsAndHashCode(callSuper = false)
@Data
public class RunChecksJobResult {
    /**
     * The highest check severity for the data quality checks executed in this batch.
     */
    @JsonPropertyDescription("The highest check severity for the data quality checks executed in this batch.")
    private RuleSeverityLevel highestSeverity;

    /**
     * The total count of all executed checks.
     */
    @JsonPropertyDescription("The total count of all executed checks.")
    private int executedChecks;

    /**
     * The total count of all checks that finished successfully (with no data quality issues).
     */
    @JsonPropertyDescription("The total count of all checks that finished successfully (with no data quality issues).")
    private int validResults;

    /**
     * The total count of all invalid data quality checks that finished raising a warning.
     */
    @JsonPropertyDescription("The total count of all invalid data quality checks that finished raising a warning.")
    private int warnings;

    /**
     * The total count of all invalid data quality checks that finished raising an error.
     */
    @JsonPropertyDescription("The total count of all invalid data quality checks that finished raising an error.")
    private int errors;

    /**
     * The total count of all invalid data quality checks that finished raising a fatal error.
     */
    @JsonPropertyDescription("The total count of all invalid data quality checks that finished raising a fatal error.")
    private int fatals;

    /**
     * The total number of checks that failed to execute due to some execution errors.
     */
    @JsonPropertyDescription("The total number of checks that failed to execute due to some execution errors.")
    private int executionErrors;

    /**
     * The default parameterless constructor.
     */
    public RunChecksJobResult() {
    }

    /**
     * Creates a check run job result from the execution summary.
     * @param checkExecutionSummary Check execution summary.
     * @return The job result object.
     */
    public static RunChecksJobResult fromCheckExecutionSummary(CheckExecutionSummary checkExecutionSummary) {
        if (checkExecutionSummary == null) {
            return null;
        }

        RunChecksJobResult runChecksJobResult = new RunChecksJobResult() {{
            setExecutedChecks(checkExecutionSummary.getTotalChecksExecutedCount());
            setValidResults(checkExecutionSummary.getValidResultsCount());
            setWarnings(checkExecutionSummary.getWarningSeverityIssuesCount());
            setErrors(checkExecutionSummary.getErrorSeverityIssuesCount());
            setFatals(checkExecutionSummary.getFatalSeverityIssuesCount());
            setExecutionErrors(checkExecutionSummary.getTotalExecutionErrorsCount());
        }};

        if (runChecksJobResult.getFatals() > 0) {
            runChecksJobResult.setHighestSeverity(RuleSeverityLevel.fatal);
        }
        else if (runChecksJobResult.getErrors() > 0) {
            runChecksJobResult.setHighestSeverity(RuleSeverityLevel.error);
        }
        else if (runChecksJobResult.getWarnings() > 0) {
            runChecksJobResult.setHighestSeverity(RuleSeverityLevel.warning);
        } else {
            runChecksJobResult.setHighestSeverity(RuleSeverityLevel.valid);
        }

        return runChecksJobResult;
    }
}
