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
package ai.dqo.sensors.bigquery.column.datetime;

import ai.dqo.BaseTest;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.column.checkspecs.datetime.ColumnDatetimeValueInRangeDatePercentCheckSpec;
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
import ai.dqo.sensors.column.datetime.ColumnDatetimeValueInRangeDatePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnDatetimeValueInRangeDatePercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnDatetimeValueInRangeDatePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnDatetimeValueInRangeDatePercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnDatetimeValueInRangeDatePercentSensorParametersSpec();
        this.sut.setFilter("{table}.id <> 4");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_average_delay, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnDatetimeValueInRangeDatePercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersAdHoc(String columnName) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForAdHocCheck(this.sampleTableMetadata, columnName, this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersCheckpoint(String columnName, CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForCheckpointCheck(this.sampleTableMetadata, columnName, this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunParametersPartitioned(String columnName, CheckTimeScale timeScale, String timeSeriesColumn) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(this.sampleTableMetadata, columnName, this.checkSpec, timeScale, timeSeriesColumn);
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
        Assertions.assertEquals("column/datetime/value_in_range_date_percent", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenAdHocNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc("date4");
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN CAST(%1$s AS DATE) >= '' AND CAST(%1$s AS DATE) <= '' THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s""";
        
        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenAdHocNoTimeSeriesNoDataStreamTypeString_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc("date3");
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN SAFE_CAST(%1$s AS DATE) >= '' AND SAFE_CAST(%1$s AS DATE) <= '' THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s""";

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
        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc("date4");
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimeSeriesGradient.day);
            setTimestampColumn("date1");
        }});

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    100.0 * SUM(
                        CASE
                            WHEN CAST(%1$s AS DATE) >= '' AND CAST(%1$s AS DATE) <= '' THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*) AS actual_value,
                    CAST(analyzed_table.`date1` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc 
                FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE %5$s
                GROUP BY time_period, time_period_utc
                ORDER BY time_period, time_period_utc""";

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
        SensorExecutionRunParameters runParameters = this.getRunParametersCheckpoint("date4", CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN CAST(%1$s AS DATE) >= '' AND CAST(%1$s AS DATE) <= '' THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

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
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned("date4", CheckTimeScale.daily, "date1");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN CAST(%1$s AS DATE) >= '' AND CAST(%1$s AS DATE) <= '' THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value,
                CAST(analyzed_table.`date1` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

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
        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc("date4");
        runParameters.setTimeSeries(null);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN CAST(%1$s AS DATE) >= '' AND CAST(%1$s AS DATE) <= '' THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value,
                analyzed_table.`date2` AS stream_level_1
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
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
        SensorExecutionRunParameters runParameters = this.getRunParametersCheckpoint("date4", CheckTimeScale.monthly);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN CAST(%1$s AS DATE) >= '' AND CAST(%1$s AS DATE) <= '' THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value,
                analyzed_table.`date2` AS stream_level_1,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
            GROUP BY stream_level_1, time_period, time_period_utc
            ORDER BY stream_level_1, time_period, time_period_utc""";

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
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned("date4", CheckTimeScale.daily, "date1");
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    100.0 * SUM(
                        CASE
                            WHEN CAST(%1$s AS DATE) >= '' AND CAST(%1$s AS DATE) <= '' THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*) AS actual_value,
                    analyzed_table.`date2` AS stream_level_1,
                    CAST(analyzed_table.`date1` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc
                FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE %5$s
                GROUP BY stream_level_1, time_period, time_period_utc
                ORDER BY stream_level_1, time_period, time_period_utc""";

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
        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc("date4");
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimeSeriesGradient.day);
            setTimestampColumn("date1");
        }});
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("date3")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN CAST(%1$s AS DATE) >= '' AND CAST(%1$s AS DATE) <= '' THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value,
                analyzed_table.`date2` AS stream_level_1,
                analyzed_table.`date3` AS stream_level_2,
                CAST(analyzed_table.`date1` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
            GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
            ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc""";

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
        SensorExecutionRunParameters runParameters = this.getRunParametersCheckpoint("date4", CheckTimeScale.monthly);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("date3")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN CAST(%1$s AS DATE) >= '' AND CAST(%1$s AS DATE) <= '' THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value,
                analyzed_table.`date2` AS stream_level_1,
                analyzed_table.`date3` AS stream_level_2,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
            GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
            ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc""";

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
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned("date4", CheckTimeScale.daily, "date1");
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("date3")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                100.0 * SUM(
                    CASE
                        WHEN CAST(%1$s AS DATE) >= '' AND CAST(%1$s AS DATE) <= '' THEN 1
                        ELSE 0
                    END
                ) / COUNT(*) AS actual_value,
                analyzed_table.`date2` AS stream_level_1,
                analyzed_table.`date3` AS stream_level_2,
                CAST(analyzed_table.`date1` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
            GROUP BY stream_level_1, stream_level_2, time_period, time_period_utc
            ORDER BY stream_level_1, stream_level_2, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }
}
