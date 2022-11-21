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
import ai.dqo.checks.CheckTimePartition;
import ai.dqo.checks.column.adhoc.ColumnAdHocCheckCategoriesSpec;
import ai.dqo.checks.column.adhoc.ColumnAdHocNullsChecksSpec;
import ai.dqo.checks.column.checkpoints.ColumnCheckpointsSpec;
import ai.dqo.checks.column.checkpoints.ColumnDailyCheckpointCategoriesSpec;
import ai.dqo.checks.column.checkpoints.nulls.ColumnNullsDailyCheckpointsSpec;
import ai.dqo.checks.column.checks.nulls.ColumnMaxNullsCountCheckSpec;
import ai.dqo.checks.column.numeric.ColumnMaxNegativeCountCheckSpec;
import ai.dqo.checks.column.partitioned.ColumnDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.column.partitioned.ColumnPartitionedChecksRootSpec;
import ai.dqo.checks.column.partitioned.nulls.ColumnNullsDailyPartitionedChecksSpec;
import ai.dqo.checks.column.partitioned.numeric.ColumnNegativeDailyPartitionedChecksSpec;
import ai.dqo.checks.table.adhoc.TableAdHocCheckCategoriesSpec;
import ai.dqo.checks.table.adhoc.TableAdHocStandardChecksSpec;
import ai.dqo.checks.table.checkpoints.TableCheckpointsSpec;
import ai.dqo.checks.table.checkpoints.TableDailyCheckpointCategoriesSpec;
import ai.dqo.checks.table.checkpoints.standard.TableStandardDailyCheckpointSpec;
import ai.dqo.checks.table.checks.standard.TableMinRowCountCheckSpec;
import ai.dqo.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import ai.dqo.checks.table.partitioned.TablePartitionedChecksRootSpec;
import ai.dqo.checks.table.partitioned.standard.TableStandardDailyPartitionedChecksSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.metadata.sources.ColumnSpec;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.rest.models.checks.UIAllChecksModel;
import ai.dqo.rest.models.checks.mapping.SpecToUiCheckMappingServiceImpl;
import ai.dqo.rest.models.checks.mapping.UiToSpecCheckMappingServiceImpl;
import ai.dqo.rest.models.metadata.ColumnBasicModel;
import ai.dqo.rest.models.metadata.ColumnModel;
import ai.dqo.rules.comparison.MaxCountRuleParametersSpec;
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
import org.junit.jupiter.params.provider.ValueSource;
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
    private ColumnAdHocCheckCategoriesSpec sampleAdHocCheck;
    private ColumnCheckpointsSpec sampleCheckpoint;
    private ColumnPartitionedChecksRootSpec samplePartitionedCheck;

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
        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SpecToUiCheckMappingServiceImpl specToUiCheckMappingService = new SpecToUiCheckMappingServiceImpl(reflectionService);
        UiToSpecCheckMappingServiceImpl uiToSpecCheckMappingService = new UiToSpecCheckMappingServiceImpl(reflectionService);
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        this.sut = new ColumnsController(this.userHomeContextFactory, specToUiCheckMappingService, uiToSpecCheckMappingService);
        this.userHomeContext = this.userHomeContextFactory.openLocalUserHome();
        this.sampleTable = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.bigquery);


        MaxCountRuleParametersSpec maxCountRule1 = new MaxCountRuleParametersSpec();
        maxCountRule1.setMaxCount(10L);
        MaxCountRuleParametersSpec maxCountRule2 = new MaxCountRuleParametersSpec();
        maxCountRule2.setMaxCount(20L);
        MaxCountRuleParametersSpec maxCountRule3 = new MaxCountRuleParametersSpec();
        maxCountRule3.setMaxCount(30L);

        ColumnMaxNullsCountCheckSpec nullsChecksSpec = new ColumnMaxNullsCountCheckSpec();
        nullsChecksSpec.setWarning(maxCountRule1);
        nullsChecksSpec.setError(maxCountRule2);
        nullsChecksSpec.setFatal(maxCountRule3);

        ColumnMaxNegativeCountCheckSpec negativeChecksSpec = new ColumnMaxNegativeCountCheckSpec();
        negativeChecksSpec.setWarning(maxCountRule1);
        negativeChecksSpec.setError(maxCountRule2);
        negativeChecksSpec.setFatal(maxCountRule3);

        ColumnAdHocNullsChecksSpec nullChecks = new ColumnAdHocNullsChecksSpec();
        nullChecks.setMaxNullsCount(nullsChecksSpec);
        this.sampleAdHocCheck = new ColumnAdHocCheckCategoriesSpec();
        this.sampleAdHocCheck.setNulls(nullChecks);

        ColumnNullsDailyCheckpointsSpec nullDailyCheckpoints = new ColumnNullsDailyCheckpointsSpec();
        nullDailyCheckpoints.setDailyCheckpointMaxNullsCount(nullsChecksSpec);
        ColumnDailyCheckpointCategoriesSpec dailyCheckpoint = new ColumnDailyCheckpointCategoriesSpec();
        dailyCheckpoint.setNulls(nullDailyCheckpoints);
        this.sampleCheckpoint = new ColumnCheckpointsSpec();
        this.sampleCheckpoint.setDaily(dailyCheckpoint);

        // What's the purpose of negative check if I can't assign it in this context?
        // I guess, constricting the dependency path on this check,
        // causes improper behavior of SpecToUiCheckMappingService.createUiModel(genericChecks).
//        ColumnNegativeDailyPartitionedChecksSpec negativeDailyPartitionedChecks = new ColumnNegativeDailyPartitionedChecksSpec();
//        negativeDailyPartitionedChecks.setDailyPartitionMaxNegativeCount(negativeChecksSpec);
        ColumnDailyPartitionedCheckCategoriesSpec dailyPartitionedCheck = new ColumnDailyPartitionedCheckCategoriesSpec();
        dailyPartitionedCheck.setDailyPartitionMaxNegativeCount(negativeChecksSpec);
        this.samplePartitionedCheck = new ColumnPartitionedChecksRootSpec();
        this.samplePartitionedCheck.setDaily(dailyPartitionedCheck);
    }

    @Test
    void getColumns_whenSampleTableRequested_thenReturnsListOfColumns() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        ResponseEntity<Flux<ColumnBasicModel>> responseEntity = this.sut.getColumns(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName());

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
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                columnSpec.getColumnName());

        ColumnModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(columnSpec.getColumnName(), result.getColumnName());
        Assertions.assertEquals(this.sampleTable.getConnectionName(), result.getConnectionName());
        Assertions.assertEquals(this.sampleTable.getTableSpec().getTarget().toPhysicalTableName(), result.getTable());
        Assertions.assertEquals(columnSpec.getHierarchyId().hashCode64(), result.getColumnHash());
        Assertions.assertSame(columnSpec, result.getSpec());
    }

    @Test
    void getColumnBasic_whenColumnFromSampleTableRequested_thenReturnsRequestedColumn() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<ColumnBasicModel>> responseEntity = this.sut.getColumnBasic(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                columnSpec.getColumnName());

        ColumnBasicModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(columnSpec.getColumnName(), result.getColumnName());
        Assertions.assertEquals(this.sampleTable.getConnectionName(), result.getConnectionName());
        Assertions.assertEquals(this.sampleTable.getTableSpec().getTarget().toPhysicalTableName(), result.getTable());
        Assertions.assertEquals(columnSpec.getHierarchyId().hashCode64(), result.getColumnHash());
        Assertions.assertEquals(columnSpec.getTypeSnapshot(), result.getTypeSnapshot());
    }

    @Test
    void getColumnAdHocChecksUI_whenColumnFromSampleTableRequested_thenReturnsAdHocChecksUi() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<UIAllChecksModel>> responseEntity = this.sut.getColumnAdHocChecksUI(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                columnSpec.getColumnName());

        UIAllChecksModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(4, result.getCategories().size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimePartition.class)
    void getColumnCheckpointsUI_whenColumnFromSampleTableRequested_thenReturnsCheckpointsUi(CheckTimePartition timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<UIAllChecksModel>> responseEntity = this.sut.getColumnCheckpointsUI(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                columnSpec.getColumnName(),
                timePartition);

        UIAllChecksModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getCategories().size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimePartition.class)
    void getColumnPartitionedChecksUI_whenColumnFromSampleTableRequested_thenReturnsPartitionedChecksUi(CheckTimePartition timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<UIAllChecksModel>> responseEntity = this.sut.getColumnPartitionedChecksUI(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                columnSpec.getColumnName(),
                timePartition);

        UIAllChecksModel result = responseEntity.getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getCategories().size());
    }

    @Test
    void updateColumnAdHocChecks_whenColumnAndAdHocChecksRequested_updatesAdHocChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<?>> responseEntity = this.sut.updateColumnAdHocChecks(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                columnSpec.getColumnName(),
                Optional.of(this.sampleAdHocCheck));

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(columnSpec.getChecks(), this.sampleAdHocCheck);
    }

    @Test
    void updateColumnCheckpointsDaily_whenColumnAndCheckpointsRequested_updatesCheckpoints() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<?>> responseEntity = this.sut.updateColumnCheckpointsDaily(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                columnSpec.getColumnName(),
                Optional.of(this.sampleCheckpoint.getDaily()));

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(columnSpec.getCheckpoints().getDaily(), this.sampleCheckpoint.getDaily());
    }

    @Test
    void updateColumnPartitionedChecksDaily_whenColumnAndPartitionedChecksRequested_updatesPartitionedChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        ResponseEntity<Mono<?>> responseEntity = this.sut.updateColumnPartitionedChecksDaily(
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getTarget().getSchemaName(),
                this.sampleTable.getTableSpec().getTarget().getTableName(),
                columnSpec.getColumnName(),
                Optional.of(this.samplePartitionedCheck.getDaily()));

        Object result = responseEntity.getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(columnSpec.getPartitionedChecks().getDaily(), this.samplePartitionedCheck.getDaily());
    }

    // TODO: updateTableAdHocChecksUI, and the following check types.
}
