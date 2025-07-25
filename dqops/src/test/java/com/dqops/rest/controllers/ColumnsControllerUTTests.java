/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rest.controllers;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import com.dqops.checks.column.checkspecs.numeric.ColumnNegativeCountCheckSpec;
import com.dqops.checks.column.monitoring.ColumnDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.ColumnMonitoringCheckCategoriesSpec;
import com.dqops.checks.column.monitoring.nulls.ColumnNullsDailyMonitoringChecksSpec;
import com.dqops.checks.column.partitioned.ColumnMonthlyPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.ColumnPartitionedCheckCategoriesSpec;
import com.dqops.checks.column.partitioned.numeric.ColumnNumericMonthlyPartitionedChecksSpec;
import com.dqops.checks.column.profiling.ColumnNullsProfilingChecksSpec;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.defaults.DefaultObservabilityConfigurationServiceImpl;
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
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.finder.SensorDefinitionFindServiceImpl;
import com.dqops.metadata.sources.ColumnSpec;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactory;
import com.dqops.metadata.storage.localfiles.dqohome.DqoHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactory;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextFactoryObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.rest.models.metadata.ColumnListModel;
import com.dqops.rest.models.metadata.ColumnModel;
import com.dqops.rules.comparison.MaxCountRule0ErrorParametersSpec;
import com.dqops.rules.comparison.MaxCountRule0WarningParametersSpec;
import com.dqops.rules.comparison.MaxCountRule100ParametersSpec;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.services.check.mapping.ModelToSpecCheckMappingServiceImpl;
import com.dqops.services.check.mapping.SpecToModelCheckMappingServiceImpl;
import com.dqops.services.check.mapping.basicmodels.CheckContainerListModel;
import com.dqops.services.check.mapping.models.CheckContainerModel;
import com.dqops.services.check.mapping.utils.CheckContainerListModelUtility;
import com.dqops.services.locking.RestApiLockServiceImpl;
import com.dqops.services.metadata.ColumnService;
import com.dqops.services.metadata.ColumnServiceImpl;
import com.dqops.utils.BeanFactoryObjectMother;
import com.dqops.utils.reflection.ReflectionServiceImpl;
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
    private UserDomainIdentity userDomainIdentity;
    
    @BeforeEach
    void setUp() {
        this.userHomeContextFactory = UserHomeContextFactoryObjectMother.createWithInMemoryContext();
        this.userDomainIdentity = UserDomainIdentityObjectMother.createAdminIdentity();
        DqoQueueJobFactory dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());
        DqoJobQueue dqoJobQueue = DqoJobQueueObjectMother.getDefaultJobQueue();
        ColumnService columnService = new ColumnServiceImpl(this.userHomeContextFactory, dqoQueueJobFactory, dqoJobQueue);

        ReflectionServiceImpl reflectionService = new ReflectionServiceImpl();
        SpecToModelCheckMappingServiceImpl specToUiCheckMappingService = SpecToModelCheckMappingServiceImpl.createInstanceUnsafe(
                reflectionService, new SensorDefinitionFindServiceImpl(), new RuleDefinitionFindServiceImpl());
        ModelToSpecCheckMappingServiceImpl uiToSpecCheckMappingService = new ModelToSpecCheckMappingServiceImpl(reflectionService);
        DqoHomeContextFactory dqoHomeContextFactory = DqoHomeContextFactoryObjectMother.getRealDqoHomeContextFactory();
        this.sut = new ColumnsController(columnService, this.userHomeContextFactory, dqoHomeContextFactory, specToUiCheckMappingService,
                uiToSpecCheckMappingService, null, new DefaultObservabilityConfigurationServiceImpl(ConnectionProviderRegistryObjectMother.getInstance()),
                new RestApiLockServiceImpl(), new TableStatusCacheStub());
        this.userHomeContext = this.userHomeContextFactory.openLocalUserHome(this.userDomainIdentity, false);
        this.sampleTable = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.bigquery);
    }

    @Test
    void getColumns_whenSampleTableRequested_thenReturnsListOfColumns() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);

        Mono<ResponseEntity<Flux<ColumnListModel>>> responseEntity = this.sut.getColumns(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                Optional.of(false),
                Optional.empty()
        );

        List<ColumnListModel> result = responseEntity.block().getBody().collectList().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
    }

    @Test
    void getColumn_whenColumnFromSampleTableRequested_thenReturnsRequestedColumn() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        Mono<ResponseEntity<Mono<ColumnModel>>> responseEntity = this.sut.getColumn(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName());

        ColumnModel result = responseEntity.block().getBody().block();
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

        Mono<ResponseEntity<Mono<ColumnListModel>>> responseEntity = this.sut.getColumnBasic(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName());

        ColumnListModel result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(columnSpec.getColumnName(), result.getColumnName());
        Assertions.assertEquals(this.sampleTable.getConnectionName(), result.getConnectionName());
        Assertions.assertEquals(this.sampleTable.getTableSpec().getPhysicalTableName(), result.getTable());
        Assertions.assertEquals(columnSpec.getHierarchyId().hashCode64(), result.getColumnHash());
        Assertions.assertEquals(columnSpec.getTypeSnapshot(), result.getTypeSnapshot());
    }

    @Test
    void getColumnProfilingChecksModel_whenColumnFromSampleTableRequested_thenReturnsProfilingChecksUi() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        Mono<ResponseEntity<Mono<CheckContainerModel>>> responseEntity = this.sut.getColumnProfilingChecksModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName());

        CheckContainerModel result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(17, result.getCategories().size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getColumnMonitoringChecksModel_whenColumnFromSampleTableRequested_thenReturnsMonitoringChecksUI(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        Mono<ResponseEntity<Mono<CheckContainerModel>>> responseEntity = this.sut.getColumnMonitoringChecksModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                timePartition);

        CheckContainerModel result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);

        if (timePartition == CheckTimeScale.daily) {
            Assertions.assertEquals(17, result.getCategories().size());
        } else {
            Assertions.assertEquals(16, result.getCategories().size());
        }
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getColumnPartitionedChecksModel_whenColumnFromSampleTableRequested_thenReturnsPartitionedChecksUi(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        Mono<ResponseEntity<Mono<CheckContainerModel>>> responseEntity = this.sut.getColumnPartitionedChecksModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                timePartition);

        CheckContainerModel result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
        if (timePartition == CheckTimeScale.daily) {
            Assertions.assertEquals(15, result.getCategories().size());
        } else {
            Assertions.assertEquals(14, result.getCategories().size());
        }
    }

    @Test
    void getColumnProfilingChecksBasicModel_whenColumnFromSampleTableRequested_thenReturnsProfilingChecksUiBasic() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        Mono<ResponseEntity<Mono<CheckContainerListModel>>> responseEntity = this.sut.getColumnProfilingChecksBasicModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName());

        CheckContainerListModel result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
        Assertions.assertEquals(17, CheckContainerListModelUtility.getCheckCategoryNames(result).size());
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getColumnMonitoringChecksBasicModel_whenColumnFromSampleTableRequested_thenReturnsMonitoringChecksUIBasic(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        Mono<ResponseEntity<Mono<CheckContainerListModel>>> responseEntity = this.sut.getColumnMonitoringChecksBasicModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                timePartition);

        CheckContainerListModel result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
        if (timePartition == CheckTimeScale.daily) {
            Assertions.assertEquals(17, CheckContainerListModelUtility.getCheckCategoryNames(result).size());
        } else {
            Assertions.assertEquals(16, CheckContainerListModelUtility.getCheckCategoryNames(result).size());
        }
    }

    @ParameterizedTest
    @EnumSource(CheckTimeScale.class)
    void getColumnPartitionedChecksBasicModel_whenColumnFromSampleTableRequested_thenReturnsPartitionedChecksUiBasic(CheckTimeScale timePartition) {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        Mono<ResponseEntity<Mono<CheckContainerListModel>>> responseEntity = this.sut.getColumnPartitionedChecksBasicModel(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                timePartition);

        CheckContainerListModel result = responseEntity.block().getBody().block();
        Assertions.assertNotNull(result);
        if (timePartition == CheckTimeScale.daily) {
            Assertions.assertEquals(15, CheckContainerListModelUtility.getCheckCategoryNames(result).size());
        } else {
            Assertions.assertEquals(14, CheckContainerListModelUtility.getCheckCategoryNames(result).size());
        }
    }

    @Test
    void updateColumnProfilingChecks_whenColumnAndProfilingChecksRequested_updatesProfilingChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        MaxCountRule0WarningParametersSpec maxCountRule1 = new MaxCountRule0WarningParametersSpec();
        maxCountRule1.setMaxCount(10L);
        MaxCountRule0ErrorParametersSpec maxCountRule2 = new MaxCountRule0ErrorParametersSpec();
        maxCountRule2.setMaxCount(20L);
        MaxCountRule100ParametersSpec maxCountRule3 = new MaxCountRule100ParametersSpec();
        maxCountRule3.setMaxCount(30L);

        ColumnNullsCountCheckSpec nullsChecksSpec = new ColumnNullsCountCheckSpec();
        nullsChecksSpec.setWarning(maxCountRule1);
        nullsChecksSpec.setError(maxCountRule2);
        nullsChecksSpec.setFatal(maxCountRule3);

        ColumnNullsProfilingChecksSpec nullChecks = new ColumnNullsProfilingChecksSpec();
        nullChecks.setProfileNullsCount(nullsChecksSpec);
        ColumnProfilingCheckCategoriesSpec sampleProfilingCheck = new ColumnProfilingCheckCategoriesSpec();
        sampleProfilingCheck.setNulls(nullChecks);
        
        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.updateColumnProfilingChecks(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                sampleProfilingCheck);

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(columnSpec.getProfilingChecks(), sampleProfilingCheck);
    }

    @Test
    void updateColumnMonitoringChecksDaily_whenColumnAndMonitoringRequested_updatesMonitoring() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        MaxCountRule0WarningParametersSpec maxCountRule1 = new MaxCountRule0WarningParametersSpec();
        maxCountRule1.setMaxCount(10L);
        MaxCountRule0ErrorParametersSpec maxCountRule2 = new MaxCountRule0ErrorParametersSpec();
        maxCountRule2.setMaxCount(20L);
        MaxCountRule100ParametersSpec maxCountRule3 = new MaxCountRule100ParametersSpec();
        maxCountRule3.setMaxCount(30L);

        ColumnNullsCountCheckSpec nullsChecksSpec = new ColumnNullsCountCheckSpec();
        nullsChecksSpec.setWarning(maxCountRule1);
        nullsChecksSpec.setError(maxCountRule2);
        nullsChecksSpec.setFatal(maxCountRule3);

        ColumnNullsDailyMonitoringChecksSpec nullDailyMonitoring = new ColumnNullsDailyMonitoringChecksSpec();
        nullDailyMonitoring.setDailyNullsCount(nullsChecksSpec);
        ColumnDailyMonitoringCheckCategoriesSpec dailyMonitoring = new ColumnDailyMonitoringCheckCategoriesSpec();
        dailyMonitoring.setNulls(nullDailyMonitoring);
        ColumnMonitoringCheckCategoriesSpec sampleMonitoring = new ColumnMonitoringCheckCategoriesSpec();
        sampleMonitoring.setDaily(dailyMonitoring);
        
        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.updateColumnMonitoringChecksDaily(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                sampleMonitoring.getDaily());

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(columnSpec.getMonitoringChecks().getDaily(), sampleMonitoring.getDaily());
        Assertions.assertNull(columnSpec.getMonitoringChecks().getMonthly());
    }

    @Test
    void updateColumnPartitionedChecksMonthly_whenColumnAndPartitionedChecksRequested_updatesPartitionedChecks() {
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, this.sampleTable);
        ColumnSpec columnSpec = this.sampleTable.getTableSpec().getColumns().values().stream().findFirst().get();

        MaxCountRule0WarningParametersSpec maxCountRule1 = new MaxCountRule0WarningParametersSpec();
        maxCountRule1.setMaxCount(10L);
        MaxCountRule0ErrorParametersSpec maxCountRule2 = new MaxCountRule0ErrorParametersSpec();
        maxCountRule2.setMaxCount(20L);
        MaxCountRule100ParametersSpec maxCountRule3 = new MaxCountRule100ParametersSpec();
        maxCountRule3.setMaxCount(30L);
        
        ColumnNegativeCountCheckSpec negativeChecksSpec = new ColumnNegativeCountCheckSpec();
        negativeChecksSpec.setWarning(maxCountRule1);
        negativeChecksSpec.setError(maxCountRule2);
        negativeChecksSpec.setFatal(maxCountRule3);
        
        ColumnNumericMonthlyPartitionedChecksSpec negativeMonthlyPartitionedChecks = new ColumnNumericMonthlyPartitionedChecksSpec();
        negativeMonthlyPartitionedChecks.setMonthlyPartitionNegativeValues(negativeChecksSpec);
        ColumnMonthlyPartitionedCheckCategoriesSpec monthlyPartitionedCheck = new ColumnMonthlyPartitionedCheckCategoriesSpec();
        monthlyPartitionedCheck.setNumeric(negativeMonthlyPartitionedChecks);
        ColumnPartitionedCheckCategoriesSpec samplePartitionedCheck = new ColumnPartitionedCheckCategoriesSpec();
        samplePartitionedCheck.setMonthly(monthlyPartitionedCheck);
        
        Mono<ResponseEntity<Mono<Void>>> responseEntity = this.sut.updateColumnPartitionedChecksMonthly(
                DqoUserPrincipalObjectMother.createStandaloneAdmin(),
                this.sampleTable.getConnectionName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getSchemaName(),
                this.sampleTable.getTableSpec().getPhysicalTableName().getTableName(),
                columnSpec.getColumnName(),
                samplePartitionedCheck.getMonthly());

        Object result = responseEntity.block().getBody().block();
        Assertions.assertNull(result);
        Assertions.assertSame(columnSpec.getPartitionedChecks().getMonthly(), samplePartitionedCheck.getMonthly());
        Assertions.assertNull(columnSpec.getPartitionedChecks().getDaily());
    }

    // TODO: updateTableProfilingChecksModel, and the remaining check types.
}
