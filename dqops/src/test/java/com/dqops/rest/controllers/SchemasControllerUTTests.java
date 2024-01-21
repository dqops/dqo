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
package com.dqops.rest.controllers;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTarget;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.checkspecs.numeric.ColumnNegativeCountCheckSpec;
import com.dqops.checks.column.checkspecs.text.ColumnTextLengthAboveMaxLengthCheckSpec;
import com.dqops.checks.column.profiling.ColumnNumericProfilingChecksSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.column.profiling.ColumnTextProfilingChecksSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.core.principal.DqoUserPrincipalObjectMother;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.execution.ExecutionContextFactoryImpl;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.HierarchyNodeTreeSearcherImpl;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rules.comparison.*;
import com.dqops.services.check.CheckFlatConfigurationFactory;
import com.dqops.services.check.CheckFlatConfigurationFactoryImpl;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.services.check.mapping.AllChecksModelFactory;
import com.dqops.services.check.mapping.AllChecksModelFactoryImpl;
import com.dqops.services.check.models.CheckConfigurationModel;
import com.dqops.services.metadata.SchemaService;
import com.dqops.services.metadata.SchemaServiceImpl;
import com.dqops.utils.reflection.ReflectionService;
import com.dqops.utils.reflection.ReflectionServiceSingleton;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class SchemasControllerUTTests extends BaseTest {
    private SchemasController sut;
    private UserHomeContextFactory userHomeContextFactory;
    private UserDomainIdentity userDomainIdentity;

    @BeforeEach
    void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();

        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        ExecutionContextFactory executionContextFactory = new ExecutionContextFactoryImpl(userHomeContextFactory, dqoHomeContextFactory);
        HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl());
        ReflectionService reflectionService = ReflectionServiceSingleton.getInstance();
        this.userDomainIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                reflectionService, new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        AllChecksModelFactory allChecksModelFactory = new AllChecksModelFactoryImpl(executionContextFactory, hierarchyNodeTreeSearcher, specToUiCheckMappingService);
        CheckFlatConfigurationFactory checkFlatConfigurationFactory = new CheckFlatConfigurationFactoryImpl(allChecksModelFactory);

        SchemaService schemaService = new SchemaServiceImpl(userHomeContextFactory, allChecksModelFactory, checkFlatConfigurationFactory);
        this.sut = new SchemasController(schemaService, this.userHomeContextFactory);
    }

    private ColumnSpec createColumn(String type, boolean nullable) {
        ColumnSpec col = new ColumnSpec();
        ColumnTypeSnapshotSpec columnTypeSnapshotSpec = new ColumnTypeSnapshotSpec();
        columnTypeSnapshotSpec.setColumnType(type);
        columnTypeSnapshotSpec.setNullable(nullable);
        col.setTypeSnapshot(columnTypeSnapshotSpec);
        return col;
    }

    private UserHomeContext createHierarchyTree() {
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome(this.userDomainIdentity);
        UserHome userHome = userHomeContext.getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("conn");
        TableWrapper table1 = connectionWrapper.getTables().createAndAddNew(
                new PhysicalTableName("sch", "tab1"));
        TableWrapper table2 = connectionWrapper.getTables().createAndAddNew(
                new PhysicalTableName("sch", "tab2"));

        ColumnSpec col11 = createColumn("string", true);
        ColumnSpec col21 = createColumn("datetime", false);
        ColumnSpec col22 = createColumn("numeric", true);
        ColumnSpec col23 = createColumn("numeric", false);

        table1.getSpec().getColumns().put("col1", col11);
        table2.getSpec().getColumns().put("col1", col21);
        table2.getSpec().getColumns().put("col2", col22);
        table2.getSpec().getColumns().put("col3", col23);

        TableProfilingCheckCategoriesSpec t1categoriesSpec = new TableProfilingCheckCategoriesSpec();
        TableVolumeProfilingChecksSpec t1volumeChecksSpec = new TableVolumeProfilingChecksSpec();
        TableRowCountCheckSpec t1rowCountSpec = new TableRowCountCheckSpec();
        MinCountRule1ParametersSpec t1rowCountErrorSpec = new MinCountRule1ParametersSpec();
        MinCountRule1ParametersSpec t1rowCountFatalSpec = new MinCountRule1ParametersSpec();
        t1rowCountErrorSpec.setMinCount(50L);
        t1rowCountFatalSpec.setMinCount(20L);
        t1rowCountSpec.setError(t1rowCountErrorSpec);
        t1rowCountSpec.setFatal(t1rowCountFatalSpec);
        t1volumeChecksSpec.setProfileRowCount(t1rowCountSpec);
        t1categoriesSpec.setVolume(t1volumeChecksSpec);
        table1.getSpec().setProfilingChecks(t1categoriesSpec);

        TableProfilingCheckCategoriesSpec t2categoriesSpec = new TableProfilingCheckCategoriesSpec();
        TableVolumeProfilingChecksSpec t2volumeChecksSpec = new TableVolumeProfilingChecksSpec();
        TableRowCountCheckSpec t2rowCountSpec = new TableRowCountCheckSpec();
        MinCountRule1ParametersSpec t2rowCountErrorSpec = new MinCountRule1ParametersSpec();
        MinCountRule1ParametersSpec t2rowCountFatalSpec = new MinCountRule1ParametersSpec();
        t2rowCountErrorSpec.setMinCount(100L);
        t2rowCountFatalSpec.setMinCount(10L);
        t2rowCountSpec.setError(t2rowCountErrorSpec);
        t2rowCountSpec.setFatal(t2rowCountFatalSpec);
        t2volumeChecksSpec.setProfileRowCount(t2rowCountSpec);
        t2categoriesSpec.setVolume(t2volumeChecksSpec);
        table2.getSpec().setProfilingChecks(t2categoriesSpec);

        ColumnProfilingCheckCategoriesSpec col21categoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        ColumnTextProfilingChecksSpec col21stringChecksSpec = new ColumnTextProfilingChecksSpec();
        ColumnTextLengthAboveMaxLengthCheckSpec col21stringLengthAboveCheckSpec = new ColumnTextLengthAboveMaxLengthCheckSpec();
        MaxCountRule0ErrorParametersSpec countRule0ParametersSpec = new MaxCountRule0ErrorParametersSpec();
        countRule0ParametersSpec.setMaxCount(40L);
        MaxCountRule100ParametersSpec countRule0ParametersSpec1 = new MaxCountRule100ParametersSpec();
        countRule0ParametersSpec1.setMaxCount(100L);
        col21stringLengthAboveCheckSpec.setError(countRule0ParametersSpec);
        col21stringLengthAboveCheckSpec.setFatal(countRule0ParametersSpec1);
        col21stringChecksSpec.setProfileTextLengthAboveMaxLength(col21stringLengthAboveCheckSpec);
        col21categoriesSpec.setText(col21stringChecksSpec);
        col21.setProfilingChecks(col21categoriesSpec);

        ColumnProfilingCheckCategoriesSpec col23categoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        col23.setProfilingChecks(col23categoriesSpec);
        ColumnNumericProfilingChecksSpec col23numericChecksSpec = new ColumnNumericProfilingChecksSpec();
        col23categoriesSpec.setNumeric(col23numericChecksSpec);
        ColumnNegativeCountCheckSpec columnNegativeCountCheckSpec = new ColumnNegativeCountCheckSpec();
        col23numericChecksSpec.setProfileNegativeValues(columnNegativeCountCheckSpec);
        MaxCountRule0WarningParametersSpec col23max1 = new MaxCountRule0WarningParametersSpec();
        col23max1.setMaxCount(15L);
        columnNegativeCountCheckSpec.setWarning(col23max1);

        userHomeContext.flush();
        return userHomeContext;
    }

    @Test
    void getSchemaProfilingChecksModel_whenAllChecksRequested_thenReturnsAllProfilingChecks() {
        UserHomeContext userHomeContext = this.createHierarchyTree();
        String connectionName = "conn";
        String schemaName = "sch";

        ResponseEntity<Flux<CheckConfigurationModel>> responseEntity = this.sut.getSchemaProfilingChecksModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                connectionName,
                schemaName,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(true),
                Optional.empty());
        Assertions.assertNotNull(responseEntity.getBody());

        List<CheckConfigurationModel> result = responseEntity.getBody().toStream().collect(Collectors.toList());
        Assertions.assertNotNull(result);

        List<CheckConfigurationModel> resultAllTables = result.stream()
                .filter(checkConfigurationModel -> checkConfigurationModel.getCheckTarget() == CheckTarget.table)
                .collect(Collectors.toList());
        Assertions.assertNotNull(resultAllTables);
        Assertions.assertEquals(2, resultAllTables.size());
        resultAllTables.forEach(Assertions::assertNotNull);
        resultAllTables.forEach(c -> Assertions.assertEquals(CheckType.profiling, c.getCheckType()));

        List<CheckConfigurationModel> resultAllColumns = result.stream()
                .filter(checkConfigurationModel -> checkConfigurationModel.getCheckTarget() == CheckTarget.column)
                .collect(Collectors.toList());
        Assertions.assertNotNull(resultAllColumns);
        Assertions.assertEquals(2, resultAllColumns.size());
        resultAllColumns.forEach(Assertions::assertNotNull);
        resultAllColumns.forEach(c -> Assertions.assertEquals(CheckType.profiling, c.getCheckType()));

        Assertions.assertEquals(1, resultAllColumns.stream().map(CheckConfigurationModel::getTableName).distinct().count());

        Assertions.assertEquals(1, result.stream().filter(c -> c.getTableName().equals("tab1")).count());
        Assertions.assertEquals(3, result.stream().filter(c -> c.getTableName().equals("tab2")).count());
    }

    @Test
    void getSchemaProfilingChecksModel_whenColumnChecksRequested_thenReturnsColumnProfilingChecks() {
        UserHomeContext userHomeContext = this.createHierarchyTree();
        String connectionName = "conn";
        String schemaName = "sch";

        ResponseEntity<Flux<CheckConfigurationModel>> responseEntity = this.sut.getSchemaProfilingChecksModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                connectionName,
                schemaName,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(CheckTarget.column),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(true),
                Optional.empty());
        Assertions.assertNotNull(responseEntity.getBody());

        List<CheckConfigurationModel> result = responseEntity.getBody().toStream().collect(Collectors.toList());
        Assertions.assertNotNull(result);

        List<CheckConfigurationModel> resultAllTables = result.stream()
                .filter(checkConfigurationModel -> checkConfigurationModel.getCheckTarget() == CheckTarget.table)
                .collect(Collectors.toList());
        Assertions.assertNotNull(resultAllTables);
        Assertions.assertEquals(0, resultAllTables.size());

        List<CheckConfigurationModel> resultAllColumns = result.stream()
                .filter(checkConfigurationModel -> checkConfigurationModel.getCheckTarget() == CheckTarget.column)
                .collect(Collectors.toList());
        Assertions.assertNotNull(resultAllColumns);
        Assertions.assertEquals(2, resultAllColumns.size());
        resultAllColumns.forEach(Assertions::assertNotNull);
        resultAllColumns.forEach(c -> Assertions.assertEquals(CheckType.profiling, c.getCheckType()));

        Assertions.assertEquals(1, resultAllColumns.stream().map(CheckConfigurationModel::getTableName).distinct().count());

        Assertions.assertEquals(0, resultAllColumns.stream().filter(c -> c.getTableName().equals("tab1")).count());
        Assertions.assertEquals(2, resultAllColumns.stream().filter(c -> c.getTableName().equals("tab2")).count());
    }

    @Test
    void getSchemaProfilingChecksModel_whenColumnChecksOnNumericColumnsRequested_thenReturnsCorrectProfilingChecks() {
        UserHomeContext userHomeContext = this.createHierarchyTree();
        String connectionName = "conn";
        String schemaName = "sch";

        ResponseEntity<Flux<CheckConfigurationModel>> responseEntity = this.sut.getSchemaProfilingChecksModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                connectionName,
                schemaName,
                Optional.empty(),
                Optional.empty(),
                Optional.of("numeric"),
                Optional.of(CheckTarget.column),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(true),
                Optional.empty());
        Assertions.assertNotNull(responseEntity.getBody());

        List<CheckConfigurationModel> result = responseEntity.getBody().toStream().collect(Collectors.toList());
        Assertions.assertNotNull(result);

        List<CheckConfigurationModel> resultAllTables = result.stream()
                .filter(checkConfigurationModel -> checkConfigurationModel.getCheckTarget() == CheckTarget.table)
                .collect(Collectors.toList());
        Assertions.assertNotNull(resultAllTables);
        Assertions.assertEquals(0, resultAllTables.size());

        List<CheckConfigurationModel> resultAllColumns = result.stream()
                .filter(checkConfigurationModel -> checkConfigurationModel.getCheckTarget() == CheckTarget.column)
                .collect(Collectors.toList());
        Assertions.assertNotNull(resultAllColumns);
        Assertions.assertEquals(1, resultAllColumns.size());
        resultAllColumns.forEach(Assertions::assertNotNull);
        resultAllColumns.forEach(c -> Assertions.assertEquals(CheckType.profiling, c.getCheckType()));

        Assertions.assertEquals(1, resultAllColumns.stream().map(CheckConfigurationModel::getTableName).distinct().count());

        Assertions.assertIterableEquals(
                Stream.of("col3").collect(Collectors.toList()),
                resultAllColumns.stream().map(CheckConfigurationModel::getColumnName).sorted().collect(Collectors.toList())
        );
    }
}
