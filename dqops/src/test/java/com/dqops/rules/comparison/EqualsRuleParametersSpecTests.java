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
public class EqualsRuleParametersSpecTests extends BaseTest {
    private EqualsRuleParametersSpec sut;

    @BeforeEach
    void setUp() {
        this.sut = new EqualsRuleParametersSpec();
    }

    @Test
    void executeRule_whenActualValueIsEqualExpectedValueAndErrorIs0_thenReturnsPassed() {
        this.sut.setExpectedValue(20.5);
        this.sut.setErrorMargin(0.0);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.5, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.5, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(20.5, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(20.5, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsEqualExpectedValueAnd_thenReturnsPassed() {
        this.sut.setExpectedValue(20.5);
        this.sut.setErrorMargin(1.5);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.5, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.5, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(22.0, ruleExecutionResult.getUpperBound());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
    }

    @Test
    void executeRule_whenActualValueIsBelowExpectedButWithinErrorMargin_thenReturnsPassed() {
        this.sut.setExpectedValue(20.5);
        this.sut.setErrorMargin(1.5);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.3, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.5, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(22.0, ruleExecutionResult.getUpperBound());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
    }

    @Test
    void executeRule_whenActualValueIsAboveExpectedButWithinErrorMargin_thenReturnsPassed() {
        this.sut.setExpectedValue(20.5);
        this.sut.setErrorMargin(1.5);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.7, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.5, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(22.0, ruleExecutionResult.getUpperBound());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
    }

    @Test
    void executeRule_whenActualValueIsAboveExpectedValueAboveErrorMargin_thenReturnsFailed() {
        this.sut.setExpectedValue(20.5);
        this.sut.setErrorMargin(1.5);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(22.7, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.5, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(22.0, ruleExecutionResult.getUpperBound());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
    }

    @Test
    void executeRule_whenActualValueIsBelowExpectedValueAboveErrorMargin_thenReturnsFailed() {
        this.sut.setExpectedValue(20.5);
        this.sut.setErrorMargin(1.5);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(28.7, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.5, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(22.0, ruleExecutionResult.getUpperBound());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
    }

    @Test
    void isDirty_whenExpectedValueSet_thenIsDirtyIsTrue() {
        this.sut.setExpectedValue(1.0);
        Assertions.assertEquals(1, this.sut.getExpectedValue());
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenExpectedValueNumberSameAsCurrentSet_thenIsDirtyIsFalse() {
        this.sut.setExpectedValue(1.0);
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
        this.sut.setExpectedValue(1.0);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenErrorMarginSet_thenIsDirtyIsTrue() {
        this.sut.setErrorMargin(1.0);
        Assertions.assertEquals(1, this.sut.getErrorMargin());
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenMarginErrorObjectSameAsCurrentSet_thenIsDirtyIsFalse() {
        this.sut.setErrorMargin(1.0);
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
        this.sut.setErrorMargin(1.0);
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
