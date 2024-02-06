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
public class BetweenPercentChange1DayRuleParametersSpecTests extends BaseTest {
    private BetweenPercentChange1DayRuleParametersSpec sut;
    private RuleTimeWindowSettingsSpec timeWindowSettings;
    private LocalDateTime readoutTimestamp;
    private Double[] sensorReadouts;

    private SampleTableMetadata sampleTableMetadata;
    private UserHomeContext userHomeContext;

    @BeforeEach
    void setUp() {
        this.sut = new BetweenPercentChange1DayRuleParametersSpec();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.timeWindowSettings = RuleTimeWindowSettingsSpecObjectMother.getRealTimeWindowSettings(this.sut.getRuleDefinitionName());
        this.readoutTimestamp = LocalDateTime.of(2022, 02, 15, 0, 0);
        this.sensorReadouts = new Double[this.timeWindowSettings.getPredictionTimeWindow()];
    }

    @Test
    void executeRule_whenActualValueIsBetweenRangeFromPreviousReadout_thenReturnsPassed() {
        this.sut.setFromPercent(-10.0);
        this.sut.setToPercent(0.0);
        this.sut.setExactDay(false);

        int readoutsCount = this.timeWindowSettings.getPredictionTimeWindow();
        this.sensorReadouts[readoutsCount - 7] = 20.0;
        this.sensorReadouts[readoutsCount - 6] = 19.2;
        this.sensorReadouts[readoutsCount - 5] = 18.2;
        this.sensorReadouts[readoutsCount - 4] = 17.6;
        this.sensorReadouts[readoutsCount - 2] = 16.0;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(14.4,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(15.2, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(14.4, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(16.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsEqualToPreviousReadoutAndRangeIsZero_thenReturnsPassed() {
        this.sut.setFromPercent(0.0);
        this.sut.setToPercent(0.0);
        this.sut.setExactDay(true);

        int readoutsCount = this.timeWindowSettings.getPredictionTimeWindow();
        this.sensorReadouts[readoutsCount - 7] = 20.0;
        this.sensorReadouts[readoutsCount - 6] = 19.2;
        this.sensorReadouts[readoutsCount - 5] = 18.2;
        this.sensorReadouts[readoutsCount - 4] = 17.6;
        this.sensorReadouts[readoutsCount - 1] = 16.0;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(16.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.getPassed());
        Assertions.assertEquals(16.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(16.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(16.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsGreaterThanAllowed_thenReturnsFailed() {
        this.sut.setFromPercent(10.0);
        this.sut.setToPercent(300.0);
        this.sut.setExactDay(true);

        int readoutsCount = this.timeWindowSettings.getPredictionTimeWindow();
        this.sensorReadouts[readoutsCount - 7] = 20.0;
        this.sensorReadouts[readoutsCount - 6] = 30.2;
        this.sensorReadouts[readoutsCount - 5] = 50.2;
        this.sensorReadouts[readoutsCount - 4] = 80.6;
        this.sensorReadouts[readoutsCount - 1] = 200.0;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(800.1,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.getPassed());
        Assertions.assertEquals(510.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(220.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(800.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenExactReadoutIsNull_thenReturnsPassed() {
        this.sut.setFromPercent(10.0);
        this.sut.setToPercent(300.0);
        this.sut.setExactDay(true);

        int readoutsCount = this.timeWindowSettings.getPredictionTimeWindow();
        this.sensorReadouts[readoutsCount - 7] = 20.0;
        this.sensorReadouts[readoutsCount - 6] = 30.2;
        this.sensorReadouts[readoutsCount - 5] = 50.2;
        this.sensorReadouts[readoutsCount - 4] = 80.6;
        this.sensorReadouts[readoutsCount - 1] = null;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts, null);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(1251.0,
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
