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
package ai.dqo.sensors.bigquery.column.consistency;

import ai.dqo.BaseTest;
import ai.dqo.checks.column.consistency.ColumnConsistencyNotNullPercentCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderServiceObjectMother;
import ai.dqo.metadata.definitions.sensors.ProviderSensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
import ai.dqo.metadata.groupings.DimensionMappingSpecObjectMother;
import ai.dqo.metadata.groupings.DimensionsConfigurationSpecObjectMother;
import ai.dqo.metadata.groupings.TimeSeriesConfigurationSpecObjectMother;
import ai.dqo.metadata.groupings.TimeSeriesGradient;
import ai.dqo.metadata.sources.ColumnSpecObjectMother;
import ai.dqo.metadata.sources.TableSpecObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.sensors.column.consistency.ColumnConsistencyNotNullPercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnConsistencyNotNullPercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnConsistencyNotNullPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private SensorExecutionRunParameters runParameters;
    private ColumnConsistencyNotNullPercentCheckSpec checkSpec;
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
        this.sut = new ColumnConsistencyNotNullPercentSensorParametersSpec();
        this.checkSpec = new ColumnConsistencyNotNullPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
        this.runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndCheck(sampleTableMetadata, "id", this.checkSpec);
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value
                        FROM `%s`.`%s`.`%s` AS analyzed_table""",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenDefaultTimeSeries_thenRendersTimeSeriesFromTableSpecAsDailyCurrentTimestamp() {
        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS date), year) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS date), quarter) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesCurrentWeek_thenRendersCorrectSql() {
        runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.WEEK));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS date), week) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS date), quarter) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS date), month) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS date), week) AS time_period
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
        runParameters.setTimeSeries(null);
        runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.DAY));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, DATETIME_TRUNC(CAST(CURRENT_TIMESTAMP() AS datetime), hour) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, DATE_TRUNC(CAST(analyzed_table.`created_at` AS date), year) AS time_period
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
        runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createTimestampColumnTimeSeries("created_at", TimeSeriesGradient.QUARTER));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, DATE_TRUNC(CAST(analyzed_table.`created_at` AS date), quarter) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, DATE_TRUNC(CAST(analyzed_table.`created_at` AS date), month) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, DATE_TRUNC(CAST(analyzed_table.`created_at` AS date), week) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, CAST(analyzed_table.`created_at` AS date) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, CAST(analyzed_table.`created_at` AS date) AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, analyzed_table.`created_at` AS time_period
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
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, DATETIME_TRUNC(CAST(analyzed_table.`created_at` AS datetime), hour) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY time_period
                        ORDER BY time_period""",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenDimensionOneStaticColumn_thenTableSettingsTakePriority() {
        runParameters.setTimeSeries(null);
        runParameters.setDimensions(
                DimensionsConfigurationSpecObjectMother.create(
                        DimensionMappingSpecObjectMother.createStaticValue("FR")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, 'FR' AS dimension_1
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY dimension_1
                        ORDER BY dimension_1""",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenDimensionOneStaticColumn_thenRendersOneGrouping() {
        runParameters.setTimeSeries(null);
        runParameters.setDimensions(
                DimensionsConfigurationSpecObjectMother.create(
                        DimensionMappingSpecObjectMother.createStaticValue("IT")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, 'IT' AS dimension_1
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY dimension_1
                        ORDER BY dimension_1""",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenDimension1StaticStringAndNoTimeSeries_thenRendersCorrectSql() {
        runParameters.setTimeSeries(null);
        runParameters.setDimensions(
                DimensionsConfigurationSpecObjectMother.create(DimensionMappingSpecObjectMother.createStaticValue("DE")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, 'DE' AS dimension_1
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY dimension_1
                        ORDER BY dimension_1""",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenDimension1StaticStringWithQuoteAndNoTimeSeries_thenRendersCorrectSql() {
        runParameters.setTimeSeries(null);
        runParameters.setDimensions(
                DimensionsConfigurationSpecObjectMother.create(DimensionMappingSpecObjectMother.createStaticValue("DE's")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, 'DE''s' AS dimension_1
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY dimension_1
                        ORDER BY dimension_1""",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenDimension1StaticStringDimension2StaticStringAndNoTimeSeries_thenRendersCorrectSql() {
        runParameters.setTimeSeries(null);
        runParameters.setDimensions(
                DimensionsConfigurationSpecObjectMother.create(
                        DimensionMappingSpecObjectMother.createStaticValue("DE"),
                        DimensionMappingSpecObjectMother.createStaticValue("PL")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, 'DE' AS dimension_1, 'PL' AS dimension_2
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY dimension_1, dimension_2
                        ORDER BY dimension_1, dimension_2""",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenDimension1StaticStringDimension2StaticStringDimension3StaticStringAndNoTimeSeries_thenRendersCorrectSql() {
        runParameters.setTimeSeries(null);
        runParameters.setDimensions(
                DimensionsConfigurationSpecObjectMother.create(
                        DimensionMappingSpecObjectMother.createStaticValue("DE"),
                        DimensionMappingSpecObjectMother.createStaticValue("PL"),
                        DimensionMappingSpecObjectMother.createStaticValue("UK")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, 'DE' AS dimension_1, 'PL' AS dimension_2, 'UK' AS dimension_3
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY dimension_1, dimension_2, dimension_3
                        ORDER BY dimension_1, dimension_2, dimension_3""",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenMissingDimension1Dimension2StaticStringDimension3StaticStringAndNoTimeSeries_thenRendersCorrectSql() {
        runParameters.setTimeSeries(null);
        runParameters.setDimensions(
                DimensionsConfigurationSpecObjectMother.create(
                        null,
                        DimensionMappingSpecObjectMother.createStaticValue("PL"),
                        DimensionMappingSpecObjectMother.createStaticValue("UK")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, 'PL' AS dimension_2, 'UK' AS dimension_3
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY dimension_2, dimension_3
                        ORDER BY dimension_2, dimension_3""",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenTimeSeriesDailyAndDimension1StaticAtCheck_thenRendersCorrectSqlWithTimeDimensionAsLastGrouping() {
        runParameters.setTimeSeries(TimeSeriesConfigurationSpecObjectMother.createCurrentTimeSeries(TimeSeriesGradient.DAY));
        runParameters.setDimensions(
                DimensionsConfigurationSpecObjectMother.create(
                        DimensionMappingSpecObjectMother.createStaticValue("US"),
                        DimensionMappingSpecObjectMother.createStaticValue("PL")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, 'US' AS dimension_1, 'PL' AS dimension_2, CAST(CURRENT_TIMESTAMP() AS date) AS time_period
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY dimension_1, dimension_2, time_period
                        ORDER BY dimension_1, dimension_2, time_period""",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenDimensionOnColumnAndSecondDimensionIsStaticValue_thenRendersCorrectSqlWithAliasedColumnReference() {
        runParameters.setTimeSeries(null);
        runParameters.setDimensions(
                DimensionsConfigurationSpecObjectMother.create(
                        DimensionMappingSpecObjectMother.createColumnMapping("country"),
                        DimensionMappingSpecObjectMother.createStaticValue("UK")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        Assertions.assertEquals(String.format("""
                        SELECT
                          CASE
                              WHEN COUNT(*)=0 THEN NULL
                              ELSE
                                100.0 * COUNT(analyzed_table.`id`)/ COUNT(*)
                          END AS actual_value, analyzed_table.`country` AS dimension_1, 'UK' AS dimension_2
                        FROM `%s`.`%s`.`%s` AS analyzed_table
                        GROUP BY dimension_1, dimension_2
                        ORDER BY dimension_1, dimension_2""",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName()
        ), renderedTemplate);
    }
}