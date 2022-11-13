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
package ai.dqo.sensors.bigquery.table.validity;

import ai.dqo.BaseTest;
import ai.dqo.checks.table.validity.TableValidityRowCountCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.connectors.bigquery.BigQueryConnectionSpecObjectMother;
import ai.dqo.connectors.bigquery.BigQueryTableSpecObjectMother;
import ai.dqo.connectors.bigquery.BigQueryUserHomeContextObjectMother;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderServiceObjectMother;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
import ai.dqo.metadata.groupings.DataStreamLevelSpecObjectMother;
import ai.dqo.metadata.groupings.DataStreamMappingSpecObjectMother;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpecObjectMother;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.sources.ColumnSpecObjectMother;
import ai.dqo.metadata.sources.TableSpecObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.sensors.table.validity.TableValidityRowCountSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableValidityRowCountSensorParametersSpecBigQueryTests extends BaseTest {
    private TableValidityRowCountSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private SensorExecutionRunParameters runParameters;
    private TableValidityRowCountCheckSpec checkSpec;

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
		this.userHomeContext = BigQueryUserHomeContextObjectMother.createWithSampleTablesInMemory();
		this.sut = new TableValidityRowCountSensorParametersSpec();
		this.checkSpec = new TableValidityRowCountCheckSpec();
		this.checkSpec.setParameters(this.sut);
		this.runParameters = SensorExecutionRunParametersObjectMother.createForTableAndCheck(
				this.userHomeContext.getUserHome(),
                BigQueryConnectionSpecObjectMother.CONNECTION_NAME,
                BigQueryTableSpecObjectMother.DATASET_NAME,
                BigQueryTableSpecObjectMother.TableNames.bq_data_types_test.name(),
				this.checkSpec
        );
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
		runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                SELECT
                    count(*) AS actual_value
                FROM %s AS analyzed_table""",
                JinjaTemplateRenderServiceObjectMother.makeExpectedTableName(runParameters)),
                renderedTemplate);
    }

    @Test
    void renderSensor_whenDefaultTimeSeries_thenRendersTimeSeriesFromTableSpecAsDailyCurrentTimestamp() {
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenDefaultTimeSeriesAndWhereFilterOnTable_thenRendersTimeSeriesFromTableSpecAndOneWhereClause() {
		runParameters.getTable().setFilter("col1=1");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        WHERE col1=1
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenDefaultTimeSeriesAndWhereFilterOnCheck_thenRendersTimeSeriesFromTableSpecAndOneWhereClause() {
		runParameters.getSensorParameters().setFilter("col2=2");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        WHERE col2=2
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenDefaultTimeSeriesAndWhereFiltersOnTableAndCheck_thenRendersTimeSeriesFromTableSpecAndTwoWhereClauseFilters() {
		runParameters.getTable().setFilter("col1=1");
		runParameters.getSensorParameters().setFilter("col2=2");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        WHERE col1=1 AND col2=2
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesCurrentTimeGradientYear_thenRendersCorrectSql() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.YEAR));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS date), year) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesCurrentQuarter_thenRendersCorrectSql() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.QUARTER));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS date), quarter) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesConfigured_thenUsedFromSensorRunParameters() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.WEEK));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS date), week) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesCurrentTimeGradientQuarter_thenRendersCorrectSql() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.QUARTER));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS date), quarter) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesCurrentTimeGradientMonth_thenRendersCorrectSql() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.MONTH));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS date), month) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesCurrentTimeGradientWeek_thenRendersCorrectSql() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.WEEK));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS date), week) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesCurrentTimeGradientDay_thenRendersCorrectSql() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.DAY));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesCurrentTimeGradientHour_thenRendersCorrectSql() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.HOUR));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, DATETIME_TRUNC(CAST(CURRENT_TIMESTAMP() AS datetime), hour) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesIsColumnAndGradientYear_thenRendersCorrectSql() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.YEAR));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, DATE_TRUNC(CAST(analyzed_table.`created_at` AS date), year) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesIsColumnAndGradientQuarter_thenRendersCorrectSql() {
		runParameters.setTimeSeries(null);
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.QUARTER));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, DATE_TRUNC(CAST(analyzed_table.`created_at` AS date), quarter) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesIsColumnAndGradientMonth_thenRendersCorrectSql() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.MONTH));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, DATE_TRUNC(CAST(analyzed_table.`created_at` AS date), month) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesIsColumnAndGradientWeek_thenRendersCorrectSql() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.WEEK));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, DATE_TRUNC(CAST(analyzed_table.`created_at` AS date), week) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesIsColumnAndGradientDayAndColumnNotInTableSpecColumns_thenRendersCorrectSql() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.DAY));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, CAST(analyzed_table.`created_at` AS date) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesIsColumnAndGradientDayAndTimestampColumnIsTimestamp_thenRendersCorrectSqlWithCasting() {
        TableSpecObjectMother.addColumn(runParameters.getTable(), "created_at", ColumnSpecObjectMother.createForType("TIMESTAMP"));
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.DAY));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, CAST(analyzed_table.`created_at` AS date) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesIsColumnAndGradientDayAndTimestampColumnIsDate_thenRendersCorrectSqlWithoutCastingAndTruncating() {
        TableSpecObjectMother.addColumn(runParameters.getTable(), "created_at", ColumnSpecObjectMother.createForType("DATE"));
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.DAY));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, analyzed_table.`created_at` AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesIsColumnAndGradientHour_thenRendersCorrectSql() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.HOUR));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, DATETIME_TRUNC(CAST(analyzed_table.`created_at` AS datetime), hour) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenLevelOneStaticColumnAndNoTimeSeries_thenRendersCorrectSql() {
		runParameters.setTimeSeries(null);
		runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createStaticValue("FR")));
		String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, 'FR' AS stream_level_1
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY stream_level_1
                        ORDER BY stream_level_1""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenLevelOnSensorRunParameters_thenLevelsSettingsUsed() {
		runParameters.setTimeSeries(null);
		runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createStaticValue("IT")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, 'IT' AS stream_level_1
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY stream_level_1
                        ORDER BY stream_level_1""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenLevel1StaticStringAndNoTimeSeries_thenRendersCorrectSql() {
		runParameters.setTimeSeries(null);
		runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(DataStreamLevelSpecObjectMother.createStaticValue("DE")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, 'DE' AS stream_level_1
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY stream_level_1
                        ORDER BY stream_level_1""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenLevel1StaticStringWithQuoteAndNoTimeSeries_thenRendersCorrectSql() {
		runParameters.setTimeSeries(null);
		runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(DataStreamLevelSpecObjectMother.createStaticValue("DE's")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, 'DE''s' AS stream_level_1
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY stream_level_1
                        ORDER BY stream_level_1""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenLevel1StaticStringLevel2StaticStringAndNoTimeSeries_thenRendersCorrectSql() {
		runParameters.setTimeSeries(null);
		runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createStaticValue("DE"),
                        DataStreamLevelSpecObjectMother.createStaticValue("PL")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, 'DE' AS stream_level_1, 'PL' AS stream_level_2
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY stream_level_1, stream_level_2
                        ORDER BY stream_level_1, stream_level_2""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenLevel1StaticStringLevel2StaticStringLevel3StaticStringAndNoTimeSeries_thenRendersCorrectSql() {
		runParameters.setTimeSeries(null);
		runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createStaticValue("DE"),
                        DataStreamLevelSpecObjectMother.createStaticValue("PL"),
                        DataStreamLevelSpecObjectMother.createStaticValue("UK")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, 'DE' AS stream_level_1, 'PL' AS stream_level_2, 'UK' AS stream_level_3
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY stream_level_1, stream_level_2, stream_level_3
                        ORDER BY stream_level_1, stream_level_2, stream_level_3""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenMissingLevel1Level2StaticStringLevel3StaticStringAndNoTimeSeries_thenRendersCorrectSql() {
		runParameters.setTimeSeries(null);
		runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        null,
                        DataStreamLevelSpecObjectMother.createStaticValue("PL"),
                        DataStreamLevelSpecObjectMother.createStaticValue("UK")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, 'PL' AS stream_level_2, 'UK' AS stream_level_3
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY stream_level_2, stream_level_3
                        ORDER BY stream_level_2, stream_level_3""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesDailyAndLevel1StaticAtCheck_thenRendersCorrectSqlWithTimeLevelAsLastGrouping() {
		runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.DAY));
		runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createStaticValue("US"),
                        DataStreamLevelSpecObjectMother.createStaticValue("PL")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, 'US' AS stream_level_1, 'PL' AS stream_level_2, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY stream_level_1, stream_level_2, time_period
                        ORDER BY stream_level_1, stream_level_2, time_period""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenLevelOnColumnAndSecondLevelIsStaticValue_thenRendersCorrectSqlWithAliasedColumnReference() {
		runParameters.setTimeSeries(null);
		runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("country"),
                        DataStreamLevelSpecObjectMother.createStaticValue("UK")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                            count(*) AS actual_value, analyzed_table.`country` AS stream_level_1, 'UK' AS stream_level_2
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY stream_level_1, stream_level_2
                        ORDER BY stream_level_1, stream_level_2""",
				runParameters.getConnection().getBigquery().getSourceProjectId(),
				runParameters.getTable().getTarget().getSchemaName(),
				runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }
}
