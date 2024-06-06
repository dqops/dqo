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
package com.dqops.singlestore.sensors.column.integrity;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.integrity.ColumnIntegrityLookupKeyNotFoundCountCheckSpec;
import com.dqops.connectors.mysql.SingleStoreDbConnectionSpecObjectMother;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.column.integrity.ColumnIntegrityForeignKeyNotMatchCountSensorParametersSpec;
import com.dqops.singlestore.BaseSingleStoreDbIntegrationTest;
import com.dqops.testutils.ValueConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;


@SpringBootTest
public class SingleStoreDbColumnIntegrityForeignKeyNotMatchCountSensorParametersSpecIntegrationTest extends BaseSingleStoreDbIntegrationTest {
    private ColumnIntegrityForeignKeyNotMatchCountSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnIntegrityLookupKeyNotFoundCountCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;
    private SampleTableMetadata sampleTableMetadataForeign;
    private ConnectionSpec connectionSpec;

    @BeforeEach
    void setUp() {
        this.connectionSpec = SingleStoreDbConnectionSpecObjectMother.create();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.value_match_right_table, connectionSpec);
        this.sampleTableMetadataForeign = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.value_match_left_table, connectionSpec);
        IntegrationTestSampleDataObjectMother.ensureTableExists(this.sampleTableMetadata);
        IntegrationTestSampleDataObjectMother.ensureTableExists(this.sampleTableMetadataForeign);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnIntegrityForeignKeyNotMatchCountSensorParametersSpec();
        this.checkSpec = new ColumnIntegrityLookupKeyNotFoundCountCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_onNullDataInPrimaryTable_thenReturnsValues() {
        String csvFileName = SampleCsvFileNames.only_nulls;
        SampleTableMetadata nullTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                csvFileName, connectionSpec);
        IntegrationTestSampleDataObjectMother.ensureTableExists(nullTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(nullTableMetadata);
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, sampleTableMetadataForeign);
        this.sut.setForeignTable(this.sampleTableMetadataForeign.getTableData().getHashedTableName());
        this.sut.setForeignColumn("primary_key");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                nullTableMetadata, "int_nulls", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0L, ValueConverter.toLong(resultTable.column(0).get(0)));
    }

    @Test
    void runSensor_onNullDataInForeignTable_thenReturnsValues() {
        String csvFileName = SampleCsvFileNames.only_nulls;
        SampleTableMetadata nullTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(
                csvFileName, connectionSpec);
        IntegrationTestSampleDataObjectMother.ensureTableExists(nullTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(nullTableMetadata);
        UserHomeContextObjectMother.addSampleTable(this.userHomeContext, sampleTableMetadataForeign);

        this.sut.setForeignTable(nullTableMetadata.getTableData().getHashedTableName());
        this.sut.setForeignColumn("int_nulls");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "foreign_key", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(20L, ValueConverter.toLong(resultTable.column(0).get(0)));
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        this.sut.setForeignTable(this.sampleTableMetadataForeign.getTableData().getHashedTableName());
        this.sut.setForeignColumn("primary_key");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "foreign_key", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(5.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        this.sut.setForeignTable(this.sampleTableMetadataForeign.getTableData().getHashedTableName());
        this.sut.setForeignColumn("primary_key");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "foreign_key", this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(5.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        this.sut.setForeignTable(this.sampleTableMetadataForeign.getTableData().getHashedTableName());
        this.sut.setForeignColumn("primary_key");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "foreign_key", this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(5.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues() {
        this.sut.setForeignTable(this.sampleTableMetadataForeign.getTableData().getHashedTableName());
        this.sut.setForeignColumn("primary_key");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "foreign_key", this.checkSpec, CheckTimeScale.daily,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(6, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(1.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues() {
        this.sut.setForeignTable(this.sampleTableMetadataForeign.getTableData().getHashedTableName());
        this.sut.setForeignColumn("primary_key");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "foreign_key", this.checkSpec, CheckTimeScale.monthly,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(6, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(1.0, resultTable.column(0).get(0));
    }
}