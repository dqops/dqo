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
package ai.dqo.rest.controllers;

import ai.dqo.BaseTest;
import ai.dqo.checks.CheckTarget;
import ai.dqo.checks.CheckType;
import ai.dqo.checks.column.checkspecs.numeric.ColumnNegativeCountCheckSpec;
import ai.dqo.checks.column.checkspecs.strings.ColumnStringLengthAboveMaxLengthCountCheckSpec;
import ai.dqo.checks.column.profiling.ColumnNumericProfilingChecksSpec;
import ai.dqo.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import ai.dqo.checks.column.profiling.ColumnStringsProfilingChecksSpec;
import ai.dqo.checks.column.recurring.ColumnDailyRecurringCheckCategoriesSpec;
import ai.dqo.checks.column.recurring.ColumnRecurringChecksRootSpec;
import ai.dqo.checks.column.recurring.numeric.ColumnNumericDailyRecurringChecksSpec;
import ai.dqo.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import ai.dqo.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import ai.dqo.checks.table.profiling.TableVolumeProfilingChecksSpec;
import ai.dqo.execution.ExecutionContextFactory;
import ai.dqo.execution.ExecutionContextFactoryImpl;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcher;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcherImpl;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rules.comparison.*;
import ai.dqo.services.check.CheckFlatConfigurationFactory;
import ai.dqo.services.check.CheckFlatConfigurationFactoryImpl;
import ai.dqo.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import ai.dqo.services.check.mapping.AllChecksModelFactory;
import ai.dqo.services.check.mapping.AllChecksModelFactoryImpl;
import ai.dqo.services.check.mapping.models.AllChecksModel;
import ai.dqo.services.check.mapping.models.CheckContainerModel;
import ai.dqo.services.check.mapping.models.CheckContainerTypeModel;
import ai.dqo.services.check.mapping.models.column.AllColumnChecksModel;
import ai.dqo.services.check.mapping.models.column.ColumnChecksModel;
import ai.dqo.services.check.mapping.models.column.TableColumnChecksModel;
import ai.dqo.services.check.mapping.models.table.AllTableChecksModel;
import ai.dqo.services.check.mapping.models.table.SchemaTableChecksModel;
import ai.dqo.services.check.mapping.models.table.TableChecksModel;
import ai.dqo.services.check.models.CheckConfigurationModel;
import ai.dqo.services.metadata.SchemaService;
import ai.dqo.services.metadata.SchemaServiceImpl;
import ai.dqo.utils.reflection.ReflectionService;
import ai.dqo.utils.reflection.ReflectionServiceSingleton;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class SchemasControllerUTTests extends BaseTest {
    private SchemasController sut;
    private UserHomeContextFactory userHomeContextFactory;

    @BeforeEach
    void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();

        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        ExecutionContextFactory executionContextFactory = new ExecutionContextFactoryImpl(userHomeContextFactory, dqoHomeContextFactory);
        HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl());
        ReflectionService reflectionService = ReflectionServiceSingleton.getInstance();

        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(reflectionService, new SensorDefinitionFindServiceImpl());
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
        UserHomeContext userHomeContext = this.userHomeContextFactory.openLocalUserHome();
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
        MinCountRule0ParametersSpec t1rowCountErrorSpec = new MinCountRule0ParametersSpec();
        MinCountRuleFatalParametersSpec t1rowCountFatalSpec = new MinCountRuleFatalParametersSpec();
        t1rowCountErrorSpec.setMinCount(50L);
        t1rowCountFatalSpec.setMinCount(20L);
        t1rowCountSpec.setError(t1rowCountErrorSpec);
        t1rowCountSpec.setFatal(t1rowCountFatalSpec);
        t1volumeChecksSpec.setRowCount(t1rowCountSpec);
        t1categoriesSpec.setVolume(t1volumeChecksSpec);
        table1.getSpec().setProfilingChecks(t1categoriesSpec);

        TableProfilingCheckCategoriesSpec t2categoriesSpec = new TableProfilingCheckCategoriesSpec();
        TableVolumeProfilingChecksSpec t2volumeChecksSpec = new TableVolumeProfilingChecksSpec();
        TableRowCountCheckSpec t2rowCountSpec = new TableRowCountCheckSpec();
        MinCountRule0ParametersSpec t2rowCountErrorSpec = new MinCountRule0ParametersSpec();
        MinCountRuleFatalParametersSpec t2rowCountFatalSpec = new MinCountRuleFatalParametersSpec();
        t2rowCountErrorSpec.setMinCount(100L);
        t2rowCountFatalSpec.setMinCount(10L);
        t2rowCountSpec.setError(t2rowCountErrorSpec);
        t2rowCountSpec.setFatal(t2rowCountFatalSpec);
        t2volumeChecksSpec.setRowCount(t2rowCountSpec);
        t2categoriesSpec.setVolume(t2volumeChecksSpec);
        table2.getSpec().setProfilingChecks(t2categoriesSpec);

        ColumnProfilingCheckCategoriesSpec col21categoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        ColumnStringsProfilingChecksSpec col21stringChecksSpec = new ColumnStringsProfilingChecksSpec();
        ColumnStringLengthAboveMaxLengthCountCheckSpec col21stringLengthAboveCheckSpec = new ColumnStringLengthAboveMaxLengthCountCheckSpec();
        MaxCountRule10ParametersSpec countRule0ParametersSpec = new MaxCountRule10ParametersSpec();
        countRule0ParametersSpec.setMaxCount(40L);
        MaxCountRule15ParametersSpec countRule0ParametersSpec1 = new MaxCountRule15ParametersSpec();
        countRule0ParametersSpec1.setMaxCount(100L);
        col21stringLengthAboveCheckSpec.setError(countRule0ParametersSpec);
        col21stringLengthAboveCheckSpec.setFatal(countRule0ParametersSpec1);
        col21stringChecksSpec.setStringLengthAboveMaxLengthCount(col21stringLengthAboveCheckSpec);
        col21categoriesSpec.setStrings(col21stringChecksSpec);
        col21.setProfilingChecks(col21categoriesSpec);

        ColumnProfilingCheckCategoriesSpec col23categoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        col23.setProfilingChecks(col23categoriesSpec);
        ColumnNumericProfilingChecksSpec col23numericChecksSpec = new ColumnNumericProfilingChecksSpec();
        col23categoriesSpec.setNumeric(col23numericChecksSpec);
        ColumnNegativeCountCheckSpec columnNegativeCountCheckSpec = new ColumnNegativeCountCheckSpec();
        col23numericChecksSpec.setNegativeCount(columnNegativeCountCheckSpec);
        MaxCountRule0ParametersSpec col23max1 = new MaxCountRule0ParametersSpec();
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
                connectionName,
                schemaName,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(true));
        Assertions.assertNotNull(responseEntity.getBody());

        List<CheckConfigurationModel> result = responseEntity.getBody().toStream().collect(Collectors.toList());
        Assertions.assertNotNull(result);

        List<CheckConfigurationModel> resultAllTables = result.stream()
                .filter(checkConfigurationModel -> checkConfigurationModel.getCheckTarget() == CheckTarget.table)
                .collect(Collectors.toList());
        Assertions.assertNotNull(resultAllTables);
        Assertions.assertEquals(2, resultAllTables.size());
        resultAllTables.forEach(Assertions::assertNotNull);
        resultAllTables.forEach(c -> Assertions.assertEquals(CheckType.PROFILING, c.getCheckType()));

        List<CheckConfigurationModel> resultAllColumns = result.stream()
                .filter(checkConfigurationModel -> checkConfigurationModel.getCheckTarget() == CheckTarget.column)
                .collect(Collectors.toList());
        Assertions.assertNotNull(resultAllColumns);
        Assertions.assertEquals(2, resultAllColumns.size());
        resultAllColumns.forEach(Assertions::assertNotNull);
        resultAllColumns.forEach(c -> Assertions.assertEquals(CheckType.PROFILING, c.getCheckType()));

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
                connectionName,
                schemaName,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(CheckTarget.column),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(true));
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
        resultAllColumns.forEach(c -> Assertions.assertEquals(CheckType.PROFILING, c.getCheckType()));

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
                connectionName,
                schemaName,
                Optional.empty(),
                Optional.empty(),
                Optional.of("numeric"),
                Optional.of(CheckTarget.column),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(true));
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
        resultAllColumns.forEach(c -> Assertions.assertEquals(CheckType.PROFILING, c.getCheckType()));

        Assertions.assertEquals(1, resultAllColumns.stream().map(CheckConfigurationModel::getTableName).distinct().count());

        Assertions.assertIterableEquals(
                Stream.of("col3").collect(Collectors.toList()),
                resultAllColumns.stream().map(CheckConfigurationModel::getColumnName).sorted().collect(Collectors.toList())
        );
    }
}
