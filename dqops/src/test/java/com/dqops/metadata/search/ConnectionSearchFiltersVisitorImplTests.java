/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
