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
package com.dqops.questdb.sensors.table.timeliness;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.table.checkspecs.timeliness.TableDataIngestionDelayCheckSpec;
import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
import com.dqops.execution.sensors.SensorExecutionResult;
import com.dqops.execution.sensors.SensorExecutionRunParameters;
import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import com.dqops.questdb.BaseQuestDbIntegrationTest;
import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
import com.dqops.sampledata.SampleCsvFileNames;
import com.dqops.sampledata.SampleTableMetadata;
import com.dqops.sampledata.SampleTableMetadataObjectMother;
import com.dqops.sensors.table.timeliness.TableTimelinessDataIngestionDelaySensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;


import com.dqops.metadata.sources.ConnectionSpec;
import com.dqops.connectors.questdb.QuestDbConnectionSpecObjectMother;

@SpringBootTest
public class QuestDbTableTimelinessDataIngestionDelaySensorParametersSpecIntegrationTest extends BaseQuestDbIntegrationTest {
    private TableTimelinessDataIngestionDelaySensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private TableDataIngestionDelayCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;
    private ConnectionSpec connectionSpec;

    @BeforeEach
    void setUp() {
        this.connectionSpec = QuestDbConnectionSpecObjectMother.create();
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_average_delay, this.connectionSpec);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new TableTimelinessDataIngestionDelaySensorParametersSpec();
        this.checkSpec = new TableDataIngestionDelayCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1");
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setIngestionTimestampColumn("date2");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForProfilingCheck(
                sampleTableMetadata, this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(9.083, (double) resultTable.column(0).get(0), 0.001);
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1");
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setIngestionTimestampColumn("date2");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForMonitoringCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(9.083, (double) resultTable.column(0).get(0), 0.001);
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1");
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setIngestionTimestampColumn("date2");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForMonitoringCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(9.083, (double) resultTable.column(0).get(0), 0.001);
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues2() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1");
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setIngestionTimestampColumn("date2");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForPartitionedCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.daily, "date1");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(10, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(10.041, (double) resultTable.column(0).get(0), 0.001);
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues2() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1");
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setIngestionTimestampColumn("date2");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForPartitionedCheck(
                sampleTableMetadata, this.checkSpec,CheckTimeScale.monthly, "date1");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(9.083, (double) resultTable.column(0).get(0), 0.001);
    }
}
