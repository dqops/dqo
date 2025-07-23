/*
 * Copyright © 2021-Present DQOps, Documati sp. z o.o. (support@dqops.com)
 *
 * This file is licensed under the Business Source License 1.1,
 * which can be found in the root directory of this repository.
 *
 * Change Date: This file will be licensed under the Apache License, Version 2.0,
 * four (4) years from its last modification date.
 */
package com.dqops.duckdb.sensors.column.numeric;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.numeric.ColumnMedianInRangeCheckSpec;
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.connectors.duckdb.config.DuckdbFilesFormatType;
import com.dqops.duckdb.BaseDuckdbIntegrationTest;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.column.numeric.ColumnNumericMedianSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;


@SpringBootTest
public class DuckdbColumnNumericMedianSensorParametersSpecIntegrationTest extends BaseDuckdbIntegrationTest {
    private ColumnNumericMedianSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnMedianInRangeCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        String csvFileName = SampleCsvFileNames.below_above_value_test;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForExplicitCsvFile(
                csvFileName, connectionSpec);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnNumericMedianSensorParametersSpec();
        this.checkSpec = new ColumnMedianInRangeCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "value", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(10.5, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "value", this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(10.5, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "value", this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(10.5, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "value", this.checkSpec, CheckTimeScale.daily,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(6, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(17.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "value", this.checkSpec, CheckTimeScale.monthly,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(6, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(17.0, resultTable.column(0).get(0));
    }
}
