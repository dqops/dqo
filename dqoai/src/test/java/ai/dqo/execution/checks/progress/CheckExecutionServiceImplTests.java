/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.dqo.execution.checks.progress;

import ai.dqo.BaseTest;
import ai.dqo.checks.CheckType;
import ai.dqo.checks.column.profiling.ColumnProfilingCheckCategoriesSpec;
import ai.dqo.checks.column.profiling.ColumnProfilingNullsChecksSpec;
import ai.dqo.checks.column.checkspecs.nulls.ColumnNullsCountCheckSpec;
import ai.dqo.checks.table.profiling.TableProfilingCheckCategoriesSpec;
import ai.dqo.checks.table.profiling.TableProfilingStandardChecksSpec;
import ai.dqo.checks.table.checkpoints.TableCheckpointsSpec;
import ai.dqo.checks.table.checkpoints.TableDailyCheckpointCategoriesSpec;
import ai.dqo.checks.table.checkpoints.sql.TableSqlDailyCheckpointSpec;
import ai.dqo.checks.table.checkspecs.sql.TableSqlConditionPassedPercentCheckSpec;
import ai.dqo.checks.table.checkspecs.standard.TableRowCountCheckSpec;
import ai.dqo.connectors.ConnectionProviderRegistryObjectMother;
import ai.dqo.connectors.ProviderType;
import ai.dqo.core.notifications.NotificationService;
import ai.dqo.core.notifications.NotificationServiceImpl;
import ai.dqo.data.errors.normalization.ErrorsNormalizationService;
import ai.dqo.data.errors.normalization.ErrorsNormalizationServiceImpl;
import ai.dqo.data.errors.snapshot.ErrorsSnapshotFactoryObjectMother;
import ai.dqo.data.normalization.CommonTableNormalizationService;
import ai.dqo.data.normalization.CommonTableNormalizationServiceImpl;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizationService;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizationServiceImpl;
import ai.dqo.data.readouts.snapshot.SensorReadoutsSnapshotFactoryObjectMother;
import ai.dqo.data.ruleresults.snapshot.RuleResultsSnapshotFactoryObjectMother;
import ai.dqo.execution.CheckExecutionContextObjectMother;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.checks.CheckExecutionServiceImpl;
import ai.dqo.execution.checks.CheckExecutionSummary;
import ai.dqo.execution.checks.ruleeval.RuleEvaluationService;
import ai.dqo.execution.checks.ruleeval.RuleEvaluationServiceImpl;
import ai.dqo.execution.checks.scheduled.ScheduledTargetChecksFindService;
import ai.dqo.execution.checks.scheduled.ScheduledTargetChecksFindServiceImpl;
import ai.dqo.execution.rules.DataQualityRuleRunnerObjectMother;
import ai.dqo.execution.rules.finder.RuleDefinitionFindServiceObjectMother;
import ai.dqo.execution.sensors.DataQualitySensorRunnerObjectMother;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.metadata.search.CheckSearchFilters;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcher;
import ai.dqo.metadata.search.HierarchyNodeTreeSearcherImpl;
import ai.dqo.metadata.sources.*;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalker;
import ai.dqo.metadata.traversal.HierarchyNodeTreeWalkerImpl;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rules.comparison.MaxCountRule0ParametersSpec;
import ai.dqo.rules.comparison.MinCountRule0ParametersSpec;
import ai.dqo.rules.comparison.MinPercentRule99ParametersSpec;
import ai.dqo.services.timezone.DefaultTimeZoneProvider;
import ai.dqo.services.timezone.DefaultTimeZoneProviderObjectMother;
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
        tableSpec.setChecks(new TableProfilingCheckCategoriesSpec());
        tableSpec.getChecks().setStandard(new TableProfilingStandardChecksSpec());
        tableSpec.getChecks().getStandard().setRowCount(new TableRowCountCheckSpec());
        tableSpec.getChecks().getStandard().getRowCount().setError(new MinCountRule0ParametersSpec(5L));

        tableSpec.setCheckpoints(new TableCheckpointsSpec());
        tableSpec.getCheckpoints().setDaily(new TableDailyCheckpointCategoriesSpec());
        tableSpec.getCheckpoints().getDaily().setSql(new TableSqlDailyCheckpointSpec());
        TableSqlConditionPassedPercentCheckSpec sqlCheckSpec = new TableSqlConditionPassedPercentCheckSpec();
        sqlCheckSpec.setError(new MinPercentRule99ParametersSpec(99.5));
        sqlCheckSpec.getParameters().setSqlCondition("nonexistent_column = 42");
        tableSpec.getCheckpoints().getDaily().getSql().setDailyCheckpointSqlConditionPassedPercentOnTable(sqlCheckSpec);

        // Column level checks
        ColumnSpec columnSpec = new ColumnSpec(ColumnTypeSnapshotSpec.fromType("INTEGER"));
        ColumnProfilingCheckCategoriesSpec columnProfilingCheckCategoriesSpec = new ColumnProfilingCheckCategoriesSpec();
        ColumnProfilingNullsChecksSpec columnProfilingNullsChecksSpec = new ColumnProfilingNullsChecksSpec();
        ColumnNullsCountCheckSpec columnNullsCountCheckSpec = new ColumnNullsCountCheckSpec();
        columnNullsCountCheckSpec.setError(new MaxCountRule0ParametersSpec());
        columnProfilingNullsChecksSpec.setNullsCount(columnNullsCountCheckSpec);
        columnProfilingCheckCategoriesSpec.setNulls(columnProfilingNullsChecksSpec);
        columnSpec.setChecks(columnProfilingCheckCategoriesSpec);
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

        ScheduledTargetChecksFindService scheduledTargetChecksFindService = new ScheduledTargetChecksFindServiceImpl(
                hierarchyNodeTreeSearcher);
        NotificationService notificationService = new NotificationServiceImpl();

        this.sut = new CheckExecutionServiceImpl(
                hierarchyNodeTreeSearcher,
                SensorExecutionRunParametersObjectMother.getFactory(),
                DataQualitySensorRunnerObjectMother.getDefault(),
                ConnectionProviderRegistryObjectMother.getInstance(),
                sensorReadoutsNormalizationService,
                ruleEvaluationService,
                SensorReadoutsSnapshotFactoryObjectMother.createDummySensorReadoutStorageService(),
                RuleResultsSnapshotFactoryObjectMother.createDummyRuleResultsStorageService(),
                errorsNormalizationService,
                ErrorsSnapshotFactoryObjectMother.createDummyErrorsStorageService(),
                scheduledTargetChecksFindService,
                RuleDefinitionFindServiceObjectMother.getRuleDefinitionFindService(),
                notificationService);
    }

    @Test
    void executeChecks_whenRequestedExecutionByType_thenResultsAddUpToAllRun() {
        CheckSearchFilters allFilters = new CheckSearchFilters() {{
            setConnectionName(connectionWrapper.getName());
        }};

        CheckSearchFilters profilingFilters = allFilters.clone();
        profilingFilters.setCheckType(CheckType.PROFILING);

        CheckSearchFilters checkpointFilters = allFilters.clone();
        checkpointFilters.setCheckType(CheckType.CHECKPOINT);

        CheckSearchFilters partitionedFilters = allFilters.clone();
        partitionedFilters.setCheckType(CheckType.PARTITIONED);

        CheckExecutionSummary profilingSummary = this.sut.executeChecks(
                this.executionContext, profilingFilters, null, this.progressListener, true);
        CheckExecutionSummary checkpointSummary = this.sut.executeChecks(
                this.executionContext, checkpointFilters, null, this.progressListener, true);
        CheckExecutionSummary partitionedSummary = this.sut.executeChecks(
                this.executionContext, partitionedFilters, null, this.progressListener, true);

        CheckExecutionSummary allSummary = this.sut.executeChecks(
                this.executionContext, allFilters, null, this.progressListener, true);

        Assertions.assertEquals(0, partitionedSummary.getTotalChecksExecutedCount());
        Assertions.assertEquals(2, profilingSummary.getTotalChecksExecutedCount());
        Assertions.assertEquals(1, checkpointSummary.getTotalChecksExecutedCount());


        Assertions.assertEquals(1.0, profilingSummary.getValidResultsColumn().sum());
        Assertions.assertEquals(0.0, checkpointSummary.getValidResultsColumn().sum());
        Assertions.assertEquals(0.0, partitionedSummary.getValidResultsColumn().sum());

        Assertions.assertEquals(1, profilingSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(1, checkpointSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(0, partitionedSummary.getErrorSeverityIssuesCount());

        Assertions.assertEquals(allSummary.getTotalChecksExecutedCount(),
                profilingSummary.getTotalChecksExecutedCount() +
                        checkpointSummary.getTotalChecksExecutedCount() +
                        partitionedSummary.getTotalChecksExecutedCount());
        Assertions.assertEquals(allSummary.getErrorSeverityIssuesCount(),
                profilingSummary.getErrorSeverityIssuesCount() +
                        checkpointSummary.getErrorSeverityIssuesCount() +
                        partitionedSummary.getErrorSeverityIssuesCount());
        Assertions.assertEquals(allSummary.getValidResultsColumn().sum(),
                profilingSummary.getValidResultsColumn().sum() +
                        checkpointSummary.getValidResultsColumn().sum() +
                        partitionedSummary.getValidResultsColumn().sum());
    }
}
