/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.db2.sensors.column.accuracy;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.accuracy.ColumnAccuracyTotalSumMatchPercentCheckSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.db2.BaseDb2IntegrationTest;
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
import com.dqops.sensors.column.accuracy.ColumnAccuracyTotalSumMatchPercentSensorParametersSpec;
import com.dqops.testutils.ValueConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;


@SpringBootTest
public class Db2ColumnAccuracyTotalSumMatchPercentSensorParametersSpecIntegrationTest extends BaseDb2IntegrationTest {
    private ColumnAccuracyTotalSumMatchPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnAccuracyTotalSumMatchPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;
    private SampleTableMetadata sampleTableMetadataReferenced;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.ip4_test, ProviderType.db2);
        this.sampleTableMetadataReferenced = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.ip6_test, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(this.sampleTableMetadata);
        IntegrationTestSampleDataObjectMother.ensureTableExists(this.sampleTableMetadataReferenced);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnAccuracyTotalSumMatchPercentSensorParametersSpec();
        this.checkSpec = new ColumnAccuracyTotalSumMatchPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_onNullDataInPrimaryTable_thenReturnsValues() {
        String csvFileName = SampleCsvFileNames.only_nulls;
        SampleTableMetadata nullTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                csvFileName, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(nullTableMetadata);

        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(nullTableMetadata);
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, sampleTableMetadataReferenced);
        this.sut.setReferencedTable(this.sampleTableMetadataReferenced.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("result");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                nullTableMetadata, "int_nulls", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("expected_value", resultTable.column(0).name());
        Assertions.assertEquals("actual_value", resultTable.column(1).name());
        Assertions.assertEquals(15L, ValueConverter.toLong(resultTable.column(0).get(0)));
        Assertions.assertEquals(null, resultTable.column(1).get(0));
    }

    @Test
    void runSensor_onNullDataInForeignTable_thenReturnsValues() {
        String csvFileName = SampleCsvFileNames.only_nulls;
        SampleTableMetadata nullTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                csvFileName, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(nullTableMetadata);

        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, nullTableMetadata);

        this.sut.setReferencedTable(nullTableMetadata.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("int_nulls");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "result", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("expected_value", resultTable.column(0).name());
        Assertions.assertEquals("actual_value", resultTable.column(1).name());
        Assertions.assertEquals(null, resultTable.column(0).get(0));
        Assertions.assertEquals(15L, ValueConverter.toLong(resultTable.column(1).get(0)));
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        this.sut.setReferencedTable(this.sampleTableMetadataReferenced.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("result");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "result", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("expected_value", resultTable.column(0).name());
        Assertions.assertEquals("actual_value", resultTable.column(1).name());
        Assertions.assertEquals(15L, ValueConverter.toLong(resultTable.column(0).get(0)));
        Assertions.assertEquals(15L, ValueConverter.toLong(resultTable.column(1).get(0)));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        this.sut.setReferencedTable(this.sampleTableMetadataReferenced.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("result");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "result", this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("expected_value", resultTable.column(0).name());
        Assertions.assertEquals("actual_value", resultTable.column(1).name());
        Assertions.assertEquals(15L, ValueConverter.toLong(resultTable.column(0).get(0)));
        Assertions.assertEquals(15L, ValueConverter.toLong(resultTable.column(1).get(0)));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        this.sut.setReferencedTable(this.sampleTableMetadataReferenced.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("result");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "result", this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("expected_value", resultTable.column(0).name());
        Assertions.assertEquals("actual_value", resultTable.column(1).name());
        Assertions.assertEquals(15L, ValueConverter.toLong(resultTable.column(0).get(0)));
        Assertions.assertEquals(15L, ValueConverter.toLong(resultTable.column(1).get(0)));
    }
}