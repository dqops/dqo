/*
 * Copyright © 2021 DQOps (support@dqops.com)
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
package com.dqops.sensors.bigquery.column.nulls;

//import com.dqops.BaseTest;
//import com.dqops.checks.column.checkspecs.nulls.ColumnNullsNotNullCountCheckSpec;
//import com.dqops.connectors.ProviderType;
//import com.dqops.execution.sensors.SensorExecutionRunParameters;
//import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
//import com.dqops.execution.sqltemplates.rendering.JinjaTemplateRenderServiceObjectMother;
//import com.dqops.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
//import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
//import com.dqops.metadata.groupings.DataStreamLevelSpecObjectMother;
//import com.dqops.metadata.groupings.DataStreamMappingSpecObjectMother;
//import com.dqops.metadata.groupings.TimeSeriesConfigurationSpecObjectMother;
//import com.dqops.metadata.groupings.TimeSeriesGradient;
//import com.dqops.metadata.sources.ColumnSpecObjectMother;
//import com.dqops.metadata.sources.TableSpecObjectMother;
//import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
//import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
//import com.dqops.sampledata.SampleCsvFileNames;
//import com.dqops.sampledata.SampleTableMetadata;
//import com.dqops.sampledata.SampleTableMetadataObjectMother;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class ColumnNullsNotNullCountSensorParametersSpecBigQueryTests extends BaseTest {
//    private ColumnValidityNotNullCountSensorParametersSpec sut;
//    private UserHomeContext userHomeContext;
//    private SensorExecutionRunParameters runParameters;
//    private ColumnNullsNotNullCountCheckSpec checkSpec;
//    private SampleTableMetadata sampleTableMetadata;
//
//    /**
//     * Called before each test.
//     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
//     *
//     * @throws Throwable
//     */
//    @Override
//    @BeforeEach
//    protected void setUp() throws Throwable {
//        super.setUp();
//		this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day, ProviderType.bigquery);
//		this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
//		this.sut = new ColumnValidityNotNullCountSensorParametersSpec();
//		this.checkSpec = new ColumnNullsNotNullCountCheckSpec();
//		this.checkSpec.setParameters(this.sut);
//		this.runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "id", this.checkSpec);
//    }
//
//    @Test
//    void getSensorDefinitionName_whenSensorDefinitionForBigQueryRetrieved_thenDefinitionFoundInDqoHome() {
//        ProviderSensorDefinitionWrapper providerSensorDefinitionWrapper =
//                SensorDefinitionWrapperObjectMother.findProviderDqoHomeSensorDefinition(this.sut.getSensorDefinitionName(), ProviderType.bigquery);
//        Assertions.assertNotNull(providerSensorDefinitionWrapper.getSpec());
//        Assertions.assertNotNull(providerSensorDefinitionWrapper);
//    }
//
//    @Test
//    void renderSensor_whenNoTimeSeries_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(null);
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                SELECT
//                    COUNT(analyzed_table.`id`) AS actual_value
//                FROM %s AS analyzed_table""",
//                        JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenDefaultTimeSeries_thenRendersTimeSeriesFromTableSpecAsDailyCurrentTimestamp() {
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, CURRENT_TIMESTAMP() AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenDefaultTimeSeriesAndWhereFilterOnTable_thenRendersTimeSeriesFromTableSpecAndOneWhereClause() {
//		runParameters.getTable().setFilter("col1=1");
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, CURRENT_TIMESTAMP() AS time_period
//                        FROM %s AS analyzed_table
//                        WHERE col1=1
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenDefaultTimeSeriesAndWhereFilterOnCheck_thenRendersTimeSeriesFromTableSpecAndOneWhereClause() {
//		runParameters.getSensorParameters().setFilter("col2=2");
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, CURRENT_TIMESTAMP() AS time_period
//                        FROM %s AS analyzed_table
//                        WHERE col2=2
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenDefaultTimeSeriesAndWhereFiltersOnTableAndCheck_thenRendersTimeSeriesFromTableSpecAndTwoWhereClauseFilters() {
//        runParameters.getTable().setFilter("col1=1");
//		runParameters.getSensorParameters().setFilter("col2=2");
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, CURRENT_TIMESTAMP() AS time_period
//                        FROM %s AS analyzed_table
//                        WHERE col1=1 AND col2=2
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesCurrentTimeGradientYear_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.YEAR));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), YEAR) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesCurrentQuarter_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.QUARTER));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), QUARTER) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesCurrentWeek_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.WEEK));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), WEEK) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesCurrentTimeGradientQuarter_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.QUARTER));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), QUARTER) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesCurrentTimeGradientMonth_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.MONTH));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesCurrentTimeGradientWeek_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.WEEK));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), WEEK) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesCurrentTimeGradientDay_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(null);
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.DAY));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesCurrentTimeGradientHour_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.HOUR));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, DATETIME_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATETIME), HOUR) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesIsColumnAndGradientYear_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.YEAR));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, DATE_TRUNC(CAST(analyzed_table.`created_at` AS DATE), YEAR) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesQuarterOfColumn_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.QUARTER));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, DATE_TRUNC(CAST(analyzed_table.`created_at` AS DATE), QUARTER) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesMonthOfColumn_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.MONTH));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, DATE_TRUNC(CAST(analyzed_table.`created_at` AS DATE), MONTH) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesWeekOfColumn_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.WEEK));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, DATE_TRUNC(CAST(analyzed_table.`created_at` AS DATE), WEEK) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesIsColumnAndGradientDayAndColumnNotInTableSpecColumns_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.DAY));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, CAST(analyzed_table.`created_at` AS DATE) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesIsColumnAndGradientDayAndTimestampColumnIsTimestamp_thenRendersCorrectSqlWithCasting() {
//        TableSpecObjectMother.addColumn(runParameters.getTable(), "created_at", ColumnSpecObjectMother.createForType("TIMESTAMP"));
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.DAY));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, CAST(analyzed_table.`created_at` AS DATE) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesIsColumnAndGradientDayAndTimestampColumnIsDate_thenRendersCorrectSqlWithoutCastingAndTruncating() {
//        TableSpecObjectMother.addColumn(runParameters.getTable(), "created_at", ColumnSpecObjectMother.createForType("DATE"));
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.DAY));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, analyzed_table.`created_at` AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesIsColumnAndGradientHourAtConnection_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.HOUR));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, DATETIME_TRUNC(CAST(analyzed_table.`created_at` AS DATETIME), HOUR) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY time_period
//                        ORDER BY time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenlevelOneStaticColumn_thenTableSettingsTakePriority() {
//        runParameters.setTimeSeries(null);
//		runParameters.setDataStreams(
//                DataStreamMappingSpecObjectMother.create(
//                        DataStreamLevelSpecObjectMother.createTag("FR")));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, 'FR' AS grouping_level_1
//                        FROM %s AS analyzed_table
//                        GROUP BY grouping_level_1
//                        ORDER BY grouping_level_1""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenlevelOneStaticValue_thenRendersCorrectSql() {
//        runParameters.setTimeSeries(null);
//		runParameters.setDataStreams(
//                DataStreamMappingSpecObjectMother.create(
//                        DataStreamLevelSpecObjectMother.createTag("IT")));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, 'IT' AS grouping_level_1
//                        FROM %s AS analyzed_table
//                        GROUP BY grouping_level_1
//                        ORDER BY grouping_level_1""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenlevel1StaticStringAndNoTimeSeries_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(null);
//		runParameters.setDataStreams(
//                DataStreamMappingSpecObjectMother.create(DataStreamLevelSpecObjectMother.createTag("DE")));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, 'DE' AS grouping_level_1
//                        FROM %s AS analyzed_table
//                        GROUP BY grouping_level_1
//                        ORDER BY grouping_level_1""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenlevel1StaticStringWithQuoteAndNoTimeSeries_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(null);
//		runParameters.setDataStreams(
//                DataStreamMappingSpecObjectMother.create(DataStreamLevelSpecObjectMother.createTag("DE's")));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, 'DE''s' AS grouping_level_1
//                        FROM %s AS analyzed_table
//                        GROUP BY grouping_level_1
//                        ORDER BY grouping_level_1""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whennlevel1StaticStringlevel2StaticStringAndNoTimeSeries_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(null);
//		runParameters.setDataStreams(
//                DataStreamMappingSpecObjectMother.create(
//                        DataStreamLevelSpecObjectMother.createTag("DE"),
//                        DataStreamLevelSpecObjectMother.createTag("PL")));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, 'DE' AS grouping_level_1, 'PL' AS grouping_level_2
//                        FROM %s AS analyzed_table
//                        GROUP BY grouping_level_1, grouping_level_2
//                        ORDER BY grouping_level_1, grouping_level_2""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenlevel1StaticStringlevel2StaticStringlevel3StaticStringAndNoTimeSeries_thenRendersCorrectSql() {
//		runParameters.setTimeSeries(null);
//		runParameters.setDataStreams(
//                DataStreamMappingSpecObjectMother.create(
//                        DataStreamLevelSpecObjectMother.createTag("DE"),
//                        DataStreamLevelSpecObjectMother.createTag("PL"),
//                        DataStreamLevelSpecObjectMother.createTag("UK")));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, 'DE' AS grouping_level_1, 'PL' AS grouping_level_2, 'UK' AS grouping_level_3
//                        FROM %s AS analyzed_table
//                        GROUP BY grouping_level_1, grouping_level_2, grouping_level_3
//                        ORDER BY grouping_level_1, grouping_level_2, grouping_level_3""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenMissinglevel1level2StaticStringlevel3StaticStringAndNoTimeSeries_thenRendersCorrectSql() {
//        runParameters.setTimeSeries(null);
//		runParameters.setDataStreams(
//                DataStreamMappingSpecObjectMother.create(
//                        null,
//                        DataStreamLevelSpecObjectMother.createTag("PL"),
//                        DataStreamLevelSpecObjectMother.createTag("UK")));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, 'PL' AS grouping_level_2, 'UK' AS grouping_level_3
//                        FROM %s AS analyzed_table
//                        GROUP BY grouping_level_2, grouping_level_3
//                        ORDER BY grouping_level_2, grouping_level_3""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenTimeSeriesDailyAndlevel1StaticAtCheck_thenRendersCorrectSqlWithTimelevelAsLastGrouping() {
//		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.DAY));
//		runParameters.setDataStreams(
//                DataStreamMappingSpecObjectMother.create(
//                        DataStreamLevelSpecObjectMother.createTag("US"),
//                        DataStreamLevelSpecObjectMother.createTag("PL")));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, 'US' AS grouping_level_1, 'PL' AS grouping_level_2, CAST(CURRENT_TIMESTAMP() AS DATE) AS time_period
//                        FROM %s AS analyzed_table
//                        GROUP BY grouping_level_1, grouping_level_2, time_period
//                        ORDER BY grouping_level_1, grouping_level_2, time_period""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//
//    @Test
//    void renderSensor_whenlevelOnColumnAndSecondlevelIsStaticValue_thenRendersCorrectSqlWithAliasedColumnReference() {
//		runParameters.setTimeSeries(null);
//		runParameters.setDataStreams(
//                DataStreamMappingSpecObjectMother.create(
//                        DataStreamLevelSpecObjectMother.createColumnMapping("country"),
//                        DataStreamLevelSpecObjectMother.createTag("UK")));
//
//        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
//
//        Assertions.assertEquals(String.format("""
//                        SELECT
//                            COUNT(analyzed_table.`id`) AS actual_value, analyzed_table.`country` AS grouping_level_1, 'UK' AS grouping_level_2
//                        FROM %s AS analyzed_table
//                        GROUP BY grouping_level_1, grouping_level_2
//                        ORDER BY grouping_level_1, grouping_level_2""",
//                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
//                renderedTemplate);
//    }
//}
