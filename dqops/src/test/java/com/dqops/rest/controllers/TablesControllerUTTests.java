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
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.defaults.DefaultObservabilityConfigurationServiceImpl;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.checks.table.monitoring.TableDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.volume.TableVolumeDailyMonitoringChecksSpec;
import com.dqops.checks.table.partitioned.TableDailyPartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.TablePartitionedCheckCategoriesSpec;
import com.dqops.checks.table.partitioned.volume.TableVolumeDailyPartitionedChecksSpec;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.connectors.ConnectionProviderRegistryObjectMother;
import com.dqops.connectors.ProviderType;
import com.dqops.core.jobqueue.DqoJobQueue;
import com.dqops.core.jobqueue.DqoJobQueueObjectMother;
import com.dqops.core.jobqueue.DqoQueueJobFactory;
import com.dqops.core.jobqueue.DqoQueueJobFactoryImpl;
import com.dqops.core.principal.DqoUserPrincipalObjectMother;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.data.checkresults.statuscache.TableStatusCacheStub;
import com.dqops.data.statistics.services.StatisticsDataServiceImpl;
import com.dqops.execution.ExecutionContextFactory;
import com.dqops.execution.ExecutionContextFactoryImpl;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.HierarchyNodeTreeSearcherImpl;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import com.dqops.rest.models.metadata.TableListModel;
import com.dqops.rest.models.metadata.TableModel;
import com.dqops.rules.comparison.MinCountRule1ParametersSpec;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.services.check.CheckFlatConfigurationFactory;
import com.dqops.services.check.CheckFlatConfigurationFactoryImpl;
import com.dqops.services.check.mapping.AllChecksModelFactory;
import com.dqops.services.check.mapping.AllChecksModelFactoryImpl;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingServiceImpl;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.services.check.mapping.basicmodels.CheckContainerListModel;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.utils.CheckContainerListModelUtility;
import com.dqops.services.locking.RestApiLockServiceImpl;
import com.dqops.services.metadata.TableService;
import com.dqops.services.metadata.TableServiceImpl;
import com.dqops.utils.BeanFactoryObjectMother;
import com.dqops.utils.reflection.ReflectionService;
import com.dqops.utils.reflection.ReflectionServiceSingleton;
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
    private UserDomainIdentity userDomainIdentity;

    @BeforeEach
    void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        this.userDomainIdentity = UserDomainIdentityObjectMother.createAdminIdentity();

        DqoQueueJobFactory dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());
        DqoJobQueue dqoJobQueue = DqoJobQueueObjectMother.getDefaultJobQueue();

        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        ExecutionContextFactory executionContextFactory = new ExecutionContextFactoryImpl(userHomeContextFactory, dqoHomeContextFactory);
        HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl());
        ReflectionService reflectionService = ReflectionServiceSingleton.getInstance();

        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                reflectionService, new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        AllChecksModelFactory allChecksModelFactory = new AllChecksModelFactoryImpl(executionContextFactory, hierarchyNodeTreeSearcher, specToUiCheckMappingService);
        CheckFlatConfigurationFactory checkFlatConfigurationFactory = new CheckFlatConfigurationFactoryImpl(allChecksModelFactory);

        TableService tableService = new TableServiceImpl(this.userHomeContextFactory, dqoQueueJobFactory, dqoJobQueue, allChecksModelFactory, checkFlatConfigurationFactory);

        ModelToSpecCheckMappingServiceImpl uiToSpecCheckMappingService = new ModelToSpecCheckMappingServiceImpl(reflectionService);

        StatisticsDataServiceImpl statisticsDataService = new StatisticsDataServiceImpl(null, null); // TODO: configure dependencies if we want to unit test statistics
        DefaultObservabilityConfigurationServiceImpl defaultObservabilityConfigurationService = new DefaultObservabilityConfigurationServiceImpl(ConnectionProviderRegistryObjectMother.getInstance());

        this.sut = new TablesController(tableService, this.userHomeContextFactory, dqoHomeContextFactory, specToUiCheckMappingService,
                uiToSpecCheckMappingService, statisticsDataService, defaultObservabilityConfigurationService,
                new HierarchyNodeTreeSearcherImpl(new HierarchyNodeTreeWalkerImpl()),
                new TableStatusCacheStub(),
                new RestApiLockServiceImpl(), null);
        this.userHomeContext = this.userHomeContextFactory.openLocalUserHome(this.userDomainIdentity, false);
        this.sampleTable = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.bigquery);
    }

    @Test
    void getTables_whenSampleConnectionRequested_thenReturnsListOfTables() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        Mono<ResponseEntity<Flux<TableListModel>>> responseEntity = this.sut.getTables(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty());

        List<TableListModel> result = responseEntity.block().getBody().collectList().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());

        Assertions.assertEquals(this.sampleTable.getTableSpec().getHierarchyId().hashCode64(), result.get(0).getTableHash());
    }

    @Test
    void getTable_whenSampleConnectionRequested_thenReturnsRequestedTable() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec tableSpec = this.sampleTable.getTableSpec();

        Mono<ResponseEntity<Mono<TableModel>>> responseEntity = this.sut.getTable(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                tableSpec.getPhysicalTableName().getSchemaName(),
                tableSpec.getPhysicalTableName().getTableName());

        TableModel result = responseEntity.block().getBody().block();
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

        Mono<ResponseEntity<Mono<TableListModel>>> responseEntity = this.sut.getTableBasic(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName());

        TableListModel result = responseEntity.block().getBody().block();
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

        Mono<ResponseEntity<Mono<CheckContainerModel>>> responseEntity = this.sut.getTableProfilingChecksModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                tableSpec.getPhysicalTableName().getSchemaName(),
                tableSpec.getPhysicalTableName().getTableName());

        CheckContainerModel result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(6, result.getCategories().size());
    }

    @Test
    void getTableDailyMonitoringChecks_whenTableRequested_thenReturnsMonitoring() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRule1ParametersSpec minRule1 = new MinCountRule1ParametersSpec(10L);
        MinCountRule1ParametersSpec minRule2 = new MinCountRule1ParametersSpec(20L);
        MinCountRule1ParametersSpec minRule3 = new MinCountRule1ParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);
        
        TableVolumeDailyMonitoringChecksSpec volumeDailyMonitoringSpec = new TableVolumeDailyMonitoringChecksSpec();
        volumeDailyMonitoringSpec.setDailyRowCount(minRowCountSpec);
        TableDailyMonitoringCheckCategoriesSpec dailyMonitoring = new TableDailyMonitoringCheckCategoriesSpec();
        dailyMonitoring.setVolume(volumeDailyMonitoringSpec);
        TableMonitoringCheckCategoriesSpec sampleMonitoring = new TableMonitoringCheckCategoriesSpec();
        sampleMonitoring.setDaily(dailyMonitoring);
        
        this.sampleTable.getTableSpec().setMonitoringChecks(sampleMonitoring);

        Mono<ResponseEntity<Mono<TableDailyMonitoringCheckCategoriesSpec>>> responseEntity = this.sut.getTableDailyMonitoringChecks(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName());

        TableDailyMonitoringCheckCategoriesSpec result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
    }

    @Test
    void getTablePartitionedChecksDaily_whenTableRequested_thenReturnsPartitionedChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRule1ParametersSpec minRule1 = new MinCountRule1ParametersSpec(10L);
        MinCountRule1ParametersSpec minRule2 = new MinCountRule1ParametersSpec(20L);
        MinCountRule1ParametersSpec minRule3 = new MinCountRule1ParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);

        TableVolumeDailyPartitionedChecksSpec volumeDailyPartitionedCheckSpec = new TableVolumeDailyPartitionedChecksSpec();
        volumeDailyPartitionedCheckSpec.setDailyPartitionRowCount(minRowCountSpec);
        TableDailyPartitionedCheckCategoriesSpec dailyPartitionedCheck = new TableDailyPartitionedCheckCategoriesSpec();
        dailyPartitionedCheck.setVolume(volumeDailyPartitionedCheckSpec);
        TablePartitionedCheckCategoriesSpec samplePartitionedCheck = new TablePartitionedCheckCategoriesSpec();
        samplePartitionedCheck.setDaily(dailyPartitionedCheck);

        this.sampleTable.getTableSpec().setPartitionedChecks(samplePartitionedCheck);

        Mono<ResponseEntity<Mono<TableDailyPartitionedCheckCategoriesSpec>>> responseEntity = this.sut.getTableDailyPartitionedChecks(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName());

        TableDailyPartitionedCheckCategoriesSpec result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
    }

    @Test
    void getTableProfilingChecksModel_whenTableRequested_thenReturnsProfilingChecksUi() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        TableSpec tableSpec = this.sampleTable.getTableSpec();

        Mono<ResponseEntity<Mono<CheckContainerModel>>> responseEntity = this.sut.getTableProfilingChecksModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                tableSpec.getPhysicalTableName().getSchemaName(),
                tableSpec.getPhysicalTableName().getTableName());

        CheckContainerModel result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(6, result.getCategories().size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTableMonitoringChecksModel_whenTableRequested_thenReturnsMonitoringChecksModel(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        Mono<ResponseEntity<Mono<CheckContainerModel>>> responseEntity = this.sut.getTableMonitoringChecksModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                timePartition);

        CheckContainerModel result = responseEntity.block().getBody().block();
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

        Mono<ResponseEntity<Mono<CheckContainerModel>>> responseEntity = this.sut.getTablePartitionedChecksModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                timePartition);

        CheckContainerModel result = responseEntity.block().getBody().block();
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

        Mono<ResponseEntity<Mono<CheckContainerListModel>>> responseEntity = this.sut.getTableProfilingChecksBasicModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                tableSpec.getPhysicalTableName().getSchemaName(),
                tableSpec.getPhysicalTableName().getTableName());

        CheckContainerListModel result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(5, CheckContainerListModelUtility.getCheckCategoryNames(result).size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTableMonitoringChecksBasicModel_whenTableRequested_thenReturnsMonitoringChecksBasicModel(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        Mono<ResponseEntity<Mono<CheckContainerListModel>>> responseEntity = this.sut.getTableMonitoringChecksBasicModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                timePartition);

        CheckContainerListModel result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
        if (timePartition == CheckTimeScale.daily) {
            Assertions.assertEquals(5, CheckContainerListModelUtility.getCheckCategoryNames(result).size());
        } else {
            Assertions.assertEquals(5, CheckContainerListModelUtility.getCheckCategoryNames(result).size());
        }
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getTablePartitionedChecksBasicModel_whenTableRequested_thenReturnsPartitionedChecksUiBasic(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        Mono<ResponseEntity<Mono<CheckContainerListModel>>> responseEntity = this.sut.getTablePartitionedChecksBasicModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                timePartition);

        CheckContainerListModel result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
        if (timePartition == CheckTimeScale.daily) {
            Assertions.assertEquals(2, CheckContainerListModelUtility.getCheckCategoryNames(result).size());
        } else {
            Assertions.assertEquals(2, CheckContainerListModelUtility.getCheckCategoryNames(result).size());
        }
    }
    
    @Test
    void updateTableProfilingChecks_whenTableAndProfilingChecksRequested_updatesProfilingChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRule1ParametersSpec minRule1 = new MinCountRule1ParametersSpec(10L);
        MinCountRule1ParametersSpec minRule2 = new MinCountRule1ParametersSpec(20L);
        MinCountRule1ParametersSpec minRule3 = new MinCountRule1ParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);

        TableVolumeProfilingChecksSpec volumeChecksSpec = new TableVolumeProfilingChecksSpec();
        volumeChecksSpec.setProfileRowCount(minRowCountSpec);
        TableProfilingCheckCategoriesSpec sampleProfilingCheck = new TableProfilingCheckCategoriesSpec();
        sampleProfilingCheck.setVolume(volumeChecksSpec);

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.updateTableProfilingChecks(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                sampleProfilingCheck);

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(this.sampleTable.getTableSpec().getProfilingChecks(), sampleProfilingCheck);
    }

    @Test
    void updateTableDailyMonitoringChecks_whenTableAndMonitoringRequested_updatesMonitoring() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRule1ParametersSpec minRule1 = new MinCountRule1ParametersSpec(10L);
        MinCountRule1ParametersSpec minRule2 = new MinCountRule1ParametersSpec(20L);
        MinCountRule1ParametersSpec minRule3 = new MinCountRule1ParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);

        TableVolumeDailyMonitoringChecksSpec volumeDailyMonitoringSpec = new TableVolumeDailyMonitoringChecksSpec();
        volumeDailyMonitoringSpec.setDailyRowCount(minRowCountSpec);
        TableDailyMonitoringCheckCategoriesSpec dailyMonitoring = new TableDailyMonitoringCheckCategoriesSpec();
        dailyMonitoring.setVolume(volumeDailyMonitoringSpec);
        TableMonitoringCheckCategoriesSpec sampleMonitoring = new TableMonitoringCheckCategoriesSpec();
        sampleMonitoring.setDaily(dailyMonitoring);

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.updateTableDailyMonitoringChecks(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                sampleMonitoring.getDaily());

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(
                this.sampleTable.getTableSpec().getMonitoringChecks().getDaily(),
                sampleMonitoring.getDaily());
    }

    @Test
    void updateTablePartitionedChecksDaily_whenTableAndPartitionedChecksRequested_updatesPartitionedChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        MinCountRule1ParametersSpec minRule1 = new MinCountRule1ParametersSpec(10L);
        MinCountRule1ParametersSpec minRule2 = new MinCountRule1ParametersSpec(20L);
        MinCountRule1ParametersSpec minRule3 = new MinCountRule1ParametersSpec(30L);
        TableRowCountCheckSpec minRowCountSpec = new TableRowCountCheckSpec();
        minRowCountSpec.setWarning(minRule1);
        minRowCountSpec.setError(minRule2);
        minRowCountSpec.setFatal(minRule3);

        TableVolumeDailyPartitionedChecksSpec volumeDailyPartitionedCheckSpec = new TableVolumeDailyPartitionedChecksSpec();
        volumeDailyPartitionedCheckSpec.setDailyPartitionRowCount(minRowCountSpec);
        TableDailyPartitionedCheckCategoriesSpec dailyPartitionedCheck = new TableDailyPartitionedCheckCategoriesSpec();
        dailyPartitionedCheck.setVolume(volumeDailyPartitionedCheckSpec);
        TablePartitionedCheckCategoriesSpec samplePartitionedCheck = new TablePartitionedCheckCategoriesSpec();
        samplePartitionedCheck.setDaily(dailyPartitionedCheck);

        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.updateTablePartitionedChecksDaily(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                samplePartitionedCheck.getDaily());

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(
                this.sampleTable.getTableSpec().getPartitionedChecks().getDaily(),
                samplePartitionedCheck.getDaily());
    }

    // TODO: updateTableProfilingChecksModel, and the remaining check types.
}
