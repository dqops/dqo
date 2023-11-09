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
import com.dqops.checks.AbstractCheckCategorySpec;
import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.AbstractRootChecksContainerSpec;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.column.profiling.ColumnNullsProfilingChecksSpec;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.metadata.id.HierarchyId;
import com.dqops.metadata.id.HierarchyNode;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.traversal.TreeNodeTraversalResult;
import com.dqops.rules.comparison.MaxCountRule10ParametersSpec;
import com.dqops.rules.comparison.MinCountRule1ParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CheckSearchFiltersVisitorTests extends BaseTest {
    CheckSearchFiltersVisitor sut;
    ConnectionList connectionList;
    ConnectionWrapper connectionWrapper;
    TableList tableList;
    TableWrapper tableWrapper;
    TableSpec tableSpec;
    ColumnSpecMap columnSpecMap;
    ColumnSpec columnSpec;
    AbstractCheckSpec abstractCheckSpec;
    CheckSearchFilters checkSearchFilters;
    UserHomeContext userHomeContext;

    @BeforeEach
    void setUp() {
		this.userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.checkSearchFilters = new CheckSearchFilters();
		checkSearchFilters.setConnection("test");
		checkSearchFilters.setFullTableName("test.test");
		checkSearchFilters.setColumn("test");
		this.sut = new CheckSearchFiltersVisitor(checkSearchFilters);

		this.connectionList = this.userHomeContext.getUserHome().getConnections();
		this.connectionWrapper = connectionList.createAndAddNew("test");
		this.tableList = this.connectionWrapper.getTables();
		this.tableWrapper = tableList.createAndAddNew(PhysicalTableName.fromSchemaTableFilter("test.test"));
		this.tableSpec = tableWrapper.getSpec();
		this.columnSpecMap = this.tableSpec.getColumns();
		this.columnSpec = new ColumnSpec();
		this.columnSpecMap.put("test", columnSpec);
        this.abstractCheckSpec = new TableRowCountCheckSpec();
    }

    @Test
    void acceptConnectionList_whenCalledForConnectionList_thenReturnsTraverseChildren() {
		this.checkSearchFilters.setConnection("test2");
		this.sut = new CheckSearchFiltersVisitor(this.checkSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionList, new SearchParameterObject());
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptConnectionList_whenCalledForConnectionListWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionList, new SearchParameterObject());
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptConnectionWrapper_whenCalledForConnectionWrapper_thenReturnsSkipChildren() {
		this.checkSearchFilters.setConnection("test2");
		this.sut = new CheckSearchFiltersVisitor(this.checkSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionWrapper, new SearchParameterObject());
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptConnectionWrapper_whenCalledForConnectionWrapperWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.connectionWrapper, new SearchParameterObject());
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableList_whenCalledForTableList_thenReturnsTraverseChildren() {
		this.checkSearchFilters.setFullTableName("test2.test2");
		this.sut = new CheckSearchFiltersVisitor(this.checkSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableList, new SearchParameterObject());
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableList_whenCalledForTableListWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableList, new SearchParameterObject());
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableWrapper_whenCalledForTableWrapper_thenReturnsSkipChildren() {
		this.checkSearchFilters.setFullTableName("test2.test2");
		this.sut = new CheckSearchFiltersVisitor(this.checkSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableWrapper, new SearchParameterObject());
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptTableWrapper_whenCalledForTableWrapperWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableWrapper, new SearchParameterObject());
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableSpec_whenCalledForTableSpecDisabled_thenReturnSkipChildren() {
		this.tableSpec.setDisabled(true);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableSpec, new SearchParameterObject());
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptTableSpec_whenCalledForTableSpecWithEnabled_thenReturnTraverseChildren() {
		this.checkSearchFilters.setEnabled(true);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableSpec, new SearchParameterObject());
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptTableSpec_whenCalledForTableSpecWithoutEnabledAndDisabled_thenReturnSkipChildren() {
		this.checkSearchFilters.setEnabled(false);
		this.tableSpec.setDisabled(false);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableSpec, new SearchParameterObject());
        // We want to get disabled checks. If table is enabled, it still can contain disabled checks.
        Assertions.assertEquals(TreeNodeTraversalResult.TRAVERSE_CHILDREN, treeNodeTraversalResult);
    }

    @Test
    void acceptColumnSpecMap_whenCalledForColumnSpecMap_thenReturnsTraverseChildren() {
		this.checkSearchFilters.setColumn("test2");
		this.sut = new CheckSearchFiltersVisitor(this.checkSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpecMap, new SearchParameterObject());
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptColumnSpecMap_whenCalledForColumnSpecMapWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpecMap, new SearchParameterObject());
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptColumnSpec_whenCalledForColumnSpecDisabled_thenReturnSkipChildren() {
		this.columnSpec.setDisabled(true);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpec, new SearchParameterObject());
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptColumnSpec_whenCalledForColumnSpecWithEnabled_thenReturnTraverseChildren() {
		this.checkSearchFilters.setEnabled(true);
        HierarchyId hierarchyId = this.columnSpec.getHierarchyId();
		this.columnSpec.setHierarchyId(hierarchyId);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpec, new SearchParameterObject());
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptColumnSpec_whenCalledForColumnSpecWithoutEnabledAndDisabled_thenReturnSkipChildren() {
		this.checkSearchFilters.setEnabled(false);
		this.columnSpec.setDisabled(false);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.columnSpec, new SearchParameterObject());
        // We want to get disabled checks. If column is enabled, it still can contain disabled checks.
        Assertions.assertEquals(TreeNodeTraversalResult.TRAVERSE_CHILDREN, treeNodeTraversalResult);
    }

    @Test
    void acceptAbstractCheck_whenCalledForAbstractCheck_thenReturnSkipChildren() {
        HierarchyId hierarchyId = this.columnSpec.getHierarchyId();
		this.abstractCheckSpec.setHierarchyId(hierarchyId);
        List<HierarchyNode> matchingNodes = new ArrayList<>();
        matchingNodes.add(abstractCheckSpec);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.abstractCheckSpec, new SearchParameterObject(matchingNodes));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    private void structure1Setup() {
        // Check attached to table.
        this.tableSpec.setProfilingChecks(new TableProfilingCheckCategoriesSpec() {{
            setVolume(new TableVolumeProfilingChecksSpec() {{
                setProfileRowCount(new TableRowCountCheckSpec() {{
                    setError(new MinCountRule1ParametersSpec(10L));
                }});
            }});
        }});

        // Check attached to column.
        this.columnSpec.setProfilingChecks(new ColumnProfilingCheckCategoriesSpec() {{
            setNulls(new ColumnNullsProfilingChecksSpec() {{
                setProfileNullsCount(new ColumnNullsCountCheckSpec() {{
                    setError(new MaxCountRule10ParametersSpec(20L));
                }});
            }});
        }});
    }
    @Test
    void acceptTableSpec_whenCalledForSelectedColumnCheck_thenTraverseChildren() {
        this.structure1Setup();

        SearchParameterObject searchParameterObject = new SearchParameterObject();
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.tableSpec, searchParameterObject);
        Assertions.assertEquals(TreeNodeTraversalResult.TRAVERSE_CHILDREN, treeNodeTraversalResult);
        Assertions.assertTrue(searchParameterObject.getNodes().isEmpty());
    }

    @Test
    void acceptAbstractRootCheckContainerSpec_whenCalledForSelectedColumnCheckOnTable_thenSkipChildrenReturnEmpty() {
        this.structure1Setup();
        AbstractRootChecksContainerSpec tableCheckContainer = this.tableSpec.getTableCheckRootContainer(CheckType.profiling, null, false);

        SearchParameterObject searchParameterObject = new SearchParameterObject();
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(tableCheckContainer, searchParameterObject);
        Assertions.assertEquals(TreeNodeTraversalResult.SKIP_CHILDREN, treeNodeTraversalResult);
        Assertions.assertTrue(searchParameterObject.getNodes().isEmpty());
    }

    @Test
    void acceptAbstractRootCheckContainerSpec_whenCalledForSelectedColumnCheckOnColumn_thenTraverseChildren() {
        this.structure1Setup();
        AbstractRootChecksContainerSpec columnCheckContainer = this.columnSpec.getColumnCheckRootContainer(CheckType.profiling, null, false);

        SearchParameterObject searchParameterObject = new SearchParameterObject();
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(columnCheckContainer, searchParameterObject);
        Assertions.assertEquals(TreeNodeTraversalResult.TRAVERSE_CHILDREN, treeNodeTraversalResult);
        Assertions.assertTrue(searchParameterObject.getNodes().isEmpty());
    }

    @Test
    void acceptAbstractCheckCategorySpec_whenCalledForSelectedColumnCheckOnColumn_thenTraverseChildren() {
        this.structure1Setup();
        AbstractCheckCategorySpec columnCheckCategorySpec = this.columnSpec.getProfilingChecks().getNulls();

        SearchParameterObject searchParameterObject = new SearchParameterObject();
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(columnCheckCategorySpec, searchParameterObject);
        Assertions.assertEquals(TreeNodeTraversalResult.TRAVERSE_CHILDREN, treeNodeTraversalResult);
        Assertions.assertTrue(searchParameterObject.getNodes().isEmpty());
    }

    @Test
    void acceptAbstractCheckSpec_whenCalledForSelectedColumnCheckOnColumn_thenSkipChildrenReturnCheck() {
        this.structure1Setup();
        AbstractCheckSpec columnCheckSpec = this.columnSpec.getProfilingChecks().getNulls().getProfileNullsCount();

        SearchParameterObject searchParameterObject = new SearchParameterObject();
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(columnCheckSpec, searchParameterObject);

        Assertions.assertEquals(TreeNodeTraversalResult.SKIP_CHILDREN, treeNodeTraversalResult);
        Assertions.assertIterableEquals(new ArrayList<>() {{add(columnCheckSpec);}}, searchParameterObject.getNodes());
    }
}
