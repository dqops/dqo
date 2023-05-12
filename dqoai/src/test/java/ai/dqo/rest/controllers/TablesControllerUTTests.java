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
import ai.dqo.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import ai.dqo.checks.table.profiling.TableProfilingStandardChecksSpec;
import ai.dqo.checks.table.recurring.TableRecurringSpec;
import ai.dqo.checks.table.recurring.TableDailyRecurringCategoriesSpec;
import ai.dqo.checks.table.recurring.standard.TableStandardDailyRecurringSpec;
import ai.dqo.checks.table.checkspecs.standard.TableRowCountCheckSpec;
import ai.dqo.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.table.partitioned.TablePartitionedChecksRootSpec;
import ai.dqo.checks.table.partitioned.standard.TableStandardDailyPartitionedChecksSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoJobQueueObjectMother;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.jobqueue.DqoQueueJobFactoryImpl;
import ai.dqo.data.statistics.services.StatisticsDataServiceImpl;
import ai.dqo.data.statistics.snapshot.StatisticsSnapshotFactoryImpl;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.rules.comparison.MinCountRuleWarningParametersSpec;
import ai.dqo.services.check.mapping.utils.UICheckContainerBasicModelUtility;
import ai.dqo.services.check.mapping.models.UICheckContainerModel;
import ai.dqo.services.check.mapping.basicmodels.UICheckContainerBasicModel;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.services.check.mapping.UiToSpecCheckMappingServiceImpl;
import ai.dqo.rest.models.metadata.TableBasicModel;
import ai.dqo.rest.models.metadata.TableModel;
import ai.dqo.rules.comparison.MinCountRule0ParametersSpec;
import ai.dqo.rules.comparison.MinCountRuleFatalParametersSpec;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.services.metadata.TableService;
import ai.dqo.services.metadata.TableServiceImpl;
import ai.dqo.utils.BeanFactoryObjectMother;
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
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        DqoQueueJobFactory dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());
        DqoJobQueue dqoJobQueue = DqoJobQueueObjectMother.getDefaultJobQueue();
        TableService tableService = new TableServiceImpl(this.userHomeContextFactory, dqoQueueJobFactory, dqoJobQueue);

        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = SpecToUiCheckMappingServiceImpl.createInstanceUnsafe(reflectionService, new SensorDefinitionFindServiceImpl());
        UiToSpecCheckMappingServiceImpl uiToSpecCheckMappingService = new UiToSpecCheckMappingServiceImpl(reflectionService);
        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        StatisticsDataServiceImpl statisticsDataService = new StatisticsDataServiceImpl(null, null); // TODO: configure dependencies if we want to unit test statistics
        this.sut = new TablesController(tableService, this.userHomeContextFactory, dqoHomeContextFactory,
                specToUiCheckMappingService, uiToSpecCheckMappingService,
                statisticsDataService);
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

        ResponseEntity<Mono<UICheckContainerModel>> responseEntity = this.sut.getTableProfilingChecksUI(
                this.sampleTable.getConnectionName(),
                tableSpec.getPhysicalTableName().getSchemaName(),
                tableSpec.getPhysicalTableName().getTableName());

        UICheckContainerModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.getCategories().size());
    }

    @Test
    void getTableRecurringDaily_whenTableRequested_thenReturnsRecurring() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRuleWarningParametersSpec minRule1 = new MinCountRuleWarningParametersSpec(10L);
        MinCountRule0ParametersSpec minRule2 = new MinCountRule0ParametersSpec(20L);
        MinCountRuleFatalParametersSpec minRule3 = new MinCountRuleFatalParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);
        
        TableStandardDailyRecurringSpec standardDailyRecurringSpec = new TableStandardDailyRecurringSpec();
        standardDailyRecurringSpec.setDailyRowCount(minRowCountSpec);
        TableDailyRecurringCategoriesSpec dailyRecurring = new TableDailyRecurringCategoriesSpec();
        dailyRecurring.setStandard(standardDailyRecurringSpec);
        TableRecurringSpec sampleRecurring = new TableRecurringSpec();
        sampleRecurring.setDaily(dailyRecurring);
        
        this.sampleTable.getTableSpec().setRecurringChecks(sampleRecurring);

        ResponseEntity<Mono<TableDailyRecurringCategoriesSpec>> responseEntity = this.sut.getTableRecurringDaily(
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

        TableStandardDailyPartitionedChecksSpec standardDailyPartitionedCheckSpec = new TableStandardDailyPartitionedChecksSpec();
        standardDailyPartitionedCheckSpec.setDailyPartitionRowCount(minRowCountSpec);
        TableDailyPartitionedCheckCategoriesSpec dailyPartitionedCheck = new TableDailyPartitionedCheckCategoriesSpec();
        dailyPartitionedCheck.setStandard(standardDailyPartitionedCheckSpec);
        TablePartitionedChecksRootSpec samplePartitionedCheck = new TablePartitionedChecksRootSpec();
        samplePartitionedCheck.setDaily(dailyPartitionedCheck);

        this.sampleTable.getTableSpec().setPartitionedChecks(samplePartitionedCheck);

        ResponseEntity<Mono<TableDailyPartitionedCheckCategoriesSpec>> responseEntity = this.sut.getTablePartitionedChecksDaily(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName());

        TableDailyPartitionedCheckCategoriesSpec result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
    }

    @Test
    void getTableProfilingChecksUI_whenTableRequested_thenReturnsProfilingChecksUi() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec tableSpec = this.sampleTable.getTableSpec();

        ResponseEntity<Mono<UICheckContainerModel>> responseEntity = this.sut.getTableProfilingChecksUI(
                this.sampleTable.getConnectionName(),
                tableSpec.getPhysicalTableName().getSchemaName(),
                tableSpec.getPhysicalTableName().getTableName());

        UICheckContainerModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.getCategories().size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTableRecurringUI_whenTableRequested_thenReturnsRecurringUi(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Mono<UICheckContainerModel>> responseEntity = this.sut.getTableRecurringUI(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                timePartition);

        UICheckContainerModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, result.getCategories().size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTablePartitionedChecksUI_whenTableRequested_thenReturnsPartitionedChecksUi(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Mono<UICheckContainerModel>> responseEntity = this.sut.getTablePartitionedChecksUI(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                timePartition);

        UICheckContainerModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getCategories().size());
    }

    @Test
    void getTableProfilingChecksUIBasic_whenTableRequested_thenReturnsProfilingChecksUiBasic() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec tableSpec = this.sampleTable.getTableSpec();

        ResponseEntity<Mono<UICheckContainerBasicModel>> responseEntity = this.sut.getTableProfilingChecksUIBasic(
                this.sampleTable.getConnectionName(),
                tableSpec.getPhysicalTableName().getSchemaName(),
                tableSpec.getPhysicalTableName().getTableName());

        UICheckContainerBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, UICheckContainerBasicModelUtility.getCheckCategoryNames(result).size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTableRecurringUIBasic_whenTableRequested_thenReturnsRecurringUiBasic(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Mono<UICheckContainerBasicModel>> responseEntity = this.sut.getTableRecurringUIBasic(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                timePartition);

        UICheckContainerBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, UICheckContainerBasicModelUtility.getCheckCategoryNames(result).size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTablePartitionedChecksUIBasic_whenTableRequested_thenReturnsPartitionedChecksUiBasic(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Mono<UICheckContainerBasicModel>> responseEntity = this.sut.getTablePartitionedChecksUIBasic(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                timePartition);

        UICheckContainerBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, UICheckContainerBasicModelUtility.getCheckCategoryNames(result).size());
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

        TableProfilingStandardChecksSpec standardChecksSpec = new TableProfilingStandardChecksSpec();
        standardChecksSpec.setRowCount(minRowCountSpec);
        TableProfilingCheckCategoriesSpec sampleProfilingCheck = new TableProfilingCheckCategoriesSpec();
        sampleProfilingCheck.setStandard(standardChecksSpec);

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
    void updateTableRecurringDaily_whenTableAndRecurringRequested_updatesRecurring() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRuleWarningParametersSpec minRule1 = new MinCountRuleWarningParametersSpec(10L);
        MinCountRule0ParametersSpec minRule2 = new MinCountRule0ParametersSpec(20L);
        MinCountRuleFatalParametersSpec minRule3 = new MinCountRuleFatalParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);

        TableStandardDailyRecurringSpec standardDailyRecurringSpec = new TableStandardDailyRecurringSpec();
        standardDailyRecurringSpec.setDailyRowCount(minRowCountSpec);
        TableDailyRecurringCategoriesSpec dailyRecurring = new TableDailyRecurringCategoriesSpec();
        dailyRecurring.setStandard(standardDailyRecurringSpec);
        TableRecurringSpec sampleRecurring = new TableRecurringSpec();
        sampleRecurring.setDaily(dailyRecurring);

        ResponseEntity<Mono<?>> responseEntity = this.sut.updateTableRecurringDaily(
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

        TableStandardDailyPartitionedChecksSpec standardDailyPartitionedCheckSpec = new TableStandardDailyPartitionedChecksSpec();
        standardDailyPartitionedCheckSpec.setDailyPartitionRowCount(minRowCountSpec);
        TableDailyPartitionedCheckCategoriesSpec dailyPartitionedCheck = new TableDailyPartitionedCheckCategoriesSpec();
        dailyPartitionedCheck.setStandard(standardDailyPartitionedCheckSpec);
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

    // TODO: updateTableProfilingChecksUI, and the following check types.
}
