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
package ai.dqo.execution.rules.finder;

import ai.dqo.BaseTest;
import ai.dqo.core.filesystem.virtual.FileContent;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.storage.localfiles.HomeType;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.metadata.userhome.UserHome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RuleDefinitionFindServiceImplTests extends BaseTest {
    private RuleDefinitionFindService sut;

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
		this.sut = new RuleDefinitionFindServiceImpl();
    }

    @Test
    void findRuleDefinition_whenRulePresentInDqoHome_thenReturnsDefinition() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        CheckExecutionContext checkExecutionContext = new CheckExecutionContext(inMemoryFileHomeContext, dqoHomeContext);

        RuleDefinitionFindResult result = this.sut.findRule(checkExecutionContext, "comparison/min_value");

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getRuleDefinitionSpec());
        Assertions.assertEquals(HomeType.DQO_HOME, result.getHome());
        Assertions.assertNotNull(result.getRulePythonFilePath());
        Assertions.assertEquals("rules/comparison/min_value.py", result.getRulePythonFilePath().toString());
    }

    @Test
    void findRuleDefinition_whenUnknownRule_thenReturnsNull() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        CheckExecutionContext checkExecutionContext = new CheckExecutionContext(inMemoryFileHomeContext, dqoHomeContext);

        RuleDefinitionFindResult result = this.sut.findRule(checkExecutionContext, "comparison/missingsensor");

        Assertions.assertNull(result);
    }

    @Test
    void findRuleDefinition_whenRuleRedefinedInUserHome_thenReturnsDefinitionFromUserHome() {
        UserHomeContext inMemoryFileHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContext();
        DqoHomeContext dqoHomeContext = DqoHomeContextObjectMother.getRealDqoHomeContext();
        CheckExecutionContext checkExecutionContext = new CheckExecutionContext(inMemoryFileHomeContext, dqoHomeContext);
        UserHome userHome = inMemoryFileHomeContext.getUserHome();
        final String ruleName = "comparison/min_value";
        RuleDefinitionWrapper userRuleDef = userHome.getRules().createAndAddNew(ruleName);
        userRuleDef.setRulePythonModuleContent(new FileContent("def fun()"));

        RuleDefinitionFindResult result = this.sut.findRule(checkExecutionContext, "comparison/min_value");

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getRuleDefinitionSpec());
        Assertions.assertSame(userRuleDef.getSpec(), result.getRuleDefinitionSpec());
        Assertions.assertEquals(HomeType.USER_HOME, result.getHome());
        Assertions.assertEquals("rules/comparison/min_value.py", result.getRulePythonFilePath().toString());
    }
}
