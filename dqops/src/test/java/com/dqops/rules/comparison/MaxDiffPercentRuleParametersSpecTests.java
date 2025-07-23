/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rules.comparison;

import com.dqops.BaseTest;
import com.dqops.execution.rules.RuleExecutionResult;
import com.dqops.execution.rules.runners.python.PythonRuleRunnerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MaxDiffPercentRuleParametersSpecTests extends BaseTest {
    private MaxDiffPercentRule1ParametersSpec sut;

    @BeforeEach
    void setUp() {
        this.sut = new MaxDiffPercentRule1ParametersSpec();
    }

    @Test
    void executeRule_whenActualValueIsBelowExpectedValue_thenReturnsFailed() {
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(0.8,1.00, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(1.01, ruleExecutionResult.getUpperBound());
        Assertions.assertEquals(0.99, ruleExecutionResult.getLowerBound());
    }

    @Test
    void executeRule_whenActualValueIsAboveExpectedValue_thenReturnsFailed() {
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1.00,0.8, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(0.808, ruleExecutionResult.getUpperBound());
        Assertions.assertEquals(0.792, ruleExecutionResult.getLowerBound());
    }

    @Test
    void executeRule_whenActualValueIsEqualToExpectedValue_thenReturnsPassed() {
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1.00,1.00, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(1.01, ruleExecutionResult.getUpperBound());
        Assertions.assertEquals(0.99, ruleExecutionResult.getLowerBound());
    }

    @Test
    void executeRule_whenActualValueIsBelowExpectedValueAndNegativeValue_thenReturnsFailed() {
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(-0.8,-1.00, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(-1.01, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(-0.99, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsAboveExpectedValueAndNegativeValue_thenReturnsFailed() {
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(-1.00,-0.8, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(-0.808, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(-0.792, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsEqualToExpectedValueAndNegativeValue_thenReturnsPassed() {
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(-1.00,-1.00, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(-1.01, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(-0.99, ruleExecutionResult.getUpperBound());
    }
}
