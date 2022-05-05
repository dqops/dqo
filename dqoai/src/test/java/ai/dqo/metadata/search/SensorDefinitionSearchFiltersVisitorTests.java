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
import ai.dqo.metadata.definitions.sensors.SensorDefinitionList;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapper;
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
public class SensorDefinitionSearchFiltersVisitorTests extends BaseTest {
    SensorDefinitionSearchFiltersVisitor sut;
    SensorDefinitionList sensorDefinitionList;
    SensorDefinitionWrapper sensorDefinitionWrapper;
    SensorDefinitionSearchFilters sensorDefinitionSearchFilters;
    UserHomeContext userHomeContext;
	ArrayList<HierarchyNode> sensorDefinitionWrappers;

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
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.sensorDefinitionList, sensorDefinitionWrappers);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptSensorDefinitionList_whenCalledForSensorDefinitionListWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.sensorDefinitionList, sensorDefinitionWrappers);
        Assertions.assertNotEquals(treeNodeTraversalResult, TreeNodeTraversalResult.TRAVERSE_CHILDREN);
    }

    @Test
    void acceptSensorDefinitionWrapper_whenCalledForSensorDefinitionWrapper_thenReturnsSkipChildren() {
		this.sensorDefinitionSearchFilters.setSensorName("test2");
		this.sut = new SensorDefinitionSearchFiltersVisitor(this.sensorDefinitionSearchFilters);
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.sensorDefinitionWrapper, sensorDefinitionWrappers);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }

    @Test
    void acceptSensorDefinitionWrapper_whenCalledForSensorDefinitionWrapperWithFilterObject_thenReturnNotTraverseChildren() {
        TreeNodeTraversalResult treeNodeTraversalResult = this.sut.accept(this.sensorDefinitionWrapper, sensorDefinitionWrappers);
        Assertions.assertEquals(treeNodeTraversalResult, TreeNodeTraversalResult.SKIP_CHILDREN);
    }
}
