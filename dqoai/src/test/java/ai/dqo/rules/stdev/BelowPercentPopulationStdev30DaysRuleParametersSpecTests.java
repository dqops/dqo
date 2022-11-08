package ai.dqo.rules.stdev;

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
public class BelowPercentPopulationStdev30DaysRuleParametersSpecTests extends BaseTest {
    private BelowPercentPopulationStdev30DaysRuleParametersSpec sut;
    private RuleTimeWindowSettingsSpec timeWindowSettings;
    private LocalDateTime readingTimestamp;
    private Double[] sensorReadings;

    private SampleTableMetadata sampleTableMetadata;
    private UserHomeContext userHomeContext;

    @Override
    @BeforeEach
    protected void  setUp() throws Throwable {
        super.setUp();
        this.sut = new BelowPercentPopulationStdev30DaysRuleParametersSpec();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);

        this.timeWindowSettings = new RuleTimeWindowSettingsSpec();
        this.readingTimestamp = LocalDateTime.of(2022, 02, 15, 0, 0);
        this.sensorReadings = new Double[this.timeWindowSettings.getPredictionTimeWindow()];
    }

    @Test
    void executeRule_whenActualValueIsBelowMaxValueAndAllPastValuesArePresentAndEqual_thenReturnsPassed() {
        this.sut.setPercentPopulationBelow(1.0);

        for (int i = 0; i < this.sensorReadings.length; i++) {
            if(i % 2 == 0) {
                this.sensorReadings[i] = 15.0;
            } else {
                this.sensorReadings[i] = 25.0;
            }
        }
        HistoricDataPoint[] historicDataPoints = HistoricDataPointObjectMother.fillHistoricReadings(this.timeWindowSettings, TimeSeriesGradient.DAY, this.readingTimestamp, this.sensorReadings);

        RuleExecutionResult ruleExecutionResult = PythonRuleRunnerObjectMother.executeBuiltInRule(20.0,
                this.sut, this.readingTimestamp, historicDataPoints, this.timeWindowSettings);

        Assertions.assertTrue(ruleExecutionResult.isPassed());
        Assertions.assertEquals(20.0, ruleExecutionResult.getExpectedValue());
        Assertions.assertEquals(14.811254783372291, ruleExecutionResult.getLowerBound());
    }
}