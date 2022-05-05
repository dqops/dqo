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
package ai.dqo.execution.rules.runners;

import ai.dqo.BaseTest;
import ai.dqo.execution.rules.runners.python.PythonRuleRunner;
import ai.dqo.metadata.definitions.rules.RuleRunnerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RuleRunnerFactoryImplTests extends BaseTest {
    private RuleRunnerFactoryImpl sut;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
		this.sut = RuleRunnerFactoryObjectMother.createDefault();
    }

    @Test
    void getRuleRunner_whenPythonRuleRunnerRequested_thenReturnsCorrectRunner() {
        AbstractRuleRunner ruleRunner = this.sut.getRuleRunner(RuleRunnerType.python, null);
        Assertions.assertNotNull(ruleRunner);
        Assertions.assertInstanceOf(PythonRuleRunner.class, ruleRunner);
    }

    @Test
    void getRuleRunner_whenPythonRuleRunnerRequestedByClassName_thenCreatesCorrectRunner() {
        AbstractRuleRunner ruleRunner = this.sut.getRuleRunner(RuleRunnerType.custom_class, PythonRuleRunner.CLASS_NAME);
        Assertions.assertNotNull(ruleRunner);
        Assertions.assertInstanceOf(PythonRuleRunner.class, ruleRunner);
    }
}
