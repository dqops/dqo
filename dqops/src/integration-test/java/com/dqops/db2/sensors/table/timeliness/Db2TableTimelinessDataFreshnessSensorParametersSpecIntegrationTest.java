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
package com.dqops.db2.sensors.table.timeliness;

import com.dqops.checks.CheckTimeScale;
import com.dqops.checks.table.checkspecs.timeliness.TableDataFreshnessCheckSpec;
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
import com.dqops.sensors.table.timeliness.TableTimelinessDataFreshnessSensorParametersSpec;
import com.dqops.testutils.ValueConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootTest
public class Db2TableTimelinessDataFreshnessSensorParametersSpecIntegrationTest extends BaseDb2IntegrationTest {
    private TableTimelinessDataFreshnessSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private TableDataFreshnessCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_average_delay, ProviderType.db2);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new TableTimelinessDataFreshnessSensorParametersSpec();
        this.checkSpec = new TableDataFreshnessCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForProfilingCheck(
                sampleTableMetadata, this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        LocalDateTime ldt = LocalDateTime.now();
        Duration timeDiff = Duration.between(this.sampleTableMetadata.getTableData().getTable().dateTimeColumn("date1").max(),ldt);
        double min = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 - 1;
        double max = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 + 1;

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertTrue(ValueConverter.toDouble(resultTable.column(0).get(0))>=min && ValueConverter.toDouble(resultTable.column(0).get(0))<=max);
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForMonitoringCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        LocalDateTime ldt = LocalDateTime.now();
        Duration timeDiff = Duration.between(this.sampleTableMetadata.getTableData().getTable().dateTimeColumn("date1").max(),ldt);
        double min = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 - 1;
        double max = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 + 1;

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertTrue(ValueConverter.toDouble(resultTable.column(0).get(0))>=min && ValueConverter.toDouble(resultTable.column(0).get(0))<=max);
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
        this.sampleTableMetadata.getTableSpec().getTimestampColumns().setEventTimestampColumn("date1");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableForMonitoringCheck(
                sampleTableMetadata, this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        LocalDateTime ldt = LocalDateTime.now();
        Duration timeDiff = Duration.between(this.sampleTableMetadata.getTableData().getTable().dateTimeColumn("date1").max(),ldt);
        double min = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 - 1;
        double max = timeDiff.toMillis() / 24.0 / 3600.0 / 1000.0 + 1;

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertTrue(ValueConverter.toDouble(resultTable.column(0).get(0))>=min && ValueConverter.toDouble(resultTable.column(0).get(0))<=max);
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues() {
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
        Assertions.assertTrue(ValueConverter.toDouble(resultTable.column(0).get(0))>=min && ValueConverter.toDouble(resultTable.column(0).get(0))<=max);
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues() {
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
        Assertions.assertTrue(ValueConverter.toDouble(resultTable.column(0).get(0))>=min && ValueConverter.toDouble(resultTable.column(0).get(0))<=max);
    }
}
