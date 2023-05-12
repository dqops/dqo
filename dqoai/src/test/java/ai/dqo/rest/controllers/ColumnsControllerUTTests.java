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
import ai.dqo.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import ai.dqo.checks.column.profiling.ColumnProfilingNullsChecksSpec;
import ai.dqo.checks.column.recurring.ColumnRecurringSpec;
import ai.dqo.checks.column.recurring.ColumnDailyRecurringCategoriesSpec;
import ai.dqo.checks.column.recurring.nulls.ColumnNullsDailyRecurringSpec;
import ai.dqo.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import ai.dqo.checks.column.checkspecs.numeric.ColumnNegativeCountCheckSpec;
import ai.dqo.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import ai.dqo.checks.column.partitioned.numeric.ColumnNumericMonthlyPartitionedChecksSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.core.jobqueue.DqoJobQueue;
import ai.dqo.core.jobqueue.DqoJobQueueObjectMother;
import ai.dqo.core.jobqueue.DqoQueueJobFactory;
import ai.dqo.core.jobqueue.DqoQueueJobFactoryImpl;
import ai.dqo.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.rules.comparison.MaxCountRule15ParametersSpec;
import ai.dqo.services.check.mapping.utils.UICheckContainerBasicModelUtility;
import ai.dqo.services.check.mapping.models.UICheckContainerModel;
import ai.dqo.services.check.mapping.basicmodels.UICheckContainerBasicModel;
import ai.dqo.services.check.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.services.check.mapping.UiToSpecCheckMappingServiceImpl;
import ai.dqo.rest.models.metadata.ColumnBasicModel;
import ai.dqo.rest.models.metadata.ColumnModel;
import ai.dqo.rules.comparison.MaxCountRule0ParametersSpec;
import ai.dqo.rules.comparison.MaxCountRule10ParametersSpec;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.services.metadata.ColumnService;
import ai.dqo.services.metadata.ColumnServiceImpl;
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
public class ColumnsControllerUTTests extends BaseTest {
    private ColumnsController sut;
    private UserHomeContextFactory userHomeContextFactory;
    private UserHomeContext userHomeContext;
    private SampleTableMetadata sampleTable;
    
    @BeforeEach
    void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        DqoQueueJobFactory dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());
        DqoJobQueue dqoJobQueue = DqoJobQueueObjectMother.getDefaultJobQueue();
        ColumnService columnService = new ColumnServiceImpl(this.userHomeContextFactory, dqoQueueJobFactory, dqoJobQueue);

        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = SpecToUiCheckMappingServiceImpl.createInstanceUnsafe(
                reflectionService, new SensorDefinitionFindServiceImpl());
        UiToSpecCheckMappingServiceImpl uiToSpecCheckMappingService = new UiToSpecCheckMappingServiceImpl(reflectionService);
        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        this.sut = new ColumnsController(columnService, this.userHomeContextFactory, dqoHomeContextFactory, specToUiCheckMappingService, uiToSpecCheckMappingService, null);
        this.userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        this.sampleTable = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.bigquery);
    }

    @Test
    void getColumns_whenSampleTableRequested_thenReturnsListOfColumns() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Flux<ColumnBasicModel>> responseEntity = this.sut.getColumns(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName());

        List<ColumnBasicModel> result = responseEntity.getBody().collectList().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void getColumn_whenColumnFromSampleTableRequested_thenReturnsRequestedColumn() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<ColumnModel>> responseEntity = this.sut.getColumn(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName());

        ColumnModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(columnSpec.getColumnName(), result.getColumnName());
        Assertions.assertEquals(this.sampleTable.getConnectionName(), result.getConnectionName());
        Assertions.assertEquals(this.sampleTable.getTableSpec().getPhysicalTableName(), result.getTable());
        Assertions.assertEquals(columnSpec.getHierarchyId().hashCode64(), result.getColumnHash());
        Assertions.assertSame(columnSpec, result.getSpec());
    }

    @Test
    void getColumnBasic_whenColumnFromSampleTableRequested_thenReturnsRequestedColumn() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<ColumnBasicModel>> responseEntity = this.sut.getColumnBasic(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName());

        ColumnBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(columnSpec.getColumnName(), result.getColumnName());
        Assertions.assertEquals(this.sampleTable.getConnectionName(), result.getConnectionName());
        Assertions.assertEquals(this.sampleTable.getTableSpec().getPhysicalTableName(), result.getTable());
        Assertions.assertEquals(columnSpec.getHierarchyId().hashCode64(), result.getColumnHash());
        Assertions.assertEquals(columnSpec.getTypeSnapshot(), result.getTypeSnapshot());
    }

    @Test
    void getColumnProfilingChecksUI_whenColumnFromSampleTableRequested_thenReturnsProfilingChecksUi() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<UICheckContainerModel>> responseEntity = this.sut.getColumnProfilingChecksUI(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName());

        UICheckContainerModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(11, result.getCategories().size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getColumnRecurringUI_whenColumnFromSampleTableRequested_thenReturnsRecurringUi(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<UICheckContainerModel>> responseEntity = this.sut.getColumnRecurringUI(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                timePartition);

        UICheckContainerModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(11, result.getCategories().size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getColumnPartitionedChecksUI_whenColumnFromSampleTableRequested_thenReturnsPartitionedChecksUi(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<UICheckContainerModel>> responseEntity = this.sut.getColumnPartitionedChecksUI(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                timePartition);

        UICheckContainerModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(10, result.getCategories().size());
    }

    @Test
    void getColumnProfilingChecksUIBasic_whenColumnFromSampleTableRequested_thenReturnsProfilingChecksUiBasic() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<UICheckContainerBasicModel>> responseEntity = this.sut.getColumnProfilingChecksUIBasic(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName());

        UICheckContainerBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(11, UICheckContainerBasicModelUtility.getCheckCategoryNames(result).size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getColumnRecurringUIBasic_whenColumnFromSampleTableRequested_thenReturnsRecurringUiBasic(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<UICheckContainerBasicModel>> responseEntity = this.sut.getColumnRecurringUIBasic(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                timePartition);

        UICheckContainerBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(11, UICheckContainerBasicModelUtility.getCheckCategoryNames(result).size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getColumnPartitionedChecksUIBasic_whenColumnFromSampleTableRequested_thenReturnsPartitionedChecksUiBasic(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<UICheckContainerBasicModel>> responseEntity = this.sut.getColumnPartitionedChecksUIBasic(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                timePartition);

        UICheckContainerBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(10, UICheckContainerBasicModelUtility.getCheckCategoryNames(result).size());
    }

    @Test
    void updateColumnProfilingChecks_whenColumnAndProfilingChecksRequested_updatesProfilingChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        MaxCountRule0ParametersSpec maxCountRule1 = new MaxCountRule0ParametersSpec();
        maxCountRule1.setMaxCount(10L);
        MaxCountRule10ParametersSpec maxCountRule2 = new MaxCountRule10ParametersSpec();
        maxCountRule2.setMaxCount(20L);
        MaxCountRule15ParametersSpec maxCountRule3 = new MaxCountRule15ParametersSpec();
        maxCountRule3.setMaxCount(30L);

        ColumnNullsCountCheckSpec nullsChecksSpec = new ColumnNullsCountCheckSpec();
        nullsChecksSpec.setWarning(maxCountRule1);
        nullsChecksSpec.setError(maxCountRule2);
        nullsChecksSpec.setFatal(maxCountRule3);

        ColumnProfilingNullsChecksSpec nullChecks = new ColumnProfilingNullsChecksSpec();
        nullChecks.setNullsCount(nullsChecksSpec);
        ColumnProfilingCheckCategoriesSpec sampleProfilingCheck = new ColumnProfilingCheckCategoriesSpec();
        sampleProfilingCheck.setNulls(nullChecks);
        
        ResponseEntity<Mono<?>> responseEntity = this.sut.updateColumnProfilingChecks(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                Optional.of(sampleProfilingCheck));

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(columnSpec.getProfilingChecks(), sampleProfilingCheck);
    }

    @Test
    void updateColumnRecurringDaily_whenColumnAndRecurringRequested_updatesRecurring() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        MaxCountRule0ParametersSpec maxCountRule1 = new MaxCountRule0ParametersSpec();
        maxCountRule1.setMaxCount(10L);
        MaxCountRule10ParametersSpec maxCountRule2 = new MaxCountRule10ParametersSpec();
        maxCountRule2.setMaxCount(20L);
        MaxCountRule15ParametersSpec maxCountRule3 = new MaxCountRule15ParametersSpec();
        maxCountRule3.setMaxCount(30L);

        ColumnNullsCountCheckSpec nullsChecksSpec = new ColumnNullsCountCheckSpec();
        nullsChecksSpec.setWarning(maxCountRule1);
        nullsChecksSpec.setError(maxCountRule2);
        nullsChecksSpec.setFatal(maxCountRule3);

        ColumnNullsDailyRecurringSpec nullDailyRecurring = new ColumnNullsDailyRecurringSpec();
        nullDailyRecurring.setDailyNullsCount(nullsChecksSpec);
        ColumnDailyRecurringCategoriesSpec dailyRecurring = new ColumnDailyRecurringCategoriesSpec();
        dailyRecurring.setNulls(nullDailyRecurring);
        ColumnRecurringSpec sampleRecurring = new ColumnRecurringSpec();
        sampleRecurring.setDaily(dailyRecurring);
        
        ResponseEntity<Mono<?>> responseEntity = this.sut.updateColumnRecurringDaily(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                Optional.of(sampleRecurring.getDaily()));

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(columnSpec.getRecurringChecks().getDaily(), sampleRecurring.getDaily());
        Assertions.assertNull(columnSpec.getRecurringChecks().getMonthly());
    }

    @Test
    void updateColumnPartitionedChecksMonthly_whenColumnAndPartitionedChecksRequested_updatesPartitionedChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        MaxCountRule0ParametersSpec maxCountRule1 = new MaxCountRule0ParametersSpec();
        maxCountRule1.setMaxCount(10L);
        MaxCountRule10ParametersSpec maxCountRule2 = new MaxCountRule10ParametersSpec();
        maxCountRule2.setMaxCount(20L);
        MaxCountRule15ParametersSpec maxCountRule3 = new MaxCountRule15ParametersSpec();
        maxCountRule3.setMaxCount(30L);
        
        ColumnNegativeCountCheckSpec negativeChecksSpec = new ColumnNegativeCountCheckSpec();
        negativeChecksSpec.setWarning(maxCountRule1);
        negativeChecksSpec.setError(maxCountRule2);
        negativeChecksSpec.setFatal(maxCountRule3);
        
        ColumnNumericMonthlyPartitionedChecksSpec negativeMonthlyPartitionedChecks = new ColumnNumericMonthlyPartitionedChecksSpec();
        negativeMonthlyPartitionedChecks.setMonthlyPartitionNegativeCount(negativeChecksSpec);
        ColumnMonthlyPartitionedCheckCategoriesSpec monthlyPartitionedCheck = new ColumnMonthlyPartitionedCheckCategoriesSpec();
        monthlyPartitionedCheck.setNumeric(negativeMonthlyPartitionedChecks);
        ColumnPartitionedChecksRootSpec samplePartitionedCheck = new ColumnPartitionedChecksRootSpec();
        samplePartitionedCheck.setMonthly(monthlyPartitionedCheck);
        
        ResponseEntity<Mono<?>> responseEntity = this.sut.updateColumnPartitionedChecksMonthly(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                Optional.of(samplePartitionedCheck.getMonthly()));

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(columnSpec.getPartitionedChecks().getMonthly(), samplePartitionedCheck.getMonthly());
        Assertions.assertNull(columnSpec.getPartitionedChecks().getDaily());
    }

    // TODO: updateTableProfilingChecksUI, and the following check types.
}
