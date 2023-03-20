/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.rules.comparison;

import ai.dqo.BaseTest;
import ai.dqo.execution.rules.RuleExecutionResult;
import ai.dqo.execution.rules.runners.python.PythonRuleRunnerObjectMother;
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
        Assertions.assertFalse(ruleExecutionResult.isPassed());
        Assertions.assertNull(ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(1.0, ruleExecutionResult.getUpperBound());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
    }

    @Test
    void executeRule_whenActualValueIsAboveExpectedValue_thenReturnsFailed() {
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1.00,0.8, this.sut);
        Assertions.assertFalse(ruleExecutionResult.isPassed());
        Assertions.assertNull(ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(1.0, ruleExecutionResult.getUpperBound());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
    }

    @Test
    void executeRule_whenActualValueIsEqualToExpectedValue_thenReturnsPassed() {
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1.00,1.00, this.sut);
        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertNull(ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(1.0, ruleExecutionResult.getUpperBound());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
    }

}
