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
public class BetweenPercentRuleParametersSpecTests extends BaseTest {
    private BetweenPercentRuleParametersSpec sut;

    @BeforeEach
    void setUp() {
		this.sut = new BetweenPercentRuleParametersSpec();
    }

    @Test
    void executeRule_whenActualValueIsEqualBegin_thenReturnsPassed() {
		this.sut.setMinPercent(1.0);
        this.sut.setMaxPercent(5.0);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1.0, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(1.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(5.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsEqualEnd_thenReturnsPassed() {
        this.sut.setMinPercent(1.0);
        this.sut.setMaxPercent(5.0);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(5.0, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(1.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(5.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsBetweenBeginAndEnd_thenReturnsPassed() {
        this.sut.setMinPercent(1.0);
        this.sut.setMaxPercent(5.0);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(3.0, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(1.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(5.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsAboveFromAndToNotProvided_thenReturnsPassed() {
        this.sut.setMinPercent(1.0);
        this.sut.setMaxPercent(null);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(3.0, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(1.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(null, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsBelowToAndFromNotProvided_thenReturnsPassed() {
        this.sut.setMinPercent(null);
        this.sut.setMaxPercent(5.0);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(3.0, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(null, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(5.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsAboveEnd_thenReturnsFailed() {
        this.sut.setMinPercent(1.0);
        this.sut.setMaxPercent(5.0);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(7.0, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(1.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(5.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsAboveEndAndFromNotProvided_thenReturnsFailed() {
        this.sut.setMinPercent(null);
        this.sut.setMaxPercent(5.0);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(7.0, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(null, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(5.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsBelowBegin_thenReturnsFailed() {
        this.sut.setMinPercent(1.0);
        this.sut.setMaxPercent(5.0);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(0.0, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(1.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(5.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsBelowBeginAndEndNotProvided_thenReturnsFailed() {
        this.sut.setMinPercent(1.0);
        this.sut.setMaxPercent(null);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(0.0, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(1.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(null, ruleExecutionResult.getUpperBound());
    }

    @Test
    void isDirty_whenBeginSet_thenIsDirtyIsTrue() {
        this.sut.setMinPercent(1.0);
        Assertions.assertEquals(1.0, this.sut.getMinPercent());
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenBeginNumberSameAsCurrentSet_thenIsDirtyIsFalse() {
        this.sut.setMinPercent(1.0);
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
        this.sut.setMinPercent(1.0);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenEndSet_thenIsDirtyIsTrue() {
        this.sut.setMaxPercent(1.0);
        Assertions.assertEquals(1.0, this.sut.getMaxPercent());
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenEndNumberSameAsCurrentSet_thenIsDirtyIsFalse() {
        this.sut.setMaxPercent(1.0);
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
        this.sut.setMaxPercent(1.0);
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
