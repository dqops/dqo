/*
 * Copyright © 2021 DQO.ai (support@dqo.ai)
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
package ai.dqo.bigquery.sensors.table.sql;

import ai.dqo.bigquery.BaseBigQueryIntegrationTest;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.table.checkspecs.sql.TableMinSqlConditionPassedPercentCheckSpec;
import ai.dqo.checks.table.checkspecs.timeliness.TableDataIngestionDelayCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.DataQualitySensorRunnerObjectMother;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.sampledata.IntegrationTestSampleDataObjectMother;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.sensors.table.sql.TableSqlConditionPassedPercentSensorParametersSpec;
import ai.dqo.sensors.table.timeliness.TableTimelinessDataIngestionDelaySensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootTest
public class TableSqlConditionPassedPercentSensorParametersSpecIntegrationTest extends BaseBigQueryIntegrationTest {
    private TableSqlConditionPassedPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private TableMinSqlConditionPassedPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    /**
     * Called before each test.
     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
     *
     * @throws Throwable
     */
    @Override
    @BeforeEach
    protected void setUp() throws Throwable {
        super.setUp();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_average_delay, ProviderType.bigquery);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new TableSqlConditionPassedPercentSensorParametersSpec();
        this.checkSpec = new TableMinSqlConditionPassedPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedAdHoc_thenReturnsValues() {
        this.sut.setSqlCondition("{table} is not null");
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForAdHocCheck(
                sampleTableMetadata, this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedCheckpointDaily_thenReturnsValues() {
        this.sut.setSqlCondition("{table} is not null");
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForCheckpointCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedCheckpointMonthly_thenReturnsValues() {
        this.sut.setSqlCondition("{table} is not null");
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForCheckpointCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues2() {
        this.sut.setSqlCondition("{table} is not null");
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForPartitionedCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.daily, "date2");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(10, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues2() {
        this.sut.setSqlCondition("{table} is not null");
        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForPartitionedCheck(
                sampleTableMetadata, this.checkSpec,CheckTimeScale.monthly, "date2");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }
}
