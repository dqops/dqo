/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.checks.jobs;

import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.rules.RuleSeverityLevel;
import com.dqops.utils.docs.generators.SampleValueFactory;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Returns the result (the highest data quality check severity and the finished checks count) for the checks that were recently executed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "RunChecksResult", description = "Returns the result (highest data quality check severity and the finished checks count) for the checks that were recently executed.")
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class RunChecksResult {
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
    public RunChecksResult() {
    }

    /**
     * Creates a check run job result from the execution summary.
     * @param checkExecutionSummary Check execution summary.
     * @return The job result object.
     */
    public static RunChecksResult fromCheckExecutionSummary(CheckExecutionSummary checkExecutionSummary) {
        if (checkExecutionSummary == null) {
            return null;
        }

        RunChecksResult runChecksResult = new RunChecksResult() {{
            setExecutedChecks(checkExecutionSummary.getTotalChecksExecutedCount());
            setValidResults(checkExecutionSummary.getValidResultsCount());
            setWarnings(checkExecutionSummary.getWarningSeverityIssuesCount());
            setErrors(checkExecutionSummary.getErrorSeverityIssuesCount());
            setFatals(checkExecutionSummary.getFatalSeverityIssuesCount());
            setExecutionErrors(checkExecutionSummary.getTotalExecutionErrorsCount());
        }};

        if (runChecksResult.getFatals() > 0) {
            runChecksResult.setHighestSeverity(RuleSeverityLevel.fatal);
        }
        else if (runChecksResult.getErrors() > 0) {
            runChecksResult.setHighestSeverity(RuleSeverityLevel.error);
        }
        else if (runChecksResult.getWarnings() > 0) {
            runChecksResult.setHighestSeverity(RuleSeverityLevel.warning);
        } else {
            runChecksResult.setHighestSeverity(RuleSeverityLevel.valid);
        }

        return runChecksResult;
    }

    public static class RunChecksResultSampleFactory implements SampleValueFactory<RunChecksResult> {
        @Override
        public RunChecksResult createSample() {
            return new RunChecksResult() {{
                    setHighestSeverity(RuleSeverityLevel.error);
                    setExecutedChecks(10);
                    setErrors(2);
                    setWarnings(1);
                    setValidResults(7);
                    setFatals(0);
            }};
        }
    }
}
