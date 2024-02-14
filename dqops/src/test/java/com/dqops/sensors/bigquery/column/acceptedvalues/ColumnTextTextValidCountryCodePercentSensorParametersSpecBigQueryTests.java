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
import com.dqops.checks.column.checkspecs.acceptedvalues.ColumnTextValidCountryCodePercentCheckSpec;
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
import com.dqops.sensors.column.acceptedvalues.ColumnTextTextValidCountryCodePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnTextTextValidCountryCodePercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnTextTextValidCountryCodePercentSensorParametersSpec sut;
    private final String valuesString = "'AF',\t'AL',\t'DZ',\t'AS',\t'AD',\t'AO',\t'AI',\t'AQ',\t'AG',\t'AR',\t'AM',\t'AW',\t'AU',\t'AT',\t'AZ',\t'BS',\t'BH',\t'BD',\t'BB',\t'BY',\t'BE',\t'BZ',\t'BJ',\t'BM',\t'BT',\t'BO',\t'BA',\t'BW',\t'BR',\t'IO',\t'VG',\t'BN',\t'BG',\t'BF',\t'BI',\t'KH',\t'CM',\t'CA',\t'CV',\t'KY',\t'CF',\t'TD',\t'CL',\t'CN',\t'CX',\t'CC',\t'CO',\t'KM',\t'CK',\t'CR',\t'HR',\t'CU',\t'CW',\t'CY',\t'CZ',\t'CD',\t'DK',\t'DJ',\t'DM',\t'DO',\t'TL',\t'EC',\t'EG',\t'SV',\t'GQ',\t'ER',\t'EE',\t'ET',\t'FK',\t'FO',\t'FJ',\t'FI',\t'FR',\t'PF',\t'GA',\t'GM',\t'GE',\t'DE',\t'GH',\t'GI',\t'GR',\t'GL',\t'GD',\t'GU',\t'GT',\t'GG',\t'GN',\t'GW',\t'GY',\t'HT',\t'HN',\t'HK',\t'HU',\t'IS',\t'IN',\t'ID',\t'IR',\t'IQ',\t'IE',\t'IM',\t'IL',\t'IT',\t'CI',\t'JM',\t'JP',\t'JE',\t'JO',\t'KZ',\t'KE',\t'KI',\t'XK',\t'KW',\t'KG',\t'LA',\t'LV',\t'LB',\t'LS',\t'LR',\t'LY',\t'LI',\t'LT',\t'LU',\t'MO',\t'MK',\t'MG',\t'MW',\t'MY',\t'MV',\t'ML',\t'MT',\t'MH',\t'MR',\t'MU',\t'YT',\t'MX',\t'FM',\t'MD',\t'MC',\t'MN',\t'ME',\t'MS',\t'MA',\t'MZ',\t'MM',\t'NA',\t'NR',\t'NP',\t'NL',\t'AN',\t'NC',\t'NZ',\t'NI',\t'NE',\t'NG',\t'NU',\t'KP',\t'MP',\t'NO',\t'OM',\t'PK',\t'PW',\t'PS',\t'PA',\t'PG',\t'PY',\t'PE',\t'PH', 'PN', 'PL',\t'PT',\t'PR',\t'QA',\t'CG',\t'RE',\t'RO',\t'RU',\t'RW',\t'BL',\t'SH',\t'KN',\t'LC',\t'MF',\t'PM',\t'VC',\t'WS',\t'SM',\t'ST',\t'SA',\t'SN',\t'RS',\t'SC',\t'SL',\t'SG',\t'SX',\t'SK',\t'SI',\t'SB',\t'SO',\t'ZA',\t'KR',\t'SS',\t'ES',\t'LK',\t'SD',\t'SR',\t'SJ',\t'SZ',\t'SE',\t'CH',\t'SY',\t'TW',\t'TJ',\t'TZ',\t'TH',\t'TG',\t'TK',\t'TO',\t'TT',\t'TN',\t'TR',\t'TM',\t'TC',\t'TV',\t'VI',\t'UG',\t'UA',\t'AE',\t'GB',\t'US',\t'UY',\t'UZ',\t'VU',\t'VA',\t'VE',\t'VN',\t'WF',\t'EH',\t'YE',\t'ZM',\t'ZW'";
    private UserHomeContext userHomeContext;
    private ColumnTextValidCountryCodePercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
		this.sut = new ColumnTextTextValidCountryCodePercentSensorParametersSpec();
        this.sut.setFilter("{alias}.`correct` = 1");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnTextValidCountryCodePercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    private SensorExecutionRunParameters getRunParametersProfiling() {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(this.sampleTableMetadata, "length_string", this.checkSpec);
    }

    private SensorExecutionRunParameters getRunParametersMonitoring(CheckTimeScale timeScale) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(this.sampleTableMetadata, "length_string", this.checkSpec, timeScale);
    }

    private SensorExecutionRunParameters getRunParametersPartitioned(CheckTimeScale timeScale, String timeSeriesColumn) {
        return SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(this.sampleTableMetadata, "length_string", this.checkSpec, timeScale, timeSeriesColumn);
    }

    private String getTableColumnName(SensorExecutionRunParameters runParameters) {
        return String.format("analyzed_table.`%1$s`", runParameters.getColumn().getColumnName());
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
        Assertions.assertEquals("column/accepted_values/text_valid_country_code_percent", this.sut.getSensorDefinitionName());
    }

    @Test
    void renderSensor_whenProfilingNoTimeSeriesNoDataStream_thenRendersCorrectSql() {
        SensorExecutionRunParameters runParameters = this.getRunParametersProfiling();
        runParameters.setTimeSeries(null);

        String renderedTemplate = JinjaTemplateRenderServiceObjectMother.renderBuiltInTemplate(runParameters);
        String target_query = """
            SELECT
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN UPPER(%1$s) IN (%2$s)
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.valuesString,
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
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN UPPER(%1$s) IN (%2$s)
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.valuesString,
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
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN UPPER(%1$s) IN (%2$s)
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
            GROUP BY time_period, time_period_utc
            ORDER BY time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.valuesString,
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
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN UPPER(%1$s) IN (%2$s)
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
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
                this.valuesString,
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
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN UPPER(%1$s) IN (%2$s)
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`length_int` AS grouping_level_1
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
            GROUP BY grouping_level_1
            ORDER BY grouping_level_1""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.valuesString,
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
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN UPPER(%1$s) IN (%2$s)
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`length_int` AS grouping_level_1,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
            GROUP BY grouping_level_1, time_period, time_period_utc
            ORDER BY grouping_level_1, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.valuesString,
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
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN UPPER(%1$s) IN (%2$s)
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`length_int` AS grouping_level_1,
                analyzed_table.`date` AS time_period,
                TIMESTAMP(analyzed_table.`date`) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
                  AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                  AND analyzed_table.`date` < CURRENT_DATE()
            GROUP BY grouping_level_1, time_period, time_period_utc
            ORDER BY grouping_level_1, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.valuesString,
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
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN UPPER(%1$s) IN (%2$s)
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
            WHERE %6$s
            GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.valuesString,
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
                CASE
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN UPPER(%1$s) IN (%2$s)
                                THEN 1
                            ELSE 0
                        END
                    ) / COUNT(%1$s)
                END AS actual_value,
                analyzed_table.`strings_with_numbers` AS grouping_level_1,
                analyzed_table.`mix_of_values` AS grouping_level_2,
                analyzed_table.`length_int` AS grouping_level_3,
                DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH) AS time_period,
                TIMESTAMP(DATE_TRUNC(CAST(CURRENT_TIMESTAMP() AS DATE), MONTH)) AS time_period_utc
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE %6$s
            GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.valuesString,
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
                    WHEN COUNT(%1$s) = 0 THEN 100.0
                    ELSE 100.0 * SUM(
                        CASE
                            WHEN UPPER(%1$s) IN (%2$s)
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
            WHERE %6$s
                  AND analyzed_table.`date` >= DATE_ADD(CURRENT_DATE(), INTERVAL -3653 DAY)
                  AND analyzed_table.`date` < CURRENT_DATE()
            GROUP BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc
            ORDER BY grouping_level_1, grouping_level_2, grouping_level_3, time_period, time_period_utc""";

        Assertions.assertEquals(String.format(target_query,
                this.getTableColumnName(runParameters),
                this.valuesString,
                runParameters.getConnection().getBigquery().getSourceProjectId(),
                runParameters.getTable().getPhysicalTableName().getSchemaName(),
                runParameters.getTable().getPhysicalTableName().getTableName(),
                this.getSubstitutedFilter("analyzed_table")
        ), renderedTemplate);
    }
}
