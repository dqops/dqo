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
package ai.dqo.sensors.bigquery.column.nulls;

import ai.dqo.BaseTest;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.column.checkspecs.nulls.ColumnNotNullsPercentCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderServiceObjectMother;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
import ai.dqo.metadata.groupings.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.sensors.column.nulls.ColumnNullsNotNullsPercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnNullsNotNullsPercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnNullsNotNullsPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnNotNullsPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnNullsNotNullsPercentSensorParametersSpec();
        this.sut.setFilter("{alias}.`id` <> 4");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_average_delay, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnNotNullsPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersProfiling() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "date3", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersRecurring(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForRecurringCheck(this.sampleTableMetadata, "date3", this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunParametersPartitioned(CheckTimeScale timeScale, String timeSeriesColumn) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(this.sampleTableMetadata, "date3", this.checkSpec, timeScale, timeSeriesColumn);
    }

    private String getTableColumnName(SensorExecutionRunParameters runParameters) {
        return String.format("analyzed_table.`%s`", runParameters.getColumn().getColumnName());
    }

    private String getSubstitutedFilter(String tableName) {
        return this.checkSpec.getParameters().getFilter() != null ?
               this.checkSpec.getParameters().getFilter().replace("{alias}", "analyzed_table") : null;
    }

    @Test
    void getSensorDefinitionName_whenSensorDefinitionRetrieved_thenDefinitionFoundInDqoHome() {
        SensorDefinitionWrapper sensorDefinitionWrapper =
                SensorDefinitionWrapperObjectMother.findDqoHomeSensorDefinition(this.sut.getSensorDefinitionName());
        Assertions.assertNotNull(sensorDefinitionWrapper.getSpec());
        Assertions.assertNotNull(sensorDefinitionWrapper);
    }

    @Test
    void getSensorDefinitionName_whenSensorDefinitionRetrieved_thenEqualsExpectedName() {
        Assertions.assertEquals("column/nulls/not_null_percent", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE 100.0 * COUNT(%s) / COUNT(*)
                END AS actual_value
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }


    @Test
    void renderSensor_whenProfilingOneTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimeSeriesGradient.day);
            setTimestampColumn("date1");
        }});

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE 100.0 * COUNT(%s) / COUNT(*)
                END AS actual_value,
                CAST(analyzed_table.`date1` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenRecurringDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring(CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE 100.0 * COUNT(%s) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date1");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE 100.0 * COUNT(%s) / COUNT(*)
                END AS actual_value,
                CAST(analyzed_table.`date1` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
                  AND analyzed_table.`date1` >= CAST(DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY) AS DATETIME)
                  AND analyzed_table.`date1` < CAST(CURRENT_DATE() AS DATETIME)
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }


    @Test
    void renderSensor_whenProfilingNoTimeSeriesOneDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE 100.0 * COUNT(%s) / COUNT(*)
                END AS actual_value,
                analyzed_table.`date2` AS stream_level_1
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1
            ORDER BY stream_level_1""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenRecurringDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring(CheckTimeScale.monthly);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE 100.0 * COUNT(%s) / COUNT(*)
                END AS actual_value,
                analyzed_table.`date2` AS stream_level_1,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1, time_period, time_period_utc
            ORDER BY stream_level_1, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date1");
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE 100.0 * COUNT(%s) / COUNT(*)
                END AS actual_value,
                analyzed_table.`date2` AS stream_level_1,
                CAST(analyzed_table.`date1` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
                  AND analyzed_table.`date1` >= CAST(DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY) AS DATETIME)
                  AND analyzed_table.`date1` < CAST(CURRENT_DATE() AS DATETIME)
            GROUP BY stream_level_1, time_period, time_period_utc
            ORDER BY stream_level_1, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }


    @Test
    void renderSensor_whenProfilingOneTimeSeriesTwoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimeSeriesGradient.day);
            setTimestampColumn("date1");
        }});
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("date4")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE 100.0 * COUNT(%s) / COUNT(*)
                END AS actual_value,
                analyzed_table.`date2` AS stream_level_1,
                analyzed_table.`date4` AS stream_level_2,
                CAST(analyzed_table.`date1` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
            ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenRecurringDefaultTimeSeriesTwoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring(CheckTimeScale.monthly);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("date4")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE 100.0 * COUNT(%s) / COUNT(*)
                END AS actual_value,
                analyzed_table.`date2` AS stream_level_1,
                analyzed_table.`date4` AS stream_level_2,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
            ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDefaultTimeSeriesTwoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date1");
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("date4")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN NULL
                    ELSE 100.0 * COUNT(%s) / COUNT(*)
                END AS actual_value,
                analyzed_table.`date2` AS stream_level_1,
                analyzed_table.`date4` AS stream_level_2,
                CAST(analyzed_table.`date1` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
                  AND analyzed_table.`date1` >= CAST(DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY) AS DATETIME)
                  AND analyzed_table.`date1` < CAST(CURRENT_DATE() AS DATETIME)
            GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
            ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }
}