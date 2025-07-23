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
public class MinRuleParametersSpecTests extends BaseTest {
    private MinRuleParametersSpec sut;

    @BeforeEach
    void setUp() {
        this.sut = new MinRuleParametersSpec();
    }

    @Test
    void executeRule_whenActualValueIsAboveMinValue_thenReturnsPassed() {
        this.sut.setMinValue(20.5);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.8, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.5, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(20.5, ruleExecutionResult.getLowerBound());
        Assertions.assertNull(ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsEqualMinValue_thenReturnsPassed() {
        this.sut.setMinValue(20.5);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.5, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.5, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(20.5, ruleExecutionResult.getLowerBound());
        Assertions.assertNull(ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsBelowMinValue_thenReturnsFailed() {
        this.sut.setMinValue(20.5);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.4, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.5, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(20.5, ruleExecutionResult.getLowerBound());
        Assertions.assertNull(ruleExecutionResult.getUpperBound());
    }

    @Test
    void isDirty_whenMinValueSet_thenIsDirtyIsTrue() {
        this.sut.setMinValue(1.0);
        Assertions.assertEquals(1, this.sut.getMinValue());
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenMinValueNumberSameAsCurrentSet_thenIsDirtyIsFalse() {
        this.sut.setMinValue(1.0);
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
        this.sut.setMinValue(1.0);
        Assertions.assertFalse(this.sut.isDirty());
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