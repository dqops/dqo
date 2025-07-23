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
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.sources.ConnectionList;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class ConnectionSearchFiltersVisitorImplTests extends BaseTest {
    ConnectionSearchFiltersVisitor sut;
    ConnectionList connectionList;
    ConnectionWrapper connectionWrapper;
    ConnectionSearchFilters connectionSearchFilters;
    UserHomeContext userHomeContext;
	ArrayList<HierarchyNode> connectionSpecs;

    @BeforeEach
    void setUp() {
		this.userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.connectionSearchFilters = new ConnectionSearchFilters();
		connectionSearchFilters.setConnectionName("test");
		this.sut = new ConnectionSearchFiltersVisitor(connectionSearchFilters);
		this.connectionList = this.userHomeContext.getUserHome().getConnections();//new ConnectionListImpl();
		this.connectionWrapper = connectionList.createAndAddNew("test");
		connectionSpecs = new ArrayList<>();
    }

    @Test
    void acceptConnectionList_whenCalledForConnectionList_thenReturnsTraverseChildren() {
		this.connectionSearchFilters.setConnectionName("test2");
		this.sut = new ConnectionSearchFiltersVisitor(this.connectionSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionList, new SearchParameterObject(connectionSpecs));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptConnectionList_whenCalledForConnectionListWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionList, new SearchParameterObject(connectionSpecs));
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptConnectionWrapper_whenCalledForConnectionWrapper_thenReturnsSkipChildren() {
		this.connectionSearchFilters.setConnectionName("test2");
		this.sut = new ConnectionSearchFiltersVisitor(this.connectionSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionWrapper, new SearchParameterObject(connectionSpecs));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptConnectionWrapper_whenCalledForConnectionWrapperWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionWrapper, new SearchParameterObject(connectionSpecs));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

}
