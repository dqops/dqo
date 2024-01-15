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
package com.dqops.snowflake.sensors.table.customsql;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.table.checkspecs.customsql.TableSqlConditionFailedCheckSpec;
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
import com.dqops.sensors.table.customsql.TableSqlConditionFailedCountSensorParametersSpec;
import com.dqops.snowflake.BaseSnowflakeIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;


@SpringBootTest
public class TableSqlConditionFailedCountSensorParametersSpecIntegrationTest extends BaseSnowflakeIntegrationTest {
    private TableSqlConditionFailedCountSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private TableSqlConditionFailedCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_average_delay, ProviderType.snowflake);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new TableSqlConditionFailedCountSensorParametersSpec();
        this.checkSpec = new TableSqlConditionFailedCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        this.sut.setSqlCondition("length("
                +"\""+this.sampleTableMetadata.getTableSpec().getColumns().getAt(0).getColumnName()+"\""
                +") > 3");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForProfilingCheck(
                sampleTableMetadata, this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(13L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        this.sut.setSqlCondition("length("
                +"\""+this.sampleTableMetadata.getTableSpec().getColumns().getAt(0).getColumnName()+"\""
                +") > 3");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForMonitoringCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(13L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        this.sut.setSqlCondition("length("
                +"\""+this.sampleTableMetadata.getTableSpec().getColumns().getAt(0).getColumnName()+"\""
                +") > 3");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForMonitoringCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(13L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues2() {
        this.sut.setSqlCondition("length("
                +"\""+this.sampleTableMetadata.getTableSpec().getColumns().getAt(0).getColumnName()+"\""
                +") > 3");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForPartitionedCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.daily, "date2");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(10, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(3L, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues2() {
        this.sut.setSqlCondition("length("
                +"\""+this.sampleTableMetadata.getTableSpec().getColumns().getAt(0).getColumnName()+"\""
                +") > 3");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForPartitionedCheck(
                sampleTableMetadata, this.checkSpec,CheckTimeScale.monthly, "date2");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(13L, resultTable.column(0).get(0));
    }
}
