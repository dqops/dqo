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
import ai.dqo.metadata.id.HierarchyId;
import ai.dqo.metadata.id.HierarchyNode;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.metadata.traversal.TreeNodeTraversalResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class ColumnSearchFiltersVisitorTests extends BaseTest {
    ColumnSearchFiltersVisitor sut;
    ConnectionList connectionList;
    ConnectionWrapper connectionWrapper;
    TableList tableList;
    TableWrapper tableWrapper;
    TableSpec tableSpec;
    ColumnSpecMap columnSpecMap;
    ColumnSpec columnSpec;
    ColumnSearchFilters columnSearchFilters;
    UserHomeContext userHomeContext;

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
		this.userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.columnSearchFilters = new ColumnSearchFilters();
		columnSearchFilters.setConnectionName("test");
		columnSearchFilters.setSchemaTableName("test.test");
		columnSearchFilters.setColumnName("test");
		this.sut = new ColumnSearchFiltersVisitor(columnSearchFilters);
		this.connectionList = this.userHomeContext.getUserHome().getConnections();
		this.connectionWrapper = connectionList.createAndAddNew("test");
		this.tableList = this.connectionWrapper.getTables();
		this.tableWrapper = tableList.createAndAddNew(PhysicalTableName.fromSchemaTableFilter("test.test"));
		this.tableSpec = tableWrapper.getSpec();
		this.columnSpecMap = this.tableSpec.getColumns();
		this.columnSpec = new ColumnSpec();
		this.columnSpecMap.put("test", columnSpec);
    }

    @Test
    void acceptConnectionList_whenCalledForConnectionList_thenReturnsTraverseChildren() {
		this.columnSearchFilters.setConnectionName("test2");
		this.sut = new ColumnSearchFiltersVisitor(this.columnSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionList, null);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptConnectionList_whenCalledForConnectionListWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionList, null);
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptConnectionWrapper_whenCalledForConnectionWrapper_thenReturnsSkipChildren() {
		this.columnSearchFilters.setConnectionName("test2");
		this.sut = new ColumnSearchFiltersVisitor(this.columnSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionWrapper, null);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptConnectionWrapper_whenCalledForConnectionWrapperWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionWrapper, null);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableList_whenCalledForTableList_thenReturnsTraverseChildren() {
		this.columnSearchFilters.setSchemaTableName("test2.test2");
		this.sut = new ColumnSearchFiltersVisitor(this.columnSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableList, null);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableList_whenCalledForTableListWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableList, null);
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableWrapper_whenCalledForTableWrapper_thenReturnsSkipChildren() {
		this.columnSearchFilters.setSchemaTableName("test2.test2");
		this.sut = new ColumnSearchFiltersVisitor(this.columnSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableWrapper, null);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptTableWrapper_whenCalledForTableWrapperWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableWrapper, null);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableSpec_whenCalledForTableSpecDisabled_thenReturnSkipChildren() {
		this.tableSpec.setDisabled(true);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableSpec, null);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptTableSpec_whenCalledForTableSpecWithEnabled_thenReturnTraverseChildren() {
		this.columnSearchFilters.setEnabled(true);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableSpec, null);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableSpec_whenCalledForTableSpecWithoutEnabledAndDisabled_thenReturnSkipChildren() {
		this.columnSearchFilters.setEnabled(false);
		this.tableSpec.setDisabled(false);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableSpec, null);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptColumnSpecMap_whenCalledForColumnSpecMap_thenReturnsTraverseChildren() {
		this.columnSearchFilters.setColumnName("test2");
		this.sut = new ColumnSearchFiltersVisitor(this.columnSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpecMap, null);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptColumnSpecMap_whenCalledForColumnSpecMapWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpecMap, null);
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptColumnSpec_whenCalledForColumnSpecDisabled_thenReturnSkipChildren() {
		this.columnSpec.setDisabled(true);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpec, null);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptColumnSpec_whenCalledForColumnSpecWithEnabled_thenReturnTraverseChildren() {
		this.columnSearchFilters.setEnabled(true);
        Object[] arr = {"test", "test"};
        HierarchyId hierarchyId = new HierarchyId(arr);
		this.columnSpec.setHierarchyId(hierarchyId);
        ArrayList<HierarchyNode>list = new ArrayList<>();
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpec, new SearchParameterObject(list, null, null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptColumnSpec_whenCalledForColumnSpecWithoutEnabledAndDisabled_thenReturnSkipChildren() {
		this.columnSearchFilters.setEnabled(false);
		this.columnSpec.setDisabled(false);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpec, null);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }
}
