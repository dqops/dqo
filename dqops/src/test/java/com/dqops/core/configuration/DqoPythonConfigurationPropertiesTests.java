/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.core.configuration;

import com.dqops.BaseTest;
import com.dqops.utils.BeanFactoryObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DqoPythonConfigurationPropertiesTests extends BaseTest {
    private DqoPythonConfigurationProperties sut;

    @BeforeEach
    void setUp() {
		this.sut = BeanFactoryObjectMother.getBeanFactory().getBean(DqoPythonConfigurationProperties.class);
    }

    @Test
    void getInterpreter_whenRetrieved_thenReturnsAllExpectedPythonInterpreterNames() {
        Assertions.assertEquals("python3,python3.exe,python,python.exe", this.sut.getInterpreter());
    }

    @Test
    void getEvaluateTemplatesModule_whenRetrieved_thenReturnsPathToEvaluateTemplatesPythonModule() {
        Assertions.assertEquals("lib/evaluate_templates.py", this.sut.getEvaluateTemplatesModule());
    }

    @Test
    void getEvaluateRulesModule_whenRetrieved_thenReturnsPathToEvaluateRulesPythonModule() {
        Assertions.assertEquals("lib/evaluate_rules.py", this.sut.getEvaluateRulesModule());
    }
}
