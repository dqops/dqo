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
import ai.dqo.checks.CheckType;
import ai.dqo.checks.table.profiling.TableProfilingStandardChecksSpec;
import ai.dqo.checks.table.checkspecs.standard.TableRowCountCheckSpec;
import ai.dqo.connectors.ProviderDialectSettingsObjectMother;
import ai.dqo.connectors.ProviderType;
import ai.dqo.data.normalization.CommonTableNormalizationServiceImpl;
import ai.dqo.data.readouts.factory.SensorReadoutsColumnNames;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizationServiceImpl;
import ai.dqo.data.readouts.normalization.SensorReadoutsNormalizedResult;
import ai.dqo.data.readouts.snapshot.SensorReadoutsSnapshot;
import ai.dqo.data.readouts.snapshot.SensorReadoutsSnapshotFactory;
import ai.dqo.data.readouts.snapshot.SensorReadoutsSnapshotFactoryObjectMother;
import ai.dqo.execution.CheckExecutionContextObjectMother;
import ai.dqo.execution.ExecutionContext;
import ai.dqo.execution.checks.progress.CheckExecutionProgressListenerStub;
import ai.dqo.execution.rules.DataQualityRuleRunner;
import ai.dqo.execution.rules.DataQualityRuleRunnerObjectMother;
import ai.dqo.execution.rules.finder.RuleDefinitionFindServiceImpl;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.TimeWindowFilterParameters;
import ai.dqo.metadata.groupings.DataStreamMappingSpec;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.sources.ConnectionWrapper;
import ai.dqo.metadata.sources.PhysicalTableName;
import ai.dqo.metadata.sources.TableSpec;
import ai.dqo.metadata.sources.TableWrapper;
import ai.dqo.metadata.userhome.UserHome;
import ai.dqo.rules.comparison.MinCountRule0ParametersSpec;
import ai.dqo.rules.comparison.MinCountRuleFatalParametersSpec;
import ai.dqo.rules.comparison.MinCountRuleWarningParametersSpec;
import ai.dqo.services.timezone.DefaultTimeZoneProvider;
import ai.dqo.services.timezone.DefaultTimeZoneProviderObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

@SpringBootTest
public class RuleEvaluationServiceImplTests extends BaseTest {
    private RuleEvaluationServiceImpl sut;
    private SensorReadoutsNormalizationServiceImpl normalizeService;
    private Table table;
    private UserHome userHome;
    private TableSpec tableSpec;
    private TableRowCountCheckSpec checkSpec;
    private SensorExecutionRunParameters sensorExecutionRunParameters;
    private ExecutionContext executionContext;
    private CheckExecutionProgressListenerStub progressListener;
    private SensorExecutionResult sensorExecutionResult;
    private SensorReadoutsSnapshot sensorReadoutsSnapshot;

    @BeforeEach
    void setUp() {
        DataQualityRuleRunner ruleRunner = DataQualityRuleRunnerObjectMother.getDefault();
        DefaultTimeZoneProvider defaultTimeZoneProvider = DefaultTimeZoneProviderObjectMother.getDefaultTimeZoneProvider();
        this.sut = new RuleEvaluationServiceImpl(ruleRunner, new RuleDefinitionFindServiceImpl(), defaultTimeZoneProvider);
		this.normalizeService = new SensorReadoutsNormalizationServiceImpl(new CommonTableNormalizationServiceImpl(), defaultTimeZoneProvider);
		this.table = Table.create("results");
		executionContext = CheckExecutionContextObjectMother.createWithInMemoryUserContext();
		userHome = executionContext.getUserHomeContext().getUserHome();
        ConnectionWrapper connectionWrapper = userHome.getConnections().createAndAddNew("conn");
        connectionWrapper.getSpec().setProviderType(ProviderType.bigquery);
        TableWrapper tableWrapper = connectionWrapper.getTables().createAndAddNew(new PhysicalTableName("schema", "tab1"));
		tableSpec = tableWrapper.getSpec();
		checkSpec = new TableRowCountCheckSpec();
        tableSpec.getChecks().setStandard(new TableProfilingStandardChecksSpec());
		tableSpec.getChecks().getStandard().setRowCount(this.checkSpec);
		sensorExecutionRunParameters = new SensorExecutionRunParameters(connectionWrapper.getSpec(), tableSpec, null,
				checkSpec,
                null,
                CheckType.PROFILING,
                TimeSeriesConfigurationSpec.createCurrentTimeMilliseconds(),
                new TimeWindowFilterParameters(),
                new DataStreamMappingSpec(),
                checkSpec.getParameters(),
                ProviderDialectSettingsObjectMother.getDialectForProvider(ProviderType.bigquery));
		progressListener = new CheckExecutionProgressListenerStub();
		sensorExecutionResult = new SensorExecutionResult(this.sensorExecutionRunParameters, this.table);
        SensorReadoutsSnapshotFactory dummySensorReadoutStorageService = SensorReadoutsSnapshotFactoryObjectMother.createDummySensorReadoutStorageService();
		sensorReadoutsSnapshot = dummySensorReadoutStorageService.createSnapshot("conn", tableSpec.getPhysicalTableName());
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndWarningSeverityRuleOnlyAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.0));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.day, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleWarningParametersSpec(11L));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndWarningSeverityRuleOnlyAndNotPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.0));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.day, this.sensorExecutionRunParameters);
        this.checkSpec.setWarning(new MinCountRuleWarningParametersSpec(11L));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAlertSeverityRuleOnlyAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.0));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.day, this.sensorExecutionRunParameters);
		this.checkSpec.setError(new MinCountRule0ParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAlertSeverityRuleOnlyAndNotPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.0));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.day, this.sensorExecutionRunParameters);
		this.checkSpec.setError(new MinCountRule0ParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(2, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndFatalSeverityRuleOnlyAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.0));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.day, this.sensorExecutionRunParameters);
		this.checkSpec.setFatal(new MinCountRuleFatalParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndFatalSeverityRuleOnlyAndNotPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.0));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.day, this.sensorExecutionRunParameters);
		this.checkSpec.setFatal(new MinCountRuleFatalParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(3, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllWarningAlertFatalLevelsConfiguredAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 15.0));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.day, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleWarningParametersSpec(12));
		this.checkSpec.setError(new MinCountRule0ParametersSpec(11));
		this.checkSpec.setFatal(new MinCountRuleFatalParametersSpec(10));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllWarningAlertFatalLevelsConfiguredAndFatalFailed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 9.0));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.day, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleWarningParametersSpec(12));
		this.checkSpec.setError(new MinCountRule0ParametersSpec(11));
		this.checkSpec.setFatal(new MinCountRuleFatalParametersSpec(10));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(3, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllWarningAlertFatalLevelsConfiguredAndAlertFailed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 11.0));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.day, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleWarningParametersSpec(13));
		this.checkSpec.setError(new MinCountRule0ParametersSpec(12));
		this.checkSpec.setFatal(new MinCountRuleFatalParametersSpec(10));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(2, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(13.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllWarningAlertFatalLevelsConfiguredAndWarningFailed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.0));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.day, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleWarningParametersSpec(13));
		this.checkSpec.setError(new MinCountRule0ParametersSpec(11));
		this.checkSpec.setFatal(new MinCountRuleFatalParametersSpec(10));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(13.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenTwoRowsAndOneRule_thenReturnsTwoResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 11.0, 12.0));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.day, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleWarningParametersSpec(14));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(14.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(0));

        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(1));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(1));
        Assertions.assertEquals(14.0, evaluationResult.getWarningLowerBoundColumn().get(1));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(1));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(1));
    }

    @Test
    void evaluateRules_whenTwoRowsForDifferentTimeSeriesAndOneRule_thenReturnsTwoResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 11.0, 10.0));
		this.table.addColumns(StringColumn.create("stream_level_1", "one", "two"));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, TimeSeriesGradient.day, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRuleWarningParametersSpec(12));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(2324401329629152617L, evaluationResult.getRuleResultsTable().column("data_stream_hash").get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(0));

        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(1));
        Assertions.assertNull(evaluationResult.getExpectedValueColumn().get(1));
        Assertions.assertEquals(12.0, evaluationResult.getWarningLowerBoundColumn().get(1));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(6373675363437324823L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(1454728803102928799L, evaluationResult.getRuleResultsTable().column("data_stream_hash").get(1));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(1));
    }
}
