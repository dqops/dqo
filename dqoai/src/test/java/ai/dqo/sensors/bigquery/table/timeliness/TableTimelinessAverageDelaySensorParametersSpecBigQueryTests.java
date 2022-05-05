package ai.dqo.sensors.bigquery.table.timeliness;

import ai.dqo.BaseTest;
import ai.dqo.checks.table.timeliness.TableTimelinessAverageDelayCheckSpec;
import ai.dqo.checks.table.timeliness.TableTimelinessColumnDatetimeDifferencePercentCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderServiceObjectMother;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpecObjectMother;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.sensors.table.timeliness.BuiltInTimeScale;
import ai.dqo.sensors.table.timeliness.TableTimelinessAverageDelaySensorParametersSpec;
import ai.dqo.sensors.table.timeliness.TableTimelinessColumnDatetimeDifferencePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableTimelinessAverageDelaySensorParametersSpecBigQueryTests extends BaseTest {
    private TableTimelinessAverageDelaySensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private SensorExecutionRunParameters runParameters;
    private TableTimelinessAverageDelayCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

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
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_average_delay, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new TableTimelinessAverageDelaySensorParametersSpec();
        this.checkSpec = new TableTimelinessAverageDelayCheckSpec();
        this.checkSpec.setParameters(this.sut);
        this.runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "id", this.checkSpec);
        this.sut = (TableTimelinessAverageDelaySensorParametersSpec) runParameters.getSensorParameters();

    }

    @Test
    void getSensorDefinitionName_whenSensorDefinitionForBigQueryRetrieved_thenDefinitionFoundInDqoHome() {
        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper =
                SensorDefinitionWrapperObjectMother.findProviderDqoHomeSensorDefinition(this.sut.getSensorDefinitionName(), ProviderType.bigquery);
        Assertions.assertNotNull(providerSensorDefinitionWrapper.getSpec());
        Assertions.assertNotNull(providerSensorDefinitionWrapper);
    }

    @Test
    void renderSensor_whenNoTimeSeries_thenRendersCorrectSql() {

        this.sut.setColumn1("date1");
        this.sut.setColumn2("date2");
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        Assertions.assertEquals(String.format("""
                        SELECT
                            AVG(ABS(TIMESTAMP_DIFF(SAFE_CAST(analyzed_table.date1 AS TIMESTAMP), SAFE_CAST(analyzed_table.date2 AS TIMESTAMP), DAY))) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }


    @Test
    void renderSensor_whenNoTimeSeries_thenRendersCorrectSql2() {

        this.sut.setColumn1("date3");
        this.sut.setColumn2("date4");
        this.sut.setTimeScale(BuiltInTimeScale.HOUR);
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        Assertions.assertEquals(String.format("""
                        SELECT
                            AVG(ABS(TIMESTAMP_DIFF(SAFE_CAST(analyzed_table.date3 AS TIMESTAMP), SAFE_CAST(analyzed_table.date4 AS TIMESTAMP), HOUR))) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    void renderSensor_whenSetTimeSeries_thenRendersCorrectSql() {

        this.sut.setColumn1("date3");
        this.sut.setColumn2("date4");
        this.sut.setTimeScale(BuiltInTimeScale.HOUR);
        runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("date4", TimeSeriesGradient.DAY));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        Assertions.assertEquals(String.format("""
                                SELECT
                                    AVG(ABS(TIMESTAMP_DIFF(SAFE_CAST(analyzed_table.date3 AS TIMESTAMP), SAFE_CAST(analyzed_table.date4 AS TIMESTAMP), HOUR))) AS actual_value, CAST(analyzed_table.`date4` AS date) AS time_period
                                FROM %s AS analyzed_table
                                GROUP BY time_period
                                ORDER BY time_period""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

}