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
import com.dqops.metadata.definitions.sensors.SensorDefinitionList;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
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
public class SensorDefinitionSearchFiltersVisitorTests extends BaseTest {
    SensorDefinitionSearchFiltersVisitor sut;
    SensorDefinitionList sensorDefinitionList;
    SensorDefinitionWrapper sensorDefinitionWrapper;
    SensorDefinitionSearchFilters sensorDefinitionSearchFilters;
    UserHomeContext userHomeContext;
	ArrayList<HierarchyNode> sensorDefinitionWrappers;

    @BeforeEach
    void setUp() {
		this.userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		this.sensorDefinitionSearchFilters = new SensorDefinitionSearchFilters();
		this.sensorDefinitionSearchFilters.setSensorName("test");
		this.sut = new SensorDefinitionSearchFiltersVisitor(this.sensorDefinitionSearchFilters);
		this.sensorDefinitionList = this.userHomeContext.getUserHome().getSensors();
		this.sensorDefinitionWrapper = sensorDefinitionList.createAndAddNew("test");
		this.sensorDefinitionWrappers = new ArrayList<>();
    }

    @Test
    void acceptSensorDefinitionList_whenCalledForSensorDefinitionList_thenReturnsTraverseChildren() {
		this.sensorDefinitionSearchFilters.setSensorName("test2");
		this.sut = new SensorDefinitionSearchFiltersVisitor(this.sensorDefinitionSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.sensorDefinitionList, new SearchParameterObject(sensorDefinitionWrappers, null, null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptSensorDefinitionList_whenCalledForSensorDefinitionListWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.sensorDefinitionList, new SearchParameterObject(sensorDefinitionWrappers, null, null));
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptSensorDefinitionWrapper_whenCalledForSensorDefinitionWrapper_thenReturnsSkipChildren() {
		this.sensorDefinitionSearchFilters.setSensorName("test2");
		this.sut = new SensorDefinitionSearchFiltersVisitor(this.sensorDefinitionSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.sensorDefinitionWrapper, new SearchParameterObject(sensorDefinitionWrappers, null, null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptSensorDefinitionWrapper_whenCalledForSensorDefinitionWrapperWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.sensorDefinitionWrapper, new SearchParameterObject(sensorDefinitionWrappers, null, null));
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }
}
