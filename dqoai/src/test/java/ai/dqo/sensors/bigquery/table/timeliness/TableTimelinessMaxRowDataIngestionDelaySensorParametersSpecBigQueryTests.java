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
package ai.dqo.sensors.bigquery.table.timeliness;

import ai.dqo.BaseTest;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxDataIngestionDelayCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableMaxRowDataIngestionDelayCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.execution.sqltemplates.JinjaTemplateRenderServiceObjectMother;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
import ai.dqo.metadata.groupings.*;
import ai.dqo.metadata.sources.TimestampColumnsSpec;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.sensors.table.timeliness.TableTimelinessDataIngestionDelaySensorParametersSpec;
import ai.dqo.sensors.table.timeliness.TableTimelinessMaxRowDataIngestionDelaySensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableTimelinessMaxRowDataIngestionDelaySensorParametersSpecBigQueryTests extends BaseTest {
    private TableTimelinessMaxRowDataIngestionDelaySensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private TableMaxRowDataIngestionDelayCheckSpec checkSpec;
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
		this.sut = new TableTimelinessMaxRowDataIngestionDelaySensorParametersSpec();
        this.sut.setFilter("{table}.correct = 0");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_average_delay, ProviderType.bigquery);
        this.sampleTableMetadata.getTableSpec().setTimestampColumns(
                new TimestampColumnsSpec() {{
                    setEventTimestampColumn("date1");
                    setIngestionTimestampColumn("date2");
                }}
        );

        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new TableMaxRowDataIngestionDelayCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersAdHoc() {
        return SensorExecutionRunParametersObjectMother.createForTableForAdHocCheck(this.sampleTableMetadata, this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersCheckpoint(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableForCheckpointCheck(this.sampleTableMetadata, this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunParametersPartitioned(CheckTimeScale timeScale, String timeSeriesColumn) {
        return SensorExecutionRunParametersObjectMother.createForTableForPartitionedCheck(this.sampleTableMetadata, this.checkSpec, timeScale, timeSeriesColumn);
    }

    private String getSubstitutedFilter(String tableName) {
        // return this.checkSpec.getParameters().getFilter().replace("{table}", tableName);
        return this.checkSpec.getParameters().getFilter();
    }

    private String getEventTimestampColumn() {
        return this.sampleTableMetadata.getTableSpec().getTimestampColumns().getEventTimestampColumn();
    }

    private String getIngestionTimestampColumn() {
        return this.sampleTableMetadata.getTableSpec().getTimestampColumns().getIngestionTimestampColumn();
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
        Assertions.assertEquals("table/timeliness/max_row_data_ingestion_delay", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenAdHocNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersAdHoc();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                MAX(
                    TIMESTAMP_DIFF(
                        CAST(%s AS TIMESTAMP),
                        CAST(%s AS TIMESTAMP),
                        MILLISECOND
                    )
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s""";

        Assertions.assertEquals(String.format(target_query,
                this.getIngestionTimestampColumn(),
                this.getEventTimestampColumn(),
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
            setTimestampColumn("date4");
        }});

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                MAX(
                    TIMESTAMP_DIFF(
                        CAST(%s AS TIMESTAMP),
                        CAST(%s AS TIMESTAMP),
                        MILLISECOND
                    )
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value, CAST(analyzed_table.`date4` AS DATE) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY time_period
            ORDER BY time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getIngestionTimestampColumn(),
                this.getEventTimestampColumn(),
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
                MAX(
                    TIMESTAMP_DIFF(
                        CAST(%s AS TIMESTAMP),
                        CAST(%s AS TIMESTAMP),
                        MILLISECOND
                    )
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY time_period
            ORDER BY time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getIngestionTimestampColumn(),
                this.getEventTimestampColumn(),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date4");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                MAX(
                    TIMESTAMP_DIFF(
                        CAST(%s AS TIMESTAMP),
                        CAST(%s AS TIMESTAMP),
                        MILLISECOND
                    )
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value, CAST(analyzed_table.`date4` AS DATE) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY time_period
            ORDER BY time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getIngestionTimestampColumn(),
                this.getEventTimestampColumn(),
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
                        DataStreamLevelSpecObjectMother.createColumnMapping("date3")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                MAX(
                    TIMESTAMP_DIFF(
                        CAST(%s AS TIMESTAMP),
                        CAST(%s AS TIMESTAMP),
                        MILLISECOND
                    )
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value, analyzed_table.`date3` AS stream_level_1
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1
            ORDER BY stream_level_1""";

        Assertions.assertEquals(String.format(target_query,
                this.getIngestionTimestampColumn(),
                this.getEventTimestampColumn(),
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
                    DataStreamLevelSpecObjectMother.createColumnMapping("date3")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                MAX(
                    TIMESTAMP_DIFF(
                        CAST(%s AS TIMESTAMP),
                        CAST(%s AS TIMESTAMP),
                        MILLISECOND
                    )
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value, analyzed_table.`date3` AS stream_level_1, DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1, time_period
            ORDER BY stream_level_1, time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getIngestionTimestampColumn(),
                this.getEventTimestampColumn(),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date4");
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date3")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                MAX(
                    TIMESTAMP_DIFF(
                        CAST(%s AS TIMESTAMP),
                        CAST(%s AS TIMESTAMP),
                        MILLISECOND
                    )
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value, analyzed_table.`date3` AS stream_level_1, CAST(analyzed_table.`date4` AS DATE) AS time_period
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY stream_level_1, time_period
            ORDER BY stream_level_1, time_period""";

        Assertions.assertEquals(String.format(target_query,
                this.getIngestionTimestampColumn(),
                this.getEventTimestampColumn(),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getTarget().getSchemaName(),
                runParameters.getTable().getTarget().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }
}
