/*
 * Copyright © 2023 DQOps (support@dqops.com)
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
import java.util.Random;

@SpringBootTest
public class AnomalyDifferencingPercentileMovingAverageRuleParametersSpecTests extends BaseTest {
    private AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec sut;
    private RuleTimeWindowSettingsSpec timeWindowSettings;
    private LocalDateTime readoutTimestamp;
    private Double[] sensorReadouts;

    private SampleTableMetadata sampleTableMetadata;
    private UserHomeContext userHomeContext;

    @BeforeEach
    void setUp() {
        this.sut = new AnomalyDifferencingPercentileMovingAverageRuleWarning1PctParametersSpec();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.timeWindowSettings = RuleTimeWindowSettingsSpecObjectMother.getRealTimeWindowSettings(this.sut.getRuleDefinitionName());
        this.readoutTimestamp = LocalDateTime.of(2022, 2, 15, 0, 0);
        this.sensorReadouts = new Double[this.timeWindowSettings.getPredictionTimeWindow()];
    }

    @Test
    void executeRule_whenActualValueIsBelowMaxValueAndPastValuesAreNormalFrom0_thenReturnsPassed() {
        this.sut.setAnomalyPercent(20.0);
        Random random = new Random(0);
        Double increment = 5.0;

        this.sensorReadouts[0] = 0.0;
        for (int i = 1; i < this.sensorReadouts.length; i++) {
            // Increment with some noise.
            this.sensorReadouts[i] = Math.max(
                    this.sensorReadouts[i - 1],
                    this.sensorReadouts[i - 1] + random.nextGaussian() * 3 + increment);
        }

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        Double actualValue = 480.0;
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(actualValue,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(478.45, ruleExecutionResult.getExpectedValue(), 0.1);
        Assertions.assertEquals(475.28, ruleExecutionResult.getLowerBound(), 0.1);
        Assertions.assertEquals(482.37, ruleExecutionResult.getUpperBound(), 0.1);
    }

    @Test
    void executeRule_whenActualValueIsBelowMaxValueAndPastValuesAreNormalFrom1_thenReturnsPassed() {
        this.sut.setAnomalyPercent(20.0);
        Random random = new Random(0);
        Double increment = 5.0;

        this.sensorReadouts[0] = 1.0;
        for (int i = 1; i < this.sensorReadouts.length; i++) {
            // Increment with some noise.
            this.sensorReadouts[i] = Math.max(
                    this.sensorReadouts[i - 1],
                    this.sensorReadouts[i - 1] + random.nextGaussian() * 3 + increment);
        }

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        Double actualValue = 480.0;
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(actualValue,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(479.45, ruleExecutionResult.getExpectedValue(), 0.1);
        Assertions.assertEquals(476.28, ruleExecutionResult.getLowerBound(), 0.1);
        Assertions.assertEquals(483.37, ruleExecutionResult.getUpperBound(), 0.1);
    }

    @Test
    void executeRule_whenActualValueIsAboveMaxValueAndPastValuesAreNormalFrom0_thenReturnsNotPassed() {
        this.sut.setAnomalyPercent(20.0);
        Random random = new Random(0);
        Double increment = 5.0;

        this.sensorReadouts[0] = 0.0;
        for (int i = 1; i < this.sensorReadouts.length; i++) {
            // Increment with some noise.
            this.sensorReadouts[i] = Math.max(
                    this.sensorReadouts[i - 1],
                    this.sensorReadouts[i - 1] + random.nextGaussian() * 3 + increment);
        }

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        Double actualValue = 485.0;
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(actualValue,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(478.45, ruleExecutionResult.getExpectedValue(), 0.1);
        Assertions.assertEquals(475.28, ruleExecutionResult.getLowerBound(), 0.1);
        Assertions.assertEquals(482.37, ruleExecutionResult.getUpperBound(), 0.1);
    }

    @Test
    void executeRule_whenActualValueIsAboveMaxValueAndPastValuesAreNormalFrom1_thenReturnsNotPassed() {
        this.sut.setAnomalyPercent(20.0);
        Random random = new Random(0);
        Double increment = 5.0;

        this.sensorReadouts[0] = 1.0;
        for (int i = 1; i < this.sensorReadouts.length; i++) {
            // Increment with some noise.
            this.sensorReadouts[i] = Math.max(
                    this.sensorReadouts[i - 1],
                    this.sensorReadouts[i - 1] + random.nextGaussian() * 3 + increment);
        }

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        Double actualValue = 485.0;
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(actualValue,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(479.45, ruleExecutionResult.getExpectedValue(), 0.1);
        Assertions.assertEquals(476.28, ruleExecutionResult.getLowerBound(), 0.1);
        Assertions.assertEquals(483.37, ruleExecutionResult.getUpperBound(), 0.1);
    }

    @Test
    void executeRule_whenActualValueIsWithinQuantileAndPastValuesAreSteadyFrom1_thenReturnsNull() {
        this.sut.setAnomalyPercent(20.0);

        Double increment = 7.0;
        this.sensorReadouts[0] = 1.0;
        for (int i = 1; i < this.sensorReadouts.length; ++i) {
            this.sensorReadouts[i] = this.sensorReadouts[i - 1] + increment;
        }

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        Double actualValue = this.sensorReadouts[this.sensorReadouts.length - 1] + increment;
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(actualValue,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertNull(ruleExecutionResult.getPassed());
    }

    @Test
    void executeRule_whenActualValueIsWithinQuantileAndPastValuesAreSteadyFrom0_thenReturnsNull() {
        this.sut.setAnomalyPercent(20.0);

        Double increment = 7.0;
        this.sensorReadouts[0] = 0.0;
        for (int i = 1; i < this.sensorReadouts.length; ++i) {
            this.sensorReadouts[i] = this.sensorReadouts[i - 1] + increment;
        }

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        Double actualValue = this.sensorReadouts[this.sensorReadouts.length - 1] + increment;
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(actualValue,
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
    void executeRule_whenActualValueIsWithinQuantileAndPastValuesAreAlwaysIncreasingFrom1ButDifferentIncreases_thenReturnsPassed() {
        this.sut.setAnomalyPercent(20.0);

        Double increment = 7.0;
        this.sensorReadouts[0] = 1.0;
        for (int i = 1; i < this.sensorReadouts.length; ++i) {
            this.sensorReadouts[i] = this.sensorReadouts[i - 1] + increment + (i % 2);
        }

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        Double actualValue = this.sensorReadouts[this.sensorReadouts.length - 1] + increment;
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(actualValue,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(677.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(676.50, ruleExecutionResult.getLowerBound(), 0.1);
        Assertions.assertEquals(677.0, ruleExecutionResult.getUpperBound(), 0.1);
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
