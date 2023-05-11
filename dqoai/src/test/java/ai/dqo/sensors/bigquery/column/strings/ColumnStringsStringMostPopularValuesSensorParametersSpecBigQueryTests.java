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
import ai.dqo.checks.column.checkspecs.strings.ColumnStringMostPopularValuesCheckSpec;
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
import ai.dqo.sensors.column.strings.ColumnStringsStringMostPopularValuesSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ColumnStringsStringMostPopularValuesSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnStringsStringMostPopularValuesSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnStringMostPopularValuesCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
		this.sut = new ColumnStringsStringMostPopularValuesSensorParametersSpec();
        this.sut.setFilter("{table}.`correct` = 1");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnStringMostPopularValuesCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersProfiling() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "strings_with_numbers", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersRecurring(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForRecurringCheck(this.sampleTableMetadata, "strings_with_numbers", this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunParametersPartitioned(CheckTimeScale timeScale, String timeSeriesColumn) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(this.sampleTableMetadata, "strings_with_numbers", this.checkSpec, timeScale, timeSeriesColumn);
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
        Assertions.assertEquals("column/strings/string_most_popular_values", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTopValues(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                SUM(
                    CASE
                        WHEN top_values IN ('a111a', 'd44d') THEN 1
                        ELSE 0
                    END
                ) AS actual_value,
                time_period
            FROM(
                SELECT
                    top_col_values.top_values as top_values,
                    top_col_values.time_period as time_period, time_period_utc,
                    RANK() OVER(partition by top_col_values.time_period
                    ORDER BY top_col_values.total_values) as top_values_rank
                FROM (
                    SELECT
                    %1$s AS top_values,
                    COUNT(*) AS total_values
                    FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s, top_values, total_values
                ) top_col_values
            )
            WHERE top_values_rank <= 2""";

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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTopValues(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("date");
        }});

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    SUM(
                        CASE
                            WHEN top_values IN ('a111a', 'd44d') THEN 1
                            ELSE 0
                        END
                    ) AS actual_value,
                    time_period
                FROM(
                    SELECT
                        top_col_values.top_values as top_values,
                        top_col_values.time_period as time_period, time_period_utc,
                        RANK() OVER(partition by top_col_values.time_period
                        ORDER BY top_col_values.total_values) as top_values_rank
                    FROM (
                        SELECT
                        %1$s AS top_values,
                        COUNT(*) AS total_values,
                    analyzed_table.`date` AS time_period,
                    TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                        FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE %5$s
                GROUP BY time_period, time_period_utc, top_values
                ORDER BY time_period, time_period_utc, total_values
                    ) top_col_values
                )
                WHERE top_values_rank <= 2
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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTopValues(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring(CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    SUM(
                        CASE
                            WHEN top_values IN ('a111a', 'd44d') THEN 1
                            ELSE 0
                        END
                    ) AS actual_value,
                    time_period
                FROM(
                    SELECT
                        top_col_values.top_values as top_values,
                        top_col_values.time_period as time_period, time_period_utc,
                        RANK() OVER(partition by top_col_values.time_period
                        ORDER BY top_col_values.total_values) as top_values_rank
                    FROM (
                        SELECT
                        %1$s AS top_values,
                        COUNT(*) AS total_values,
                    DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                        FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE %5$s
                GROUP BY time_period, time_period_utc, top_values
                ORDER BY time_period, time_period_utc, total_values
                    ) top_col_values
                )
                WHERE top_values_rank <= 2
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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTopValues(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    SUM(
                        CASE
                            WHEN top_values IN ('a111a', 'd44d') THEN 1
                            ELSE 0
                        END
                    ) AS actual_value,
                    time_period
                FROM(
                    SELECT
                        top_col_values.top_values as top_values,
                        top_col_values.time_period as time_period, time_period_utc,
                        RANK() OVER(partition by top_col_values.time_period
                        ORDER BY top_col_values.total_values) as top_values_rank
                    FROM (
                        SELECT
                        %1$s AS top_values,
                        COUNT(*) AS total_values,
                    analyzed_table.`date` AS time_period,
                    TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                        FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE %5$s
                      AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                      AND analyzed_table.`date` < CURRENT_DATE()
                GROUP BY time_period, time_period_utc, top_values
                ORDER BY time_period, time_period_utc, total_values
                    ) top_col_values
                )
                WHERE top_values_rank <= 2
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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTopValues(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    SUM(
                        CASE
                            WHEN top_values IN ('a111a', 'd44d') THEN 1
                            ELSE 0
                        END
                    ) AS actual_value,
                    time_period
                FROM(
                    SELECT
                        top_col_values.top_values as top_values,
                        top_col_values.time_period as time_period, time_period_utc,
                        RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1
                        ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1
                    FROM (
                        SELECT
                        %1$s AS top_values,
                        COUNT(*) AS total_values,
                    analyzed_table.`result` AS stream_level_1
                        FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE %5$s
                GROUP BY stream_level_1, top_values
                ORDER BY stream_level_1, total_values
                    ) top_col_values
                )
                WHERE top_values_rank <= 2
                GROUP BY stream_level_1
                ORDER BY stream_level_1""";

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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTopValues(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring(CheckTimeScale.monthly);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    SUM(
                        CASE
                            WHEN top_values IN ('a111a', 'd44d') THEN 1
                            ELSE 0
                        END
                    ) AS actual_value,
                    time_period
                FROM(
                    SELECT
                        top_col_values.top_values as top_values,
                        top_col_values.time_period as time_period, time_period_utc,
                        RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1
                        ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1
                    FROM (
                        SELECT
                        %1$s AS top_values,
                        COUNT(*) AS total_values,
                    analyzed_table.`result` AS stream_level_1,
                    DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                        FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE %5$s
                GROUP BY stream_level_1, time_period, time_period_utc, top_values
                ORDER BY stream_level_1, time_period, time_period_utc, total_values
                    ) top_col_values
                )
                WHERE top_values_rank <= 2
                GROUP BY stream_level_1, time_period, time_period_utc
                ORDER BY stream_level_1, time_period, time_period_utc""";

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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTopValues(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    SUM(
                        CASE
                            WHEN top_values IN ('a111a', 'd44d') THEN 1
                            ELSE 0
                        END
                    ) AS actual_value,
                    time_period
                FROM(
                    SELECT
                        top_col_values.top_values as top_values,
                        top_col_values.time_period as time_period, time_period_utc,
                        RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1
                        ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1
                    FROM (
                        SELECT
                        %1$s AS top_values,
                        COUNT(*) AS total_values,
                    analyzed_table.`result` AS stream_level_1,
                    analyzed_table.`date` AS time_period,
                    TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                        FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE %5$s
                      AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                      AND analyzed_table.`date` < CURRENT_DATE()
                GROUP BY stream_level_1, time_period, time_period_utc, top_values
                ORDER BY stream_level_1, time_period, time_period_utc, total_values
                    ) top_col_values
                )
                WHERE top_values_rank <= 2
                GROUP BY stream_level_1, time_period, time_period_utc
                ORDER BY stream_level_1, time_period, time_period_utc""";

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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTopValues(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("date");
        }});
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        String target_query = """
                SELECT
                    SUM(
                        CASE
                            WHEN top_values IN ('a111a', 'd44d') THEN 1
                            ELSE 0
                        END
                    ) AS actual_value,
                    time_period
                FROM(
                    SELECT
                        top_col_values.top_values as top_values,
                        top_col_values.time_period as time_period, time_period_utc,
                        RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2, top_col_values.stream_level_3
                        ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2, top_col_values.stream_level_3
                    FROM (
                        SELECT
                        %1$s AS top_values,
                        COUNT(*) AS total_values,
                    analyzed_table.`result` AS stream_level_1,
                    analyzed_table.`result` AS stream_level_2,
                    analyzed_table.`result` AS stream_level_3,
                    analyzed_table.`date` AS time_period,
                    TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                        FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE %5$s
                GROUP BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc, top_values
                ORDER BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc, total_values
                    ) top_col_values
                )
                WHERE top_values_rank <= 2
                GROUP BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc
                ORDER BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc""";

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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTopValues(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring(CheckTimeScale.monthly);
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    SUM(
                        CASE
                            WHEN top_values IN ('a111a', 'd44d') THEN 1
                            ELSE 0
                        END
                    ) AS actual_value,
                    time_period
                FROM(
                    SELECT
                        top_col_values.top_values as top_values,
                        top_col_values.time_period as time_period, time_period_utc,
                        RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2, top_col_values.stream_level_3
                        ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2, top_col_values.stream_level_3
                    FROM (
                        SELECT
                        %1$s AS top_values,
                        COUNT(*) AS total_values,
                    analyzed_table.`result` AS stream_level_1,
                    analyzed_table.`result` AS stream_level_2,
                    analyzed_table.`result` AS stream_level_3,
                    DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                    TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                        FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE %5$s
                GROUP BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc, top_values
                ORDER BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc, total_values
                    ) top_col_values
                )
                WHERE top_values_rank <= 2
                GROUP BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc
                ORDER BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc""";

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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTopValues(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataStreams(
                DataStreamMappingSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    SUM(
                        CASE
                            WHEN top_values IN ('a111a', 'd44d') THEN 1
                            ELSE 0
                        END
                    ) AS actual_value,
                    time_period
                FROM(
                    SELECT
                        top_col_values.top_values as top_values,
                        top_col_values.time_period as time_period, time_period_utc,
                        RANK() OVER(partition by top_col_values.time_period, top_col_values.stream_level_1, top_col_values.stream_level_2, top_col_values.stream_level_3
                        ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.stream_level_1, top_col_values.stream_level_2, top_col_values.stream_level_3
                    FROM (
                        SELECT
                        %1$s AS top_values,
                        COUNT(*) AS total_values,
                    analyzed_table.`result` AS stream_level_1,
                    analyzed_table.`result` AS stream_level_2,
                    analyzed_table.`result` AS stream_level_3,
                    analyzed_table.`date` AS time_period,
                    TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                        FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE %5$s
                      AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                      AND analyzed_table.`date` < CURRENT_DATE()
                GROUP BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc, top_values
                ORDER BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc, total_values
                    ) top_col_values
                )
                WHERE top_values_rank <= 2
                GROUP BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc
                ORDER BY stream_level_1, stream_level_2, stream_level_3, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }
}
