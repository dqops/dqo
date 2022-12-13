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
package ai.dqo.sensors.bigquery.column.validity;

import ai.dqo.BaseTest;
import ai.dqo.checks.column.validity.ColumnValidityValueInRangeDatePercentCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderServiceObjectMother;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.sensors.column.validity.ColumnValidityValueInRangeDatePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class ColumnValidityValueInRangeDatePercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnValidityValueInRangeDatePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private SensorExecutionRunParameters runParameters;
    private ColumnValidityValueInRangeDatePercentCheckSpec checkSpec;
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
		this.sut = new ColumnValidityValueInRangeDatePercentSensorParametersSpec();
		this.checkSpec = new ColumnValidityValueInRangeDatePercentCheckSpec();
		this.checkSpec.setParameters(this.sut);
		this.runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "date", this.checkSpec);
        this.sut = (ColumnValidityValueInRangeDatePercentSensorParametersSpec) runParameters.getSensorParameters();
    }

    @Test
    void getSensorDefinitionName_whenSensorDefinitionForBigQueryRetrieved_thenDefinitionFoundInDocumatiHome() {
        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper =
                SensorDefinitionWrapperObjectMother.findProviderDqoHomeSensorDefinition(this.sut.getSensorDefinitionName(), ProviderType.bigquery);
        Assertions.assertNotNull(providerSensorDefinitionWrapper.getSpec());
        Assertions.assertNotNull(providerSensorDefinitionWrapper);
    }

    @Test
    void renderSensor_whenNoTimeSeriesAndDefaultBonds_thenRendersCorrectSql() {

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,2);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);

		runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN analyzed_table.`date` >= '2022-01-01' AND analyzed_table.`date` <= '2022-01-02' THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }
    @Test
    void renderSensor_whenLowerBoundTrueAndUpperBoundFalse_thenRendersCorrectSql() {

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,2);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);

        this.sut.setIncludeMinValue(true);
        this.sut.setIncludeMaxValue(false);

        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN analyzed_table.`date` >= '2022-01-01' AND analyzed_table.`date` < '2022-01-02' THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    void renderSensor_whenUpperAndLowerBoundFalse_thenRendersCorrectSql() {

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,2);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);

        this.sut.setIncludeMinValue(false);
        this.sut.setIncludeMaxValue(false);

        runParameters.setTimeSeries(null);
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN analyzed_table.`date` > '2022-01-01' AND analyzed_table.`date` < '2022-01-02' THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    void renderSensor_whenLowerBoundFalseAndUpperBoundTrue_thenRendersCorrectSql() {

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,2);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);

        this.sut.setIncludeMinValue(false);
        this.sut.setIncludeMaxValue(true);

        runParameters.setTimeSeries(null);
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN analyzed_table.`date` > '2022-01-01' AND analyzed_table.`date` <= '2022-01-02' THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    void renderSensor_whenLowerAndUpperBoundTrue_thenRendersCorrectSql() {

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,2);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);

        this.sut.setIncludeMinValue(true);
        this.sut.setIncludeMaxValue(true);

        runParameters.setTimeSeries(null);
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN analyzed_table.`date` >= '2022-01-01' AND analyzed_table.`date` <= '2022-01-02' THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    void renderSensor_whenLowerBoundTrueAndUpperBoundDefault_thenRendersCorrectSql() {

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,2);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);

        this.sut.setIncludeMinValue(true);

        runParameters.setTimeSeries(null);
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN analyzed_table.`date` >= '2022-01-01' AND analyzed_table.`date` <= '2022-01-02' THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    void renderSensor_whenLowerBoundFalseAndUpperBoundDefault_thenRendersCorrectSql() {

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,2);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);

        this.sut.setIncludeMinValue(false);

        runParameters.setTimeSeries(null);
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN analyzed_table.`date` > '2022-01-01' AND analyzed_table.`date` <= '2022-01-02' THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    void renderSensor_whenLowerBoundDefaultAndUpperBoundTrue_thenRendersCorrectSql() {

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,2);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);

        this.sut.setIncludeMaxValue(true);

        runParameters.setTimeSeries(null);
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN analyzed_table.`date` >= '2022-01-01' AND analyzed_table.`date` <= '2022-01-02' THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    void renderSensor_whenLowerBoundDefaultAndUpperBoundFalse_thenRendersCorrectSql() {

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,2);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);

        this.sut.setIncludeMaxValue(false);

        runParameters.setTimeSeries(null);
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN analyzed_table.`date` >= '2022-01-01' AND analyzed_table.`date` < '2022-01-02' THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    void renderSensor_whenLowerBoundDefaultAndLowerBoundFalse_thenRendersCorrectSql() {

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,2);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);

        this.sut.setIncludeMinValue(false);

        runParameters.setTimeSeries(null);
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN analyzed_table.`date` > '2022-01-01' AND analyzed_table.`date` <= '2022-01-02' THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }
}
