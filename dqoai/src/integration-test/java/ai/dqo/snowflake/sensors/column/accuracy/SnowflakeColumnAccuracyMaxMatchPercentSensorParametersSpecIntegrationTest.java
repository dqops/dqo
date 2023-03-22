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
package ai.dqo.snowflake.sensors.column.accuracy;

import ai.dqo.checks.CheckTimeScale;
import ai.dqo.checks.column.checkspecs.accuracy.ColumnAccuracyMaxMatchPercentCheckSpec;
import ai.dqo.connectors.ProviderType;
import ai.dqo.execution.sensors.DataQualitySensorRunnerObjectMother;
import ai.dqo.execution.sensors.SensorExecutionResult;
import ai.dqo.execution.sensors.SensorExecutionRunParameters;
import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
import ai.dqo.snowflake.BaseSnowflakeIntegrationTest;
import ai.dqo.sampledata.IntegrationTestSampleDataObjectMother;
import ai.dqo.sampledata.SampleCsvFileNames;
import ai.dqo.sampledata.SampleTableMetadata;
import ai.dqo.sampledata.SampleTableMetadataObjectMother;
import ai.dqo.sensors.column.accuracy.ColumnAccuracyMaxMatchPercentSensorParametersSpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tech.tablesaw.api.Table;


@SpringBootTest
public class SnowflakeColumnAccuracyMaxMatchPercentSensorParametersSpecIntegrationTest extends BaseSnowflakeIntegrationTest {
    private ColumnAccuracyMaxMatchPercentSensorParametersSpec sut;
    private UserHomeContext userHomeContext;
    private ColumnAccuracyMaxMatchPercentCheckSpec checkSpec;
    private SampleTableMetadata sampleTableMetadata;
    private SampleTableMetadata sampleTableMetadataReferenced;

    @BeforeEach
    void setUp() {
        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.ip4_test, ProviderType.snowflake);
        this.sampleTableMetadataReferenced = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.ip6_test, ProviderType.snowflake);
        IntegrationTestSampleDataObjectMother.ensureTableExists(this.sampleTableMetadata);
        IntegrationTestSampleDataObjectMother.ensureTableExists(this.sampleTableMetadataReferenced);
        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
        this.sut = new ColumnAccuracyMaxMatchPercentSensorParametersSpec();
        this.checkSpec = new ColumnAccuracyMaxMatchPercentCheckSpec();
        this.checkSpec.setParameters(this.sut);
    }

    @Test
    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
        this.sut.setReferencedTable(this.sampleTableMetadataReferenced.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("result");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
                sampleTableMetadata, "result", this.checkSpec);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("expected_value", resultTable.column(0).name());
        Assertions.assertEquals("actual_value", resultTable.column(1).name());
        Assertions.assertEquals(1L, resultTable.column(0).get(0));
        Assertions.assertEquals(1L, resultTable.column(1).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedRecurringDaily_thenReturnsValues() {
        this.sut.setReferencedTable(this.sampleTableMetadataReferenced.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("result");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForRecurringCheck(
                sampleTableMetadata, "result", this.checkSpec, CheckTimeScale.daily);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("expected_value", resultTable.column(0).name());
        Assertions.assertEquals("actual_value", resultTable.column(1).name());
        Assertions.assertEquals(1L, resultTable.column(0).get(0));
        Assertions.assertEquals(1L, resultTable.column(1).get(0));
    }

    @Test
    void runSensor_whenSensorExecutedRecurringMonthly_thenReturnsValues() {
        this.sut.setReferencedTable(this.sampleTableMetadataReferenced.getTableData().getHashedTableName());
        this.sut.setReferencedColumn("result");

        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForRecurringCheck(
                sampleTableMetadata, "result", this.checkSpec, CheckTimeScale.monthly);

        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);

        Table resultTable = sensorResult.getResultTable();
        Assertions.assertEquals(1, resultTable.rowCount());
        Assertions.assertEquals("expected_value", resultTable.column(0).name());
        Assertions.assertEquals("actual_value", resultTable.column(1).name());
        Assertions.assertEquals(1L, resultTable.column(0).get(0));
        Assertions.assertEquals(1L, resultTable.column(1).get(0));
    }
}