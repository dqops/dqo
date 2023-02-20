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
package ai.dqo.postgresql.sensors.column.bool;

import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.column.checkspecs.bool.ColumnTruePercentCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.DataQualitySensorRunnerObjectMother;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.postgresql.BasePostgresqlIntegrationTest;
import ai.dqo.sampledata.IntegrationTestSampleDataObjectMother;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.sensors.column.bool.ColumnBoolTruePercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;

@SpringBootTest
public class PostgresqlColumnBoolTruePercentSensorParametersSpecIntegrationTest extends BasePostgresqlIntegrationTest {

        private ColumnBoolTruePercentSensorParametersSpec sut;
        private UserHomeContext userHomeContext;
        private ColumnTruePercentCheckSpec checkSpec;
        private SampleTableMetadata sampleTableMetadata;

        @BeforeEach
        void setUp() {
            this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.string_test_data, ProviderType.postgresql);
            IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
            this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
            this.sut = new ColumnBoolTruePercentSensorParametersSpec();
            this.checkSpec = new ColumnTruePercentCheckSpec();
            this.checkSpec.setParameters(this.sut);
        }

        @Test
        void runSensor_whenSensorExecutedAdHoc_thenReturnsValues() {
            SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForAdHocCheck(
                    sampleTableMetadata, "boolean_type_placeholder", this.checkSpec);

            SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(13.333, (double) resultTable.column(0).get(0),3);
    }

        @Test
        void runSensor_whenSensorExecutedCheckpointDaily_thenReturnsValues() {
            SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForCheckpointCheck(
                    sampleTableMetadata, "boolean_type_placeholder", this.checkSpec, CheckTimeScale.daily);

            SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("actual_value", resultTable.column(0).name());
        Assertions.assertEquals(13.333, (double) resultTable.column(0).get(0),3);
    }

        @Test
        void runSensor_whenSensorExecutedCheckpointMonthly_thenReturnsValues() {
            SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForCheckpointCheck(
                    sampleTableMetadata, "boolean_type_placeholder", this.checkSpec, CheckTimeScale.monthly);

            SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

            Table resultTable = sensorResult.getResultTable();
            Assertions.assertEquals(1, resultTable.rowCount());
            Assertions.assertEquals("actual_value", resultTable.column(0).name());
            Assertions.assertEquals(13.333333333333334, resultTable.column(0).get(0));
        }

        @Test
        void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues() {
            SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                    sampleTableMetadata, "boolean_type_placeholder", this.checkSpec, CheckTimeScale.daily,"date");

            SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

            Table resultTable = sensorResult.getResultTable();
            Assertions.assertEquals(25, resultTable.rowCount());
            Assertions.assertEquals("actual_value", resultTable.column(0).name());
            Assertions.assertEquals(16.666666666666668, resultTable.column(0).get(0));
        }

        @Test
        void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues() {
            SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
                    sampleTableMetadata, "boolean_type_placeholder", this.checkSpec, CheckTimeScale.monthly,"date");

            SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

            Table resultTable = sensorResult.getResultTable();
            Assertions.assertEquals(1, resultTable.rowCount());
            Assertions.assertEquals("actual_value", resultTable.column(0).name());
            Assertions.assertEquals(13.333333333333334, resultTable.column(0).get(0));
        }

}