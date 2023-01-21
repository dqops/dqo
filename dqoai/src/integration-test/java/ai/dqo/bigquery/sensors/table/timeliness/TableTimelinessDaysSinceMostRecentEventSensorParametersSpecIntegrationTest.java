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
package ai.dqo.bigquery.sensors.table.timeliness;

import ai.dqo.bigquery.BaseBigQueryIntegrationTest;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.table.checkspecs.timeliness.TableDaysSinceMostRecentEventCheckSpec;
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
import ai.dqo.sensors.table.timeliness.TableTimelinessDaysSinceMostRecentEventSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootTest
public class TableTimelinessDaysSinceMostRecentEventSensorParametersSpecIntegrationTest extends BaseBigQueryIntegrationTest {
    private TableTimelinessDaysSinceMostRecentEventSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private TableDaysSinceMostRecentEventCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_average_delay, ProviderType.bigquery);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new TableTimelinessDaysSinceMostRecentEventSensorParametersSpec();
        this.checkSpec = new TableDaysSinceMostRecentEventCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedAdHoc_thenReturnsValues() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForAdHocCheck(
                sampleTableMetadata, this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        LocalDateTime ldt = LocalDateTime.now();
        Duration timeDiff = Duration.between(this.sampleTableMetadata.getTableData().getTable().dateTimeColumn("date1").max(),ldt);
        double min = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 - 1;
        double max = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 + 1;

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertTrue((double)resultTable.column(0).get(0)>=min && (double)resultTable.column(0).get(0)<=max);
    }

    @Test
    void runSensor_whenSensorExecutedCheckpointDaily_thenReturnsValues() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForCheckpointCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        LocalDateTime ldt = LocalDateTime.now();
        Duration timeDiff = Duration.between(this.sampleTableMetadata.getTableData().getTable().dateTimeColumn("date1").max(),ldt);
        double min = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 - 1;
        double max = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 + 1;

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertTrue((double)resultTable.column(0).get(0)>=min && (double)resultTable.column(0).get(0)<=max);
    }

    @Test
    void runSensor_whenSensorExecutedCheckpointMonthly_thenReturnsValues() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForCheckpointCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        LocalDateTime ldt = LocalDateTime.now();
        Duration timeDiff = Duration.between(this.sampleTableMetadata.getTableData().getTable().dateTimeColumn("date1").max(),ldt);
        double min = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 - 1;
        double max = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 + 1;

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertTrue((double)resultTable.column(0).get(0)>=min && (double)resultTable.column(0).get(0)<=max);
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues2() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForPartitionedCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.daily, "date2");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        LocalDateTime ldt = LocalDateTime.now();
        Duration timeDiff = Duration.between(this.sampleTableMetadata.getTableData().getTable().dateTimeColumn("date1").get(0),ldt);
        double min = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 - 1;
        double max = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 + 1;

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(10, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertTrue((double)resultTable.column(0).get(0)>=min && (double)resultTable.column(0).get(0)<=max);
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues2() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForPartitionedCheck(
                sampleTableMetadata, this.checkSpec,CheckTimeScale.monthly, "date2");

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        LocalDateTime ldt = LocalDateTime.now();
        Duration timeDiff = Duration.between(this.sampleTableMetadata.getTableData().getTable().dateTimeColumn("date1").max(),ldt);
        double min = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 - 1;
        double max = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 + 1;

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertTrue((double)resultTable.column(0).get(0)>=min && (double)resultTable.column(0).get(0)<=max);
    }
}
