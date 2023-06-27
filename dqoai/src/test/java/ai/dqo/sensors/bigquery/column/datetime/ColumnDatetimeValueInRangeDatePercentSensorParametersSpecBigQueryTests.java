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
package ai.dqo.sensors.bigquery.column.datetime;

import ai.dqo.BaseTest;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.column.checkspecs.datetime.ColumnDatetimeValueInRangeDatePercentCheckSpec;
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
import ai.dqo.sensors.column.datetime.ColumnDatetimeValueInRangeDatePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class ColumnDatetimeValueInRangeDatePercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnDatetimeValueInRangeDatePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnDatetimeValueInRangeDatePercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnDatetimeValueInRangeDatePercentSensorParametersSpec();
        this.sut.setFilter("{alias}.id <> 4");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_average_delay, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnDatetimeValueInRangeDatePercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersProfiling(String columnName) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, columnName, this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersRecurring(String columnName, CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForRecurringCheck(this.sampleTableMetadata, columnName, this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunParametersPartitioned(String columnName, CheckTimeScale timeScale, String timeSeriesColumn) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(this.sampleTableMetadata, columnName, this.checkSpec, timeScale, timeSeriesColumn);
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
        Assertions.assertEquals("column/datetime/value_in_range_date_percent", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
                
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling("date4");
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN SAFE_CAST(%1$s AS DATE) >= '2022-01-01' AND SAFE_CAST(%1$s AS DATE) <= '2022-01-10' THEN 1
                        ELSE 0
                        END
                    ) / COUNT(*)
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
    void renderSensor_whenProfilingNoTimeSeriesNoDataStreamTypeString_thenRendersCorrectSql() {
        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling("date3");
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN SAFE_CAST(%1$s AS DATE) >= '2022-01-01' AND SAFE_CAST(%1$s AS DATE) <= '2022-01-10' THEN 1
                        ELSE 0
                        END
                    ) / COUNT(*)
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
        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling("date4");
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("date1");
        }});

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE
                        WHEN COUNT(%1$s) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN SAFE_CAST(%1$s AS DATE) >= '2022-01-01' AND SAFE_CAST(%1$s AS DATE) <= '2022-01-10' THEN 1
                            ELSE 0
                            END
                        ) / COUNT(*)
                    END AS actual_value,
                    CAST(analyzed_table.`date1` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc 
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
        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        
        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring("date4", CheckTimeScale.monthly);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN SAFE_CAST(%1$s AS DATE) >= '2022-01-01' AND SAFE_CAST(%1$s AS DATE) <= '2022-01-10' THEN 1
                        ELSE 0
                        END
                    ) / COUNT(*)
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
        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned("date4", CheckTimeScale.daily, "date1");

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN SAFE_CAST(%1$s AS DATE) >= '2022-01-01' AND SAFE_CAST(%1$s AS DATE) <= '2022-01-10' THEN 1
                        ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                CAST(analyzed_table.`date1` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
                  AND analyzed_table.`date1` >= CAST(DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY) AS DATETIME)
                  AND analyzed_table.`date1` < CAST(CURRENT_DATE() AS DATETIME)
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
        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling("date4");
        runParameters.setTimeSeries(null);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN SAFE_CAST(%1$s AS DATE) >= '2022-01-01' AND SAFE_CAST(%1$s AS DATE) <= '2022-01-10' THEN 1
                        ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`date2` AS grouping_level_1
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
        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        
        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring("date4", CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN SAFE_CAST(%1$s AS DATE) >= '2022-01-01' AND SAFE_CAST(%1$s AS DATE) <= '2022-01-10' THEN 1
                        ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`date2` AS grouping_level_1,
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
        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned("date4", CheckTimeScale.daily, "date1");
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
                SELECT
                    CASE
                        WHEN COUNT(%1$s) = 0 THEN NULL
                        ELSE 100.0 * SUM(
                            CASE
                                WHEN SAFE_CAST(%1$s AS DATE) >= '2022-01-01' AND SAFE_CAST(%1$s AS DATE) <= '2022-01-10' THEN 1
                            ELSE 0
                            END
                        ) / COUNT(*)
                    END AS actual_value,
                    analyzed_table.`date2` AS grouping_level_1,
                    CAST(analyzed_table.`date1` AS DATE) AS time_period,
                    TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc
                FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
                WHERE %5$s
                      AND analyzed_table.`date1` >= CAST(DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY) AS DATETIME)
                      AND analyzed_table.`date1` < CAST(CURRENT_DATE() AS DATETIME)
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
    void renderSensor_whenProfilingOneTimeSeriesTwoDataStream_thenRendersCorrectSql() {
        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling("date4");
        runParameters.setTimeSeries(new TimeSeriesConfigurationSpec(){{
            setMode(TimeSeriesMode.timestamp_column);
            setTimeGradient(TimePeriodGradient.day);
            setTimestampColumn("date1");
        }});
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("date3")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN SAFE_CAST(%1$s AS DATE) >= '2022-01-01' AND SAFE_CAST(%1$s AS DATE) <= '2022-01-10' THEN 1
                        ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`date2` AS grouping_level_1,
                analyzed_table.`date3` AS grouping_level_2,
                CAST(analyzed_table.`date1` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenRecurringDefaultTimeSeriesTwoDataStream_thenRendersCorrectSql() {
        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        
        SensorExecutionRunParameters runParameters = this.getRunParametersRecurring("date4", CheckTimeScale.monthly);
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("date3")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN SAFE_CAST(%1$s AS DATE) >= '2022-01-01' AND SAFE_CAST(%1$s AS DATE) <= '2022-01-10' THEN 1
                        ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`date2` AS grouping_level_1,
                analyzed_table.`date3` AS grouping_level_2,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }

    @Test
    void renderSensor_whenPartitionedDefaultTimeSeriesTwoDataStream_thenRendersCorrectSql() {
        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        
        SensorExecutionRunParameters runParameters = this.getRunParametersPartitioned("date4", CheckTimeScale.daily, "date1");
        runParameters.setDataGroupings(
                DataGroupingConfigurationSpecObjectMother.create(
                        DataStreamLevelSpecObjectMother.createColumnMapping("date2"),
                        DataStreamLevelSpecObjectMother.createColumnMapping("date3")));

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN NULL
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN SAFE_CAST(%1$s AS DATE) >= '2022-01-01' AND SAFE_CAST(%1$s AS DATE) <= '2022-01-10' THEN 1
                        ELSE 0
                        END
                    ) / COUNT(*)
                END AS actual_value,
                analyzed_table.`date2` AS grouping_level_1,
                analyzed_table.`date3` AS grouping_level_2,
                CAST(analyzed_table.`date1` AS DATE) AS time_period,
                TIMESTAMP(CAST(analyzed_table.`date1` AS DATE)) AS time_period_utc
            FROM `%2$s`.`%3$s`.`%4$s` AS analyzed_table
            WHERE %5$s
                  AND analyzed_table.`date1` >= CAST(DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY) AS DATETIME)
                  AND analyzed_table.`date1` < CAST(CURRENT_DATE() AS DATETIME)
            GROUP BY grouping_level_1, grouping_level_2, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }
}
