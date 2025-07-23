/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.metadata.search;

import com.dqops.BaseTest;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionWrapper;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;
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
