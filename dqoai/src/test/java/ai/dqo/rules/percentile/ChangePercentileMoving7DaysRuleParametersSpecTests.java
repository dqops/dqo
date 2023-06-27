/*
 * Copyright © 2023 DQO.ai (support@dqo.ai)
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
package ai.dqo.rules.percentile;

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
import java.util.Random;

@SpringBootTest
public class ChangePercentileMoving7DaysRuleParametersSpecTests extends BaseTest {
    private ChangePercentileMoving7DaysRuleParametersSpec sut;
    private RuleTimeWindowSettingsSpec timeWindowSettings;
    private LocalDateTime readoutTimestamp;
    private Double[] sensorReadouts;

    private SampleTableMetadata sampleTableMetadata;
    private UserHomeContext userHomeContext;

    @BeforeEach
    void setUp() {
        this.sut = new ChangePercentileMoving7DaysRuleParametersSpec();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.timeWindowSettings = RuleTimeWindowSettingsSpecObjectMother.getRealTimeWindowSettings(this.sut.getRuleDefinitionName());
        this.readoutTimestamp = LocalDateTime.of(2022, 2, 15, 0, 0);
        this.sensorReadouts = new Double[this.timeWindowSettings.getPredictionTimeWindow()];
    }

    @Test
    void executeRule_whenActualValueIsBelowMaxValueAndPastValuesAreNormal_thenReturnsPassed() {
        this.sut.setPercentileBelow(5.0);
        this.sut.setPercentileAbove(15.0);

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
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts);

        Double actualValue = 40.0;
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(actualValue,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(42.22, ruleExecutionResult.getExpectedValue(), 0.1);
        Assertions.assertEquals(35.45, ruleExecutionResult.getLowerBound(), 0.1);
        Assertions.assertEquals(46.49, ruleExecutionResult.getUpperBound(), 0.1);
    }

    @Test
    void executeRule_whenActualValueIsWithinQuantileAndPastValuesAreSteady_thenReturnsPassed() {
        this.sut.setPercentileBelow(10.0);
        this.sut.setPercentileAbove(20.0);

        Double increment = 7.0;
        this.sensorReadouts[0] = 0.0;
        for (int i = 1; i < this.sensorReadouts.length; ++i) {
            this.sensorReadouts[i] = this.sensorReadouts[i - 1] + increment;
        }

        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts);

        Double actualValue = this.sensorReadouts[0] + this.sensorReadouts.length * increment;
        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(actualValue,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(actualValue, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(actualValue, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(actualValue, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsNull_thenReturnsPassed() {
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadouts(
                this.timeWindowSettings, TimePeriodGradient.day, this.readoutTimestamp, this.sensorReadouts);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(null,
                this.sut, this.readoutTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(null, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(null, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(null, ruleExecutionResult.getUpperBound());
    }
}
