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
package com.dqops.sensors.bigquery.table.uniqueness;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.table.checkspecs.uniqueness.TableDuplicateRecordPercentCheckSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.execution.sqltemplates.rendering.JinjaTemplateRenderServiceObjectMother;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapper;
import com.dqops.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
import com.dqops.metadata.groupings.DataGroupingConfigurationSpecObjectMother;
import com.dqops.metadata.groupings.DataStreamLevelSpecObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.metadata.timeseries.TimePeriodGradient;
import com.dqops.metadata.timeseries.TimeSeriesConfigurationSpec;
import com.dqops.metadata.timeseries.TimeSeriesMode;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.table.uniqueness.TableDuplicateRecordPercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TableUniquenessDuplicateRecordPercentSensorParametersSpecBigQueryTests extends BaseTest {
    private TableDuplicateRecordPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private TableDuplicateRecordPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
		this.sut = new TableDuplicateRecordPercentSensorParametersSpec();
        this.sut.setFilter("{alias}.`correct` = 1");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new TableDuplicateRecordPercentCheckSpec();
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

    @Test
    void getSensorDefinitionName_whenSensorDefinitionRetrieved_thenDefinitionFoundInDqoHome() {
        SensorDefinitionWrapper sensorDefinitionWrapper =
                SensorDefinitionWrapperObjectMother.findDqoHomeSensorDefinition(this.sut.getSensorDefinitionName());
        Assertions.assertNotNull(sensorDefinitionWrapper.getSpec());
        Assertions.assertNotNull(sensorDefinitionWrapper);
    }

    @Test
    void getSensorDefinitionName_whenSensorDefinitionRetrieved_thenEqualsExpectedName() {
        Assertions.assertEquals("table/uniqueness/duplicate_record_percent", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (%s)
                          AND (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                    GROUP BY `int_nulls`, `string_nulls`
                ) grouping_table""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenProfilingOneTimeSeriesNoDataStreamAndNoFilters_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("date");
        }});
        runParameters.getSensorParameters().setFilter(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value,
                    time_period,
                    time_period_utc
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records,
                        analyzed_table.`date` AS time_period,
                        TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                    GROUP BY `int_nulls`, `string_nulls`, time_period, time_period_utc
                ) grouping_table
                GROUP BY time_period, time_period_utc
                ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName()
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenProfilingOneTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("date");
        }});

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value,
                    time_period,
                    time_period_utc
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records,
                        analyzed_table.`date` AS time_period,
                        TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (%s)
                          AND (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                    GROUP BY `int_nulls`, `string_nulls`, time_period, time_period_utc
                ) grouping_table
                GROUP BY time_period, time_period_utc
                ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenProfilingOneTimeSeriesNoDataStreamAndOneAdditionalFilter_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("date");
        }});
        runParameters.getAdditionalFilters().add("{alias}.col2=5");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value,
                    time_period,
                    time_period_utc
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records,
                        analyzed_table.`date` AS time_period,
                        TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (%s)
                          AND (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                          AND (analyzed_table.col2=5)
                    GROUP BY `int_nulls`, `string_nulls`, time_period, time_period_utc
                ) grouping_table
                GROUP BY time_period, time_period_utc
                ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenProfilingOneTimeSeriesNoDataStreamAndTwoAdditionalFilters_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("date");
        }});
        runParameters.getAdditionalFilters().add("{alias}.col2=5");
        runParameters.getAdditionalFilters().add("{alias}.col3='x'");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value,
                    time_period,
                    time_period_utc
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records,
                        analyzed_table.`date` AS time_period,
                        TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (%s)
                          AND (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                          AND (analyzed_table.col2=5)
                          AND (analyzed_table.col3='x')
                    GROUP BY `int_nulls`, `string_nulls`, time_period, time_period_utc
                ) grouping_table
                GROUP BY time_period, time_period_utc
                ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }


    @Test
    void renderSensor_whenMonitoringDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (%s)
                          AND (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                    GROUP BY `int_nulls`, `string_nulls`
                ) grouping_table""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value,
                    time_period,
                    time_period_utc
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records,
                        analyzed_table.`date` AS time_period,
                        TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (%s)
                          AND (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                          AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                          AND analyzed_table.`date` < CURRENT_DATE()
                    GROUP BY `int_nulls`, `string_nulls`, time_period, time_period_utc
                ) grouping_table
                GROUP BY time_period, time_period_utc
                ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }


    @Test
    void renderSensor_whenProfilingNoTimeSeriesOneDataStream_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value,
                    grouping_table.grouping_level_1
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records,
                        analyzed_table.`strings_with_numbers` AS grouping_level_1
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (%s)
                          AND (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                    GROUP BY `int_nulls`, `string_nulls`, grouping_level_1
                ) grouping_table
                GROUP BY grouping_level_1
                ORDER BY grouping_level_1""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenMonitoringDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                    DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value,
                    grouping_table.grouping_level_1
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records,
                        analyzed_table.`strings_with_numbers` AS grouping_level_1
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (%s)
                          AND (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                    GROUP BY `int_nulls`, `string_nulls`, grouping_level_1
                ) grouping_table
                GROUP BY grouping_level_1
                ORDER BY grouping_level_1""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDailyDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value,
                    grouping_table.grouping_level_1,
                    time_period,
                    time_period_utc
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records,
                        analyzed_table.`strings_with_numbers` AS grouping_level_1,
                        analyzed_table.`date` AS time_period,
                        TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (%s)
                          AND (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                          AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                          AND analyzed_table.`date` < CURRENT_DATE()
                    GROUP BY `int_nulls`, `string_nulls`, grouping_level_1, time_period, time_period_utc
                ) grouping_table
                GROUP BY grouping_level_1, time_period, time_period_utc
                ORDER BY grouping_level_1, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedMonthlyDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.monthly, "date");
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value,
                    grouping_table.grouping_level_1,
                    time_period,
                    time_period_utc
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records,
                        analyzed_table.`strings_with_numbers` AS grouping_level_1,
                        DATE_TRUNC(CAST(analyzed_table.`date` AS DATE), MONTH) AS time_period,
                        TIMESTAMP(DATE_TRUNC(CAST(analyzed_table.`date` AS DATE), MONTH)) AS time_period_utc
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (%s)
                          AND (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                          AND analyzed_table.`date` >= DATE_ADD(DATE_TRUNC(CAST(CURRENT_DATE() AS DATE), MONTH), INTERVAL -120 MONTH)
                          AND analyzed_table.`date` < DATE_TRUNC(CAST(CURRENT_DATE() AS DATE), MONTH)
                    GROUP BY `int_nulls`, `string_nulls`, grouping_level_1, time_period, time_period_utc
                ) grouping_table
                GROUP BY grouping_level_1, time_period, time_period_utc
                ORDER BY grouping_level_1, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenProfilingOneTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("date");
        }});
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_string")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value,
                    grouping_table.grouping_level_1,
                    grouping_table.grouping_level_2,
                    grouping_table.grouping_level_3,
                    time_period,
                    time_period_utc
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records,
                        analyzed_table.`strings_with_numbers` AS grouping_level_1,
                        analyzed_table.`mix_of_values` AS grouping_level_2,
                        analyzed_table.`length_string` AS grouping_level_3,
                        analyzed_table.`date` AS time_period,
                        TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (%s)
                          AND (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                    GROUP BY `int_nulls`, `string_nulls`, grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
                ) grouping_table
                GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
                ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenMonitoringDefaultTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_string")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value,
                    grouping_table.grouping_level_1,
                    grouping_table.grouping_level_2,
                    grouping_table.grouping_level_3
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records,
                        analyzed_table.`strings_with_numbers` AS grouping_level_1,
                        analyzed_table.`mix_of_values` AS grouping_level_2,
                        analyzed_table.`length_string` AS grouping_level_3
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (%s)
                          AND (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                    GROUP BY `int_nulls`, `string_nulls`, grouping_level_1, grouping_level_2, grouping_level_3
                ) grouping_table
                GROUP BY grouping_level_1, grouping_level_2, grouping_level_3
                ORDER BY grouping_level_1, grouping_level_2, grouping_level_3""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDefaultTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        this.sut.setColumns(List.of("int_nulls", "string_nulls"));
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_string")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE WHEN SUM(distinct_records) IS NULL THEN 0
                        ELSE 1 - SUM(distinct_records) / SUM(records_number) END
                        AS actual_value,
                    grouping_table.grouping_level_1,
                    grouping_table.grouping_level_2,
                    grouping_table.grouping_level_3,
                    time_period,
                    time_period_utc
                FROM (
                    SELECT COUNT(*) AS records_number,
                        COUNT(*) OVER (PARTITION BY `int_nulls`, `string_nulls`) AS distinct_records,
                        analyzed_table.`strings_with_numbers` AS grouping_level_1,
                        analyzed_table.`mix_of_values` AS grouping_level_2,
                        analyzed_table.`length_string` AS grouping_level_3,
                        analyzed_table.`date` AS time_period,
                        TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                    FROM `%s`.`%s`.`%s` AS analyzed_table
                    WHERE (%s)
                          AND (COALESCE(CAST(`int_nulls` AS STRING), CAST(`string_nulls` AS STRING)) IS NOT NULL)
                          AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                          AND analyzed_table.`date` < CURRENT_DATE()
                    GROUP BY `int_nulls`, `string_nulls`, grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
                ) grouping_table
                GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
                ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }
}
