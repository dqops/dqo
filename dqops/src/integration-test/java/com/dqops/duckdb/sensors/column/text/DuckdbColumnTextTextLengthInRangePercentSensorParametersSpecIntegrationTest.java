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
package com.dqops.duckdb.sensors.column.text;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.text.ColumnTextLengthInRangePercentCheckSpec;
import com.dqops.connectors.duckdb.DuckdbConnectionSpecObjectMother;
import com.dqops.connectors.duckdb.DuckdbFilesFormatType;
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
import com.dqops.sensors.column.text.ColumnTextTextLengthInRangePercentSensorParametersSpec;
import com.dqops.testutils.ValueConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;


@SpringBootTest
public class DuckdbColumnTextTextLengthInRangePercentSensorParametersSpecIntegrationTest extends BaseDuckdbIntegrationTest {
    private ColumnTextTextLengthInRangePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnTextLengthInRangePercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        String csvFileName = SampleCsvFileNames.string_min_length_test;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForExplicitCsvFile(
                csvFileName, connectionSpec);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnTextTextLengthInRangePercentSensorParametersSpec();
        this.checkSpec = new ColumnTextLengthInRangePercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_onNullDataAndNoParams_thenReturnsValues() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        String csvFileName = SampleCsvFileNames.only_nulls;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForExplicitCsvFile(
                csvFileName, connectionSpec);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "string_nulls", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        // no option to set no params, check the SensorParametersSpec
        Assertions.assertEquals(100.0, ValueConverter.toDouble(resultTable.column(0).get(0)));
    }

    @Test
    void runSensor_onNullData_thenReturnsValues() {
        ConnectionSpec connectionSpec = DuckdbConnectionSpecObjectMother.createForFiles(DuckdbFilesFormatType.csv);
        String csvFileName = SampleCsvFileNames.only_nulls;
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForExplicitCsvFile(
                csvFileName, connectionSpec);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);

        this.sut.setMinLength(3);
        this.sut.setMaxLength(4);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "string_nulls", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, ValueConverter.toDouble(resultTable.column(0).get(0)));
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        this.sut.setMinLength(3);
        this.sut.setMaxLength(4);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "text", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(30.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        this.sut.setMinLength(3);
        this.sut.setMaxLength(4);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "text", this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(30.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        this.sut.setMinLength(3);
        this.sut.setMaxLength(4);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "text", this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(30.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues() {
        this.sut.setMinLength(3);
        this.sut.setMaxLength(4);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "text", this.checkSpec, CheckTimeScale.daily,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(6, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues() {
        this.sut.setMinLength(3);
        this.sut.setMaxLength(4);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "text", this.checkSpec, CheckTimeScale.monthly,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(6, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenRunOnIntegerColumn_thenReturnsValues() {
        this.sut.setMinLength(1);
        this.sut.setMaxLength(1);

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "result", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, ValueConverter.toDouble(resultTable.column(0).get(0)), 0.001);
    }

}