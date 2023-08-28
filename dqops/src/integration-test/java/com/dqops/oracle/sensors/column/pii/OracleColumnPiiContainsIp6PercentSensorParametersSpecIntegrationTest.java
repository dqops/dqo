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
package com.dqops.oracle.sensors.column.pii;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.column.checkspecs.pii.ColumnPiiContainsIp6PercentCheckSpec;
import com.dqops.connectors.ProviderType;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.oracle.BaseOracleIntegrationTest;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.column.pii.ColumnPiiContainsIp6PercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;


@SpringBootTest
class OracleColumnPiiContainsIp6PercentSensorParametersSpecIntegrationTest extends BaseOracleIntegrationTest {
    private ColumnPiiContainsIp6PercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnPiiContainsIp6PercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;
    private final String testedColumnName = "ip6";
    private final String validExamplesFilterText = "\"result\" = 1";
    private final String invalidExamplesFilterText = "\"result\" = 0";

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.contains_ip6_test, ProviderType.oracle);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnPiiContainsIp6PercentSensorParametersSpec();
        this.checkSpec = new ColumnPiiContainsIp6PercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    // todo: test for verification the 0.0 score when no rows (fragment such as: WHEN COUNT(*) = 0 THEN 0.0)

    @Test
    void runSensor_whenSensorExecutedProfilingValidRows_thenReturnsOneRowWithTotallySuccessPercentage() {
        this.sut.setFilter(validExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, testedColumnName, this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0F, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedProfilingInvalidRows_thenReturnsOneRowWithTotallyFailedPercentage() {
        this.sut.setFilter(invalidExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, testedColumnName, this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0F, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDailyValidRows_thenReturnsOneRowWithTotallySuccessPercentage() {
        this.sut.setFilter(validExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForMonitoringCheck(
                        sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0F, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDailyInvalidRows_thenReturnsOneRowWithTotallyFailedPercentage() {
        this.sut.setFilter(invalidExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForMonitoringCheck(
                        sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0F, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthlyValidRows_thenReturnsOneRowWithTotallySuccessPercentage() {
        this.sut.setFilter(validExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForMonitoringCheck(
                        sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0F, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthlyInvalidRows_thenReturnsOneRowWithTotallyFailedPercentage() {
        this.sut.setFilter(invalidExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother
                .createForTableColumnForMonitoringCheck(
                        sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0F, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDailyValidRows_thenReturnTotallySuccessPercentage() {
        this.sut.setFilter(validExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.daily,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(2, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0F, resultTable.column(0).get(0));
        Assertions.assertEquals(100.0F, resultTable.column(0).get(1));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDailyInvalidRows_thenReturnsTotallyFailedPercentage() {
        this.sut.setFilter(invalidExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.daily,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(3, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0F, resultTable.column(0).get(0));
        Assertions.assertEquals(0.0F, resultTable.column(0).get(1));
        Assertions.assertEquals(0.0F, resultTable.column(0).get(2));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthlyValidRows_thenReturnsTotallySuccessPercentage() {
        this.sut.setFilter(validExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.monthly,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0F, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthlyInvalidRows_thenReturnsTotallyFailedPercentage() {
        this.sut.setFilter(invalidExamplesFilterText);
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                sampleTableMetadata, testedColumnName, this.checkSpec, CheckTimeScale.monthly,"date");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(2, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(0.0F, resultTable.column(0).get(0));
        Assertions.assertEquals(0.0F, resultTable.column(0).get(1));
    }

}