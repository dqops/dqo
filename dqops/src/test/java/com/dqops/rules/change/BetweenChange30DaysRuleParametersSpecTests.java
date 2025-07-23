/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rules.change;

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

@SpringBootTest
public class BetweenChange30DaysRuleParametersSpecTests extends BaseTest {
    private BetweenChange30DaysRuleParametersSpec sut;
    private RuleTimeWindowSettingsSpec timeWindowSettings;
    private LocalDateTime readoutTimestamp;
    private Double[] sensorReadouts;

    private SampleTableMetadata sampleTableMetadata;
    private UserHomeContext userHomeContext;

    @BeforeEach
    void setUp() {
        this.sut = new BetweenChange30DaysRuleParametersSpec();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.timeWindowSettings = RuleTimeWindowSettingsSpecObjectMother.getRealTimeWindowSettings(this.sut.getRuleDefinitionName());
        this.readoutTimestamp = LocalDateTime.of(2022, 02, 15, 0, 0);
        this.sensorReadouts = new Double[this.timeWindowSettings.getPredictionTimeWindow()];
    }

    @Test
    void executeRule_whenActualValueIsBetweenRangeFromPreviousReadout_thenReturnsPassed() {
        this.sut.setFrom(-1.0);
        this.sut.setTo(0.0);
        this.sut.setExactDay(false);

        int readoutsCount = this.timeWindowSettings.getPredictionTimeWindow();
        this.sensorReadouts[readoutsCount - 42] = 20.0;
        this.sensorReadouts[readoutsCount - 16] = 1219.2;
        this.sensorReadouts[readoutsCount - 5] = 1010008.2;
        this.sensorReadouts[readoutsCount - 8] = 0.0;
        this.sensorReadouts[readoutsCount - 3] = 1006.8;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(19.1,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(19.5, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(20.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsEqualToPreviousReadoutAndRangeIsZero_thenReturnsPassed() {
        this.sut.setFrom(0.0);
        this.sut.setTo(0.0);
        this.sut.setExactDay(true);

        int readoutsCount = this.timeWindowSettings.getPredictionTimeWindow();
        this.sensorReadouts[readoutsCount - 42] = 23.0;
        this.sensorReadouts[readoutsCount - 30] = 20.0;
        this.sensorReadouts[readoutsCount - 6] = 1219.2;
        this.sensorReadouts[readoutsCount - 5] = 1010008.2;
        this.sensorReadouts[readoutsCount - 4] = 0.0;
        this.sensorReadouts[readoutsCount - 2] = 1006.8;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(20.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(20.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsGreaterThanAllowed_thenReturnsFailed() {
        this.sut.setFrom(10.0);
        this.sut.setTo(300.0);
        this.sut.setExactDay(true);

        int readoutsCount = this.timeWindowSettings.getPredictionTimeWindow();
        this.sensorReadouts[readoutsCount - 30] = 20.0;
        this.sensorReadouts[readoutsCount - 16] = 1219.2;
        this.sensorReadouts[readoutsCount - 15] = 1010008.2;
        this.sensorReadouts[readoutsCount - 8] = 0.0;
        this.sensorReadouts[readoutsCount - 4] = 1006.8;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1251.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(175.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(30.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(320.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenExactReadoutIsNull_thenReturnsPassed() {
        this.sut.setFrom(10.0);
        this.sut.setTo(300.0);
        this.sut.setExactDay(true);

        int readoutsCount = this.timeWindowSettings.getPredictionTimeWindow();
        this.sensorReadouts[readoutsCount - 31] = 12.0;
        this.sensorReadouts[readoutsCount - 30] = null;
        this.sensorReadouts[readoutsCount - 16] = 30.2;
        this.sensorReadouts[readoutsCount - 8] = 50.2;
        this.sensorReadouts[readoutsCount - 3] = 80.6;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(-142.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertNull(ruleExecutionResult.getPassed());
        Assertions.assertEquals(null, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(null, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(null, ruleExecutionResult.getUpperBound());
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
