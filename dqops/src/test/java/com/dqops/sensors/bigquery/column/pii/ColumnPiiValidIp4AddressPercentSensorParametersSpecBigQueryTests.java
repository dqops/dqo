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
package com.dqops.sensors.bigquery.column.pii;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.pii.ColumnPiiValidIp4AddressPercentCheckSpec;
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
import com.dqops.sensors.column.pii.ColumnPiiValidIp4AddressPercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnPiiValidIp4AddressPercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnPiiValidIp4AddressPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnPiiValidIp4AddressPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnPiiValidIp4AddressPercentSensorParametersSpec();
        this.sut.setFilter("{alias}.`correct` = 1");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.ip4_test, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnPiiValidIp4AddressPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersProfiling() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "ip4", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersRecurring(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForRecurringCheck(this.sampleTableMetadata, "ip4", this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunParametersPartitioned(CheckTimeScale timeScale, String timeSeriesColumn) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(this.sampleTableMetadata, "ip4", this.checkSpec, timeScale, timeSeriesColumn);
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
        Assertions.assertEquals("column/pii/valid_ip4_address_percent", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(%s AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s""";

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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(%s AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(%s AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
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
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(%s AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
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
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(%s AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`result` AS grouping_level_1
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
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
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(%s AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`result` AS grouping_level_1,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
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
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(%s AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`result` AS grouping_level_1,
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
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(%s AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`result` AS grouping_level_1,
                analyzed_table.`result` AS grouping_level_2,
                analyzed_table.`result` AS grouping_level_3,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
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
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(%s AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`result` AS grouping_level_1,
                analyzed_table.`result` AS grouping_level_2,
                analyzed_table.`result` AS grouping_level_3,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%s`.`%s`.`%s` AS analyzed_table
            WHERE %s
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
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("result")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(*) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN REGEXP_CONTAINS(CAST(%s AS STRING), r"^((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])[.]){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9][0-9]|[0-9])$")
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`result` AS grouping_level_1,
                analyzed_table.`result` AS grouping_level_2,
                analyzed_table.`result` AS grouping_level_3,
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
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }
}
