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
package com.dqops.execution.checks.ruleeval;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.CheckType;
import com.dqops.checks.table.profiling.TableVolumeProfilingChecksSpec;
import com.dqops.checks.table.checkspecs.volume.TableRowCountCheckSpec;
import com.dqops.connectors.ProviderDialectSettingsObjectMother;
import com.dqops.connectors.ProviderType;
import com.dqops.core.principal.UserDomainIdentity;
import com.dqops.core.principal.UserDomainIdentityObjectMother;
import com.dqops.data.normalization.CommonTableNormalizationServiceImpl;
import com.dqops.data.readouts.factory.SensorReadoutsColumnNames;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizationServiceImpl;
import com.dqops.data.readouts.normalization.SensorReadoutsNormalizedResult;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshot;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshotFactory;
import com.dqops.data.readouts.snapshot.SensorReadoutsSnapshotFactoryObjectMother;
import com.dqops.execution.CheckExecutionContextObjectMother;
import com.dqops.execution.ExecutionContext;
import com.dqops.execution.checks.EffectiveSensorRuleNames;
import com.dqops.execution.checks.progress.CheckExecutionProgressListenerStub;
import com.dqops.execution.rules.DataQualityRuleRunner;
import com.dqops.execution.rules.DataQualityRuleRunnerObjectMother;
import com.dqops.execution.rules.finder.RuleDefinitionFindServiceImpl;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.TimeWindowFilterParameters;
import com.dqops.metadata.groupings.*;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.sources.ConnectionWrapper;
import com.dqops.metadata.sources.PhysicalTableName;
import com.dqops.metadata.sources.TableSpec;
import com.dqops.metadata.sources.TableWrapper;
import com.dqops.metadata.userhome.UserHome;
import com.dqops.rules.comparison.*;
import com.dqops.services.timezone.DefaultTimeZoneProvider;
import com.dqops.services.timezone.DefaultTimeZoneProviderObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TextColumn;

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
        tableSpec.setDefaultDataGroupingConfiguration(new DataGroupingConfigurationSpec());
		checkSpec = new TableRowCountCheckSpec();
        tableSpec.getProfilingChecks().setVolume(new TableVolumeProfilingChecksSpec());
		tableSpec.getProfilingChecks().getVolume().setProfileRowCount(this.checkSpec);
		sensorExecutionRunParameters = new SensorExecutionRunParameters(connectionWrapper.getSpec(), tableSpec, null,
				checkSpec,
                null,
                new EffectiveSensorRuleNames(checkSpec.getParameters().getSensorDefinitionName(), new MinCountRule1ParametersSpec().getRuleDefinitionName()),
                CheckType.profiling,
                TimeSeriesConfigurationSpec.createCurrentTimeMilliseconds(),
                new TimeWindowFilterParameters(),
                tableSpec.getDefaultDataGroupingConfiguration(),
                null,
                null,
                checkSpec.getParameters(),
                ProviderDialectSettingsObjectMother.getDialectForProvider(ProviderType.bigquery),
                null,
                1000,
                true);
		progressListener = new CheckExecutionProgressListenerStub();
		sensorExecutionResult = new SensorExecutionResult(this.sensorExecutionRunParameters, this.table);
        SensorReadoutsSnapshotFactory dummySensorReadoutStorageService = SensorReadoutsSnapshotFactoryObjectMother.createDummySensorReadoutStorageService();
        UserDomainIdentity userDomainIdentity = UserDomainIdentityObjectMother.createAdminIdentity();
        sensorReadoutsSnapshot = dummySensorReadoutStorageService.createSnapshot("conn", tableSpec.getPhysicalTableName(), userDomainIdentity);
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndWarningSeverityRuleOnlyAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.0));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));
                SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRule1ParametersSpec(11L));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndWarningSeverityRuleOnlyAndNotPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.0));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, this.sensorExecutionRunParameters);
        this.checkSpec.setWarning(new MinCountRule1ParametersSpec(11L));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAlertSeverityRuleOnlyAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.0));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, this.sensorExecutionRunParameters);
		this.checkSpec.setError(new MinCountRule1ParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAlertSeverityRuleOnlyAndNotPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.0));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, this.sensorExecutionRunParameters);
		this.checkSpec.setError(new MinCountRule1ParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(2, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndFatalSeverityRuleOnlyAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.0));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, this.sensorExecutionRunParameters);
		this.checkSpec.setFatal(new MinCountRule1ParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndFatalSeverityRuleOnlyAndNotPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 10.0));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, this.sensorExecutionRunParameters);
		this.checkSpec.setFatal(new MinCountRule1ParametersSpec(11));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(3, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllWarningAlertFatalLevelsConfiguredAndPassed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 15.0));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRule1ParametersSpec(12));
		this.checkSpec.setError(new MinCountRule1ParametersSpec(11));
		this.checkSpec.setFatal(new MinCountRule1ParametersSpec(10));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(0, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllWarningAlertFatalLevelsConfiguredAndFatalFailed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 9.0));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRule1ParametersSpec(12));
		this.checkSpec.setError(new MinCountRule1ParametersSpec(11));
		this.checkSpec.setFatal(new MinCountRule1ParametersSpec(10));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(3, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllWarningAlertFatalLevelsConfiguredAndAlertFailed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 11.0));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRule1ParametersSpec(13));
		this.checkSpec.setError(new MinCountRule1ParametersSpec(12));
		this.checkSpec.setFatal(new MinCountRule1ParametersSpec(10));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(2, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(13.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(13.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenOneRowAndMinValueRuleAndAllWarningAlertFatalLevelsConfiguredAndWarningFailed_thenReturnsResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 12.0));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRule1ParametersSpec(13));
		this.checkSpec.setError(new MinCountRule1ParametersSpec(11));
		this.checkSpec.setFatal(new MinCountRule1ParametersSpec(10));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(13.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(13.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(11.0, evaluationResult.getErrorLowerBoundColumn().get(0));
        Assertions.assertEquals(10.0, evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
    }

    @Test
    void evaluateRules_whenTwoRowsAndOneRule_thenReturnsTwoResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 11.0, 12.0));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRule1ParametersSpec(14));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(14.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(14.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(0));

        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(1));
        Assertions.assertEquals(14.0, evaluationResult.getExpectedValueColumn().get(1));
        Assertions.assertEquals(14.0, evaluationResult.getWarningLowerBoundColumn().get(1));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(1));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(1));
    }

    @Test
    void evaluateRules_whenTwoRowsForDifferentTimeSeriesAndOneRule_thenReturnsTwoResults() {
		this.table.addColumns(DoubleColumn.create("actual_value", 11.0, 10.0));
		this.table.addColumns(TextColumn.create("grouping_level_1", "one", "two"));
        this.tableSpec.getDefaultDataGroupingConfiguration()
                .setLevel1(DataStreamLevelSpecObjectMother.createColumnMapping("length_string"));
        this.sensorExecutionRunParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimeSeriesForPartitionedCheck(
                CheckTimeScale.daily, "date"));
        SensorReadoutsNormalizedResult normalizedResult = this.normalizeService.normalizeResults(
				this.sensorExecutionResult, this.sensorExecutionRunParameters);
		this.checkSpec.setWarning(new MinCountRule1ParametersSpec(12));

        RuleEvaluationResult evaluationResult = this.sut.evaluateRules(executionContext, this.checkSpec,
				this.sensorExecutionRunParameters, normalizedResult, this.sensorReadoutsSnapshot, progressListener);

        Table resultTable = evaluationResult.getRuleResultsTable();
        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getExpectedValueColumn().get(0));
        Assertions.assertEquals(12.0, evaluationResult.getWarningLowerBoundColumn().get(0));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(0));
        Assertions.assertEquals(2333338297035482505L, evaluationResult.getRuleResultsTable().column("data_group_hash").get(0));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(0));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(0));

        Assertions.assertEquals(1, evaluationResult.getSeverityColumn().get(1));
        Assertions.assertEquals(12.0, evaluationResult.getExpectedValueColumn().get(1));
        Assertions.assertEquals(12.0, evaluationResult.getWarningLowerBoundColumn().get(1));
        Assertions.assertEquals(4093888846442877636L, resultTable.column(SensorReadoutsColumnNames.CONNECTION_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(8593232963387153742L, resultTable.column(SensorReadoutsColumnNames.TABLE_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(3144014129830228863L, resultTable.column(SensorReadoutsColumnNames.CHECK_HASH_COLUMN_NAME).get(1));
        Assertions.assertEquals(5453616552587120769L, evaluationResult.getRuleResultsTable().column("data_group_hash").get(1));
        Assertions.assertNull(evaluationResult.getWarningUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getFatalUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getFatalLowerBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getErrorUpperBoundColumn().get(1));
        Assertions.assertNull(evaluationResult.getErrorLowerBoundColumn().get(1));
    }
}
