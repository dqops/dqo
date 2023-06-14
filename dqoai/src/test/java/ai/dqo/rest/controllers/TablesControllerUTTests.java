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
import ai.dqo.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import ai.dqo.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.table.partitioned.TablePartitionedChecksRootSpec;
import ai.dqo.checks.table.partitioned.volume.TableVolumeDailyPartitionedChecksSpec;
import ai.dqo.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import ai.dqo.checks.table.profiling.TableVolumeProfilingChecksSpec;
import ai.dqo.checks.table.recurring.TableDailyRecurringCategoriesSpec;
import ai.dqo.checks.table.recurring.TableRecurringChecksSpec;
import ai.dqo.checks.table.recurring.volume.TableVolumeDailyRecurringChecksSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoJobQueueObjectMother;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.jobqueue.DqoQueueJobFactoryImpl;
import ai.dqo.data.statistics.services.StatisticsDataServiceImpl;
import ai.dqo.execution.ExecutionContextFactory;
import ai.dqo.execution.ExecutionContextFactoryImpl;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcher;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcherImpl;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import ai.dqo.rules.comparison.MinCountRuleWarningParametersSpec;
import ai.dqo.services.check.mapping.utils.CheckContainerBasicModelUtility;
import ai.dqo.services.check.mapping.models.CheckContainerModel;
import ai.dqo.services.check.mapping.basicmodels.CheckContainerBasicModel;
import ai.dqo.rest.models.metadata.TableBasicModel;
import ai.dqo.rest.models.metadata.TableModel;
import ai.dqo.rules.comparison.MinCountRule0ParametersSpec;
import ai.dqo.rules.comparison.MinCountRuleFatalParametersSpec;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import ai.dqo.services.check.mapping.AllChecksModelFactory;
import ai.dqo.services.check.mapping.AllChecksModelFactoryImpl;
import ai.dqo.services.check.mapping.ModelToSpecCheckMappingServiceImpl;
import ai.dqo.services.metadata.TableService;
import ai.dqo.services.metadata.TableServiceImpl;
import ai.dqo.utils.BeanFactoryObjectMother;
import ai.dqo.utils.reflection.ReflectionService;
import ai.dqo.utils.reflection.ReflectionServiceSingleton;
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
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();

        DqoQueueJobFactory dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());
        DqoJobQueue dqoJobQueue = DqoJobQueueObjectMother.getDefaultJobQueue();

        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        ExecutionContextFactory executionContextFactory = new ExecutionContextFactoryImpl(userHomeContextFactory, dqoHomeContextFactory);
        HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl());
        ReflectionService reflectionService = ReflectionServiceSingleton.getInstance();

        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(reflectionService, new SensorDefinitionFindServiceImpl());
        AllChecksModelFactory allChecksModelFactory = new AllChecksModelFactoryImpl(executionContextFactory, hierarchyNodeTreeSearcher, specToUiCheckMappingService);

        TableService tableService = new TableServiceImpl(this.userHomeContextFactory, dqoQueueJobFactory, dqoJobQueue, allChecksModelFactory);

        ModelToSpecCheckMappingServiceImpl uiToSpecCheckMappingService = new ModelToSpecCheckMappingServiceImpl(reflectionService);

        StatisticsDataServiceImpl statisticsDataService = new StatisticsDataServiceImpl(null, null); // TODO: configure dependencies if we want to unit test statistics

        this.sut = new TablesController(tableService, this.userHomeContextFactory, dqoHomeContextFactory, specToUiCheckMappingService, uiToSpecCheckMappingService, allChecksModelFactory, statisticsDataService);
        this.userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        this.sampleTable = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.bigquery);
    }

    @Test
    void getTables_whenSampleConnectionRequested_thenReturnsListOfTables() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Flux<TableBasicModel>> responseEntity = this.sut.getTables(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName());

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
                tableSpec.getPhysicalTableName().getSchemaName(),
                tableSpec.getPhysicalTableName().getTableName());

        TableModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(tableSpec.getPhysicalTableName().getTableName(), result.getSpec().getPhysicalTableName().getTableName());
        Assertions.assertEquals(this.sampleTable.getConnectionName(), result.getConnectionName());
        Assertions.assertEquals(
                tableSpec.getPhysicalTableName(),
                result.getSpec().getPhysicalTableName());
        Assertions.assertEquals(tableSpec.getHierarchyId().hashCode64(), result.getSpec().getHierarchyId().hashCode64());
        Assertions.assertSame(tableSpec, result.getSpec());
    }

    @Test
    void getTableBasic_whenTableRequested_thenReturnsRequestedTable() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec tableSpec = this.sampleTable.getTableSpec();

        ResponseEntity<Mono<TableBasicModel>> responseEntity = this.sut.getTableBasic(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName());

        TableBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(tableSpec.getPhysicalTableName().getTableName(), result.getTarget().getTableName());
        Assertions.assertEquals(this.sampleTable.getConnectionName(), result.getConnectionName());
        Assertions.assertEquals(tableSpec.getPhysicalTableName(), result.getTarget());
        Assertions.assertEquals(tableSpec.getHierarchyId().hashCode64(), result.getTableHash());
    }

    @Test
    void getTableProfilingChecks_whenTableRequested_thenReturnsProfilingChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec tableSpec = this.sampleTable.getTableSpec();

        ResponseEntity<Mono<CheckContainerModel>> responseEntity = this.sut.getTableProfilingChecksModel(
                this.sampleTable.getConnectionName(),
                tableSpec.getPhysicalTableName().getSchemaName(),
                tableSpec.getPhysicalTableName().getTableName());

        CheckContainerModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(6, result.getCategories().size());
    }

    @Test
    void getTableDailyRecurringChecks_whenTableRequested_thenReturnsRecurring() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRuleWarningParametersSpec minRule1 = new MinCountRuleWarningParametersSpec(10L);
        MinCountRule0ParametersSpec minRule2 = new MinCountRule0ParametersSpec(20L);
        MinCountRuleFatalParametersSpec minRule3 = new MinCountRuleFatalParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);
        
        TableVolumeDailyRecurringChecksSpec volumeDailyRecurringSpec = new TableVolumeDailyRecurringChecksSpec();
        volumeDailyRecurringSpec.setDailyRowCount(minRowCountSpec);
        TableDailyRecurringCategoriesSpec dailyRecurring = new TableDailyRecurringCategoriesSpec();
        dailyRecurring.setVolume(volumeDailyRecurringSpec);
        TableRecurringChecksSpec sampleRecurring = new TableRecurringChecksSpec();
        sampleRecurring.setDaily(dailyRecurring);
        
        this.sampleTable.getTableSpec().setRecurringChecks(sampleRecurring);

        ResponseEntity<Mono<TableDailyRecurringCategoriesSpec>> responseEntity = this.sut.getTableDailyRecurringChecks(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName());

        TableDailyRecurringCategoriesSpec result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
    }

    @Test
    void getTablePartitionedChecksDaily_whenTableRequested_thenReturnsPartitionedChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRuleWarningParametersSpec minRule1 = new MinCountRuleWarningParametersSpec(10L);
        MinCountRule0ParametersSpec minRule2 = new MinCountRule0ParametersSpec(20L);
        MinCountRuleFatalParametersSpec minRule3 = new MinCountRuleFatalParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);

        TableVolumeDailyPartitionedChecksSpec volumeDailyPartitionedCheckSpec = new TableVolumeDailyPartitionedChecksSpec();
        volumeDailyPartitionedCheckSpec.setDailyPartitionRowCount(minRowCountSpec);
        TableDailyPartitionedCheckCategoriesSpec dailyPartitionedCheck = new TableDailyPartitionedCheckCategoriesSpec();
        dailyPartitionedCheck.setVolume(volumeDailyPartitionedCheckSpec);
        TablePartitionedChecksRootSpec samplePartitionedCheck = new TablePartitionedChecksRootSpec();
        samplePartitionedCheck.setDaily(dailyPartitionedCheck);

        this.sampleTable.getTableSpec().setPartitionedChecks(samplePartitionedCheck);

        ResponseEntity<Mono<TableDailyPartitionedCheckCategoriesSpec>> responseEntity = this.sut.getTableDailyPartitionedChecks(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName());

        TableDailyPartitionedCheckCategoriesSpec result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
    }

    @Test
    void getTableProfilingChecksModel_whenTableRequested_thenReturnsProfilingChecksUi() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec tableSpec = this.sampleTable.getTableSpec();

        ResponseEntity<Mono<CheckContainerModel>> responseEntity = this.sut.getTableProfilingChecksModel(
                this.sampleTable.getConnectionName(),
                tableSpec.getPhysicalTableName().getSchemaName(),
                tableSpec.getPhysicalTableName().getTableName());

        CheckContainerModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(6, result.getCategories().size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTableRecurringChecksModel_whenTableRequested_thenReturnsRecurringChecksModel(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Mono<CheckContainerModel>> responseEntity = this.sut.getTableRecurringChecksModel(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                timePartition);

        CheckContainerModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);

        if (timePartition == CheckTimeScale.daily) {
            Assertions.assertEquals(6, result.getCategories().size());
        } else {
            Assertions.assertEquals(6, result.getCategories().size());
        }
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTablePartitionedChecksModel_whenTableRequested_thenReturnsPartitionedChecksUi(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Mono<CheckContainerModel>> responseEntity = this.sut.getTablePartitionedChecksModel(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                timePartition);

        CheckContainerModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);

        if (timePartition == CheckTimeScale.daily) {
            Assertions.assertEquals(3, result.getCategories().size());
        } else {
            Assertions.assertEquals(3, result.getCategories().size());
        }
    }

    @Test
    void getTableProfilingChecksBasicModel_whenTableRequested_thenReturnsProfilingChecksUiBasic() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec tableSpec = this.sampleTable.getTableSpec();

        ResponseEntity<Mono<CheckContainerBasicModel>> responseEntity = this.sut.getTableProfilingChecksBasicModel(
                this.sampleTable.getConnectionName(),
                tableSpec.getPhysicalTableName().getSchemaName(),
                tableSpec.getPhysicalTableName().getTableName());

        CheckContainerBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(6, CheckContainerBasicModelUtility.getCheckCategoryNames(result).size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTableRecurringChecksBasicModel_whenTableRequested_thenReturnsRecurringChecksBasicModel(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Mono<CheckContainerBasicModel>> responseEntity = this.sut.getTableRecurringChecksBasicModel(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                timePartition);

        CheckContainerBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        if (timePartition == CheckTimeScale.daily) {
            Assertions.assertEquals(6, CheckContainerBasicModelUtility.getCheckCategoryNames(result).size());
        } else {
            Assertions.assertEquals(6, CheckContainerBasicModelUtility.getCheckCategoryNames(result).size());
        }
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTablePartitionedChecksBasicModel_whenTableRequested_thenReturnsPartitionedChecksUiBasic(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Mono<CheckContainerBasicModel>> responseEntity = this.sut.getTablePartitionedChecksBasicModel(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                timePartition);

        CheckContainerBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        if (timePartition == CheckTimeScale.daily) {
            Assertions.assertEquals(3, CheckContainerBasicModelUtility.getCheckCategoryNames(result).size());
        } else {
            Assertions.assertEquals(3, CheckContainerBasicModelUtility.getCheckCategoryNames(result).size());
        }
    }
    
    @Test
    void updateTableProfilingChecks_whenTableAndProfilingChecksRequested_updatesProfilingChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRuleWarningParametersSpec minRule1 = new MinCountRuleWarningParametersSpec(10L);
        MinCountRule0ParametersSpec minRule2 = new MinCountRule0ParametersSpec(20L);
        MinCountRuleFatalParametersSpec minRule3 = new MinCountRuleFatalParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);

        TableVolumeProfilingChecksSpec volumeChecksSpec = new TableVolumeProfilingChecksSpec();
        volumeChecksSpec.setRowCount(minRowCountSpec);
        TableProfilingCheckCategoriesSpec sampleProfilingCheck = new TableProfilingCheckCategoriesSpec();
        sampleProfilingCheck.setVolume(volumeChecksSpec);

        ResponseEntity<Mono<?>> responseEntity = this.sut.updateTableProfilingChecks(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                Optional.of(sampleProfilingCheck));

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(this.sampleTable.getTableSpec().getProfilingChecks(), sampleProfilingCheck);
    }

    @Test
    void updateTableDailyRecurringChecks_whenTableAndRecurringRequested_updatesRecurring() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRuleWarningParametersSpec minRule1 = new MinCountRuleWarningParametersSpec(10L);
        MinCountRule0ParametersSpec minRule2 = new MinCountRule0ParametersSpec(20L);
        MinCountRuleFatalParametersSpec minRule3 = new MinCountRuleFatalParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);

        TableVolumeDailyRecurringChecksSpec volumeDailyRecurringSpec = new TableVolumeDailyRecurringChecksSpec();
        volumeDailyRecurringSpec.setDailyRowCount(minRowCountSpec);
        TableDailyRecurringCategoriesSpec dailyRecurring = new TableDailyRecurringCategoriesSpec();
        dailyRecurring.setVolume(volumeDailyRecurringSpec);
        TableRecurringChecksSpec sampleRecurring = new TableRecurringChecksSpec();
        sampleRecurring.setDaily(dailyRecurring);

        ResponseEntity<Mono<?>> responseEntity = this.sut.updateTableDailyRecurringChecks(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                Optional.of(sampleRecurring.getDaily()));

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(
                this.sampleTable.getTableSpec().getRecurringChecks().getDaily(),
                sampleRecurring.getDaily());
    }

    @Test
    void updateTablePartitionedChecksDaily_whenTableAndPartitionedChecksRequested_updatesPartitionedChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRuleWarningParametersSpec minRule1 = new MinCountRuleWarningParametersSpec(10L);
        MinCountRule0ParametersSpec minRule2 = new MinCountRule0ParametersSpec(20L);
        MinCountRuleFatalParametersSpec minRule3 = new MinCountRuleFatalParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);

        TableVolumeDailyPartitionedChecksSpec volumeDailyPartitionedCheckSpec = new TableVolumeDailyPartitionedChecksSpec();
        volumeDailyPartitionedCheckSpec.setDailyPartitionRowCount(minRowCountSpec);
        TableDailyPartitionedCheckCategoriesSpec dailyPartitionedCheck = new TableDailyPartitionedCheckCategoriesSpec();
        dailyPartitionedCheck.setVolume(volumeDailyPartitionedCheckSpec);
        TablePartitionedChecksRootSpec samplePartitionedCheck = new TablePartitionedChecksRootSpec();
        samplePartitionedCheck.setDaily(dailyPartitionedCheck);

        ResponseEntity<Mono<?>> responseEntity = this.sut.updateTablePartitionedChecksDaily(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                Optional.of(samplePartitionedCheck.getDaily()));

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(
                this.sampleTable.getTableSpec().getPartitionedChecks().getDaily(),
                samplePartitionedCheck.getDaily());
    }

    // TODO: updateTableProfilingChecksModel, and the remaining check types.
}
