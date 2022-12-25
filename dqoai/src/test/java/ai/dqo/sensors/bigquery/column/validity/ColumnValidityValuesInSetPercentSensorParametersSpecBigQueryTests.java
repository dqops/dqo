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
import ai.dqo.checks.column.validity.ColumnValidityValuesInSetPercentCheckSpec;
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
import ai.dqo.sensors.column.validity.BuiltInListFormats;
import ai.dqo.sensors.column.validity.ColumnValidityValuesInSetPercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;


@SpringBootTest
public class ColumnValidityValuesInSetPercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnValidityValuesInSetPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private SensorExecutionRunParameters runParameters;
    private ColumnValidityValuesInSetPercentCheckSpec checkSpec;
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
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnValidityValuesInSetPercentSensorParametersSpec();
        this.checkSpec = new ColumnValidityValuesInSetPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
        this.runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "length_string", this.checkSpec);
        this.sut = (ColumnValidityValuesInSetPercentSensorParametersSpec) runParameters.getSensorParameters();
    }

    @Test
    void getSensorDefinitionName_whenSensorDefinitionForBigQueryRetrieved_thenDefinitionFoundInDocumatiHome() {
        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper =
                SensorDefinitionWrapperObjectMother.findProviderDqoHomeSensorDefinition(this.sut.getSensorDefinitionName(), ProviderType.bigquery);
        Assertions.assertNotNull(providerSensorDefinitionWrapper.getSpec());
        Assertions.assertNotNull(providerSensorDefinitionWrapper);
    }

    // TODO: move the test to another sensor that works on numeric values

//    @Test
//    void renderSensor_whenNoTimeSeriesAndCheckForEmptyDoubleList_thenRendersCorrectSql() {
//        ArrayList<Double> valuesList = new ArrayList<>();
//        this.sut.setValuesType(BuiltInListFormats.NUMERIC);
//        this.sut.setValuesList(valuesList);
//        runParameters.setTimeSeries(null);
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//        Assertions.assertEquals(String.format("""
//                                SELECT
//                                    CASE
//                                        WHEN COUNT(*) = 0 THEN NULL
//                                        ELSE
//                                            100.0 * SUM(
//                                                CASE
//                                                    WHEN (CAST(analyzed_table.`length_string` AS NUMERIC) IN (NULL)) IS TRUE THEN 1
//                                                    ELSE 0
//                                                END
//                                            )/COUNT(*)
//                                    END AS actual_value
//                                FROM %s AS analyzed_table""",
//                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }

    // TODO: move the test to another sensor that works on numeric values

//    @Test
//    void renderSensor_whenNoTimeSeriesAndCheckForEmptyIntegerList_thenRendersCorrectSql() {
//        ArrayList<Integer> valuesList = new ArrayList<>();
//        this.sut.setValuesType(BuiltInListFormats.NUMERIC);
//        this.sut.setValuesList(valuesList);
//        runParameters.setTimeSeries(null);
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//        Assertions.assertEquals(String.format("""
//                                SELECT
//                                    CASE
//                                        WHEN COUNT(*) = 0 THEN NULL
//                                        ELSE
//                                            100.0 * SUM(
//                                                CASE
//                                                    WHEN (CAST(analyzed_table.`length_string` AS NUMERIC) IN (NULL)) IS TRUE THEN 1
//                                                    ELSE 0
//                                                END
//                                            )/COUNT(*)
//                                    END AS actual_value
//                                FROM %s AS analyzed_table""",
//                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }

    @Test
    void renderSensor_whenNoTimeSeriesAndCheckForEmptyStringList_thenRendersCorrectSql() {
        ArrayList<String> valuesList = new ArrayList<>();
        this.sut.setValuesList(valuesList);
        this.sut.setValuesType(BuiltInListFormats.STRING);
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        Assertions.assertEquals(String.format("""
                                SELECT
                                    CASE
                                        WHEN COUNT(*) = 0 THEN NULL
                                        ELSE 
                                            100.0 * SUM(
                                                CASE
                                                    WHEN (analyzed_table.`length_string` IN (NULL)) IS TRUE THEN 1
                                                    ELSE 0
                                                END
                                            )/COUNT(*)
                                    END AS actual_value   
                                FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    // TODO: move the test to another sensor that works on numeric values

//    @Test
//    void renderSensor_whenNoTimeSeriesAndCheckForNumericalValues_thenRendersCorrectSql() {
//        ArrayList<Integer> valuesList = new ArrayList<>();
//        valuesList.add(123);
//        valuesList.add(1234);
//        this.sut.setValuesType(BuiltInListFormats.NUMERIC);
//        this.sut.setValuesList(valuesList);
//        runParameters.setTimeSeries(null);
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//        Assertions.assertEquals(String.format("""
//                                SELECT
//                                    CASE
//                                        WHEN COUNT(*) = 0 THEN NULL
//                                        ELSE
//                                            100.0 * SUM(
//                                                CASE
//                                                    WHEN (CAST(analyzed_table.`length_string` AS NUMERIC) IN (123, 1234)) IS TRUE THEN 1
//                                                    ELSE 0
//                                                END
//                                            )/COUNT(*)
//                                    END AS actual_value
//                                FROM %s AS analyzed_table""",
//                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }

    @Test
    void renderSensor_whenNoTimeSeriesAndCheckForStringValues_thenRendersCorrectSql() {
        ArrayList<String> valuesList = new ArrayList<>();
        valuesList.add("abc");
        valuesList.add("abcd");
        this.sut.setValuesType(BuiltInListFormats.STRING);
        this.sut.setValuesList(valuesList);
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        Assertions.assertEquals(String.format("""
                                SELECT
                                    CASE
                                        WHEN COUNT(*) = 0 THEN NULL
                                        ELSE 
                                            100.0 * SUM(
                                                CASE
                                                    WHEN (analyzed_table.`length_string` IN ('abc', 'abcd')) IS TRUE THEN 1
                                                    ELSE 0
                                                END
                                            )/COUNT(*)
                                    END AS actual_value
                                FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    void renderSensor_whenNoTimeSeriesAndCheckForDateValuesWhereCorrect1_thenRendersCorrectSql() {
        ArrayList<String> valuesList = new ArrayList<>();
        valuesList.add("2020-05-31");
        this.sut.setValuesList(valuesList);
        this.sut.setFilter("correct=1");
        this.sut.setValuesType(BuiltInListFormats.DATE);
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        Assertions.assertEquals(String.format("""
                                SELECT
                                    CASE
                                        WHEN COUNT(*) = 0 THEN NULL
                                        ELSE 
                                            100.0 * SUM(
                                                CASE
                                                    WHEN (CAST(analyzed_table.`length_string` AS DATE) IN (CAST('2020-05-31' AS DATE))) IS TRUE THEN 1
                                                    ELSE 0
                                                END
                                            )/COUNT(*)
                                    END AS actual_value   
                                FROM %s AS analyzed_table
                                WHERE correct=1""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }
}
