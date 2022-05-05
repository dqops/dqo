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
package ai.dqo.sensors.bigquery.column.completeness;

import ai.dqo.BaseTest;
import ai.dqo.checks.column.completeness.ColumnCompletenessMissingDatesCountCheckSpec;
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
import ai.dqo.sensors.column.completeness.ColumnCompletenessMissingDatesCountSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.MessageFormat;

@SpringBootTest
public class ColumnCompletenessMissingDatesCountSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnCompletenessMissingDatesCountSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private SensorExecutionRunParameters runParameters;
    private ColumnCompletenessMissingDatesCountCheckSpec checkSpec;
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
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnCompletenessMissingDatesCountSensorParametersSpec();
        this.checkSpec = new ColumnCompletenessMissingDatesCountCheckSpec();
        this.checkSpec.setParameters(this.sut);
        this.runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata,"date",this.checkSpec);

    }

    @Test
    void getSensorDefinitionName_whenSensorDefinitionForBigQueryRetrieved_thenDefinitionFoundInDocumatiHome() {
        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper =
                SensorDefinitionWrapperObjectMother.findProviderDqoHomeSensorDefinition(this.sut.getSensorDefinitionName(), ProviderType.bigquery);
        Assertions.assertNotNull(providerSensorDefinitionWrapper.getSpec());
        Assertions.assertNotNull(providerSensorDefinitionWrapper);
    }

    @Test
    void renderSensor_whenTimeSeriesDateArrayGradientMonth_thenRendersCorrectSql() {
        runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("date_array", TimeSeriesGradient.MONTH));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(MessageFormat.format("""
                        WITH generatedDays AS (
                            SELECT date_array
                            FROM UNNEST(
                                    GENERATE_DATE_ARRAY(
                                        (
                                            SELECT MIN(CAST(analyzed_table.`date` AS DATE))
                                            FROM `{0}`.`{1}`.`{2}` AS analyzed_table
                                        ),
                                        (
                                            SELECT MAX(CAST(analyzed_table.`date` AS DATE))
                                            FROM `{0}`.`{1}`.`{2}` AS analyzed_table
                                        )
                                    )
                                ) AS date_array
                        ),
                        rawData AS (
                            SELECT CAST(analyzed_table.`date` AS DATE) AS dates,
                                COUNT(*) AS cnt
                            FROM `{0}`.`{1}`.`{2}` AS analyzed_table
                            GROUP BY dates
                            ORDER BY dates
                        ),
                        missingDays AS (
                            SELECT date_array,
                                DATE_TRUNC(CAST(generatedDays.`date_array` AS date), month) AS time_gradient,
                                SUM(CASE WHEN cnt = 1 THEN 0 ELSE 1 END) AS missingdates
                            FROM generatedDays
                                LEFT JOIN rawData r ON r.dates = generatedDays.date_array
                            GROUP BY generatedDays.date_array, cnt
                            ORDER BY generatedDays.date_array)
                        SELECT sum(missingdates) AS actual_value
                        FROM missingDays""",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }
}