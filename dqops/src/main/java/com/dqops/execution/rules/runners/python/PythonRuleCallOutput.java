/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.rules.runners.python;

import com.dqops.execution.rules.RuleExecutionResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Schema for the json object that is returned from the python rule evaluation module for each rule that was executed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@EqualsAndHashCode(callSuper = false)
public class PythonRuleCallOutput {
    private RuleExecutionResult result;
    private Map<String, Object> parameters = new LinkedHashMap<>();
    private String error;

    /**
     * Result object returned from the python rule.
     * @return Rule execution result.
     */
    public RuleExecutionResult getResult() {
        return result;
    }

    /**
     * Sets the rule execution result.
     * @param result Rule execution result.
     */
    public void setResult(RuleExecutionResult result) {
        this.result = result;
    }

    /**
     * Returns the rule parameters that were passed, but were converted to a dictionary.
     * @return Rule execution parameters.
     */
    public Map<String, Object> getParameters() {
        return parameters;
    }

    /**
     * Sets the rule parameters.
     * @param parameters Rule parameters.
     */
    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    /**
     * Returns the error (and the call stack) from the python rule if the rule evaluation failed and an exception was raised.
     * The error is null when the rule was evaluated correctly.
     * @return Error text or null when the rule was evaluated successfully.
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the error message.
     * @param error Error message.
     */
    public void setError(String error) {
        this.error = error;
    }
}
