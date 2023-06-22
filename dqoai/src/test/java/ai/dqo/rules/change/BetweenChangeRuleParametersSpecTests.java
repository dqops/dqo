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
package ai.dqo.rules.change;

import ai.dqo.BaseTest;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.rules.HistoricDataPoint;
import ai.dqo.execution.rules.HistoricDataPointObjectMother;
import ai.dqo.execution.rules.RuleExecutionResult;
import ai.dqo.execution.rules.runners.python.PythonRuleRunnerObjectMother;
import ai.dqo.metadata.timeseries.TimePeriodGradient;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import ai.dqo.rules.RuleTimeWindowSettingsSpecObjectMother;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class BetweenChangeRuleParametersSpecTests extends BaseTest {
    private BetweenChangeRuleParametersSpec sut;
    private RuleTimeWindowSettingsSpec timeWindowSettings;
    private LocalDateTime readoutTimestamp;
    private Double[] sensorReadouts;

    private SampleTableMetadata sampleTableMetadata;
    private UserHomeContext userHomeContext;

    @BeforeEach
    void setUp() {
        this.sut = new BetweenChangeRuleParametersSpec();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.timeWindowSettings = RuleTimeWindowSettingsSpecObjectMother.getRealTimeWindowSettings(this.sut.getRuleDefinitionName());
        this.readoutTimestamp = LocalDateTime.of(2022, 02, 15, 0, 0);
        this.sensorReadouts = new Double[this.timeWindowSettings.getPredictionTimeWindow()];
    }

    @Test
    void executeRule_whenActualValueIsBetweenRangeFromPreviousReadout_thenReturnsPassed() {
        this.sut.setFrom(-10.0);
        this.sut.setTo(0.0);
        this.sensorReadouts[0] = 20.0;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(19.2,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(15.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(10.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(20.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsEqualToPreviousReadoutAndRangeIsZero_thenReturnsPassed() {
        this.sut.setFrom(0.0);
        this.sut.setTo(0.0);
        this.sensorReadouts[0] = 20.0;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(20.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(20.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsGreaterThanAllowed_thenReturnsFailed() {
        this.sut.setFrom(10.0);
        this.sut.setTo(300.0);
        this.sensorReadouts[0] = -50.0;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(251.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.isPassed());
        Assertions.assertEquals(105.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(-40.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(250.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsNull_thenReturnsPassed() {
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(null,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(null, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(null, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(null, ruleExecutionResult.getUpperBound());
    }
}
