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
public class TableSearchFiltersVisitorTests extends BaseTest {
    TableSearchFiltersVisitor sut;
    ConnectionList connectionList;
    ConnectionWrapper connectionWrapper;
    TableList tableList;
    TableWrapper tableWrapper;
    TableSpec tableSpec;
    TableSearchFilters tableSearchFilters;
    UserHomeContext userHomeContext;
    ArrayList<HierarchyNode> hierarchyNodeArrayList;

    @BeforeEach
    void setUp() {
		this.userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.tableSearchFilters = new TableSearchFilters();
		tableSearchFilters.setConnection("test");
		tableSearchFilters.setFullTableName("test.test");
		this.sut = new TableSearchFiltersVisitor(tableSearchFilters);
		this.connectionList = this.userHomeContext.getUserHome().getConnections();
		this.connectionWrapper = connectionList.createAndAddNew("test");
		this.tableList = this.connectionWrapper.getTables();
		this.tableWrapper = tableList.createAndAddNew(PhysicalTableName.fromSchemaTableFilter("test.test"));
		this.tableSpec = tableWrapper.getSpec();
		this.hierarchyNodeArrayList = new ArrayList<>();
    }

    @Test
    void acceptConnectionList_whenCalledForConnectionList_thenReturnsTraverseChildren() {
		this.tableSearchFilters.setConnection("test2");
		this.sut = new TableSearchFiltersVisitor(this.tableSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionList, new SearchParameterObject(hierarchyNodeArrayList));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptConnectionList_whenCalledForConnectionListWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionList, new SearchParameterObject(hierarchyNodeArrayList));
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptConnectionWrapper_whenCalledForConnectionWrapper_thenReturnsSkipChildren() {
		this.tableSearchFilters.setConnection("test2");
		this.sut = new TableSearchFiltersVisitor(this.tableSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionWrapper, new SearchParameterObject(hierarchyNodeArrayList));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptConnectionWrapper_whenCalledForConnectionWrapperWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionWrapper, new SearchParameterObject(hierarchyNodeArrayList));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableList_whenCalledForTableList_thenReturnsTraverseChildren() {
		this.tableSearchFilters.setFullTableName("test2.test2");
		this.sut = new TableSearchFiltersVisitor(this.tableSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableList, new SearchParameterObject(hierarchyNodeArrayList));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableList_whenCalledForTableListWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableList, new SearchParameterObject(hierarchyNodeArrayList));
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableWrapper_whenCalledForTableWrapper_thenReturnsSkipChildren() {
		this.tableSearchFilters.setFullTableName("test2.test2");
		this.sut = new TableSearchFiltersVisitor(this.tableSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableWrapper, new SearchParameterObject(hierarchyNodeArrayList));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptTableWrapper_whenCalledForTableWrapperWithFilterObject_thenReturnNotSkipChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableWrapper, new SearchParameterObject(hierarchyNodeArrayList));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptTableSpec_whenCalledForTableSpecDisabled_thenReturnSkipChildren() {
		this.tableSpec.setDisabled(true);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableWrapper, new SearchParameterObject(hierarchyNodeArrayList));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptTableSpec_whenCalledForTableSpecWithEnabled_thenReturnSkipChildren() {
		this.tableSearchFilters.setEnabled(true);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableWrapper, new SearchParameterObject(hierarchyNodeArrayList));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptTableSpec_whenCalledForTableSpecWithoutEnabledAndDisabled_thenReturnSkipChildren() {
		this.tableSearchFilters.setEnabled(false);
		this.tableSpec.setDisabled(false);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableWrapper, new SearchParameterObject(hierarchyNodeArrayList));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }
}
