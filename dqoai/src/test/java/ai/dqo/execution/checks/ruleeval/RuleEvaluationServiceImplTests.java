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
import ai.dqo.checks.table.validity.TableValidityRowCountCheckSpec;
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
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rules.comparison.MinValueRuleParametersSpec;
import ai.dqo.rules.comparison.MinValueRuleThresholdsSpec;
import ai.dqo.rules.comparison.ValueEqualsRuleParametersSpec;
import ai.dqo.rules.comparison.ValueEqualsRuleThresholdsSpec;
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
    private TableValidityRowCountCheckSpec checkSpec;
    private SensorExecutionRunParameters sensorExecutionRunParameters;
    private MinValueRuleThresholdsSpec minRowCountThresholds;
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
		this.sut = new RuleEvaluationServiceImpl(ruleRunner);
		this.normalizeService = new SensorResultNormalizeServiceImpl();
		this.table = Table.create("results");
		checkExecutionContext = CheckExecutionContextObjectMother.createWithInMemoryUserContext();
		userHome = checkExecutionContext.getUserHomeContext().getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("conn");
        connectionWrapper.getSpec().setProviderType(ProviderType.bigquery);
		this.utcZone = connectionWrapper.getSpec().getJavaTimeZoneId();
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "tab1"));
		tableSpec = tableWrapper.getSpec();
		checkSpec = new TableValidityRowCountCheckSpec();
		minRowCountThresholds = new MinValueRuleThresholdsSpec();
		checkSpec.getRules().setMinRowCount(minRowCountThresholds);
		tableSpec.getChecks().getValidity().setRowCount(checkSpec);
		sensorExecutionRunParameters = new SensorExecutionRunParameters(connectionWrapper.getSpec(), tableSpec, null,
				checkSpec.getHierarchyId(),
                checkSpec.getTimeSeriesOverride(),
                checkSpec.getDataStreamsOverride(),
                checkSpec.getSensorParameters(),
                ProviderDialectSettingsObjectMother.getDialectForProvider(ProviderType.bigquery));
		progressListener = new CheckExecutionProgressListenerStub();
		sensorExecutionResult = new SensorExecutionResult(this.sensorExecutionRunParameters, this.table);
        SensorReadingsSnapshotFactory dummySensorReadingStorageService = SensorReadingsSnapshotFactoryObjectMother.createDummySensorReadingStorageService();
		sensorReadingsSnapshot = dummySensorReadingStorageService.createSnapshot("conn", tableSpec.getTarget().toPhysicalTableName());
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndLowSeverityRuleOnlyAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		minRowCountThresholds.setLow(new MinValueRuleParametersSpec(11.5));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndLowSeverityRuleOnlyAndNotPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.5));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		minRowCountThresholds.setLow(new MinValueRuleParametersSpec(11.5));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndMediumSeverityRuleOnlyAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		minRowCountThresholds.setMedium(new MinValueRuleParametersSpec(11.5));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndMediumSeverityRuleOnlyAndNotPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.5));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		minRowCountThresholds.setMedium(new MinValueRuleParametersSpec(11.5));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(2, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndHighSeverityRuleOnlyAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		minRowCountThresholds.setHigh(new MinValueRuleParametersSpec(11.5));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndHighSeverityRuleOnlyAndNotPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.5));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		minRowCountThresholds.setHigh(new MinValueRuleParametersSpec(11.5));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(3, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllLowMidHighLevelsConfiguredAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.5));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		minRowCountThresholds.setLow(new MinValueRuleParametersSpec(11.5));
		minRowCountThresholds.setMedium(new MinValueRuleParametersSpec(11.0));
		minRowCountThresholds.setHigh(new MinValueRuleParametersSpec(10.5));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(10.5, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertEquals(10.5, evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllLowMidHighLevelsConfiguredAndHighFailed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.4));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		minRowCountThresholds.setLow(new MinValueRuleParametersSpec(11.5));
		minRowCountThresholds.setMedium(new MinValueRuleParametersSpec(11.0));
		minRowCountThresholds.setHigh(new MinValueRuleParametersSpec(10.5));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(3, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(10.5, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertEquals(10.5, evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllLowMidHighLevelsConfiguredAndMediumFailed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.9));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		minRowCountThresholds.setLow(new MinValueRuleParametersSpec(11.5));
		minRowCountThresholds.setMedium(new MinValueRuleParametersSpec(11.0));
		minRowCountThresholds.setHigh(new MinValueRuleParametersSpec(10.5));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(2, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(10.5, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertEquals(10.5, evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllLowMidHighLevelsConfiguredAndLowFailed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 11.2));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		minRowCountThresholds.setLow(new MinValueRuleParametersSpec(11.5));
		minRowCountThresholds.setMedium(new MinValueRuleParametersSpec(11.0));
		minRowCountThresholds.setHigh(new MinValueRuleParametersSpec(10.5));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(10.5, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertEquals(10.5, evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndTwoRulesEnabled_thenReturnsTwoResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 11.2));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		minRowCountThresholds.setLow(new MinValueRuleParametersSpec(11.5));
        ValueEqualsRuleThresholdsSpec rowCountEquals = new ValueEqualsRuleThresholdsSpec();
        rowCountEquals.setMedium(new ValueEqualsRuleParametersSpec(12.5, 0.01));
		checkSpec.getRules().setRowCountEquals(rowCountEquals);

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(2, resultTable.rowCount());
        Assertions.assertEquals(2, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(12.50, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(12.51, evaluationResult.getMediumUpperBoundColumn().get(0));
        Assertions.assertEquals(12.49, evaluationResult.getMediumLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(334589435897980510L, evaluationResult.getRuleHashColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(0));

        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(1));
        Assertions.assertEquals(11.5, evaluationResult.getExpectedValueColumn().get(1));
        Assertions.assertEquals(11.5, evaluationResult.getLowLowerBoundColumn().get(1));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(1));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(1));
    }

    @Test
    void evaluateRules_whenTwoRowsAndOneRule_thenReturnsTwoResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 11.2, 11.3));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		minRowCountThresholds.setLow(new MinValueRuleParametersSpec(11.5));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(0));

        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(1));
        Assertions.assertEquals(11.5, evaluationResult.getExpectedValueColumn().get(1));
        Assertions.assertEquals(11.5, evaluationResult.getLowLowerBoundColumn().get(1));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(1));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(1));
    }

    @Test
    void evaluateRules_whenTwoRowsForDifferentDimensionsAndOneRule_thenReturnsTwoResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 11.2, 11.3));
		this.table.addColumns(StringColumn.create("dimension_1", "one", "two"));
        SensorNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.DAY, this.sensorExecutionRunParameters);
		minRowCountThresholds.setLow(new MinValueRuleParametersSpec(11.5));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(checkExecutionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadingsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.5, evaluationResult.getLowLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(0));
        Assertions.assertEquals(2324401329629152617L, evaluationResult.getRuleResultsTable().column("dimension_id").get(0));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(0));

        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(1));
        Assertions.assertEquals(11.5, evaluationResult.getExpectedValueColumn().get(1));
        Assertions.assertEquals(11.5, evaluationResult.getLowLowerBoundColumn().get(1));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorNormalizedResult.CONNECTION_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorNormalizedResult.TABLE_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(1343052445123349368L, resultTable.column(SensorNormalizedResult.CHECK_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(6158854366931482287L, evaluationResult.getRuleHashColumn().get(1));
        Assertions.assertEquals(1454728803102928799L, evaluationResult.getRuleResultsTable().column("dimension_id").get(1));
        Assertions.assertNull(evaluationResult.getLowUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getHighUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getHighLowerBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getMediumUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getMediumLowerBoundColumn().get(1));
    }
}
