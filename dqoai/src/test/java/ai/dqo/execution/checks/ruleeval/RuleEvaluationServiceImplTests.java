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
package ai.dqo.execution.checks.ruleeval;

import ai.dqo.BaseTest;
import ai.dqo.checks.table.adhoc.TableAdHocStandardChecksSpec;
import ai.dqo.checks.table.checks.standard.TableMinRowCountCheckSpec;
import ai.dqo.connectors.ProviderDialectSettingsObjectMother;
import ai.dqo.connectors.ProviderType;
import ai.dqo.data.readings.normalization.SensorNormalizedResult;
import ai.dqo.data.readings.normalization.SensorResultNormalizeServiceImpl;
import ai.dqo.data.readings.snapshot.SensorReadingsSnapshot;
import ai.dqo.data.readings.snapshot.SensorReadingsSnapshotFactory;
import ai.dqo.data.readings.snapshot.SensorReadingsSnapshotFactoryObjectMother;
import ai.dqo.execution.CheckExecutionContext;
import ai.dqo.execution.CheckExecutionContextObjectMother;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListenerStub;
import ai.dqo.execution.rules.DataQualityRuleRunner;
import ai.dqo.execution.rules.DataQualityRuleRunnerObjectMother;
import ai.dqo.execution.rules.finder.RuleDefinitionFindServiceImpl;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rules.comparison.MinCountRuleParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import java.time.ZoneId;

@SpringBootTest
public class RuleEvaluationServiceImplTests extends BaseTest {
    private RuleEvaluationServiceImpl sut;
    private SensorResultNormalizeServiceImpl normalizeService;
    private ZoneId utcZone;
    private Table table;
    private UserHome userHome;
    private TableSpec tableSpec;
    private TableMinRowCountCheckSpec checkSpec;
    private SensorExecutionRunParameters sensorExecutionRunParameters;
    private CheckExecutionContext checkExecutionContext;
    private CheckExecutionProgressListenerStub progressListener;
    private SensorExecutionResult sensorExecutionResult;
    private SensorReadingsSnapshot sensorReadingsSnapshot;

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
        DataQualityRuleRunner ruleRunner = DataQualityRuleRunnerObjectMother.getDefault();
		this.sut = new RuleEvaluationServiceImpl(ruleRunner, new RuleDefinitionFindServiceImpl());
		this.normalizeService = new SensorResultNormalizeServiceImpl();
		this.table = Table.create("results");
		checkExecutionContext = CheckExecutionContextObjectMother.createWithInMemoryUserContext();
		userHome = checkExecutionContext.getUserHomeContext().getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("conn");
        connectionWrapper.getSpec().setProviderType(ProviderType.bigquery);
		this.utcZone = connectionWrapper.getSpec().getJavaTimeZoneId();
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "tab1"));
		tableSpec = tableWrapper.getSpec();
		checkSpec = new TableMinRowCountCheckSpec();
        tableSpec.getChecks().setStandard(new TableAdHocStandardChecksSpec());
		tableSpec.getChecks().getStandard().setMinRowCount(this.checkSpec);
		sensorExecutionRunParameters = new SensorExecutionRunParameters(connectionWrapper.getSpec(), tableSpec, null,
				checkSpec.getHierarchyId(),
                TimeSeriesConfigurationSpec.createDefault(),
                new DataStreamMappingSpec(),
                checkSpec.getParameters(),
                ProviderDialectSettingsObjectMother.getDialectForProvider(ProviderType.bigquery));
		progressListener = new CheckExecutionProgressListenerStub();
		sensorExecutionResult = new SensorExecutionResult(this.sensorExecutionRunParameters, this.table);
        SensorReadingsSnapshotFactory dummySensorReadingStorageService = SensorReadingsSnapshotFactoryObjectMother.createDummySensorReadingStorageService();
		sensorReadingsSnapshot = dummySensorReadingStorageService.createSnapshot("conn", tableSpec.getTarget().toPhysicalTableName());
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndWarningSeverityRuleOnlyAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.0));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndWarningSeverityRuleOnlyAndNotPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.0));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
        this.checkSpec.setWarning(new MinCountRuleParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAlertSeverityRuleOnlyAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.0));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		this.checkSpec.setAlert(new MinCountRuleParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAlertSeverityRuleOnlyAndNotPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.0));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		this.checkSpec.setAlert(new MinCountRuleParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(2, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndFatalSeverityRuleOnlyAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.0));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		this.checkSpec.setFatal(new MinCountRuleParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndFatalSeverityRuleOnlyAndNotPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.0));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		this.checkSpec.setFatal(new MinCountRuleParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(3, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllWarningAlertFatalLevelsConfiguredAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 15.0));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleParametersSpec(12));
		this.checkSpec.setAlert(new MinCountRuleParametersSpec(11));
		this.checkSpec.setFatal(new MinCountRuleParametersSpec(10));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllWarningAlertFatalLevelsConfiguredAndFatalFailed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 9.0));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleParametersSpec(12));
		this.checkSpec.setAlert(new MinCountRuleParametersSpec(11));
		this.checkSpec.setFatal(new MinCountRuleParametersSpec(10));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(3, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllWarningAlertFatalLevelsConfiguredAndAlertFailed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 11.0));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleParametersSpec(13));
		this.checkSpec.setAlert(new MinCountRuleParametersSpec(12));
		this.checkSpec.setFatal(new MinCountRuleParametersSpec(10));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(2, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(13.0, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllWarningAlertFatalLevelsConfiguredAndWarningFailed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.0));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleParametersSpec(13));
		this.checkSpec.setAlert(new MinCountRuleParametersSpec(11));
		this.checkSpec.setFatal(new MinCountRuleParametersSpec(10));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(13.0, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenTwoRowsAndOneRule_thenReturnsTwoResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 11.0, 12.0));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleParametersSpec(14));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(14.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(14.0, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(0));

        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(1));
        Assertions.assertEquals(14.0, evaluationResult.getExpectedValueColumn().get(1));
        Assertions.assertEquals(14.0, evaluationResult.getLowLowerBoundColumn().get(1));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(1));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(1));
    }

    @Test
    void evaluateRules_whenTwoRowsForDifferentTimeSeriesAndOneRule_thenReturnsTwoResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 11.0, 10.0));
		this.table.addColumns(StringColumn.create("dimension_1", "one", "two"));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleParametersSpec(12));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(2324401329629152617L, evaluationResult.getRuleResultsTable().column("dimension_id").get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(0));

        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(1));
        Assertions.assertEquals(12.0, evaluationResult.getExpectedValueColumn().get(1));
        Assertions.assertEquals(12.0, evaluationResult.getLowLowerBoundColumn().get(1));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(8830480320851780991L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(1454728803102928799L, evaluationResult.getRuleResultsTable().column("dimension_id").get(1));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(1));
    }
}
