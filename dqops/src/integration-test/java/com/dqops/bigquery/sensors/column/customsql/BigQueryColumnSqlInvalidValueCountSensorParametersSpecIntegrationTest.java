/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.bigquery.sensors.column.customsql;

import com.dqops.bigquery.BaseBigQueryIntegrationTest;
import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.customsql.ColumnSqlInvalidValueCountCheckSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.column.customsql.ColumnSqlInvalidValueCountSensorParametersSpec;
import com.dqops.testutils.ValueConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
public class BigQueryColumnSqlInvalidValueCountSensorParametersSpecIntegrationTest extends BaseBigQueryIntegrationTest {
    private ColumnSqlInvalidValueCountSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnSqlInvalidValueCountCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.string_test_data, ProviderType.bigquery);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
		this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
		this.sut = new ColumnSqlInvalidValueCountSensorParametersSpec();
		this.checkSpec = new ColumnSqlInvalidValueCountCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_onNullData_thenReturnsValues() {
        String csvFileName = SampleCsvFileNames.only_nulls;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                csvFileName, ProviderType.bigquery);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);

        this.sut.setSqlQuery("SELECT {column} AS actual_value FROM {table} as analyzed_table WHERE {column} IS NOT NULL");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "string_nulls", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0, ValueConverter.toDouble(resultTable.column(0).get(0)));
    }

    @Test
    void runSensor_whenSensorExecutedProfilingWithJinjaVariables_thenReturnsValues() {
        this.sut.setSqlQuery("SELECT {column} AS actual_value FROM {table} as analyzed_table WHERE {column} = 'married'");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "null_placeholder", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(16L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        this.sut.setSqlQuery("SELECT null_placeholder AS actual_value FROM {table} WHERE null_placeholder = 'married'");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "null_placeholder", this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(16L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        this.sut.setSqlQuery("SELECT null_placeholder AS actual_value FROM {table} WHERE null_placeholder = 'married'");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "null_placeholder", this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(16L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenErrorSamplingSensorExecutedWithNoGroupingAndNoIdColumns_thenReturnsErrorSamples() {
        this.sut.setSqlQuery("SELECT {column} AS actual_value FROM {table} as analyzed_table WHERE {column} = 'married'");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForErrorSampling(
                sampleTableMetadata, "null_placeholder", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(16, resultTable.rowCount());
        Assertions.assertEquals(1, resultTable.columnCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        List<String> sampleValues = List.of(resultTable.column("actual_value").asObjectArray())
                .stream().map(val -> String.valueOf(val))
                .collect(Collectors.toList());

        Assertions.assertTrue(sampleValues.contains("married"));
    }

}
