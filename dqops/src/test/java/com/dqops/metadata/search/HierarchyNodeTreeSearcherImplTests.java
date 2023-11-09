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
import com.dqops.checks.AbstractCheckSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.metadata.definitions.rules.RuleDefinitionList;
import com.dqops.metadata.definitions.rules.RuleDefinitionSpec;
import com.dqops.metadata.definitions.rules.RuleDefinitionWrapper;
import com.dqops.metadata.definitions.sensors.SensorDefinitionList;
import com.dqops.metadata.definitions.sensors.SensorDefinitionSpec;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalker;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootTest
public class HierarchyNodeTreeSearcherImplTests extends BaseTest {
    HierarchyNodeTreeSearcherImpl sut;
    UserHomeContext userHomeContext;
    ConnectionList connectionList;
    ConnectionWrapper connection;
    TableWrapper table;
    TableSpec tableSpec;
    SensorDefinitionList sensorDefinitionList;
    SensorDefinitionWrapper sensorDefinitionWrapper;
    RuleDefinitionList ruleDefinitionList;
    RuleDefinitionWrapper ruleDefinitionWrapper;

    @BeforeEach
    void setUp() {
        HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
		this.sut = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);
		this.userHomeContext = UserHomeContextObjectMother.createTemporaryFileHomeContext(true);
		connectionList = userHomeContext.getUserHome().getConnections();
		connection = connectionList.createAndAddNew("test");
        PhysicalTableName physicalTableName = new PhysicalTableName("test", "test");
		table = connection.getTables().createAndAddNew(physicalTableName);
		sensorDefinitionList = userHomeContext.getUserHome().getSensors();
		sensorDefinitionWrapper = sensorDefinitionList.createAndAddNew("test");
		ruleDefinitionList = userHomeContext.getUserHome().getRules();
		ruleDefinitionWrapper = ruleDefinitionList.createAndAddNew("test");
		tableSpec = new TableSpec();
    }

    @Test
    void findConnections_whenCalledForAll_thenReturnsCollection() {
        ConnectionSearchFilters connectionSearchFilters = new ConnectionSearchFilters();
        connectionSearchFilters.setConnectionName("*");
        ArrayList<ConnectionSpec> expectedList = new ArrayList<>();
        expectedList.add(connection.getSpec());
        ConnectionWrapper connectionWrapper2 = connectionList.createAndAddNew("test2");
        expectedList.add(connectionWrapper2.getSpec());
        Collection<ConnectionSpec> connectionSpecCollection = this.sut.findConnections(userHomeContext.getUserHome(), connectionSearchFilters);
        Assertions.assertEquals(connectionSpecCollection, expectedList);
    }

    @Test
    void findConnections_whenCalledForNonFilters_thenReturnsAll() {
        ConnectionSearchFilters connectionSearchFilters = new ConnectionSearchFilters();
        ArrayList<ConnectionSpec> expectedList = new ArrayList<>();
        expectedList.add(connection.getSpec());
        ConnectionWrapper connectionWrapper2 = connectionList.createAndAddNew("test2");
        expectedList.add(connectionWrapper2.getSpec());
        Collection<ConnectionSpec> connectionSpecCollection = this.sut.findConnections(userHomeContext.getUserHome(), connectionSearchFilters);
        Assertions.assertEquals(connectionSpecCollection, expectedList);
    }

    @Test
    void findConnections_whenCalledForExistedOne_thenReturnsSingleCollection() {
        ConnectionSearchFilters connectionSearchFilters = new ConnectionSearchFilters();
        connectionSearchFilters.setConnectionName("test");
        ArrayList<ConnectionSpec> expectedList = new ArrayList<>();
        expectedList.add(connection.getSpec());
        Collection<ConnectionSpec> connectionSpecCollection = this.sut.findConnections(userHomeContext.getUserHome(), connectionSearchFilters);
        Assertions.assertEquals(connectionSpecCollection, expectedList);
    }

    @Test
    void findConnections_whenCalledForNotExisted_thenReturnsEmptyCollection() {
        ConnectionSearchFilters connectionSearchFilters = new ConnectionSearchFilters();
        connectionSearchFilters.setConnectionName("testNotExisted");
        ArrayList<ConnectionSpec> expectedList = new ArrayList<>();
        Collection<ConnectionSpec> connectionSpecCollection = this.sut.findConnections(userHomeContext.getUserHome(), connectionSearchFilters);
        Assertions.assertEquals(connectionSpecCollection, expectedList);
    }

    @Test
    void findTables_whenCalledForAll_thenReturnsCollection() {
        TableSearchFilters tableSearchFilters = new TableSearchFilters();
        tableSearchFilters.setFullTableName("*");
        ArrayList<TableWrapper> expectedList = new ArrayList<>();
        expectedList.add(table);
        TableWrapper tableWrapper2 = connection.getTables().createAndAddNew(new PhysicalTableName("test2", "test2"));
        expectedList.add(tableWrapper2);
        Collection<TableWrapper> tableSpecCollection = this.sut.findTables(userHomeContext.getUserHome(), tableSearchFilters);
        Assertions.assertEquals(tableSpecCollection, expectedList);
    }

    @Test
    void findTables_whenCalledForNonFilters_thenReturnsAll() {
        TableSearchFilters tableSearchFilters = new TableSearchFilters();
        ArrayList<TableWrapper> expectedList = new ArrayList<>();
        expectedList.add(table);
        TableWrapper tableWrapper2 = connection.getTables().createAndAddNew(new PhysicalTableName("test2", "test2"));
        expectedList.add(tableWrapper2);
        Collection<TableWrapper> tableSpecCollection = this.sut.findTables(userHomeContext.getUserHome(), tableSearchFilters);
        Assertions.assertEquals(tableSpecCollection, expectedList);
    }

    @Test
    void findTables_whenCalledForExistedOne_thenReturnsSingleCollection() {
        TableSearchFilters tableSearchFilters = new TableSearchFilters();
        tableSearchFilters.setFullTableName("test.test");
        ArrayList<TableWrapper> expectedList = new ArrayList<>();
        expectedList.add(table);
        Collection<TableWrapper> tableSpecCollection = this.sut.findTables(userHomeContext.getUserHome(), tableSearchFilters);
        Assertions.assertEquals(tableSpecCollection, expectedList);
    }

    @Test
    void findTables_whenCalledForNotExisted_thenReturnsEmptyCollection() {
        TableSearchFilters tableSearchFilters = new TableSearchFilters();
        tableSearchFilters.setFullTableName("testNotExisted.testNotExisted");
        ArrayList<TableWrapper> expectedList = new ArrayList<>();
        Collection<TableWrapper> tableSpecCollection = this.sut.findTables(userHomeContext.getUserHome(), tableSearchFilters);
        Assertions.assertEquals(tableSpecCollection, expectedList);
    }

    @Test
    void findColumns_whenCalledForAll_thenReturnsNonEmptyArray() {
        ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
        columnSearchFilters.setColumnName("*");
        ColumnSpecMap columnSpecMap = new ColumnSpecMap();
        ColumnSpec columnSpec = new ColumnSpec();
        columnSpecMap.put("test", columnSpec);
		tableSpec.setColumns(columnSpecMap);
        columnSpecMap.put("test2", columnSpec);
		table.setSpec(tableSpec);
        ArrayList<ColumnSpec> expectedList = new ArrayList<>();
        expectedList.add(columnSpec);
        expectedList.add(columnSpec);
        Collection<ColumnSpec> columnSpecCollection = this.sut.findColumns(userHomeContext.getUserHome(), columnSearchFilters);
        Assertions.assertEquals(columnSpecCollection, expectedList);
    }

    @Test
    void findColumns_whenCalledForNonFilters_thenReturnsAll() {
        ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
        ColumnSpecMap columnSpecMap = new ColumnSpecMap();
        ColumnSpec columnSpec = new ColumnSpec();
        columnSpecMap.put("test", columnSpec);
		tableSpec.setColumns(columnSpecMap);
        columnSpecMap.put("test2", columnSpec);
		table.setSpec(tableSpec);
        ArrayList<ColumnSpec> expectedList = new ArrayList<>();
        expectedList.add(columnSpec);
        expectedList.add(columnSpec);
        Collection<ColumnSpec> columnSpecCollection = this.sut.findColumns(userHomeContext.getUserHome(), columnSearchFilters);
        Assertions.assertEquals(columnSpecCollection, expectedList);
    }

    @Test
    void findColumns_whenCalledForSpecifiedName_thenReturnsNonEmptyArray() {
        ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
        columnSearchFilters.setColumnName("test");
        ColumnSpecMap columnSpecMap = new ColumnSpecMap();
        ColumnSpec columnSpec = new ColumnSpec();
        columnSpecMap.put("test", columnSpec);
		tableSpec.setColumns(columnSpecMap);
		table.setSpec(tableSpec);
        ArrayList<ColumnSpec> expectedList = new ArrayList<>();
        expectedList.add(columnSpec);
        Collection<ColumnSpec> columnSpecCollection = this.sut.findColumns(userHomeContext.getUserHome(), columnSearchFilters);
        Assertions.assertEquals(columnSpecCollection, expectedList);
    }

    @Test
    void findColumns_whenCalledForNotExistingName_thenReturnsEmptyArray() {
        ColumnSearchFilters columnSearchFilters = new ColumnSearchFilters();
        columnSearchFilters.setColumnName("test");
        ArrayList<ColumnSpec> expectedList = new ArrayList<>();
        Collection<ColumnSpec> columnSpecCollection = this.sut.findColumns(userHomeContext.getUserHome(), columnSearchFilters);
        Assertions.assertEquals(columnSpecCollection, expectedList);
    }

    @Test
    void findChecks_whenCalledForAll_thenReturnsNonEmptyArray() {
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
        checkSearchFilters.setCheckName("*");
        TableVolumeProfilingChecksSpec volume = new TableVolumeProfilingChecksSpec();
        tableSpec.getProfilingChecks().setVolume(volume);
        TableRowCountCheckSpec check = new TableRowCountCheckSpec();
        volume.setProfileRowCount(check);
        table.setSpec(tableSpec);
        ArrayList<TableRowCountCheckSpec> expectedList = new ArrayList<>();
        expectedList.add(check);

        Collection<AbstractCheckSpec<?,?,?,?>> checkSpecCollection = this.sut.findChecks(userHomeContext.getUserHome(), checkSearchFilters);
        Assertions.assertEquals(checkSpecCollection, expectedList);
    }

    @Test
    void findChecks_whenCalledForNonFilters_thenReturnsAll() {
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
        TableVolumeProfilingChecksSpec volume = new TableVolumeProfilingChecksSpec();
        tableSpec.getProfilingChecks().setVolume(volume);
        TableRowCountCheckSpec check = new TableRowCountCheckSpec();
        volume.setProfileRowCount(check);
        table.setSpec(tableSpec);
        ArrayList<TableRowCountCheckSpec> expectedList = new ArrayList<>();
        expectedList.add(check);

        Collection<AbstractCheckSpec<?,?,?,?>> checkSpecCollection = this.sut.findChecks(userHomeContext.getUserHome(), checkSearchFilters);
        Assertions.assertEquals(checkSpecCollection, expectedList);
    }

    @Test
    void findChecks_whenCalledForNotExistingName_thenReturnsEmptyArray() {
        CheckSearchFilters checkSearchFilters = new CheckSearchFilters();
        checkSearchFilters.setCheckName("test");
        TableVolumeProfilingChecksSpec volume = new TableVolumeProfilingChecksSpec();
        tableSpec.getProfilingChecks().setVolume(volume);
        TableRowCountCheckSpec check = new TableRowCountCheckSpec();
        volume.setProfileRowCount(check);
		table.setSpec(tableSpec);

        ArrayList<AbstractCheckSpec<?,?,?,?>> expectedList = new ArrayList<>();
        Collection<AbstractCheckSpec<?,?,?,?>> checkSpecCollection = this.sut.findChecks(userHomeContext.getUserHome(), checkSearchFilters);
        Assertions.assertEquals(checkSpecCollection, expectedList);
    }

    @Test
    void findSensors_whenCalledForAll_thenReturnsCollection() {
        SensorDefinitionSearchFilters sensorDefinitionSearchFilters = new SensorDefinitionSearchFilters();
        sensorDefinitionSearchFilters.setSensorName("*");
        ArrayList<SensorDefinitionSpec> expectedList = new ArrayList<>();
        expectedList.add(sensorDefinitionWrapper.getSpec());
        SensorDefinitionWrapper sensorDefinitionWrapper2 = sensorDefinitionList.createAndAddNew("test2");
        expectedList.add(sensorDefinitionWrapper2.getSpec());
        Collection<SensorDefinitionSpec> sensorDefinitionSpecCollection = this.sut.findSensors(userHomeContext.getUserHome(), sensorDefinitionSearchFilters);
        Assertions.assertEquals(sensorDefinitionSpecCollection, expectedList);
    }

    @Test
    void findSensors_whenCalledForNonFilters_thenReturnsAll() {
        SensorDefinitionSearchFilters sensorDefinitionSearchFilters = new SensorDefinitionSearchFilters();
        ArrayList<SensorDefinitionSpec> expectedList = new ArrayList<>();
        expectedList.add(sensorDefinitionWrapper.getSpec());
        SensorDefinitionWrapper sensorDefinitionWrapper2 = sensorDefinitionList.createAndAddNew("test2");
        expectedList.add(sensorDefinitionWrapper2.getSpec());
        Collection<SensorDefinitionSpec> sensorDefinitionSpecCollection = this.sut.findSensors(userHomeContext.getUserHome(), sensorDefinitionSearchFilters);
        Assertions.assertEquals(sensorDefinitionSpecCollection, expectedList);
    }

    @Test
    void findSensors_whenCalledForExistedOne_thenReturnsSingleCollection() {
        SensorDefinitionSearchFilters sensorDefinitionSearchFilters = new SensorDefinitionSearchFilters();
        sensorDefinitionSearchFilters.setSensorName("test");
        ArrayList<SensorDefinitionSpec> expectedList = new ArrayList<>();
        expectedList.add(sensorDefinitionWrapper.getSpec());
        Collection<SensorDefinitionSpec> sensorDefinitionSpecCollection = this.sut.findSensors(userHomeContext.getUserHome(), sensorDefinitionSearchFilters);
        Assertions.assertEquals(sensorDefinitionSpecCollection, expectedList);
    }

    @Test
    void findSensors_whenCalledForNotExisted_thenReturnsEmptyCollection() {
        SensorDefinitionSearchFilters sensorDefinitionSearchFilters = new SensorDefinitionSearchFilters();
        sensorDefinitionSearchFilters.setSensorName("testNotExisted");
        ArrayList<SensorDefinitionSpec> expectedList = new ArrayList<>();
        Collection<SensorDefinitionSpec> sensorDefinitionSpecCollection = this.sut.findSensors(userHomeContext.getUserHome(), sensorDefinitionSearchFilters);
        Assertions.assertEquals(sensorDefinitionSpecCollection, expectedList);
    }

    @Test
    void findRules_whenCalledForAll_thenReturnsCollection() {
        RuleDefinitionSearchFilters ruleDefinitionSearchFilters = new RuleDefinitionSearchFilters();
        ruleDefinitionSearchFilters.setRuleName("*");
        ArrayList<RuleDefinitionSpec> expectedList = new ArrayList<>();
        expectedList.add(ruleDefinitionWrapper.getSpec());
        RuleDefinitionWrapper ruleDefinitionWrapper2 = ruleDefinitionList.createAndAddNew("test2");
        expectedList.add(ruleDefinitionWrapper2.getSpec());
        Collection<RuleDefinitionSpec> ruleDefinitionSpecCollection = this.sut.findRules(userHomeContext.getUserHome(), ruleDefinitionSearchFilters);
        Assertions.assertEquals(ruleDefinitionSpecCollection, expectedList);
    }

    @Test
    void findRules_whenCalledForNonFilters_thenReturnsAll() {
        RuleDefinitionSearchFilters ruleDefinitionSearchFilters = new RuleDefinitionSearchFilters();
        ArrayList<RuleDefinitionSpec> expectedList = new ArrayList<>();
        expectedList.add(ruleDefinitionWrapper.getSpec());
        RuleDefinitionWrapper ruleDefinitionWrapper2 = ruleDefinitionList.createAndAddNew("test2");
        expectedList.add(ruleDefinitionWrapper2.getSpec());
        Collection<RuleDefinitionSpec> ruleDefinitionSpecCollection = this.sut.findRules(userHomeContext.getUserHome(), ruleDefinitionSearchFilters);
        Assertions.assertEquals(ruleDefinitionSpecCollection, expectedList);
    }

    @Test
    void findRules_whenCalledForExistedOne_thenReturnsSingleCollection() {
        RuleDefinitionSearchFilters ruleDefinitionSearchFilters = new RuleDefinitionSearchFilters();
        ruleDefinitionSearchFilters.setRuleName("test");
        ArrayList<RuleDefinitionSpec> expectedList = new ArrayList<>();
        expectedList.add(ruleDefinitionWrapper.getSpec());
        Collection<RuleDefinitionSpec> ruleDefinitionSpecCollection = this.sut.findRules(userHomeContext.getUserHome(), ruleDefinitionSearchFilters);
        Assertions.assertEquals(ruleDefinitionSpecCollection, expectedList);
    }

    @Test
    void findRules_whenCalledForNotExisted_thenReturnsEmptyCollection() {
        RuleDefinitionSearchFilters ruleDefinitionSearchFilters = new RuleDefinitionSearchFilters();
        ruleDefinitionSearchFilters.setRuleName("testNotExisted");
        ArrayList<RuleDefinitionSpec> expectedList = new ArrayList<>();
        Collection<RuleDefinitionSpec> ruleDefinitionSpecCollection = this.sut.findRules(userHomeContext.getUserHome(), ruleDefinitionSearchFilters);
        Assertions.assertEquals(ruleDefinitionSpecCollection, expectedList);
    }
}
