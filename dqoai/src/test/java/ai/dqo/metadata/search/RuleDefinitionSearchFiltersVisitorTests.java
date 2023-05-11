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
package ai.dqo.metadata.search;

import ai.dqo.BaseTest;
import ai.dqo.metadata.definitions.rules.RuleDefinitionList;
import ai.dqo.metadata.definitions.rules.RuleDefinitionWrapper;
import ai.dqo.metadata.id.HierarchyNode;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class RuleDefinitionSearchFiltersVisitorTests extends BaseTest {
    RuleDefinitionSearchFiltersVisitor sut;
    RuleDefinitionList ruleDefinitionList;
    RuleDefinitionWrapper ruleDefinitionWrapper;
    RuleDefinitionSearchFilters ruleDefinitionSearchFilters;
    UserHomeContext userHomeContext;
	ArrayList<HierarchyNode> ruleDefinitionWrappers;

    @BeforeEach
    void setUp() {
        this.userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.ruleDefinitionSearchFilters = new RuleDefinitionSearchFilters();
		this.ruleDefinitionSearchFilters.setRuleName("test");
		this.sut = new RuleDefinitionSearchFiltersVisitor(this.ruleDefinitionSearchFilters);
		this.ruleDefinitionList = this.userHomeContext.getUserHome().getRules();
		this.ruleDefinitionWrapper = ruleDefinitionList.createAndAddNew("test");
		ruleDefinitionWrappers = new ArrayList<>();
    }

    @Test
    void acceptRuleDefinitionList_whenCalledForRuleDefinitionList_thenReturnsTraverseChildren() {
		this.ruleDefinitionSearchFilters.setRuleName("test2");
		this.sut = new RuleDefinitionSearchFiltersVisitor(this.ruleDefinitionSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.ruleDefinitionList, new SearchParameterObject(ruleDefinitionWrappers, null, null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptRuleDefinitionList_whenCalledForRuleDefinitionListWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.ruleDefinitionList, new SearchParameterObject(ruleDefinitionWrappers, null, null));
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptRuleDefinitionWrapper_whenCalledForRuleDefinitionWrapper_thenReturnsSkipChildren() {
		this.ruleDefinitionSearchFilters.setRuleName("test2");
		this.sut = new RuleDefinitionSearchFiltersVisitor(this.ruleDefinitionSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.ruleDefinitionWrapper, new SearchParameterObject(ruleDefinitionWrappers, null, null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptRuleDefinitionWrapper_whenCalledForRuleDefinitionWrapperWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.ruleDefinitionWrapper, new SearchParameterObject(ruleDefinitionWrappers, null, null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }
}
