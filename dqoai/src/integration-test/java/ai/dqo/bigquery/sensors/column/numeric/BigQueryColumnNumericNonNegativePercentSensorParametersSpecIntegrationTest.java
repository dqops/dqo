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
package ai.dqo.bigquery.sensors.column.numeric;

//import ai.dqo.checks.column.checkspecs.numeric.ColumnNonNegativePercentCheckSpec;
//import ai.dqo.connectors.ProviderType;
//import ai.dqo.execution.sensors.DataQualitySensorRunnerObjectMother;
//import ai.dqo.execution.sensors.SensorExecutionResult;
//import ai.dqo.execution.sensors.SensorExecutionRunParameters;
//import ai.dqo.execution.sensors.SensorExecutionRunParametersObjectMother;
//import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContext;
//import ai.dqo.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
//import ai.dqo.sampledata.IntegrationTestSampleDataObjectMother;
//import ai.dqo.sampledata.SampleCsvFileNames;
//import ai.dqo.sampledata.SampleTableMetadata;
//import ai.dqo.sampledata.SampleTableMetadataObjectMother;
//import ai.dqo.sensors.column.numeric.ColumnNumericNonNegativePercentSensorParametersSpec;
//import ai.dqo.snowflake.BaseSnowflakeIntegrationTest;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import tech.tablesaw.api.Table;
//
//@SpringBootTest
//public class ColumnNumericNonNegativePercentSensorParametersSpecIntegrationTest extends BaseSnowflakeIntegrationTest {
//    private ColumnNumericNonNegativePercentSensorParametersSpec sut;
//    private UserHomeContext userHomeContext;
//    private ColumnNonNegativePercentCheckSpec checkSpec;
//    private SampleTableMetadata sampleTableMetadata;
//
//    /**
//     * Called before each test.
//     * This method should be overridden in derived super classes (test classes), but remember to add {@link BeforeEach} annotation in a derived test class. JUnit5 demands it.
//     *
//     * @throws Throwable
//     */
//    @Override
//    @BeforeEach
//    protected void setUp() throws Throwable {
//        super.setUp();
//		this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.continuous_days_one_row_per_day_13_non_negative_floats, ProviderType.snowflake);
//        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
//		this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
//		this.sut = new ColumnNumericNonNegativePercentSensorParametersSpec();
//		this.checkSpec = new ColumnNonNegativePercentCheckSpec();
//		this.checkSpec.setParameters(this.sut);
//    }
//
//    @Test
//    void runSensor_whenSensorExecutedOnColumnWithPositiveValues_thenReturnsValues() {
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "id", this.checkSpec);
//
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(1, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(100.0F, resultTable.column(0).get(0));
//    }
//
//    @Test
//    void runSensor_whenSensorExecutedOnColumnWithNegativeValues_thenReturnsValues() {
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "value", this.checkSpec);
//
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(1, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(52.0F, resultTable.column(0).get(0));
//    }
//}
