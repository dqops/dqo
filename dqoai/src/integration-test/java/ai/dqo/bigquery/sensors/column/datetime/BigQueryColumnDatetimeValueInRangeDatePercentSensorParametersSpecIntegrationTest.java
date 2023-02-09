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
package ai.dqo.bigquery.sensors.column.datetime;

import ai.dqo.bigquery.BaseBigQueryIntegrationTest;
import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.column.checkspecs.datetime.ColumnDatetimeValueInRangeDatePercentCheckSpec;
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
import ai.dqo.sensors.column.datetime.ColumnDatetimeValueInRangeDatePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

import java.time.LocalDate;
import java.util.Objects;

@SpringBootTest
public class BigQueryColumnDatetimeValueInRangeDatePercentSensorParametersSpecIntegrationTest extends BaseBigQueryIntegrationTest {
    private ColumnDatetimeValueInRangeDatePercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnDatetimeValueInRangeDatePercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_different_time_data_types, ProviderType.bigquery);
        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnDatetimeValueInRangeDatePercentSensorParametersSpec();
        this.checkSpec = new ColumnDatetimeValueInRangeDatePercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedAdHoc_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForAdHocCheck(sampleTableMetadata, "date_type", this.checkSpec);
        this.sut = (ColumnDatetimeValueInRangeDatePercentSensorParametersSpec) runParameters.getSensorParameters();

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        runParameters.setTimeSeries(null);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedCheckpointDaily_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForAdHocCheck(sampleTableMetadata, "date_type", this.checkSpec);
        this.sut = (ColumnDatetimeValueInRangeDatePercentSensorParametersSpec) runParameters.getSensorParameters();

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        runParameters.setTimeSeries(null);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedCheckpointMonthly_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForAdHocCheck(sampleTableMetadata, "date_type", this.checkSpec);
        this.sut = (ColumnDatetimeValueInRangeDatePercentSensorParametersSpec) runParameters.getSensorParameters();

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        runParameters.setTimeSeries(null);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForAdHocCheck(sampleTableMetadata, "date_type", this.checkSpec);
        this.sut = (ColumnDatetimeValueInRangeDatePercentSensorParametersSpec) runParameters.getSensorParameters();

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        runParameters.setTimeSeries(null);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues() {

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForAdHocCheck(sampleTableMetadata, "date_type", this.checkSpec);
        this.sut = (ColumnDatetimeValueInRangeDatePercentSensorParametersSpec) runParameters.getSensorParameters();

        LocalDate lower = LocalDate.of(2022,1,1);
        LocalDate upper = LocalDate.of(2022,1,10);

        this.sut.setMinValue(lower);
        this.sut.setMaxValue(upper);
        runParameters.setTimeSeries(null);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(100.0, resultTable.column(0).get(0));
    }
}
