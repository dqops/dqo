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
