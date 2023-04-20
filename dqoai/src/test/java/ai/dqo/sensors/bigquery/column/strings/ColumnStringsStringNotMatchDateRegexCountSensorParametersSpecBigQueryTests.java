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
package ai.dqo.sensors.bigquery.column.strings;

import ai.dqo.BaseTest;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.column.checkspecs.strings.ColumnStringNotMatchDateRegexCountCheckSpec;
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
import ai.dqo.sensors.column.strings.ColumnStringsStringNotMatchDateRegexCountSensorParametersSpec;
import ai.dqo.sensors.column.strings.StringsBuiltInDateFormats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnStringsStringNotMatchDateRegexCountSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnStringsStringNotMatchDateRegexCountSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnStringNotMatchDateRegexCountCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;



    @BeforeEach
    void setUp() {
		this.sut = new ColumnStringsStringNotMatchDateRegexCountSensorParametersSpec();
        this.sut.setFilter("{alias}.`correct` = 1");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnStringNotMatchDateRegexCountCheckSpec();
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

    private SensorExecutionRunParameters getRunParametersRecurring(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForRecurringCheck(this.sampleTableMetadata, "date_iso", this.checkSpec, timeScale);
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
        Assertions.assertEquals("column/strings/string_not_match_date_regex_count", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensorWithDateType_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormats(StringsBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s""";

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
        this.sut.setDateFormats(StringsBuiltInDateFormats.MonthDayYear);
        SensorExecutionRunParameters runParameters = this.getRunParametersProfilingWithMonthDayYearDateType();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s""";

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
        this.sut.setDateFormats(StringsBuiltInDateFormats.DayMonthYear);
        SensorExecutionRunParameters runParameters = this.getRunParametersProfilingWithDayMonthYearDateType();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s""";

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
        this.sut.setDateFormats(StringsBuiltInDateFormats.YearMonthDay);
        SensorExecutionRunParameters runParameters = this.getRunParametersProfilingWithYearMonthDayDateType();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s""";

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
        this.sut.setDateFormats(StringsBuiltInDateFormats.MonthNameDayYear);
        SensorExecutionRunParameters runParameters = this.getRunParametersProfilingWithMonthNameDayYearDateType();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s""";

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
        this.sut.setDateFormats(StringsBuiltInDateFormats.ISO8601);
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
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
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
    void renderSensor_whenRecurringDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormats(StringsBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring(CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
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
    void renderSensor_whenPartitionedDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormats(StringsBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
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
        this.sut.setDateFormats(StringsBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value,
                analyzed_table.`length_int` AS stream_level_1
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
            GROUP BY stream_level_1
            ORDER BY stream_level_1""";

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
    void renderSensor_whenRecurringDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormats(StringsBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring(CheckTimeScale.monthly);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value,
                analyzed_table.`length_int` AS stream_level_1,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
            GROUP BY stream_level_1, time_period, time_period_utc
            ORDER BY stream_level_1, time_period, time_period_utc""";

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
        this.sut.setDateFormats(StringsBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value,
                analyzed_table.`length_int` AS stream_level_1,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
                  AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                  AND analyzed_table.`date` < CURRENT_DATE()
            GROUP BY stream_level_1, time_period, time_period_utc
            ORDER BY stream_level_1, time_period, time_period_utc""";

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
        this.sut.setDateFormats(StringsBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("date");
        }});
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value,
                analyzed_table.`strings_with_numbers` AS stream_level_1,
                analyzed_table.`mix_of_values` AS stream_level_2,
                analyzed_table.`length_int` AS stream_level_3,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
            GROUP BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc
            ORDER BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc""";

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
    void renderSensor_whenRecurringDefaultTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        this.sut.setDateFormats(StringsBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring(CheckTimeScale.monthly);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value,
                analyzed_table.`strings_with_numbers` AS stream_level_1,
                analyzed_table.`mix_of_values` AS stream_level_2,
                analyzed_table.`length_int` AS stream_level_3,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
            GROUP BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc
            ORDER BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc""";

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
        this.sut.setDateFormats(StringsBuiltInDateFormats.ISO8601);
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE SUM(
                        CASE
                            WHEN SAFE.PARSE_DATE(%2$s, %1$s) IS NULL
                                THEN 1
                            ELSE 0
                        END
                    )
                END AS actual_value,
                analyzed_table.`strings_with_numbers` AS stream_level_1,
                analyzed_table.`mix_of_values` AS stream_level_2,
                analyzed_table.`length_int` AS stream_level_3,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
                  AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                  AND analyzed_table.`date` < CURRENT_DATE()
            GROUP BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc
            ORDER BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc""";

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
