/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rules.comparison;

import com.dqops.BaseTest;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.rules.HistoricDataPoint;
import com.dqops.execution.rules.HistoricDataPointObjectMother;
import com.dqops.execution.rules.RuleExecutionResult;
import com.dqops.execution.rules.runners.python.PythonRuleRunnerObjectMother;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.rules.RuleTimeWindowSettingsSpec;
import com.dqops.rules.RuleTimeWindowSettingsSpecObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * Unit tests for max_failures python rule.
 */
@SpringBootTest
public class MaxFailuresRuleParametersSpecTests extends BaseTest {
    private MaxFailuresRule1ParametersSpec sut;
    private RuleTimeWindowSettingsSpec timeWindowSettings;
    private LocalDateTime readoutTimestamp;
    private Double[] sensorReadouts;

    @BeforeEach
    void setUp() {
        this.sut = new MaxFailuresRule1ParametersSpec();
        SampleTableMetadata sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.bigquery);
        UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.timeWindowSettings = RuleTimeWindowSettingsSpecObjectMother.getRealTimeWindowSettings(this.sut.getRuleDefinitionName());
        this.readoutTimestamp = LocalDateTime.of(2022, 2, 15, 0, 0);
    }

    @Test
    void timeWindowSettingsMinPeriodsWithReadouts_whenMaxFailureSensor_thenRequires0MinimumHistoricalReadouts() {
        Assertions.assertEquals(0, this.timeWindowSettings.getMinPeriodsWithReadouts());
    }

    @Test
    void timeWindowSettingsPredictionTimeWindow_whenMaxFailureSensor_thenRequiresHistorical60ReadoutsForRuleEvaluation() {
        Assertions.assertEquals(60, this.timeWindowSettings.getPredictionTimeWindow());
    }

    @Test
    void executeRuleMaxFailures_whenCurrentResultPassAndPreviousAreFailuresAndMaxFailures5_thenReturnsPassed() {
        this.sut.setMaxFailures(5L);

        Double[] previousReadouts = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        this.sensorReadouts = previousReadouts;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(0.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(0.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(5.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRuleMaxFailures_whenCurrentResultPassAndPreviousAreFailuresAndMaxFailures0_thenReturnsPassed() {
        this.sut.setMaxFailures(0L);

        Double[] previousReadouts = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        this.sensorReadouts = previousReadouts;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(0.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(0.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(0.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRuleMaxFailures_whenCurrentResultFailAndPreviousArePassAndMaxFailures1_thenReturnsPassed() {
        this.sut.setMaxFailures(1L);

        Double[] previousReadouts = {0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        this.sensorReadouts = previousReadouts;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(0.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(1.0, ruleExecutionResult.getUpperBound());
        Assertions.assertEquals(1.0, ruleExecutionResult.getNewActualValue());
    }

    @Test
    void executeRuleMaxFailures_whenCurrentResultFailAndPreviousArePassButRecentValueAreNullAndMaxFailures1_thenReturnsPassed() {
        this.sut.setMaxFailures(1L);

        Double[] previousReadouts = {0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, null, null};

        this.sensorReadouts = previousReadouts;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(0.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(1.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRuleMaxFailures_whenCurrentResultFailAndPreviousArePassButRecentValueAreNullAndMaxFailures5_thenReturnsPassed() {
        this.sut.setMaxFailures(5L);

        Double[] previousReadouts = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, null, 1.0, 1.0, null, null, null};
        this.sensorReadouts = previousReadouts;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(0.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(5.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRuleMaxFailures_whenCurrentResultFailAndPreviousArePassAndMaxFailures0_thenReturnsFailed() {
        this.sut.setMaxFailures(0L);

        Double[] previousReadouts = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        this.sensorReadouts = previousReadouts;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(0.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(0.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRuleMaxFailures_whenCurrentResultFailAndPreviousAreAllFailuresAndMaxFailures1_thenReturnsFailed() {
        this.sut.setMaxFailures(1L);

        Double[] previousReadouts = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        this.sensorReadouts = previousReadouts;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(0.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(1.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRuleMaxFailures_whenCurrentResultFailAndPreviousAreAllFailuresAndMaxFailures5_thenReturnsFailed() {
        this.sut.setMaxFailures(5L);

        Double[] previousReadouts = {1.0, 0.0, 1.0, 1.0, 1.0, null, 1.0, 1.0, null, 1.0, 1.0, 1.0};
        this.sensorReadouts = previousReadouts;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(0.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(5.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRuleMaxFailures_whenCurrentResultFailAndPreviousAreAllFailuresButOnly3AndMaxFailures5_thenReturnsPassed() {
        this.sut.setMaxFailures(5L);

        Double[] previousReadouts = {1.0, null, null, null, null, null, 1.0, null, 1.0, null};
        this.sensorReadouts = previousReadouts;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(0.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(5.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRuleMaxFailures_whenActualValueIsNull_thenReturnsPassed() {
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(null, this.sut);
        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertNull(ruleExecutionResult.getExpectedValue());
        Assertions.assertNull(ruleExecutionResult.getLowerBound());
        Assertions.assertNull(ruleExecutionResult.getUpperBound());
    }
}
