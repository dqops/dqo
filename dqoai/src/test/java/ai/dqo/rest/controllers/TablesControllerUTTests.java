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
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.table.adhoc.TableAdHocCheckCategoriesSpec;
import ai.dqo.checks.table.adhoc.TableAdHocStandardChecksSpec;
import ai.dqo.checks.table.checkpoints.TableCheckpointsSpec;
import ai.dqo.checks.table.checkpoints.TableDailyCheckpointCategoriesSpec;
import ai.dqo.checks.table.checkpoints.standard.TableStandardDailyCheckpointSpec;
import ai.dqo.checks.table.checkspecs.standard.TableRowCountCheckSpec;
import ai.dqo.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.table.partitioned.TablePartitionedChecksRootSpec;
import ai.dqo.checks.table.partitioned.standard.TableStandardDailyPartitionedChecksSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.services.check.mapping.models.UIAllChecksModel;
import ai.dqo.services.check.mapping.basicmodels.UIAllChecksBasicModel;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.services.check.mapping.UiToSpecCheckMappingServiceImpl;
import ai.dqo.rest.models.metadata.TableBasicModel;
import ai.dqo.rest.models.metadata.TableModel;
import ai.dqo.rules.comparison.MinCountRule0ParametersSpec;
import ai.dqo.rules.comparison.MinCountRuleParametersSpec;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.utils.reflection.ReflectionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class TablesControllerUTTests extends BaseTest {
    private TablesController sut;
    private UserHomeContextFactory userHomeContextFactory;
    private UserHomeContext userHomeContext;
    private SampleTableMetadata sampleTable;

    @BeforeEach
    void setUp() {
        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(reflectionService, new SensorDefinitionFindServiceImpl());
        UiToSpecCheckMappingServiceImpl uiToSpecCheckMappingService = new UiToSpecCheckMappingServiceImpl(reflectionService);
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        this.sut = new TablesController(this.userHomeContextFactory, dqoHomeContextFactory, specToUiCheckMappingService, uiToSpecCheckMappingService);
        this.userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        this.sampleTable = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.bigquery);
    }

    @Test
    void getTables_whenSampleConnectionRequested_thenReturnsListOfTables() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Flux<TableBasicModel>> responseEntity = this.sut.getTables(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName());

        List<TableBasicModel> result = responseEntity.getBody().collectList().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        Assertions.assertEquals(this.sampleTable.getTableSpec().getHierarchyId().hashCode64(), result.get(0).getTableHash());
    }

    @Test
    void getTable_whenSampleConnectionRequested_thenReturnsRequestedTable() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec tableSpec = this.sampleTable.getTableSpec();

        ResponseEntity<Mono<TableModel>> responseEntity = this.sut.getTable(
                this.sampleTable.getConnectionName(),
                tableSpec.getTarget().getSchemaName(),
                tableSpec.getTarget().getTableName());

        TableModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(tableSpec.getTarget().getTableName(), result.getSpec().getTarget().getTableName());
        Assertions.assertEquals(this.sampleTable.getConnectionName(), result.getConnectionName());
        Assertions.assertEquals(
                tableSpec.getTarget().toPhysicalTableName(),
                result.getSpec().getTarget().toPhysicalTableName());
        Assertions.assertEquals(tableSpec.getHierarchyId().hashCode64(), result.getSpec().getHierarchyId().hashCode64());
        Assertions.assertSame(tableSpec, result.getSpec());
    }

    @Test
    void getTableBasic_whenTableRequested_thenReturnsRequestedTable() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec tableSpec = this.sampleTable.getTableSpec();

        ResponseEntity<Mono<TableBasicModel>> responseEntity = this.sut.getTableBasic(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName());

        TableBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(tableSpec.getTarget().getTableName(), result.getTarget().getTableName());
        Assertions.assertEquals(this.sampleTable.getConnectionName(), result.getConnectionName());
        Assertions.assertEquals(tableSpec.getTarget().toPhysicalTableName(), result.getTarget().toPhysicalTableName());
        Assertions.assertEquals(tableSpec.getHierarchyId().hashCode64(), result.getTableHash());
    }

    @Test
    void getTableAdHocChecks_whenTableRequested_thenReturnsAdHocChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec tableSpec = this.sampleTable.getTableSpec();

        ResponseEntity<Mono<UIAllChecksModel>> responseEntity = this.sut.getTableAdHocChecksUI(
                this.sampleTable.getConnectionName(),
                tableSpec.getTarget().getSchemaName(),
                tableSpec.getTarget().getTableName());

        UIAllChecksModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.getCategories().size());
    }

    @Test
    void getTableCheckpointsDaily_whenTableRequested_thenReturnsCheckpoints() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRuleParametersSpec minRule1 = new MinCountRuleParametersSpec(10L);
        MinCountRule0ParametersSpec minRule2 = new MinCountRule0ParametersSpec(20L);
        MinCountRuleParametersSpec minRule3 = new MinCountRuleParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);
        
        TableStandardDailyCheckpointSpec standardDailyCheckpointSpec = new TableStandardDailyCheckpointSpec();
        standardDailyCheckpointSpec.setDailyCheckpointRowCount(minRowCountSpec);
        TableDailyCheckpointCategoriesSpec dailyCheckpoint = new TableDailyCheckpointCategoriesSpec();
        dailyCheckpoint.setStandard(standardDailyCheckpointSpec);
        TableCheckpointsSpec sampleCheckpoint = new TableCheckpointsSpec();
        sampleCheckpoint.setDaily(dailyCheckpoint);
        
        this.sampleTable.getTableSpec().setCheckpoints(sampleCheckpoint);

        ResponseEntity<Mono<TableDailyCheckpointCategoriesSpec>> responseEntity = this.sut.getTableCheckpointsDaily(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName());

        TableDailyCheckpointCategoriesSpec result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
    }

    @Test
    void getTablePartitionedChecksDaily_whenTableRequested_thenReturnsPartitionedChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRuleParametersSpec minRule1 = new MinCountRuleParametersSpec(10L);
        MinCountRule0ParametersSpec minRule2 = new MinCountRule0ParametersSpec(20L);
        MinCountRuleParametersSpec minRule3 = new MinCountRuleParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);

        TableStandardDailyPartitionedChecksSpec standardDailyPartitionedCheckSpec = new TableStandardDailyPartitionedChecksSpec();
        standardDailyPartitionedCheckSpec.setDailyPartitionRowCount(minRowCountSpec);
        TableDailyPartitionedCheckCategoriesSpec dailyPartitionedCheck = new TableDailyPartitionedCheckCategoriesSpec();
        dailyPartitionedCheck.setStandard(standardDailyPartitionedCheckSpec);
        TablePartitionedChecksRootSpec samplePartitionedCheck = new TablePartitionedChecksRootSpec();
        samplePartitionedCheck.setDaily(dailyPartitionedCheck);

        this.sampleTable.getTableSpec().setPartitionedChecks(samplePartitionedCheck);

        ResponseEntity<Mono<TableDailyPartitionedCheckCategoriesSpec>> responseEntity = this.sut.getTablePartitionedChecksDaily(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName());

        TableDailyPartitionedCheckCategoriesSpec result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
    }

    @Test
    void getTableAdHocChecksUI_whenTableRequested_thenReturnsAdHocChecksUi() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec tableSpec = this.sampleTable.getTableSpec();

        ResponseEntity<Mono<UIAllChecksModel>> responseEntity = this.sut.getTableAdHocChecksUI(
                this.sampleTable.getConnectionName(),
                tableSpec.getTarget().getSchemaName(),
                tableSpec.getTarget().getTableName());

        UIAllChecksModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.getCategories().size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTableCheckpointsUI_whenTableRequested_thenReturnsCheckpointsUi(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Mono<UIAllChecksModel>> responseEntity = this.sut.getTableCheckpointsUI(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                timePartition);

        UIAllChecksModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.getCategories().size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTablePartitionedChecksUI_whenTableRequested_thenReturnsPartitionedChecksUi(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Mono<UIAllChecksModel>> responseEntity = this.sut.getTablePartitionedChecksUI(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                timePartition);

        UIAllChecksModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.getCategories().size());
    }

    @Test
    void getTableAdHocChecksUIBasic_whenTableRequested_thenReturnsAdHocChecksUiBasic() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec tableSpec = this.sampleTable.getTableSpec();

        ResponseEntity<Mono<UIAllChecksBasicModel>> responseEntity = this.sut.getTableAdHocChecksUIBasic(
                this.sampleTable.getConnectionName(),
                tableSpec.getTarget().getSchemaName(),
                tableSpec.getTarget().getTableName());

        UIAllChecksBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.getCategories().size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTableCheckpointsUIBasic_whenTableRequested_thenReturnsCheckpointsUiBasic(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Mono<UIAllChecksBasicModel>> responseEntity = this.sut.getTableCheckpointsUIBasic(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                timePartition);

        UIAllChecksBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.getCategories().size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTablePartitionedChecksUIBasic_whenTableRequested_thenReturnsPartitionedChecksUiBasic(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Mono<UIAllChecksBasicModel>> responseEntity = this.sut.getTablePartitionedChecksUIBasic(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                timePartition);

        UIAllChecksBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.getCategories().size());
    }
    
    @Test
    void updateTableAdHocChecks_whenTableAndAdHocChecksRequested_updatesAdHocChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRuleParametersSpec minRule1 = new MinCountRuleParametersSpec(10L);
        MinCountRule0ParametersSpec minRule2 = new MinCountRule0ParametersSpec(20L);
        MinCountRuleParametersSpec minRule3 = new MinCountRuleParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);

        TableAdHocStandardChecksSpec standardChecksSpec = new TableAdHocStandardChecksSpec();
        standardChecksSpec.setRowCount(minRowCountSpec);
        TableAdHocCheckCategoriesSpec sampleAdHocCheck = new TableAdHocCheckCategoriesSpec();
        sampleAdHocCheck.setStandard(standardChecksSpec);

        ResponseEntity<Mono<?>> responseEntity = this.sut.updateTableAdHocChecks(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                Optional.of(sampleAdHocCheck));

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(this.sampleTable.getTableSpec().getChecks(), sampleAdHocCheck);
    }

    @Test
    void updateTableCheckpointsDaily_whenTableAndCheckpointsRequested_updatesCheckpoints() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRuleParametersSpec minRule1 = new MinCountRuleParametersSpec(10L);
        MinCountRule0ParametersSpec minRule2 = new MinCountRule0ParametersSpec(20L);
        MinCountRuleParametersSpec minRule3 = new MinCountRuleParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);

        TableStandardDailyCheckpointSpec standardDailyCheckpointSpec = new TableStandardDailyCheckpointSpec();
        standardDailyCheckpointSpec.setDailyCheckpointRowCount(minRowCountSpec);
        TableDailyCheckpointCategoriesSpec dailyCheckpoint = new TableDailyCheckpointCategoriesSpec();
        dailyCheckpoint.setStandard(standardDailyCheckpointSpec);
        TableCheckpointsSpec sampleCheckpoint = new TableCheckpointsSpec();
        sampleCheckpoint.setDaily(dailyCheckpoint);

        ResponseEntity<Mono<?>> responseEntity = this.sut.updateTableCheckpointsDaily(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                Optional.of(sampleCheckpoint.getDaily()));

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(
                this.sampleTable.getTableSpec().getCheckpoints().getDaily(),
                sampleCheckpoint.getDaily());
    }

    @Test
    void updateTablePartitionedChecksDaily_whenTableAndPartitionedChecksRequested_updatesPartitionedChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRuleParametersSpec minRule1 = new MinCountRuleParametersSpec(10L);
        MinCountRule0ParametersSpec minRule2 = new MinCountRule0ParametersSpec(20L);
        MinCountRuleParametersSpec minRule3 = new MinCountRuleParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);

        TableStandardDailyPartitionedChecksSpec standardDailyPartitionedCheckSpec = new TableStandardDailyPartitionedChecksSpec();
        standardDailyPartitionedCheckSpec.setDailyPartitionRowCount(minRowCountSpec);
        TableDailyPartitionedCheckCategoriesSpec dailyPartitionedCheck = new TableDailyPartitionedCheckCategoriesSpec();
        dailyPartitionedCheck.setStandard(standardDailyPartitionedCheckSpec);
        TablePartitionedChecksRootSpec samplePartitionedCheck = new TablePartitionedChecksRootSpec();
        samplePartitionedCheck.setDaily(dailyPartitionedCheck);

        ResponseEntity<Mono<?>> responseEntity = this.sut.updateTablePartitionedChecksDaily(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                Optional.of(samplePartitionedCheck.getDaily()));

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(
                this.sampleTable.getTableSpec().getPartitionedChecks().getDaily(),
                samplePartitionedCheck.getDaily());
    }

    // TODO: updateTableAdHocChecksUI, and the following check types.
}
