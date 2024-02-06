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
package com.dqops.spark.sensors.column.datetime;

import com.dqops.checks.column.checkspecs.datetime.ColumnDateInRangePercentCheckSpec;
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
import com.dqops.sensors.column.datetime.ColumnDateInRangePercentSensorParametersSpec;
import com.dqops.spark.BaseSparkIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.time.LocalDate;

@SpringBootTest
public class SparkColumnDatetimeDateInRangePercentSensorParametersSpecIntegrationTest extends BaseSparkIntegrationTest {
    private ColumnDateInRangePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnDateInRangePercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_different_time_data_types, ProviderType.spark);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnDateInRangePercentSensorParametersSpec();
        this.checkSpec = new ColumnDateInRangePercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(sampleTableMetadata, "date_type", this.checkSpec);
        this.sut = (ColumnDateInRangePercentSensorParametersSpec) runParameters.getSensorParameters();

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinDate(lower);
        this.sut.setMaxDate(upper);
        runParameters.setTimeSeries(null);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(sampleTableMetadata, "date_type", this.checkSpec);
        this.sut = (ColumnDateInRangePercentSensorParametersSpec) runParameters.getSensorParameters();

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinDate(lower);
        this.sut.setMaxDate(upper);
        runParameters.setTimeSeries(null);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(sampleTableMetadata, "date_type", this.checkSpec);
        this.sut = (ColumnDateInRangePercentSensorParametersSpec) runParameters.getSensorParameters();

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinDate(lower);
        this.sut.setMaxDate(upper);
        runParameters.setTimeSeries(null);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(sampleTableMetadata, "date_type", this.checkSpec);
        this.sut = (ColumnDateInRangePercentSensorParametersSpec) runParameters.getSensorParameters();

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinDate(lower);
        this.sut.setMaxDate(upper);
        runParameters.setTimeSeries(null);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(sampleTableMetadata, "date_type", this.checkSpec);
        this.sut = (ColumnDateInRangePercentSensorParametersSpec) runParameters.getSensorParameters();

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinDate(lower);
        this.sut.setMaxDate(upper);
        runParameters.setTimeSeries(null);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }
}
