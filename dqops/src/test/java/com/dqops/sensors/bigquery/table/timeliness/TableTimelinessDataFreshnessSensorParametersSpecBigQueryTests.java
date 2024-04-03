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
package com.dqops.sensors.bigquery.table.timeliness;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.table.checkspecs.timeliness.TableDataFreshnessCheckSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.execution.sqltemplates.rendering.JinjaTemplateRenderServiceObjectMother;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
import com.dqops.metadata.groupings.*;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.timeseries.TimeSeriesMode;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.table.timeliness.TableTimelinessDataFreshnessSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TableTimelinessDataFreshnessSensorParametersSpecBigQueryTests extends BaseTest {
    private TableTimelinessDataFreshnessSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private TableDataFreshnessCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
		this.sut = new TableTimelinessDataFreshnessSensorParametersSpec();
        this.sut.setFilter("{alias}.correct = 0");
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_timeliness_sensors, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new TableDataFreshnessCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersProfiling() {
        return SensorExecutionRunParametersObjectMother.createForTableForProfilingCheck(this.sampleTableMetadata, this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersMonitoring(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableForMonitoringCheck(this.sampleTableMetadata, this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunParametersPartitioned(CheckTimeScale timeScale, String timeSeriesColumn) {
        return SensorExecutionRunParametersObjectMother.createForTableForPartitionedCheck(this.sampleTableMetadata, this.checkSpec, timeScale, timeSeriesColumn);
    }

    private String getSubstitutedFilter(String tableName) {
        return this.checkSpec.getParameters().getFilter() != null ?
               this.checkSpec.getParameters().getFilter().replace("{alias}", "analyzed_table") : null;
    }

    private String getEventTimestampColumn() {
        // return this.checkSpec.getParameters().getFilter().replace("{table}", tableName);
        return this.sampleTableMetadata.getTableSpec().getTimestampColumns().getEventTimestampColumn();
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
        Assertions.assertEquals("table/timeliness/data_freshness", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensorWithTimestampInput_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("earlier_timestamp");

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`%s`),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s""";

        Assertions.assertEquals(String.format(target_query,
                this.getEventTimestampColumn(),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensorWithDateInput_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("earlier_date");

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                DATE_DIFF(
                    CURRENT_DATE(),
                    MAX(analyzed_table.`%s`),
                    DAY
                ) AS actual_value
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s""";

        Assertions.assertEquals(String.format(target_query,
                this.getEventTimestampColumn(),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensorWithDatetimeInput_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("earlier_datetime");

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                DATETIME_DIFF(
                    CURRENT_DATETIME(),
                    MAX(analyzed_table.`%s`),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s""";

        Assertions.assertEquals(String.format(target_query,
                this.getEventTimestampColumn(),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensorWithStringInput_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("earlier_string");

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(
                        SAFE_CAST(analyzed_table.`%s` AS TIMESTAMP)
                    ),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s""";

        Assertions.assertEquals(String.format(target_query,
                this.getEventTimestampColumn(),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensorWithTimestampInput_whenProfilingOneTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("earlier_timestamp");

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("earlier_datetime");
        }});

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`%s`),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
                CAST(analyzed_table.`earlier_datetime` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`earlier_datetime` AS DATE)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getEventTimestampColumn(),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensorWithTimestampInput_whenMonitoringDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("earlier_timestamp");
        
        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`%s`),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getEventTimestampColumn(),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensorWithTimestampInput_whenPartitionedDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("earlier_timestamp");
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "earlier_datetime");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`%s`),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
                CAST(analyzed_table.`earlier_datetime` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`earlier_datetime` AS DATE)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
                  AND analyzed_table.`earlier_datetime` >= CAST(DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY) AS DATETIME)
                  AND analyzed_table.`earlier_datetime` < CAST(CURRENT_DATE() AS DATETIME)
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getEventTimestampColumn(),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }


    @Test
    void renderSensorWithTimestampInput_whenProfilingNoTimeSeriesOneDataStream_thenRendersCorrectSql() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("earlier_timestamp");
        
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("earlier_string")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`%s`),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
                analyzed_table.`earlier_string` AS grouping_level_1
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY grouping_level_1
            ORDER BY grouping_level_1""";

        Assertions.assertEquals(String.format(target_query,
                this.getEventTimestampColumn(),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensorWithTimestampInput_whenMonitoringDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("earlier_timestamp");
        
        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                    DataStreamLevelSpecObjectMother.createColumnMapping("earlier_string")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`%s`),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
                analyzed_table.`earlier_string` AS grouping_level_1,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY grouping_level_1, time_period, time_period_utc
            ORDER BY grouping_level_1, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getEventTimestampColumn(),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensorWithTimestampInput_whenPartitionedDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("earlier_timestamp");
        
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "earlier_datetime");
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("earlier_string")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                TIMESTAMP_DIFF(
                    CURRENT_TIMESTAMP(),
                    MAX(analyzed_table.`%s`),
                    MILLISECOND
                ) / 24.0 / 3600.0 / 1000.0 AS actual_value,
                analyzed_table.`earlier_string` AS grouping_level_1,
                CAST(analyzed_table.`earlier_datetime` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`earlier_datetime` AS DATE)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
                  AND analyzed_table.`earlier_datetime` >= CAST(DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY) AS DATETIME)
                  AND analyzed_table.`earlier_datetime` < CAST(CURRENT_DATE() AS DATETIME)
            GROUP BY grouping_level_1, time_period, time_period_utc
            ORDER BY grouping_level_1, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getEventTimestampColumn(),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }
}
