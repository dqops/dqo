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
///*
// * Copyright © 2021 DQOps (support@dqops.com)
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.dqops.redshift.column.strings;
//
//import com.dqops.checks.CheckTimeScale;
//import com.dqops.checks.column.checkspecs.strings.ColumnStringMostPopularValuesCheckSpec;
//import com.dqops.connectors.ProviderType;
//import com.dqops.execution.sensors.DataQualitySensorRunnerObjectMother;
//import com.dqops.execution.sensors.SensorExecutionResult;
//import com.dqops.execution.sensors.SensorExecutionRunParameters;
//import com.dqops.execution.sensors.SensorExecutionRunParametersObjectMother;
//import com.dqops.metadata.groupings.DataStreamLevelSource;
//import com.dqops.metadata.groupings.DataStreamLevelSpec;
//import com.dqops.metadata.groupings.DataStreamMappingSpec;
//import com.dqops.metadata.storage.localfiles.userhome.UserHomeContext;
//import com.dqops.metadata.storage.localfiles.userhome.UserHomeContextObjectMother;
//import com.dqops.redshift.BaseRedshiftIntegrationTest;
//import com.dqops.sampledata.IntegrationTestSampleDataObjectMother;
//import com.dqops.sampledata.SampleCsvFileNames;
//import com.dqops.sampledata.SampleTableMetadata;
//import com.dqops.sampledata.SampleTableMetadataObjectMother;
//import com.dqops.sensors.column.strings.ColumnStringsStringMostPopularValuesSensorParametersSpec;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import tech.tablesaw.api.Table;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@SpringBootTest
//public class RedshiftColumnStringsStringMostPopularValuesSensorParametersSpecIntegrationTests extends BaseRedshiftIntegrationTest {
//    private ColumnStringsStringMostPopularValuesSensorParametersSpec sut;
//    private UserHomeContext userHomeContext;
//    private ColumnStringMostPopularValuesCheckSpec checkSpec;
//    private SampleTableMetadata sampleTableMetadata;
//
//    @BeforeEach
//    void setUp() {
//        this.sampleTableMetadata = SampleTableMetadataObjectMother.createSampleTableMetadataForCsvFile(SampleCsvFileNames.test_data_values_in_set, ProviderType.redshift);
//        IntegrationTestSampleDataObjectMother.ensureTableExists(sampleTableMetadata);
//        this.userHomeContext = UserHomeContextObjectMother.createInMemoryFileHomeContextForSampleTable(sampleTableMetadata);
//        this.sut = new ColumnStringsStringMostPopularValuesSensorParametersSpec();
//        this.checkSpec = new ColumnStringMostPopularValuesCheckSpec();
//        this.checkSpec.setParameters(this.sut);
//    }
//
//    @Test
//    void runSensor_whenSensorExecutedProfiling_thenReturnsValues() {
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForProfilingCheck(
//                sampleTableMetadata, "strings_with_numbers", this.checkSpec);
//
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(1, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals("", resultTable.column(0).get(0));
//    }
//
//    @Test
//    void runSensor_whenSensorExecutedMonitoringDaily_thenReturnsValues() {
//        List<String> values = new ArrayList<>();
//        values.add("a111a");
//        values.add("d44d");
//        this.sut.setExpectedValues(values);
//        this.sut.setTopValues(2L);
//        this.sut.setFilter("id < 5");
//        DataStreamMappingSpec dataStreamMapping = this.sampleTableMetadata.getTableSpec().getDataStreams().getFirstDataStreamMapping();
//        dataStreamMapping.setLevel1(new DataStreamLevelSpec() {{
//            setSource(DataStreamLevelSource.column_value);
//            setColumn("id");
//        }});
//        this.sampleTableMetadata.getTableSpec().getDataStreams().setFirstDataStreamMapping(dataStreamMapping);
//
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
//                sampleTableMetadata, "strings_with_numbers", this.checkSpec, CheckTimeScale.daily);
//
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(4, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(1L, resultTable.column(0).get(0));
//    }
//
//    @Test
//    void runSensor_whenSensorExecutedMonitoringMonthly_thenReturnsValues() {
//        List<String> values = new ArrayList<>();
//        values.add("a111a");
//        values.add("d44d");
//        this.sut.setExpectedValues(values);
//        this.sut.setTopValues(5L);
//
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForMonitoringCheck(
//                sampleTableMetadata, "strings_with_numbers", this.checkSpec, CheckTimeScale.monthly);
//
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(1, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(2L, resultTable.column(0).get(0));
//    }
//
//    @Test
//    void runSensor_whenSensorExecutedPartitionedDaily_thenReturnsValues() {
//        List<String> values = new ArrayList<>();
//        values.add("a111a");
//        values.add("d44d");
//        this.sut.setExpectedValues(values);
//        this.sut.setTopValues(5L);
//
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
//                sampleTableMetadata, "strings_with_numbers", this.checkSpec, CheckTimeScale.daily, "date");
//
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(25, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(2L, resultTable.column(0).get(0));
//    }
//
//    @Test
//    void runSensor_whenSensorExecutedPartitionedMonthly_thenReturnsValues() {
//        List<String> values = new ArrayList<>();
//        values.add("a111a");
//        values.add("d44d");
//        this.sut.setExpectedValues(values);
//        this.sut.setTopValues(5L);
//
//        SensorExecutionRunParameters runParameters = SensorExecutionRunParametersObjectMother.createForTableColumnForPartitionedCheck(
//                sampleTableMetadata, "strings_with_numbers", this.checkSpec, CheckTimeScale.monthly, "date");
//
//        SensorExecutionResult sensorResult = DataQualitySensorRunnerObjectMother.executeSensor(this.userHomeContext, runParameters);
//
//        Table resultTable = sensorResult.getResultTable();
//        Assertions.assertEquals(1, resultTable.rowCount());
//        Assertions.assertEquals("actual_value", resultTable.column(0).name());
//        Assertions.assertEquals(2L, resultTable.column(0).get(0));
//    }
//}