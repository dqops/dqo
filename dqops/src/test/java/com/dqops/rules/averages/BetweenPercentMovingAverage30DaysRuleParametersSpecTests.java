/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.rules.averages;

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
public class BetweenPercentMovingAverage30DaysRuleParametersSpecTests extends BaseTest {
    private BetweenPercentMovingAverage30DaysRule5ParametersSpec sut;
    private RuleTimeWindowSettingsSpec timeWindowSettings;
    private LocalDateTime readoutTimestamp;
    private Double[] sensorReadouts;

    private SampleTableMetadata sampleTableMetadata;
    private UserHomeContext userHomeContext;

    @BeforeEach
    void setUp() {
        this.sut = new BetweenPercentMovingAverage30DaysRule5ParametersSpec();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.timeWindowSettings = RuleTimeWindowSettingsSpecObjectMother.getRealTimeWindowSettings(this.sut.getRuleDefinitionName());
        this.readoutTimestamp = LocalDateTime.of(2022, 02, 15, 0, 0);
        this.sensorReadouts = new Double[this.timeWindowSettings.getPredictionTimeWindow()];
    }

    @Test
    void executeRule_whenActualValueIsBelowMaxValueAndAllPastValuesArePresentAndEqual_thenReturnsPassed() {
        this.sut.setMaxPercentAbove(5.0);
        this.sut.setMaxPercentBelow(5.0);

        for (int i = 0; i < this.sensorReadouts.length; i++) {
            this.sensorReadouts[i] = 20.0;
        }
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.8,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(21.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsEqualMaxValueAndAllPastValuesArePresentAndEqual_thenReturnsPassed() {
        this.sut.setMaxPercentAbove(5.0);
        this.sut.setMaxPercentBelow(5.0);
        for (int i = 0; i < this.sensorReadouts.length; i++) {
            this.sensorReadouts[i] = 20.0;
        }
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(21.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(21.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsAboveMaxValueAndAllPastValuesArePresentAndEqual_thenReturnsFailed() {
        this.sut.setMaxPercentAbove(5.0);
        this.sut.setMaxPercentBelow(5.0);
        for (int i = 0; i < this.sensorReadouts.length; i++) {
            this.sensorReadouts[i] = 20.0;
        }
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(22.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(21.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsBelowMaxValueAndAllPastValuesArePresentAndEqualButOnlyEverySecondValueIsPresent_thenReturnsPassed() {
        this.sut.setMaxPercentAbove(5.0);
        this.sut.setMaxPercentBelow(5.0);

        for (int i = 0; i < this.sensorReadouts.length; i++) {
            if (i % 2 == 1) {
                continue; // skip
            }
            this.sensorReadouts[i] = 20.0;
        }
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.8,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(21.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsBelowMaxValueAndAllPastValuesArePresentAndAreDifferentSoAveragingIsRequired_thenReturnsPassed() {
        this.sut.setMaxPercentAbove(5.0);
        this.sut.setMaxPercentBelow(5.0);

        for (int i = 0; i < this.sensorReadouts.length; i++) {
            this.sensorReadouts[i] = (i % 2 == 1) ? 22.0 : 18.0; // the average will be 20.0
        }
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.8,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(21.0, ruleExecutionResult.getUpperBound());
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