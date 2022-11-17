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
import ai.dqo.checks.column.validity.ColumnValidityStringLengthInRangePercentCheckSpec;
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
import ai.dqo.sensors.column.validity.ColumnValidityStringLengthInRangePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnValidityStringLengthInRangePercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnValidityStringLengthInRangePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private SensorExecutionRunParameters runParameters;
    private ColumnValidityStringLengthInRangePercentCheckSpec checkSpec;
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
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_regex_sensor, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnValidityStringLengthInRangePercentSensorParametersSpec();
        this.checkSpec = new ColumnValidityStringLengthInRangePercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
        this.runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "id", this.checkSpec);
        this.sut = (ColumnValidityStringLengthInRangePercentSensorParametersSpec) runParameters.getSensorParameters();
    }

    @Test
    void getSensorDefinitionName_whenSensorDefinitionForBigQueryRetrieved_thenDefinitionFoundInDocumatiHome() {
        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper =
                SensorDefinitionWrapperObjectMother.findProviderDqoHomeSensorDefinition(this.sut.getSensorDefinitionName(), ProviderType.bigquery);
        Assertions.assertNotNull(providerSensorDefinitionWrapper.getSpec());
        Assertions.assertNotNull(providerSensorDefinitionWrapper);
    }

    @Test
    void renderSensor_whenLowerBoundIs8AndUpperBoundIs8_thenRendersCorrectSql() {
        this.sut.setMinLength(8);
        this.sut.setMaxLength(8);
        runParameters.setTimeSeries(null);
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            CASE
                                WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                                ELSE
                                    100.0 * SUM(
                                                CASE WHEN LENGTH( SAFE_CAST(analyzed_table.`id` AS STRING) ) BETWEEN 8 AND 8 THEN 1
                                                        ELSE 0
                                                    END
                            ) / COUNT(analyzed_table.`id`) END AS actual_value
                        FROM %s AS analyzed_table""",
                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);

    }

    @Test
    void renderSensor_whenLowerBoundIs3AndUpperBoundIs5_thenRendersCorrectSql() {
        this.sut.setMinLength(3);
        this.sut.setMaxLength(5);
        runParameters.setTimeSeries(null);
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            CASE
                                WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                                ELSE
                                    100.0 * SUM(
                                                CASE WHEN LENGTH( SAFE_CAST(analyzed_table.`id` AS STRING) ) BETWEEN 3 AND 5 THEN 1
                                                        ELSE 0
                                                    END
                            ) / COUNT(analyzed_table.`id`) END AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);

    }

    @Test
    void renderSensor_whenLowerBoundIs3AndUpperBoundIs5AndColumnWithIntValues_thenRendersCorrectSql() {
        this.runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "length_string_int", this.checkSpec);
        this.sut = (ColumnValidityStringLengthInRangePercentSensorParametersSpec) runParameters.getSensorParameters();
        this.sut.setMinLength(3);
        this.sut.setMaxLength(5);
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            CASE
                                WHEN COUNT(analyzed_table.`length_string_int`) = 0 THEN NULL
                                ELSE
                                    100.0 * SUM(
                                                CASE WHEN LENGTH( SAFE_CAST(analyzed_table.`length_string_int` AS STRING) ) BETWEEN 3 AND 5 THEN 1
                                                        ELSE 0
                                                    END
                            ) / COUNT(analyzed_table.`length_string_int`) END AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);

    }
}
