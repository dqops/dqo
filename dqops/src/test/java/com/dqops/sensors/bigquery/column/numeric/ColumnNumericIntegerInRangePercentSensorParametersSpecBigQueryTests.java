/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors.bigquery.column.numeric;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.numeric.ColumnIntegerInRangePercentCheckSpec;
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
import com.dqops.sensors.column.numeric.ColumnNumericIntegerInRangePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnNumericIntegerInRangePercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnNumericIntegerInRangePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnIntegerInRangePercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
		this.sut = new ColumnNumericIntegerInRangePercentSensorParametersSpec();
        this.sut.setFilter("{alias}.`correct` = 1");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.nulls_and_uniqueness, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnIntegerInRangePercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersProfiling() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "negative", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersMonitoring(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(this.sampleTableMetadata, "negative", this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunParametersPartitioned(CheckTimeScale timeScale, String timeSeriesColumn) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(this.sampleTableMetadata, "negative", this.checkSpec, timeScale, timeSeriesColumn);
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
        Assertions.assertEquals("column/numeric/integer_in_range_percent", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %1$s >= 29 AND %1$s <= 30 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE (%5$s)""";

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
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("date");
        }});

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %1$s >= 29 AND %1$s <= 30 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE (%5$s)
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
    void renderSensor_whenMonitoringDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %1$s >= 29 AND %1$s <= 30 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE (%5$s)""";

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
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %1$s >= 29 AND %1$s <= 30 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE (%5$s)
                  AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                  AND analyzed_table.`date` < CURRENT_DATE()
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
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %1$s >= 29 AND %1$s <= 30 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`date` AS grouping_level_1
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE (%5$s)
            GROUP BY grouping_level_1
            ORDER BY grouping_level_1""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenMonitoringDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("nulls_ok")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %1$s >= 29 AND %1$s <= 30 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`nulls_ok` AS grouping_level_1
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE (%5$s)
            GROUP BY grouping_level_1
            ORDER BY grouping_level_1""";

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
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("nulls_ok")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %1$s >= 29 AND %1$s <= 30 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`nulls_ok` AS grouping_level_1,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE (%5$s)
                  AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                  AND analyzed_table.`date` < CURRENT_DATE()
            GROUP BY grouping_level_1, time_period, time_period_utc
            ORDER BY grouping_level_1, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }


    @Test
    void renderSensor_whenProfilingOneTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

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
                        DataStreamLevelSpecObjectMother.createColumnMapping("nulls_ok")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %1$s >= 29 AND %1$s <= 30 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`strings_with_numbers` AS grouping_level_1,
                analyzed_table.`mix_of_values` AS grouping_level_2,
                analyzed_table.`nulls_ok` AS grouping_level_3,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE (%5$s)
            GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenMonitoringDefaultTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("nulls_ok")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %1$s >= 29 AND %1$s <= 30 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`strings_with_numbers` AS grouping_level_1,
                analyzed_table.`mix_of_values` AS grouping_level_2,
                analyzed_table.`nulls_ok` AS grouping_level_3
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE (%5$s)
            GROUP BY grouping_level_1, grouping_level_2, grouping_level_3
            ORDER BY grouping_level_1, grouping_level_2, grouping_level_3""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDefaultTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("nulls_ok")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN %1$s >= 29 AND %1$s <= 30 THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`strings_with_numbers` AS grouping_level_1,
                analyzed_table.`mix_of_values` AS grouping_level_2,
                analyzed_table.`nulls_ok` AS grouping_level_3,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE (%5$s)
                  AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                  AND analyzed_table.`date` < CURRENT_DATE()
            GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenErrorSamplingForProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderErrorSamplingTemplate(runParameters);
        String target_query = """
            SELECT
                %1$s as actual_value
            FROM
                `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE (%5$s)
                  AND (%1$s IS NOT NULL AND NOT (%1$s >= 29 AND %1$s <= 30))
            ORDER BY analyzed_table.`negative` ASC
            LIMIT 50""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenErrorSamplingForProfilingNoTimeSeriesButDataGroupingConfigured_thenRendersCorrectSql() {
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("nulls_ok")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderErrorSamplingTemplate(runParameters);
        String target_query = """
            WITH error_samples AS (
                SELECT
                    %1$s AS sample_value,
                    analyzed_table.`strings_with_numbers` AS grouping_level_1,
                    analyzed_table.`mix_of_values` AS grouping_level_2,
                    analyzed_table.`nulls_ok` AS grouping_level_3,
                    ROW_NUMBER() OVER (
                        PARTITION BY
                            analyzed_table.`strings_with_numbers`,
                            analyzed_table.`mix_of_values`,
                            analyzed_table.`nulls_ok`
                        ORDER BY
                            analyzed_table.`negative` ASC
                    ) AS sample_index
                FROM
                    `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE (%5$s)
                      AND (%1$s IS NOT NULL AND NOT (%1$s >= 29 AND %1$s <= 30))
            )
            SELECT
                sample_table.sample_value AS actual_value,
                sample_table.sample_index AS sample_index,
                sample_table.grouping_level_1 AS grouping_level_1,
                sample_table.grouping_level_2 AS grouping_level_2,
                sample_table.grouping_level_3 AS grouping_level_3
            FROM error_samples AS sample_table
            WHERE sample_table.sample_index <= 50
            LIMIT 1000""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenErrorSamplingForProfilingNoTimeSeriesNoDataStreamButIdColumnPresent_thenRendersCorrectSql() {
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);
        runParameters.getTable().getColumns().get("id").setId(true);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderErrorSamplingTemplate(runParameters);
        String target_query = """
            SELECT
                %1$s as actual_value,
                analyzed_table.`id` AS row_id_1
            FROM
                `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE (%5$s)
                  AND (%1$s IS NOT NULL AND NOT (%1$s >= 29 AND %1$s <= 30))
            ORDER BY analyzed_table.`negative` ASC
            LIMIT 50""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenErrorSamplingForProfilingNoTimeSeriesNoDataStreamButTwoIdColumnPresent_thenRendersCorrectSql() {
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);
        runParameters.getTable().getColumns().get("id").setId(true);
        runParameters.getTable().getColumns().get("nulls").setId(true);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderErrorSamplingTemplate(runParameters);
        String target_query = """
            SELECT
                %1$s as actual_value,
                analyzed_table.`id` AS row_id_1,
                analyzed_table.`nulls` AS row_id_2
            FROM
                `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE (%5$s)
                  AND (%1$s IS NOT NULL AND NOT (%1$s >= 29 AND %1$s <= 30))
            ORDER BY analyzed_table.`negative` ASC
            LIMIT 50""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenErrorSamplingForProfilingNoTimeSeriesButDataGroupingConfiguredAndTwoIdColumns_thenRendersCorrectSql() {
        this.sut.setMinValue(29L);
        this.sut.setMaxValue(30L);

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);
        runParameters.getTable().getColumns().get("id").setId(true);
        runParameters.getTable().getColumns().get("nulls").setId(true);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("nulls_ok")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderErrorSamplingTemplate(runParameters);
        String target_query = """
            WITH error_samples AS (
                SELECT
                    %1$s AS sample_value,
                    analyzed_table.`strings_with_numbers` AS grouping_level_1,
                    analyzed_table.`mix_of_values` AS grouping_level_2,
                    analyzed_table.`nulls_ok` AS grouping_level_3,
                    analyzed_table.`id` AS row_id_1,
                    analyzed_table.`nulls` AS row_id_2,
                    ROW_NUMBER() OVER (
                        PARTITION BY
                            analyzed_table.`strings_with_numbers`,
                            analyzed_table.`mix_of_values`,
                            analyzed_table.`nulls_ok`
                        ORDER BY
                            analyzed_table.`negative` ASC, analyzed_table.`id` ASC, analyzed_table.`nulls` ASC
                    ) AS sample_index
                FROM
                    `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE (%5$s)
                      AND (%1$s IS NOT NULL AND NOT (%1$s >= 29 AND %1$s <= 30))
            )
            SELECT
                sample_table.sample_value AS actual_value,
                sample_table.sample_index AS sample_index,
                sample_table.grouping_level_1 AS grouping_level_1,
                sample_table.grouping_level_2 AS grouping_level_2,
                sample_table.grouping_level_3 AS grouping_level_3,
                sample_table.row_id_1 AS row_id_1,
                sample_table.row_id_2 AS row_id_2
            FROM error_samples AS sample_table
            WHERE sample_table.sample_index <= 50
            LIMIT 1000""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

}
