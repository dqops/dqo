/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.rules;

import com.dqops.execution.ExecutionContext;
import com.dqops.execution.sensors.SensorExecutionRunParameters;

/**
 * Data quality rule runner that picks the right rule implementation and executes a rule.
 */
public interface DataQualityRuleRunner {
    /**
     * Executes a rule and returns the rule evaluation result.
     * @param executionContext Check execution context that provides access to the user home and dqo home.
     * @param ruleRunParameters Rule run parameters (rule parameters, additional data that the rule requires).
     * @param sensorRunParameters Sensor run parameters with a reference to the check specification.
     * @return Rule execution result with the severity status.
     */
    RuleExecutionResult executeRule(ExecutionContext executionContext,
                                    RuleExecutionRunParameters ruleRunParameters,
                                    SensorExecutionRunParameters sensorRunParameters);
}
