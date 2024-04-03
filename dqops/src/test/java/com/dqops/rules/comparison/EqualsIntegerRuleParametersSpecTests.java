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
package com.dqops.rules.comparison;

import com.dqops.BaseTest;
import com.dqops.execution.rules.RuleExecutionResult;
import com.dqops.execution.rules.runners.python.PythonRuleRunnerObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EqualsIntegerRuleParametersSpecTests extends BaseTest {
    private EqualsIntegerRuleParametersSpec sut;

    @BeforeEach
    void setUp() {
        this.sut = new EqualsIntegerRuleParametersSpec();
    }

    @Test
    void executeRule_whenActualValueIsEqualExpectedValue_thenReturnsPassed() {
        this.sut.setExpectedValue(20L);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.0, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(20.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(20.0, ruleExecutionResult.getUpperBound());
    }
    @Test
    void executeRule_whenActualValueIsNotEqualExpectedValue_thenReturnsPassed() {
        this.sut.setExpectedValue(20L);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(19.0, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(20.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(20.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void isDirty_whenExpectedValueSet_thenIsDirtyIsTrue() {
        this.sut.setExpectedValue(1L);
        Assertions.assertEquals(1L, this.sut.getExpectedValue());
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void isDirty_whenExpectedValueNumberSameAsCurrentSet_thenIsDirtyIsFalse() {
        this.sut.setExpectedValue(1L);
        Assertions.assertTrue(this.sut.isDirty());
        this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
        this.sut.setExpectedValue(1L);
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
