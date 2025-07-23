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
public class DetectedDatatypeEqualsRuleParametersSpecTests extends BaseTest {
    private DetectedDatatypeEqualsRuleParametersSpec sut;

    @BeforeEach
    void setUp() {
        this.sut = new DetectedDatatypeEqualsRuleParametersSpec();
    }

    @Test
    void executeRule_whenActualValueIsEqualExpectedDatatype_thenReturnsPassed() {
        this.sut.setExpectedDatatype(DetectedDatatypeCategory.floats);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(2.0, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(2.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(2.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(2.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsEqualNotExpectedDatatype_thenReturnsPassed() {
        this.sut.setExpectedDatatype(DetectedDatatypeCategory.floats);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(3.0, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(2.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(2.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(2.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsNull_thenReturnsPassed() {
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(null, this.sut);
        Assertions.assertNull(ruleExecutionResult.getPassed());
        Assertions.assertNull(ruleExecutionResult.getExpectedValue());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
        Assertions.assertNull(ruleExecutionResult.getUpperBound());
    }
}
