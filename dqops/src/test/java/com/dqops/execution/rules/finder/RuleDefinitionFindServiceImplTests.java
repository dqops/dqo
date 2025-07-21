/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.execution.rules.finder;

import com.dqops.BaseTest;
import com.dqops.core.filesystem.virtual.FileContent;
import com.dqops.execution.ExecutionContext;
import com.dqops.metadata.definitions.rules.RuleDefinitionWrapper;
import com.dqops.metadata.storage.localfiles.HomeType;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContext;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.userhome.UserHome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RuleDefinitionFindServiceImplTests extends BaseTest {
    private RuleDefinitionFindService sut;

    @BeforeEach
    void setUp() {
		this.sut = new RuleDefinitionFindServiceImpl();
    }

    @Test
    void findRuleDefinition_whenRulePresentInDqoHome_thenReturnsDefinition() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        ExecutionContext executionContext = new ExecutionContext(inMemoryFileHomeContext, dqoHomeContext);

        RuleDefinitionFindResult result = this.sut.findRule(executionContext, "comparison/min_value");

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getRuleDefinitionSpec());
        Assertions.assertEquals(HomeType.DQO_HOME, result.getHome());
        Assertions.assertNotNull(result.getRulePythonFilePath());
        Assertions.assertEquals("[]/rules/comparison/min_value.py", result.getRulePythonFilePath().toString());
    }

    @Test
    void findRuleDefinition_whenUnknownRule_thenReturnsNull() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        ExecutionContext executionContext = new ExecutionContext(inMemoryFileHomeContext, dqoHomeContext);

        RuleDefinitionFindResult result = this.sut.findRule(executionContext, "comparison/missingsensor");

        Assertions.assertNull(result);
    }

    @Test
    void findRuleDefinition_whenRuleRedefinedInUserHome_thenReturnsDefinitionFromUserHome() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        ExecutionContext executionContext = new ExecutionContext(inMemoryFileHomeContext, dqoHomeContext);
        UserHome userHome = inMemoryFileHomeContext.getUserHome();
        final String ruleName = "comparison/min_value";
        RuleDefinitionWrapper userRuleDef = userHome.getRules().createAndAddNew(ruleName);
        userRuleDef.setRulePythonModuleContent(new FileContent("def fun()"));

        RuleDefinitionFindResult result = this.sut.findRule(executionContext, "comparison/min_value");

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getRuleDefinitionSpec());
        Assertions.assertSame(userRuleDef.getSpec(), result.getRuleDefinitionSpec());
        Assertions.assertEquals(HomeType.USER_HOME, result.getHome());
        Assertions.assertEquals("[]/rules/comparison/min_value.py", result.getRulePythonFilePath().toString());
    }
}
