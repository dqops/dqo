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
package ai.dqo.rules.comparison;

import ai.dqo.BaseTest;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.rules.HistoricDataPoint;
import ai.dqo.execution.rules.HistoricDataPointObjectMother;
import ai.dqo.execution.rules.RuleExecutionResult;
import ai.dqo.execution.rules.runners.python.PythonRuleRunnerObjectMother;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContext;
import ai.dqo.metadata.storage.localfiles.dqohome.DqoHomeContextObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.rules.RuleTimeWindowSettingsSpec;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class MaxFailuresRuleParametersSpecTests extends BaseTest {
    private MaxFailuresRule1ParametersSpec sut;
    private RuleTimeWindowSettingsSpec timeWindowSettings;
    private LocalDateTime readoutTimestamp;
    private Double[] sensorReadouts;
    private SampleTableMetadata sampleTableMetadata;
    private UserHomeContext userHomeContext;

    @BeforeEach
    void setUp() {
        this.sut = new MaxFailuresRule1ParametersSpec();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.timeWindowSettings = new RuleTimeWindowSettingsSpec();
        this.readoutTimestamp = LocalDateTime.of(2022, 02, 15, 0, 0);
        this.sensorReadouts = new Double[this.timeWindowSettings.getPredictionTimeWindow()];
    }

    @Test
    void executeRule_whenCountOfFailuresLessThenMaxFailures_thenReturnsPassed() {
        this.sut.setMaxFailures(5L);

        Double[] previousReadouts = {0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        this.sensorReadouts = previousReadouts;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(this.timeWindowSettings, TimeSeriesGradient.DAY, this.readoutTimestamp, this.sensorReadouts);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(0.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(5.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(5.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(null, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenCountOfFailuresGreaterThenMaxFailures_thenReturnsFailed() {
        this.sut.setMaxFailures(2L);

        Double[] previousReadouts = {0.0, 1.0, 1.0, 1.0, null, 0.0, null, 1.0, 0.0, 1.0, 1.0, 1.0, null, 1.0};
        this.sensorReadouts = previousReadouts;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(this.timeWindowSettings, TimeSeriesGradient.DAY, this.readoutTimestamp, this.sensorReadouts);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(0.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(!ruleExecutionResult.isPassed());
        Assertions.assertEquals(2.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(2.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(null, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenCountOfFailuresGreaterThenMaxFailuresAndSetTimeWindow_thenReturnsFailed() {
        this.sut.setMaxFailures(2L);
        this.timeWindowSettings.setPredictionTimeWindow(10);

        Double[] previousReadouts = {null, 0.0, null, 1.0, 0.0, 1.0, 1.0, 1.0, null, 1.0};
        this.sensorReadouts = previousReadouts;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(this.timeWindowSettings, TimeSeriesGradient.DAY, this.readoutTimestamp, this.sensorReadouts);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(0.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(!ruleExecutionResult.isPassed());
        Assertions.assertEquals(2.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(2.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(null, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenCountOfFailuresGreaterThenMaxFailuresAndSetTimeWindow_thenReturnsPassed() {
        this.sut.setMaxFailures(5L);
        this.timeWindowSettings.setPredictionTimeWindow(10);

        Double[] previousReadouts = {null, 0.0, null, 1.0, 0.0, 1.0, 1.0, 1.0, null, 1.0};
        this.sensorReadouts = previousReadouts;

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(this.timeWindowSettings, TimeSeriesGradient.DAY, this.readoutTimestamp, this.sensorReadouts);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(0.0,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(5.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(5.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(null, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsNull_thenReturnsPassed() {
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(null, this.sut);
        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(null, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(null, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(null, ruleExecutionResult.getUpperBound());
    }
}
