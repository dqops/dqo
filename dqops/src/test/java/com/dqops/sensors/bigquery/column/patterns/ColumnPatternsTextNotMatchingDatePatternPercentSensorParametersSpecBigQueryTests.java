/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors.bigquery.column.patterns;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.patterns.ColumnTextNotMatchingDatePatternPercentCheckSpec;
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
import com.dqops.sensors.column.patterns.ColumnPatternsTextNotMatchingDatePatternPercentSensorParametersSpec;
import com.dqops.sensors.column.text.TextBuiltInDateFormats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnPatternsTextNotMatchingDatePatternPercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnPatternsTextNotMatchingDatePatternPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnTextNotMatchingDatePatternPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;



    @BeforeEach
    void setUp() {
		this.sut = new ColumnPatternsTextNotMatchingDatePatternPercentSensorParametersSpec();
        this.sut.setFilter("{alias}.`correct` = 1");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnTextNotMatchingDatePatternPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersProfiling() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "date_iso", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersProfilingWithMonthDayYearDateType() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "date_mdy", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersProfilingWithDayMonthYearDateType() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "date_dmy", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersProfilingWithYearMonthDayDateType() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "date_ymd", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersProfilingWithMonthNameDayYearDateType() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "date_bdy", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersMonitoring(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(this.sampleTableMetadata, "date_iso", this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunParametersPartitioned(CheckTimeScale timeScale, String timeSeriesColumn) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(this.sampleTableMetadata, "date_iso", this.checkSpec, timeScale, timeSeriesColumn);
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
        Assertions.assertEquals("column/patterns/text_not_matching_date_patterns_percent", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensorWithDateType_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);


        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`date_iso` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%Y-%m-%d'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensorWithMonthDayYearDateType_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.MonthDayYear);
        SensorExecutionRunParameters runParameters = this.getRunParametersProfilingWithMonthDayYearDateType();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`date_mdy` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%m/%d/%Y'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensorWithDayMonthYearDateType_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.DayMonthYear);
        SensorExecutionRunParameters runParameters = this.getRunParametersProfilingWithDayMonthYearDateType();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`date_dmy` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%d/%m/%Y'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensorWithYearMonthDayDateType_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.YearMonthDay);
        SensorExecutionRunParameters runParameters = this.getRunParametersProfilingWithYearMonthDayDateType();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`date_ymd` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%Y/%m/%d'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensorWithMonthNameDayYearDateType_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.MonthNameDayYear);
        SensorExecutionRunParameters runParameters = this.getRunParametersProfilingWithMonthNameDayYearDateType();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`date_bdy` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%b %d, %Y'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenProfilingOneTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.ISO8601);
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
                            WHEN analyzed_table.`date_iso` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%Y-%m-%d'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenMonitoringDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`date_iso` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%Y-%m-%d'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`date_iso` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)
                  AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                  AND analyzed_table.`date` < CURRENT_DATE()
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%Y-%m-%d'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }


    @Test
    void renderSensor_whenProfilingNoTimeSeriesOneDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`date_iso` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`length_int` AS grouping_level_1
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)
            GROUP BY grouping_level_1
            ORDER BY grouping_level_1""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%Y-%m-%d'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenMonitoringDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`date_iso` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`length_int` AS grouping_level_1
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)
            GROUP BY grouping_level_1
            ORDER BY grouping_level_1""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%Y-%m-%d'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`date_iso` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`length_int` AS grouping_level_1,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)
                  AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                  AND analyzed_table.`date` < CURRENT_DATE()
            GROUP BY grouping_level_1, time_period, time_period_utc
            ORDER BY grouping_level_1, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%Y-%m-%d'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }


    @Test
    void renderSensor_whenProfilingOneTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.ISO8601);
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
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`date_iso` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`strings_with_numbers` AS grouping_level_1,
                analyzed_table.`mix_of_values` AS grouping_level_2,
                analyzed_table.`length_int` AS grouping_level_3,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)
            GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%Y-%m-%d'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenMonitoringDefaultTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`date_iso` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`strings_with_numbers` AS grouping_level_1,
                analyzed_table.`mix_of_values` AS grouping_level_2,
                analyzed_table.`length_int` AS grouping_level_3
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)
            GROUP BY grouping_level_1, grouping_level_2, grouping_level_3
            ORDER BY grouping_level_1, grouping_level_2, grouping_level_3""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%Y-%m-%d'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDefaultTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormat(TextBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN analyzed_table.`date_iso` IS NOT NULL
                                    AND SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`strings_with_numbers` AS grouping_level_1,
                analyzed_table.`mix_of_values` AS grouping_level_2,
                analyzed_table.`length_int` AS grouping_level_3,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)
                  AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                  AND analyzed_table.`date` < CURRENT_DATE()
            GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                "'%Y-%m-%d'",
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }
}
