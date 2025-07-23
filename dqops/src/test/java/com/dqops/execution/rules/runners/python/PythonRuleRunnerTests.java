/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.rules.runners.python;

import com.dqops.BaseTest;
import com.dqops.core.configuration.DqoPythonConfigurationProperties;
import com.dqops.core.configuration.DqoPythonConfigurationPropertiesObjectMother;
import com.dqops.core.filesystem.localfiles.HomeLocationFindServiceImpl;
import com.dqops.core.filesystem.localfiles.HomeLocationFindServiceObjectMother;
import com.dqops.execution.CheckExecutionContextObjectMother;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.rules.RuleExecutionResult;
import com.dqops.execution.rules.RuleExecutionRunParameters;
import com.dqops.execution.rules.finder.RuleDefinitionFindResult;
import com.dqops.execution.rules.finder.RuleDefinitionFindResultObjectMother;
import com.dqops.rules.comparison.MinValueRuleParametersSpec;
import com.dqops.utils.python.PythonCallServiceObjectMother;
import com.dqops.utils.python.PythonCallerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PythonRuleRunnerTests extends BaseTest {
    private PythonRuleRunner sut;
    private PythonCallerService pythonCallerService;
    private HomeLocationFindServiceImpl homeLocationFinder;
    private ExecutionContext inMemoryCheckContext;
    private DqoPythonConfigurationProperties pythonConfigurationProperties;

    @BeforeEach
    void setUp() {
		pythonCallerService = PythonCallServiceObjectMother.getDefault();
        pythonConfigurationProperties = DqoPythonConfigurationPropertiesObjectMother.getDefaultCloned();
        homeLocationFinder = HomeLocationFindServiceObjectMother.getWithTestUserHome(true);
		this.sut = new PythonRuleRunner(pythonCallerService, pythonConfigurationProperties, homeLocationFinder);
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
        Assertions.assertTrue(executionResult.getPassed());
        Assertions.assertEquals(15.5, executionResult.getExpectedValue().doubleValue());
        Assertions.assertEquals(15.5, executionResult.getLowerBound().doubleValue());
        Assertions.assertNull(executionResult.getUpperBound());
    }
}
