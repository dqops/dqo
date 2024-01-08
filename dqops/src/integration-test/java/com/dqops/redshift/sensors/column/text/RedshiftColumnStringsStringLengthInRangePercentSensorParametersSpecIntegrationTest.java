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
package com.dqops.redshift.sensors.column.text;

//import com.dqops.checks.column.checkspecs.strings.ColumnStringStringLengthInRangePercentCheckSpec;
//import com.dqops.connectors.ProviderType;
//import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
//import com.dqops.execution.sensors.SensorExecutionResult;
//import com.dqops.execution.sensors.SensorExecutionRunParameters;
//import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
//import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
//import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
//import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
//import com.dqops.sampledata.SampleCsvFileNames;
//import com.dqops.sampledata.SampleTableMetadata;
//import com.dqops.sampledata.SampleTableMetadataObjectMother;
//import com.dqops.sensors.column.strings.ColumnStringsStringLengthInRangePercentSensorParametersSpec;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import tech.tablesaw.api.Table;
//
//@SpringBootTest
//public class ColumnStringsStringLengthInRangePercentSensorParametersSpecIntegrationTest extends BaseRedshiftIntegrationTest {
//    private ColumnStringsStringLengthInRangePercentSensorParametersSpec sut;
//    private UserHomeContext userHomeContext;
//    private ColumnStringStringLengthInRangePercentCheckSpec checkSpec;
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
//		this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_regex_sensor, ProviderType.redshift);
//        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
//		this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
//		this.sut = new ColumnStringsStringLengthInRangePercentSensorParametersSpec();
//		this.checkSpec = new ColumnStringStringLengthInRangePercentCheckSpec();
//		this.checkSpec.setParameters(this.sut);
//    }
//
//    @Test
//    void runSensor_whenSensorExecutedOnColumnWithStringsAndLowerLengthBoundIs3AndUpperLengthBoundIs5_thenReturnsPercentLengthOfStringInSpecifiedRange() {
//
//        this.sut.setMinLength(3);
//        this.sut.setMaxLength(5);
//
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "length_string", this.checkSpec);
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(1, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(60.0, resultTable.column(0).get(0));
//    }
//
//    @Test
//    void runSensor_whenSensorExecutedOnColumnWithStringsAndLowerLengthBoundIs6AndUpperLengthBoundIs10_thenReturnsPercentLengthOfStringInSpecifiedRange() {
//
//        this.sut.setMinLength(6);
//        this.sut.setMaxLength(10);
//
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "length_string", this.checkSpec);
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(1, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(40.0, resultTable.column(0).get(0));
//    }
//
//    @Test
//    void runSensor_whenSensorExecutedOnColumnWithStringsAndLowerLengthBoundIs2AndUpperLengthBoundIs5_thenReturnsPercentLengthOfStringInSpecifiedRange() {
//
//        this.sut.setMinLength(2);
//        this.sut.setMaxLength(5);
//
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "length_string_int", this.checkSpec);
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(1, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(60.0, resultTable.column(0).get(0));
//    }
//
//    @Test
//    void runSensor_whenSensorExecutedOnEmptyColumnLowerLengthBoundIs2AndUpperLengthBoundIs5_thenReturnsNull() {
//
//        this.sut.setMinLength(2);
//        this.sut.setMaxLength(5);
//
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnAndLegacyCheck(sampleTableMetadata, "count_0", this.checkSpec);
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(1, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(null, resultTable.column(0).get(0));
//    }
//
//
//
//}
