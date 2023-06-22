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
package ai.dqo.sensors.bigquery.column.strings;

import ai.dqo.BaseTest;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.column.checkspecs.strings.ColumnStringLengthInRangePercentCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.execution.sqltemplates.rendering.JinjaTemplateRenderServiceObjectMother;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapper;
import ai.dqo.metadata.definitions.sensors.SensorDefinitionWrapperObjectMother;
import ai.dqo.metadata.groupings.*;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.metadata.timeseries.TimePeriodGradient;
import ai.dqo.metadata.timeseries.TimeSeriesConfigurationSpec;
import ai.dqo.metadata.timeseries.TimeSeriesMode;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.sensors.column.strings.ColumnStringsStringLengthInRangePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnStringsStringLengthInRangePercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnStringsStringLengthInRangePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnStringLengthInRangePercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnStringsStringLengthInRangePercentSensorParametersSpec();
        this.sut.setFilter("{alias}.`correct` = 1");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnStringLengthInRangePercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersProfiling() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "length_string", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersRecurring(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForRecurringCheck(this.sampleTableMetadata, "length_string", this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunParametersPartitioned(CheckTimeScale timeScale, String timeSeriesColumn) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(this.sampleTableMetadata, "length_string", this.checkSpec, timeScale, timeSeriesColumn);
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
        Assertions.assertEquals("column/strings/string_length_in_range_percent", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE
                        100.0 * SUM(
                            CASE
                                WHEN LENGTH( %1$s ) BETWEEN 0 AND 0 THEN 1
                                ELSE 0
                            END
                    ) / COUNT(%1$s)
                END AS actual_value
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s""";

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
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("date");
        }});

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE
                        100.0 * SUM(
                            CASE
                                WHEN LENGTH( %1$s ) BETWEEN 0 AND 0 THEN 1
                                ELSE 0
                            END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
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
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE
                        100.0 * SUM(
                            CASE
                                WHEN LENGTH( %1$s ) BETWEEN 0 AND 0 THEN 1
                                ELSE 0
                            END
                    ) / COUNT(%1$s)
                END AS actual_value,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
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
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE
                        100.0 * SUM(
                            CASE
                                WHEN LENGTH( %1$s ) BETWEEN 0 AND 0 THEN 1
                                ELSE 0
                            END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
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
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE
                        100.0 * SUM(
                            CASE
                                WHEN LENGTH( %1$s ) BETWEEN 0 AND 0 THEN 1
                                ELSE 0
                            END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`length_int` AS grouping_level_1
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
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
    void renderSensor_whenRecurringDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring(CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE
                        100.0 * SUM(
                            CASE
                                WHEN LENGTH( %1$s ) BETWEEN 0 AND 0 THEN 1
                                ELSE 0
                            END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`length_int` AS grouping_level_1,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
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
    void renderSensor_whenPartitionedDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE
                        100.0 * SUM(
                            CASE
                                WHEN LENGTH( %1$s ) BETWEEN 0 AND 0 THEN 1
                                ELSE 0
                            END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`length_int` AS grouping_level_1,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
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
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE
                        100.0 * SUM(
                            CASE
                                WHEN LENGTH( %1$s ) BETWEEN 0 AND 0 THEN 1
                                ELSE 0
                            END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`strings_with_numbers` AS grouping_level_1,
                analyzed_table.`mix_of_values` AS grouping_level_2,
                analyzed_table.`length_int` AS grouping_level_3,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
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
    void renderSensor_whenRecurringDefaultTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring(CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE
                        100.0 * SUM(
                            CASE
                                WHEN LENGTH( %1$s ) BETWEEN 0 AND 0 THEN 1
                                ELSE 0
                            END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`strings_with_numbers` AS grouping_level_1,
                analyzed_table.`mix_of_values` AS grouping_level_2,
                analyzed_table.`length_int` AS grouping_level_3,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
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
    void renderSensor_whenPartitionedDefaultTimeSeriesThreeDataStream_thenRendersCorrectSql() {
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
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE
                        100.0 * SUM(
                            CASE
                                WHEN LENGTH( %1$s ) BETWEEN 0 AND 0 THEN 1
                                ELSE 0
                            END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`strings_with_numbers` AS grouping_level_1,
                analyzed_table.`mix_of_values` AS grouping_level_2,
                analyzed_table.`length_int` AS grouping_level_3,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
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
}
