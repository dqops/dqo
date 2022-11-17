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
package ai.dqo.metadata.storage.localfiles.ruledefinitions;

import ai.dqo.BaseTest;
import ai.dqo.core.filesystem.virtual.FileContent;
import ai.dqo.metadata.basespecs.InstanceStatus;
import ai.dqo.metadata.definitions.rules.RuleDefinitionList;
import ai.dqo.metadata.definitions.rules.RuleDefinitionSpec;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapperImpl;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.List;

@SpringBootTest
public class FileRuleDefinitionListImplTests extends BaseTest {
    private FileRuleDefinitionListImpl sut;
    private UserHomeContext homeContext;

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
		homeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.sut = (FileRuleDefinitionListImpl) homeContext.getUserHome().getRules();
    }

    @Test
    void createNewElement_whenCalled_createsElementWithEmptyModel() {
        RuleDefinitionWrapperImpl wrapper = this.sut.createNewElement("test");
        Assertions.assertEquals("test", wrapper.getRuleName());
        Assertions.assertEquals(InstanceStatus.UNCHANGED, wrapper.getStatus());
        Assertions.assertNotNull(wrapper.getSpec());
    }

    @Test
    void toList_whenCalled_returnList() {
        RuleDefinitionWrapper wrapper = this.sut.createAndAddNew("test");
        Assertions.assertEquals(1, this.sut.size());
        List<RuleDefinitionWrapper> list = this.sut.toList();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(1, list.size());
        Assertions.assertSame(wrapper, list.get(0));
    }

    @Test
    void createAndAddNew_whenNewRuleWithSpecAndNoPythonAddedAndFlushed_thenIsSaved() {
        RuleDefinitionWrapper wrapper = this.sut.createAndAddNew("src1");
        RuleDefinitionSpec model = wrapper.getSpec();
        model.getParameters().put("param1", "value");
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        RuleDefinitionList list2 = homeContext2.getUserHome().getRules();
        RuleDefinitionWrapper wrapper2 = list2.getByObjectName("src1", true);
        Assertions.assertNotEquals(null, wrapper2);
        Assertions.assertEquals("value", wrapper2.getSpec().getParameters().get("param1"));
        Assertions.assertNull(wrapper2.getRulePythonModuleContent().getTextContent());
    }

    @Test
    void createAndAddNew_whenNewRuleWithSpecAndPythonFileAddedAndFlushed_thenIsSaved() {
        RuleDefinitionWrapper wrapper = this.sut.createAndAddNew("src1");
        RuleDefinitionSpec model = wrapper.getSpec();
        model.getParameters().put("param1", "value");
        wrapper.setRulePythonModuleContent(new FileContent("def fun()"));
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        RuleDefinitionList list2 = homeContext2.getUserHome().getRules();
        RuleDefinitionWrapper wrapper2 = list2.getByObjectName("src1", true);
        Assertions.assertNotEquals(null, wrapper2);
        Assertions.assertEquals("value", wrapper2.getSpec().getParameters().get("param1"));
        Assertions.assertEquals("def fun()", wrapper2.getRulePythonModuleContent().getTextContent());
    }

    @Test
    void flush_whenExistingRuleLoadedModifiedAndFlushed_thenIsSaved() {
		this.sut.createAndAddNew("dir/dir2/sample2");
		homeContext.flush();

        RuleDefinitionSpec spec2 = new RuleDefinitionSpec();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        RuleDefinitionList list2 = homeContext2.getUserHome().getRules();
        RuleDefinitionWrapper wrapper2 = list2.getByObjectName("dir/dir2/sample2", true);
        wrapper2.setRulePythonModuleContent(new FileContent("def fun()"));
        wrapper2.setSpec(spec2);
        spec2.getParameters().put("p1", "v");
        homeContext2.flush();

        UserHomeContext homeContext3 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        RuleDefinitionList list3 = homeContext3.getUserHome().getRules();
        RuleDefinitionWrapper wrapper3 = list3.getByObjectName("dir/dir2/sample2", true);
        Assertions.assertEquals(false, wrapper3.getSpec().isDirty());
        Assertions.assertEquals("v", wrapper3.getSpec().getParameters().get("p1"));
        Assertions.assertEquals("def fun()", wrapper2.getRulePythonModuleContent().getTextContent());
    }

    @Test
    void iterator_whenDefinitionAdded_thenReturnsDefinition() {
		this.sut.createAndAddNew("tab/src3");
		homeContext.flush();

        UserHomeContext homeContext2 = UserHomeContextObjectMother.createTemporaryFileHomeContext(false);
        RuleDefinitionList sut2 = homeContext2.getUserHome().getRules();
        Iterator<RuleDefinitionWrapper> iterator = sut2.iterator();
        Assertions.assertTrue(iterator.hasNext());
        RuleDefinitionWrapper wrapperLoaded = iterator.next();
        Assertions.assertEquals("tab/src3", wrapperLoaded.getRuleName());
        Assertions.assertNotNull(wrapperLoaded);
    }
}
