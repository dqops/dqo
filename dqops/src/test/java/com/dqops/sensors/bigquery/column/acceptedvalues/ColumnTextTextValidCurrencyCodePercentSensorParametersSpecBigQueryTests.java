/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.sensors.bigquery.column.acceptedvalues;

import com.dqops.BaseTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.acceptedvalues.ColumnTextValidCurrencyCodePercentCheckSpec;
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
import com.dqops.sensors.column.acceptedvalues.ColumnTextTextValidCurrencyCodePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ColumnTextTextValidCurrencyCodePercentSensorParametersSpecBigQueryTests extends BaseTest {
    private ColumnTextTextValidCurrencyCodePercentSensorParametersSpec sut;
    private final String valuesString = "'ALL',\t'AFN',\t'ARS',\t'AWG',\t'AUD',\t'AZN',\t'BSD',\t'BBD',\t'BYN',\t'BZD',\t'BMD',\t'BOB',\t'BAM',\t'BWP',\t'BGN',\t'BRL',\t'BND',\t'KHR',\t'CAD',\t'KYD',\t'CLP',\t'CNY',\t'COP',\t'CRC',\t'HRK',\t'CUP',\t'CZK',\t'DKK',\t'DOP',\t'XCD',\t'EGP',\t'SVC',\t'EUR',\t'FKP',\t'FJD',\t'GHS',\t'GIP',\t'GTQ',\t'GGP',\t'GYD',\t'HNL',\t'HKD',\t'HUF',\t'ISK',\t'INR',\t'IDR',\t'IRR',\t'IMP',\t'ILS',\t'JMD',\t'JPY',\t'JEP',\t'KZT',\t'KPW',\t'KRW',\t'KGS',\t'LAK',\t'LBP',\t'LRD',\t'MKD',\t'MYR',\t'MUR',\t'MXN',\t'MNT',\t'MZN',\t'NAD',\t'NPR',\t'ANG',\t'NZD',\t'NIO',\t'NGN',\t'NOK',\t'OMR',\t'PKR',\t'PAB',\t'PYG',\t'PEN',\t'PHP',\t'PLN',\t'QAR',\t'RON',\t'RUB',\t'SHP',\t'SAR',\t'RSD',\t'SCR',\t'SGD',\t'SBD',\t'SOS',\t'ZAR',\t'LKR',\t'SEK',\t'CHF',\t'SRD',\t'SYP',\t'TWD',\t'THB',\t'TTD',\t'TRY',\t'TVD',\t'UAH',\t'AED',\t'GBP',\t'USD',\t'UYU',\t'UZS',\t'VEF',\t'VND',\t'YER',\t'ZWD',\t'LEK',\t'؋',\t'$',\t'Ƒ',\t'₼',\t'BR',\t'BZ$',\t'$B',\t'KM',\t'P',\t'ЛВ',\t'R$',\t'៛',\t'¥',\t'₡',\t'KN',\t'₱',\t'KČ',\t'KR',\t'RD$', '£',\t'€',\t'¢',\t'Q',\t'L',\t'FT',\t'₹',\t'RP',\t'﷼',\t'₪',\t'J$',\t'₩',\t'₭',\t'ДЕН',\t'RM',\t'₨',\t'₮',\t'د.إ',\t'MT',\t'C$',\t'₦',\t'B/.',\t'GS',\t'S/.', 'ZŁ',\t'LEI',\t'ДИН.',\t'S',\t'R',\t'NT$',\t'฿',\t'TT$',\t'₺',\t'₴',\t'$U',\t'BS',\t'₫', 'Z$'";
    private UserHomeContext userHomeContext;
    private ColumnTextValidCurrencyCodePercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sut = new ColumnTextTextValidCurrencyCodePercentSensorParametersSpec();
        this.sut.setFilter("{alias}.`correct` = 1");

        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.bigquery);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.checkSpec = new ColumnTextValidCurrencyCodePercentCheckSpec();
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
        Assertions.assertEquals("column/accepted_values/text_valid_currency_code_percent", this.sut.getSensorDefinitionName());
    }

    @Test
    void areValuesUppercase() {
        Assertions.assertEquals(this.valuesString.toUpperCase(), this.valuesString);
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
            WHERE (%6$s)""";

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
            WHERE (%6$s)
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
                END AS actual_value
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)""";

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
            WHERE (%6$s)
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
            WHERE (%6$s)
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
                analyzed_table.`length_int` AS grouping_level_1
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)
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
            WHERE (%6$s)
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
            WHERE (%6$s)
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
                analyzed_table.`length_int` AS grouping_level_3
            FROM `%3$s`.`%4$s`.`%5$s` AS analyzed_table
            WHERE (%6$s)
            GROUP BY grouping_level_1, grouping_level_2, grouping_level_3
            ORDER BY grouping_level_1, grouping_level_2, grouping_level_3""";

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
            WHERE (%6$s)
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
