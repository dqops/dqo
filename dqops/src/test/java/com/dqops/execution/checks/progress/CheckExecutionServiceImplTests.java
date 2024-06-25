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
package com.dqops.execution.checks.progress;

import com.dqops.BaseTest;
import com.dqops.checks.CheckType;
import com.dqops.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import com.dqops.checks.column.profiling.ColumnNullsProfilingChecksSpec;
import com.dqops.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import com.dqops.checks.defaults.DefaultObservabilityConfigurationServiceImpl;
import com.dqops.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.checks.table.monitoring.TableMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.TableDailyMonitoringCheckCategoriesSpec;
import com.dqops.checks.table.monitoring.customsql.TableCustomSqlDailyMonitoringChecksSpec;
import com.dqops.checks.table.checkspecs.customsql.TableSqlConditionPassedPercentCheckSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.connectors.ConnectionProviderRegistry;
import com.dqops.connectors.ConnectionProviderRegistryObjectMother;
import com.dqops.connectors.ProviderType;
import com.dqops.core.configuration.DqoErrorSamplingConfigurationProperties;
import com.dqops.core.configuration.DqoLoggingUserErrorsConfigurationProperties;
import com.dqops.core.configuration.DqoSensorLimitsConfigurationProperties;
import com.dqops.core.configuration.DqoSensorLimitsConfigurationPropertiesObjectMother;
import com.dqops.core.jobqueue.DqoJobQueueObjectMother;
import com.dqops.core.jobqueue.DqoQueueJobFactoryImpl;
import com.dqops.core.jobqueue.JobCancellationTokenObjectMother;
import com.dqops.core.principal.DqoUserPrincipal;
import com.dqops.core.principal.DqoUserPrincipalObjectMother;
import com.dqops.data.errors.normalization.ErrorsNormalizationService;
import com.dqops.data.errors.normalization.ErrorsNormalizationServiceImpl;
import com.dqops.data.errors.snapshot.ErrorsSnapshotFactoryObjectMother;
import com.dqops.data.errorsamples.factory.ErrorSamplesTableFactoryImpl;
import com.dqops.data.errorsamples.normalization.ErrorSamplesNormalizationServiceImpl;
import com.dqops.data.errorsamples.snapshot.ErrorSamplesSnapshotFactoryImpl;
import com.dqops.data.normalization.CommonTableNormalizationService;
import com.dqops.data.normalization.CommonTableNormalizationServiceImpl;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizationService;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizationServiceImpl;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshotFactoryObjectMother;
import com.dqops.data.checkresults.snapshot.RuleResultsSnapshotFactoryObjectMother;
import com.dqops.data.storage.DummyParquetPartitionStorageService;
import com.dqops.execution.CheckExecutionContextObjectMother;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.checks.CheckExecutionServiceImpl;
import com.dqops.execution.checks.CheckExecutionSummary;
import com.dqops.execution.checks.TableCheckExecutionServiceImpl;
import com.dqops.execution.checks.ruleeval.RuleEvaluationService;
import com.dqops.execution.checks.ruleeval.RuleEvaluationServiceImpl;
import com.dqops.execution.checks.scheduled.ScheduledTargetChecksFindService;
import com.dqops.execution.checks.scheduled.ScheduledTargetChecksFindServiceImpl;
import com.dqops.execution.errorsampling.TableErrorSamplerExecutionServiceImpl;
import com.dqops.execution.rules.DataQualityRuleRunnerObjectMother;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceObjectMother;
import com.dqops.execution.sensors.DataQualitySensorRunnerImpl;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionRunParametersFactory;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.search.CheckSearchFilters;
import com.dqops.metadata.search.HierarchyNodeTreeSearcher;
import com.dqops.metadata.search.HierarchyNodeTreeSearcherImpl;
import com.dqops.metadata.sources.*;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalker;
import com.dqops.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rules.comparison.MaxCountRule0ErrorParametersSpec;
import com.dqops.rules.comparison.MinCountRule1ParametersSpec;
import com.dqops.rules.comparison.MinPercentRule100ErrorParametersSpec;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.services.timezone.DefaultTimeZoneProviderObjectMother;
import com.dqops.utils.BeanFactoryObjectMother;
import com.dqops.utils.logging.UserErrorLoggerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CheckExecutionServiceImplTests extends BaseTest {
    private CheckExecutionServiceImpl sut;
    private ExecutionContext executionContext;
    private CheckExecutionProgressListener progressListener;
    private ConnectionWrapper connectionWrapper;
    private TableWrapper tableWrapper;

    @BeforeEach
    void setUp() {
        this.executionContext = CheckExecutionContextObjectMother.createWithInMemoryUserContext();
        this.progressListener = new CheckExecutionProgressListenerStub();

        UserHome userHome = this.executionContext.getUserHomeContext().getUserHome();
        this.connectionWrapper = userHome.getConnections().createAndAddNew("conn");
        this.connectionWrapper.getSpec().setProviderType(ProviderType.bigquery);
        this.tableWrapper = this.connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "tab1"));
        TableSpec tableSpec = this.tableWrapper.getSpec();

        // Table level checks
        tableSpec.setProfilingChecks(new TableProfilingCheckCategoriesSpec());
        tableSpec.getProfilingChecks().setVolume(new TableVolumeProfilingChecksSpec());
        tableSpec.getProfilingChecks().getVolume().setProfileRowCount(new TableRowCountCheckSpec());
        tableSpec.getProfilingChecks().getVolume().getProfileRowCount().setError(new MinCountRule1ParametersSpec(5L));

        tableSpec.setMonitoringChecks(new TableMonitoringCheckCategoriesSpec());
        tableSpec.getMonitoringChecks().setDaily(new TableDailyMonitoringCheckCategoriesSpec());
        tableSpec.getMonitoringChecks().getDaily().setCustomSql(new TableCustomSqlDailyMonitoringChecksSpec());
        TableSqlConditionPassedPercentCheckSpec sqlCheckSpec = new TableSqlConditionPassedPercentCheckSpec();
        sqlCheckSpec.setError(new MinPercentRule100ErrorParametersSpec(99.5));
        sqlCheckSpec.getParameters().setSqlCondition("nonexistent_column = 42");
        tableSpec.getMonitoringChecks().getDaily().getCustomSql().setDailySqlConditionPassedPercentOnTable(sqlCheckSpec);

        // Column level checks
        ColumnSpec columnSpec = new ColumnSpec(ColumnTypeSnapshotSpec.fromType("INTEGER"));
        ColumnProfilingCheckCategoriesSpec columnProfilingCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        ColumnNullsProfilingChecksSpec columnNullsProfilingChecksSpec = new ColumnNullsProfilingChecksSpec();
        ColumnNullsCountCheckSpec columnNullsCountCheckSpec = new ColumnNullsCountCheckSpec();
        columnNullsCountCheckSpec.setError(new MaxCountRule0ErrorParametersSpec(10L));
        columnNullsProfilingChecksSpec.setProfileNullsCount(columnNullsCountCheckSpec);
        columnProfilingCheckCategoriesSpec.setNulls(columnNullsProfilingChecksSpec);
        columnSpec.setProfilingChecks(columnProfilingCheckCategoriesSpec);
        tableWrapper.getSpec().getColumns().put("col1", columnSpec);

        // Sut
        DefaultTimeZoneProvider defaultTimeZoneProvider = DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider();

        HierarchyNodeTreeWalker hierarchyNodeTreeWalker = new HierarchyNodeTreeWalkerImpl();
        HierarchyNodeTreeSearcher hierarchyNodeTreeSearcher = new HierarchyNodeTreeSearcherImpl(hierarchyNodeTreeWalker);

        SensorReadoutsNormalizationService sensorReadoutsNormalizationService = new SensorReadoutsNormalizationServiceImpl(
                new CommonTableNormalizationServiceImpl(),
                defaultTimeZoneProvider);

        RuleEvaluationService ruleEvaluationService = new RuleEvaluationServiceImpl(
                DataQualityRuleRunnerObjectMother.getDefault(),
                RuleDefinitionFindServiceObjectMother.getRuleDefinitionFindService(),
                defaultTimeZoneProvider);

        CommonTableNormalizationService commonTableNormalizationService = new CommonTableNormalizationServiceImpl();
        ErrorsNormalizationService errorsNormalizationService = new ErrorsNormalizationServiceImpl(
                sensorReadoutsNormalizationService,
                commonTableNormalizationService,
                defaultTimeZoneProvider);

        DefaultObservabilityConfigurationServiceImpl defaultObservabilityConfigurationService =
                new DefaultObservabilityConfigurationServiceImpl(ConnectionProviderRegistryObjectMother.getInstance());

        ScheduledTargetChecksFindService scheduledTargetChecksFindService = new ScheduledTargetChecksFindServiceImpl(
                hierarchyNodeTreeSearcher, defaultObservabilityConfigurationService);

        DqoQueueJobFactoryImpl dqoQueueJobFactory = new DqoQueueJobFactoryImpl(BeanFactoryObjectMother.getBeanFactory());

        ConnectionProviderRegistry connectionProviderRegistry = ConnectionProviderRegistryObjectMother.getInstance();
        UserErrorLoggerImpl userErrorLogger = new UserErrorLoggerImpl(new DqoLoggingUserErrorsConfigurationProperties());
        ErrorSamplesNormalizationServiceImpl errorSamplesNormalizationService =
                new ErrorSamplesNormalizationServiceImpl(commonTableNormalizationService, new DqoErrorSamplingConfigurationProperties());
        DummyParquetPartitionStorageService dummyParquetPartitionStorageService = new DummyParquetPartitionStorageService();
        ErrorSamplesSnapshotFactoryImpl errorSamplesSnapshotFactory =
                new ErrorSamplesSnapshotFactoryImpl(dummyParquetPartitionStorageService, new ErrorSamplesTableFactoryImpl());

        SensorExecutionRunParametersFactory sensorExecutionRunParametersFactory = SensorExecutionRunParametersObjectMother.getFactory();
        DataQualitySensorRunnerImpl dataQualitySensorRunner = DataQualitySensorRunnerObjectMother.getDefault();

        TableErrorSamplerExecutionServiceImpl tableErrorSamplerExecutionService = new TableErrorSamplerExecutionServiceImpl(
                hierarchyNodeTreeSearcher, sensorExecutionRunParametersFactory, dataQualitySensorRunner,
                connectionProviderRegistry, errorSamplesNormalizationService, errorSamplesSnapshotFactory,
                new DqoSensorLimitsConfigurationProperties(), userErrorLogger, defaultObservabilityConfigurationService);

        TableCheckExecutionServiceImpl tableCheckExecutionService = new TableCheckExecutionServiceImpl(
                hierarchyNodeTreeSearcher,
                sensorExecutionRunParametersFactory,
                dataQualitySensorRunner,
                ConnectionProviderRegistryObjectMother.getInstance(),
                sensorReadoutsNormalizationService,
                ruleEvaluationService,
                SensorReadoutsSnapshotFactoryObjectMother.createDummySensorReadoutStorageService(),
                RuleResultsSnapshotFactoryObjectMother.createDummyRuleResultsStorageService(),
                errorsNormalizationService,
                ErrorsSnapshotFactoryObjectMother.createDummyErrorsStorageService(),
                RuleDefinitionFindServiceObjectMother.getRuleDefinitionFindService(),
                null,
                DqoSensorLimitsConfigurationPropertiesObjectMother.getDefault(),
                userErrorLogger,
                defaultObservabilityConfigurationService,
                tableErrorSamplerExecutionService,
                DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider());

        this.sut = new CheckExecutionServiceImpl(
                hierarchyNodeTreeSearcher,
                dqoQueueJobFactory,
                DqoJobQueueObjectMother.getDefaultJobQueue(),
                scheduledTargetChecksFindService,
                tableCheckExecutionService);
    }

    @Test
    void executeChecks_whenRequestedExecutionByType_thenResultsAddUpToAllRun() {
        CheckSearchFilters allFilters = new CheckSearchFilters() {{
            setConnection(connectionWrapper.getName());
        }};

        CheckSearchFilters profilingFilters = allFilters.clone();
        profilingFilters.setCheckType(CheckType.profiling);

        CheckSearchFilters monitoringFilters = allFilters.clone();
        monitoringFilters.setCheckType(CheckType.monitoring);

        CheckSearchFilters partitionedFilters = allFilters.clone();
        partitionedFilters.setCheckType(CheckType.partitioned);

        DqoUserPrincipal principal = DqoUserPrincipalObjectMother.createStandaloneAdmin();

        CheckExecutionSummary profilingSummary = this.sut.executeChecks(
                this.executionContext, profilingFilters, null, false, this.progressListener, true,
                false, null, JobCancellationTokenObjectMother.createDummyJobCancellationToken(), principal);
        CheckExecutionSummary monitoringSummary = this.sut.executeChecks(
                this.executionContext, monitoringFilters, null, false, this.progressListener, true,
                false, null, JobCancellationTokenObjectMother.createDummyJobCancellationToken(), principal);
        CheckExecutionSummary partitionedSummary = this.sut.executeChecks(
                this.executionContext, partitionedFilters, null, false, this.progressListener, true,
                false, null, JobCancellationTokenObjectMother.createDummyJobCancellationToken(), principal);

        CheckExecutionSummary allSummary = this.sut.executeChecks(
                this.executionContext, allFilters, null, false, this.progressListener, true,
                false, null, JobCancellationTokenObjectMother.createDummyJobCancellationToken(), principal);

        Assertions.assertEquals(0, partitionedSummary.getTotalChecksExecutedCount());
        Assertions.assertEquals(2, profilingSummary.getTotalChecksExecutedCount());
        Assertions.assertEquals(1, monitoringSummary.getTotalChecksExecutedCount());


        Assertions.assertEquals(2.0, profilingSummary.getValidResultsColumn().sum());
        Assertions.assertEquals(0.0, monitoringSummary.getValidResultsColumn().sum());
        Assertions.assertEquals(0.0, partitionedSummary.getValidResultsColumn().sum());

        Assertions.assertEquals(0, profilingSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(1, monitoringSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(0, partitionedSummary.getErrorSeverityIssuesCount());

        Assertions.assertEquals(allSummary.getTotalChecksExecutedCount(),
                profilingSummary.getTotalChecksExecutedCount() +
                        monitoringSummary.getTotalChecksExecutedCount() +
                        partitionedSummary.getTotalChecksExecutedCount());
        Assertions.assertEquals(allSummary.getErrorSeverityIssuesCount(),
                profilingSummary.getErrorSeverityIssuesCount() +
                        monitoringSummary.getErrorSeverityIssuesCount() +
                        partitionedSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(allSummary.getValidResultsColumn().sum(),
                profilingSummary.getValidResultsColumn().sum() +
                        monitoringSummary.getValidResultsColumn().sum() +
                        partitionedSummary.getValidResultsColumn().sum());
    }
}
