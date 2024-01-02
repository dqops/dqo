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
package com.dqops.trino.sensors.column.strings;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.strings.ColumnStringInvalidIp6AddressCountCheckSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.trino.BaseTrinoIntegrationTest;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.column.strings.ColumnStringsStringInvalidIp6AddressCountSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;


@SpringBootTest
public class TrinoColumnStringsStringInvalidIp6AddressCountSensorParametersSpecIntegrationTest extends BaseTrinoIntegrationTest {
    private ColumnStringsStringInvalidIp6AddressCountSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnStringInvalidIp6AddressCountCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.ip6_test, ProviderType.trino);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnStringsStringInvalidIp6AddressCountSensorParametersSpec();
        this.checkSpec = new ColumnStringInvalidIp6AddressCountCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "ip6", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(5L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "ip6", this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(5L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
                sampleTableMetadata, "ip6", this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(5L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "ip6", this.checkSpec, CheckTimeScale.daily,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(6, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(1L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues() {
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, "ip6", this.checkSpec, CheckTimeScale.monthly,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(6, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(1L, resultTable.column(0).get(0));
    }
}
