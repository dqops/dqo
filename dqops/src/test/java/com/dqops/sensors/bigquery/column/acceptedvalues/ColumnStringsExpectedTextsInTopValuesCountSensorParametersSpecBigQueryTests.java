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
package com.dqops.sensors.bigquery.column.acceptedvalues;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.acceptedvalues.ColumnExpectedTextsInTopValuesCountCheckSpec;
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
import com.dqops.sensors.column.acceptedvalues.ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnExpectedTextsInTopValuesCountCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
		this.sut = new ColumnStringsExpectedTextsInTopValuesCountSensorParametersSpec();
        this.sut.setFilter("{table}.`correct` = 1");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnExpectedTextsInTopValuesCountCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersProfiling() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "strings_with_numbers", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersMonitoring(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(this.sampleTableMetadata, "strings_with_numbers", this.checkSpec, timeScale);
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
        Assertions.assertEquals("column/accepted_values/expected_texts_in_top_values_count", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    COUNT(DISTINCT
                        CASE
                            WHEN top_values.top_value IN ('a111a', 'd44d') THEN top_values.top_value
                            ELSE NULL
                        END
                    ) AS actual_value,
                    MAX(2) AS expected_value,
                    top_values.time_period,
                    top_values.time_period_utc
                FROM
                (
                    SELECT
                        top_col_values.top_value as top_value,
                        top_col_values.time_period as time_period,
                        top_col_values.time_period_utc as time_period_utc,
                        RANK() OVER(PARTITION BY top_col_values.time_period
                            ORDER BY top_col_values.total_values) as top_values_rank
                    FROM
                    (
                        SELECT
                            %1$s AS top_value,
                            COUNT(*) AS total_values
                        FROM
                            `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                        WHERE id < 5, top_value, total_values
                    ) AS top_col_values
                ) AS top_values
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
        this.sut.setTop(2L);
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
                    COUNT(DISTINCT
                        CASE
                            WHEN top_values.top_value IN ('a111a', 'd44d') THEN top_values.top_value
                            ELSE NULL
                        END
                    ) AS actual_value,
                    MAX(2) AS expected_value,
                    top_values.time_period,
                    top_values.time_period_utc
                FROM
                (
                    SELECT
                        top_col_values.top_value as top_value,
                        top_col_values.time_period as time_period,
                        top_col_values.time_period_utc as time_period_utc,
                        RANK() OVER(PARTITION BY top_col_values.time_period
                            ORDER BY top_col_values.total_values) as top_values_rank
                    FROM
                    (
                        SELECT
                            %1$s AS top_value,
                            COUNT(*) AS total_values,
                            analyzed_table.`date` AS time_period,
                            TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                        FROM
                            `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                        WHERE %5$s
                        GROUP BY time_period, time_period_utc, top_value
                        ORDER BY time_period, time_period_utc, total_values
                    ) AS top_col_values
                ) AS top_values
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
    void renderSensor_whenMonitoringDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    COUNT(DISTINCT
                        CASE
                            WHEN top_values.top_value IN ('a111a', 'd44d') THEN top_values.top_value
                            ELSE NULL
                        END
                    ) AS actual_value,
                    MAX(2) AS expected_value,
                    top_values.time_period,
                    top_values.time_period_utc
                FROM
                (
                    SELECT
                        top_col_values.top_value as top_value,
                        top_col_values.time_period as time_period,
                        top_col_values.time_period_utc as time_period_utc,
                        RANK() OVER(PARTITION BY top_col_values.time_period
                            ORDER BY top_col_values.total_values) as top_values_rank
                    FROM
                    (
                        SELECT
                            %1$s AS top_value,
                            COUNT(*) AS total_values,
                            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                        FROM
                            `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                        WHERE %5$s
                        GROUP BY time_period, time_period_utc, top_value
                        ORDER BY time_period, time_period_utc, total_values
                    ) AS top_col_values
                ) AS top_values
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
        this.sut.setTop(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    COUNT(DISTINCT
                        CASE
                            WHEN top_values.top_value IN ('a111a', 'd44d') THEN top_values.top_value
                            ELSE NULL
                        END
                    ) AS actual_value,
                    MAX(2) AS expected_value,
                    top_values.time_period,
                    top_values.time_period_utc
                FROM
                (
                    SELECT
                        top_col_values.top_value as top_value,
                        top_col_values.time_period as time_period,
                        top_col_values.time_period_utc as time_period_utc,
                        RANK() OVER(PARTITION BY top_col_values.time_period
                            ORDER BY top_col_values.total_values) as top_values_rank
                    FROM
                    (
                        SELECT
                            %1$s AS top_value,
                            COUNT(*) AS total_values,
                            analyzed_table.`date` AS time_period,
                            TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                        FROM
                            `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                        WHERE %5$s
                              AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                              AND analyzed_table.`date` < CURRENT_DATE()
                        GROUP BY time_period, time_period_utc, top_value
                        ORDER BY time_period, time_period_utc, total_values
                    ) AS top_col_values
                ) AS top_values
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
        this.sut.setTop(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    COUNT(DISTINCT
                        CASE
                            WHEN top_values.top_value IN ('a111a', 'd44d') THEN top_values.top_value
                            ELSE NULL
                        END
                    ) AS actual_value,
                    MAX(2) AS expected_value,
                    top_values.time_period,
                    top_values.time_period_utc,
                    top_values.grouping_level_1
                FROM
                (
                    SELECT
                        top_col_values.top_value as top_value,
                        top_col_values.time_period as time_period,
                        top_col_values.time_period_utc as time_period_utc,
                        RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1
                            ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1
                    FROM
                    (
                        SELECT
                            %1$s AS top_value,
                            COUNT(*) AS total_values,
                            analyzed_table.`result` AS grouping_level_1
                        FROM
                            `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                        WHERE id < 5
                        GROUP BY grouping_level_1, top_value
                        ORDER BY grouping_level_1, total_values
                    ) AS top_col_values
                ) AS top_values
                WHERE top_values_rank <= 2
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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    COUNT(DISTINCT
                        CASE
                            WHEN top_values.top_value IN ('a111a', 'd44d') THEN top_values.top_value
                            ELSE NULL
                        END
                    ) AS actual_value,
                    MAX(2) AS expected_value,
                    top_values.time_period,
                    top_values.time_period_utc,
                    top_values.grouping_level_1
                FROM
                (
                    SELECT
                        top_col_values.top_value as top_value,
                        top_col_values.time_period as time_period,
                        top_col_values.time_period_utc as time_period_utc,
                        RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1
                            ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1
                    FROM
                    (
                        SELECT
                            %1$s AS top_value,
                            COUNT(*) AS total_values,
                            analyzed_table.`result` AS grouping_level_1,
                            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                        FROM
                            `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                        WHERE id < 5
                        GROUP BY grouping_level_1, time_period, time_period_utc, top_value
                        ORDER BY grouping_level_1, time_period, time_period_utc, total_values
                    ) AS top_col_values
                ) AS top_values
                WHERE top_values_rank <= 2
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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    COUNT(DISTINCT
                        CASE
                            WHEN top_values.top_value IN ('a111a', 'd44d') THEN top_values.top_value
                            ELSE NULL
                        END
                    ) AS actual_value,
                    MAX(2) AS expected_value,
                    top_values.time_period,
                    top_values.time_period_utc,
                    top_values.grouping_level_1
                FROM
                (
                    SELECT
                        top_col_values.top_value as top_value,
                        top_col_values.time_period as time_period,
                        top_col_values.time_period_utc as time_period_utc,
                        RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1
                            ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1
                    FROM
                    (
                        SELECT
                            %1$s AS top_value,
                            COUNT(*) AS total_values,
                            analyzed_table.`result` AS grouping_level_1,
                            analyzed_table.`date` AS time_period,
                            TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                        FROM
                            `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                        WHERE id < 5
                              AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                              AND analyzed_table.`date` < CURRENT_DATE()
                        GROUP BY grouping_level_1, time_period, time_period_utc, top_value
                        ORDER BY grouping_level_1, time_period, time_period_utc, total_values
                    ) AS top_col_values
                ) AS top_values
                WHERE top_values_rank <= 2
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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("date");
        }});
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);

        String target_query = """
                SELECT
                    COUNT(DISTINCT
                        CASE
                            WHEN top_values.top_value IN ('a111a', 'd44d') THEN top_values.top_value
                            ELSE NULL
                        END
                    ) AS actual_value,
                    MAX(2) AS expected_value,
                    top_values.time_period,
                    top_values.time_period_utc,
                    top_values.grouping_level_1,
                    top_values.grouping_level_2,
                    top_values.grouping_level_3
                FROM
                (
                    SELECT
                        top_col_values.top_value as top_value,
                        top_col_values.time_period as time_period,
                        top_col_values.time_period_utc as time_period_utc,
                        RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2, top_col_values.grouping_level_3
                            ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2, top_col_values.grouping_level_3
                    FROM
                    (
                        SELECT
                            %1$s AS top_value,
                            COUNT(*) AS total_values,
                            analyzed_table.`result` AS grouping_level_1,
                            analyzed_table.`result` AS grouping_level_2,
                            analyzed_table.`result` AS grouping_level_3,
                            analyzed_table.`date` AS time_period,
                            TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                        FROM
                            `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                        WHERE %5$s
                        GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc, top_value
                        ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc, total_values
                    ) AS top_col_values
                ) AS top_values
                WHERE top_values_rank <= 2
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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    COUNT(DISTINCT
                        CASE
                            WHEN top_values.top_value IN ('a111a', 'd44d') THEN top_values.top_value
                            ELSE NULL
                        END
                    ) AS actual_value,
                    MAX(2) AS expected_value,
                    top_values.time_period,
                    top_values.time_period_utc,
                    top_values.grouping_level_1,
                    top_values.grouping_level_2,
                    top_values.grouping_level_3
                FROM
                (
                    SELECT
                        top_col_values.top_value as top_value,
                        top_col_values.time_period as time_period,
                        top_col_values.time_period_utc as time_period_utc,
                        RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2, top_col_values.grouping_level_3
                            ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2, top_col_values.grouping_level_3
                    FROM
                    (
                        SELECT
                            %1$s AS top_value,
                            COUNT(*) AS total_values,
                            analyzed_table.`result` AS grouping_level_1,
                            analyzed_table.`result` AS grouping_level_2,
                            analyzed_table.`result` AS grouping_level_3,
                            DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                            TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
                        FROM
                            `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                        WHERE %5$s
                        GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc, top_value
                        ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc, total_values
                    ) AS top_col_values
                ) AS top_values
                WHERE top_values_rank <= 2
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
        List<String> values = new ArrayList<>();
        values.add("a111a");
        values.add("d44d");
        this.sut.setExpectedValues(values);
        this.sut.setTop(2L);
        this.sut.setFilter("id < 5");

        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned(CheckTimeScale.daily, "date");
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    COUNT(DISTINCT
                        CASE
                            WHEN top_values.top_value IN ('a111a', 'd44d') THEN top_values.top_value
                            ELSE NULL
                        END
                    ) AS actual_value,
                    MAX(2) AS expected_value,
                    top_values.time_period,
                    top_values.time_period_utc,
                    top_values.grouping_level_1,
                    top_values.grouping_level_2,
                    top_values.grouping_level_3
                FROM
                (
                    SELECT
                        top_col_values.top_value as top_value,
                        top_col_values.time_period as time_period,
                        top_col_values.time_period_utc as time_period_utc,
                        RANK() OVER(PARTITION BY top_col_values.time_period, top_col_values.grouping_level_1, top_col_values.grouping_level_2, top_col_values.grouping_level_3
                            ORDER BY top_col_values.total_values) as top_values_rank, top_col_values.grouping_level_1, top_col_values.grouping_level_2, top_col_values.grouping_level_3
                    FROM
                    (
                        SELECT
                            %1$s AS top_value,
                            COUNT(*) AS total_values,
                            analyzed_table.`result` AS grouping_level_1,
                            analyzed_table.`result` AS grouping_level_2,
                            analyzed_table.`result` AS grouping_level_3,
                            analyzed_table.`date` AS time_period,
                            TIMESTAMP(analyzed_table.`date`) AS time_period_utc
                        FROM
                            `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                        WHERE %5$s
                              AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                              AND analyzed_table.`date` < CURRENT_DATE()
                        GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc, top_value
                        ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc, total_values
                    ) AS top_col_values
                ) AS top_values
                WHERE top_values_rank <= 2
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
