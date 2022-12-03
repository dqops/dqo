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
import ai.dqo.checks.column.validity.ColumnValidityRegexMatchPercentCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderServiceObjectMother;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
import ai.dqo.metadata.groupings.DataStreamLevelSpecObjectMother;
import ai.dqo.metadata.groupings.DataStreamMappingSpecObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.sensors.column.validity.BuiltInRegex;
import ai.dqo.sensors.column.validity.ColumnValidityRegexMatchPercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnValidityRegexMatchPercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnValidityRegexMatchPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private SensorExecutionRunParameters runParameters;
    private ColumnValidityRegexMatchPercentCheckSpec checkSpec;
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
        this.sut = new ColumnValidityRegexMatchPercentSensorParametersSpec();
        this.checkSpec = new ColumnValidityRegexMatchPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
        this.runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "id", this.checkSpec);
        this.sut = (ColumnValidityRegexMatchPercentSensorParametersSpec) runParameters.getSensorParameters();
    }

    @Test
    void getSensorDefinitionName_whenSensorDefinitionForBigQueryRetrieved_thenDefinitionFoundInDocumatiHome() {
        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper =
                SensorDefinitionWrapperObjectMother.findProviderDqoHomeSensorDefinition(this.sut.getSensorDefinitionName(), ProviderType.bigquery);
        Assertions.assertNotNull(providerSensorDefinitionWrapper.getSpec());
        Assertions.assertNotNull(providerSensorDefinitionWrapper);
    }


    @Test
    void renderSensor_whenSetCustomRegex_thenRendersCorrectSql() {
        this.sut.setCustomRegex("\\d{5,5}"); //We set a test regex "test_custom_regex_defined_by_user" - just to check that correct sql is rendered.
//        this.runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "id", this.checkSpec);
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        String expectedQuery = String.format("""
                SELECT CASE
                        WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(
                                    analyzed_table.`id`,
                                    r'\\d{5,5}'
                                ) IS TRUE THEN 1
                                ELSE 0
                            END
                        ) / COUNT(analyzed_table.`id`)
                    END AS actual_value
                FROM %s AS analyzed_table""",
            JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters));

        Assertions.assertEquals(expectedQuery, renderedTemplate);
    }

    @Test
    void renderSensor_whenSetEmailRegexAndNoTimeSeries_thenRendersCorrectSql() {
        this.sut.setNamedRegex(BuiltInRegex.email);
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        String expectedQuery = String.format("""
                SELECT CASE
                        WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(
                                    analyzed_table.`id`,
                                    r'^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$'
                                ) IS TRUE THEN 1
                                ELSE 0
                            END
                        ) / COUNT(analyzed_table.`id`)
                    END AS actual_value
                FROM %s AS analyzed_table""",
            JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)
        );

        Assertions.assertEquals(expectedQuery,
                renderedTemplate);
    }

    @Test
    void renderSensor_whenSetPhoneNumberRegex_thenRendersCorrectSql() {
        this.sut.setNamedRegex(BuiltInRegex.phoneNumber);
        runParameters.setTimeSeries(null);

        String expectedQuery = String.format("""
                SELECT CASE
                        WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(
                                    analyzed_table.`id`,
                                    r'^[pP]?[tT]?[nN]?[oO]?[0-9]{7,11}$'
                                ) IS TRUE THEN 1
                                ELSE 0
                            END
                        ) / COUNT(analyzed_table.`id`)
                    END AS actual_value
                FROM %s AS analyzed_table""",
            JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)
        );

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        Assertions.assertEquals(expectedQuery, renderedTemplate);
    }

    @Test
    void renderSensor_whenSetCustomRegexAndLevelOneStaticColumnAndNoTimeSeries_thenRendersCorrectSql() {
        this.sut.setCustomRegex("\\d{5,5}"); //We set a test regex "test_custom_regex_defined_by_user" - just to check that correct sql is rendered.
        runParameters.setTimeSeries(null);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createTag("FR")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother
            .renderBuiltInTemplate(runParameters);

        String expectedQuery = String.format("""
                SELECT CASE
                        WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(
                                    analyzed_table.`id`,
                                    r'\\d{5,5}'
                                ) IS TRUE THEN 1
                                ELSE 0
                            END
                        ) / COUNT(analyzed_table.`id`)
                    END AS actual_value, 'FR' AS stream_level_1
                FROM %s AS analyzed_table
                GROUP BY stream_level_1
                ORDER BY stream_level_1""",
            JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters));

        Assertions.assertEquals(expectedQuery, renderedTemplate);
    }

    @Test
    void renderSensor_whenSetEmailRegexAndLevelWithoutTimeSeries_thenRendersCorrectSql() {
        this.sut.setNamedRegex(BuiltInRegex.email); //We set a test regex "test_custom_regex_defined_by_user" - just to check that correct sql is rendered.
        runParameters.setTimeSeries(null);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createTag("FR")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        String expectedQuery = String.format("""
                SELECT CASE
                        WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(
                                    analyzed_table.`id`,
                                    r'^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$'
                                ) IS TRUE THEN 1
                                ELSE 0
                            END
                        ) / COUNT(analyzed_table.`id`)
                    END AS actual_value, 'FR' AS stream_level_1
                FROM %s AS analyzed_table
                GROUP BY stream_level_1
                ORDER BY stream_level_1""",
            JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters));

        Assertions.assertEquals(expectedQuery, renderedTemplate);
    }

    @Test
    void renderSensor_whenSetPhoneNumberRegexAndLevelOneStaticColumnWithoutTimeSeries_thenRendersCorrectSql() {
        this.sut.setNamedRegex(BuiltInRegex.phoneNumber); //We set a test regex "test_custom_regex_defined_by_user" - just to check that correct sql is rendered.
        runParameters.setTimeSeries(null);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createTag("FR")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        String expectedQuery = String.format("""
                SELECT CASE
                        WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(
                                    analyzed_table.`id`,
                                    r'^[pP]?[tT]?[nN]?[oO]?[0-9]{7,11}$'
                                ) IS TRUE THEN 1
                                ELSE 0
                            END
                        ) / COUNT(analyzed_table.`id`)
                    END AS actual_value, 'FR' AS stream_level_1
                FROM %s AS analyzed_table
                GROUP BY stream_level_1
                ORDER BY stream_level_1""",
            JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters));

        Assertions.assertEquals(expectedQuery, renderedTemplate);
    }

    @Test
    void renderSensor_whenLevel1StaticStringAndNoTimeSeriesAndSetCustomRegex_thenRendersCorrectSql() {
        this.sut.setCustomRegex("\\d{5,5}");

        runParameters.setTimeSeries(null);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(DataStreamLevelSpecObjectMother.createTag("DE")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        String expectedQuery = String.format("""
                SELECT CASE
                        WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(
                                    analyzed_table.`id`,
                                    r'\\d{5,5}'
                                ) IS TRUE THEN 1
                                ELSE 0
                            END
                        ) / COUNT(analyzed_table.`id`)
                    END AS actual_value, 'DE' AS stream_level_1
                FROM %s AS analyzed_table
                GROUP BY stream_level_1
                ORDER BY stream_level_1""",
            JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters));

        Assertions.assertEquals(expectedQuery, renderedTemplate);
    }

    @Test
    void renderSensor_whenConnectionLevel1StaticStringAndNoTimeSeriesAndSetEmailRegex_thenRendersCorrectSql() {
        this.sut.setNamedRegex(BuiltInRegex.email);

        runParameters.setTimeSeries(null);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(DataStreamLevelSpecObjectMother.createTag("DE")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        String expectedQuery = String.format("""
                SELECT CASE
                        WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(
                                    analyzed_table.`id`,
                                    r'^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$'
                                ) IS TRUE THEN 1
                                ELSE 0
                            END
                        ) / COUNT(analyzed_table.`id`)
                    END AS actual_value, 'DE' AS stream_level_1
                FROM %s AS analyzed_table
                GROUP BY stream_level_1
                ORDER BY stream_level_1""",
            JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters));

        Assertions.assertEquals(expectedQuery, renderedTemplate);
    }

    @Test
    void renderSensor_whenLevel1StaticStringAndNoTimeSeriesAndSetPhoneNumberRegex_thenRendersCorrectSql() {
        this.sut.setNamedRegex(BuiltInRegex.phoneNumber);

        runParameters.setTimeSeries(null);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(DataStreamLevelSpecObjectMother.createTag("DE")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        String expectedQuery = String.format("""
                SELECT CASE
                        WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(
                                    analyzed_table.`id`,
                                    r'^[pP]?[tT]?[nN]?[oO]?[0-9]{7,11}$'
                                ) IS TRUE THEN 1
                                ELSE 0
                            END
                        ) / COUNT(analyzed_table.`id`)
                    END AS actual_value, 'DE' AS stream_level_1
                FROM %s AS analyzed_table
                GROUP BY stream_level_1
                ORDER BY stream_level_1""",
            JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters));

        Assertions.assertEquals(expectedQuery, renderedTemplate);
    }

    @Test
    void renderSensor_whenNoTimeSeriesAndSetCustomRegex_thenRendersCorrectSql() {
        this.sut.setCustomRegex("\\d{5,5}");
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        String expectedQuery = String.format("""
                SELECT CASE
                        WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(
                                    analyzed_table.`id`,
                                    r'\\d{5,5}'
                                ) IS TRUE THEN 1
                                ELSE 0
                            END
                        ) / COUNT(analyzed_table.`id`)
                    END AS actual_value
                FROM %s AS analyzed_table""",
            JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters));

        Assertions.assertEquals(expectedQuery, renderedTemplate);
    }

    @Test
    void renderSensor_whenNoTimeSeriesAndSetPhoneNumberRegex_thenRendersCorrectSql() {
        this.sut.setNamedRegex(BuiltInRegex.phoneNumber);
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        String expectedQuery = String.format("""
                SELECT CASE
                        WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(
                                    analyzed_table.`id`,
                                    r'^[pP]?[tT]?[nN]?[oO]?[0-9]{7,11}$'
                                ) IS TRUE THEN 1
                                ELSE 0
                            END
                        ) / COUNT(analyzed_table.`id`)
                    END AS actual_value
                FROM %s AS analyzed_table""",
            JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters));

        Assertions.assertEquals(expectedQuery, renderedTemplate);
    }

    @Test
    void renderSensor_whenNoTimeSeriesAndSetEmailRegex_thenRendersCorrectSql() {
        this.sut.setNamedRegex(BuiltInRegex.email);
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        String expectedQuery = String.format("""
                SELECT CASE
                        WHEN COUNT(analyzed_table.`id`) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN REGEXP_CONTAINS(
                                    analyzed_table.`id`,
                                    r'^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$'
                                ) IS TRUE THEN 1
                                ELSE 0
                            END
                        ) / COUNT(analyzed_table.`id`)
                    END AS actual_value
                FROM %s AS analyzed_table""",
            JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters));

        Assertions.assertEquals(expectedQuery, renderedTemplate);
    }

}