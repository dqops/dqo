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
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;
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

    @BeforeEach
    void setUp() {
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
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionList, new SearchParameterObject(null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptConnectionList_whenCalledForConnectionListWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionList, new SearchParameterObject(null));
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptConnectionWrapper_whenCalledForConnectionWrapper_thenReturnsSkipChildren() {
		this.columnSearchFilters.setConnectionName("test2");
		this.sut = new ColumnSearchFiltersVisitor(this.columnSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionWrapper, new SearchParameterObject(null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptConnectionWrapper_whenCalledForConnectionWrapperWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionWrapper, new SearchParameterObject(null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableList_whenCalledForTableList_thenReturnsTraverseChildren() {
		this.columnSearchFilters.setSchemaTableName("test2.test2");
		this.sut = new ColumnSearchFiltersVisitor(this.columnSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableList, new SearchParameterObject(null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableList_whenCalledForTableListWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableList, new SearchParameterObject(null));
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableWrapper_whenCalledForTableWrapper_thenReturnsSkipChildren() {
		this.columnSearchFilters.setSchemaTableName("test2.test2");
		this.sut = new ColumnSearchFiltersVisitor(this.columnSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableWrapper, new SearchParameterObject(null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptTableWrapper_whenCalledForTableWrapperWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableWrapper, new SearchParameterObject(null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableSpec_whenCalledForTableSpecDisabled_thenReturnSkipChildren() {
		this.tableSpec.setDisabled(true);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableSpec, new SearchParameterObject(null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptTableSpec_whenCalledForTableSpecWithEnabled_thenReturnTraverseChildren() {
		this.columnSearchFilters.setEnabled(true);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableSpec, new SearchParameterObject(null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableSpec_whenCalledForTableSpecWithoutEnabledAndDisabled_thenReturnSkipChildren() {
		this.columnSearchFilters.setEnabled(false);
		this.tableSpec.setDisabled(false);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableSpec, new SearchParameterObject(null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptColumnSpecMap_whenCalledForColumnSpecMap_thenReturnsTraverseChildren() {
		this.columnSearchFilters.setColumnName("test2");
		this.sut = new ColumnSearchFiltersVisitor(this.columnSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpecMap, new SearchParameterObject(null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptColumnSpecMap_whenCalledForColumnSpecMapWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpecMap, new SearchParameterObject(null));
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptColumnSpec_whenCalledForColumnSpecDisabled_thenReturnSkipChildren() {
		this.columnSpec.setDisabled(true);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpec, new SearchParameterObject(null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptColumnSpec_whenCalledForColumnSpecWithEnabled_thenReturnTraverseChildren() {
		this.columnSearchFilters.setEnabled(true);
        Object[] arr = {"test", "test"};
        HierarchyId hierarchyId = new HierarchyId(arr);
		this.columnSpec.setHierarchyId(hierarchyId);
        ArrayList<HierarchyNode>list = new ArrayList<>();
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpec, new SearchParameterObject(list));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptColumnSpec_whenCalledForColumnSpecWithoutEnabledAndDisabled_thenReturnSkipChildren() {
		this.columnSearchFilters.setEnabled(false);
		this.columnSpec.setDisabled(false);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpec, new SearchParameterObject(null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }
}
