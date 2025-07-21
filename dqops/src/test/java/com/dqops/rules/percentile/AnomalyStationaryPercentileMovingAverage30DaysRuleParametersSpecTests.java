/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rules.percentile;

import com.dqops.BaseTest;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.rules.HistoricDataPoint;
import com.dqops.execution.rules.HistoricDataPointObjectMother;
import com.dqops.execution.rules.RuleExecutionResult;
import com.dqops.execution.rules.runners.python.PythonRuleRunnerObjectMother;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
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
import java.util.Arrays;

@SpringBootTest
public class AnomalyStationaryPercentileMovingAverage30DaysRuleParametersSpecTests extends BaseTest {
    private AnomalyStationaryPercentileMovingAverage30DaysRuleWarning1PctParametersSpec sut;
    private RuleTimeWindowSettingsSpec timeWindowSettings;
    private LocalDateTime readoutTimestamp;
    private Double[] sensorReadouts;

    private SampleTableMetadata sampleTableMetadata;
    private UserHomeContext userHomeContext;

    @BeforeEach
    void setUp() {
        this.sut = new AnomalyStationaryPercentileMovingAverage30DaysRuleWarning1PctParametersSpec();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.timeWindowSettings = RuleTimeWindowSettingsSpecObjectMother.getRealTimeWindowSettings(this.sut.getRuleDefinitionName());
        this.readoutTimestamp = LocalDateTime.of(2022, 2, 15, 0, 0);
        this.sensorReadouts = new Double[this.timeWindowSettings.getPredictionTimeWindow()];
    }

    @Test
    void executeRule_whenActualValueIsBelowMaxValueAndAllPastValuesArePresentChangeOnOddDays_thenReturnsPassed() {
        this.sut.setAnomalyPercent(15.0);

        for (int i = 0; i < this.sensorReadouts.length; i++) {
            this.sensorReadouts[i] = 20.0 + (i % 4);
        }

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(21.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.64, ruleExecutionResult.getLowerBound(), 0.1);
        Assertions.assertEquals(23.43, ruleExecutionResult.getUpperBound(), 0.1);
    }

    @Test
    void executeRule_whenActualValueIsAboveMaxValueAndAllPastValuesArePresentAndChangeOnOddDays_thenReturnsPassed() {
        this.sut.setAnomalyPercent(15.0);

        for (int i = 0; i < this.sensorReadouts.length; i++) {
            this.sensorReadouts[i] = 20.0 + (i % 4);
        }

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(40.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(21.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.64, ruleExecutionResult.getLowerBound(), 0.1);
        Assertions.assertEquals(23.43, ruleExecutionResult.getUpperBound(), 0.1);
    }

    @Test
    void executeRule_whenActualValueIsBelowMinValueAndAllPastValuesArePresentAndChangeOnOddDays_thenReturnsPassed() {
        this.sut.setAnomalyPercent(15.0);

        for (int i = 0; i < this.sensorReadouts.length; i++) {
            this.sensorReadouts[i] = 20.0 + (i % 4);
        }

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(10.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(21.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.64, ruleExecutionResult.getLowerBound(), 0.1);
        Assertions.assertEquals(23.43, ruleExecutionResult.getUpperBound(), 0.1);
    }

    @Test
    void executeRule_whenActualValueIsEqualToLastValueAndAllPastValuesArePresentChangeAndEqual_thenReturnsNull() {
        this.sut.setAnomalyPercent(20.0);

        for (int i = 0; i < this.sensorReadouts.length; i++) {
            this.sensorReadouts[i] = 20.0;
        }

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertNull(ruleExecutionResult.getPassed());
    }

    @Test
    void executeRule_whenActualValueIsNotEqualToLastValueAndAllPastValuesArePresentChangeAndEqual_thenReturnsFailed() {
        this.sut.setAnomalyPercent(20.0);

        for (int i = 0; i < this.sensorReadouts.length; i++) {
            this.sensorReadouts[i] = 20.0;
        }

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(21.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(20.0, ruleExecutionResult.getLowerBound(), 0.1);
        Assertions.assertEquals(20.0, ruleExecutionResult.getUpperBound(), 0.1);
    }

    @Test
    void executeRule_whenActualValueIsWithinQuantileAndPastValuesAreEqual_thenReturnsNull() {
        this.sut.setAnomalyPercent(20.0);

        Arrays.fill(this.sensorReadouts, 10.0);

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(10.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertNull(ruleExecutionResult.getPassed());
    }

    @Test
    void executeRule_whenActualValueIsNull_thenReturnsPassed() {
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(null,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertNull(ruleExecutionResult.getPassed());
        Assertions.assertEquals(null, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(null, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(null, ruleExecutionResult.getUpperBound());
    }
}
