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
package ai.dqo.sensors.bigquery.column.numeric;

import ai.dqo.BaseTest;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.column.checkspecs.numeric.ColumnValueBelowMinValuePercentCheckSpec;
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
import ai.dqo.sensors.column.numeric.ColumnNumericValueBelowMinValuePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnNumericValueBelowMinValuePercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnNumericValueBelowMinValuePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnValueBelowMinValuePercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
		this.sut = new ColumnNumericValueBelowMinValuePercentSensorParametersSpec();
        this.sut.setFilter("{table}.`correct` = 1");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.below_above_value_test, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnValueBelowMinValuePercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersAdHoc() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForAdHocCheck(this.sampleTableMetadata, "value", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersCheckpoint(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForCheckpointCheck(this.sampleTableMetadata, "value", this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunParametersPartitioned(CheckTimeScale timeScale, String timeSeriesColumn) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(this.sampleTableMetadata, "value", this.checkSpec, timeScale, timeSeriesColumn);
    }

    private String getTableColumnName(SensorExecutionRunParameters runParameters) {
        return String.format("analyzed_table.`%s`", runParameters.getColumn().getColumnName());
    }

    private String getSubstitutedFilter(String tableName) {
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
        Assertions.assertEquals("column/numeric/value_below_min_value_percent", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenAdHocNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setMinValue(16);

        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN %s < 16 THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value
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
        this.sut.setMinValue(16);

        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimeSeriesGradient.day);
            setTimestampColumn("date");
        }});

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN %s < 16 THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value, analyzed_table.`date` AS time_period
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
        this.sut.setMinValue(16);

        SensorExecutionRunParameters runParameters = this.getRunParametersCheckpoint(CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN %s < 16 THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period
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
        this.sut.setMinValue(16);

        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN %s < 16 THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value, analyzed_table.`date` AS time_period
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
        this.sut.setMinValue(16);

        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc();
        runParameters.setTimeSeries(null);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN %s < 16 THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value, analyzed_table.`date` AS stream_level_1
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
        this.sut.setMinValue(16);

        SensorExecutionRunParameters runParameters = this.getRunParametersCheckpoint(CheckTimeScale.monthly);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("nulls_ok")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN %s < 16 THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value, analyzed_table.`nulls_ok` AS stream_level_1, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period
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
        this.sut.setMinValue(16);

        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("nulls_ok")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN %s < 16 THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value, analyzed_table.`nulls_ok` AS stream_level_1, analyzed_table.`date` AS time_period
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
    void renderSensor_whenAdHocOneTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        this.sut.setMinValue(16);

        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimeSeriesGradient.day);
            setTimestampColumn("date");
        }});
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("nulls_ok")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN %s < 16 THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value, analyzed_table.`strings_with_numbers` AS stream_level_1, analyzed_table.`mix_of_values` AS stream_level_2, analyzed_table.`nulls_ok` AS stream_level_3, analyzed_table.`date` AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1, stream_level_2, stream_level_3, time_period
            ORDER BY stream_level_1, stream_level_2, stream_level_3, time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenCheckpointDefaultTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        this.sut.setMinValue(16);

        SensorExecutionRunParameters runParameters = this.getRunParametersCheckpoint(CheckTimeScale.monthly);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("nulls_ok")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN %s < 16 THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value, analyzed_table.`strings_with_numbers` AS stream_level_1, analyzed_table.`mix_of_values` AS stream_level_2, analyzed_table.`nulls_ok` AS stream_level_3, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1, stream_level_2, stream_level_3, time_period
            ORDER BY stream_level_1, stream_level_2, stream_level_3, time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDefaultTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        this.sut.setMinValue(16);

        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("nulls_ok")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN %s < 16 THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value, analyzed_table.`strings_with_numbers` AS stream_level_1, analyzed_table.`mix_of_values` AS stream_level_2, analyzed_table.`nulls_ok` AS stream_level_3, analyzed_table.`date` AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1, stream_level_2, stream_level_3, time_period
            ORDER BY stream_level_1, stream_level_2, stream_level_3, time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }
}
