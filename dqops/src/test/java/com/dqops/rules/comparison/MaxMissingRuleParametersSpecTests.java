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
public class MaxMissingRuleParametersSpecTests extends BaseTest {
    private MaxMissingRule0WarningParametersSpec sut;

    @BeforeEach
    void setUp() {
		this.sut = new MaxMissingRule0WarningParametersSpec();
    }

    @Test
    void isDirty_whenMaxMissingSet_thenIsDirtyIsTrue() {
		this.sut.setMaxMissing(1L);
        Assertions.assertEquals(1L, this.sut.getMaxMissing());
        Assertions.assertTrue(this.sut.isDirty());
		this.sut.clearDirty(true);
        Assertions.assertFalse(this.sut.isDirty());
    }

    @Test
    void executeRule_whenActualValueIsNull_thenReturnsPassed() {
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(null, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertNull(ruleExecutionResult.getExpectedValue());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
        Assertions.assertNull(ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenMaxMissing0AndNoneMissing_thenReturnsPassed() {
        this.sut.setMaxMissing(0L);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(5.0, 5.0, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(5.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(5.0, ruleExecutionResult.getLowerBound());
        Assertions.assertNull(ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenMaxMissing1AndNoneMissing_thenReturnsPassed() {
        this.sut.setMaxMissing(1L);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(5.0, 5.0, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(5.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(4.0, ruleExecutionResult.getLowerBound());
        Assertions.assertNull(ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenMaxMissing1AndOneMissing_thenReturnsPassed() {
        this.sut.setMaxMissing(1L);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(4.0, 5.0, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(5.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(4.0, ruleExecutionResult.getLowerBound());
        Assertions.assertNull(ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenMaxMissing1And2Missing_thenReturnsPassed() {
        this.sut.setMaxMissing(1L);
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(3.0, 5.0, this.sut);
        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(5.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(4.0, ruleExecutionResult.getLowerBound());
        Assertions.assertNull(ruleExecutionResult.getUpperBound());
    }
}
