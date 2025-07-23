/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.rules.runners;

import com.dqops.BaseTest;
import com.dqops.execution.rules.runners.python.PythonRuleRunner;
import com.dqops.metadata.definitions.rules.RuleRunnerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RuleRunnerFactoryImplTests extends BaseTest {
    private RuleRunnerFactoryImpl sut;

    @BeforeEach
    void setUp() {
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
