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
import ai.dqo.checks.column.checkspecs.validity.ColumnValidityDateTypePercentCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderServiceObjectMother;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
import ai.dqo.metadata.groupings.DataStreamLevelSpecObjectMother;
import ai.dqo.metadata.groupings.DataStreamMappingSpecObjectMother;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpecObjectMother;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.sensors.column.validity.BuiltInDateFormats;
import ai.dqo.sensors.column.validity.ColumnValidityDateTypePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnValidityDateTypePercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnValidityDateTypePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private SensorExecutionRunParameters runParameters;
    private ColumnValidityDateTypePercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_date_and_string_formats, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnValidityDateTypePercentSensorParametersSpec();
        this.checkSpec = new ColumnValidityDateTypePercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
        this.runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "date", this.checkSpec);
        this.sut = (ColumnValidityDateTypePercentSensorParametersSpec) runParameters.getSensorParameters();
    }

    @Test
    void getSensorDefinitionName_whenSensorDefinitionForBigQueryRetrieved_thenDefinitionFoundInDocumatiHome() {
        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper =
                SensorDefinitionWrapperObjectMother.findProviderDqoHomeSensorDefinition(this.sut.getSensorDefinitionName(), ProviderType.bigquery);
        Assertions.assertNotNull(providerSensorDefinitionWrapper.getSpec());
        Assertions.assertNotNull(providerSensorDefinitionWrapper);
    }

    @Test
    @Disabled()
    void renderSensor_whenNoTimeSeriesAndDefaultDateFormat_thenRendersCorrectSql() {
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN SAFE_CAST(analyzed_table.`date` AS FLOAT64) IS NOT NULL
                                    OR SAFE_CAST(analyzed_table.`date` AS DATE) IS NOT NULL
                                    OR SAFE.PARSE_DATE('%%Y-%%m-%%d', analyzed_table.`date`) IS NOT NULL THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    @Disabled()
    void renderSensor_whenNoTimeSeriesAndDateFomratIsMonthDayYear_thenRendersCorrectSql() {

        this.sut.setNamedDateFormat(BuiltInDateFormats.MonthDayYear);

        runParameters.setTimeSeries(null);
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN SAFE_CAST(analyzed_table.`date` AS FLOAT64) IS NOT NULL
                                    OR SAFE_CAST(analyzed_table.`date` AS DATE) IS NOT NULL
                                    OR SAFE.PARSE_DATE('%%m/%%d/%%Y', analyzed_table.`date`) IS NOT NULL THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    @Disabled()
    void renderSensor_whenNoTimeSeriesAndDateFormatIsYearMonthDay_thenRendersCorrectSql() {

        this.sut.setNamedDateFormat(BuiltInDateFormats.YearMonthDay);

        runParameters.setTimeSeries(null);
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN SAFE_CAST(analyzed_table.`date` AS FLOAT64) IS NOT NULL
                                    OR SAFE_CAST(analyzed_table.`date` AS DATE) IS NOT NULL
                                    OR SAFE.PARSE_DATE('%%Y/%%m/%%d', analyzed_table.`date`) IS NOT NULL THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    @Disabled()
    void renderSensor_whenNoTimeSeriesAndDateFormatIsMonthNameDayYear_thenRendersCorrectSql() {

        this.sut.setNamedDateFormat(BuiltInDateFormats.MonthNameDayYear);

        runParameters.setTimeSeries(null);
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN SAFE_CAST(analyzed_table.`date` AS FLOAT64) IS NOT NULL
                                    OR SAFE_CAST(analyzed_table.`date` AS DATE) IS NOT NULL
                                    OR SAFE.PARSE_DATE('%%b %%d, %%Y', analyzed_table.`date`) IS NOT NULL THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    @Disabled()
    void renderSensor_whenLevelOnColumnAndSecondLevelIsStaticValue_thenRendersCorrectSqlWithAliasedColumnReference() {
        runParameters.setTimeSeries(null);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("country"),
                        DataStreamLevelSpecObjectMother.createTag("UK")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN SAFE_CAST(analyzed_table.`date` AS FLOAT64) IS NOT NULL
                                    OR SAFE_CAST(analyzed_table.`date` AS DATE) IS NOT NULL
                                    OR SAFE.PARSE_DATE('%%Y-%%m-%%d', analyzed_table.`date`) IS NOT NULL THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value, analyzed_table.`country` AS stream_level_1, 'UK' AS stream_level_2 
                        FROM %s AS analyzed_table
                        GROUP BY stream_level_1, stream_level_2
                        ORDER BY stream_level_1, stream_level_2""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    @Disabled()
    void renderSensor_whenTimeSeriesSetOnDateColumn_thenRendersCorrectSql() {
        runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("date", TimeSeriesGradient.MONTH));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN SAFE_CAST(analyzed_table.`date` AS FLOAT64) IS NOT NULL
                                    OR SAFE_CAST(analyzed_table.`date` AS DATE) IS NOT NULL
                                    OR SAFE.PARSE_DATE('%%Y-%%m-%%d', analyzed_table.`date`) IS NOT NULL THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value, DATE_TRUNC(CAST(analyzed_table.`date` AS date), month) AS time_period 
                        FROM %s AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    void renderSensor_whenNoTimeSeriesAndCustomDateFormat_thenRendersCorrectSql() {
        runParameters.setTimeSeries(null);

        this.sut.setCustomDateFormat("%m %d %Y");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN SAFE_CAST(analyzed_table.`date` AS FLOAT64) IS NOT NULL
                                    OR SAFE_CAST(analyzed_table.`date` AS DATE) IS NOT NULL
                                    OR SAFE.PARSE_DATE('%%m %%d %%Y', analyzed_table.`date`) IS NOT NULL THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    void renderSensor_whenCustomAndNamedDateFormat_thenPrioritizeCustomDateFormat() {
        runParameters.setTimeSeries(null);

        this.sut.setNamedDateFormat(BuiltInDateFormats.DayMonthYear);
        this.sut.setCustomDateFormat("%m %d %Y");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            100.0 * SUM(
                                CASE
                                    WHEN SAFE_CAST(analyzed_table.`date` AS FLOAT64) IS NOT NULL
                                    OR SAFE_CAST(analyzed_table.`date` AS DATE) IS NOT NULL
                                    OR SAFE.PARSE_DATE('%%m %%d %%Y', analyzed_table.`date`) IS NOT NULL THEN 1
                                    ELSE 0
                                END
                            ) / COUNT(*) AS actual_value
                        FROM %s AS analyzed_table""",
                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }
}
