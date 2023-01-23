/*
 * Copyright © 2022 DQO.ai (support@dqo.ai)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import ai.dqo.checks.column.checkspecs.nulls.ColumnNullsPercentCheckSpec;
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
import ai.dqo.sensors.column.nulls.ColumnNullsNullsPercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnNullsNullsPercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnNullsNullsPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnNullsPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
		this.sut = new ColumnNullsNullsPercentSensorParametersSpec();
        this.sut.setFilter("{table}.`id` <> 4");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_average_delay, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnNullsPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersAdHoc() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForAdHocCheck(this.sampleTableMetadata, "date3", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersCheckpoint(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForCheckpointCheck(this.sampleTableMetadata, "date3", this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunParametersPartitioned(CheckTimeScale timeScale, String timeSeriesColumn) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(this.sampleTableMetadata, "date3", this.checkSpec, timeScale, timeSeriesColumn);
    }

    private String getTableColumnName(SensorExecutionRunParameters runParameters) {
        return String.format("analyzed_table.`%s`", runParameters.getColumn().getColumnName());
    }

    private String getSubstitutedFilter(String tableName) {
        // return this.checkSpec.getParameters().getFilter().replace("{table}", tableName);
        return this.checkSpec.getParameters().getFilter();
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
        Assertions.assertEquals("column/nulls/null_percent", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenAdHocNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %s IS NULL THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }


    @Test
    void renderSensor_whenAdHocOneTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimeSeriesGradient.DAY);
            setTimestampColumn("date1");
        }});

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %s IS NULL THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value, CAST(analyzed_table.`date1` AS DATE) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY time_period
            ORDER BY time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenCheckpointDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersCheckpoint(CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %s IS NULL THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY time_period
            ORDER BY time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %s IS NULL THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value, CAST(analyzed_table.`date1` AS DATE) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY time_period
            ORDER BY time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }


    @Test
    void renderSensor_whenAdHocNoTimeSeriesOneDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc();
        runParameters.setTimeSeries(null);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %s IS NULL THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value, analyzed_table.`date2` AS stream_level_1
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1
            ORDER BY stream_level_1""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenCheckpointDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersCheckpoint(CheckTimeScale.monthly);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                    DataStreamLevelSpecObjectMother.createColumnMapping("date2")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %s IS NULL THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value, analyzed_table.`date2` AS stream_level_1, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1, time_period
            ORDER BY stream_level_1, time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %s IS NULL THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value, analyzed_table.`date2` AS stream_level_1, CAST(analyzed_table.`date1` AS DATE) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1, time_period
            ORDER BY stream_level_1, time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }


    @Test
    void renderSensor_whenAdHocOneTimeSeriesTwoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimeSeriesGradient.DAY);
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %s IS NULL THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value, analyzed_table.`date2` AS stream_level_1, analyzed_table.`date4` AS stream_level_2, CAST(analyzed_table.`date1` AS DATE) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1, stream_level_2, time_period
            ORDER BY stream_level_1, stream_level_2, time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenCheckpointDefaultTimeSeriesTwoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersCheckpoint(CheckTimeScale.monthly);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("date4")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %s IS NULL THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value, analyzed_table.`date2` AS stream_level_1, analyzed_table.`date4` AS stream_level_2, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1, stream_level_2, time_period
            ORDER BY stream_level_1, stream_level_2, time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %s IS NULL THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value, analyzed_table.`date2` AS stream_level_1, analyzed_table.`date4` AS stream_level_2, CAST(analyzed_table.`date1` AS DATE) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1, stream_level_2, time_period
            ORDER BY stream_level_1, stream_level_2, time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }
}
