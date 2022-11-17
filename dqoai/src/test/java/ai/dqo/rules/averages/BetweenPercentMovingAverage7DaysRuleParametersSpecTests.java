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
package ai.dqo.rules.averages;

import ai.dqo.BaseTest;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.rules.HistoricDataPoint;
import ai.dqo.execution.rules.HistoricDataPointObjectMother;
import ai.dqo.execution.rules.RuleExecutionResult;
import ai.dqo.execution.rules.runners.python.PythonRuleRunnerObjectMother;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
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
public class BetweenPercentMovingAverage7DaysRuleParametersSpecTests extends BaseTest {
    private BetweenPercentMovingAverage7DaysRuleParametersSpec sut;
    private RuleTimeWindowSettingsSpec timeWindowSettings;
    private LocalDateTime readingTimestamp;
    private Double[] sensorReadings;

    private SampleTableMetadata sampleTableMetadata;
    private UserHomeContext userHomeContext;


    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
        this.sut = new BetweenPercentMovingAverage7DaysRuleParametersSpec();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);

        this.timeWindowSettings = new RuleTimeWindowSettingsSpec();
        this.readingTimestamp = LocalDateTime.of(2022, 02, 15, 0, 0);
        this.sensorReadings = new Double[this.timeWindowSettings.getPredictionTimeWindow()];

    }

    @Test
    void executeRule_whenActualValueIsBelowMaxValueAndAllPastValuesArePresentAndEqual_thenReturnsPassed() {
        this.sut.setMaxPercentAbove(5.0);
        this.sut.setMaxPercentBelow(5.0);

        for (int i = 0; i < this.sensorReadings.length; i++) {
            this.sensorReadings[i] = 20.0;
        }
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadings(this.timeWindowSettings, TimeSeriesGradient.DAY, this.readingTimestamp, this.sensorReadings);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.8,
                this.sut, this.readingTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(21.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsEqualMaxValueAndAllPastValuesArePresentAndEqual_thenReturnsPassed() {
        this.sut.setMaxPercentAbove(5.0);
        this.sut.setMaxPercentBelow(5.0);
        for (int i = 0; i < this.sensorReadings.length; i++) {
            this.sensorReadings[i] = 20.0;
        }
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadings(this.timeWindowSettings, TimeSeriesGradient.DAY, this.readingTimestamp, this.sensorReadings);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(21.0,
                this.sut, this.readingTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(21.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsAboveMaxValueAndAllPastValuesArePresentAndEqual_thenReturnsFailed() {
        this.sut.setMaxPercentAbove(5.0);
        this.sut.setMaxPercentBelow(5.0);
        for (int i = 0; i < this.sensorReadings.length; i++) {
            this.sensorReadings[i] = 20.0;
        }
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadings(this.timeWindowSettings, TimeSeriesGradient.DAY, this.readingTimestamp, this.sensorReadings);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(22.0,
                this.sut, this.readingTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertFalse(ruleExecutionResult.isPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(21.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsBelowMaxValueAndAllPastValuesArePresentAndEqualButOnlyEverySecondValueIsPresent_thenReturnsPassed() {
        this.sut.setMaxPercentAbove(5.0);
        this.sut.setMaxPercentBelow(5.0);

        for (int i = 0; i < this.sensorReadings.length; i++) {
            if (i % 2 == 1) {
                continue; // skip
            }
            this.sensorReadings[i] = 20.0;
        }
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadings(this.timeWindowSettings, TimeSeriesGradient.DAY, this.readingTimestamp, this.sensorReadings);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.8,
                this.sut, this.readingTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(21.0, ruleExecutionResult.getUpperBound());
    }

    @Test
    void executeRule_whenActualValueIsBelowMaxValueAndAllPastValuesArePresentAndAreDifferentSoAveragingIsRequired_thenReturnsPassed() {
        this.sut.setMaxPercentAbove(5.0);
        this.sut.setMaxPercentBelow(5.0);

        for (int i = 0; i < this.sensorReadings.length; i++) {
            this.sensorReadings[i] = (i % 2 == 1) ? 22.0 : 18.0; // the average will be 20.0
        }
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadings(this.timeWindowSettings, TimeSeriesGradient.DAY, this.readingTimestamp, this.sensorReadings);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.8,
                this.sut, this.readingTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(19.0, ruleExecutionResult.getLowerBound());
        Assertions.assertEquals(21.0, ruleExecutionResult.getUpperBound());
    }
}
