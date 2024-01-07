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
package com.dqops.sensors.bigquery.column.strings;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.acceptedvalues.ColumnExpectedTextValuesInUseCountCheckSpec;
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
import com.dqops.sensors.column.acceptedvalues.ColumnStringsExpectedTextValuesInUseCountSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.stream.Collectors;

@SpringBootTest
public class ColumnStringsExpectedTextValuesInUseCountSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnStringsExpectedTextValuesInUseCountSensorParametersSpec sut;
    private String sutValuesAsString;
    private UserHomeContext userHomeContext;
    private ColumnExpectedTextValuesInUseCountCheckSpec checkSpec;
    private ColumnExpectedTextValuesInUseCountCheckSpec altCheckSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp(){
		this.sut = new ColumnStringsExpectedTextValuesInUseCountSensorParametersSpec();
        this.sut.setFilter("{alias}.`correct` = 1");
        ColumnStringsExpectedTextValuesInUseCountSensorParametersSpec altSut = (ColumnStringsExpectedTextValuesInUseCountSensorParametersSpec) this.sut.deepClone();
        this.sut.setExpectedValues(new ArrayList<>(){{
            add("abcde"); add("abcdef"); add("abcdefg");
        }});
        this.sutValuesAsString = this.sut.getExpectedValues().stream()
                .map(s -> String.format("'%s'", s))
                .collect(Collectors.joining(", "));

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnExpectedTextValuesInUseCountCheckSpec();
        this.checkSpec.setParameters(this.sut);
        this.altCheckSpec = new ColumnExpectedTextValuesInUseCountCheckSpec();
        this.altCheckSpec.setParameters(altSut);
    }

    private SensorExecutionRunParameters getRunParametersProfiling() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "length_string", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersProfilingAlt() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "length_string", this.altCheckSpec);
    }

    private SensorExecutionRunParameters getRunParametersMonitoring(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(this.sampleTableMetadata, "length_string", this.checkSpec, timeScale);
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
        Assertions.assertEquals("column/strings/expected_strings_in_use_count", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN %s IN (%s)
                            THEN %s
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.sutValuesAsString,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenProfilingNoTimeSeriesNoDataStreamNoValuesList_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersProfilingAlt();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                NULL AS actual_value,
                MAX(0) AS expected_value
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s""";

        Assertions.assertEquals(String.format(target_query,
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
                COUNT(DISTINCT
                    CASE
                        WHEN %s IN (%s)
                            THEN %s
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.sutValuesAsString,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenMonitoringDefaultTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN %s IN (%s)
                            THEN %s
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.sutValuesAsString,
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
                COUNT(DISTINCT
                    CASE
                        WHEN %s IN (%s)
                            THEN %s
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
                  AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                  AND analyzed_table.`date` < CURRENT_DATE()
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.sutValuesAsString,
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
                COUNT(DISTINCT
                    CASE
                        WHEN %s IN (%s)
                            THEN %s
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`length_int` AS grouping_level_1
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY grouping_level_1
            ORDER BY grouping_level_1""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.sutValuesAsString,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenMonitoringDefaultTimeSeriesOneDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN %s IN (%s)
                            THEN %s
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`length_int` AS grouping_level_1,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY grouping_level_1, time_period, time_period_utc
            ORDER BY grouping_level_1, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.sutValuesAsString,
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
                COUNT(DISTINCT
                    CASE
                        WHEN %s IN (%s)
                            THEN %s
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`length_int` AS grouping_level_1,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
                  AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                  AND analyzed_table.`date` < CURRENT_DATE()
            GROUP BY grouping_level_1, time_period, time_period_utc
            ORDER BY grouping_level_1, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.sutValuesAsString,
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
                COUNT(DISTINCT
                    CASE
                        WHEN %s IN (%s)
                            THEN %s
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`strings_with_numbers` AS grouping_level_1,
                analyzed_table.`mix_of_values` AS grouping_level_2,
                analyzed_table.`length_int` AS grouping_level_3,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.sutValuesAsString,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenMonitoringDefaultTimeSeriesThreeDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersMonitoring(CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("strings_with_numbers"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("mix_of_values"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("length_int")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                COUNT(DISTINCT
                    CASE
                        WHEN %s IN (%s)
                            THEN %s
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`strings_with_numbers` AS grouping_level_1,
                analyzed_table.`mix_of_values` AS grouping_level_2,
                analyzed_table.`length_int` AS grouping_level_3,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
            GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.sutValuesAsString,
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
                COUNT(DISTINCT
                    CASE
                        WHEN %s IN (%s)
                            THEN %s
                        ELSE NULL
                    END
                ) AS actual_value,
                MAX(3) AS expected_value,
                analyzed_table.`strings_with_numbers` AS grouping_level_1,
                analyzed_table.`mix_of_values` AS grouping_level_2,
                analyzed_table.`length_int` AS grouping_level_3,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
                  AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                  AND analyzed_table.`date` < CURRENT_DATE()
            GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.sutValuesAsString,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }
}
