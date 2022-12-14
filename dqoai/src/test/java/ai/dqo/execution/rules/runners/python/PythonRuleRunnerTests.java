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
package ai.dqo.execution.rules.runners.python;

import ai.dqo.BaseTest;
import ai.dqo.core.configuration.DqoConfigurationProperties;
import ai.dqo.core.configuration.DqoConfigurationPropertiesObjectMother;
import ai.dqo.core.filesystem.localfiles.HomeLocationFindServiceImpl;
import ai.dqo.core.filesystem.localfiles.HomeLocationFindServiceObjectMother;
import ai.dqo.execution.CheckExecutionContextObjectMother;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.rules.RuleExecutionResult;
import ai.dqo.execution.rules.RuleExecutionRunParameters;
import ai.dqo.execution.rules.finder.RuleDefinitionFindResult;
import ai.dqo.execution.rules.finder.RuleDefinitionFindResultObjectMother;
import ai.dqo.rules.comparison.MinValueRuleParametersSpec;
import ai.dqo.utils.python.PythonCallServiceObjectMother;
import ai.dqo.utils.python.PythonCallerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PythonRuleRunnerTests extends BaseTest {
    private PythonRuleRunner sut;
    private PythonCallerService pythonCallerService;
    private DqoConfigurationProperties dqoConfigurationProperties;
    private HomeLocationFindServiceImpl homeLocationFinder;
    private ExecutionContext inMemoryCheckContext;

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
		pythonCallerService = PythonCallServiceObjectMother.getDefault();
		dqoConfigurationProperties = DqoConfigurationPropertiesObjectMother.getDefaultCloned();
		homeLocationFinder = HomeLocationFindServiceObjectMother.getWithTestUserHome(true);
		this.sut = new PythonRuleRunner(pythonCallerService, dqoConfigurationProperties, homeLocationFinder);
		inMemoryCheckContext = CheckExecutionContextObjectMother.createWithInMemoryUserContext();
    }

    @Test
    void executeRule_whenMinValueBuiltInRuleNonErrorEvaluated_thenReturnsResultPassed() {
        RuleExecutionRunParameters ruleRunParameters = new RuleExecutionRunParameters();
        MinValueRuleParametersSpec ruleParameters = new MinValueRuleParametersSpec();
        ruleRunParameters.setParameters(ruleParameters);
        ruleParameters.setMinValue(15.5);
        ruleRunParameters.setActualValue(15.8);
        RuleDefinitionFindResult ruleDefinitionFindResult = RuleDefinitionFindResultObjectMother.findDqoHomeRuleDefinition("comparison/min_value");

        RuleExecutionResult executionResult = this.sut.executeRule(this.inMemoryCheckContext, ruleRunParameters, ruleDefinitionFindResult);

        Assertions.assertNotNull(executionResult);
        Assertions.assertTrue(executionResult.isPassed());
        Assertions.assertEquals(15.5, executionResult.getExpectedValue().doubleValue());
        Assertions.assertEquals(15.5, executionResult.getLowerBound().doubleValue());
        Assertions.assertNull(executionResult.getUpperBound());
    }
}
